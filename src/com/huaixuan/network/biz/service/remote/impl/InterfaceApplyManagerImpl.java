package com.huaixuan.network.biz.service.remote.impl;

import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.huaixuan.network.biz.dao.remote.InterfaceApplyDao;
import com.huaixuan.network.biz.domain.remote.InterfaceApply;
import com.huaixuan.network.biz.enums.EnumInterfaceApplyStatus;
import com.huaixuan.network.biz.enums.EnumInterfaceType;
import com.huaixuan.network.biz.query.InterfaceApplyQuery;
import com.huaixuan.network.biz.query.QueryPage;
import com.huaixuan.network.biz.service.remote.InterfaceApplyManager;

/**  
 * ï¿½ï¿½ï¿½ï¿½ï¿½Ô¶ï¿½ï¿½ï¿½ï¿??(bibleUtil auto code generation) 
 * @version 3.2.0  
 */
@Service("interfaceApplyManager")
public class InterfaceApplyManagerImpl implements InterfaceApplyManager {
    protected final Log log = LogFactory.getLog(this.getClass());
    /* @model: */
    @Autowired
    InterfaceApplyDao   interfaceApplyDao;

    /* @model: */
    public void addInterfaceApply(InterfaceApply interfaceApplyDao) {
        log.info("InterfaceApplyManagerImpl.addInterfaceApply method");
        try {
            InterfaceApply interfaceApply = this.getInterfaceApplyByUserId(
                interfaceApplyDao.getUserId(), EnumInterfaceType.PAIPAI.getKey());
            if (interfaceApply != null) {
                interfaceApplyDao.setStatus(EnumInterfaceApplyStatus.PASS.getKey());
                this.interfaceApplyDao.editInterfaceApply(interfaceApplyDao);
            } else {
                this.interfaceApplyDao.addInterfaceApply(interfaceApplyDao);
            }
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }

    /* @model: */
    public void editInterfaceApply(InterfaceApply interfaceApply) {
        log.info("InterfaceApplyManagerImpl.editInterfaceApply method");
        try {
            this.interfaceApplyDao.editInterfaceApply(interfaceApply);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }

    public void editGmtSync(InterfaceApply interfaceApply) {
        log.info("InterfaceApplyManagerImpl.editGmtSync method");
        try {
            this.interfaceApplyDao.editGmtSync(interfaceApply);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }

    /* @model: */
    public void removeInterfaceApply(Long interfaceApplyId) {
        log.info("InterfaceApplyManagerImpl.removeInterfaceApply method");
        try {
            this.interfaceApplyDao.removeInterfaceApply(interfaceApplyId);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }

    /* @model: */
    public InterfaceApply getInterfaceApply(Long interfaceApplyId) {
        log.info("InterfaceApplyManagerImpl.getInterfaceApply method");
        try {
            return this.interfaceApplyDao.getInterfaceApply(interfaceApplyId);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return null;
    }

    public InterfaceApply getInterfaceApplyByUserId(Long userId, String type) {
        log.info("InterfaceApplyManagerImpl.getInterfaceApplyByUserId method");
        try {
            return this.interfaceApplyDao.getInterfaceApplyByUserId(userId, type);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return null;
    }

    /* @model: */
    public List<InterfaceApply> getInterfaceApplys() {
        log.info("InterfaceApplyManagerImpl.getInterfaceApplys method");
        try {
            return this.interfaceApplyDao.getInterfaceApplys();
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return null;
    }

    public int getCountByMap(Map parMap) {
        log.info("InterfaceApplyManagerImpl.getCountByMap method");
        try {
            return interfaceApplyDao.getCountByMap(parMap);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return 0;
    }

    public List<InterfaceApply> getListByMap(Map parMap) {
        log.info("InterfaceApplyManagerImpl.getListByMap method");
        try {
            return this.interfaceApplyDao.getListByMap(parMap);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return null;
    }

    public void editInterfaceApplyStatus(Long id, String status) {
        log.info("InterfaceApplyManagerImpl.editInterfaceApply method");
        try {
            this.interfaceApplyDao.editInterfaceApplyStatus(id, status);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }

    @SuppressWarnings("unchecked")
	@Override
    public QueryPage getListByMap(InterfaceApplyQuery query, int currPage, int pageSize) {

        QueryPage queryPage = new QueryPage(query);
        Map pramas = queryPage.getParameters();
        try {
            int count = interfaceApplyDao.getCountByMap(pramas);

            if (count > 0) {

                /* µ±Ç°Ò³ */
                queryPage.setCurrentPage(currPage);
                /* Ã¿Ò³×ÜÊý */
                queryPage.setPageSize(pageSize);
                queryPage.setTotalItem(count);

                pramas.put("startRow", queryPage.getPageFristItem());
                pramas.put("endRow", queryPage.getPageLastItem());

                List<InterfaceApply> interfaceApplyList = interfaceApplyDao.getListByMap(pramas);
                if (interfaceApplyList != null && interfaceApplyList.size() > 0) {
                    queryPage.setItems(interfaceApplyList);
                }
            }
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return queryPage;
    }

}
