package com.shangchao.controller;

import com.alibaba.fastjson.JSONObject;
import com.shangchao.commons.CodeAndMsgEnum;
import com.shangchao.entity.User;
import com.shangchao.service.IUserService;
import com.shangchao.sm.SM3Digest;
import com.shangchao.utils.JwtUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
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
@Api(tags = "登录相关接口", description = "如有疑问请联系王宪良")
public class LoginController {

  @Autowired private IUserService userService;

  @PostMapping(value = "/userLogin")
  @ApiOperation("用户登录接口")
  @ApiImplicitParams({
          @ApiImplicitParam(name = "username", value = "用户名", required = true),
          @ApiImplicitParam(name = "password", value = "密码", required = true)
  })
  public Map ajaxLogin(@RequestBody JSONObject params, HttpServletResponse response) {
    String username = params.getString("username").toString();
    String password = params.getString("password").toString();
//    String username = loginInfo.get("username");
//    String password = loginInfo.get("password");
    Map result = new HashMap<>(4);
    User vo = this.userService.getUserByUserName(username);
    System.out.println("=====:" + SM3Digest.summary(password));
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
  public Map saveUser(@RequestBody JSONObject params, HttpServletResponse response) {
    String username = params.getString("username").toString();
    String password = params.getString("password").toString();
    User user = new User();
    user.setUserName(username);
    if (!StringUtils.isEmpty(password)) {
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

  @PostMapping(value = "/weChatLogin")
  @ApiOperation("微信登录接口")
  @ApiImplicitParams({
          @ApiImplicitParam(name = "code", value = "微信获取code", required = true)
  })
  public Map weChatLogin(@RequestBody JSONObject params, HttpServletResponse response) {
    //        String username=loginInfo.get("username");
    //        String password=loginInfo.get("password");
    //        User vo = this.userService.getUserByUserName(username);
    String code = params.getString("code").toString();
    Map result = this.userService.weChatLogin(code, response);

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

  @PostMapping(value = "/weChatLoginForIOS")
  @ApiOperation("IOS微信登录接口")
  @ApiImplicitParams({
          @ApiImplicitParam(name = "code", value = "微信获取code", required = true)
  })
  public Map weChatLoginForIOS(@RequestBody JSONObject params, HttpServletResponse response) {
    //        String username=loginInfo.get("username");
    //        String password=loginInfo.get("password");
    //        User vo = this.userService.getUserByUserName(username);
    String code = params.getString("code").toString();
    Map result = this.userService.weChatLoginForIOS(code, response);

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
