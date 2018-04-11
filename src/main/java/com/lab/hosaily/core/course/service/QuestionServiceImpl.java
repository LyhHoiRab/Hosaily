package com.lab.hosaily.core.course.service;

import com.lab.hosaily.core.course.dao.QuestionDao;
import com.lab.hosaily.core.course.entity.Question;
import com.rab.babylon.commons.security.exception.ServiceException;
import com.rab.babylon.commons.security.response.Page;
import com.rab.babylon.commons.security.response.PageRequest;
import com.rab.babylon.core.consts.entity.UsingState;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class QuestionServiceImpl implements QuestionService {

    private static Logger logger = LoggerFactory.getLogger(QuestionServiceImpl.class);

    @Autowired
    private QuestionDao questionDao;

    /**
     * 保存记录
     */
    @Override
    @Transactional(readOnly = false)
    public void save(Question question) {
        try {
            Assert.notNull(question, "标签信息不能为空");

            questionDao.saveOrUpdate(question);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new ServiceException(e.getMessage(), e);
        }
    }

    /**
     * 更新记录
     */
    @Override
    @Transactional(readOnly = false)
    public void update(Question question) {
        try {
            Assert.notNull(question, "标签信息不能为空");
            Assert.hasText(question.getId(), "标签sim不能为空");

            questionDao.saveOrUpdate(question);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new ServiceException(e.getMessage(), e);
        }
    }

    /**
     * 删除
     */
    @Override
    @Transactional(readOnly = false)
    public void delete(String id) {
        try {
            Assert.hasText(id, "帖子ID不能为空");

            questionDao.delete(id);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new ServiceException(e.getMessage(), e);
        }
    }

    /**
     * 根据ID查询记录
     */
    @Override
    public Question getById(String id) {
        try {
            Assert.hasText(id, "标签信息不能为空");

            return questionDao.getById(id);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new ServiceException(e.getMessage(), e);
        }
    }

    /**
     * 分页查询
     */
    @Override
    public Page<Question> page(PageRequest pageRequest, String num, String title, String projectId, String organizationId) {
        try {
            Assert.notNull(pageRequest, "分页信息不能为空");

            return questionDao.page(pageRequest, num, title, projectId, organizationId);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new ServiceException(e.getMessage(), e);
        }
    }

    /**
     * 查询列表
     */
    @Override
    public List<Question> list(String projectId, String organizationId) {
        try {
            return questionDao.list(projectId, organizationId);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new ServiceException(e.getMessage(), e);
        }
    }

    @Override
    public Question getFirstQuestion(String projectId, String num, UsingState state, String organizationId, String questionId) {
        try {
            return questionDao.getFirstQuestion(projectId, num, state, organizationId, questionId);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new ServiceException(e.getMessage(), e);
        }
    }

    @Override
    public Question getNextQuestion(String projectId, String num, UsingState state, String organizationId, String questionId) {
        try {
//            Assert.hasText(id, "标签信息不能为空");

            return questionDao.getNextQuestion(projectId, num, state, organizationId, questionId);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new ServiceException(e.getMessage(), e);
        }
    }
}
