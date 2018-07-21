package api.controller;

import api.dto.DataMapPackage;
import api.dto.PaginationRTDto;
import api.dto.R;
import api.dto.SysMenuCustomDto;
import api.pojo.sys.SysMenu;
import api.service.sys.SysMenuService;
import api.utils.ShiroUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by Administrator on 2018/6/25.
 */
@RequestMapping("/api")
@Controller
public class SysMenuController {
    @Autowired
    private SysMenuService sysMenuService;

    /***********************查询**********************/
    //分页模糊查询SysMenuCustom
    @ResponseBody
    @RequestMapping("/sys/selectAllSysMenuCustom")
    public PaginationRTDto selectAllSysMenuCustom(@RequestParam Map<String,Object> bootstrapRequestData){
        System.out.println(bootstrapRequestData);

        DataMapPackage offsetLimitDMP=new DataMapPackage(bootstrapRequestData);
        return sysMenuService.selectAllSysMenuCustom(offsetLimitDMP);
    }
    @ResponseBody
    @GetMapping("/sys/selectNoButtonCustom")
    public R selectNoButtonCustom(){
        List<SysMenuCustomDto> sysMenuCustomDtos=sysMenuService.selectNoButtonCustomer();

        SysMenuCustomDto root=new SysMenuCustomDto();
        root.setName("一级菜单");
        root.setOpen(true);
        root.setId(0L);
        root.setParentId(-1L);

        sysMenuCustomDtos.add(root);

        return R.ok().put("menuList",sysMenuCustomDtos);
    }

    /****************用户导航菜单显示********************/
    @RequestMapping("/sys/nowUserMenu")
    @ResponseBody
    public R nowUserMenu(){

        Long userId = ShiroUtils.getUserId();

        //获取permissions
        Set<String> nowUserPerms = sysMenuService.selectPermsByUserId(userId);

        //获取menuList
        List<SysMenuCustomDto> menuList = sysMenuService.selectNowUserMenu(userId);

        return R.ok().put("permissions",nowUserPerms).put("menuList",menuList);
    }

    /***********************删除**********************/
    //根据id批量删除SysMenu
    @ResponseBody
    @PostMapping("/sys/deleteBatchByIdSysMenu")
//    //注解配置shiro授权
//    @RequiresPermissions({"sys:menu:save"})
//    @RequiresRoles({"user"})
    public R deleteBatchByIdSysMenu(@RequestBody List<Long> ids){
        int i = sysMenuService.deleteBatchByIdSysMenu(ids);
        if (i==-1) {
            return R.error(500,"系统菜单不得删除");
        }
        return R.ok("删除成功").put("i",i);
    }

    /***********************保存**********************/
    //保存SysMenu
    @ResponseBody
    @RequestMapping("/sys/saveSysMenu")
    public R saveSysMenu(@RequestBody SysMenu sysMenu ){
        int i = sysMenuService.createSysMenu(sysMenu);
        return R.ok("保存成功").put("count",i);
    }

    /***********************其他**********************/
    @ResponseBody
    @GetMapping("/sys/info/{id}")
    //@RequiresPermissions({"sys:menu:info"})
    public R info(@PathVariable("id") Long id){
        SysMenu sysMenu = sysMenuService.selectInfoSysmenu(id);
        System.out.println(sysMenu);
        return R.ok("获取数据成功").put("sysMenu", sysMenu);
    }

    /***********************更新**********************/
    @ResponseBody
    @PostMapping("/sys/updateSysMenu")
    //@RequiresPermissions({"sys:menu:update"})
    public R update(@RequestBody SysMenu sysMenu){

        int count = sysMenuService.updateObject(sysMenu);

        return R.ok("更新成功").put("count", count);
    }
}
