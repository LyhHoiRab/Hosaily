package com.lab.hosaily.core.application.webservice;

import com.lab.hosaily.core.application.service.ApplicationService;
import com.rab.babylon.commons.security.exception.ApplicationException;
import com.rab.babylon.commons.security.response.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@RestController
@RequestMapping(value = "/api/1.0/application")
public class ApplicationRestController{

    private static Logger logger = LoggerFactory.getLogger(ApplicationRestController.class);

    @Autowired
    private ApplicationService applicationService;

    /**
     * 查询网站应用授权二维码参数
     */
    @RequestMapping(value = "/qr", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public Response<Map<String, Object>> getQRParams(HttpServletRequest request, String token, String redirectUrl){
        try{
            String sessionId = request.getSession().getId();
            Map<String, Object> result = applicationService.getQRParams(sessionId, token, redirectUrl);

            return new Response<Map<String, Object>>("查询成功", result);
        }catch(Exception e){
            logger.error(e.getMessage(), e);
            throw new ApplicationException(e.getMessage(), e);
        }
    }
}
