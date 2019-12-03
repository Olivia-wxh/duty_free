package com.shangchao.controller;


import com.shangchao.commons.CodeAndMsgEnum;
import com.shangchao.entity.User;
import com.shangchao.service.IUserService;
import com.shangchao.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

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
    @RequestMapping(value = "/userLogin", method = RequestMethod.GET)
    public Map ajaxLogin(String username,String password,HttpServletResponse response) {
        Map result = new HashMap<>(4);
        User vo = this.userService.getUserByUserName(username);
        if (null != vo && vo.getPassword().equals(password)) {
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
}
