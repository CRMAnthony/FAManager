package com.jszt.util;

/**
 * 一些公共的方法
 * <一句话功能简述>
 * <功能详细描述>
 * 
 * @author  ling
 * @version  [版本号, 2015-7-3]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class CommonUtil
{
    /**
     * 日期格式转换
     * <一句话功能简述>
     * <功能详细描述>
     * @param date
     * @return  xxxx年xx月xx日
     * @see [类、类#方法、类#成员]
     */
    public static String intDateFormater1(int date)
    {
        String dateString = String.valueOf(date);
        StringBuilder dateBuilder = new StringBuilder();
        dateBuilder.append(dateString.substring(0, 4));
        dateBuilder.append("年");
        dateBuilder.append(dateString.subSequence(4, 6));
        dateBuilder.append("月");
        dateBuilder.append(dateString.substring(6));
        dateBuilder.append("日");
        
        return dateBuilder.toString();
    }
    
    /**
     * 日期格式转换
     * <一句话功能简述>
     * <功能详细描述>
     * @param date
     * @return xxxx-xx-xx
     * @see [类、类#方法、类#成员]
     */
    public static String intDateFormater2(int date)
    {
        String dateString = String.valueOf(date);
        StringBuilder dateBuilder = new StringBuilder();
        dateBuilder.append(dateString.substring(0, 4));
        dateBuilder.append("-");
        dateBuilder.append(dateString.subSequence(4, 6));
        dateBuilder.append("-");
        dateBuilder.append(dateString.substring(6));
        
        return dateBuilder.toString();
    }
}
