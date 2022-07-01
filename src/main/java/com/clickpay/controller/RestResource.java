package com.clickpay.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/rest-resource")
public class RestResource {

    @PostMapping("/hello")
    public ResponseEntity profile() {
        return ResponseEntity.ok("Hello without security.");
    }
}