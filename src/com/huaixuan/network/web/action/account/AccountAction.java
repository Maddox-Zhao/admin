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
    public final static String accountIneerType = "3";                // �˻�����,�ڲ��˻�
    @Autowired
    private AccountManager     accountManager;
    @Autowired
    private PayPackageManager  payPackageManager;
    private static final int   PAGE_SIZE        = 10;

    //
    // private Account account;
    // private Map<String, String> searchCondition;
    //
    // /** �ʻ���ѯ����б� **/
    // private List<Account> searcheList;
    //
    // /** ������Ľ����ʾ���� **/
    // private String processCode;
    //
    // private FreezeBalanceReq balanceReq;
    //
    // private String balanceAmount;
    //
    // /** �˻����䶯��ˮ����� **/
    // private List<Map> searchAccountLogsList;
    //
    // /** ��֤��䶯��ˮ����� **/
    // private List<Map> searchBalanceLogsList;
    //
    // private Long securityAmount; // ��֤��
    //
    // private String resultString;
    //
    // /**
    // * �ڲ��˻���listҳ��
    // *
    // * @return "success" if no exceptions thrown
    // */
    // public String showInnerAccountList() throws Exception {
    // // ��ȡlist��ҳ����
    // accountList = accountManager.getAccountPage(this.currentPage,
    // this.pageSize);
    // if (null != accountList) {
    // page = accountList.getPage();
    // }
    // return SUCCESS;
    // }
    //
    /**
     * ���� �ڲ��˻� ��ʼҳ��
     *
     * @return "success" if no exceptions thrown
     */
    @AdminAccess({EnumAdminPermission.A_INNER_ACCOUNT_ADD})
    @RequestMapping(value = "addInnerAccountPage")
    public String addInnerAccountPage(@ModelAttribute("account") Account account) throws Exception {

        return "/account/addInnerAccount";
    }

    /**
     * ����ڲ��˻�
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

        if (!accountManager.checkAddSubAccount(map)) // ���û�����
            if (accountManager.addInnerWebAccount(accountIneerType, account.getAccountSubType(),
                adminAgent.getUsername(), account.getMemo().trim())) // ����ڲ��˻�,�ɹ��Ļ�
            {
                model.addAttribute("addInnerAccountSuccessInfo", "��ӳɹ�");
                return "/account/addInnerAccount";
            } else // ����ڲ��˻���ʧ�ܵĻ�
            {
                model.addAttribute("addInnerAccountErrorInfo", "���ʧ��");
                return "/account/addInnerAccount";
            }
        else // ��������
        {
            model.addAttribute("accountInfoError", "���Ѿ���ӹ����������˻�");
            return "/account/addInnerAccount";
        }

    }

    /**
     * ������������ȡ�ÿͻ��ʻ�
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
        // �ж��Ƿ���н�����Ա��Ȩ��
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
    // * ���ʻ����ж���/�ⶳ֮��Ĳ���
    // *
    // * @return
    // * @throws Exception
    // */
    // public String doProcessAccount() {
    // if (account == null || account.getAccountNo() == null
    // || account.getAccountNo().equals("")) {
    // processCode = EnumProcessCode.PROCESS_ERROR.getCode();
    // } else {
    // // ���account�����������
    // try {
    // String loginId = getLoginAdminUser().getUsername() + "";
    // account.setModifier(loginId);
    // accountManager.doProcessAccount(account);
    // processCode = EnumProcessCode.PROCESS_OK.getCode();
    // // if (account.getEnabledStatus().equals("T"))
    // // addWorkLog("���ʻ����нⶳ����", account.getAccountNo(),
    // // EnumWorkLogType.Account
    // // .getKey(), "�ⶳ�����ɹ�");
    // // else
    // // addWorkLog("���ʻ����ж������", account.getAccountNo(),
    // // EnumWorkLogType.Account
    // // .getKey(), "��������ɹ�");
    // getAccountDetail();
    //
    // } catch (Exception ex) {
    // processCode = EnumProcessCode.PROCESS_ERROR.getCode();
    // }
    // }
    // return SUCCESS;
    // }

    /**
     * ����accountNo���account
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
            // securityAmount = securityMoney.getCent(); //�õ���֤��
            model.addAttribute("account", account);
        } catch (Exception ex) {
            return "/account/account_error";
        }
        return "/account/accountDetail";
    }

    // /**
    // * ����ҳ������Ľ����ʻ����ж������
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
    // // addWorkLog("����ҳ������Ľ����ʻ����ж������", balanceReq.getAccountNo(),
    // // EnumWorkLogType.Account
    // // .getKey(), "��������ɹ�");
    // return getAccountDetail();
    // }
    //
    // /**
    // * ����ҳ������Ľ����ʻ����нⶳ����
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
    // // addWorkLog("����ҳ������Ľ����ʻ����нⶳ����", balanceReq.getAccountNo(),
    // // EnumWorkLogType.Account
    // // .getKey(), "�ⶳ�����ɹ�");
    // return getAccountDetail();
    // }

    /**
     * �����ʻ��Ų鿴�˻����䶯��ˮ
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
            // modify by fanyj start 2010-12-16 �ϲ�������޸�
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
    // * �����ʻ��Ų鿴�˻��ʽ𶳽�䶯��ˮ
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
    // * ����ҳ������Ľ����ʻ����ж���/�ⶳ�Ĳ���
    // *
    // * @return
    // */
    // private void doBalanceReq() {
    // balanceReq.setAmount(new Money(balanceAmount));
    // balanceReq.setAccountNo(account.getAccountNo());
    // balanceReq.setOperator(getLoginAdminUser().getUsername());// TODO
    // // ����ȡ��½�û�ID�ķ����պ�Ҫ�䶯
    // balanceReq.setMemo(account.getMemo());
    // processCode = accountManager.doBalanceReq(balanceReq).getCode();
    // }
    //
    // /**
    // * ���ʻ�������¼��עҳ��
    // *
    // * @return
    // * @throws Exception
    // */
    // public String accountProcessReason() throws Exception {
    // return SUCCESS;
    // }

    /**
     * ��ת���û���ѯҳ��
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
        // ��ǰ�µĵ�һ��
        GregorianCalendar gc = (GregorianCalendar) Calendar.getInstance();
        gc.setTime(now);
        gc.set(Calendar.DAY_OF_MONTH, 1);
        String day_first = df.format(gc.getTime());

        // ��ǰ�µ����һ��

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
    // * ��ת������/�ⶳ�ʻ����ҳ��
    // *
    // * @return
    // * @throws Exception
    // */
    // public String showBalanceReq() throws Exception {
    // return SUCCESS;
    // }
    //
    // /**
    // * ��ת���˻�����ֶ��޸�ҳ��
    // *
    // * @return
    // * @throws Exception
    // */
    // public String showAccountBalanceDo() throws Exception {
    // return SUCCESS;
    // }
    //
    // /**
    // * �޸��˻���ʼ�����
    // *
    // * @return
    // * @throws Exception
    // */
    // public String doInitializeAccount() throws Exception {
    // String dateStart = getRequest().getParameter("datestart");
    // String dateEnd = getRequest().getParameter("dateEnd");
    // // ʱ��Ļ�����֤
    // if (StringUtil.isBlank(dateStart) || StringUtil.isBlank(dateEnd)) {
    // resultString = "������������ʱ��";
    // return INPUT;
    // }
    // // ʱ�俪ʼʱ�����С�ڵ��ڽ���ʱ��
    // int days = DateUtil.countDays(dateStart, dateEnd, "yyyy-MM-dd");
    // if (days < 0) {
    // resultString = "����ʱ�������ڿ�ʼʱ��";
    // return INPUT;
    // }
    // resultString = accountManager.doInitializeAccount(dateStart, dateEnd);
    // return SUCCESS;
    // }
    //
    // /**
    // * �˻�����޸�
    // *
    // * @return
    // * @throws Exception
    // */
    // public String doUpdateAccount() throws Exception {
    // String accountNo = getRequest().getParameter("accountNo");
    // String balance = getRequest().getParameter("balance");
    // // ���滻�ո�
    // balance = StringUtil.replace(balance, " ", "");
    // // ��֤���
    // Pattern pattern = Pattern
    // .compile("^(-)?(([1-9]{1}\\d*)|([0]{1}))(\\.(\\d){1,2})?$");
    // Matcher matcher = pattern.matcher(balance);
    // if (!matcher.matches()) {
    // resultString = getText("error.amount.format");
    // return INPUT;
    // }
    //
    // if (accountManager.getAccountByAccountNo(accountNo) != null) //
    // ����˻����Ǵ��ڵĻ�
    // resultString = accountManager.doUpdateAccount(accountNo, balance);
    // else
    // resultString = "NOAccountNO";
    // return SUCCESS;
    // }


}
