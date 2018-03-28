package com.lab.hosaily.core.course.service;

import com.lab.hosaily.core.course.entity.Question;
import com.rab.babylon.commons.security.response.Page;
import com.rab.babylon.commons.security.response.PageRequest;
import com.rab.babylon.core.consts.entity.UsingState;

import java.util.List;

public interface QuestionService {

    /**
     * 保存记录
     */
    void save(Question question);

    /**
     * 更新记录
     */
    void update(Question question);

    /**
     * 根据ID查询记录
     */
    Question getById(String id);

    /**
     * 删除
     */
    void delete(String id);

    /**
     * 分页查询
     */
    Page<Question> page(PageRequest pageRequest, String num, String title, String projectId, String organizationId);


    /**
     * 查询列表
     */
    List<Question> list(String nickname, String name, UsingState state, String organizationId, String organizationToken);



    /**
     * 根据ID查询记录
     */
    Question getFirstQuestion(String projectId, String num, UsingState state, String organizationId, String questionId);

    /**
     * 根据ID查询记录
     */
    Question getNextQuestion(String projectId, String num, UsingState state, String organizationId, String questionId);


}
