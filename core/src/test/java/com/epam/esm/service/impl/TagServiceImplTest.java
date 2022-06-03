package com.epam.esm.service.impl;

import com.epam.esm.dto.TagRequestDto;
import com.epam.esm.entity.TagEntity;
import com.epam.esm.exception.ApplicationNotValidDataException;
import com.epam.esm.repository.TagRepository;
import com.epam.esm.utils.ApplicationValidator;
import com.epam.esm.utils.PaginationProvider;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.epam.esm.utils.ParamsStringProvider.*;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

/**
 * @author Obidjon Eshmamatov
 * @project rest_api_advanced_2
 * @created 02/06/2022 - 12:37 PM
 */

@ExtendWith(MockitoExtension.class)
class TagServiceImplTest {

    @Mock
    private TagRepository tagRepository;

    @Mock
    private ApplicationValidator validator;

    @Mock
    private ModelMapper modelMapper;

    @Mock
    private PaginationProvider paginationProvider;

    private TagServiceImpl underTest;

    private TagEntity tagEntity;
    private TagRequestDto tagRequestDto;
    private List<TagEntity> tagEntities;

    @BeforeEach
    void setUp() {
        underTest = new TagServiceImpl(tagRepository, validator, modelMapper, paginationProvider);
        tagEntity = new TagEntity(1, "tagEntity", LocalDateTime.now());
        tagRequestDto = new TagRequestDto("tagEntity");

        tagEntities = new ArrayList<>();
        TagEntity tagEntity1 = new TagEntity(2, "tag", LocalDateTime.now());
        TagEntity tagEntity2 = new TagEntity(3, "tag", LocalDateTime.now());
        tagEntities.add(tagEntity1);
        tagEntities.add(tagEntity2);
    }

    @Test
    void canCreateTag() {
        //given
        BindingResult bindingResult = new BeanPropertyBindingResult(tagRequestDto, "tagRequestDto");
        given(validator.isNameValid(tagRequestDto.getName())).willReturn(true);
        given(modelMapper.map(tagRequestDto, TagEntity.class)).willReturn(tagEntity);
        given(tagRepository.merge(tagEntity)).willReturn(tagEntity);

        //when
        TagEntity createdTagEntity = underTest.create(tagRequestDto, bindingResult);

        //then
        assertThat(createdTagEntity).isEqualTo(tagEntity);
        verify(tagRepository, times(1)).merge(tagEntity);
    }

    @Test
    void findById() {
        //given
        Long id = 1L;
        given(validator.isNumberValid(id)).willReturn(true);
        given(tagRepository.findById(id)).willReturn(tagEntity);

        //when
        TagEntity tagEntityById = underTest.findById(id);

        //then
        assertThat(tagEntityById).isEqualTo(tagEntity);
        verify(tagRepository, times(1)).findById(id);
    }

    @Test
    void willThrowErrorIfIdNotValidInFinding() {
        //given
        Long id = 1L;
        given(validator.isNumberValid(id)).willReturn(false);

        //when
        //then
        assertThatThrownBy(() -> underTest.findById(id))
                        .isInstanceOf(ApplicationNotValidDataException.class)
                        .hasMessageContaining(ID_NOT_VALID);
        verify(tagRepository, never()).findById(id);
    }

    @Test
    void deleteById() {
        //given
        Long id = 1L;
        given(validator.isNumberValid(id)).willReturn(true);

        //when
        underTest.deleteById(id);

        //then
        verify(tagRepository, times(1)).deleteById(id);
    }

    @Test
    void canFindAllTagsWhenNameIsValid() {
        //given
        Map<String, Object> params = new HashMap<>();
        params.put(LIMIT, 25);
        params.put(OFFSET, 1);
        params.put(NAME, "tag");
        String name = (String) params.get(NAME);
        given(validator.isNameValid(name)).willReturn(true);
        given(tagRepository.findByName(name)).willReturn(tagEntities);

        //when
        List<TagEntity> tagsByName = underTest.findAll(params);

        //then
        assertThat(tagsByName).isEqualTo(tagEntities);
        verify(tagRepository, times(1)).findByName(name);
        verify(tagRepository, never()).findTagsByCertificateId(any(), any());
    }

    @Test
    void canFindAllTagsWhenNameIsNotValidGiftCertificateIdValid() {
        //given
        Map<String, Object> params = new HashMap<>();
        params.put(CERTIFICATE_ID, 11);
        String name = (String) params.get(NAME);
        Integer gift_certificate_id = (Integer) params.get(CERTIFICATE_ID);
        given(validator.isNameValid(name)).willReturn(false);
        given(validator.isNumberValid(gift_certificate_id)).willReturn(true);
        given(tagRepository.findTagsByCertificateId(gift_certificate_id, new HashMap<>())).willReturn(tagEntities);

        //when
        List<TagEntity> tagsByName = underTest.findAll(params);

        //then
        assertThat(tagsByName).isEqualTo(tagEntities);
        verify(tagRepository, times(1)).findTagsByCertificateId(gift_certificate_id, new HashMap<>());
        verify(tagRepository, never()).findByName(any());
    }

    @Test
    void findTagsByName() {
        //given
        String name = "tag";
        given(tagRepository.findByName(name)).willReturn(tagEntities);

        //when
        List<TagEntity> tagsByName = underTest.findTagsByName(name);

        //then
        assertThat(tagsByName).isEqualTo(tagEntities);
        verify(tagRepository, times(1)).findByName(name);
    }

    @Test
    void findMostUsedTagOfUserWithHighestCostOfOrders() {
        //given
        given(tagRepository.findMostUsedTagOfUserWithHighestCostOfOrders()).willReturn(tagEntities);

        //when
        List<TagEntity> mostUsedTagOfUserWithHighestCostOfOrders = underTest.findMostUsedTagOfUserWithHighestCostOfOrders();

        //then
        assertThat(mostUsedTagOfUserWithHighestCostOfOrders).isEqualTo(tagEntities);
        verify(tagRepository, times(1)).findMostUsedTagOfUserWithHighestCostOfOrders();
    }
}