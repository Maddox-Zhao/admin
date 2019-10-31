var SHOPPINGCART_PRE = "sc_"; // Cookie中，购物车的前缀
var SHOPPINGCART_PATH = "/"; // Cookie中，购物车的Cookie的路径
function formatAsMoney(mnt) {
	mnt -= 0;
	mnt = (Math.round(mnt * 100)) / 100;
	return (mnt == Math.floor(mnt)) ? mnt + '.00' : ((mnt * 10 == Math
			.floor(mnt * 10)) ? mnt + '0' : mnt);
}
// ===========================================
// 使用方法如下：
// ===========================================
// -----------------------
// 第一步：在页面上添加以下代码
// -----------------------
// <script type='text/javascript' src='${ctx}/scripts/hs-cookie.js'></script>
// <script type='text/javascript'
// src='${ctx}/scripts/hs-shoppingCart.js'></script>
// -----------------------
// 第二步：函数调用方法：
// -----------------------
function checkInputNum(goodsId,cartId) {
	var cartGoodsNumObj;
	cartGoodsNumObj = document.getElementById('cartGoodsNumber_' + cartId);
	if (isNaN(cartGoodsNumObj.value) || cartGoodsNumObj.value < 1)
		cartGoodsNumObj.value = 1;
	cartGoodsNumObj.value = parseInt(cartGoodsNumObj.value);
	goodsNumber = document.getElementById('goodsNumber_' + cartId).value;
	if (parseInt(cartGoodsNumObj.value) >= parseInt(goodsNumber)) {
		// alert("此商品库存量不足，请修改商品数量购买数量！");
		// cartGoodsNumObj.focus();
		// return;
		cartGoodsNumObj.value = parseInt(goodsNumber);
	}
	editJqueryShoppingCartNum(goodsId,cartId);
	// calculate();
}

