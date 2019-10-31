/*  Prototype JavaScript framework
 *  (c) 2005 Sam Stephenson <sam@conio.net>
 *  Prototype is freely distributable under the terms of an MIT-style license.
 *  For details, see the Prototype web site: http://prototype.conio.net/
/*--------------------------------------------------------------------------*/

//note: modified & stripped down version of prototype, to be used with moo.fx by mad4milk (http://moofx.mad4milk.net).

var Class = {
	create: function() {
		return function() {
			this.initialize.apply(this, arguments);
		}
	}
}

Object.extend = function(destination, source) {
	for (property in source) destination[property] = source[property];
	return destination;
}

Function.prototype.bind = function(object) {
	var __method = this;
	return function() {
		return __method.apply(object, arguments);
	}
}

Function.prototype.bindAsEventListener = function(object) {
var __method = this;
	return function(event) {
		__method.call(object, event || window.event);
	}
}

function $() {
	if (arguments.length == 1) return get$(arguments[0]);
	var elements = [];
	$c(arguments).each(function(el){
		elements.push(get$(el));
	});
	return elements;

	function get$(el){
		if (typeof el == 'string') el = document.getElementById(el);
		return el;
	}
}

if (!window.Element) var Element = new Object();

Object.extend(Element, {
	remove: function(element) {
		element = $(element);
		element.parentNode.removeChild(element);
	},

	hasClassName: function(element, className) {
		element = $(element);
		if (!element) return;
		var hasClass = false;
		element.className.split(' ').each(function(cn){
			if (cn == className) hasClass = true;
		});
		return hasClass;
	},

	addClassName: function(element, className) {
		element = $(element);
		Element.removeClassName(element, className);
		element.className += ' ' + className;
	},
  
	removeClassName: function(element, className) {
		element = $(element);
		if (!element) return;
		var newClassName = '';
		element.className.split(' ').each(function(cn, i){
			if (cn != className){
				if (i > 0) newClassName += ' ';
				newClassName += cn;
			}
		});
		element.className = newClassName;
	},

	cleanWhitespace: function(element) {
		element = $(element);
		$c(element.childNodes).each(function(node){
			if (node.nodeType == 3 && !/\S/.test(node.nodeValue)) Element.remove(node);
		});
	},

	find: function(element, what) {
		element = $(element)[what];
		while (element.nodeType != 1) element = element[what];
		return element;
	}
});

var Position = {
	cumulativeOffset: function(element) {
		var valueT = 0, valueL = 0;
		do {
			valueT += element.offsetTop  || 0;
			valueL += element.offsetLeft || 0;
			element = element.offsetParent;
		} while (element);
		return [valueL, valueT];
	}
};

document.getElementsByClassName = function(className) {
	var children = document.getElementsByTagName('*') || document.all;
	var elements = [];
	$c(children).each(function(child){
		if (Element.hasClassName(child, className)) elements.push(child);
	});  
	return elements;
}

//useful array functions
Array.prototype.iterate = function(func){
	for(var i=0;i<this.length;i++) func(this[i], i);
}
if (!Array.prototype.each) Array.prototype.each = Array.prototype.iterate;

function $c(array){
	var nArray = [];
	for (var i=0;i<array.length;i++) nArray.push(array[i]);
	return nArray;
}

//-----------------------------------------------------------------------------------------------------------------------

/*
moo.fx, simple effects library built with prototype.js (http://prototype.conio.net).
by Valerio Proietti (http://mad4milk.net) MIT-style LICENSE.
for more info (http://moofx.mad4milk.net).
Sunday, March 05, 2006
v 1.2.3
*/

var fx = new Object();
//base
fx.Base = function(){};
fx.Base.prototype = {
	setOptions: function(options) {
	this.options = {
		duration: 500,
		onComplete: '',
		transition: fx.sinoidal
	}
	Object.extend(this.options, options || {});
	},

	step: function() {
		var time  = (new Date).getTime();
		if (time >= this.options.duration+this.startTime) {
			this.now = this.to;
			clearInterval (this.timer);
			this.timer = null;
			if (this.options.onComplete) setTimeout(this.options.onComplete.bind(this), 10);
		}
		else {
			var Tpos = (time - this.startTime) / (this.options.duration);
			this.now = this.options.transition(Tpos) * (this.to-this.from) + this.from;
		}
		this.increase();
	},

	custom: function(from, to) {
		if (this.timer != null) return;
		this.from = from;
		this.to = to;
		this.startTime = (new Date).getTime();
		this.timer = setInterval (this.step.bind(this), 13);
	},

	hide: function() {
		this.now = 0;
		this.increase();
	},

	clearTimer: function() {
		clearInterval(this.timer);
		this.timer = null;
	}
}

