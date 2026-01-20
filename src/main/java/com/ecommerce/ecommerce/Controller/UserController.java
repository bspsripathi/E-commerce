package com.ecommerce.ecommerce.Controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("/api")
public class UserController {

    @GetMapping("/work")
    public String working(){
        return "Working";
    }

}
