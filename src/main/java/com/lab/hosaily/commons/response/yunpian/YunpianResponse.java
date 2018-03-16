package com.lab.hosaily.commons.response.yunpian;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class YunpianResponse{

    private Integer code;
    private String msg;
    private Integer count;
    private Double fee;
    private String unit;
    private String mobile;
    private Long sid;
}
