package com.lab.hosaily.core.client.service;

import com.lab.hosaily.commons.response.wechat.WechatMerchantPayCallbackResponse;
import com.lab.hosaily.commons.response.wechat.WechatMerchantPayResponse;
import com.lab.hosaily.commons.utils.WechatMerchantPayUtils;
import com.lab.hosaily.core.account.dao.WeChatAccountDao;
import com.lab.hosaily.core.account.entity.WeChatAccount;
import com.lab.hosaily.core.client.consts.PayState;
import com.lab.hosaily.core.client.consts.PayType;
import com.lab.hosaily.core.client.dao.PaymentDao;
import com.lab.hosaily.core.client.dao.WechatMerchantPayDao;
import com.lab.hosaily.core.client.entity.Payment;
import com.lab.hosaily.core.client.entity.WechatMerchantPay;
import com.rab.babylon.commons.security.exception.ServiceException;
import com.rab.babylon.commons.utils.UUIDGenerator;
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
public class WechatMerchantPayServiceImpl implements WechatMerchantPayService{

    private static Logger logger = LoggerFactory.getLogger(WechatMerchantPayServiceImpl.class);

    @Autowired
    private WechatMerchantPayDao wechatMerchantPayDao;

    @Autowired
    private PaymentDao paymentDao;

    @Autowired
    private WeChatAccountDao weChatAccountDao;

    /**
     * 预支付
     */
    @Override
    @Transactional(readOnly = false)
    public Map<String, String> prepay(String organizationId, String purchaseId, String accountId, Double totalFee){
        try{
            Assert.hasText(organizationId, "企业ID不能为空");
            Assert.hasText(accountId, "账户ID不能为空");
            Assert.notNull(totalFee, "支付金额不能为空");

            //TODO 根据企业ID查询支付账号信息
            String appId = "appid";
            String mchId = "mch_id";
            String appKey = "app_key";
            String notifyUrl = "notify_url";

            //创建支付记录
            Payment payment = new Payment();
            payment.setPurchaseId(purchaseId);
            payment.setState(PayState.APPLY);
            payment.setPayTime(new Date());
            payment.setPrice(totalFee);
            payment.setType(PayType.WECHAT_MERCHANT);
            paymentDao.saveOrUpdate(payment);

            //查询微信账户
            WeChatAccount account = weChatAccountDao.getByAccountIdAndAppId(accountId, appId);
            //创建微信商户支付记录
            WechatMerchantPay wechatMerchantPay = new WechatMerchantPay();
            wechatMerchantPay.setAppId(appId);
            wechatMerchantPay.setMchId(mchId);
            wechatMerchantPay.setKey(appKey);
            wechatMerchantPay.setOpenId(account.getOpenId());
            wechatMerchantPay.setNonceStr(UUIDGenerator.by32());
            wechatMerchantPay.setBody("永恒情书服务支付");
            wechatMerchantPay.setOutTradeNo(payment.getId());
            wechatMerchantPay.setTradeType("JSAPI");
            wechatMerchantPay.setNotifyUrl(notifyUrl);
            wechatMerchantPay.setTotalFee(totalFee.intValue() * 100);
            wechatMerchantPay.setOutTradeNo(payment.getId());

            //提交预支付
            WechatMerchantPayResponse response = WechatMerchantPayUtils.prepay(wechatMerchantPay);
            if(response.getReturnCode().equalsIgnoreCase("SUCCESS")){
                if(response.getResultCode().equalsIgnoreCase("SUCCESS")){
                    payment.setState(PayState.UNCONFIRMED);
                    wechatMerchantPay.setPrepayId(response.getPrepayId());
                }else{
                    payment.setState(PayState.FAIL);
                    wechatMerchantPay.setMsg(response.getErrCodeDes());
                }
            }else{
                payment.setState(PayState.FAIL);
                wechatMerchantPay.setMsg(response.getReturnMsg());
            }

            //更新支付状态
            paymentDao.saveOrUpdate(payment);
            //保存微信商户支付记录
            wechatMerchantPayDao.saveOrUpdate(wechatMerchantPay);

            if(StringUtils.isBlank(wechatMerchantPay.getPrepayId())){
                //生成网页支付参数
                Map<String, String> params = new HashMap<String, String>();
                params.put("appId", wechatMerchantPay.getAppId());
                params.put("timeStamp", String.valueOf(System.currentTimeMillis() / 1000));
                params.put("nonceStr", wechatMerchantPay.getNonceStr());
                params.put("package", "prepay_id=" + wechatMerchantPay.getPrepayId());
                params.put("signType", "MD5");
                params.put("paySign", WechatMerchantPayUtils.sign(params, wechatMerchantPay.getKey()));

                return params;
            }

            throw new ServiceException(String.format("微信支付失败:%s", response.getReturnMsg()));
        }catch(Exception e){
            logger.error(e.getMessage(), e);
            throw new ServiceException(e.getMessage(), e);
        }
    }

    /**
     * 回调
     */
    @Override
    @Transactional(readOnly = true)
    public String callback(String xml){
        try{
            Assert.hasText(xml, "微信商户支付回调XML信息不能为空");

            //结果回复
            Map<String, String> params = new HashMap<String, String>();

            WechatMerchantPayCallbackResponse response = WechatMerchantPayUtils.toWechatMerchantPayCallbackResponse(xml);
            if(response != null && response.getReturnCode().equalsIgnoreCase("SUCCESS")){
                if(response.getResultCode().equalsIgnoreCase("SUCCESS")){
                    //TODO 验证签名

                    String paymentId = response.getOutTradeNo();
                    //支付记录
                    Payment payment = paymentDao.getById(paymentId);
                    //微信商户支付记录
                    WechatMerchantPay pay = wechatMerchantPayDao.getByOutTradeNo(paymentId);

                    //简单验证
                    if(payment != null
                            && pay != null
                            && pay.getTotalFee() == response.getTotalFee()
                            && pay.getAppId().equals(response.getAppId())
                            && pay.getMchId().equals(response.getMchId())
                            && pay.getOpenId().equals(response.getOpenId())){

                        if(payment.getState().equals(PayState.UNCONFIRMED)){
                            payment.setState(PayState.PAID);
                            payment.setAffirmTime(new Date());
                            paymentDao.saveOrUpdate(payment);
                        }

                        params.put("return_code", "SUCCESS");
                        params.put("return_msg", "OK");
                    }
                }else{
                    params.put("return_code", "FAIL");
                    params.put("return_msg", response.getErrCodeDes());
                }
            }else{
                params.put("return_code", "FAIL");
                params.put("return_msg", response.getReturnMsg());
            }

            return WechatMerchantPayUtils.toXml(params);
        }catch(Exception e){
            logger.error(e.getMessage(), e);
            throw new ServiceException(e.getMessage(), e);
        }
    }
}
