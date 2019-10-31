package com.huaixuan.network.biz.service.user.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.huaixuan.network.biz.dao.admin.AdminDao;
import com.huaixuan.network.biz.dao.express.RegionDao;
import com.huaixuan.network.biz.dao.storage.DepositoryFirstDao;
import com.huaixuan.network.biz.dao.user.UserAddressDao;
import com.huaixuan.network.biz.dao.user.UserAgentDao;
import com.huaixuan.network.biz.dao.user.UserInfoDao;
import com.huaixuan.network.biz.dao.user.UserSalesDao;
import com.huaixuan.network.biz.domain.admin.Admin;
import com.huaixuan.network.biz.domain.crm.query.CrmRankQuery;
import com.huaixuan.network.biz.domain.express.Region;
import com.huaixuan.network.biz.domain.storage.DepositoryFirst;
import com.huaixuan.network.biz.domain.trade.Trade;
import com.huaixuan.network.biz.domain.user.AgentInfo;
import com.huaixuan.network.biz.domain.user.UserAddress;
import com.huaixuan.network.biz.domain.user.UserInfo;
import com.huaixuan.network.biz.domain.user.UserSales;
import com.huaixuan.network.biz.query.QueryPage;
import com.huaixuan.network.biz.service.user.UserSalesManager;
import com.hundsun.network.melody.common.util.StringUtil;

@Service("userSalesManager")
public class UserSalesManagerImpl implements UserSalesManager {
    protected Log              log = LogFactory.getLog(this.getClass());
    @Autowired
    private UserSalesDao       userSalesDao;
    @Autowired
    private UserAddressDao     userAddressDao;
    @Autowired
    private RegionDao          regionDao;
    @Autowired
    private UserInfoDao        userInfoDao;
    @Autowired
    private UserAgentDao       userAgentDao;
    @Autowired
    private AdminDao           adminDao;
    @Autowired
    private DepositoryFirstDao depositoryFirstDao;

    @Override
    public double getUserRefundInfo(Map parMap) {
        return this.userSalesDao.getUserRefundInfo(parMap);
    }

    @Override
    public UserSales getUserSalesInfo(Map parMap) {
        return this.userSalesDao.getUserSalesInfo(parMap);
    }

    @Override
    public int getUserSalesCountByParameterMap(Map parMap) {
        return this.userSalesDao.getUserSalesCountByParameterMap(parMap);
    }

    @Override
    public QueryPage getUserSalesListsByParameterMap(CrmRankQuery crmCustomerRankQuery,
                                                     int currentPage, int pageSize) {

        QueryPage userSalesList = userSalesDao.getUserSalesListsByParameterMap(
            crmCustomerRankQuery, currentPage, pageSize);
        if (userSalesList != null && userSalesList.getItems() != null && userSalesList.getItems().size() > 0) {
            for (UserSales userSales : (List<UserSales>) userSalesList.getItems()) {
                Map newMap = new HashMap();
                newMap.put("userId", userSales.getUserId());
                List<UserAddress> userAddressList = userAddressDao
                    .getUserAddresssByParameterMap(newMap);
                for (UserAddress userAddress : userAddressList) {
                    if (userAddress.getProvince() != null && userAddress.getCity() != null
                        && userAddress.getProvince() != "ÇëÑ¡Ôñ" && userAddress.getCity() != "ÇëÑ¡Ôñ") {
                        try {
                            Region regionP = this.regionDao.getRegionByCode(userAddress
                                .getProvince());
                            Region regionC = this.regionDao.getRegionByCode(userAddress.getCity());
                            userSales.setArea(regionP.getRegionName() + regionC.getRegionName());
                        } catch (Exception e) {
                            log.error(e.getMessage());
                        }
                    }
                }
                try {
                    UserInfo userInfo = this.userInfoDao.getUserInfoByUserId(userSales.getUserId());
                    if (userInfo.getHomePhone() != null || userInfo.getMobilePhone() != null) {
                        userSales
                            .setPhone(StringUtil.isNotBlank(userInfo.getHomePhone()) ? userInfo
                                .getHomePhone() : userInfo.getMobilePhone());
                    }
                } catch (Exception e) {
                    log.error(e.getMessage());
                }
                AgentInfo agentInfo = userAgentDao.getAgentInfoByUserId(userSales.getUserId());
                if (agentInfo != null
                    && (agentInfo.getTaobaoPrestige() != null || agentInfo.getYouaPrestige() != null)) {
                    userSales
                        .setShopTYRank(StringUtil.isNotBlank(agentInfo.getTaobaoPrestige()) ? agentInfo
                            .getTaobaoPrestige()
                            : agentInfo.getYouaPrestige());
                } else if (agentInfo != null
                           && (agentInfo.getPaipaiPrestige() != null || agentInfo.getEbayPrestige() != null)) {
                    userSales
                        .setShopPYRank(StringUtil.isNotBlank(agentInfo.getPaipaiPrestige()) ? agentInfo
                            .getPaipaiPrestige()
                            : agentInfo.getEbayPrestige());
                }
            }
        }
        return userSalesList;
    }

    @Override
    public int getSalesManCountByParameterMap(Map parMap) {
        return this.userSalesDao.getSalesManCountByParameterMap(parMap);
    }

    @Override
    public QueryPage getSalesManListsByParameterMap(CrmRankQuery crmSalesmanRankQuery,
                                                    int currentPage, int pageSize) {
        QueryPage salesManList = userSalesDao.getSalesManListsByParameterMap(crmSalesmanRankQuery,
            currentPage, pageSize);
        if (salesManList != null && salesManList.getItems() != null && salesManList.getItems().size() > 0) {
            for (UserSales userSales : (List<UserSales>) salesManList.getItems()) {
                Admin admin = adminDao.getAdminById(userSales.getAdminId());
                if (admin != null && (admin.getTel() != null || admin.getMobile() != null)) {
                    userSales.setPhone(StringUtil.isNotBlank(admin.getTel()) ? admin.getTel()
                        : admin.getMobile());
                }
            }
        }
        return salesManList;
    }

    @Override
    public QueryPage getUserTradeDetail(Map parMap, int currentPage, int pageSize) {
        QueryPage tradelist = this.userSalesDao.getUserTradeDetail(parMap, currentPage, pageSize);
        if (tradelist != null && tradelist.getItems() != null && tradelist.getItems().size() > 0) {
            for (Trade trade : (List<Trade>) tradelist.getItems()) {
                DepositoryFirst depositoryFirst = depositoryFirstDao.getDepositoryById(trade
                    .getDepFirstId());
                if (depositoryFirst != null && depositoryFirst.getDepFirstName() != null) {
                    trade.setDepFirstName(depositoryFirst.getDepFirstName());
                }
            }
        }
        return tradelist;
    }

    @Override
    public int getUserTradeDetailCount(Map parMap) {
        return this.userSalesDao.getUserTradeDetailCount(parMap);
    }

    @Override
    public void deleteUserSalesByParameterMap(Map parMap) {
        this.userSalesDao.deleteUserSalesByParameterMap(parMap);
    }

    @Override
    public int getSearchDayByMap(Map parMap) {
        return this.userSalesDao.getSearchDayByMap(parMap);
    }

    @Override
    public void addUserSales(UserSales userSales) {
        this.userSalesDao.addUserSales(userSales);
    }
}
