/**
 * 
 */
package com.huaixuan.network.biz.dao.provider;

import java.util.List;
import java.util.Map;

import com.huaixuan.network.biz.domain.provider.ProvidePostOrderLog;

/**
 * @author TT
 * 
 */
public interface ProvidePostOrderLogDao {

	/**
	 * @param maplog
	 * @return
	 */
	List<ProvidePostOrderLog> selectPushOrderLog(Map<String, String> maplog);

	/**
	 * @param maplog
	 * @return
	 */
	void insertPushOrderLog(Map<String, String> maplog);

}
