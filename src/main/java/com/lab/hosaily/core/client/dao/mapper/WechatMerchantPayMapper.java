package com.lab.hosaily.core.client.dao.mapper;

import com.lab.hosaily.core.client.entity.WechatMerchantPay;
import com.rab.babylon.commons.security.mybatis.Criteria;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WechatMerchantPayMapper{

    /**
     * 保存
     */
    void save(WechatMerchantPay pay);

    /**
     * 更新
     */
    void update(WechatMerchantPay pay);

    /**
     * 查询
     */
    WechatMerchantPay getByParams(@Param("params") Criteria criteria);

    /**
     * 查询
     */
    List<WechatMerchantPay> findByParams(@Param("params") Criteria criteria);
}
