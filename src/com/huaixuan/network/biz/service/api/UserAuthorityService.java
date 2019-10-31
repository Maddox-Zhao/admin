package com.huaixuan.network.biz.service.api;

import javax.servlet.http.HttpServletRequest;

import com.huaixuan.network.biz.domain.api.ResponseData;



/**
 * @author Mr_Yang   2015-12-3 下午01:48:16
 * 验证用户是否有效  有效进行下一步操作
 **/

public interface UserAuthorityService
{

	/**
	 * 验证用户
	 * @param request 请求参数
	 * @return
	 */
	public ResponseData validataUser(HttpServletRequest request);
	
	
	/**
	 * 验证用户
	 * @param request 请求参数
	 * @return
	 */
	public ResponseData validataUserJsonArray(HttpServletRequest request);
	
	
	
}	
 
