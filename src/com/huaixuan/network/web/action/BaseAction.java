package com.huaixuan.network.web.action;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;

import com.huaixuan.network.biz.domain.admin.AdminAgent;
import com.huaixuan.network.biz.domain.admin.AdminTeam;
import com.huaixuan.network.biz.enums.EnumAdminPermission;
import com.huaixuan.network.biz.service.admin.AdminService;
import com.hundsun.network.melody.common.util.StringUtil;
import com.hundsun.network.melody.common.web.url.URLBroker;

public class BaseAction {

    protected final Log        logger             = LogFactory.getLog(this.getClass());

    // 采购单类型（生产采购订单编号时使用，例如：CG200901020002）
    public static final String CAIGOU             = "CG";
    // 入库单类型（生产入库单编号时使用，例如：RK200901020002）
    public static final String RUKU               = "RK";
    // 出库单类型（生产出库单编号时使用，例如：CK200901020002）
    public static final String CHUKU              = "CK";
    // 采购退货单类型（生产编号时使用，例如：CT200901020002）
    public static final String CAITUI             = "CT";
    // 库存退货单类型（生产编号时使用，例如：KT200901020002）
    public static final String KUTUI              = "KT";
    // 报残单：BCyyyymmdd+000000
    public static final String BAOCAN             = "BC";
    // 销售订单：XSyyyymmdd+000000
    public static final String XIAOSHOU           = "XS";
    // 移库单号
    public static final String YIKU               = "YK";
    // 外借单号
    public static final String WAIJIE             = "WJ";

    public static final String TYPE_SHOPPING_TEAM = "shopping_team";
    /**
     * Constant for cancel result String
     */
    public static final String CANCEL             = "cancel";

    /**
     * 查看全部用户权限
     */
    public static final String A_CRM_SEE_ALL_USER = "A_CRM_SEE_ALL_USER";

    /**
     * 每页记录数
     */
    protected int              pageSize           = 25;

    /**
     * 当前页
     */
    //	protected int currentPage = 1;

    @Autowired
    protected AdminService     adminService;

    @Autowired
    protected URLBroker        appServerBroker;

    protected String redirect(String url) {
        return "redirect:" + appServerBroker + url;
    }

    protected String redirectOuter(String url) {
        return "redirect:" + url;
    }

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        dateFormat.setLenient(false);
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
        binder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
    }

    /**
     * 得到管理用户
     */
    public AdminAgent getLoginAdmin(AdminAgent adminAgent) {
        return adminAgent;
    }

    /**
     * 用来判断后台用户是否已经登录
     *
     * @return
     */
    public boolean isAdminLoged(AdminAgent adminAgent) {
        if (adminAgent != null) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 判断登录用户是否有对应的一级仓库权限
     *
     * @param depFirstId
     *            Long 一级仓库ID
     * @return boolean true：有权限；false：无权限
     */
    public boolean hasDepFirstAuth(Long depFirstId, AdminAgent adminAgent) {
        if (adminAgent == null) {
            return false;
        }
        if (adminAgent.getDepfirstIds().intValue() == 0) {
            return false;
        }

        boolean isTrue = false;
        for (int i = 0; i < adminAgent.getDepfirstIds().bitLength(); i++) {
            boolean test = adminAgent.getDepfirstIds().testBit(i);
            if (test) {
                isTrue = true;
                break;
            }
        }

        if (isTrue) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 获取管理员的一级仓库ids
     */
    public List<Long> getDepfirstIdForQuery(AdminAgent adminAgent) {
        if (adminAgent == null) {
            return null;
        }

        if (adminAgent.getDepfirstIds() == null) {
            return null;
        }

        List<Long> depFirstIds = new ArrayList<Long>();
        for (int i = 0; i < adminAgent.getDepfirstIds().bitLength(); i++) {
            boolean test = adminAgent.getDepfirstIds().testBit(i);
            if (test) {
                depFirstIds.add((long) i);
            }
        }
        return depFirstIds;
    }

    /**
     * 将有查询一级仓库的权限的加入查询列表中去
     */
    public Map<String, Object> setAdminUserAuthority(Map paramMap, AdminAgent adminAgent) {
        if (adminAgent == null) {
            return null;
        }

        List<Long> depfirstIdList = getDepfirstIdForQuery(adminAgent);
        if (depfirstIdList == null || depfirstIdList.size() == 0) {
            return null;
        }
        Map<String, Object> tmpMap = new HashMap<String, Object>();
        if (null != paramMap) {
            tmpMap.putAll(paramMap);
        }
        tmpMap.put("depfirstIds", depfirstIdList);
        return tmpMap;
    }

    /**
     * 判断后台用户是否
     *
     * @return
     */
    public boolean hasAnyAuthority(String authorityNames, AdminAgent adminAgent) {
        if (adminAgent == null) {
            return false;
        }
        if (StringUtils.isBlank(authorityNames)) {
            return false;
        }
        String[] names = null;
        if (authorityNames.contains(",")) {
            names = authorityNames.split(",");
        } else {
            names = new String[] { authorityNames };
        }

        if (names != null && names.length > 0) {
            for (String name : names) {
                EnumAdminPermission enumAdmin = EnumAdminPermission.valueOf(name);
                if (enumAdmin != null && StringUtil.isNotBlank(enumAdmin.getId())) {
                    boolean test = adminAgent.getPermissions().testBit(enumAdmin.ordinal());
                    if (test) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /**
     * 判断登录用户是否有对应的一级仓库权限
     *
     * @param depFirstId
     *            Long 一级仓库ID
     * @return boolean true：有权限；flase：无权限
     * @author chenyan 2010/03/04
     */
    public boolean hasDepFirstAuth(AdminAgent agent, Long depFirstId) {
        List<Long> depfirstIdList = getDepfirstIdForQuery(agent);

        if (depfirstIdList == null) {
            return false;
        }

        return depfirstIdList.contains(depFirstId);
    }

    /**
     * 获得同组用户登录名字符串
     *
     * @return
     */
    public List<String> getSameTeamUsers(AdminAgent agent) {
        List<String> returnList = new ArrayList<String>();
        if (agent != null) {
            List<AdminTeam> adminTeamList = adminService.getListBySameTeam(agent.getUsername());
            if (adminTeamList != null && adminTeamList.size() > 0) {

                for (AdminTeam temp : adminTeamList) {
                    returnList.add(temp.getUserName());
                }
            }
        }
        return returnList;
    }

    /**
     * 判断后台登陆用户是否有分组信息
     *
     * @return
     */
    public boolean isHasTeam(AdminAgent agent) {
        if (agent != null && getSameTeamUsers(agent).size() > 0) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 判断登录人是否与记录创建人是否在同一组
     *
     * @param userName
     *            记录创建人
     * @return
     */
    public boolean isSameTeam(String userName, AdminAgent agent) {
        boolean result = false;
        if (getSameTeamUsers(agent).contains(userName)) {
            result = true;
        }
        return result;
    }

    public String getRemortIP(HttpServletRequest request) {
        if (request.getHeader("x-forwarded-for") == null) {
            return request.getRemoteAddr();
        }
        return request.getHeader("x-forwarded-for");
    }

    public String getParameter(HttpServletRequest request, String parameterName) {
        String value = request.getParameter(parameterName);
        if (value == null)
            return "";
        return value;
    }

}
