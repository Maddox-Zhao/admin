/**
 * created since 2010-6-7
 */
package com.huaixuan.network.biz.service.counter.impl;

import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.stereotype.Service;

import com.huaixuan.network.biz.service.counter.ParseCompareFileFactory;
import com.huaixuan.network.biz.service.counter.ParseCompareFileTrans;





/**
 * @author guoyj
 * @version $Id: ParseCompareFileFactoryImpl.java,v 0.1 2010-6-7
 */
@Service("parseCompareFileFactory")
public class ParseCompareFileFactoryImpl implements ParseCompareFileFactory, BeanFactoryAware {

    protected final Log         log = LogFactory.getLog(ParseCompareFileFactoryImpl.class);

    private BeanFactory         beanFactory;

    private Map<String, String> transMap;

    /**
     * 根据银行类型返回对应的接口
     * @param enumInstitution
     * @return
     * @see com.hundsun.bible.counter.factory.ParseCompareFileFactory#createParseCompareFileTrans(com.hundsun.bible.domain.Enum.EnumInstitution)
     */
    public ParseCompareFileTrans createParseCompareFileTrans(String type) {
        if (type == null) {
            return null;
        }
        if (transMap == null) {
            log.error("transMap为空,初始化失败");
            return null;
        }
        String transName = transMap.get(type);
        return (ParseCompareFileTrans) beanFactory.getBean(transName);
    }

    public void setTransMap(Map<String, String> transMap) {
        this.transMap = transMap;
    }

    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        this.beanFactory = beanFactory;
    }
}
