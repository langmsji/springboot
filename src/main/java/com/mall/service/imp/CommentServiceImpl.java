package com.mall.service.imp;

import com.mall.entity.Comment;
import com.mall.mapper.CommentMapper;
import com.mall.service.AbstractService;
import com.mall.service.CommentService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

@Service
@Transactional
public class CommentServiceImpl extends AbstractService<Comment> implements CommentService{
    @Resource
    private CommentMapper commentMapper;
}
