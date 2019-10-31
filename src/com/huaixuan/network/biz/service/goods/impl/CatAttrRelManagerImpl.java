package com.huaixuan.network.biz.service.goods.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.huaixuan.network.biz.dao.goods.CatAttrRelDao;
import com.huaixuan.network.biz.dao.goods.CategoryDao;
import com.huaixuan.network.biz.domain.goods.CatAttrRel;
import com.huaixuan.network.biz.domain.goods.Category;
import com.huaixuan.network.biz.service.goods.CatAttrRelManager;


@Service("catAttrRelManager")
public class CatAttrRelManagerImpl implements CatAttrRelManager {
    /**
    * Logger for this class
    */
    private static final Logger logger = Logger.getLogger(CatAttrRelManagerImpl.class);
    
	protected Log  log = LogFactory.getLog(this.getClass());

    @Autowired
    private  CatAttrRelDao       catAttrRelDao;
 
    @Autowired
    private  CategoryDao         categoryDao;


    /* @model: */
    public void addCatAttrRel(CatAttrRel catAttrRel) {
        log.info("CatAttrRelManagerImpl.addCatAttrRel method");
        try {
            int count = this.catAttrRelDao.getRelAcountOfTheCategory(catAttrRel);
            catAttrRel.setSortOrder(count + 1);
            this.catAttrRelDao.addCatAttrRel(catAttrRel);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }

    /* @model: */
    public void editCatAttrRel(CatAttrRel catAttrRel) {
        log.info("CatAttrRelManagerImpl.editCatAttrRel method");
        try {
            this.catAttrRelDao.editCatAttrRel(catAttrRel);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }

    /* @model: */
    public void removeCatAttrRel(Long catAttrRelId) {
        log.info("CatAttrRelManagerImpl.removeCatAttrRel method");
        try {
            this.catAttrRelDao.removeCatAttrRel(catAttrRelId);

        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }

    /* @model: */
    public CatAttrRel getCatAttrRel(Long catAttrRelId) {
        log.info("CatAttrRelManagerImpl.getCatAttrRel method");
        try {
            return this.catAttrRelDao.getCatAttrRel(catAttrRelId);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return null;
    }

    /* @model: */
    public List<CatAttrRel> getCatAttrRels() {
        log.info("CatAttrRelManagerImpl.getCatAttrRels method");
        try {
            return this.catAttrRelDao.getCatAttrRels();
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return null;
    }

    public int getSortOrderOfTheCatRel(CatAttrRel catAttrRel) throws Exception {
        if (logger.isDebugEnabled()) {
            logger.debug("getSortOrderOfTheCatRel(CatAttrRel) - start"); //$NON-NLS-1$
        }
        int count = 0;
        try {
            count = this.catAttrRelDao.getRelAcountOfTheCategory(catAttrRel);
        } catch (Exception e) {
            if (log.isErrorEnabled()) {
                log.error(e.getMessage());
                throw e;
            }
        }
        return count + 1;
    }

    @SuppressWarnings("unchecked")
    public List<CatAttrRel> getAttributeOfTheCategoty(String catCode) throws Exception {
        if (logger.isDebugEnabled()) {
            logger.debug("getAttributeOfTheCategoty(String) - start"); //$NON-NLS-1$
        }
        //定义list 的排序
        Comparator comp = new Comparator() {
            public int compare(Object o1, Object o2) {
                CatAttrRel p1 = (CatAttrRel) o1;
                CatAttrRel p2 = (CatAttrRel) o2;
                int flag = p1.getCatCode().compareTo(p2.getCatCode());
                if (flag == 0) {
                    flag = String.valueOf(p1.getSortOrder()).compareTo(
                        String.valueOf(p2.getSortOrder()));
                }
                return flag;
            }
        };
        Map<String, List<CatAttrRel>> map = new HashMap<String, List<CatAttrRel>>();
        List<CatAttrRel> list = new ArrayList<CatAttrRel>();
        List<CatAttrRel> listAll = new ArrayList<CatAttrRel>();
        Category category = new Category();
        try {
            list = catAttrRelDao.getAttributeOfTheCategory(catCode);
            for (CatAttrRel catAttrRel : list) {
                listAll.add(catAttrRel);
            }
            category = categoryDao.getCategoryByCode(catCode);
            //have parent,so the property of the parent is the property of mine
            while (!category.getParentCode().equalsIgnoreCase("-1")) {
                for (CatAttrRel catAttrRel : catAttrRelDao.getAttributeOfTheCategory(category
                    .getParentCode())) {
                    //表示这是父节点的
                    catAttrRel.setParentTag(1);
                    listAll.add(catAttrRel);
                }
                category = categoryDao.getCategoryByCode(category.getParentCode());
            }
        } catch (Exception e) {
            if (log.isErrorEnabled()) {
                log.error(e.getMessage());
                throw e;
            }
        }
        Collections.sort(listAll, comp);
        return listAll;

    }

    /**
     * 关联的上一条属性信息
     * @param catAttrRel
     * @return
     * @throws Exception
     */
    private CatAttrRel getLastRecord(CatAttrRel catAttrRel) throws Exception {
        if (logger.isDebugEnabled()) {
            logger.debug("getLastRecord(CatAttrRel) - start"); //$NON-NLS-1$
        }
        CatAttrRel catAttrRel2 = new CatAttrRel();
        try {
            catAttrRel2 = catAttrRelDao.getLastRecord(catAttrRel);
        } catch (Exception e) {
            if (log.isErrorEnabled()) {
                log.error(e.getMessage());
                throw e;
            }
        }
        return catAttrRel2;
    }

    /**
     * 关联的下一条属性信息
     * @param catAttrRel
     * @return
     * @throws Exception
     */
    private CatAttrRel getNextRecord(CatAttrRel catAttrRel) throws Exception {
        if (logger.isDebugEnabled()) {
            logger.debug("getLastRecord(CatAttrRel) - start"); //$NON-NLS-1$
        }
        CatAttrRel catAttrRel2 = new CatAttrRel();
        try {
            catAttrRel2 = catAttrRelDao.getNextRecord(catAttrRel);
        } catch (Exception e) {
            if (log.isErrorEnabled()) {
                log.error(e.getMessage());
                throw e;
            }
        }
        return catAttrRel2;

    }

    public void exchangeSortOrderAfter(long sourceID) throws Exception {
        if (logger.isDebugEnabled()) {
            logger.debug("exchangeSortOrderAfter(long) - start"); //$NON-NLS-1$
        }
        CatAttrRel catAttrRel = new CatAttrRel();
        CatAttrRel sourceNew = new CatAttrRel();
        CatAttrRel desNew = new CatAttrRel();
        try {
            catAttrRel = catAttrRelDao.getCatAttrRel(sourceID);
            int sourceOder = catAttrRel.getSortOrder();
            long desId = getNextRecord(catAttrRel).getId();
            int desOrder = getNextRecord(catAttrRel).getSortOrder();
            sourceNew.setSortOrder(desOrder);
            sourceNew.setId(sourceID);
            desNew.setSortOrder(sourceOder);
            desNew.setId(desId);
            this.catAttrRelDao.editSortOrder(sourceNew);
            this.catAttrRelDao.editSortOrder(desNew);

        } catch (Exception e) {
            // TODO: handle exception
        }

        if (logger.isDebugEnabled()) {
            logger.debug("exchangeSortOrderAfter(long) - end"); //$NON-NLS-1$
        }
    }

    public void exchangeSortOrderBefore(long sourceID) {
        if (logger.isDebugEnabled()) {
            logger.debug("exchangeSortOrderAfter(long) - start"); //$NON-NLS-1$
        }
        CatAttrRel catAttrRel = new CatAttrRel();
        CatAttrRel sourceNew = new CatAttrRel();
        CatAttrRel desNew = new CatAttrRel();
        try {
            catAttrRel = catAttrRelDao.getCatAttrRel(sourceID);
            int sourceOder = catAttrRel.getSortOrder();
            long desId = getLastRecord(catAttrRel).getId();
            int desOrder = getLastRecord(catAttrRel).getSortOrder();
            sourceNew.setSortOrder(desOrder);
            sourceNew.setId(sourceID);
            desNew.setSortOrder(sourceOder);
            desNew.setId(desId);
            this.catAttrRelDao.editSortOrder(sourceNew);
            this.catAttrRelDao.editSortOrder(desNew);

        } catch (Exception e) {
            // TODO: handle exception
        }

        if (logger.isDebugEnabled()) {
            logger.debug("exchangeSortOrderAfter(long) - end"); //$NON-NLS-1$
        }
    }

	public CatAttrRel getCatAttrRelByCondition(String catCode, String attrCode) {
		Map parMap = new HashMap();
		parMap.put("catCode", catCode);
		parMap.put("attrCode", attrCode);
		return catAttrRelDao.getCatAttrRelByCondition(parMap);
	}

}
