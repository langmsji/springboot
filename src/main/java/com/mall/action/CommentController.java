package com.mall.action;

import com.mall.core.Result;
import com.mall.entity.Comment;
import com.mall.entity.Favourite;
import com.mall.service.CommentService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import tk.mybatis.mapper.entity.Condition;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Date;

@RestController
public class CommentController {

    @Resource
    CommentService commentService;
/*
{
	"fromUserId": 1,
	"goodsId": 1,
	"content": "垃圾"
}
 */
    @RequestMapping(
            value={"addComment"},
            method = {RequestMethod.POST}
    )
    public Result addComment(@RequestBody Comment comment) {
        Result result = new Result();
        if(comment.getContent().length() == 0) {
            result.setSuccess(false);
            result.setMsg("评论不能为空");
            return result;
        }
        Date date = new Date();
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        comment.setConmmentDate(sf.format(date));
        commentService.save(comment);
        result.setSuccess(true);
        result.setMsg("增加评论成功");
        return result;
    }

    @RequestMapping(
            value={"deleteComment"},
            method = {RequestMethod.POST}
    )
    public Result deleteComment(@RequestBody Comment comment) {
        Result result = new Result();
        int userId = comment.getFromUserId();
        int goodId = comment.getGoodsId();
        Condition condition = new Condition(Favourite.class);
        condition.createCriteria().andCondition("from_uid = " + userId).andCondition("goods_id = " + goodId);
        commentService.deleteByCondition(condition);
        result.setSuccess(true);
        result.setMsg("删除评论成功");
        return result;
    }
}
