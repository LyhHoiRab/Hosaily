package com.lab.hosaily.core.popularize.service;

import com.lab.hosaily.commons.utils.FileNameUtils;
import com.lab.hosaily.commons.utils.UpyunUtils;
import com.lab.hosaily.core.popularize.dao.AnswerDao;
import com.lab.hosaily.core.popularize.dao.TestLibraryDao;
import com.lab.hosaily.core.popularize.dao.TestLogsDao;
import com.lab.hosaily.core.popularize.entity.Answer;
import com.lab.hosaily.core.popularize.entity.TestLibrary;
import com.lab.hosaily.core.popularize.entity.TestLogs;
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

import java.util.*;

@Service
@Transactional(readOnly = true)
public class TestLibraryServiceImpl implements TestLibraryService{

    private static Logger logger = LoggerFactory.getLogger(TestLibraryServiceImpl.class);

    @Autowired
    private TestLibraryDao testLibraryDao;

    @Autowired
    private AnswerDao answerDao;

    @Autowired
    private TestLogsDao testLogsDao;

    /**
     * 保存
     */
    @Override
    @Transactional(readOnly = false)
    public void save(TestLibrary library){
        try{
            Assert.notNull(library, "题库信息不能为空");

            testLibraryDao.saveOrUpdate(library);

            if(library.getAnswers() != null && !library.getAnswers().isEmpty()){
                for(Answer answer : library.getAnswers()){
                    answer.setLibraryId(library.getId());
                }

                answerDao.saveBatch(library.getAnswers());
            }
        }catch(Exception e){
            logger.error(e.getMessage(), e);
            throw new ServiceException(e.getMessage(), e);
        }
    }

    /**
     * 更新
     */
    @Override
    @Transactional(readOnly = false)
    public void update(TestLibrary library){
        try{
            Assert.notNull(library, "题库信息不能为空");
            Assert.hasText(library.getId(), "题库ID不能为空");

            testLibraryDao.saveOrUpdate(library);

            if(library.getAnswers() != null && !library.getAnswers().isEmpty()){
                List<Answer> save = new ArrayList<Answer>();
                List<Answer> update = new ArrayList<Answer>();

                for(Answer answer : library.getAnswers()){
                    if(StringUtils.isBlank(answer.getId())){
                        answer.setLibraryId(library.getId());
                        save.add(answer);
                    }else{
                        update.add(answer);
                    }
                }

                if(!save.isEmpty()){
                    answerDao.saveBatch(save);
                }
                if(!update.isEmpty()){
                    answerDao.updateBatch(update);
                }
            }
        }catch(Exception e){
            logger.error(e.getMessage(), e);
            throw new ServiceException(e.getMessage(), e);
        }
    }

    /**
     * 删除题库
     */
    @Override
    @Transactional(readOnly = false)
    public void delete(String id){
        try{
            Assert.hasText(id, "题库ID不能为空");

            testLibraryDao.delete(id);
        }catch(Exception e){
            logger.error(e.getMessage(), e);
            throw new ServiceException(e.getMessage(), e);
        }
    }

    /**
     * 删除答案
     */
    @Override
    @Transactional(readOnly = false)
    public void deleteAnswer(String answerId){
        try{
            Assert.hasText(answerId, "题库答案ID不能为空");

            answerDao.delete(answerId);
        }catch(Exception e){
            logger.error(e.getMessage(), e);
            throw new ServiceException(e.getMessage(), e);
        }
    }

    /**
     * 根据ID查询
     */
    @Override
    public TestLibrary getById(String id){
        try{
            Assert.hasText(id, "题库ID不能为空");

            return testLibraryDao.getById(id);
        }catch(Exception e){
            logger.error(e.getMessage(), e);
            throw new ServiceException(e.getMessage(), e);
        }
    }

    /**
     * 分页
     */
    @Override
    public Page<TestLibrary> page(PageRequest pageRequest, UsingState state, Date createTime, String kind, String title){
        try{
            Assert.notNull(pageRequest, "分页信息不能为空");

            return testLibraryDao.page(pageRequest, state, createTime, kind, title);
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
            String uploadPath = UpyunUtils.TEST_LIBRARY_DIR + name;
            //上传
            boolean result = UpyunUtils.upload(uploadPath, file);

            return result ? UpyunUtils.URL + uploadPath : "";
        }catch(Exception e){
            logger.error(e.getMessage(), e);
            throw new ServiceException(e.getMessage(), e);
        }
    }

    /**
     * 查询用户当天测试记录
     */
    @Override
    public List<TestLibrary> findByAccountIdToday(String accountId){
        try{
            Assert.hasText(accountId, "账户ID不能为空");

            TestLogs logs = testLogsDao.getByParams(accountId, new Date());

            if(logs != null){
                String[] testIds = logs.getTestId().split(";");
                return testLibraryDao.list(null, null, null, null);
            }

            List<TestLibrary> list = testLibraryDao.list(null, null, null, null);
            List<TestLibrary> target = new ArrayList<TestLibrary>();

            Random random = new Random();
            int lastIndex = -1;

            while(!(target.size() == 3)){
                int index = random.nextInt(list.size());

                if(lastIndex == index){
                    continue;
                }

                target.add(list.get(index));
                lastIndex = index;
            }

            StringBuffer sb = new StringBuffer();

            for(TestLibrary test : target){
                sb.append(test.getId()).append(",");
            }

            logs = new TestLogs();
            logs.setAccountId(accountId);
            logs.setTestId(sb.deleteCharAt(sb.lastIndexOf(",")).toString());

            testLogsDao.save(logs);

            return target;
        }catch(Exception e){
            logger.error(e.getMessage(), e);
            throw new ServiceException(e.getMessage(), e);
        }
    }

    @Override
    public List<TestLibrary> list(Long pageSize, UsingState state, Date createTime, String kind, String title){
        try{
            List<TestLibrary> list = testLibraryDao.list(state, createTime, kind, title);
            List<TestLibrary> target = new ArrayList<TestLibrary>();

            pageSize = pageSize > list.size() ? list.size() : pageSize;

            Random random = new Random();

            while(target.size() < pageSize){
                int index = random.nextInt(list.size());

                if(!target.contains(list.get(index))){
                    target.add(list.get(index));
                }
            }

            return target;
        }catch(Exception e){
            logger.error(e.getMessage(), e);
            throw new ServiceException(e.getMessage(), e);
        }
    }
}
