package com.huaixuan.network.biz.dao.counter;

import java.util.List;
import java.util.Map;

import com.huaixuan.network.biz.domain.Withdraw;

public interface WithdrawSearchDao {

    //    public PageUtil<Withdraw> getWithdrawPage(int currentPage, int pageSize, int totalCount)
    //                                                                                            throws Exception;

    public Integer getWithdrawSearchCount();

    //    PageUtil<Withdraw> getWithdrawSearchPage(Map searchWithdraw, Page page);

    public Long getWithdrawSearchSumTransAmount(Map searchWithdraw);

    public void subWithdraw(List<Long> ids);

    public void FullWithdraw(Withdraw withdraw);

    public void WrongWithdraw(Withdraw withdraw);

    /**
     * @param accountNo
     * @return
     */
    public List<Withdraw> getMoneyRequiredWithdrawByAccountNo(String accountNo);
}
