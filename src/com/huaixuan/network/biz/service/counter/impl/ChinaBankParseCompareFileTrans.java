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
	 * �������������ļ�
	 */
	protected CounterOutPintMessage getParseFile(CounterCompareReq counterCompareReq) {
        CounterOutPintMessage counterOutPintMessageu = new CounterOutPintMessage(); //�����ļ�����Ĳ���bean
        InputStream is = null;
        BufferedInputStream bis = null;
        Workbook rwb = null;
        try {
            StringBuffer sBuf = new StringBuffer();
            List<BankCompareItem> itemListu = new ArrayList<BankCompareItem>();
            int line = 0;
            int successCountLine = 0; //�ܹ������ĳɹ���������
            int failCountLine = 0; //������ʧ�ܵ�����
            //            boolean startFlag = false; //�ı���ʼ�ַ���־
            boolean endFlag = false; //�ı������ַ���־

            //-------------����excel��ʼ-------------
            File f = new File(counterCompareReq.getCompareAccountFile());
            is = new FileInputStream(f);
            bis = new BufferedInputStream(is);
			// ���workbook ������
			rwb = Workbook.getWorkbook(bis);
			Sheet rs = rwb.getSheet(0);
			//--------------����excel����-------------
			
            Long batchId = inputFileDao.getInputFileSeq(); //�õ��ļ����κ�

            for (int i=1;i<(rs.getRows()-1);i++) {
            	line++;
                sBuf.delete(0, sBuf.length());
                String failDescriptionStr = ""; //�������ԭ��


                String payDate = "";//��������ʱ��
                String bankType = ""; //��������
                String bankBillNo = ""; //���ж�����
                String inPayAmount = ""; //������
                String bankSerialNo = ""; //������ˮ��
                String counterType = ""; //ҵ������

                /********************���������ļ���Ԫ�ز�ƥ��Ļ�*******************************************/
                try {
                //��������ʱ��
                	Cell c7 = rs.getCell(7, i);
                	payDate = c7.getContents();
                //��������
        			bankType = counterCompareReq.getCompareAccountType();
        			
        		//���ж�����
        			Cell c0 = rs.getCell(0, i);
        			bankBillNo = c0.getContents();

                  //������
                    Cell c1 = rs.getCell(1, i);
                    inPayAmount = c1.getContents();

                  //������ˮ��   ***���������ļ�û����ˮ�ţ��ö����Ŵ���
                    bankSerialNo = bankBillNo;
                    
                  //������ˮ��
                    Cell c4 = rs.getCell(4, i);
                    counterType = c4.getContents();
                    
                } catch (Exception e) {
                    log.error("Get data error", e);
                    sBuf.append(EnumZhiFuBaoMistakeType.ONLINIE.getValue() + line
                                + EnumZhiFuBaoMistakeType.LINE.getValue()
                                + EnumZhiFuBaoMistakeType.PARSEMISTAKE.getValue());
                }
                /***********************************************************************************************/

                if (payDate.equals("") || payDate == null)//�����������ʱ��Ϊ�յĻ�
                    sBuf.append(EnumZhiFuBaoMistakeType.PAYDATE.getValue()); //��������ʱ��Ϊ��
                else //�����������ʱ�䲻Ϊ�յĻ�
                {
					 SimpleDateFormat sdf = new  SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                    SimpleDateFormat sdff = new SimpleDateFormat("yyyyMMdd"); //��ʽת��
                    try {
                        payDate = sdff.format(sdf.parse(payDate)); //������Ķ�������ʱ��
                    } catch (Exception e) {
                        log.error("Date format error", e);
                        sBuf.append(EnumZhiFuBaoMistakeType.PAYDATEPARSEMISTAKE.getValue()); //��������ʱ���������
                    }
                }

                if (bankBillNo.equals("") || bankBillNo == null)
                    sBuf.append(EnumZhiFuBaoMistakeType.BANKBILLNO.getValue()); //���ж�����Ϊ��
                else {
                    if (bankBillNo.length() > 32) //������ж����Ŵ���32���ַ��Ļ�
                        sBuf.append(EnumZhiFuBaoMistakeType.BANKBILLNOMISTAKE.getValue()); //���ж������ַ�����      
                }

                if (inPayAmount.equals("") || inPayAmount == null)
                    sBuf.append(EnumZhiFuBaoMistakeType.INPAYAMOUNT.getValue()); //�������Ϊ��
                else //��������Ϊ�յĻ�
                {
                    Pattern pattern = Pattern.compile("(\\d{1,13}+.{0,1}\\d{0,2})$");
                    if (!pattern.matcher(inPayAmount).matches()) //��������������͵Ļ���
                        sBuf
                            .append(EnumZhiFuBaoMistakeType.PAYMOUNTPARSEMISTAKE.getValue()); //��������������
                    else { //���������͵Ļ�
                        if (Float.parseFloat(inPayAmount) <= 0) { //���������С�ڵ�����Ļ�
                            sBuf.append(EnumZhiFuBaoMistakeType.PAYMOUNTPARSEMISTAKE
                                .getValue()); //��������������
                        }
                    }

                }

                if (bankSerialNo.equals("") || bankSerialNo == null)
                    sBuf.append(EnumZhiFuBaoMistakeType.BANKSERIALNO.getValue()); //������ˮ�Ų���Ϊ��

                if (counterType.equals("") || counterType == null
                    || !counterType.equals(EnumZhiFuBaoMistakeType.SUCCESSORDER.getValue()))
                    sBuf.append(EnumZhiFuBaoMistakeType.COUNTERTYPEMISTAKE.getValue()); //ҵ�����ͳ���
                failDescriptionStr = sBuf.toString();

                if (failDescriptionStr.equals("")
                    && Float.parseFloat(inPayAmount.trim()) > 0) { //�õ����������������֧�������������������Ļ������Ҷ�Ӧ�����еĸ�ʽ��ȷ�Ļ������������
                    successCountLine++; //�ɹ�������������
                    BankCompareItem bankCompareItemu = new BankCompareItem(); //�½�һ�����ж����ļ���ϸ����
                    bankCompareItemu.setBatchId(batchId); //�����ļ����κ�

                    /****��ʼ��������ʱ�䣬��߶�������ʱ��Ҫע�⣬����ʽ��yyyyMMdd,����Ҫ��Ӧת��******/
                    bankCompareItemu.setPayDate(payDate); //���ö�������ʱ��
                    /****������������ʱ�䣬��߶�������ʱ��Ҫע�⣬����ʽ��yyyyMMdd,����Ҫ��Ӧת��******/
                    bankCompareItemu.setBankType(bankType);//������������
                    bankCompareItemu.setBankBillNo(bankBillNo); //�������ж�����

                    /*************************���������ÿ�ʼ****************************/

                    Money amountMoney = new Money(inPayAmount);
                    Long amount = amountMoney.getCent();
                    bankCompareItemu.setPayAmount(amount); //������������0��������������

                    /*************************���������ý���****************************/

                    bankCompareItemu.setBankSerialNo(bankSerialNo); //����ҵ����ˮ��

                    itemListu.add(bankCompareItemu); //����bankCompareItemu

                    if (successCountLine % 1000 == 0) //���listu��1000�����󣬾Ϳ�ʼ������������
                    {
                        addBatchCompareItem("C", itemListu); //��Ӷ����ļ�������ϸ
                        itemListu.clear(); //������֮��listu������գ�������һ�β���
                    }

				} else{ // ��������
					failCountLine++; // ʧ�ܵ�����������
					failDescriptionStr = EnumZhiFuBaoMistakeType.PAYNO
							.getValue()
							+ bankBillNo
							+ EnumZhiFuBaoMistakeType.IMPORTFAIL.getValue()
							+ failDescriptionStr;
					counterOutPintMessageu.getFailDescriptionListu().add(
							failDescriptionStr); // ��ӵ���ʧ����ϸ
					continue;
				}
			}
   
            
            
            
          //----------��ȡEXCEL���
            addBatchCompareItem("C", itemListu); //������Ѿ������ļ�������־������Ӷ����ļ�������ϸ
            
            /**start  ��ѯ������ϸ���Ƿ����ظ����ж�����,���������ɾ���ظ���¼����ǰ̨��ʾ   2010.9.3 cw***/
            InputFile input = new InputFile();
            input.setBatchId(batchId); //���õ����ļ����κ�
            input.setBankType(counterCompareReq.getCompareAccountType()); //������������
            List<BankCompareItem> bankCompareItemList = inputFileDao.getBankCompareItemBankBillNO(input);
            if(bankCompareItemList !=null&&bankCompareItemList.size()>0){
            	inputFileDao.deleteBankCompareItemBankBillNO(input);
            	String failDescriptionStr = "";
            	BankCompareItem bci = null;
            	for(int a=0;a<bankCompareItemList.size();a++){
            		bci = bankCompareItemList.get(a);
            		failCountLine+=bci.getRecount(); // ʧ�ܵ�����������
            		successCountLine -=bci.getRecount(); // �ɹ�������������
					failDescriptionStr = EnumZhiFuBaoMistakeType.PAYNO
							.getValue()
							+ bci.getBankBillNo()
							+ EnumZhiFuBaoMistakeType.IMPORTFAIL.getValue()
							+ EnumZhiFuBaoMistakeType.RE_BANKBILLNO.getValue();
					counterOutPintMessageu.getFailDescriptionListu().add(
							failDescriptionStr); // ��ӵ���ʧ����ϸ
            	}
            }
            /**end  ��ѯ������ϸ���Ƿ����ظ����ж�����,���������ɾ���ظ���¼����ǰ̨��ʾ   2010.9.3 cw***/
            
            /*******************��ʼ��ӵ����ļ����α�************************/
            InputFile inputFile = new InputFile();
            inputFile.setBatchId(batchId); //���õ����ļ����κ�
            inputFile.setOperateType("C"); //���ò�������
            inputFile.setBankType(counterCompareReq.getCompareAccountType()); //������������
            inputFile.setWaitDealCount(Long.parseLong(String.valueOf(successCountLine))); //���ô��������
            inputFile.setStatus(Long.valueOf(1)); //����״̬,1���ļ��ѵ��룬������2�������У�3���������
            inputFile.setDealOperator(counterCompareReq.getDealOperator()); //���ô������Ա
            inputFile.setFileName(counterCompareReq.getCompareReNameAccountFile()); //�����ϴ�֮����ļ���
            inputFileDao.addInputFile(inputFile); //��ӵ����ļ����α�
            /*******************������ӵ����ļ����α�************************/
            endFlag = true;
          //---------
            
            

            EnumZhiFuBaoMistakeType.NOTFOUNDSTARTPARSEFILE.getValue(); //�Ҳ�����ʼ������ʽ
            if (!endFlag)
                counterOutPintMessageu.getFailDescriptionListu().add(
                    EnumZhiFuBaoMistakeType.NOTFOUNDENDPARSEFILE.getValue()); //�Ҳ�������������ʽ


            if (endFlag) //������ȷ�Ļ�����ȷ���÷����ļ����κ�
                counterOutPintMessageu.setBatchId(String.valueOf(batchId)); //�����ļ����κ�
            else
                //��������Ļ�
                counterOutPintMessageu.setBatchId(""); //�����ļ����κ� 
            counterOutPintMessageu.setAccountFileName(counterCompareReq
                .getCompareReNameAccountFile());//�����ļ���
            counterOutPintMessageu.setBlankName(counterCompareReq.getCompareAccountType()); //������������
            counterOutPintMessageu.setImportSuccessCount(String.valueOf(successCountLine)); //���óɹ���������
            counterOutPintMessageu.setImportFailCount(String.valueOf(failCountLine)); //����ʧ�ܵ�������

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
