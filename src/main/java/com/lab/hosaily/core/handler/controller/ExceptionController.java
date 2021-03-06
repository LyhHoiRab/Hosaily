package com.lab.hosaily.core.handler.controller;

import com.rab.babylon.commons.security.exception.ApplicationException;
import com.rab.babylon.commons.security.response.Response;
import org.apache.http.HttpStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class ExceptionController{

    private static Logger logger = LoggerFactory.getLogger(ExceptionController.class);

    /**
     * 应用异常处理
     */
    @ExceptionHandler(value = ApplicationException.class)
    @ResponseBody
    public Response applicationExceptionHandler(ApplicationException e){
        logger.error(e.getMessage(), e);

        Response response = new Response();
        response.setCode(HttpStatus.SC_INTERNAL_SERVER_ERROR);
        response.setMsg(e.getMessage());
        response.setSuccess(false);

        return response;
    }
}
