package com.lab.hosaily.core.exception.handler;

import com.rab.babylon.commons.security.exception.ApplicationException;
import com.rab.babylon.commons.security.response.Response;
import org.apache.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class ExceptionController{

    /**
     * 应用异常处理
     */
    @ExceptionHandler(value = ApplicationException.class)
    @ResponseBody
    public Response applicationExceptionHandler(ApplicationException e){
        Response response = new Response();
        response.setCode(HttpStatus.SC_INTERNAL_SERVER_ERROR);
        response.setMsg(e.getMessage());
        response.setSuccess(false);
        response.setResult(null);

        return response;
    }
}
