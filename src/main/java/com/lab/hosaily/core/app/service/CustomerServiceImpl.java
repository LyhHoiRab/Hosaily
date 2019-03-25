package com.lab.hosaily.core.app.service;


import com.lab.hosaily.commons.consts.RedisConsts;
import com.lab.hosaily.core.app.dao.CustomerDao;
import com.lab.hosaily.core.app.dao.ProfileDao;
import com.lab.hosaily.core.app.entity.Customer;
import com.lab.hosaily.core.app.entity.LoginUser;
import com.lab.hosaily.core.app.entity.Profile;
import com.lab.hosaily.core.app.utils.ReadExcel;
import com.rab.babylon.commons.utils.RedisUtils;
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
    private ProfileDao profileDao;

    @Autowired
    private ShardedJedisPool shardedJedisPool;

    @Value("${system.environment}")
    private String env;

    @Override
    @Transactional
    public void save(Customer customer) {
        Assert.notNull(customer, "用户信息不能为空");
        customerDao.save(customer);
    }

    @Override
    @Transactional
    public void update(Customer customer) {
        Assert.notNull(customer, "用户信息不能为空");
        Assert.hasText(customer.getId(), "Id不能为空");
        customerDao.update(customer);
    }

    @Override
    @Transactional
    public void updateSort(String id, String sort, String follower) {
        Assert.hasText(id, "Id不能为空");
        Assert.hasText(follower, "follower不能为空");
        Assert.hasText(sort, "sort不能为空");
        customerDao.updateSort(id, sort, follower);
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
                               String situation, String startTime, String endTime, String process, String follower, String phone) {
        Assert.notNull(pageRequest, "分页信息不能为空");
        return customerDao.page(pageRequest, name,
                situation, startTime, endTime, process, follower, phone);
    }

    @Override
    public Page<Customer> pageForSeller(PageRequest pageRequest, String name, String channel, String customerService) {
        Assert.notNull(pageRequest, "分页信息不能为空");
        return null;
    }

    @Override
    @Transactional
    public List<String> importFile(MultipartFile file, String uploader) throws Exception {
        Assert.notNull(file, "上传文件不能为空");


        ReadExcel obj = new ReadExcel();
        List excelList = obj.readExcel(file.getInputStream());
        System.out.println("excelListexcelList: " + excelList.size());

        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");

        List<Customer> customers = new ArrayList<Customer>();
        List<String> badList = new ArrayList<String>();

        StringBuilder badName = new StringBuilder();
        badName.append("第");
        StringBuilder badPW = new StringBuilder();
        badPW.append("第");



//        获取销售列表
        List<Profile> profileList = profileDao.list(null, 3);
        for (int i = 0; i < profileList.size(); i++) {
            Profile profile = profileList.get(i);
            System.out.println(profile.getName());
        }

        for (int i = 1; i < excelList.size(); i++) {
            List list = (List) excelList.get(i);
            System.out.println("listlistlistlistlist: " + list.size());
            Customer customer = new Customer();
            customer.setIndex(i + 1);
            if (null != list.get(0) && list.get(0).toString().startsWith("201")) {                                      //日期
                customer.setTime(sf.parse(list.get(0).toString()));
            }
            if (null != list.get(1)) {                                                             //跟进人
                for (int j = 0; j < profileList.size(); j++) {
                    Profile profile = profileList.get(j);
                    if(list.get(1).toString().trim().equals(profile.getName())){
                        customer.setFollower(profile.getId());
                    }
                }



//                customer.setFollower(list.get(1).toString());
            }
            if (null != list.get(2)) {                                      //地区
                customer.setAddress(list.get(2).toString());
            }
            if (null != list.get(3) && !"".equals(list.get(3).toString())) {                                      //客户名称
                customer.setName(list.get(3).toString());
            } else {
                badList.add("第" + i + "行数据导入失败，原因: 客户名称不能为空！");
                badName.append(i + 1 + ",");
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
                badPW.append(i + 1 + ",");
                continue;
            }
            if (null != list.get(7)) {                                      //链接
                customer.setLink(list.get(7).toString());
            }
            if (null != list.get(8)) {                                      //聊天记录
                customer.setChatRecord(list.get(8).toString());
                System.out.println(customer.getChatRecord());
            }
            if (null != list.get(9)) {                                      //跟进情况
                customer.setComment(list.get(9).toString());
            }
            if (null != list.get(10)) {                                      //渠道
                customer.setChannel(list.get(10).toString());
            }
            customers.add(customer);
        }
        badName.append("行数据导入失败，原因: 客户名称不能为空！");
        badPW.append("行数据导入失败，原因: 电话或微信号必须有一项！");
        List<Customer> saveList = new ArrayList<Customer>();
        List<Customer> originals = customerDao.findAllByMix(null, null, null, null, null);
        StringBuilder samePhone = new StringBuilder();
        samePhone.append("第");
        StringBuilder sameWeChat = new StringBuilder();
        sameWeChat.append("第");
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
                            samePhone.append(customer.getIndex() + ",");
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
                            sameWeChat.append(customer.getIndex() + ",");
                            continue continueOut;
                        }
                    }
                }
            }
            customer.setUploader(uploader);
            saveList.add(customer);
        }
        samePhone.append("行数据导入失败，原因: 电话号码已录入！");
        sameWeChat.append("行数据导入失败，原因: 微信号已录入！");
        List<String> alertBadList = new ArrayList<String>();
        alertBadList.add("导入失败：" + badList.size() + "条！ ");
        alertBadList.add("导入成功：" + saveList.size() + "条！ ");
        alertBadList.add(badName.toString());
        alertBadList.add(badPW.toString());
        alertBadList.add(samePhone.toString());
        alertBadList.add(sameWeChat.toString());

        System.out.println("saveListsaveListsaveList:  " + saveList.size());
        if (saveList != null && !saveList.isEmpty()) {
            customerDao.saveList(saveList);
        }
        return alertBadList;
    }

    @Override
    public List<Customer> findAllByMix(String name, String situation, String startTime, String endTime, String phone) {
        return customerDao.findAllByMix(name, situation, startTime, endTime, phone);
    }
}
