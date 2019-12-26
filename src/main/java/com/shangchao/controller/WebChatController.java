package com.shangchao.controller;

import com.shangchao.entity.WebChatEntity;
import com.shangchao.utils.ConfigUtil;
import com.shangchao.utils.WebChatUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping(value = "/webchat")
@Api(tags = "微信分享相关接口", description = "如有疑问请联系王宪良")
public class WebChatController {

  @GetMapping("/signature")
  @ApiOperation("微信分享的接口")
  @ApiImplicitParam(name = "url", value = "分享地址Url", required = true)
  public Map<String, Object> signature(HttpServletRequest request) {
    String strUrl = request.getParameter("url");
    WebChatEntity wx = WebChatUtil.getWebChatEntity(strUrl);
    // 将wx的信息到给页面
    Map<String, Object> map = new HashMap<String, Object>();
    String sgture =
        WebChatUtil.getSignature(wx.getTicket(), wx.getNoncestr(), wx.getTimestamp(), strUrl);
    map.put("sgture", sgture.trim()); // 签名
    map.put("timestamp", wx.getTimestamp().trim()); // 时间戳
    map.put("noncestr", wx.getNoncestr().trim()); // 随即串
    map.put("appid", ConfigUtil.getCommonYml("webchat.webChatAppId")); // appID
    return map;
  }
}
