package com.lab.hosaily.core.app.dao.mapper;



import com.lab.hosaily.core.app.entity.Customer;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import org.wah.doraemon.utils.mybatis.Criteria;

import java.util.List;

@Repository
public interface CustomerMapper {

    void save(Customer customer);

    void update(Customer customer);

    void saveList(@Param("customers") List<Customer> customers);

    void updateList(@Param("customers") List<Customer> customers);

    Customer get(@Param("params") Criteria criteria);

    List<Customer> find(@Param("params") Criteria criteria);

    Long count(@Param("params") Criteria criteria);

    void delete(String id);
}
