package com.lab.hosaily.core.sell.dao.mapper;

import com.lab.hosaily.core.sell.entity.PayLogs;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PayLogsMapper{

    /**
     * 保存
     */
    void save(PayLogs logs);

    /**
     * 批量保存
     */
    void saveBatch(@Param("logs") List<PayLogs> logs);

    /**
     * 更新
     */
    void update(PayLogs logs);
}
