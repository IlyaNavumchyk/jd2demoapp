package com.jd2.controller;

import com.jd2.controller.request.UserCreateRequest;
import com.jd2.controller.request.UserSearchRequest;
import com.jd2.domain.User;
import com.jd2.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import java.sql.Timestamp;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/rest/users")
public class UserRestController {

    private final UserService userService;

    @GetMapping
    public ResponseEntity<Object> findAllUsers() {

        List<User> users = userService.findAll();

        Map<String, Object> model = new HashMap<>();

        model.put("user", "NAUM");
        model.put("users", users);

        return new ResponseEntity<>(model, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> findUserById(@PathVariable("id") String id) {

        Long userId = Long.parseLong(id);

        Map<String, Object> model = new HashMap<>();
        model.put("user", userService.findById(userId));

        return new ResponseEntity<>(model, HttpStatus.OK);
    }

    @GetMapping("/search")
    public ResponseEntity<Object> findUsersWithParams(@ModelAttribute UserSearchRequest userSearchRequest) {

        int limit = Integer.parseInt(userSearchRequest.getLimit());
        int offset = Integer.parseInt(userSearchRequest.getOffset());

        return new ResponseEntity<>(
                Collections.singletonMap("result", userService.findAll(limit, offset)),
                HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Object> createUser(@RequestBody UserCreateRequest createRequest) {

        User user = User
                .builder()
                .userName(createRequest.getUserName())
                .surname(createRequest.getSurname())
                .birth(new Timestamp(new Date().getTime()))
                .isDeleted(false)
                .creationDate(new Timestamp(new Date().getTime()))
                .modificationDate(new Timestamp(new Date().getTime()))
                .build();

        Map<String, Object> model = new HashMap<>();
        model.put("user", userService.create(user));

        return new ResponseEntity<>(model, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteUserById(@PathVariable("id") String id) {

        Long userId = Long.parseLong(id);

        return new ResponseEntity<>(Collections.singletonMap("userId", userService.delete(userId)), HttpStatus.OK);
    }
}
