// JavaScript Document

// 随机数发生器
rnd.today=new Date();
rnd.seed=rnd.today.getTime();
function rnd() {
	rnd.seed = (rnd.seed*9301+49297) % 233280;
	return rnd.seed/(233280.0);
}
function rand(number) {
	return Math.ceil(rnd()*number);
}

// 滚动widget
function simplescroll(c, config) {
	this.config = config ? config : {start_delay:3000, speed: 23, delay:4000, scrollItemCount:1,movecount:1};
	this.container = $i(c);
	this.pause = false;
	var _this = this;
	
	this.init = function() {
		_this.scrollTimeId = null;
		setTimeout(_this.start,_this.config.start_delay);
	}
	
	this.start = function() {
		var d = _this.container;
		var line_height = d.getElementsByTagName('li')[0].offsetHeight;
		if(d.scrollHeight-d.offsetHeight>=line_height) _this.scrollTimeId = setInterval(_this.scroll,_this.config.speed)
	};
	
	this.scroll = function() {
		if(_this.pause)return;
		var d = _this.container;d.scrollTop+=2;
		var line_height = d.getElementsByTagName('li')[0].offsetHeight;
		//alert(d.scrollTop + "%" + line_height + " : " + d.scrollTop%line_height);
		if(d.scrollTop%(line_height*_this.config.scrollItemCount)<=1){
			if(_this.config.movecount != undefined)
				for(var i=0;i<_this.config.movecount;i++){d.appendChild(d.getElementsByTagName('li')[0]);}
			else for(var i=0;i<_this.config.scrollItemCount;i++){d.appendChild(d.getElementsByTagName('li')[0]);}
			d.scrollTop=0;
			clearInterval(_this.scrollTimeId);
			setTimeout(_this.start,_this.config.delay);
		}
	}
	
	this.container.onmouseover=function(){_this.pause = true;}
	this.container.onmouseout=function(){_this.pause = false;}
}

// 横着滚动
function simpleSideScroll(c,ul,config,direction){
    this.config = config ? config : {start_delay:3000, speed: 23, delay:4000, scrollItemCount:1};
	this.c = $i(c);
	this.ul = $i(ul);
	this.direction = direction ? direction : "left";
	this.pause = false;
	this.buttonlist= new Object();
	this.delayTimeId=null;
	
	var _this = this;



	this.c.onmouseover=function(){_this.pause = true;}
	this.c.onmouseout=function(){_this.pause = false;}
	
	this.init = function() {
		_this.scrollTimeId = null;
		setTimeout(_this.start,_this.config.start_delay);
	}
	
	this.start = function() {
		var d = _this.c;
		var width = d.getElementsByTagName('li')[0].offsetWidth;
		if(d.scrollWidth-d.offsetLeft>=width) _this.scrollTimeId = setInterval(_this.scroll,_this.config.speed)
	};
	
	this.scroll = function() {
		if(_this.pause)return;
		var ul= _this.ul;
		var d = _this.c;
		var width = d.getElementsByTagName('li')[0].offsetWidth;
		if(_this.direction == 'left'){
	        d.scrollLeft += 2;
	        if(d.scrollLeft%(width*_this.config.scrollItemCount)<=1){
		        if(_this.config.movecount != undefined)
			        for(var i=0;i<_this.config.movecount;i++){ul.appendChild(ul.getElementsByTagName('li')[0]);}
		        else for(var i=0;i<_this.config.scrollItemCount;i++){ul.appendChild(ul.getElementsByTagName('li')[0]);}
		        d.scrollLeft=0;
		        clearInterval(_this.scrollTimeId);
		        
		        _this.delayTimeId=setTimeout(_this.start,_this.config.delay);
	        }
		}
		else {
		    if(d.scrollLeft==0)
		    {
		        var lis=ul.getElementsByTagName('li');
		        for(var i=0;i<_this.config.scrollItemCount;i++){
		            ul.insertBefore(lis[lis.length-1],lis[0]);
		        }
		        d.scrollLeft = width;
		    }
		    d.scrollLeft -= 2;
		    if(d.scrollLeft%(width*_this.config.scrollItemCount)<=1){
		        d.scrollLeft=0;
		        clearInterval(_this.scrollTimeId);
		        _this.delayTimeId=setTimeout(_this.start,_this.config.delay);
		    }
		}
	}
	
	this.setButton=function(id,direction){
	    if($i(id)){
	        var c=$i(id);
	        var buttonlist =_this.buttonlist;
	        if(buttonlist[id] == undefined){
	            buttonlist[id] =new Object();
	            _this.buttonlist[id][0]=c;
	            _this.buttonlist[id][1]=direction;
	            
	            c.onclick=function(){
	                 clearInterval(_this.scrollTimeId);
	                 
	                var dir=_this.buttonlist[this.id][1];
	                var d = _this.c;
	                var ul= _this.ul;
	                d.scrollLeft=0;
	                if(dir =="left")
	                {
	                    for(var i=0;i<_this.config.scrollItemCount;i++){ul.appendChild(ul.getElementsByTagName('li')[0]);}
	                }
	                else{
	                    var lis=ul.getElementsByTagName('li');
		                for(var i=0;i<_this.config.scrollItemCount;i++){
		                    ul.insertBefore(lis[lis.length-1],lis[0]);
		                }
	                }
	                    
	                _this.direction= dir;
	                clearTimeout(_this.delayTimeId);
	                _this.delayTimeId=setTimeout(_this.start,_this.config.delay);
	                return false;
	            }
	        }
	    }
	}
}
// tab切换
function tabswitch(c, config){
	this.config = config ? config : {start_delay:3000, delay:1500};
	this.container = $i(c);
	this.pause = false;
	this.nexttb = 1;
	this.tabs = this.container.getElementsByTagName('dt');
	var _this = this;
	if(this.tabs.length<1)this.tabs = this.container.getElementsByTagName('li');
	for(var i = 0; i < this.tabs.length; i++){
		var _ec = this.tabs[i].getElementsByTagName('span');
		if(_ec.length<1)_ec = this.tabs[i].getElementsByTagName('a');
		if(_ec.length<1){
			_ec = this.tabs[i]
		}else{
			_ec = _ec[0];
		}
		_ec.onmouseover = function(e) {
			_this.pause = true;
			var ev = !e ? window.event : e;
			_this.start(ev, false, null);
		};
		
		_ec.onmouseout = function() {
			_this.pause = false;
		};
		
		try{
			$i(this.tabs[i].id + '_body_1').onmouseover = function(){
				_this.pause = true;
			};
			
			$i(this.tabs[i].id + '_body_1').onmouseout = function(){
				_this.pause = false;
			};
		}catch(e){}
	}

	if ($i(c + '_sts')) {
		var _sts = $i(c + '_sts');
		var _step = _sts.getElementsByTagName('li');
		if(_step.length<1)_step = _sts.getElementsByTagName('div');
		_step[0].onclick = function() {
			if (_this.tabs[_this.tabs.length-1].className.indexOf('current') > -1) {
				_this.nexttb = _this.tabs.length + 1;
			};
			_this.nexttb = _this.nexttb - 2 < 1 ? _this.tabs.length : _this.nexttb - 2;
			//alert(_this.nexttb);
			_this.start(null, null, _this.nexttb);
		};
		
		_step[1].onclick = function() {
			_this.nexttb = _this.nexttb < 1 ? 1 : _this.nexttb;
			_this.start(null, null, _this.nexttb);
		};
	};
	
	this.start = function(e, r, n){
		if(_this.pause && !e)return;
		if(r){
			curr_tab = $i(_this.container.id + '_' + rand(4));
		}else{
			if(n){
				//alert(_this.container.id + '_' + _this.nexttb);
				curr_tab = $i(_this.container.id + '_' + _this.nexttb);
			}else{
				curr_tab = _jsc.evt.gTar(e);
				if(curr_tab.id=="")curr_tab = curr_tab.parentNode;
			}
		}
		
		var tb = curr_tab.id.split("_");
		for(var i = 0; i < _this.tabs.length; i++){
			if(_this.tabs[i]==curr_tab){
				_this.tabs[i].className="t_hot Selected current";
				try{
					//alert(_this.tabs[i].id);
					$i(_this.tabs[i].id + '_body_1').style.display = "block";
				}catch(e){}
			}else{
				_this.tabs[i].className="";
				try{
					$i(_this.tabs[i].id + '_body_1').style.display = "none";
				}catch(e){}
			}
		}
		_this.nexttb = parseInt(tb[tb.length-1]) >= _this.tabs.length ? 1 : parseInt(tb[tb.length-1]) + 1;
	};
}

