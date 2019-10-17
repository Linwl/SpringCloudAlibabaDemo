package com.linwl.springcloudalibabademo.converter;

import org.apache.commons.beanutils.converters.AbstractConverter;
import org.apache.commons.lang3.StringUtils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @author ：linwl
 * @date ：Created in 2019/10/17 10:36
 * @description ：
 * @modified By：
 */
public class DateTimeStringConverter extends AbstractConverter {
    private String timePattern = "yyyy-MM-dd HH:mm:ss";
    private DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(timePattern);

    public DateTimeStringConverter() {
        super();
    }

    public DateTimeStringConverter(String timePattern) {
        super();
        if (StringUtils.isNotBlank(timePattern) && !this.timePattern.equals(timePattern)) {
            this.timePattern = timePattern;
            dateTimeFormatter = DateTimeFormatter.ofPattern(timePattern);
        }

    }

    public DateTimeStringConverter(Object defaultValue) {
        super(defaultValue);
    }

    @Override
    protected Class<?> getDefaultType() {
        return String.class;
    }

    @Override
    protected <T> T convertToType(Class<T> type, Object value) throws Throwable {
        // We have to support Object, too, because this class is sometimes
        // used for a standard to Object conversion
        if (String.class.equals(type) || Object.class.equals(type)) {
            return type.cast(value.toString());
        }
        throw conversionException(type, value);
    }

    /**
     * 转成时间字符串
     */
    @Override
    protected String convertToString(Object value) throws Throwable {
        if (value.getClass().equals(LocalDateTime.class)) {
            return LocalDateTime.class.cast(value).format(dateTimeFormatter);
        }
        return super.convertToString(value);
    }

}
