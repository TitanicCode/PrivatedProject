package api.service.sys;

import api.dto.DataMapPackage;
import api.dto.PaginationRTDto;
import api.dto.SysMenuCustomDto;
import api.pojo.sys.SysMenu;
import api.pojo.sys.SysMenuExample;

import java.util.List;
import java.util.Set;

/**
 * Created by Administrator on 2018/6/25.
 */
public interface SysMenuService {

    /***********************保存添加**********************/
    int createSysMenu(SysMenu sysMenu);

    /***********************查询**********************/
    //分页模糊查询SysMenuCustom
    PaginationRTDto selectAllSysMenuCustom(DataMapPackage offsetLimitDMP);
    //去按钮菜单查询
    List<SysMenuCustomDto>  selectNoButtonCustomer();
    //使用逆向工程查询
    SysMenu selectInfoSysmenu(Long id);
    //查询perms
    Set<String> selectPermsByUserId(Long userId);
    //查询当前用户显示的菜单
    List<SysMenuCustomDto> selectNowUserMenu(Long userId);

    /***********************删除**********************/
    //批量删除SysMenu
    int deleteBatchByIdSysMenu(List<Long> ids);

    int updateObject(SysMenu sysMenu);
}