function multibanners(){
	this.i = 0;
	this.ul = $i('multi_banners');
	this.nav = $i('mb-nav');
	this.pause = false;
	this.lis = this.ul.getElementsByTagName('li');
	this.navs = this.nav.getElementsByTagName('li');
	var _this = this;
	
	this.sw = function(o){
		for(var i = 0; i < _this.lis.length; i++){
			_this.lis[i].className = '';
			_this.navs[i].className = '';
		}
		o.className = 'current';
		var _cp = parseInt(o.innerHTML);
		_this.lis[_cp-1].className='current';
		//_this.i = ++_cp % 2;
		//var navs = _this.navs;
		//setTimeout(function() {
		//	if(_this.pause)return;
		//	_this.sw(navs[++_this.i % 2]);
		//}, 7800);
		//if(_this.i>=8)_this.i=0;
	};
	
	this.init = function(instance_name){
		var lis = _this.lis;
		var navs = _this.navs;
		for(var i = 0; i < lis.length; i++){
			var _img;
			_img = lis[i].getElementsByTagName('img')[0];
			if(!_img)_img = lis[i].getElementsByTagName('object')[0];
			_img.onmouseover = function(){_this.pause=true};
			_img.onmouseout = function(){_this.pause=false};
			navs[i].onmouseover = function(e){
				_this.pause=true;
				var ev = !e ? window.event : e;
				_this.sw(_jsc.evt.gTar(ev));
			};
			navs[i].onmouseout = function(){
				_this.pause=false;
			};
		}
		_this.sw(navs[0]);
		setInterval(function() {
			if(_this.pause)return;
			_this.sw(navs[++_this.i % 3]);
		}, 4800);
	};
}

// accordion by nowa
function hslide(c){
	this.c = $i(c);
	this.b = $i('slide_body');
	this.h3s = this.c.getElementsByTagName('ul')[0].getElementsByTagName('h3');
	this.sliding = false;
	var _this = this;
	
	for(var i=0;i<this.h3s.length;i++){
		this.h3s[i].onclick = function(e){
			var ev = !e ? window.event : e;
			_this.slide(_jsc.evt.gTar(ev));
		};
	}
	
	this.slide = function(o){
		if(_this.sliding)return;
	
		var bleft = 0;
		var tleft = 0;
		var passed = false;
		var _tab;
		
		for(var i=0;i<_this.h3s.length;i++){
			if(_this.h3s[i]!=o && _this.h3s[i].parentNode.className=='current'){
				_tab = _this.h3s[i];
			}
		}
		
		try{
			var _tabb = $i(_tab.id + '_body');
			var anim = function(){
				_this.sliding = true;
            	n += 69;
            	if(n >= 367){
					_tabb.style.width = "0px";
            		_tabb.style.display = 'none';
            		_tab.parentNode.className = "";
            		window.clearInterval(tt);
					
					o.parentNode.className = 'current';
					var _tabb2 = $i(o.id + '_body');
					_tabb2.style.width = "0px";
					_tabb2.style.display = 'block';
					var anim2 = function(){
						z += 69;
						if(z >= 367){
							_tabb2.style.width = "376px";
							_this.sliding = false;
							window.clearInterval(tt2);
						}else{
							_tabb2.style.width = z + "px";
						}
					},z=0;
					var tt2 = setInterval(anim2, 30);
            	}else{
            		_tabb.style.width = (377 - n) + "px";
            	}
            },n=0;
            var tt = setInterval(anim, 30);
		}catch(e){}
	}
}

