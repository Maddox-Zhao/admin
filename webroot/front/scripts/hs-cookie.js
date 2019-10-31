// 增加一个名为 trim 的函数作为
// String 构造函数的原型对象的一个方法。
String.prototype.trim = function() {
	// 用正则表达式将前后空格
	// 用空字符串替代。
	return this.replace(/(^\s*)|(\s*$)/g, "");
}
// 以下是通用函数

function getCookieValue(name) {
	var start = document.cookie.indexOf(name + "=");
	var len = start + name.length + 1;
	if ((!start) && (name != document.cookie.substring(0, name.length))) {
		return null;
	}
	if (start == -1)
		return null;
	var end = document.cookie.indexOf(';', len);
	if (end == -1)
		end = document.cookie.length;
	return unescape(document.cookie.substring(len, end));
}

function setCookie(name, value, expires, path, domain, secure) {
	var today = new Date();
	today.setTime(today.getTime());
	if (expires) {
		expires = expires * 1000 * 60 * 60 * 24;
	}
	var expires_date = new Date(today.getTime() + (expires));
	document.cookie = name + '='
			+ escape(value)
			+ ((expires) ? ';expires=' + expires_date.toGMTString() : '')
			+ // expires.toGMTString()
			((path) ? ';path=' + path : '')
			+ ((domain) ? ';domain=' + domain : '')
			+ ((secure) ? ';secure' : '');
}

function deleteCookie(name, path, domain) {
	if (getCookieValue(name))
		document.cookie = name + '=' + ((path) ? ';path=' + path : '')
				+ ((domain) ? ';domain=' + domain : '')
				+ ';expires=Thu, 01-Jan-1970 00:00:01 GMT';
}

// 清空所有Cookie
function cleanCookie() {
	var domain, path;
	path = "/";
	var cookieStr = document.cookie;
	var cookieArr = cookieStr.split("; ");
	for (var i = 0; i < cookieArr.length; i++) {
		var tmpArray = cookieArr[i].split("=");
		document.cookie = tmpArray[0] + '=' + ((path) ? ';path=' + path : '')
				+ ((domain) ? ';domain=' + domain : '')
				+ ';expires=Thu, 01-Jan-1970 00:00:01 GMT';
	}
}
// 清空Cookie的Name前缀为preStr的Cookie
function cleanCookieByPre(preStr) {
	var domain, path;
	path = "/";
	var cookieStr = document.cookie;
	var cookieArr = cookieStr.trim().split("; ");

	var pos = -1;
	for (var i = 0; i < cookieArr.length; i++) {
		var tmpArray = cookieArr[i].trim().split("=");
		pos = tmpArray[0].trim().indexOf(preStr);
		if (pos == 0) {
			document.cookie = tmpArray[0] + '='
					+ ((path) ? ';path=' + path : '')
					+ ((domain) ? ';domain=' + domain : '')
					+ ';expires=Thu, 01-Jan-1970 00:00:01 GMT';
		}
	}
}

/**
 * 获取所有比较队列中的商品
 * 
 * @return {}
 *         返回Cookies数组,结构为:[['name1','value1'],['name2','value2'],['name3','value3']...]
 */
function getAllCookie() {
	var cookieStr = document.cookie;
	var cookieArr = cookieStr.trim().split("; ");
	var cookiesArray = new Array();
	var cookiesCount = 0;

	for (var i = 0; i < cookieArr.length; i++) {
		var tmpArray = cookieArr[i].trim().split("=");
		if (!tmpArray || tmpArray.length < 2) {
			continue;
		}
		var cookieTmp = new Array(2);
		var name = tmpArray[0].trim();
		var value = unescape(tmpArray[1]).trim();
		cookieTmp[0] = name;
		cookieTmp[1] = value;
		cookiesArray[cookiesCount++] = cookieTmp;
	}

	return cookiesArray;
}
/**
 * 获取Cookie的Name前缀为preStr的Cookie数组
 * 
 * @param {}
 *            preStr Cookie的Name前缀
 * @return {}
 *         返回Cookies数组,结构为:[['name1','value1'],['name2','value2'],['name3','value3']...]
 */
function getAllCookieByPre(preStr) {
	var cookieStr = document.cookie;
	var cookieArr = cookieStr.trim().split("; ");
	var cookiesArray = new Array();
	var cookiesCount = 0;

	var pos = -1;
	for (var i = 0; i < cookieArr.length; i++) {
		var tmpArray = cookieArr[i].trim().split("=");
		if (!tmpArray || tmpArray.length < 2) {
			continue;
		}
		var name = tmpArray[0].trim();
		pos = name.indexOf(preStr);
		if (pos == 0) {
			var value = unescape(tmpArray[1]).trim();
			var cookieTmp = new Array(2);
			cookieTmp[0] = name;
			cookieTmp[1] = value;
			cookiesArray[cookiesCount++] = cookieTmp;
		}
	}

	return cookiesArray;
}

/**
 * 通过Cookie的Name,查出其值.
 * 
 * @param {string}
 *            name
 * @return {String}
 */
function getCookieValue1(name) {
	var cookiesArray = getAllCookie();
	if (!cookiesArray || cookiesArray.length < 1) {
		return "";
	}
	for (var index = 0; index < cookiesArray.length; index++) {
		var cookies = cookiesArray[index];
		if (cookies && cookies.length > 1) {
			return cookies[1].trim();
		}
	}
	return "";
}
