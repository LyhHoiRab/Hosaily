package utils;

import com.lab.hosaily.commons.utils.YunpianUtils;
import com.lab.hosaily.core.client.consts.SMSConfig;
import com.lab.hosaily.core.client.consts.SMSTemplate;

import java.text.MessageFormat;

public class YunpianUtilsTest{

    public static void main(String[] args){
        String mobile = "13246308797";
        SMSConfig config = SMSConfig.NINE_LAB;
        String captch = "3306";
        String template = MessageFormat.format(SMSTemplate.CAPTCHA, config.getCompany(), captch);

        YunpianUtils.sendSms(config.getApiKey(), template, mobile);
    }
}
