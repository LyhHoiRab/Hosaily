package com.lab.hosaily.core.pay.service;

import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.domain.AlipayTradeWapPayModel;
import com.alipay.api.request.AlipayTradeWapPayRequest;
import com.lab.hosaily.commons.response.wechat.AccessTokenResponse;
import com.lab.hosaily.commons.response.wechat.WechatMerchantPayResponse;
import com.lab.hosaily.commons.utils.WeChatUtils;
import com.lab.hosaily.commons.utils.WechatMerchantPayUtils;
import com.lab.hosaily.core.client.consts.AlipayStatus;
import com.lab.hosaily.core.client.entity.Alipay;
import com.lab.hosaily.core.client.entity.WechatMerchantPay;
import com.rab.babylon.commons.security.exception.ServiceException;
import com.rab.babylon.commons.utils.UUIDGenerator;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.wah.doraemon.utils.IDGenerator;

import java.util.Map;

@Service
@Transactional(readOnly = true)
public class PayServiceImpl implements PayService{

    @Override
    public Map<String, String> wxPay(String code, Double totalFee) throws Exception{
        String appId = "wx0d7797805ed0d9b3";
        String appSecret = "e501dada14c76fb852bc7efa1a5543c7";
        String mchId = "1492968172";
        String appKey = "7W80cW9zmjGTnodn4BEZwVvkU8BtJar8";
        String notifyUrl = "http://www.klpua.com/api/1.0/pay/wx/callback";

        AccessTokenResponse token = WeChatUtils.getAccessToken(code, appId, appSecret);
        String openId = token.getOpenId();

        if (StringUtils.isBlank(openId)){
            throw new ServiceException("未知用户");
        }

        //创建微信商户支付记录
        WechatMerchantPay wechatMerchantPay = new WechatMerchantPay();
        wechatMerchantPay.setAppId(appId);
        wechatMerchantPay.setMchId(mchId);
        wechatMerchantPay.setKey(appKey);
        wechatMerchantPay.setOpenId(openId);
        wechatMerchantPay.setNonceStr(UUIDGenerator.by32());
        wechatMerchantPay.setBody("永恒情书服务支付");
        wechatMerchantPay.setOutTradeNo(IDGenerator.uuid32());
        wechatMerchantPay.setTradeType("JSAPI");
        wechatMerchantPay.setNotifyUrl(notifyUrl);
        wechatMerchantPay.setSignType("MD5");
        wechatMerchantPay.setTotalFee(new Double(totalFee * 100).intValue());

        //提交预支付
        WechatMerchantPayResponse response = WechatMerchantPayUtils.prepay(wechatMerchantPay);
        if(response.getReturnCode().equalsIgnoreCase("SUCCESS")){
            if(response.getResultCode().equalsIgnoreCase("SUCCESS")){
                wechatMerchantPay.setPrepayId(response.getPrepayId());
            }else{
                throw new ServiceException(response.getReturnMsg());
            }
        }else{
            throw new ServiceException(response.getReturnMsg());
        }

        if(!StringUtils.isBlank(wechatMerchantPay.getPrepayId())){
            return WechatMerchantPayUtils.getJsapiParams(wechatMerchantPay);
        }

        throw new ServiceException(String.format("微信支付失败:%s", response.getReturnMsg()));
    }

