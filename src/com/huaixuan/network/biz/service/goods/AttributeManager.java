package com.huaixuan.network.biz.service.goods;

import java.util.List;

import com.huaixuan.network.biz.domain.admin.Admin;
import com.huaixuan.network.biz.domain.goods.Attribute;
import com.huaixuan.network.biz.domain.goods.AttributeDTO;
import com.huaixuan.network.biz.domain.goods.CatAttrRel;
import com.huaixuan.network.biz.query.QueryPage;

public interface AttributeManager {
	/* @interface model: Attribute */
	public boolean addAttribute(Attribute attribute) throws Exception;

	/* @interface model: Attribute */
	public boolean editAttribute(Attribute attribute) throws Exception;

	/* @interface model: Attribute */
	public boolean removeAttribute(Long attributeId, String attrCode) throws Exception;

	/* @interface model: Attribute,Attribute */
	public Attribute getAttribute(Long attributeId) throws Exception;

	/* @interface model: Attribute,Attribute */
	public List<Attribute> getAttributes(String catCode) throws Exception;

	// public PageUtil<Attribute> getAttributesAll(int currentPage, int pageSize) throws Exception;

	/**
	 * check the name is unique or not
	 * 
	 * @param attribute
	 * @return
	 * @throws Exception
	 */
	public boolean checkNameAvailable(Attribute attribute) throws Exception;

	/**
	 * querygetget get attribute Info by Name
	 * 
	 * @param name
	 * @return
	 * @throws Exception
	 */
	// public PageUtil<Attribute> getAttributesByName(String name, String catCode, int currentPage,
	// int pageSize) throws Exception;
	
	public QueryPage getAttributesByNameByPage(CatAttrRel catAttrRel, int currPage, int pageSize)throws Exception;

	/**
	 * 根据类目代码，获取类目下所有属性集合(包括继承的属性)，如果同名，获取最近类目下的属性
	 * 
	 * @param catCode
	 * @return
	 * @throws DaoException
	 */
	List<AttributeDTO> getAttributeDTOByCatCode(String catCode);

	/**
	 * 根据attrCode得到Attribute信息
	 * 
	 * @param attrCode
	 * @return
	 * @throws Exception
	 */
	public Attribute getAttributeByAttrCode(String attrCode);

	public String getFullAttributeStringByAttrs(String attrs);

	/**
	 * 根据属性字符串得到属性中的所有值字符串
	 * 
	 * @param attrs
	 * @return
	 * @throws Exception
	 */
	public String getAllAttributeValueByAttrs(String attrs) throws Exception;

	/**
	 * 根据名称获取属性
	 * 
	 * @param attrs
	 * @return
	 * @throws Exception
	 */
	public String getFullAttributeStringByAttrsTwo(String attrs) throws Exception;
	

	public QueryPage getAttrListByConditionWithPage(int currPage, int pageSize);
	
	public Attribute getAttributesByName(String attrName);
}
