package com.shangchao.entity;

import com.shangchao.utils.PaginationEntity;

import java.io.Serializable;
import java.util.List;

public class User extends PaginationEntity implements Serializable {
  private Integer uid;
  private String userName;
  private String password;
  private String realName;
  private Integer sflag;
  private Integer lock;
  private String createDate;
  private List<Role> roles;

  private String sToken;
  private String textStr;

  private String openId;
  private String nickName;
  private String headImgUrl;

  public Integer getUid() {
    return uid;
  }

  public void setUid(Integer uid) {
    this.uid = uid;
  }

  public String getUserName() {
    return userName;
  }

  public void setUserName(String userName) {
    this.userName = userName;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public String getRealName() {
    return realName;
  }

  public void setRealName(String realName) {
    this.realName = realName;
  }

  public Integer getSflag() {
    return sflag;
  }

  public void setSflag(Integer sflag) {
    this.sflag = sflag;
  }

  public Integer getLock() {
    return lock;
  }

  public void setLock(Integer lock) {
    this.lock = lock;
  }

  public String getCreateDate() {
    return createDate;
  }

  public void setCreateDate(String createDate) {
    this.createDate = createDate;
  }

  public List<Role> getRoles() {
    return roles;
  }

  public void setRoles(List<Role> roles) {
    this.roles = roles;
  }

  public String getsToken() {
    return sToken;
  }

  public void setsToken(String sToken) {
    this.sToken = sToken;
  }

  public String getTextStr() {
    return textStr;
  }

  public void setTextStr(String textStr) {
    this.textStr = textStr;
  }

  public String getOpenId() {
    return openId;
  }

  public void setOpenId(String openId) {
    this.openId = openId;
  }

  public String getNickName() {
    return nickName;
  }

  public void setNickName(String nickName) {
    this.nickName = nickName;
  }

  public String getHeadImgUrl() {
    return headImgUrl;
  }

  public void setHeadImgUrl(String headImgUrl) {
    this.headImgUrl = headImgUrl;
  }
}
