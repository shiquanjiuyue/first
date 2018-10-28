var oTable = null;
var initTableData = function(){
	var table = $('#permission_table');
	//第一次检索时初始化Datatable
	if(oTable == null){
		oTable = table.dataTable({
			"bProcessing" : true, //DataTables载入数据时，是否显示‘进度’提示 
			"bServerSide": true, //开启服务器模式，使用服务器端处理配置datatable
			"bStateSave" : true,//是否打开客户端状态记录功能,此功能在ajax刷新纪录的时候不会将个性化设定回复为初始化状态 
			//"bJQueryUI" : true,//是否使用 jQury的UI theme 
			"aLengthMenu" : [10, 15, 20], //更改显示记录数选项
			"iDisplayLength": 10,//每页显示10数据
			"bPaginate" : true, //是否显示（应用）分页器 
			"bInfo" : true, //是否显示页脚信息，DataTables插件左下角显示记录数 
			"bFilter":false,//是否显示查询
			"sPaginationType" : "full_numbers", //详细分页组，可以支持直接跳转到某页 
			"bSort" : false, //是否启动各个字段的排序功能 
			//"aaSorting" : [[1, "asc"]], //默认的排序方式，第2列，升序排列 
			bLengthChange:false,
			"sAjaxSource": root + "/x/permission/queryList",// get地址
			"fnServerData": function(sSource, aoData, fnCallback) {
				/* post 方法调用*/
				$.ajax({
					"type": "POST",
					"url": sSource,
					"dataType": "json",
					"data" : { 
						aoData : JSON.stringify(aoData) 
					}, 
					"success": function (resp) {
						fnCallback(resp);
					},
				 	error:function(response, textStatus, errorThrown){
				 	}
				});
			},
			"bAutoWidth": false, //自适应宽度
			//默认排序
			"order": [
			          [0, 'asc']//第一列正序
			          ],  
			          //向服务器传额外的参数
			          "fnServerParams": function (aoData) {
			        	  aoData.push(
			        			  { "name": "search_permission_name", "value": $("#search_permission_name").val() }
			        	  )
			          },	
			          //配置列要显示的数据
			          "columns": [
			                      { "data": ""}, 
			                      { "data": "permissionSn","bVisible":false},      
			                      { "data": "name" },
			                      { "data": "expression" },			                     
			                      { "data": "" }
			                      ],
			                      "fnRowCallback" : function(nRow, aData, iDisplayIndex) {//相当于对字段格式化  
			                      },			                      
			                      //按钮列
			                      "columnDefs": [
			                                     {
			                                    	 "targets": [0],
			                                    	 "data": null,
			                                    	 "defaultContent": "<input type='checkbox' name='checkbox'>"
			                                     },   			                                     
			                                     {
			                                    	 "targets": -1,//操作列
			                                    	 "data": null,
			                                    	 "defaultContent": "<a title='编辑' href='javascript:;' onclick='admin_permission_edit('权限编辑','admin-permission-add.html','1','','310')' class='ml-5' style='text-decoration:none'><i class='Hui-iconfont'>&#xe6df;</i></a> <a title='删除' href='javascript:;' onclick='admin_permission_del(this)' class='ml-5' style='text-decoration:none'><i class='Hui-iconfont'>&#xe6e2;</i></a>"
			                                     }
			                                     ] ,                 
		});
	}
}

/** 页面初始化 */
$(document).ready(function(){
	// 加载表格数据
	initTableData();
});

/*
参数解释：
title	标题
url		请求的url
id		需要操作的数据id
w		弹出层宽度（缺省调默认值）
h		弹出层高度（缺省调默认值）
*/
/*管理员-权限-添加*/
function admin_permission_add(title,url,w,h){
	layer_show(title,url,w,h);
}
/*管理员-权限-编辑*/
function admin_permission_edit(title,url,id,w,h){
	layer_show(title,url,w,h);
}

/** 删除 */
function admin_permission_del(obj){
	// 获取点击的按钮的所在行
	var objs = $(obj).parents('tr');
	// 获取所在行的数据
	var data = oTable.fnGetData( objs );
	layer.confirm('确认要删除吗？', {icon: 3, title:'提示'}, function(index){
		ajaxInvoke('/x/permission/delete', {"permissionSn" : data.permissionSn}, reload, null);
	});
}

/** 批量删除 */
function batchDelete(){
	var ids = "";
	$("#permission_table input[type='checkbox'][name='checkbox']").each(function(){
		if($(this).prop("checked")){
			 var obj = $(this).parents('tr'); 
			 var data = oTable.fnGetData( obj );
			 ids += data.permissionSn + ",";
		}
	});
	if(ids == ""){
		layer.msg("请选择要删除的数据" ,{icon:2});
		return;
	}else{
		ids= ids.substring(0, ids.length - 1);
	}
	layer.confirm('确认要删除吗？', {icon: 3, title:'提示'}, function(index){
		ajaxInvoke('/x/permission/batchDelete?ids='+ids, null, null, reload);
		$("#permission_table input[type='checkbox']").prop('checked',false);
	});
}

/** 重新加载表格数据 */
function reload(){
	if(oTable!=null){  
		oTable.fnClearTable(0);  
		oTable.fnDraw(); //重新加载数据  
	}	
}
