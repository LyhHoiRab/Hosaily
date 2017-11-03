package com.lab.hosaily.core.sell.webservice;

import com.lab.hosaily.core.sell.entity.PaymentType;
import com.lab.hosaily.core.sell.service.PaymentTypeService;
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
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import java.util.List;

@RestController
@RequestMapping(value = "/api/1.0/paymentType")
public class PaymentTypeRestController{

    private static Logger logger = LoggerFactory.getLogger(PaymentTypeRestController.class);

    @Autowired
    private PaymentTypeService paymentTypeService;

    /**
     * 添加
     */
    @RequestMapping(value = "", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Response<PaymentType> save(@RequestBody PaymentType paymentType){
        try{
            paymentTypeService.save(paymentType);

            return new Response<PaymentType>("添加成功", paymentType);
        }catch(Exception e){
            logger.error(e.getMessage(), e);
            throw new ApplicationException(e.getMessage(), e);
        }
    }

    /**
     * 修改
     */
    @RequestMapping(value = "", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Response<PaymentType> update(@RequestBody PaymentType paymentType){
        try{
            paymentTypeService.update(paymentType);

            return new Response<PaymentType>("修改成功", paymentType);
        }catch(Exception e){
            logger.error(e.getMessage(), e);
            throw new ApplicationException(e.getMessage(), e);
        }
    }

    /**
     * 根据ID查询
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public Response<PaymentType> getById(@PathVariable("id") String id){
        try{
            PaymentType paymentType = paymentTypeService.getById(id);

            return new Response<PaymentType>("查询成功", paymentType);
        }catch(Exception e){
            logger.error(e.getMessage(), e);
            throw new ApplicationException(e.getMessage(), e);
        }
    }

    /**
     * 分页查询
     */
    @RequestMapping(value = "/page", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public Response<Page<PaymentType>> page(Long pageNum, Long pageSize, UsingState state, String name){
        try{
            PageRequest pageRequest = new PageRequest(pageNum, pageSize);
            Page<PaymentType> page = paymentTypeService.page(pageRequest, state, name);

            return new Response<Page<PaymentType>>("查询成功", page);
        }catch(Exception e){
            logger.error(e.getMessage(), e);
            throw new ApplicationException(e.getMessage(), e);
        }
    }

    /**
     * 查询列表
     */
    @RequestMapping(value = "/list", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public Response<List<PaymentType>> list(UsingState state, String name){
        try{
            List<PaymentType> list = paymentTypeService.list(state, name);

            return new Response<List<PaymentType>>("查询成功", list);
        }catch(Exception e){
            logger.error(e.getMessage(), e);
            throw new ApplicationException(e.getMessage(), e);
        }
    }

    /**
     * 上传图片
     */
    @RequestMapping(value = "/upload", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public Response<String> upload(@RequestParam("file") CommonsMultipartFile file){
        try{
            String url = paymentTypeService.upload(file);
            return new Response<String>("上传成功", url);
        }catch(Exception e){
            logger.error(e.getMessage(), e);
            throw new ApplicationException(e.getMessage(), e);
        }
    }
}
