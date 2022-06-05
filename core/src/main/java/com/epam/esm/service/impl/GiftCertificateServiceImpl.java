package com.epam.esm.service.impl;

import com.epam.esm.dto.GiftCertificateRequestDto;
import com.epam.esm.dto.TagRequestDto;
import com.epam.esm.entity.GiftCertificateEntity;
import com.epam.esm.entity.TagEntity;
import com.epam.esm.exception.ApplicationDuplicateDataException;
import com.epam.esm.exception.ApplicationNotFoundException;
import com.epam.esm.exception.ApplicationNotValidDataException;
import com.epam.esm.repository.GiftCertificateRepository;
import com.epam.esm.service.GiftCertificateService;
import com.epam.esm.service.TagService;
import com.epam.esm.utils.ApplicationValidator;
import com.epam.esm.utils.PaginationProvider;
import org.modelmapper.Conditions;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;

import static com.epam.esm.utils.ParamsStringProvider.*;

/**
 * @author Obidjon Eshmamatov
 * @project rest_api_advanced_2
 * @created 31/05/2022 - 4:46 PM
 */

@Service
public class GiftCertificateServiceImpl implements GiftCertificateService {
    private final GiftCertificateRepository repository;
    private final ApplicationValidator validator;
    private final ModelMapper modelMapper;
    private final TagService tagService;
    private final PaginationProvider paginationProvider;

