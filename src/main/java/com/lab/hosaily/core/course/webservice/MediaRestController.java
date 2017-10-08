package com.lab.hosaily.core.course.webservice;

import com.lab.hosaily.commons.utils.MediaUtils;
import com.lab.hosaily.core.course.entity.Media;
import com.lab.hosaily.core.course.service.MediaService;
import com.rab.babylon.commons.security.exception.ApplicationException;
import com.rab.babylon.commons.security.exception.ResourceNotFoundException;
import com.rab.babylon.commons.security.response.Page;
import com.rab.babylon.commons.security.response.PageRequest;
import com.rab.babylon.commons.security.response.Response;
import com.rab.babylon.commons.utils.HttpDownloadUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.MessageFormat;

@RestController
@RequestMapping(value = "/api/1.0/media")
public class MediaRestController{

    private static Logger logger = LoggerFactory.getLogger(MediaRestController.class);

    @Autowired
    private MediaService mediaService;

    /**
     * 更新记录
     */
    @RequestMapping(value = "", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Response<Media> update(@RequestBody Media media){
        try{
            mediaService.update(media);
            return new Response<Media>("修改成功", media);
        }catch(Exception e){
            logger.error(e.getMessage(), e);
            throw new ApplicationException(e.getMessage(), e);
        }
    }

    /**
     * 分页查询
     */
    @RequestMapping(value = "/page", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public Response<Page<Media>> page(Long pageNum, Long pageSize){
        try{
            PageRequest pageRequest = new PageRequest(pageNum, pageSize);
            Page<Media> page = mediaService.page(pageRequest);

            return new Response<Page<Media>>("查询成功", page);
        }catch(Exception e){
            logger.error(e.getMessage(), e);
            throw new ApplicationException(e.getMessage(), e);
        }
    }

    /**
     * 根据ID查询
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public Response<Media> getById(@PathVariable("id") String id){
        try{
            Media media = mediaService.getById(id);
            return new Response<Media>("查询成功", media);
        }catch(Exception e){
            logger.error(e.getMessage(), e);
            throw new ApplicationException(e.getMessage(), e);
        }
    }

    /**
     * 下载媒体文件
     */
    @RequestMapping(value = "/download/{md5}", method = RequestMethod.GET)
    public void download(HttpServletRequest request, HttpServletResponse response, @PathVariable("md5") String md5){
        try{
            Media media = mediaService.getByMd5(md5);

            if(media == null){
                throw new ResourceNotFoundException(MessageFormat.format("找不到关于\"{0}\"的资源", md5));
            }

            //项目路径
            String path = request.getServletContext().getRealPath("/");
            //上传目录
            String dir = MediaUtils.getDir(media.getType());
            //上传路径
            String uploadPath = path + dir;
            //文件名称
            String name = media.getFileName() + media.getSuffix();

            HttpDownloadUtils download = new HttpDownloadUtils(request, response, uploadPath + name);
            download.download();
        }catch(Exception e){
            logger.error(e.getMessage(), e);
            throw new ApplicationException(e.getMessage(), e);
        }
    }

    /**
     * 上传媒体文件
     */
    @RequestMapping(value = "/upload", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public Response<Media> upload(HttpServletRequest request, @RequestParam("file") CommonsMultipartFile file){
        try{
            String url = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort();
            String path = request.getServletContext().getRealPath("/");

            Media media = mediaService.upload(path, url, file);

            return new Response<Media>("上传成功", media);
        }catch(Exception e){
            logger.error(e.getMessage(), e);
            throw new ApplicationException(e.getMessage(), e);
        }
    }
}
