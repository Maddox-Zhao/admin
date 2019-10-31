package com.huaixuan.network.biz.service.admin;

import com.huaixuan.network.biz.domain.account.Account;


/**
 * guoyj
 */
public interface AccountManager {

	// /**
	// * 创建内部账户
	// *
	// * @param accountType
	// * 账户类型 3表是内部账户
	// * @param accountSubType
	// * 账户子类型 01:交易账户，02:保证金账户,03:佣金账户，04:返点账户
	// * @param accountName
	// * 账户创建者
	// * @param memo
	// * 账户备注
	// * @return
	// */
	// public boolean addInnerWebAccount(String accountType, String accountSubType,
	// String accountName, String memo);
	//
	// /**
	// * 创建账户，主要是创建商家账户和顾客账户，用户注册时的调用接口
	// *
	// * @param userId
	// * 用户id
	// * @param userType
	// * 用户类型
	// * @return boolean
	// */
	// public boolean addFrontWebAccount(Long userId, String userType);
	//
	// /**
	// * 返回用户账号，用户账号=11185+账户类型+子类型+用户Id
	// *
	// * @param accountNoBegin
	// * 账户开头是5位，规定是11185开头
	// * @param accountType
	// * 账户类型，规定是一位
	// * @param accountSubType
	// * 账户子类型，规定是两位
	// * @param userId
	// * 用户Id,规定是12位，不够12位的话左端补零
	// * @return String
	// */
	// public String getAccountNO(String accountNoBegin, String accountType, String accountSubType,
	// String userId);
	//
	// /**
	// * 返回用户userId,userId为12位，如果不足12位的话就左端补零
	// *
	// * @param userId
	// * @return
	// */
	// public String getAccountNO(String userId);
	//
	// /**
	// * 得到用户表的seq值
	// * @return
	// */
	// public Long getUserSeq();
	//
	// /**
	// * account分页
	// * @param currentPage
	// * @param pageSize
	// * @return
	// */
	// public PageUtil<Account> getAccountPage(int currentPage, int pageSize);
	//
	// /**
	// * 创建账户
	// *
	// * @param account
	// */
	// public void addAccount(Account account);
	//
	// /**
	// * 得到所有账号，返回List封装对象
	// *
	// * @return
	// */
	// public List<Account> getAccounts();
	//
	// /**
	// * 判断其内部账户子类别是否重复添加
	// * @param map
	// * @return
	// */
	// public boolean checkAddSubAccount(Map map);
	//
	// /**
	// * 根据搜索条件取得客户账号
	// * @return
	// */
	// public PageUtil<Account> getAccountsByCondition(Map<String, String> searchMap, Page page);
	//
	// /**
	// * 根据帐户号查看账户金额变动流水
	// * @param acount
	// * @param page
	// * @return page
	// */
	// public PageUtil<Map> getAccountLogsByAccountNo(Map<String, String> searchMap, Page page);
	//
	// /**
	// * 根据帐户号查看账户保证金变动流水
	// * @param acount
	// * @param page
	// * @return page
	// */
	// public PageUtil<Map> getBalanceLogsListByAccountNo(Map<String, String> searchMap, Page page);
	//
	// /**
	// * 根据条件查看账户金额变动流水
	// * @param acount
	// * @param page
	// * @return page
	// */
	// public PageUtil<Map> getAccountLogsByUserId(Map<String, String> searchMap, Page page);
	//
	// /**
	// * 对帐户进行冻结/解冻之类的操作
	// * @param account
	// */
	// public void doProcessAccount(Account account);

	/**
	 * 获取买家交易账户
	 * 
	 * @param userId
	 * @return
	 */
	public Account getBuyerTransAccount(long userId);
	// /**
	// * 获取买家保证金账户
	// * @param userId
	// * @return
	// */
	// public Account getBuyerAuctionAccount(long userId);
	//
	// /**
	// * 获取卖家交易账户(当有多个商户时)
	// * @param userId
	// * @return
	// */
	// public Account getSellerTransAccount(long userId);
	//
	//
	//
	// /**
	// * 获取用户某一类型的账户(收款或者付款)
	// * @param userId
	// * @return
	// */
	// public Account getAccountByUidAndType(long userId, EnumSubAccount accoutType);
	//
	// /**
	// * 通过accountNo获得account
	// * @param AccountNo
	// * @return
	// */
	// public Account getAccountByAccountNo(String AccountNo);
	//
	// /**
	// * 根据页面输入的金额对帐户进行冻结/解冻的操作
	// * @param req
	// * @return
	// */
	// public AccountTransResult doBalanceReq(FreezeBalanceReq req);
	//
	// /**
	// * 获取保证金余额
	// * @param accountNo
	// * @return
	// */
	// public Money getSecurityBalance(String accountNo);
	//
	// /**
	// * 修改账户初始化金额
	// * @param datestart
	// * @param dateEnd
	// * @return
	// */
	// public String doInitializeAccount(String datestart, String dateEnd);
	//
	// /**
	// * 账户金额修改
	// * @param accountNo
	// * @param balance
	// * @return
	// */
	// public String doUpdateAccount(String accountNo, String balance);
	//
	//
	//
	// /**
	// * 取得拍卖保证金余额
	// * @param accountNo
	// * @param type
	// * @return
	// */
	// public Money getAuctionSecurityBalance(String accountNo);

}