    @Autowired
    public GiftCertificateServiceImpl(
            GiftCertificateRepository repository,
            ApplicationValidator validator,
            ModelMapper modelMapper,
            TagService tagService,
            PaginationProvider paginationProvider
    ) {
        this.repository = repository;
        this.validator = validator;
        this.modelMapper = modelMapper;
        this.tagService = tagService;
        this.paginationProvider = paginationProvider;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public GiftCertificateEntity create(GiftCertificateRequestDto request) {
        checkIfModelValidForCreating(request);
        GiftCertificateEntity entity = modelMapper.map(request, GiftCertificateEntity.class);
        entity.setCreateDate(getCurrentTime());
        entity.setLastUpdateDate(getCurrentTime());
        entity.setTags(checkIfTagExist(entity.getTags()));
        return repository.merge(entity);
    }

    private void checkIfModelValidForCreating(GiftCertificateRequestDto request) {
        checkIfModelValid(request);
        if (!validator.isNameValid(request.getName())
                || !validator.isDescriptionValid(request.getDescription())
        ) {
            throw new ApplicationNotValidDataException(CERTIFICATE_NAME_OR_DESC_NOT_ACCEPTABLE, request);
        }
        List<GiftCertificateEntity> byName = repository.findByName(request.getName());
        if (!byName.isEmpty()) {
            throw new ApplicationDuplicateDataException(GIFT_CERTIFICATE_ALREADY_EXIST, request);
        }
    }

    private Set<TagEntity> checkIfTagExist(Set<TagEntity> tags) {
        Set<TagEntity> tagEntities = new HashSet<>();
        if (tags == null || tags.size() == 0) return tagEntities;
        tags.forEach(tag -> {
            List<TagEntity> tagsByName = tagService.findTagsByName(tag.getName());
            if (tagsByName.isEmpty()) {
                tagEntities.add(tag);
            } else {
                tagEntities.add(tagsByName.get(0));
            }
        });
        return tagEntities;
    }

    @Override
    public GiftCertificateEntity findById(Long id) {
        if (!validator.isNumberValid(id)) {
            throw new ApplicationNotValidDataException(ID_NOT_VALID, id);
        }
        return Optional.ofNullable(repository.findById(id))
                .orElseThrow(() -> new ApplicationNotFoundException(GIFT_CERTIFICATE_NOT_FOUND, id));
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void deleteById(Long id) {
        if (!validator.isNumberValid(id)) {
            throw new ApplicationNotValidDataException(ID_NOT_VALID, id);
        }
        repository.deleteById(id);
    }

    @Override
    public List<GiftCertificateEntity> findAll(Map<String, Object> params) {
        List<GiftCertificateEntity> all;
        String name = (String) params.get(NAME);
        String description = (String) params.get(DESCRIPTION);
        String tagNames = (String) params.get(TAG_NAMES);

        if (validator.isNameValid(name)) {
            all = repository.findAllFilteredAndSortedByName(
                    name,
                    paginationProvider.getPaginationParam(params),
                    getValidSortingString(params)
            );
        } else if (validator.isDescriptionValid(description)) {
            all = repository.findAllFilteredAndSortedByDescription(
                    description,
                    paginationProvider.getPaginationParam(params),
                    getValidSortingString(params)
            );
        } else if (tagNames != null) {
            all = repository.findAllFilteredAndSortedByTagNames(
                    buildValidTagNameList(tagNames),
                    paginationProvider.getPaginationParam(params),
                    getValidSortingString(params)
            );
        } else {
            all = repository.findAllFilteredAndSorted(
                    paginationProvider.getPaginationParam(params),
                    getValidSortingString(params)
            );
        }
        return all;
    }

    private List<String> buildValidTagNameList(String tagNames) {
        List<String> tagNameList = new ArrayList<>();
        for (String s: tagNames.split(", ")) {
            if (validator.isNameValid(s)) {
                tagNameList.add(s);
            }
        }
        return tagNameList;
    }

    private String getValidSortingString(Map<String, Object> params) {
        StringBuilder builder = new StringBuilder();
        String sortParams = (String) params.get(SORT_PARAMS);  // id desc
        if (sortParams != null) {
            String[] sortArray = sortParams.split(", ");
            for (int i=0; i < sortArray.length; i++) {
                String param = sortArray[i];
                if (checkIfSortingParamValid(param)) {
                    if (i != 0) {
                        builder.append(", ").append(param);
                    } else builder.append(param);
                }
            }
        }
        return builder.toString();
    }

    public boolean checkIfSortingParamValid(String sortParam) {
        List<String> gift_certificate_params = new ArrayList<>();
        gift_certificate_params.add(NAME);
        gift_certificate_params.add(DESCRIPTION);
        gift_certificate_params.add(PRICE);
        gift_certificate_params.add(DURATION);
        gift_certificate_params.add(CREATE_DATE);
        gift_certificate_params.add(LAST_UPDATE_DATE);

        return sortParam != null &&
                gift_certificate_params.stream().anyMatch(sortParam::contains) &&
                (sortParam.contains(ASC) || sortParam.contains(DESC));
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public GiftCertificateEntity update(
            GiftCertificateRequestDto update,
            Long certificateId
    ) {
        GiftCertificateEntity old = checkIfModelValidForUpdatingAndGetOld(update, certificateId);
        Set<TagEntity> tagEntities = old.getTags();

        Set<TagRequestDto> updateTags = update.getTags();
        update.setTags(null);
        modelMapper.getConfiguration().setPropertyCondition(Conditions.isNotNull());
        modelMapper.map(update, old);
        if (updateTags != null && updateTags.size() != 0) {
            updateTags.forEach(tag -> {
                List<TagEntity> tagsByName = tagService.findTagsByName(tag.getName());
                if (!tagsByName.isEmpty()) {
                    tagEntities.add(tagsByName.get(0));
                } else {
                    tagEntities.add(new TagEntity(tag.getName(), getCurrentTime(), null));
                }
            });
        }
        old.setTags(tagEntities);
        old.setLastUpdateDate(getCurrentTime());
        return repository.merge(old);
    }

    private GiftCertificateEntity checkIfModelValidForUpdatingAndGetOld(
            GiftCertificateRequestDto update,
            Long certificateId
    ) {
        if (!validator.isNumberValid(certificateId)) {
            throw new ApplicationNotValidDataException(ID_NOT_VALID, certificateId);
        }
        if (update.getName() != null && !validator.isNameValid(update.getName())) {
            throw new ApplicationNotValidDataException(NAME_NOT_VALID, update.getName());
        }
        if (update.getDescription() != null && !validator.isDescriptionValid(update.getDescription())) {
            throw new ApplicationNotValidDataException(NAME_NOT_VALID, update.getDescription());
        }
        checkIfModelValid(update);
        GiftCertificateEntity old = repository.findById(certificateId);
        if (old == null) {
            throw new ApplicationNotFoundException(GIFT_CERTIFICATE_NOT_FOUND, update);
        }
        return old;
    }

    private void checkIfModelValid(GiftCertificateRequestDto request) {
        if (request.getTags() != null) {
            request.getTags().forEach(tag -> {
                if (!validator.isNameValid(tag.getName())) {
                    throw new ApplicationNotValidDataException(NAME_NOT_VALID, tag.getName());
                }
            });
        }
    }

    private LocalDateTime getCurrentTime() {
        return LocalDateTime.now(ZoneId.systemDefault());
    }
}
