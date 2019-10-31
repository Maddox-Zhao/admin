package com.huaixuan.network.web.action.hx;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import sun.misc.BASE64Encoder;

import com.huaixuan.network.biz.dao.hx.AccountDao;
import com.huaixuan.network.biz.domain.admin.Admin;
import com.huaixuan.network.biz.domain.admin.AdminAgent;
import com.huaixuan.network.biz.domain.hx.Account;
import com.huaixuan.network.biz.domain.hx.AccountDetail;
import com.huaixuan.network.biz.query.QueryPage;
import com.huaixuan.network.biz.service.admin.AdminService;
import com.huaixuan.network.biz.service.hx.AccountDetailService;
import com.huaixuan.network.biz.service.hx.AccountService;
import com.huaixuan.network.common.util.DateUtil;
import com.huaixuan.network.web.action.BaseAction;

@Controller
@RequestMapping("/hxaccount")
public class HXAccountAction extends BaseAction
{
	@Autowired
	private AccountService accountService;

	@Autowired
	private AccountDetailService accountDetailService;

	@Autowired
	private AdminService adminService;
	
	@Autowired
	private AccountDao accountDao;

	/**
	 * 得到刚用户的的现金账户
	 * 
	 * @param model
	 * @param adminAgent
	 * @return
	 */
	@RequestMapping("/list")
	public String accountList(Model model, AdminAgent adminAgent)
	{

		Admin admin = adminService.getAdminById(adminAgent.getId());
		List<Account> accounts;// 
		if (!"-1".equals(admin.getDepCode())) // -1为总公司,该部门可以看到所有的现金账户
		{
			accounts = accountService.getAccountByDepcode(admin.getDepCode());// 通过部门来查找对应的现金账户
		}
		else
		{
			accounts = accountService.getAccountByDepcode(null);// 总公司
		}
		model.addAttribute("accounts", accounts);
		return "hx/accountViewList";
	}

	@RequestMapping("/addDetail")
	public String addAccount(@ModelAttribute("accountDetail") AccountDetail accountDetail,@RequestParam(value = "accountId",required = false)Long accountId,
			Model model,AdminAgent adminAgent)
	{
		if(accountId != null)
			accountDetail.setAccountId(accountId);
		String userName = adminAgent.getUsername();
		Long opreation = -1L ;
		try
		{
			opreation = Long.parseLong(userName);
		}
		catch (Exception e)
		{
			
		}
		accountDetail.setOperationId(opreation);
		accountDetailService.addAccount(accountDetail);
		model.addAttribute("accountId", accountDetail.getAccountId());
		model.addAttribute("isFirst", "true");
		return "redirect:/hxaccount/search.html";
	}

	@RequestMapping("/update")
	public String updateAccount(@ModelAttribute("account") Account account,
			Model model)
	{
		accountService.updateAccountByNotNull(account);
		return null;
	}

	@RequestMapping("/delete")
	public String deleteAccount(@RequestParam("id") Long id)
	{
		accountService.deleteAccountById(id);
		return null;
	}

	/**
	 * 查看当前现金账户的详情
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping("/search")
	public String detailAccount(
			@ModelAttribute("accountDetail") AccountDetail accountDetail,
			Model model,
			@RequestParam(value = "page", required = false, defaultValue = "1") int currpage,
			@RequestParam(value = "isFirst", required = false, defaultValue = "false") String isFirst,
			@RequestParam(value= "accountId",required = false)Long accountId,AdminAgent adminAgent)
	{
		if ("true".equals(isFirst))
		{
			Date now = new Date();
			if (accountDetail.getDateStart() == null
					|| accountDetail.getDateStart().equals(""))
			{
				accountDetail.setDateStart(DateUtil.getDate(now, -30));
			}
			if (accountDetail.getDateEnd() == null || accountDetail.getDateEnd().equals(""))
			{
				accountDetail.setDateEnd(DateUtil.getDate(now, 1));
			}
		}
		accountDetail.setAccountId(accountId);
		QueryPage query = accountDetailService.getAccountDetailByConditionWithPage(accountDetail, currpage, this.pageSize);
		if (query != null)
		{
			model.addAttribute("query", query);
		}
		model.addAttribute("adminList", accountDetailService.getAdminByDepCode(accountService.getAccountByAccountId(accountId).getAccountDepcode()));
		model.addAttribute("account", accountDao.getAccountByAccountId(accountId));
		model.addAttribute("accountId", accountId);
		return "hx/accountDetail";

	}
	
	
	@RequestMapping("/addInput")
	public String addDetailInput(@ModelAttribute("accountDetail")AccountDetail accountDetail,Model model,@RequestParam("accountId")Long accountId)
	{
		model.addAttribute("accountId", accountId);
		accountDetail.setAccountId(accountId);
		return  "hx/addAccountDetailInput";
	}
	
	public static void main(String[] args) throws Exception
	{
	   URL url = new URL("http://ccapi.weishangye.com/product/getall?shopId=2569719171&pageIndex=1&count=10");
	   URLConnection conn = url.openConnection();
	   conn.setRequestProperty("Content-Type", "text/xml");
	   conn.setRequestProperty("Accept", "*/*");
	   //String loingName = "12688657:6190c2c58ddf65c9504f83a48dfb32dc";
	   String loingName = "2540208215:6e2cd7ee9d4d5ac067a67a34ee58168e";
	   String login = new BASE64Encoder().encode(loingName.getBytes()).replaceAll("\n", "");
	   conn.setRequestProperty("Authorization", "Basic " + login);
	   BufferedInputStream bis = new BufferedInputStream(conn.getInputStream());
	   BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream("d:/1.txt"));
	   byte[] buffer = new byte[1024];
	   int len = -1;
	   while(-1 != (len = bis.read(buffer, 0, 1024)))
	   {
		   bos.write(buffer, 0, len);
	   }
	   bis.close();
	   bos.close();
	}
	
}
