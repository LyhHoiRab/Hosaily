package com.lab.hosaily.core.course.service;

import com.lab.hosaily.core.course.entity.Record;
import com.rab.babylon.commons.security.response.Page;
import com.rab.babylon.commons.security.response.PageRequest;


public interface RecordService {

    /**
     * 保存记录
     */
    void save(Record record);

    /**
     * 更新记录
     */
    void update(Record record);

    /**
     * 根据ID查询记录
     */
    Record getById(String id);

    /**
     * 删除
     */
    void delete(String id);

    /**
     * 分页查询
     */
    Page<Record> page(PageRequest pageRequest, String userName, String num, String outGoingNum, String sim, String userType, String organizationId, String fileType);
}