// 购物车的商品数量减1
function reduceOne(goodsId,cartId) {
	var cartGoodsNumObj;
	cartGoodsNumObj = document.getElementById('cartGoodsNumber_' + cartId);
	goodsNumber = document.getElementById('goodsNumber_' + cartId).value;
	if (isNaN(cartGoodsNumObj.value) || cartGoodsNumObj.value < 2)
		cartGoodsNumObj.value = 1;
	else if (parseInt(cartGoodsNumObj.value) >= parseInt(goodsNumber))
		cartGoodsNumObj.value = goodsNumber - 1;
	else
		cartGoodsNumObj.value = cartGoodsNumObj.value - 1;
	editJqueryShoppingCartNum(goodsId,cartId);
	// calculate();
}
// 购物车的商品数量加1
function increaseOne(goodsId,cartId) {
	var cartGoodsNumObj;
	cartGoodsNumObj = document.getElementById('cartGoodsNumber_' + cartId);
	if (isNaN(cartGoodsNumObj.value) || cartGoodsNumObj.value < 1)
		cartGoodsNumObj.value = 1;
	goodsNumber = document.getElementById('goodsNumber_' + cartId).value;
	if (parseInt(cartGoodsNumObj.value) >= parseInt(goodsNumber)) {
		// alert("此商品库存量不足，请修改商品数量购买数量！");
		// cartGoodsNumObj.focus();
		// return;
		cartGoodsNumObj.value = goodsNumber;
	} else {
		cartGoodsNumObj.value = parseInt(cartGoodsNumObj.value) + 1;
	}
	editJqueryShoppingCartNum(goodsId,cartId);
	// calculate();
}
// 计算总价等。并且检查库存是否充足。
function calculate() {
	var goodsPrice, marketPrice, cartGoodsNumber, goodsNumber, goodsId,cartId;
	var goodsAcount;
	var goodsPriceAcount, marketPriceAcount;
	var promationPrice,promationMarketPrice;
	var promationPriceAcount,promationmarketPriceAcount;
	var isOKofSelectedGoods = true;
	goodsPriceAcount = 0;
	marketPriceAcount = 0;
	promationPriceAcount = 0;
	promationmarketPriceAcount = 0;
	// 遍历所有商品

	var cartIdIdObjs = document.getElementsByName('cartId');
	for (var i = 0; i < cartIdIdObjs.length; i++) {
		cartId = cartIdIdObjs[i].value;
		// 检查库存量
		var cartGoodsNumObj = document.getElementById('cartGoodsNumber_' + cartId);
		if(cartGoodsNumObj){
			if (isNaN(cartGoodsNumObj.value) || cartGoodsNumObj.value < 1) {
				cartGoodsNumObj.value = 1;
				// alert("您选购的商品中,至少有一个商品数量格式不正确，请修改光标所在处的商品购买数量！");
				// document.getElementById('cartGoodsNumber_' + goodsId).focus();
				// return false;
			}
			cartGoodsNumber = document.getElementById('cartGoodsNumber_' + cartId).value;
			goodsNumber = document.getElementById('goodsNumber_' + cartId).value;
			goodsCheckBox = document.getElementById('goodsCartIdList_' + cartId);
			if (parseInt(cartGoodsNumber) > parseInt(goodsNumber)) {
				// continue;
				// alert("您选购的商品中,至少有一个商品库存量不足，请修改光标所在处的商品购买数量！");
				// document.getElementById('cartGoodsNumber_' + goodsId).focus();
				// return false;
				// 如果超过库存,就不让他结账.
				if (goodsCheckBox && goodsCheckBox.checked == true) {
					isOKofSelectedGoods = false;
				}
			}
			/*
			 * // 如果商品的数量没有变化,则隐藏保存按钮,如果有变化,就显示保存按钮 if
			 * (parseInt(document.getElementById('oldCartGoodsNumber_' +
			 * goodsId).value) == parseInt(document
			 * .getElementById('cartGoodsNumber_' + goodsId).value)) {
			 * document.getElementById('saveEditButton_' + goodsId).style.display =
			 * 'none'; } else { document.getElementById('saveEditButton_' +
			 * goodsId).style.display = ''; if (goodsCheckBox &&
			 * goodsCheckBox.checked == true) { isOKofSelectedGoods = false; } }
			 */
			if (!goodsCheckBox || goodsCheckBox.checked == false) {
				continue;
			}

			goodsPrice = document.getElementById('goodsPrice_' + cartId).value;
			if(isNaN(goodsPrice)){
			     goodsPrice='0';
			}
			marketPrice = document.getElementById('marketPrice_' + cartId).value;

			// 计算
			goodsAcount = parseFloat(goodsPrice) * parseInt(cartGoodsNumber);// 单件商品的小计
			document.getElementById('goodsAcount_' + cartId).innerHTML = formatAsMoney(goodsAcount);
		}else{
			goodsAcount = 0;// 单件商品的小计
			cartGoodsNumber=0;
		}

        if(!marketPrice){
            marketPrice=0;
        }
		//
		goodsPriceAcount = parseFloat(goodsPriceAcount)
				+ parseFloat(goodsAcount);
		marketPriceAcount = parseFloat(marketPriceAcount)
				+ parseFloat(marketPrice) * parseInt(cartGoodsNumber);
	}

//套餐屏蔽
/*
	//songfy  套餐
	var promationIdObjs = document.getElementsByName('promationId');
	for (var i = 0; i < promationIdObjs.length; i++) {
		var promationId = promationIdObjs[i].value;
		// 检查库存量

		var cartPromationNumber = document.getElementById('cartPromationNumber_' + promationId).value;

		var promationCheckBox = document.getElementById('promationIdList_' + promationId);
*/		
		/*
		 * // 如果商品的数量没有变化,则隐藏保存按钮,如果有变化,就显示保存按钮 if
		 * (parseInt(document.getElementById('oldCartGoodsNumber_' +
		 * goodsId).value) == parseInt(document
		 * .getElementById('cartGoodsNumber_' + goodsId).value)) {
		 * document.getElementById('saveEditButton_' + goodsId).style.display =
		 * 'none'; } else { document.getElementById('saveEditButton_' +
		 * goodsId).style.display = ''; if (goodsCheckBox &&
		 * goodsCheckBox.checked == true) { isOKofSelectedGoods = false; } }
		 */
//套餐屏蔽
/*		 
		if (!promationCheckBox || promationCheckBox.checked == false) {
			continue;
		}

		promationPrice = document.getElementById('promationPrice_' + promationId).value;
		promationMarketPrice = document.getElementById('promationMarketPrice_' + promationId).value;
		// 计算
		promationPriceAcount = parseFloat(promationPriceAcount) +parseFloat(promationPrice) * parseInt(cartPromationNumber);// 单件商品的小计
		promationmarketPriceAcount = parseFloat(promationmarketPriceAcount)
				+ parseFloat(promationMarketPrice) * parseInt(cartPromationNumber);
				alert(promationPriceAcount);
				alert(promationmarketPriceAcount);
	}
*/

	document.getElementById('goodsPriceAcount').innerHTML = formatAsMoney(goodsPriceAcount);
	document.getElementById('marketPriceAcount').innerHTML = formatAsMoney(marketPriceAcount);
	document.getElementById('jieshenPriceAcount').innerHTML = formatAsMoney(parseFloat(marketPriceAcount)
			- parseFloat(goodsPriceAcount));
//套餐屏蔽
//	document.getElementById('promationPriceAcount').innerHTML = formatAsMoney(promationPriceAcount);
//	document.getElementById('promationmarketPriceAcount').innerHTML = formatAsMoney(promationmarketPriceAcount);
//	document.getElementById('jieshenpromationPriceAcount').innerHTML = formatAsMoney(parseFloat(promationmarketPriceAcount)
//			- parseFloat(promationPriceAcount));
//套餐屏蔽
	var reducePrice = document.getElementById('reducePrice').value;
	if(reducePrice==''){
		reducePrice = 0;
	}
	document.getElementById('tadeTotalAmount').innerHTML = formatAsMoney((parseFloat(goodsPriceAcount)+parseFloat(promationPriceAcount)) - parseFloat(reducePrice));
//屏蔽赠送积分
//	document.getElementById('tradePoint').innerHTML = parseInt(formatAsMoney((parseFloat(goodsPriceAcount)+parseFloat(promationPriceAcount)) - parseFloat(reducePrice))/100);
	document.getElementById('tradeAmount').value = formatAsMoney(parseFloat(goodsPriceAcount)+parseFloat(promationPriceAcount));


	//购物变化后重新判断选择的套餐是否符合条件
	//var selectFullGivePromationId = document.getElementById('selectFullGivePromationId').value;
	//if(selectFullGivePromationId !='' && selectFullGivePromationId>0){
	//	fullGivePromationDwrShoppingCart(selectFullGivePromationId);
	//}

}

