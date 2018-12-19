package com.lab.hosaily.core.app.webservice;

import com.lab.hosaily.core.app.entity.Order;
import com.lab.hosaily.core.app.entity.OrderProfile;
import com.lab.hosaily.core.app.entity.Profile;
import com.lab.hosaily.core.app.service.OrderService;
import com.lab.hosaily.core.app.service.ProfileService;
import com.lab.hosaily.core.client.entity.Agreement;
import com.lab.hosaily.core.client.service.AgreementService;
import com.rab.babylon.commons.security.exception.ApplicationException;
import com.rab.babylon.commons.security.response.Page;
import com.rab.babylon.commons.security.response.PageRequest;
import com.rab.babylon.commons.security.response.Response;
import com.rab.babylon.core.consts.entity.UsingState;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.util.*;

/**
 * Created by Administrator on 2017/9/13.
 */
@RestController
@RequestMapping(value = "/api/1.0/app/order")
@Api(description = "订单")
public class OrderRestController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private AgreementService agreementService;

    @Autowired
    private ProfileService profileService;

    //    http://localhost:8080/swagger-ui.html
    private static final Logger logger = LoggerFactory.getLogger(OrderRestController.class);

//    @ApiIgnore
//    @RequestMapping(value = "", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
//    public Response<Order> save() {
//        try {
//            Order order = new Order();
//            order.setStatus("setStatus");
//            order.setOrganizationId("setOrganizationId");
//            order.setCustomerName("setCustomerName");
//            order.setPayType("setPayType");
//            order.setPrice(12.1);
//            order.setSellId("setSellId");
//            order.setServicePackage("setServicePackage");
////            order.setTeacher1Id("setTeacher1Id");
////            order.setTeacher2Id("setTeacher2Id");
//            order.setTitle("setTitle");
//            order.setWechatNum("setWechatNum");
//            orderService.save(order);
//            return new Response<Order>("添加成功", order);
//        } catch (Exception e) {
//            logger.error(e.getMessage(), e);
//            throw new ApplicationException(e.getMessage(), e);
//        }
//    }

