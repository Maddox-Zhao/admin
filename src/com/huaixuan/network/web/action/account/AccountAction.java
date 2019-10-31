package com.huaixuan.network.web.action.account;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.huaixuan.network.biz.domain.account.Account;
import com.huaixuan.network.biz.domain.account.query.SearchConditionQuery;
import com.huaixuan.network.biz.domain.admin.AdminAgent;
import com.huaixuan.network.biz.domain.trade.PayPackage;
import com.huaixuan.network.biz.enums.EnumAdminPermission;
import com.huaixuan.network.biz.query.QueryPage;
import com.huaixuan.network.biz.service.account.AccountManager;
import com.huaixuan.network.biz.service.trade.PayPackageManager;
import com.huaixuan.network.web.action.BaseAction;
import com.huaixuan.network.web.action.interceptor.AdminAccess;
import com.hundsun.network.melody.common.util.StringUtil;

/**
 * @author guoyj
 */
@Controller
@RequestMapping(value = "/account")
public class AccountAction extends BaseAction {

    // /**
    // *
    // */
    private static final long  serialVersionUID = 281596586037074948L;
    //
    public final static String accountIneerType = "3";                // 账户类型,内部账户
    @Autowired
    private AccountManager     accountManager;
    @Autowired
    private PayPackageManager  payPackageManager;
    private static final int   PAGE_SIZE        = 10;

    //
    // private Account account;
    // private Map<String, String> searchCondition;
    //
    // /** 帐户查询结果列表 **/
    // private List<Account> searcheList;
    //
    // /** 操作后的结果提示符号 **/
    // private String processCode;
    //
    // private FreezeBalanceReq balanceReq;
    //
    // private String balanceAmount;
    //
    // /** 账户金额变动流水结果集 **/
    // private List<Map> searchAccountLogsList;
    //
    // /** 保证金变动流水结果集 **/
    // private List<Map> searchBalanceLogsList;
    //
    // private Long securityAmount; // 保证金
    //
    // private String resultString;
    //
    // /**
    // * 内部账户的list页面
    // *
    // * @return "success" if no exceptions thrown
    // */
    // public String showInnerAccountList() throws Exception {
    // // 获取list分页数据
    // accountList = accountManager.getAccountPage(this.currentPage,
    // this.pageSize);
    // if (null != accountList) {
    // page = accountList.getPage();
    // }
    // return SUCCESS;
    // }
    //
    /**
     * 新增 内部账户 初始页面
     *
     * @return "success" if no exceptions thrown
     */
    @AdminAccess({EnumAdminPermission.A_INNER_ACCOUNT_ADD})
    @RequestMapping(value = "addInnerAccountPage")
    public String addInnerAccountPage(@ModelAttribute("account") Account account) throws Exception {

        return "/account/addInnerAccount";
    }

