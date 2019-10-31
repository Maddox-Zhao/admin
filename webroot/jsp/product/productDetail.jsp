<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>产品详情</title>
<meta http-equiv="content-type" content="text/html; charset=UTF-8" />
<script type="text/javascript"
	src="/front/scripts/miniui3/jquery-1.6.2.min.js"></script>
<script type="text/javascript"
	src="/front/scripts/miniui3/miniui/miniui.js"></script>
<link type="text/css" rel="stylesheet"
	href="/front/scripts/miniui3/miniui/themes/default/miniui.css" />
<link type="text/css" rel="stylesheet"
	href="/front/scripts/miniui3/miniui/themes/icons.css" />

<style type="text/css">
html,body {
	font-size: 12px;
	padding: 0;
	margin: 0;
	border: 0;
	height: 100%;
	overflow: hidden;
}
</style>
</head>
<body>

	<form id="form1" method="post">
		<input name="id" class="mini-hidden" />
		<div style="padding-left:10px;padding-bottom:5px;">
			<table style="table-layout:fixed;">
				<tr>
					<td style="width:70px;">idProduct：</td>
					<td style="width:150px;"><input name="idProduct"
						class="mini-textbox" emptyText="idProduct" /></td>
					<td style="width:40px;">品牌：</td>
					<td style="width:100px;"><input name="brandId"
						class="mini-textbox" /></td>
					<td style="width:40px;">品名：</td>
					<td style="width:70px;"><input name="seriesId"
						class="mini-textbox" /></td>
					<td style="width:40px;">尺寸：</td>
					<td style="width:70px;"><input name="size"
						class="mini-textbox" /></td>
					<!-- 
                    <td rowspan="4"><img src="http://img1.selfimg.com.cn/voguenormal/2015/10/11/1444553981_FzKmpC.jpg" width="200" height="200"/></td>
                     -->
				</tr>

				<tr>
					<td style="width:70px;">型号：</td>
					<td style="width:150px;"><input name="type"
						class="mini-textbox" /></td>
					<td style="width:40px;">材质：</td>
					<td style="width:100px;"><input name="material"
						class="mini-textbox" /></td>
					<td style="width:40px;">颜色：</td>
					<td style="width:70px;"><input name="color"
						class="mini-textbox" /></td>
					<td style="width:60px;">客户群体：</td>
					<td style="width:70px;"><input name="targetCustomers"
						class="mini-textbox" /></td>
				</tr>

				<tr>
					<td style="width:70px;">名称：</td>
					<td style="width:150px;"><input name="name"
						class="mini-textbox" /></td>
					<td style="width:60px;">材质描述：</td>
					<td style="width:100px;"><input name="materialDes"
						class="mini-textbox" /></td>
					<td style="width:60px;">颜色描述：</td>
					<td style="width:70px;"><input name="colorDes"
						class="mini-textbox" /></td>
					<td style="width:80px;">长*宽*高：</td>
					<td style="width:70px;"><input name="ckg" class="mini-textbox" />
					</td>
				</tr>

				<tr>
					<td style="width:70px;">UUID：</td>
					<td style="width:150px;"><input name="uuid"
						class="mini-textbox" /></td>
					<td style="width:40px;">aUUID：</td>
					<td style="width:100px;"><input name="auuid"
						class="mini-textbox" /></td>
					<td style="width:40px;">产地：</td>
					<td style="width:70px;"><input name="chandi"
						class="mini-textbox" /></td>
					<td style="width:40px;">SKU：</td>
					<td style="width:70px;"><input name="sku" class="mini-textbox" />
					</td>
				</tr>



				<tr>
					<td style="width:70px;">类别：</td>
					<td style="width:150px;"><input name="leibie"
						class="mini-textbox" /></td>
					<td style="width:40px;">货源：</td>
					<td style="width:100px;"><input name="supply"
						class="mini-textbox" /></td>
					<td style="width:40px;">真品卡：</td>
					<td style="width:70px;"><input name="realyCar"
						class="mini-textbox" /></td>
					<td style="width:40px;">成本：</td>
					<td style="width:70px;"><input name="cost"
						class="mini-textbox" /></td>
				</tr>



				<tr>
					<td style="width:70px;">位置：</td>
					<td style="width:150px;"><input name="idLocation"
						class="mini-textbox" /></td>
					<td style="width:40px;">前位置：</td>
					<td style="width:100px;"><input name="lastIdLocation"
						class="mini-textbox" /></td>
					<td style="width:40px;">状态：</td>
					<td style="width:70px;"><input name="status"
						class="mini-textbox" /></td>
					<td style="width:40px;">最后经手人：</td>
					<td style="width:70px;"><input name="lastOperator"
						class="mini-textbox" /></td>
				</tr>


				<tr>
					<td style="width:70px;">大陆价：</td>
					<td style="width:150px;"><input name="dlPrice"
						class="mini-textbox" /></td>
					<td style="width:40px;">欧洲价(EU)：</td>
					<td style="width:100px;"><input name="euPrice"
						class="mini-textbox" /></td>
					<td style="width:40px;">代销价：</td>
					<td style="width:70px;"><input name="dxPrice"
						class="mini-textbox" /></td>
					<td style="width:40px;">尚美价：</td>
					<td style="width:70px;"><input name="smPrice"
						class="mini-textbox" /></td>
				</tr>


				<tr>
					<td style="width:70px;">尚上价：</td>
					<td style="width:150px;"><input name="ssPrice"
						class="mini-textbox" /></td>
					<td style="width:40px;">备注：</td>
					<td colspan="5"><textarea class="mini-textarea" nam="remark"></textarea>
					</td>

				</tr>

			</table>
		</div>
		<!-- 
        <fieldset style="border:solid 1px #aaa;padding:3px;">
            <legend >基本信息</legend>
            <div style="padding:5px;">
        <table>
            <tr>
                <td style="width:70px;">姓名</td>
                <td style="width:150px;">    
                    <input name="name" class="mini-textbox" required="true"/>
                </td>
                <td style="width:70px;">性别：</td>
                <td >                        
                    <select name="gender" class="mini-radiobuttonlist">
                        <option value="1">男</option>
                        <option value="2">女</option>
                    </select>
                </td>
                
            </tr>
            <tr>
                <td >年龄：</td>
                <td >    
                    <input name="age" class="mini-spinner" value="25" minValue="1" maxValue="200" />
                </td>
                <td >出生日期：</td>
                <td >    
                    <input name="birthday" class="mini-datepicker" required="true" emptyText="请选择日期"/>
                </td>
            </tr>
            <tr>
                <td ></td>
                <td >    
                    <input name="married" class="mini-checkbox" text="已婚？" trueValue="1" falseValue="0" />
                </td>
                <td ></td>
                <td >    
                    
                </td>
            </tr>     
            <tr>
                <td >国家：</td>
                <td >    
                    <input name="country" class="mini-combobox" url="../data/countrys.txt" />
                </td>
                <td >城市：</td>
                <td >    
                    <input name="city" class="mini-combobox"  />
                </td>
            </tr>
            <tr>
                <td >备注：</td>
                <td colspan="3">    
                    <input name="remarks" class="mini-textarea" style="width:350px;" />
                </td>
            </tr>          
        </table>            
            </div>
        </fieldset>
         -->
		<div style="text-align:center;padding:10px;">
			<a class="mini-button" onclick="onOk"
				style="width:60px;margin-right:20px;">确定</a> <a class="mini-button"
				onclick="onCancel" style="width:60px;">取消</a>
		</div>
	</form>
	<script type="text/javascript">
        mini.parse();

        var form = new mini.Form("form1");

        function SaveData() {
            var o = form.getData();            

            form.validate();
            if (form.isValid() == false) return;

            var json = mini.encode([o]);
            $.ajax({
                url: "",
				type: 'post',
                data: { data: json },
                cache: false,
                success: function (text) {
                    CloseWindow("save");
                },
                error: function (jqXHR, textStatus, errorThrown) {
                    //alert(jqXHR.responseText);
                    CloseWindow();
                }
            });
        }

        ////////////////////
        //标准方法接口定义
        function SetData(data) {
            if (data.action == "edit") {
                //跨页面传递的数据对象，克隆后才可以安全使用
                data = mini.clone(data);

                $.ajax({
                    url: "",
                    cache: false,
                    success: function (text) {	
                    text = {idProduct:idProduct,type:'sku',material:'D94JT',color:'F0002'};
                        var o = mini.decode(text);
                        form.setData(o);
                        //form.setChanged(false);

                        //onDeptChanged();
                        //mini.getbyName("position").setValue(o.position);
                    }
                });
            }
        }

        function GetData() {
            var o = form.getData();
            return o;
        }
        function CloseWindow(action) {            
            if (action == "close" && form.isChanged()) {
                if (confirm("数据被修改了，是否先保存？")) {
                    return false;
                }
            }
            if (window.CloseOwnerWindow) return window.CloseOwnerWindow(action);
            else window.close();            
        }
        function onOk(e) {
            SaveData();
        }
        function onCancel(e) {
            CloseWindow("cancel");
        }
        //////////////////////////////////
        function onDeptChanged(e) {
            var deptCombo = mini.getbyName("dept_id");
            var positionCombo = mini.getbyName("position");
            var dept_id = deptCombo.getValue();

            //positionCombo.load("../data/AjaxService.aspx?method=GetPositionsByDepartmenId&id=" + dept_id);
            positionCombo.setValue("");
        }



    </script>
</body>
</html>