//    @ApiIgnore
//    @RequestMapping(value = "", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
//    public Response<Order> update() {
//        try {
//            Order order = orderService.getById("65c407ff8da24f229df888384cafde85", null);
//            order.setStatus("setStatus1");
//            order.setOrganizationId("setOrganizationId1");
//            order.setCustomerName("setCustomerName1");
//            order.setPayType("setPayType1");
//            order.setPrice(12.1);
//            Profile seller = new Profile();
//            seller.setId(agreement.getServiceId());
//            order.setSeller(seller);
//            order.setServicePackage("setServicePackage1");
////            order.setTeacher1Id("setTeacher1Id1");
////            order.setTeacher2Id("setTeacher2Id1");
//            order.setTitle("setTitle1");
//            order.setWechatNum("setWechatNum1");
//            orderService.update(order);
//            return new Response<Order>("修改成功", order);
//        } catch (Exception e) {
//            logger.error(e.getMessage(), e);
//            throw new ApplicationException(e.getMessage(), e);
//        }
//    }

    @ApiOperation(value = "用id跳到下单页面", notes = "用id跳到下单页面")
    @ApiImplicitParam(name = "agreementId", value = "合同ID", paramType = "path", required = true, dataType = "String")
    @RequestMapping(value = "/{agreementId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public Response<Order> getByAgreementId(@PathVariable("agreementId") String agreementId) {
        try {
            Response<Order> response = new Response<Order>();
//            验证合同是否有效，且未下单。
            Agreement agreement = agreementService.getById(agreementId);
//            检查状态是已经确认，并且未下单。
            if (agreement.getState().WAIT_FOR_SIGN == agreement.getState()) {
                response.setSuccess(false);
                response.setMsg(agreement.getState().getDescription());
                return response;
            }
//            生成订单
            Order order = orderService.getByAgreementId(agreementId);
            if (null == order) {
                order = new Order();
            } else {
                if (StringUtils.isNotBlank(order.getStatus())) {
                    response.setSuccess(false);
                    response.setMsg("订单异常！");
                    return response;
                }
            }
            order.setOrganizationId("setOrganizationId1");
            order.setCustomerName(agreement.getClient());
            order.setAccountId(agreement.getAccountId());
            order.setPrice(agreement.getPrice());
            Profile seller = new Profile();
            seller.setId(agreement.getServiceId());
            order.setSeller(seller);
            order.setServicePackage(agreement.getName());
            order.setTitle(agreement.getName());
            order.setWechatNum(agreement.getWechat());
            order.setAgreementId(agreementId);
            order.setClientHeadImgUrl(agreement.getClientHeadImgUrl());

            orderService.save(order);
            response.setSuccess(true);
            response.setMsg("订单生成成功");
            response.setResult(order);
            return response;

        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new ApplicationException(e.getMessage(), e);
        }
    }

    @ApiOperation(value = "我的订单", notes = "我的订单列表分页")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "sellerId", value = "销售ID", paramType = "query", required = false, dataType = "String"),
            @ApiImplicitParam(name = "advisorName", value = "导师名字(用于分配导师搜索)", paramType = "query", required = false, dataType = "String"),
            @ApiImplicitParam(name = "mixSearch", value = "综合查询(套餐名/微信号/客户名/导师名)", paramType = "query", required = false, dataType = "String"),
            @ApiImplicitParam(name = "assign", value = "分配情况(0:未分配/1:已分配)", paramType = "query", required = false, dataType = "String"),
            @ApiImplicitParam(name = "pageNum", value = "页码", paramType = "query", required = false, dataType = "Long"),
            @ApiImplicitParam(name = "pageSize", value = "页大小", paramType = "query", required = false, dataType = "Long")
    })
    @RequestMapping(value = "/page", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public Response<Page<Order>> page(Long pageNum, Long pageSize, String sellerId, String advisorName, String assign, String mixSearch, String startTime, String endTime) {
        try {
            PageRequest pageRequest = new PageRequest(pageNum, pageSize);
            Page<Order> page = orderService.page(pageRequest, sellerId, advisorName, assign, mixSearch, startTime, endTime);

            return new Response<Page<Order>>("查询成功", page);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new ApplicationException(e.getMessage(), e);
        }
    }


    @ApiOperation(value = "提交下单", notes = "提交下单")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "订单ID", paramType = "query", required = true, dataType = "String"),