    /**
     * 添加内部账户
     *
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "addInnerAccount")
    public String addInnerAccount(@ModelAttribute("account") Account account, Model model,
                                  AdminAgent adminAgent) throws Exception {

        Map<String, String> map = new HashMap<String, String>();
        map.put("ACCOUNT_TYPE", accountIneerType);
        map.put("ACCOUNT_SUB_TYPE", account.getAccountSubType());

        if (!accountManager.checkAddSubAccount(map)) // 如果没有添加
            if (accountManager.addInnerWebAccount(accountIneerType, account.getAccountSubType(),
                adminAgent.getUsername(), account.getMemo().trim())) // 添加内部账户,成功的话
            {
                model.addAttribute("addInnerAccountSuccessInfo", "添加成功");
                return "/account/addInnerAccount";
            } else // 添加内部账户，失败的话
            {
                model.addAttribute("addInnerAccountErrorInfo", "添加失败");
                return "/account/addInnerAccount";
            }
        else // 如果添加了
        {
            model.addAttribute("accountInfoError", "你已经添加过该子类型账户");
            return "/account/addInnerAccount";
        }

    }

    /**
     * 根据搜索条件取得客户帐户
     *
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/doAccountsListByCondition")
    public String doAccountsListByCondition(@ModelAttribute("searchConditionQuery") SearchConditionQuery searchConditionQuery,
                                            Model model,
                                            @RequestParam(value = "page", required = false, defaultValue = "1") int currPage)
                                                                                                                             throws Exception {

        // String accountType = "1,2";
        // 判断是否具有结算人员的权限
        // String[] counterPerm = new String[] { "ROLE_ADMIN",
        // "PERM_COUNTER_COMMON",
        // "PERM_COUNTER_COMPARE", "PERM_COUNTER_RECOVER",
        // "PERM_COUNTER_ADVANCED" };
        // if (hasAnyAdminPermission(counterPerm)) {
        // accountType += ",3";
        // }
        // searchCondition.put("accountType", accountType);
        Map<String, String> searchMap = new HashMap<String, String>();
        searchMap.put("accountNo", searchConditionQuery.getAccountNo());
        searchMap.put("account", searchConditionQuery.getAccount());
        searchMap.put("createTimeStart", searchConditionQuery.getCreateTimeStart());
        searchMap.put("createTimeEnd", searchConditionQuery.getCreateTimeEnd());
        searchMap.put("accountBalance", searchConditionQuery.getAccountBalance());
        searchMap.put("enabledStatus", searchConditionQuery.getEnabledStatus());
        searchMap.put("accountTypeSearch", searchConditionQuery.getAccountTypeSearch());
        QueryPage query = accountManager.getAccountsByCondition(searchMap, currPage, PAGE_SIZE);
        if (query != null) {
            model.addAttribute("query", query);
        }

        return "/account/showAccountsList";
    }

    // /**
    // * 对帐户进行冻结/解冻之类的操作
    // *
    // * @return
    // * @throws Exception
    // */
    // public String doProcessAccount() {
    // if (account == null || account.getAccountNo() == null
    // || account.getAccountNo().equals("")) {
    // processCode = EnumProcessCode.PROCESS_ERROR.getCode();
    // } else {
    // // 如果account满足更新条件
    // try {
    // String loginId = getLoginAdminUser().getUsername() + "";
    // account.setModifier(loginId);
    // accountManager.doProcessAccount(account);
    // processCode = EnumProcessCode.PROCESS_OK.getCode();
    // // if (account.getEnabledStatus().equals("T"))
    // // addWorkLog("对帐户进行解冻操作", account.getAccountNo(),
    // // EnumWorkLogType.Account
    // // .getKey(), "解冻操作成功");
    // // else
    // // addWorkLog("对帐户进行冻结操作", account.getAccountNo(),
    // // EnumWorkLogType.Account
    // // .getKey(), "冻结操作成功");
    // getAccountDetail();
    //
    // } catch (Exception ex) {
    // processCode = EnumProcessCode.PROCESS_ERROR.getCode();
    // }
    // }
    // return SUCCESS;
    // }

    /**
     * 根据accountNo获得account
     *
     * @return
     */
    @RequestMapping(value = "/getAccountDetail")
    public String getAccountDetail(Model model, HttpServletRequest request) {
        try {
            String accountNo = request.getParameter("account.accountNo");
            if (StringUtil.isBlank(accountNo)) {
                return "/account/account_error";
            }
            Account account = accountManager.getAccountByAccountNo(accountNo);
            // Money securityMoney =
            // accountManager.getSecurityBalance(account.getAccountNo());
            // securityAmount = securityMoney.getCent(); //得到保证金
            model.addAttribute("account", account);
        } catch (Exception ex) {
            return "/account/account_error";
        }
        return "/account/accountDetail";
    }

