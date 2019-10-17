package com.linwl.springcloudalibabademo.utils;

import com.linwl.springcloudalibabademo.converter.DateConverter;
import com.linwl.springcloudalibabademo.converter.DateTimeStringConverter;
import com.linwl.springcloudalibabademo.converter.LocalDateTimeConverter;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.beanutils.SuppressPropertiesBeanIntrospector;
import org.apache.commons.beanutils.converters.StringConverter;
import org.apache.commons.lang3.StringUtils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author ：linwl
 * @date ：Created in 2019/10/17 10:38
 * @description ：
 * @modified By：
 */
public class CommonTools {

    private static DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private static DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");


    private CommonTools() {}

    /**
     * 返回默认处理字符串
     *
     * @param handleStr
     * @param defaultStr
     * @return
     */
    public static String handleToDefaultString(String handleStr, String defaultStr) {
        if (StringUtils.isBlank(handleStr)) {
            if (StringUtils.isBlank(defaultStr)) {
                return null;
            } else return defaultStr;
        }
        return handleStr;
    }

    /**
     * 返回默认LocalDate日期
     * @param handleStr
     * @param defaultDate
     * @return
     */
    public static LocalDate handleToDefaultDate(String handleStr, LocalDate defaultDate) {
        try {
            if (StringUtils.isBlank(handleStr)) {
                if (defaultDate == null) {
                    return null;
                } else return defaultDate;
            } else {
                return LocalDate.parse(handleStr, dateFormatter);
            }
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 返回默认LocalDateTime时间
     * @param handleStr
     * @param defaultDateTime
     * @return
     */
    public static LocalDateTime handleToDefaultDateTime(
            String handleStr, LocalDateTime defaultDateTime) {
        try {
            if (StringUtils.isBlank(handleStr)) {
                if (defaultDateTime == null) {
                    return null;
                } else return defaultDateTime;
            } else {
                return LocalDateTime.parse(handleStr, dateTimeFormatter);
            }
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 将byte数组转换为表示16进制值的字符串， 如：byte[]{8,18}转换为：0813， 和public static byte[] hexStr2ByteArr(String
     * strIn) 互为可逆的转换过程
     *
     * @param arrB 需要转换的byte数组
     * @return 转换后的字符串
     * @throws Exception 本方法不处理任何异常，所有异常全部抛出
     */
    public static String byteArr2HexStr(byte[] arrB) throws Exception {
        int iLen = arrB.length;
        // 每个byte用两个字符才能表示，所以字符串的长度是数组长度的两倍
        StringBuffer sb = new StringBuffer(iLen * 2);
        for (int i = 0; i < iLen; i++) {
            int intTmp = arrB[i];
            // 把负数转换为正数
            while (intTmp < 0) {
                intTmp = intTmp + 256;
            }
            // 小于0F的数需要在前面补0
            if (intTmp < 16) {
                sb.append("0");
            }
            sb.append(Integer.toString(intTmp, 16));
        }
        return sb.toString();
    }

    /**
     * 将表示16进制值的字符串转换为byte数组， 和public static String byteArr2HexStr(byte[] arrB) 互为可逆的转换过程
     *
     * @param strIn 需要转换的字符串
     * @return 转换后的byte数组
     */
    public static byte[] hexStr2ByteArr(String strIn) throws Exception {
        byte[] arrB = strIn.getBytes();
        int iLen = arrB.length;
        // 两个字符表示一个字节，所以字节数组长度是字符串长度除以2
        byte[] arrOut = new byte[iLen / 2];
        for (int i = 0; i < iLen; i = i + 2) {
            String strTmp = new String(arrB, i, 2);
            arrOut[i / 2] = (byte) Integer.parseInt(strTmp, 16);
        }
        return arrOut;
    }


    /**
     * 转换对象列表工具
     *
     * @param sources 来源对象列表
     * @param target  目标对象class
     * @param <T>     目标对象
     * @param <V>     来源对象
     * @return 目标对象列表
     * @throws Exception
     */
    public static <T, V> List<T> convertObjectList(List<V> sources, Class<?> target) throws Exception {
        List<T> list = new ArrayList<>();
        int size = sources.size();
        for (int i = 0; i < size; i++) {
            T module = (T) target.newInstance();
            V project = sources.get(i);
            CommonTools.initBeanUtilsConverters();
            BeanUtils.copyProperties(module, project);
            list.add(module);
        }
        return list;
    }

    /**
     * 初始BeanUtils化转换器
     */
    public static void initBeanUtilsConverters() {
        PropertyUtils.clearDescriptors();
        PropertyUtils.addBeanIntrospector(SuppressPropertiesBeanIntrospector.SUPPRESS_CLASS);
        ConvertUtils.deregister(StringConverter.class);
        ConvertUtils.register(new DateTimeStringConverter(), String.class);
        ConvertUtils.register(new LocalDateTimeConverter(), LocalDateTime.class);
        ConvertUtils.register(new DateConverter(), Date.class);
    }


}
