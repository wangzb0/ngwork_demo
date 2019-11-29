package com.example.demo.controller;

import cn.hutool.core.util.XmlUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.example.demo.entity.WxAccessToken;
import com.example.demo.entity.WxAccessTokenResult;
import com.example.demo.feign.WxFeingClient;
import com.example.demo.service.WxService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.w3c.dom.Document;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * @author wzb
 * @description TODO
 * @ClassName TestController
 * @create: 2019-11-13 16:47
 */
@Slf4j
@RestController
@RequestMapping("wx")
public class TestController {

    @Value("${wx.token}")
    private String token;
    private static final String APPID = "wx05b7bf4206a778e7";
    private static final String APPSECRET = "ec7765c3f46b018600a1ecdb2e20d3f0";
    private static WxAccessToken wxAccessToken;

    @Autowired
    private WxFeingClient wxFeingClient;


    @GetMapping("test")
    public String getTest() {
        return "test";
    }

    @GetMapping(value = "access")
    public String WeChatInterface(HttpServletRequest request) throws Exception {
        log.info("-------------验证微信服务号信息开始----------");
        /*
            signature	微信加密签名，signature结合了开发者填写的token参数和请求中的timestamp参数、nonce参数。
            timestamp	时间戳
            nonce	随机数
            echostr	随机字符串
        */
        String signature = request.getParameter("signature");
        String timestamp = request.getParameter("timestamp");
        String nonce = request.getParameter("nonce");
        String echostr = request.getParameter("echostr");

        log.info("微信加密签名：[{}]，时间戳：[{}]，随机数：[{}]，随机字符串：[{}]", signature, timestamp, nonce, echostr);

        //校验
        if (WxService.isCheck(token, signature, timestamp, nonce)) {
            log.info("-----------验证微信服务号结束------------");
            return echostr;
        } else {
            log.info("-----------验证微信服务号错误------------");
            return null;
        }
    }

    /**
     * 接收消息和事件推送
     *
     * @param request
     * @param response
     * @throws Exception
     */
    @PostMapping("access")
    public String getWeiXinMessage(HttpServletRequest request, HttpServletResponse response) throws Exception {
        log.info("----------------开始处理微信发过来的消息------------------");
        Document document = XmlUtil.readXML(request.getInputStream());
        Map<String, Object> map = XmlUtil.xmlToMap(XmlUtil.toStr(document));
        log.info("map : ({})", map);
        String wxResponse = WxService.getWxResponse(map);
        if (wxResponse == null) return null;
        else return wxResponse;
    }

    /**
     * 获取accessToken
     *
     * @return
     */
    @GetMapping("getAccessToken")
    public String getAccessToken() {
        if (wxAccessToken == null || wxAccessToken.isExpired()) {
            getToken();
        }
        return wxAccessToken.getAccessToken();
    }

    private void getToken() {
        WxAccessTokenResult result = wxFeingClient.getAccessToken(APPID, APPSECRET);
        wxAccessToken = new WxAccessToken(result.getAccessToken(), result.getExpiresIn());
    }

    @GetMapping("getInfo")
    public void getInfo(HttpServletRequest request) {
        String code = request.getParameter("code");
        String str1 = wxFeingClient.getWebAccessToken("wx05b7bf4206a778e7", "ec7765c3f46b018600a1ecdb2e20d3f0", code);
        JSONObject obj = JSONUtil.parseObj(str1);
        String token = obj.getStr("access_token");
        String openid = obj.getStr("openid");

        String str2 = wxFeingClient.getUserInfo(token, openid);

        log.info("userInfo : ({})", str2);
    }

    @PostMapping("addKfAccount")
    public void addKfAccount() {
        String accessToken = getAccessToken();
        JSONObject obj = new JSONObject();
        obj.put("kf_account", "test1@gh_805a9f65654e");
        obj.put("nickname", "客服1");
        obj.put("password", "123456");
        log.info("AccessToken : ({})", accessToken);
        log.info("JsonObject : ({})", obj.toString());
        wxFeingClient.addKfAccount(accessToken, obj);
    }

    @PostMapping("sendMsg")
    public void sendMsg() {

    }
}
