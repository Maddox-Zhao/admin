package com.huaixuan.network.biz.dao.storage;

import java.util.List;
import java.util.Map;

import com.huaixuan.network.biz.domain.storage.GatherInDepository;
import com.huaixuan.network.biz.domain.storage.GoodsForLocation;
import com.huaixuan.network.biz.domain.storage.InDetail;
import com.huaixuan.network.biz.domain.storage.InDetailBaseInfo;
import com.huaixuan.network.biz.domain.storage.InDetailGoods;
import com.huaixuan.network.biz.domain.storage.query.FinanceDepositoryQuery;
import com.huaixuan.network.biz.query.QueryPage;

/**
 * (bibleUtil auto code generation)
 * @version 3.2.0
 */
public interface InDetailDao {
    /* @interface model: InDetail */
    long addInDetail(InDetail inDetail) throws Exception;

    /* @interface model: InDetail */
    void editInDetail(InDetail inDetail) throws Exception;

    /* @interface model: InDetail */
    void removeInDetail(Long inDetailId) throws Exception;

    /* @interface model: InDetail,InDetail */
    InDetail getInDetail(Long inDetailId) throws Exception;

    /* @interface model: InDetail,InDetail */
    List<InDetail> getInDetails() throws Exception;

    /**
     * ������ⵥIDȡ�ö�Ӧ��Ʒ������Ϣ
     * @param id Long
     * @return List
     * @author chenyan 2009/07/22
     */
    List<InDetailGoods> getInDetailGoodsLists(Long id);

    /**
     * ������Ʒ�ɷ���Ŀ�λ
     * @param id Long
     * @param depFirstId Long
     * @return List
     * @author chenyan 2009/07/22 modified by chenyan 2010/03/16
     */
    List<GoodsForLocation> getLocationForGoods(Long id, Long depFirstId);

    /**
     * ��ѯ�ɷ���Ŀ�λ��������Ʒ��Ӧ��
     * @param id Long
     * @param depFirstId Long
     * @return List
     * @author chenyan 2009/07/22 modified by chenyan 2010/03/16
     */
    List<GoodsForLocation> getLocationForGoodsNoMatch(Long id, Long depFirstId);

    /**
     * ����ID���������ϸ���״̬
     * @param status String
     * @param id Long
     * @param depFirstId Long
     * @param storType String
     * @author chenyan 2009/07/23 modified by chenyan 2010/03/17
     */
    void updateStatusById(String status, Long id, Long depFirstId, String storType);

    /**
     * ������ⵥ����IDȡ��δ��ɷ���Ĳ�Ʒ��
     * @param inDepId Long
     * @param status String
     * @return List
     * @author chenyan 2009/07/23
     */
    List<InDetailGoods> listInDetailNotFinish(Long inDepId, String status);

    /**
     * ȡ���̵����ʱ��Ļ�����Ϣ
     * @param inDetailId Long
     * @return InDetailBaseInfo
     * @author chenyan 2009/07/24
     */
    InDetailBaseInfo getCheckInDetailBaseInfo(Long inDetailId);

    /**
     * ȡ�òɹ������˻������ʱ��Ļ�����Ϣ
     * @param inDetailId Long
     * @param type String
     * @return InDetailBaseInfo
     * @author chenyan 2009/07/24
     */
    InDetailBaseInfo getShoppingOrSalesInDetailBaseInfo(Long inDetailId, String type);

    /**
     * ����������Ʒ�ɷ���Ŀ�λ
     * @param id Long
     * @return List
     * @author chenyan 2009/07/25
     */
    List<GoodsForLocation> getSalesLocationForGoods(Long id);

    List<GoodsForLocation> getSalesLocationForGoodsChange(Long id);

    void addInDetails(List<InDetail> inDetails);

    /**
     * ��������굥IDȡ�÷���õĲ�Ʒ�б�
     * @param inDetailId Long
     * @return List
     * @author chenyan 2009/08/12
     */
    List<GoodsForLocation> listInDetailForDisByDetailId(Long inDetailId);

    /**
     * ��������굥IDȡ���ѽ�����λ�����Ķ�Ӧ��Ʒʵ������
     * @param inDetailId Long
     * @return int
     * @author chenyan 2009/08/13
     */
    int getGoodsLocationCountByInDetailId(Long inDetailId);

    /**
     * ���������õ�������ⵥ�б�����
     * @param parMap
     * @return
     */
    int gatherInDepositoryListsCount(Map parMap) throws Exception;

    /**
     * ���������õ�������ⵥ�б�
     * @param parMap
     * @return
     */
    QueryPage gatherInDepositoryLists(Map parMap, int currentPage, int pageSize, boolean isPage) throws Exception;

    void editInDetailGoodsInfo(InDetailGoods inDetailGoodsInfo) throws Exception;

    GatherInDepository gatherFinanceInDepositoryCount(FinanceDepositoryQuery financeDepositoryQuery) throws Exception;

    QueryPage gatherFinanceInDepositoryLists(FinanceDepositoryQuery financeDepositoryQuery, int currentPage, int pageSize)
                                                                                                  throws Exception;

    /**
     * ���������õ��ݹ���ⵥ�����б�����
     * @param parMap
     * @return
     */
    GatherInDepository estimateFinanceInDepositoryCount(FinanceDepositoryQuery financeDepositoryQuery)
                                                                                   throws Exception;

    /**
     * ���������õ��ݹ���ⵥ�����б�
     * @param parMap
     * @return
     */
    QueryPage estimateFinanceInDepositoryLists(FinanceDepositoryQuery financeDepositoryQuery, int currentPage, int pageSize)
                                                                                                    throws Exception;

    /**
     * ��������굥IDȡ��һ���ֿ�Ϳ�����ͣ��ɹ�������ͣ�
     * @param inDetailId Long
     * @return InDetailBaseInfo
     * @author chenyan 2010/03/17
     */
    InDetailBaseInfo getInfoByInDetailIdForShoppingList(Long inDetailId);

    /**
     * ��ѯ�ɷ���Ŀ�λ����Ʒ�⣩
     * @param id
     * @param depFirstId
     * @param locId
     * @return
     */
    List<GoodsForLocation> getLocationForDefect(Long id, Long locId);

    /**
     * ���������ȡ�ӱ��б�
     * @param InDepositoryId
     * @return
     */
    List<InDetail> getInDetailListByInDepositoryId(Long InDepositoryId);
}
