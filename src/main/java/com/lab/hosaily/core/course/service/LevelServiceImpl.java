package com.lab.hosaily.core.course.service;

import com.lab.hosaily.commons.utils.FileNameUtils;
import com.lab.hosaily.commons.utils.UpyunUtils;
import com.lab.hosaily.core.course.dao.LevelDao;
import com.lab.hosaily.core.course.dao.LevelPriceDao;
import com.lab.hosaily.core.course.entity.Level;
import com.lab.hosaily.core.course.entity.LevelPrice;
import com.rab.babylon.commons.security.exception.ServiceException;
import com.rab.babylon.commons.security.response.Page;
import com.rab.babylon.commons.security.response.PageRequest;
import com.rab.babylon.commons.utils.FileUtils;
import com.rab.babylon.core.consts.entity.UsingState;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@Transactional(readOnly = true)
public class LevelServiceImpl implements LevelService{

    private static Logger logger = LoggerFactory.getLogger(LevelServiceImpl.class);

    @Autowired
    private LevelDao levelDao;

    @Autowired
    private LevelPriceDao levelPriceDao;

    /**
     * 保存记录
     */
    @Override
    @Transactional(readOnly = false)
    public void save(Level level){
        try{
            Assert.notNull(level, "等级信息不能为空");

            levelDao.saveOrUpdate(level);

            if(level.getPrice() != null && !level.getPrice().isEmpty()){
                for(LevelPrice price : level.getPrice()){
                    Assert.notNull(price.getPrice(), "价格不能为空");
                    Assert.notNull(price.getEffective(), "有效天数不能为空");

                    price.setLevelId(level.getId());
                }

                levelPriceDao.saveByBatch(level.getPrice());
            }
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
    public void update(Level level){
        try{
            Assert.notNull(level, "等级信息不能为空");
            Assert.hasText(level.getId(), "等级ID不能为空");

            levelDao.saveOrUpdate(level);

            if(level.getPrice() != null && !level.getPrice().isEmpty()){
                List<LevelPrice> updates = new ArrayList<LevelPrice>();
                List<LevelPrice> inserts = new ArrayList<LevelPrice>();

                for(LevelPrice price : level.getPrice()){
                    Assert.notNull(price.getPrice(), "价格不能为空");
                    Assert.notNull(price.getEffective(), "有效天数不能为空");

                    if(StringUtils.isBlank(price.getId())){
                        inserts.add(price);
                    }else{
                        updates.add(price);
                    }
                }

                if(!inserts.isEmpty()){
                    levelPriceDao.saveByBatch(inserts);
                }
                if(!updates.isEmpty()){
                    levelPriceDao.updateByBatch(updates);
                }
            }
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
            String uploadPath = UpyunUtils.LEVEL_COVER_DIR + name;
            //上传
            boolean result = UpyunUtils.upload(uploadPath, file);

            return result ? UpyunUtils.URL + uploadPath : "";
        }catch(Exception e){
            logger.error(e.getMessage(), e);
            throw new ServiceException(e.getMessage(), e);
        }
    }

    /**
     * 根据ID查询
     */
    @Override
    public Level getById(String id){
        try{
            Assert.hasText(id, "等级ID不能为空");

            return levelDao.getById(id);
        }catch(Exception e){
            logger.error(e.getMessage(), e);
            throw new ServiceException(e.getMessage(), e);
        }
    }

    /**
     * 根据状态查询
     */
    @Override
    public List<Level> findByState(UsingState state){
        try{
            Assert.notNull(state, "等级状态不能为空");

            return levelDao.findByState(state);
        }catch(Exception e){
            logger.error(e.getMessage(), e);
            throw new ServiceException(e.getMessage(), e);
        }
    }

    /**
     * 分页查询
     */
    @Override
    public Page<Level> page(PageRequest pageRequest, UsingState state, Date createTime, Date minCreateTime, Date maxCreateTime){
        try{
            Assert.notNull(pageRequest, "分页信息不能为空");

            return levelDao.page(pageRequest, state, createTime, minCreateTime, maxCreateTime);
        }catch(Exception e){
            logger.error(e.getMessage(), e);
            throw new ServiceException(e.getMessage(), e);
        }
    }
}
