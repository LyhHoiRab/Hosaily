package com.lab.hosaily.core.client.service;

import java.util.Map;

public interface AlipayService{

    /**
     * 支付
     */
    String pay(String purchaseId, Double totalFee);

    /**
     * 回调
     */
    String callback(Map<String, String> params);
}
