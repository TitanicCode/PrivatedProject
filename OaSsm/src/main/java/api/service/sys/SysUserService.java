package api.service.sys;

import api.dto.DataMapPackage;
import api.dto.PaginationRTDto;
import api.dto.SysMenuCustomDto;
import api.pojo.sys.SysMenu;
import api.pojo.sys.SysUser;

import java.util.List;

/**
 * Created by helen on 2018/6/23
 */
public interface SysUserService {


    PaginationRTDto queryPageList(DataMapPackage query);


    int deleteBatch(List<Long> ids);

    List<SysMenuCustomDto> queryNoButtonList();

    int save(SysMenu sysMenu);

    SysMenu queryObject(Long id);

    int updateObject(SysMenu sysMenu);

    SysUser queryUserByUsername(String username);
}
