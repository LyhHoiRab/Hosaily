package com.lab.hosaily.core.app.service;


import com.lab.hosaily.core.app.dao.LoginUserDao;
import com.lab.hosaily.core.app.entity.LoginUser;
import com.lab.hosaily.core.app.utils.ReadExcel;
import com.rab.babylon.core.consts.entity.Sex;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.web.multipart.MultipartFile;
import org.wah.doraemon.security.request.Page;
import org.wah.doraemon.security.request.PageRequest;
import redis.clients.jedis.ShardedJedisPool;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional(readOnly = true)
public class LoginUserServiceImpl implements LoginUserService {

    @Autowired
    private LoginUserDao loginUserDao;

    @Override
    @Transactional
    public void save(LoginUser loginUser) {
        Assert.notNull(loginUser, "用户信息不能为空");
        loginUserDao.save(loginUser);
    }

    @Override
    @Transactional
    public void update(LoginUser loginUser) {
        Assert.notNull(loginUser, "用户信息不能为空");
        Assert.hasText(loginUser.getId(), "Id不能为空");
        loginUserDao.update(loginUser);
    }

    @Override
    public LoginUser getByPhone(String phone) {
        return loginUserDao.getByPhone(phone);
    }

    @Override
    public Page<LoginUser> page(PageRequest pageRequest, String name,
                               String situation, String startTime, String endTime, String process, String follower) {
        Assert.notNull(pageRequest, "分页信息不能为空");
        return loginUserDao.page(pageRequest, name,
                situation, startTime, endTime, process, follower);
    }
}