// 修改购物车中商品的数量
function editShoppingCartNum(goodsId,cartId) {
	var cartId, goodsNumber;
	cartId = document.getElementById('cartId_' + cartId).value;
	goodsNumber = document.getElementById('cartGoodsNumber_' + cartId).value;
	alert("goodsNumber  : "+goodsNumber);
	if (isNaN(goodsNumber) || goodsNumber < 1) {
		alert('商品的数量必须是数字！并且商品的数量必须大于0！');
		document.getElementById('cartGoodsNumber_' + goodsId).focus();
		// return false;
	}
	if (confirm('你确定要修改数量吗？'))
		location.href = 'editnum.html?id=' + cartId + '&goodsId='
				+ goodsId + '&goodsNumber=' + goodsNumber;
	// return false;
}

// 删除购物车
function removeShoppingCart(goodsId,cartId) {

	if (confirm('你确定要删除这个商品吗？'))
		location.href = 'remove.html?id=' + cartId + '&goodsId='
				+ goodsId;
	// return false;
}

// 清空购物车
function cleanShoppingCart() {
	if (confirm('你确定清空购物车吗？'))
		location.href = '/u/cleanShoppingCart.htm';
	// return false;
}

// 清除购物车中过期的商品
function removeOutShoppingCart() {
	//if (confirm('你确定要清除购物车中过期的商品吗？'))
		location.href = 'removeout.html';
	// return false;
}

