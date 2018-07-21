// 客户端请求menu.html后，menu.html一旦加载完毕就会调用menu.js的function方法
$(function(){
    //阅读后面会发现，bootstrap-table调用了option函数，只要执行$(function()函数，bootstrap-table就会按下面的url发送AJAX请求
    var option = {
        //cotrolle入口
        url: '/api/sys/selectAllSysMenuCustom',

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

        //bootstrap-table会自动生成下面参数所有的title表头，并把前面对应的数据加载到对应列下
        columns: [
            {checkbox: true},
            {field: 'menuId',
                title: '序号',
                width: 40,
                formatter: function(value, row, index) {
                    var option=$('#table').bootstrapTable('getOptions')
                    var pageNumber = option.pageNumber;
                    var pageSize = option.pageSize;
                    return pageSize * (pageNumber - 1) + index + 1;
                }
            },
            { title: '菜单ID', field: 'id'},
            {field:'name', title:'菜单名称', sortable: true ,  formatter: function(value,row){
                if(row.type === 0){
                    return value;
                }
                //├─就是一个符号，不用多想
                if(row.type === 1){
                    return '<span style="margin-left: 40px;">├─ ' + value + '</span>';
                }
                if(row.type === 2){
                    return '<span style="margin-left: 80px;">├─ ' + value + '</span>';
                }
            }},
            { title: '上级菜单', field: 'parentName'},
            // sortable设置为true，bootstrap会发送sort(字段名)和order(排序方式)请求
            { title: '菜单图标', field: 'icon', formatter: function(value){
                //判断如果该行icon是空，这个地方就不会显示图标，否则就调用插件显示图标
                //<span class="fa fa-lock"></span>显示一把锁
                // icon的value值就是一个个类似于fa fa-lock的图标名称符号
                if(value==null||value==''){
                    return '';
                }else{
                    return '<span class="'+value+'"></span>>'
                }
            }},
            { title: '菜单URL', field: 'url'},
            { title: '授权标识', field: 'perms'},
            //<span class="label label-primary"></span>是bootstrap已经设计好的不同的小按钮
            { title: '类型', field: 'type', formatter: function(value){
                if(value === 0){
                    return '<span class="label label-primary">目录</span>';
                }
                if(value === 1){
                    return '<span class="label label-success">菜单</span>';
                }
                if(value === 2){
                    return '<span class="label label-warning">按钮</span>';
                }
            }},
            { title: '排序号', field: 'orderNum'}
        ]};

    $('#table').bootstrapTable(option);
});

