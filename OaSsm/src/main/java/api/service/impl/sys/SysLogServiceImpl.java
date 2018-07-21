package api.service.impl.sys;

import api.dao.sys.SysLogCustomMapper;
import api.dto.DataMapPackage;
import api.dto.PaginationRTDto;
import api.pojo.sys.SysLog;
import api.service.sys.SysLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Administrator on 2018/7/2.
 */
@Service
public class SysLogServiceImpl implements SysLogService {

    @Autowired
    private SysLogCustomMapper sysLogCustomMapper;

    @Override
    public PaginationRTDto selectAllSysLogCustom(DataMapPackage bootstrapRequestData) {
        List<SysLog> sysLogs = sysLogCustomMapper.selectAllSysLogCustom(bootstrapRequestData);
        Long total=sysLogCustomMapper.countBySelectAllSysLogCustom(bootstrapRequestData);
        PaginationRTDto paginationRTDto=new PaginationRTDto(total,sysLogs);
        return paginationRTDto;
    }
}
