package com.mall.action;

import com.mall.core.Result;
import com.mall.entity.Goods;
import com.mall.entity.User;
import com.mall.service.GoodsService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;
import tk.mybatis.mapper.entity.Condition;

@RestController
public class GoodsController {
    @Resource
    private GoodsService goodsService;
/*
{
    "goodsName": "欧莱雅",
    "category": "护肤",
    "price": 1000,
    "salevol": 1024,
    "reserve": 200,
    "description": "好"

}
 */
    @RequestMapping(value = {"/addGoods"})
    public Result addGoods(@RequestBody Goods goods) {
        Result result = new Result();
        goodsService.save(goods);
        result.setSuccess(true);
        result.setMsg("增加商品成功");
        return result;
    }

    @RequestMapping(value = {"/getAllGoods"})
    public Result getAllGoods() {
        Result result = new Result();
        List<Goods> goodsList = goodsService.findAll();
        result.setData(goodsList);
        result.setSuccess(true);
        result.setMsg("获取所有商品成功");
        return result;
    }

    @RequestMapping(
            value = {"getGoodsById"},
            method = {RequestMethod.POST}
    )
    public Result getGoodsById(@RequestBody Goods goods,HttpSession httpSession){
        Result result = new Result();
        int id = goods.getId();
        Goods goods1 = goodsService.findById(id);
        httpSession.setAttribute("getGoodsById",goods1);
        result.setData(goods);
        result.setSuccess(true);
        result.setMsg("通过Id获取商品成功");
        return result;
    }

    @RequestMapping(
            value = {"/searchGoods"},
            method = {RequestMethod.POST}
            )
    public Result searchGoods(@RequestBody Goods goods, HttpSession httpSession) {
        Result result = new Result();
        String goodsName = goods.getGoodsName();
        String category = goods.getCategory();


//        if(fromPrice > toPrice) {
//            int temp;
//            temp=toPrice;
//            toPrice=fromPrice;
//            fromPrice=temp;
//        }

        /**/
        Condition condition = new Condition(Goods.class);
        condition.createCriteria().andCondition(" g_name like '%" + goodsName + "%'").andCondition(" category like '%" + category+ "%'");//.andCondition("price between " + fromPrice + " to " +toPrice);
        List<Goods> goodsList = goodsService.findByCondition(condition);
        result.setData(goodsList);
        result.setSuccess(true);
        result.setMsg("搜索成功");
        return result;
    }

    @RequestMapping(value = "/uploadGoodsFile", method = RequestMethod.POST)
    @ResponseBody
    public Result uploadGoodsFile(@RequestParam MultipartFile productImgUpload, @RequestBody Goods goods, HttpServletRequest request) {
        Result result = new Result();
        try {
            if (productImgUpload != null && !productImgUpload.isEmpty()) {
                String sep = File.separator;
                String filePath = (Thread.currentThread().getContextClassLoader().getResource("").getPath() + "static" + sep + "img" + sep).substring(1);
                Condition condition = new Condition(Goods.class);
                int id = goods.getId();
                String fileName = String.valueOf(id) + ".jpg";
                byte[] bytes = productImgUpload.getBytes();
                Path path = Paths.get(filePath + fileName);
                Files.write(path, bytes);
                result.setMsg("上传文件成功");
                result.setSuccess(true);
            }
        } catch (Exception e) {
            result.setSuccess(false);
            result.setMsg("上传文件失败");
        }
        return result;
    }

}
