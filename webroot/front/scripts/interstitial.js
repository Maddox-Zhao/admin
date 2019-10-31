 var ads_agt=navigator.userAgent.toLowerCase();
var ads_is_major = parseInt(navigator.appVersion);

var ads_is_ie     = ((ads_agt.indexOf("msie") != -1) && (ads_agt.indexOf("opera") == -1) && (ads_agt.indexOf("omniweb") == -1));
var ads_is_ie3    = (ads_is_ie && (ads_is_major < 4));
var ads_is_ie4    = (ads_is_ie && (ads_is_major == 4) && (ads_agt.indexOf("msie 5")==-1) && (ads_agt.indexOf("msie 6")==-1));
var ads_is_ie4up    = (ads_is_ie    && (ads_is_major >= 4));
var ads_is_ie5    = (ads_is_ie && (ads_is_major == 4) && (ads_agt.indexOf("msie 5.0")!=-1) );
var ads_is_ie5up    = (ads_is_ie    && !ads_is_ie3 && !ads_is_ie4);
var ads_is_ie5_1_macbeta = (ads_is_ie && (ads_agt.indexOf("mac")!=-1) && (ads_agt.indexOf("5.1b")!=-1));

var ads_is_nav    = ((ads_agt.indexOf('mozilla')!=-1) && (ads_agt.indexOf('spoofer')==-1)
     && (ads_agt.indexOf('compatible') == -1) && (ads_agt.indexOf('opera')==-1)
     && (ads_agt.indexOf('webtv')==-1));
var ads_is_nav6 = (ads_is_nav && (ads_agt.indexOf("netscape6")!=-1) && (ads_agt.indexOf("6.1")==-1));
var ads_is_nav6up = (ads_is_nav && !ads_is_nav4 && !ads_is_nav3 && !ads_is_nav2);
var ads_is_nav6_1 = (ads_is_nav && (ads_agt.indexOf("netscape6")!=-1) && (ads_agt.indexOf("6.1")!=-1));
var ads_is_nav6_1up = (ads_is_nav && !ads_is_nav6 && !ads_is_nav4 && !ads_is_nav3 && !ads_is_nav2);
getsGlu = ((ads_is_ie5up && !ads_is_ie5_1_macbeta) || ads_is_nav6_1up);
function changediv()
{
document.getElementById('hiddenLayer').style.display = "block";
 //document.getElementById('interstitialFrame').src = adInterPath;
setTimeout("hidediv()",2000)
}
function hidediv()
{
document.getElementById('hiddenLayer').style.display="none";
//SYtag=2;
}
function showfull()
{
setTimeout("changediv()",2000);
}