package com.lab.hosaily.core.account.webservice;

import com.lab.hosaily.core.account.service.AttentionService;
import com.rab.babylon.commons.security.exception.ApplicationException;
import com.rab.babylon.commons.security.response.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/1.0/attention")
public class AttentionRestController{

    private static Logger logger = LoggerFactory.getLogger(AttentionRestController.class);

    @Autowired
    private AttentionService attentionService;

    /**
     * 删除收藏
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Response delete(@PathVariable("id") String id){
        try{
            attentionService.delete(id);
            return new Response("删除成功", null);
        }catch(Exception e){
            logger.error(e.getMessage(), e);
            throw new ApplicationException(e.getMessage(), e);
        }
    }
}
