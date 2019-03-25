package com.lab.hosaily.core.app.service;

import com.lab.hosaily.commons.utils.FileNameUtils;
import com.lab.hosaily.commons.utils.UpyunUtils;
import com.lab.hosaily.core.app.dao.PosterDao;
import com.lab.hosaily.core.app.entity.Poster;
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

import java.util.List;

@Service
@Transactional(readOnly = true)
public class PosterServiceImpl implements PosterService{

    private static Logger logger = LoggerFactory.getLogger(PosterServiceImpl.class);

    @Autowired
    private PosterDao posterDao;

    /**
     * 保存记录
     */
    @Override
    @Transactional(readOnly = false)
    public void save(Poster poster){
        try{
            Assert.notNull(poster, "导师信息不能为空");

            posterDao.saveOrUpdate(poster);
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
    public void update(Poster poster){
        try{
            Assert.notNull(poster, "导师信息不能为空");
//            Assert.hasText(poster.getId(), "导师ID不能为空");

            posterDao.saveOrUpdate(poster);
        }catch(Exception e){
            logger.error(e.getMessage(), e);
            throw new ServiceException(e.getMessage(), e);
        }
    }

    /**
     * 根据ID查询记录
     */
    @Override
    public Poster getBySellerIdAndAdvisorId(String sellerId, String AdvisorId){
        try{
            Assert.hasText(sellerId, "sellerId不能为空");
            Assert.hasText(AdvisorId, "AdvisorId不能为空");

            return posterDao.getBySellerId(sellerId);
        }catch(Exception e){
            logger.error(e.getMessage(), e);
            throw new ServiceException(e.getMessage(), e);
        }
    }

    @Override
    public Poster getBySellerId(String sellerId){
        try{
            Assert.hasText(sellerId, "sellerId不能为空");
            return posterDao.getBySellerId(sellerId);
        }catch(Exception e){
            logger.error(e.getMessage(), e);
            throw new ServiceException(e.getMessage(), e);
        }
    }

    /**
     * 分页查询
     */
    @Override
    public Page<Poster> page(PageRequest pageRequest, String profileId){
        try{
            Assert.notNull(pageRequest, "分页信息不能为空");

            return posterDao.page(pageRequest, profileId);
        }catch(Exception e){
            logger.error(e.getMessage(), e);
            throw new ServiceException(e.getMessage(), e);
        }
    }

    /**
     * 查询列表
     */
    @Override
    public List<Poster> list(String nickname, String name, UsingState state, String organizationId, String organizationToken){
        try{
            return posterDao.list(nickname, name, state, organizationId, organizationToken);
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