function init_imh () {
	var _ul = document.getElementsByTagName('ul');
	for (var i=0;i<_ul.length;i++) {
		if (_ul[i].getAttribute('jpe') == 'imh:hover') {
			var _lis = _ul[i].getElementsByTagName('li');
			for (var j=0;j<_lis.length;j++) {
				_lis[j].onmouseover = function(e) {
					var ev = !e ? window.event : e;
					var _target = _jsc.evt.gTar(ev);
					while (_target.tagName.toLowerCase() != 'li') {
						_target = _target.parentNode;
					}
					_target.className = 'hover';
				};
				
				_lis[j].onmouseout = function(e) {
					var ev = !e ? window.event : e;
					var _target = _jsc.evt.gTar(ev);
					while (_target.tagName.toLowerCase() != 'li') {
						_target = _target.parentNode;
					}
					_target.className = '';
				};
			}
		}
	}
	
	var _lis = document.getElementsByTagName('li');
	for (var i=0;i<_lis.length;i++) {
		if (_lis[i].getAttribute('jpe') == 'linkto:a') {
			_lis[i].onclick = function(e) {
				var ev = !e ? window.event : e;
				var _target = _jsc.evt.gTar(ev);
				while (_target.tagName.toLowerCase() != 'li') {
					_target = _target.parentNode;
				}
				location.href = _target.getElementsByTagName('a')[0].getAttribute('href');
				return false;
			};
		}
	}
}
var mm_select=function(c){
	this.c=document.getElementById(c); //整体下拉框
	this.selecttext={};  //选中后显示的内容
	this.select={};  //下拉框内容
	this.hidetimer=null;  //移出时的隐藏
	var _this=this;
	this.addonclick=null;
	this.iframe=null;

	this.returnvalue=function(){
        return this.c.getAttribute("val");
    }

	this.c.onmouseover=function(){
        var th=_this;
        if(th.hidetimer)
            window.clearTimeout(th.hidetimer);
        th.c.className="mm_select1";
    }

	this.c.onmouseout=function(){
        _this.select_none();
    }


	this.c.onmousedown=function(){
	    _this.c.className="mm_select2";
	}

	this.c.onmouseup=function(){
       var th=_this;
        if(th.hidetimer)
            window.clearTimeout(th.hidetimer);
        th.c.className="mm_select1";    
        
        th.iframe.style.top=_jsc.pos.getY(th.c)+24+"px";
        th.iframe.style.left=_jsc.pos.getX(th.c)+"px"; 
        th.iframe.style.display="block";
        
        th.select.style.top=th.iframe.style.top;
        th.select.style.left=th.iframe.style.left;
        th.select.style.display="block";
        
        if(th.iframe.style.height=="0px")
        {
            th.iframe.style.height=th.select.offsetHeight+"px";
            th.iframe.style.width=th.select.offsetWidth+"px";
        }
    }
 
	this.select_none=function(){
        _this.hidetimer = window.setTimeout(function(){
            _this.c.className="mm_select";
            _this.select.style.display="none";
             _this.iframe.style.display="none";
         },300);
    }

	this.init=function(){
        var c=this.c;var divs=new Array();var childs=c.childNodes;
        for(var i=0;i<childs.length;i++)
        { 
            if(childs[i].tagName && childs[i].tagName.toLowerCase() =="div") divs[divs.length]=childs[i];
        }
        this.selecttext=divs[0];
        this.select=divs[1];
        this.iframe=document.createElement("iframe");
        this.iframe.style.cssText="position:absolute;display:none;filter:alpha(opacity=0);opacity:0;border-width:0;height:0px;";
        this.iframe.src="#";
        c.insertBefore(this.iframe,this.select);
        
        this.select.onclick=function(event){
            var tar =event?event.target:window.event.srcElement;
            if(tar.tagName.toLowerCase() =="a")
            {_this.c.setAttribute("val",tar.getAttribute("val"));
            _this.selecttext.innerHTML=tar.innerHTML; }
            this.style.display="none";
            _this.iframe.style.display="none";
            return false;
        }
        if(this.addonclick)
        {
            _jsc.util.addEvent(this.select,"click",this.addonclick);
        }
        this.select.onmouseover=function(){
            var th=_this;
            if(th.hidetimer)
                window.clearTimeout(th.hidetimer);
        }
        this.select.onmousedown=function(event){
            if(_jsc.client.isIE)
            {
                event=window.event;
                event.cancelBubble=true;
            }
            else
                event.stopPropagation();
        }
    }
}
function init_arp(){
	var list=$i("select_arp").getElementsByTagName("ul")[0];
	var li='<li><a href="#" val="000">省份</a></li>'
	li+='<li><a href="#" val="19">北京</a></li>';
	li+='<li><a href="#" val="417">上海</a></li>';
	li+='<li><a href="#" val="68">广东</a></li>';
	li+='<li><a href="#" val="255">江苏</a></li>';
	li+='<li><a href="#" val="368">山东</a></li>';
	li+='<li><a href="#" val="508">浙江</a></li>';
	li+='<li><a href="#" val="145">河南</a></li>';
	li+='<li><a href="#" val="125">河北</a></li>';
	li+='<li><a href="#" val="294">辽宁</a></li>';
	li+='<li><a href="#" val="438">四川</a></li>';
	li+='<li><a href="#" val="184">湖北</a></li>';
	li+='<li><a href="#" val="39">福建</a></li>';
	li+='<li><a href="#" val="212">湖南</a></li>';
	li+='<li><a href="#" val="165">黑龙江</a></li>';
	li+='<li><a href="#" val="1">安徽</a></li>';
	li+='<li><a href="#" val="279">江西</a></li>';
	li+='<li><a href="#" val="92">广西</a></li>';
	li+='<li><a href="#" val="234">吉林</a></li>';
	li+='<li><a href="#" val="488">云南</a></li>';
	li+='<li><a href="#" val="406">陕西</a></li>';
	li+='<li><a href="#" val="393">山西</a></li>';
	li+='<li><a href="#" val="461">天津</a></li>';
	li+='<li><a href="#" val="532">重庆</a></li>';
	li+='<li><a href="#" val="333">内蒙古</a></li>';
	li+='<li><a href="#" val="471">新疆</a></li>';
	li+='<li><a href="#" val="109">贵州</a></li>';
	li+='<li><a href="#" val="52">甘肃</a></li>';
	li+='<li><a href="#" val="120">海南</a></li>';
	li+='<li><a href="#" val="357">青海</a></li>';
	li+='<li><a href="#" val="351">宁夏</a></li>';
	li+='<li><a href="#" val="463">西藏</a></li>';
	li+='<li><a href="#" val="576">澳门</a></li>';
	li+='<li><a href="#" val="574">国外</a></li>';
	li+='<li><a href="#" val="577">台湾</a></li>';
	li+='<li><a href="#" val="599">香港</a></li>';
	list.innerHTML=li;
}
addr_data = [
{"areaname":"国外","arealevel":"0","areacode":"574","parentareacode": "0"},{"areaname":"香港","arealevel":"0","areacode":"599","parentareacode": "0"},
{"areaname":"澳门","arealevel":"0","areacode":"576","parentareacode": "0"},{"areaname":"澳门","arealevel":"0","areacode":"576","parentareacode": "0"},
{"areaname":"澳门","arealevel":"0","areacode":"576","parentareacode": "0"},{"areaname":"台湾","arealevel":"0","areacode":"577","parentareacode": "0"},
{"areaname":"中国大陆","arealevel":"0","areacode":"9999","parentareacode": "0"},{"areaname":"北京","arealevel":"0","areacode":"19","parentareacode": "0"},
{"areaname":"广东","arealevel":"0","areacode":"68","parentareacode": "0"},{"areaname":"广州","arealevel":"1","areacode":"68|73","parentareacode": "68"},
{"areaname":"汕尾","arealevel":"1","areacode":"68|83","parentareacode": "68"},{"areaname":"阳江","arealevel":"1","areacode":"68|86","parentareacode": "68"},
{"areaname":"揭阳","arealevel":"1","areacode":"68|77","parentareacode": "68"},{"areaname":"茂名","arealevel":"1","areacode":"68|78","parentareacode": "68"},
{"areaname":"江门","arealevel":"1","areacode":"68|76","parentareacode": "68"},{"areaname":"韶关","arealevel":"1","areacode":"68|84","parentareacode": "68"},
{"areaname":"惠州","arealevel":"1","areacode":"68|75","parentareacode": "68"},{"areaname":"梅州","arealevel":"1","areacode":"68|79","parentareacode": "68"},
{"areaname":"汕头","arealevel":"1","areacode":"68|81","parentareacode": "68"},{"areaname":"深圳","arealevel":"1","areacode":"68|85","parentareacode": "68"},
{"areaname":"珠海","arealevel":"1","areacode":"68|91","parentareacode": "68"},{"areaname":"佛山","arealevel":"1","areacode":"68|71","parentareacode": "68"},
{"areaname":"肇庆","arealevel":"1","areacode":"68|89","parentareacode": "68"},{"areaname":"湛江","arealevel":"1","areacode":"68|88","parentareacode": "68"},
{"areaname":"中山","arealevel":"1","areacode":"68|90","parentareacode": "68"},{"areaname":"河源","arealevel":"1","areacode":"68|74","parentareacode": "68"},
{"areaname":"清远","arealevel":"1","areacode":"68|80","parentareacode": "68"},{"areaname":"云浮","arealevel":"1","areacode":"68|87","parentareacode": "68"},
{"areaname":"潮州","arealevel":"1","areacode":"68|69","parentareacode": "68"},{"areaname":"东莞","arealevel":"1","areacode":"68|70","parentareacode": "68"},
{"areaname":"上海","arealevel":"0","areacode":"417","parentareacode": "0"},{"areaname":"天津","arealevel":"0","areacode":"461","parentareacode": "0"},
{"areaname":"重庆","arealevel":"0","areacode":"532","parentareacode": "0"},{"areaname":"辽宁","arealevel":"0","areacode":"294","parentareacode": "0"},
{"areaname":"沈阳","arealevel":"1","areacode":"294|311","parentareacode": "294"},{"areaname":"铁岭","arealevel":"1","areacode":"294|312","parentareacode": "294"},
{"areaname":"大连","arealevel":"1","areacode":"294|300","parentareacode": "294"},{"areaname":"鞍山","arealevel":"1","areacode":"294|295","parentareacode": "294"},
{"areaname":"抚顺","arealevel":"1","areacode":"294|304","parentareacode": "294"},{"areaname":"本溪","arealevel":"1","areacode":"294|297","parentareacode": "294"},
{"areaname":"丹东","arealevel":"1","areacode":"294|303","parentareacode": "294"},{"areaname":"锦州","arealevel":"1","areacode":"294|308","parentareacode": "294"},
{"areaname":"营口","arealevel":"1","areacode":"294|314","parentareacode": "294"},{"areaname":"阜新","arealevel":"1","areacode":"294|305","parentareacode": "294"},
{"areaname":"辽阳","arealevel":"1","areacode":"294|309","parentareacode": "294"},{"areaname":"朝阳","arealevel":"1","areacode":"294|298","parentareacode": "294"},
{"areaname":"盘锦","arealevel":"1","areacode":"294|310","parentareacode": "294"},{"areaname":"葫芦岛","arealevel":"1","areacode":"294|306","parentareacode": "294"},
{"areaname":"江苏","arealevel":"0","areacode":"255","parentareacode": "0"},{"areaname":"南京","arealevel":"1","areacode":"255|260","parentareacode": "255"},
{"areaname":"无锡","arealevel":"1","areacode":"255|269","parentareacode": "255"},{"areaname":"镇江","arealevel":"1","areacode":"255|277","parentareacode": "255"},
{"areaname":"苏州","arealevel":"1","areacode":"255|262","parentareacode": "255"},{"areaname":"南通","arealevel":"1","areacode":"255|261","parentareacode": "255"},
{"areaname":"扬州","arealevel":"1","areacode":"255|275","parentareacode": "255"},{"areaname":"盐城","arealevel":"1","areacode":"255|273","parentareacode": "255"},
{"areaname":"徐州","arealevel":"1","areacode":"255|272","parentareacode": "255"},{"areaname":"淮安","arealevel":"1","areacode":"255|257","parentareacode": "255"},
{"areaname":"连云港","arealevel":"1","areacode":"255|259","parentareacode": "255"},{"areaname":"常州","arealevel":"1","areacode":"255|256","parentareacode": "255"},
{"areaname":"泰州","arealevel":"1","areacode":"255|266","parentareacode": "255"},{"areaname":"宿迁","arealevel":"1","areacode":"255|265","parentareacode": "255"},
{"areaname":"湖北","arealevel":"0","areacode":"184","parentareacode": "0"},{"areaname":"武汉","arealevel":"1","areacode":"184|200","parentareacode": "184"},
{"areaname":"襄樊","arealevel":"1","areacode":"184|205","parentareacode": "184"},{"areaname":"鄂州","arealevel":"1","areacode":"184|185","parentareacode": "184"},
{"areaname":"孝感","arealevel":"1","areacode":"184|207","parentareacode": "184"},{"areaname":"黄冈","arealevel":"1","areacode":"184|188","parentareacode": "184"},
{"areaname":"黄石","arealevel":"1","areacode":"184|191","parentareacode": "184"},{"areaname":"咸宁","arealevel":"1","areacode":"184|203","parentareacode": "184"},
{"areaname":"荆州","arealevel":"1","areacode":"184|193","parentareacode": "184"},{"areaname":"宜昌","arealevel":"1","areacode":"184|210","parentareacode": "184"},
{"areaname":"恩施","arealevel":"1","areacode":"184|186","parentareacode": "184"},{"areaname":"十堰","arealevel":"1","areacode":"184|196","parentareacode": "184"},
{"areaname":"随州","arealevel":"1","areacode":"184|198","parentareacode": "184"},{"areaname":"荆门","arealevel":"1","areacode":"184|192","parentareacode": "184"},
{"areaname":"仙桃","arealevel":"1","areacode":"184|202","parentareacode": "184"},{"areaname":"天门","arealevel":"1","areacode":"184|199","parentareacode": "184"},
{"areaname":"四川","arealevel":"0","areacode":"438","parentareacode": "0"},{"areaname":"成都","arealevel":"1","areacode":"438|441","parentareacode": "438"},
{"areaname":"攀枝花","arealevel":"1","areacode":"438|454","parentareacode": "438"},{"areaname":"自贡","arealevel":"1","areacode":"438|459","parentareacode": "438"},
{"areaname":"绵阳","arealevel":"1","areacode":"438|451","parentareacode": "438"},{"areaname":"南充","arealevel":"1","areacode":"438|452","parentareacode": "438"},
{"areaname":"达州","arealevel":"1","areacode":"438|442","parentareacode": "438"},{"areaname":"遂宁","arealevel":"1","areacode":"438|455","parentareacode": "438"},
{"areaname":"广安","arealevel":"1","areacode":"438|445","parentareacode": "438"},{"areaname":"巴中","arealevel":"1","areacode":"438|440","parentareacode": "438"},
{"areaname":"泸州","arealevel":"1","areacode":"438|460","parentareacode": "438"},{"areaname":"宜宾","arealevel":"1","areacode":"438|457","parentareacode": "438"},
{"areaname":"资阳","arealevel":"1","areacode":"438|458","parentareacode": "438"},{"areaname":"内江","arealevel":"1","areacode":"438|453","parentareacode": "438"},
{"areaname":"乐山","arealevel":"1","areacode":"438|448","parentareacode": "438"},{"areaname":"眉山","arealevel":"1","areacode":"438|450","parentareacode": "438"},
{"areaname":"西昌","arealevel":"1","areacode":"438|449","parentareacode": "438"},{"areaname":"雅安","arealevel":"1","areacode":"438|456","parentareacode": "438"},
{"areaname":"甘孜州","arealevel":"1","areacode":"438|444","parentareacode": "438"},{"areaname":"马尔康","arealevel":"1","areacode":"438|439","parentareacode": "438"},
{"areaname":"德阳","arealevel":"1","areacode":"438|443","parentareacode": "438"},{"areaname":"广元","arealevel":"1","areacode":"438|447","parentareacode": "438"},
{"areaname":"陕西","arealevel":"0","areacode":"406","parentareacode": "0"},{"areaname":"西安","arealevel":"1","areacode":"406|413","parentareacode": "406"},
{"areaname":"咸阳","arealevel":"1","areacode":"406|414","parentareacode": "406"},{"areaname":"延安","arealevel":"1","areacode":"406|415","parentareacode": "406"},
{"areaname":"榆林","arealevel":"1","areacode":"406|416","parentareacode": "406"},{"areaname":"渭南","arealevel":"1","areacode":"406|412","parentareacode": "406"},
{"areaname":"商洛","arealevel":"1","areacode":"406|410","parentareacode": "406"},{"areaname":"安康","arealevel":"1","areacode":"406|407","parentareacode": "406"},
{"areaname":"汉中","arealevel":"1","areacode":"406|409","parentareacode": "406"},{"areaname":"宝鸡","arealevel":"1","areacode":"406|408","parentareacode": "406"},
{"areaname":"铜川","arealevel":"1","areacode":"406|411","parentareacode": "406"},{"areaname":"河北","arealevel":"0","areacode":"125","parentareacode": "0"},
{"areaname":"邯郸","arealevel":"1","areacode":"125|133","parentareacode": "125"},{"areaname":"石家庄","arealevel":"1","areacode":"125|138","parentareacode": "125"},
{"areaname":"保定","arealevel":"1","areacode":"125|126","parentareacode": "125"},{"areaname":"张家口","arealevel":"1","areacode":"125|144","parentareacode": "125"},{"areaname":"承德","arealevel":"1","areacode":"125|132","parentareacode": "125"},{"areaname":"唐山","arealevel":"1","areacode":"125|140","parentareacode": "125"},{"areaname":"廊坊","arealevel":"1","areacode":"125|135","parentareacode": "125"},{"areaname":"沧州","arealevel":"1","areacode":"125|129","parentareacode": "125"},
{"areaname":"衡水","arealevel":"1","areacode":"125|134","parentareacode": "125"},{"areaname":"邢台","arealevel":"1","areacode":"125|141","parentareacode": "125"},{"areaname":"秦皇岛","arealevel":"1","areacode":"125|137","parentareacode": "125"},{"areaname":"山西","arealevel":"0","areacode":"393","parentareacode": "0"},{"areaname":"朔州","arealevel":"1","areacode":"393|401","parentareacode": "393"},{"areaname":"忻州","arealevel":"1","areacode":"393|403","parentareacode": "393"},
{"areaname":"太原","arealevel":"1","areacode":"393|402","parentareacode": "393"},{"areaname":"大同","arealevel":"1","areacode":"393|395","parentareacode": "393"},{"areaname":"阳泉","arealevel":"1","areacode":"393|404","parentareacode": "393"},{"areaname":"榆次","arealevel":"1","areacode":"393|397","parentareacode": "393"},{"areaname":"长治","arealevel":"1","areacode":"393|394","parentareacode": "393"},{"areaname":"晋城","arealevel":"1","areacode":"393|396","parentareacode": "393"},
{"areaname":"临汾","arealevel":"1","areacode":"393|398","parentareacode": "393"},{"areaname":"离石","arealevel":"1","areacode":"393|400","parentareacode": "393"},
{"areaname":"运城","arealevel":"1","areacode":"393|405","parentareacode": "393"},{"areaname":"河南","arealevel":"0","areacode":"145","parentareacode": "0"},{"areaname":"商丘","arealevel":"1","areacode":"145|155","parentareacode": "145"},{"areaname":"郑州","arealevel":"1","areacode":"145|160","parentareacode": "145"},{"areaname":"安阳","arealevel":"1","areacode":"145|146","parentareacode": "145"},{"areaname":"新乡","arealevel":"1","areacode":"145|156","parentareacode": "145"},
{"areaname":"许昌","arealevel":"1","areacode":"145|159","parentareacode": "145"},{"areaname":"平顶山","arealevel":"1","areacode":"145|152","parentareacode": "145"},{"areaname":"信阳","arealevel":"1","areacode":"145|157","parentareacode": "145"},{"areaname":"南阳","arealevel":"1","areacode":"145|151","parentareacode": "145"},{"areaname":"开封","arealevel":"1","areacode":"145|149","parentareacode": "145"},{"areaname":"洛阳","arealevel":"1","areacode":"145|150","parentareacode": "145"},
{"areaname":"焦作","arealevel":"1","areacode":"145|148","parentareacode": "145"},{"areaname":"鹤壁","arealevel":"1","areacode":"145|147","parentareacode": "145"},{"areaname":"濮阳","arealevel":"1","areacode":"145|164","parentareacode": "145"},{"areaname":"周口","arealevel":"1","areacode":"145|161","parentareacode": "145"},{"areaname":"漯河","arealevel":"1","areacode":"145|163","parentareacode": "145"},{"areaname":"驻马店","arealevel":"1","areacode":"145|162","parentareacode": "145"},
{"areaname":"三门峡","arealevel":"1","areacode":"145|153","parentareacode": "145"},{"areaname":"吉林","arealevel":"0","areacode":"234","parentareacode": "0"},{"areaname":"长春","arealevel":"1","areacode":"234|238","parentareacode": "234"},{"areaname":"吉林","arealevel":"1","areacode":"234|239","parentareacode": "234"},{"areaname":"延边自治州","arealevel":"1","areacode":"234|249","parentareacode": "234"},{"areaname":"四平","arealevel":"1","areacode":"234|242","parentareacode": "234"},
{"areaname":"通化","arealevel":"1","areacode":"234|246","parentareacode": "234"},{"areaname":"白城","arealevel":"1","areacode":"234|235","parentareacode": "234"},{"areaname":"辽源","arealevel":"1","areacode":"234|241","parentareacode": "234"},{"areaname":"松原","arealevel":"1","areacode":"234|244","parentareacode": "234"},{"areaname":"白山","arealevel":"1","areacode":"234|237","parentareacode": "234"},{"areaname":"黑龙江","arealevel":"0","areacode":"165","parentareacode": "0"},
{"areaname":"哈尔滨","arealevel":"1","areacode":"165|167","parentareacode": "165"},{"areaname":"齐齐哈尔","arealevel":"1","areacode":"165|179","parentareacode": "165"},{"areaname":"牡丹江","arealevel":"1","areacode":"165|176","parentareacode": "165"},{"areaname":"佳木斯","arealevel":"1","areacode":"165|174","parentareacode": "165"},{"areaname":"绥化","arealevel":"1","areacode":"165|181","parentareacode": "165"},{"areaname":"黑河","arealevel":"1","areacode":"165|170","parentareacode": "165"},
{"areaname":"伊春","arealevel":"1","areacode":"165|183","parentareacode": "165"},{"areaname":"大庆","arealevel":"1","areacode":"165|166","parentareacode": "165"},{"areaname":"七台河","arealevel":"1","areacode":"165|178","parentareacode": "165"},{"areaname":"鸡西","arealevel":"1","areacode":"165|173","parentareacode": "165"},{"areaname":"鹤岗","arealevel":"1","areacode":"165|169","parentareacode": "165"},{"areaname":"双鸭山","arealevel":"1","areacode":"165|180","parentareacode": "165"},
{"areaname":"内蒙古","arealevel":"0","areacode":"315","parentareacode": "0"},{"areaname":"海拉尔","arealevel":"1","areacode":"315|322","parentareacode": "315"},{"areaname":"扎兰屯","arealevel":"1","areacode":"315|325","parentareacode": "315"},{"areaname":"牙克石","arealevel":"1","areacode":"315|324","parentareacode": "315"},{"areaname":"满洲里","arealevel":"1","areacode":"315|323","parentareacode": "315"},{"areaname":"呼和浩特","arealevel":"1","areacode":"315|321","parentareacode": "315"},
{"areaname":"包头","arealevel":"1","areacode":"315|318","parentareacode": "315"},{"areaname":"乌海","arealevel":"1","areacode":"315|328","parentareacode": "315"},{"areaname":"集宁","arealevel":"1","areacode":"315|329","parentareacode": "315"},{"areaname":"通辽","arealevel":"1","areacode":"315|326","parentareacode": "315"},{"areaname":"赤峰","arealevel":"1","areacode":"315|319","parentareacode": "315"},{"areaname":"东胜","arealevel":"1","areacode":"315|320","parentareacode": "315"},
{"areaname":"临河","arealevel":"1","areacode":"315|317","parentareacode": "315"},{"areaname":"锡林浩特","arealevel":"1","areacode":"315|331","parentareacode": "315"},{"areaname":"二连浩特","arealevel":"1","areacode":"315|330","parentareacode": "315"},{"areaname":"乌兰浩特","arealevel":"1","areacode":"315|332","parentareacode": "315"},{"areaname":"巴彦浩特","arealevel":"1","areacode":"315|316","parentareacode": "315"},{"areaname":"山东","arealevel":"0","areacode":"368","parentareacode": "0"},
{"areaname":"菏泽","arealevel":"1","areacode":"368|372","parentareacode": "368"},{"areaname":"济南","arealevel":"1","areacode":"368|373","parentareacode": "368"},{"areaname":"青岛","arealevel":"1","areacode":"368|380","parentareacode": "368"},{"areaname":"淄博","arealevel":"1","areacode":"368|392","parentareacode": "368"},{"areaname":"德州","arealevel":"1","areacode":"368|370","parentareacode": "368"},{"areaname":"烟台","arealevel":"1","areacode":"368|389","parentareacode": "368"},
{"areaname":"潍坊","arealevel":"1","areacode":"368|386","parentareacode": "368"},{"areaname":"济宁","arealevel":"1","areacode":"368|374","parentareacode": "368"},{"areaname":"泰安","arealevel":"1","areacode":"368|382","parentareacode": "368"},{"areaname":"临沂","arealevel":"1","areacode":"368|379","parentareacode": "368"},{"areaname":"滨州","arealevel":"1","areacode":"368|369","parentareacode": "368"},{"areaname":"东营","arealevel":"1","areacode":"368|371","parentareacode": "368"},
{"areaname":"威海","arealevel":"1","areacode":"368|384","parentareacode": "368"},{"areaname":"枣庄","arealevel":"1","areacode":"368|390","parentareacode": "368"},{"areaname":"日照","arealevel":"1","areacode":"368|381","parentareacode": "368"},{"areaname":"莱芜","arealevel":"1","areacode":"368|376","parentareacode": "368"},{"areaname":"聊城","arealevel":"1","areacode":"368|377","parentareacode": "368"},{"areaname":"安徽","arealevel":"0","areacode":"1","parentareacode": "0"},
{"areaname":"滁州","arealevel":"1","areacode":"1|6","parentareacode": "1"},{"areaname":"合肥","arealevel":"1","areacode":"1|8","parentareacode": "1"},{"areaname":"蚌埠","arealevel":"1","areacode":"1|3","parentareacode": "1"},{"areaname":"芜湖","arealevel":"1","areacode":"1|16","parentareacode": "1"},{"areaname":"淮南","arealevel":"1","areacode":"1|10","parentareacode": "1"},{"areaname":"马鞍山","arealevel":"1","areacode":"1|13","parentareacode": "1"},
{"areaname":"安庆","arealevel":"1","areacode":"1|2","parentareacode": "1"},{"areaname":"宿州","arealevel":"1","areacode":"1|14","parentareacode": "1"},{"areaname":"阜阳","arealevel":"1","areacode":"1|7","parentareacode": "1"},{"areaname":"亳州","arealevel":"1","areacode":"1|18","parentareacode": "1"},{"areaname":"黄山","arealevel":"1","areacode":"1|11","parentareacode": "1"},{"areaname":"淮北","arealevel":"1","areacode":"1|9","parentareacode": "1"},{"areaname":"铜陵","arealevel":"1","areacode":"1|15","parentareacode": "1"},
{"areaname":"宣城","arealevel":"1","areacode":"1|17","parentareacode": "1"},{"areaname":"六安","arealevel":"1","areacode":"1|12","parentareacode": "1"},{"areaname":"巢湖","arealevel":"1","areacode":"1|4","parentareacode": "1"},{"areaname":"池州","arealevel":"1","areacode":"1|5","parentareacode": "1"},{"areaname":"浙江","arealevel":"0","areacode":"508","parentareacode": "0"},{"areaname":"衢州","arealevel":"1","areacode":"508|529","parentareacode": "508"},
{"areaname":"杭州","arealevel":"1","areacode":"508|509","parentareacode": "508"},{"areaname":"湖州","arealevel":"1","areacode":"508|511","parentareacode": "508"},{"areaname":"嘉兴","arealevel":"1","areacode":"508|512","parentareacode": "508"},{"areaname":"宁波","arealevel":"1","areacode":"508|519","parentareacode": "508"},{"areaname":"绍兴","arealevel":"1","areacode":"508|522","parentareacode": "508"},{"areaname":"台州","arealevel":"1","areacode":"508|523","parentareacode": "508"},
{"areaname":"温州","arealevel":"1","areacode":"508|526","parentareacode": "508"},{"areaname":"丽水","arealevel":"1","areacode":"508|518","parentareacode": "508"},{"areaname":"金华","arealevel":"1","areacode":"508|514","parentareacode": "508"},{"areaname":"舟山","arealevel":"1","areacode":"508|528","parentareacode": "508"},{"areaname":"福建","arealevel":"0","areacode":"39","parentareacode": "0"},{"areaname":"福州","arealevel":"1","areacode":"39|40","parentareacode": "39"},{"areaname":"厦门","arealevel":"1","areacode":"39|50","parentareacode": "39"},
{"areaname":"宁德","arealevel":"1","areacode":"39|44","parentareacode": "39"},{"areaname":"莆田","arealevel":"1","areacode":"39|45","parentareacode": "39"},{"areaname":"泉州","arealevel":"1","areacode":"39|46","parentareacode": "39"},{"areaname":"漳州","arealevel":"1","areacode":"39|51","parentareacode": "39"},{"areaname":"龙岩","arealevel":"1","areacode":"39|41","parentareacode": "39"},{"areaname":"三明","arealevel":"1","areacode":"39|48","parentareacode": "39"},
{"areaname":"南平","arealevel":"1","areacode":"39|42","parentareacode": "39"},{"areaname":"湖南","arealevel":"0","areacode":"212","parentareacode": "0"},{"areaname":"岳阳","arealevel":"1","areacode":"212|230","parentareacode": "212"},{"areaname":"长沙","arealevel":"1","areacode":"212|214","parentareacode": "212"},{"areaname":"湘潭","arealevel":"1","areacode":"212|224","parentareacode": "212"},{"areaname":"株洲","arealevel":"1","areacode":"212|233","parentareacode": "212"},
{"areaname":"衡阳","arealevel":"1","areacode":"212|216","parentareacode": "212"},{"areaname":"郴州","arealevel":"1","areacode":"212|215","parentareacode": "212"},{"areaname":"常德","arealevel":"1","areacode":"212|213","parentareacode": "212"},{"areaname":"益阳","arealevel":"1","areacode":"212|227","parentareacode": "212"},{"areaname":"娄底","arealevel":"1","areacode":"212|220","parentareacode": "212"},{"areaname":"邵阳","arealevel":"1","areacode":"212|223","parentareacode": "212"},{"areaname":"吉首","arealevel":"1","areacode":"212|226","parentareacode": "212"},
{"areaname":"张家界 ","arealevel":"1","areacode":"212|232","parentareacode": "212"},{"areaname":"怀化","arealevel":"1","areacode":"212|218","parentareacode": "212"},{"areaname":"永州","arealevel":"1","areacode":"212|228","parentareacode": "212"},{"areaname":"广西","arealevel":"0","areacode":"92","parentareacode": "0"},{"areaname":"防城港","arealevel":"1","areacode":"92|96","parentareacode": "92"},{"areaname":"南宁","arealevel":"1","areacode":"92|105","parentareacode": "92"},
{"areaname":"凭祥","arealevel":"1","areacode":"92|95","parentareacode": "92"},{"areaname":"柳州","arealevel":"1","areacode":"92|104","parentareacode": "92"},{"areaname":"合山","arealevel":"1","areacode":"92|103","parentareacode": "92"},{"areaname":"桂林","arealevel":"1","areacode":"92|98","parentareacode": "92"},{"areaname":"梧州","arealevel":"1","areacode":"92|107","parentareacode": "92"},{"areaname":"贺州","arealevel":"1","areacode":"92|102","parentareacode": "92"},
{"areaname":"玉林","arealevel":"1","areacode":"92|108","parentareacode": "92"},{"areaname":"贵港","arealevel":"1","areacode":"92|99","parentareacode": "92"},{"areaname":"百色","arealevel":"1","areacode":"92|93","parentareacode": "92"},{"areaname":"钦州","arealevel":"1","areacode":"92|106","parentareacode": "92"},{"areaname":"河池","arealevel":"1","areacode":"92|100","parentareacode": "92"},{"areaname":"北海","arealevel":"1","areacode":"92|94","parentareacode": "92"},{"areaname":"江西","arealevel":"0","areacode":"279","parentareacode": "0"},
{"areaname":"鹰潭","arealevel":"1","areacode":"279|293","parentareacode": "279"},{"areaname":"新余","arealevel":"1","areacode":"279|291","parentareacode": "279"},{"areaname":"南昌","arealevel":"1","areacode":"279|288","parentareacode": "279"},{"areaname":"九江","arealevel":"1","areacode":"279|286","parentareacode": "279"},{"areaname":"上饶","arealevel":"1","areacode":"279|290","parentareacode": "279"},{"areaname":"抚州","arealevel":"1","areacode":"279|280","parentareacode": "279"},
{"areaname":"宜春","arealevel":"1","areacode":"279|292","parentareacode": "279"},{"areaname":"吉安","arealevel":"1","areacode":"279|283","parentareacode": "279"},{"areaname":"赣州","arealevel":"1","areacode":"279|282","parentareacode": "279"},{"areaname":"景德镇","arealevel":"1","areacode":"279|285","parentareacode": "279"},{"areaname":"萍乡","arealevel":"1","areacode":"279|289","parentareacode": "279"},{"areaname":"贵州","arealevel":"0","areacode":"109","parentareacode": "0"},
{"areaname":"贵阳","arealevel":"1","areacode":"109|112","parentareacode": "109"},{"areaname":"遵义","arealevel":"1","areacode":"109|118","parentareacode": "109"},{"areaname":"安顺","arealevel":"1","areacode":"109|110","parentareacode": "109"},{"areaname":"都匀","arealevel":"1","areacode":"109|115","parentareacode": "109"},{"areaname":"凯里","arealevel":"1","areacode":"109|114","parentareacode": "109"},{"areaname":"铜仁","arealevel":"1","areacode":"109|117","parentareacode": "109"},{"areaname":"毕节","arealevel":"1","areacode":"109|111","parentareacode": "109"},
{"areaname":"六盘水","arealevel":"1","areacode":"109|113","parentareacode": "109"},{"areaname":"兴义","arealevel":"1","areacode":"109|116","parentareacode": "109"},{"areaname":"云南","arealevel":"0","areacode":"488","parentareacode": "0"},{"areaname":"版纳","arealevel":"1","areacode":"488|505","parentareacode": "488"},{"areaname":"德宏","arealevel":"1","areacode":"488|492","parentareacode": "488"},{"areaname":"昭通","arealevel":"1","areacode":"488|507","parentareacode": "488"},
{"areaname":"昆明","arealevel":"1","areacode":"488|497","parentareacode": "488"},{"areaname":"大理","arealevel":"1","areacode":"488|491","parentareacode": "488"},{"areaname":"个旧","arealevel":"1","areacode":"488|495","parentareacode": "488"},{"areaname":"开远","arealevel":"1","areacode":"488|496","parentareacode": "488"},{"areaname":"曲靖","arealevel":"1","areacode":"488|502","parentareacode": "488"},{"areaname":"保山","arealevel":"1","areacode":"488|489","parentareacode": "488"},{"areaname":"文山","arealevel":"1","areacode":"488|504","parentareacode": "488"},
{"areaname":"玉溪","arealevel":"1","areacode":"488|506","parentareacode": "488"},{"areaname":"楚雄","arealevel":"1","areacode":"488|490","parentareacode": "488"},{"areaname":"思茅","arealevel":"1","areacode":"488|503","parentareacode": "488"},{"areaname":"临沧","arealevel":"1","areacode":"488|500","parentareacode": "488"},{"areaname":"六库","arealevel":"1","areacode":"488|501","parentareacode": "488"},{"areaname":"中甸","arealevel":"1","areacode":"488|494","parentareacode": "488"},
{"areaname":"丽江","arealevel":"1","areacode":"488|499","parentareacode": "488"},{"areaname":"西藏","arealevel":"0","areacode":"463","parentareacode": "0"},{"areaname":"拉萨","arealevel":"1","areacode":"463|466","parentareacode": "463"},{"areaname":"日喀则","arealevel":"1","areacode":"463|469","parentareacode": "463"},{"areaname":"山南","arealevel":"1","areacode":"463|470","parentareacode": "463"},{"areaname":"林芝","arealevel":"1","areacode":"463|467","parentareacode": "463"},
{"areaname":"昌都","arealevel":"1","areacode":"463|465","parentareacode": "463"},{"areaname":"那曲","arealevel":"1","areacode":"463|468","parentareacode": "463"},{"areaname":"阿里","arealevel":"1","areacode":"463|464","parentareacode": "463"},{"areaname":"海南","arealevel":"0","areacode":"120","parentareacode": "0"},{"areaname":"儋州","arealevel":"1","areacode":"120|124","parentareacode": "120"},{"areaname":"海口","arealevel":"1","areacode":"120|121","parentareacode": "120"},{"areaname":"通什","arealevel":"1","areacode":"120|123","parentareacode": "120"},
{"areaname":"三亚","arealevel":"1","areacode":"120|122","parentareacode": "120"},{"areaname":"甘肃","arealevel":"0","areacode":"52","parentareacode": "0"},{"areaname":"临夏","arealevel":"1","areacode":"52|60","parentareacode": "52"},{"areaname":"兰州","arealevel":"1","areacode":"52|59","parentareacode": "52"},{"areaname":"定西","arealevel":"1","areacode":"52|54","parentareacode": "52"},{"areaname":"平凉","arealevel":"1","areacode":"52|62","parentareacode": "52"},
{"areaname":"庆阳","arealevel":"1","areacode":"52|63","parentareacode": "52"},{"areaname":"金昌","arealevel":"1","areacode":"52|56","parentareacode": "52"},{"areaname":"武威","arealevel":"1","areacode":"52|66","parentareacode": "52"},{"areaname":"张掖","arealevel":"1","areacode":"52|67","parentareacode": "52"},{"areaname":"酒泉","arealevel":"1","areacode":"52|57","parentareacode": "52"},{"areaname":"嘉峪关","arealevel":"1","areacode":"52|55","parentareacode": "52"},
{"areaname":"天水","arealevel":"1","areacode":"52|65","parentareacode": "52"},{"areaname":"陇南","arealevel":"1","areacode":"52|61","parentareacode": "52"},{"areaname":"白银","arealevel":"1","areacode":"52|53","parentareacode": "52"},{"areaname":"宁夏","arealevel":"0","areacode":"351","parentareacode": "0"},{"areaname":"银川","arealevel":"1","areacode":"351|356","parentareacode": "351"},{"areaname":"石嘴山","arealevel":"1","areacode":"351|353","parentareacode": "351"},{"areaname":"吴忠","arealevel":"1","areacode":"351|354","parentareacode": "351"},
{"areaname":"固原","arealevel":"1","areacode":"351|352","parentareacode": "351"},{"areaname":"青海","arealevel":"0","areacode":"357","parentareacode": "0"},{"areaname":"海晏","arealevel":"1","areacode":"357|359","parentareacode": "357"},{"areaname":"西宁","arealevel":"1","areacode":"357|366","parentareacode": "357"},{"areaname":"海东","arealevel":"1","areacode":"357|361","parentareacode": "357"},{"areaname":"同仁","arealevel":"1","areacode":"357|365","parentareacode": "357"},
{"areaname":"共和","arealevel":"1","areacode":"357|362","parentareacode": "357"},{"areaname":"玛沁","arealevel":"1","areacode":"357|358","parentareacode": "357"},{"areaname":"玉树","arealevel":"1","areacode":"357|367","parentareacode": "357"},{"areaname":"德令哈","arealevel":"1","areacode":"357|363","parentareacode": "357"},{"areaname":"门源","arealevel":"1","areacode":"357|360","parentareacode": "357"},{"areaname":"格尔木","arealevel":"1","areacode":"357|364","parentareacode": "357"},
{"areaname":"新疆","arealevel":"0","areacode":"471","parentareacode": "0"},{"areaname":"塔城","arealevel":"1","areacode":"471|483","parentareacode": "471"},{"areaname":"哈密","arealevel":"1","areacode":"471|477","parentareacode": "471"},{"areaname":"和田","arealevel":"1","areacode":"471|478","parentareacode": "471"},{"areaname":"阿勒泰","arealevel":"1","areacode":"471|473","parentareacode": "471"},{"areaname":"阿图什","arealevel":"1","areacode":"471|481","parentareacode": "471"},
{"areaname":"博乐","arealevel":"1","areacode":"471|475","parentareacode": "471"},{"areaname":"克拉玛依","arealevel":"1","areacode":"471|480","parentareacode": "471"},{"areaname":"乌鲁木齐","arealevel":"1","areacode":"471|485","parentareacode": "471"},{"areaname":"石河子","arealevel":"1","areacode":"471|482","parentareacode": "471"},{"areaname":"昌吉","arealevel":"1","areacode":"471|476","parentareacode": "471"},{"areaname":"吐鲁番","arealevel":"1","areacode":"471|484","parentareacode": "471"},
{"areaname":"库尔勒","arealevel":"1","areacode":"471|474","parentareacode": "471"},{"areaname":"阿克苏","arealevel":"1","areacode":"471|472","parentareacode": "471"},{"areaname":"喀什","arealevel":"1","areacode":"471|479","parentareacode": "471"},{"areaname":"伊犁","arealevel":"1","areacode":"471|486","parentareacode": "471"}]

