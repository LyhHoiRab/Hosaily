package com.lab.hosaily.core.handler.webservice;

import com.rab.babylon.commons.security.exception.ApplicationException;
import com.rab.babylon.commons.security.response.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping(value = "/api/1.0/mediaType")
public class MediaTypeRestController{

    private static Logger logger = LoggerFactory.getLogger(MediaTypeRestController.class);

    @RequestMapping(value = "/list", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public Response<Map<Object, Object>> list(){
        try{
            Map<Object, Object> map = new HashMap<Object, Object>();
            for(com.lab.hosaily.core.course.consts.MediaType type : com.lab.hosaily.core.course.consts.MediaType.values()){
                map.put(type.getId(), type.getDescription());
            }

            return new Response<Map<Object, Object>>("查询成功", map);
        }catch(Exception e){
            logger.error(e.getMessage(), e);
            throw new ApplicationException(e.getMessage(), e);
        }
    }
}
