package com.epam.esm.controller;

import com.epam.esm.entity.AppUserEntity;
import com.epam.esm.service.AppUserService;
import com.epam.esm.common.BaseResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.epam.esm.utils.ParamsStringProvider.*;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

/**
 * @author Obidjon Eshmamatov
 * @project rest_api_advanced_2
 * @created 31/05/2022 - 4:46 PM
 */

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
        AppUserEntity userById = service.findUserById(id);
        addLinks(userById);
        BaseResponse success = new BaseResponse(HttpStatus.OK.value(), SUCCESS_MESSAGE, userById);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(success);
    }

    @GetMapping("")
    public ResponseEntity<?> getAllUsers(
            @RequestParam(name = "name", required = false) String name,
            @RequestParam(name = "email", required = false) String email,
            @RequestParam(value = "limit", required = false) Integer limit,
            @RequestParam(value = "offset", required = false) Integer offset
    ) {
        Map<String, Object> params = new HashMap<>();
        params.put(NAME, name);
        params.put(EMAIL, email);
        params.put(LIMIT, limit);
        params.put(OFFSET, offset);
        List<AppUserEntity> allUsers = service.findAllUsers(params);
        allUsers.forEach(this::addLinks);
        BaseResponse success = new BaseResponse(HttpStatus.OK.value(), SUCCESS_MESSAGE, allUsers);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(success);
    }

    private void addLinks(AppUserEntity appUser) {
        if (appUser != null)
            appUser.add(linkTo(methodOn(AppUserController.class).getUserById(appUser.getId())).withSelfRel());
    }
}
