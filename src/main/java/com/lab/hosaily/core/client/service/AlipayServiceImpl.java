package com.lab.hosaily.core.client.service;

import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.domain.AlipayTradeWapPayModel;
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
            String appId = "2016082100306752";
            String privateKey = "MIIEvAIBADANBgkqhkiG9w0BAQEFAASCBKYwggSiAgEAAoIBAQCFk732mgoE2CQu1UJCAbwGE+hf0Bm1VtEkuyDtqyuATeq+Sk22ZKY1WctUN+DYRkgf2I9Fy8nI4rKUbWtcngr7+DZeXtUOKvPLVhlOW+JQJeJNJzeM4T4Yg3AIyOOf1DqMTSCHgKXpQp64c9v6hEY7v4tdupJSYCXaRZXRt34WOPPIQEh6e050u/jNWISraps60zzYaeH80cyIcsEWnvygrk9bvX0lUL49DOTYbC34I1oj53P6bhJdkvIFS6OejI2k/JUGHKXj3Y0SJCesz5JPjJtK68KIFshvqV5ozQVYkDRebm+IIdDBcQfOIeVu8U7teVGDiWAWb2CKTzXqXw/NAgMBAAECggEAazq46jeqB02Dbl0f/WDrEW31wsSJfrsc1YGq41/fYfYqlWuMivitPHLC+O6KftOcnoP4L61yZOMnPGPCJe1xH+iXrJYwLllosDiDxBWazYA79Heg7mT6ti79RvANWobCIVLg5CXwChrC04uecbKxttEDDdDyGVK50EjABkRo4WjZzPzURtEaGjOziqNYmRP7kHGbDfyRjOQrt4wr162AY3G6gWXT/jG7Sl/op7uz1tpPVNDb2GFDqW+KzHZChi7gK3s9dcEW33L8OkL80TclbkMlH116WdGtkILGKGRWxSaWL7RHwp67DamqtfB8Da1F+Zo5fM2RCGHVYU1TiFNuQQKBgQC7nKW+Vyb0fxIZdOH2Wg4rrIgV0ykt7XGWtrb3B2D5ociJ6VYViVMIl5MdLbqNxLYlW6Xk6z1QwOoloDVAeL0J98eMYqYCyDbhqP7Q/tJXhIFmdfP3YRWJA6Z7Q/ety4jG9g1PHrL7TD7GiVDJC4E3Y9sOHXpaBpFnKhfuedsliQKBgQC2RL0DjlhxsInHEAYJ0w51vkk6Waj+Xc6qVP/KKFgfT6mqsCvWkskywCetBbclWez1BcLnSxNii+UgOelD1nV45stR/lGQn9Tl6dN3xHXkue6gYys6f94WzLdnGCF1wHI9O6OgvkOqD5m+PT1SKCngmKI6hx9zu2YgNe/NorfLJQKBgARZ7b4sZ0rkZ5cPju4XyJxatA1NiHb4Oto0AqpYEXe4uN35+3UkajiYy2Cx3tHK7i6PoZldm150GvokbRfBtSC2DnrDDvahjiRNtxtBzb8Z46ZstevW5Jj47LOPL/9n6RQ0QLrFC4GokwwnwyfmjGiseDgrltrfrB+oakWtrdeBAoGAeQQwnwDO1kVJZSLNb45zRlfeFODc5G5yPgOLhbS9taRV2V9469GvxoNwlF98F0+alaCKpLjjGQYbTgdFSmZEvs415q29iBGm8DQM1LNbN08AEftmA0RMt8SabNfHt3/cGa3UWXCaSnprMLVGVpsWGuocpFF834FoMDC/lD/aWgUCgYB6MSwnqw5/Q2NuKG9pWmDwsRQl2CuDtGZ6MqxSkoOfiR0DFa8IgsrIOnsVgjbBa2eJ73xTzYGU3gRWs4BsAsy9FBVuDdNFaioIu8FENInVPuvd/5MMVh1PrkQ2l7xz4jXjLqQZbOJOwSkfctkfhaywgOOlJI8x3IQZHoNNZ0bFEQ==";
            String publicKey = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEA8qHy88srmdlrQl37tSPZmIpz0uJ5fOfbVds/cMktknRNqiPUc2khQeVba2t/JC8Xak0J/iTqWHPkiRpv5CTBTduCwOPb7UYz7uBdrKept5rg5iyDoxxY610VRt+li2gaT0RuVPQyrvIju5AUqypZoyaiWCnMFQvFGYqWJ+Fmwfjkzy9ZGUoAIXQuH3mjxWqf6vIE/+AEqApKFOuI8w0NR5CPa8vDv787JVFILQ1MBaLnQtG1t4TDTUV9m1tn7TwJ5OPAsf26ERmljDLP3XgF+AGesmOhmsdl7hy8vxoIRzGbLQxgRlhjSu8YRXeqh9deIiJ73fSo50it9GjnMbd2uQIDAQAB";
            String signType = "RSA2";
            String format = "JSON";
            String charset = "UTF-8";
            String api = "https://openapi.alipaydev.com/gateway.do";
            String returnUrl = "http://9435845f.ngrok.io/api/1.0/alipay/return";
            String notifyUrl = "http://9435845f.ngrok.io/api/1.0/alipay/callback";

            //创建支付记录
            Payment payment = new Payment();
            payment.setPurchaseId(purchaseId);
            payment.setState(PayState.APPLY);
            payment.setPayTime(new Date());
            payment.setPrice(totalFee);
            payment.setType(PayType.ALIPAY_MERCHANT);
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
            AlipayTradeWapPayModel model = new AlipayTradeWapPayModel();
            model.setSubject(alipay.getSubject());
            model.setOutTradeNo(payment.getId());
            model.setTotalAmount(totalFee.toString());
            model.setProductCode(alipay.getProductCode());
            request.setBizModel(model);
            String form = client.pageExecute(request).getBody();

            //更新支付记录
            payment.setState(PayState.UNCONFIRMED);
            paymentDao.saveOrUpdate(payment);

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
            String publicKey = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEA8qHy88srmdlrQl37tSPZmIpz0uJ5fOfbVds/cMktknRNqiPUc2khQeVba2t/JC8Xak0J/iTqWHPkiRpv5CTBTduCwOPb7UYz7uBdrKept5rg5iyDoxxY610VRt+li2gaT0RuVPQyrvIju5AUqypZoyaiWCnMFQvFGYqWJ+Fmwfjkzy9ZGUoAIXQuH3mjxWqf6vIE/+AEqApKFOuI8w0NR5CPa8vDv787JVFILQ1MBaLnQtG1t4TDTUV9m1tn7TwJ5OPAsf26ERmljDLP3XgF+AGesmOhmsdl7hy8vxoIRzGbLQxgRlhjSu8YRXeqh9deIiJ73fSo50it9GjnMbd2uQIDAQAB";
            String charset = "UTF-8";
            String signType = "RSA2";

            //验签
            boolean verified = AlipaySignature.rsaCheckV1(params, publicKey, charset, signType);
            if(verified){
                String status = params.get("trade_status");
                Double totalFee = Double.valueOf(params.get("total_amount"));
                String appid = params.get("app_id");
                String tradeNo = params.get("trade_no");

                //简单验证
                if(alipay != null
                        && alipay.getTotalAmount().equals(totalFee)
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

            return "fail";
        }catch(Exception e){
            logger.error(e.getMessage(), e);
            throw new ServiceException(e.getMessage(), e);
        }
    }
}
