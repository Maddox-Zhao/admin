
// ===========================================
// 使用方法如下：
// ===========================================
// -----------------------
// 第一步：在页面上添加以下代码
// -----------------------
// <script type='text/javascript' src='${ctx}/scripts/hs-tableUtil.js'></script>
// -----------------------
// 第二步：函数调用方法：
// -----------------------


// ======================================
// 表格的操作
// ======================================
// 删除行
function removeTableRow(table_id, row_index) {
	var tableObj = document.getElementById(table_id);
	if (!tableObj)
		return;
	tableObj.deleteRow(row_index);
}

// 删除列,table_id：表格的ID,col_index：要删除的列index
function removeTableCol(table_id, col_index) {
	var tableObj = document.getElementById(table_id);
	if (!tableObj)
		return;
	for (var i = 0; i < tableObj.rows.length; i++) {
		if (tableObj.rows[i].cells.length == 2)
			tableObj.rows[i].cells[col_index].innerHTML = "...";
		else
			tableObj.rows[i].deleteCell(col_index);
	}
}

// 删除表格,table_id：表格的ID
function removeTable(table_id) {
	var tableObj = document.getElementById(table_id);
	if (!tableObj)
		return;
	tableObj.parentNode.removeChild(tableObj);
}

// 删除表格的列，除了第一列
function removeColExceptFirst(table_id) {
	var tableObj = document.getElementById(table_id);
	if (!tableObj)
		return;
	for (var i = 0; i < tableObj.rows.length; i++) {
		while (tableObj.rows[i].cells.length > 1) {
			if (tableObj.rows[i].cells.length == 2) {
				tableObj.rows[i].cells[1].innerHTML = "...";
				break;
			} else
				tableObj.rows[i].deleteCell(1);
		}
	}
}