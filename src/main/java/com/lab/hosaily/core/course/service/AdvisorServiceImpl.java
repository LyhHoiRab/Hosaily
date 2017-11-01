package com.lab.hosaily.core.course.service;

import com.lab.hosaily.commons.utils.FileNameUtils;
import com.lab.hosaily.commons.utils.UpyunUtils;
import com.lab.hosaily.core.course.dao.AdvisorDao;
import com.lab.hosaily.core.course.entity.Advisor;
import com.rab.babylon.commons.security.exception.ServiceException;
import com.rab.babylon.commons.security.response.Page;
import com.rab.babylon.commons.security.response.PageRequest;
import com.rab.babylon.commons.utils.FileUtils;
import com.rab.babylon.core.consts.entity.UsingState;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import java.util.Date;
import java.util.List;

@Service
@Transactional(readOnly = true)
public class AdvisorServiceImpl implements AdvisorService{

    private static Logger logger = LoggerFactory.getLogger(AdvisorServiceImpl.class);

    @Autowired
    private AdvisorDao advisorDao;

    /**
     * 保存记录
     */
    @Override
    @Transactional(readOnly = false)
    public void save(Advisor advisor){
        try{
            Assert.notNull(advisor, "导师信息不能为空");

            advisorDao.saveOrUpdate(advisor);
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
    public void update(Advisor advisor){
        try{
            Assert.notNull(advisor, "导师信息不能为空");
            Assert.hasText(advisor.getId(), "导师ID不能为空");

            advisorDao.saveOrUpdate(advisor);
        }catch(Exception e){
            logger.error(e.getMessage(), e);
            throw new ServiceException(e.getMessage(), e);
        }
    }

    /**
     * 根据ID查询记录
     */
    @Override
    public Advisor getById(String id){
        try{
            Assert.hasText(id, "导师ID不能为空");

            return advisorDao.getById(id);
        }catch(Exception e){
            logger.error(e.getMessage(), e);
            throw new ServiceException(e.getMessage(), e);
        }
    }

    /**
     * 根据状态查询
     */
    @Override
    public List<Advisor> findByState(UsingState state){
        try{
            Assert.notNull(state, "导师状态不能为空");

            return advisorDao.findByState(state);
        }catch(Exception e){
            logger.error(e.getMessage(), e);
            throw new ServiceException(e.getMessage(), e);
        }
    }

    /**
     * 分页查询
     */
    @Override
    public Page<Advisor> page(PageRequest pageRequest, String nickname, String name, UsingState state, Date createTime, Date minCreateTime, Date maxCreateTime){
        try{
            Assert.notNull(pageRequest, "分页信息不能为空");

            return advisorDao.page(pageRequest, nickname, name, state, createTime, minCreateTime, maxCreateTime);
        }catch(Exception e){
            logger.error(e.getMessage(), e);
            throw new ServiceException(e.getMessage(), e);
        }
    }

    /**
     * 查询列表
     */
    @Override
    public List<Advisor> list(String nickname, String name, UsingState state, Date createTime){
        try{
            return advisorDao.list(nickname, name, state, createTime);
        }catch(Exception e){
            logger.error(e.getMessage(), e);
            throw new ServiceException(e.getMessage(), e);
        }
    }

    /**
     * 上传图片
     */
    @Override
    public String upload(CommonsMultipartFile file){
        try{
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
            String uploadPath = UpyunUtils.ADVISOR_HEAD_DIR + name;
            //上传
            boolean result = UpyunUtils.upload(uploadPath, file);

            return result ? UpyunUtils.URL + uploadPath : "";
        }catch(Exception e){
            logger.error(e.getMessage(), e);
            throw new ServiceException(e.getMessage(), e);
        }
    }
}
