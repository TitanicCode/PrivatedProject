package com.shenrun.p2pproject.user.service.impl;

import com.common.constantcode.ConstantCodeUtil;
import com.common.dto.ResultData;
import com.common.redis.client.JedisClient;
import com.common.redis.client.JedisInterface;
import com.shenrun.p2pproject.user.mapper.UserMapper;
import com.shenrun.p2pproject.user.pojo.UserPojo;
import com.shenrun.p2pproject.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;

import javax.servlet.http.Cookie;
import java.util.UUID;

/**
 * Created by Administrator on 2018/7/14.
 */
@Service("userServiceImpl")
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private JedisInterface jedisClient;

    /**
     * 注册用户
     * @param type
     * @param value
     * @param userPojo
     * @param phoCode
     * @param picCode
     * @throws Exception 如果抛出异常，表示插入失败
     */
    //校验注册信息,要注意，如果信息对了就不需要返回什么，信息错了就可以返回错误直接结束方法了，所以最好判断如果不合法，就返回错误。
    @Override
    public ResultData checkRegister(UserPojo userPojo,String type, String value,String phoCode,String picCode,String confirmPassword) {

        Jedis jedis = jedisClient.getJedis();
//        //在jedisClient的getJedis函数已经认证了
//        jedis.auth("redis001");

        //验证用户类型：借款or投资
        if(type==null||type.equals("")){
            jedis.close();
            return ResultData.setError(ConstantCodeUtil.ERROR,"请选择用户类型",null);
        }

        //手机验证码验证
        //获取用户输入的手机号码
        String inputPhoneNum = userPojo.getPhoneNum();
        if (inputPhoneNum==null||inputPhoneNum.equalsIgnoreCase("")){
            jedis.close();
            return ResultData.setError(ConstantCodeUtil.ERROR,"请输入正确手机号",null);
        }
        String phoCodeServe = jedisClient.get(inputPhoneNum, jedis);
        if (phoCode==null||!phoCode.equalsIgnoreCase(phoCodeServe)){
            jedis.close();
            return ResultData.setError(ConstantCodeUtil.PHONECODEERROR,"验证码错误",null);
        }

        //图片验证码验证
        if(picCode==null||picCode.equalsIgnoreCase("")){
            jedis.close();
            return ResultData.setError(ConstantCodeUtil.PICCODEERROR,"请输入正确的验证码",null);
        }

        System.out.println("value~~~~~~~~"+value.toString());
        if (value == null || value.equalsIgnoreCase("")) {
            jedis.close();
            return ResultData.setError(ConstantCodeUtil.PICCODEERROR, "图片验证码已过期", null);
        }
        String redisPicCode = jedisClient.get(value, jedis);
        System.out.println(redisPicCode+picCode);
        if (redisPicCode==null||!redisPicCode.equalsIgnoreCase(picCode)){
            jedis.close();
            return ResultData.setError(ConstantCodeUtil.PICCODEERROR,"验证码错误",null);
        }

        if(!confirmPassword.equals(userPojo.getPassword())){
            jedis.close();
            return ResultData.setError(ConstantCodeUtil.PICCODEERROR,"两次输入密码不一致",null);
        }
        jedis.close();
        return ResultData.setOk(userPojo);
    }

    @Override
    public void insertUser(UserPojo userPojo,String type) throws Exception{

        //插入数据之前，需要校验数据的合法性,按照项目预先定好的要求,来决定如何校验
        //校验的措施太多,地方太多 如何操作
        //能不能封装一个工具类,能帮我们实现任意对象的任意内容校验
        //例如进行约定,编写注解，notnull 代表修饰的变量不能为空,length(8,16)代表变量的值的长度是8-16 regex('ddasdasd')使用正则进行校验
        //编写完注解并解析注解后，只要发现notnull，就判断该变量是否为空

        Jedis jedis = jedisClient.getJedis();
        //在jedisClient的getJedis函数已经认证了
        //jedis.auth("redis001");

//        //UserId这个主键的插入在mapper.xml的sql语句中完成了
//        String uuid = UUID.randomUUID().toString();
//        System.out.println(uuid);
//        userPojo.setUserId(uuid);

        //此处创建用户的邀请码,用于求邀请他人使用,邀请码是10位的,不是uuid类型的,那么如何去创建一个唯一的邀请码
        //使用redis的自增功能,来模拟生成一个唯一数,预先定义规则(约定,约定优于编码,约定优于配置) 比如邀请码是从10000开始
        //在这里需要在启动redis时给mycode赋初始值
        //TODO 这里按理说不应该在这里赋值10000的，最好的办法是在redis初始化的时候设置这个值
        String myCodeInit="100000";
        Long myCode = jedisClient.incr(myCodeInit,jedis);
        userPojo.setMyRefererCode(myCode.toString());


        //可以进行程序预热,在项目启动的时候将一些几乎不变的内容或者是经常使用的内容先查询出来放入redis中
        //这里的type和typeUserId就可以使用程序预热
        String typeUserId = userMapper.selectUserTypeId(Integer.parseInt(type));
        userPojo.setUserTypeId(typeUserId);
        System.out.println(userPojo.toString());

        //usercreditlevelid,usercreditlevelname,userstatus没有执行插入，这是管理员操作字段，数据库已经设置默认值
        userMapper.insertUser(userPojo);
    }

    @Override
    public ResultData findUserByUserNameAndPasswordAndUserStatus(String userName, String password) {
        UserPojo userPojo = userMapper.selectByUserNameAndPasswordAndUserStatus(userName, password);
        Jedis jedis = jedisClient.getJedis();
        //查询完的对象中 关联的用户的类型是没有的,因为我们没有使用表关联查询,也没有设置冗余数据
        //此处可以使用用户类型type去redis中查询
        //如果查询后想要将数据设置到用户上面，就需要给userpojo添加属性了（需要注意我们用户上并没有任何属性可以接收这个值,如果添加了属性就相当于有冗余数据)
        if (userPojo==null){
            return ResultData.setError(ConstantCodeUtil.ERROR,"用户不存在",null);
        }
        //注意错误,我们当时放的是hash,这里用jedisClient.get是不行,会出现WRONGTYPE Operation against a key holding the wrong kind of value 代表当前key存放的数据类型和取的方式不一致
        //user.setUserTypeName(jedisClient.get(user.getUsertypeid(),jedis));
        //这里用到了userPojo.setUserTypeName，所以需要在mapper.xml中添加对这一字段的查询，要不然这个对象不包含这个字段，你是设置不进去的
        userPojo.setUserTypeName(jedisClient.hGet(userPojo.getUserTypeId(),"userTypeName",jedis));
        jedis.close();
        //TODO
        System.out.println(userPojo.toString());
        return ResultData.setOk(userPojo);
    }
}
