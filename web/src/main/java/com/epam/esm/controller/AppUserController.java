package com.epam.esm.controller;

import com.epam.esm.service.AppUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
public class AppUserController {
    private final AppUserService service;

    @Autowired
    public AppUserController(AppUserService service) {
        this.service = service;
    }
}
