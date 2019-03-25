package com.lab.hosaily.core.app.webservice;


import com.lab.hosaily.commons.consts.RedisConsts;
import com.lab.hosaily.core.app.entity.Customer;
import com.lab.hosaily.core.app.entity.LoginUser;
import com.lab.hosaily.core.app.service.CustomerService;
import com.lab.hosaily.core.app.utils.JavaSmsApi;
import com.lab.hosaily.core.app.utils.WriteExcel;
import com.rab.babylon.commons.utils.RedisUtils;
import com.rab.babylon.core.consts.entity.Sex;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import org.wah.doraemon.security.consts.ResponseCode;
import org.wah.doraemon.security.request.Page;
import org.wah.doraemon.security.request.PageRequest;
import org.wah.doraemon.security.response.Responsed;
import redis.clients.jedis.ShardedJedisPool;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.*;


/**
 * Created by miku03 on 2018/5/11.
 */
@RestController
@RequestMapping(value = "/api/1.0/customer")
public class CustomerRestController {

    @Autowired
    private CustomerService customerService;

    @Autowired
    private ShardedJedisPool shardedJedisPool;

    @Value("${system.environment}")
    private String env;

    @RequestMapping(value = "/page", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public Responsed<Page<Customer>> page(HttpServletRequest request, Long pageNum, Long pageSize, String name,
                                          String situation, String startTime, String endTime, String process, String follower) {
        Responsed<Page<Customer>> responsed = new Responsed<Page<Customer>>();
        String phone = request.getHeader("phone");
        System.out.println("request.getHeader(\"phone\"): " + phone);
        LoginUser loginUser = (LoginUser) RedisUtils.get(shardedJedisPool.getResource(), RedisConsts.USER_INFO + phone, LoginUser.class);
        if (null == loginUser || !loginUser.getPhone().equals(phone)) {
            responsed.setSuccess(false);
            responsed.setMsg("登录状态无效");
            responsed.setCode(ResponseCode.FORBIDDEN);
            return responsed;
        }
        PageRequest pageRequest = new PageRequest(pageNum, pageSize);
        Page<Customer> page = null;
        if ("0".equals(loginUser.getRole())) {            //超级管理员
            page = customerService.page(pageRequest, name,
                    situation, startTime, endTime, process, follower, null);
        } else if ("1".equals(loginUser.getRole())) {     //客服
            page = customerService.page(pageRequest, name,
                    situation, startTime, endTime, process, follower, phone);
        } else {                                          //普通用户
            page = customerService.page(pageRequest, name,
                    situation, startTime, endTime, process, follower, null);
            for (int i = 0; i < page.getContent().size(); i++) {
                page.getContent().get(i).setPhone(hidenStr(page.getContent().get(i).getPhone()));
                page.getContent().get(i).setWeChat(hidenStr(page.getContent().get(i).getWeChat()));
            }
        }


        responsed.setSuccess(true);
        responsed.setMsg("查询成功");
        responsed.setResult(page);
        return responsed;
    }


    @RequestMapping(value = "/appPage", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public Responsed<Page<Customer>> appPage(HttpServletRequest request, Long pageNum, Long pageSize, String name,
                                             String situation, String startTime, String endTime, String process, String follower) {
        Responsed<Page<Customer>> responsed = new Responsed<Page<Customer>>();

        if (StringUtils.isBlank(follower)) {
            responsed.setSuccess(false);
            responsed.setMsg("follower不能为空");
            return responsed;
        }

        PageRequest pageRequest = new PageRequest(pageNum, pageSize);
        Page<Customer> page = customerService.page(pageRequest, name,
                situation, startTime, endTime, process, follower, null);
        responsed.setSuccess(true);
        responsed.setMsg("查询成功");
        responsed.setResult(page);
        return responsed;
    }


    @RequestMapping(value = "/page/seller", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public Responsed<Page<Customer>> pageForSeller(HttpServletRequest request, Long pageNum, Long pageSize, String name,
                                                   String channel, String cs) {
        Responsed<Page<Customer>> responsed = new Responsed<Page<Customer>>();
        String phone = request.getHeader("phone");
        System.out.println("request.getHeader(\"phone\"): " + phone);
        LoginUser loginUser = (LoginUser) RedisUtils.get(shardedJedisPool.getResource(), RedisConsts.USER_INFO + phone, LoginUser.class);
        if (null == loginUser || !loginUser.getPhone().equals(phone)) {
            responsed.setSuccess(false);
            responsed.setMsg("登录状态无效");
            responsed.setCode(ResponseCode.FORBIDDEN);
            return responsed;
        }
        PageRequest pageRequest = new PageRequest(pageNum, pageSize);
        Page<Customer> page = customerService.pageForSeller(pageRequest, name, channel, cs);
        responsed.setSuccess(true);
        responsed.setMsg("查询成功");
        responsed.setResult(page);
        return responsed;
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public Responsed<Customer> save(HttpServletRequest request, String name, String phone, Sex sex,
                                    String weChat, Date time, String link, String follower, String address,
                                    String chatRecord, String sutuation, String qq, String uploader, String process, String comment, String channel) {

        Responsed<Customer> responsed = new Responsed<Customer>();
        String checkPhone = request.getHeader("phone");
        System.out.println("request.getHeader(\"phone\"): " + checkPhone);
        LoginUser loginUser = (LoginUser) RedisUtils.get(shardedJedisPool.getResource(), RedisConsts.USER_INFO + checkPhone, LoginUser.class);
        if (null == loginUser || !loginUser.getPhone().equals(checkPhone)) {
            responsed.setSuccess(false);
            responsed.setMsg("登录状态无效");
            responsed.setCode(ResponseCode.FORBIDDEN);
            return responsed;
        }

        if (StringUtils.isBlank(name)) {
            responsed.setSuccess(false);
            responsed.setMsg("客户名称不能为空！");
            return responsed;
        }
        if (StringUtils.isBlank(phone) && StringUtils.isBlank(weChat)) {
            responsed.setSuccess(false);
            responsed.setMsg("电话和微信号至少要填一项");
            return responsed;
        }
        Customer customer = new Customer();
        customer.setName(name);
        customer.setPhone(phone);
        customer.setSex(sex);
        customer.setWeChat(weChat);
        customer.setTime(time);
        customer.setLink(link);
        customer.setFollower(follower);
        customer.setAddress(address);
        customer.setChatRecord(chatRecord);
        customer.setUploader(loginUser.getPhone());
//        customer.setSituation("0");
        customer.setQq(qq);
        customer.setProcess(process);
        customer.setComment(comment);
        customer.setChannel(channel);
        customerService.save(customer);
        responsed.setSuccess(true);
        responsed.setMsg("添加成功");
        responsed.setResult(customer);
        return responsed;
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public Responsed<Customer> update(HttpServletRequest request, String id, String name, String phone, Sex sex,
                                      String weChat, Date time, String link, String follower, String address,
                                      String chatRecord, String sutuation, String qq, String uploader,
                                      String process, String comment, String channel, String sort) {


        Responsed<Customer> responsed = new Responsed<Customer>();
        String checkPhone = request.getHeader("phone");
        System.out.println("request.getHeader(\"phone\"): " + checkPhone);
        LoginUser loginUser = (LoginUser) RedisUtils.get(shardedJedisPool.getResource(), RedisConsts.USER_INFO + checkPhone, LoginUser.class);
        if (null == loginUser || !loginUser.getPhone().equals(checkPhone)) {
            responsed.setSuccess(false);
            responsed.setMsg("登录状态无效");
            responsed.setCode(ResponseCode.FORBIDDEN);
            return responsed;
        }

        if (StringUtils.isBlank(name)) {
            responsed.setSuccess(false);
            responsed.setMsg("客户名称不能为空！");
            return responsed;
        }
        if (StringUtils.isBlank(phone) && StringUtils.isBlank(weChat)) {
            responsed.setSuccess(false);
            responsed.setMsg("电话和微信号至少要填一项");
            return responsed;
        }
        Customer customer = new Customer();
        customer.setId(id);
        customer.setName(name);
        customer.setPhone(phone);
        customer.setSex(sex);
        customer.setWeChat(weChat);
        customer.setTime(time);
        customer.setLink(link);
        customer.setFollower(follower);
        customer.setAddress(address);
//        customer.setSort(sort);
        customer.setChatRecord(chatRecord);
//        customer.setSituation(sutuation);
        customer.setQq(qq);
//        customer.setUploader(loginUser.getPhone());
        customer.setProcess(process);
        customer.setComment(comment);
        customer.setChannel(channel);
        customerService.update(customer);
        responsed.setSuccess(true);
        responsed.setMsg("添加成功");
        responsed.setResult(customer);
        return responsed;
    }

    @RequestMapping(value = "/updateSort", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public Responsed<String> updateSort(HttpServletRequest request, String id, String sort, String follower) {
        Responsed<String> responsed = new Responsed<String>();
        customerService.updateSort(id, sort, follower);
        responsed.setSuccess(true);
        responsed.setMsg("置顶成功");
        return responsed;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public Responsed<Customer> getById(HttpServletRequest request, @PathVariable("id") String id) {
        Responsed<Customer> responsed = new Responsed<Customer>();
        String checkPhone = request.getHeader("phone");
        System.out.println("request.getHeader(\"phone\"): " + checkPhone);
        LoginUser loginUser = (LoginUser) RedisUtils.get(shardedJedisPool.getResource(), RedisConsts.USER_INFO + checkPhone, LoginUser.class);
        if (null == loginUser || !loginUser.getPhone().equals(checkPhone)) {
            responsed.setSuccess(false);
            responsed.setMsg("登录状态无效");
            responsed.setCode(ResponseCode.FORBIDDEN);
            return responsed;
        }
        Customer customer = customerService.getById(id);
        responsed.setSuccess(true);
        responsed.setMsg("查询成功");
        responsed.setResult(customer);
        return responsed;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Responsed<Customer> delete(HttpServletRequest request, @PathVariable("id") String id) {
        Responsed<Customer> responsed = new Responsed<Customer>();
        String checkPhone = request.getHeader("phone");
        System.out.println("request.getHeader(\"phone\"): " + checkPhone);
        LoginUser loginUser = (LoginUser) RedisUtils.get(shardedJedisPool.getResource(), RedisConsts.USER_INFO + checkPhone, LoginUser.class);
        if (null == loginUser || !loginUser.getPhone().equals(checkPhone)) {
            responsed.setSuccess(false);
            responsed.setMsg("登录状态无效");
            responsed.setCode(ResponseCode.FORBIDDEN);
            return responsed;
        }
        customerService.delete(id);
        responsed.setSuccess(true);
        responsed.setMsg("删除成功");
        responsed.setResult(null);
        return responsed;
    }

    @RequestMapping(value = "/downLoadModel", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public void downLoadModel(HttpServletRequest request, HttpServletResponse response) {
        String fileName = "customer_model.xls".toString(); // 文件的默认保存名
        try {
            String path = "";
//            if ("production".equals(env)) {
            path = "/opt/project/hsl_api/model/" + fileName;
//            } else {
//                String pathpath = "G:\\crm\\";
//                File filePath2 = new File(pathpath);
//                if (!filePath2.exists()) {
//                    filePath2.mkdir();
//                }
//                path = filePath2 + "\\" + fileName;
//            }
            InputStream inStream = new FileInputStream(path);// 文件的存放路径
            response.reset();
            response.setContentType("bin");
            response.addHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\"");
            byte[] b = new byte[100];
            int len;
            while ((len = inStream.read(b)) > 0)
                response.getOutputStream().write(b, 0, len);
            inStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @RequestMapping(value = "/exportFile", method = RequestMethod.GET, produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public void exportFile(HttpServletRequest request, HttpServletResponse response, String name,
                           String situation, String startTime, String endTime, String phone) {
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
        String modelName = "exportModel.xls".toString(); // 文件的默认保存名
        String fileName = "export_" + sf.format(new Date()) + ".xls"; // 文件的默认保存名
        // 读到流中
//        http://localhost:8886/api/1.0/customer/exportFile
        System.out.println("namenamename: " + name);



        LoginUser loginUser = (LoginUser) RedisUtils.get(shardedJedisPool.getResource(), RedisConsts.USER_INFO + phone, LoginUser.class);

        List<Customer> list = new ArrayList<Customer>();
        if ("0".equals(loginUser.getRole())) {            //超级管理员
            list = customerService.findAllByMix(name, situation, startTime, endTime, null);
        } else if ("1".equals(loginUser.getRole())) {     //客服
            list = customerService.findAllByMix(name, situation, startTime, endTime, phone);
        } else {                                          //普通用户

        }

        System.out.println("exportFileexportFileexportFile: " + list.size());
        try {
            List<Map> listMap = new ArrayList<Map>();
//            SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
            Map<String, String> dataMapHead = new HashMap<String, String>();
            dataMapHead.put("time", "日期");
            dataMapHead.put("follower", "跟进人");
            dataMapHead.put("address", "地区");
            dataMapHead.put("name", "客户名称");
            dataMapHead.put("sex", "性别");
            dataMapHead.put("phone", "电话");
            dataMapHead.put("weChat", "微信");
            dataMapHead.put("link", "链接");
            dataMapHead.put("chatRecord", "聊天记录");
            dataMapHead.put("situation", "跟进情况");
            dataMapHead.put("channel", "渠道");
            listMap.add(dataMapHead);
            for (int i = 0; i < list.size(); i++) {
                Map<String, String> dataMap = new HashMap<String, String>();
                Customer customer = list.get(i);
                dataMap.put("time", sf.format(customer.getTime()));
                dataMap.put("follower", customer.getFollower());
                dataMap.put("address", customer.getAddress());
                dataMap.put("name", customer.getName());
                dataMap.put("sex", customer.getSex().toString());
                dataMap.put("phone", customer.getPhone());
                dataMap.put("weChat", customer.getWeChat());
                dataMap.put("link", customer.getLink());
                dataMap.put("chatRecord", customer.getChatRecord());
                dataMap.put("situation", customer.getSituation());
                dataMap.put("channel", customer.getChannel());
                listMap.add(dataMap);
            }
            String modelPath = "";
            String exportPath = "";
            System.out.println("AAAAAAAAAAAAAAAAAA: " + env);
//            if ("production".equals(env)) {
            modelPath = "/opt/project/hsl_api/model/" + modelName;
            exportPath = "/opt/project/hsl_api/model/" + fileName;
//            } else {
//                String pathpath = "G:\\crm\\";
//                File filePath2 = new File(pathpath);
//                if (!filePath2.exists()) {
//                    filePath2.mkdir();
//                }
//                modelPath = filePath2 + "\\" + modelName;
//                exportPath = filePath2 + "\\" + fileName;
//                FileUtils.copyFile(new File(modelPath), new File(exportPath));
//            }
            FileUtils.copyFile(new File(modelPath), new File(exportPath));
            System.out.println("AAAAAAAAAAAAAAAAAA: " + modelPath);
            WriteExcel.writeExcel(listMap, 11, exportPath);/**/
            InputStream inStream = new FileInputStream(exportPath);// 文件的存放路径
            response.reset();
            response.setContentType("bin");
            response.addHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\"");
            byte[] b = new byte[100];
            int len;
            while ((len = inStream.read(b)) > 0)
                response.getOutputStream().write(b, 0, len);
            inStream.close();
            new File(exportPath).delete();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @RequestMapping(value = "/importFile", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public Responsed<List<String>> importFile(HttpServletRequest request, @RequestParam("file") MultipartFile file) throws Exception {
        Responsed<List<String>> responsed = new Responsed<List<String>>();
        String checkPhone = request.getHeader("phone");
        System.out.println("request.getHeader(\"phone\"): " + checkPhone);
        LoginUser loginUser = (LoginUser) RedisUtils.get(shardedJedisPool.getResource(), RedisConsts.USER_INFO + checkPhone, LoginUser.class);
        if (null == loginUser || !loginUser.getPhone().equals(checkPhone)) {
            responsed.setSuccess(false);
            responsed.setMsg("登录状态无效");
            responsed.setCode(ResponseCode.FORBIDDEN);
            return responsed;
        }

        List<String> badList = customerService.importFile(file, loginUser.getPhone());
        responsed.setSuccess(true);
        responsed.setMsg("导入成功");
        responsed.setResult(badList);
        return responsed;
    }

    public static String hidenStr(String str) {
        String xxxStr = "";
        if (StringUtils.isNotBlank(str)) {
            if (str.length() > 3) {
                System.out.println(str.length() / 2);
                System.out.println(str.length() / 3);
                int startR = str.length() / 3;
                int endR = str.length() * 2 / 3;
                System.out.println(startR);
                System.out.println(endR);
                int rStrL = endR - startR;
                System.out.println(rStrL);
                StringBuilder rStr = new StringBuilder();
                for (int i = 0; i < rStrL; i++) {
                    rStr.append("*");
                }
                xxxStr = str.substring(0, startR) + rStr + str.substring(endR, (str.length()));
            } else {
                xxxStr = str;
            }
        } else {
            xxxStr = str;
        }
        return xxxStr;
    }

    public static void main(String[] asd) {
        System.out.println(hidenStr("lab775123"));
    }
}
