package com.lab.hosaily.core.client.service;

import com.lab.hosaily.commons.response.wechat.AccessTokenResponse;
import com.lab.hosaily.commons.response.wechat.WechatMerchantPayCallbackResponse;
import com.lab.hosaily.commons.response.wechat.WechatMerchantPayResponse;
import com.lab.hosaily.commons.utils.ProjectStatus;
import com.lab.hosaily.commons.utils.WeChatUtils;
import com.lab.hosaily.commons.utils.WechatMerchantPayUtils;
import com.lab.hosaily.core.account.dao.AccountDao;
import com.lab.hosaily.core.account.dao.WeChatAccountDao;
import com.lab.hosaily.core.account.dao.XcxAccountDao;
import com.lab.hosaily.core.account.entity.WeChatAccount;
import com.lab.hosaily.core.account.entity.XcxAccount;
import com.lab.hosaily.core.client.consts.PayState;
import com.lab.hosaily.core.client.consts.PayType;
import com.lab.hosaily.core.client.consts.PurchaseState;
import com.lab.hosaily.core.client.dao.AgreementDao;
import com.lab.hosaily.core.client.dao.PaymentDao;
import com.lab.hosaily.core.client.dao.PurchaseDao;
import com.lab.hosaily.core.client.dao.WechatMerchantPayDao;
import com.lab.hosaily.core.client.entity.Agreement;
import com.lab.hosaily.core.client.entity.Payment;
import com.lab.hosaily.core.client.entity.Purchase;
import com.lab.hosaily.core.client.entity.WechatMerchantPay;
import com.lab.hosaily.core.course.dao.AccountLevelDao;
import com.lab.hosaily.core.course.dao.AccountProjectDao;
import com.lab.hosaily.core.course.dao.CourseGroupDao;
import com.lab.hosaily.core.course.entity.AccountLevel;
import com.lab.hosaily.core.course.entity.AccountProject;
import com.lab.hosaily.core.course.entity.Course;
import com.lab.hosaily.core.sell.dao.AccountCourseDao;
import com.lab.hosaily.core.sell.entity.AccountCourse;
import com.rab.babylon.commons.security.exception.ServiceException;
import com.rab.babylon.commons.utils.UUIDGenerator;
import com.rab.babylon.core.account.entity.Account;
import com.rab.babylon.core.consts.entity.UsingState;
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
public class WechatMerchantPayServiceImpl implements WechatMerchantPayService {

    private static Logger logger = LoggerFactory.getLogger(WechatMerchantPayServiceImpl.class);

    @Autowired
    private WechatMerchantPayDao wechatMerchantPayDao;

    @Autowired
    private PaymentDao paymentDao;

    @Autowired
    private WeChatAccountDao weChatAccountDao;

    @Autowired
    private PurchaseDao purchaseDao;

    @Autowired
    private AccountProjectDao accountProjectDao;

    @Autowired
    private XcxAccountDao xcxAccountDao;

    @Autowired
    private AccountDao accountDao;

    @Autowired
    private AccountCourseDao accountCourseDao;

    @Autowired
    private AgreementDao agreementDao;

    @Autowired
    private CourseGroupDao courseGroupDao;

    @Autowired
    private AccountLevelDao accountLevelDao;

