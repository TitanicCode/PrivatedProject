package api.realm;

import api.pojo.sys.SysUser;
import api.service.sys.SysMenuService;
import api.service.sys.SysUserService;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


/**
 * Created by helen on 2018/6/28
 */
public class UserRealm extends AuthorizingRealm {


    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private SysMenuService sysMenuService;

    /**
     * 认证
     * @param token
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {

        //获取用户输入
        String usernameInput = (String)token.getPrincipal();
        String passwordInput = new String((char[])token.getCredentials());

        //判断用户名是否存在
        SysUser sysUser = sysUserService.queryUserByUsername(usernameInput);
        if(sysUser == null){
            throw new UnknownAccountException("用户账号不存在");
        }

        //判断用户密码是否正确
        String password = sysUser.getPassword();
        if(!password.equals(passwordInput)){
            throw new IncorrectCredentialsException("用户密码不正确");
        }

        //判断用户账号的状态
        if(sysUser.getStatus() == 0){
            throw new LockedAccountException("账号已被锁定");
        }


        SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(sysUser, password, getName());
        return info;
    }

    /**
     * 授权
     * @param principals
     * @return
     */
    //认证中最后info存的第一个是什么参数，principals接受的就会是什么参数
    //比如本程序认证中info存的第一个参数是sysUser对象，所以授权中principals接受的也将是sysUser对象
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {

        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        SysUser sysUser = (SysUser) principals.getPrimaryPrincipal();
        Long userId = sysUser.getId();

        /**************资源授权**********************/
        Set<String> permString=sysMenuService.selectPermsByUserId(userId);
        //用setStringPermissions是为了正好传入一个set<String>集合
        System.out.println(permString);
        info.setStringPermissions(permString);

        //角色授权
        List<String> roles = Arrays.asList("admin", "users");
        info.addRoles(roles);

        return info;
    }

}
