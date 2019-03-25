package com.lab.hosaily.core.app.webservice;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import com.lab.hosaily.commons.utils.FileNameUtils;
import com.lab.hosaily.commons.utils.UpyunUtils;
import com.lab.hosaily.core.app.entity.Poster;
import com.lab.hosaily.core.app.entity.Profile;
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
import org.springframework.web.bind.annotation.PathVariable;
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
@RequestMapping(value = "/api/1.0/app/poster")
@Api(description = "客户动态资料")
public class PosterRestController {

    @Autowired
    private PosterService posterService;

    @Autowired
    private ProfileService profileService;

    final int width = 300;
    final int height = 300;
    final String format = "png";
    String content = "http://www.baidu.com";

//    http://m.elletter.com/app/?sellerId='+requestData.sellerId+'&advisorId='+requestData.advisorId+'#/shareQrcodeCode

    //    http://localhost:8080/swagger-ui.html
    private static final Logger logger = LoggerFactory.getLogger(PosterRestController.class);

    @ApiIgnore
    @RequestMapping(value = "/save", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public Response<Poster> save() {
        try {
            Poster poster = new Poster();
            poster.setOrganizationId("setOrganizationId");
//            Profile profile = new Profile();
//            profile.setId("72b9929558a545fe8524a28630793924");
            poster.setSellerId("72b9929558a545fe8524a28630793924");
            poster.setAdvisorId("72b9929558a545fe8524a28630793924");
            poster.setCompoundUrl("setCompoundUrl");
            poster.setCompoundImgUrl("setCompoundImgUrl");
            posterService.save(poster);
            return new Response<Poster>("添加成功", poster);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new ApplicationException(e.getMessage(), e);
        }
    }

    @ApiIgnore
    @RequestMapping(value = "/update", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public Response<Poster> update() {
        try {
            Poster poster = posterService.getBySellerIdAndAdvisorId("72b9929558a545fe8524a28630793924", "72b9929558a545fe8524a28630793924");
            poster.setOrganizationId("setOrganizationId2");
            poster.setCompoundUrl("setCompoundUrl2");
            poster.setCompoundImgUrl("setCompoundImgUrl2");
            posterService.update(poster);
            return new Response<Poster>("修改成功", poster);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new ApplicationException(e.getMessage(), e);
        }
    }

    @RequestMapping(value = "/getBySellerIdAndAdvisorId", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public Response<Poster> getBySellerIdAndAdvisorId(String sellerId, String advisorId) {
        try {
            Poster poster = posterService.getBySellerIdAndAdvisorId(sellerId, advisorId);
            return new Response<Poster>("查询成功", poster);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new ApplicationException(e.getMessage(), e);
        }
    }

    @ApiOperation(value = "客户资料", notes = "在这里可以获取分页获取客户朋友圈动态")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "profileId", value = "爱情档案ID", paramType = "query", required = true, dataType = "String"),
            @ApiImplicitParam(name = "pageNum", value = "页码", paramType = "query", required = true, dataType = "Long"),
            @ApiImplicitParam(name = "pageSize", value = "页大小", paramType = "query", required = true, dataType = "Long")
    })
    @RequestMapping(value = "/page", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public Response<Page<Poster>> page(Long pageNum, Long pageSize, String profileId) {
        try {
            System.out.println("啊实打实大师大师大师大所多撒");
            PageRequest pageRequest = new PageRequest(pageNum, pageSize);
            Page<Poster> page = posterService.page(pageRequest, profileId);

            return new Response<Page<Poster>>("查询成功", page);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new ApplicationException(e.getMessage(), e);
        }
    }


    @ApiOperation(value = "提交上传客户资料", notes = "图片url用';'号隔开")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "picUrls", value = "图片url用';'分隔", paramType = "query", required = true, dataType = "String"),
            @ApiImplicitParam(name = "comment", value = "备注文本", paramType = "query", required = true, dataType = "String"),
            @ApiImplicitParam(name = "uploaderId", value = "上传者profileID", paramType = "query", required = true, dataType = "String"),
            @ApiImplicitParam(name = "profileId", value = "当前浏览客户爱情档案ID", paramType = "query", required = true, dataType = "String")
    })
    @RequestMapping(value = "/upload", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public Response<String> upload(String picUrls, String comment, String uploaderId, String profileId) {
        try {
            Response<String> response = new Response<String>();


            if (StringUtils.isBlank(uploaderId)) {
                response.setSuccess(false);
                response.setMsg("uploaderId不能为空！");
                return response;
            }
            if (StringUtils.isBlank(profileId)) {
                response.setSuccess(false);
                response.setMsg("profileId不能为空！");
                return response;
            }


            Poster poster = new Poster();
//            poster.setPicUrls(picUrls);
//            poster.setProfileId(profileId);
//            poster.setComment(comment);
//            Profile profile = profileService.getById(uploaderId);
//            System.out.println("aaaaaaaaaaaaaaa: " + profile.getName());
//            poster.setUploader(profile);
            posterService.save(poster);
            response.setSuccess(true);
            response.setMsg("提交成功");
            return response;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new ApplicationException(e.getMessage(), e);
        }
    }


    @RequestMapping(value = "/compoundQRCode", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public Response<String> compoundQRCode(String sellerId) {
        try {
            Response<String> response = new Response<String>();
            if (StringUtils.isBlank(sellerId)) {
                response.setSuccess(false);
                response.setMsg("sellerId不能为空！");
                return response;
            }
//            if (StringUtils.isBlank(advisorId)) {
//                response.setSuccess(false);
//                response.setMsg("advisorId不能为空！");
//                return response;
//            }
            Poster poster = posterService.getBySellerId(sellerId);

                //定义二维码的参数
                HashMap hints = new HashMap();
                hints.put(EncodeHintType.CHARACTER_SET, "utf-8");
                hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.M);
                hints.put(EncodeHintType.MARGIN, 2);

//                content = content + "?sellerId=" + sellerId + "&advisorId=" + advisorId;
//                http://m.elletter.com/app/?sellerId='+requestData.sellerId+'&advisorId='+requestData.advisorId+'#/shareQrcodeCode
                content = "http://m.elletter.com/app/?sellerId=" + sellerId + "#/shareQrcode";

                //生成二维码
                File filePath = new File(CreateQRCode.class.getResource("/").getPath() + "tem/");
                if (!filePath.exists()) {
                    filePath.mkdir();
                }
                //OutputStream stream = new OutputStreamWriter();
                BitMatrix bitMatrix = new MultiFormatWriter().encode(content, BarcodeFormat.QR_CODE, width, height, hints);
                File file = new File(CreateQRCode.class.getResource("/").getPath() + "tem/" + sellerId + ".png");
                MatrixToImageWriter.writeToFile(bitMatrix, format, file);
                TwoComposePic.composePicUrl("http://kuliao.b0.upaiyun.com/advisor/head/4d84b9274ab3c86d125d640dd7f3ab37.jpg", CreateQRCode.class.getResource("/").getPath() + "tem/" + sellerId + ".png",
                        CreateQRCode.class.getResource("/").getPath() + "tem/" + "compound_" + sellerId + ".png");

                byte[] fileByte = CreateQRCode.File2byte(CreateQRCode.class.getResource("/").getPath() + "tem/" + "compound_" + sellerId + ".png");
                //MD5
                String md5 = FileUtils.getMD5(fileByte);
                //文件后缀
                String suffix = FileNameUtils.getSuffix("compound_" + sellerId + ".png");
                //上传名称
                String name = md5 + suffix;
                //上传路径
                String uploadPath = UpyunUtils.QR_Code_DIR + "compound_" + sellerId + ".png";
                //上传
                boolean result = UpyunUtils.upload(uploadPath, fileByte);
                System.out.println("uploadPathuploadPath: " + uploadPath);
                System.out.println("resultresult: " + UpyunUtils.URL + uploadPath);

                //MatrixToImageWriter.writeToStream(bitMatrix, format, stream);


                System.out.println("CreateQRCode: " + CreateQRCode.class.getResource("/").getPath() + "tem/" + sellerId + ".png");
                System.out.println("CreateQRCode: " + CreateQRCode.class.getResource("/").getPath() + "tem/" + "compound_" + sellerId + ".png");
                File qrCodeDelete = new File(CreateQRCode.class.getResource("/").getPath() + "tem/" + sellerId + ".png");
                File compoundDelete = new File(CreateQRCode.class.getResource("/").getPath() + "tem/" + "compound_" + sellerId + ".png");
                if (qrCodeDelete.exists()) {
                    qrCodeDelete.delete();
                }
                if (compoundDelete.exists()) {
                    compoundDelete.delete();
                }
            if (null == poster) {
                poster = new Poster();
                poster.setSellerId(sellerId);
//                poster.setAdvisorId(advisorId);
                poster.setCompoundUrl(content);
                poster.setCompoundImgUrl(UpyunUtils.URL + uploadPath);
                posterService.save(poster);
            }else{
                poster.setCompoundUrl(content);
                poster.setCompoundImgUrl(UpyunUtils.URL + uploadPath);
                posterService.update(poster);
            }
            response.setResult(poster.getCompoundImgUrl());
            response.setSuccess(true);
            response.setMsg("提交成功");
            return response;

        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new ApplicationException(e.getMessage(), e);
        }
    }
}
