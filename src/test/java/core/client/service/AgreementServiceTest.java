package core.client.service;

import com.lab.hosaily.core.client.entity.Agreement;
import com.lab.hosaily.core.client.service.AgreementService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring.xml", "classpath:springbean.xml"})
@ActiveProfiles(value = "test")
public class AgreementServiceTest{

    @Autowired
    private AgreementService agreementService;

    @Test
    public void create(){
        String serviceId = "c4a58dba48a344ffb00baacec4e1bd58";

        String productId_1 = "28602396aed64c158f014fb9d3c297b5";
        String productId_2 = "2a70cc0947284f269a22bc07905adbba";
        String productId_3 = "e64c456c306c401b895f719f9b61b079";
        String productId_4 = "e926d101c23146c9ae7513747af481aa";
        String productId_5 = "fdd13cad703e4f938db5ecda01975757";
    }

    @Test
    public void getById(){
        Agreement agreement = agreementService.getById("8795d64805b9453496a45a96582fe8ca");
        System.out.println(agreement);
    }
}