    @Override
    public String alipay(Double totalFee) throws Exception{
        //永恒情书
        String appId = "2017102309477484";
        String privateKey = "MIIEvAIBADANBgkqhkiG9w0BAQEFAASCBKYwggSiAgEAAoIBAQCFk732mgoE2CQu1UJCAbwGE+hf0Bm1VtEkuyDtqyuATeq+Sk22ZKY1WctUN+DYRkgf2I9Fy8nI4rKUbWtcngr7+DZeXtUOKvPLVhlOW+JQJeJNJzeM4T4Yg3AIyOOf1DqMTSCHgKXpQp64c9v6hEY7v4tdupJSYCXaRZXRt34WOPPIQEh6e050u/jNWISraps60zzYaeH80cyIcsEWnvygrk9bvX0lUL49DOTYbC34I1oj53P6bhJdkvIFS6OejI2k/JUGHKXj3Y0SJCesz5JPjJtK68KIFshvqV5ozQVYkDRebm+IIdDBcQfOIeVu8U7teVGDiWAWb2CKTzXqXw/NAgMBAAECggEAazq46jeqB02Dbl0f/WDrEW31wsSJfrsc1YGq41/fYfYqlWuMivitPHLC+O6KftOcnoP4L61yZOMnPGPCJe1xH+iXrJYwLllosDiDxBWazYA79Heg7mT6ti79RvANWobCIVLg5CXwChrC04uecbKxttEDDdDyGVK50EjABkRo4WjZzPzURtEaGjOziqNYmRP7kHGbDfyRjOQrt4wr162AY3G6gWXT/jG7Sl/op7uz1tpPVNDb2GFDqW+KzHZChi7gK3s9dcEW33L8OkL80TclbkMlH116WdGtkILGKGRWxSaWL7RHwp67DamqtfB8Da1F+Zo5fM2RCGHVYU1TiFNuQQKBgQC7nKW+Vyb0fxIZdOH2Wg4rrIgV0ykt7XGWtrb3B2D5ociJ6VYViVMIl5MdLbqNxLYlW6Xk6z1QwOoloDVAeL0J98eMYqYCyDbhqP7Q/tJXhIFmdfP3YRWJA6Z7Q/ety4jG9g1PHrL7TD7GiVDJC4E3Y9sOHXpaBpFnKhfuedsliQKBgQC2RL0DjlhxsInHEAYJ0w51vkk6Waj+Xc6qVP/KKFgfT6mqsCvWkskywCetBbclWez1BcLnSxNii+UgOelD1nV45stR/lGQn9Tl6dN3xHXkue6gYys6f94WzLdnGCF1wHI9O6OgvkOqD5m+PT1SKCngmKI6hx9zu2YgNe/NorfLJQKBgARZ7b4sZ0rkZ5cPju4XyJxatA1NiHb4Oto0AqpYEXe4uN35+3UkajiYy2Cx3tHK7i6PoZldm150GvokbRfBtSC2DnrDDvahjiRNtxtBzb8Z46ZstevW5Jj47LOPL/9n6RQ0QLrFC4GokwwnwyfmjGiseDgrltrfrB+oakWtrdeBAoGAeQQwnwDO1kVJZSLNb45zRlfeFODc5G5yPgOLhbS9taRV2V9469GvxoNwlF98F0+alaCKpLjjGQYbTgdFSmZEvs415q29iBGm8DQM1LNbN08AEftmA0RMt8SabNfHt3/cGa3UWXCaSnprMLVGVpsWGuocpFF834FoMDC/lD/aWgUCgYB6MSwnqw5/Q2NuKG9pWmDwsRQl2CuDtGZ6MqxSkoOfiR0DFa8IgsrIOnsVgjbBa2eJ73xTzYGU3gRWs4BsAsy9FBVuDdNFaioIu8FENInVPuvd/5MMVh1PrkQ2l7xz4jXjLqQZbOJOwSkfctkfhaywgOOlJI8x3IQZHoNNZ0bFEQ==";
        String publicKey = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAh8aEZ7mjfCUk1bgau+4OBuzlYwUJQjqsycOUE012FnWG6iJXsmz1G/FY10sj56jhkvfljBWA5IdSQiaorK64WErdiPWnAfSVeFISs5I9ojDYKrD7QlIdKsTpMUNgqQa9DctTWeMdU80uRp+cICNs2VD/8FOQO1USLn4PwjPR5yO7TG7h/t7g7I5gQuafIalnapT5D1KwPvJtokDSt/5RC9SrwaH/mjH7M3TBavwX9zlhQBfDG6H00GRnXLN0wKdW9AGJGON3qqcQ2gQkj4lYyowfKfsdv8vrvVq4h2PwOm9ttERp1erO77DdVM/B0iDVcD/J2Yawun5vAqm2VDSSvwIDAQAB";
        String signType = "RSA2";
        String format = "JSON";
        String charset = "UTF-8";
        String api = "https://openapi.alipay.com/gateway.do";
        String returnUrl = "http://www.klpua.com/api/1.0/pay/ali/return";
        String notifyUrl = "http://www.klpua.com/api/1.0/pay/ali/callback";

        //创建支付宝支付记录
        Alipay alipay = new Alipay();
        alipay.setAppId(appId);
        alipay.setReturnUrl(returnUrl);
        alipay.setNotifyUrl(notifyUrl);
        alipay.setSubject("永恒情书服务支付");
        alipay.setSignType(signType);
        alipay.setOutTradeNo(IDGenerator.uuid32());
        alipay.setTotalAmount(totalFee);
        alipay.setProductCode("QUICK_WAP_WAY");
        alipay.setStatus(AlipayStatus.WAIT_BUYER_PAY);

        //创建请求客户端
        AlipayClient client = new DefaultAlipayClient(api, appId, privateKey, format, charset, publicKey, signType);
        //创建请求
        AlipayTradeWapPayRequest request = new AlipayTradeWapPayRequest();
        request.setReturnUrl(returnUrl);
        request.setNotifyUrl(notifyUrl);
        //业务参数
        AlipayTradeWapPayModel model = new AlipayTradeWapPayModel();
        model.setSubject(alipay.getSubject());
        model.setOutTradeNo(alipay.getOutTradeNo());
        model.setTotalAmount(totalFee.toString());
        model.setProductCode(alipay.getProductCode());
        request.setBizModel(model);
        return client.pageExecute(request).getBody();
    }
}
