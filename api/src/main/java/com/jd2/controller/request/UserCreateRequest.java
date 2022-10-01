package com.jd2.controller.request;

import lombok.Data;

@Data
public class UserCreateRequest {

    private String userName;

    private String surname;

    private String login;
}
