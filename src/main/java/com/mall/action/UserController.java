package com.mall.action;

import com.mall.core.PhoneFormatCheckUtils;
import com.mall.core.EmailFormatCheckUtils;
import com.mall.core.Result;
import com.mall.entity.User;
import com.mall.service.UserService;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.springframework.web.multipart.MultipartFile;
import tk.mybatis.mapper.entity.Condition;
import java.util.List;

@RestController
public class UserController {

    @Resource
    private UserService userService;

    public UserController() {
    }
/*
{
	"userName": "tangww",
	"password": "123456",
	"phoneNum": "15521269666",
	"email": "1132053580@qq.com",
	"realName": "tangwenwei",
	"sex": "man"
}
 */
    @RequestMapping({"doRegister"})
    public Result doRegister(@RequestBody User user){
        Result result = new Result();
        String userName = user.getUserName();
        String password = user.getPassword();
        String phoneNum = user.getPhoneNum();
        String email = user.getEmail();

        if(password.length() < 6) {
            result.setSuccess(false);
            result.setMsg("密码不能少于6位");
            return result;
        }
        if (!EmailFormatCheckUtils.checkEmaile(email)) {// 验证邮箱
            result.setSuccess(false);
            result.setMsg("邮箱格式错误");
            return result;
        }
        if(!PhoneFormatCheckUtils.isPhoneLegal(phoneNum)) {// 验证是否为大陆或者香港手机号
            result.setSuccess(false);
            result.setMsg("手机号格式错误");
            return result;
        }
        Condition condition = new Condition(User.class);
        condition.createCriteria().andCondition(" u_name = '" + userName + "'");
        if(!userService.findByCondition(condition).isEmpty()) {
            result.setSuccess(false);
            result.setMsg("用户名已经被注册过了");
        } else {
            Condition condition1 = new Condition(User.class);
            condition1.createCriteria().andCondition(" phone_num = '" + phoneNum + "'");
            if(!userService.findByCondition(condition1).isEmpty()) {
                result.setSuccess(false);
                result.setMsg("手机号已经被注册过了");
            } else {
                Condition condition2 = new Condition(User.class);
                condition2.createCriteria().andCondition(" email = '" + email + "'");
                if(!userService.findByCondition(condition2).isEmpty()) {
                    result.setSuccess(false);
                    result.setMsg("邮箱已经被注册过了");
                } else {
                    Date date = new Date();
                    SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    user.setRegisterTime(sf.format(date));
                    userService.save(user);
                    result.setSuccess(true);
                    result.setMsg("注册成功");
                }
            }
        }
        return result;
    }

/*
{
    "userName": "tangww",
    "password"：123456
}
*/
    @RequestMapping(
            value = {"doLogin"},
            method = {RequestMethod.POST}
    )
    public Result doLogin(@RequestBody User user, HttpSession httpSession) {
        Result result = new Result();
        String userNameOrEmailOrPhoneNum;
        if(user.getUserName() != null) {
            userNameOrEmailOrPhoneNum = user.getUserName();
        } else if(user.getEmail() != null) {
            userNameOrEmailOrPhoneNum = user.getEmail();
        } else {
            userNameOrEmailOrPhoneNum = user.getPhoneNum();
        }
        String password = user.getPassword();
        Condition condition = new Condition(User.class);
        condition.createCriteria().andCondition(" u_name = '" + userNameOrEmailOrPhoneNum + "' or email = '" + userNameOrEmailOrPhoneNum +"' or phone_num = '" + userNameOrEmailOrPhoneNum + "'");
        if(userService.findByCondition(condition).isEmpty()) {
            result.setSuccess(false);
            result.setMsg("当前用户还未注册");
         } else {
            User user1 = userService.findByCondition(condition).get(0);
            if(user1.getPassword().equals(password)) {
                result.setSuccess(true);//success = true;
                result.setMsg("登陆成功");
                httpSession.setAttribute("currentUser" ,user1);
            } else {
                result.setSuccess(false);
                result.setMsg("密码错误");
            }

        }
        return result;
    }
    /*
    {
        "userName": "tangww",
        "password": "123456",
        "phoneNum": "15521269666",
        "email": "1132053580@qq.com",
        "realName": "tangwenwei",
        "sex": "man"
    }
     */
    @RequestMapping(
            value = {"/doUpdate"},
            method = {RequestMethod.POST}
    )
    public Result doUpdate(@RequestBody User user) {
        Result result = new Result();
        int id = user.getId();
        String userName = user.getUserName();
        String password = user.getPassword();
        String phoneNum = user.getPhoneNum();
        String email = user.getEmail();
        String realName = user.getRealName();
        String sex = user.getSex();

        if(password.length() < 6) {
            result.setSuccess(false);
            result.setMsg("密码不能少于6位");
            return result;
        }
        if (!EmailFormatCheckUtils.checkEmaile(email)) {// 验证邮箱
            result.setSuccess(false);
            result.setMsg("邮件格式错误");
            return result;
        }
        if(!PhoneFormatCheckUtils.isPhoneLegal(phoneNum)) {// 验证是否为大陆或者香港手机号
            result.setSuccess(false);
            result.setMsg("手机号格式错误");
            return result;
        }
        User user1 = userService.findById(id);
        user1.setUserName(userName);
        user1.setPassword(password);
        user1.setPhoneNum(phoneNum);
        user1.setEmail(email);
        user1.setRealName(realName);
        user1.setSex(sex);
        userService.update(user1);
        result.setSuccess(true);
        result.setMsg("用户信息更新成功");
        return result;
    }

