/**
 * @license Copyright (c) 2003-2017, CKSource - Frederico Knabben. All rights reserved.
 * For licensing, see LICENSE.md or http://ckeditor.com/license
 */

CKEDITOR.editorConfig = function(config){
    config.enterMode = CKEDITOR.ENTER_BR;
    config.shiftEnterMode = CKEDITOR.ENTER_BR;
    //图片上传
    config.filebrowserImageUploadUrl = '/api/1.0/ckEditor/upload';
    config.filebrowserImageBrowseUrl = '/api/1.0/ckEditor/upload';
    //Flash上传
    config.filebrowserFlashUploadUrl = '/api/1.0/ckEditor/upload';
    config.filebrowserFlashBrowseUrl = '/api/1.0/ckEditor/upload';
    //预览区域
    config.image_previewText = ' ';
    //高度
    config.height = 400;
    //语言
    config.language = 'zh-cn';
    //皮肤
    config.skin = 'moono-lisa';
};
