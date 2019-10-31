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
 * @version $Id: CounterManager.java,v 0.1 2010-6-7 上午09:33:24 guoyj Exp $
 */
public interface CounterManager {

    /**
     *解析对账文件，根据对账银行类型
     * @param counterCompareReq
     * @return
     */
    public CounterOutPintMessage parseCompareFileExcute(CounterCompareReq counterCompareReq);

    /**
     * 上传对账文件
     * @param compareAccountFile
     * @param compareAccountFileFileName
     * @return  string[0]返回上传之后的重新命名的名字，string[1]返回上传的路径
     */
    public String[] uploadCompareFile(File compareAccountFile, String compareAccountFileFileName);

    public String[] uploadCompareFileNew(MultipartFile compareAccountFile,
                                         String compareAccountFileFileName);

    /**
     * 根据不同银行生成规则生成,目前仅为年月日+支付目的+8位sequence
     * @param payDest
     * @return
     */
    public String getStringSeq(String payDest);

    /**
     * 判断某账户是否是公司账户,一般公司账户都有2个账户的，一个是收款账户，一个是付款账户 
     * @param accountNo
     * @return
     */
    public boolean isCompayAccount(String accountNo);

}
