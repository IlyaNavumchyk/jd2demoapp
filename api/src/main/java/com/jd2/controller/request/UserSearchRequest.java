package com.jd2.controller.request;

import lombok.Data;

@Data
public class UserSearchRequest {

    private String limit;

    private String offset;

    private String searchQuery;

}
