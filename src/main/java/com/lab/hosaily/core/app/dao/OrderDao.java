package com.lab.hosaily.core.app.dao;

import com.lab.hosaily.core.app.dao.mapper.OrderMapper;
import com.lab.hosaily.core.app.entity.Order;
import com.lab.hosaily.core.app.entity.OrderProfile;
import com.rab.babylon.commons.security.exception.DataAccessException;
import com.rab.babylon.commons.security.mybatis.Criteria;
import com.rab.babylon.commons.security.mybatis.Restrictions;
import com.rab.babylon.commons.security.response.Page;
import com.rab.babylon.commons.security.response.PageRequest;
import com.rab.babylon.commons.utils.UUIDGenerator;
import com.rab.babylon.core.consts.entity.UsingState;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

import java.util.Date;
import java.util.List;

@Repository
public class OrderDao {

    private static Logger logger = LoggerFactory.getLogger(OrderDao.class);

    @Autowired
    private OrderMapper mapper;

    /**
     * 保存或更新
     */
    public void saveOrUpdate(Order order) {
        try {
            Assert.notNull(order, "导师信息不能为空");

            if (StringUtils.isBlank(order.getId())) {
                order.setId(UUIDGenerator.by32());
                order.setCreateTime(new Date());
                mapper.save(order);
            } else {
                order.setUpdateTime(new Date());
                mapper.update(order);
//                mapper.deleteOrderProfiles(order.getId());


                if (null != order.getUpdateOrderProfiles() && order.getUpdateOrderProfiles().size() > 0) {
                    for (int i = 0; i < order.getUpdateOrderProfiles().size(); i++) {
                        OrderProfile orderProfile = order.getUpdateOrderProfiles().get(i);
                        orderProfile.setUpdateTime(new Date());
                        orderProfile.setStatus("0");
                        mapper.updateOrderProfile(orderProfile);
                    }
                }


//                if (null != order.getUpdateOrderProfiles() && order.getUpdateOrderProfiles().size() > 0) {
//                    for (int i = 0; i < order.getUpdateOrderProfiles().size(); i++) {
//                        OrderProfile orderProfile = order.getUpdateOrderProfiles().get(i);
//                        List<OrderProfile> oldOrderProfiles = mapper.getOrderProfileById(orderProfile.getOrderId());
//                        boolean updateFlag = false;
//                        for (int j = 0; j < oldOrderProfiles.size(); j++) {
//                            if(orderProfile.getProfileId().equals(oldOrderProfiles.get(j).getProfileId())){
//                                updateFlag = true;
//                            }
//
//                        }
//                        orderProfile.setUpdateTime(new Date());
//                        orderProfile.setStatus("0");
//                        if(updateFlag){
//                            mapper.updateOrderProfile(orderProfile);
//                        }else{
//                            mapper.saveOrderProfile(orderProfile);
//                        }
//                    }
//                }


                if (null != order.getOrderProfiles() && order.getOrderProfiles().size() > 0) {
                    System.out.println("AAAAAAAAAAAAAAAA: " + order.getOrderProfiles().size());
                    for (int i = 0; i < order.getOrderProfiles().size(); i++) {
                        OrderProfile orderProfile = order.getOrderProfiles().get(i);

                        List<OrderProfile> oldOrderProfiles = mapper.getOrderProfileById("'" + orderProfile.getOrderId() + "'");
                        boolean updateFlag = false;
                        for (int j = 0; j < oldOrderProfiles.size(); j++) {
                            if (orderProfile.getProfileId().equals(oldOrderProfiles.get(j).getProfileId())) {
                                updateFlag = true;

                            }

                        }

                        orderProfile.setCreateTime(new Date());

                        orderProfile.setStatus("1");
                        if (updateFlag) {
                            orderProfile.setUpdateTime(new Date());
                            mapper.updateOrderProfile(orderProfile);
                        } else {
                            mapper.saveOrderProfile(orderProfile);
                        }
                    }
                }


                //更新导师
//                System.out.println("bbbbbbbbbbbbb: " + order.getOrderProfiles() != null);
//                System.out.println("ccccccccccccc: " + !order.getOrderProfiles().isEmpty());
//                if (order.getOrderProfiles() != null && !order.getOrderProfiles().isEmpty()) {
//                    mapper.addOrderProfiles(order.getOrderProfiles());
//                }
            }


        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new DataAccessException(e.getMessage(), e);
        }
    }

