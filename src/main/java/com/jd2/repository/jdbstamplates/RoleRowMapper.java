package com.jd2.repository.jdbstamplates;

import com.jd2.domain.Role;
import org.apache.log4j.Logger;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

import static com.jd2.repository.role.RoleTableColums.CREATION_DATE;
import static com.jd2.repository.role.RoleTableColums.ID;
import static com.jd2.repository.role.RoleTableColums.MODIFICATION_DATE;
import static com.jd2.repository.role.RoleTableColums.ROLE_NAME;

@Component
public class RoleRowMapper implements RowMapper<Role> {

    private static final Logger LOG = Logger.getLogger(UserRowMapper.class);

    @Override
    public Role mapRow(ResultSet rs, int rowNum) throws SQLException {
        LOG.info("Role row mapping start");

        Role role = Role.builder()
                .id(rs.getInt(ID))
                .roleName(rs.getString(ROLE_NAME))
                .creationDate(rs.getTimestamp(CREATION_DATE))
                .modificationDate(rs.getTimestamp(MODIFICATION_DATE))
                .build();

        LOG.info("Role row mapping end");

        return role;
    }
}
