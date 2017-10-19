package com.lab.hosaily.core.course.service;

import com.lab.hosaily.commons.utils.FileNameUtils;
import com.lab.hosaily.commons.utils.UpyunUtils;
import com.lab.hosaily.core.course.dao.CourseDao;
import com.lab.hosaily.core.course.entity.Course;
import com.rab.babylon.commons.security.exception.DataAccessException;
import com.rab.babylon.commons.security.exception.ServiceException;
import com.rab.babylon.commons.security.response.Page;
import com.rab.babylon.commons.security.response.PageRequest;
import com.rab.babylon.commons.utils.FileUtils;
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
public class CourseServiceImpl implements CourseService{

    private static Logger logger = LoggerFactory.getLogger(CourseServiceImpl.class);

    @Autowired
    private CourseDao courseDao;

    /**
     * 保存记录
     */
    @Override
    @Transactional(readOnly = false)
    public void save(Course course){
        try{
            Assert.notNull(course, "课程信息不能为空");

            courseDao.saveOrUpdate(course);
        }catch(Exception e){
            logger.error(e.getMessage(), e);
            throw new ServiceException(e.getMessage(), e);
        }
    }

    /**
     * 更新记录
     */
    @Override
    @Transactional(readOnly = false)
    public void update(Course course){
        try{
            Assert.notNull(course, "课程信息不能为空");
            Assert.hasText(course.getId(), "课程ID不能为空");

            courseDao.saveOrUpdate(course);
        }catch(Exception e){
            logger.error(e.getMessage(), e);
            throw new ServiceException(e.getMessage(), e);
        }
    }

    /**
     * 分页查询课程记录
     */
    @Override
    public Page<Course> pageByCourse(PageRequest pageRequest, String tagName){
        try{
            Assert.notNull(pageRequest, "分页信息不能为空");

            return courseDao.pageByCourse(pageRequest, tagName);
        }catch(Exception e){
            logger.error(e.getMessage(), e);
            throw new DataAccessException(e.getMessage(), e);
        }
    }

    /**
     * 分页查询帖子记录
     */
    @Override
    public Page<Course> pageByPost(PageRequest pageRequest){
        try{
            Assert.notNull(pageRequest, "分页信息不能为空");

            return courseDao.pageByPost(pageRequest);
        }catch(Exception e){
            logger.error(e.getMessage(), e);
            throw new DataAccessException(e.getMessage(), e);
        }
    }

    /**
     * 根据课程ID查询章节记录
     */
    @Override
    public List<Course> findChapterByCourseId(String courseId){
        try{
            Assert.hasText(courseId, "课程ID不能为空");

            return courseDao.findChapterByCourseId(courseId);
        }catch(Exception e){
            logger.error(e.getMessage(), e);
            throw new ServiceException(e.getMessage(), e);
        }
    }

    /**
     * 根据ID查询帖子
     */
    @Override
    public Course getPostById(String id){
        try{
            Assert.hasText(id, "帖子ID不能为空");

            return courseDao.getPostById(id);
        }catch(Exception e){
            logger.error(e.getMessage(), e);
            throw new ServiceException(e.getMessage(), e);
        }
    }

    /**
     * 根据ID查询课程
     */
    @Override
    public Course getCourseById(String id){
        try{
            Assert.hasText(id, "课程ID不能为空");

            return courseDao.getCourseById(id);
        }catch(Exception e){
            logger.error(e.getMessage(), e);
            throw new ServiceException(e.getMessage(), e);
        }
    }

    /**
     * 根据ID查询章节
     */
    @Override
    public Course getChapterById(String id){
        try{
            Assert.hasText(id, "章节ID不能为空");

            return courseDao.getChapterById(id);
        }catch(Exception e){
            logger.error(e.getMessage(), e);
            throw new ServiceException(e.getMessage(), e);
        }
    }

    /**
     * 根据ID查询课时
     */
    @Override
    public Course getSectionById(String id){
        try{
            Assert.hasText(id, "课时ID不能为空");

            return courseDao.getSectionById(id);
        }catch(Exception e){
            logger.error(e.getMessage(), e);
            throw new ServiceException(e.getMessage(), e);
        }
    }

    /**
     * 上传图片
     */
    @Override
    public String upload(CommonsMultipartFile file){
        try{
            Assert.notNull(file, "上传文件不能为空");

            //文件名称
            String originalFilename = file.getOriginalFilename();
            //MD5
            String md5 = FileUtils.getMD5(file.getBytes());
            //文件后缀
            String suffix = FileNameUtils.getSuffix(originalFilename);
            //上传名称
            String name = md5 + suffix;
            //上传路径
            String uploadPath = UpyunUtils.COURSE_COVER_DIR + name;
            //上传
            boolean result = UpyunUtils.upload(uploadPath, file);

            return result ? UpyunUtils.URL + uploadPath : "";
        }catch(Exception e){
            logger.error(e.getMessage(), e);
            throw new ServiceException(e.getMessage(), e);
        }
    }
}
