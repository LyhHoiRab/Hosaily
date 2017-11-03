package com.lab.hosaily.core.sell.service;

import com.lab.hosaily.commons.utils.FileNameUtils;
import com.lab.hosaily.commons.utils.UpyunUtils;
import com.lab.hosaily.core.sell.dao.PaymentTypeDao;
import com.lab.hosaily.core.sell.entity.PaymentType;
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
public class PaymentTypeServiceImpl implements PaymentTypeService{

    private static Logger logger = LoggerFactory.getLogger(PaymentTypeServiceImpl.class);

    @Autowired
    private PaymentTypeDao paymentTypeDao;

    /**
     * 保存
     */
    @Transactional(readOnly = false)
    @Override
    public void save(PaymentType paymentType){
        try{
            Assert.notNull(paymentType, "支付方式信息不能为空");

            paymentTypeDao.saveOrUpdate(paymentType);
        }catch(Exception e){
            logger.error(e.getMessage(), e);
            throw new ServiceException(e.getMessage(), e);
        }
    }

    /**
     * 更新
     */
    @Transactional(readOnly = false)
    @Override
    public void update(PaymentType paymentType){
        try{
            Assert.notNull(paymentType, "支付方式信息不能为空");
            Assert.hasText(paymentType.getId(), "支付方式ID不能为空");

            paymentTypeDao.saveOrUpdate(paymentType);
        }catch(Exception e){
            logger.error(e.getMessage(), e);
            throw new ServiceException(e.getMessage(), e);
        }
    }

    /**
     * 根据ID查询
     */
    @Override
    public PaymentType getById(String id){
        try{
            Assert.hasText(id, "支付方式ID不能为空");

            return paymentTypeDao.getById(id);
        }catch(Exception e){
            logger.error(e.getMessage(), e);
            throw new ServiceException(e.getMessage(), e);
        }
    }

    /**
     * 分页查询
     */
    @Override
    public Page<PaymentType> page(PageRequest pageRequest, UsingState state, String name){
        try{
            Assert.notNull(pageRequest, "分页信息不能为空");

            return paymentTypeDao.page(pageRequest, state, name);
        }catch(Exception e){
            logger.error(e.getMessage(), e);
            throw new ServiceException(e.getMessage(), e);
        }
    }

    /**
     * 查询列表
     */
    @Override
    public List<PaymentType> list(UsingState state, String name){
        try{
            return paymentTypeDao.list(state, name);
        }catch(Exception e){
            logger.error(e.getMessage(), e);
            throw new ServiceException(e.getMessage(), e);
        }
    }

    /**
     * 上传
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
            String uploadPath = UpyunUtils.PAYMENT_TYPE_COVER_DIR + name;
            //上传
            boolean result = UpyunUtils.upload(uploadPath, file);

            return result ? UpyunUtils.URL + uploadPath : "";
        }catch(Exception e){
            logger.error(e.getMessage(), e);
            throw new ServiceException(e.getMessage(), e);
        }
    }
}
