package com.huaixuan.network.biz.service.goods.impl;

import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.huaixuan.network.biz.dao.goods.AttributeDao;
import com.huaixuan.network.biz.dao.goods.CatAttrRelDao;
import com.huaixuan.network.biz.domain.admin.Admin;
import com.huaixuan.network.biz.domain.goods.Attribute;
import com.huaixuan.network.biz.domain.goods.AttributeDTO;
import com.huaixuan.network.biz.domain.goods.CatAttrRel;
import com.huaixuan.network.biz.query.QueryPage;
import com.huaixuan.network.biz.service.common.CodeManager;
import com.huaixuan.network.biz.service.goods.AttributeManager;
import com.huaixuan.network.common.util.DateUtil;

@Service("attributeManager")
public class AttributeManagerImpl implements AttributeManager {

	protected Log log = LogFactory.getLog(this.getClass());

	public static final String sep = ";";
	public static final String equ = "=";

	@Autowired
	private AttributeDao attributeDao;

	@Autowired
	private CodeManager codeManager;

	@Autowired
	private CatAttrRelDao catAttrRelDao;

	private String getRandomAttriCode() {
		String randomAttrCode = RandomStringUtils.random(6, false, true);
		String today = DateUtil.convertDateToString("yyyyMMdd", new Date());
		StringBuffer sb = new StringBuffer();
		sb.append(today);
		sb.append(randomAttrCode);
		return sb.toString();
	}

	/* @model: */
	public boolean addAttribute(Attribute attribute) throws Exception {
		log.info("AttributeManagerImpl.addAttribute method");
		String attrCode = codeManager.buildCode(2);
		/**
		 * if the generated random catCode is not unique,then we try to get another one
		 */
		while (attributeDao.getAttributeByCode(attrCode) > 0) {
			attrCode = codeManager.buildCode(2);
		}
		attribute.setAttrCode(attrCode);
		if (checkNameAvailable(attribute)) {
			this.attributeDao.addAttribute(attribute);
			return true;
		}
		return false;
	}

	/* @model: */
	public boolean editAttribute(Attribute attribute) throws Exception {
		if (checkNameAvailable(attribute)) {
			this.attributeDao.editAttribute(attribute);
			return true;
		}
		return false;
	}

	/* @model: */
	public boolean removeAttribute(Long attributeId, String attrCode) throws Exception {
		log.info("AttributeManagerImpl.removeAttribute method");
		try {
			if (this.catAttrRelDao.countInfoByAttrCode(attrCode) <= 0) {
				this.attributeDao.removeAttribute(attributeId);
				return true;
			}
		} catch (Exception e) {
			if (log.isErrorEnabled()) {
				log.error(e.getMessage());
				throw e;
			}
		}
		return false;
	}

	/* @model: */
	public Attribute getAttribute(Long attributeId) throws Exception {
		log.info("AttributeManagerImpl.getAttribute method");
		try {
			return this.attributeDao.getAttribute(attributeId);
		} catch (Exception e) {
			if (log.isErrorEnabled()) {
				log.error(e.getMessage());
				throw e;
			}
		}
		return null;
	}

	/* @model: */
	public List<Attribute> getAttributes(String catCode) throws Exception {
		log.info("AttributeManagerImpl.getAttributes method");
		try {
			return this.attributeDao.getAttributes(catCode);
		} catch (Exception e) {
			if (log.isErrorEnabled()) {
				log.error(e.getMessage());
				throw e;
			}
		}
		return null;
	}

	public boolean checkNameAvailable(Attribute attribute) throws Exception {
		boolean flag = false;
		try {

			// 先去掉同名重复的校验??1??7
			if (true)
				return true;
			int count = attributeDao.checkNameAvailable(attribute);
			if (count == 0) {
				flag = true;
			}
			/*
			 * if (count == 1) { if (attribute.getId() == attributeDao.getAttributeByName(attribute.getAttrName())
			 * .getId()) { flag = true; } }
			 */
		} catch (Exception e) {
			if (log.isErrorEnabled()) {
				log.error(e.getMessage());
				throw e;
			}
		}
		return flag;
	}

	public List<AttributeDTO> getAttributeDTOByCatCode(String catCode) {
		return this.catAttrRelDao.getAttributeDTOByCatCode(catCode);
	}

	public Attribute getAttributeByAttrCode(String attrCode) {
		return this.attributeDao.getAttributeByBuberChoose(attrCode);
	}

	public String getFullAttributeStringByAttrs(String attrs) {
		if(StringUtils.isBlank(attrs)){
			return "";
		}
		StringBuilder attrsSb = new StringBuilder();
		Set<Map.Entry<String, String>> attrsMap = getProperties(attrs).entrySet();
		String attreValue = "";
		for (Map.Entry<String, String> aa : attrsMap) {
			if (StringUtils.isNotEmpty(aa.getValue())) {
				attreValue = aa.getValue();
			}
			attrsSb.append(getAttributeByAttrCode(aa.getKey()).getAttrName()).append("=").append(attreValue);
		}
		return attrsSb.toString();
	}

