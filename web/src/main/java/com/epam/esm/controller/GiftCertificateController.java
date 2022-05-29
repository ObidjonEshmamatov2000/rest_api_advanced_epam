package com.epam.esm.controller;

import com.epam.esm.dto.GiftCertificateRequestDto;
import com.epam.esm.service.GiftCertificateService;
import com.epam.esm.util.BaseResponse;
import com.epam.esm.utils.ParamsStringProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

import static com.epam.esm.utils.ParamsStringProvider.*;

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
        @RequestParam(value = "limit", required = false) Integer limit,
        @RequestParam(value = "offset", required = false) Integer offset
    ) {
        Map<String, Object> params = new HashMap<>();
        params.put(NAME, name);
        params.put(DESCRIPTION, description);
        params.put(TAG_NAMES, tags);
        params.put(SORT_PARAMS, sortParams);
        params.put(LIMIT, limit);
        params.put(OFFSET, offset);

        BaseResponse success =
                new BaseResponse(HttpStatus.OK.value(), SUCCESS_MESSAGE, service.getAll(params));
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(success);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable("id") Long id) {
        BaseResponse success =
                new BaseResponse(HttpStatus.OK.value(), SUCCESS_MESSAGE, service.get(id));
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
        BaseResponse success =
                new BaseResponse(HttpStatus.CREATED.value(), SUCCESS_MESSAGE, service.create(request, bindingResult));
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
        BaseResponse success =
                new BaseResponse(HttpStatus.OK.value(), SUCCESS_MESSAGE, service.update(request, id, bindingResult));
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(success);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Long id) {
        service.delete(id);
        BaseResponse success = new BaseResponse(HttpStatus.OK.value(), SUCCESS_MESSAGE);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(success);
    }
}
