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
 * @version $Id: IcbcParseCompareFileTrans.java,v 0.1 2010-12-01 ����02:38:14 guoyj Exp $
 */

@Service("icbcParseCompareFileTrans")
public class IcbcParseCompareFileTrans extends AbstractParseCompareFileTrans {
   
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
        try{
        	StringBuffer sBuf = new StringBuffer();
            List<BankCompareItem> itemListu = new ArrayList<BankCompareItem>();
            String m = "";
            String[] data;

            int successCountLine = 0; //�ܹ������ĳɹ���������
            int failCountLine = 0; //������ʧ�ܵ�����
            int countLine = 0;

            File f = new File(counterCompareReq.getCompareAccountFile());
            reader = new InputStreamReader(new FileInputStream(f), "gbk");
            file   = new BufferedReader(reader);

            Long batchId = inputFileDao.getInputFileSeq(); //�õ��ļ����κ�

            while ((m = file.readLine()) != null) {
                countLine++;
                if (!m.equals("") && (countLine > 4)) //����Ҫ��ȡ�����Ҵӵ�5�п�ʼ��ȡ
                {
                    sBuf.delete(0, sBuf.length());
                    String failDescriptionStr = ""; //�������ԭ��
                    m = replaceOneSpace(m);
                    data = m.split(" "); //���ݷָ����ո�,�õ���ÿ�зָ�������
                    String payDate = "";//������������
                    String payTime = "";//��������ʱ��
                    String bankType = ""; //��������
                    String bankBillNo = ""; //���ж�����
                    String inPayAmount = ""; //������
                    String bankSerialNo = ""; //������ˮ��
                    String counterType = ""; //ҵ������
                    /********************���������ļ���Ԫ�ز�ƥ��Ļ�*******************************************/
                    try {
                    	  payDate = data[4]; //������������
                    	  payTime = data[5]; //��������ʱ��
                          bankType = counterCompareReq.getCompareAccountType(); //��������
                          bankBillNo = data[1]; //���ж�����
                          BigDecimal bg=new BigDecimal(bankBillNo);
                          inPayAmount = data[3]; //������
                          bankSerialNo = data[2]; //������ˮ��
                          counterType = data[6]; //ҵ������
                    } catch (Exception e) {
                        log.error("Get data error", e);
                        sBuf.append(EnumIcbcMistakeType.ONLINIE.getValue() + countLine
                                    + EnumIcbcMistakeType.LINE.getValue()
                                    + EnumIcbcMistakeType.PARSEMISTAKE.getValue());
                    }
                    /***********************************************************************************************/
                    if (StringUtil.isBlank(payDate)) //���֧��ʱ��Ϊ�յĻ�
                        sBuf.append(EnumIcbcMistakeType.PAYDATE.getValue()); //֧��ʱ��Ϊ��
                    else //���֧��ʱ�䲻Ϊ�յĻ�
                    {
//                      SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                    	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                        SimpleDateFormat sdff = new SimpleDateFormat("yyyyMMdd"); //��ʽת��
                        try {
                        	 payDate = sdff.format(sdf.parse(payDate)); //�������֧��ʱ��
                        }catch (Exception e) {
                            log.error("Date format error", e);
                            sBuf.append(EnumIcbcMistakeType.PAYDATEPARSEMISTAKE.getValue()); //֧��ʱ���������
                        }
                    }
                    if (StringUtil.isBlank(bankBillNo))
                        sBuf.append(EnumIcbcMistakeType.BANKBILLNO.getValue()); //���ж�����Ϊ��
                    else {
                        if (bankBillNo.length() > 32) //������ж����Ŵ���32���ַ��Ļ�
                            sBuf.append(EnumIcbcMistakeType.BANKBILLNOMISTAKE.getValue()); //���ж������ַ�����      
                    }

                    if (StringUtil.isBlank(inPayAmount))
                        sBuf.append(EnumIcbcMistakeType.INPAYAMOUNT.getValue()); //�������Ϊ��
                    else //��������Ϊ�յĻ�
                    {
                        Pattern pattern = Pattern.compile("(\\d{1,13}+.{0,1}\\d{0,2})$");
                        if (!pattern.matcher(inPayAmount).matches()) //��������������͵Ļ���
                            sBuf
                                .append(EnumIcbcMistakeType.PAYMOUNTPARSEMISTAKE.getValue()); //��������������
                        else { //���������͵Ļ�
                            if (Float.parseFloat(inPayAmount) <= 0) { //���������С�ڵ�����Ļ�
                                sBuf.append(EnumIcbcMistakeType.PAYMOUNTPARSEMISTAKE
                                    .getValue()); //��������������
                            }
                        }

                    }

                    if (bankSerialNo.equals("") || bankSerialNo == null)
                        sBuf.append(EnumIcbcMistakeType.BANKSERIALNO.getValue()); //������ˮ�Ų���Ϊ��

                    if (counterType.equals("") || counterType == null
                        || counterType.indexOf((EnumIcbcMistakeType.COUNTERTYPE.getValue())) == -1)
                        sBuf.append(EnumIcbcMistakeType.COUNTERTYPEMISTAKE.getValue()); //����״̬����
                        failDescriptionStr = sBuf.toString();
                    
                    if (failDescriptionStr.equals("")) { //�õ������еĸ�ʽ��ȷ�Ļ������������
                        successCountLine++; //�ɹ�������������

                        BankCompareItem bankCompareItemu = new BankCompareItem(); //�½�һ�����ж����ļ���ϸ����;
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

                        itemListu.add(bankCompareItemu); //����terminalCompareItemu

                        if (successCountLine % 1000 == 0) //���listu��1000�����󣬾Ϳ�ʼ������������
                        {
                            addBatchCompareItem("C", itemListu); //��Ӷ����ļ�������ϸ
                            itemListu.clear(); //������֮��listu������գ�������һ�β���
                        }
                    } else //��������
                    {
                        failCountLine++; //ʧ�ܵ�����������
                        failDescriptionStr = EnumIcbcMistakeType.PAYNO.getValue() + bankBillNo
                                             + EnumIcbcMistakeType.IMPORTFAIL.getValue()
                                             + failDescriptionStr;
                        counterOutPintMessageu.getFailDescriptionListu().add(failDescriptionStr); //��ӵ���ʧ����ϸ
                        continue;
                    }
                } else
                    continue;

            }

            addBatchCompareItem("C", itemListu); //������Ѿ������ļ�����������Ӷ����ļ�������ϸ
            /*******************��ʼ��ӵ����ļ����α�************************/
            InputFile inputFile = new InputFile();
            inputFile.setBatchId(batchId); //���õ����ļ����κ�
            inputFile.setOperateType("C"); //���ò�������
            inputFile.setBankType(counterCompareReq.getCompareAccountType()); //������������,����
            inputFile.setWaitDealCount(Long.parseLong(String.valueOf(successCountLine))); //���ô��������
            inputFile.setStatus(Long.valueOf(1)); //����״̬,1���ļ��ѵ��룬������2�������У�3���������
            inputFile.setDealOperator(counterCompareReq.getDealOperator()); //���ô������Ա
            inputFile.setFileName(counterCompareReq.getCompareReNameAccountFile()); //�����ϴ�֮����ļ���
            inputFileDao.addInputFile(inputFile); //��ӵ����ļ����α�
            /*******************������ӵ����ļ����α�************************/

            counterOutPintMessageu.setBatchId(String.valueOf(batchId)); //�����ļ����κ�      
            counterOutPintMessageu.setAccountFileName(counterCompareReq
                .getCompareReNameAccountFile());//�����ļ���
            counterOutPintMessageu.setBlankName(counterCompareReq.getCompareAccountType()); //������������
            counterOutPintMessageu.setImportSuccessCount(String.valueOf(successCountLine)); //���óɹ���������
            counterOutPintMessageu.setImportFailCount(String.valueOf(failCountLine)); //����ʧ�ܵ�������
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
	 * ����ո��滻Ϊһ���ո�
	 */
	public String replaceOneSpace(String srcStr){
		Pattern p = Pattern.compile("[ ]+");
		 //������: ƥ��һ�����߶���ո�
        Matcher match = p.matcher(srcStr);
        srcStr = match.replaceAll(" ");
        return srcStr;
	}
}
