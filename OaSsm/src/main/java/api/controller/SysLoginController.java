package api.controller;

import api.dto.R;
import api.utils.Constant;
import api.utils.ShiroUtils;
import com.google.code.kaptcha.Constants;
import com.google.code.kaptcha.Producer;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.crypto.hash.Sha256Hash;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Map;

/**
 * Created by helen on 2018/6/28
 */
@RequestMapping("/api")
@Controller
public class SysLoginController {

    @Autowired
    private Producer producer;

    /**************验证码**********************/
    @GetMapping("/sys/captcha.jpg")
    public void kaptchaCode(HttpServletResponse response) throws IOException {

        response.setHeader("Cache-Control", "no-store, no-cache");
        response.setContentType("image/jpeg");

        String text = producer.createText();
        BufferedImage image = producer.createImage(text);

        //将验证码保存到session，Constants.KAPTCHA_SESSION_KEY是自定义的可以随便定义
        //在此可以建一个常数类专门存放常数
        ShiroUtils.setSessionAttribute(Constants.KAPTCHA_SESSION_KEY, text);


        ServletOutputStream out = response.getOutputStream();
        //图片流  后缀名 输出流
        ImageIO.write(image, "jpg", out);
        out.flush();
    }

    /**************登录**********************/
    @PostMapping("/sys/login")
    @ResponseBody
    public R login(@RequestBody Map<String, String> map){

        String username = map.get("username");
        String password = map.get("password");
        String captcha = map.get("captcha");
        String rememberMeString = map.get("rememberMe");

        Boolean rememberMe = false;
        if("true".equals(rememberMeString)){
            rememberMe = true;
        }

        String kaptcha = ShiroUtils.getKaptcha(Constants.KAPTCHA_SESSION_KEY);
        if(!captcha.equals(kaptcha)){
            return R.error("验证码不正确");
        }

        Subject subject = ShiroUtils.getSubject();

        Sha256Hash hash = new Sha256Hash(password, username, Constant.HASH_ITERATIONS);
        password = hash.toString();

        UsernamePasswordToken token = new UsernamePasswordToken(username, password);
        token.setRememberMe(rememberMe);
        subject.login(token);

        return R.ok("登录成功");
    }

    /**************登出**********************/
    @GetMapping("/sys/logOut")
    public String loginOut(){
        //调用shiro整合的登出方法
        ShiroUtils.logout();
        return "redirect: /login.html";
    }
}
