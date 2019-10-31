<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>产品数据</title>
    <meta http-equiv="content-type" content="text/html; charset=UTF-8" />
    <script type="text/javascript" src="/front/scripts/miniui3/jquery-1.6.2.min.js"></script>
    <script type="text/javascript" src="/front/scripts/miniui3/miniui/miniui.js"></script>
    <link type="text/css" rel="stylesheet" href="/front/scripts/miniui3/miniui/themes/default/miniui.css"/>
    <link type="text/css" rel="stylesheet" href="/front/scripts/miniui3/miniui/themes/icons.css"/>
</head>
<body>
	
	<div style="width:100%;" id="form1">
        <div class="mini-toolbar" style="border-bottom:0;padding:0px;">
              <table class="form-table" border="0" cellpadding="1" cellspacing="2">
				<tr>
                     <td class="form-label" style="width:60px;">idProduct：</td>  
                     <td style="width:150px">
                        <input name="idProduct" class="mini-textbox" onenter="search()"/> 
					 </td>
					 <td class="form-label" style="width:60px;">sku：</td>
					  <td style="width:150px">
                        <input name="sku" class="mini-textbox" onenter="search()"/> 
					 </td>
					 <td class="form-label" style="width:60px;">型号：</td>
					  <td style="width:150px">
                        <input name="type" class="mini-textbox" onenter="search()"/> 
					 </td>
					  <td class="form-label" style="width:40px;">材质：</td>
					  <td style="width:100px">
                        <input name="material" class="mini-textbox" onenter="search()"/> 
					 </td>
					  <td class="form-label" style="width:60px;">颜色：</td>
					  <td style="width:150px">
                        <input name="color" class="mini-textbox" onenter="search()"/> 
					 </td>
					<td rowspan="4"><a class="mini-button" iconCls="icon-search" onclick="search()">查询</a></td>
				 </tr>
				 
				 <tr>
                     <td class="form-label" style="width:60px;">品牌：</td>  
                     <td style="width:150px">
						<input name="brandID" onenter="search()" class="mini-combobox"   textField="brandName" valueField="id" emptyText="请选择..."
						  url="/brand/getListByBrandName.html"   allowInput="true" showNullItem="true" nullItemText="请选择..."/>         
					 </td>
					 <td class="form-label" style="width:60px;">品名：</td>
					  <td style="width:150px">					
						<input name="seriesId" onenter="search()"  class="mini-combobox"   textField="name" valueField="id" emptyText="请选择..."
						  url="/series/getAllSeries.html"  allowInput="false" showNullItem="true" nullItemText="请选择..."/>  
					 </td>
					 <td class="form-label" style="width:60px;">大小：</td>
					  <td style="width:150px">
                        <input name="size" class="mini-textbox" onenter="search()"/> 
					 </td>
					  <td class="form-label" style="width:40px;">客户：</td>
					  <td style="width:100px">
                        <input name="customerName" class="mini-textbox" onenter="search()"/> 
					 </td>
					  <td class="form-label" style="width:60px;">状态：</td>
					  <td style="width:150px">
						<input  name="status" onenter="search()" class="mini-combobox"   textField="value" valueField="id" emptyText="请选择..."
							data="statusData"    allowInput="false" showNullItem="true" nullItemText="请选择..."/>         
					 </td>
				 </tr>
				 
				  <tr>
                     <td class="form-label" style="width:60px;">货源：</td>  
                     <td style="width:150px">
						<input name="supplierId" class="mini-textbox" onenter="search()"/>  
					 </td>
					 <td class="form-label" style="width:60px;">前位置：</td>
					  <td style="width:150px">					
							<input  name="idLastLocation" onenter="search()" class="mini-combobox"   textField="name" valueField="id" emptyText="请选择..."
							 url="/site/getSiteByType.html"  allowInput="false" showNullItem="true" nullItemText="请选择..."/> 
					 </td>
					 <td class="form-label" style="width:60px;">当前位置：</td>
					  <td style="width:150px">
						<input  name="idLocation" onenter="search()" class="mini-combobox"   textField="name" valueField="id" emptyText="请选择..."
							 url="/site/getSiteByType.html"  allowInput="false" showNullItem="true" nullItemText="请选择..."/> 
					 </td>
					  <td class="form-label" style="width:40px;">渠道：</td>
					  <td style="width:100px">
						<input  name="idSellChannel" onenter="search()" class="mini-combobox"   textField="name" valueField="id" emptyText="请选择..."
							 url="/sell/getAllSellChannel.html"  allowInput="false" showNullItem="true" nullItemText="请选择..."/> 
					 </td>
					  <td class="form-label" style="width:80px;">最后经手人：</td>
					  <td style="width:150px">
						<input name="idLastOperator" class="mini-textbox" onenter="search()"/>    
					 </td>
				 </tr>
				 
				 
				 <tr>
                     <td class="form-label" style="width:60px;">入库时间：</td>  
                     <td style="width:150px" colspan="3">
						<input id="inStockDateStart" name="inStockDateStart" class="mini-datepicker"    format="yyyy-MM-dd"  />~
						<input id="inStockDateEnd"  name="inStockDateEnd" class="mini-datepicker"    format="yyyy-MM-dd" />
					 </td>
					 <td class="form-label" style="width:60px;">销售时间：</td>  
                     <td   colspan="3">
						<input id="sellDateStart" name="sellDateStart" class="mini-datepicker"  format="yyyy-MM-dd" />~
						<input id="sellDateEnd" name="sellDateEnd" class="mini-datepicker" format="yyyy-MM-dd"  />
					 </td>

				 </tr>
			  </table>      
        </div>
    </div>
	
    <div style="width:100%;">
        <div class="mini-toolbar" style="border-bottom:0;padding:0px;">
            <table style="width:100%;">
                <tr>
                    <td style="width:100%;">
                        <a class="mini-button" iconCls="icon-add" onclick="add()">增加</a>
                        <a class="mini-button" iconCls="icon-add" onclick="edit()">编辑</a>
                        <a class="mini-button" iconCls="icon-remove" onclick="remove()">删除</a>       
                    </td>
                </tr>
            </table>           
        </div>
    </div>
    <div id="datagrid1" class="mini-datagrid" style="width:100%;height:500px;" allowResize="true"
        url="/product/getJsonList.html"  idField="idProduct" multiSelect="false"  pageSize="50" allowAlternating="true"
        onRowdblclick="edit()">
        <div property="columns">
			<div type="indexcolumn"></div> 
            <div field="idProduct" width="120" headerAlign="center" align="center" allowSort="true">idProduct</div>
			<div field="sku" width="120" headerAlign="center" align="center"  allowSort="true">sku</div> 
			<div field="brandName" width="100" headerAlign="center" align="center"  allowSort="true">品牌</div>
			<div field="seriesName" width="40" headerAlign="center" align="center"  allowSort="true">品类</div>
			<div field="type" width="100" headerAlign="center" align="center"  allowSort="true">型号</div>
			<div field="material" width="60" headerAlign="center" align="center"  allowSort="true">材质</div>
			<div field="color" width="60" headerAlign="center" align="center"  allowSort="true">颜色</div>
			<div field="size" width="40" headerAlign="center" align="center"  allowSort="true">size</div>
			<div field="dlPrice" width="60" headerAlign="center" align="center"  allowSort="true">大陆价</div>
			<div field="euPrice" width="60" headerAlign="center" align="center"  allowSort="true">欧洲价</div>
			<div field="dxPrice" width="60" headerAlign="center" align="center"  allowSort="true">代销价</div>
			<div field="smPrice" width="60" headerAlign="center" align="center"  allowSort="true">尚美价</div>
			<div field="ssPrice" width="60" headerAlign="center"  align="center"  allowSort="true">尚上价</div>
			<div field="cost" width="60" headerAlign="center" align="center"  allowSort="true">成本</div>
			<div field="idCostCurrency" width="60" headerAlign="center" align="center"  allowSort="true" renderer="renderCurrency">成本币种</div>
			<div field="exchangeRate" width="60" headerAlign="center" align="center"  allowSort="true">汇率</div>
			<div field="curSiteName" width="60" headerAlign="center" align="center"  allowSort="true">当前位置</div>
			<div field="status" width="60" headerAlign="center" align="center"  allowSort="true">状态</div>
			<div field="salePrice" width="60" headerAlign="center" align="center"  allowSort="true">售价</div>
			<div field="salePriceCurrency" width="60" headerAlign="center" align="center"  allowSort="true" renderer="renderCurrency">销售币种</div>
			<div field="instock" width="120" headerAlign="center" align="center"  allowSort="true" renderer="renderDate" >入库时间</div>
			<div field="sellDate" width="80" headerAlign="center" align="center"  allowSort="true"   renderer="renderDate1" >销售时间</div>
			<div field="sellIdOrder" width="60" headerAlign="center" align="center"  allowSort="true">销售单号</div>
			<div field="sellChannel" width="60" headerAlign="center" align="center"  allowSort="true">销售渠道</div>
			<div field="customerName" width="80" headerAlign="center" align="center"  allowSort="true">客户</div>
            
        </div>
    </div>
    

    <script type="text/javascript">
		var statusData = [{id:1,value:"可售"},{id:2,value:"运输在途"},{id:3,value:"预订"},{id:4,value:"已售"},{id:5,value:"寄卖已售未结算"},{id:6,value:"准入库"},]
        mini.parse();
		
        var grid = mini.get("datagrid1");
        grid.load();
       // grid.sortBy("createtime", "desc");
		function renderDate(row)
		{
			if(!row.value) return "";
			var d = new Date(parseInt(row.value));     
			return formatDate(d,true);
		}
		function renderDate1(row)
		{
			if(!row.value) return "";
			var d = new Date(parseInt(row.value));     
			return formatDate(d,false);
		}
		
		function renderCurrency(row)
		{
			if(!row.value) return "";
			if(row.value  == '1') return "RMB";  
			if(row.value  == '2') return "EU";     
			if(row.value  == '3') return "HKD";
			if(row.value  == '4') return "US";
			return formatDate(d,false);
		}
		function   formatDate(now,showTime)
		{     
              var   year=now.getFullYear();     
              var   month=now.getMonth()+1;    
              var   date=now.getDate();     
              var   hour=now.getHours();
              var   minute=now.getMinutes();
              var   second=now.getSeconds();
			  var   result = year+"-"+month+"-"+date;
			  if(showTime == true)
			  {
			  	result += ""+hour+":"+minute+":"+second;
			  }
              return result;
			  
        }     
        function add() {
            
            mini.open({
                url: bootPATH + "../demo/CommonLibs/EmployeeWindow.html",
                title: "新增员工", width: 600, height: 360,
                onload: function () {
                    var iframe = this.getIFrameEl();
                    var data = { action: "new"};
                    iframe.contentWindow.SetData(data);
                },
                ondestroy: function (action) {

                    grid.reload();
                }
            });
        }
		
		//查询
		function search()
		{
			var form = new mini.Form("#form1");
            var data = form.getData();      //获取表单多个控件的数据
			
			//处理日期
			 var date = mini.get("inStockDateStart").getValue();
			 if(date instanceof  Date)
			 {
			 	data.inStockDateStart = formatDate(date);
			 }
			 var date = mini.get("inStockDateEnd").getValue();
			 if(date instanceof  Date)
			 {
			 	data.inStockDateEnd = formatDate(date);
			 }
			  var date = mini.get("sellDateStart").getValue();
			  if(date instanceof Date)
			 {
			 	data.sellDateStart = formatDate(date);
			 }
			 var date = mini.get("sellDateEnd").getValue();
			 if(date instanceof Date)
			 {
			 	data.sellDateEnd = formatDate(date);
			 }
			 
			 
			grid.load(data);
		}
		
		
		
        function edit() {
         
            var row = grid.getSelected();
            if (row) {
                mini.open({
                    url: "/jsp/product/productDetail.jsp",
                    title: "产品详情", width: 1100, height: 600,
                    onload: function () {
                        var iframe = this.getIFrameEl();
                        var data = { action: "edit", id: row.idProduct };
                        iframe.contentWindow.SetData(data);
                    },
                    ondestroy: function (action) {
                       // grid.reload();
                    }
                });
                
            } else {
                alert("请选中一条记录");
            }
            
        }
        function remove() {
            
            var rows = grid.getSelecteds();
            if (rows.length > 0) {
                if (confirm("确定删除选中记录？")) {
                    var ids = [];
                    for (var i = 0, l = rows.length; i < l; i++) {
                        var r = rows[i];
                        ids.push(r.id);
                    }
                    var id = ids.join(',');
                    grid.loading("操作中，请稍后......");
                    jQuery.ajax({
                        url: "" +id,
                        success: function (text) {
                            grid.reload();
                        },
                        error: function () {
                        }
                    });
                }
            } else {
                alert("请选中一条记录");
            }
        }
 
 
 

    </script>
 
</body>
</html>