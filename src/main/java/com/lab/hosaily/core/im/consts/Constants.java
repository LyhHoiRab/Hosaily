package com.lab.hosaily.core.im.consts;

import org.springframework.stereotype.Component;

@Component
public class Constants {

    public static final String PRIVATE_KEY = "classpath:/key/PrivateKey.pem";

    public static final String PUBLIC_KEY  = "classpath:/key/PublicKey.pem";

    public static final String IM_PRIVATE_KEY_HSL = "classpath:/key/IMPrivateKey_hsl.pem";

    public static final String IM_PUBLIC_KEY_HSL  = "classpath:/key/IMPublicKey_hsl.pem";

//    public static final String IM_PRIVATE_KEY_XYJ = "classpath:/key/IMPrivateKey_xyj.pem";
//
//    public static final String IM_PUBLIC_KEY_XYJ  = "classpath:/key/IMPublicKey_xyj.pem";
}
