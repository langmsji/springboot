package com.mall.service;

import java.util.List;
import tk.mybatis.mapper.entity.Condition;

public interface Service<T> {
    void save(T t);
    void save(List<T> list);
    int deleteById(Integer id);
    int deleteByCondition(Condition condition);
    void update(T t);
    T findById(Integer id);
    List<T> findByCondition(Condition condition);
    List<T> findAll();
}
