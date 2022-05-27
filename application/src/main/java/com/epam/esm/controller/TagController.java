package com.epam.esm.controller;

import com.epam.esm.util.BaseResponse;
import com.epam.esm.dto.TagRequestDto;
import com.epam.esm.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/tags")
public class TagController {
    private final TagService tagService;

    @Autowired
    public TagController(TagService tagService) {
        this.tagService = tagService;
    }


    @GetMapping("")
    public ResponseEntity<?> getAll() {
        BaseResponse success =
                new BaseResponse(HttpStatus.OK.value(), "success", tagService.getAll());
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(success);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> get(@PathVariable("id") Long id) {
        BaseResponse success =
                new BaseResponse(HttpStatus.OK.value(), "success", tagService.get(id));
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(success);
    }

    @PostMapping("")
    public ResponseEntity<?> create(@Valid @RequestBody TagRequestDto tagRequestDto, BindingResult bindingResult) {
        BaseResponse success =
                new BaseResponse(HttpStatus.CREATED.value(), "success", tagService.create(tagRequestDto, bindingResult));
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(success);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Long id) {
        tagService.delete(id);
        BaseResponse success = new BaseResponse(HttpStatus.OK.value(), "success");
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(success);
    }
}
