package com.lab.hosaily.core.course.webservice;

import com.lab.hosaily.core.course.entity.Record;
import com.lab.hosaily.core.course.service.RecordService;
import com.rab.babylon.commons.security.exception.ApplicationException;
import com.rab.babylon.commons.security.response.Page;
import com.rab.babylon.commons.security.response.PageRequest;
import com.rab.babylon.commons.security.response.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;

@RestController
@RequestMapping(value = "/api/1.0/record")
public class RecordRestController {

    private static Logger logger = LoggerFactory.getLogger(RecordRestController.class);

    @Autowired
    private RecordService recordService;

    /**
     * 保存记录
     */
    @RequestMapping(value = "", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Response<Record> save(@RequestBody Record record){
        try{
            recordService.save(record);
            return new Response<Record>("添加成功", record);
        }catch(Exception e){
            logger.error(e.getMessage(), e);
            throw new ApplicationException(e.getMessage(), e);
        }
    }

    /**
     * 更新记录
     */
    @RequestMapping(value = "", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Response<Record> update(@RequestBody Record record){
        try{
            recordService.update(record);
            return new Response<Record>("修改成功", record);
        }catch(Exception e){
            logger.error(e.getMessage(), e);
            throw new ApplicationException(e.getMessage(), e);
        }
    }

    /**
     * 根据ID查询记录
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public Response<Record> getById(@PathVariable("id") String id){
        try{
            Record record = recordService.getById(id);
            return new Response<Record>("查询成功", record);
        }catch(Exception e){
            logger.error(e.getMessage(), e);
            throw new ApplicationException(e.getMessage(), e);
        }
    }

    /**
     * 分页查询
     */
    @RequestMapping(value = "/page", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public Response<Page<Record>> page(Long pageNum, Long pageSize, String userName, String num, String outGoingNum, String sim, String userType, String organizationId){
        try{
            PageRequest pageRequest = new PageRequest(pageNum, pageSize);
            Page<Record> page = recordService.page(pageRequest, userName, num, outGoingNum, sim, userType, organizationId);

            return new Response<Page<Record>>("查询成功", page);
        }catch(Exception e){
            logger.error(e.getMessage(), e);
            throw new ApplicationException(e.getMessage(), e);
        }
    }


    @RequestMapping("/testHttpMessageDown/{id}")
    public ResponseEntity<byte[]> download(HttpServletRequest request, @PathVariable("id") String id) throws IOException {
        System.out.println("TTTTTTTTTTTTTDDDDDDDDDDDDDDDDDD: " + id);
        Record record = recordService.getById(id);
        File file = new File(record.getPath());
        byte[] body = null;
        InputStream is = new FileInputStream(file);
        body = new byte[is.available()];
        is.read(body);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "attchement;filename=" + file.getName());
        HttpStatus statusCode = HttpStatus.OK;
        ResponseEntity<byte[]> entity = new ResponseEntity<byte[]>(body, headers, statusCode);
        return entity;
    }

    @RequestMapping("/testHttpMessagePlay/{id}")
    public ModelAndView testHttpMessagePlay(HttpServletRequest request, @PathVariable("id") String id, HttpServletResponse response) throws IOException {
        System.out.println("TTTTTTTTTTTTTDDDDDDDDDDDDDDDDDD: " + id);
        Record record = recordService.getById(id);
        File file = new File(record.getPath());

        String range = request.getHeader("Range");
        String[] rs = range.split("\\=");
        range = rs[1].split("\\-")[0];

//        String path = request.getServletContext().getRealPath("/");
//        File file = new File(path + "/WEB-INF/mp3/" + id + ".mp3");
        if(!file.exists()) throw new RuntimeException("音频文件不存在 --> 404");
        OutputStream os = response.getOutputStream();
        FileInputStream fis = new FileInputStream(file);
        long length = file.length();
        System.out.println("file length : " + length);
        // 播放进度
        int count = 0;
        // 播放百分比
        int percent = (int)(length * 0.4);

        int irange = Integer.parseInt(range);
        length = length - irange;

        response.addHeader("Accept-Ranges", "bytes");
        response.addHeader("Content-Length", length + "");
        response.addHeader("Content-Range", "bytes " + range + "-" + length + "/" + length);
        response.addHeader("Content-Type", "audio/mpeg;charset=UTF-8");

        int len = 0;
        byte[] b = new byte[1024];
        while ((len = fis.read(b)) != -1) {
            os.write(b, 0, len);
            count += len;
            if(count >= percent){
                break;
            }
        }
        System.out.println("count: " + count + ", percent: " + percent);
        fis.close();
        os.close();
        return null;
    }


    /**
     * 删除
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Response delete(@PathVariable("id") String id){
        try{
            recordService.delete(id);

            return new Response("删除成功", null);
        }catch(Exception e){
            logger.error(e.getMessage(), e);
            throw new ApplicationException(e.getMessage(), e);
        }
    }

}
