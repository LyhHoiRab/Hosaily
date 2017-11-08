package com.lab.hosaily.core.popularize.dao.mapper;

import com.lab.hosaily.core.popularize.entity.Answer;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AnswerMapper{

    /**
     * 保存
     */
    void save(Answer answer);

    /**
     * 更新
     */
    void update(Answer answer);

    /**
     * 批量保存
     */
    void saveBatch(@Param("list") List<Answer> list);

    /**
     * 批量更新
     */
    void updateBatch(@Param("list") List<Answer> list);

    /**
     * 删除
     */
    void delete(String id);

    /**
     * 根据题库ID查询
     */
    void deleteByTestLibraryId(String testLibraryId);
}
