package com.jd2.controller;

import com.jd2.controller.request.UserCreateRequest;
import com.jd2.controller.request.UserSearchRequest;
import com.jd2.domain.Gender;
import com.jd2.domain.hibernate.HibernateUser;
import com.jd2.repository.hibernate.HibernateUserInterface;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Timestamp;
import java.util.Collections;
import java.util.Date;

import static com.jd2.util.DefaultEntityInfo.getDefaultEntityInfo;

@RestController
@RequiredArgsConstructor
@RequestMapping("/hibernate/users")
public class HibernateController {

    private final HibernateUserInterface hibernateUserInterface;

    @GetMapping
    public ResponseEntity<Object> findAllUsers() {
        return new ResponseEntity<>(
                Collections.singletonMap("users", hibernateUserInterface.findAll()),
                HttpStatus.OK
        );
    }

    @GetMapping("search")
    public ResponseEntity<Object> findAllUsers(@ModelAttribute UserSearchRequest userSearchRequest) {

        Integer limit = Integer.parseInt(userSearchRequest.getLimit());
        Integer offset = Integer.parseInt(userSearchRequest.getOffset());

        return new ResponseEntity<>(
                Collections.singletonMap("users", hibernateUserInterface.findAll(limit, offset)),
                HttpStatus.OK
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> findById(@PathVariable("id") String id) {

        Long userId = Long.parseLong(id);

        return new ResponseEntity<>(
                Collections.singletonMap("user", hibernateUserInterface.findById(userId)), HttpStatus.OK
        );
    }

    @PostMapping()
    public ResponseEntity<Object> create(@RequestBody UserCreateRequest userCreateRequest) {

        Long time = new Date().getTime();

        HibernateUser user = HibernateUser.builder()
                .userName(userCreateRequest.getUserName())
                .surname(userCreateRequest.getSurname())
                .birth(new Timestamp(time))
                .isDeleted(false)
                .entityInfo(getDefaultEntityInfo())
                .userLogin(userCreateRequest.getLogin())
                .userPassword("default_password")
                .gender(Gender.NOT_SELECTED)
                .build();

        return new ResponseEntity<>(
                Collections.singletonMap("user", hibernateUserInterface.create(user)),
                HttpStatus.OK
        );
    }
}