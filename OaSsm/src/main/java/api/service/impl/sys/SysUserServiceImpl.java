package api.service.impl.sys;


import api.dao.sys.SysMenuCustomMapper;
import api.dao.sys.SysMenuMapper;
import api.dao.sys.SysUserCustomMapper;
import api.dao.sys.SysUserMapper;
import api.dto.DataMapPackage;
import api.dto.PaginationRTDto;
import api.dto.SysMenuCustomDto;
import api.pojo.sys.SysMenu;
import api.pojo.sys.SysUser;
import api.pojo.sys.SysUserExample;
import api.service.sys.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by helen on 2018/6/23
 */
@Service
public class SysUserServiceImpl implements SysUserService {

    @Autowired
    private SysUserCustomMapper sysUserCustomMapper;


    @Autowired
    private SysUserMapper sysUserMapper;

    @Autowired
    private SysMenuCustomMapper sysMenuCustomMapper;

    @Autowired
    private SysMenuMapper sysMenuMapper;


    @Override
    public PaginationRTDto queryPageList(DataMapPackage query) {

        //数据列表
        List<SysUser> rows = sysUserCustomMapper.queryList(query);

        //总记录数
        long total = sysUserCustomMapper.count(query);

        //创建DataGridResult对象
        PaginationRTDto dataGridResult = new PaginationRTDto(total,rows);

        return dataGridResult;
    }

    @Override
    public int deleteBatch(List<Long> ids) {
        return sysMenuCustomMapper.deleteBatchByIdSysMenu(ids);
    }

    @Override
    public List<SysMenuCustomDto> queryNoButtonList() {
        return sysMenuCustomMapper.selectNoButtonCustomer();
    }

    @Override
    public int save(SysMenu sysMenu) {
        return sysMenuMapper.insertSelective(sysMenu);
    }

    @Override
    public SysMenu queryObject(Long id) {
        return sysMenuMapper.selectByPrimaryKey(id);
    }

    @Override
    public int updateObject(SysMenu sysMenu) {

        return sysMenuMapper.updateByPrimaryKeySelective(sysMenu);
    }



    @Override
    public SysUser queryUserByUsername(String username) {

        SysUserExample example = new SysUserExample();
        SysUserExample.Criteria criteria = example.createCriteria();
        criteria.andUsernameEqualTo(username);

        List<SysUser> sysUsers = sysUserMapper.selectByExample(example);
        if(sysUsers != null && sysUsers.size() > 0){
            return sysUsers.get(0);
        }else{
            return null;
        }
    }

}
