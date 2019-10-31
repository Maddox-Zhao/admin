window.alertDialog = function(txt) {

	var alertFram = document.createElement("DIV");
	document.body.appendChild(alertFram);

	var shield = document.createElement("DIV");
	document.body.appendChild(shield);
	shield.id = "shield";
	shield.className = "shield_div";
	sstrHtml="<iframe class='iframe'>";
	sstrHtml += "</iframe>";
	shield.innerHTML = sstrHtml;
	alertFram.id = "car_alertFram";
	alertFram.style.marginLeft = -225+document.documentElement.scrollLeft+"px";
	alertFram.style.marginTop = -75+document.documentElement.scrollTop+"px";
	alertFram.className = "alert_div";
	strHtml = "<div id='car_buy_tc_bg'>";
	strHtml += " <div class='tc_title'>";
	strHtml += " <input type='button' onclick='doClose()' id='do_close' class='tc_bt01'></input>";
	strHtml += " </div>";
    strHtml += " <div class='tc_con2'>"+txt;
    strHtml += "<p><input onclick='doOk()' id='do_OK' type='button' class='tc_bt02'></input></p>";
    strHtml += " </div>";
	strHtml += "</div>";
	alertFram.innerHTML = strHtml;
	    this.doClose = function() {
        alertFram.style.display = "none";
        shield.style.display = "none";
}
    	 this.doClose = function(){
        document.body.removeChild(alertFram);
        document.body.removeChild(shield);
        document.body.onselectstart = function(){return true;}
        document.body.oncontextmenu = function(){return true;}
    }
    this.doOk = function() {
        alertFram.style.display = "none";
        shield.style.display = "none";
    }
	this.doOk = function(){
        document.body.removeChild(alertFram);
        document.body.removeChild(shield);
        document.body.onselectstart = function(){return true;}
        document.body.oncontextmenu = function(){return true;}
    }
    document.body.onselectstart = function(){return false;}
    document.body.oncontextmenu = function(){return false;}
}


window.alert = function(txt) {

	var alertFram = document.createElement("DIV");
	document.body.appendChild(alertFram);

	var shield = document.createElement("DIV");
	document.body.appendChild(shield);
	shield.id = "shield";
	shield.style.width =  screen.width+"px";
	shield.style.height = document.body.scrollHeight+"px";
	shield.className = "shield_div";
	sstrHtml="<iframe class='iframe'>";
	sstrHtml += "</iframe>";
	shield.innerHTML = sstrHtml;
	alertFram.id = "alertFram";
	alertFram.style.marginLeft = -25+document.documentElement.scrollLeft+"px";
	alertFram.style.marginTop = -75+document.documentElement.scrollTop+"px";
	alertFram.className = "alert_div";
	strHtml = "<div id='tc_bg'>";
	strHtml += " <div class='tc_title'>";
	strHtml += " <input type='button' onclick='doClose()' id='do_close' class='tc_bt01'></input>";
	strHtml += " </div>";
    strHtml += " <div class='tc_con2'>"+txt;
    strHtml += "<p><input onclick='doOk()' id='do_OK' type='button' class='tc_bt02'></input></p>";
    strHtml += " </div>";
	strHtml += "</div>";
	alertFram.innerHTML = strHtml;
	    this.doClose = function() {
        alertFram.style.display = "none";
        shield.style.display = "none";
    }
	 this.doClose = function(){
        document.body.removeChild(alertFram);
        document.body.removeChild(shield);
        document.body.onselectstart = function(){return true;}
        document.body.oncontextmenu = function(){return true;}
    }
    this.doOk = function() {
        alertFram.style.display = "none";
        shield.style.display = "none";
    }
	this.doOk = function(){
        document.body.removeChild(alertFram);
        document.body.removeChild(shield);
        document.body.onselectstart = function(){return true;}
        document.body.oncontextmenu = function(){return true;}
    }
    document.body.onselectstart = function(){return false;}
    document.body.oncontextmenu = function(){return false;}
}

