package com.lab.hosaily.core.account.service;

import com.lab.hosaily.commons.utils.FileNameUtils;
import com.lab.hosaily.commons.utils.UpyunUtils;
import com.lab.hosaily.core.account.dao.UserDao;
import com.rab.babylon.commons.security.exception.ServiceException;
import com.rab.babylon.commons.security.response.Page;
import com.rab.babylon.commons.security.response.PageRequest;
import com.rab.babylon.commons.utils.FileUtils;
import com.rab.babylon.core.account.entity.User;
import com.rab.babylon.core.consts.entity.Sex;
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
public class UserServiceImpl implements UserService{

    private static Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    private UserDao userDao;

    /**
     * 保存记录
     */
    @Override
    @Transactional(readOnly = false)
    public void save(User user){
        try{
            Assert.notNull(user, "用户信息不能为空");

            userDao.saveOrUpdate(user);
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
    public void update(User user){
        try{
            Assert.notNull(user, "用户信息不能为空");
            Assert.hasText(user.getId(), "用户ID不能为空");

            userDao.saveOrUpdate(user);
        }catch(Exception e){
            logger.error(e.getMessage(), e);
            throw new ServiceException(e.getMessage(), e);
        }
    }

    /**
     * 修改用户信息
     */
    @Override
    @Transactional(readOnly = false)
    public void updateByH5(User user){
        try{
            Assert.notNull(user, "用户信息不能为空");
            Assert.hasText(user.getAccountId(), "账户ID不能为空");

            User source = userDao.getCacheByAccountId(user.getAccountId());
            user.setId(source.getId());
            //更新数据库
            userDao.saveOrUpdate(user);
            //更新缓存
            userDao.cache(user);
        }catch(Exception e){
            logger.error(e.getMessage(), e);
            throw new ServiceException(e.getMessage(), e);
        }
    }

    /**
     * 上传头像
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
            String uploadPath = UpyunUtils.USER_HEAD_DIR + name;
            //上传
            boolean result = UpyunUtils.upload(uploadPath, file);

            return result ? UpyunUtils.URL + uploadPath : "";
        }catch(Exception e){
            logger.error(e.getMessage(), e);
            throw new ServiceException(e.getMessage(), e);
        }
    }

    /**
     * 根据ID查询记录
     */
    @Override
    public User getById(String id){
        try{
            Assert.hasText(id, "用户ID不能为空");

            return userDao.getById(id);
        }catch(Exception e){
            logger.error(e.getMessage(), e);
            throw new ServiceException(e.getMessage(), e);
        }
    }

    /**
     * 根据accountId查询用户缓存信息
     */
    @Override
    public User getCacheByAccountId(String accountId){
        try{
            Assert.hasText(accountId, "账号ID不能为空");

            return userDao.getCacheByAccountId(accountId);
        }catch(Exception e){
            logger.error(e.getMessage(), e);
            throw new ServiceException(e.getMessage(), e);
        }
    }

    /**
     * 分页查询
     */
    @Override
    public Page<User> page(PageRequest pageRequest, Sex sex, UsingState state, String wechat, String nickname, String name, Integer code, String organizationId, String organizationToken){
        try{
            Assert.notNull(pageRequest, "分页信息不能为空");

            return userDao.page(pageRequest, sex, state, wechat, nickname, name, code, organizationId, organizationToken);
        }catch(Exception e){
            logger.error(e.getMessage(), e);
            throw new ServiceException(e.getMessage(), e);
        }
    }

    /**
     * 查询列表
     */
    @Override
    public List<User> list(Sex sex, UsingState state, String wechat, String nickname, String name, Integer code, String organizationId, String organizationToken){
        try{
            return userDao.list(sex, state, wechat, nickname, name, code, organizationId, organizationToken);
        }catch(Exception e){
            logger.error(e.getMessage(), e);
            throw new ServiceException(e.getMessage(), e);
        }
    }
}
