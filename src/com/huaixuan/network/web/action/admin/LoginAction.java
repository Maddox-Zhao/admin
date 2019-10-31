package com.huaixuan.network.web.action.admin;

import java.io.UnsupportedEncodingException;
import java.net.InetAddress;
import java.net.URLDecoder;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.providers.encoding.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.huaixuan.network.biz.domain.Comments;
import com.huaixuan.network.biz.domain.Feedback;
import com.huaixuan.network.biz.domain.admin.Admin;
import com.huaixuan.network.biz.domain.admin.AdminAgent;
import com.huaixuan.network.biz.domain.admin.LoginLogo;
import com.huaixuan.network.biz.domain.hx.Dxrecord;
import com.huaixuan.network.biz.domain.trade.Trade;
import com.huaixuan.network.biz.enums.EnumLoginResult;
import com.huaixuan.network.biz.enums.EnumTradeStatus;
import com.huaixuan.network.biz.service.admin.LoginLogoService;
import com.huaixuan.network.biz.service.admin.LoginService;
import com.huaixuan.network.biz.service.goods.CommentsService;
import com.huaixuan.network.biz.service.shop.FeedbackService;
import com.huaixuan.network.biz.service.trade.TradeManager;
import com.huaixuan.network.web.action.BaseAction;
import com.hundsun.network.melody.common.util.StringUtil;
import com.hundsun.network.melody.common.web.cookyjar.Cookyjar;
import com.tenpay.util.MD5Util;

@Controller
public class LoginAction extends BaseAction{

    @Autowired
    private PasswordEncoder     passwordEncoder;

    @Autowired
    private LoginService      loginService;

    @Autowired
    private TradeManager      tradeManager;

    @Autowired
    private FeedbackService      feedbackService;
    
    @Autowired
    private CommentsService  commentsService;
    @Autowired
    private LoginLogoService loginLogoService;

    /** 编码集 */
    private @Value("${web.encoding}")
    String  webEncoding;

	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public ModelAndView login(@RequestParam(value = "returnto", required = false) String returnto,
			@ModelAttribute("admin")Admin admin, AdminAgent adminAgent, Model model) {
        //如果用户已经登录，直接跳到相应页面
        model.addAttribute("returnto", returnto);
        if (adminAgent != null) {
            return new ModelAndView("redirect:/index.html", model.asMap());
        }
        return null;
	}
	
	//代销客户登录
	@RequestMapping(value = "/slogin", method = RequestMethod.GET)
	public ModelAndView slogin(@RequestParam(value = "returnto", required = false) String returnto,
			@ModelAttribute("admin")Admin admin, AdminAgent adminAgent, Model model) {
        //如果用户已经登录，直接跳到相应页面
        model.addAttribute("returnto", returnto);
        if (adminAgent != null) {
            return new ModelAndView("redirect:/index.html", model.asMap());
        }
        ModelAndView mv = new ModelAndView();
    	mv.setViewName("/slogin");
    	return mv;
	}
	
