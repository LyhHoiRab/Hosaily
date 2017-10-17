package com.lab.hosaily.core.account.service;

import com.lab.hosaily.core.account.consts.AttentionConsts;
import com.lab.hosaily.core.account.consts.AttentionType;
import com.lab.hosaily.core.account.dao.AttentionDao;
import com.lab.hosaily.core.account.entity.Attention;
import com.rab.babylon.commons.security.exception.ServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class AttentionServiceImpl implements AttentionService{

    private static Logger logger = LoggerFactory.getLogger(AttentionServiceImpl.class);

    @Autowired
    private AttentionDao attentionDao;

    /**
     * 添加足迹
     */
    @Override
    @Transactional(readOnly = false)
    public void saveTrack(Attention attention){
        try{
            Assert.notNull(attention, "课程信息不能为空");
            Assert.hasText(attention.getAccountId(), "账户ID不能为空");
            Assert.notNull(attention.getCourse(), "课程信息不能为空");
            Assert.hasText(attention.getCourse().getId(), "课程ID不能为空");

            attention.setType(AttentionType.TRACK);
            attentionDao.save(attention);

            //删除多余记录
            List<Attention> tracks = attentionDao.findTrackByAccountId(attention.getAccountId());
            if(tracks.size() > AttentionConsts.TRACK_MAX_COUNT){
                Attention last = tracks.get(tracks.size() - 1);
                attentionDao.delete(last.getId());
            }
        }catch(Exception e){
            logger.error(e.getMessage(), e);
            throw new ServiceException(e.getMessage(), e);
        }
    }

    /**
     * 添加收藏
     */
    @Override
    @Transactional(readOnly = false)
    public void saveCollect(Attention attention){
        try{
            Assert.notNull(attention, "课程信息不能为空");
            Assert.hasText(attention.getAccountId(), "账户ID不能为空");
            Assert.notNull(attention.getCourse(), "课程信息不能为空");
            Assert.hasText(attention.getCourse().getId(), "课程ID不能为空");

            if(!attentionDao.existByAccountIdAndCourseId(attention.getAccountId(), attention.getCourse().getId())){
                attention.setType(AttentionType.COLLECT);
                attentionDao.save(attention);
            }
        }catch(Exception e){
            logger.error(e.getMessage(), e);
            throw new ServiceException(e.getMessage(), e);
        }
    }

    /**
     * 删除关注记录
     */
    @Override
    @Transactional(readOnly = false)
    public void delete(String id){
        try{
            Assert.hasText(id, "关注ID不能为空");

            attentionDao.delete(id);
        }catch(Exception e){
            logger.error(e.getMessage(), e);
            throw new ServiceException(e.getMessage(), e);
        }
    }

    /**
     * 根据账户ID查询足迹
     */
    @Override
    public List<Attention> findTrackByAccountId(String accountId){
        try{
            Assert.hasText(accountId, "账户ID不能为空");

            return attentionDao.findTrackByAccountId(accountId);
        }catch(Exception e){
            logger.error(e.getMessage(), e);
            throw new ServiceException(e.getMessage(), e);
        }
    }

    /**
     * 根据账户ID查询收藏
     */
    @Override
    public List<Attention> findCollectByAccountId(String accountId){
        try{
            Assert.hasText(accountId, "账户ID不能为空");

            return attentionDao.findCollectByAccountId(accountId);
        }catch(Exception e){
            logger.error(e.getMessage(), e);
            throw new ServiceException(e.getMessage(), e);
        }
    }
}
