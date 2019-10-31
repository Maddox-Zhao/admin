package com.huaixuan.network.biz.dao.admin.ibatis;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.ibatis.SqlMapClientTemplate;
import org.springframework.stereotype.Repository;

import com.huaixuan.network.biz.dao.admin.ResourcesDao;
import com.huaixuan.network.biz.domain.admin.Resources;

@Repository("resourcesDao")
public class ResourcesDaoiBatis implements ResourcesDao {
	@Autowired
	private SqlMapClientTemplate sqlMapClient;
	@Override
    public List<Resources> getResourcesByMap(Map map) {
        return this.sqlMapClient.queryForList("getResourcesValueByType",map);
    }
    @Override
    public void addResources(Resources resources) throws Exception {
        this.sqlMapClient.insert("addResources", resources);
    }

    @Override
    public void editResources(Resources resources) throws Exception {
        this.sqlMapClient.update("editResources", resources);
    }

    @Override
    public void removeResources(Long resourcesId) throws Exception {
        this.sqlMapClient.delete("removeResources", resourcesId);
    }

    @Override
    public Resources getResources(Long resourcesId) throws Exception {
        return (Resources) this.sqlMapClient.queryForObject("selectResources",
            resourcesId);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Resources> getResourcess() throws Exception {
        return this.sqlMapClient.queryForList("getResourcess", null);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Resources> getResourcesByType(String type) throws Exception {
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("type", type);
        return this.sqlMapClient.queryForList("getResourcesValueByType", paramMap);
    }
    @Override
    public Resources getResourcesByTypeAndName(String type,String name) throws Exception {
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("type", type);
        paramMap.put("name", name);
        return (Resources) this.sqlMapClient.queryForObject("getResourcesValueByType", paramMap);
    }
	
}
