package com.lab.hosaily.core.course.webservice;

/**
 * Created by miku03 on 2017/11/24.
 */

import com.lab.hosaily.commons.utils.UpyunUtils;
import com.lab.hosaily.core.course.entity.Record;
import com.lab.hosaily.core.course.service.RecordService;
import com.rab.babylon.commons.security.exception.ApplicationException;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Component("UploadServlet")
public class UploadServlet extends HttpServlet {
    @Autowired(required = true)
    private RecordService recordService;

    protected void service(HttpServletRequest request,
                           HttpServletResponse response) throws ServletException, IOException {
        try {
            System.out.println("UploadServlet start...........................................");


//            String outGoingNum = request.getParameter("outGoingNum");
//            String sim = request.getParameter("sim");
//            String time = request.getParameter("time");
            request.setCharacterEncoding("UTF-8"); // 设置处理请求参数的编码格式
            response.setContentType("text/html;charset=UTF-8"); // 设置Content-Type字段值
            PrintWriter out = response.getWriter();

// 下面的代码开始使用Commons-UploadFile组件处理上传的文件数据
            FileItemFactory factory = new DiskFileItemFactory(); // 建立FileItemFactory对象
            ServletFileUpload upload = new ServletFileUpload(factory);
// 分析请求，并得到上传文件的FileItem对象
            List<FileItem> items = upload.parseRequest(request);
// 从web.xml文件中的参数中得到上传文件的路径
//            String uploadPath = "E://test//";
////            String uploadPath = "/opt/project/hsl_api/luyin/";
//            File file = new File(uploadPath);
//            if (!file.exists()) {
//                file.mkdir();
//            }
            String filename = ""; // 上传文件保存到服务器的文件名
            InputStream is = null; // 当前上传文件的InputStream对象


// 循环处理上传文件
            for (FileItem item : items) {
// 处理普通的表单域
                if (item.isFormField()) {
                    if (item.getFieldName().equals("filename")) {
// 如果新文件不为空，将其保存在filename中
                        if (!item.getString().equals(""))
                            filename = item.getString("UTF-8");
                    }
                }
// 处理上传文件
                else if (item.getName() != null && !item.getName().equals("")) {
// 从客户端发送过来的上传文件路径中截取文件名
                    filename = item.getName().substring(
                            item.getName().lastIndexOf("\\") + 1);

                    System.out.println("filename: " + filename);


                    if (filename.endsWith(".db")) {
//                        System.out.println("db文件上传start!");
//                        String uploadPath = "E://test//";
////            String uploadPath = "/opt/project/hsl_api/luyin/";
//                        File file = new File(uploadPath);
//                        if (!file.exists()) {
//                            file.mkdir();
//                        }
//                        is = item.getInputStream(); // 得到上传文件的InputStream对象
//                        // 将路径和上传文件名组合成完整的服务端路径
//                        String outFilePath = uploadPath + filename;
//                        // 如果服务器已经存在和上传文件同名的文件，则输出提示信息
//                        if (new File(outFilePath).exists()) {
//                            new File(outFilePath).delete();
//                        }
//                        // 开始上传文件
//                        if (!outFilePath.equals("")) {
//                            // 用FileOutputStream打开服务端的上传文件
//                            FileOutputStream fos = new FileOutputStream(outFilePath);
//                            byte[] buffer = new byte[8192]; // 每次读8K字节
//                            int count = 0;
//                            // 开始读取上传文件的字节，并将其输出到服务端的上传文件输出流中
//                            while ((count = is.read(buffer)) > 0) {
//                                fos.write(buffer, 0, count); // 向服务端文件写入字节流
//                            }
//                            fos.close(); // 关闭FileOutputStream对象
//                            is.close(); // InputStream对象
//                            out.println("db文件上传成功!");
//                        }


                        //上传路径
//                        13e641322d9c097d047afff4e298872a_EnMicroMsg.db
                        System.out.println("db文件上传start!");
                        String filePath = UpyunUtils.RECORD_WX_DB_DIR + filename;
                        System.out.println(filePath);
                        //上传
                        boolean result = UpyunUtils.writerFile(filePath, item.getInputStream(), true, null);
                        System.out.println("AAAAAAAAAAAAAAA: " + result);
                        String[] paramArr = filename.split("_");
                        String outFilePath = "http://upyun.elletter.com" + filePath;
                        System.out.println("AAAAAAAAAAAAAAA: " + outFilePath);
                        Record record = new Record();
                        record.setCreateTime(new Date());
                        record.setFileType("db");
                        record.setUidMd5(paramArr[0]);
                        recordService.save(record);
                        out.println("db文件上传成功!");
                    } else {
                        //上传路径
                        System.out.println("mp3文件上传start!");
                        String filePath = UpyunUtils.RECORD_LU_YIN_DIR + filename;
                        System.out.println(filePath);
                        //上传
                        boolean result = UpyunUtils.writerFile(filePath, item.getInputStream(), true, null);
                        System.out.println("AAAAAAAAAAAAAAA: " + result);
                        String[] paramArr = filename.split("_");
                        String outFilePath = "http://upyun.elletter.com" + filePath;
                        System.out.println("AAAAAAAAAAAAAAA: " + outFilePath);
                        Record record = new Record();
                        record.setSim(paramArr[1]);
                        record.setOutGoingNum(paramArr[2]);
                        //                record.setTime(paramArr[3].split("\\.")[0]);

                        record.setTime(Long.parseLong(paramArr[3]) / 1000 + "");
                        //时间戳转化为Sting或Date
                        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                        Long time = new Long(paramArr[4].split("\\.")[0]);
                        String d = format.format(time);
                        Date date = format.parse(d);
                        System.out.println("Format To String(Date):" + d);
                        System.out.println("Format To Date:" + date);
                        record.setCreateTime(date);
                        record.setPath(outFilePath);
                        record.setFileType("mp3");
                        recordService.save(record);
                        out.println("文件上传成功!");
                    }


                }
            }

            System.out.println("UploadServlet end...........................................");
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage(), e);
        }
    }
}
