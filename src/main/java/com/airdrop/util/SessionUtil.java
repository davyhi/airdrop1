package com.airdrop.util;

import javax.servlet.http.HttpSession;
import java.util.Hashtable;

/**
 * @author ShengGuang.Ye
 * @version V1.0
 * @Description: session管理
 * @date 2018/9/11 10:23
 */
public class SessionUtil {

    private static Hashtable<String, HttpSession> sessionHashtable = new Hashtable<>();


    /**
     * 添加用户session
     *
     * @param token
     * @param session
     */
    public static void addUser(String token, HttpSession session) {
        sessionHashtable.put(token, session);
    }

    /**
     * 删除用户session
     *
     * @param token
     */
    public static void removeUser(String token) {
        sessionHashtable.remove(token);
    }

    /**
     * 检测用户的token是否还在有效状态
     *
     * @param token
     * @return true:通过，false不通过
     */
    public static boolean checkUser(String token) {
        HttpSession httpSession = sessionHashtable.get(token);
        // 判断token是否存在
        if (httpSession == null) {
            return false;
        }
        try {
            // 判断session是否过了有效期
            if ((System.currentTimeMillis() - httpSession.getCreationTime()) / 1000 >= httpSession.getMaxInactiveInterval()) {
                sessionHashtable.remove(token);
                return false;
            }
        } catch (IllegalStateException e) {
            return false;
        }

        return true;
    }


}
