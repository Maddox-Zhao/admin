/**
 * created since 2009-9-24
 */
package com.huaixuan.network.biz.service.counter;

import com.huaixuan.network.biz.domain.counter.CounterCompareReq;
import com.huaixuan.network.biz.domain.counter.CounterOutPintMessage;


/**
 * @author zhangxiang
 * @version $Id: ParseCompareFileTrans.java,v 0.1 2009-9-24 下午02:55:10 zhangxiang Exp $
 */
public interface ParseCompareFileTrans {

    /**
     * ���������ļ����������ж�Ҫʵ�ִ˷���
     * @param req
     * @return
     */
    public CounterOutPintMessage doParseFile(CounterCompareReq req);
}
