package com.lab.hosaily.core.app.service;

import com.lab.hosaily.commons.utils.FileNameUtils;
import com.lab.hosaily.commons.utils.UpyunUtils;
import com.lab.hosaily.core.app.dao.ProfileDao;
import com.lab.hosaily.core.app.dao.SellerClientRelationDao;
import com.lab.hosaily.core.app.entity.Profile;
import com.lab.hosaily.core.app.entity.SellerClientRelation;
import com.lab.hosaily.core.app.service.ProfileService;
import com.rab.babylon.commons.security.exception.ServiceException;
import com.rab.babylon.commons.security.response.Page;
import com.rab.babylon.commons.security.response.PageRequest;
import com.rab.babylon.commons.utils.FileUtils;
import com.rab.babylon.core.consts.entity.UsingState;
import org.apache.commons.lang.StringUtils;
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
public class ProfileServiceImpl implements ProfileService{

    private static Logger logger = LoggerFactory.getLogger(ProfileServiceImpl.class);

    @Autowired
    private ProfileDao profileDao;

    @Autowired
    private SellerClientRelationDao sellerClientRelationDao;

    /**
     * 保存记录
     */
    @Override
    @Transactional(readOnly = false)
    public void save(Profile profile){
        try{
            Assert.notNull(profile, "导师信息不能为空");
            Assert.hasText(profile.getId(), "导师ID不能为空");
            profileDao.save(profile);
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
    public void update(Profile profile){
        try{
            Assert.notNull(profile, "导师信息不能为空");
            Assert.hasText(profile.getId(), "id不能为空");
            System.out.println("sssssssssssss: " + profile.getSellerId());
//            当sellerId有值的时候确定为绑定关系
            if(StringUtils.isNotBlank(profile.getSellerId())){
                SellerClientRelation sellerClientRelation = new SellerClientRelation();
                sellerClientRelation.setClientId(profile.getId());
                sellerClientRelation.setSellerId(profile.getSellerId());
                sellerClientRelationDao.save(sellerClientRelation);
            }
            profileDao.update(profile);
        }catch(Exception e){
            logger.error(e.getMessage(), e);
            throw new ServiceException(e.getMessage(), e);
        }
    }

    /**
     * 根据ID查询记录
     */
    @Override
    public Profile getById(String id){
        try{
            Assert.hasText(id, "导师ID不能为空");

            return profileDao.getById(id);
        }catch(Exception e){
            logger.error(e.getMessage(), e);
            throw new ServiceException(e.getMessage(), e);
        }
    }


    @Override
    public Profile getByAccountId(String accountId){
        try{
            Assert.hasText(accountId, "accountId不能为空");

            return profileDao.getByAccountId(accountId);
        }catch(Exception e){
            logger.error(e.getMessage(), e);
            throw new ServiceException(e.getMessage(), e);
        }
    }


    /**
     * 分页查询
     */
    @Override
    public Page<Profile> page(PageRequest pageRequest, Integer signAgreement, Integer signProfile, Integer uploaded, String name, String sellerId){
        try{
            Assert.notNull(pageRequest, "分页信息不能为空");

            return profileDao.page(pageRequest, signAgreement, signProfile, uploaded, name, sellerId);
        }catch(Exception e){
            logger.error(e.getMessage(), e);
            throw new ServiceException(e.getMessage(), e);
        }
    }


    /**
     * 分页查询
     */
    @Override
    public Page<Profile> findClientsPage(PageRequest pageRequest, String clientName, String advisorId){
        try{
            Assert.notNull(pageRequest, "分页信息不能为空");

            return profileDao.findClientsPage(pageRequest, clientName, advisorId);
        }catch(Exception e){
            logger.error(e.getMessage(), e);
            throw new ServiceException(e.getMessage(), e);
        }
    }


    /**
     * 查询列表
     */
    @Override
    public List<Profile> list(String advisorName, Integer role){
        try{
            return profileDao.list(advisorName, role);
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
