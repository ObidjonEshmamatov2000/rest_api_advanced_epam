package com.epam.esm.service.impl;

import com.epam.esm.dto.params.GiftCertificateParams;
import com.epam.esm.dto.params.PaginationParams;
import com.epam.esm.dto.request.GiftCertificateRequestDto;
import com.epam.esm.entity.GiftCertificateEntity;
import com.epam.esm.exception.ApplicationNotValidDataException;
import com.epam.esm.repository.GiftCertificateRepository;
import com.epam.esm.service.TagService;
import com.epam.esm.utils.ApplicationValidator;
import com.epam.esm.utils.PaginationProvider;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.modelmapper.internal.InheritingConfiguration;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static com.epam.esm.utils.ParamsStringProvider.ID_NOT_VALID;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

/**
 * @author Obidjon Eshmamatov
 * @project rest_api_advanced_2
 * @created 02/06/2022 - 6:47 PM
 */

@ExtendWith(MockitoExtension.class)
class GiftCertificateServiceImplTest {
    @Mock
    private GiftCertificateRepository giftCertificateRepository;

    @Mock
    private ApplicationValidator validator;

    @Mock
    private ModelMapper modelMapper;

    @Mock
    private PaginationProvider paginationProvider;

    @Mock
    private TagService tagService;

    private GiftCertificateServiceImpl underTest;

    private GiftCertificateEntity giftCertificateEntity;
    private GiftCertificateRequestDto giftCertificateRequestDto;
    private List<GiftCertificateEntity> giftCertificateEntities;

    @BeforeEach
    void setUp() {
        underTest = new GiftCertificateServiceImpl(
                giftCertificateRepository,
                validator,
                modelMapper,
                tagService,
                paginationProvider
        );

        giftCertificateEntity = new GiftCertificateEntity(
                3L,
                "gift",
                "nice",
                BigDecimal.valueOf(12),
                13,
                LocalDateTime.now(),
                LocalDateTime.now(), null
        );

        giftCertificateRequestDto = new GiftCertificateRequestDto(
                "gift",
                "nice",
                BigDecimal.valueOf(12),
                13,
                null
        );

        GiftCertificateEntity giftCertificateEntity1 = new GiftCertificateEntity(
                1L,
                "gift1",
                "nice1",
                BigDecimal.valueOf(1),
                1,
                LocalDateTime.now(),
                LocalDateTime.now(), null
        );
        GiftCertificateEntity giftCertificateEntity2 = new GiftCertificateEntity(
                2L,
                "gift2",
                "nice2",
                BigDecimal.valueOf(2),
                2,
                LocalDateTime.now(),
                LocalDateTime.now(),
                null
        );
        giftCertificateEntities = new ArrayList<>();
        giftCertificateEntities.add(giftCertificateEntity1);
        giftCertificateEntities.add(giftCertificateEntity2);
    }

    @Test
    void canCreateGiftCertificate() {
        //given
        given(validator.isNameValid(giftCertificateEntity.getName())).willReturn(true);
        given(validator.isDescriptionValid(giftCertificateEntity.getDescription())).willReturn(true);
        given(modelMapper.map(giftCertificateRequestDto, GiftCertificateEntity.class)).willReturn(giftCertificateEntity);
        given(giftCertificateRepository.merge(giftCertificateEntity)).willReturn(giftCertificateEntity);
        given(giftCertificateRepository.findByName(giftCertificateRequestDto.getName())).willReturn(new ArrayList<>());

        //when
        GiftCertificateEntity createdEntity = underTest.create(giftCertificateRequestDto);

        //then
        assertThat(createdEntity).isEqualTo(giftCertificateEntity);
        verify(giftCertificateRepository, times(1)).merge(giftCertificateEntity);
    }

    @Test
    void canFindById() {
        //given
        long id = 3L;
        given(validator.isNumberValid(id)).willReturn(true);
        given(giftCertificateRepository.findById(id)).willReturn(giftCertificateEntity);

        //when
        GiftCertificateEntity byId = underTest.findById(id);

        //then
        assertThat(byId).isEqualTo(giftCertificateEntity);
        verify(giftCertificateRepository, times(1)).findById(id);
    }

    @Test
    void willThrowErrorIfIdNotValidDuringFinding() {
        //given
        long id = 3L;
        given(validator.isNumberValid(id)).willReturn(false);

        //when
        //then
        assertThatThrownBy(() -> underTest.findById(id))
                    .isInstanceOf(ApplicationNotValidDataException.class)
                        .hasMessageContaining(ID_NOT_VALID);
        verify(giftCertificateRepository, never()).findById(id);
    }

    @Test
    void canDeleteById() {
        //given
        long id = 3L;
        given(validator.isNumberValid(id)).willReturn(true);

        //when
        underTest.deleteById(id);

        //then
        verify(giftCertificateRepository, times(1)).deleteById(id);
    }

    @Test
    void willThrowErrorIfIdNotValidDuringDeleting() {
        //given
        long id = 3L;
        given(validator.isNumberValid(id)).willReturn(false);

        //when
        //then
        assertThatThrownBy(() -> underTest.deleteById(id))
                .isInstanceOf(ApplicationNotValidDataException.class)
                .hasMessageContaining(ID_NOT_VALID);
        verify(giftCertificateRepository, never()).deleteById(id);
    }

