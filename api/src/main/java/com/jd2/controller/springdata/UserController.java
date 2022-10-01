package com.jd2.controller.springdata;

import com.jd2.repository.springdata.SpringDataUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;

@RestController
@RequiredArgsConstructor
@RequestMapping("/rest/data")
public class UserController {

    private final SpringDataUserRepository springDataUserRepository;

    @GetMapping
    public ResponseEntity<Object> findAll() {

        return new ResponseEntity<>(
                Collections.singletonMap(
                        "result",
                        springDataUserRepository.findAll(PageRequest.of(0, 10, Sort.Direction.DESC, "id"))),
                HttpStatus.OK);
    }

    @GetMapping("/security")
    public ResponseEntity<Object> findAllByUserLoginAndUserPassword(@RequestParam("login") String login, @RequestParam("password") String password) {

        return new ResponseEntity<>(
                Collections.singletonMap(
                        "result",
                        springDataUserRepository.findAllByUserLoginAndUserPassword(login, password)),
                HttpStatus.OK);
    }
}
