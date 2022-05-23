package com.epam.esm.controller;

import com.epam.esm.dto.GiftCertificateRequestDto;
import com.epam.esm.service.GiftCertificateService;
import com.epam.esm.util.BaseResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/gift_certificates")
public class GiftCertificateController {

    private final GiftCertificateService service;

    @Autowired
    public GiftCertificateController(GiftCertificateService giftCertificateService) {
        this.service = giftCertificateService;
    }

    @GetMapping("")
    public ResponseEntity<?> getAll() {
        BaseResponse success =
                new BaseResponse(HttpStatus.OK.value(), "success", null);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(success);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable("id") Long id) {
        BaseResponse success =
                new BaseResponse(HttpStatus.OK.value(), "success", service.get(id));
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(success);
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody GiftCertificateRequestDto request) {
        BaseResponse success = new BaseResponse(HttpStatus.CREATED.value(), "success", service.create(request));
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(success);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Long id) {
        service.delete(id);
        BaseResponse success = new BaseResponse(HttpStatus.OK.value(), "success");
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(success);
    }

    @PutMapping
    public ResponseEntity<?> update(
            @RequestBody GiftCertificateRequestDto request,
            @RequestParam(value = "id") Long id
    ) {
        BaseResponse success =
                new BaseResponse(HttpStatus.OK.value(), "success", service.update(request, id));
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(success);
    }
}
