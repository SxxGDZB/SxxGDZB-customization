var dataRow,table;
layui.use(['form', 'table'], function () {
    var $ = layui.jquery,
        form = layui.form;
        table = layui.table;
    var currentPage = 1;
    /*请求参数*/
    var jsonEntity = {};
    jsonEntity.roleId = authUser.roleId;
    jsonEntity.roleId = authUser.roleId;
    loadTickets(jsonEntity);
    
    /*加载表格*/
    function loadTickets(jsonEntity){
    	table.render({
            elem: '#currentTableId',
            url: getRootPath() + '/resourcerole/list',
            headers: { "Authorization": authorization},//通过请求头来发送token，放弃了通过cookie的发送方式
            where : jsonEntity,
            async : false,
            toolbar: '#toolbarDemo',
            defaultToolbar: ['filter', 'exports', 'print', {
                title: '提示',
                layEvent: 'LAYTABLE_TIPS',
                icon: 'layui-icon-tips'
            }],
            cols: [[
                {type: "checkbox"},
                {field: 'id',title: 'ID', sort: true},
                {field: 'resCode',title: '资源CODE', sort: true},
                {field: 'resName',title: '资源名称'},
                {field: 'roleCode',title: '角色CODE', sort: true},
                {field: 'roleName', title: '角色名称'}
            ]],
            limits: [5, 10, 20, 25, 50, 100],
            limit: 10,
            page: true,
            skin: 'line',
            done : function(res, curr, count) {
				currentPage = curr;
			}
        });
    }
    initSelectRole();
    // 监听搜索操作
    form.on('submit(data-search-btn)', function (data) {
        var result = JSON.stringify(data.field);
        var jsonEntity = {};
        $.each(data.field, function(key, val) {
        	if(data.field[key] != null && data.field[key].length > 0){
        		jsonEntity[key] = val;
        	}
       });
        loadTickets(jsonEntity);
        return false;
    });
    //刷新表格
	function flush(currentPage, jsonEntity) {
		table.reload('currentTableId', {
			page : {
				curr : currentPage
			},
			where : jsonEntity
		});
	}
    /**
     * toolbar监听事件
     */
    table.on('toolbar(currentTableFilter)', function (obj) {
        if (obj.event === 'add') {  // 监听添加操作
        	openPopUp('添加','/admin/role/add');
        } else if (obj.event === 'delete') {  // 监听删除操作
            var checkStatus = table.checkStatus('currentTableId')
                , data = checkStatus.data;
            if(data.length != 0){
            	var ids = [];
            	$.each(data,function(index,xx){
            		ids.push(xx.id);
            	})
            	$.ajax({
         			url: "/resourcerole/"+ids.join(','),
         			type : "DELETE",
        	　　		dataType : "json",
        	　　		contentType: "application/json;charset=utf-8",
    			    headers: { "Authorization": authorization },//通过请求头来发送token，放弃了通过cookie的发送方式
    			    success:function(data){
    			    	 	if(data.success){
    			    	 		flush(currentPage, jsonEntity);
    			    	 		layer.msg(data.msg, {
      		    	 			  icon: 6,
      		    	 			  time: 2000 //2秒关闭（如果不配置，默认是3秒）
      		    	 			}); 
    						}else{
    							layer.msg(data.msg,{icon: 5, time: 2000});
    					}
    			    }
    			 });
            }
        }else if(obj.event === 'upd'){
        	openPopUp('修改','/admin/role/edit');
        }
    });
});