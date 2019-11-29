package com.example.demo.feign;

import cn.hutool.json.JSONObject;
import com.example.demo.entity.WxAccessTokenResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @author wzb
 * @description TODO
 * @ClassName WxFeingClient
 * @create: 2019-11-14 15:22
 */
@FeignClient(name = "wx-client", url = "https://api.weixin.qq.com")
public interface WxFeingClient {

    @GetMapping("/cgi-bin/token?grant_type=client_credential&appid={appId}&secret={appSecret}")
    WxAccessTokenResult getAccessToken(@PathVariable("appId") String appId, @PathVariable("appSecret") String appSecret);

    @GetMapping("/sns/oauth2/access_token?appid={appId}&secret={appSecret}&code={code}&grant_type=authorization_code")
    String getWebAccessToken(@PathVariable("appId") String appId, @PathVariable("appSecret") String appSecret,
                             @PathVariable("code") String code);

    @GetMapping("/sns/userinfo?access_token={accessToken}&openid={oppenId}&lang=zh_CN")
    String getUserInfo(@PathVariable("accessToken") String accessToken, @PathVariable("oppenId") String oppenId);

    @PostMapping("/customservice/kfaccount/add?access_token={accessToken}")
    String addKfAccount(@PathVariable("accessToken") String accessToken, @RequestBody JSONObject obj);

    @PostMapping("/cgi-bin/message/custom/send?access_token={accessToken}")
    String sendMsg(@PathVariable("accessToken") String accessToken, @RequestBody JSONObject obj);
}
