// 客户端请求menu.html后，menu.html一旦加载完毕就会调用menu.js的function方法
$(function(){
    //阅读后面会发现，bootstrap-table调用了option函数，只要执行$(function()函数，bootstrap-table就会按下面的url发送AJAX请求
    var option = {
        //cotrolle入口
        url: '/api/sys/selectAllSysLogCustom',

        datatype: "json",

        //配置搜索
        search: true,

        //分页,属于前端分页，通过javascript分页
        //优点：方便简洁，不需要后台修改代码，调取请求等
        //缺点：数据大的话会很慢，且即时性慢，不会及时显示数据库修改后的分页
        //综上：数据量大，时效性要求高的分页必须使用服务器端分页而不是用该前端客户端分页
        //当选择服务器端显示分页时，Pagination: true作用就变成了显示分页条
        pagination: true,
        sidePagination: 'server',

        //显示刷新按钮
        showRefresh: true,

        //设置为true会有隔行变色效果
        striped : true,

        //标记数据主键id
        idField: 'id',

        //将一些id设定为菜单栏，可以放到一行中
        toolbar: '#CRUDButton',

        viewrecords: true,
        height: 385,
        rowNum: 10,
        rowList : [10,30,50],
        rownumbers: true,
        rownumWidth: 25,
        autowidth:true,
        pager: "#jqGridPager",
        jsonReader : {
            root: "page.list",
            page: "page.currPage",
            total: "page.totalPage",
            records: "page.totalCount"
        },
        prmNames : {
            page:"page",
            rows:"limit",
            order: "order"
        },
        gridComplete:function(){
            //隐藏grid底部滚动条
            $("#jqGrid").closest(".ui-jqgrid-bdiv").css({ "overflow-x" : "hidden" });
        },
        //bootstrap-table会自动生成下面参数所有的title表头，并把前面对应的数据加载到对应列下
        columns: [
            {checkbox: true},
            {field: 'menuId',
                title: '序号',
                width: 40,
                formatter: function(value, row, index) {
                    var option=$('#jqGrid').bootstrapTable('getOptions')
                    var pageNumber = option.pageNumber;
                    var pageSize = option.pageSize;
                    return pageSize * (pageNumber - 1) + index + 1;
                }
            },
            { title: 'id', field: 'id', width: 30},
            { title: '用户名', field: 'username', width: 50},
            { title: '用户操作', field: 'operation', width: 70},
            { title: '请求方法', field: 'method', width: 150},
            { title: '请求参数', field: 'params', width: 80},
            { title: 'IP地址', field: 'ip', width: 70},
            { title: '创建时间', field: 'createDate', width: 90, sortable: true},
        ]};

    $('#jqGrid').jqGrid(option);
});

var vm = new Vue({
    el:'#rrapp',
    data:{
        q:{
            key: null
        },
    },
    methods: {
        query: function () {
            vm.reload();
        },
        reload: function (event) {
            var page = $("#jqGrid").jqGrid('getGridParam','page');
            $("#jqGrid").jqGrid('setGridParam',{
                postData:{'key': vm.q.key},
                page:page
            }).trigger("reloadGrid");
        }
    }

});