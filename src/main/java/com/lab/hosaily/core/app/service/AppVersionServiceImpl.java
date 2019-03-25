package com.lab.hosaily.core.app.service;


import com.lab.hosaily.core.app.dao.AppVersionDao;
import com.lab.hosaily.core.app.entity.AppVersion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.wah.doraemon.security.request.Page;
import org.wah.doraemon.security.request.PageRequest;

@Service
@Transactional(readOnly = true)
public class AppVersionServiceImpl implements AppVersionService {

    @Autowired
    private AppVersionDao appVersionDao;

    @Override
    @Transactional
    public void save(AppVersion appVersion) {
        Assert.notNull(appVersion, "用户信息不能为空");
        appVersionDao.save(appVersion);
    }

    @Override
    @Transactional
    public void update(AppVersion appVersion) {
        Assert.notNull(appVersion, "用户信息不能为空");
        Assert.hasText(appVersion.getId(), "Id不能为空");
        appVersionDao.update(appVersion);
    }

    @Override
    public AppVersion getByPhone(String phone) {
        return appVersionDao.getByPhone(phone);
    }

    @Override
    public AppVersion getNewestVersion() {
        return appVersionDao.getNewestVersion();
    }

    @Override
    public Page<AppVersion> page(PageRequest pageRequest, String name,
                               String situation, String startTime, String endTime, String process, String follower) {
        Assert.notNull(pageRequest, "分页信息不能为空");
        return appVersionDao.page(pageRequest, name,
                situation, startTime, endTime, process, follower);
    }
}
