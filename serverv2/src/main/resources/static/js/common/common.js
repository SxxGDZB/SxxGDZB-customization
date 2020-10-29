/**
 * 得到根路径
 * @returns
 */
function getRootPath(){
    var curWwwPath=window.document.location.href;
    var pathName=window.document.location.pathname;
    var pos=curWwwPath.indexOf(pathName);
    var localhostPaht=curWwwPath.substring(0, pos);
    return localhostPaht;
}
/**
 * 弹框
 * @param title 标题
 * @param url 路径
 * @param w 宽度
 * @param h 高度
 * @returns
 */
function openPopUp(title,url,w,h){
	/*静态资源 ----> 页面跳转URL Map*/
	var staticResource = indexHome(window).staticResource;
	w = w || 50;
	h = h || 70;
	var index = layer.open({
        title: title,
        type: 2,
        shade: 0.2,
        maxmin:true,
        //shadeClose: true,
        area: [w + '%', h +'%'],
        content: staticResource.get(url)
    });
}
/**
 * 去除空格
 */
String.prototype.NoSpace = function () {
    return this.replace(/\s+/g, "");
};
/**
 * 返回数组下标
 */
Array.prototype.indexOf = function(val) {
	for (var i = 0; i < this.length; i++) {
		if (this[i] == val)
			return i;
	}
	return -1;
};
/**
 * 对象数组 根据属性类容返回数组下标
 */
Array.prototype.indexObjectOf = function(fieldName,val){
	for (var i = 0; i < this.length; i++) {
		console.log(this[i][fieldName] + "----------" )
		console.log(val);
		if (this[i][fieldName] == val)
			return i;
	}
	return -1;
}

/**
 * 删除数组中第一个匹配的元素，成功则返回位置索引，失败则返回 -1。
 */
Array.prototype.deleteElementByValue = function(varElement)
{
    var numDeleteIndex = -1;
    for (var i=0; i<this.length; i++)
    {
        // 严格比较，即类型与数值必须同时相等。
        if (this[i] === varElement)
        {
            this.splice(i, 1);
            numDeleteIndex = i;
            break;
        }
    }
    return numDeleteIndex;
}
/**
 * 根据下标删除数组内容
 */
Array.prototype.remove = function(dx) {
	for (var i = 0, n = 0; i < this.length; i++) {
		if (this[i] != this[dx]) {
			this[n++] = this[i]
		}
	}
	this.length -= 1
}
/**
 * 得到主页
 */
var indexHome = function(elem,i) {
		if(typeof(i) != "undefined" || i != ''){
			i = i + 1;
		}
	   if(elem.index == 0 || i > 5){
			return elem;
	   }
	return indexHome(elem.parent,i);
}
/**
 * list转化 树List
 * @param rows list
 * @returns
 */
function convert(rows) {
	function exists(rows, parentId) {
		for (var i = 0; i < rows.length; i++) {
			if (rows[i].id == parentId)
				return true;
		}
		return false;
	}
	var nodes = [];
	for (var i = 0; i < rows.length; i++) {
		var row = rows[i];
		if (!exists(rows, row.parentId)) {
			nodes.push({
				id : row.id,
				name:row.name,
				parentId:row.parentId,
				title:row.name
			});
		}
	}
	var toDo = [];
	for (var i = 0; i < nodes.length; i++) {
		
		toDo.push(nodes[i]);
	}
	while (toDo.length) {
		var node = toDo.shift(); 
		for (var i = 0; i < rows.length; i++) {
			var row = rows[i];
			if (row.parentId == node.id) {//寻找子元素
				var child = {
					id : row.id,
					name:row.name,
					parentId:row.parentId,
					title:row.name
				};
				if (node.children) {
					
					node.children.push(child);
				} else {
					
					node.children = [ child ];
				}
				toDo.push(child);
			}
		}
	}
	return nodes;
}
/**
 * 返回数据全局定义
 */
var callBackData = function() {
	var data = {};
	return data;
};
/**
 * 数组去重复
 */
