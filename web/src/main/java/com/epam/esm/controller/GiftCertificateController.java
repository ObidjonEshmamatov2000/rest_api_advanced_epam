package com.epam.esm.controller;

import com.epam.esm.dto.GiftCertificateRequestDto;
import com.epam.esm.entity.GiftCertificateEntity;
import com.epam.esm.exception.ApplicationNotValidDataException;
import com.epam.esm.service.GiftCertificateService;
import com.epam.esm.common.BaseResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
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
@RequestMapping("/api/gift_certificates")
public class GiftCertificateController {

    private final GiftCertificateService service;

    @Autowired
    public GiftCertificateController(GiftCertificateService giftCertificateService) {
        this.service = giftCertificateService;
    }

    @GetMapping("")
    public ResponseEntity<?> getAll(
        @RequestParam(value = "name", required = false) String name,
        @RequestParam(value = "description", required = false) String description,
        @RequestParam(value = "tags", required = false) String tags,
        @RequestParam(value = "sort", required = false) String sortParams,
        @RequestParam(value = "pageSize", required = false) Integer pageSize,
        @RequestParam(value = "pageNumber", required = false) Integer pageNumber
    ) {
        Map<String, Object> params = new HashMap<>();
        params.put(NAME, name);
        params.put(DESCRIPTION, description);
        params.put(TAG_NAMES, tags);
        params.put(SORT_PARAMS, sortParams);
        params.put(PAGE_SIZE, pageSize);
        params.put(PAGE_NUMBER, pageNumber);

        List<GiftCertificateEntity> entities = service.findAll(params);
        entities.forEach(this::addLinks);
        BaseResponse success = new BaseResponse(HttpStatus.OK.value(), SUCCESS_MESSAGE, entities);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(success);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable("id") Long id) {
        GiftCertificateEntity entity = service.findById(id);
        addLinks(entity);
        BaseResponse success = new BaseResponse(HttpStatus.OK.value(), SUCCESS_MESSAGE, entity);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(success);
    }

    @PostMapping
    public ResponseEntity<?> create(
            @Valid
            @RequestBody GiftCertificateRequestDto request,
            BindingResult bindingResult
    ) {
        if (bindingResult.hasErrors()) {
            List<FieldError> errors = bindingResult.getFieldErrors();
            throw new ApplicationNotValidDataException(
                    errors.get(0).getDefaultMessage(),
                    bindingResult.getTarget()
            );
        }
        GiftCertificateEntity entity = service.create(request);
        addLinks(entity);
        BaseResponse success = new BaseResponse(HttpStatus.CREATED.value(), SUCCESS_MESSAGE, entity);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(success);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> update(
            @Valid
            @RequestBody GiftCertificateRequestDto request,
            BindingResult bindingResult,
            @PathVariable(value = "id") Long id
    ) {
        if (bindingResult.hasErrors()) {
            List<FieldError> errors = bindingResult.getFieldErrors();
            throw new ApplicationNotValidDataException(
                    errors.get(0).getDefaultMessage(),
                    bindingResult.getTarget()
            );
        }

        GiftCertificateEntity update = service.update(request, id);
        addLinks(update);
        BaseResponse success = new BaseResponse(HttpStatus.OK.value(), SUCCESS_MESSAGE, update);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(success);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Long id) {
        service.deleteById(id);
        BaseResponse success = new BaseResponse(HttpStatus.OK.value(), SUCCESS_MESSAGE);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(success);
    }

    private void addLinks(GiftCertificateEntity giftCertificate) {
        if (giftCertificate != null) {
            giftCertificate.add(linkTo(methodOn(GiftCertificateController.class)
                    .getById(giftCertificate.getId())).withSelfRel());
            if (giftCertificate.getTags() != null) {
                giftCertificate.getTags().forEach(tag -> {
                    if(tag.getLinks().hasSize(0))
                            tag.add(linkTo(methodOn(TagController.class).get(tag.getId())).withSelfRel());
                });
            }
        }
    }
}
