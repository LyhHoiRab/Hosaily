package com.lab.hosaily.core.pay.service;

import java.util.Map;

public interface PayService{

    Map<String, String> wxPay(String code, Double totalFee) throws Exception;

    String alipay(Double totalFee) throws Exception;
}
