package com.lab.hosaily.core.course.webservice;

/**
 * Created by miku03 on 2017/11/24.
 */

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
import java.util.List;

@Component("UploadServlet")
public class UploadServlet extends HttpServlet
{
     @Autowired(required = true)
    private RecordService recordService;


//    private RecordService recordService;
//    private ApplicationContext applicationContext;

//    public void init(ServletConfig config) throws ServletException {
//        super.init(config);
//        applicationContext = WebApplicationContextUtils.getRequiredWebApplicationContext(this.getServletContext());
//        recordService = (RecordService) applicationContext.getBean("RecordService");
//    }


//    public void init(ServletConfig servletConfig) throws ServletException {
//        ServletContext servletContext = servletConfig.getServletContext();
//        WebApplicationContext webApplicationContext = WebApplicationContextUtils.getWebApplicationContext(servletContext);
//        AutowireCapableBeanFactory autowireCapableBeanFactory = webApplicationContext.getAutowireCapableBeanFactory();
//        recordService = (RecordService)autowireCapableBeanFactory.configureBean(this, "recordService");
//    }


    protected void service(HttpServletRequest request,
                           HttpServletResponse response) throws ServletException, IOException
    {
        try
        {

//            String outGoingNum = request.getParameter("outGoingNum");
//            String sim = request.getParameter("sim");
//            String time = request.getParameter("time");
            System.out.println("TTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTT: ");





            request.setCharacterEncoding("UTF-8"); // 设置处理请求参数的编码格式
            response.setContentType("text/html;charset=UTF-8"); // 设置Content-Type字段值
            PrintWriter out = response.getWriter();

// 下面的代码开始使用Commons-UploadFile组件处理上传的文件数据
            FileItemFactory factory = new DiskFileItemFactory(); // 建立FileItemFactory对象
            ServletFileUpload upload = new ServletFileUpload(factory);
// 分析请求，并得到上传文件的FileItem对象
            List<FileItem> items = upload.parseRequest(request);
// 从web.xml文件中的参数中得到上传文件的路径
            String uploadPath = "E:\\test\\";
            File file = new File(uploadPath);
            if (!file.exists())
            {
                file.mkdir();
            }
            String filename = ""; // 上传文件保存到服务器的文件名
            InputStream is = null; // 当前上传文件的InputStream对象
// 循环处理上传文件
            for (FileItem item : items)
            {
// 处理普通的表单域
                if (item.isFormField())
                {
                    if (item.getFieldName().equals("filename"))
                    {
// 如果新文件不为空，将其保存在filename中
                        if (!item.getString().equals(""))
                            filename = item.getString("UTF-8");
                    }
                }
// 处理上传文件
                else if (item.getName() != null && !item.getName().equals(""))
                {
// 从客户端发送过来的上传文件路径中截取文件名
                    filename =item.getName().substring(
                            item.getName().lastIndexOf("\\") + 1);
                    is = item.getInputStream(); // 得到上传文件的InputStream对象
                }
            }
// 将路径和上传文件名组合成完整的服务端路径
            String outFilePath = uploadPath + filename;
// 如果服务器已经存在和上传文件同名的文件，则输出提示信息
            if (new File(outFilePath).exists())
            {
                new File(outFilePath).delete();
            }
// 开始上传文件
            if (!outFilePath.equals(""))
            {
// 用FileOutputStream打开服务端的上传文件
                FileOutputStream fos = new FileOutputStream(outFilePath);
                byte[] buffer = new byte[8192]; // 每次读8K字节
                int count = 0;
// 开始读取上传文件的字节，并将其输出到服务端的上传文件输出流中
                while ((count = is.read(buffer)) > 0)
                {
                    fos.write(buffer, 0, count); // 向服务端文件写入字节流

                }
                fos.close(); // 关闭FileOutputStream对象
                is.close(); // InputStream对象
                System.out.println("recordServicerecordServicerecordServicerecordServicerecordService: ");

                System.out.println("recordServicerecordServicerecordServicerecordServicerecordService: " + filename);

//                RecordService recordService = new RecordServiceImpl();


                String[] paramArr = filename.split("_");
                System.out.println("recordServicerecordServicerecordServicerecordServicerecordService: " + paramArr[0]);
                System.out.println("recordServicerecordServicerecordServicerecordServicerecordService: " + paramArr[1]);
                System.out.println("recordServicerecordServicerecordServicerecordServicerecordService: " + paramArr[2]);
                System.out.println("recordServicerecordServicerecordServicerecordServicerecordService: " + paramArr[3]);
                System.out.println("recordServicerecordServicerecordServicerecordServicerecordService: " + paramArr[3].split("\\.")[0]);

                Record record = new Record();
                record.setSim(paramArr[1]);
                record.setOutGoingNum(paramArr[2]);
                record.setTime(paramArr[3].split("\\.")[0]);
                record.setPath(outFilePath);
                recordService.save(record);
                System.out.println("recordServicerecordServicerecordServicerecordServicerecordService: ");
                out.println("文件上传成功!");

            }
        }
        catch(Exception e){
            throw new ApplicationException(e.getMessage(), e);
        }
    }}