    /**
     * 预支付
     */
    @Override
    @Transactional(readOnly = false)
    public Map<String, String> prepay(String purchaseId, Double totalFee, String code) {
        try {
            Assert.notNull(totalFee, "支付金额不能为空");

            //TODO 根据企业ID查询支付账号信息
            String appId = "wx0d7797805ed0d9b3";
            String appSecret = "e501dada14c76fb852bc7efa1a5543c7";
            String mchId = "1492968172";
            String appKey = "7W80cW9zmjGTnodn4BEZwVvkU8BtJar8";
            String notifyUrl = "http://www.klpua.com/api/1.0/wechatMerchantPay/callback";

            //购买记录
            Purchase purchase = purchaseDao.getById(purchaseId);

            //创建支付记录
            Payment payment = new Payment();
            payment.setPurchaseId(purchaseId);
            payment.setState(PayState.APPLY);
            payment.setPayTime(new Date());
            payment.setPrice(totalFee);
            payment.setType(PayType.WECHAT_MERCHANT);
            paymentDao.saveOrUpdate(payment);

            //查询微信账户
            String openId = "";
            if(!StringUtils.isBlank(purchase.getAccountId())){
                WeChatAccount account = weChatAccountDao.getByAccountIdAndAppId(purchase.getAccountId(), appId);
                openId = account.getOpenId();

            }else if(!StringUtils.isBlank(code)){
                AccessTokenResponse token = WeChatUtils.getAccessToken(code, appId, appSecret);
                openId = token.getOpenId();

            }

            if(StringUtils.isBlank(openId)){
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
            wechatMerchantPay.setOutTradeNo(payment.getId());
            wechatMerchantPay.setTradeType("JSAPI");
            wechatMerchantPay.setNotifyUrl(notifyUrl);
            wechatMerchantPay.setSignType("MD5");
            wechatMerchantPay.setTotalFee(new Double(totalFee * 100).intValue());
            wechatMerchantPay.setOutTradeNo(payment.getId());

            //提交预支付
            WechatMerchantPayResponse response = WechatMerchantPayUtils.prepay(wechatMerchantPay);
            if(response.getReturnCode().equalsIgnoreCase("SUCCESS")){
                if(response.getResultCode().equalsIgnoreCase("SUCCESS")){
                    payment.setState(PayState.UNCONFIRMED);
                    wechatMerchantPay.setPrepayId(response.getPrepayId());
                }else{
                    throw new ServiceException(response.getReturnMsg());
                }
            }else{
                throw new ServiceException(response.getReturnMsg());
            }

            //更新支付状态
            paymentDao.saveOrUpdate(payment);
            //保存微信商户支付记录
            wechatMerchantPayDao.saveOrUpdate(wechatMerchantPay);

            if (!StringUtils.isBlank(wechatMerchantPay.getPrepayId())) {
                //生成网页支付参数
//                Map<String, String> params = new HashMap<String, String>();
//                params.put("appId", wechatMerchantPay.getAppId());
//                params.put("timeStamp", String.valueOf(System.currentTimeMillis() / 1000));
//                params.put("nonceStr", wechatMerchantPay.getNonceStr());
//                params.put("package", "prepay_id=" + wechatMerchantPay.getPrepayId());
//                params.put("signType", "MD5");
//                params.put("paySign", WechatMerchantPayUtils.sign(params, wechatMerchantPay.getKey()));
//
//                return params;
                return WechatMerchantPayUtils.getJsapiParams(wechatMerchantPay);
            }

            throw new ServiceException(String.format("微信支付失败:%s", response.getReturnMsg()));
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new ServiceException(e.getMessage(), e);
        }
    }


    /**
     * 预支付
     */
    @Override
    @Transactional(readOnly = false)
    public Map<String, String> xcxPrepay(String projectId, String accountId, Double totalFee, String code) {
        try {
            Assert.notNull(totalFee, "支付金额不能为空");

            //TODO 根据企业ID查询支付账号信息
            String appId = "wx616f17701a2c0d19";
            String appSecret = "677ee85fb1bddde5863ae40ff44aa7e9";
            String mchId = "1492968172";
            String appKey = "7W80cW9zmjGTnodn4BEZwVvkU8BtJar8";
            String notifyUrl = "http://www.klpua.com/api/1.0/xcxPay/callback";

//            //购买记录
//            Purchase purchase = purchaseDao.getById(purchaseId);

            //创建支付记录
            Payment payment = new Payment();
//            payment.setPurchaseId(purchaseId);
            payment.setPurchaseId(projectId);
            payment.setState(PayState.APPLY);
            payment.setPayTime(new Date());
            payment.setPrice(totalFee);
            payment.setType(PayType.WECHAT_MERCHANT);
            paymentDao.saveOrUpdate(payment);

            //查询微信账户
            String openId = "";


//            XcxAccount xcxAccount = xcxAccountDao.

            Account account = accountDao.getById(accountId);
            XcxAccount xcxAccount = xcxAccountDao.getByOpenIdOrUnionId(appId, account.getWeChat(), account.getWeChat());
            openId = xcxAccount.getOpenId();
            System.out.println("openIdopenIdopenIdopenId: " + openId);


//            if(!StringUtils.isBlank(purchase.getAccountId())){
//                WeChatAccount account = weChatAccountDao.getByAccountIdAndAppId(purchase.getAccountId(), appId);
//                openId = account.getOpenId();
//            }else if(!StringUtils.isBlank(code)){
//                AccessTokenResponse token = WeChatUtils.getAccessToken(code, appId, appSecret);
//                openId = token.getOpenId();
//            }

            if (StringUtils.isBlank(openId)) {
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
            wechatMerchantPay.setOutTradeNo(payment.getId());
            wechatMerchantPay.setTradeType("JSAPI");
            wechatMerchantPay.setNotifyUrl(notifyUrl);
            wechatMerchantPay.setSignType("MD5");
            wechatMerchantPay.setTotalFee(new Double(totalFee * 100).intValue());
            wechatMerchantPay.setOutTradeNo(payment.getId());
            wechatMerchantPay.setMsg("xcx:" + accountId + ";" + projectId);

//            创建测试项目购买未激活记录
//            AccountProject accountProject = new AccountProject();
//            accountProject.setAccountId(accountId);
//            accountProject.setProjectId(projectId);
//            accountProject.setState(UsingState.INACTIVE);
//            accountProject.setStatus(ProjectStatus.PROJECT_UNDONE);
//            accountProjectDao.saveOrUpdate(accountProject);


            //提交预支付
            WechatMerchantPayResponse response = WechatMerchantPayUtils.prepay(wechatMerchantPay);
            if (response.getReturnCode().equalsIgnoreCase("SUCCESS")) {
                if (response.getResultCode().equalsIgnoreCase("SUCCESS")) {
                    payment.setState(PayState.UNCONFIRMED);
                    wechatMerchantPay.setPrepayId(response.getPrepayId());
                } else {
                    throw new ServiceException(response.getReturnMsg());
                }
            } else {
                throw new ServiceException(response.getReturnMsg());
            }

            //更新支付状态
            paymentDao.saveOrUpdate(payment);
            //保存微信商户支付记录
            wechatMerchantPayDao.saveOrUpdate(wechatMerchantPay);

            if (!StringUtils.isBlank(wechatMerchantPay.getPrepayId())) {
                //生成网页支付参数
//                Map<String, String> params = new HashMap<String, String>();
//                params.put("appId", wechatMerchantPay.getAppId());
//                params.put("timeStamp", String.valueOf(System.currentTimeMillis() / 1000));
//                params.put("nonceStr", wechatMerchantPay.getNonceStr());
//                params.put("package", "prepay_id=" + wechatMerchantPay.getPrepayId());
//                params.put("signType", "MD5");
//                params.put("paySign", WechatMerchantPayUtils.sign(params, wechatMerchantPay.getKey()));
//
//                return params;
                return WechatMerchantPayUtils.getJsapiParams(wechatMerchantPay);
            }

            throw new ServiceException(String.format("微信支付失败:%s", response.getReturnMsg()));
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new ServiceException(e.getMessage(), e);
        }
    }

    /**
     * 回调
     */
    @Override
    @Transactional(readOnly = false)
    public String callback(String xml) {
        try {
            Assert.hasText(xml, "微信商户支付回调XML信息不能为空");

            //结果回复
            Map<String, String> params = new HashMap<String, String>();

            WechatMerchantPayCallbackResponse response = WechatMerchantPayUtils.toWechatMerchantPayCallbackResponse(xml);
            if (response != null && response.getReturnCode().equalsIgnoreCase("SUCCESS")) {
                if (response.getResultCode().equalsIgnoreCase("SUCCESS")) {
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

                        //更新支付记录
                        if(payment.getState().equals(PayState.UNCONFIRMED)){
                            payment.setState(PayState.PAID);
                            payment.setAffirmTime(new Date());
                            paymentDao.saveOrUpdate(payment);
                        }
                        //更新微信商户支付记录
                        pay.setIsSuccess(true);
                        wechatMerchantPayDao.saveOrUpdate(pay);

                        params.put("return_code", "SUCCESS");
                        params.put("return_msg", "OK");

                        Agreement agreement = agreementDao.getByPurchaseId(payment.getPurchaseId());
                        Purchase purchase = purchaseDao.getById(payment.getPurchaseId());
                        double paid = paymentDao.priceByPurchaseId(payment.getPurchaseId(), null, PayState.PAID);

                        if(paid >= agreement.getPrice()){
                            purchase.setPurchaseState(PurchaseState.PAID);
                            purchaseDao.saveOrUpdate(purchase);
                        }else{
                            purchase.setPurchaseState(PurchaseState.PAYING);
                            purchaseDao.saveOrUpdate(purchase);
                        }
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


    /**
     * 回调
     */
    @Override
    @Transactional(readOnly = false)
    public String xcxCallback(String xml) {
        try {
            Assert.hasText(xml, "微信商户支付回调XML信息不能为空");

            //结果回复
            Map<String, String> params = new HashMap<String, String>();

            WechatMerchantPayCallbackResponse response = WechatMerchantPayUtils.toWechatMerchantPayCallbackResponse(xml);
            if (response != null && response.getReturnCode().equalsIgnoreCase("SUCCESS")) {
                if (response.getResultCode().equalsIgnoreCase("SUCCESS")) {
                    //TODO 验证签名
                    String paymentId = response.getOutTradeNo();
                    //支付记录
                    Payment payment = paymentDao.getById(paymentId);
                    //微信商户支付记录
                    WechatMerchantPay pay = wechatMerchantPayDao.getByOutTradeNo(paymentId);

                    //简单验证
                    if (payment != null
                            && pay != null
                            && pay.getTotalFee() == response.getTotalFee()
                            && pay.getAppId().equals(response.getAppId())
                            && pay.getMchId().equals(response.getMchId())
                            && pay.getOpenId().equals(response.getOpenId())) {

                        //更新支付记录
                        if (payment.getState().equals(PayState.UNCONFIRMED)) {
                            payment.setState(PayState.PAID);
                            payment.setAffirmTime(new Date());
                            paymentDao.saveOrUpdate(payment);
                        }
                        //更新微信商户支付记录
                        pay.setIsSuccess(true);
                        wechatMerchantPayDao.saveOrUpdate(pay);

                        //                        更新account_project状态
                        String accountProjectMsg = pay.getMsg();
                        String[] xcxAJArr = accountProjectMsg.split(":");
                        String[] aJArr = xcxAJArr[1].split(";");
//                        AccountProject accountProject = accountProjectDao.getByAccountIdAndProjectId(aJArr[0], aJArr[1]);
//                        accountProject.setState(UsingState.NORMAL);
//                        accountProjectDao.saveOrUpdate(accountProject);


                        //            创建测试项目购买未激活记录
                        AccountProject accountProject = new AccountProject();
                        accountProject.setAccountId(aJArr[0]);
                        accountProject.setProjectId(aJArr[1]);
                        accountProject.setState(UsingState.NORMAL);
                        accountProject.setStatus(ProjectStatus.PROJECT_UNDONE);
                        accountProjectDao.saveOrUpdate(accountProject);

                        params.put("return_code", "SUCCESS");
                        params.put("return_msg", "OK");
                    }
                } else {

                    params.put("return_code", "FAIL");
                    params.put("return_msg", response.getErrCodeDes());
                }
            } else {
                params.put("return_code", "FAIL");
                params.put("return_msg", response.getReturnMsg());
            }

            return WechatMerchantPayUtils.toXml(params);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new ServiceException(e.getMessage(), e);
        }
    }

    /**
     * 查询JSAPI支付参数
     */
    @Override
    public Map<String, String> getJsapi(String outTradeNo) {
        try {
            Assert.hasText(outTradeNo, "支付记录ID不能为空");

            WechatMerchantPay pay = wechatMerchantPayDao.getByOutTradeNo(outTradeNo);

            return WechatMerchantPayUtils.getJsapiParams(pay);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new ServiceException(e.getMessage(), e);
        }
    }



    /**
     * 预支付
     */
    @Override
    @Transactional(readOnly = false)
    public Map<String, String> xcxCoursePrepay(String courseId, String accountId, Double totalFee, String code) {
        try {
            Assert.notNull(totalFee, "支付金额不能为空");

            //TODO 根据企业ID查询支付账号信息
            String appId = "wx5c1252ff3262c920";
            String appSecret = "f94b3a4d8167a740a850e2b2b9d75f88";
            String mchId = "1492968172";
            String appKey = "7W80cW9zmjGTnodn4BEZwVvkU8BtJar8";
            String notifyUrl = "http://www.klpua.com/api/1.0/xcxPay/course/callback";

//            //购买记录
//            Purchase purchase = purchaseDao.getById(purchaseId);

            //创建支付记录
            Payment payment = new Payment();
//            payment.setPurchaseId(purchaseId);
            payment.setPurchaseId(courseId);
            payment.setState(PayState.APPLY);
            payment.setPayTime(new Date());
            payment.setPrice(totalFee);
            payment.setType(PayType.WECHAT_MERCHANT);
            paymentDao.saveOrUpdate(payment);
            //查询微信账户
            String openId = "";
            Account account = accountDao.getById(accountId);
            XcxAccount xcxAccount = xcxAccountDao.getByOpenIdOrUnionId(appId, account.getWeChat(), account.getWeChat());
            openId = xcxAccount.getOpenId();
            if (StringUtils.isBlank(openId)) {
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
            wechatMerchantPay.setOutTradeNo(payment.getId());
            wechatMerchantPay.setTradeType("JSAPI");
            wechatMerchantPay.setNotifyUrl(notifyUrl);
            wechatMerchantPay.setSignType("MD5");
            wechatMerchantPay.setTotalFee(new Double(totalFee * 100).intValue());
            wechatMerchantPay.setOutTradeNo(payment.getId());
            wechatMerchantPay.setMsg("xcx:" + accountId + ";" + courseId);

            //提交预支付
            WechatMerchantPayResponse response = WechatMerchantPayUtils.prepay(wechatMerchantPay);
            if (response.getReturnCode().equalsIgnoreCase("SUCCESS")) {
                if (response.getResultCode().equalsIgnoreCase("SUCCESS")) {
                    payment.setState(PayState.UNCONFIRMED);
                    wechatMerchantPay.setPrepayId(response.getPrepayId());
                } else {
                    throw new ServiceException(response.getReturnMsg());
                }
            } else {
                throw new ServiceException(response.getReturnMsg());
            }

            //更新支付状态
            paymentDao.saveOrUpdate(payment);
            //保存微信商户支付记录
            wechatMerchantPayDao.saveOrUpdate(wechatMerchantPay);

            if (!StringUtils.isBlank(wechatMerchantPay.getPrepayId())) {
                return WechatMerchantPayUtils.getJsapiParams(wechatMerchantPay);
            }

            throw new ServiceException(String.format("微信支付失败:%s", response.getReturnMsg()));
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new ServiceException(e.getMessage(), e);
        }
    }



    /**
     * 回调
     */
    @Override
    @Transactional(readOnly = false)
    public String xcxCourseCallback(String xml) {
        try {
            Assert.hasText(xml, "微信商户支付回调XML信息不能为空");

            //结果回复
            Map<String, String> params = new HashMap<String, String>();

            WechatMerchantPayCallbackResponse response = WechatMerchantPayUtils.toWechatMerchantPayCallbackResponse(xml);
            if (response != null && response.getReturnCode().equalsIgnoreCase("SUCCESS")) {
                if (response.getResultCode().equalsIgnoreCase("SUCCESS")) {
                    //TODO 验证签名
                    String paymentId = response.getOutTradeNo();
                    //支付记录
                    Payment payment = paymentDao.getById(paymentId);
                    //微信商户支付记录
                    WechatMerchantPay pay = wechatMerchantPayDao.getByOutTradeNo(paymentId);

                    //简单验证
                    if (payment != null
                            && pay != null
                            && pay.getTotalFee() == response.getTotalFee()
                            && pay.getAppId().equals(response.getAppId())
                            && pay.getMchId().equals(response.getMchId())
                            && pay.getOpenId().equals(response.getOpenId())) {

                        //更新支付记录
                        if (payment.getState().equals(PayState.UNCONFIRMED)) {
                            payment.setState(PayState.PAID);
                            payment.setAffirmTime(new Date());
                            paymentDao.saveOrUpdate(payment);
                        }
                        //更新微信商户支付记录
                        pay.setIsSuccess(true);
                        wechatMerchantPayDao.saveOrUpdate(pay);

                        //                        更新account_project状态
                        String accountProjectMsg = pay.getMsg();
                        String[] xcxAJArr = accountProjectMsg.split(":");
                        String[] aJArr = xcxAJArr[1].split(";");
                        //            创建课程购买记录
                        AccountCourse accountCourse = new AccountCourse();
                        accountCourse.setAccountId(aJArr[0]);
                        Course course = new Course();
                        course.setId(aJArr[1]);
                        accountCourse.setCourse(course);
                        accountCourse.setState(UsingState.NORMAL);
                        accountCourseDao.saveOrUpdate(accountCourse);


                        params.put("return_code", "SUCCESS");
                        params.put("return_msg", "OK");
                    }
                } else {

                    params.put("return_code", "FAIL");
                    params.put("return_msg", response.getErrCodeDes());
                }
            } else {
                params.put("return_code", "FAIL");
                params.put("return_msg", response.getReturnMsg());
            }

            return WechatMerchantPayUtils.toXml(params);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new ServiceException(e.getMessage(), e);
        }
    }





    /**
     * 预支付
     */
    @Override
    @Transactional(readOnly = false)
    public Map<String, String> xcxVipPrepay(String accountId, Double totalFee, String code) {
        try {
            Assert.notNull(totalFee, "支付金额不能为空");

            //TODO 根据企业ID查询支付账号信息
            String appId = "wx5c1252ff3262c920";
            String appSecret = "f94b3a4d8167a740a850e2b2b9d75f88";
            String mchId = "1492968172";
            String appKey = "7W80cW9zmjGTnodn4BEZwVvkU8BtJar8";
            String notifyUrl = "http://www.klpua.com/api/1.0/xcxPay/course/callback";

//            //购买记录
//            Purchase purchase = purchaseDao.getById(purchaseId);

            //创建支付记录
            Payment payment = new Payment();
//            payment.setPurchaseId(purchaseId);
            payment.setPurchaseId("vip");
            payment.setState(PayState.APPLY);
            payment.setPayTime(new Date());
            payment.setPrice(totalFee);
            payment.setType(PayType.WECHAT_MERCHANT);
            paymentDao.saveOrUpdate(payment);
            //查询微信账户
            String openId = "";
            Account account = accountDao.getById(accountId);
            XcxAccount xcxAccount = xcxAccountDao.getByOpenIdOrUnionId(appId, account.getWeChat(), account.getWeChat());
            openId = xcxAccount.getOpenId();
            if (StringUtils.isBlank(openId)) {
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
            wechatMerchantPay.setOutTradeNo(payment.getId());
            wechatMerchantPay.setTradeType("JSAPI");
            wechatMerchantPay.setNotifyUrl(notifyUrl);
            wechatMerchantPay.setSignType("MD5");
            wechatMerchantPay.setTotalFee(new Double(totalFee * 100).intValue());
            wechatMerchantPay.setOutTradeNo(payment.getId());
            wechatMerchantPay.setMsg("xcx:" + accountId + ";" + "vip");

            //提交预支付
            WechatMerchantPayResponse response = WechatMerchantPayUtils.prepay(wechatMerchantPay);
            if (response.getReturnCode().equalsIgnoreCase("SUCCESS")) {
                if (response.getResultCode().equalsIgnoreCase("SUCCESS")) {
                    payment.setState(PayState.UNCONFIRMED);
                    wechatMerchantPay.setPrepayId(response.getPrepayId());
                } else {
                    throw new ServiceException(response.getReturnMsg());
                }
            } else {
                throw new ServiceException(response.getReturnMsg());
            }

            //更新支付状态
            paymentDao.saveOrUpdate(payment);
            //保存微信商户支付记录
            wechatMerchantPayDao.saveOrUpdate(wechatMerchantPay);

            if (!StringUtils.isBlank(wechatMerchantPay.getPrepayId())) {
                return WechatMerchantPayUtils.getJsapiParams(wechatMerchantPay);
            }

            throw new ServiceException(String.format("微信支付失败:%s", response.getReturnMsg()));
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new ServiceException(e.getMessage(), e);
        }
    }



    /**
     * 回调
     */
    @Override
    @Transactional(readOnly = false)
    public String xcxVipCallback(String xml) {
        try {
            Assert.hasText(xml, "微信商户支付回调XML信息不能为空");

            //结果回复
            Map<String, String> params = new HashMap<String, String>();

            WechatMerchantPayCallbackResponse response = WechatMerchantPayUtils.toWechatMerchantPayCallbackResponse(xml);
            if (response != null && response.getReturnCode().equalsIgnoreCase("SUCCESS")) {
                if (response.getResultCode().equalsIgnoreCase("SUCCESS")) {
                    //TODO 验证签名
                    String paymentId = response.getOutTradeNo();
                    //支付记录
                    Payment payment = paymentDao.getById(paymentId);
                    //微信商户支付记录
                    WechatMerchantPay pay = wechatMerchantPayDao.getByOutTradeNo(paymentId);

                    //简单验证
                    if (payment != null
                            && pay != null
                            && pay.getTotalFee() == response.getTotalFee()
                            && pay.getAppId().equals(response.getAppId())
                            && pay.getMchId().equals(response.getMchId())
                            && pay.getOpenId().equals(response.getOpenId())) {

                        //更新支付记录
                        if (payment.getState().equals(PayState.UNCONFIRMED)) {
                            payment.setState(PayState.PAID);
                            payment.setAffirmTime(new Date());
                            paymentDao.saveOrUpdate(payment);
                        }
                        //更新微信商户支付记录
                        pay.setIsSuccess(true);
                        wechatMerchantPayDao.saveOrUpdate(pay);

                        //                        更新account_project状态
                        String accountProjectMsg = pay.getMsg();
                        String[] xcxAJArr = accountProjectMsg.split(":");
                        String[] aJArr = xcxAJArr[1].split(";");
                        //            加入vip
//                        courseGroupDao.authorizationByVIP(aJArr[0]);
                        //添加会员记录
                        String accountId = aJArr[0];
                        AccountLevel accountLevel = new AccountLevel();
                        accountLevel.setAccountId(accountId);
                        accountLevelDao.saveOrUpdate(accountLevel);

                        params.put("return_code", "SUCCESS");
                        params.put("return_msg", "OK");
                    }
                } else {

                    params.put("return_code", "FAIL");
                    params.put("return_msg", response.getErrCodeDes());
                }
            } else {
                params.put("return_code", "FAIL");
                params.put("return_msg", response.getReturnMsg());
            }

            return WechatMerchantPayUtils.toXml(params);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new ServiceException(e.getMessage(), e);
        }
    }
}
