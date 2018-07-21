//ajax全局配置
$.ajaxSetup({
    dataType: "json",
    contentType: "application/json",
    cache: false
});
function getSelectionRows(){
    var rows=$('#table').bootstrapTable('getSelections');
    if(rows.length==0){
        layer.alert("请选择一条记录");
        return;
    }
    return rows;
}
function getSelectionRow() {
    var rows = $('#table').bootstrapTable('getSelections');
    if (rows.length == 0) {
        layer.alert("请选择一条记录");
        return;
    }

    if (rows.length > 1) {
        layer.alert("只能选择一条记录");
        return;
    }

    return rows[0];
}

// 工具Tools
// window.T = {};
//
// //bootstrap-table列配置
// T.bootstrapTableOption = {
//     pagination: true,    //显示分页条
//     sidePagination: 'server', //服务端分页
//     showRefresh: true,  //显示刷新按钮
//     search: true,
//     toolbar: '#toolbar',
//     striped : true,     //设置为true会有隔行变色效果
//     idField: 'menuId',
//     columns: [
//         {
//             field: 'menuId',
//             title: '序号',
//             width: 40,
//             formatter: function(value, row, index) {
//                 var pageSize = $('#table').bootstrapTable('getOptions').pageSize;
//                 var pageNumber = $('#table').bootstrapTable('getOptions').pageNumber;
//                 return pageSize * (pageNumber - 1) + index + 1;
//             }
//         },
//         {checkbox:true}
//     ]};
//
// //ajax全局配置
// $.ajaxSetup({
//     dataType: "json",
//     contentType: "application/json",
//     cache: false
// });
//


//
//
// function hasPermission(permission){
//
//     console.log(permission);
//     console.log(window.parent.permissions);
//     console.log(window.parent.permissions.indexOf(permission) > -1);
//     return (window.parent.permissions.indexOf(permission) > -1)
// }
