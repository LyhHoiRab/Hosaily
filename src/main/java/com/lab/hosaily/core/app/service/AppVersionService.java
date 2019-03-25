package com.lab.hosaily.core.app.service;



import com.lab.hosaily.core.app.entity.AppVersion;
import org.wah.doraemon.security.request.Page;
import org.wah.doraemon.security.request.PageRequest;

public interface AppVersionService {

    void save(AppVersion appVersion);

    void update(AppVersion appVersion);

    AppVersion getByPhone(String phone);

    AppVersion getNewestVersion();

    Page<AppVersion> page(PageRequest pageRequest, String name,
                         String situation, String startTime, String endTime, String process, String follower);
}
