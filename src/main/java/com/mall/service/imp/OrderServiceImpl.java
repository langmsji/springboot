package com.mall.service.imp;

import com.mall.entity.Order;
import com.mall.mapper.OrderMapper;
import com.mall.service.AbstractService;
import com.mall.service.OrderService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

@Service
@Transactional
public class OrderServiceImpl  extends AbstractService<Order> implements OrderService{
    @Resource
    private OrderMapper orderMapper;
}
