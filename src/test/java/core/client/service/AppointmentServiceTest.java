package core.client.service;

import com.lab.hosaily.core.client.service.AppointmentService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring.xml", "classpath:springbean.xml"})
@ActiveProfiles(value = "test")
public class AppointmentServiceTest{

    @Autowired
    private AppointmentService appointmentService;

    @Test
    public void sendSms(){
        appointmentService.sendCaptcha("13246308797");
    }

    @Test
    public void validate(){
        String phone = "13246308797";
        String captcha = "8720";

        System.out.println(appointmentService.validateCaptcha(phone, captcha));
    }
}
