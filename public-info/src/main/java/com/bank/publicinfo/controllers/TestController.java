package com.bank.publicinfo.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(name = "/")
public class TestController {
    @GetMapping("")
    String getTestString() {
        return "Hello public_info api";
    }
}