//stretchers
fx.Layout = Class.create();
fx.Layout.prototype = Object.extend(new fx.Base(), {
	initialize: function(el, options) {
		this.el = $(el);
		this.el.style.overflow = "hidden";
		this.iniWidth = this.el.offsetWidth;
		this.iniHeight = this.el.offsetHeight;
		this.setOptions(options);
	}
});

fx.Height = Class.create();
Object.extend(Object.extend(fx.Height.prototype, fx.Layout.prototype), {	
	increase: function() {
		this.el.style.height = this.now + "px";
	},

	toggle: function() {
		if (this.el.offsetHeight > 0) this.custom(this.el.offsetHeight, 0);
		else this.custom(0, this.el.scrollHeight);
	}
});

fx.Width = Class.create();
Object.extend(Object.extend(fx.Width.prototype, fx.Layout.prototype), {	
	increase: function() {
		this.el.style.width = this.now + "px";
	},

	toggle: function(){
		if (this.el.offsetWidth > 0) this.custom(this.el.offsetWidth, 0);
		else this.custom(0, this.iniWidth);
	}
});

//fader
fx.Opacity = Class.create();
fx.Opacity.prototype = Object.extend(new fx.Base(), {
	initialize: function(el, options) {
		this.el = $(el);
		this.now = 1;
		this.increase();
		this.setOptions(options);
	},

	increase: function() {
		if (this.now == 1 && (/Firefox/.test(navigator.userAgent))) this.now = 0.9999;
		this.setOpacity(this.now);
	},
	
	setOpacity: function(opacity) {
		if (opacity == 0 && this.el.style.visibility != "hidden") this.el.style.visibility = "hidden";
		else if (this.el.style.visibility != "visible") this.el.style.visibility = "visible";
		if (window.ActiveXObject) this.el.style.filter = "alpha(opacity=" + opacity*100 + ")";
		this.el.style.opacity = opacity;
	},

	toggle: function() {
		if (this.now > 0) this.custom(1, 0);
		else this.custom(0, 1);
	}
});

//transitions
fx.sinoidal = function(pos){
	return ((-Math.cos(pos*Math.PI)/2) + 0.5);
	//this transition is from script.aculo.us
}
fx.linear = function(pos){
	return pos;
}
fx.cubic = function(pos){
	return Math.pow(pos, 3);
}
fx.circ = function(pos){
	return Math.sqrt(pos);
}
// ---------------------------------------------------------------------------------------------------

/*
moo.fx pack, effects extensions for moo.fx.
by Valerio Proietti (http://mad4milk.net) MIT-style LICENSE
for more info visit (http://moofx.mad4milk.net).
Friday, April 14, 2006
v 1.2.4
*/

//smooth scroll
fx.Scroll = Class.create();
fx.Scroll.prototype = Object.extend(new fx.Base(), {
	initialize: function(options) {
		this.setOptions(options);
	},

	scrollTo: function(el){
		var dest = Position.cumulativeOffset($(el))[1];
		var client = window.innerHeight || document.documentElement.clientHeight;
		var full = document.documentElement.scrollHeight;
		var top = window.pageYOffset || document.body.scrollTop || document.documentElement.scrollTop;
		if (dest+client > full) this.custom(top, dest - client + (full-dest));
		else this.custom(top, dest);
	},

	increase: function(){
		window.scrollTo(0, this.now);
	}
});

//text size modify, now works with pixels too.
fx.Text = Class.create();
fx.Text.prototype = Object.extend(new fx.Base(), {
	initialize: function(el, options) {
		this.el = $(el);
		this.setOptions(options);
		if (!this.options.unit) this.options.unit = "em";
	},

	increase: function() {
		this.el.style.fontSize = this.now + this.options.unit;
	}
});

//composition effect: widht/height/opacity
fx.Combo = Class.create();
fx.Combo.prototype = {
	setOptions: function(options) {
		this.options = {
			opacity: true,
			height: true,
			width: false
		}
		Object.extend(this.options, options || {});
	},

	initialize: function(el, options) {
		this.el = $(el);
		this.setOptions(options);
		if (this.options.opacity) {
			this.o = new fx.Opacity(el, options);
			options.onComplete = null;
		}
		if (this.options.height) {
			this.h = new fx.Height(el, options);
			options.onComplete = null;
		}
		if (this.options.width) this.w = new fx.Width(el, options);
	},
	
	toggle: function() { this.checkExec('toggle'); },

	hide: function(){ this.checkExec('hide'); },
	
	clearTimer: function(){ this.checkExec('clearTimer'); },
	
	checkExec: function(func){
		if (this.o) this.o[func]();
		if (this.h) this.h[func]();
		if (this.w) this.w[func]();
	},
	
	//only if width+height
	resizeTo: function(hto, wto) {
		if (this.h && this.w) {
			this.h.custom(this.el.offsetHeight, this.el.offsetHeight + hto);
			this.w.custom(this.el.offsetWidth, this.el.offsetWidth + wto);
		}
	},

	customSize: function(hto, wto) {
		if (this.h && this.w) {
			this.h.custom(this.el.offsetHeight, hto);
			this.w.custom(this.el.offsetWidth, wto);
		}
	}
}

