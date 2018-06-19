package core.client.dao;

import com.lab.hosaily.core.client.dao.ContractDao;
import com.lab.hosaily.core.client.entity.Contract;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring.xml", "classpath:springbean.xml"})
@ActiveProfiles(value = "test")
public class ContractDaoTest{

    @Autowired
    private ContractDao contractDao;

    @Test
    public void save(){
        Contract contract = new Contract();
        contractDao.save(contract);
    }
}
