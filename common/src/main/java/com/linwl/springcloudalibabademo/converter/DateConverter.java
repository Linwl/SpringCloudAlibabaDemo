package com.linwl.springcloudalibabademo.converter;

import org.apache.commons.beanutils.converters.DateTimeConverter;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

/**
 * @author ：linwl
 * @date ：Created in 2019/10/17 10:40
 * @description ：
 * @modified By：
 */
public class DateConverter extends DateTimeConverter {

    public DateConverter() {
        super();
    }

    public DateConverter(final Object defaultValue) {
        super(defaultValue);
    }

    @Override
    protected Class<?> getDefaultType() {
        return Date.class;
    }

    @Override
    protected <T> T convertToType(Class<T> targetType, Object value) throws Exception {

        if (value instanceof LocalDateTime) {
            ZoneId zone = ZoneId.systemDefault();
            LocalDateTime localDateTime = (LocalDateTime) value;
            Instant instant = localDateTime.atZone(zone).toInstant();
            return targetType.cast(Date.from(instant));
        } else if (value instanceof LocalDate) {
            ZoneId zone = ZoneId.systemDefault();
            LocalDate localDate = (LocalDate) value;
            Instant instant = localDate.atStartOfDay().atZone(zone).toInstant();
            return targetType.cast(Date.from(instant));
        } else {
            return super.convertToType(targetType, value);
        }
    }
}
