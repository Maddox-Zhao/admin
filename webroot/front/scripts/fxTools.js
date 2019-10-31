
/* *********  f(x)Tools ***********
	
	fx-001  ReplaceDot()  //将中文逗号转换为英文逗号


********************************* */

/*
	功能编号：fx-001
	功能描述：将中文逗号转换为英文逗号 str:待替换的字符
	使用说明：在页面内调用该JS文件，然后在需要使用的INPUT或textarea内放上这句代码
	调用方法：onKeyUp="ReplaceDot('XXXX')"
*/
function ReplaceDot(str)
{
    var Obj=document.getElementById(str);
    var oldValue=Obj.value;
    while(oldValue.indexOf("，")!=-1)//寻找每一个中文逗号，并替换
    {
        Obj.value=oldValue.replace("，",",");
        oldValue=Obj.value;
    }
}