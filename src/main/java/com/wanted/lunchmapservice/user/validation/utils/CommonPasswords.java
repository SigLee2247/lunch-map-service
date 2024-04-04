package com.wanted.lunchmapservice.user.validation.utils;


import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import lombok.Getter;

@Getter
public class CommonPasswords {

    private static final Map<String, Boolean> commonPasswordMap;

    static {
        commonPasswordMap = init();
    }

    private CommonPasswords() {
    }

    private static Map<String, Boolean> init() {
        Map<String, Boolean> map = new ConcurrentHashMap<>();
        initData(map);
        return map;
    }

    public static boolean isCommonPassword(String password) {
        return commonPasswordMap.containsKey(password);
    }

    private static void initData(Map<String, Boolean> map) {
        map.put("abc123", true);
        map.put("password1", true);
        map.put("1q2w3e4r", true);
        map.put("qwerty123", true);
        map.put("zaq12wsx", true);
        map.put("qaz2wsx", true);
        map.put("p@ssw0rd!!", true);
    }
}
