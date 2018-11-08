package core.client.webservice;

import com.lab.hosaily.core.client.consts.AgreementState;
import com.lab.hosaily.core.client.dao.AgreementDao;
import com.lab.hosaily.core.client.entity.Agreement;
import com.lab.hosaily.core.client.service.AgreementService;
import com.lab.hosaily.core.organization.dao.OrganizationDao;
import com.lab.hosaily.core.organization.entity.Organization;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring.xml", "classpath:springbean.xml"})
@ActiveProfiles(value = "test")
public class AgreementRestControllerTest{

    @Autowired
    private AgreementDao agreementDao;

    @Autowired
    private OrganizationDao organizationDao;

    @Test
    public void save(){
        String serviceId = "df3b550d1d164defaeb9e7eba5ba6fe8";
        String organizationId = "ad748e6d57be453f920f2953ddf0bb70";

        Organization organization = organizationDao.getById(organizationId);

        Agreement agreement = new Agreement();
        agreement.setServiceId(serviceId);
        //设置状态
        agreement.setState(AgreementState.CREATED);
        agreement.setEdit(true);
        //设置公司信息
        agreement.setCompany(organization.getCompany());
        agreement.setLicenseNumber(organization.getLicenseNumber());
        agreement.setLegalPerson(organization.getLegalPerson());
        agreement.setCompanyAddress(organization.getCompanyAddress());
        agreement.setCompanyPhone(organization.getCompanyPhone());
        agreement.setCompanyEmail(organization.getCompanyEmail());
        agreement.setCompanyWebsite(organization.getCompanyWebsite());
        //设置服务
        agreement.setPrice(28100d);
        agreement.setRetail(28100d);
        agreement.setDuration(2d);
        agreement.setName("女神计划咨询套餐");
        //保存协议
        agreementDao.saveOrUpdate(agreement);
    }
}
