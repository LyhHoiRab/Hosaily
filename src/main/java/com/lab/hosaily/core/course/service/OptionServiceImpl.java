package com.lab.hosaily.core.course.service;

import com.lab.hosaily.commons.utils.FileNameUtils;
import com.lab.hosaily.commons.utils.UpyunUtils;
import com.lab.hosaily.core.course.dao.OptionDao;
import com.lab.hosaily.core.course.entity.Option;
import com.rab.babylon.commons.security.exception.ServiceException;
import com.rab.babylon.commons.security.response.Page;
import com.rab.babylon.commons.security.response.PageRequest;
import com.rab.babylon.commons.utils.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class OptionServiceImpl implements OptionService {

    private static Logger logger = LoggerFactory.getLogger(OptionServiceImpl.class);

    @Autowired
    private OptionDao optionDao;

    /**
     * 保存记录
     */
    @Override
    @Transactional(readOnly = false)
    public void save(Option option){
        try{
            Assert.notNull(option, "标签信息不能为空");

            optionDao.saveOrUpdate(option);
        }catch(Exception e){
            logger.error(e.getMessage(), e);
            throw new ServiceException(e.getMessage(), e);
        }
    }

    /**
     * 更新记录
     */
    @Override
    @Transactional(readOnly = false)
    public void update(Option option){
        try{
            Assert.notNull(option, "标签信息不能为空");
            Assert.hasText(option.getId(), "标签sim不能为空");

            optionDao.saveOrUpdate(option);
        }catch(Exception e){
            logger.error(e.getMessage(), e);
            throw new ServiceException(e.getMessage(), e);
        }
    }

    /**
     * 删除
     */
    @Override
    @Transactional(readOnly = false)
    public void delete(String id){
        try{
            Assert.hasText(id, "帖子ID不能为空");

            optionDao.delete(id);
        }catch(Exception e){
            logger.error(e.getMessage(), e);
            throw new ServiceException(e.getMessage(), e);
        }
    }

    /**
     * 根据ID查询记录
     */
    @Override
    public Option getById(String id){
        try{
            Assert.hasText(id, "标签信息不能为空");

            return optionDao.getById(id);
        }catch(Exception e){
            logger.error(e.getMessage(), e);
            throw new ServiceException(e.getMessage(), e);
        }
    }

    /**
     * 分页查询
     */
    @Override
    public Page<Option> page(PageRequest pageRequest, String title, String projectId, String questionId, String questionOption, String organizationId){
        try{
            Assert.notNull(pageRequest, "分页信息不能为空");

            return optionDao.page(pageRequest, title, projectId, questionId, questionOption, organizationId);
        }catch(Exception e){
            logger.error(e.getMessage(), e);
            throw new ServiceException(e.getMessage(), e);
        }
    }


    /**
     * 上传图片
     */
    @Override
    public String upload(CommonsMultipartFile file) {
        try {
            Assert.notNull(file, "上传文件不能为空");

            //文件名称
            String originalFilename = file.getOriginalFilename();
            //MD5
            String md5 = FileUtils.getMD5(file.getBytes());
            //文件后缀
            String suffix = FileNameUtils.getSuffix(originalFilename);
            //上传名称
            String name = md5 + suffix;
            //上传路径
            String uploadPath = UpyunUtils.EMOTION_TEST_DIR + name;
            //上传
            boolean result = UpyunUtils.upload(uploadPath, file);

            return result ? UpyunUtils.URL + uploadPath : "";
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new ServiceException(e.getMessage(), e);
        }
    }

    @Override
    public List<Option> list(String questionId) {
        return optionDao.list(questionId);
    }
}
