package com.shangchao.service;

import com.shangchao.entity.User;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

/** */
public interface IUserService {
  User getUserByUserName(String userName);

  Map<String, Object> getRolesAndPermissionsByUserName(String userName);

  Map<String, Object> createRandomToken(String textStr);

  boolean checkRandomToken(String sToken, String textStr);

  void addTokenToRedis(String userName, String jwtTokenStr);

  boolean removeJWTToken(String userName);

  List<User> listOnLineUser();

  void saveUser(User user);

  Map weChatLogin(String code, HttpServletResponse response);

  Map weChatLoginForIOS(String code, HttpServletResponse response);
}
