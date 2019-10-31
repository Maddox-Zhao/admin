/**
 * created since 2009-8-3
 */
package com.huaixuan.network.biz.service.counter;

import java.io.File;

import org.springframework.web.multipart.MultipartFile;

import com.huaixuan.network.biz.domain.counter.CounterCompareReq;
import com.huaixuan.network.biz.domain.counter.CounterOutPintMessage;

/**
 * @author guoyj
 * @version $Id: CounterManager.java,v 0.1 2010-6-7 ����09:33:24 guoyj Exp $
 */
public interface CounterManager {

    /**
     *���������ļ������ݶ�����������
     * @param counterCompareReq
     * @return
     */
    public CounterOutPintMessage parseCompareFileExcute(CounterCompareReq counterCompareReq);

    /**
     * �ϴ������ļ�
     * @param compareAccountFile
     * @param compareAccountFileFileName
     * @return  string[0]�����ϴ�֮����������������֣�string[1]�����ϴ���·��
     */
    public String[] uploadCompareFile(File compareAccountFile, String compareAccountFileFileName);

    public String[] uploadCompareFileNew(MultipartFile compareAccountFile,
                                         String compareAccountFileFileName);

    /**
     * ���ݲ�ͬ�������ɹ�������,Ŀǰ��Ϊ������+֧��Ŀ��+8λsequence
     * @param payDest
     * @return
     */
    public String getStringSeq(String payDest);

    /**
     * �ж�ĳ�˻��Ƿ��ǹ�˾�˻�,һ�㹫˾�˻�����2���˻��ģ�һ�����տ��˻���һ���Ǹ����˻� 
     * @param accountNo
     * @return
     */
    public boolean isCompayAccount(String accountNo);

}
