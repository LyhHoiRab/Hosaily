package com.lab.hosaily.core.course.service;

import com.lab.hosaily.core.course.dao.RecordDao;
import com.lab.hosaily.core.course.entity.Record;
import com.rab.babylon.commons.security.exception.ServiceException;
import com.rab.babylon.commons.security.response.Page;
import com.rab.babylon.commons.security.response.PageRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.io.File;

@Service
@Transactional(readOnly = true)
@Component("recordService")
public class RecordServiceImpl implements RecordService {

    private static Logger logger = LoggerFactory.getLogger(RecordServiceImpl.class);

    @Autowired
    private RecordDao recordDao;

    /**
     * 保存记录
     */
    @Override
    @Transactional(readOnly = false)
    public void save(Record record){
        try{
            Assert.notNull(record, "标签信息不能为空");
            System.out.println("savesavesavesavesavesavesave");
            recordDao.saveOrUpdate(record);
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
    public void update(Record record){
        try{
            Assert.notNull(record, "标签信息不能为空");
            Assert.hasText(record.getId(), "标签ID不能为空");



            recordDao.saveOrUpdate(record);
        }catch(Exception e){
            logger.error(e.getMessage(), e);
            throw new ServiceException(e.getMessage(), e);
        }
    }

    /**
     * 根据ID查询记录
     */
    @Override
    public Record getById(String id){
        try{
            Assert.hasText(id, "标签信息不能为空");

            return recordDao.getById(id);
        }catch(Exception e){
            logger.error(e.getMessage(), e);
            throw new ServiceException(e.getMessage(), e);
        }
    }

    /**
     * 删除
     */
    @Override
    @Transactional(readOnly = false)
    public void delete(String id){
        try{
            Assert.hasText(id, "帖子ID不能为空");
            Record record = recordDao.getById(id);
            File file = new File(record.getPath());
            if(file.exists()){
                System.out.println(file.getName() + " deleted!!");
                file.delete();
            }
            recordDao.delete(id);
        }catch(Exception e){
            logger.error(e.getMessage(), e);
            throw new ServiceException(e.getMessage(), e);
        }
    }

    /**
     * 分页查询
     */
    @Override
    public Page<Record> page(PageRequest pageRequest, String userName, String num, String outGoingNum, String sim, String userType, String organizationId){
        try{
            Assert.notNull(pageRequest, "分页信息不能为空");

            return recordDao.page(pageRequest, userName, num, outGoingNum, sim, userType, organizationId);
        }catch(Exception e){
            logger.error(e.getMessage(), e);
            throw new ServiceException(e.getMessage(), e);
        }
    }
}
