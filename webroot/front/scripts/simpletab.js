
/**
 *
 *@param {JSON} config 可选配置属性
 *                      isAuto:是否自动切换tab，true为自动(默认为非自动)
 *                      start_delay：自动模式下，鼠标离开后开始的时间(默认为3秒)
 *                      delay: 自动模式下，切换tab的时间(默认为4秒)              
 *                      selectedStyle:触发对象鼠标选中时的样式名称(默认为current)
 *                      deselectStyle:触发对象鼠标非选中时的样式名称(默认为无)
 *                      selectedIndex:开始选中的tab(默认为第一个，值为0)
 *                      type:触发的事件类型(比如click或mouseover 触发）
 */
function TabSwitch(config){
   this.tabItems = new Array();
   this.tabContents = new Array();
    
   var _this=this;
    
   this.selectedIndex=0;
   this.start_delay=3000;
   this.delay=4000;
   this.isAuto=false;
   this.selectedStyle="current";
   this.deselectStyle="";
   this.type="mouseover";
   
   //load configuration
   if(config!=null){
   	if(typeof(config.start_delay)=="number"){ _this.start_delay=config.start_delay;}
   	if(typeof(config.delay)=="number"){ _this.delay=config.delay;}
   	if(typeof(config.isAuto)=="boolean"){ _this.isAuto=config.isAuto;}
   	if(typeof(config.selectedStyle)=="string"){ _this.selectedStyle=config.selectedStyle;}
   	if(typeof(config.deselectStyle)=="string"){ _this.deselectStyle=config.deselectStyle;}
   	if(typeof(config.selectedIndex)=="number"){ _this.selectedIndex=config.selectedIndex;}
   	if(typeof(config.type)=="string"){ _this.type=config.type;}
   }
   
   this.switchTimeId=null;
   this.switchInterId=null;
    
    
    /**
     *添加tab
     *public method
     */
    this.addTab= function(srcId,desId){
    	_this.tabItems.push(document.getElementById(srcId));
    	_this.tabContents.push(document.getElementById(desId));
   };
   
   
   this.refresh=function(){
   	for(var j=0;j<_this.tabItems.length;j++){
            _this.tabItems[j].className=_this.deselectStyle;
            _this.tabContents[j].style.display="none";
        }
   };
   
   this.getIndex=function(tabItem){
   	for(var j=0;j<_this.tabItems.length;j++){
           if(_this.tabItems[j]==tabItem)
           return j;
        }
        return -1;
   }
   
    this.getContentIndex=function(contentItem){
   	for(var j=0;j<_this.tabContents.length;j++){
           if(_this.tabContents[j]==contentItem)
           return j;
        }
        return -1;
   }
   
   //此方法必须在tab被添加后调用
   this.init=function(){
   	if(_this.selectedIndex>=_this.tabItems.length){_this.selectedIndex = _this.tabItems.length-1;}
        else if(_this.selectedIndex<0){_this.selectedIndex = 0;}
   	
   	for(var j=0;j<_this.tabItems.length;j++){
            if( j == _this.selectedIndex){
              _this.tabContents[j].style.display="block";
              _this.tabItems[j].className=_this.selectedStyle;
           }else{
              _this.tabContents[j].style.display="none";
              _this.tabItems[j].className=_this.deselectStyle;
           }  
        }
   	
   	
   	for(var i=0;i<_this.tabItems.length;i++){
   	    if(_this.type=="click"){
   	    	
   	    	_this.tabItems[i].onclick=function(){
   	     	if(_this.isAuto){
   	             clearTimeout(_this.switchTimeId);
                     clearInterval(_this.switchInterId);
   	         }		
   	     	_this.refresh();
   	     	this.className=_this.selectedStyle;
   	     	_this.tabContents[_this.getIndex(this)].style.display="block";
   	    };
   	    
   	      _this.tabContents[i].onmouseover=function(){
   	     	if(_this.isAuto){
   	             clearTimeout(_this.switchTimeId);
                     clearInterval(_this.switchInterId);
   	         }		
   	     	
   	    };
   	    
   	    if(_this.isAuto){
   	        _this.tabItems[i].onmouseout=function(){
   	     	   clearTimeout(_this.switchTimeId);
                  clearInterval(_this.switchInterId);
                  _this.switchTimeId=setTimeout(_this.start,_this.start_delay);
   	      };
   	      
   	      
   	        _this.tabContents[i].onmouseout=function(){
   	     	   clearTimeout(_this.switchTimeId);
                  clearInterval(_this.switchInterId);
                  _this.switchTimeId=setTimeout(_this.start,_this.start_delay);
   	      };
   	    }
   	    
   		
   	}else{ 		
   	     _this.tabItems[i].onmouseover=function(){
   	     	if(_this.isAuto){
   	             clearTimeout(_this.switchTimeId);
                     clearInterval(_this.switchInterId);
   	         }		
   	     	_this.refresh();
   	     	this.className=_this.selectedStyle;
   	     	_this.tabContents[_this.getIndex(this)].style.display="block";
   	    };
   	    
   	      _this.tabContents[i].onmouseover=function(){
   	     	if(_this.isAuto){
   	             clearTimeout(_this.switchTimeId);
                     clearInterval(_this.switchInterId);
   	         }		
   	     	_this.refresh();
   	     	_this.tabItems[_this.getContentIndex(this)].className=_this.selectedStyle;
   	     	this.style.display="block";
   	    };
   	    
   	    if(_this.isAuto){
   	        _this.tabItems[i].onmouseout=function(){
   	     	   clearTimeout(_this.switchTimeId);
                  clearInterval(_this.switchInterId);
                  _this.switchTimeId=setTimeout(_this.start,_this.start_delay);
   	      };
   	      
   	      
   	        _this.tabContents[i].onmouseout=function(){
   	     	   clearTimeout(_this.switchTimeId);
                  clearInterval(_this.switchInterId);
                  _this.switchTimeId=setTimeout(_this.start,_this.start_delay);
   	      };
   	    }
   	    
   	}
   	    
   	}
   	    
   };

   
   this.start = function() {
		_this.switchInterId=setInterval(_this.autoSwitch,_this.delay);
	};
 
   this.autoSwitch = function(){
  	for(var i=0;i<_this.tabItems.length;i++){
          var item = _this.tabItems[i];
          if(item.className ==_this.selectedStyle){           
             var nextIndex = ((i+1)>=_this.tabItems.length)?0:(i+1);
              item.className=_this.deselectStyle;
             _this.tabContents[i].style.display="none";  
             _this.tabItems[nextIndex].className=_this.selectedStyle;
             _this.tabContents[nextIndex].style.display="block";    
             return;         
          }
       }
   };
   
   if(_this.isAuto)
    _this.switchTimeId=setTimeout(_this.start,_this.start_delay);
    
}