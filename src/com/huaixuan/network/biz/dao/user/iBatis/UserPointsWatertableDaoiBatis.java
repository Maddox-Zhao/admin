package com.huaixuan.network.biz.dao.user.iBatis;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.ibatis.SqlMapClientTemplate;
import org.springframework.stereotype.Repository;

import com.huaixuan.network.biz.dao.user.UserPointsWatertableDao;
import com.huaixuan.network.biz.domain.user.UserPointsWatertable;

@Repository("userPointsWatertableDao")
public class UserPointsWatertableDaoiBatis implements UserPointsWatertableDao {

	@Autowired
	private SqlMapClientTemplate sqlMapClient;
	
	   /* @model: */
    public void addUserPointsWatertable(UserPointsWatertable userPointsWatertable) throws Exception {
        this.sqlMapClient.insert("addUserPointsWatertable", userPointsWatertable);
    }

    /* @model: */
    public void editUserPointsWatertable(UserPointsWatertable userPointsWatertable)
                                                                                   throws Exception {
        this.sqlMapClient.update("editUserPointsWatertable", userPointsWatertable);
    }

    /* @model: */
    public void removeUserPointsWatertable(Long userPointsWatertableId) throws Exception {
        this.sqlMapClient.delete("removeUserPointsWatertable", userPointsWatertableId);
    }

    /* @model: */
    public UserPointsWatertable getUserPointsWatertable(Long userPointsWatertableId)
                                                                                    throws Exception {
        return (UserPointsWatertable) this.sqlMapClient.queryForObject(
            "getUserPointsWatertable", userPointsWatertableId);
    }

    /**
     * 查询全部流水，以创建时间为序
     * 
     * @param page
     * @return
     * @throws Exception
     * @see com.hundsun.bible.dao.UserPointsWatertableDao#getUserPointsWatertables(com.hundsun.bible.Page)
     */
//    @SuppressWarnings("unchecked")
//    public PageUtil<UserPointsWatertable> getUserPointsWatertables(Page page) throws Exception {
//
//        List<UserPointsWatertable> list = this
//            .findQueryPage("getUserPointsWatertables", null, page);
//
//        return new PageUtil<UserPointsWatertable>(list, page);
//
//    }

    /**
     * 根据会员帐号、创建时间、及type(类型)为条件查询，以创建时间为序
     * 
     * @param pointsWaterable 流水对象用来封装查询条件，不为null
     * @return
     * @throws Exception
     */
//    @SuppressWarnings("unchecked")
//    public PageUtil<UserPointsWatertable> getWatertablesByComCondition(
//                                                                       UserPointsWatertable pointsWaterable,
//                                                                       int currentPage,
//                                                                       int pageSize, int totalCount)
//                                                                                                    throws Exception {
//
//        Page page = new Page();
//        page.setPageSize(pageSize);
//        page.setTotalRowsAmount(totalCount);
//        page.setCurrentPage(currentPage);
//        List<UserPointsWatertable> list = this.findQueryPage("getWatertablesByComCondition",
//            pointsWaterable, page);
//
//        return new PageUtil<UserPointsWatertable>(list, page);
//    }

    /**
     * 根据会员帐号、创建时间、及type(类型)为条件查询，以创建时间为序
     * 
     * @param pointsWaterable 流水对象用来封装查询条件，不为null
     * @return
     * @throws Exception
     */
//    @SuppressWarnings("unchecked")
//    public PageUtil<UserPointsWatertable> getWatertablesByComCondition(
//                                                                       UserPointsWatertable pointsWaterable)
//                                                                                                            throws Exception {
//
//        List<UserPointsWatertable> list = this.sqlMapClient.queryForList(
//            "getAllWatertablesByComCondition", pointsWaterable);
//
//        return new PageUtil<UserPointsWatertable>(list, null);
//
//    }

    /**
     * 查询某个会员帐号会员积分流水，以创建时间为序
     * 
     * @param userNick 会员名称，不为能null和空
     * @return
     * @throws Exception
     */
 //   @SuppressWarnings("unchecked")
//    public PageUtil<UserPointsWatertable> getWatertablesByUserNick(String userNick,
//                                                                   int currentPage, int pageSize,
//                                                                   int totalCount) throws Exception {
//
//        Page page = new Page();
//        page.setPageSize(pageSize);
//        page.setTotalRowsAmount(totalCount);
//        page.setCurrentPage(currentPage);
//        List<UserPointsWatertable> list = this.findQueryPage("getWatertablesByUserNick", userNick,
//            page);
//
//        return new PageUtil<UserPointsWatertable>(list, page);
//    }

    /**
     * 全部流水Count
     * @return
     * @throws Exception
     */
    public Integer getUserPointsWatertablesCount() throws Exception {
        return (Integer) this.sqlMapClient.queryForObject(
            "getUserPointsWatertablesCount");
    }

    /**
     * 查询某个会员帐号会员积分流水Count
     * 
     * @return
     * @throws Exception
     */
    public Integer getWatertablesByUserNickCount(String userNick) throws Exception {
        return (Integer) this.sqlMapClient.queryForObject(
            "getWatertablesByUserNickCount", userNick);
    }

    /**
     * 根据会员帐号、创建时间、及type(类型)为条件查询,获取总数
     * 
     * @return
     * @throws Exception
     */
    public Integer getWatertablesByComConditionCount(UserPointsWatertable pointsWaterable)
                                                                                          throws Exception {
        return (Integer) this.sqlMapClient.queryForObject(
            "getWatertablesByComConditionCount", pointsWaterable);
    }
	
}
