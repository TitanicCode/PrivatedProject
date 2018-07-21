package api.service.sys;

import api.dto.DataMapPackage;
import api.dto.PaginationRTDto;

/**
 * Created by Administrator on 2018/7/2.
 */
public interface SysConfigService {

//    /***********************添加**********************/
////    int save
//
//    /***********************查询**********************/
//    //分页模糊查询SysMenuCustom
//    PaginationRTDto selectAllSysMenuCustom(DataMapPackage offsetLimitDMP);
//    //去按钮菜单查询
//    List<SysMenuCustomDto>  selectNoButtonCustomer();
//    /***********************删除**********************/
//    //批量删除SysMenu
//    int deleteBatchByIdSysMenu(List<Long> ids);
    /***********************查询**********************/
    //分页模糊查询SysConfigCustom
    PaginationRTDto selectAllSysConfigCustom(DataMapPackage bootstrapRequestData);
}
