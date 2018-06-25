package com.lab.hosaily.core.client.service;

import com.lab.hosaily.commons.utils.FileNameUtils;
import com.lab.hosaily.commons.utils.UpyunUtils;
import com.lab.hosaily.core.account.dao.UserDao;
import com.lab.hosaily.core.client.consts.AgreementState;
import com.lab.hosaily.core.client.consts.PayState;
import com.lab.hosaily.core.client.consts.PurchaseState;
import com.lab.hosaily.core.client.dao.*;
import com.lab.hosaily.core.client.entity.Agreement;
import com.lab.hosaily.core.client.entity.Contract;
import com.lab.hosaily.core.client.entity.Purchase;
import com.lab.hosaily.core.client.entity.Regulation;
import com.lab.hosaily.core.organization.dao.OrganizationDao;
import com.lab.hosaily.core.organization.entity.Organization;
import com.lab.hosaily.core.product.dao.ProductDao;
import com.lab.hosaily.core.product.dao.ServiceDao;
import com.lab.hosaily.core.product.entity.Product;
import com.rab.babylon.commons.security.exception.ServiceException;
import com.rab.babylon.commons.security.response.Page;
import com.rab.babylon.commons.security.response.PageRequest;
import com.rab.babylon.commons.utils.FileUtils;
import com.rab.babylon.core.account.entity.User;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@Transactional(readOnly = true)
public class AgreementServiceImpl implements AgreementService{

    private static Logger logger = LoggerFactory.getLogger(AgreementServiceImpl.class);

    @Autowired
    private AgreementDao agreementDao;

    @Autowired
    private ServiceDao serviceDao;

    @Autowired
    private PurchaseDao purchaseDao;

    @Autowired
    private PaymentDao paymentDao;

    @Autowired
    private ProductDao productDao;

    @Autowired
    private OrganizationDao organizationDao;

    @Autowired
    private UserDao userDao;

    @Autowired
    private ContractDao contractDao;

    /**
     * 保存
     */
    @Override
    @Transactional(readOnly = false)
    public void save(Agreement agreement){
        try{
            Assert.notNull(agreement, "协议信息不能为空");

            //保存协议
            agreementDao.saveOrUpdate(agreement);
            //保存服务
            serviceDao.save(agreement.getId(), agreement.getServices());
        }catch(Exception e){
            logger.error(e.getMessage(), e);
            throw new ServiceException(e.getMessage(), e);
        }
    }

    /**
     * 更新
     */
    @Override
    @Transactional(readOnly = false)
    public void update(Agreement agreement){
        try{
            Assert.notNull(agreement, "协议信息不能为空");
            Assert.hasText(agreement.getId(), "协议ID不能为空");

            //更新协议
            agreementDao.saveOrUpdate(agreement);
            //更新服务
            serviceDao.deleteByMasterId(agreement.getId());
            serviceDao.save(agreement.getId(), agreement.getServices());
        }catch(Exception e){
            logger.error(e.getMessage(), e);
            throw new ServiceException(e.getMessage(), e);
        }
    }

    /**
     * 根据ID查询
     */
    @Override
    public Agreement getById(String id){
        try{
            Assert.hasText(id, "协议ID不能为空");

            Agreement agreement = agreementDao.getById(id);

            Contract contract = contractDao.getByVersion(agreement.getVersion());

            List<Regulation> sorted = new ArrayList<Regulation>();

            List<Regulation> regulations = contract.getRegulations();
            if(regulations != null && !regulations.isEmpty()){
                for(Regulation child : regulations){


                    for(Regulation parent : regulations){
                        if(StringUtils.isNotBlank(child.getParentId()) && child.getParentId().equals(parent.getId())){
                            if(parent.getChildren() == null){
                                parent.setChildren(new ArrayList<Regulation>());
                            }

                            parent.getChildren().add(child);
                            break;
                        }
                    }

                    if(child.getLevelUp() == 0){
                        sorted.add(child);
                    }
                }
            }

            contract.setRegulations(sorted);
            agreement.setContract(contract);

            return agreement;
        }catch(Exception e){
            logger.error(e.getMessage(), e);
            throw new ServiceException(e.getMessage(), e);
        }
    }

    /**
     * 根据购买ID查询
     */
    @Override
    public Agreement getByPurchaseId(String purchaseId){
        try{
            Assert.hasText(purchaseId, "购买记录ID不能为空");

            Agreement agreement = agreementDao.getByPurchaseId(purchaseId);

            Double paid = paymentDao.priceByPurchaseId(purchaseId, null, PayState.PAID);
            agreement.setPaid(paid);

            return agreement;
        }catch(Exception e){
            logger.error(e.getMessage(), e);
            throw new ServiceException(e.getMessage(), e);
        }
    }

    /**
     * 协议确认
     */
    @Override
    @Transactional(readOnly = false)
    public void affirm(String id){
        try{
            Assert.hasText(id, "协议ID不能为空");

            //更新协议
            Agreement agreement = agreementDao.getById(id);

            if(agreement.getAffirmTime() == null){
                agreement.setAffirmTime(new Date());
                agreementDao.saveOrUpdate(agreement);
                //更新购买记录状态
                Purchase purchase = purchaseDao.getById(agreement.getPurchaseId());
                purchase.setPurchaseState(PurchaseState.AGREEMENT);
                purchaseDao.saveOrUpdate(purchase);
            }
        }catch(Exception e){
            logger.error(e.getMessage(), e);
            throw new ServiceException(e.getMessage(), e);
        }
    }