	@RequestMapping(value = "/index", method = RequestMethod.GET)
	public ModelAndView index(@RequestParam(value = "returnto", required = false) String returnto,
			@ModelAttribute("admin")Admin admin, AdminAgent adminAgent, Model model,HttpServletRequest request) {
        //如果用户已经登录，直接跳到相应页面
        model.addAttribute("returnto", returnto);

        
        if (adminAgent == null) {
        	String domain = getDomain(request);
        	
        	//代销用户登录点
        	if("http://supplier.hkshangshang.com/".equals(domain))
            {
        		return new ModelAndView("redirect:/slogin.html", model.asMap());
            }
        	else
        	{
        		return new ModelAndView("redirect:/login.html", model.asMap());
        	}
        }else{
        	ModelAndView mv = new ModelAndView();
        	mv.setViewName("/index");
        	return mv;
        }
	}

	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public ModelAndView login(
			HttpServletRequest request,
			@ModelAttribute("admin") Admin admin,
			BindingResult result, Model model, Cookyjar cookyjar) throws UnsupportedEncodingException{
		
		String returnto = request.getParameter("returnto");
		boolean rememberMe = Boolean.valueOf(request.getParameter("rememberMe"));
        if(StringUtil.isEmpty(admin.getUserName()) || StringUtil.isBlank(admin.getUserName())){
        	admin.setLoginTag(EnumLoginResult.USERNAME_BLANK.getCode());
        	model.addAttribute(admin.getLoginTag(), true);
        	return null;
        }
        if(StringUtil.isEmpty(admin.getPassword()) || StringUtil.isBlank(admin.getPassword())){
        	admin.setLoginTag(EnumLoginResult.PASSWORD_BLANK.getCode());
        	model.addAttribute(admin.getLoginTag(), true);
        	return null;
        }
		admin.setPassword(MD5Util.MD5Encode(admin.getPassword(), null));
		Admin loginTag = loginService.login(admin,cookyjar,rememberMe);
		
		
		
		if(loginTag.getLoginTag().equals(EnumLoginResult.SUCCESS.getCode())){
			
			//登录成功，如果是代销客户记录登录IP 信息等。。
			if("dx".equals(admin.getType()))
			{
				 
				Dxrecord dx = new Dxrecord();
				dx.setUserName(admin.getUserName());
				dx.setIp(getIpAddr(request));
				loginService.addDxrecord(dx);
			}
            // 如果“返回地址”不为空，返回到“返回地址”
			if (StringUtil.isNotBlank(returnto)) {
                model.addAttribute("returnto", URLDecoder.decode(returnto, this.webEncoding));
   
			}
			
			
			//记录到登录的信息记录
			LoginLogo loginLogo = new LoginLogo();
			
			String name = loginTag.getName();//得到登录的用户名
			
			
			String ipAddress = request.getHeader("x-forwarded-for");
			if(ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
				ipAddress = request.getHeader("Proxy-Client-IP");
		
			}
			if(ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
				ipAddress = request.getHeader("WL-Proxy-Client-IP");
				
			}
			if(ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
				ipAddress = request.getRemoteAddr();
	
				if(ipAddress.equals("127.0.0.1") || ipAddress.equals("0:0:0:0:0:0:0:1")){
					//根据网卡取本机配置的IP
					InetAddress inet=null;
					try {
						inet = InetAddress.getLocalHost();

					} catch (UnknownHostException e) {
						e.printStackTrace();
					}
					ipAddress= inet.getHostAddress();
					
	
				}
			}
			//对于通过多个代理的情况，第一个IP为客户端真实IP,多个IP按照','分割
			if(ipAddress!=null && ipAddress.length()>15){ //"***.***.***.***".length() = 15
				if(ipAddress.indexOf(",")>0){
		
					ipAddress = ipAddress.substring(0,ipAddress.indexOf(","));

				}
			}
			
			loginLogo.setUser(name);
			loginLogo.setIp(ipAddress);
			loginLogoService.addLogin(loginLogo);

            return new ModelAndView("redirect:/index.html", model.asMap());
            
		}else{
        	model.addAttribute(loginTag.getLoginTag(), true);
			//return null;
			if("dx".equals(admin.getType()))
			{
				//代销客户登录失败
				return new ModelAndView("slogin", model.asMap());
			}
			else
				 return null;
        	 
        }
		
		
		
	}
	
	/**
	 * 获取当前网络ip
	 * @param request
	 * @return
	 */
	
	public String getIpAddr(HttpServletRequest request){
		String ipAddress = request.getHeader("x-forwarded-for");
			if(ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
				ipAddress = request.getHeader("Proxy-Client-IP");
			}
			if(ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
				ipAddress = request.getHeader("WL-Proxy-Client-IP");
			}
			if(ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
				ipAddress = request.getRemoteAddr();
				if(ipAddress.equals("127.0.0.1") || ipAddress.equals("0:0:0:0:0:0:0:1")){
					//根据网卡取本机配置的IP
					InetAddress inet=null;
					try {
						inet = InetAddress.getLocalHost();
					} catch (UnknownHostException e) {
						e.printStackTrace();
					}
					ipAddress= inet.getHostAddress();
				}
			}
			//对于通过多个代理的情况，第一个IP为客户端真实IP,多个IP按照','分割
			if(ipAddress!=null && ipAddress.length()>15){ //"***.***.***.***".length() = 15
				if(ipAddress.indexOf(",")>0){
					ipAddress = ipAddress.substring(0,ipAddress.indexOf(","));
				}
			}
			return ipAddress; 
	}
	
	
	//代销账号登录
	@RequestMapping(value = "/slogin", method = RequestMethod.POST)
	public ModelAndView slogin(
			HttpServletRequest request,
			@ModelAttribute("admin") Admin admin,
			BindingResult result, Model model, Cookyjar cookyjar) throws UnsupportedEncodingException{
		 	
		 return login(request, admin, result, model, cookyjar);
	}

    @RequestMapping(value = "/exit")
    public String exit(Cookyjar cookyjar,HttpServletRequest request) {
        cookyjar.remove(AdminAgent.class);
        /*
        String domain = getDomain(request);
        if("http://supplier.hkshangshang.com/".equals(domain))
        {
        	 return "redirect:/slogin.html";
        }
        */
        return "redirect:/login.html";
    }