function change_city()
{
    var list_c = $i("select_arc").getElementsByTagName("ul")[0];
    var pr_value=select_pr.returnvalue();
    if(pr_value=="000");
        list_c.innerHTML="";
    var allarea=addr_data; //addr_date 是外部temp.js上的数据
    var li="";
    for(var i in allarea)
    {
        if(allarea[i].parentareacode==pr_value)
            li+='<li><a href="#" val="'+allarea[i].areacode+'">'+allarea[i].areaname+'</a></li>';
    }
    list_c.innerHTML=li;
    var selecttext=$i("select_arc").getElementsByTagName("div")[0];
    $i("select_arc").setAttribute("val","000");
    selecttext.innerHTML="城市";
    if(li=="")
        $i("select_arc").style.display="none";
    else
        $i("select_arc").style.display="block";
}

function batch_sub(){
    var areaid="0";
    if(select_pc.returnvalue() && select_pc.returnvalue() !="000")
        areaid=select_pc.returnvalue();
    else if(select_pr.returnvalue() && select_pr.returnvalue() !="000")
        areaid=select_pr.returnvalue();
    var ps1=0,ps2=0;
    ps1 = $i('batch_schPS1').value;
	ps2 = $i('batch_schPS2').value;
	
	if (isNaN(ps1) || isNaN(ps2)) {
		alert('价格必须是数字！');
		return;
	} else {
		if (parseFloat(ps2) < parseFloat(ps1)) {
			alert('结束价格必须大于或等于起始价格！');
			return;
		}
	}
	var cb="0";
	if($i("batch_schCPK").getAttribute("val"))
	    cb=$i("batch_schCPK").getAttribute("val");
	window.open("http://www.alimama.com/membersvc/buyadzone/search_zone.htm#ty=2&ar="+areaid+"&ps1="+ps1+"&ps2="+ps2+"&cb="+cb);
}


