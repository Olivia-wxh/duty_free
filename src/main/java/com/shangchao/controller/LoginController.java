package com.shangchao.controller;


import com.shangchao.commons.CodeAndMsgEnum;
import com.shangchao.entity.User;
import com.shangchao.service.IUserService;
import com.shangchao.sm.SM3Digest;
import com.shangchao.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * 登录
 *
 * @param
 * @return
 */
@RestController
@RequestMapping("/login")
public class LoginController {
    @Autowired
    private IUserService userService;
    @RequestMapping(value = "/userLogin", method = RequestMethod.POST)
    public Map ajaxLogin(@RequestBody Map<String,String> loginInfo, HttpServletResponse response) {
        String username=loginInfo.get("username");
        String password=loginInfo.get("password");
        Map result = new HashMap<>(4);
        User vo = this.userService.getUserByUserName(username);
        System.out.println("=====:"+SM3Digest.summary(password));
        if (null != vo && vo.getPassword().equals(SM3Digest.summary(password))) {
            String tokenStr = JwtUtil.sign(username, password);
            userService.addTokenToRedis(username, tokenStr);
            result.put("code", CodeAndMsgEnum.SUCCESS.getcode());
            result.put("msg", "登录成功！");
            response.setHeader("Authorization", tokenStr);
        } else {
            result.put("code", CodeAndMsgEnum.ERROR.getcode());
            result.put("msg", "帐号或密码错误！");
        }
         return result;
    }

    @RequestMapping(value = "/saveUser", method = RequestMethod.POST)
    public Map saveUser(@RequestBody Map<String,String> usrInfo, HttpServletResponse response) {
        String username=usrInfo.get("username");
        String password=usrInfo.get("password");
        User user =new User();
        user.setUserName(username);
        if(!StringUtils.isEmpty(password)){
            user.setPassword(SM3Digest.summary(password));
        }
        Map result = new HashMap<>(4);
        this.userService.saveUser(user);
//        if (null != vo && vo.getPassword().equals(password)) {
//            String tokenStr = JwtUtil.sign(username, password);
//            userService.addTokenToRedis(username, tokenStr);
            result.put("code", CodeAndMsgEnum.SUCCESS.getcode());
            result.put("msg", "登录成功！");
//            response.setHeader("Authorization", tokenStr);
//        } else {
//            result.put("code", CodeAndMsgEnum.ERROR.getcode());
//            result.put("msg", "帐号或密码错误！");
//        }
        return result;
    }

    @RequestMapping(value = "/weChatLogin", method = RequestMethod.POST)
    public Map weChatLogin(@RequestParam String code, HttpServletResponse response) {
//        String username=loginInfo.get("username");
//        String password=loginInfo.get("password");
//        User vo = this.userService.getUserByUserName(username);
        Map result=this.userService.weChatLogin(code,response);

//        System.out.println("=====:"+SM3Digest.summary(password));
//        if (null != vo && vo.getPassword().equals(SM3Digest.summary(password))) {
//            String tokenStr = JwtUtil.sign(username, password);
//            userService.addTokenToRedis(username, tokenStr);
//            result.put("code", CodeAndMsgEnum.SUCCESS.getcode());
//            result.put("msg", "登录成功！");
//            response.setHeader("Authorization", tokenStr);
//        } else {
//            result.put("code", CodeAndMsgEnum.ERROR.getcode());
//            result.put("msg", "帐号或密码错误！");
//        }
        return result;
    }
}
