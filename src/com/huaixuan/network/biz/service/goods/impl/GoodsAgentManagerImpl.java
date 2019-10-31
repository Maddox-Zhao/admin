package com.huaixuan.network.biz.service.goods.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.huaixuan.network.biz.dao.goods.GoodsAgentDao;
import com.huaixuan.network.biz.dao.goods.GoodsDao;
import com.huaixuan.network.biz.domain.goods.GoodsAgent;
import com.huaixuan.network.biz.service.admin.ResourcesManager;
import com.huaixuan.network.biz.service.goods.GoodsAgentManager;

@Service("goodsAgentManager")
public class GoodsAgentManagerImpl implements GoodsAgentManager {

	protected Log  log = LogFactory.getLog(this.getClass());
	
	@Autowired
	private GoodsAgentDao goodsAgentDao;
	
	@Autowired
	private ResourcesManager resourcesManager;

	private Date date=new Date();

	public boolean insertGoodsAgent(GoodsAgent agent) {
		if(null==agent)
			return false;
		else if(agent.getUserId()!=null && agent.getGoodsId()!=null){
			Long userId=agent.getUserId();
			Long goodsId=agent.getGoodsId();
			Map<String,Object> paramMap=new HashMap<String,Object>();
			paramMap.put("userId", userId.toString());
			paramMap.put("goodsId", goodsId.toString());

			GoodsAgent goodsAgent=this.getGoodsAgent(paramMap);
			//记录存在，状态为N的，改变状态
			if(null!=goodsAgent ){
				Map<String,Object> map=new HashMap<String,Object>();
                map.put("userId", userId.toString());
                map.put("goodsId", goodsId.toString());
				map.put("status", "y");
				this.goodsAgentDao.updateAgentStatus(map);
			}else{
				this.goodsAgentDao.insertGoodsAgent(agent);
			}
		}
		return true;
	}

	public int countGoodsAgentByUserId(String userId){
		Map<String,Object> paramMap=new HashMap<String,Object>();
		paramMap.put("userId", userId);
    	paramMap.put("status",  "y");
		return this.goodsAgentDao.countGoodsAgentByUserId(paramMap);
	}

	public int countGoodsAgentByMap(Map<String,Object> paramMap){
		return this.goodsAgentDao.countGoodsAgentByUserId(paramMap);
	}
	public boolean updateBatchGoodsAgent(List<String> goodsIds,Long userId){
		if(null==goodsIds)
			return false;
		else{
			try{
				List<Map> GoodsAgentUpdateList=new ArrayList<Map>();
				for(String ids : goodsIds){//将需要更新与新增的分开
					Map<String,Object> map=new HashMap<String,Object>();
		            map.put("userId", userId.toString());
		            map.put("goodsId", ids);
					map.put("status", "n");
					GoodsAgentUpdateList.add(map);
				}
				goodsAgentDao.updateBatchGoodsAgent(GoodsAgentUpdateList);
				return true;
			}catch(Exception e){
				this.log.error(e);
				return false;
			}
		}
	}