var banners, new_trans, super_rec, crazy_buy, star,bargain_scroll,buy_ok_div;
var select_pr,select_pc,select_cpk;
_jsc.util.addEvent(window, 'load', function() {
		if ($i('banners')) {	
			banners = new tabswitch('banners', {});
			setInterval("banners.start(null, null, 1);", 6000);
		}
		
		if ($i('super_rec')) {	
			super_rec = new tabswitch('super_rec', {});
			setInterval("super_rec.start(null, null, 1);", 5000);
		}
		
		if($i('new_trans')){
		    new_trans = new simplescroll('new_trans', {start_delay:3000, speed: 23, delay:3000, scrollItemCount:1});
		    new_trans.init();
		}
		if($i('crazy_buy')){
			crazy_buy = new simplescroll('crazy_buy', {start_delay:3000, speed: 23, delay:3000, scrollItemCount:1});
			crazy_buy.init();
		}
		
		if($i('buy_ok_div')){
		    buy_ok_div=new simpleSideScroll('buy_ok_div','buy_ok_ul',{start_delay:3000, speed: 23, delay:3000, scrollItemCount:1},'left')
		    buy_ok_div.setButton('buy_ok_scroll_left','left');
		    buy_ok_div.setButton('buy_ok_scroll_right','right');
		    buy_ok_div.init();
		}
		
		if($i('category')){
		    var category_lis = $i('category').getElementsByTagName("li");
		    for(var i=0;i<category_lis.length;i++){
		        category_lis[i].onmouseover=function(){
		            this.className +=" onmouse";
		        }
		        category_lis[i].onmouseout=function(){
		            this.className = this.className.replace(" onmouse","").replace("onmouse","")
		        }
		    }
		}
		
		init_imh();
		
		if($i("select_arp")){
		    init_arp();
            select_pr=new mm_select("select_arp");select_pr.addonclick=change_city; select_pr.init();
        
            select_pc=new mm_select("select_arc"); select_pc.init();
            select_cpk=new mm_select("batch_schCPK");select_cpk.init();
        }
});