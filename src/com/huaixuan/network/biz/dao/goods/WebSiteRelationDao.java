package com.huaixuan.network.biz.dao.goods;

import com.huaixuan.network.biz.domain.goods.WebSiteRelation;

public interface WebSiteRelationDao {

    void insertWebSiteRelation(WebSiteRelation webSiteRelation);

    void removeWebSiteRelation(WebSiteRelation webSiteRelation);

    void updateWebSiteRelationStatus(WebSiteRelation webSiteRelation);

    WebSiteRelation getWebSiteRelation(WebSiteRelation webSiteRelation);
}
