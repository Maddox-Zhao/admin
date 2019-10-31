package com.huaixuan.network.biz.service.admin.impl;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.huaixuan.network.biz.dao.admin.ResourcesDao;
import com.huaixuan.network.biz.domain.admin.Resources;
import com.huaixuan.network.biz.service.admin.ResourcesManager;

@Service("resourcesManager")
public class ResourcesManagerImpl implements ResourcesManager {

	@Autowired
	private ResourcesDao resourcesDao;
	
	protected Log  log = LogFactory.getLog(this.getClass());

	@Override
    public void addResources(Resources resourcesDao) {
        log.info("ResourcesManagerImpl.addResources method");
        try {
            this.resourcesDao.addResources(resourcesDao);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }
	@Override
    public void editResources(Resources resources) {
        log.info("ResourcesManagerImpl.editResources method");
        try {
            this.resourcesDao.editResources(resources);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }
	@Override
    public void removeResources(Long resourcesId) {
        log.info("ResourcesManagerImpl.removeResources method");
        try {
            this.resourcesDao.removeResources(resourcesId);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }
	@Override
    public Resources getResources(Long resourcesId) {
        log.info("ResourcesManagerImpl.getResources method");
        try {
            return this.resourcesDao.getResources(resourcesId);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return null;
    }
	@Override
    public String getResorcesValue(String type, Long point) {
        log.info("ResourcesManagerImpl.getResorcesValue method");
        try {
            List<Resources> resourceList = resourcesDao.getResourcesByType(type);
            for (Resources resources : resourceList) {
                String name = resources.getName();
                String[] nameArea = name.split("~");
                if (nameArea.length == 2) {
                    if (point >= Long.parseLong(nameArea[0])
                        && point <= Long.parseLong(nameArea[1])) {
                        return resources.getValue();
                    }

                }
            }
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return null;
    }
	@Override
    public String getResorcesValue(String type, String name) {
        log.info("ResourcesManagerImpl.getResorcesValue method");
        try {
            if (StringUtils.isEmpty(type) && StringUtils.isEmpty(name)) {
                return null;
            }
            Resources resources = resourcesDao.getResourcesByTypeAndName(type, name);
            if (null == resources) {
                return null;
            }
            return resources.getValue();

        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return null;
    }
	@Override
    public List<Resources> getResourcess() {
        log.info("ResourcesManagerImpl.getResourcess method");
        try {
            return this.resourcesDao.getResourcess();
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return null;
    }
	@Override
    public List<Resources> getResourcesByMap(Map map) {
        return this.resourcesDao.getResourcesByMap(map);
    }
	@Override
    public Resources getResourcesByTypeAndName(String type, String name) {
        try {
            return resourcesDao.getResourcesByTypeAndName(type, name);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return null;
    }
	@Override
    public List<Resources> getResourcesByType(String type) {
        try {
            return resourcesDao.getResourcesByType(type);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return null;
    }

}
