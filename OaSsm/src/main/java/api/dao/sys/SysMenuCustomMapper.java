package api.dao.sys;

import api.dto.DataMapPackage;
import api.dto.SysMenuCustomDto;

import java.util.List;
import java.util.Set;

/**
 * Created by Administrator on 2018/6/26.
 */
public interface SysMenuCustomMapper {
    /************************查询*************************/
    List<SysMenuCustomDto>  selectAllSysMenuCustom(DataMapPackage bootstrapRequestData);
    List<SysMenuCustomDto>  selectNoButtonCustomer();
    Set<String> selectPermsByUserId(Long UserId);
    Set<String> selectAllPermsByUserId(Long userId);
    //查询当前用户对应的能触及到的菜单列表的Id byUserId
    List<Long> selectNowUserMenuIdByUserId(Long userId);
    //查询当前级别的所有菜单列表byParentId
    List<SysMenuCustomDto> selectMenuByParentId(Long parentId);

    /************************统计*************************/
    Long countBySelectAllSysMenuCustom(DataMapPackage bootstrapRequestData);

    /************************删除*************************/
    int deleteBatchByIdSysMenu(List<Long> ids);
}
