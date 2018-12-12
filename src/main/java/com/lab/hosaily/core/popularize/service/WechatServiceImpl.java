package com.lab.hosaily.core.popularize.service;

import com.lab.hosaily.commons.utils.FileNameUtils;
import com.lab.hosaily.commons.utils.UpyunUtils;
import com.lab.hosaily.core.popularize.dao.WechatDao;
import com.lab.hosaily.core.popularize.entity.Wechat;
import com.rab.babylon.commons.security.exception.ServiceException;
import com.rab.babylon.commons.utils.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.wah.doraemon.security.request.Page;
import org.wah.doraemon.security.request.PageRequest;

import java.text.MessageFormat;

@Service
@Transactional(readOnly = true)
public class WechatServiceImpl implements WechatService{

    private Logger logger = LoggerFactory.getLogger(WechatServiceImpl.class);

    @Autowired
    private WechatDao wechatDao;

    @Override
    @Transactional
    public void save(Wechat wechat){
        try{
            Assert.notNull(wechat, "微信信息不能为空");
            Assert.hasText(wechat.getWxno(), "微信号不能为空");
            Assert.hasText(wechat.getOrganizationId(), "微信所属企业ID不能为空");
            Assert.notNull(wechat.getState(), "微信使用状态不能为空");

            if(wechatDao.existByOrganizationIdAndWxno(wechat.getOrganizationId(), wechat.getWxno())){
                throw new ServiceException(MessageFormat.format("微信号[{0}]已添加", wechat.getWxno()));
            }

            wechatDao.saveOrUpdate(wechat);
        }catch(Exception e){
            logger.error(e.getMessage(), e);
            throw new ServiceException(e.getMessage(), e);
        }
    }

    @Override
    @Transactional
    public void update(Wechat wechat){
        try{
            Assert.notNull(wechat, "微信信息不能为空");
            Assert.hasText(wechat.getId(), "微信ID不能为空");

            wechatDao.saveOrUpdate(wechat);
        }catch(Exception e){
            logger.error(e.getMessage(), e);
            throw new ServiceException(e.getMessage(), e);
        }
    }

    @Override
    public Wechat getById(String id){
        try{
            Assert.hasText(id, "微信ID不能为空");

            return wechatDao.getById(id);
        }catch(Exception e){
            logger.error(e.getMessage(), e);
            throw new ServiceException(e.getMessage(), e);
        }
    }

    public Wechat getByOrganizationIdAndWxno(String organizationId, String wxno){
        try{
            Assert.hasText(organizationId, "微信所属企业ID不能为空");
            Assert.hasText(wxno, "微信号不能为空");

            return wechatDao.getByOrganizationIdAndWxno(organizationId, wxno);
        }catch(Exception e){
            logger.error(e.getMessage(), e);
            throw new ServiceException(e.getMessage(), e);
        }
    }

    @Override
    public Page<Wechat> page(PageRequest pageRequest, String organizationId, String wxno, String nickname, String remark,
                             String seller, String advisorId){
        try{
            Assert.notNull(pageRequest, "分页信息不能为空");

            return wechatDao.page(pageRequest, organizationId, wxno, nickname, remark, seller, advisorId);
        }catch(Exception e){
            logger.error(e.getMessage(), e);
            throw new ServiceException(e.getMessage(), e);
        }
    }

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
            String uploadPath = UpyunUtils.WECHAT_HEAD_DIR + name;
            //上传
            boolean result = UpyunUtils.upload(uploadPath, file);

            return result ? UpyunUtils.URL + uploadPath : "";
        }catch(Exception e){
            logger.error(e.getMessage(), e);
            throw new ServiceException(e.getMessage(), e);
        }
    }
}
