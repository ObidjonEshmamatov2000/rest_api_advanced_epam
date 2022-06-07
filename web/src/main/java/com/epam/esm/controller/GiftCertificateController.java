package com.epam.esm.controller;

import com.epam.esm.hateos.assembler.GiftCertificateModelAssembler;
import com.epam.esm.common.BaseResponse;
import com.epam.esm.dto.request.GiftCertificateRequestDto;
import com.epam.esm.entity.GiftCertificateEntity;
import com.epam.esm.exception.ApplicationNotValidDataException;
import com.epam.esm.service.GiftCertificateService;
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

/**
 * @author Obidjon Eshmamatov
 * @project rest_api_advanced_2
 * @created 31/05/2022 - 4:46 PM
 */
@RestController
@RequestMapping("/api/gift_certificates")
public class GiftCertificateController {

    private final GiftCertificateService service;
    private final GiftCertificateModelAssembler assembler;

    @Autowired
    public GiftCertificateController(
            GiftCertificateService giftCertificateService,
            GiftCertificateModelAssembler assembler
    ) {
        this.service = giftCertificateService;
        this.assembler = assembler;
    }

    @GetMapping("")
    public ResponseEntity<BaseResponse> getAll(
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
        BaseResponse success = new BaseResponse(
                HttpStatus.OK.value(),
                SUCCESS_MESSAGE,
                assembler.toCollectionModels(entities)
        );
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(success);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BaseResponse> getById(@PathVariable("id") Long id) {
        GiftCertificateEntity entity = service.findById(id);
        BaseResponse success = new BaseResponse(
                HttpStatus.OK.value(),
                SUCCESS_MESSAGE,
                assembler.toModel(entity)
        );
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(success);
    }

    @PostMapping
    public ResponseEntity<BaseResponse> create(
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
        BaseResponse success = new BaseResponse(
                HttpStatus.CREATED.value(),
                SUCCESS_MESSAGE,
                assembler.toModel(entity)
        );
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(success);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<BaseResponse> update(
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
        BaseResponse success = new BaseResponse(
                HttpStatus.OK.value(),
                SUCCESS_MESSAGE,
                assembler.toModel(update)
        );
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(success);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<BaseResponse> delete(@PathVariable("id") Long id) {
        service.deleteById(id);
        BaseResponse success = new BaseResponse(HttpStatus.OK.value(), SUCCESS_MESSAGE);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(success);
    }
}
