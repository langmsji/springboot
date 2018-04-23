package com.mall.service.imp;

import com.mall.entity.Goods;
import com.mall.mapper.GoodsMapper;
import com.mall.service.AbstractService;
import com.mall.service.GoodsService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

@Service
@Transactional
public class GoodsServiceImpl extends AbstractService<Goods> implements GoodsService{
    @Resource
    private GoodsMapper goodsMapper;
}
