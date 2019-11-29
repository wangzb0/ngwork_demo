package com.example.demo.entity;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import lombok.Data;

import java.time.Instant;
import java.util.Map;

/**
 * @author wzb
 * @description TODO
 * @ClassName WxBaseMessage
 * @create: 2019-11-14 11:07
 */
@Data
@XStreamAlias("xml")
public class WxBaseMessage {
    @XStreamAlias("ToUserName")
    private String toUserName;
    @XStreamAlias("FromUserName")
    private String fromUserName;
    @XStreamAlias("CreateTime")
    private String createTime;
    @XStreamAlias("MsgType")
    private String msgType;

    public WxBaseMessage(Map<String, Object> requestMap) {
        this.toUserName = (String) requestMap.get("FromUserName");
        this.fromUserName = (String) requestMap.get("ToUserName");
        this.createTime = String.valueOf(Instant.now().getEpochSecond());
    }

}
