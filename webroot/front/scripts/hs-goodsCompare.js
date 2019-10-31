var GOODSCOMPARE_COOKIE_PRE = "gcp_";// Cookie中商品比较列表的前缀
var MAX_GOODS_IN_COMPARE = 5; // 最多可以进行几个商品进行比较
var button_compare = "开始比较";
var compareBox;
var compareList;
var showBox = "y";
// ===========================================
// 使用方法如下：
// ===========================================
// -----------------------
// 第一步：在页面上添加以下代码
// -----------------------
// <link href="$!ctx/styles/simplicity/goodsCompare.css" type="text/css"
// rel="stylesheet">
// <script type='text/javascript' src='${ctx}/scripts/hs-cookie.js'></script>
// <script type='text/javascript'
// src='${ctx}/scripts/hs-goodsCompare.js'></script>
// <SCRIPT LANGUAGE="JavaScript">
// // 先到自己的页面里定义一下这个变量，指定比较页面的地址
// var compareUrl = "$!ctx/l/compare.html";
// 如果要想页面上不出现浮动的比较框,请在页面JS里加上:
// var showBox="n";
// </SCRIPT>
// 网页中不能自动出现浮动的比较框，请手动在页面上使用这个代码：<body onload="showCompareBox();">

// -----------------------
// 第二步：函数调用方法：
// -----------------------
// 添加商品到比较队列中：goodsId=商品的ID;goodsTitle=商品的名字;goodsCatCode=商品的类别;
// addGoodsCompare(goodsId, goodsTitle, goodsCatCode)

// 删除队列中一个商品
// removeGoodsCompare(goodsId)

// 清空队列
// cleanGoodsCompare()

// 获取所有比较队列中的商品
// getAllGoodsCompare()

// ============================================
if (document.all) {
	window.attachEvent('onload', showCompareBox)// IE中
} else {
	window.addEventListener('load', showCompareBox, false);// firefox
}
// =============================================

// 设置一个Div浮动在页面上
function steDivFloatOnTop(div_id) {
	document.getElementById(div_id).style.position = "absolute";
	var y = 200;
	var y_top = document.documentElement.scrollTop;
	var diff = (y_top + y - document.getElementById(div_id).offsetTop) * 0.20;
	var y = y_top + y - diff;
	document.getElementById(div_id).style.top = y + "px";
	setTimeout("steDivFloatOnTop('" + div_id + "');", 20);
}

// 创建比较队列的浮动层 showBox:[y/n]:标记是否显示比较的浮动框,不带参数默认为y
function showCompareBox() {
	var goodsCompareArray = getAllGoodsCompare();
	if (!showBox || showBox != "n")
		showBox = "y";
	if (!compareBox) {
		compareBox = document.createElement("DIV");
		var submitBtn = document.createElement("INPUT");
		compareList = document.createElement("UL");
		var closeBtn = document.createElement("A");
		compareBox.id = "compareBox";
		compareBox.style.display = "none";
		compareBox.style.top = "200px";
		compareBox.align = "center";
		compareList.id = "compareList";
		submitBtn.type = "button";
		// submitBtn.value = button_compare;
		compareBox.appendChild(compareList);
		compareBox.appendChild(submitBtn);
		compareBox.appendChild(closeBtn);
		closeBtn.onclick=function(){
			cleanGoodsCompare();
		}
		submitBtn.onclick = function() {
			goodsCompareArray = getAllGoodsCompare();
			var i = goodsCompareArray.length;
			if (i < 2) {
				alert("您没有选定任何需要比较的商品或者比较的商品数少于2个");
				return;
			}
			if (i > MAX_GOODS_IN_COMPARE) {
				alert("最多可以比较 [" + MAX_GOODS_IN_COMPARE + "] 个商品。目前有 ["
						+ goodsCompareArray.length + "] 个商品在比较队列");
				return;
			}
			window.open(compareUrl);
		}
		document.body.appendChild(compareBox);
	}
	compareList.innerHTML = "";

	if (goodsCompareArray.length > 0) {
		for (var i = 0; i < goodsCompareArray.length; i++) {
			var goodsCompare = goodsCompareArray[i];
			var li = document.createElement("LI");
			var span = document.createElement("SPAN");
			span.innerHTML = goodsCompare[2];
			li.appendChild(span);
			li.style.listStyle = "none";
			var delBtn = document.createElement("IMG");
			// 删除按钮
			delBtn.src = "../images/compareDrop.gif";
			delBtn.className = goodsCompare[0];
			delBtn.onclick = function() {
//				 document.getElementById("compareList").removeChild(this.parentNode);
				removeGoodsCompare(this.className);
			}
			li.insertBefore(delBtn, li.childNodes[0]);
			compareList.appendChild(li);
		}
	}

	if (showBox != 'n' && compareList.childNodes.length > 0) {
		compareBox.style.display = "";
		steDivFloatOnTop("compareBox");
	} else {
		compareBox.style.display = "none";
	}
}

