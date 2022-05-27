package com.epam.esm.controller;

import com.epam.esm.service.AppUserService;
import com.epam.esm.util.BaseResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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

    @GetMapping("/{id}")
    public ResponseEntity<?> getUserById(@PathVariable("id") long id) {
        BaseResponse success = new BaseResponse(HttpStatus.OK.value(), "success", service.getUserById(id));
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(success);
    }

    @GetMapping("")
    public ResponseEntity<?> getAllUsers() {
        BaseResponse success = new BaseResponse(HttpStatus.OK.value(), "success", service.getAllUsers());
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(success);
    }
}
