package com.shangchao.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.shangchao.commons.CommonConstant;
import com.shangchao.dao.IUserDAO;
import com.shangchao.entity.Permission;
import com.shangchao.entity.Role;
import com.shangchao.entity.User;
import com.shangchao.service.IUserService;
import com.shangchao.service.abs.AbstractService;
import com.shangchao.sm.SM3Digest;
import com.shangchao.utils.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.util.*;
import java.util.concurrent.TimeUnit;

// import com.sun.deploy.net.HttpResponse;

/** */
@Service("userService")
public class UserServiceImpl extends AbstractService implements IUserService {

  private static Logger log = LogManager.getLogger(UserServiceImpl.class);
  //
  @Value("${redis.identifyingTokenExpireTime}")
  private long identifyingTokenExpireTime;
  // redis中jwtToken过期时间
  @Value("${redis.refreshJwtTokenExpireTime}")
  private long refreshJwtTokenExpireTime;

  @Autowired RedisTemplate redisTemplate;

  @Autowired private IUserDAO userDAO;

  @Override
  public User getUserByUserName(String userName) {
    return this.userDAO.findByName(userName);
  }

  @Override
  public Map<String, Object> getRolesAndPermissionsByUserName(String userName) {
    Role role = null;
    Permission permission = null;
    Set<String> roles = new HashSet<String>();
    Set<String> permissions = new HashSet<String>();
    Map<String, Object> map = new HashMap<String, Object>();
    User vo = this.userDAO.listRolesAndPermissions(userName);
    for (int i = 0; i < vo.getRoles().size(); i++) {
      role = vo.getRoles().get(i);
      roles.add(role.getRoleName());
      for (int j = 0; j < role.getPermissions().size(); j++) {
        permission = role.getPermissions().get(i);
        permissions.add(permission.getPermissionName());
      }
    }
    map.put("allRoles", roles);
    map.put("allPermissions", permissions);
    return map;
  }

  @Override
  public Map<String, Object> createRandomToken(String textStr) {
    // 生成一个token
    String sToken = UUID.randomUUID().toString();
    // 生成验证码对应的token  以token为key  验证码为value存在redis中
    redisTemplate
        .opsForValue()
        .set(
            CommonConstant.NO_REPEAT_PRE + sToken,
            textStr,
            identifyingTokenExpireTime,
            TimeUnit.MINUTES);
    Map<String, Object> map = new HashMap<>();
    map.put("cToken", sToken);
    return map;
  }

  @Override
  public boolean checkRandomToken(String sToken, String textStr) {
    Object value = redisTemplate.opsForValue().get(CommonConstant.NO_REPEAT_PRE + sToken);
    if (value != null && textStr.equals(value)) {
      return true;
    } else {
      return false;
    }
  }

  @Override
  public void addTokenToRedis(String userName, String jwtTokenStr) {
    String key = CommonConstant.JWT_TOKEN + userName;
    redisTemplate.opsForValue().set(key, jwtTokenStr, refreshJwtTokenExpireTime, TimeUnit.MINUTES);
  }

  @Override
  public boolean removeJWTToken(String userName) {
    String key = CommonConstant.JWT_TOKEN + userName;
    return redisTemplate.delete(key);
  }

  @Override
  public List<User> listOnLineUser() {
    Set setNames = redisTemplate.keys(CommonConstant.JWT_TOKEN + "*");
    List list = new ArrayList<>(setNames.size());
    Iterator<String> iter = setNames.iterator();
    while (iter.hasNext()) {
      String temp = iter.next();
      list.add(temp.substring(temp.lastIndexOf("_") + 1));
    }
    return userDAO.listUserByNams(list.toArray());
  }

  @Override
  public void saveUser(User user) {
    userDAO.saveUser(user);
  }

  /**
   * @throws
   * @title weChatLogin
   * @description 微信授权登录
   * @author Kuangzc
   * @updateTime 2019-9-12 16:00:51
   */
  @Override
  public Map weChatLogin(String code, HttpServletResponse response) {
    try {
      // 通过第一步获得的code获取微信授权信息
      String url =
          "https://api.weixin.qq.com/sns/oauth2/access_token?appid="
              + ConfigUtil.getCommonYml("webchat.webChatAppId")
              + "&secret="
              + ConfigUtil.getCommonYml("webchat.webChatAppSecret")
              + "&code="
              + code
              + "&grant_type=authorization_code";
      JSONObject jsonObject = AuthUtil.doGetJson(url);
      String openid = jsonObject.getString("openid");
      String token = jsonObject.getString("access_token");
      String infoUrl =
          "https://api.weixin.qq.com/sns/userinfo?access_token="
              + token
              + "&openid="
              + openid
              + "&lang=zh_CN";
      JSONObject userInfo = AuthUtil.doGetJson(infoUrl);
      // 成功获取授权,以下部分为业务逻辑处理了，根据个人业务需求写就可以了
      if (userInfo != null && openid != null) {
        String nickname = userInfo.getString("nickname");
        String headimgurl = userInfo.getString("headimgurl");
        headimgurl = headimgurl.replace("\\", "");
        // 根据openid查询时候有用户信息
        User user = userDAO.findByOpenId(openid);
        if (Objects.isNull(user)) {

          // 没有绑定用户请前往绑定
          user = new User();
          user.setLock(0);
          user.setOpenId(openid);
          user.setNickName(nickname);
          user.setHeadImgUrl(headimgurl);
          user.setUserName(RandomUtil.getStringRandom(10));
          user.setPassword(SM3Digest.summary(RandomUtil.genRandomPwd(10)));
          this.saveUser(user);
        }
        String tokenStr = JwtUtil.sign(user.getUserName(), user.getPassword());
        this.addTokenToRedis(user.getUserName(), tokenStr);
        response.setHeader("Authorization", tokenStr);
        user.setPassword("");
        HashMap map = new HashMap();
        map.put("openid", openid);
        map.put("user", user);
        return ResponseUtil.success("登录成功！", map);
        //                else {

        // 已经绑定用户信息直接登录
        //                    UserInfo userInfos = UserInfo.build(memberDTO.getId().longValue(),
        // memberDTO.getMobile(), new Date(), 1);
        //                    String Token = tokenManager.setToken(userInfos);
        //                    // 缓存权限
        //                    Map<String, String> httpUrl = new HashMap<>();
        //                    httpUrl.put("kylin", "shuoye.com:8484");
        //                    authHandler.setRoleUrl(memberDTO.getId().toString(), httpUrl);
        //                    String tokenStr = JwtUtil.sign(user.getUserName(),
        // user.getPassword());
        //                    this.addTokenToRedis(user.getUserName(), tokenStr);
        //                    response.setHeader("Authorization", tokenStr);
        //                    HashMap map = new HashMap();
        //                    map.put("openid", openid);
        //                    map.put("user",user);
        //                    return ApiUtil.success("登录成功！",map);
        //                }
      } else {
        log.error("微信远程调用token失败：{}{}", jsonObject.get("errcode"), jsonObject.get("errmsg"));
        return ResponseUtil.fail("登录失败");
      }
    } catch (Exception e) {
      log.error("微信登录失败：{}", e.getMessage());
      return ResponseUtil.fail("登录失败");
    }
  }
}
