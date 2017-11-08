package com.lab.hosaily.core.popularize.webservice;

import com.lab.hosaily.core.popularize.entity.TestLibrary;
import com.lab.hosaily.core.popularize.service.TestLibraryService;
import com.rab.babylon.commons.security.exception.ApplicationException;
import com.rab.babylon.commons.security.response.Page;
import com.rab.babylon.commons.security.response.PageRequest;
import com.rab.babylon.commons.security.response.Response;
import com.rab.babylon.core.consts.entity.UsingState;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import java.util.Date;

@RestController
@RequestMapping(value = "/api/1.0/testLibrary")
public class TestLibraryRestController{

    private static Logger logger = LoggerFactory.getLogger(TestLibraryRestController.class);

    @Autowired
    private TestLibraryService testLibraryService;

    @RequestMapping(value = "", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Response<TestLibrary> save(@RequestBody TestLibrary library){
        try{
            testLibraryService.save(library);

            return new Response<TestLibrary>("添加成功", library);
        }catch(Exception e){
            logger.error(e.getMessage(), e);
            throw new ApplicationException(e.getMessage(), e);
        }
    }

    @RequestMapping(value = "", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Response<TestLibrary> update(@RequestBody TestLibrary library){
        try{
            testLibraryService.update(library);

            return new Response<TestLibrary>("修改成功", library);
        }catch(Exception e){
            logger.error(e.getMessage(), e);
            throw new ApplicationException(e.getMessage(), e);
        }
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Response delete(@PathVariable("id") String id){
        try{
            testLibraryService.delete(id);

            return new Response("删除成功", null);
        }catch(Exception e){
            logger.error(e.getMessage(), e);
            throw new ApplicationException(e.getMessage(), e);
        }
    }

    @RequestMapping(value = "/answer/{id}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Response deleteAnswer(@PathVariable("id") String answerId){
        try{
            testLibraryService.deleteAnswer(answerId);

            return new Response("删除成功", null);
        }catch(Exception e){
            logger.error(e.getMessage(), e);
            throw new ApplicationException(e.getMessage(), e);
        }
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public Response<TestLibrary> getById(@PathVariable("id") String id){
        try{
            TestLibrary library = testLibraryService.getById(id);

            return new Response<TestLibrary>("查询成功", library);
        }catch(Exception e){
            logger.error(e.getMessage(), e);
            throw new ApplicationException(e.getMessage(), e);
        }
    }

    @RequestMapping(value = "/page", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public Response<Page<TestLibrary>> page(Long pageNum, Long pageSize, UsingState state, Date createTime, String kind, String title){
        try{
            PageRequest pageRequest = new PageRequest(pageNum, pageSize);
            Page<TestLibrary> page = testLibraryService.page(pageRequest, state, createTime, kind, title);

            return new Response<Page<TestLibrary>>("查询成功", page);
        }catch(Exception e){
            logger.error(e.getMessage(), e);
            throw new ApplicationException(e.getMessage(), e);
        }
    }

    @RequestMapping(value = "/upload", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public Response<String> upload(@RequestParam("file") CommonsMultipartFile file){
        try{
            String url = testLibraryService.upload(file);

            return new Response<String>("上传成功", url);
        }catch(Exception e){
            logger.error(e.getMessage(), e);
            throw new ApplicationException(e.getMessage(), e);
        }
    }
}
