package com.epam.esm.controller;

import com.epam.esm.common.BaseResponse;
import com.epam.esm.dto.params.PaginationParams;
import com.epam.esm.dto.params.TagParams;
import com.epam.esm.dto.request.TagRequestDto;
import com.epam.esm.entity.TagEntity;
import com.epam.esm.exception.ApplicationNotValidDataException;
import com.epam.esm.hateos.assembler.TagModelAssembler;
import com.epam.esm.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

import static com.epam.esm.utils.ParamsStringProvider.SUCCESS_MESSAGE;

/**
 * @author Obidjon Eshmamatov
 * @project rest_api_advanced_2
 * @created 31/05/2022 - 4:46 PM
 */
@RestController
@RequestMapping("/api/tags")
public class TagController {
    private final TagService tagService;
    private final TagModelAssembler assembler;

    @Autowired
    public TagController(TagService tagService, TagModelAssembler tagModelAssembler) {
        this.tagService = tagService;
        this.assembler = tagModelAssembler;
    }

    @GetMapping("")
    public ResponseEntity<BaseResponse> getAll(
            @RequestParam(name = "name", required = false) String name,
            @RequestParam(name = "giftCertificateId", required = false) Integer certificateId,
            @RequestParam(value = "pageSize", required = false) Integer pageSize,
            @RequestParam(value = "pageNumber", required = false) Integer pageNumber
    ) {
        PaginationParams paginationParams = new PaginationParams(pageSize, pageNumber);
        TagParams tagParams = new TagParams(name, certificateId, paginationParams);
        List<TagEntity> entities = tagService.findAllTags(tagParams);
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
    public ResponseEntity<BaseResponse> get(@PathVariable("id") Long id) {
        TagEntity entity = tagService.findById(id);
        BaseResponse success = new BaseResponse(
                HttpStatus.OK.value(),
                SUCCESS_MESSAGE,
                assembler.toModel(entity)
        );
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(success);
    }

    @GetMapping("/mostUsedTag")
    public ResponseEntity<BaseResponse> getMostUsedTagOfUserWithHighestCostOfOrders() {
        List<TagEntity> tags = tagService.findMostUsedTagOfUserWithHighestCostOfOrders();
        BaseResponse success = new BaseResponse(
                HttpStatus.OK.value(),
                SUCCESS_MESSAGE,
                assembler.toCollectionModels(tags)
        );
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(success);
    }

    @PostMapping("")
    public ResponseEntity<BaseResponse> create(
            @Valid @RequestBody TagRequestDto tagRequestDto,
            BindingResult bindingResult
    ) {
        if (bindingResult.hasErrors()) {
            List<FieldError> errors = bindingResult.getFieldErrors();
            throw new ApplicationNotValidDataException(
                    errors.get(0).getDefaultMessage(),
                    bindingResult.getTarget()
            );
        }
        TagEntity entity = tagService.create(tagRequestDto);
        BaseResponse success = new BaseResponse(
                HttpStatus.CREATED.value(),
                SUCCESS_MESSAGE,
                assembler.toModel(entity)
        );
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(success);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<BaseResponse> delete(@PathVariable("id") Long id) {
        tagService.deleteById(id);
        BaseResponse success = new BaseResponse(HttpStatus.OK.value(), SUCCESS_MESSAGE);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(success);
    }
}
