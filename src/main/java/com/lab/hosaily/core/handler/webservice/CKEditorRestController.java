package com.lab.hosaily.core.handler.webservice;

import com.lab.hosaily.core.handler.service.CKEditorService;
import com.rab.babylon.commons.security.exception.ApplicationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

@RestController
@RequestMapping(value = "/api/1.0/ckEditor")
public class CKEditorRestController{

    private static Logger logger = LoggerFactory.getLogger(CKEditorRestController.class);

    @Autowired
    private CKEditorService ckEditorService;

    @RequestMapping(value = "/upload", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public void uploadFile(HttpServletRequest request, HttpServletResponse response, @RequestParam(value = "upload") CommonsMultipartFile file){
        try{
            String url = ckEditorService.upload(file);
            String callback = request.getParameter("CKEditorFuncNum");

            PrintWriter writer = response.getWriter();
            writer.println("<script type=\"text/javascript\">");
            writer.print("window.parent.CKEDITOR.tools.callFunction(" + callback + ",'" + url + "','')");
            writer.print("</script>");
        }catch(Exception e){
            logger.error(e.getMessage(), e);
            throw new ApplicationException(e.getMessage(), e);
        }
    }
}