// ======================================================
// DWR
// ======================================================
function removeJqueryShoppingCart(goodsId,cartId) {
	if (confirm('你确定要删除这个商品吗？')) {
		jQuery.get('/u/removeJqueryShoppingCart.htm',{param1:cartId,param2:goodsId},function(jsonMap){
			if (jsonMap != null) {
				callbackRemoveDwrShoppingCart(jsonMap, goodsId, cartId);
			}
			},'json');
		
		// 这个方法就是Action的方法，最后一个参数是这里额外加的参数，就是回调参数（在下面的这个）。
	}
}
// 回调函数，参数msg就是Action函数的返回值
function callbackRemoveDwrShoppingCart(msg, goodsId,cartId) {
	if (msg != null && msg.success != null) {
		var currRowIndex = document.getElementById("sc_tr_" + cartId).rowIndex;
		removeTableRow("list-table", currRowIndex);
		calculate();
	} else {
		alert("删除购物车失败!");
	}
}
// dwr 修改数量
function editJqueryShoppingCartNum(goodsId,cartId) {
	goodsNumber = document.getElementById('cartGoodsNumber_' + cartId).value;
	if (isNaN(goodsNumber) || goodsNumber < 1) {
		alert('商品的数量必须是数字！并且商品的数量必须大于0！');
		document.getElementById('cartGoodsNumber_' + cartId).focus();
		return;
	}
	goodsNumberBD = document.getElementById('goodsNumber_' + cartId).value;
	if (parseInt(goodsNumber) > parseInt(goodsNumberBD)) {
		alert("此商品库存量不足，请修改商品数量购买数量！");
		document.getElementById('cartGoodsNumber_' + cartId).focus();
		return;
	}
	// if (confirm('你确定要修改数量吗？')) {
		jQuery.get('/u/editJqueryShoppingCartNum.htm',{param1:cartId,param2:goodsId,param3:goodsNumber},function(jsonMap){
			callbackeditJqueryShoppingCart(jsonMap, goodsId,cartId);
			},'json');
	// }
}
// 回调函数，参数msg就是Action函数的返回值
function callbackeditJqueryShoppingCart(msg, goodsId,cartId) {
	var msgValue = msg.success;
	if (msg != null && msg.success != null) {
		document.getElementById('oldCartGoodsNumber_' + cartId).value = document
				.getElementById('cartGoodsNumber_' + cartId).value;
		// document.getElementById('saveEditButton_' + goodsId).style.display =
		// 'none';
		calculate();
	} else
		alert("修改购物车失败!");
}
// ==============================================
// 以下是操作购物车的Cookie
// ==============================================

/**
 * 如果存在相同的商品在Cookie中，先相加，再保存到Cookie中。
 *
 * @param {}
 *            goodsId
 * @param {}
 *            num
 */
function addShoppingCartCookie(goodsId, num) {
	var name, value, expires, path, domain, secure;
	path = SHOPPINGCART_PATH;
	name = SHOPPINGCART_PRE + goodsId;
	var numOld = getCookieValue(name)
	value = parseInt(num) + parseInt(numOld);
	setCookie(name, value, expires, path, domain, secure);
}

/**
 * 保存到Cookie中,如果已经存在相同的商品,则会覆盖.
 *
 * @param {}
 *            goodsId
 * @param {}
 *            num
 */
function saveShoppingCartCookie(goodsId, num) {
	var name, value, expires, path, domain, secure;
	name = SHOPPINGCART_PRE + goodsId;
	value = num;
	setCookie(name, value, expires, path, domain, secure);
}

/**
 * (没实现)重新保存购物车集,此操作会先清空Cookie中的购物车,再重新保存.
 *
 * @param {}
 *            ShoppingCartList
 */
function reSaveShoppingCartListCookie(ShoppingCartList) {
	// removeAllShoppingCart();
	alert("此功能还没开发完成.");
}

/**
 * 从Cookie中移除一个购物车
 *
 * @param {}
 *            goodsId
 */
function removeShoppingCartCookie(goodsId) {
	var name, path, domain;
	path = SHOPPINGCART_PATH;
	name = SHOPPINGCART_PRE + goodsId;
	deleteCookie(name, path, domain)
}

/**
 * 清空全部购物车
 *
 * @param {}
 *            goodsId
 */
function removeAllShoppingCartCookie() {

}

function selectAllCheckBox(obj, chk) {
	if (chk == null) {
		chk = 'goodsCartIdList';
	}

	var elems = obj.form.getElementsByTagName("INPUT");

	for (var i = 0; i < elems.length; i++) {
		if (elems[i].name == chk) {
			elems[i].checked = obj.checked;
		}
	}
	calculate();
}


//songfy 全场套餐活动
function fullGivePromationDwrShoppingCart(promationId) {
	// if (confirm('你确定要修改数量吗？')) {
	var amount = document.getElementById('tradeAmount').value;
	ShoppingCartAction.fullGivePromationDwrShoppingCart(promationId,amount,
			function(msg) {
				callbackFullGivePromationDwrShoppingCart(msg, promationId);
			});
}


