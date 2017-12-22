package com.lab.hosaily.core.client.webservice;

import com.lab.hosaily.core.client.consts.PurchaseState;
import com.lab.hosaily.core.client.entity.Purchase;
import com.lab.hosaily.core.client.service.PurchaseService;
import com.rab.babylon.commons.security.exception.ApplicationException;
import com.rab.babylon.commons.security.response.Page;
import com.rab.babylon.commons.security.response.PageRequest;
import com.rab.babylon.commons.security.response.Response;
import com.rab.babylon.core.consts.entity.UsingState;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/1.0/purchase")
public class PurchaseRestController{

    private static Logger logger = LoggerFactory.getLogger(PurchaseRestController.class);

    @Autowired
    private PurchaseService purchaseService;

    /**
     * 保存
     */
    @RequestMapping(value = "", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Response<Purchase> save(@RequestBody Purchase purchase){
        try{
            purchaseService.save(purchase);

            return new Response<Purchase>("添加成功", purchase);
        }catch(Exception e){
            logger.error(e.getMessage(), e);
            throw new ApplicationException(e.getMessage(), e);
        }
    }

    /**
     * 修改
     */
    @RequestMapping(value = "", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Response<Purchase> update(@RequestBody Purchase purchase){
        try{
            purchaseService.update(purchase);

            return new Response<Purchase>("修改成功", purchase);
        }catch(Exception e){
            logger.error(e.getMessage(), e);
            throw new ApplicationException(e.getMessage(), e);
        }
    }

    /**
     * 根据ID查询
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public Response<Purchase> getById(@PathVariable("id") String id){
        try{
            Purchase purchase = purchaseService.getById(id);

            return new Response<Purchase>("查询成功", purchase);
        }catch(Exception e){
            logger.error(e.getMessage(), e);
            throw new ApplicationException(e.getMessage(), e);
        }
    }

    /**
     * 列表
     */
    @RequestMapping(value = "/list", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public Response<List<Purchase>> list(PurchaseState purchaseState, UsingState state, String accountId, String organizationId, Double price){
        try{
            List<Purchase> list = purchaseService.list(purchaseState, state, accountId, organizationId, price);

            return new Response<List<Purchase>>("查询成功", list);
        }catch(Exception e){
            logger.error(e.getMessage(), e);
            throw new ApplicationException(e.getMessage(), e);
        }
    }

    /**
     * 分页
     */
    @RequestMapping(value = "/page", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public Response<Page<Purchase>> page(Long pageNum, Long pageSize, PurchaseState purchaseState, UsingState state, String accountId, String organizationId, Double price){
        try{
            PageRequest pageRequest = new PageRequest(pageNum, pageSize);
            Page<Purchase> page = purchaseService.page(pageRequest, purchaseState, state, accountId, organizationId, price);

            return new Response<Page<Purchase>>("查询成功", page);
        }catch(Exception e){
            logger.error(e.getMessage(), e);
            throw new ApplicationException(e.getMessage(), e);
        }
    }
}
