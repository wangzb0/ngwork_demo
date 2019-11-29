package com.example.demo.entity;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import lombok.Data;

import java.util.Map;

/**
 * @author wzb
 * @description TODO
 * @ClassName WxTextMessage
 * @create: 2019-11-14 11:07
 */
@Data
@XStreamAlias("xml")
public class WxTextMessage extends WxBaseMessage {
    @XStreamAlias("Content")
    private String content;

    public WxTextMessage(Map<String, Object> requestMap, String content) {
        super(requestMap);
        this.setMsgType("text");
        this.content = content;
    }
}
