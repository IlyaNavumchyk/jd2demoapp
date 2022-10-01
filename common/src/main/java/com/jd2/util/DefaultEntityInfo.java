package com.jd2.util;

import com.jd2.domain.hibernate.HibernateEntityInfo;

import java.sql.Timestamp;
import java.util.Date;

public class DefaultEntityInfo {

    public static HibernateEntityInfo getDefaultEntityInfo() {

        Timestamp time = new Timestamp(new Date().getTime());

        return HibernateEntityInfo.builder()
                .creationDate(time)
                .modificationDate(time)
                .build();
    }
}