    @Override
    public Page<Agreement> page(PageRequest pageRequest, String accountId, String serviceId, AgreementState state){
       try{
           return agreementDao.page(pageRequest, accountId, serviceId, state);
       }catch(Exception e){
           logger.error(e.getMessage(), e);
           throw new ServiceException(e.getMessage(), e);
       }
    }

    @Override
    @Transactional(readOnly = false)
    public String create(String serviceId, String productId, Double price, Integer duration){
        try{
            Assert.hasText(serviceId, "销售ID不能为空");
            Assert.hasText(productId, "产品ID不能为空");

            Product product = productDao.getById(productId);
            Organization organization = organizationDao.getById(product.getOrganizationId());

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
            agreement.setPrice(price == null || price < 0? product.getPrice() : price);
            agreement.setRetail(product.getPrice());
            agreement.setDuration(duration == null || duration < 0 ? product.getDuration() : duration);
            agreement.setName(product.getName());
            //保存协议
            agreementDao.saveOrUpdate(agreement);
            //保存服务
            serviceDao.save(agreement.getId(), product.getServices());

            return agreement.getId();
        }catch(Exception e){
            logger.error(e.getMessage(), e);
            throw new ServiceException(e.getMessage(), e);
        }
    }

    @Override
    @Transactional(readOnly = false)
    public void fill(String id, String client, String phone, String address, String idCard, String wechat, String email, String emergencyContact, String accountId){
        try{
            Assert.hasText(accountId, "客户ID不能为空");
            Assert.hasText(id, "协议ID不能为空");

            Agreement agreement = agreementDao.getById(id);
            //查询用户
            User user = userDao.getByAccountId(accountId);
            //修改协议信息
            agreement.setClientHeadImgUrl(user.getHeadImgUrl());
            agreement.setClientNickname(user.getNickname());
            agreement.setAccountId(accountId);
            //设置客户信息
            agreement.setClient(client);
            agreement.setPhone(phone);
            agreement.setAddress(address);
            agreement.setIdCard(idCard);
            agreement.setWechat(wechat);
            agreement.setEmail(email);
            agreement.setEmergencyContact(emergencyContact);
            agreement.setState(AgreementState.WAIT_FOR_SIGN);

            agreementDao.saveOrUpdate(agreement);
        }catch(Exception e){
            logger.error(e.getMessage(), e);
            throw new ServiceException(e.getMessage(), e);
        }
    }

    @Override
    @Transactional(readOnly = false)
    public void sign(String id, CommonsMultipartFile file){
        try{
            Assert.hasText(id, "协议ID不能为空");

            //文件名称
            String originalFilename = file.getOriginalFilename();
            //MD5
            String md5 = FileUtils.getMD5(file.getBytes());
            //文件后缀
            String suffix = FileNameUtils.getSuffix(originalFilename);
            //上传名称
            String name = md5 + suffix;
            //上传路径
            String uploadPath = UpyunUtils.AGREEMENT_SIGN + name;

            //上传
            boolean result = UpyunUtils.upload(uploadPath, file);

            if(!result){
                throw new ServiceException("签名上传失败");
            }

            Agreement agreement = agreementDao.getById(id);
            agreement.setAffirmTime(new Date());
            agreement.setAutographUrl(UpyunUtils.URL + uploadPath);
            agreement.setEdit(false);
            agreement.setState(AgreementState.TAKE_EFFECT);
            agreementDao.saveOrUpdate(agreement);
        }catch(Exception e){
            logger.error(e.getMessage(), e);
            throw new ServiceException(e.getMessage(), e);
        }
    }

    @Override
    @Transactional(readOnly = false)
    public void backToEdit(String id){
        try{
            Assert.hasText(id, "协议ID不能为空");

            Agreement agreement = agreementDao.getById(id);
            agreement.setEdit(true);

            agreementDao.saveOrUpdate(agreement);
        }catch(Exception e){
            logger.error(e.getMessage(), e);
            throw new ServiceException(e.getMessage(), e);
        }
    }

    @Override
    @Transactional(readOnly = false)
    public void share(String id, String accountId){
        try{
            Assert.hasText(id, "协议ID不能为空");
            Assert.hasText(accountId, "客户ID不能为空");

            Agreement agreement = agreementDao.getById(id);

            if(StringUtils.isNotBlank(agreement.getAccountId())){
                throw new ServiceException("协议已分享");
            }

            //查询用户
            User user = userDao.getByAccountId(accountId);
            //修改协议信息
            agreement.setClientHeadImgUrl(user.getHeadImgUrl());
            agreement.setClientNickname(user.getNickname());
            agreement.setAccountId(accountId);
            agreement.setState(AgreementState.WAIT_FOR_FILL);

            agreementDao.saveOrUpdate(agreement);
        }catch(Exception e){
            logger.error(e.getMessage(), e);
            throw new ServiceException(e.getMessage(), e);
        }
    }
}
