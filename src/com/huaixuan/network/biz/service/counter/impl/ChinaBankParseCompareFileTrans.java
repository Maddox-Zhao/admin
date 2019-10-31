package com.huaixuan.network.biz.service.counter.impl;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import org.springframework.stereotype.Service;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;

import com.huaixuan.network.biz.domain.counter.BankCompareItem;
import com.huaixuan.network.biz.domain.counter.CounterCompareReq;
import com.huaixuan.network.biz.domain.counter.CounterOutPintMessage;
import com.huaixuan.network.biz.domain.counter.InputFile;
import com.huaixuan.network.biz.enums.EnumZhiFuBaoMistakeType;
import com.hundsun.itrans.common.util.Money;

@Service("chinaBankParseCompareFileTrans")
public class ChinaBankParseCompareFileTrans  extends AbstractParseCompareFileTrans {

	/**
	 * 解析网银在线文件
	 */
	protected CounterOutPintMessage getParseFile(CounterCompareReq counterCompareReq) {
        CounterOutPintMessage counterOutPintMessageu = new CounterOutPintMessage(); //对账文件输出的参数bean
        InputStream is = null;
        BufferedInputStream bis = null;
        Workbook rwb = null;
        try {
            StringBuffer sBuf = new StringBuffer();
            List<BankCompareItem> itemListu = new ArrayList<BankCompareItem>();
            int line = 0;
            int successCountLine = 0; //总共解析的成功完整行数
            int failCountLine = 0; //解析的失败的行数
            //            boolean startFlag = false; //文本开始字符标志
            boolean endFlag = false; //文本结束字符标志

            //-------------解析excel开始-------------
            File f = new File(counterCompareReq.getCompareAccountFile());
            is = new FileInputStream(f);
            bis = new BufferedInputStream(is);
			// 获得workbook 工作薄
			rwb = Workbook.getWorkbook(bis);
			Sheet rs = rwb.getSheet(0);
			//--------------解析excel结束-------------
			
            Long batchId = inputFileDao.getInputFileSeq(); //得到文件批次号

            for (int i=1;i<(rs.getRows()-1);i++) {
            	line++;
                sBuf.delete(0, sBuf.length());
                String failDescriptionStr = ""; //导入错误原因


                String payDate = "";//订单生成时间
                String bankType = ""; //银行类型
                String bankBillNo = ""; //银行订单号
                String inPayAmount = ""; //收入金额
                String bankSerialNo = ""; //银行流水号
                String counterType = ""; //业务类型

                /********************如果导入的文件的元素不匹配的话*******************************************/
                try {
                //订单生成时间
                	Cell c7 = rs.getCell(7, i);
                	payDate = c7.getContents();
                //银行类型
        			bankType = counterCompareReq.getCompareAccountType();
        			
        		//银行订单号
        			Cell c0 = rs.getCell(0, i);
        			bankBillNo = c0.getContents();

                  //收入金额
                    Cell c1 = rs.getCell(1, i);
                    inPayAmount = c1.getContents();

                  //银行流水号   ***网银在线文件没有流水号，用订单号代替
                    bankSerialNo = bankBillNo;
                    
                  //银行流水号
                    Cell c4 = rs.getCell(4, i);
                    counterType = c4.getContents();
                    
                } catch (Exception e) {
                    log.error("Get data error", e);
                    sBuf.append(EnumZhiFuBaoMistakeType.ONLINIE.getValue() + line
                                + EnumZhiFuBaoMistakeType.LINE.getValue()
                                + EnumZhiFuBaoMistakeType.PARSEMISTAKE.getValue());
                }
                /***********************************************************************************************/

                if (payDate.equals("") || payDate == null)//如果订单生成时间为空的话
                    sBuf.append(EnumZhiFuBaoMistakeType.PAYDATE.getValue()); //订单生成时间为空
                else //如果订单生成时间不为空的话
                {
					 SimpleDateFormat sdf = new  SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                    SimpleDateFormat sdff = new SimpleDateFormat("yyyyMMdd"); //格式转换
                    try {
                        payDate = sdff.format(sdf.parse(payDate)); //解析后的订单生成时间
                    } catch (Exception e) {
                        log.error("Date format error", e);
                        sBuf.append(EnumZhiFuBaoMistakeType.PAYDATEPARSEMISTAKE.getValue()); //订单生成时间解析错误
                    }
                }

                if (bankBillNo.equals("") || bankBillNo == null)
                    sBuf.append(EnumZhiFuBaoMistakeType.BANKBILLNO.getValue()); //银行订单号为空
                else {
                    if (bankBillNo.length() > 32) //如果银行订单号大于32个字符的话
                        sBuf.append(EnumZhiFuBaoMistakeType.BANKBILLNOMISTAKE.getValue()); //银行订单号字符过长      
                }

                if (inPayAmount.equals("") || inPayAmount == null)
                    sBuf.append(EnumZhiFuBaoMistakeType.INPAYAMOUNT.getValue()); //收入金额不能为空
                else //如果收入金额不为空的话
                {
                    Pattern pattern = Pattern.compile("(\\d{1,13}+.{0,1}\\d{0,2})$");
                    if (!pattern.matcher(inPayAmount).matches()) //如果不是数字类型的话，
                        sBuf
                            .append(EnumZhiFuBaoMistakeType.PAYMOUNTPARSEMISTAKE.getValue()); //发生金额解析错误
                    else { //是数字类型的话
                        if (Float.parseFloat(inPayAmount) <= 0) { //如果收入金额小于等于零的话
                            sBuf.append(EnumZhiFuBaoMistakeType.PAYMOUNTPARSEMISTAKE
                                .getValue()); //发生金额解析错误
                        }
                    }

                }

                if (bankSerialNo.equals("") || bankSerialNo == null)
                    sBuf.append(EnumZhiFuBaoMistakeType.BANKSERIALNO.getValue()); //银行流水号不能为空

                if (counterType.equals("") || counterType == null
                    || !counterType.equals(EnumZhiFuBaoMistakeType.SUCCESSORDER.getValue()))
                    sBuf.append(EnumZhiFuBaoMistakeType.COUNTERTYPEMISTAKE.getValue()); //业务类型出错
                failDescriptionStr = sBuf.toString();

                if (failDescriptionStr.equals("")
                    && Float.parseFloat(inPayAmount.trim()) > 0) { //得到的这行如果是在线支付，并且收入金额大于零的话，并且对应的这行的格式正确的话，才允许添加
                    successCountLine++; //成功导入条数增加
                    BankCompareItem bankCompareItemu = new BankCompareItem(); //新建一个银行对账文件明细对象
                    bankCompareItemu.setBatchId(batchId); //设置文件批次号

                    /****开始订单生产时间，这边订单生成时间要注意，其形式是yyyyMMdd,所以要相应转换******/
                    bankCompareItemu.setPayDate(payDate); //设置订单生产时间
                    /****订结束单生产时间，这边订单生成时间要注意，其形式是yyyyMMdd,所以要相应转换******/
                    bankCompareItemu.setBankType(bankType);//设置银行类型
                    bankCompareItemu.setBankBillNo(bankBillNo); //设置银行订单号

                    /*************************发生额设置开始****************************/

                    Money amountMoney = new Money(inPayAmount);
                    Long amount = amountMoney.getCent();
                    bankCompareItemu.setPayAmount(amount); //如果收入金额不等于0，就设置收入金额

                    /*************************发生额设置结束****************************/

                    bankCompareItemu.setBankSerialNo(bankSerialNo); //设置业务流水号

                    itemListu.add(bankCompareItemu); //增加bankCompareItemu

                    if (successCountLine % 1000 == 0) //如果listu有1000个对象，就开始进行批量插入
                    {
                        addBatchCompareItem("C", itemListu); //添加对帐文件导入明细
                        itemListu.clear(); //插入完之后，listu进行清空，进行下一次插入
                    }

				} else{ // 否则不允许
					failCountLine++; // 失败导入条数增加
					failDescriptionStr = EnumZhiFuBaoMistakeType.PAYNO
							.getValue()
							+ bankBillNo
							+ EnumZhiFuBaoMistakeType.IMPORTFAIL.getValue()
							+ failDescriptionStr;
					counterOutPintMessageu.getFailDescriptionListu().add(
							failDescriptionStr); // 添加导入失败明细
					continue;
				}
			}
   
            
            
            
          //----------读取EXCEL完毕
            addBatchCompareItem("C", itemListu); //如果是已经到了文件结束标志，就添加对帐文件导入明细
            
            /**start  查询对账明细表是否有重复银行订单号,如果存在则删除重复记录并且前台提示   2010.9.3 cw***/
            InputFile input = new InputFile();
            input.setBatchId(batchId); //设置导入文件批次号
            input.setBankType(counterCompareReq.getCompareAccountType()); //设置银行类型
            List<BankCompareItem> bankCompareItemList = inputFileDao.getBankCompareItemBankBillNO(input);
            if(bankCompareItemList !=null&&bankCompareItemList.size()>0){
            	inputFileDao.deleteBankCompareItemBankBillNO(input);
            	String failDescriptionStr = "";
            	BankCompareItem bci = null;
            	for(int a=0;a<bankCompareItemList.size();a++){
            		bci = bankCompareItemList.get(a);
            		failCountLine+=bci.getRecount(); // 失败导入条数增加
            		successCountLine -=bci.getRecount(); // 成功导入条数减少
					failDescriptionStr = EnumZhiFuBaoMistakeType.PAYNO
							.getValue()
							+ bci.getBankBillNo()
							+ EnumZhiFuBaoMistakeType.IMPORTFAIL.getValue()
							+ EnumZhiFuBaoMistakeType.RE_BANKBILLNO.getValue();
					counterOutPintMessageu.getFailDescriptionListu().add(
							failDescriptionStr); // 添加导入失败明细
            	}
            }
            /**end  查询对账明细表是否有重复银行订单号,如果存在则删除重复记录并且前台提示   2010.9.3 cw***/
            
            /*******************开始添加导入文件批次表************************/
            InputFile inputFile = new InputFile();
            inputFile.setBatchId(batchId); //设置导入文件批次号
            inputFile.setOperateType("C"); //设置操作类型
            inputFile.setBankType(counterCompareReq.getCompareAccountType()); //设置银行类型
            inputFile.setWaitDealCount(Long.parseLong(String.valueOf(successCountLine))); //设置待处理笔数
            inputFile.setStatus(Long.valueOf(1)); //设置状态,1：文件已导入，待处理，2：处理中，3：处理结束
            inputFile.setDealOperator(counterCompareReq.getDealOperator()); //设置处理操作员
            inputFile.setFileName(counterCompareReq.getCompareReNameAccountFile()); //设置上传之后的文件名
            inputFileDao.addInputFile(inputFile); //添加导入文件批次表
            /*******************结束添加导入文件批次表************************/
            endFlag = true;
          //---------
            
            

            EnumZhiFuBaoMistakeType.NOTFOUNDSTARTPARSEFILE.getValue(); //找不到开始解析格式
            if (!endFlag)
                counterOutPintMessageu.getFailDescriptionListu().add(
                    EnumZhiFuBaoMistakeType.NOTFOUNDENDPARSEFILE.getValue()); //找不到结束解析格式


            if (endFlag) //解析正确的话，正确设置返回文件批次号
                counterOutPintMessageu.setBatchId(String.valueOf(batchId)); //设置文件批次号
            else
                //解析错误的话
                counterOutPintMessageu.setBatchId(""); //设置文件批次号 
            counterOutPintMessageu.setAccountFileName(counterCompareReq
                .getCompareReNameAccountFile());//设置文件名
            counterOutPintMessageu.setBlankName(counterCompareReq.getCompareAccountType()); //设置银行类型
            counterOutPintMessageu.setImportSuccessCount(String.valueOf(successCountLine)); //设置成功导入条数
            counterOutPintMessageu.setImportFailCount(String.valueOf(failCountLine)); //设置失败导入条数

            return counterOutPintMessageu;

        } catch (Exception e) {
            log.error("add alipay item error:", e);
            return counterOutPintMessageu;
        }finally{
              if(bis!=null)
				try {
					bis.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
              if(is!=null)
				try {
					is.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
              if(rwb!=null)
            	  rwb.close();
        }

    }

}
