package com.lab.hosaily.core.app.service;

import com.lab.hosaily.commons.utils.FileNameUtils;
import com.lab.hosaily.commons.utils.UpyunUtils;
import com.lab.hosaily.core.app.dao.OrderDao;
import com.lab.hosaily.core.app.entity.Order;
import com.lab.hosaily.core.app.entity.OrderProfile;
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
public class OrderServiceImpl implements OrderService{

    private static Logger logger = LoggerFactory.getLogger(OrderServiceImpl.class);

    @Autowired
    private OrderDao orderDao;

    /**
     * 保存记录
     */
    @Override
    @Transactional(readOnly = false)
    public void save(Order order){
        try{
            Assert.notNull(order, "导师信息不能为空");

            orderDao.saveOrUpdate(order);
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
    public void update(Order order){
        try{
            Assert.notNull(order, "导师信息不能为空");
            Assert.hasText(order.getId(), "导师ID不能为空");

            orderDao.saveOrUpdate(order);
        }catch(Exception e){
            logger.error(e.getMessage(), e);
            throw new ServiceException(e.getMessage(), e);
        }
    }

    /**
     * 根据ID查询记录
     */
    @Override
    public Order getByAgreementId(String agreementId){
        try{
            Assert.hasText(agreementId, "合同ID不能为空");

            return orderDao.getByAgreementId(agreementId);
        }catch(Exception e){
            logger.error(e.getMessage(), e);
            throw new ServiceException(e.getMessage(), e);
        }
    }


    /**
     * 根据ID查询记录
     */
    @Override
    public Order getById(String id, String advisorStatus){
        try{
            Assert.hasText(id, "客户档案ID不能为空");

            return orderDao.getById(id, advisorStatus);
        }catch(Exception e){
            logger.error(e.getMessage(), e);
            throw new ServiceException(e.getMessage(), e);
        }
    }

    @Override
    public List<OrderProfile> getOrderProfileById(String orderId) {
        return orderDao.getOrderProfileById(orderId);
    }

    /**
     * 分页查询
     */
    @Override
    public Page<Order> page(PageRequest pageRequest, String sellerId, String advisorName, String assign, String mixSearch, String startTime, String endTime){
        try{
            Assert.notNull(pageRequest, "分页信息不能为空");

            return orderDao.page(pageRequest, sellerId, advisorName, assign, mixSearch, startTime, endTime);
        }catch(Exception e){
            logger.error(e.getMessage(), e);
            throw new ServiceException(e.getMessage(), e);
        }
    }

    /**
     * 查询列表
     */
    @Override
    public List<Order> list(String nickname, String name, UsingState state, String organizationId, String organizationToken){
        try{
            return orderDao.list(nickname, name, state, organizationId, organizationToken);
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

    @Override
    public List<Order> countOrderByParam(String startTime, String endTime, String countType, String sellerId){
        try{
            return orderDao.countOrderByParam(startTime, endTime, countType, sellerId);
        }catch(Exception e){
            logger.error(e.getMessage(), e);
            throw new ServiceException(e.getMessage(), e);
        }
    }
}
