package com.mall.service;

import com.mall.core.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import java.lang.reflect.ParameterizedType;
import java.util.List;
import tk.mybatis.mapper.entity.Condition;

public abstract class AbstractService<T> implements Service<T> {

    @Autowired
    private Mapper<T> mapper;
    private Class<T> clazz;

    public AbstractService() {
        ParameterizedType pt = (ParameterizedType) this.getClass().getGenericSuperclass();
        clazz = (Class<T>) pt.getActualTypeArguments()[0];
    }

    @Override
    public void save(T t) {
        mapper.insertSelective(t);
    }

    @Override
    public void save(List<T> list) {
        mapper.insertList(list);
    }

    @Override
    public int deleteById(Integer id) {
        return mapper.deleteByPrimaryKey(id);
    }

    @Override
    public int deleteByCondition(Condition condition) {
        return mapper.deleteByCondition(condition);
    }

    @Override
    public void update(T t) {
        mapper.updateByPrimaryKey(t);
    }

    @Override
    public T findById(Integer id) {
        return mapper.selectByPrimaryKey(id);
    }

    @Override
    public List<T> findByCondition(Condition condition) {
        return mapper.selectByCondition(condition);
    }

    @Override
    public List<T> findAll() {
        return mapper.selectAll();
    }
}
