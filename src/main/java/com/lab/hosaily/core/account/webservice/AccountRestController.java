package com.lab.hosaily.core.account.webservice;

import com.lab.hosaily.commons.consts.AuthorizationConsts;
import com.lab.hosaily.commons.consts.SessionConsts;
import com.lab.hosaily.core.account.consts.Gender;
import com.lab.hosaily.core.account.entity.AppAccount;
import com.lab.hosaily.core.account.service.AccountService;
import com.lab.hosaily.core.account.service.UserService;
import com.lab.hosaily.core.app.entity.Profile;
import com.lab.hosaily.core.app.service.ProfileService;
import com.lab.hosaily.core.handler.utils.handler.SexEditor;
import com.rab.babylon.commons.security.exception.ApplicationException;
import com.rab.babylon.commons.security.response.Response;
import com.rab.babylon.commons.utils.AESUtils;
import com.rab.babylon.core.account.entity.Account;
import com.rab.babylon.core.account.entity.User;
import com.rab.babylon.core.consts.entity.Sex;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

@RestController
@Api(description = "登录")
@RequestMapping(value = "/api/1.0/account")
public class AccountRestController {

    private static Logger logger = LoggerFactory.getLogger(AccountRestController.class);

    @Autowired
    private AccountService accountService;

    @Autowired
    private UserService userService;

    @Autowired
    private ProfileService profileService;

