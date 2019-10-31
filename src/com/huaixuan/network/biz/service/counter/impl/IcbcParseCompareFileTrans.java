package com.huaixuan.network.biz.service.counter.impl;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.stereotype.Service;

import com.huaixuan.network.biz.domain.counter.BankCompareItem;
import com.huaixuan.network.biz.domain.counter.CounterCompareReq;
import com.huaixuan.network.biz.domain.counter.CounterOutPintMessage;
import com.huaixuan.network.biz.domain.counter.InputFile;
import com.huaixuan.network.biz.enums.EnumIcbcMistakeType;
import com.hundsun.common.lang.StringUtil;
import com.hundsun.itrans.common.util.Money;

/**
 * @author guoyj
 * @version $Id: IcbcParseCompareFileTrans.java,v 0.1 2010-12-01 下午02:38:14 guoyj Exp $
 */

@Service("icbcParseCompareFileTrans")
public class IcbcParseCompareFileTrans extends AbstractParseCompareFileTrans {
   
	/**
	 * 解析工行文件
	 * @param req
	 * @return
	 * @see com.hundsun.bible.counter.trans.impl.AbstractParseCompareFileTrans#getParseFile(com.hundsun.bible.domain.model.counter.CounterCompareReq)
	 */
	@Override
	protected CounterOutPintMessage getParseFile(CounterCompareReq counterCompareReq) {
		CounterOutPintMessage counterOutPintMessageu = new CounterOutPintMessage(); //对账文件输出的参数bean
        InputStreamReader reader = null;
        BufferedReader file = null;
        try{
        	StringBuffer sBuf = new StringBuffer();
            List<BankCompareItem> itemListu = new ArrayList<BankCompareItem>();
            String m = "";
            String[] data;

            int successCountLine = 0; //总共解析的成功完整行数
            int failCountLine = 0; //解析的失败的行数
            int countLine = 0;

            File f = new File(counterCompareReq.getCompareAccountFile());
            reader = new InputStreamReader(new FileInputStream(f), "gbk");
            file   = new BufferedReader(reader);

            Long batchId = inputFileDao.getInputFileSeq(); //得到文件批次号

            while ((m = file.readLine()) != null) {
                countLine++;
                if (!m.equals("") && (countLine > 4)) //不需要读取空行且从第5行开始读取
                {
                    sBuf.delete(0, sBuf.length());
                    String failDescriptionStr = ""; //导入错误原因
                    m = replaceOneSpace(m);
                    data = m.split(" "); //根据分隔符空格,得到的每行分割数组项
                    String payDate = "";//订单生成日期
                    String payTime = "";//订单生成时间
                    String bankType = ""; //银行类型
                    String bankBillNo = ""; //银行订单号
                    String inPayAmount = ""; //收入金额
                    String bankSerialNo = ""; //银行流水号
                    String counterType = ""; //业务类型
                    /********************如果导入的文件的元素不匹配的话*******************************************/
                    try {
                    	  payDate = data[4]; //订单生成日期
                    	  payTime = data[5]; //订单生成时间
                          bankType = counterCompareReq.getCompareAccountType(); //银行类型
                          bankBillNo = data[1]; //银行订单号
                          BigDecimal bg=new BigDecimal(bankBillNo);
                          inPayAmount = data[3]; //收入金额
                          bankSerialNo = data[2]; //银行流水号
                          counterType = data[6]; //业务类型
                    } catch (Exception e) {
                        log.error("Get data error", e);
                        sBuf.append(EnumIcbcMistakeType.ONLINIE.getValue() + countLine
                                    + EnumIcbcMistakeType.LINE.getValue()
                                    + EnumIcbcMistakeType.PARSEMISTAKE.getValue());
                    }
                    /***********************************************************************************************/
                    if (StringUtil.isBlank(payDate)) //如果支付时间为空的话
                        sBuf.append(EnumIcbcMistakeType.PAYDATE.getValue()); //支付时间为空
                    else //如果支付时间不为空的话
                    {
//                      SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                    	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                        SimpleDateFormat sdff = new SimpleDateFormat("yyyyMMdd"); //格式转换
                        try {
                        	 payDate = sdff.format(sdf.parse(payDate)); //解析后的支付时间
                        }catch (Exception e) {
                            log.error("Date format error", e);
                            sBuf.append(EnumIcbcMistakeType.PAYDATEPARSEMISTAKE.getValue()); //支付时间解析错误
                        }
                    }
                    if (StringUtil.isBlank(bankBillNo))
                        sBuf.append(EnumIcbcMistakeType.BANKBILLNO.getValue()); //银行订单号为空
                    else {
                        if (bankBillNo.length() > 32) //如果银行订单号大于32个字符的话
                            sBuf.append(EnumIcbcMistakeType.BANKBILLNOMISTAKE.getValue()); //银行订单号字符过长      
                    }

                    if (StringUtil.isBlank(inPayAmount))
                        sBuf.append(EnumIcbcMistakeType.INPAYAMOUNT.getValue()); //收入金额不能为空
                    else //如果收入金额不为空的话
                    {
                        Pattern pattern = Pattern.compile("(\\d{1,13}+.{0,1}\\d{0,2})$");
                        if (!pattern.matcher(inPayAmount).matches()) //如果不是数字类型的话，
                            sBuf
                                .append(EnumIcbcMistakeType.PAYMOUNTPARSEMISTAKE.getValue()); //发生金额解析错误
                        else { //是数字类型的话
                            if (Float.parseFloat(inPayAmount) <= 0) { //如果收入金额小于等于零的话
                                sBuf.append(EnumIcbcMistakeType.PAYMOUNTPARSEMISTAKE
                                    .getValue()); //发生金额解析错误
                            }
                        }

                    }

                    if (bankSerialNo.equals("") || bankSerialNo == null)
                        sBuf.append(EnumIcbcMistakeType.BANKSERIALNO.getValue()); //银行流水号不能为空

                    if (counterType.equals("") || counterType == null
                        || counterType.indexOf((EnumIcbcMistakeType.COUNTERTYPE.getValue())) == -1)
                        sBuf.append(EnumIcbcMistakeType.COUNTERTYPEMISTAKE.getValue()); //交易状态出错
                        failDescriptionStr = sBuf.toString();
                    
                    if (failDescriptionStr.equals("")) { //得到的这行的格式正确的话，才允许添加
                        successCountLine++; //成功导入条数增加

                        BankCompareItem bankCompareItemu = new BankCompareItem(); //新建一个银行对账文件明细对象;
                        bankCompareItemu.setBatchId(batchId); //设置文件批次号

                        /****开始订单生产时间，这边订单生成时间要注意，其形式是yyyyMMdd,所以要相应转换******/
                        bankCompareItemu.setPayDate(payDate); //设置订单生产时间
                        /****结束订单生产时间，这边订单生成时间要注意，其形式是yyyyMMdd,所以要相应转换******/
                        bankCompareItemu.setBankType(bankType);//设置银行类型
                        bankCompareItemu.setBankBillNo(bankBillNo); //设置银行订单号
                        

                        /*************************发生额设置开始****************************/

                        Money amountMoney = new Money(inPayAmount);
                        Long amount = amountMoney.getCent();
                        bankCompareItemu.setPayAmount(amount); //如果收入金额不等于0，就设置收入金额

                        /*************************发生额设置结束****************************/
                        
                        bankCompareItemu.setBankSerialNo(bankSerialNo); //设置业务流水号

                        itemListu.add(bankCompareItemu); //增加terminalCompareItemu

                        if (successCountLine % 1000 == 0) //如果listu有1000个对象，就开始进行批量插入
                        {
                            addBatchCompareItem("C", itemListu); //添加对帐文件导入明细
                            itemListu.clear(); //插入完之后，listu进行清空，进行下一次插入
                        }
                    } else //否则不允许
                    {
                        failCountLine++; //失败导入条数增加
                        failDescriptionStr = EnumIcbcMistakeType.PAYNO.getValue() + bankBillNo
                                             + EnumIcbcMistakeType.IMPORTFAIL.getValue()
                                             + failDescriptionStr;
                        counterOutPintMessageu.getFailDescriptionListu().add(failDescriptionStr); //添加导入失败明细
                        continue;
                    }
                } else
                    continue;

            }

            addBatchCompareItem("C", itemListu); //如果是已经到了文件结束，就添加对帐文件导入明细
            /*******************开始添加导入文件批次表************************/
            InputFile inputFile = new InputFile();
            inputFile.setBatchId(batchId); //设置导入文件批次号
            inputFile.setOperateType("C"); //设置操作类型
            inputFile.setBankType(counterCompareReq.getCompareAccountType()); //设置银行类型,网点
            inputFile.setWaitDealCount(Long.parseLong(String.valueOf(successCountLine))); //设置待处理笔数
            inputFile.setStatus(Long.valueOf(1)); //设置状态,1：文件已导入，待处理，2：处理中，3：处理结束
            inputFile.setDealOperator(counterCompareReq.getDealOperator()); //设置处理操作员
            inputFile.setFileName(counterCompareReq.getCompareReNameAccountFile()); //设置上传之后的文件名
            inputFileDao.addInputFile(inputFile); //添加导入文件批次表
            /*******************结束添加导入文件批次表************************/

            counterOutPintMessageu.setBatchId(String.valueOf(batchId)); //设置文件批次号      
            counterOutPintMessageu.setAccountFileName(counterCompareReq
                .getCompareReNameAccountFile());//设置文件名
            counterOutPintMessageu.setBlankName(counterCompareReq.getCompareAccountType()); //设置银行类型
            counterOutPintMessageu.setImportSuccessCount(String.valueOf(successCountLine)); //设置成功导入条数
            counterOutPintMessageu.setImportFailCount(String.valueOf(failCountLine)); //设置失败导入条数
            return counterOutPintMessageu;
        }catch(Exception e){
        	log.error("add icbc item error:", e);
            return counterOutPintMessageu;
        }finally{
        	if(reader!=null)
				try {
					reader.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
        	if(file!=null)
				try {
					file.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
        }
	
	}
	/**
	 * 多个空格替换为一个空格
	 */
	public String replaceOneSpace(String srcStr){
		Pattern p = Pattern.compile("[ ]+");
		 //正则表达: 匹配一个或者多个空格
        Matcher match = p.matcher(srcStr);
        srcStr = match.replaceAll(" ");
        return srcStr;
	}
}
