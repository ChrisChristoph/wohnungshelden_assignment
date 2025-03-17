package de.wohnungshelden.applications.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
public class HelloWorldController {

    @RequestMapping("/")
    public String hello() {
        log.info("Hello World was called!");
        return "Hello World";
    }

}
