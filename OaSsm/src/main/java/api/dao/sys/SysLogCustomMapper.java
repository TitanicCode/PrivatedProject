package api.dao.sys;

import api.dto.DataMapPackage;
import api.pojo.sys.SysLog;

import java.util.List;

/**
 * Created by Administrator on 2018/7/2.
 */
public interface SysLogCustomMapper {
    /************************查询*************************/
    List<SysLog> selectAllSysLogCustom(DataMapPackage bootstrapRequestData);

    /************************统计*************************/
    Long countBySelectAllSysLogCustom(DataMapPackage bootstrapRequestData);
}