    // /**
    // * 根据页面输入的金额对帐户进行冻结操作
    // *
    // * @return
    // */
    // public String doFreezeBalanceReq() {
    // if (balanceReq == null) {
    // balanceReq = new FreezeBalanceReq();
    // }
    // balanceReq
    // .setSubTransCode(EnumSubTransCode.TXCODE_ACCOUNT_ADMIN_FREEZE);
    // doBalanceReq();
    // // addWorkLog("根据页面输入的金额对帐户进行冻结操作", balanceReq.getAccountNo(),
    // // EnumWorkLogType.Account
    // // .getKey(), "冻结操作成功");
    // return getAccountDetail();
    // }
    //
    // /**
    // * 根据页面输入的金额对帐户进行解冻操作
    // *
    // * @return
    // */
    // public String doUnFreezeBalanceReq() {
    // if (balanceReq == null) {
    // balanceReq = new FreezeBalanceReq();
    // }
    // balanceReq
    // .setSubTransCode(EnumSubTransCode.TXCODE_ACCOUNT_ADMIN_UNFREEZE);
    // balanceReq.setPositive(false);
    // doBalanceReq();
    // // addWorkLog("根据页面输入的金额对帐户进行解冻操作", balanceReq.getAccountNo(),
    // // EnumWorkLogType.Account
    // // .getKey(), "解冻操作成功");
    // return getAccountDetail();
    // }

    /**
     * 根据帐户号查看账户金额变动流水
     *
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/doAccountLogsListByAccountNo")
    public String doAccountLogsListByAccountNo(Model model,
                                               @RequestParam(value = "page", required = false, defaultValue = "1") int currPage,
                                               HttpServletRequest request) throws Exception {
        String accountNo = request.getParameter("account.accountNo");
        Map<String, String> searchMap = new HashMap<String, String>();
        searchMap.put("accountNo", accountNo);
        QueryPage accountLogsListPage = accountManager.getAccountLogsByAccountNo(searchMap,
            currPage, PAGE_SIZE);
        if (accountLogsListPage != null) {
            List<Map> searchAccountLogsList = (List<Map>) accountLogsListPage.getItems();
            // modify by fanyj start 2010-12-16 合并付款功能修改
            if (searchAccountLogsList != null && searchAccountLogsList.size() > 0) {

                List<PayPackage> payPackageList = null;
                for (Map map : searchAccountLogsList) {
                    String orderNo = "";
                    String pid = (String) map.get("ORDER_NO");
                    if (StringUtil.isBlank(pid) || !StringUtil.isNumeric(pid)) {
                        continue;
                    }
                    payPackageList = payPackageManager.getPayPackagesByPId(Long.parseLong(pid));
                    if (payPackageList != null && payPackageList.size() > 0) {
                        for (PayPackage obj : payPackageList) {
                            orderNo += obj.getTid() + ",";
                        }
                        map.put("ORDER_NO", orderNo.substring(0, orderNo.length() - 1));
                    }
                }
            }
            model.addAttribute("searchAccountLogsList", searchAccountLogsList);
            model.addAttribute("accountLogsListPage", accountLogsListPage);
            model.addAttribute("accountNo", accountNo);
            // modify by fanyj end 2010-12-16
        }
        return "/account/accountLogsList";
    }

    // /**
    // * 根据帐户号查看账户资金冻结变动流水
    // *
    // * @return
    // * @throws Exception
    // */
    // public String doBalanceLogsListByAccountNo() throws Exception {
    // if (page == null) {
    // page = new Page();
    // }
    // page.setPageSize(pageSize);
    // page.setCurrentPage(currentPage);
    // Map<String, String> searchMap = new HashMap<String, String>();
    // searchMap.put("accountNo", account.getAccountNo());
    // PageUtil<Map> balancetLogsListPage = accountManager
    // .getBalanceLogsListByAccountNo(searchMap, page);
    // if (balancetLogsListPage != null) {
    // searchBalanceLogsList = balancetLogsListPage.getItems();
    // page = balancetLogsListPage.getPage();
    // }
    // return SUCCESS;
    // }
    //
    // /**
    // * 根据页面输入的金额对帐户进行冻结/解冻的操作
    // *
    // * @return
    // */
    // private void doBalanceReq() {
    // balanceReq.setAmount(new Money(balanceAmount));
    // balanceReq.setAccountNo(account.getAccountNo());
    // balanceReq.setOperator(getLoginAdminUser().getUsername());// TODO
    // // 这里取登陆用户ID的方法日后要变动
    // balanceReq.setMemo(account.getMemo());
    // processCode = accountManager.doBalanceReq(balanceReq).getCode();
    // }
    //
    // /**
    // * 对帐户操作记录备注页面
    // *
    // * @return
    // * @throws Exception
    // */
    // public String accountProcessReason() throws Exception {
    // return SUCCESS;
    // }

