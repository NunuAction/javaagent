package com.example.test.controller;

import com.snow.nu.User;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * created with IDEA
 *
 * @author:huqm
 * @date:2020/6/10
 * @time:19:44 <p>
 *
 * </p>
 */
@RestController
@RequestMapping("test")
public class TestContronller {

    @RequestMapping("test")
    public String test(){
        User user =new User();
        user.setName("guahao");
        user.setAge(10);
        System.out.println(user.toString());
        return user.toString();
    }
}
