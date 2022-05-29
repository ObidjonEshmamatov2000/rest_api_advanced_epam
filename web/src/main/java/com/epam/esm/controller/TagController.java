package com.epam.esm.controller;

import com.epam.esm.dto.TagRequestDto;
import com.epam.esm.service.TagService;
import com.epam.esm.util.BaseResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

import static com.epam.esm.utils.ParamsStringProvider.SUCCESS_MESSAGE;

@RestController
@RequestMapping("/api/tags")
public class TagController {
    private final TagService tagService;

    @Autowired
    public TagController(TagService tagService) {
        this.tagService = tagService;
    }


    @GetMapping("")
    public ResponseEntity<?> getAll(
            @RequestParam(name = "name", required = false) String name
    ) {
        Map<String, Object> params = new HashMap<>();
        BaseResponse success =
                new BaseResponse(HttpStatus.OK.value(), SUCCESS_MESSAGE, tagService.getAll(params));
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(success);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> get(@PathVariable("id") Long id) {
        BaseResponse success =
                new BaseResponse(HttpStatus.OK.value(), SUCCESS_MESSAGE, tagService.get(id));
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(success);
    }

    @PostMapping("")
    public ResponseEntity<?> create(@Valid @RequestBody TagRequestDto tagRequestDto, BindingResult bindingResult) {
        BaseResponse success =
                new BaseResponse(HttpStatus.CREATED.value(), SUCCESS_MESSAGE, tagService.create(tagRequestDto, bindingResult));
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(success);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Long id) {
        tagService.delete(id);
        BaseResponse success = new BaseResponse(HttpStatus.OK.value(), SUCCESS_MESSAGE);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(success);
    }
}
