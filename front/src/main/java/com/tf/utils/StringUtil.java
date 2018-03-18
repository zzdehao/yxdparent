package com.tf.utils;
/**
 * 字符串工具类
 *
 * @author
 */
public class StringUtil {

    /**
     * 判断是否是空
     *
     * @param str
     * @return
     */
    public static boolean isEmpty(String str) {
        return str == null || "".equals(str.trim());
    }
    /**
     * 判断多个参数是否是空
     *
     * @param str
     * @return
     */
    public static int isNotEmpty(String... strArray) {
        int i = 0;
        for (String str : strArray){
            if(str == null || "".equals(str.trim())){
                return i;
            }
            i++;
        }
        return 0;
    }

    /**
     * 判断是否不是空
     *
     * @param str
     * @return
     */
    public static boolean isNotEmpty(String str) {
        return (str != null) && !"".equals(str.trim());
    }

    /**
     * 格式化模糊查询
     *
     * @param str
     * @return
     */
    public static String formatLike(String str) {
        if (isNotEmpty(str)) {
            return "%" + str + "%";
        } else {
            return null;
        }
    }
}