Array.prototype.setList = function(){  
    var arr=[];    //定义一个临时数组  
    for(var i = 0; i < this.length; i++){    //循环遍历当前数组  
        //判断当前数组下标为i的元素是否已经保存到临时数组  
        //如果已保存，则跳过，否则将此元素保存到临时数组中  
        if(arr.indexOf(this[i]) == -1){
            arr.push(this[i]);  
        }
    }
    return arr;  
}
/**
 * JWT验证
 * @param elem
 * @returns
 */
function JwtErr(elem){
	layer.open({
        type: 1
        ,area: [ '35%', '50%']
        ,id: 'JwtErr' //防止重复弹出
        ,content: '<div style="padding: 20px 100px;">JwtErr验证失败，请重新登录验证!!!!! </div>'
        ,btn: '关闭'
        ,btnAlign: 'r' //按钮居中
        ,shade: 0 //不显示遮罩
        ,shadeClose :true
        ,yes: function(){
          layer.closeAll();
          window.location.href = "/";
        },cancel: function(){
        	window.location.href = "/";
          }
      });
}
//禁止页面鼠标事件
function Unclickable() {
	$("body").css("pointer-events", "none");
}
//取消禁止页面鼠标事件
function Mayclickable() {
	$("body").css("pointer-events", "");
}
//缓存对象 --->待加入 jwt 身份验证 页面提示
var authorization = localStorage.getItem('Authorization');
var authUser = JSON.parse(localStorage.getItem('authUser'));
(function ($) {
	/**
	 * 路径携带的参数
	 * @param $
	 * @returns
	 */
    $.getUrlParam = function (name) {
        var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)");
        var r = window.location.search.substr(1).match(reg);
        if (r != null) return unescape(r[2]); return null;
    }
})(jQuery);
/**
 * 时间格式转化 DATE ---> String
 */
