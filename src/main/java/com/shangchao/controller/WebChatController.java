package com.shangchao.controller;

import com.alibaba.fastjson.JSONObject;
import com.shangchao.commons.ResultState;
import com.shangchao.commons.WechatMaterialConstant;
import com.shangchao.entity.UploadNewsEntity;
import com.shangchao.entity.WebChatEntity;
import com.shangchao.service.WechatAuthService;
import com.shangchao.service.WechatMaterialService;
import com.shangchao.utils.ConfigUtil;
import com.shangchao.utils.ResponseUtil;
import com.shangchao.utils.WebChatUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping(value = "/webchat")
@Api(tags = "微信分享相关接口", description = "如有疑问请联系王宪良")
public class WebChatController {

  @Autowired
  private WechatAuthService wechatAuthService;

  @Autowired
  private WechatMaterialService wechatMaterialService;

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
  @GetMapping("/testM")
  public void testM(){
    String filePath = "F:/Chrysanthemum.jpg";
//    String accessToken = wechatAuthService.getAccessToken();
//    System.out.println("新增临时本地图片素材,返回" + wechatMaterialService.uploadTempMediaFile(accessToken,
//            WechatMaterialConstant.MATERIAL_UPLOAD_TYPE_IMAGE, filePath));
    String accessToken = wechatAuthService.getAccessToken();
    System.out.println("新增永久本地图片素材,返回" + wechatMaterialService.uploadForeverMediaFile(accessToken,
            WechatMaterialConstant.MATERIAL_UPLOAD_TYPE_IMAGE, filePath));

  }
  @PostMapping("/uploadNewsMedia")
  @ApiOperation("新增永久图文素材")
  @ApiImplicitParams({
          @ApiImplicitParam(name = "title", value = "标题", required = true),
          @ApiImplicitParam(name = "thumbMediaId", value = "图文消息的封面图片素材id（必须是永久mediaID）", required = true),
          @ApiImplicitParam(name = "author", value = "作者", required = false),
          @ApiImplicitParam(name = "digest", value = "图文消息的摘要，仅有单图文消息才有摘要，多图文此处为空。如果本字段为没有填写，则默认抓取正文前64个字。", required = false),
          @ApiImplicitParam(name = "showConverPic", value = "是否显示封面，0为false，即不显示，1为true，即显示", required = true),
          @ApiImplicitParam(name = "content", value = "图文消息的具体内容，支持HTML标签，必须少于2万字符，小于1M，且此处会去除JS,涉及图片url必须来源 \"上传图文消息内的图片获取URL\"接口获取。外部图片url将被过滤。", required = true),
          @ApiImplicitParam(name = "contentSourceUrl", value = "图文消息的原文地址，即点击“阅读原文”后的URL", required = true),
          @ApiImplicitParam(name = "needOpenComment", value = "Uint32 是否打开评论，0不打开，1打开", required = false),
          @ApiImplicitParam(name = "onlyFansCanComment", value = "Uint32 是否粉丝才可评论，0所有人可评论，1粉丝才可评论", required = false),
  })
  public JSONObject uploadNewsMedia(@RequestBody JSONObject params){
    String thumbMediaId= params.getString("thumbMediaId").toString();
    String author= params.getString("author").toString();
    String title= params.getString("title").toString();
    String contentSourceUrl= params.getString("contentSourceUrl").toString();
    String content= params.getString("content").toString();
    String digest= params.getString("digest").toString();
    String showConverPic= params.getString("showConverPic").toString();
    String needOpenComment= params.getString("needOpenComment")==null?"1":params.getString("needOpenComment").toString();
    String onlyFansCanComment= params.getString("onlyFansCanComment")==null?"0":params.getString("needOpenComment").toString();
    String accessToken = wechatAuthService.getAccessToken();
    UploadNewsEntity entity = new UploadNewsEntity();
    UploadNewsEntity.UploadNewsArticle article2 = new UploadNewsEntity.UploadNewsArticle();
        article2.setThumbMediaId(thumbMediaId);
    article2.setAuthor(author);
    article2.setTitle(title);
    article2.setContentSourceUrl(contentSourceUrl);
    article2.setContent(content);
    article2.setDigest(digest);
    article2.setShowConverPic(Integer.valueOf(showConverPic));
    article2.setNeedOpenComment(Integer.valueOf(needOpenComment));
    article2.setOnlyFansCanComment(Integer.valueOf(onlyFansCanComment));
    entity.addArticle(article2);
    return ResponseUtil.success(wechatMaterialService.uploadNewsMedia(accessToken, entity));
//    article2.setThumbMediaId("kXL9WDhls0p1spPVpmeQBPe7f5NGfgvgvVf2abbujD4");
//    article2.setAuthor("wxl");
//    article2.setTitle("文章测试");
//    article2.setContentSourceUrl("https://developers.weixin.qq.com/doc/offiaccount/Asset_Management/Adding_Permanent_Assets.html");
//    article2.setContent("<html>abcde<img src='http://mmbiz.qpic.cn/mmbiz_jpg/4P0MBEOaeiadpeRrCVricIKDyEibfYmvB1wBjs332VAA3N4DA9RnSCRaCGmNmq0iay2xYoxwqHZ3BVEd4cokplWSxw/0?wx_fmt=jpeg'></img></html>");
//    article2.setDigest("article2");
//    article2.setShowConverPic(1);
//    entity.addArticle(article2);
//    System.out.println("新增永久图文素材, 返回" + wechatMaterialService.uploadNewsMedia(accessToken, entity));
  }
  @GetMapping("/deleteMaterial")
  @ApiOperation("删除永久素材")
  @ApiImplicitParams({
          @ApiImplicitParam(name = "mediaId", value = "要获取的素材的media_id", required = true)
  })
  public JSONObject deleteMaterial(String mediaId){
    String accessToken = wechatAuthService.getAccessToken();
    ResultState resultState=wechatMaterialService.deleteMaterial(accessToken,mediaId);
    System.out.println(resultState.getErrcode());
    if(resultState.getErrcode()==0){
        return ResponseUtil.success(resultState.getErrmsg());
    }else{
      return ResponseUtil.fail(resultState.getErrmsg());
    }
  }

  @PostMapping("/uploadForeverMediaFile")
  @ApiOperation("新增永久素材(网络)")
  @ApiImplicitParams({
          @ApiImplicitParam(name = "type", value = "素材类型：" +
                  "图片（image）: 2M，支持bmp/png/jpeg/jpg/gif格式\n" +
                  "\n" +
                  "语音（voice）：2M，播放长度不超过60s，mp3/wma/wav/amr格式\n" +
                  "\n" +
                  "视频（video）：10MB，支持MP4格式\n" +
                  "\n" +
                  "缩略图（thumb）：64KB，支持JPG格式）", required = true),
          @ApiImplicitParam(name = "url", value = "网络路径", required = true)
  })
  public JSONObject uploadForeverMediaUrl(@RequestBody JSONObject params){
    String type= params.getString("type").toString();
    String url= params.getString("url").toString();
    String accessToken = wechatAuthService.getAccessToken();
    return ResponseUtil.success(wechatMaterialService.uploadForeverMediaUrl(accessToken,
            WechatMaterialConstant.MATERIAL_UPLOAD_TYPE_IMAGE, url));
  }
}
