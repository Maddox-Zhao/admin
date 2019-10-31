/**
 * created since 2009-9-24
 */
package com.huaixuan.network.biz.service.counter;




/**
 * @author zhangxiang
 * @version $Id: CompareFactory.java,v 0.1 2009-9-24 下午02:47:14 zhangxiang Exp $
 */
public interface ParseCompareFileFactory {
    /**
     * 创建解析对应对账文件接口
     * @param type
     * @return
     */
    public ParseCompareFileTrans createParseCompareFileTrans(String type);

}
