/**
 * created since 2010-6-7
 */
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
import java.util.regex.Pattern;

import org.springframework.stereotype.Service;

import com.huaixuan.network.biz.domain.counter.BankCompareItem;
import com.huaixuan.network.biz.domain.counter.CounterCompareReq;
import com.huaixuan.network.biz.domain.counter.CounterOutPintMessage;
import com.huaixuan.network.biz.domain.counter.InputFile;
import com.huaixuan.network.biz.enums.EnumZhiFuBaoMistakeType;
import com.hundsun.itrans.common.util.Money;

/**
 * @author guoyj
 * @version $Id: AliPayParseCompareFile.java,v 0.1 2010-6-7 下午02:43:14 guoyj Exp $
 */
@Service("aliPayParseCompareFileTrans")
public class AliPayParseCompareFileTrans extends AbstractParseCompareFileTrans {

    /**
     * ���������ļ�
     * @param req
     * @return
     * @see com.hundsun.bible.counter.trans.impl.AbstractParseCompareFileTrans#getParseFile(com.hundsun.bible.domain.model.counter.CounterCompareReq)
     */
    @Override
    protected CounterOutPintMessage getParseFile(CounterCompareReq counterCompareReq) {
        CounterOutPintMessage counterOutPintMessageu = new CounterOutPintMessage(); //�����ļ�����Ĳ���bean
        InputStreamReader reader = null;
        BufferedReader file = null;
        try {
            String endParseLineStr = EnumZhiFuBaoMistakeType.ENDPARSELINE.getValue(); //����ͰͶ����ļ��������˽�����ʽ

            StringBuffer sBuf = new StringBuffer();
            List<BankCompareItem> itemListu = new ArrayList<BankCompareItem>();
            String m = "";
            String[] data;
            int line = 0;
            int successCountLine = 0; //�ܹ������ĳɹ���������
            int failCountLine = 0; //������ʧ�ܵ�����
            //            boolean startFlag = false; //�ı���ʼ�ַ���־
            boolean endFlag = false; //�ı������ַ���־

            File f = new File(counterCompareReq.getCompareAccountFile());

            reader = new InputStreamReader(new FileInputStream(f), "gbk");
            file = new BufferedReader(reader);

            Long batchId = inputFileDao.getInputFileSeq(); //�õ��ļ����κ�

            while ((m = file.readLine()) != null) {
                line++;
                if (!m.equals(endParseLineStr)) //�ı������ı�־   
                {
                    if (!m.equals("") && (line > 5)) //����Ҫ��ȡ����,���������ı���ʼ��־��һ����ʼ��ȡ  
                    {
                        sBuf.delete(0, sBuf.length());
                        String failDescriptionStr = ""; //�������ԭ��

                        data = m.split("\\,"); //�õ���ÿ�зָ�������

                        String payDate = "";//��������ʱ��
                        String bankType = ""; //��������
                        String bankBillNo = ""; //���ж�����
                        String inPayAmount = ""; //������
                        String bankSerialNo = ""; //������ˮ��
                        String counterType = ""; //ҵ������

                        /********************���������ļ���Ԫ�ز�ƥ��Ļ�*******************************************/
                        try {
                            payDate = data[3];//��������ʱ��
                            bankType = counterCompareReq.getCompareAccountType(); //��������
                            bankBillNo = data[2]; //���ж�����
                            BigDecimal bg=new BigDecimal(bankBillNo);
                            inPayAmount = data[5]; //������
                            bankSerialNo = data[1]; //������ˮ��
                            counterType = data[9]; //ҵ������
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
							// SimpleDateFormat sdf = new
							// SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                            SimpleDateFormat sdf = new SimpleDateFormat("yyyy��MM��dd�� hh:mm:ss");
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
                            || !counterType.equals(EnumZhiFuBaoMistakeType.COUNTERTYPE.getValue()))
                            sBuf.append(EnumZhiFuBaoMistakeType.COUNTERTYPEMISTAKE.getValue()); //ҵ�����ͳ���
                        failDescriptionStr = sBuf.toString();

                        if (failDescriptionStr.equals("")
                            && counterType.equals(EnumZhiFuBaoMistakeType.COUNTERTYPE.getValue())
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

                        } else //��������
                        {
                            failCountLine++; //ʧ�ܵ�����������
                            failDescriptionStr = EnumZhiFuBaoMistakeType.PAYNO.getValue()
                                                 + bankBillNo
                                                 + EnumZhiFuBaoMistakeType.IMPORTFAIL.getValue()
                                                 + failDescriptionStr;
                            counterOutPintMessageu.getFailDescriptionListu()
                                .add(failDescriptionStr); //��ӵ���ʧ����ϸ
                            continue;
                        }
                    }

                } else {
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
                    
                    
                  
                    
                    break;
                }

                m = "";
            }

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

}
