package com.lab.hosaily.core.client.webservice;

import com.lab.hosaily.core.client.entity.Agreement;
import com.lab.hosaily.core.client.service.AgreementService;
import com.rab.babylon.commons.security.exception.ApplicationException;
import com.rab.babylon.commons.security.response.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/1.0/agreement")
public class AgreementRestController{

    private static Logger logger = LoggerFactory.getLogger(AgreementRestController.class);

    @Autowired
    private AgreementService agreementService;

    /**
     * 添加
     */
    @RequestMapping(value = "", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Response<Agreement> save(@RequestBody Agreement agreement){
        try{
            agreementService.save(agreement);

            return new Response<Agreement>("添加成功", agreement);
        }catch(Exception e){
            logger.error(e.getMessage(), e);
            throw new ApplicationException(e.getMessage(), e);
        }
    }

    /**
     * 修改
     */
    @RequestMapping(value = "", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Response<Agreement> update(@RequestBody Agreement agreement){
        try{
            agreementService.update(agreement);

            return new Response<Agreement>("修改成功", agreement);
        }catch(Exception e){
            logger.error(e.getMessage(), e);
            throw new ApplicationException(e.getMessage(), e);
        }
    }

    /**
     * 根据ID查询
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public Response<Agreement> getById(@PathVariable("id") String id){
        try{
            Agreement agreement = agreementService.getById(id);

            return new Response<Agreement>("查询成功", agreement);
        }catch(Exception e){
            logger.error(e.getMessage(), e);
            throw new ApplicationException(e.getMessage(), e);
        }
    }

    /**
     * 根据购买记录ID查询
     */
    @RequestMapping(value = "/purchaseId/{purchaseId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public Response<Agreement> getByPurchaseId(@PathVariable("purchaseId") String purchaseId){
        try{
            Agreement agreement = agreementService.getByPurchaseId(purchaseId);

            return new Response<Agreement>("查询成功", agreement);
        }catch(Exception e){
            logger.error(e.getMessage(), e);
            throw new ApplicationException(e.getMessage(), e);
        }
    }

    /**
     * 协议确认
     */
    @RequestMapping(value = "/affirm/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public Response affirm(@PathVariable("id") String id){
        try{
            agreementService.affirm(id);

            return new Response("协议确认成功", null);
        }catch(Exception e){
            logger.error(e.getMessage(), e);
            throw new ApplicationException(e.getMessage(), e);
        }
    }
}
