package com.mall.action;

import com.mall.core.Result;
import com.mall.entity.Favourite;
import com.mall.service.FavouriteService;
import org.springframework.web.bind.annotation.*;
import tk.mybatis.mapper.entity.Condition;
import javax.annotation.Resource;

@RestController
public class FavouriteController {

    @Resource
    FavouriteService favouriteService;
/*
{
	"userId": 1,
	"goodsId": 1
}
 */

    @RequestMapping(
            value = {"addFavourite"},
            method = {RequestMethod.POST}
    )
    public Result addFavourite(@RequestBody Favourite favourite) {
        Result result = new Result();
        int userId = favourite.getUserId();
        int goodId = favourite.getGoodsId();
        Condition condition = new Condition(Favourite.class);
        condition.createCriteria().andCondition("user_id = " + userId).andCondition("goods_id = " + goodId);
        if(!favouriteService.findByCondition(condition).isEmpty()) {
            result.setSuccess(false);
            result.setMsg("该商品已经增加过到收藏夹");
        } else {
            favourite.setUserId(userId);
            favourite.setGoodsId(goodId);
            favouriteService.save(favourite);
            result.setSuccess(true);
            result.setMsg("增加商品到收藏夹成功");
        }
        return result;
    }
/*
{
	"userId": 1,
	"goodsId": 1
}
*/
    @RequestMapping(
            value = {"deleteFavourite"},
            method = {RequestMethod.POST}
    )
    public Result deleteFavourite(@RequestBody Favourite favourite) {
        Result result = new Result();
        int userId = favourite.getUserId();
        int goodId = favourite.getGoodsId();
        Condition condition = new Condition(Favourite.class);
        condition.createCriteria().andCondition("user_id = " + userId).andCondition("goods_id = " + goodId);
        if(favouriteService.findByCondition(condition).isEmpty()) {
            result.setSuccess(false);
            result.setMsg("没有收藏该商品");
        } else {
            favouriteService.deleteByCondition(condition);
            result.setSuccess(true);
            result.setMsg("取消收藏商品成功");
        }
        return result;
    }
/*
{
	"userId": 1
}
*/
    @RequestMapping(
            value = {"getFavouriteByUserId"},
            method = {RequestMethod.POST}
    )
    public Result getFavouriteByUserId(@RequestBody Favourite favourite) {
        Result result = new Result();
        int userId = favourite.getUserId();
        Condition condition = new Condition(Favourite.class);
        condition.createCriteria().andCondition("user_id = " + userId);
        result.setData(favouriteService.findByCondition(condition));
        result.setSuccess(true);
        return result;
    }
}
