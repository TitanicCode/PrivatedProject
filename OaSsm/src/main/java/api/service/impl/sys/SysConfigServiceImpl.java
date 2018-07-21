package api.service.impl.sys;

import api.dao.sys.SysConfigCustomMapper;
import api.dto.DataMapPackage;
import api.dto.PaginationRTDto;
import api.pojo.sys.SysConfig;
import api.service.sys.SysConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Administrator on 2018/7/2.
 */
@Service
public class SysConfigServiceImpl implements SysConfigService {

    @Autowired
    private SysConfigCustomMapper sysConfigCustomMapper;

    /***********************查询**********************/
    //分页模糊查询SysConfigCustom列表
    @Override
    public PaginationRTDto selectAllSysConfigCustom(DataMapPackage SysConfigBootstrapRequestData) {
        List<SysConfig> sysConfigs = sysConfigCustomMapper.selectAllSysConfigCustom(SysConfigBootstrapRequestData);
        long total = sysConfigCustomMapper.countBySelectAllSysConfigCustom(SysConfigBootstrapRequestData);
        PaginationRTDto paginationRTDto=new PaginationRTDto(total,sysConfigs);
        return paginationRTDto;
    }
//    @Autowired
//    private SysMenuCustomMapper sysMenuCustomMapper;
//
//
//    SysMenuExample sysMenuExample=new SysMenuExample();
//
//    /***********************查询**********************/
//    //分页模糊查询SysMenuCustom列表
//    @Override
//    public PaginationRTDto selectAllSysMenuCustom(DataMapPackage bootstrapRequestData) {
//
//        List<SysMenuCustomDto> sysMenuCustomDto = sysMenuCustomMapper.selectAllSysMenuCustom(bootstrapRequestData);
//        long total = sysMenuCustomMapper.countBySelectAllSysMenuCustom(bootstrapRequestData);
//        PaginationRTDto paginationRTDto=new PaginationRTDto(total,sysMenuCustomDto);
//        return paginationRTDto;
//    }
//    //菜单去按钮查询SysMenu
//    @Override
//    public List<SysMenuCustomDto> selectNoButtonCustomer() {
//        List<SysMenuCustomDto> sysMenuCustomDtos = sysMenuCustomMapper.selectNoButtonCustomer();
//        return sysMenuCustomDtos;
//    }
//
//
//    /***********************删除**********************/
//    //批量删除SysMenu列表
//    @Override
//    public int deleteBatchByIdSysMenu(List<Long> ids) {
//        for (Long id: ids) {
//            if(id.longValue()<=31){
//                return -1;
//            }
//        }
//        int i = sysMenuCustomMapper.deleteBatchByIdSysMenu(ids);
//        return i;
//    }

}