    /**
     * 根据ID查询
     */
    public Order getByAgreementId(String agreementId) {
        try {
            Criteria criteria = new Criteria();
            criteria.and(Restrictions.eq("a.agreementId", agreementId));

            return mapper.getByParams(criteria);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new DataAccessException(e.getMessage(), e);
        }
    }


    /**
     * 根据ID查询
     */
    public Order getById(String id, String advisorStatus) {
        try {
            Assert.hasText(id, "客户ID不能为空");

            Criteria criteria = new Criteria();
            criteria.and(Restrictions.eq("a.id", id));

//            if (!StringUtils.isBlank(advisorStatus)) {
//                criteria.and(Restrictions.eq("op.status", advisorStatus));
//            }

            return mapper.getByParams(criteria);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new DataAccessException(e.getMessage(), e);
        }
    }


    public List<OrderProfile> getOrderProfileById(String orderId) {
        try {
            Assert.hasText(orderId, "客户ID不能为空");
            return mapper.getOrderProfileById("'" + orderId + "'");
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new DataAccessException(e.getMessage(), e);
        }
    }

    /**
     * 分页查询
     */
    public Page<Order> page(PageRequest pageRequest, String sellerId, String advisorName, String assign, String mixSearch, String startTime, String endTime) {
        try {
            Assert.notNull(pageRequest, "分页信息不能为空");

            Criteria criteria = new Criteria();
            criteria.limit(Restrictions.limit(pageRequest.getOffset(), pageRequest.getPageSize()));
            criteria.sort(Restrictions.desc("a.updateTime"));
//
            if (!StringUtils.isBlank(sellerId)) {
                criteria.and(Restrictions.eq("a.sellerId", sellerId));
            }

            if (!StringUtils.isBlank(advisorName)) {
                criteria.and(Restrictions.like("ad.advisorName", advisorName));
            }
            if (!StringUtils.isBlank(assign)) {
//                if ("0".equals(assign)) {
//                    criteria.and(Restrictions.isNull("ad.advisorName"));
//                } else if ("1".equals(assign)) {
//                    criteria.and(Restrictions.isNotNull("ad.advisorName"));
//                }
                criteria.and(Restrictions.eq("a.status", assign));
            }
            if (!StringUtils.isBlank(mixSearch)) {
                mixSearch = "'%" + mixSearch + "%'";
            }
            Long count = mapper.countByParams(criteria, mixSearch);
            List<Order> list = mapper.findByParams(criteria, mixSearch, startTime, endTime);

            return new Page<Order>(list, pageRequest, count);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new DataAccessException(e.getMessage(), e);
        }
    }

    /**
     * 查询列表
     */
    public List<Order> list(String nickname, String name, UsingState state, String organizationId, String organizationToken) {
        try {
            Criteria criteria = new Criteria();
            criteria.sort(Restrictions.asc("a.sort"));

            if (!StringUtils.isBlank(nickname)) {
                criteria.and(Restrictions.like("a.nickname", nickname));
            }
            if (!StringUtils.isBlank(name)) {
                criteria.and(Restrictions.like("a.name", name));
            }
            if (state != null) {
                criteria.and(Restrictions.eq("a.state", state.getId()));
            }
            if (!StringUtils.isBlank(organizationId)) {
                criteria.and(Restrictions.eq("a.organizationId", organizationId));
            }
            if (!StringUtils.isBlank(organizationToken)) {
                criteria.and(Restrictions.eq("o.token", organizationToken));
            }

            return mapper.findByParams(criteria, null, null, null);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new DataAccessException(e.getMessage(), e);
        }
    }


    public List<Order> countOrderByParam(String startTime, String endTime, String countType, String sellerId){
        sellerId = "'" + sellerId + "'";
        return mapper.countOrderByParam(startTime, endTime, countType, sellerId);
    }
}