function callbackFullGivePromationDwrShoppingCart(msg, promationId) {
	var msgType = msg.substring(msg.indexOf("\'") + 1, msg.indexOf("\',"));
	var msgValue = msg.substring(msg.indexOf("\',\'") + 3, msg
					.lastIndexOf("\']"));
	document.getElementById('fullGiveMsg').style.display = "";
	if (msgType == 'give') {
		document.getElementById('fullGiveMsg').innerHTML = "<ol><li>活动选择成功</li></ol><div class=\"clearing\"></div>";
		document.getElementById('selectFullGivePromationId').value = promationId;
		document.getElementById('fullGivePromationId_'+promationId).checked = true;
		document.getElementById('reducePrice').value = 0;
		var totalAmount = parseFloat(document.getElementById('tradeAmount').value);
		document.getElementById('tadeTotalAmount').innerHTML = formatAsMoney(totalAmount);
//屏蔽赠送积分
//		document.getElementById('tradePoint').innerHTML = parseInt(totalAmount/100);
	}else if (msgType == 'reduce') {
		document.getElementById('fullGiveMsg').innerHTML = "<ol><li>活动选择成功</li></ol><div class=\"clearing\"></div>";
		document.getElementById('selectFullGivePromationId').value = promationId;
		document.getElementById('reducePrice').value = msgValue;
		var totalAmount = parseFloat(document.getElementById('tradeAmount').value)-parseFloat(msgValue);
		document.getElementById('tadeTotalAmount').innerHTML = formatAsMoney(totalAmount);
//屏蔽赠送积分
//		document.getElementById('tradePoint').innerHTML = parseInt(totalAmount/100);
		document.getElementById('fullGivePromationId_'+promationId).checked = true;

	} else{
		document.getElementById('fullGiveMsg').innerHTML = "<ol><li>你当前的购物金额不能享受选择的活动</li></ol><div class=\"clearing\"></div>";
		document.getElementById('fullGivePromationId_'+promationId).checked = false;
		document.getElementById('reducePrice').value = 0;
		var totalAmount = parseFloat(document.getElementById('tradeAmount').value);
		document.getElementById('tadeTotalAmount').innerHTML = formatAsMoney(totalAmount);
//屏蔽赠送积分
//		document.getElementById('tradePoint').innerHTML = parseInt(totalAmount/100);
	}
	// alert("edit false; [" + msgValue + "]");
}

//删除购物车套餐(此方法未使用 chenyan 2010/01/26)
function removeDwrShoppingCartPromation(promationId,timeTag) {
	if (confirm('你确定要删除这个套餐吗？')) {
		ShoppingCartAction.removeDwrShoppingCartPromation(promationId, timeTag,
				function(msg) {
					callbackRemoveDwrShoppingCartPromation(msg, promationId,timeTag);
				});
		// 这个方法就是Action的方法，最后一个参数是这里额外加的参数，就是回调参数（在下面的这个）。
	}
}
// 回调函数，参数msg就是Action函数的返回值(此方法未使用 chenyan 2010/01/26)
function callbackRemoveDwrShoppingCartPromation(msg, promationId,timeTag) {
	var msgType = msg.substring(msg.indexOf("\'") + 1, msg.indexOf("\',"));
	var msgValue = msg.substring(msg.indexOf("\',\'") + 3, msg
					.lastIndexOf("\']"));

	if (msgType == 'true') {
		var currRowIndex = document.getElementById("sc_tr_" + promationId+timeTag).rowIndex;
		// alert(msgValue);
		removeTableRow("list-table-promation", currRowIndex);
		calculate();
	} else
		alert("删除购物车失败!");
}

