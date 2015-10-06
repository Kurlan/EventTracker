package com.eventtracker.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.eventtracker.model.User;
import com.eventtracker.utils.UserUtils;

@Controller
@RequestMapping("/user")
public class UserController {

    private final UserUtils userUtils;

    @Autowired
    public UserController(UserUtils userUtils) {
        this.userUtils = userUtils;
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    @ResponseBody
    public User createUser(@RequestParam("name") String name, @RequestParam("email") String email) {
        return userUtils.createUser(name, email);
    }
}
