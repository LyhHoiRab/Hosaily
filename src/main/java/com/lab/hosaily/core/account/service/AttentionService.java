package com.lab.hosaily.core.account.service;

import com.lab.hosaily.core.account.entity.Attention;

import java.util.List;

public interface AttentionService{

    /**
     * 添加足迹
     */
    void saveTrack(Attention attention);

    /**
     * 添加收藏
     */
    void saveCollect(Attention attention);

    /**
     * 删除关注记录
     */
    void delete(String id);

    /**
     * 根据账户ID查询足迹
     */
    List<Attention> findTrackByAccountId(String accountId);

    /**
     * 根据账户ID查询收藏
     */
    List<Attention> findCollectByAccountId(String accountId);
}
