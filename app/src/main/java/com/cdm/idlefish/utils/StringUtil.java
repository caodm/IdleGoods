package com.cdm.idlefish.utils;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.text.TextUtils;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/*******************************************************************
 * @文件名称: StringUtil
 * @作者 : yangx
 * @文件描述: 字符串工具类
 * @创建时间: 2016/6/13
 * ******************************************************************
 */
public class StringUtil {
    /**
     * 验证手机号码是否正确
     * 验证以13x,15x,17x,14x,18x 号段
     *
     * @param phoneNumber 传入手机号码
     * @return 是手机号码 返回 true 否则 返回 false
     */
    public static boolean isVaildPhoneNumber(String phoneNumber) {
        Pattern pattern = Pattern.compile("^((13[0-9])|(15[^4,\\D])|(17[0-9])|(14[0-9])|(18[0,0-9]))\\d{8}$");
        Matcher m = pattern.matcher(phoneNumber);
        return m.matches();
    }

    /**
     * 正则表达式 验证密码  6-16位 数字字母组合
     *
     * @param password
     * @return
     */
    public static boolean isValidPwd(String password) {
        Pattern pattern = Pattern.compile("(?!^\\d+$)(?!^[a-zA-Z]+$)^[0-9a-zA-Z]{6,16}$");
        Matcher matcher = pattern.matcher(password);
        return matcher.matches();
    }

    public static String formatNull(String str) {
        return TextUtils.isEmpty(str) ? "" : str;
    }

    /**
     * 获取版本信息
     *
     * @return
     */
    public static String getVersionName(Context context) {
        try {
            PackageInfo packageInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            return packageInfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return "未知版本";
        }
    }

    /**
     * 保留两位小数
     *
     * @param strNum
     * @return
     */
    public static String getDecimalFormatNum(String strNum) {
        DecimalFormat decimalFormat = new DecimalFormat("0.00");
        try {
            return decimalFormat.parse(strNum).toString();
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }

    }

    /**
     * 分割字符串
     * @param str
     * @param split
     * @return
     */
    public static String[] splitString(String str,String split){
        if(str==null || split==null){
            return  null;
        }
        return str.split(split);
    }

    /**
     * 移除空值
     * @param arr
     * @return
     */
    public static List<String> formatArr(List<String> arr){
        if(arr==null){
            return null;
        }
        for(int i=0;i<arr.size();i++){
            if(TextUtils.isEmpty(arr.get(i))){
                arr.remove(i);
                continue;
            }
        }
        return arr;
    }

}
