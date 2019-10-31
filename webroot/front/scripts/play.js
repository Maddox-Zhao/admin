

var layeri=0;
var stopshow=0;
var timeout=0;

function moveformat(id){
	if(stopshow == 0){
		//picswin(id);
		var browser=navigator.appName;
		if(browser!="Microsoft Internet Explorer"){
			layeri++;
			document.getElementById(id).style.zIndex = layeri;
			document.getElementById(id).style.left=(0+"px");
			var linkid="link"+id;
		}else{
			document.getElementById(id).style.posLeft = 360;
			layeri++;
			document.getElementById(id).style.zIndex = layeri;
			movestart(id);
		}
	}
}

function movestart(id){
	if(document.getElementById(id).style.posLeft!=0){
		var mnum=document.getElementById(id).style.posLeft*0.2;
		var x=document.getElementById(id).style.posLeft-mnum;
		document.getElementById(id).style.posLeft=x;
		moveid=id;
		setTimeout("movestart(moveid);",30);
	}else{
		var linkid="link"+id;
	}
}
var k=1;

function showindexad(){
	var kk=document.getElementById('lfloat').getElementsByTagName('a').length;
	if (k<kk && stopshow==0){
	  k++;
	  var picid="p";
	  picid=picid+k;
	  setTimeout("moveformat('"+picid+"');showindexad();",5000);
 	}else if(stopshow==0){
	  k=1;
	  var picid="p";
	  picid=picid+k;
	  setTimeout("moveformat('"+picid+"');showindexad();",5000);
	}else{
		timeout=1;
	}
}


function picsw(id) {
	showindexad2(id);
}

function showindexad2(id){
  stopshow=1;
  var browser=navigator.appName;
  if(browser!="Microsoft Internet Explorer"){
  layeri++;
  document.getElementById(id).style.zIndex = layeri;
  document.getElementById(id).style.left=(0+"px");
  var linkid="link"+id;

  }
else{
  document.getElementById(id).style.posLeft = 360;
  layeri++;
  document.getElementById(id).style.zIndex = layeri;
  movestart(id);
  }
}
function startauto(){
  stopshow=0;
  if(timeout==1){
  showindexad();
  timeout=0;
  }
}

