package com.lab.hosaily.core.client.service;

import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.internal.util.AlipaySignature;
import com.alipay.api.request.AlipayTradeWapPayRequest;
import com.lab.hosaily.core.client.consts.AlipayStatus;
import com.lab.hosaily.core.client.consts.PayState;
import com.lab.hosaily.core.client.consts.PayType;
import com.lab.hosaily.core.client.dao.AlipayDao;
import com.lab.hosaily.core.client.dao.PaymentDao;
import com.lab.hosaily.core.client.dao.PurchaseDao;
import com.lab.hosaily.core.client.entity.Alipay;
import com.lab.hosaily.core.client.entity.Payment;
import com.lab.hosaily.core.client.entity.Purchase;
import com.lab.hosaily.core.client.entity.WechatMerchantPay;
import com.rab.babylon.commons.security.exception.ServiceException;
import com.rab.babylon.commons.utils.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
@Transactional(readOnly = true)
public class AlipayServiceImpl implements AlipayService{

    private static Logger logger = LoggerFactory.getLogger(AlipayServiceImpl.class);

    @Autowired
    private PaymentDao paymentDao;

    @Autowired
    private AlipayDao alipayDao;

    @Autowired
    private PurchaseDao purchaseDao;

    /**
     * 支付
     */
    @Override
    @Transactional(readOnly = false)
    public String pay(String purchaseId, Double totalFee){
        try{
            Assert.notNull(totalFee, "支付金额不能为空");

            //TODO 根据企业ID查询支付账号信息
            String appId = "";
            String privateKey = "";
            String publicKey = "";
            String signType = "";
            String format = "json";
            String charset = "utf-8";
            String api = "";
            String returnUrl = "";
            String notifyUrl = "";

            //创建支付记录
            Payment payment = new Payment();
            payment.setPurchaseId(purchaseId);
            payment.setState(PayState.APPLY);
            payment.setPayTime(new Date());
            payment.setPrice(totalFee);
            payment.setType(PayType.WECHAT_MERCHANT);
            paymentDao.saveOrUpdate(payment);

            //创建支付宝支付记录
            Alipay alipay = new Alipay();
            alipay.setAppId(appId);
            alipay.setReturnUrl(returnUrl);
            alipay.setNotifyUrl(notifyUrl);
            alipay.setSubject("永恒情书服务支付");
            alipay.setSignType(signType);
            alipay.setOutTradeNo(payment.getId());
            alipay.setTotalAmount(totalFee);
            alipay.setProductCode("QUICK_WAP_WAY");
            alipay.setStatus(AlipayStatus.WAIT_BUYER_PAY);
            alipayDao.saveOrUpdate(alipay);

            //创建请求客户端
            AlipayClient client = new DefaultAlipayClient(api, appId, privateKey, format, charset, publicKey, signType);
            //创建请求
            AlipayTradeWapPayRequest request = new AlipayTradeWapPayRequest();
            request.setReturnUrl(returnUrl);
            request.setNotifyUrl(notifyUrl);
            //业务参数
            Map<String, String> params = new HashMap<String, String>();
            params.put("subject", alipay.getSubject());
            params.put("outTradeNo", payment.getId());
            params.put("total_amount", totalFee.toString());
            params.put("product_code", alipay.getProductCode());
            request.setBizContent(ObjectUtils.serialize(params));
            //SDK生成表单
            String form = client.pageExecute(request).getBody();

            return form;
        }catch(Exception e){
            logger.error(e.getMessage(), e);
            throw new ServiceException(e.getMessage(), e);
        }
    }

    /**
     * 回调
     */
    @Override
    @Transactional(readOnly = false)
    public String callback(Map<String, String> params){
        try{
            String outTradeNo = params.get("out_trade_no");
            //支付记录ID
            Payment payment = paymentDao.getById(outTradeNo);
            //支付宝支付记录
            Alipay alipay = alipayDao.getByOutTradeNo(outTradeNo);
            //购买记录
            Purchase purchase = purchaseDao.getById(payment.getPurchaseId());

            //TODO 根据企业ID查询支付账号信息
            String appId = "";
            String privateKey = "";
            String publicKey = "";
            String signType = "";
            String format = "json";
            String charset = "utf-8";
            String api = "";
            String returnUrl = "";
            String notifyUrl = "";

            //验签
            boolean verified = AlipaySignature.rsaCheckV2(params, publicKey, charset);
            if(verified){
                String status = params.get("trade_status");
                Double totalFee = Double.valueOf(params.get("total_amount"));
                String appid = params.get("app_id");
                String tradeNo = params.get("trade_no");

                //简单验证
                if(alipay != null
                        && alipay.getTotalAmount() == totalFee
                        && alipay.getAppId().equals(appid)){

                    //更新支付记录
                    if(payment.getState().equals(PayState.UNCONFIRMED)){
                        payment.setState(PayState.PAID);
                        payment.setAffirmTime(new Date());
                        paymentDao.saveOrUpdate(payment);
                    }
                    //更新支付宝支付记录
                    alipay.setStatus(AlipayStatus.valueOf(status));
                    alipay.setTradeNo(tradeNo);
                    alipayDao.saveOrUpdate(alipay);

                    return "success";
                }
            }

            return "FAIL";
        }catch(Exception e){
            logger.error(e.getMessage(), e);
            throw new ServiceException(e.getMessage(), e);
        }
    }
}
