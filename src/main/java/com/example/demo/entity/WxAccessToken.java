package com.example.demo.entity;

import lombok.Data;

import java.time.Instant;

/**
 * @author wzb
 * @description TODO
 * @ClassName WxAccessToken
 * @create: 2019-11-14 15:26
 */
@Data
public class WxAccessToken {
    private String accessToken;
    private long expireTime;

    public WxAccessToken(String accessToken, long expireIn) {
        this.accessToken = accessToken;
        this.expireTime = Instant.now().toEpochMilli() + expireIn * 1000;
    }

    /**
     * 判断token是否过期
     *
     * @return
     */
    public boolean isExpired() {
        return Instant.now().toEpochMilli() > expireTime;
    }
}
