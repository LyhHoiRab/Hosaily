package core.client.dao;

import com.lab.hosaily.core.client.dao.RegulationDao;
import com.lab.hosaily.core.client.entity.Regulation;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring.xml", "classpath:springbean.xml"})
@ActiveProfiles(value = "test")
public class RegulationDaoTest{

    @Autowired
    private RegulationDao regulationDao;

    @Test
    public void save(){
        String contractId = "821c7fd00b8542f783ccba9691d928b9";

        Regulation regulation_9 = new Regulation();
        regulation_9.setContractId(contractId);
        regulation_9.setSort(8);
        regulation_9.setLevelUp(0);
        regulation_9.setTitle("九、商业贿赂");
        regulationDao.saveOrUpdate(regulation_9);

        Regulation regulation_10 = new Regulation();
        regulation_10.setContractId(contractId);
        regulation_10.setSort(9);
        regulation_10.setLevelUp(0);
        regulation_10.setTitle("十、其他事项");
        regulationDao.saveOrUpdate(regulation_10);
    }

    @Test
    public void saveChild(){
        String parentId = "30e08a856ee2478aba6ac1c2fd46d9aa";
        String contarctId = "821c7fd00b8542f783ccba9691d928b9";

        Regulation regulation_1 = new Regulation();
        regulation_1.setContractId(contarctId);
        regulation_1.setParentId(parentId);
        regulation_1.setLevelUp(2);
        regulation_1.setSort(0);
        regulation_1.setContent("2.2.1 产品组成 = 一对一情感跟踪服务费用 + 幸福管家学习权限");
        regulationDao.saveOrUpdate(regulation_1);

        Regulation regulation_2 = new Regulation();
        regulation_2.setContractId(contarctId);
        regulation_2.setParentId(parentId);
        regulation_2.setLevelUp(2);
        regulation_2.setSort(1);
        regulation_2.setContent("2.2.2 电话咨询的购买价格与服务人员级别相对应,服务人员级别越高,对应的单 价越高,但服务有效期统一,即购买 1 小时有效期为 10 个自然日,购买 2 小时有效期 为 20 个自然日,以此类推。");
        regulationDao.saveOrUpdate(regulation_2);

        Regulation regulation_3 = new Regulation();
        regulation_3.setContractId(contarctId);
        regulation_3.setParentId(parentId);
        regulation_3.setLevelUp(2);
        regulation_3.setSort(2);
        regulation_3.setContent("2.2.3 若您在套餐次数使用完毕后仍继续单独购买该单项服务的,应按该价格标准 续费执行。");
        regulationDao.saveOrUpdate(regulation_3);
    }
}