    @RequestMapping(value = "admin_right")
    public String index(AdminAgent adminAgent, Model model, @RequestParam(value = "returnto", required = false) String returnto){
    	if (adminAgent == null) {
    		return "redirect:/login.html";
    	}
    	
        if (StringUtil.isNotBlank(returnto)) {
//            return new ModelAndView("redirect:" + returnto);
        	return "redirect:"+returnto;
        }
    	
        // 订单信息
        List<Trade> tradeList = new ArrayList<Trade>();
        tradeList = tradeManager.getTradeCountWithStatus();

        Long waitSellerConfirm = 0L;
        Long waitBuyerConfirm = 0L;
        Long waitDistribution = 0L;
        Long waitBuyerPay = 0L;
        Long waitSellerSendGoods = 0L;
        Long waitBuyerConfirmGoods = 0L;
        Long tradeClose = 0L;
        Long tradeFinish = 0L;
        Long allTrade = 0L;
        for (Trade trade : tradeList) {
        	if (EnumTradeStatus.WAIT_SELLER_CONFIRM.getKey().equals(trade.getStatus()))
        		waitSellerConfirm = trade.getCountWithStatus();
            else if (EnumTradeStatus.WAIT_BUYER_CONFIRM.getKey().equals(trade.getStatus()))
            	waitBuyerConfirm = trade.getCountWithStatus();
            else if (EnumTradeStatus.WAIT_DISTRIBUTION.getKey().equals(trade.getStatus()))
            	waitDistribution = trade.getCountWithStatus();
            else if (EnumTradeStatus.WAIT_BUYER_PAY.getKey().equals(trade.getStatus()))
                waitBuyerPay = trade.getCountWithStatus();
            else if (EnumTradeStatus.WAIT_SELLER_SEND_GOODS.getKey().equals(trade.getStatus()))
                waitSellerSendGoods = trade.getCountWithStatus();
            else if (EnumTradeStatus.WAIT_BUYER_CONFIRM_GOODS.getKey().equals(trade.getStatus()))
                waitBuyerConfirmGoods = trade.getCountWithStatus();
            else if (EnumTradeStatus.TRADE_CLOSE.getKey().equals(trade.getStatus()))
                tradeClose = trade.getCountWithStatus();
            else if (EnumTradeStatus.TRADE_FINISH.getKey().equals(trade.getStatus()))
                tradeFinish = trade.getCountWithStatus();
        }
        allTrade = waitSellerConfirm + waitBuyerConfirm + waitDistribution + waitBuyerPay + 
        			waitSellerSendGoods + waitBuyerConfirmGoods + tradeClose + tradeFinish;
        
        // 留言回复信息
        Feedback feedback = new Feedback();
        feedback.setToUserType("shop");
        List<Feedback> feedbackList = new ArrayList<Feedback>();
        feedbackList = feedbackService.feedbackCountWithReplyFlag(feedback);
        Long notReplyFeedback = 0L;
        Long replyFeedback = 0L;
        Long allFeedback = 0L;
        for (Feedback fb : feedbackList) {
            if ("no".equals(fb.getReplyFlag())) {
                notReplyFeedback = fb.getCountWithReplyFlag();
            } else if ("yes".equals(fb.getReplyFlag())){
                replyFeedback = fb.getCountWithReplyFlag();
            }
        }
        allFeedback = notReplyFeedback + replyFeedback;
        
        // 评论信息
        List<Comments> commentsList = new ArrayList<Comments>();
        commentsList = commentsService.commentsCountWithReplyFlag();
        Long notReplyComments = 0L;
        Long replyComments = 0L;
        Long allComments = 0L;
        for (Comments comment : commentsList) {
            if ("no".equals(comment.getReplyFlag())) {
                notReplyComments = comment.getCountWithReplyFlag();
            } else if ("yes".equals(comment.getReplyFlag())) {
                replyComments = comment.getCountWithReplyFlag();
            }
        }
        allComments = notReplyComments + replyComments;
        
        model.addAttribute("waitSellerConfirm", waitSellerConfirm);
        model.addAttribute("waitBuyerConfirm", waitBuyerConfirm);
        model.addAttribute("waitDistribution", waitDistribution);
        model.addAttribute("waitBuyerPay", waitBuyerPay);
        model.addAttribute("waitSellerSendGoods", waitSellerSendGoods);
        model.addAttribute("waitBuyerConfirmGoods", waitBuyerConfirmGoods);
        model.addAttribute("tradeClose", tradeClose);
        model.addAttribute("tradeFinish", tradeFinish);
        model.addAttribute("allTrade",  allTrade);

        model.addAttribute("allFeedback", allFeedback);
        model.addAttribute("notReply", notReplyFeedback);

        model.addAttribute("allComments", allComments);
        model.addAttribute("notReplyComments", notReplyComments);

    	model.addAttribute("adminAgent", adminAgent);

    	return null;
    }
    
    
    
    private String getDomain(HttpServletRequest request)
    {
    	StringBuffer url = request.getRequestURL();
    	if(null == url) return "";
    	String tempContextUrl = url.delete(url.length() - request.getRequestURI().length(), url.length()).append("/").toString();
    	return tempContextUrl;
    }

}