fx.Accordion = Class.create();
fx.Accordion.prototype = {
	setOptions: function(options) {
		this.options = {
			delay: 100,
			opacity: false
		}
		Object.extend(this.options, options || {});
	},

	initialize: function(togglers, elements, options) {
		this.elements = elements;
		this.setOptions(options);
		var options = options || '';
		this.fxa = [];
		if (options && options.onComplete) options.onFinish = options.onComplete;
		elements.each(function(el, i){
			options.onComplete = function(){
				if (el.offsetHeight > 0) el.style.height = '1%';
				if (options.onFinish) options.onFinish(el);
			}
			this.fxa[i] = new fx.Combo(el, options);
			this.fxa[i].hide();
		}.bind(this));

		togglers.each(function(tog, i){
			if (typeof tog.onclick == 'function') var exClick = tog.onclick;
			tog.onclick = function(){
				if (exClick) exClick();
				this.showThisHideOpen(elements[i]);
			}.bind(this);
		}.bind(this));
	},

	showThisHideOpen: function(toShow){
		this.elements.each(function(el, j){
			if (el.offsetHeight > 0 && el != toShow) this.clearAndToggle(el, j);
			if (el == toShow && toShow.offsetHeight == 0) setTimeout(function(){this.clearAndToggle(toShow, j);}.bind(this), this.options.delay);
		}.bind(this));
	},

	clearAndToggle: function(el, i){
		this.fxa[i].clearTimer();
		this.fxa[i].toggle();
	}
}

var Remember = new Object();
Remember = function(){};
Remember.prototype = {
	initialize: function(el, options){
		this.el = $(el);
		this.days = 365;
		this.options = options;
		this.effect();
		var cookie = this.readCookie();
		if (cookie) {
			this.fx.now = cookie;
			this.fx.increase();
		}
	},

	//cookie functions based on code by Peter-Paul Koch
	setCookie: function(value) {
		var date = new Date();
		date.setTime(date.getTime()+(this.days*24*60*60*1000));
		var expires = "; expires="+date.toGMTString();
		document.cookie = this.el+this.el.id+this.prefix+"="+value+expires+"; path=/";
	},

	readCookie: function() {
		var nameEQ = this.el+this.el.id+this.prefix + "=";
		var ca = document.cookie.split(';');
		for(var i=0;c=ca[i];i++) {
			while (c.charAt(0)==' ') c = c.substring(1,c.length);
			if (c.indexOf(nameEQ) == 0) return c.substring(nameEQ.length,c.length);
		}
		return false;
	},

	custom: function(from, to){
		if (this.fx.now != to) {
			this.setCookie(to);
			this.fx.custom(from, to);
		}
	}
}

fx.RememberHeight = Class.create();
fx.RememberHeight.prototype = Object.extend(new Remember(), {
	effect: function(){
		this.fx = new fx.Height(this.el, this.options);
		this.prefix = 'height';
	},
	
	toggle: function(){
		if (this.el.offsetHeight == 0) this.setCookie(this.el.scrollHeight);
		else this.setCookie(0);
		this.fx.toggle();
	},
	
	resize: function(to){
		this.setCookie(this.el.offsetHeight+to);
		this.fx.custom(this.el.offsetHeight,this.el.offsetHeight+to);
	},

	hide: function(){
		if (!this.readCookie()) {
			this.fx.hide();
		}
	}
});

fx.RememberText = Class.create();
fx.RememberText.prototype = Object.extend(new Remember(), {
	effect: function(){
		this.fx = new fx.Text(this.el, this.options);
		this.prefix = 'text';
	}
});

//useful for-replacement
Array.prototype.iterate = function(func){
	for(var i=0;i<this.length;i++) func(this[i], i);
}
if (!Array.prototype.each) Array.prototype.each = Array.prototype.iterate;

//Easing Equations (c) 2003 Robert Penner, all rights reserved.
//This work is subject to the terms in http://www.robertpenner.com/easing_terms_of_use.html.

