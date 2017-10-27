package com.lab.hosaily.core.handler.service;

import org.springframework.web.multipart.commons.CommonsMultipartFile;

public interface CKEditorService {

    /**
     * 上传
     */
    String upload(CommonsMultipartFile file);
}