//            @ApiImplicitParam(name = "payType", value = "支付方式(1/支付宝;2/微信;3/银行卡;)", paramType = "query", required = true, dataType = "String"),
            @ApiImplicitParam(name = "bankPay", value = "银行卡支付金额", paramType = "query", required = false, dataType = "Double"),
            @ApiImplicitParam(name = "payPalPay", value = "paypal支付金额", paramType = "query", required = false, dataType = "Double"),
            @ApiImplicitParam(name = "aliPay", value = "支付宝支付金额", paramType = "query", required = false, dataType = "Double"),
            @ApiImplicitParam(name = "wecharPay", value = "微信支付金额", paramType = "query", required = false, dataType = "Double")
    })
    @RequestMapping(value = "/summitOrder", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public Response<String> summitOrder(String id, Double bankPay, Double payPalPay, Double aliPay, Double wecharPay) {
        try {
            Response<String> response = new Response<String>();
            Order order = orderService.getById(id, null);

            if (null == order) {
                response.setSuccess(false);
                response.setMsg("订单不存在！");
                return response;
            }

            if (StringUtils.isNotBlank(order.getStatus())) {
                response.setSuccess(false);
                response.setMsg("订单异常！");
                return response;
            }
            if ((aliPay + wecharPay + bankPay + payPalPay) < order.getPrice()) {
                response.setSuccess(false);
                response.setMsg("支付金额不足！");
                return response;
            }
//            order.setPayType(payType);
            order.setAliPay(aliPay);
            order.setWecharPay(wecharPay);
            order.setStatus("0");
            order.setBankPay(bankPay);
            order.setPayPalPay(payPalPay);
            orderService.update(order);
            response.setSuccess(true);
            response.setMsg("下单成功");
            return response;

        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new ApplicationException(e.getMessage(), e);
        }
    }


    @ApiOperation(value = "提交分配(首次分配)", notes = "提交分配(首次分配)")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "订单ID", paramType = "query", required = true, dataType = "String"),
            @ApiImplicitParam(name = "profileIds", value = "导师ids(用分号';'隔开)", paramType = "query", required = true, dataType = "String")
    })
    @RequestMapping(value = "/assignAdvisoor", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public Response<String> assignAdvisoor(String id, String profileIds) {
        try {
            Response<String> response = new Response<String>();
            Order order = orderService.getById(id, null);

            if (null == order) {
                response.setSuccess(false);
                response.setMsg("订单不存在！");
                return response;
            }

            if (!"0".equals(order.getStatus())) {
                response.setSuccess(false);
                response.setMsg("订单异常！");
                return response;
            }

            if (StringUtils.isBlank(profileIds)) {
                response.setSuccess(false);
                response.setMsg("导师必选！");
                return response;
            }

            String[] profileIdAtt = profileIds.split(";");
            List<OrderProfile> orderProfiles = new ArrayList<OrderProfile>();
            for (int i = 0; i < profileIdAtt.length; i++) {
                Profile profile = profileService.getById(profileIdAtt[i]);
                if (null == profile) {
                    response.setSuccess(false);
                    response.setMsg("导师无效！");
                    return response;
                }
                OrderProfile orderProfile = new OrderProfile();
                orderProfile.setOrderId(order.getId());
                orderProfile.setProfileId(profile.getId());
                orderProfile.setAdvisorName(profile.getName());
                orderProfile.setCreateTime(new Date());
                orderProfile.setStatus("1");
                orderProfiles.add(orderProfile);
            }
            if (orderProfiles.size() > 0) {
                order.setOrderProfiles(orderProfiles);
            }
//            System.out.println("aaaaaaaaaaaaaaaaaaa: " + order.getProfiles().size());
            order.setStatus("1");
            orderService.update(order);
            response.setSuccess(true);
            response.setMsg("下单成功");
            return response;

        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new ApplicationException(e.getMessage(), e);
        }
    }


    @ApiOperation(value = "提交分配(修改分配)", notes = "提交分配(修改分配)")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "订单ID", paramType = "query", required = true, dataType = "String"),
            @ApiImplicitParam(name = "profileIds", value = "导师ids(用分号';'隔开)", paramType = "query", required = true, dataType = "String"),
            @ApiImplicitParam(name = "profileIdServiceTimes", value = "导师id+':'+服务时间(用分号';'隔开)", paramType = "query", required = true, dataType = "String")
    })
    @RequestMapping(value = "/editAssignAdvisoor", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public Response<String> editAssignAdvisoor(String id, String profileIds, String profileIdServiceTimes) {
        try {
            Response<String> response = new Response<String>();
            Order order = orderService.getById(id, null);

            if (null == order) {
                response.setSuccess(false);
                response.setMsg("订单不存在！");
                return response;
            }

            if (!"1".equals(order.getStatus())) {
                response.setSuccess(false);
                response.setMsg("订单异常！");
                return response;
            }

            if (StringUtils.isBlank(profileIds)) {
                response.setSuccess(false);
                response.setMsg("导师必选！");
                return response;
            }


//            List<OrderProfile> oldOrderProfiles = orderService.getOrderProfileById(id);


            String[] profileIdServiceTimesAtt = profileIdServiceTimes.split(";");
            List<OrderProfile> updateOrderProfiles = new ArrayList<OrderProfile>();
            for (int i = 0; i < profileIdServiceTimesAtt.length; i++) {
                String[] profileIdTime = profileIdServiceTimesAtt[i].split(":");
                OrderProfile orderProfile = new OrderProfile();
                orderProfile.setOrderId(order.getId());
                if (profileIdTime.length != 2) {
                    response.setSuccess(false);
                    response.setMsg("导师修改服务状态异常！");
                    return response;
                }
                Profile advisor = profileService.getById(profileIdTime[0]);
                if (null == advisor) {
                    response.setSuccess(false);
                    response.setMsg("导师修改服务状态异常！");
                    return response;
                }
                orderProfile.setProfileId(profileIdTime[0]);
                orderProfile.setServiceHour(profileIdTime[1]);
                orderProfile.setAdvisorName(advisor.getName());
                updateOrderProfiles.add(orderProfile);
            }
            order.setUpdateOrderProfiles(updateOrderProfiles);

            String[] profileIdAtt = profileIds.split(";");
            List<OrderProfile> orderProfiles = new ArrayList<OrderProfile>();
            for (int i = 0; i < profileIdAtt.length; i++) {
                Profile profile = profileService.getById(profileIdAtt[i]);


//                for (int j = 0; j < oldOrderProfiles.size(); j++) {
//                    if (profile.getId().equals(oldOrderProfiles.get(j).getProfileId())) {
//                        response.setSuccess(false);
//                        response.setMsg(profile.getName() + "导师已服务过！不能勾选。");
//                        return response;
//                    }
//                }
                if (null == profile) {
                    response.setSuccess(false);
                    response.setMsg("导师无效！");
                    return response;
                }
                OrderProfile orderProfile = new OrderProfile();
                orderProfile.setOrderId(order.getId());
                orderProfile.setProfileId(profile.getId());
                orderProfile.setCreateTime(new Date());
                orderProfile.setAdvisorName(profile.getName());
                orderProfile.setStatus("1");
                orderProfiles.add(orderProfile);
            }
            if (orderProfiles.size() > 0) {
                order.setOrderProfiles(orderProfiles);
//                orderService.
            }

//            System.out.println("aaaaaaaaaaaaaaaaaaa: " + order.getProfiles().size());
            order.setStatus("1");
            orderService.update(order);
            response.setSuccess(true);
            response.setMsg("下单成功");
            return response;

        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new ApplicationException(e.getMessage(), e);
        }
    }


    @ApiOperation(value = "助理端提交订单", notes = "助理端提交订单")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "订单ID", paramType = "query", required = true, dataType = "String"),
