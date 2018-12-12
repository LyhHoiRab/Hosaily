package com.lab.hosaily.core.popularize.service;

import com.lab.hosaily.core.popularize.entity.Wechat;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.wah.doraemon.security.request.Page;
import org.wah.doraemon.security.request.PageRequest;

public interface WechatService{

    void save(Wechat wechat);

    void update(Wechat wechat);

    Wechat getById(String id);

    Wechat getByOrganizationIdAndWxno(String organizationId, String wxno);

    Page<Wechat> page(PageRequest pageRequest, String organizationId, String wxno, String nickname, String remark,
                      String seller, String advisorId);

    String upload(CommonsMultipartFile file);
}
