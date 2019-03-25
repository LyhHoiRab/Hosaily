package com.lab.hosaily.core.app.webservice;


import com.lab.hosaily.commons.consts.RedisConsts;
import com.lab.hosaily.commons.utils.RandomStringUtils;
import com.lab.hosaily.commons.utils.YunpianUtils;
import com.lab.hosaily.core.app.entity.LoginUser;
import com.lab.hosaily.core.app.service.LoginUserService;
import com.lab.hosaily.core.app.utils.JavaSmsApi;
import com.lab.hosaily.core.app.utils.WriteExcel;
import com.lab.hosaily.core.client.consts.SMSConfig;
import com.lab.hosaily.core.client.consts.SMSTemplate;
import com.rab.babylon.commons.utils.RedisUtils;
import com.rab.babylon.core.consts.entity.Sex;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.wah.doraemon.security.request.Page;
import org.wah.doraemon.security.request.PageRequest;
import org.wah.doraemon.security.response.Responsed;
import redis.clients.jedis.ShardedJedisPool;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URLEncoder;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.*;


/**
 * Created by miku03 on 2018/5/11.
 */
@RestController
@RequestMapping(value = "/api/1.0/loginUser")
public class LoginUserRestController {

    @Autowired
    private LoginUserService loginUserService;

    @Autowired
    private ShardedJedisPool shardedJedisPool;

    @Value("${system.environment}")
    private String env;

    @RequestMapping(value = "/page", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public Responsed<Page<LoginUser>> page(HttpServletRequest request, Long pageNum, Long pageSize, String name,
                                                  String situation, String startTime, String endTime, String process, String follower) {
        PageRequest pageRequest = new PageRequest(pageNum, pageSize);
        Page<LoginUser> page = loginUserService.page(pageRequest, name,
                situation, startTime, endTime, process, follower);
        return new Responsed<Page<LoginUser>>("查询成功", page);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public Responsed<LoginUser> getByPhone(@PathVariable("phone") String phone) {
        LoginUser loginUser = loginUserService.getByPhone(phone);
        return new Responsed<LoginUser>("查询成功", loginUser);
    }



    @RequestMapping(value = "/login", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public Responsed<LoginUser> login(HttpServletRequest request, String phone, String smsCode) throws Exception {



//        System.out.println("login sessionId: " + request.getSession().getId());
        Responsed<LoginUser> responsed = new Responsed<LoginUser>();
        LoginUser loginUser = loginUserService.getByPhone(phone);
        if(null == loginUser){
            responsed.setSuccess(false);
            responsed.setMsg("用户不存在！");
            responsed.setResult(null);
            return responsed;
        }
        if(null == smsCode || smsCode.length() != 4){
            responsed.setSuccess(false);
            responsed.setMsg("验证码错误！");
            responsed.setResult(null);
            return responsed;
        }
//        String sessionSmsCode = request.getSession().getAttribute("smsCode") == null ? "": request.getSession().getAttribute("smsCode").toString();
        //缓存
        String sessionSmsCode = RedisUtils.get(shardedJedisPool.getResource(), RedisConsts.CAPTCHA_PHONE + phone, String.class);
        System.out.println("sessionSmsCode: " + sessionSmsCode);
        if(!smsCode.equals(sessionSmsCode)){
            responsed.setSuccess(false);
            responsed.setMsg("验证码错误！");
            responsed.setResult(null);
            return responsed;
        }
//        request.getSession().removeAttribute("smsCode");
//        request.getSession().setAttribute("loginUser", loginUser);


        RedisUtils.delete(shardedJedisPool.getResource(), RedisConsts.CAPTCHA_PHONE + phone);
        //缓存
        RedisUtils.save(shardedJedisPool.getResource(), RedisConsts.USER_INFO + phone, loginUser, RedisConsts.APP_USER_EFFECTIVE_SECOND);

        responsed.setSuccess(true);
        responsed.setMsg("登录成功");
        responsed.setResult(loginUser);
        return responsed;
    }



    @RequestMapping(value = "/sendSms", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public Responsed<String> sendSms(HttpServletRequest request, HttpServletResponse response, String phone) throws Exception {


        Responsed<String> responsed = new Responsed<String>();
        LoginUser loginUser = loginUserService.getByPhone(phone);
        if(null == loginUser){
            responsed.setSuccess(false);
            responsed.setMsg("用户不存在,发送失败！");
            responsed.setResult(null);
            return responsed;
        }

        long tpl_id = 1990660;
//        感谢您注册#app#，您的验证码是#code#
        //设置对应的模板变量值

        String apikey = "650d68be159b88b3d996298c6ff5bfcc";

        //修改为您要发送的手机号
//        String mobile = "13143536569";
//        Random r = new Random();
//        String code = r.nextInt(10) + "" + r.nextInt(10) + "" + r.nextInt(10) + "" + r.nextInt(10);
//        System.out.println("code: " + code);
//        String tpl_value = URLEncoder.encode("#code#", "UTF-8") + "=" +
//                URLEncoder.encode(code, "UTF-8") + "&" + URLEncoder.encode(
//                "#app#", "UTF-8") + "=" + URLEncoder.encode("永恒情书",
//                "UTF-8");
        SMSConfig config = SMSConfig.YHQS;
        String captcha = RandomStringUtils.nextNumber(4);
        String template = MessageFormat.format(SMSTemplate.CAPTCHA, config.getCompany(), captcha);
        //发送
        YunpianUtils.sendSms(config.getApiKey(), template, phone);

        //缓存
        RedisUtils.save(shardedJedisPool.getResource(), RedisConsts.CAPTCHA_PHONE + phone, captcha, RedisConsts.CAPTCHA_EFFECTIVE_SECOND);


        //模板发送的调用示例
//        System.out.println("tpl_value: " + tpl_value);
//        System.out.println("JavaSmsApi.tplSendSms(apikey, tpl_id, tpl_value,mobile):" + JavaSmsApi.tplSendSms(apikey, tpl_id, tpl_value, phone));
//        request.getSession().setAttribute("smsCode", code);
//        System.out.println("send sms sessionId: " + request.getSession().getId());
        responsed.setSuccess(true);
        responsed.setMsg("发送成功");
        responsed.setResult(null);
        return responsed;
    }
}
