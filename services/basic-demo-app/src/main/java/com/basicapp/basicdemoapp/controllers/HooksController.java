package com.basicapp.basicdemoapp.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

@Slf4j
@Controller
public class HooksController {

    @PostMapping("/post-install")
    @ResponseStatus(HttpStatus.OK)
    public void postInstallation() {
        log.info("postInstallation hook was called");
    }
}
