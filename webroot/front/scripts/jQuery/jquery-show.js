

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
