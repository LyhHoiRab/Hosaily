package com.lab.hosaily.core.client.service;

public interface PaymentService{

    /**
     * 根据购买记录统计已成功付款金额
     */
    long priceWechatMerchantPayByPurchaseId(String purchaseId);
}
