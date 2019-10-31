

/*
 * 登录页面淡入与输入框方法
 */
function JQ_login(){
	$(".login-box").hide();
	$(".login-box").fadeIn(1000);
	$(".inp").bind("focus",function(){
									$(this).addClass("inp2");
									});
	$(".inp").bind("blur",function(){
									$(this).removeClass("inp2");
									}); 
}

/*
 * 登录页面淡入与输入框方法
 */
function JQ_welcome(){
	$(".wel-bg").animate({left:"150px",top:"100px", height:"250px",width:"500px"},800, function(){
																							$(".wel-nr").show(400);																												
																							});
	
	
}
