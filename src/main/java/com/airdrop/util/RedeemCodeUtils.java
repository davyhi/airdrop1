package com.airdrop.util;

/**
 * @author ShengGuang.Ye
 * @version V1.0
 * @Description: 兑换码生成工具类
 * @date 2018/9/18 17:42
 */
public class RedeemCodeUtils {

    /**
     * 生成兑换码方法测试
     *
     * @param args
     */
    public static void main(String[] args) {
        String st1 = createBigSmallLetterStrOrNumberRadom(12);
        String st2 = createSmallStrOrNumberRadom(12);
        String st3 = createBigStrOrNumberRadom(12);
        System.out.println(st1);
        System.out.println(st2);
        System.out.println(st3);
    }

    /**
     * @param num
     * @return
     * @function 生成num位的随机字符串(数字 、 大写 、 小写字母随机混排)
     */
    public static String createBigSmallLetterStrOrNumberRadom(int num) {

        String str = "";
        for (int i = 0; i < num; i++) {
            int intVal = (int) (Math.random() * 58 + 65);
            if (intVal >= 91 && intVal <= 96) {
                i--;
            }
            if (intVal < 91 || intVal > 96) {
                if (intVal % 2 == 0) {
                    str += (char) intVal;
                } else {
                    str += (int) (Math.random() * 10);
                }
            }
        }
        return str;
    }

    /**
     * @param num
     * @return
     * @function 生成num位的随机字符串(数字 、 小写字母随机混排)
     */
    public static String createSmallStrOrNumberRadom(int num) {

        String str = "";
        for (int i = 0; i < num; i++) {
            int intVal = (int) (Math.random() * 26 + 97);
            if (intVal % 2 == 0) {
                str += (char) intVal;
            } else {
                str += (int) (Math.random() * 10);
            }
        }
        return str;
    }

    /**
     * @param num
     * @return
     * @function 生成num位的随机字符串(数字 、 大写字母混排)
     */
    public static String createBigStrOrNumberRadom(int num) {

        String str = "";
        for (int i = 0; i < num; i++) {
            int intVal = (int) (Math.random() * 26 + 65);
            if (intVal % 2 == 0) {
                str += (char) intVal;
            } else {
                str += (int) (Math.random() * 10);
            }
        }
        return str;
    }
}