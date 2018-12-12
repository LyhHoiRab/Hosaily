package com.lab.hosaily.core.client.service;

import java.util.Map;

public interface WechatMerchantPayService{

    /**
     * 客户预支付
     */
    Map<String, String> prepay(String purchaseId, Double totalFee, String code);

    /**
     * 客户预支付
     */
    Map<String, String> xcxPrepay(String projectId, String accountId, Double totalFee, String code);

    /**
     * 回调
     */
    String callback(String xml);

    /**
     * 回调
     */
    String xcxCallback(String xml);

    /**
     * 查询JSAPI支付参数
     */
    Map<String, String> getJsapi(String outTradeNo);


    Map<String, String> xcxCoursePrepay(String courseId, String accountId, Double totalFee, String code);

    String xcxCourseCallback(String xml);

    Map<String, String> xcxVipPrepay(String accountId, Double totalFee, String code);

    String xcxVipCallback(String xml);





    /**
     * 情感测试小程序客户VIP预支付
     */
    Map<String, String> xcxTestVipPrepay(String accountId, Double totalFee, String code);



    /**
     * 感测试小程序客户VIP预支付回调
     */
    String xcxTestVipCallback(String xml);


}
