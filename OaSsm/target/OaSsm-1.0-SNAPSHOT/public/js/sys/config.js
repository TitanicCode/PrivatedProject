$(function(){

    $('#jqGrid').bootstrapTable({
        url: 'config/list',
        pagination: true,
        sidePagination: 'server',
        //search: true,
        //showRefresh: true,
        striped: true,
        toolbar: '#toolbar',
        columns: [
            {field: 'id', title: '序号', formatter: function(value, row, index){
                var options = $('#jqGrid').bootstrapTable('getOptions');
                //console.log(options);
                var pageSize = options.pageSize;
                var pageNumber = options.pageNumber;
                return pageSize * (pageNumber - 1) + index + 1;
            }},
            {checkbox: true},
            {field: 'id', title: 'ID'},
            {field: 'key', title: '参数名', sortable: true},
            {field: 'value', title: '参数值'},
            {field: 'remark', title: '备注'}
        ]
    });
});

var vm = new Vue({
    el:'#rrapp',
    data:{
        q:{
            key: null
        },
        showList: true,
        title: '',
        config:{}

    },
    methods: {
        query: function () {
            vm.reload();
        },

        del: function(){
            console.log("del");

            var rows = getSelectionRows();
            if(rows == undefined){
                return;
            }

            layer.confirm('您确定要删除所选的记录吗？', function(index){
                //do something

                var ids = new Array();
                $.each(rows, function(i, row){
                    ids[i] = row['id'];
                })

                $.ajax({
                    type: 'post',
                    url: 'config/del',
                    data: JSON.stringify(ids),
                    success: function (r) {


                        if(r.code == 0){
                            layer.alert(r.msg);
                            //刷新表格
                            $('#jqGrid').bootstrapTable('refresh');
                        }else{
                            layer.alert(r.msg);
                        }
                    },
                    error: function(){
                        layer.alert('服务器未能返回数据，可能正忙请稍后重试');
                    }
                });
            });
        },

        add: function(){
            console.log("add");
            vm.showList = false;
            vm.title = '新增';
            vm.config = {};
            // vm.config = {type:1, orderNum:0, parentId: 0, parentName: null};
            //
            // vm.getMenu();

        },

        update: function(){
            console.log("update");

            var row = getSelectionRow();
            if(row == undefined){
                return;
            }

            var id = row['id'];

            $.get('config/info/' + id, function(r){

                if(r.code == 0){
                    vm.showList = false;
                    vm.title = '修改';
                    vm.config = r.sysConfig;

                    //vm.getMenu();
                }else{
                    layer.alert(r.msg);
                }
            });

        },

        saveOrUpdate: function(){

            var url=vm.config.id==null?'config/save':'config/update';
            $.ajax({
                url:url,
                type:'post',
                data:JSON.stringify(vm.config),
                success:function (r) {
                    if (r.code==0){
                        layer.alert(r.msg,function (index) {
                            vm.reload();
                            layer.close(index);
                        });
                    }
                }

            });

        },

        reload: function(){
            vm.showList = true;
            //刷新表格
            $('#jqGrid').bootstrapTable('refresh');
        }

    }
});