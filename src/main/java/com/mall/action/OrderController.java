package com.mall.action;

import com.mall.core.Result;
import com.mall.entity.Order;
import com.mall.entity.ShoppingCar;
import com.mall.service.GoodsService;
import com.mall.service.OrderService;
import org.springframework.web.bind.annotation.*;
import tk.mybatis.mapper.entity.Condition;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@RestController
public class OrderController {

    @Resource
    OrderService orderService;

    @Resource
    private GoodsService goodsService;
    /*
    {
        "userId": 1,
        "goodsId": 1,
        "counts": 1,
        "addr": "广州"
    }
     */
    @RequestMapping(
            value = {"addOrder"},
            method = {RequestMethod.POST}
    )
    public Result addOrder(@RequestBody Order order) {
        Result result = new Result();
        int userId = order.getUserId();
        int goodsId = order.getGoodsId();
        int counts = order.getCounts();
        //Goods goods = goodsService.findById(goodsId);
        //goods.setReserve(goods.getReserve()-counts);
        //goodsService.save(goods);
        order.setTotalPrice(goodsService.findById(goodsId).getPrice() * counts);
        Date date = new Date();
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        order.setOrderDate(sf.format(date));
        orderService.save(order);
        result.setSuccess(true);
        result.setMsg("增加订单");
        return result;
    }
    /*
    {
        "userId": 1,
        "goodsId": 1
    }
    */
    @RequestMapping(
            value = {"deleteOrder"},
            method = {RequestMethod.POST}
    )
    public Result deleteOrder(@RequestBody Order order) {
        Result result = new Result();
        int userId = order.getUserId();
        //int goodsId = order.getGoodsId();
        //int counts = order.getCounts();
        Condition condition =  new Condition(ShoppingCar.class);
        condition.createCriteria().andCondition("user_id = ",userId);
        //goodsService.findById(goodsId).setReserve(goodsService.findById(goodsId).getReserve()+counts);
        orderService.deleteByCondition(condition);
        result.setSuccess(true);
        result.setMsg("删除订单");
        return result;
    }
    /*
    {
        "userId": 1
    }
    */
    @RequestMapping(
            value = {"getOrderByUserId"},
            method = {RequestMethod.POST}
    )
    public Result getOrderByUserId(@RequestBody Order order) {
        Result result = new Result();
        int userId = order.getUserId();
        Condition condition =  new Condition(ShoppingCar.class);
        condition.createCriteria().andCondition("user_id = ",userId);
        List<Order> order1 = orderService.findByCondition(condition);
        result.setSuccess(true);
        result.setMsg("获取某用户订单");
        result.setData(order1);
        return result;
    }

}
