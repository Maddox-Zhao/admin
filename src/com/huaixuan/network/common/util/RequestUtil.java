package com.huaixuan.network.common.util;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Convenience class for setting and retrieving cookies.
 */
public final class RequestUtil {
    private static final Log log = LogFactory.getLog(RequestUtil.class);

    /**
     * Checkstyle rule: utility classes should not have public constructor
     */
    public RequestUtil() {
    }

    /**
     * Convenience method to set a cookie
     *
     * @param response the current response
     * @param name the name of the cookie
     * @param value the value of the cookie
     * @param path the path to set it on
     */
    public static void setCookie(HttpServletResponse response, String name, String value,
                                 String path) {
        if (log.isDebugEnabled()) {
            log.debug("Setting cookie '" + name + "' on path '" + path + "'");
        }

        Cookie cookie = new Cookie(name, value);
        cookie.setSecure(false);
        cookie.setPath(path);
        cookie.setMaxAge(3600 * 24 * 30); // 30 days

        response.addCookie(cookie);
    }

    /**
     * Convenience method to get a cookie by name
     *
     * @param request the current request
     * @param name the name of the cookie to find
     *
     * @return the cookie (if found), null if not found
     */
    public static Cookie getCookie(HttpServletRequest request, String name) {
        Cookie[] cookies = request.getCookies();
        Cookie returnCookie = null;

        if (cookies == null) {
            return returnCookie;
        }

        for (Cookie thisCookie : cookies) {
            if (thisCookie.getName().equals(name)) {
                // cookies with no value do me no good!
                if (!thisCookie.getValue().equals("")) {
                    returnCookie = thisCookie;

                    break;
                }
            }
        }

        return returnCookie;
    }

    public static int getGoodsNumByIdInCookie(HttpServletRequest request, String name) {
        Cookie[] cookies = request.getCookies();
        int num = 0;

        if (cookies == null) {
            return num;
        }

        for (Cookie thisCookie : cookies) {
            if (thisCookie.getName().equals(name)) {
                // cookies with no value do me no good!
                if (!thisCookie.getValue().equals("")) {
                    num = Integer.parseInt(thisCookie.getValue());

                    break;
                }
            }
        }

        return num;
    }

    /**
     * Convenience method for deleting a cookie by name
     *
     * @param response the current web response
     * @param cookie the cookie to delete
     * @param path the path on which the cookie was set (i.e. /appfuse)
     */
    public static void deleteCookie(HttpServletResponse response, Cookie cookie, String path) {
        if (cookie != null) {
            // Delete the cookie by setting its maximum age to zero
            cookie.setMaxAge(0);
            cookie.setPath(path);
            response.addCookie(cookie);
        }
    }

    /**
     * Convenience method to get the application's URL based on request
     * variables.
     *
     * @param request the current request
     * @return URL to application
     */
    public static String getAppURL(HttpServletRequest request) {
        StringBuffer url = new StringBuffer();
        int port = request.getServerPort();
        if (port < 0) {
            port = 80; // Work around java.net.URL bug
        }
        String scheme = request.getScheme();
        url.append(scheme);
        url.append("://");
        url.append(request.getServerName());
        if ((scheme.equals("http") && (port != 80)) || (scheme.equals("https") && (port != 443))) {
            url.append(':');
            url.append(port);
        }
        url.append(request.getContextPath());
        return url.toString();
    }

    public static String getFullRequestURL(HttpServletRequest request) {

        StringBuffer url_buffer = request.getRequestURL();
        String queryString = request.getQueryString();
        if (queryString != null && queryString.trim().length() > 0) {
            url_buffer.append("?");
            url_buffer.append(queryString);
        }
        return url_buffer.toString();
    }

    public static String replacePageURL(HttpServletRequest request, int page)
                                                                             throws UnsupportedEncodingException {
        return replacePageURL(request, "currentPage", page);
    }

    public synchronized static String replacePageURL(HttpServletRequest request, String pageName,
                                                     int page) throws UnsupportedEncodingException {
        String surl = request.getRequestURL().toString();
        java.util.Enumeration enumParam = request.getParameterNames();
        boolean first = true;
        boolean addPageParam = false;
        if (enumParam.hasMoreElements()) {
            surl += "?";
        } else {
            addPageParam = true;
            first = false;
            surl += "?" + pageName + "=" + page;
        }
        while (enumParam.hasMoreElements()) {
            String name = (String) enumParam.nextElement();
            if (!name.equals(pageName)) {
                if (!first)
                    surl += "&";
                surl += name + "=" + request.getParameter(name);
                first = false;
            } else {
                if (!first)
                    surl += "&";
                surl += pageName + "=" + page;
                first = false;
                addPageParam = true;
            }
        }
        if (!addPageParam) {
            if (!first)
                surl += "&";
            surl += pageName + "=" + page;
        }
        //  String temp = URLEncoder.encode(surl, "utf-8");
        return surl;

    }
    /***
     * 获取URL路径（除去ServletPath部分）
     *
     * The returned String contains a protocol, server name, port number,
     * doesn't contains server path and query string parameters.
     *
     * @param request
     * @return resultUrl
     *
     */
    public static String getUrlContext(HttpServletRequest request) {

		String fullUrl = request.getRequestURL().toString();
		String sevletPath = request.getServletPath();
		int ii = fullUrl.indexOf(sevletPath);
		String resultUrl = fullUrl.substring(0, ii);

		return resultUrl;
	}

}
