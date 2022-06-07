package com.epam.esm.service.impl;

import com.epam.esm.dto.params.PaginationParams;
import com.epam.esm.dto.params.TagParams;
import com.epam.esm.dto.request.TagRequestDto;
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

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static com.epam.esm.utils.ParamsStringProvider.ID_NOT_VALID;
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
        given(validator.isNameValid(tagRequestDto.getName())).willReturn(true);
        given(modelMapper.map(tagRequestDto, TagEntity.class)).willReturn(tagEntity);
        given(tagRepository.merge(tagEntity)).willReturn(tagEntity);

        //when
        TagEntity createdTagEntity = underTest.create(tagRequestDto);

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
        PaginationParams paginationParams = new PaginationParams(15, 1);
        TagParams tagParams = new TagParams("tag", null, paginationParams);

        given(validator.isNameValid(tagParams.getName())).willReturn(true);
        given(tagRepository.findByName(tagParams.getName())).willReturn(tagEntities);

        //when
        List<TagEntity> tagsByName = underTest.findAllTags(tagParams);

        //then
        assertThat(tagsByName).isEqualTo(tagEntities);
        verify(tagRepository, times(1)).findByName(tagParams.getName());
        verify(tagRepository, never()).findTagsByCertificateId(any(), any());
    }

    @Test
    void canFindAllTagsWhenNameIsNotValidGiftCertificateIdValid() {
        //given
        PaginationParams paginationParams = new PaginationParams(15, 1);
        TagParams tagParams = new TagParams(null, 1002, paginationParams);

        PaginationParams pageParams = new PaginationParams(15, 0);

        given(validator.isNumberValid(tagParams.getCertificateId())).willReturn(true);
        given(paginationProvider.getPaginationParams(tagParams.getPaginationParams())).willReturn(pageParams);
        given(tagRepository.findTagsByCertificateId(tagParams.getCertificateId(), pageParams)).willReturn(tagEntities);

        //when
        List<TagEntity> tagsByName = underTest.findAllTags(tagParams);

        //then
        assertThat(tagsByName).isEqualTo(tagEntities);
        verify(tagRepository, times(1)).findTagsByCertificateId(tagParams.getCertificateId(), pageParams);
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