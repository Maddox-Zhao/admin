/*

isInt(string,string,int or string):(测试字符串,+ or - or empty,empty or 0)
功能：判断是否为整数、正整数、负整数、正整数+0、负整数+0
*/
function isInt(objStr,sign,zero){
    if(objStr == "")
		return true;

    var reg;
    var bolzero;

    if(trim(objStr) == "")
        return false;
    else
        objStr=objStr.toString();

    if((sign == null)||(trim(sign) == ""))
		sign = "+-";

    if((zero == null)||(trim(zero) == ""))
		bolzero = false;
    else  {
		zero = zero.toString();
        if(zero == "0")
            bolzero=true;
    }

    switch(sign){
		case "+-": //整数
            reg=/(^-?|^\+?)\d+$/;
            break;
        case "+":
            if(!bolzero)//正整数
                reg=/^\+?[0-9]*[1-9][0-9]*$/;
            else //正整数+0
                //reg=/^\+?\d+$/;
                reg=/^\+?[0-9]*[0-9][0-9]*$/;
            break;
        case "-":
            if(!bolzero) //负整数
                reg=/^-[0-9]*[1-9][0-9]*$/;
            else //负整数+0
                //reg=/^-\d+$/;
                reg=/^-[0-9]*[0-9][0-9]*$/;
            break;
        default:
            return false;
            break;
    }

    var r = objStr.match(reg);
    if(r == null)
		return false;
    else
        return true;
}

/*
功能：判断是否为正整数
*/
function isIntP(objStr){
	return isInt(objStr,"+","0")
}

/*
isDouble(string,string,int or string):(测试字符串,+ or - or empty,empty or 0)
功能：判断是否为整数、正整数、负整数、正整数+0、负整数+0
*/
function isDouble(objStr,sign,zero){
	if(objStr == "")
		return true;

	var reg;
    var bolzero;

    if(trim(objStr) == "")
        return false;
    else
        objStr=objStr.toString();

    if((sign == null)||(trim(sign) == ""))
		sign = "+-";

    if((zero == null)||(trim(zero) == ""))
		bolzero = false;
    else     {
		zero = zero.toString();
        if(zero == "0")
            bolzero = true;
    }

    switch(sign) {
		case "+-": //数字
            reg=/^[0-9]+[\.[0-9]+]*$/;
            break;
        case "+":
            if(!bolzero)//正数
                reg=/(^0.[0-9]*$)|(^[1-9]+[0-9]*[\.[0-9]*]*$)/;
            else //正数+0
                //reg=/^\+?\d+$/;
				reg=/^[0-9]*[\.[0-9]+]*$/;
            break;
        case "-":
            if(!bolzero) //负数
                reg=/^[0-9]+[\.[0-9]+]*$/;
            else //负数+0
                //reg=/^-\d+$/;
                reg=/^[0-9]+[\.[0-9]+]*$/;
            break;
        default:
            return false;
            break;
    }

    var r = objStr.match(reg);
    if(r == null)
		return false;
    else
        return true;
}

function isDoubleP(objStr){
	return isDouble(objStr,"+");
}

/*
功能：判断是的是数字
参数：测试字符串,+ or - or empty,empty or 0
*/
function isNumber(objStr,sign,zero){
	return isDouble(objStr,sign,zero) || isInt(objStr,sign,zero);
}

/*
功能：判断是的是大于0数字
参数：测试字符串
*/
function isNumberP(objStr){
	return isDouble(objStr,"+") || isInt(objStr,"+");
}

function isDate(str){
    if(str == "")
		return true;

    //var re	= new RegExp("^([0-9]{1,2})[./]{1}([0-9]{1,2})[./]{1}([0-9]{4})$");
    var re = new RegExp("^([0-9]{4})[-]{1}([0-9]{1,2})[-]{1}([0-9]{1,2})$");
    var ar;
    var res = true;

    if ((ar = re.exec(str)) != null)
    {
        var i;

        i = parseFloat(ar[2]);

        if (i <= 0 || i > 12)
            res = false;

        i = parseFloat(ar[3]);

        if (i <= 0 || i > 31)
            res = false;
    }
    else
        res = false;

    return res;
}

function isEmail(str){
	if(str == "")
		return true;
	str = trim(str);
	var reg = /^\w+((-\w+)|(\.\w+))*\@[A-Za-z0-9]+((\.|-)[A-Za-z0-9]+)*\.[A-Za-z0-9]+$/;
	return reg.exec(str) != null;

}

function isTel(str){
	if(str == "")
		return true;
	str = trim(str);
	var reg = /^([0-9]{2}-)?(0?[0-9]{2,3}\-)?[1-9]?[0-9]{7}$/;
	return reg.exec(str) != null;
}

function isMobile(str){
	if(str == "")
		return true;
	str = trim(str);
	var reg=/^[0-9]{11}/;
	return reg.exec(str) != null;
}

function isPostcode(str){
	if(str == "")
		return true;
	str = trim(str);
	var reg = /^[0-9]{6}$/;
	return reg.exec(str) != null;
}

function trimInner(sInputString,iType){
	var sTmpStr = ' ';
	var i = -1;
	if(iType == null || iType == 0 || iType == 1)
	{
		while(sTmpStr == ' ')
		{
			++i;
			sTmpStr = sInputString.substring(i,1);
		}
		sInputString = sInputString.substring(i);
	}

	if(iType == null || iType == 0 || iType == 2)
	{
		sTmpStr = ' ';
		i = sInputString.length;
		while(sTmpStr == ' ')
		{
			--i;
			sTmpStr = sInputString.substring(i,1);
		}
		sInputString = sInputString.substring(0,i+1);
	}
	return sInputString;
}

function trim(sInputString){
	if(sInputString == undefined || sInputString == null)
		return "";

	return trimInner(sInputString,0)
}
