package com.lab.hosaily.core.app.webservice;


import com.lab.hosaily.commons.consts.RedisConsts;
import com.lab.hosaily.commons.utils.RandomStringUtils;
import com.lab.hosaily.commons.utils.YunpianUtils;
import com.lab.hosaily.core.app.entity.AppVersion;
import com.lab.hosaily.core.app.service.AppVersionService;
import com.lab.hosaily.core.client.consts.SMSConfig;
import com.lab.hosaily.core.client.consts.SMSTemplate;
import com.rab.babylon.commons.utils.RedisUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.wah.doraemon.security.request.Page;
import org.wah.doraemon.security.request.PageRequest;
import org.wah.doraemon.security.response.Responsed;
import redis.clients.jedis.ShardedJedisPool;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.MessageFormat;


/**
 * Created by miku03 on 2018/5/11.
 */
@RestController
@RequestMapping(value = "/api/1.0/appVersion")
public class AppVersionRestController {

    @Autowired
    private AppVersionService appVersionService;

    @Value("${system.environment}")
    private String env;

    @RequestMapping(value = "/getNewestVersion", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public Responsed<AppVersion> getNewestVersion() {
        AppVersion appVersion = appVersionService.getNewestVersion();
        return new Responsed<AppVersion>("查询成功", appVersion);
    }


}
