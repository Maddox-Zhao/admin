function showHideEl(id,btnId){
  var el = document.getElementById(id);
  var btn = document.getElementById(btnId);
  if(el.style.display=='none'){
    el.style.display = 'block';
    btn.className="toggle";
	 btn.title="隐藏";
    dealStyles2();
    dealStyles();
    
  }else{
    el.style.display = 'none';
     btn.className="collapsed";
	 btn.title="展开";
       dealStyles2();
    dealStyles();
  }
}

function showHideE2(id,btnId){
  var el = document.getElementById(id);
  var btn = document.getElementById(btnId);
  if(el.style.display=='none'){
    el.style.display = 'block';
    btn.className="toggle";
	 btn.title="隐藏";
    dealStyles2();
    dealStyles();
    
  }else{
    el.style.display = 'none';
     btn.className="collapsed";
	 btn.title="展开";
       dealStyles2();
    dealStyles();
  }
}
