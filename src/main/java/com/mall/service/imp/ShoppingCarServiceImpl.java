package com.mall.service.imp;

import com.mall.entity.ShoppingCar;
import com.mall.mapper.ShoppingCarMapper;
import com.mall.service.AbstractService;
import com.mall.service.ShoppingCarService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

@Service
@Transactional
public class ShoppingCarServiceImpl extends AbstractService<ShoppingCar> implements ShoppingCarService{
    @Resource
    private ShoppingCarMapper shoppingCarMapper;
}