//            @ApiImplicitParam(name = "payType", value = "支付方式(1/支付宝;2/微信;3/银行卡;)", paramType = "query", required = true, dataType = "String"),
            @ApiImplicitParam(name = "bankPay", value = "银行卡支付金额", paramType = "query", required = false, dataType = "Double"),
            @ApiImplicitParam(name = "payPalPay", value = "paypal支付金额", paramType = "query", required = false, dataType = "Double"),
            @ApiImplicitParam(name = "aliPay", value = "支付宝支付金额", paramType = "query", required = false, dataType = "Double"),
            @ApiImplicitParam(name = "wecharPay", value = "微信支付金额", paramType = "query", required = false, dataType = "Double"),
            @ApiImplicitParam(name = "profileIds", value = "导师ids(用分号';'隔开)", paramType = "query", required = true, dataType = "String")
    })
    @RequestMapping(value = "/advisorSummitOrder", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public Response<String> advisorSummitOrder(String id, Double bankPay, Double payPalPay, Double aliPay, Double wecharPay, String profileIds) {
        try {
            Response<String> response = new Response<String>();
            Order order = orderService.getById(id, null);

            if (null == order) {
                response.setSuccess(false);
                response.setMsg("订单不存在！");
                return response;
            }

            if (StringUtils.isNotBlank(order.getStatus())) {
                response.setSuccess(false);
                response.setMsg("订单异常！");
                return response;
            }
            if ((aliPay + wecharPay + bankPay + payPalPay) < order.getPrice()) {
                response.setSuccess(false);
                response.setMsg("支付金额不足！");
                return response;
            }
//            order.setPayType(payType);
            order.setAliPay(aliPay);
            order.setWecharPay(wecharPay);
            order.setBankPay(bankPay);
            order.setPayPalPay(payPalPay);

            orderService.save(order);

            if (StringUtils.isNotBlank(order.getStatus())) {
                response.setSuccess(false);
                response.setMsg("订单异常！");
                return response;
            }

            if (StringUtils.isBlank(profileIds)) {
                response.setSuccess(false);
                response.setMsg("导师必选！");
                return response;
            }

            System.out.println("profileIdsprofileIdsprofileIdsprofileIds: " + profileIds);
            String[] profileIdAtt = profileIds.split(";");
            List<OrderProfile> orderProfiles = new ArrayList<OrderProfile>();
            for (int i = 0; i < profileIdAtt.length; i++) {
                Profile profile = profileService.getById(profileIdAtt[i]);
                System.out.println("profileprofileprofileprofile: " + profile.getNickname());
                if (null == profile) {
                    response.setSuccess(false);
                    response.setMsg("导师无效！");
                    return response;
                }
                OrderProfile orderProfile = new OrderProfile();
                orderProfile.setOrderId(order.getId());
                orderProfile.setProfileId(profile.getId());
                orderProfile.setAdvisorName(profile.getName());
                orderProfile.setCreateTime(new Date());
                orderProfile.setStatus("1");
                orderProfiles.add(orderProfile);
            }
            if (orderProfiles.size() > 0) {
                order.setOrderProfiles(orderProfiles);
            }
//            System.out.println("aaaaaaaaaaaaaaaaaaa: " + order.getProfiles().size());
            order.setStatus("1");
            orderService.update(order);
            response.setSuccess(true);
            response.setMsg("下单成功");
            return response;

        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new ApplicationException(e.getMessage(), e);
        }
    }


    @ApiOperation(value = "根据orderId查订单详情", notes = "根据orderId查订单详情")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "订单ID(已分配进入修改分配页面)", paramType = "query", required = true, dataType = "String")
    })
    @RequestMapping(value = "/getById", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public Response<Order> getById(String id) {
        try {
            Order order = orderService.getById(id, "1");
            Response<Order> response = new Response<Order>();
            response.setSuccess(true);
            response.setMsg("成功");
            response.setResult(order);
            return response;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new ApplicationException(e.getMessage(), e);
        }
    }


    @ApiOperation(value = "根据orderId查订单分配记录", notes = "根据orderId查订单分配记录")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "orderId", value = "订单ID(已分配进入修改分配页面)", paramType = "query", required = true, dataType = "String")
    })
    @RequestMapping(value = "/getAssignByOrderId", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public Response<List<OrderProfile>> getAssignByOrderId(String orderId) {
        try {
            List<OrderProfile> orderProfiles = orderService.getOrderProfileById(orderId);
            Response<List<OrderProfile>> response = new Response<List<OrderProfile>>();
            response.setSuccess(true);
            response.setMsg("成功");
            response.setResult(orderProfiles);
            return response;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new ApplicationException(e.getMessage(), e);
        }
    }





    @RequestMapping(value = "/countOrderByParam", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public Response<List<Order>> countOrderByParam(String startTime, String endTime, String countType, String sellerId) {
        try {
            System.out.println("countOrderByParamcountOrderByParamcountOrderByParam");
            Response<List<Order>> response = new Response<List<Order>>();
            List<Order> orders = new ArrayList<Order>();
//            if("day".equals(countType)){
                orders = orderService.countOrderByParam(startTime, endTime, countType, sellerId);
//            }else if("week".equals(countType)){
//
//            }else if("month".equals(countType)){
//
//            }else if("se".equals(countType)){
//
//            }

            response.setSuccess(true);
            response.setMsg("成功");
            response.setResult(orders);
            return response;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new ApplicationException(e.getMessage(), e);
        }
    }



}