// 清空队列
function cleanGoodsCompare() {
	cleanCookieByPre(GOODSCOMPARE_COOKIE_PRE);
	showCompareBox();
}

// 删除队列中一个商品
function removeGoodsCompare(goodsId, showBox) {
	var name, domain, path;
	// 保存到Cookie中
	name = "gcp_" + goodsId;
	path = "/";
	deleteCookie(name, path, domain);
	showCompareBox();
}


// 添加商品到比较队列中：goodsId=商品的ID;goodsTitle=商品的名字;goodsCatCode=商品的类别;
function addGoodsCompare(goodsId, goodsTitle, goodsCatCode, showBox) {
	var name, value, expires, path, domain, secure;
	// 判断这个商品的类别是否和队列中的商品同属一个类别，只能比较同类别的商品
	var goodsCompareArray = getAllGoodsCompare();
	var i = goodsCompareArray.length;
	showCompareBox();
	if (i >= MAX_GOODS_IN_COMPARE) {
		alert("最多可以比较 [" + MAX_GOODS_IN_COMPARE + "] 个商品。目前有 ["
				+ goodsCompareArray.length + "] 个商品在比较队列");
		return;
	}
	for (var i = 0; i < goodsCompareArray.length; i++) {
		var goodsCompare = goodsCompareArray[i];
		/*
		 * if (goodsCompare[1] != goodsCatCode) { alert("[" + goodsTitle + "]
		 * 与所选择的在比较队列中的商品不是同一类别，无法进行比较。"); return; }
		 */
		if (goodsCompare[0] == goodsId) {
			alert("[" + goodsTitle + "] 已经在比较队列中。");
			return;
		}
	}
	// 保存到Cookie中
	name = "gcp_" + goodsId;
	value = goodsCatCode + "_" + goodsTitle;
	path = "/";
	setCookie(name, value, expires, path, domain, secure);
	showCompareBox();
}
// 获取所有比较队列中的商品
function getAllGoodsCompare() {
	var goodsCompareArray = new Array();
	var goodsCount = 0;

	var cookiesArray = getAllCookieByPre(GOODSCOMPARE_COOKIE_PRE);
	for (var i = 0; i < cookiesArray.length; i++) {
		var cookies = cookiesArray[i];
		var goodsCompare = new Array(3);
		var name = cookies[0];
		var value = cookies[1];
		var goodsId = name.substring(GOODSCOMPARE_COOKIE_PRE.length);
		var goodsCatCode = value.substring(0, value.indexOf("_"));
		var goodsTitle = value.substring(value.indexOf("_") + 1);
		goodsCompare[0] = (goodsId) ? goodsId : 0;
		goodsCompare[1] = (goodsCatCode) ? goodsCatCode : "";
		goodsCompare[2] = (goodsTitle) ? goodsTitle : "";
		goodsCompareArray[goodsCount++] = goodsCompare;
	}

	return goodsCompareArray;
}
