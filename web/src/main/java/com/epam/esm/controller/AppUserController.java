package com.epam.esm.controller;

import com.epam.esm.common.BaseResponse;
import com.epam.esm.dto.params.AppUserParams;
import com.epam.esm.dto.params.PaginationParams;
import com.epam.esm.entity.AppUserEntity;
import com.epam.esm.hateos.assembler.AppUserModelAssembler;
import com.epam.esm.service.AppUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.epam.esm.utils.ParamsStringProvider.SUCCESS_MESSAGE;

/**
 * @author Obidjon Eshmamatov
 * @project rest_api_advanced_2
 * @created 31/05/2022 - 4:46 PM
 */
@RestController
@RequestMapping("/api/users")
public class AppUserController {
    private final AppUserService service;
    private final AppUserModelAssembler assembler;

    @Autowired
    public AppUserController(AppUserService service, AppUserModelAssembler assembler) {
        this.service = service;
        this.assembler = assembler;
    }

    @GetMapping("/{id}")
    public ResponseEntity<BaseResponse> getUserById(@PathVariable("id") long id) {
        AppUserEntity userById = service.findUserById(id);
        BaseResponse success = new BaseResponse(
                HttpStatus.OK.value(),
                SUCCESS_MESSAGE,
                assembler.toModel(userById)
        );
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(success);
    }

    @GetMapping("")
    public ResponseEntity<BaseResponse> getAllUsers(
            @RequestParam(name = "name", required = false) String name,
            @RequestParam(name = "email", required = false) String email,
            @RequestParam(value = "pageSize", required = false) Integer pageSize,
            @RequestParam(value = "pageNumber", required = false) Integer pageNumber
    ) {
        PaginationParams paginationParams = new PaginationParams(pageSize, pageNumber);
        AppUserParams appUserParams = new AppUserParams(name, email, paginationParams);
        List<AppUserEntity> allUsers = service.findAllUsers(appUserParams);
        BaseResponse success = new BaseResponse(
                HttpStatus.OK.value(),
                SUCCESS_MESSAGE,
                assembler.toCollectionModels(allUsers)
        );
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(success);
    }
}