// 批发总报价单的商品数量减1
function pfreduceOne(goodsInstanceId,cartId,goodsId) {
    jQuery("p[name='goodsWholsale_"+goodsId+"']").removeClass();
    jQuery("span[name='Wholsale_"+goodsId+"']").removeClass();

	var cartGoodsNumObj;
	cartGoodsNumObj = document.getElementById('cartGoodsNumber_' + cartId);
	if (isNaN(cartGoodsNumObj.value) || cartGoodsNumObj.value < 1){
	    cartGoodsNumObj.value = 0;
	}else{
	    cartGoodsNumObj.value = cartGoodsNumObj.value - 1;   
	}
	editJqueryPfShoppingCartNum(goodsInstanceId,cartId,goodsId);
}
// 批发总报价单的商品数量加1
function pfincreaseOne(goodsInstanceId,cartId,goodsId) {
   jQuery("p[name='goodsWholsale_"+goodsId+"']").removeClass();
   jQuery("span[name='Wholsale_"+goodsId+"']").removeClass();

	var cartGoodsNumObj;
	cartGoodsNumObj = document.getElementById('cartGoodsNumber_' + cartId);
	if (isNaN(cartGoodsNumObj.value) || cartGoodsNumObj.value < 0){
	   cartGoodsNumObj.value = 0;  
	}
	cartGoodsNumObj.value = parseInt(cartGoodsNumObj.value) + 1;
	editJqueryPfShoppingCartNum(goodsInstanceId,cartId,goodsId);
}
//批发修改数量
function editJqueryPfShoppingCartNum(goodsInstanceId,cartId,goodsId) {
	goodsNumber = document.getElementById('cartGoodsNumber_' + cartId).value;
	if (isNaN(goodsNumber) || goodsNumber < 0) {
		alert('商品的数量必须是数字！并且商品的数量必须大于等于0！');
		document.getElementById('cartGoodsNumber_' + cartId).focus();
		return;
	}
	jQuery.post('editJqueryPfShoppingCartNum.html',{param1:cartId,param2:goodsInstanceId,param3:goodsNumber},function(jsonMap){
		callbackeditpfJqueryShoppingCart(jsonMap, goodsInstanceId,cartId,goodsId);
	},'json');
}
// 回调函数
function callbackeditpfJqueryShoppingCart(msg, goodsInstanceId,cartId,goodsId) {
	var msgValue = msg.success;
	if (msg != null && msg.success != null) {
		pfcalculate(goodsId);
	} else
		alert("修改购物车失败!");
}

function pfcalculate(goodsId) {
   var oneGoodsNum;
   var onePrice;
   var totalNum = 0;
   var totalPrice = 0;
	var allGoodsNum = document.getElementsByName('goodsId_' + goodsId);
	for (var i = 0; i < allGoodsNum.length; i++) {
       oneGoodsNum = allGoodsNum[i].value;
       totalNum += oneGoodsNum*1;
    }
    var goodsCheck = document.getElementById('goodsCartIdList_' + goodsId);
    jQuery.post('choiceWholeLevel.html',{param1:goodsId,param2:totalNum},function(goodsWholsale){
        if(goodsWholsale.wholesaleLevel=="0"){
           alert("商品总数量小于起批数量!");
           document.getElementById('goodsPrice_'+goodsId).innerHTML=0;
           document.getElementById('allPrice_'+goodsId).value = 0;
		   var totalpfPrice = document.getElementById('tadeTotalAmount');
		   var allPrices = document.getElementsByName('allPrice');
	       for (var i = 0; i < allPrices.length; i++) {
             onePrice = allPrices[i].value;
             totalPrice += onePrice*1;
           }
           totalpfPrice.innerHTML = totalPrice.toFixed(2)+"元";
           return;
		}else if(goodsWholsale.wholesaleLevel=="1"){
		   document.getElementById(goodsId+'1').className = "selected";
		   document.getElementById('1'+goodsId).className = "pfj";
           document.getElementById('goodsPrice_'+goodsId).innerHTML=goodsWholsale.wholesalePrice;
           document.getElementById('allPrice_'+goodsId).value = goodsWholsale.wholesalePrice;
		}else if(goodsWholsale.wholesaleLevel=="2"){
		   document.getElementById(goodsId+'2').className = "selected";
		   document.getElementById('2'+goodsId).className = "pfj";
           document.getElementById('goodsPrice_'+goodsId).innerHTML=goodsWholsale.wholesalePrice;
           document.getElementById('allPrice_'+goodsId).value = goodsWholsale.wholesalePrice;
		}else if(goodsWholsale.wholesaleLevel=="3"){
		   document.getElementById(goodsId+'3').className = "selected";
		   document.getElementById('3'+goodsId).className = "pfj";
           document.getElementById('goodsPrice_'+goodsId).innerHTML=goodsWholsale.wholesalePrice;
           document.getElementById('allPrice_'+goodsId).value = goodsWholsale.wholesalePrice;
		}
		var totalpfPrice = document.getElementById('tadeTotalAmount');
		var pfTotalAmount = document.getElementById('pfTotalAmount');
		var allGoodsCheck = document.getElementsByName('goodsCartIdList');
		for(var i = 0; i < allGoodsCheck.length; i++){
           if(allGoodsCheck[i].checked == true){
               var tempGoodsId = allGoodsCheck[i].value;
               onePrice = document.getElementById('allPrice_'+tempGoodsId).value;
               totalPrice += onePrice*1;
           }		   
		}
        totalpfPrice.innerHTML = totalPrice.toFixed(2)+"元";
        pfTotalAmount.value = totalPrice.toFixed(2);
    },'json');
}

