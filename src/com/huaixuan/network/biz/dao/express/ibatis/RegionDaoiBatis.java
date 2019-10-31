package com.huaixuan.network.biz.dao.express.ibatis;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.ibatis.SqlMapClientTemplate;
import org.springframework.stereotype.Repository;

import com.huaixuan.network.biz.dao.express.RegionDao;
import com.huaixuan.network.biz.domain.express.Region;

/**
 * µØÇø(bibleUtil auto code generation)
 *
 * @version 3.2.0
 */
@Repository("regionDao")
public class RegionDaoiBatis implements RegionDao {
	@Autowired
	private SqlMapClientTemplate sqlMapClient;

	@Override
	public void addRegion(Region region) {
		this.sqlMapClient.insert("addRegion", region);
	}

	@Override
	public void editRegion(Region region) {
		this.sqlMapClient.update("editRegion", region);
	}

	@Override
	public void removeRegion(Long regionId) {
		this.sqlMapClient.delete("removeRegion", regionId);
	}

	@Override
	public Region getRegion(Long regionId) {
		return (Region) this.sqlMapClient.queryForObject("getRegion", regionId);
	}

	@Override
	public List<Region> getRegions() {
		return this.sqlMapClient.queryForList("getRegions", null);
	}

	@Override
	public List<Region> getRegionByParams(Region region) {
		return this.sqlMapClient.queryForList("getRegionByParams", region);
	}

	@Override
	public Region getRegionByCode(String code) {
		return (Region) this.sqlMapClient.queryForObject("getRegionByCode",
				code);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<String> listRegionCodeByParentCode(String parentCode) {
		return this.sqlMapClient.queryForList("listRegionCodeByParentCode",
				parentCode);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<String> listTwoRegionCodeByParentCode(String parentCode) {
		return this.sqlMapClient.queryForList("listTwoRegionCodeByParentCode",
				parentCode);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Region> getRegion(Region region) {
		return this.sqlMapClient.queryForList("getRegionByParams", region);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Region> getRegionByName(Map region) {
		return this.sqlMapClient.queryForList("getRegionByName", region);
	}

	@Override
	public Region getRegionByName(String regionName) {
		return (Region) this.sqlMapClient.queryForObject("getRegionByName",
				regionName);
	}

	@Override
	public Region getRegionByNameAndType(String regionName, Integer regionType) {
		Map par = new HashMap();
		par.put("regionName", regionName);
		par.put("regionType", regionType);
		return (Region) this.sqlMapClient.queryForObject(
				"getRegionByNameAndType", par);
	}

	@Override
	public Region getRegionByNameAndTypeAndCode(String regionName,
			Integer regionType, String parentCode) {
		Map par = new HashMap();
		par.put("regionName", regionName);
		par.put("regionType", regionType);
		par.put("parentCode", parentCode);
		return (Region) this.sqlMapClient.queryForObject(
				"getRegionByNameAndTypeAndCode", par);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Region> getRegionByFirstDepositoryId(String depositoryId) {
		return this.sqlMapClient.queryForList("getRegionByFirstDepositoryId",
				depositoryId);
	}

	@Override
	public List<Region> getSpecialRegion(Map parMap) {
		return this.sqlMapClient.queryForList("getSpecialRegion", parMap);
	}

}
