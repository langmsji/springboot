package com.mall.action;

import com.mall.core.Result;
import com.mall.entity.ShoppingCar;
import com.mall.service.GoodsService;
import com.mall.service.ShoppingCarService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import tk.mybatis.mapper.entity.Condition;

import javax.annotation.Resource;
import java.util.List;

@RestController
public class ShoppingCarController {
    @Resource
    private ShoppingCarService shoppingCarService;

    @Resource
    private GoodsService goodsService;
/*
{
	"userId": 1,
	"goodsId": 1,
	"counts": 1
}
 */
    @RequestMapping(
            value = {"addShoppingCar"},
            method = {RequestMethod.POST}
    )
    public Result addShoppingCar(@RequestBody ShoppingCar shoppingCar) {
        Result result = new Result();
        int userId = shoppingCar.getUserId();
        int goodsId = shoppingCar.getGoodsId();
        int counts = shoppingCar.getCounts();
        Condition condition = new Condition(ShoppingCar.class);
        condition.createCriteria().andCondition("user_id = ", userId).andCondition("goods_id = ", goodsId);
        List<ShoppingCar> shoppingCars = shoppingCarService.findByCondition(condition);
        ShoppingCar shoppingCar1 = null;
        if (!shoppingCars.isEmpty()) {
            shoppingCar1 = shoppingCars.get(0);
        }
        if (shoppingCar1 == null) {
            ShoppingCar shoppingCar2 = new ShoppingCar();
            shoppingCar2.setUserId(userId);
            shoppingCar2.setGoodsId(goodsId);
            shoppingCar2.setCounts(counts);
            shoppingCar2.setTotalPrice(goodsService.findById(goodsId).getPrice() * counts);
            shoppingCarService.save(shoppingCar2);
        } else {
            shoppingCar1.setCounts(shoppingCar1.getCounts() + counts);
            shoppingCar1.setTotalPrice(shoppingCar1.getTotalPrice()+goodsService.findById(goodsId).getPrice() * counts);
            shoppingCarService.update(shoppingCar1);
        }
        result.setSuccess(true);
        result.setMsg("增加购物车");
        return result;
    }
/*
{
	"userId": 1,
	"goodsId": 1
}
*/
    @RequestMapping(
            value = {"deleteShoppingCar"},
            method = {RequestMethod.POST}
    )
    public Result deleteShoppingCar(@RequestBody ShoppingCar shoppingCar) {
        Result result =  new Result();
        int userId = shoppingCar.getUserId();
        int goodsId = shoppingCar.getGoodsId();
        Condition condition =  new Condition(ShoppingCar.class);
        condition.createCriteria().andCondition("user_id = ", userId).andCondition("goods_id = ", goodsId);
        shoppingCarService.deleteByCondition(condition);
        result.setSuccess(true);
        result.setMsg("删除购物车");
        return result;
    }
/*
{
	"userId": 1
}
*/
    @RequestMapping(
            value = {"getShoppingCarByUserId"},
            method = {RequestMethod.POST}
    )
    public Result getShoppingCarByUserId(@RequestBody ShoppingCar shoppingCar) {
        Result result = new Result();
        int userId = shoppingCar.getUserId();
        Condition condition =  new Condition(ShoppingCar.class);
        condition.createCriteria().andCondition("user_id = ",userId);
        List<ShoppingCar> shoppingCar1 = shoppingCarService.findByCondition(condition);
        result.setSuccess(true);
        result.setMsg("获取某用户Id购物车");
        result.setData(shoppingCar1);
        return result;
    }
}