function checkpfInputNum(goodsInstanceId,cartId,goodsId) {
   jQuery("p[name='goodsWholsale_"+goodsId+"']").removeClass();
   jQuery("span[name='Wholsale_"+goodsId+"']").removeClass();
	var cartGoodsNumObj;
	cartGoodsNumObj = document.getElementById('cartGoodsNumber_' + cartId);
	if (cartGoodsNumObj.value == "" ||isNaN(cartGoodsNumObj.value) || cartGoodsNumObj.value < 0 ){
	    cartGoodsNumObj.value = 0;
	}
	cartGoodsNumObj.value = parseInt(cartGoodsNumObj.value);
	editJqueryPfShoppingCartNum(goodsInstanceId,cartId,goodsId);
}

function changeZero(goodsInstanceId,cartId,goodsId){
   jQuery("p[name='goodsWholsale_"+goodsId+"']").removeClass();
   jQuery("span[name='Wholsale_"+goodsId+"']").removeClass();
	var cartGoodsNumObj;
	cartGoodsNumObj = document.getElementById('cartGoodsNumber_' + cartId);
	cartGoodsNumObj.value = 0;
	editJqueryPfShoppingCartNum(goodsInstanceId,cartId,goodsId);
}

function checkpfcalculate(goodsId){
  var pfTotal = document.getElementById('pfTotalAmount')
  var pfTotalAmount = document.getElementById('pfTotalAmount').value;
  var totalpfPrice = document.getElementById('tadeTotalAmount');
  var goodsCheck = document.getElementById('goodsCartIdList_' + goodsId);
  var allPrice = document.getElementById('allPrice_' + goodsId);
  if (goodsCheck && goodsCheck.checked == true){
     var priceTemp = allPrice.value;
     pfTotalAmount = priceTemp*1 + pfTotalAmount*1;
     totalpfPrice.innerHTML = pfTotalAmount.toFixed(2)+"元";
     pfTotal.value = pfTotalAmount.toFixed(2);
  }else{
     var priceTemp = allPrice.value;
     pfTotalAmount -= priceTemp*1;
     totalpfPrice.innerHTML = pfTotalAmount.toFixed(2)+"元";
     pfTotal.value = pfTotalAmount.toFixed(2);
  }
}

function selectAllpfCheckBox(obj, chk) {
	if (chk == null) {
		chk = 'goodsCartIdList';
	}

	var elems = obj.form.getElementsByTagName("INPUT");

	for (var i = 0; i < elems.length; i++) {
		if (elems[i].name == chk) {
			elems[i].checked = obj.checked;
		}
	}
	
	var pfTotalAmount = document.getElementById('pfTotalAmount').value;
	var totalpfPrice = document.getElementById('tadeTotalAmount');
	if(obj.checked == true){
	   totalpfPrice.innerHTML = pfTotalAmount+"元";
	   var totalPrice = 0;
	   var allPrices = document.getElementsByName('allPrice');
	   for (var i = 0; i < allPrices.length; i++) {
             var onePrice = allPrices[i].value;
             totalPrice += onePrice*1;
       }
       pfTotalAmount = totalPrice;
       document.getElementById('pfTotalAmount').value = totalPrice;
       totalpfPrice.innerHTML = pfTotalAmount.toFixed(2)+"元";
	}else{
	   totalpfPrice.innerHTML = "0 元";
	   document.getElementById('pfTotalAmount').value = 0;
	}
}
function removeZpGoods(cartId){
  if (confirm('确定不要这个商品的赠品了吗？')) {
       document.getElementById("toOrderSubmitPage").action="removeZpGoods.html?cartId="+cartId;
       document.getElementById("toOrderSubmitPage").submit();
  }
}
