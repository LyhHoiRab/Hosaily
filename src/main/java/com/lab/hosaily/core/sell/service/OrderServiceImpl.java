package com.lab.hosaily.core.sell.service;

import com.lab.hosaily.core.account.dao.UserDao;
import com.lab.hosaily.core.course.entity.Course;
import com.lab.hosaily.core.sell.dao.AccountCourseDao;
import com.lab.hosaily.core.sell.dao.OrderDao;
import com.lab.hosaily.core.sell.dao.PayLogsDao;
import com.lab.hosaily.core.sell.entity.AccountCourse;
import com.lab.hosaily.core.sell.entity.AccreditLogs;
import com.lab.hosaily.core.sell.entity.Order;
import com.lab.hosaily.core.sell.entity.PayLogs;
import com.rab.babylon.core.account.entity.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Service
@Transactional(readOnly = true)
public class OrderServiceImpl implements OrderService{

    private static Logger logger = LoggerFactory.getLogger(OrderServiceImpl.class);

    @Autowired
    private OrderDao orderDao;

    @Autowired
    private PayLogsDao payLogsDao;

    @Autowired
    private AccountCourseDao accountCourseDao;

    @Autowired
    private UserDao userDao;

    /**
     * 保存
     */
    @Transactional(readOnly = false)
    @Override
    public void save(Order order){
        try{
            Assert.notNull(order, "订单信息不能为空");
            Assert.notEmpty(order.getPayLogs(), "支付记录不能为空");
            Assert.notEmpty(order.getAccreditLogs(), "授权记录不能为空");

            //客户用户信息
            User clientUser = userDao.getByAccountId(order.getClientAccount());
            order.setClientUser(clientUser);

            //保存订单
            orderDao.saveOrUpdate(order);

            //保存支付记录
            for(PayLogs logs : order.getPayLogs()){
                logs.setOrder(order);
            }
            payLogsDao.saveBatch(order.getPayLogs());

            //授权课程
            List<Course> courses = new ArrayList<Course>();
            for(AccreditLogs logs : order.getAccreditLogs()){
                courses.add(logs.getCourse());
            }

            //客户原有课程
            List<AccountCourse> accountCourses = accountCourseDao.findByAccountId(order.getClientAccount());
            Iterator<Course> iterator = courses.iterator();

            while(iterator.hasNext()){
                Course course = iterator.next();

                for(AccountCourse accountCourse : accountCourses){
                    if(course.getId().equals(accountCourse.getCourse().getId())){
                        iterator.remove();
                        break;
                    }
                }
            }

            List<AccountCourse> newCourses = new ArrayList<AccountCourse>();
            for(Course course : courses){
                AccountCourse accountCourse = new AccountCourse();
                accountCourse.setCourse(course);
                accountCourse.setAccountId(order.getClientAccount());

                newCourses.add(accountCourse);
            }

            //添加新课程
            if(!newCourses.isEmpty()){
                accountCourseDao.saveBatch(newCourses);
            }
        }catch(Exception e){
            logger.error(e.getMessage(), e);
        }
    }
}
