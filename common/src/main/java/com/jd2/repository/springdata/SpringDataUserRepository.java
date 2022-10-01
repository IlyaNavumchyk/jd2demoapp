package com.jd2.repository.springdata;

import com.jd2.domain.hibernate.HibernateUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.nio.file.LinkOption;

public interface SpringDataUserRepository extends JpaRepository<HibernateUser, LinkOption> {

    HibernateUser findAllByUserLoginAndUserPassword(String login, String password);


}
