package api.dao.sys;

import api.dto.DataMapPackage;
import api.pojo.sys.SysConfig;

import java.util.List;
import java.util.Set;

/**
 * Created by Administrator on 2018/7/2.
 */
public interface SysConfigCustomMapper {

//
//    /************************删除*************************/
//    int deleteBatchByIdSysMenu(List<Long> ids);
     /************************查询*************************/
     List<SysConfig> selectAllSysConfigCustom(DataMapPackage bootstrapRequestData);



     /************************统计*************************/
     Long countBySelectAllSysConfigCustom(DataMapPackage bootstrapRequestData);
}
