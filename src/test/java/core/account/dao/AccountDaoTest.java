package core.account.dao;

import com.lab.hosaily.core.account.dao.AccountDao;
import com.rab.babylon.core.account.entity.Account;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring.xml")
@ActiveProfiles(value = "test")
public class AccountDaoTest{

    @Autowired
    private AccountDao dao;

    @Test
    public void getByOpenIdOrUnionId(){
        String openId = "2";
        String unionId = "1";

        Account account = dao.getByOpenIdOrUnionId(openId, unionId);
    }
}
