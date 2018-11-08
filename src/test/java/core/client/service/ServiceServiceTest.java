package core.client.service;

import com.lab.hosaily.core.product.consts.ServiceType;
import com.lab.hosaily.core.product.dao.ServiceDao;
import com.lab.hosaily.core.product.entity.Service;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring.xml", "classpath:springbean.xml"})
@ActiveProfiles(value = "test")
public class ServiceServiceTest{

    @Autowired
    private ServiceDao serviceDao;

    @Test
    public void save(){
        String id = "0";

        Service service1 =  new Service();
        service1.setMasterId(id);
        service1.setType(ServiceType.ONE_TO_ONE);
        service1.setName("情商导师电话咨询指导(60分钟/次),单价2000元/次");
        service1.setDescription("情商导师电话咨询指导(60分钟/次),单价2000元/次");
        service1.setTime(8);
        service1.setUnitPrice(2000d);
        service1.setSort(0);

        Service service2 =  new Service();
        service2.setMasterId(id);
        service2.setType(ServiceType.ONE_TO_ONE);
        service2.setName("关系管理导师电话咨询指导(60分钟/次),单价2000元/次");
        service2.setDescription("关系管理导师电话咨询指导(60分钟/次),单价2000元/次");
        service2.setTime(8);
        service2.setUnitPrice(2000d);
        service2.setSort(1);

        Service service3 =  new Service();
        service3.setMasterId(id);
        service3.setType(ServiceType.ONE_TO_ONE);
        service3.setName("微信语音指导(60分钟/次),单价1800元/次");
        service3.setDescription("微信语音指导(60分钟/次),单价1800元/次");
        service3.setTime(6);
        service3.setUnitPrice(1800d);
        service3.setSort(2);

        Service service4 =  new Service();
        service4.setMasterId(id);
        service4.setType(ServiceType.ONE_TO_ONE);
        service4.setName("期望管理建设(60分钟/次),单价1800元/次/小时");
        service4.setDescription("期望管理建设(60分钟/次),单价1800元/次/小时");
        service4.setTime(2);
        service4.setUnitPrice(1800d);
        service4.setSort(3);

        Service service5 =  new Service();
        service5.setMasterId(id);
        service5.setType(ServiceType.ONE_TO_ONE);
        service5.setName("微信跟踪服务");
        service5.setDescription("微信跟踪服务");
        service5.setTime(-1);
        service5.setUnitPrice(0d);
        service5.setSort(4);

        Service service6 =  new Service();
        service6.setMasterId(id);
        service6.setType(ServiceType.VIDEO_LEARNING_PERMISSION);
        service6.setName("1年幸福卫士课程学习");
        service6.setDescription("1年幸福卫士课程学习");
        service6.setTime(-1);
        service6.setUnitPrice(0d);
        service6.setSort(0);

        List<Service> list = new ArrayList<Service>();
        list.add(service1);
        list.add(service2);
        list.add(service3);
        list.add(service4);
        list.add(service5);
        list.add(service6);

        serviceDao.save(id, list);
    }
}
