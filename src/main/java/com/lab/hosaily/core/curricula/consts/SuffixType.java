package com.lab.hosaily.core.curricula.consts;

public enum SuffixType{
    //未知
    UNKNOWN("unknown", MediaType.UNKNOWN),
    //视频
    RM(".rm", MediaType.VIDEO),
    RMVB(".rmvb", MediaType.VIDEO),
    WMV(".wmv", MediaType.VIDEO),
    AVI(".avi", MediaType.VIDEO),
    MP4(".mp4", MediaType.VIDEO),
    GP_3(".3gp", MediaType.VIDEO),
    MKV(".mkv", MediaType.VIDEO),
    MPEG(".mpeg", MediaType.VIDEO),
    ASF(".asf", MediaType.VIDEO),
    MOV(".mov", MediaType.VIDEO),
    FLV(".flv", MediaType.VIDEO),
    F4V(".f4v", MediaType.VIDEO),
    M4V(".m4v", MediaType.VIDEO),
    //音频
    WAV(".wav", MediaType.AUDIO),
    MP3(".mp3", MediaType.AUDIO),
    WMA(".wma", MediaType.AUDIO),
    RA(".ra", MediaType.AUDIO),
    MID(".mid", MediaType.AUDIO),
    OGG(".ogg", MediaType.AUDIO),
    APE(".ape", MediaType.AUDIO),
    ACC(".acc", MediaType.AUDIO),
    FLAC(".flac", MediaType.AUDIO),
    WV(".wv", MediaType.AUDIO),
    WAVE(".wave", MediaType.AUDIO),
    //图片
    BMP(".bmp", MediaType.IMG),
    PNG(".png", MediaType.IMG),
    JPEG(".jpeg", MediaType.IMG),
    JPG(".jpg", MediaType.IMG),
    GIF(".gif", MediaType.IMG),
    TIFF(".tif", MediaType.IMG),
    PSD(".psd", MediaType.IMG),
    RAW(".raw", MediaType.IMG),
    //文本
    EXCEL(".xls", MediaType.TEXT),
    CSV(".csv", MediaType.TEXT),
    TEXT(".txt", MediaType.TEXT),
    WORD(".doc", MediaType.TEXT),
    WORDX(".docx", MediaType.TEXT),
    HTML(".html", MediaType.TEXT),
    HTM(".htm", MediaType.TEXT);

    private String id;
    private MediaType type;

    private SuffixType(String id, MediaType type){
        this.id = id;
        this.type = type;
    }

    public String getId(){
        return id;
    }

    public MediaType getType(){
        return type;
    }

    public static SuffixType getById(String id){
        for(SuffixType type : SuffixType.values()){
            if(type.getId().equals(id)){
                return type;
            }
        }

        return UNKNOWN;
    }
}
