package com.nowcoder.community.controller;

import com.nowcoder.community.entity.User;
import com.nowcoder.community.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.HashMap;
import java.util.Map;

@Controller
public class LoginController {
    public static final String TAG = "LoginController";
    private Logger logger = LoggerFactory.getLogger(TAG);
    @Autowired
    UserService userService;

    @RequestMapping(path = "/register", method = RequestMethod.GET)
    public String getRegisterPage(){
        return "/site/register";
    }

    @RequestMapping(path = "/register",method = RequestMethod.POST)
    public String register(Model model, User user){
        Map<String,Object> map = new HashMap<>();
        map = userService.register(user);
        if (map == null || map.isEmpty()){
            logger.debug(TAG,"注册成功");
            model.addAttribute("msg","注册成功，我们已向您的邮箱发送一封激活邮件，请尽快激活");
            model.addAttribute("target","/index");
            return "/site/operate-result";
        }else {
            logger.debug(TAG,"注册失败");
            model.addAttribute("usernameMsg",map.get("usernameMsg"));
            model.addAttribute("passwordMsg",map.get("passwordMsg"));
            model.addAttribute("emailMsg",map.get("emailMsg"));
            return "/site/register";
        }

    }
}
