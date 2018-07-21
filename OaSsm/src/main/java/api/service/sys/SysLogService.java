package api.service.sys;

import api.dto.DataMapPackage;
import api.dto.PaginationRTDto;

/**
 * Created by Administrator on 2018/7/2.
 */
public interface SysLogService {
    PaginationRTDto selectAllSysLogCustom(DataMapPackage bootstrapRequestData);
}
