package com.huaixuan.network.biz.dao.goods.ibatis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.ibatis.SqlMapClientTemplate;
import org.springframework.stereotype.Repository;

import com.huaixuan.network.biz.dao.goods.WebSiteRelationDao;
import com.huaixuan.network.biz.domain.goods.WebSiteRelation;

@Repository("webSiteRelationDao")
public class WebSiteRelationDaoiBatis implements WebSiteRelationDao {
	@Autowired
	private SqlMapClientTemplate sqlMapClient;

    public WebSiteRelation getWebSiteRelation(WebSiteRelation webSiteRelation) {
        return (WebSiteRelation) this.sqlMapClient.queryForObject("getWebSiteRelation",webSiteRelation);
 }

 public void insertWebSiteRelation(WebSiteRelation webSiteRelation) {
     this.sqlMapClient.insert("addWebSiteRelation", webSiteRelation);

 }

 public void removeWebSiteRelation(WebSiteRelation webSiteRelation) {
     sqlMapClient.update("editWebSiteRelationStatus", webSiteRelation);

 }



 public void updateWebSiteRelationStatus(WebSiteRelation webSiteRelation) {
     sqlMapClient.update("editWebSiteRelationStatus", webSiteRelation);

 }

}
