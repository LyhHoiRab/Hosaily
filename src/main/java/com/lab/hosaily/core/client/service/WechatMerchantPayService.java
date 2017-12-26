package com.lab.hosaily.core.client.service;

import java.util.Map;

public interface WechatMerchantPayService{

    /**
     * 客户预支付
     */
    Map<String, String> prepay(String organizationId, String purchaseId, String accountId, Double totalFee);

    /**
     * 回调
     */
    String callback(String xml);
}