	/*
	 * （非 Javadoc）
	 *
	 * @see com.hundsun.bible.facade.goods.AttributeManager#getAllAttributeValueByAttrs(java.lang.String)
	 */
	public String getAllAttributeValueByAttrs(String attrs) throws Exception {
		StringBuilder attrsSb = new StringBuilder();
		Set<Map.Entry<String, String>> attrsMap = getProperties(attrs).entrySet();
		for (Map.Entry<String, String> aa : attrsMap) {
			if (StringUtils.isNotEmpty(aa.getValue())) {
				attrsSb.append(aa.getValue());
			}
		}
		return attrsSb.toString();
	}

	private Map<String, String> getProperties(String attrs) {
		if (StringUtils.isBlank(attrs)) {
			return Collections.EMPTY_MAP;
		}
		String[] pros = attrs.split(sep);
		if (pros.length == 0) {
			return Collections.EMPTY_MAP;
		}
		Map<String, String> back = new LinkedHashMap<String, String>();
		for (String s : pros) {
			if (StringUtils.isBlank(s)) {
				continue;
			}
			String[] kvpair = s.split(equ);
			if (kvpair == null) {
				continue;
			}
			if (kvpair.length == 1) {
				back.put(kvpair[0], null);
			}
			if (kvpair.length == 2) {
				back.put(kvpair[0], kvpair[1]);
			}
		}
		return back;
	}

	public String getFullAttributeStringByAttrsTwo(String attrs) throws Exception {
		StringBuilder attrsSb = new StringBuilder();
		Set<Map.Entry<String, String>> attrsMap = getProperties(attrs).entrySet();
		String attreValue = "";
		for (Map.Entry<String, String> aa : attrsMap) {
			if (StringUtils.isNotEmpty(aa.getValue())) {
				attreValue = aa.getValue();
			}
			attrsSb.append(getAttributeByAttrCode(aa.getKey()).getAttrName()).append("=").append(attreValue)
					.append(";");
		}
		return attrsSb.toString();
	}

	@SuppressWarnings("unchecked")
	@Override
	public QueryPage getAttributesByNameByPage(CatAttrRel catAttrRel, int currPage, int pageSize) throws Exception {
		QueryPage queryPage = new QueryPage(catAttrRel);
		Map pramas = queryPage.getParameters();

		int count = attributeDao.getAttributeByNameCount(pramas);

		if (count > 0) {

			/* 当前页 */
			queryPage.setCurrentPage(currPage);
			/* 每页总数 */
			queryPage.setPageSize(pageSize);
			queryPage.setTotalItem(count);

			pramas.put("startRow", queryPage.getPageFristItem());
			pramas.put("endRow", queryPage.getPageLastItem());

			List<Attribute> attributeList = attributeDao.getAttributesByName(pramas);
			if (attributeList != null && attributeList.size() > 0) {
				queryPage.setItems(attributeList);
			}
		}
		return queryPage;
	}

	@SuppressWarnings("unchecked")
	@Override
	public QueryPage getAttrListByConditionWithPage(int currPage, int pageSize) {
		Attribute attribute = new Attribute();
		QueryPage queryPage = new QueryPage(attribute);
		Map pramas = queryPage.getParameters();

		int count = attributeDao.getAllrAllCount();

		if (count > 0) {

			/* 当前页 */
			queryPage.setCurrentPage(currPage);
			/* 每页总数 */
			queryPage.setPageSize(pageSize);
			queryPage.setTotalItem(count);

			pramas.put("startRow", queryPage.getPageFristItem());
			pramas.put("endRow", queryPage.getPageLastItem());

			List<Attribute> attributeList = attributeDao.getAttributesAll(pramas);
			if (attributeList != null && attributeList.size() > 0) {
				queryPage.setItems(attributeList);
			}
		}
		return queryPage;
	}

	@Override
	public Attribute getAttributesByName(String attrName) {
		return attributeDao.getAttributeByName(attrName);
	}

	// public PageUtil<Attribute> getAttributesByName(String attName, String catCode, int currentPage,
	// int pageSize) throws Exception {
	// PageUtil<Attribute> pageUtil = new PageUtil<Attribute>(new ArrayList<Attribute>(),
	// new Page());
	// Map map = new HashMap<String, Object>();
	// map.put("attrName", attName);
	// map.put("catCode", catCode);
	// int count = this.attributeDao.getAttributeByNameCount(map);
	// try {
	// pageUtil = this.attributeDao.getAttributesByName(map, currentPage, pageSize, count);
	// } catch (Exception e) {
	// if (log.isErrorEnabled()) {
	// log.error(e.getMessage());
	// throw e;
	// }
	// }
	// return pageUtil;
	// }

	// public PageUtil<c> getAttributesAll(int currentPage, int pageSize) throws Exception {
	// int count = this.attributeDao.getAllrAllCount();
	// return this.attributeDao.getAttributesAll(currentPage, pageSize, count);
	// }




}
