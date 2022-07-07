package com.clickpay.controller.test;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/test")
public class TestController {

    @GetMapping("/")
    public ResponseEntity test() {
        return ResponseEntity.ok("Permitted api with basic auth.");
    }
}
