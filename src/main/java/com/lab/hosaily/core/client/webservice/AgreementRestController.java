package com.lab.hosaily.core.client.webservice;

import com.lab.hosaily.core.client.consts.AgreementState;
import com.lab.hosaily.core.client.entity.Agreement;
import com.lab.hosaily.core.client.service.AgreementService;
import com.rab.babylon.commons.security.exception.ApplicationException;
import com.rab.babylon.commons.security.exception.ServiceException;
import com.rab.babylon.commons.security.response.Page;
import com.rab.babylon.commons.security.response.PageRequest;
import com.rab.babylon.commons.security.response.Response;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

@RestController
@RequestMapping(value = "/api/1.0/agreement")
public class AgreementRestController {

    private static Logger logger = LoggerFactory.getLogger(AgreementRestController.class);

    @Autowired
    private AgreementService agreementService;

    /**
     * 添加
     */
    @RequestMapping(value = "", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Response<Agreement> save(@RequestBody Agreement agreement) {
        try {
            agreementService.save(agreement);

            return new Response<Agreement>("添加成功", agreement);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new ApplicationException(e.getMessage(), e);
        }
    }

    /**
     * 修改
     */
    @RequestMapping(value = "", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Response<Agreement> update(@RequestBody Agreement agreement) {
        try {
            agreementService.update(agreement);

            return new Response<Agreement>("修改成功", agreement);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new ApplicationException(e.getMessage(), e);
        }
    }

    /**
     * 根据ID查询
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public Response<Agreement> getById(@PathVariable("id") String id) {
        try {
            Agreement agreement = agreementService.getById(id);

            return new Response<Agreement>("查询成功", agreement);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new ApplicationException(e.getMessage(), e);
        }
    }

    /**
     * 根据购买记录ID查询
     */
    @RequestMapping(value = "/purchaseId/{purchaseId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public Response<Agreement> getByPurchaseId(@PathVariable("purchaseId") String purchaseId) {
        try {
            Agreement agreement = agreementService.getByPurchaseId(purchaseId);

            return new Response<Agreement>("查询成功", agreement);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new ApplicationException(e.getMessage(), e);
        }
    }

    /**
     * 协议确认
     */
    @RequestMapping(value = "/affirm/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public Response affirm(@PathVariable("id") String id) {
        try {
            agreementService.affirm(id);

            return new Response("协议确认成功", null);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new ApplicationException(e.getMessage(), e);
        }
    }

    @RequestMapping(value = "/page", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public Response<Page<Agreement>> page(Long pageNum, Long pageSize, String accountId, String serviceId, AgreementState state) {
        try {
            PageRequest pageRequest = new PageRequest(pageNum, pageSize);
            Page<Agreement> page = agreementService.page(pageRequest, accountId, serviceId, state);

            return new Response<Page<Agreement>>("查询成功", page);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new ApplicationException(e.getMessage(), e);
        }
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public Response<String> create(String serviceId, String productId, Double price, Integer duration, Double callUnitPrice, Integer callTimes) {
        try {
            System.out.println("callUnitPrice: " + callUnitPrice);
            System.out.println("callTimes: " + callTimes);
            String id = agreementService.create(serviceId, productId, price, duration, callUnitPrice, callTimes);


//            92592c28d7364a558f096f13ce30c5fd    在线课程 不需要填咨询单价和咨询次数

            return new Response<String>("添加成功", id);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new ApplicationException(e.getMessage(), e);
        }
    }

    @RequestMapping(value = "/fill", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public Response fillIn(String id, String client, String phone, String address, String idCard, String wechat, String email, String emergencyContact, String accountId) {
        try {
            Response response = new Response();
            Agreement agreement = agreementService.getById(id);
            if (!agreement.getState().equals(AgreementState.WAIT_FOR_FILL) && !agreement.getState().equals(AgreementState.WAIT_FOR_SIGN) && !agreement.getState().equals(AgreementState.CREATED)) {
//                throw new ServiceException("只有合同状态为等待填写或等待签名的合同才可以提交填写资料！");
                response.setSuccess(false);
                response.setResult("只有合同状态为等待填写或等待签名的合同才可以提交填写资料！");
                return response;
            }

//            if (!agreement.getAccountId().equals(accountId)) {
//                response.setSuccess(false);
//                response.setResult("提交失败：当前用户不是合同绑定对象！");
//                return response;
//            }
            agreementService.fill(id, client, phone, address, idCard, wechat, email, emergencyContact, accountId);

            return new Response("填写成功", null);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new ApplicationException(e.getMessage(), e);
        }
    }

    @RequestMapping(value = "/sign/{id}", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public Response sign(@PathVariable("id") String id, @RequestParam("file") CommonsMultipartFile file, String accountId) {
        try {
            Response response = new Response();
            Agreement agreement = agreementService.getById(id);
            if (!agreement.getState().equals(AgreementState.WAIT_FOR_SIGN)) {
//                throw new ServiceException("只有合同状态为等待签名的合同才可以提交签名！");
                response.setSuccess(false);
                response.setResult("只有合同状态为等待签名的合同才可以提交签名！");
                return response;
            }

//            if (!agreement.getAccountId().equals(accountId)) {
//                response.setSuccess(false);
//                response.setResult("提交失败：当前用户不是合同绑定对象！");
//                return response;
//            }
            agreementService.sign(id, file);

            return new Response("签名成功", null);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new ApplicationException(e.getMessage(), e);
        }
    }

    @RequestMapping(value = "/backToEdit", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public Response backToEdit(String id) {
        try {
            agreementService.backToEdit(id);

            return new Response("修改成功", null);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new ApplicationException(e.getMessage(), e);
        }
    }

    @RequestMapping(value = "/share", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public Response share(String id, String accountId) {
        try {
            Response response = new Response();
            Agreement agreement = agreementService.getById(id);
//            if (!agreement.getState().equals(AgreementState.CREATED)) {
////                throw new ServiceException("只有合同状态为新创建的合同才可以绑定！");
//                response.setSuccess(false);
//                response.setResult("只有合同状态为新创建的合同才可以绑定！");
//                return response;
//            }


            if (StringUtils.isNotBlank(agreement.getAccountId())) {
//                throw new ServiceException("协议已分享");
                response.setSuccess(false);
                response.setResult("合同已绑定！");
                return response;
            }

            agreementService.share(id, accountId);

            return new Response("分享成功", null);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new ApplicationException(e.getMessage(), e);
        }
    }

    @RequestMapping(value = "/version", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public Response<String> getVersion() {
        String v = "<image class='bg' src='http://kuliao.b0.upaiyun.com/ellxy/ell_ht/%E5%90%88%E5%90%8C-%E5%90%AF%E5%8A%A8%E9%A1%B5.jpg'></image><button class='open' bindgetuserinfo=\"{{opentype?'login':''}}\" bindtap=\"{{opentype?'':'login'}}\" open-type='{{opentype}}'>立 即 开 启 </button>";

        return new Response<String>("查询成功", v);
    }

    @Scheduled(cron = "0 0 0 * * ?")
    public void effectiveCheck() {
        agreementService.effectiveCheck();
    }


//    public static void main(String[] asd){
//        Agreement agreement = new Agreement();
//        agreement.setState();
//        System.out.println(!agreement.getState().equals(AgreementState.WAIT_FOR_FILL) && !agreement.getState().equals(AgreementState.WAIT_FOR_SIGN));
//    }
}
