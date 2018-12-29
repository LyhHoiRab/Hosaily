package com.lab.hosaily.core.app.service;



import com.lab.hosaily.core.app.entity.LoginUser;
import org.springframework.web.multipart.MultipartFile;
import org.wah.doraemon.security.request.Page;
import org.wah.doraemon.security.request.PageRequest;

import java.util.List;

public interface LoginUserService {

    void save(LoginUser loginUser);

    void update(LoginUser loginUser);

    LoginUser getByPhone(String phone);

    Page<LoginUser> page(PageRequest pageRequest, String name,
                        String situation, String startTime, String endTime, String process, String follower);
}
