package com.lab.hosaily.core.app.service;

import com.lab.hosaily.core.app.entity.InviteWeChatAccount;
import com.rab.babylon.commons.security.response.Page;
import com.rab.babylon.commons.security.response.PageRequest;
import com.rab.babylon.core.consts.entity.UsingState;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import java.util.List;

public interface InviteWeChatAccountService {
    Page<InviteWeChatAccount> page(PageRequest pageRequest, String sellerId, String advisorId);
}
