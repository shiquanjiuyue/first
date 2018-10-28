/**
 * 存放通用方法的js
 */
/** 重写日期格式化函数 */
Date.prototype.format = function (fmt) { //author: meizz
  var o = {
    "M+": this.getMonth() + 1, //月份
    "d+": this.getDate(), //日
    "h+": this.getHours(), //小时
    "m+": this.getMinutes(), //分
    "s+": this.getSeconds(), //秒
    "q+": Math.floor((this.getMonth() + 3) / 3), //季度
    "S": this.getMilliseconds() //毫秒
  };
  if (/(y+)/.test(fmt)) fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
  for (var k in o)
  if (new RegExp("(" + k + ")").test(fmt)) fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
  return fmt;
}

/** 判断是否有值 */
function hasLength(s){
	if(s != null && s != "" && s != undefined){
		return true;
	} else {
		return false;
	}
}

/**
 * 封装的ajax请求
 * @param url 请求的url地址(去掉站点根路径)
 * @param data 传入的参数
 * @param successMethod 成功后并且无错误需要执行的方法
 * @param overMethod 执行完成后并且不管有误错误需要执行的方法
 */
function ajaxInvoke(url, data, successMethod, overMethod){
	$.ajax({
		async : false,
		cache : false,
		type : 'POST',
		data : data,
		dataType : "json",
		url : root + url,
		error: function () {
			layer.msg("请求异常！" ,{icon:2});
		},success:function(data){
			if(data.errorCode != -1){
				if(successMethod){
					successMethod();
				}
				layer.msg(data.message ,{icon:1});
			}else{
				layer.msg(data.message ,{icon:2});
			}
			if(overMethod){
				overMethod();
			}
		}
	});
}