    @RequestMapping({"/doLogout"})
    public Result doLogout(HttpSession httpSession) {
        Result result = new Result();
        httpSession.setAttribute("currentUser", "/default");
        result.setMsg("注销");
        return result;
    }

/*
{
	"id": "2"
}
 */
    @RequestMapping(
            value = {"getUserById"},
            method = {RequestMethod.POST}
            )
    public Result getUserById(@RequestBody User user) {
        Result result = new Result();
        int id = user.getId();
        User user1 = userService.findById(id);
        result.setData(user1);
        result.setSuccess(true);
        result.setMsg("通过Id获取用户信息成功");
        return result;
    }

/*
{
	"userName": "tangww"
}
 */
    @RequestMapping(
            value = {"getUserByUserName"},
            method = {RequestMethod.POST}
    )
    public Result getUserByUserName(@RequestBody User user) {
        Result result = new Result();
        String userName = user.getUserName();
        Condition condition = new Condition(User.class);
        condition.createCriteria().andCondition(" u_name = '" + userName + "'");
        List<User> user1 = userService.findByCondition(condition);
        result.setData(user1.get(0));
        result.setSuccess(true);
        result.setMsg("通过Id获取用户信息成功");
        return result;
    }

    @RequestMapping(
            value = {"getUserImgPath"},
            method = {RequestMethod.POST}
    )
    public Result getUserImgPath(String userNameOrEmailOrPhoneNum) {
        Result result = new Result();
        Condition condition = new Condition(User.class);
        condition.createCriteria().andCondition(" u_name = '" + userNameOrEmailOrPhoneNum + "' or email = '" + userNameOrEmailOrPhoneNum +"' or phone_num = '" + userNameOrEmailOrPhoneNum + "'");
        int id =userService.findByCondition(condition).get(0).getId();
        String sep = File.separator;
        String path = (Thread.currentThread().getContextClassLoader().getResource("").getPath() + "static" + sep + "static" + sep + "img" + sep).substring(1) + sep + String.valueOf(id) + ".jpg";
        result.setMsg("获取用户图片成功");
        result.setSuccess(true);
        result.setData(path);
        return result;
    }

    @RequestMapping(value = "/uploadImgFile", method = RequestMethod.POST)
    @ResponseBody
    public Result uploadImgFile(@RequestParam MultipartFile userImgUpload, String userName, HttpServletRequest request) {
        Result result = new Result();
        try {
            if (userImgUpload != null && !userImgUpload.isEmpty()) {
                String sep = File.separator;
                String filePath = (Thread.currentThread().getContextClassLoader().getResource("").getPath() + "static" + sep + "static" + sep + "img" + sep).substring(1);
                Condition condition = new Condition(User.class);
                condition.createCriteria().andCondition(" u_name = '" + userName + "'");
                List<User> user1 = userService.findByCondition(condition);
                int id = user1.get(0).getId();
                String fileName = String.valueOf(id) + ".jpg";
                byte[] bytes = userImgUpload.getBytes();
                Path path = Paths.get(filePath + fileName);
                Files.write(path, bytes);
                result.setMsg("上传照片成功");
                result.setSuccess(true);
            }
        } catch (Exception e) {
            result.setSuccess(false);
            result.setMsg("上传照片失败");
        }
        return result;
    }
}

