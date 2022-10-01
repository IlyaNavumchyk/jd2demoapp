package com.jd2.domain.hibernate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.sql.Timestamp;
import java.util.Date;

@Embeddable
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class HibernateEntityInfo {

    //@Column(name = "creation_date")
    private Timestamp creationDate;

    //@Column(name = "modification_date")
    private Timestamp modificationDate;
}
