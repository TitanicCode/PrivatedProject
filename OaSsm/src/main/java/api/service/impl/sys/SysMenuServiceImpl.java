package api.service.impl.sys;

import api.dao.sys.SysMenuCustomMapper;
import api.dao.sys.SysMenuMapper;
import api.dto.DataMapPackage;
import api.dto.PaginationRTDto;
import api.dto.SysMenuCustomDto;
import api.pojo.sys.SysMenu;
import api.pojo.sys.SysMenuExample;
import api.service.sys.SysMenuService;
import api.utils.Constant;
import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Created by Administrator on 2018/6/25.
 */
@Service
public class SysMenuServiceImpl implements SysMenuService {

    @Autowired
    private SysMenuCustomMapper sysMenuCustomMapper;
    @Autowired
    private SysMenuMapper sysMenuMapper;


    /***********************查询**********************/
    //分页模糊查询SysMenuCustom列表
    @Override
    public PaginationRTDto selectAllSysMenuCustom(DataMapPackage bootstrapRequestData) {

        List<SysMenuCustomDto> sysMenuCustomDto = sysMenuCustomMapper.selectAllSysMenuCustom(bootstrapRequestData);
        long total = sysMenuCustomMapper.countBySelectAllSysMenuCustom(bootstrapRequestData);
        PaginationRTDto paginationRTDto=new PaginationRTDto(total,sysMenuCustomDto);
        return paginationRTDto;
    }
    //菜单去按钮查询SysMenu
    @Override
    public List<SysMenuCustomDto> selectNoButtonCustomer() {
        List<SysMenuCustomDto> sysMenuCustomDtos = sysMenuCustomMapper.selectNoButtonCustomer();
        return sysMenuCustomDtos;
    }

    @Override
    public SysMenu selectInfoSysmenu(Long id) {
        SysMenu sysMenu = sysMenuMapper.selectByPrimaryKey(id);
        return sysMenu;
    }

    //查询perms
    @Override
    public Set<String> selectPermsByUserId(Long userId) {
        Set<String> perms=new HashSet<String >();
        Set<String> perms1=new HashSet<>();
//        if (userId== Constant.SUPER_ADMIN){
//            //为超级管理员授权所有权限
//            perms1 = sysMenuCustomMapper.selectAllPermsByUserId(userId);
//        }else{
            perms1 = sysMenuCustomMapper.selectPermsByUserId(userId);
//        }

        //dao层查询出来的perms会包含空字段，有的UserId对应的perms字段是空的，要把空字段去掉，否则传到授权函数的时候会报错
        for (String string : perms1) {
            if (StringUtils.isBlank(string)){
                continue;
            }
            String[] strings=string.trim().split(",");
            perms.addAll(Arrays.asList(strings));
        }

        System.out.println(perms);
        return perms;
    }

    //导航菜单的显示实现
    @Override
    public List<SysMenuCustomDto> selectNowUserMenu(Long userId) {

        List<Long> nowUserAllMenuId=new ArrayList<>();

        //查询当前用户对应的能触及到的菜单列表的Id byUserId
//        if(userId == Constant.SUPER_ADMIN){
//            //如果当前用户是超级管理员，则不做权限过滤
//            nowUserAllMenuId=null;
//        }else{
            nowUserAllMenuId = sysMenuCustomMapper.selectNowUserMenuIdByUserId(userId);
//        }

        System.out.println("测试当前用户所有列表"+nowUserAllMenuId);

        //查询当前级别的所有菜单列表byParentId
        List<SysMenuCustomDto> thisRankAllMenus = sysMenuCustomMapper.selectMenuByParentId(0L);
        System.out.println("测试父类Id菜单列表："+thisRankAllMenus);

        //如果是超级管理员，就会传递空值，认为拥有所有的权限，则返回所有的列表
        if(nowUserAllMenuId == null){
            for (SysMenuCustomDto thisRankAllMenu: thisRankAllMenus) {
                List<SysMenuCustomDto> subList=new ArrayList<>();
                List<SysMenuCustomDto> thisSonRankAllMenus = sysMenuCustomMapper.selectMenuByParentId(thisRankAllMenu.getId());
                for (SysMenuCustomDto thisSonRankAllMenu: thisSonRankAllMenus) {
                        subList.add(thisSonRankAllMenu);
                }
                thisRankAllMenu.setList(subList);
            }
            return thisRankAllMenus;
        }
        //否则进一步过滤
        List<SysMenuCustomDto> menuList= new ArrayList<>();
        //List<SysMenuCustomDto> subList=new ArrayList<>();
        //比较父类Id菜单列表和当前用户菜单列表，返回menuList集合中的一级菜单
        for (SysMenuCustomDto thisRankAllMenu: thisRankAllMenus) {
            List<SysMenuCustomDto> subList=new ArrayList<>();
            if(nowUserAllMenuId.contains(thisRankAllMenu.getId())){
                menuList.add(thisRankAllMenu);
                System.out.println("测试当前用户能看到的一级列表"+menuList);
            }
            //继续加载子菜单
            List<SysMenuCustomDto> thisSonRankAllMenus = sysMenuCustomMapper.selectMenuByParentId(thisRankAllMenu.getId());
            for (SysMenuCustomDto thisSonRankAllMenu: thisSonRankAllMenus) {
                if(nowUserAllMenuId.contains(thisSonRankAllMenu.getId())){
                    subList.add(thisSonRankAllMenu);
                    System.out.println("测试当前用户能看到的二级列表"+subList);
                }
            }
            thisRankAllMenu.setList(subList);
        }
        return menuList;
    }

    /***********************删除**********************/
    //批量删除SysMenu列表
    @Override
    public int deleteBatchByIdSysMenu(List<Long> ids) {
        for (Long id: ids) {
            if(id.longValue()<=31){
                return -1;
            }
        }
        int i = sysMenuCustomMapper.deleteBatchByIdSysMenu(ids);
        return i;
    }

    /***********************保存**********************/
    //保存SysMenu
    @Override
    public int createSysMenu(SysMenu sysMenu) {
        int i = sysMenuMapper.insertSelective(sysMenu);
        return i;
    }


        @Override
        public int updateObject(SysMenu sysMenu) {

            return sysMenuMapper.updateByPrimaryKeySelective(sysMenu);
        }


}
