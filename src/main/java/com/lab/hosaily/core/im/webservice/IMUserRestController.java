package com.lab.hosaily.core.im.webservice;


import com.lab.hosaily.core.im.consts.HeaderName;
import com.lab.hosaily.core.im.entity.IMUser;
import com.lab.hosaily.core.im.service.IMUserService;
import com.rab.babylon.commons.security.response.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@RestController
@RequestMapping(value = "/api/1.0/im")
public class IMUserRestController{

    @Autowired
    private IMUserService imUserService;

    @RequestMapping(value = "/ticket", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public Response<IMUser> getByTicket(HttpServletRequest request){
        String ticket = request.getHeader(HeaderName.TICKET);
//        IMUser user   = imUserService.getByTicket(ticket);

        return new Response<IMUser>("查询成功", null);
    }

    @RequestMapping(value = "/getByAccount", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public Response<Map<String, Object>> getByAccount(String accountId, String headImgUrl, String wxid, String nickname, String jpush, String versionName){
        Map<String, Object> user = imUserService.getByAccount(accountId, headImgUrl, wxid, nickname, jpush, versionName);

        return new Response<Map<String, Object>>("查询成功", user);
    }


}