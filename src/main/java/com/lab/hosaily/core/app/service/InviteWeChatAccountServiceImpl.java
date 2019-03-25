package com.lab.hosaily.core.app.service;

import com.lab.hosaily.commons.utils.FileNameUtils;
import com.lab.hosaily.commons.utils.UpyunUtils;
import com.lab.hosaily.core.app.dao.InviteWeChatAccountDao;
import com.lab.hosaily.core.app.entity.InviteWeChatAccount;
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
public class InviteWeChatAccountServiceImpl implements InviteWeChatAccountService{

    private static Logger logger = LoggerFactory.getLogger(InviteWeChatAccountServiceImpl.class);

    @Autowired
    private InviteWeChatAccountDao inviteWeChatAccountDao;

    /**
     * 分页查询
     */
    @Override
    public Page<InviteWeChatAccount> page(PageRequest pageRequest, String sellerId, String advisorId){
        try{
            Assert.notNull(pageRequest, "分页信息不能为空");

            return inviteWeChatAccountDao.page(pageRequest, sellerId, advisorId);
        }catch(Exception e){
            logger.error(e.getMessage(), e);
            throw new ServiceException(e.getMessage(), e);
        }
    }
}
