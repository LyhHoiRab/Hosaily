package com.lab.hosaily.commons.utils;

import org.apache.commons.lang3.StringUtils;

import java.security.SecureRandom;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class RandomStringUtils {

    private static final String DEFAULT = "AaBbCcDdEeFfGgHhIiJjKkLlMmNnOoPpQqRrSsTtUuVvWwXxYyZz0123456789";
    private static final String DEFAULT_LETTER = "AaBbCcDdEeFfGgHhIiJjKkLlMmNnOoPpQqRrSsTtUuVvWwXxYyZz";
    private static final String DEFAULT_UPPER = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final String DEFAULT_LOWER = DEFAULT_UPPER.toLowerCase();
    private static final String DEFAULT_NUM = "0123456789";
    private static final List<String> SIMILAR = Arrays.asList("0", "2", "1", "5", "8", "9", "g", "q", "o", "l", "s", "w", "x", "v", "u", "k", "c", "y", "p", "z", "P", "C", "K", "Y", "U", "V", "X", "W", "Z", "S", "O", "I", "B");

    public RandomStringUtils(){

    }

    private static String random(int length, boolean similar, String symbol){
        if(length < 1){
            throw new RuntimeException("随机字符串长度不能小于1");
        }

        if(StringUtils.isBlank(symbol)){
            throw new RuntimeException("随机字符串选集不能为空");
        }

        Random random = new SecureRandom();
        char[] symbols = symbol.toCharArray();
        char[] buf = new char[length];
        int i = 0;

        while(i < buf.length){
            char letter = symbols[random.nextInt(symbols.length)];

            if(!similar){
                if(SIMILAR.contains(String.valueOf(letter))){
                    continue;
                }
            }

            buf[i] = letter;
            i += 1;
        }

        return new String(buf);
    }

    public static String next(int length, boolean similar){
        return random(length, similar, DEFAULT);
    }

    public static String nextUpper(int length){
        return random(length, true, DEFAULT_UPPER);
    }

    public static String nextLower(int length){
        return random(length, true, DEFAULT_LOWER);
    }

    public static String nextLetter(int length, boolean similar){
        return random(length, similar, DEFAULT_LETTER);
    }

    public static String nextNumber(int length){
        return random(length, true, DEFAULT_NUM);
    }
}
