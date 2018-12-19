package com.lab.hosaily.core.app.service;


import com.lab.hosaily.core.app.dao.CustomerDao;
import com.lab.hosaily.core.app.entity.Customer;
import com.lab.hosaily.core.app.utils.ReadExcel;
import com.rab.babylon.core.consts.entity.Sex;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.web.multipart.MultipartFile;
import org.wah.doraemon.security.request.Page;
import org.wah.doraemon.security.request.PageRequest;
import redis.clients.jedis.ShardedJedisPool;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional(readOnly = true)
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    private CustomerDao customerDao;

    @Autowired
    private ShardedJedisPool pool;

    @Value("${system.environment}")
    private String env;

    @Override
    @Transactional
    public void save(Customer customer) {
        Assert.notNull(customer, "用户信息不能为空");
//        Assert.hasText(customer.getPhone(), "用户电话不能为空");


//        Customer original = customerDao.getByCompanyIdAndPhone("companyId", customer.getPhone());

//        if (original != null) {
//            customer.setId(original.getId());
        customerDao.save(customer);
//        } else {
//            customerDao.save(customer);
//        }

    }

    @Override
    @Transactional
    public void update(Customer customer) {
        Assert.notNull(customer, "用户信息不能为空");
        Assert.hasText(customer.getId(), "Id不能为空");
        customerDao.update(customer);
    }

    @Override
    public Customer getById(String id) {
        return customerDao.getById(id);
    }

    @Override
    @Transactional
    public void delete(String id) {
        Assert.hasText(id, "ID不能为空");
        customerDao.delete(id);
    }

    @Override
    public Page<Customer> page(PageRequest pageRequest, String name,
                               String situation, String startTime, String endTime) {
        Assert.notNull(pageRequest, "分页信息不能为空");
        return customerDao.page(pageRequest, name,
                situation, startTime, endTime);
    }

    @Override
    public Page<Customer> pageForSeller(PageRequest pageRequest, String name, String channel, String customerService) {
        Assert.notNull(pageRequest, "分页信息不能为空");
        return customerDao.page(pageRequest, "companyId", name, channel, "account.getUsername()");
    }

//    @Override
//    public List<Customer> findAllByNameChannelCS(String name, String channel, String customerService) {
//        return customerDao.findAllByNameChannelCS(name, channel, customerService);
//    }

    @Override
    @Transactional
    public List<String> importFile(MultipartFile file) throws Exception {
        Assert.notNull(file, "上传文件不能为空");


        ReadExcel obj = new ReadExcel();
        List excelList = obj.readExcel(file.getInputStream());
        System.out.println("excelListexcelList: " + excelList.size());

        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");

        List<Customer> customers = new ArrayList<Customer>();
        List<String> badList = new ArrayList<String>();
        for (int i = 1; i < excelList.size(); i++) {
            List list = (List) excelList.get(i);
            System.out.println("listlistlistlistlist: " + list.size());
            Customer customer = new Customer();
            customer.setIndex(i);
            if (null != list.get(0) && list.get(0).toString().startsWith("201")) {                                      //日期
                customer.setTime(sf.parse(list.get(0).toString()));
            }
            if (null != list.get(1)) {                                      //跟进人
                customer.setFollower(list.get(1).toString());
            }
            if (null != list.get(2)) {                                      //地区
                customer.setAddress(list.get(2).toString());
            }
            if (null != list.get(3) && !"".equals(list.get(3).toString())) {                                      //客户名称
                customer.setName(list.get(3).toString());
            } else {
                badList.add("第" + i + "行数据导入失败，原因: 客户名称不能为空！");
                continue;
            }
            if (null != list.get(4) && !"".equals(list.get(4).toString())) {                                      //日期
                if ("男".equals(list.get(4).toString())) {
                    customer.setSex(Sex.MALE);
                } else if ("女".equals(list.get(4).toString())) {
                    customer.setSex(Sex.FEMALE);
                } else {
                    customer.setSex(Sex.UNKNOWN);
                }
            } else {
                customer.setSex(Sex.UNKNOWN);
            }

//            customer.setSex(Sex.FEMALE);                                  //性别

            if (null != list.get(5) || null != list.get(6)) {
                if (null != list.get(5)) {                                      //电话
                    customer.setPhone(list.get(5).toString());
                }
                if (null != list.get(6)) {                                      //微信
                    customer.setWeChat(list.get(6).toString());
                }
            } else {
                badList.add("第" + i + "行数据导入失败，原因: 电话或微信号必须有一项！");
                continue;
            }
            if (null != list.get(7)) {                                      //链接
                customer.setLink(list.get(7).toString());
            }
            if (null != list.get(8)) {                                      //链接
                customer.setChatRecord(list.get(8).toString());
            }
            if (null != list.get(9)) {                                      //跟进情况
                customer.setFollower(list.get(9).toString());
            }
            customers.add(customer);
        }

        List<Customer> saveList = new ArrayList<Customer>();
        List<Customer> updateList = new ArrayList<Customer>();

        List<Customer> originals = customerDao.findAllByMix(null, null, null, null);

        continueOut:
        for (int j = 0; j < customers.size(); j++) {
            Customer customer = customers.get(j);
            if (customer.getPhone() != null && !"".equals(customer.getPhone().trim())) {
                for (int i = 0; i < originals.size(); i++) {
                    Customer original = originals.get(i);
                    if (original.getPhone() != null && !"".equals(original.getPhone().trim())) {
                        if (original.getPhone().trim().equals(customer.getPhone().trim())) {
                            System.out.println("customer.getPhone(): " + customer.getName());
                            badList.add("第" + customer.getIndex() + "行数据导入失败，原因: 电话号码已录入！");
                            continue continueOut;
                        }
                    }
                }
            }
            if (customer.getWeChat() != null && !"".equals(customer.getWeChat().trim())) {
                for (int i = 0; i < originals.size(); i++) {
                    Customer original = originals.get(i);
                    if (original.getWeChat() != null && !"".equals(original.getWeChat().trim())) {
                        if (original.getWeChat().trim().equals(customer.getWeChat().trim())) {
                            System.out.println("customer.getWeChat(): " + customer.getName());
                            badList.add("第" + customer.getIndex() + "行数据导入失败，原因: 微信号已录入！");
                            continue continueOut;
                        }
                    }
                }
            }
            saveList.add(customer);
        }

        System.out.println("saveListsaveListsaveList:  " + saveList.size());
        if (saveList != null && !saveList.isEmpty()) {
            customerDao.saveList(saveList);
        }
//        if (updateList != null && !updateList.isEmpty()) {
//            customerDao.updateList(updateList);
//        }

        return badList;
    }

    @Override
    public List<Customer> findAllByMix(String name, String situation, String startTime, String endTime) {
        return customerDao.findAllByMix(name, situation, startTime, endTime);
    }
}
