package com.example.demo.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author wzb
 * @description TODO
 * @ClassName WxAccessTokenResult
 * @create: 2019-11-14 15:29
 */
@Data
public class WxAccessTokenResult implements Serializable{

    @JsonProperty("access_token")
    private String accessToken;
    @JsonProperty("expires_in")
    private long expiresIn;
    @JsonProperty("errcode")
    private long errCode;
    @JsonProperty("errmsg")
    private String errMsg;
}
