package com.huaixuan.network.biz.service.goods;

import java.util.List;
import java.util.Map;

import com.huaixuan.network.biz.domain.ShoppingCart;
import com.huaixuan.network.biz.domain.goods.PromContext;
import com.huaixuan.network.biz.domain.goods.Promation;
import com.huaixuan.network.biz.domain.goods.PromationFullGive;
import com.huaixuan.network.biz.domain.goods.PromationGive;
import com.huaixuan.network.biz.domain.goods.PromationVO;
import com.huaixuan.network.biz.query.PromationQuery;
import com.huaixuan.network.biz.query.QueryPage;


 public interface PromationManager {
	 	/* @interface model: Promation */
	 	public void addPromation(Promation promation);

	 	/* @interface model: Promation */
	 	public void editPromation(Promation promation);

	 	/* @interface model: Promation */
	 	public void removePromation(Long promationId);

	 	/* @interface model: Promation,Promation */
	 	public Promation getPromation(Long promationId);
	 	
	 	/**
	     * �����ײ����Ͳ�ѯ��Ч���ײͼ�¼
	     * @param context
	     * @return
	     */
	 	public Promation getPromationByModeCode(String modeCode);

	 	/**
	     * �����ײ����Ʒ��ظ��ײ�
	     * @param context
	     * @return
	     */
	 	public List<Promation> getPromationByName(String name);

	    /* @interface model: Promation,Promation */
	 	public List<Promation> getPromations();

	 	/**
	 	 * ������ƷID���Ż������ģ���ȡ��Ʒ�������ܵ��Ż��б�
	 	 * ʹ�÷�Χ����Ʒ���顢�ײ�����ҳ�桢���ﳵ�б�
	 	 * @param context
	 	 * @return
	 	 */
	    public List<PromationVO> getPromationsByContext(PromContext context);
	    
	    /**
	     * �ж���Ʒ�Ƿ�����Ͱ����ײ��е���Ʒ
	     * @param context
	     * @return
	     */
	    public boolean isExemptPostageGoods(PromContext context);
	    
	    /**
	     * ���һ����Ʒ�Ĵ��۴����ۿ�
	     * @param context
	     * @return
	     */
	    public double getGoodsDiscount(Long goodsId);

	    /**
	     * �����Ż�DO���Ż������Ľ�����������
	     * @param promation
	     * @param promContext
	     * @return
	     */
	    public boolean addPromation(Promation promation,PromContext promContext);

	    /**
	     * �����Ż�ID,��ȡ�Ż����ݸ��Żݹ��򣬹������Ż������Ľ��з��ء�
	     * @param promationId
	     * @returnd
	     */
	    public Map getPromContextByPromation(Long promationId);

	    /**
	     * �����Ż�ID�б�,��ȡ�Ż����ݸ��Żݹ����б��������Ż������Ľ��з��ء�
	     * @param promationId List
	     * @returnd
	     */
	    public List<Map> getPromationMapListByIds(List promationIdList);


	    public List<Promation> getPromationsByIds(List ids);

	    /**
	     * �����Ż�DO���Ż������Ľ��������޸�
	     * @param promation
	     * @param promContext
	     * @return
	     */
	    public boolean editPromation(Promation promation,PromContext promContext);


	    /**
	     * �����Ż�ID�����Żݵ������ģ���ȡ�������ܵ��Ż���Ϣ
	     *
	     * @param promationId
	     * @param promContext
	     * @return
	     */
	    public PromationVO getPromationInfo(Long promationId,PromContext promContext);
	    /**
	     * ���ݷ�ҳ�������Ϣ��ѯPromationList
	     * ʹ�÷�Χ��Promation��ѯ
	     * @param context
	     * @return
	     */
//	    public List<Promation> getPromationsByPage(Map<String, Object> conditions,Page page);

	    /**
	     * �������ͻ��������ͣ������ײ�
	     * @param scope
	     * @return
	     */
	    public List<PromationVO> getGivePromationList(String scope);

	    /**
	     * ��ʱ���Զ�ȡ������
	     *
	     * @param promationId
	     * @param promContext
	     * @return
	     */
	    public void autoCanelFreeze();

	    /**
	     * ��ȡһ����Ʒ��ȫ��������Ĳ�Ʒ
	     * @author zhangwy
	     * @param goodsId
	     * @return
	     */
	    public List<PromationGive> getNewPromationGiveList(Long goodsId);
	    
	    /**
	     * ��ȡ���ﳵ�������������ײ͵����в�Ʒ
	     * @author fanyj
	     * @param shoppingCartsList
	     * @return
	     */
	    public List<PromationGive> getFullGiveGoodsList(List<ShoppingCart> shoppingCartsList);
	    
	    /**
	     * ��ȡ�������ײͼ�¼����
	     * @author fanyj
	     * @return
	     */
	    public Map<String,List<PromationFullGive>> getPromationFullGiveList();
	    
	    /**
	     * �ײ��б�
	     * @param promation
	     * @param currPage
	     * @param pageSize
	     * @return
	     */
	    public QueryPage getPromationListByConditionWithPage(PromationQuery promation ,int currPage, int pageSize);
 }