    /**
     * 小程序应用注册
     */
    @ApiIgnore
    @RequestMapping(value = "/register/xcx", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public Response<User> registerByXcx(String token, String code, String signature, String rawData, String encryptedData, String iv) {
        try {
            User user = accountService.registerByXcx(token, code, signature, rawData, encryptedData, iv);
            return new Response<User>("登录成功", user);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new ApplicationException(e.getMessage(), e);
        }
    }


    /**
     * 小程序应用注册
     */
    @ApiIgnore
    @RequestMapping(value = "/login/web", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public Response<User> loginWeb(String phone, String password, HttpServletRequest request, HttpServletResponse response, String accountId, String state) {
        try {
            String decrypt = AESUtils.decryptBy128(state, AuthorizationConsts.KEY);
            String[] info = decrypt.split("_");
            String redirectUrl = info[0];
            HttpSession session = request.getSession();
            Account account = accountService.getById(accountId);
            Response<User> response1 = new Response<User>();
            if (null == account) {
                response1.setSuccess(false);
                response1.setMsg("授权失败！");
                return response1;
            }
            if (StringUtils.isBlank(account.getPhone())) {
                response1.setSuccess(false);
                response1.setMsg("请先注册账号！");
                return response1;
            }
            if (account.getPhone().equals(phone) || account.getPassword().equals(password)) {
                response1.setSuccess(false);
                response1.setMsg("账号或密码错误");
                return response1;
            }
            User user = userService.getByAccountId(accountId);
            session.setAttribute(SessionConsts.ACCOUNT_ID, user.getAccountId());
            session.setAttribute(SessionConsts.USER_NICKNAME, user.getNickname());
            session.setAttribute(SessionConsts.USER_NAME, user.getName());
            session.setAttribute(SessionConsts.USER_HEAD_IMG_URL, user.getHeadImgUrl());
            //有效时长
            session.setMaxInactiveInterval(SessionConsts.EFFECTIVE_SECOND);
//            response.sendRedirect(redirectUrl);
            response1.setSuccess(true);
            response1.setMsg("登录成功！");
            response1.setResult(user);
            return response1;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new ApplicationException(e.getMessage(), e);
        }
    }


    /**
     * 网站应用注册
     */
    @ApiOperation(value = "客户列表", notes = "客户列表分页")
    @RequestMapping(value = "/register/newWeb", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public Response<Profile> registerByNewWeb(String code, String token) {
        try {
            System.out.println("codecodecodecodecode: " + code);
            System.out.println("tokentokentokentoken: " + token);
            User user = accountService.registerByNewWeb(token, code);
            Response<Profile> response = new Response<Profile>();
            response.setSuccess(true);
            response.setMsg("授权成功！");
//            Map<String, String> map = new HashMap<String, String>();
//            map.put("accountId", user.getAccountId());
            Profile profile = profileService.getByAccountId(user.getAccountId());
//            map.put("role", profile.);
            response.setResult(profile);
            return response;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new ApplicationException(e.getMessage(), e);
        }
    }


    /**
     * 网站应用注册
     */
    @ApiOperation(value = "客户列表", notes = "客户列表分页")
    @RequestMapping(value = "/register/app", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public Response<Profile> registerByApp(String openid, String nickname, String sex, String language, String city, String province, String country, String headimgurl, String unionid, String[] privilege) {
        try {


            System.out.println("headimgurl: " + headimgurl);
            System.out.println("nickname: " + nickname);
            System.out.println("unionid: " + unionid);

            AppAccount appAccount = new AppAccount();
            appAccount.setNickname(nickname);
            appAccount.setAvatarUrl(headimgurl);
            appAccount.setCity(city);
            appAccount.setCountry(country);
            appAccount.setGender(Gender.getById(Integer.parseInt(sex)));
            appAccount.setLanguage(language);
            appAccount.setOpenId(openid);
            appAccount.setProvince(province);
            appAccount.setUnionId(unionid);
            appAccount.setAppId("wx1cca280c62d7a9f1");

//            {
//                "openid":"oQTXV04JWvLIo-H0XMhmTgHQY01A",
//                    "nickname":"宁静",
//                    "sex":0,
//                    "language":"zh_CN",
//                    "city":"",
//                    "province":""
//                    ,"country":""
//                    ,"headimgurl":"http://thirdwx.qlogo.cn/mmopen/vi_32/SqgtPxDr3icibFU6yxQamWfTTJssWPwZBzTSRwSMUB6K25CTB4M3UEcvY5yBLykZ3c0SekdvlGZI7lC3VkWacHIA/132",
//                    "privilege":[],
//                "unionid":"ov6euwobctJO6UxoHzY2xrfbXv80"
//            }

            User user = accountService.registerByApp(appAccount);
            Profile profile = profileService.getByAccountId(user.getAccountId());
            Response<Profile> response = new Response<Profile>();
            response.setSuccess(true);
            response.setMsg("授权成功！");

            response.setResult(profile);
            return response;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new ApplicationException(e.getMessage(), e);
        }
    }

    /**
     * 网站应用注册
     */
    @RequestMapping(value = "/register/newWebByPhone", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public Response<String> registerByNewWeb(String phone, String password, String password2, String accountId) {
        try {
            Account account = accountService.getById(accountId);
            Response<String> response = new Response<String>();
            if (null == account) {
                response.setSuccess(false);
                response.setMsg("授权失败！");
                return response;
            }
            if (StringUtils.isNotBlank(account.getPhone())) {
                response.setSuccess(false);
                response.setMsg("此微信已绑定！");
                return response;
            }
            if (0 < accountService.countByPhone(phone)) {
                response.setSuccess(false);
                response.setMsg("此电话号码已注册！");
                return response;
            }
            if (StringUtils.isBlank(phone)) {
                response.setSuccess(false);
                response.setMsg("电话号码不能为空！");
                return response;
            }
            if (StringUtils.isBlank(password)) {
                response.setSuccess(false);
                response.setMsg("密码不能为空！");
                return response;
            }
            if (!password.equals(password2)) {
                response.setSuccess(false);
                response.setMsg("密码不一样！");
                return response;
            }
            account.setPhone(phone);
            account.setPassword(password);
            accountService.saveOrUpdate(account);
            response.setSuccess(true);
            response.setMsg("注册成功！");
            return response;

        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new ApplicationException(e.getMessage(), e);
        }
    }


    /**
     * 网站应用注册
     */
    @ApiIgnore
    @RequestMapping(value = "/register/web", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public void registerByWeb(HttpServletRequest request, HttpServletResponse response, String code, String state) {
        try {
            String decrypt = AESUtils.decryptBy128(state, AuthorizationConsts.KEY);
            String[] info = decrypt.split("_");
            String redirectUrl = info[0];
            String token = info[1];

            User user = accountService.registerByWeb(token, code);

            HttpSession session = request.getSession();
            //用户信息
            session.setAttribute(SessionConsts.ACCOUNT_ID, user.getAccountId());
            session.setAttribute(SessionConsts.USER_NICKNAME, user.getNickname());
            session.setAttribute(SessionConsts.USER_NAME, user.getName());
            session.setAttribute(SessionConsts.USER_HEAD_IMG_URL, user.getHeadImgUrl());
            //有效时长
            session.setMaxInactiveInterval(SessionConsts.EFFECTIVE_SECOND);

            response.sendRedirect(redirectUrl);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new ApplicationException(e.getMessage(), e);
        }
    }

    /**
     * 小程序获取用户手机号码
     */
    @ApiIgnore
    @RequestMapping(value = "/phone/xcx", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public Response<String> phoneByXcx(String token, String accountId, String sessionKey, String encryptedData, String vi) {
        try {
            String phone = accountService.phoneByXcx(token, accountId, sessionKey, encryptedData, vi);

            return new Response("手机号码获取成功", phone);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new ApplicationException(e.getMessage(), e);
        }
    }

    /**
     * 小程序获取用户地理位置
     */
    @ApiIgnore
    @RequestMapping(value = "/location/xcx", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public Response locationByXcx(String token, String accountId, Double latitude, Double longitude) {
        try {
            accountService.locationByXcx(token, accountId, latitude, longitude);

            return new Response("地理位置上报成功", null);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new ApplicationException(e.getMessage(), e);
        }
    }
}
