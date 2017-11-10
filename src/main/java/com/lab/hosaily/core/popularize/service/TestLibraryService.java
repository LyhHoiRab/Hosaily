package com.lab.hosaily.core.popularize.service;

import com.lab.hosaily.core.popularize.entity.TestLibrary;
import com.rab.babylon.commons.security.response.Page;
import com.rab.babylon.commons.security.response.PageRequest;
import com.rab.babylon.core.consts.entity.UsingState;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import java.util.Date;
import java.util.List;

public interface TestLibraryService{

    /**
     * 保存
     */
    void save(TestLibrary library);

    /**
     * 更新
     */
    void update(TestLibrary library);

    /**
     * 删除题库
     */
    void delete(String id);

    /**
     * 删除答案
     */
    void deleteAnswer(String answerId);

    /**
     * 根据ID查询
     */
    TestLibrary getById(String id);

    /**
     * 分页
     */
    Page<TestLibrary> page(PageRequest pageRequest, UsingState state, Date createTime, String kind, String title);

    /**
     * 上传图片
     */
    String upload(CommonsMultipartFile file);

    /**
     * 查询用户当天测试记录
     */
    List<TestLibrary> findByAccountIdToday(String accountId);

    List<TestLibrary> list(Long pageSize, UsingState state, Date createTime, String kind, String title);
}
