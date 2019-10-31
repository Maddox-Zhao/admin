package com.huaixuan.network.biz.dao.user.iBatis;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.ibatis.SqlMapClientTemplate;
import org.springframework.stereotype.Repository;

import com.huaixuan.network.biz.dao.user.UserPointsDao;
import com.huaixuan.network.biz.domain.user.UserPoints;

@Repository("userPointsDao")
public class UserPointsDaoiBatis implements UserPointsDao {

	@Autowired
	private SqlMapClientTemplate sqlMapClient;
	
    /**
    *
    * @param userPoints
    * @throws Exception
    * @see com.hundsun.bible.dao.UserPointsDao#addUserPoints(com.hundsun.bible.domain.model.UserPoints)
    */
   public void addUserPoints(UserPoints userPoints) throws Exception {
       this.sqlMapClient.insert("addUserPoints", userPoints);
   }

   /**
    *
    * @param userPoints
    * @throws Exception
    * @see com.hundsun.bible.dao.UserPointsDao#editUserPoints(com.hundsun.bible.domain.model.UserPoints)
    */
   public void editUserPoints(UserPoints userPoints) throws Exception {
       this.sqlMapClient.update("editUserPoints", userPoints);
   }

   /* @model: */
   public void removeUserPoints(Long userPointsId) throws Exception {
       this.sqlMapClient.delete("removeUserPoints", userPointsId);
   }

   /* @model: */
   public UserPoints getUserPoints(Long userPointsId) throws Exception {
       return (UserPoints) this.sqlMapClient.queryForObject("getUserPoints",
           userPointsId);
   }

   public List<UserPoints> getUserPointssExportList(UserPoints userPoints) throws Exception {
       List<UserPoints> list = this.sqlMapClient.queryForList("getUserPointss",
           userPoints);
       return list;
   }

//   /**
//    * 查询所有会员积分，以修改时间为序
//    *
//    * @return
//    * @throws Exception
//    * @see com.hundsun.bible.dao.UserPointsDao#getUserPointss()
//    */
//   @SuppressWarnings("unchecked")
//   public List<UserPoints> getUserPoints(Map parm, Page page) throws Exception {
//       List<UserPoints> list = null;
//       if (page == null) {
//           list = this.sqlMapClient.queryForList("getUserPointss", parm);
//       } else {
//
//           list = this.findQueryPage("getUserPointss", parm, page);
//       }
//
//       return list;
//   }

   /**
    * 根据会员帐号统计其是否已存在积分记录
    *
    * @param userNick
    * @return
    * @throws Exception
    * @see com.hundsun.bible.dao.UserPointsDao#getCountByUserNick(java.lang.String)
    */
   public Integer getCountByUserNick(String userNick) throws Exception {
       return (Integer) this.sqlMapClient.queryForObject("getCountByUserNick",
           userNick);
   }

   /**
    * 根据会员帐号查询积分情况
    *
    * @param userNick
    * @return
    * @throws Exception
    * @see com.hundsun.bible.dao.UserPointsDao#getUserPointsByUserNick(java.lang.String)
    */
   public UserPoints getUserPointsByUserNick(String userNick) throws Exception {
       return (UserPoints) this.sqlMapClient.queryForObject(
           "getUserPointsByUserNick", userNick);
   }

   /**
    * 获取记录总数
    *
    * @return
    * @throws Exception
    */
   public Integer getUserPointsCount(Map parm) throws Exception {
       return (Integer) this.sqlMapClient.queryForObject("getUserPointsCount", parm);
   }

   @SuppressWarnings("unchecked")
   public List<UserPoints> getUserPointsList() throws Exception {
       return sqlMapClient.queryForList("getUserPointsList");
   }

   public UserPoints getUserPointsByUserId(String userId) throws Exception {
       return (UserPoints) sqlMapClient.queryForObject("getUserPointsByUserId",
           userId);
   }

}
