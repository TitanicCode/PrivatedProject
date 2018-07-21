package api.controller;

import api.dto.R;
import api.pojo.sys.SysUser;
import api.service.sys.SysMenuService;
import api.utils.ShiroUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Set;

/**
 * Created by Administrator on 2018/7/7.
 */
@Controller
@RequestMapping("/api")
public class SysUserController {
    @Autowired
    private SysMenuService sysMenuService;

    /****************用户欢迎信息********************/
    @ResponseBody
    @RequestMapping("/sys/selectNowUserInfo")
    public R selectNowUserInfo(){
        SysUser nowUserInfo = ShiroUtils.getUserEntity();
        System.out.println(nowUserInfo);
        return R.ok().put("user",nowUserInfo);
    }

}
