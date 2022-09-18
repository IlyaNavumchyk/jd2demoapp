package com.jd2.controller.request;

import lombok.Data;

@Data
public class AuthRequest {

    private String login;

    private String password;
}