window.alertSuccess = function(txt) {

    var alertFram = document.createElement("DIV");
    document.body.appendChild(alertFram);

    var shield = document.createElement("DIV");
    document.body.appendChild(shield);
    shield.id = "shield";
    shield.style.width =  screen.width+"px";
    shield.style.height = document.body.scrollHeight+"px";
    shield.className = "shield_div";
    sstrHtml="<iframe class='iframe'>";
    sstrHtml += "</iframe>";
    shield.innerHTML = sstrHtml;


    alertFram.id = "alertFram";
    alertFram.style.marginLeft = -225+document.documentElement.scrollLeft+"px";
    alertFram.style.marginTop = -75+document.documentElement.scrollTop+"px";
   alertFram.className = "alert_div";
    strHtml = "<div id='tc_bg'>";
    strHtml += " <div class='tc_title'>";
    strHtml += " <input type='button' onclick='doClose()' id='do_close' class='tc_bt01'></input>";
    strHtml += " </div>";
    strHtml += " <div class='tc_con1'>"+txt;
    strHtml += "<p><input onclick='doOk()' id='do_OK' type='button' class='tc_bt02'></input></p>";
    strHtml += " </div>";
    strHtml += "</div>";
    alertFram.innerHTML = strHtml;
        this.doClose = function() {
        alertFram.style.display = "none";
        shield.style.display = "none";
    }
     this.doClose = function(){
        document.body.removeChild(alertFram);
        document.body.removeChild(shield);
        document.body.onselectstart = function(){return true;}
        document.body.oncontextmenu = function(){return true;}
    }
    this.doOk = function() {
        alertFram.style.display = "none";
        shield.style.display = "none";
    }
    this.doOk = function(){
        document.body.removeChild(alertFram);
        document.body.removeChild(shield);
        document.body.onselectstart = function(){return true;}
        document.body.oncontextmenu = function(){return true;}
    }
    document.body.onselectstart = function(){return false;}
    document.body.oncontextmenu = function(){return false;}

}
//add by jinxx 2010/10/28 start for 点击确定后刷新页面
window.alertSuccessTaobao = function(txt) {

    var alertFram = document.createElement("DIV");
    document.body.appendChild(alertFram);

    var shield = document.createElement("DIV");
    document.body.appendChild(shield);
    shield.id = "shield";
    shield.style.width =  screen.width+"px";
    shield.style.height = document.body.scrollHeight+"px";
    shield.className = "shield_div";
    sstrHtml="<iframe class='iframe'>";
    sstrHtml += "</iframe>";
    shield.innerHTML = sstrHtml;


    alertFram.id = "alertFram";
    alertFram.style.marginLeft = -225+document.documentElement.scrollLeft+"px";
    alertFram.style.marginTop = -75+document.documentElement.scrollTop+"px";
   alertFram.className = "alert_div";
    strHtml = "<div id='tc_bg'>";
    strHtml += " <div class='tc_title'>";
    strHtml += " <input type='button' onclick='doClose()' id='do_close' class='tc_bt01'></input>";
    strHtml += " </div>";
    strHtml += " <div class='tc_con1'>"+txt;
    strHtml += "<p><input onclick='doOk()' id='do_OK' type='button' class='tc_bt02'></input></p>";
    strHtml += " </div>";
    strHtml += "</div>";
    alertFram.innerHTML = strHtml;
        this.doClose = function() {
        alertFram.style.display = "none";
        shield.style.display = "none";
    }
     this.doClose = function(){
        document.body.removeChild(alertFram);
        document.body.removeChild(shield);
        document.body.onselectstart = function(){return true;}
        document.body.oncontextmenu = function(){return true;}
    }
    this.doOk = function() {
        alertFram.style.display = "none";
        shield.style.display = "none";
    }
    this.doOk = function(){
        document.body.removeChild(alertFram);
        document.body.removeChild(shield);
        document.body.onselectstart = function(){return true;}
        document.body.oncontextmenu = function(){return true;}
		window.location.href="searchTaobaoGoods.html";
    }
    document.body.onselectstart = function(){return false;}
    document.body.oncontextmenu = function(){return false;}
}
//add by jinxx 2010/10/28 end
