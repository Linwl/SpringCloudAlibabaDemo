package com.linwl.springcloudalibabademo.authentication.config.db;

import com.linwl.springcloudalibabademo.db.DbContextHolder;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

/**
 * @author ：linwl
 * @date ：Created in 2019/10/17 9:26
 * @description ：
 * @modified By：
 */
public class DynamicDataSource extends AbstractRoutingDataSource {

    @Override
    public Object determineCurrentLookupKey()
    {
        return DbContextHolder.getDbType();
    }
}

