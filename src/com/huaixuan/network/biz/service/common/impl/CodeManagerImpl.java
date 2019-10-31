/**
 * @Title: CodeManagerImpl.java
 * @Package com.huaixuan.network.biz.service.common.impl
 * @Description: TODO
 * @author liuwd weida-liu@foxmail.com
 * @date 2011-3-1 ÏÂÎç02:43:32
 * @version V1.0
 */
package com.huaixuan.network.biz.service.common.impl;

import java.text.DecimalFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.huaixuan.network.biz.dao.common.CodeDao;
import com.huaixuan.network.biz.domain.Code;
import com.huaixuan.network.biz.service.common.CodeManager;
import com.huaixuan.network.common.util.DateUtil;

/**
 * @ClassName: CodeManagerImpl
 * @Description: TODO
 * @author liuwd weida-liu@foxmail.com
 * @date 2011-3-1 ÏÂÎç02:43:32
 *
 */
@Service("codeManager")
public class CodeManagerImpl implements CodeManager {

	@Autowired
    private CodeDao codeDao;

    /**
     * @param type
     * @return
     * @see com.hundsun.bible.facade.user.CodeManager#buildCode(int)
     */
    public String buildCode(int type) {
        return buildCode(type, -1);
    }

    /**
     * @param type
     * @param length
     * @return
     * @see com.hundsun.bible.facade.user.CodeManager#buildCode(int, int)
     */
    public String buildCode(int type, int length) {
        return buildCode(type, length, DateUtil.convertDateToString("yyyyMMdd", new Date()));
    }

    /**
     * @param type
     * @param length
     * @param preStr
     * @return
     * @see com.hundsun.bible.facade.user.CodeManager#buildCode(int, int, java.lang.String)
     */
    public String buildCode(int type, int length, String preStr) {

        try {
            Code code = codeDao.getCodeByType(type);
            if (code != null) {
                StringBuffer temp = new StringBuffer(0);
                if (length <= 0) {
                    length = code.getCodeLength();
                }
                for (int i = 0; i < length; i++) {
                    temp.append("0");
                }
                DecimalFormat format = new DecimalFormat(temp.toString());
                long nextVal = 0;
                String t1 = DateUtil.convertDateToString("yyyyMMdd", new Date());
                String t2 = DateUtil.convertDateToString("yyyyMMdd", code.getCodeDate());
                if (t1.equals(t2)) {
                    nextVal = code.getCurValue() + 1;
                    code.setCurValue(nextVal);
                } else {
                    nextVal = 1;
                    code.setCodeDate(new Date());
                    code.setCurValue(nextVal);
                }
                codeDao.editCode(code);
                return preStr + format.format(nextVal);
            } else {
                code = new Code();
                code.setCodeType(type);
                if (length <= 0) {
                    code.setCodeLength(6);
                } else {
                    code.setCodeLength(length);
                }
                code.setCurValue(1);
                code.setCodeDate(new Date());
                codeDao.addCode(code);

                StringBuffer temp = new StringBuffer(0);

                for (int i = 0; i < code.getCodeLength(); i++) {
                    temp.append("0");
                }
                DecimalFormat format = new DecimalFormat(temp.toString());
                return preStr + format.format(code.getCurValue());
            }
        } catch (Exception ex) {
//            log.error(ex.getMessage());
        }
        return null;
    }

    public String buildCodeByDateAndType(int type, int length, String typeStr) {
        String codeStr = buildCode(type, length, DateUtil.convertDateToString("yyyyMMdd",
            new Date()));
        return typeStr + codeStr;
    }

    public void setCodeDao(CodeDao codeDao) {
        this.codeDao = codeDao;
    }

    public CodeDao getCodeDao() {
        return codeDao;
    }

}