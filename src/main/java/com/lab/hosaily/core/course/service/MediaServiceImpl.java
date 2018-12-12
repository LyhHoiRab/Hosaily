package com.lab.hosaily.core.course.service;

import com.lab.hosaily.commons.utils.FileNameUtils;
import com.lab.hosaily.commons.utils.MediaUtils;
import com.lab.hosaily.commons.utils.UpyunUtils;
import com.lab.hosaily.core.course.consts.MediaType;
import com.lab.hosaily.core.course.consts.SuffixType;
import com.lab.hosaily.core.course.dao.MediaDao;
import com.lab.hosaily.core.course.entity.Media;
import com.rab.babylon.commons.security.exception.ServiceException;
import com.rab.babylon.commons.security.response.Page;
import com.rab.babylon.commons.security.response.PageRequest;
import com.rab.babylon.commons.utils.FileUtils;
import com.rab.babylon.commons.utils.UUIDGenerator;
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
public class MediaServiceImpl implements MediaService {

    private static Logger logger = LoggerFactory.getLogger(MediaServiceImpl.class);

    @Autowired
    private MediaDao mediaDao;

    /**
     * 保存记录
     */
    @Override
    @Transactional(readOnly = false)
    public void save(Media media) {
        try {
            Assert.notNull(media, "媒体信息不能为空");
            Assert.hasText(media.getMd5(), "媒体MD5不能为空");

            mediaDao.saveOrUpdate(media);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new ServiceException(e.getMessage(), e);
        }
    }

    /**
     * 更新记录
     */
    @Override
    @Transactional(readOnly = false)
    public void update(Media media) {
        try {
            Assert.notNull(media, "媒体信息不能为空");
//            Assert.hasText(media.getId(), "媒体ID不能为空");
            if (null == media.getId()) {
                media.setId(UUIDGenerator.by32());
            }

            mediaDao.saveOrUpdate(media);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new ServiceException(e.getMessage(), e);
        }
    }

    /**
     * 根据ID查询记录
     */
    @Override
    public Media getById(String id) {
        try {
            Assert.hasText(id, "媒体ID不能为空");

            return mediaDao.getById(id);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new ServiceException(e.getMessage(), e);
        }
    }

    /**
     * 根据MD5查询记录
     */
    @Override
    public Media getByMd5(String md5) {
        try {
            Assert.hasText(md5, "媒体MD5不能为空");

            return mediaDao.getByMd5(md5);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new ServiceException(e.getMessage(), e);
        }
    }

    /**
     * 分页查询
     */
    @Override
    public Page<Media> page(PageRequest pageRequest, String remark, MediaType type, String suffix, String organizationId, String organizationToken, UsingState state) {
        try {
            Assert.notNull(pageRequest, "分页信息不能为空");

            return mediaDao.page(pageRequest, remark, type, suffix, organizationId, organizationToken, state);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new ServiceException(e.getMessage(), e);
        }
    }

    /**
     * 查询列表
     */
    @Override
    public List<Media> list(String remark, MediaType type, String suffix, String organizationId, String organizationToken, UsingState state) {
        try {
            return mediaDao.list(remark, type, suffix, organizationId, organizationToken, state);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new ServiceException(e.getMessage(), e);
        }
    }

    /**
     * 上传媒体文件
     */
    @Override
    @Transactional(readOnly = false)
    public Media upload(String path, String url, CommonsMultipartFile file) {
        try {
//            Assert.hasText(path, "上传路径不能为空");
            Assert.notNull(file, "上传文件不能为空");
//            Assert.notNull(url, "资源外链不能为空");

            //文件原名称
            String originalName = file.getOriginalFilename();
            //文件大小
            long size = file.getSize();
            //MD5
            String md5 = FileUtils.getMD5(file.getBytes());
            //后缀
            String suffix = FileNameUtils.getSuffix(originalName);
            //媒体类型
            SuffixType suffixType = SuffixType.getById(suffix);
            //上传目录
            String dir = MediaUtils.getDir(suffixType.getType());
            //上传路径
            String uploadPath = UpyunUtils.COURSE_MEDIA_DIR + md5 + suffixType.getId();

            //上传到upyun
            boolean result = UpyunUtils.uploadVideo(uploadPath, file);

            if (result) {
                //查询文件是否已上传
                Media media = mediaDao.getByMd5(md5);
                if (media == null) {
                    media = new Media();
                    media.setFileName(md5);
                }

                //设置媒体信息
                media.setSize(size);
                media.setMd5(md5);
                media.setSuffix(suffix);
                media.setType(suffixType.getType());
                media.setUrl(UpyunUtils.VIDEO_URL + uploadPath);
                //保存信息
                mediaDao.saveOrUpdate(media);

                return media;
            }

            return null;

            //上传路径
//            String uploadPath = path + dir;
//            //创建目录
//            File root = new File(uploadPath);
//            if(!root.exists() || !root.isDirectory()){
//                root.mkdir();
//            }
//
//            //查询文件是否已上传
//            Media media = mediaDao.getByMd5(md5);
//            if(media != null){
//                //删除原文件
//                String name = media.getFileName() + media.getSuffix();
//                File source = new File(path + MediaUtils.getDir(media.getType()) + name);
//                if(source.exists()){
//                    source.delete();
//                }
//            }else{
//                //创建记录
//                media = new Media();
//                media.setFileName(UUIDGenerator.by32());
//            }
//
//            //设置媒体信息
//            media.setSize(size);
//            media.setMd5(md5);
//            media.setSuffix(suffix);
//            media.setType(suffixType.getType());
//            media.setUrl(url + MediaUtils.DOWNLOAD_API + media.getMd5());
//
//            //上传文件
//            file.transferTo(new File(uploadPath + media.getFileName() + media.getSuffix()));
//            //保存信息
//            mediaDao.saveOrUpdate(media);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new ServiceException(e.getMessage(), e);
        }
    }


    /**
     * 上传媒体文件
     */
    @Override
    @Transactional(readOnly = false)
    public Media newUpload(String path, String url, String originalName, Long size, String md5) {
        try {
            //后缀
            String suffix = FileNameUtils.getSuffix(originalName);
            //媒体类型
            SuffixType suffixType = SuffixType.getById(suffix);
            //上传目录
            String dir = MediaUtils.getDir(suffixType.getType());



            //查询文件是否已上传
            Media media = mediaDao.getByMd5(md5);
            if (media == null) {
                media = new Media();
                media.setFileName(md5);
            }

            //设置媒体信息
            media.setSize(size);
            media.setMd5(md5);
            media.setSuffix(suffix);
            media.setType(suffixType.getType());
            media.setUrl(path);
            //保存信息
            mediaDao.saveOrUpdate(media);

            return media;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new ServiceException(e.getMessage(), e);
        }
    }
}
