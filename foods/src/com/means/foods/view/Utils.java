package com.means.foods.view;

public class Utils {
	/**
     * 是否包含字母 和 数字
     *
     * @param str
     * @return
     */
    public static boolean isValidPassword(String str) {
        boolean isDigit = false;//定义一个boolean值，用来表示是否包含数字
        boolean isLetter = false;//定义一个boolean值，用来表示是否包含字母
        for (int i = 0; i < str.length(); i++) { //循环遍历字符串
            if (Character.isDigit(str.charAt(i))) {     //用char包装类中的判断数字的方法判断每一个字符
                isDigit = true;
            }
            if (Character.isLetter(str.charAt(i))) {   //用char包装类中的判断字母的方法判断每一个字符
                isLetter = true;
            }
        }
        //包含字母和数字
        if (isDigit && isLetter)
            return true;
        return false;
    }
}
