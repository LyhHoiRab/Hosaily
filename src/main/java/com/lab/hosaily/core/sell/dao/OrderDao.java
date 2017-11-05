package com.lab.hosaily.core.sell.dao;

import com.lab.hosaily.core.sell.dao.mapper.OrderMapper;
import com.lab.hosaily.core.sell.entity.Order;
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

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Repository
public class OrderDao{

    private static Logger logger = LoggerFactory.getLogger(OrderDao.class);

    @Autowired
    private OrderMapper mapper;

    /**
     * 保存或更新
     */
    public void saveOrUpdate(Order order){
        try{
            Assert.notNull(order, "订单信息不能为空");

            if(StringUtils.isBlank(order.getId())){
                Assert.hasText(order.getSalesAccount(), "销售账户ID不能为空");
                Assert.notNull(order.getSalesUser(), "销售用户信息不能为空");
                Assert.hasText(order.getSalesUser().getId(), "销售用户ID不能为空");
                Assert.hasText(order.getClientAccount(), "客户账户ID不能为空");
                Assert.notNull(order.getClientUser(), "客户用户信息不能为空");
                Assert.hasText(order.getClientUser().getId(), "客户用户ID不能为空");
                Assert.notNull(order.getPrice(), "金额不能为空");
                Assert.notNull(order.getPay(), "预付金额不能为空");

                order.setId(UUIDGenerator.by32());
                order.setOrderId(order.getId());
                order.setState(UsingState.NORMAL);
                order.setIsDelete(false);
                order.setCreateTime(new Date());
                mapper.save(order);
            }else{
                order.setUpdateTime(new Date());
                mapper.update(order);
            }
        }catch(Exception e){
            logger.error(e.getMessage(), e);
            throw new DataAccessException(e.getMessage(), e);
        }
    }

    /**
     * 分页查询
     */
    public Page<Order> page(PageRequest pageRequest, String clientAccount, String salesAccount){
        try{
            Assert.notNull(pageRequest, "分页信息不能为空");

            Criteria criteria = new Criteria();
            criteria.limit(Restrictions.limit(pageRequest.getOffset(), pageRequest.getPageSize()));
            criteria.groupBy(Restrictions.groupBy("o.id"));
            criteria.sort(Restrictions.asc("o.createTime"));

            if(!StringUtils.isBlank(clientAccount)){
                criteria.and(Restrictions.eq("o.clientAccount", clientAccount));
            }
            if(!StringUtils.isBlank(salesAccount)){
                criteria.and(Restrictions.eq("o.salesAccount", salesAccount));
            }

            List<String> ids = mapper.findIdByParams(criteria);
            Long count = mapper.countByParams(criteria);

            List<Order> list = new ArrayList<Order>();

            if(ids != null && !ids.isEmpty()){
                criteria.clear();
                criteria.and(Restrictions.in("o.id", ids));
                criteria.sort(Restrictions.asc("o.createTime"));

                list = mapper.findByParams(criteria);
            }

            return new Page<Order>(list, pageRequest, count);
        }catch(Exception e){
            logger.error(e.getMessage(), e);
            throw new DataAccessException(e.getMessage(), e);
        }
    }
}
