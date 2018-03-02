package com.lab.hosaily.core.client.consts;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
public enum SMSConfig{

    NINE_LAB(0, "九研美尚", "aa0791acb6e280103d83f283e7eef954", "九研美尚");

    @Getter
    private int id;
    @Getter
    private String description;
    @Getter
    private String apiKey;
    @Getter
    private String company;

    public static SMSConfig getById(int id){
        for(SMSConfig config : SMSConfig.values()){
            if(config.getId() == id){
                return config;
            }
        }

        throw new IllegalArgumentException("未知的云片账户常量");
    }
}