    @Test
    void canFindAllGiftCertificatesWhenNameValid() {
        //given
        PaginationParams paginationParams = new PaginationParams(15, 1);
        GiftCertificateParams gcParams = new GiftCertificateParams("gift", null, null, "name asc", paginationParams);
        PaginationParams pageParams = new PaginationParams(15, 0);

        given(validator.isNameValid(gcParams.getName())).willReturn(true);
        given(paginationProvider.getPaginationParams(gcParams.getPaginationParams())).willReturn(pageParams);
        given(giftCertificateRepository.findAllFilteredAndSortedByName(gcParams.getName(), pageParams, gcParams.getSortParams()))
                .willReturn(giftCertificateEntities);

        //when
        List<GiftCertificateEntity> sortedAndFiltered = underTest.findAllGiftCertificates(gcParams);

        //then
        assertThat(sortedAndFiltered).isEqualTo(giftCertificateEntities);
        verify(giftCertificateRepository, times(1))
                .findAllFilteredAndSortedByName(gcParams.getName(), pageParams, gcParams.getSortParams());
    }

    @Test
    void canFindAllGiftCertificatesWhenNameNotValidDescValid() {
        //given
        PaginationParams paginationParams = new PaginationParams(15, 1);
        GiftCertificateParams gcParams = new GiftCertificateParams(null, "desc", null, "name asc", paginationParams);
        PaginationParams pageParams = new PaginationParams(15, 0);

        given(validator.isDescriptionValid(gcParams.getDescription())).willReturn(true);
        given(paginationProvider.getPaginationParams(gcParams.getPaginationParams())).willReturn(pageParams);
        given(giftCertificateRepository.findAllFilteredAndSortedByDescription(gcParams.getDescription(), pageParams, gcParams.getSortParams()))
                .willReturn(giftCertificateEntities);

        //when
        List<GiftCertificateEntity> sortedAndFiltered = underTest.findAllGiftCertificates(gcParams);

        //then
        assertThat(sortedAndFiltered).isEqualTo(giftCertificateEntities);
        verify(giftCertificateRepository, never())
                .findAllFilteredAndSortedByName(any(), any(), any());
        verify(giftCertificateRepository, times(1))
                .findAllFilteredAndSortedByDescription(gcParams.getDescription(), pageParams, gcParams.getSortParams());
    }

    @Test
    void canFindAllGiftCertificatesWhenNameNotValidDescNotValidTagNameValid() {
        //given
        PaginationParams paginationParams = new PaginationParams(15, 1);
        GiftCertificateParams gcParams = new GiftCertificateParams(null, null, "tag1, tag2", "name asc", paginationParams);
        PaginationParams pageParams = new PaginationParams(15, 0);

        given(paginationProvider.getPaginationParams(gcParams.getPaginationParams())).willReturn(pageParams);
        given(giftCertificateRepository.findAllFilteredAndSortedByTagNames(new ArrayList<>(), pageParams, gcParams.getSortParams()))
                .willReturn(giftCertificateEntities);

        //when
        List<GiftCertificateEntity> sortedAndFiltered = underTest.findAllGiftCertificates(gcParams);

        //then
        assertThat(sortedAndFiltered).isEqualTo(giftCertificateEntities);
        verify(giftCertificateRepository, never())
                .findAllFilteredAndSortedByName(any(), any(), any());
        verify(giftCertificateRepository, never())
                .findAllFilteredAndSortedByDescription(any(), any(), any());
        verify(giftCertificateRepository, times(1))
                .findAllFilteredAndSortedByTagNames(new ArrayList<>(), pageParams, gcParams.getSortParams());
    }

    @Test
    void canFindAllGiftCertificatesWhenNameNotValidDescNotValidTagNameNotValid() {
        //given
        PaginationParams paginationParams = new PaginationParams(15, 1);
        GiftCertificateParams gcParams = new GiftCertificateParams(null, null, null, "name asc", paginationParams);
        PaginationParams pageParams = new PaginationParams(15, 0);

        given(paginationProvider.getPaginationParams(gcParams.getPaginationParams())).willReturn(pageParams);
        given(giftCertificateRepository.findAllFilteredAndSorted(pageParams, gcParams.getSortParams()))
                .willReturn(giftCertificateEntities);

        //when
        List<GiftCertificateEntity> sortedAndFiltered = underTest.findAllGiftCertificates(gcParams);

        //then
        assertThat(sortedAndFiltered).isEqualTo(giftCertificateEntities);
        verify(giftCertificateRepository, never())
                .findAllFilteredAndSortedByName(any(), any(), any());
        verify(giftCertificateRepository, never())
                .findAllFilteredAndSortedByDescription(any(), any(), any());
        verify(giftCertificateRepository, never())
                .findAllFilteredAndSortedByTagNames(any(), any(), any());
        verify(giftCertificateRepository, times(1))
                .findAllFilteredAndSorted(pageParams, gcParams.getSortParams());
    }

    @Test
    @Disabled
    void update() {
        //given
        GiftCertificateRequestDto update = new GiftCertificateRequestDto(
                "name",
                "new name desc",
                BigDecimal.valueOf(123),
                12,
                null
        );

        long id = 3L;
        given(validator.isNumberValid(id)).willReturn(true);
        given(giftCertificateRepository.findById(id)).willReturn(giftCertificateEntity);
        given(modelMapper.getConfiguration()).willReturn(new InheritingConfiguration());
        doNothing().when(modelMapper).map(update, giftCertificateEntity);
        given(giftCertificateRepository.merge(giftCertificateEntity)).willReturn(giftCertificateEntity);

        //when
        GiftCertificateEntity updatedEntity = underTest.update(update, id);

        //then
        assertThat(updatedEntity).isEqualTo(giftCertificateEntity);
        verify(giftCertificateRepository, times(1)).merge(giftCertificateEntity);
        verify(giftCertificateRepository, times(1)).findById(id);

    }
}