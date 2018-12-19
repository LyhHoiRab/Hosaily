package com.lab.hosaily.core.app.webservice;



import com.lab.hosaily.core.app.entity.Customer;
import com.lab.hosaily.core.app.service.CustomerService;
import com.lab.hosaily.core.app.utils.WriteExcel;
import com.rab.babylon.core.consts.entity.Sex;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import org.wah.doraemon.security.request.Page;
import org.wah.doraemon.security.request.PageRequest;
import org.wah.doraemon.security.response.Responsed;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
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

    @Value("${system.environment}")
    private String env;

    @RequestMapping(value = "/page", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public Responsed<Page<Customer>> page(HttpServletRequest request, Long pageNum, Long pageSize, String name,
                                                  String situation, String startTime, String endTime) {
        PageRequest pageRequest = new PageRequest(pageNum, pageSize);
        Page<Customer> page = customerService.page(pageRequest, name,
                situation, startTime, endTime);
        return new Responsed<Page<Customer>>("查询成功", page);
    }

    @RequestMapping(value = "/page/seller", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public Responsed<Page<Customer>> pageForSeller(HttpServletRequest request, Long pageNum, Long pageSize, String name,
                                                   String channel, String cs){
        PageRequest pageRequest = new PageRequest(pageNum, pageSize);
        Page<Customer> page = customerService.pageForSeller(pageRequest, name, channel, cs);
        return new Responsed<Page<Customer>>("查询成功", page);
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public Responsed<Customer> save(HttpServletRequest request, String name, String phone, Sex sex,
                                    String weChat, Date time, String link, String follower, String address,
                                    String chatRecord, String sutuation, String qq, String uploader, String process, String comment) {

        System.out.println("按时打算打算的: ");
        System.out.println("AAAAAAAAAAAA: " + name);
        Responsed<Customer> responsed = new Responsed<Customer>();
        if(StringUtils.isBlank(name)){
            responsed.setSuccess(false);
            responsed.setMsg("客户名称不能为空！");
            return responsed;
        }
        if(StringUtils.isBlank(phone) && StringUtils.isBlank(weChat)){
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
        customer.setSituation(sutuation);
        customer.setQq(qq);

        customer.setUploader(uploader);
        customer.setProcess(process);
        customer.setComment(comment);

        customerService.save(customer);
        responsed.setSuccess(true);
        responsed.setMsg("添加成功");
        responsed.setResult(customer);
        return responsed;
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public Responsed<Customer> update(String id, String name, String phone, Sex sex,
                                      String weChat, Date time, String link, String follower, String address,
                                      String chatRecord, String sutuation, String qq, String uploader, String process, String comment) {
        Responsed<Customer> responsed = new Responsed<Customer>();
        if(StringUtils.isBlank(name)){
            responsed.setSuccess(false);
            responsed.setMsg("客户名称不能为空！");
            return responsed;
        }
        if(StringUtils.isBlank(phone) && StringUtils.isBlank(weChat)){
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
        customer.setChatRecord(chatRecord);
        customer.setSituation(sutuation);
        customer.setQq(qq);
        customer.setUploader(uploader);
        customer.setProcess(process);
        customer.setComment(comment);
        customerService.update(customer);
        responsed.setSuccess(true);
        responsed.setMsg("添加成功");
        responsed.setResult(customer);
        return responsed;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public Responsed<Customer> getById(@PathVariable("id") String id) {
        Customer customer = customerService.getById(id);
        return new Responsed<Customer>("查询成功", customer);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Responsed<Customer> delete(@PathVariable("id") String id) {
        customerService.delete(id);
        return new Responsed<Customer>("删除成功", null);
    }

    @RequestMapping(value = "/downLoadModel", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public void downLoadModel(HttpServletResponse response) {
//        http://localhost:8886/api/1.0/customer/downLoadModel
        String fileName = "customer_model.xls".toString(); // 文件的默认保存名
        try {
            String path = "";
            if ("production".equals(env)) {
                path = "/opt/project/cloned/tep/" + fileName;
            } else {
                String pathpath = "G:\\crm\\";
                File filePath2 = new File(pathpath);
                if (!filePath2.exists()) {
                    filePath2.mkdir();
                }
                path = filePath2 + "\\" + fileName;
            }
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
    public void exportFile(HttpServletResponse response, String name,
                           String situation, String startTime, String endTime) {
//        http://localhost:8886/api/1.0/customer/exportFile
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
        String modelName = "exportModel.xls".toString(); // 文件的默认保存名
        String fileName = "export_" + sf.format(new Date()) + ".xls"; // 文件的默认保存名
        // 读到流中
//        http://localhost:8886/api/1.0/customer/exportFile
        System.out.println("namenamename: " + name);
//        System.out.println("channelchannelchannelchannel: " + channel);
//        System.out.println("customerService1customerService1: " + cs);
        List<Customer> list = customerService.findAllByMix(name, situation, startTime, endTime);
        System.out.println("exportFileexportFileexportFile: " + list.size());
        try {
            List<Map> listMap = new ArrayList<Map>();
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
            listMap.add(dataMapHead);
            for (int i = 0; i < list.size(); i++) {
                Map<String, String> dataMap = new HashMap<String, String>();
                Customer customer = list.get(i);
                dataMap.put("time", customer.getTime().toString());
                dataMap.put("follower", customer.getFollower());
                dataMap.put("address", customer.getAddress());
                dataMap.put("name", customer.getName());
                dataMap.put("sex", customer.getSex().toString());
                dataMap.put("phone", customer.getPhone());
                dataMap.put("weChat", customer.getWeChat());
                dataMap.put("link", customer.getLink());
                dataMap.put("chatRecord", customer.getChatRecord());
                dataMap.put("situation", customer.getSituation());
                listMap.add(dataMap);
            }
            String modelPath = "";
            String exportPath = "";
            System.out.println("AAAAAAAAAAAAAAAAAA: " + env);
            if ("production".equals(env)) {
                modelPath = "/opt/project/cloned/tep/" + modelName;
                exportPath = "/opt/project/cloned/tep/" + fileName;
            } else {
                String pathpath = "G:\\crm\\";
                File filePath2 = new File(pathpath);
                if (!filePath2.exists()) {
                    filePath2.mkdir();
                }
                modelPath = filePath2 + "\\" + modelName;
                exportPath = filePath2 + "\\" + fileName;
                FileUtils.copyFile(new File(modelPath), new File(exportPath));
            }
            System.out.println("AAAAAAAAAAAAAAAAAA: " + modelPath);
            WriteExcel.writeExcel(listMap, 10, exportPath);/**/
            InputStream inStream = new FileInputStream(exportPath);// 文件的存放路径
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

    @RequestMapping(value = "/importFile", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public Responsed<List<String>> importFile(HttpServletRequest request, @RequestParam("file") MultipartFile file) throws Exception {
        List<String> badList =  customerService.importFile(file);
        Responsed<List<String>> responsed = new Responsed<List<String>>();
        responsed.setSuccess(true);
        responsed.setMsg("导入成功");
        responsed.setResult(badList);
        return responsed;
    }


}
