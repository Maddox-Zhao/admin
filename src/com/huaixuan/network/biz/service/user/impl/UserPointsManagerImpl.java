package com.huaixuan.network.biz.service.user.impl;

import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.huaixuan.network.biz.dao.user.UserDao;
import com.huaixuan.network.biz.dao.user.UserPointsDao;
import com.huaixuan.network.biz.domain.user.UserPoints;
import com.huaixuan.network.biz.service.user.UserPointsManager;

@Service("userPointsManager")
public class UserPointsManagerImpl implements UserPointsManager {


	@Autowired
	private UserPointsDao userPointsDao;

	protected Log  logger = LogFactory.getLog(this.getClass());

    /* @model: */
    public void addUserPoints(UserPoints userPointsDao) {
        logger.info("UserPointsManagerImpl.addUserPoints method");
        try {
            this.userPointsDao.addUserPoints(userPointsDao);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
    }

    public Integer getCountByUserNick(String userNice) {
        logger.info("UserPointsManagerImpl.getCountByUserNick method");
        try {
            return this.userPointsDao.getCountByUserNick(userNice);
        } catch (Exception e) {
            logger.error(e.getMessage());
            return null;
        }
    }

    /* @model: */
    public void editUserPoints(UserPoints userPoints) {
        logger.info("UserPointsManagerImpl.editUserPoints method");
        try {
            this.userPointsDao.editUserPoints(userPoints);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
    }

    /* @model: */
    public void removeUserPoints(Long userPointsId) {
        logger.info("UserPointsManagerImpl.removeUserPoints method");
        try {
            this.userPointsDao.removeUserPoints(userPointsId);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
    }

    /* @model: */
    public UserPoints getUserPoints(Long userPointsId) {
        logger.info("UserPointsManagerImpl.getUserPoints method");
        try {
            return this.userPointsDao.getUserPoints(userPointsId);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return null;
    }

    public List<UserPoints> getUserPointssExportList(UserPoints userPoints) {
        logger.info("UserPointsManagerImpl.getUserPointss method");
        try {
            // 		   int currentPage, int pageSize, int totalCount
            return this.userPointsDao.getUserPointssExportList(userPoints);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return null;
    }

    /**
     * 查询所有会员积分，以修改时间为序
     *
     * @param page
     * @return
     * @see com.hundsun.bible.facade.user.UserPointsManager#getUserPointss(com.hundsun.bible.Page)
     */
//    public List<UserPoints> getUserPoints(Map parm, Page page) {
//        logger.info("UserPointsManagerImpl.getUserPointss method");
//        try {
//            // 		   int currentPage, int pageSize, int totalCount
//            return this.userPointsDao.getUserPoints(parm, page);
//        } catch (Exception e) {
//            logger.error(e.getMessage());
//        }
//        return null;
//    }

    /**
     * 获取记录总数
     *
     * @return
     * @throws Exception
     */
    public Integer getUserPointsCount(Map parm) {

        logger.info("UserPointsManagerImpl.getUserPointsCount method");
        try {
            return this.userPointsDao.getUserPointsCount(parm);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return null;

    }

    public UserPoints getUserPointsByUserNick(String userNick) {
        logger.info("UserPointsManagerImpl.getUserPointsByUserNick method");
        try {
            return this.userPointsDao.getUserPointsByUserNick(userNick);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return null;
    }

    /**
     * 获取用户积分信息
     *
     * @return
     * @throws Exception
     */
    public List<UserPoints> getUserPointsList() {
        logger.info("UserPointsManagerImpl.getUserPointsList");
        try {
            return userPointsDao.getUserPointsList();
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return null;
    }

    public UserPoints getUserPointsByUserId(String userId) throws Exception {
        logger.info("UserPointsManagerImpl.getUserPointsByUserId");
        try {
            return userPointsDao.getUserPointsByUserId(userId);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return null;
    }

}
