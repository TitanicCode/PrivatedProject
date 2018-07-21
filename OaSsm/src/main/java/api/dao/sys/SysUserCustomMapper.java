package api.dao.sys;



import api.dto.DataMapPackage;
import api.pojo.sys.SysUser;

import java.util.List;

/**
 * Created by helen on 2018/6/28
 */
public interface SysUserCustomMapper {

    List<SysUser> queryList(DataMapPackage query);

    Long count(DataMapPackage query);
}