    /**
     * 跳转到用户查询页面
     *
     * @return
     * @throws Exception
     */
    @AdminAccess({EnumAdminPermission.A_SHOW_ACCOUNT_QUERY})
    @RequestMapping(value = "/showAccountsQueryView")
    public String showAccountsQueryView(@ModelAttribute("searchConditionQuery") SearchConditionQuery searchConditionQuery,
                                        Model model) throws Exception {

        Map<String, String> searchConditionn = new HashMap();
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        Date now = new Date();
        Calendar calendar = Calendar.getInstance();
        // 当前月的第一天
        GregorianCalendar gc = (GregorianCalendar) Calendar.getInstance();
        gc.setTime(now);
        gc.set(Calendar.DAY_OF_MONTH, 1);
        String day_first = df.format(gc.getTime());

        // 当前月的最后一天

        calendar.add(calendar.MONTH, 1);
        calendar.set(calendar.DATE, 1);
        calendar.add(calendar.DATE, -1);

        String day_end = df.format(calendar.getTime());

        searchConditionQuery.setCreateTimeStart(day_first);
        searchConditionQuery.setCreateTimeEnd(day_end);
        model.addAttribute("searchConditionQuery", searchConditionQuery);
        return "/account/showAccountsList";
    }

    // /**
    // * 跳转到冻结/解冻帐户余额页面
    // *
    // * @return
    // * @throws Exception
    // */
    // public String showBalanceReq() throws Exception {
    // return SUCCESS;
    // }
    //
    // /**
    // * 跳转到账户金额手动修改页面
    // *
    // * @return
    // * @throws Exception
    // */
    // public String showAccountBalanceDo() throws Exception {
    // return SUCCESS;
    // }
    //
    // /**
    // * 修改账户初始化金额
    // *
    // * @return
    // * @throws Exception
    // */
    // public String doInitializeAccount() throws Exception {
    // String dateStart = getRequest().getParameter("datestart");
    // String dateEnd = getRequest().getParameter("dateEnd");
    // // 时间的基本验证
    // if (StringUtil.isBlank(dateStart) || StringUtil.isBlank(dateEnd)) {
    // resultString = "请输入完整的时间";
    // return INPUT;
    // }
    // // 时间开始时间必须小于等于结束时间
    // int days = DateUtil.countDays(dateStart, dateEnd, "yyyy-MM-dd");
    // if (days < 0) {
    // resultString = "结束时间必须大于开始时间";
    // return INPUT;
    // }
    // resultString = accountManager.doInitializeAccount(dateStart, dateEnd);
    // return SUCCESS;
    // }
    //
    // /**
    // * 账户金额修改
    // *
    // * @return
    // * @throws Exception
    // */
    // public String doUpdateAccount() throws Exception {
    // String accountNo = getRequest().getParameter("accountNo");
    // String balance = getRequest().getParameter("balance");
    // // 先替换空格
    // balance = StringUtil.replace(balance, " ", "");
    // // 验证余额
    // Pattern pattern = Pattern
    // .compile("^(-)?(([1-9]{1}\\d*)|([0]{1}))(\\.(\\d){1,2})?$");
    // Matcher matcher = pattern.matcher(balance);
    // if (!matcher.matches()) {
    // resultString = getText("error.amount.format");
    // return INPUT;
    // }
    //
    // if (accountManager.getAccountByAccountNo(accountNo) != null) //
    // 如果账户号是存在的话
    // resultString = accountManager.doUpdateAccount(accountNo, balance);
    // else
    // resultString = "NOAccountNO";
    // return SUCCESS;
    // }


}
