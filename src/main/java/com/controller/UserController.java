package com.controller;


import com.alibaba.fastjson.JSONObject;
import com.annotation.UserLoginToken;
import com.entiy.User;
import com.util.JwtUtils;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class UserController {


    @RequestMapping("/login")
    public Object login() {
        JSONObject jsonObject = new JSONObject();
        User userForBase = new User("001","wyq","123456");
        if (userForBase == null) {
            jsonObject.put("message", "登录失败,用户不存在");
            return jsonObject;
        } else {
            if (!userForBase.getPassword().equals("123456")) {
                jsonObject.put("message", "登录失败,密码错误");
                return jsonObject;
            } else {
                String token = JwtUtils.getToken(userForBase);
                jsonObject.put("token", token);
                jsonObject.put("user", userForBase);
                return jsonObject;
            }
        }
    }


//    @UserLoginToken
    @GetMapping("/getMessage")
    public String getMessage(){
        return "你已通过验证";
    }
}