package com.huaixuan.network.biz.dao.goods.ibatis;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.ibatis.SqlMapClientTemplate;
import org.springframework.stereotype.Repository;

import com.huaixuan.network.biz.dao.goods.AttributeDao;
import com.huaixuan.network.biz.domain.goods.Attribute;
import com.huaixuan.network.biz.query.QueryPage;

@Repository("attributeDao")
public class AttributeDaoiBatis implements AttributeDao {

	@Autowired
	private SqlMapClientTemplate sqlMapClient;

	public void addAttribute(Attribute attribute) throws Exception {
		this.sqlMapClient.insert("addAttribute", attribute);
	}

	public void editAttribute(Attribute attribute) throws Exception {
		this.sqlMapClient.update("editAttribute", attribute);
	}

	public void removeAttribute(Long attributeId) throws Exception {
		this.sqlMapClient.update("removeAttribute", attributeId);
	}

	public Attribute getAttribute(Long attributeId) throws Exception {
		return (Attribute) this.sqlMapClient.queryForObject("getAttribute", attributeId);
	}

	public List<Attribute> getAttributes(String catCode) throws Exception {
		return this.sqlMapClient.queryForList("getAttributes", catCode);
	}

	public Attribute getAttributeByName(String attributeName) {
		return (Attribute) this.sqlMapClient.queryForObject("getAttributeByName", attributeName);
	}

	public void removeAll() throws Exception {
		this.sqlMapClient.update("removeALLAttribute");
	}

	public int checkNameAvailable(Attribute attribute) throws Exception {
		int count = (Integer) this.sqlMapClient.queryForObject("checkEditNameAvaiOrNot", attribute);
		return count;
	}

	// public PageUtil<Attribute> getAttributesByName(Map map, int currentPage, final int pageSize,
	// int totalCount) throws Exception {
	// Page page = new Page();
	// page.setPageSize(pageSize);
	// page.setTotalRowsAmount(totalCount);
	// page.setCurrentPage(currentPage);
	// List<Attribute> list = this.findQueryPage("getAttributesByName",
	// map, page);
	// return new PageUtil<Attribute>(list, page);
	// }

	// public PageUtil<Attribute> getAttributesAll(int currentPage, final int pageSize, int totalCount)
	// throws Exception {
	// Page page = new Page();
	// page.setPageSize(pageSize);
	// page.setTotalRowsAmount(totalCount);
	// page.setCurrentPage(currentPage);
	// List<Attribute> list = this.findQueryPage("getAttributesAll",null,
	// page);
	// return new PageUtil<Attribute>(list, page);
	// }

	public int getAttributeByCode(String attributeCode) throws Exception {
		return (Integer) this.sqlMapClient.queryForObject("getAttributeByAttrCode", attributeCode);
	}

	public int getAllrAllCount() {
		return (Integer) this.sqlMapClient.queryForObject("getAttrAllCount");
	}

	public int getAttributeByNameCount(Map map) throws Exception {
		return (Integer) this.sqlMapClient.queryForObject("getAttributesByNameCount", map);
	}

	public Attribute getAttributeByBuberChoose(String attrCode) {
		return (Attribute) this.sqlMapClient.queryForObject("getAttributeIsBuyerChoose", attrCode);
	}
	
	 public List<Attribute> getAttributesByName(Map map) throws Exception {
	      return sqlMapClient.queryForList("getAttributesByName", map);
	 }

	@Override
	public List<Attribute> getAttributesAll(Map parMap) {
		return sqlMapClient.queryForList("getAttributesAll", parMap);
	}

	// public PageUtil<Attribute> getAttributesAll(int currentPage, final int pageSize, int totalCount)
	// throws Exception {
	// Page page = new Page();
	// page.setPageSize(pageSize);
	// page.setTotalRowsAmount(totalCount);
	// page.setCurrentPage(currentPage);
	// List<Attribute> list = this.findQueryPage("getAttributesAll",null,
	// page);
	// return new PageUtil<Attribute>(list, page);
	// }
	 
	 
}
