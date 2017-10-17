package com.lab.hosaily.core.account.webservice;

import com.lab.hosaily.commons.consts.AuthorizationConsts;
import com.lab.hosaily.commons.consts.SessionConsts;
import com.lab.hosaily.core.account.service.AccountService;
import com.rab.babylon.commons.security.exception.ApplicationException;
import com.rab.babylon.commons.utils.AESUtils;
import com.rab.babylon.core.account.entity.Account;
import com.rab.babylon.core.account.entity.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@RestController
@RequestMapping(value = "/api/1.0/account")
public class AccountRestController{

    private static Logger logger = LoggerFactory.getLogger(AccountRestController.class);

    @Autowired
    private AccountService accountService;

    /**
     * 网站应用注册
     */
    @RequestMapping(value = "/register/web", method = RequestMethod.GET)
    public void registerByWeb(HttpServletRequest request, HttpServletResponse response, String code, String state){
        try{
            String decrypt = AESUtils.decryptBy128(state, AuthorizationConsts.KEY);
//            String decrypt = state;
            String[] info = decrypt.split("_");
            String redirectUrl = info[0];
            String token = info[1];

            User user = accountService.registerByWeb(token, code);

            HttpSession session = request.getSession();
            //用户信息
            session.setAttribute(SessionConsts.ACCOUNT_ID, user.getAccountId());
            session.setAttribute(SessionConsts.USER_NICKNAME, user.getNickname());
            session.setAttribute(SessionConsts.USER_HEAD_IMG_URL, user.getHeadImgUrl());
            //有效时长
            session.setMaxInactiveInterval(SessionConsts.EFFECTIVE_SECOND);

            response.sendRedirect(redirectUrl);
        }catch(Exception e){
            logger.error(e.getMessage(), e);
            throw new ApplicationException(e.getMessage(), e);
        }
    }
}
