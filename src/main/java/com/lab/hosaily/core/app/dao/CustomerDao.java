package com.lab.hosaily.core.app.dao;


import com.lab.hosaily.core.app.dao.mapper.CustomerMapper;
import com.lab.hosaily.core.app.entity.Customer;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;
import org.wah.doraemon.security.exception.DataAccessException;
import org.wah.doraemon.security.request.Page;
import org.wah.doraemon.security.request.PageRequest;
import org.wah.doraemon.utils.IDGenerator;
import org.wah.doraemon.utils.mybatis.Criteria;
import org.wah.doraemon.utils.mybatis.Restrictions;

import java.util.Date;
import java.util.List;

@Repository
public class CustomerDao {

    private Logger logger = LoggerFactory.getLogger(CustomerDao.class);

    @Autowired
    private CustomerMapper mapper;

    public void save(Customer customer) {
        try {
            Assert.notNull(customer, "用户信息不能为空");
//            Assert.notNull(customer.getCompanyId(), "用户公司ID不能为空");
//            Assert.hasText(customer.getPhone(), "用户电话不能为空");
            if (null == customer.getTime()) {
                customer.setTime(new Date());
            }
            customer.setId(IDGenerator.uuid32());
            customer.setCreateTime(new Date());
            customer.setSituation("0");
            mapper.save(customer);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new DataAccessException(e.getMessage(), e);
        }
    }

    public void update(Customer customer) {
        try {
            Assert.notNull(customer, "用户信息不能为空");
            Assert.hasText(customer.getId(), "用户ID不能为空");
            customer.setUpdateTime(new Date());
            mapper.update(customer);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new DataAccessException(e.getMessage(), e);
        }
    }

    public void updateSort(String id, String sort, String follower) {
        try {
            Long sortNum = mapper.getMaxSort(follower);
            if (null == sortNum) {
                sortNum = 1l;
            } else {
                sortNum++;
            }
            if ("addSort".equals(sort)) {
                mapper.updateSort(id, sortNum + "");
            } else if ("removeSort".equals(sort)) {
                mapper.updateSort(id, null);
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new DataAccessException(e.getMessage(), e);
        }
    }

    public void saveList(List<Customer> list) {
        try {
            Assert.notEmpty(list, "用户信息列表不能为空");

            for (Customer customer : list) {
                Assert.notNull(customer, "用户信息不能为空");
//                Assert.notNull(customer.getCompanyId(), "用户公司ID不能为空");
//                Assert.hasText(customer.getPhone(), "用户电话不能为空");
//                System.out.println("for(Customer customer : list){");
                customer.setId(IDGenerator.uuid32());
                customer.setCreateTime(new Date());
                customer.setSituation("0");
                if (null == customer.getTime()) {
                    customer.setTime(new Date());
                }
            }

//            for (int i = 0; i < list.size(); i++) {
//                System.out.println("AAAAAAAAAAAAAAAA:  " + ((Customer)list.get(i)).getId());
//                ((Customer)list.get(i)).setId(IDGenerator.uuid32());
//                ((Customer)list.get(i)).setCreateTime(new Date());
//            }


            mapper.saveList(list);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new DataAccessException(e.getMessage(), e);
        }
    }

    public void updateList(List<Customer> list) {
        try {
            Assert.notEmpty(list, "用户信息列表不能为空");

            for (Customer customer : list) {
                Assert.notNull(customer, "用户信息不能为空");
                Assert.hasText(customer.getId(), "用户ID不能为空");

                customer.setUpdateTime(new Date());
                mapper.update(customer);
            }

            mapper.updateList(list);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new DataAccessException(e.getMessage(), e);
        }
    }

    public Customer getById(String id) {
        try {
            Assert.hasText(id, "id不能为空");
            Criteria criteria = new Criteria();
            criteria.and(Restrictions.eq("id", id));
            return mapper.get(criteria);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new DataAccessException(e.getMessage(), e);
        }
    }

    public void delete(String id) {
        try {
            Assert.hasText(id, "ID不能为空");
            mapper.delete(id);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new DataAccessException(e.getMessage(), e);
        }
    }

    public Page<Customer> page(PageRequest pageRequest, String name,
                               String situation, String startTime, String endTime, String process, String follower, String phone) {
        try {
            Assert.notNull(pageRequest, "分页信息不能为空");

            Criteria criteria = new Criteria();
            criteria.limit(Restrictions.limit(pageRequest.getOffset(), pageRequest.getPageSize()));
//            criteria.sort(Restrictions.desc("createTime"));

            criteria.and(Restrictions.isNotNull("id"));

//            if (StringUtils.isNotBlank(name)) {
//                criteria.and(Restrictions.like("name", name));
//            }
            if (StringUtils.isNotBlank(situation)) {
                criteria.and(Restrictions.eq("situation", situation));
                if ("0".equals(situation)) {
                    criteria.sort(Restrictions.desc("sort"));
                    criteria.sort(Restrictions.asc("time"));
                } else if ("1".equals(situation)) {
                    criteria.sort(Restrictions.desc("sort"));
                    criteria.sort(Restrictions.desc("processTime"));
                }
            } else {
                criteria.sort(Restrictions.desc("createTime"));
            }
            if (StringUtils.isNotBlank(startTime)) {
                criteria.and(Restrictions.gt("createTime", startTime));
            }
            if (StringUtils.isNotBlank(endTime)) {
                criteria.and(Restrictions.lt("createTime", endTime));
            }
            if (StringUtils.isNotBlank(process)) {
                criteria.and(Restrictions.eq("process", process));
            }
            if (StringUtils.isNotBlank(follower)) {
                criteria.and(Restrictions.eq("follower", follower));
            }

            if (StringUtils.isNotBlank(phone)) {
                criteria.and(Restrictions.eq("uploader", phone));
            }

            List<Customer> list = mapper.find(criteria, name);
            Long total = mapper.count(criteria);
            return new Page<Customer>(list, total, pageRequest);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new DataAccessException(e.getMessage(), e);
        }
    }

    public List<Customer> findAllByMix(String name, String situation, String startTime, String endTime, String phone) {
        try {
            Criteria criteria = new Criteria();
            criteria.sort(Restrictions.desc("createTime"));
            if (StringUtils.isNotBlank(name)) {
                criteria.and(Restrictions.like("name", name));
            }
            if (StringUtils.isNotBlank(situation)) {
                criteria.and(Restrictions.eq("situation", situation));
            }
            if (StringUtils.isNotBlank(startTime)) {
                criteria.and(Restrictions.gt("create_time", startTime));
            }
            if (StringUtils.isNotBlank(endTime)) {
                criteria.and(Restrictions.lt("create_time", endTime));
            }
            if (StringUtils.isNotBlank(phone)) {
                criteria.and(Restrictions.eq("uploader", phone));
            }
            List<Customer> list = mapper.find(criteria, null);
            return list;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new DataAccessException(e.getMessage(), e);
        }
    }

}
