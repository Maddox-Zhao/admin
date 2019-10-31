/**
 * 统计报表模块的JS代码
 *
 */
// ===========================================
// 使用方法如下：
// ===========================================
// -----------------------
// 第一步：在页面上添加以下代码
// -----------------------
// <script type='text/javascript'
// src='$ctx/scripts/admin/hs-analysis.js'></script>
// -----------------------
// 第二步：函数调用方法：
// -----------------------
function singleOrderAnalysisFormSubmit(formObj) {
	var formMap = getValues(formObj);
	AnalysisAction.getSingleOrderAnalysis(formMap, callBackOrderAnalysis);
}

function multiOrderAnalysisFormSubmit(formObj) {
	var formMap = getValues(formObj);
	AnalysisAction.getMultiOrderAnalysis(formMap, callBackOrderAnalysis);
}
function callBackOrderAnalysis(reportHtml) {
	// alert(reportHtml);
	$('report-div').innerHTML = reportHtml;
}

function getValues(eleOrNameOrId) {

  var ele = null;
  if (typeof eleOrNameOrId == "string") {
    ele = document.forms[eleOrNameOrId];
    if (ele == null) ele = dwr.util.byId(eleOrNameOrId);
  }
  else if (dwr.util._isHTMLElement(eleOrNameOrId)) {
    ele = eleOrNameOrId;
  }
  if (ele != null) {
    if (ele.elements == null) {
      alert("getFormValues() requires an object or reference to a form element.");
      return null;
    }
    var reply = {};
    var name;
    var value;
    for (var i = 0; i < ele.elements.length; i++) {
      if (ele[i].type in {button:0,submit:0,reset:0,image:0,file:0}) continue;
      if (ele[i].name) {
        name = ele[i].name;
        value = ele[i].value;
      }
      else {
        if (ele[i].id) name = ele[i].id;
        else name = "element" + i;
        value = dwr.util.getValue(ele[i]);
      }
      reply[name] = value;
    }
    return reply;
  }
}
