package com.lab.hosaily.core.application.webservice;

import com.lab.hosaily.core.application.service.WechatService;
import com.lab.hosaily.core.application.utils.XMLUtils;
import com.lab.hosaily.core.application.utils.consts.WechatXMLConsts;
import com.rab.babylon.commons.security.exception.ApplicationException;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

@RestController
@RequestMapping(value = "/api/1.0/wechat")
public class WechatRestController{

    private static Logger logger = LoggerFactory.getLogger(WechatRestController.class);

    @Autowired
    private WechatService wechatService;

    /**
     * 微信公众号平台接入接口
     */
    @RequestMapping(value = "/{token}", method = {RequestMethod.GET, RequestMethod.POST}, produces = MediaType.APPLICATION_JSON_VALUE)
    public String wechat(HttpServletRequest request, HttpServletResponse response, @PathVariable("token") String token){
        try{
            //微信账号接入校验
            String echostr = request.getParameter("echostr");
            if(!StringUtils.isBlank(echostr)){
                String signature = request.getParameter("signature");
                String nonce = request.getParameter("nonce");
                String timestamp = request.getParameter("timestamp");

                return wechatService.authorize(token, signature, timestamp, nonce) ? echostr : null;
            }

            //xml报文处理
            String xml = XMLUtils.read(request.getInputStream());
            if(!StringUtils.isBlank(xml)){
                //解析xml
                Map<String, Object> parse = XMLUtils.parse(xml);
                //是否加密
                if(XMLUtils.isEncrypt(xml)){
                    //提取加密节点和收件人
                    Map<String, Object> extract = XMLUtils.extract(xml);
//                    String key = wechatAccountService.getEncodingAESKeyByOriginalId((String) extract.get(WechatXMLConsts.NODE_TO_USER_NAME));
//                    parse = XMLUtils.decrypt((String) extract.get(WechatXMLConsts.NODE_ENCRYPT), key);
                }
            }

            //统一回复
            return "SUCCESS";
        }catch(Exception e){
            logger.error(e.getMessage(), e);
            throw new ApplicationException(e.getMessage(), e);
        }
    }
}
