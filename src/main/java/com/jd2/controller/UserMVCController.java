package com.jd2.controller;

import com.jd2.controller.request.UserCreateRequest;
import com.jd2.controller.request.UserSearchRequest;
import com.jd2.domain.User;
import com.jd2.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserMVCController {

    private final UserService userService;

    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView findAllUsers() {
        List<User> users = userService.findAll();

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("user", "NAUM");
        modelAndView.addObject("users", users);
        modelAndView.setViewName("users");

        return modelAndView;
    }

    @GetMapping("/{id}")
    public ModelAndView findUserById(@PathVariable("id") String id) {

        Long userId = Long.parseLong(id);
        User user = userService.findById(userId);

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("id", user.getId());
        modelAndView.addObject("user", user);
        modelAndView.setViewName("user");

        return modelAndView;
    }

    @GetMapping("/search")
    public ModelAndView searchUsers(@ModelAttribute UserSearchRequest userSearchRequest) {

        int verifiedLimit = Integer.parseInt(userSearchRequest.getLimit());
        int verifiedOffset = Integer.parseInt(userSearchRequest.getOffset());

        List<User> users = userService.search(verifiedLimit, verifiedOffset);

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("user", "Users");
        modelAndView.addObject("users", users);
        modelAndView.setViewName("users");

        return modelAndView;
    }

    @PostMapping
    //Jackson
    public ModelAndView createUser(@RequestBody UserCreateRequest createRequest) {

        User user = new User();
        user.setUserName(createRequest.getUserName());
        user.setSurname(createRequest.getSurname());
        user.setBirth(new Timestamp(new Date().getTime()));
        user.setIsDeleted(false);
        user.setCreationDate(new Timestamp(new Date().getTime()));
        user.setModificationDate(new Timestamp(new Date().getTime()));

        User user1 = userService.create(user);

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("id", user.getId());
        modelAndView.addObject("user", user);
        modelAndView.setViewName("user");

        List<User> users = userService.findAll();

       /* ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("user", "Slava");
        modelAndView.addObject("users", users);
        modelAndView.setViewName("users");*/

        return modelAndView;
    }
}
