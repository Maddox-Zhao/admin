package com.huaixuan.network.biz.service.user.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.huaixuan.network.biz.dao.user.UserPointsWatertableDao;
import com.huaixuan.network.biz.domain.user.UserPointsWatertable;
import com.huaixuan.network.biz.service.user.UserPointsWatertableManager;


@Service("userPointsWatertableManager")
public class UserPointsWatertableManagerImpl implements
		UserPointsWatertableManager {

	protected Log  log = LogFactory.getLog(this.getClass());

	@Autowired
	private UserPointsWatertableDao userPointsWatertableDao;


 	/* @model: */
 	public void addUserPointsWatertable(UserPointsWatertable userPointsWatertableDao) {
 		log.info("UserPointsWatertableManagerImpl.addUserPointsWatertable method");
 		try {
 			 this.userPointsWatertableDao.addUserPointsWatertable(userPointsWatertableDao);
 		} catch (Exception e) {
 			log.error(e.getMessage());
 		}
 	}

 	/* @model: */
 	public void editUserPointsWatertable(UserPointsWatertable userPointsWatertable) {
 		log.info("UserPointsWatertableManagerImpl.editUserPointsWatertable method");
 		try {
 			this.userPointsWatertableDao.editUserPointsWatertable(userPointsWatertable);
 		} catch (Exception e) {
 			log.error(e.getMessage());
 		}
 	}

 	/* @model: */
 	public void removeUserPointsWatertable(Long userPointsWatertableId) {
 		log.info("UserPointsWatertableManagerImpl.removeUserPointsWatertable method");
 		try {
 			this.userPointsWatertableDao.removeUserPointsWatertable(userPointsWatertableId);
 		} catch (Exception e) {
 			log.error(e.getMessage());
 		}
 	}

 	/* @model: */
 	public UserPointsWatertable getUserPointsWatertable(Long userPointsWatertableId) {
 		log.info("UserPointsWatertableManagerImpl.getUserPointsWatertable method");
 		try {
 			return this.userPointsWatertableDao.getUserPointsWatertable(userPointsWatertableId);
 		} catch (Exception e) {
 			log.error(e.getMessage());
 		}
 		return null;
 	}

 	
 	/**
 	 * 查询全部流水，以创建时间为序
 	 * 
 	 * @param page
 	 * @return
 	 * @see com.hundsun.bible.facade.user.UserPointsWatertableManager#getUserPointsWatertables(com.hundsun.bible.Page)
 	 */
// 	public PageUtil <UserPointsWatertable> getUserPointsWatertables(Page page) {
// 		log.info("UserPointsWatertableManagerImpl.getUserPointsWatertables method");
// 		try {
// 		    //
// 			return this.userPointsWatertableDao.getUserPointsWatertables(page);
// 		} catch (Exception e) {
// 			log.error(e.getMessage());
// 		}
// 		return null;
// 	}
 	
    /**
     * 查询某个会员帐号会员积分流水，以创建时间为序
     * 
     * @param userNick 会员帐号，不为能null和空
     * @param page
     * @return
     */
//    public PageUtil<UserPointsWatertable> getWatertablesByUserNick(String userNick, Page page){
//        log.info("UserPointsWatertableManagerImpl.getWatertablesByUserNick method");
//        try {
//            //
//            return this.userPointsWatertableDao.getWatertablesByUserNick(userNick,page.getCurrentPage(),
//                page.getPageSize(),page.getTotalRowsAmount());
//        } catch (Exception e) {
//            log.error(e.getMessage());
//        }
//        return null;                
//    }
    
    /**
     * 根据会员帐号、创建时间、及type(类型)为条件查询，以创建时间为序
     * 
     * @param pointsWaterable 流水对象用来封装查询条件，不为null
     * @return
     * @throws Exception
     */
//    public PageUtil<UserPointsWatertable> getWatertablesByComCondition(UserPointsWatertable pointsWaterable, Page page){
//        log.info("UserPointsWatertableManagerImpl.getWatertablesByComCondition method");
//        try {
//            //
//            return this.userPointsWatertableDao.getWatertablesByComCondition(pointsWaterable,page.getCurrentPage(),
//                page.getPageSize(),page.getTotalRowsAmount());
//        } catch (Exception e) {
//            log.error(e.getMessage());
//        }
//        return null;                  
//    }
    
    /**
     * 根据会员帐号、创建时间、及type(类型)为条件查询(全部)，以创建时间为序
     * 
     * @param pointsWaterable 流水对象用来封装查询条件，不为null
     * @return
     * @throws Exception
     */
//    public PageUtil<UserPointsWatertable> getWatertablesByComCondition(UserPointsWatertable pointsWaterable){
//        log.info("UserPointsWatertableManagerImpl.getWatertablesByComCondition method");
//        try {
//            //
//            return this.userPointsWatertableDao.getWatertablesByComCondition(pointsWaterable);
//        } catch (Exception e) {
//            log.error(e.getMessage());
//        }
//        return null;           
//        
//    }
    
    
    /**
     * 全部流水Count
     * @return
     * @throws Exception
     */
    public Integer getUserPointsWatertablesCount(){
        log.info("UserPointsWatertableManagerImpl.getUserPointsWatertablesCount method");
        try {
            //
            return this.userPointsWatertableDao.getUserPointsWatertablesCount();
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return null;           
    }
    
    
    /**
     * 查询某个会员帐号会员积分流水Count
     * 
     * @param userNick
     * @return
     * @throws Exception
     */
    public Integer getWatertablesByUserNickCount(String userNick){
        log.info("UserPointsWatertableManagerImpl.getWatertablesByUserNickCount method");
        try {
            //
            return this.userPointsWatertableDao.getWatertablesByUserNickCount(userNick);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return null;            
    }
    
    
    /**
     * 根据会员帐号、创建时间、及type(类型)为条件查询,获取总数
     * 
     * @param pointsWaterable
     * @return
     * @throws Exception
     */
    public Integer getWatertablesByComConditionCount(UserPointsWatertable pointsWaterable){
        log.info("UserPointsWatertableManagerImpl.getWatertablesByComConditionCount method");
        try {
            //
            return this.userPointsWatertableDao.getWatertablesByComConditionCount(pointsWaterable);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return null;           
    }

}
