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
	 * ������Ŀ���룬��ȡ��Ŀ���������Լ���(�����̳е�����)�����ͬ������ȡ�����Ŀ�µ�����
	 * 
	 * @param catCode
	 * @return
	 * @throws DaoException
	 */
	List<AttributeDTO> getAttributeDTOByCatCode(String catCode);

	/**
	 * ����attrCode�õ�Attribute��Ϣ
	 * 
	 * @param attrCode
	 * @return
	 * @throws Exception
	 */
	public Attribute getAttributeByAttrCode(String attrCode);

	public String getFullAttributeStringByAttrs(String attrs);

	/**
	 * ���������ַ����õ������е�����ֵ�ַ���
	 * 
	 * @param attrs
	 * @return
	 * @throws Exception
	 */
	public String getAllAttributeValueByAttrs(String attrs) throws Exception;

	/**
	 * �������ƻ�ȡ����
	 * 
	 * @param attrs
	 * @return
	 * @throws Exception
	 */
	public String getFullAttributeStringByAttrsTwo(String attrs) throws Exception;
	

	public QueryPage getAttrListByConditionWithPage(int currPage, int pageSize);
	
	public Attribute getAttributesByName(String attrName);
}
