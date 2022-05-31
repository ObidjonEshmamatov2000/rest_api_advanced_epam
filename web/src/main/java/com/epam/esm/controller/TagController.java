package com.epam.esm.controller;

import com.epam.esm.dto.TagRequestDto;
import com.epam.esm.entity.TagEntity;
import com.epam.esm.service.TagService;
import com.epam.esm.common.BaseResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
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
@RequestMapping("/api/tags")
public class TagController {
    private final TagService tagService;

    @Autowired
    public TagController(TagService tagService) {
        this.tagService = tagService;
    }


    @GetMapping("")
    public ResponseEntity<?> getAll(
            @RequestParam(name = "name", required = false) String name,
            @RequestParam(name = "giftCertificateId", required = false) Integer certificateId,
            @RequestParam(value = "limit", required = false) Integer limit,
            @RequestParam(value = "offset", required = false) Integer offset
    ) {
        Map<String, Object> params = new HashMap<>();
        params.put(NAME, name);
        params.put(CERTIFICATE_ID, certificateId);
        params.put(LIMIT, limit);
        params.put(OFFSET, offset);

        List<TagEntity> entities = tagService.findAll(params);
        entities.forEach(this::addLinks);
        BaseResponse success = new BaseResponse(HttpStatus.OK.value(), SUCCESS_MESSAGE, entities);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(success);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> get(@PathVariable("id") Long id) {
        TagEntity entity = tagService.findById(id);
        addLinks(entity);
        BaseResponse success = new BaseResponse(HttpStatus.OK.value(), SUCCESS_MESSAGE, entity);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(success);
    }

    @GetMapping("/mostUsedTag")
    public ResponseEntity<?> getMostUsedTagOfUserWithHighestCostOfOrders() {
        List<TagEntity> tags = tagService.findMostUsedTagOfUserWithHighestCostOfOrders();
        tags.forEach(this::addLinks);
        BaseResponse success = new BaseResponse(HttpStatus.OK.value(), SUCCESS_MESSAGE, tags);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(success);
    }

    @PostMapping("")
    public ResponseEntity<?> create(
            @Valid @RequestBody TagRequestDto tagRequestDto,
            BindingResult bindingResult
    ) {
        TagEntity entity = tagService.create(tagRequestDto, bindingResult);
        addLinks(entity);
        BaseResponse success = new BaseResponse(HttpStatus.CREATED.value(), SUCCESS_MESSAGE, entity);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(success);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Long id) {
        tagService.deleteById(id);
        BaseResponse success = new BaseResponse(HttpStatus.OK.value(), SUCCESS_MESSAGE);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(success);
    }

    public void addLinks(TagEntity tag) {
        if (tag != null)
            tag.add(linkTo(methodOn(TagController.class).get(tag.getId())).withSelfRel());
    }
}
