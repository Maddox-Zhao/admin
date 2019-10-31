package com.huaixuan.network.biz.dao.counter.ibatis;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.ibatis.SqlMapClientTemplate;
import org.springframework.stereotype.Service;

import com.huaixuan.network.biz.dao.counter.WithdrawSearchDao;
import com.huaixuan.network.biz.domain.Withdraw;

@Service("withdrawSearchDao")
public class WithdrawSearchDaoiBatis implements WithdrawSearchDao {
    @Autowired
    private SqlMapClientTemplate sqlMapClientTemplate;

    //    public PageUtil<Withdraw> getWithdrawPage(int currentPage, int pageSize, int totalCount) {
    //        Page page = new Page();
    //        page.setPageSize(pageSize);
    //        page.setTotalRowsAmount(totalCount);
    //        page.setCurrentPage(currentPage);
    //
    //        List<Withdraw> list = this.getSqlMapClientTemplate().queryForList("getWithdrawSearchs", "",
    //            page.getPageStartRow() - 1, page.getPageSize());
    //        return new PageUtil<Withdraw>(list, page);
    //    }

    public Integer getWithdrawSearchCount() {
        return (Integer) this.sqlMapClientTemplate.queryForObject("getWithdrawSearchCount");
    }

    //    @SuppressWarnings("unchecked")
    //    public PageUtil<Withdraw> getWithdrawSearchPage(Map searchWithdraw, Page page) {
    //        List<Withdraw> list = this.findQueryPage("search-withdraw-query", "search-withdraw-count",
    //            searchWithdraw, page);
    //        return new PageUtil(list, page);
    //    }

    @SuppressWarnings("unchecked")
    public Long getWithdrawSearchSumTransAmount(Map searchWithdraw) {
        return (Long) this.sqlMapClientTemplate.queryForObject(
            "search-withdraw-query-sumTransAmount", searchWithdraw);
    }

    @SuppressWarnings("unchecked")
    public void subWithdraw(List<Long> ids) {
        Map param = new HashMap();
        param.put("ids", ids);
        this.sqlMapClientTemplate.update("subWithdraw", param);
    }

    public void FullWithdraw(Withdraw withdraw) {
        this.sqlMapClientTemplate.update("FullWithdraw", withdraw);
    }

    public void WrongWithdraw(Withdraw withdraw) {
        this.sqlMapClientTemplate.update("WrongWithdraw", withdraw);
    }

    /**
     * @param accountNo
     * @return
     * @see com.hundsun.bible.dao.counter.WithdrawSearchDao#getMoneyRequiredWithdrawByAccountNo(long)
     */
    public List<Withdraw> getMoneyRequiredWithdrawByAccountNo(String accountNo) {
        return this.sqlMapClientTemplate.queryForList(
            "withdraw-searchMoneyRequiredWithdrawsByAccountNo-query", accountNo);
    }

}
