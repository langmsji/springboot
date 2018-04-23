package com.mall.service.imp;

import com.mall.entity.Favourite;
import com.mall.mapper.FavouriteMapper;
import com.mall.service.AbstractService;
import com.mall.service.FavouriteService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

@Service
@Transactional
public class FavouriteServiceImpl extends AbstractService<Favourite> implements FavouriteService{
    @Resource
    private FavouriteMapper favouriteMapper;
}