var vm = new Vue({
    el:'#app',
    data:{
        showList: true,
        title: '',
        menu:{}
    },
    methods: {
        del: function () {
            var rows = getSelectionRows();
            if (rows == undefined) {
                return;
            }

            //index返回当前索引层，即当前执行行数；layero返回的是当前dom层
            layer.confirm('Are you sure that you will delete this recoding?', {
                btn: ['Yes', 'No'] //可以无限个按钮
            }, function (index, layero) {
                var ids = new Array();
                $.each(rows, function (i, row) {
                    ids[i] = row['id'];
                });
                $.ajax({
                    type: 'post',
                    url: '/api/sys/deleteBatchByIdSysMenu',
                    data: JSON.stringify(ids),
                    success: function (r) {
                        if (r.code == 0) {
                            layer.alert("删除成功");
                            $('#table').bootstrapTable('refresh');
                        } else {
                            layer.alert(r.msg);
                        }
                    },
                    error: function () {
                        layer.alert('服务器未能返回数据，可能正忙请稍后重试')
                    }
                });
            });
        },
        cre: function () {
            vm.showList = false;
            vm.title = '新增';
            vm.menu = {type: 1, orderNum: 0, parentId: 0, parentName: null};

            vm.getMenu();

        },
        upd: function () {
            var row = getSelectionRow();
            if(row == undefined){
                return;
            }

            var id = row['id'];

            // 不要漏了info后面的斜杠
            $.get('/api/sys/info/' + id, function(r){

                if(r.code == 0){
                    vm.showList = false;
                    vm.title = '修改';
                    vm.menu = r.sysMenu;

                    vm.getMenu();
                }else{
                    layer.alert(r.msg);
                }
            });
        },
        saveOrUpdate: function () {
            var url = vm.menu.id == null ?'/api/sys/saveSysMenu':'/api/sys/updateSysMenu';
            $.ajax({
                url: url,
                type: 'post',
                data: JSON.stringify(vm.menu),
                success: function (r) {

                    if(r.code == 0){
                        layer.alert(r.msg, function(index){
                            vm.reload();
                            layer.close(index);
                        });
                    }else{
                        layer.alert(r.msg);
                    }
                }

            });
        },
        menuTree: function () {
            layer.open({
                //层类型
                type: 1,
                //坐标
                offset: '50px',
                //皮肤
                skin: 'layui-layer-molv',
                //标题
                title: '请选择菜单',
                //宽高
                area: ['300px', '450px'],
                //遮罩（选框外区域变暗）
                shade: 0,
                //遮罩关闭（遮罩区，即弹框外区域单击可以关闭弹框）
                shadeClose: false,
                //内容
                content: $('#menuLayer'),
                btn: ['确定', '取消'],
                btn1: function(index){
                    //TODO 选择父菜单，并回填至文本框
                    console.log('确定');

                    var treeObj = $.fn.zTree.getZTreeObj("menuTree");
                    var nodes = treeObj.getSelectedNodes();
                    if(nodes.length == 0){
                        layer.alert('请选择一个选项');
                    }else{
                        //实际上应该支持多选的，但这个地方我们没有设置多选的功能，所以只能取nodes[0].*
                        vm.menu.parentId = nodes[0].id;
                        vm.menu.parentName = nodes[0].name;
                        layer.close(index);
                    }

                }/*,
                 btn2: function(){
                 console.log('取消');
                 }*/
            });
        },

        reload: function () {
            vm.showList = true;
            //刷新表格
            $('#table').bootstrapTable('refresh');
        },

        getMenu: function () {
            var setting = {
                data: {
                    simpleData: {
                        enable: true,
                        idKey: 'id',
                        pIdKey: 'parentId',
                        rootPId: -1
                    },
                    //key:url,当点击菜单不需要跳转时可以随便设置一没用的默认值
                    key:{
                        url: 'nourl'
                    }
                }
            };

            /*静态数据返回到弹层*/
            // //https://gitee.com/zTree/zTree_v3/blob/master/demo/cn/core/simpleData.html
            // var zNodes =[
            //     { id:1, pId:0, name:"父节点1 - 展开", open:true},
            //     { id:11, pId:1, name:"父节点11 - 折叠"},
            //     { id:111, pId:11, name:"叶子节点111"},
            //     { id:112, pId:11, name:"叶子节点112"},
            //     { id:113, pId:11, name:"叶子节点113"},
            //     { id:114, pId:11, name:"叶子节点114"},
            //     { id:12, pId:1, name:"父节点12 - 折叠"},
            //     { id:121, pId:12, name:"叶子节点121"},
            //     { id:122, pId:12, name:"叶子节点122"},
            //     { id:123, pId:12, name:"叶子节点123"},
            //     { id:124, pId:12, name:"叶子节点124"},
            //     { id:13, pId:1, name:"父节点13 - 没有子节点", isParent:true},
            //     { id:2, pId:0, name:"父节点2 - 折叠"},
            //     { id:21, pId:2, name:"父节点21 - 展开", open:true},
            //     { id:211, pId:21, name:"叶子节点211"},
            //     { id:212, pId:21, name:"叶子节点212"},
            //     { id:213, pId:21, name:"叶子节点213"},
            //     { id:214, pId:21, name:"叶子节点214"},
            //     { id:22, pId:2, name:"父节点22 - 折叠"},
            //     { id:221, pId:22, name:"叶子节点221"},
            //     { id:222, pId:22, name:"叶子节点222"},
            //     { id:223, pId:22, name:"叶子节点223"},
            //     { id:224, pId:22, name:"叶子节点224"},
            //     { id:23, pId:2, name:"父节点23 - 折叠"},
            //     { id:231, pId:23, name:"叶子节点231"},
            //     { id:232, pId:23, name:"叶子节点232"},
            //     { id:233, pId:23, name:"叶子节点233"},
            //     { id:234, pId:23, name:"叶子节点234"},
            //     { id:3, pId:0, name:"父节点3 - 没有子节点", isParent:true}
            // ];

            // $.fn.zTree.init($('#menuTree'), setting, zNodes);

            // /*动态后台获取数据*/
            // $.get('/api/sys/selectNoButtonCustom', function(r){
            //     var zNodes = r.menuList;
            //     //将数据zNodes以setting的设置方式显示在menuTree节点上
            //     $.fn.zTree.init($('#menuTree'), setting, zNodes);
            // })
            $.get('/api/sys/selectNoButtonCustom', function(r){

                var zNodes = r.menuList;
                var treeObj = $.fn.zTree.init($('#menuTree'), setting, zNodes);

                var parentId = vm.menu.parentId;
                //根据id属性获取父节点json数据对象
                var parentNode = treeObj.getNodeByParam('id', parentId);
                //选中指定节点
                treeObj.selectNode(parentNode);
                console.log(parentNode);

                vm.menu.parentName = parentNode.name;
            })
        }
    }

});