	public boolean insertBatchGoodsAgent(List<String> goodsIds,Long userId,Long agentCount){
		if(null==goodsIds)
			return false;
		else{
			try{
				List<GoodsAgent> GoodsAgentList=new ArrayList<GoodsAgent>();
				List<Map> GoodsAgentUpdateList=new ArrayList<Map>();
				for(String ids : goodsIds){//将需要更新与新增的分开
					Map<String,Object> paramMap=new HashMap<String,Object>();
					paramMap.put("userId", userId.toString());
					paramMap.put("goodsId", ids);
					GoodsAgent goodsAgentTmp=this.getGoodsAgent(paramMap);
					if(null==goodsAgentTmp){
						GoodsAgent goodsAgent=new GoodsAgent();
						goodsAgent.setGoodsId(Long.parseLong(ids));
						goodsAgent.setUserId(userId);
						goodsAgent.setStatus("y");
						GoodsAgentList.add(goodsAgent);
					}else{
						Map<String,Object> map=new HashMap<String,Object>();
						if(goodsAgentTmp.getStatus().equalsIgnoreCase("n")){
			                map.put("userId", userId.toString());
			                map.put("goodsId", ids);
							map.put("status", "y");
							GoodsAgentUpdateList.add(map);
						}
					}
				}
				int count=this.countGoodsAgentByUserId(userId.toString());//用户商品关联统计数量
		        //TODO int userCount=this.resourcesManager.getResourcesByType(type);
				if((count + GoodsAgentList.size()+GoodsAgentUpdateList.size()) > agentCount){
					return false;
				}
				this.goodsAgentDao.insertBatchGoodsAgent(GoodsAgentList, GoodsAgentUpdateList);
				return true;
			}catch(Exception e){
				this.log.error(e);
				return false;
			}
		}
	}

	public int getGoodsAgentCountByParameterMap(Map<String,String> paramMap){
		return goodsAgentDao.getGoodsAgentCountByParameterMap(paramMap);
	}

	public GoodsAgent getGoodsAgent(Map<String, Object> parMap) {
		return this.goodsAgentDao.getGoodsAgent(parMap);
	}
	public void updateAgentStatus(Map<String, Object> parMap) {
		this.goodsAgentDao.updateAgentStatus(parMap);
	}

	public boolean insertaUserGoodsAgentList(List<String> ids, Long userId) {
		boolean flag=false;
		for(String goodsId : ids){
			GoodsAgent goodsAgent=new GoodsAgent();
			goodsAgent.setGoodsId(Long.parseLong(goodsId));
			goodsAgent.setUserId(userId);
			goodsAgent.setStatus("y");
			flag=insertGoodsAgent(goodsAgent);
			if(!flag){
				return flag;
			}
		}
		return flag;
	}
	public List<GoodsAgent> getGoodsAgentList(Map<String, Object> parMap) {
		return goodsAgentDao.getGoodsAgentList(parMap);
	}

//	public List<GoodsAgent> getErrorGoodsAgentByParameterMap(
//			Map<String, String> parMap, Page page) {
//		return goodsAgentDao.getErrorGoodsAgentByParameterMap(parMap, page);
//	}
//
//	public List<GoodsAgent> getGoodsAgentByParameterMap(
//			Map<String, String> parMap, Page page) {
//		return goodsAgentDao.getGoodsAgentByParameterMap(parMap, page);
//	}

	public int getErrorGoodsAgentCountByParameterMap(
			Map<String, String> paramMap) {
		return goodsAgentDao.getErrorGoodsAgentCountByParameterMap(paramMap);
	}

	public List<Long> getPastUserIdForAddGoodsRelation(){
	    log.info("GoodsAgentManagerImpl.getPastUserIdForAddGoodsRelation method");
        try {
            return this.goodsAgentDao.getPastUserIdForAddGoodsRelation();
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return null;
	}

    public List<Long> getCurrUserIdForAddGoodsRelation(Long userId){
        log.info("GoodsAgentManagerImpl.getCurrUserIdForAddGoodsRelation method");
        try {
            return this.goodsAgentDao.getCurrUserIdForAddGoodsRelation(userId);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return null;
    }

    public int editStatusByUserIdGoodsId(GoodsAgent goodsAgent){
        log.info("GoodsAgentManagerImpl.editStatusByUserIdGoodsId method");
        try {
            return this.goodsAgentDao.editStatusByUserIdGoodsId(goodsAgent);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return 0;
    }

    public void insertBatchAgentUserForGoodsId(Long goodsId){
        log.info("GoodsAgentManagerImpl.insertBatchAgentUserForGoodsId method");
        try {
            this.goodsAgentDao.insertBatchAgentUserForGoodsId(goodsId);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }

}
