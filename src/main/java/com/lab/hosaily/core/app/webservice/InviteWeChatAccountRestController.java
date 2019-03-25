package com.lab.hosaily.core.app.webservice;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import com.lab.hosaily.commons.utils.FileNameUtils;
import com.lab.hosaily.commons.utils.UpyunUtils;
import com.lab.hosaily.core.app.entity.InviteWeChatAccount;
import com.lab.hosaily.core.app.entity.Poster;
import com.lab.hosaily.core.app.service.InviteWeChatAccountService;
import com.lab.hosaily.core.app.service.PosterService;
import com.lab.hosaily.core.app.service.ProfileService;
import com.lab.hosaily.core.app.utils.CreateQRCode;
import com.lab.hosaily.core.app.utils.MatrixToImageWriter;
import com.lab.hosaily.core.app.utils.TwoComposePic;
import com.rab.babylon.commons.security.exception.ApplicationException;
import com.rab.babylon.commons.security.response.Page;
import com.rab.babylon.commons.security.response.PageRequest;
import com.rab.babylon.commons.security.response.Response;
import com.rab.babylon.commons.utils.FileUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import java.io.File;
import java.util.HashMap;

/**
 * Created by Administrator on 2017/9/13.
 */
@RestController
@RequestMapping(value = "/api/1.0/app/inviteWeChatAccount")
@Api(description = "客户动态资料")
public class InviteWeChatAccountRestController {

    @Autowired
    private InviteWeChatAccountService inviteWeChatAccountService;

    private static final Logger logger = LoggerFactory.getLogger(InviteWeChatAccountRestController.class);

    @RequestMapping(value = "/page", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public Response<Page<InviteWeChatAccount>> page(Long pageNum, Long pageSize, String sellerId, String advisorId) {
        try {
            System.out.println("啊实打实大师大师大师大所多撒");
            PageRequest pageRequest = new PageRequest(pageNum, pageSize);
            Page<InviteWeChatAccount> page = inviteWeChatAccountService.page(pageRequest, sellerId, advisorId);
            return new Response<Page<InviteWeChatAccount>>("查询成功", page);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new ApplicationException(e.getMessage(), e);
        }
    }
}
