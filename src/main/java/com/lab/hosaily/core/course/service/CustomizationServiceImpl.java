package com.lab.hosaily.core.course.service;

import com.lab.hosaily.commons.utils.FileNameUtils;
import com.lab.hosaily.commons.utils.UpyunUtils;
import com.lab.hosaily.core.course.dao.CustomizationDao;
import com.lab.hosaily.core.course.entity.Customization;
import com.rab.babylon.commons.security.exception.DataAccessException;
import com.rab.babylon.commons.security.exception.ServiceException;
import com.rab.babylon.commons.security.mybatis.Criteria;
import com.rab.babylon.commons.security.mybatis.Restrictions;
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

@Service
@Transactional(readOnly = true)
public class CustomizationServiceImpl implements CustomizationService{

    private static Logger logger = LoggerFactory.getLogger(CustomizationServiceImpl.class);

    @Autowired
    private CustomizationDao customizationDao;

    /**
     * 保存
     */
    @Override
    @Transactional(readOnly = false)
    public void save(Customization customization){
        try{
            Assert.notNull(customization, "定制服务信息不能为空");

            customizationDao.saveOrUpdate(customization);
        }catch(Exception e){
            logger.error(e.getMessage(), e);
            throw new DataAccessException(e.getMessage(), e);
        }
    }

    /**
     * 更新
     */
    @Override
    @Transactional(readOnly = false)
    public void update(Customization customization){
        try{
            Assert.notNull(customization, "定制服务信息不能为空");
            Assert.hasText(customization.getId(), "定制服务ID不能为空");

            customizationDao.saveOrUpdate(customization);
        }catch(Exception e){
            logger.error(e.getMessage(), e);
            throw new DataAccessException(e.getMessage(), e);
        }
    }

    /**
     * 根据ID查询
     */
    @Override
    public Customization getById(String id){
        try{
            Assert.hasText(id, "定制服务ID不能为空");

            return customizationDao.getById(id);
        }catch(Exception e){
            logger.error(e.getMessage(), e);
            throw new ServiceException(e.getMessage(), e);
        }
    }

    /**
     * 分页查询
     */
    @Override
    public Page<Customization> page(PageRequest pageRequest, UsingState state, String tagName, String organizationId, String organizationToken){
        try{
            Assert.notNull(pageRequest, "分页信息不能为空");

            return customizationDao.page(pageRequest, state, tagName, organizationId, organizationToken);
        }catch(Exception e){
            logger.error(e.getMessage(), e);
            throw new DataAccessException(e.getMessage(), e);
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
            String uploadPath = UpyunUtils.COURSE_COVER_DIR + name;
            //上传
            boolean result = UpyunUtils.upload(uploadPath, file);

            return result ? UpyunUtils.URL + uploadPath : "";
        }catch(Exception e){
            logger.error(e.getMessage(), e);
            throw new ServiceException(e.getMessage(), e);
        }
    }
}