//expo
fx.expoIn = function(pos){
	return Math.pow(2, 10 * (pos - 1));
}
fx.expoOut = function(pos){
	return (-Math.pow(2, -10 * pos) + 1);
}

//quad
fx.quadIn = function(pos){
	return Math.pow(pos, 2);
}
fx.quadOut = function(pos){
	return -(pos)*(pos-2);
}

//circ
fx.circOut = function(pos){
	return Math.sqrt(1 - Math.pow(pos-1,2));
}
fx.circIn = function(pos){
	return -(Math.sqrt(1 - Math.pow(pos, 2)) - 1);
}

//back
fx.backIn = function(pos){
	return (pos)*pos*((2.7)*pos - 1.7);
}
fx.backOut = function(pos){
	return ((pos-1)*(pos-1)*((2.7)*(pos-1) + 1.7) + 1);
}

//sine
fx.sineOut = function(pos){
	return Math.sin(pos * (Math.PI/2));
}
fx.sineIn = function(pos){
	return -Math.cos(pos * (Math.PI/2)) + 1;
}
fx.sineInOut = function(pos){
	return -(Math.cos(Math.PI*pos) - 1)/2;
}




//身份证验证

function IDCardCheck(num) {
    num = num.toUpperCase();
    //身份证号码为15位或者18位，15位时全为数字，18位前17位为数字，最后一位是校验位，可能为数字或字符X。   
    if (!(/(^\d{15}$)|(^\d{17}([0-9]|X)$)/.test(num))) {
        alert('输入的身份证号长度不对，或者号码不符合规定！\n15位号码应全为数字，18位号码末位可以为数字或X。');
        return false;
    }
    //校验位按照ISO 7064:1983.MOD 11-2的规定生成，X可以认为是数字10。 
    //下面分别分析出生日期和校验位 
    var len, re;
    len = num.length;
    if (len == 15) {
        re = new RegExp(/^(\d{6})(\d{2})(\d{2})(\d{2})(\d{3})$/);
        var arrSplit = num.match(re);

        //检查生日日期是否正确 
        var dtmBirth = new Date('19' + arrSplit[2] + '/' + arrSplit[3] + '/' + arrSplit[4]);
        var bGoodDay;
        bGoodDay = (dtmBirth.getYear() == Number(arrSplit[2])) && ((dtmBirth.getMonth() + 1) == Number(arrSplit[3])) && (dtmBirth.getDate() == Number(arrSplit[4]));
        if (!bGoodDay) {
            alert('输入的身份证号里出生日期不对！');
            return false;
        }
        else {
            //将15位身份证转成18位 
            //校验位按照ISO 7064:1983.MOD 11-2的规定生成，X可以认为是数字10。 
            var arrInt = new Array(7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2);
            var arrCh = new Array('1', '0', 'X', '9', '8', '7', '6', '5', '4', '3', '2');
            var nTemp = 0, i;
            num = num.substr(0, 6) + '19' + num.substr(6, num.length - 6);
            for (i = 0; i < 17; i++) {
                nTemp += num.substr(i, 1) * arrInt[i];
            }
            num += arrCh[nTemp % 11];
            return true;
        }
    }
    if (len == 18) {
        re = new RegExp(/^(\d{6})(\d{4})(\d{2})(\d{2})(\d{3})([0-9]|X)$/);
        var arrSplit = num.match(re);

        //检查生日日期是否正确 
        var dtmBirth = new Date(arrSplit[2] + "/" + arrSplit[3] + "/" + arrSplit[4]);
        var bGoodDay;
        bGoodDay = (dtmBirth.getFullYear() == Number(arrSplit[2])) && ((dtmBirth.getMonth() + 1) == Number(arrSplit[3])) && (dtmBirth.getDate() == Number(arrSplit[4]));
        if (!bGoodDay) {
            //alert(dtmBirth.getYear());
          //  alert(arrSplit[2]);
            alert('输入的身份证号里出生日期不对！');
            return false;
        }
        else {
            //检验18位身份证的校验码是否正确。 
            //校验位按照ISO 7064:1983.MOD 11-2的规定生成，X可以认为是数字10。 
            var valnum;
            var arrInt = new Array(7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2);
            var arrCh = new Array('1', '0', 'X', '9', '8', '7', '6', '5', '4', '3', '2');
            var nTemp = 0, i;
            for (i = 0; i < 17; i++) {
                nTemp += num.substr(i, 1) * arrInt[i];
            }
            valnum = arrCh[nTemp % 11];
            if (valnum != num.substr(17, 1)) {
                alert('18位身份证的校验码不正确！'); //应该为： + valnum
                return false;
            }
            return true;
        }
    }
    return false;
}

