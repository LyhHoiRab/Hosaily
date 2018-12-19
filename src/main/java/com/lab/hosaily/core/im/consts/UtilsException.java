package com.lab.hosaily.core.im.consts;

import java.text.MessageFormat;

/**
 * 工具类异常
 */
public class UtilsException extends RuntimeException{

    public UtilsException(){

    }

    public UtilsException(Throwable cause){
        super(cause);
    }

    public UtilsException(String message, Throwable cause){
        super(message, cause);
    }

    public UtilsException(String message, Object... args){
        this(MessageFormat.format(message, args));
    }

    public UtilsException(Enum clazz, Throwable cause, Object... args){
        this((String) MessageFormat.format(clazz.toString(), args), (Throwable) cause);
    }

    public UtilsException(String message){
        super(message);
    }

    public UtilsException(Enum clazz, Object... args){
        super(MessageFormat.format(clazz.toString(), args));
    }
}
