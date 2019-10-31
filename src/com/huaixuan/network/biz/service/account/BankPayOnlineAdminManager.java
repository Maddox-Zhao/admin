package com.huaixuan.network.biz.service.account;

import com.huaixuan.network.biz.domain.counter.query.PayOnlineSearchQuery;
import com.huaixuan.network.biz.query.QueryPage;

/**
 * ���ж�����̨����ӿ�
 *
 * @author guoyj
 * @version $Id: BankPayOnlineAdminManager.java,v 0.1 2010-6-7 ����09:28:47 guoyj
 *          Exp $
 */
public interface BankPayOnlineAdminManager {
	/**
	 * ��������������������������֧����ˮ
	 *
	 * @return
	 */
	public QueryPage searchBankPayOnlineListByCondition(
			PayOnlineSearchQuery payOnlineSearchQuery, int currentPage,
			int pageSize);

}