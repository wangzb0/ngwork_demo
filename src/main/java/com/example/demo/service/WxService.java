package com.example.demo.service;

import cn.hutool.crypto.SecureUtil;
import com.example.demo.entity.WxBaseMessage;
import com.example.demo.entity.WxTextMessage;
import com.thoughtworks.xstream.XStream;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Map;

/**
 * @author wzb
 * @description TODO
 * @ClassName WxService
 * @create: 2019-11-13 17:46
 */
@Service
public class WxService {
    public static Boolean isCheck(String token, String signature, String timestamp, String nonce) {

        // 1 将token、timestamp、nonce三个参数进行字典序排序
        String[] sortStrs = new String[]{token, timestamp, nonce};
        Arrays.sort(sortStrs);
        // 2 将三个参数字符串拼接成一个字符串进行sha1加密
        String needSha1 = sortStrs[0] + sortStrs[1] + sortStrs[2];
        String sha1 = SecureUtil.sha1().digestHex(needSha1);
        // 3 开发者获得加密后的字符串可与signature对比，标识该请求来源于微信
        return signature.equalsIgnoreCase(sha1);
    }

    public static String getWxResponse(Map<String, Object> requestMap) {
        WxBaseMessage wxMsg = null;
        String msgType = (String) requestMap.get("MsgType");
        switch (msgType) {
            case "text":
                wxMsg = dealTextMessage(requestMap);
                break;
            case "image":

                break;
            case "voice":

                break;
            case "video":

                break;
            case "shortvideo":

                break;
            case "location":

                break;
            case "link":

                break;
            case "event":
                wxMsg = dealEvent(requestMap);
                break;
            default:

                break;
        }
        return beanToXml(wxMsg);
    }

    private static WxBaseMessage dealEvent(Map<String, Object> requestMap) {
        String event = (String) requestMap.get("Event");
        switch (event) {
            case "CLICK":
                //return dealClick(requestMap);
                break;
            case "VIEW":

                break;
            default:
                break;
        }
        return null;
    }

    private static WxBaseMessage dealTextMessage(Map<String, Object> requestMap) {
        String msg = (String) requestMap.get("Content");
        String url = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=wx05b7bf4206a778e7&redirect_uri=http://www.aes666.com/wx/getInfo" +
                "&response_type=code&scope=snsapi_userinfo#wechat_redirect";
        if ("登录".equals(msg)) return new WxTextMessage(requestMap, "<a href=\"" + url + "\">点我登录</a>");
        else return null;
    }

    private static String beanToXml(WxBaseMessage wxMsg) {
        XStream stream = new XStream();
        // 处理使用@XStreamAlias("xml")注解的类
        stream.processAnnotations(WxTextMessage.class);
        return stream.toXML(wxMsg);
    }
}