Date.prototype.Format = function (fmt) {
    var o = {
        "M+": this.getMonth() + 1,
        "d+": this.getDate(),
        "h+": this.getHours(),
        "m+": this.getMinutes(),
        "s+": this.getSeconds(),
        "q+": Math.floor((this.getMonth() + 3) / 3),
        "S": this.getMilliseconds()
    };
    if (/(y+)/.test(fmt))
        fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
    for (var k in o)
        if (new RegExp("(" + k + ")").test(fmt))
            fmt = fmt.replace(RegExp.$1, (RegExp.$1.length === 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
    return fmt;
}
/**
 * 得到多少天前后的日期
 * @param a
 * @returns
 */
function fun_day(a){
    var date1 = new Date(),
    time1 = date1.getFullYear()+"-"+(date1.getMonth()+1)+"-"+date1.getDate();
    var date2 = new Date(date1);
    date2.setDate(date1.getDate() + a);
    return date2.Format("yyyy-MM-dd hh:mm:ss");
}
function fun_month(a){
	var date1 = new Date(),
    time1 = date1.getFullYear()+"-"+(date1.getMonth()+1)+"-"+date1.getDate();
    var date2 = new Date(date1);
    date2.setMonth(date1.getMonth() + a);
    return date2.Format("yyyy-MM-dd hh:mm:ss");
}
function fun_hours(a){
	var date1 = new Date(),
    time1 = date1.getFullYear()+"-"+(date1.getMonth()+1)+"-"+date1.getDate();
    var date2 = new Date(date1);
    date2.setHours(date2.getHours() + a);
    return date2.Format("yyyy-MM-dd hh:mm:ss");
}
function fun_days(a,date1){
	var time1 = date1.getFullYear()+"-"+(date1.getMonth()+1)+"-"+date1.getDate();
    var date2 = new Date(date1);
    date2.setDate(date1.getDate() + a);
    return date2.Format("yyyy-MM-dd hh:mm:ss");
}
/*OneNet 请求 设备批量命令下发，异步操作*/
function oneNet(e,hint,w){
	$.ajax({
		url: "/onenet/issuedList",
	    type: "POST",
	    data:JSON.stringify(e),
	    dataType : "json",
	    cache:true, 
        async:false, 
        traditional:true,
　　		contentType: "application/json;charset=utf-8",
	    headers: { "Authorization": authorization ,"dz-usr": authUser.uid},//通过请求头来发送token，放弃了通过cookie的发送方式
	    success:function(data){
	    	if(hint){
	    		if(data.result.data[e[0].imei] == 'read time out'){
		    		w.layer.msg('连接超时', {
		    			  icon: 2,
		    			  offset:'rt',
		    			  anim: 2,
		    			  time: 3000 //2秒关闭（如果不配置，默认是3秒）
		    		});
		    	}else if(data.result.data[e[0].imei].error == 'device not online') {
		    		w.layer.msg('设备掉线', {
		    			  icon: 2,
		    			  offset:'rt',
		    			  anim: 2,
		    			  time: 3000 //2秒关闭（如果不配置，默认是3秒）
		    		});
		    	}else if(data.result.data[e[0].imei].error == 'auth failed: not found'){
		    		w.layer.msg('auth failed: not found', {
		    			  icon: 2,
		    			  offset:'rt',
		    			  anim: 2,
		    			  time: 3000 //2秒关闭（如果不配置，默认是3秒）
		    		});
		    	}else{
		    		w.layer.msg(data.msg + "请10秒后查看", {
		    			  icon: 1,
		    			  offset:'rt',
		    			  anim: 2,
		    			  time: 3000 //2秒关闭（如果不配置，默认是3秒）
		    		});
		    	}
	    	}
	    }
	});
}
/*3:上报告警信息，4:上报系统参数*/
var onenetVariable = function(imei,eqptType,reg){
	var e = {},r= {},array = new Array();
	e.imei = imei;
	r.reg_00 = reg;
	e.register = r;
	e.eqptType = eqptType;
	array.push(e);
	return array;
}
/***
 * url ---> 路径
 * isLeft --> 是否刷新左菜单	
 * 
 * 
 ***/
function DELETE(url,isLeft){
	$.ajax({
		url: url,
		type : "DELETE",
　　		dataType : "json",
　　		contentType: "application/json;charset=utf-8",
	    headers: { "Authorization": authorization },//通过请求头来发送token，放弃了通过cookie的发送方式
	    success:function(data){
    	 	if(data.success){
    	 		if(isLeft){
    	 			indexHome(window).init();
    	 			flush();
    	 		}else{
    	 			flush(currentPage, jsonEntity);
    	 		}
    	 		layer.msg(data.msg,{icon:6, time: 2000});
			}else{
				layer.msg(data.msg,{icon: 5, time: 2000});
			}
	    }
	});
}
/**
 * 修改 
 * @param url 路径
 * @param parameterDate 参数
 * @param isLeft 是否刷新左菜单
 * @returns
 */
function UPDATE(url,parameterDate,isLeft){
	$.ajax({
		url: url,
	    type: "PUT",
	    data:JSON.stringify(parameterDate),
	    dataType : "json",
　　		contentType: "application/json;charset=utf-8",
	    headers: { "Authorization": authorization },//通过请求头来发送token，放弃了通过cookie的发送方式
	    success:function(data){
	    	 	if(data.success || data.code == 3000){
	    	 		layer.msg(data.msg, {
	    	 			icon: 6,
	    	 			time: 1000 //2秒关闭（如果不配置，默认是3秒）
    	 			}, function(){
    	 				var iframeIndex = parent.layer.getFrameIndex(window.name);
		    	 		parent.layer.close(iframeIndex);
    	 				if(isLeft){
    	 					console.log(indexHome(window).index);
    	 					indexHome(window).init();
    	 					window.parent.flush();
    	 				}else{
    	 					window.parent.flush(window.parent.currentPage,window.parent.jsonEntity);
    	 				}
    	 			});
				}else{
					layer.msg(data.msg,{icon: 5, time: 2000});
				}
	    }
	});
}
/**
 * 添加
 * @param url 路径
 * @param parameterDate 参数
 * @param isLeft 是否刷新左菜单
 * @returns
 */
function ADD(url,parameterDate,isLeft){
	$.ajax({
			url: url,
		    type: "POST",
		    data:JSON.stringify(parameterDate),
		    dataType : "json",
	　　		contentType: "application/json;charset=utf-8",
		    headers: { "Authorization": authorization },//通过请求头来发送token，放弃了通过cookie的发送方式
		    success:function(data){
	    	 	if(data.success){
	    	 		layer.msg(data.msg, {
	    	 			  icon: 6,
	    	 			  time: 2000 //2秒关闭（如果不配置，默认是3秒）
	    	 			}, function(){
	    	 				var iframeIndex = parent.layer.getFrameIndex(window.name);
			    	 		parent.layer.close(iframeIndex);
	    	 				if(isLeft){
	    	 					indexHome(window).init();
				    	 		var iframeIndex = parent.layer.getFrameIndex(window.name);
				    	 		parent.layer.close(iframeIndex);
				    	 		window.parent.initTreeTable();
	    	 				}else{
	    	 					window.parent.flush(window.parent.currentPage,window.parent.jsonEntity);
	    	 				}
	    	 			}); 
				}else{
					layer.msg(data.msg,{icon: 5, time: 2000});
				}
		    }
	});
}
/**
 * 字典表查询
 * @param type 类型
 * @returns 数组对象
 */
var _sysDict =  function(type){
	 var _data = null; 
	 $.ajax({
			url: "/dictionary/selectByType/" + type,
		    type: "GET",
		    dataType : "json",
		    cache:true, 
	        async:false, 
	　　		contentType: "application/json;charset=utf-8",
		    headers: { "Authorization": authorization },//通过请求头来发送token，放弃了通过cookie的发送方式
		    success:function(data){
		    	 	if(data.success){
		    	 		_data = data.data.data;
					}else{
						
					}
		    }
	})
	return _data;
}

var _sysDictV = function(type,k){
	var _data = null; 
	 $.ajax({
			url: "/dictionary/selectByTypeAndKey/" + type + "/" + k,
		    type: "GET",
		    dataType : "json",
		    cache:true, 
	        async:false, 
	　　		contentType: "application/json;charset=utf-8",
		    headers: { "Authorization": authorization },//通过请求头来发送token，放弃了通过cookie的发送方式
		    success:function(data){
	    	 	if(data.success){
	    	 		_data = data.data.data;
				}
		    }
	})
	return _data;
}

/**
 * 获取数组中需要的 对象 --> 属性值
 */
var array_kv = function (array,k) {
	var v = null;
	array.forEach(function(item){
		//console.log(item);
    });
}
Date.prototype.format = function() {
	var s = '';
	s += this.getFullYear() + '-'; // 获取年份。
	if((this.getMonth() + 1) >= 10) {// 获取月份。
		s += (this.getMonth() + 1) + "-";
	} else {
		s += "0" + (this.getMonth() + 1) + "-";
	}
	if(this.getDate() >= 10) {// 获取日。
		s += this.getDate();
	} else {
		s += "0" + this.getDate();
	}
	return(s); // 返回日期。
};
function getAll(begin, end) {
	var ab = begin.split("-");
	var ae = end.split("-");
	var db = new Date();
	db.setUTCFullYear(ab[0], ab[1] - 1, ab[2]);
	var de = new Date();
	de.setUTCFullYear(ae[0], ae[1] - 1, ae[2]);
	var unixDb = db.getTime();
	var unixDe = de.getTime();
	var str = "";
	for(var k = unixDb + 24 * 60 * 60 * 1000; k < unixDe;) {
		str += (new Date(parseInt(k)).format()) + ",";
		k = k + 24 * 60 * 60 * 1000;
	}
	return str;
}

/*var _alarmIcon = function(v){
	var _icon = '';
	v.forEach(function(item, i){
		 if(item.msg == "过流告警"){
			 _icon += '<i class="iconfont _alarm_icon" title='+item.msg + ":" + item.value +' >&#xe652;</i>   ';
		 }else if(item.msg == "线温告警"){
			 _icon += '<i class="iconfont _alarm_icon" title='+item.msg + ":" + item.value +' >&#xe62d;</i>   ';
		 }else if(item.msg == "过载告警"){
			 _icon += '<i class="iconfont _alarm_icon" title='+item.msg + ":" + item.value +' >&#xe655;</i>   ';
		 }else if(item.msg == "过压告警"){
			 _icon += '<i class="iconfont _alarm_icon" title='+item.msg + ":" + item.value +' >&#xe651;</i>   ';
		 }else if(item.msg == "欠压告警"){
			 _icon += '<i class="iconfont _alarm_icon" title='+item.msg + ":" + item.value +' >&#xe62c;</i>   ';
		 }else if(item.msg == "掉电告警"){
			 _icon += '<i class="iconfont _alarm_icon" title='+item.msg + ":" + item.value +' >&#xe620;</i>   ';
		 }else if(item.msg == "漏电流告警"){
			 _icon += '<i class="iconfont _alarm_icon" title='+item.msg + ":" + item.value +' >&#xe65b;</i>   ';
		 }else if(item.msg == "烟雾告警"){
			 _icon += '<i class="iconfont _alarm_icon" title='+item.msg + ":" + item.value +' >&#xe61b;</i>   ';
		 }
	 });
	return _icon;
}*/
var _alarmIcons = function (v,e){
	var _icon = '';
	var msg = '';
	e.forEach(function(val,index){
		if($.isEmptyObject(val) == false){
			msg = msg + val.msg + ":" + val.value + "\n";
		}
		
	})
	v.forEach(function(item, i){
		 if(item == 1){
			 _icon += '<i class="iconfont _alarm_icon"  >&#xe652;</i>   ';
		 }else if(item == 2){
			 _icon += '<i class="iconfont _alarm_icon"  >&#xe62d;</i>   ';
		 }else if(item == 3){
			 _icon += '<i class="iconfont _alarm_icon"  >&#xe655;</i>   ';
		 }else if(item == 4){
			 _icon += '<i class="iconfont _alarm_icon"  >&#xe651;</i>   ';
		 }else if(item == 5){
			 _icon += '<i class="iconfont _alarm_icon"  >&#xe62c;</i>   ';
		 }else if(item == 6){
			 _icon += '<i class="iconfont _alarm_icon"  >&#xe620;</i>   ';
		 }else if(item == 7){
			 _icon += '<i class="iconfont _alarm_icon"  >&#xe65b;</i>   ';
		 }else if(item == 8){
			 _icon += '<i class="iconfont _alarm_icon"  >&#xe61b;</i>   ';
		 }else{
			 
		 }
	 });
	return '<div title="' + msg + '">' + _icon + '</div>';
}

/**
 * 获取菜单页面ID
 * @returns
 */
function tabMenuId(){
	var elm = $(".layui-tab-item.layui-show", parent.document).find("iframe");
	return elm[0].getAttribute("lay-menuid");
}

/**
 * 按钮遍历
 * @returns
 */
function _init_buttons_common(){
	$.ajax({
		url: "/button/info/" + tabMenuId()+"/" + authUser.roleId,
		type : "GET",
	　　		dataType : "json",
	　　		cache:true, 
	        async:false, 
	　　		contentType: "application/json;charset=utf-8",
		    headers: { "Authorization": authorization},//通过请求头来发送token，放弃了通过cookie的发送方式
		    success:function(data){
		    	var _toolbar_demo_html = '<div class="layui-btn-container">';
		    	var _current_table_bar = "";
		    	data.data.data.forEach(function(item){
		    	   if(item.type == 1){
		    		   _toolbar_demo_html += '<div class="layui-inline" id="'+item.event+'" lay-event="'+item.event+'">  <i class="iconfont"  data-toggle="tooltip" title="'+item.title+'" >'+item.icon+'</i></div>';
		    	   }else if(item.type == 2){
		    		   _current_table_bar += '<a class="iconfont" style="font-size: 24px; color: #1E9FFF;cursor: pointer;" lay-event="'+item.event+'" title="'+item.title+'">'+item.icon+'</a>';
		    	   }
		    	});
		    	$("#toolbarDemo").html(_toolbar_demo_html + '</div>');
		    	$("#currentTableBar").html(_current_table_bar);
		    }
	  });
}
