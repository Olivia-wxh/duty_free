package com.shangchao.controller;

import com.shangchao.entity.WebChatEntity;
import com.shangchao.utils.ConfigUtil;
import com.shangchao.utils.WebChatUtil;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping(value = "/webchat")
public class WebChatController {
    @RequestMapping("/signature")
    @ResponseBody
    public Map<String, Object> signature(HttpServletRequest request) {
        String strUrl=request.getParameter("url");
        WebChatEntity wx = WebChatUtil.getWebChatEntity(strUrl);
        // 将wx的信息到给页面
        Map<String, Object> map = new HashMap<String, Object>();
        String sgture = WebChatUtil.getSignature(wx.getTicket(), wx.getNoncestr(), wx.getTimestamp(), strUrl);
        map.put("sgture", sgture.trim());//签名
        map.put("timestamp", wx.getTimestamp().trim());//时间戳
        map.put("noncestr",  wx.getNoncestr().trim());//随即串
        map.put("appid", ConfigUtil.getCommonYml("webchat.webChatAppId"));//appID
        return map;
    }
}
