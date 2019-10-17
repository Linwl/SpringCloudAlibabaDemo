package com.linwl.springcloudalibabademo.converter;

import org.apache.commons.beanutils.converters.AbstractConverter;
import org.apache.commons.lang3.StringUtils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @author ：linwl
 * @date ：Created in 2019/10/17 10:40
 * @description ：
 * @modified By：
 */
public class LocalDateTimeConverter extends AbstractConverter {

    private String timePattern = "yyyy-MM-dd HH:mm:ss";
    private DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(timePattern);


    public LocalDateTimeConverter() {
        super();
    }

    public LocalDateTimeConverter(String timePattern) {
        super();
        if (StringUtils.isNotBlank(timePattern) && !this.timePattern.equals(timePattern)) {
            this.timePattern = timePattern;
            this.dateTimeFormatter = DateTimeFormatter.ofPattern(timePattern);
        }

    }

    public LocalDateTimeConverter(Object defaultValue) {
        super(defaultValue);
    }

    @Override
    protected Class<?> getDefaultType() {
        return LocalDateTime.class;
    }

    @Override
    protected <T> T convertToType(Class<T> type, Object value) throws Throwable {
        // We have to support Object, too, because this class is sometimes
        // used for a standard to Object conversion
        if (LocalDateTime.class.equals(type)) {

            return type.cast(LocalDateTime.parse(value.toString(), dateTimeFormatter));
        }
        throw conversionException(type, value);
    }


}

