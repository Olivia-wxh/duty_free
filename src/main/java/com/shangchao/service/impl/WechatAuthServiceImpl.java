/**
 * FileName: AuthServiceImpl
 * Author:   Phil
 * Date:     11/21/2018 12:13 PM
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.shangchao.service.impl;

import com.google.gson.JsonSyntaxException;
import com.shangchao.commons.CommonConstant;
import com.shangchao.service.WechatAuthService;
import com.shangchao.utils.ConfigUtil;
import com.shangchao.utils.RedisUtils;
import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONObject;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.TreeMap;

/**
 * 〈一句话功能简述〉<br>
 * 〈〉
 *
 * @author Phil
 * @create 11/21/2018 12:13 PM
 * @since 1.0
 */
@Service
@Slf4j
public class WechatAuthServiceImpl implements WechatAuthService {

    @Resource
    RedisUtils redisUtils;


    /**
     * 获取授权凭证token
     *
     * @return 授权凭证token
     */
    @Override
    public String getAccessToken() {
        String accessToken = null;
        String grant_type = "client_credential"; // 获取access_token填写client_credential
        if (Objects.isNull(redisUtils.get(CommonConstant.WEBCHAT_TOKEN))) {
            Map<String, String> map = new TreeMap<>();
            String AppId = (String) ConfigUtil.getCommonYml("webchat.officialAccountsAppId"); // 第三方用户唯一凭证
            String secret =
                    (String) ConfigUtil.getCommonYml("webchat.officialAccountsAppSecret"); // 第三方用户唯一凭证密钥，即appsecret
            map.put("appid",AppId);
            map.put("secret", secret);
            map.put("grant_type", "client_credential");
            String url =
                    "https://api.weixin.qq.com/cgi-bin/token?grant_type="
                            + grant_type
                            + "&appid="
                            + AppId
                            + "&secret="
                            + secret; // 访问链接

            try {
                URL urlGet = new URL(url);
                HttpURLConnection http = (HttpURLConnection) urlGet.openConnection();
                http.setRequestMethod("GET"); // 必须是get方式请求
                http.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                http.setDoOutput(true);
                http.setDoInput(true);
                http.connect();
                InputStream is = http.getInputStream();
                int size = is.available();
                byte[] jsonBytes = new byte[size];
                is.read(jsonBytes);
                String message = new String(jsonBytes);
                JSONObject tokenJson = JSONObject.fromObject(message);
                accessToken = tokenJson.getString("access_token");
                redisUtils.set(CommonConstant.WEBCHAT_TOKEN, accessToken, 60 * 120);
                is.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            accessToken = redisUtils.get(CommonConstant.WEBCHAT_TOKEN).toString();
            log.info("从redis中获取的授权凭证{}", accessToken);
        }
        return accessToken;
    }
}
