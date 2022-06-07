package com.epam.esm.service.impl;

import com.epam.esm.dto.params.AppUserParams;
import com.epam.esm.dto.params.PaginationParams;
import com.epam.esm.entity.AppUserEntity;
import com.epam.esm.exception.ApplicationNotValidDataException;
import com.epam.esm.repository.AppUserRepository;
import com.epam.esm.utils.ApplicationValidator;
import com.epam.esm.utils.PaginationProvider;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static com.epam.esm.utils.ParamsStringProvider.ID_NOT_VALID;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

/**
 * @author Star
 * @project rest_api_advanced_2
 * @created 02/06/2022 - 8:50 PM
 */

@ExtendWith(MockitoExtension.class)
class AppUserServiceImplTest {

    @Mock
    private AppUserRepository appUserRepository;

    @Mock
    private ApplicationValidator validator;

    @Mock
    private PaginationProvider paginationProvider;

    private AppUserServiceImpl underTest;
    private AppUserEntity appUserEntity;
    private List<AppUserEntity> appUserEntities;

    @BeforeEach
    void setUp() {
        underTest = new AppUserServiceImpl(appUserRepository, validator, paginationProvider);

        appUserEntity = new AppUserEntity(1L, "john", "smith", "john@gmail.com", null);
        AppUserEntity appUserEntity2 = new AppUserEntity(2L, "tim", "backer", "tim@gmail.com", null);
        AppUserEntity appUserEntity3 = new AppUserEntity(3L, "alice", "kim", "alice@gmail.com", null);
        appUserEntities = new ArrayList<>();
        appUserEntities.add(appUserEntity2);
        appUserEntities.add(appUserEntity3);
    }

    @Test
    void canFindAllUsersWhenNameValid() {
        //given
        PaginationParams paginationParams = new PaginationParams(15, 1);
        AppUserParams appUserParams = new AppUserParams("user", null, paginationParams);

        PaginationParams pageParams = new PaginationParams(15, 0);

        given(validator.isNameValid(appUserParams.getName())).willReturn(true);
        given(paginationProvider.getPaginationParams(appUserParams.getPaginationParams())).willReturn(pageParams);
        given(appUserRepository.findAllUsersByName(appUserParams.getName(), pageParams)).willReturn(appUserEntities);

        //when
        List<AppUserEntity> allUsers = underTest.findAllUsers(appUserParams);

        //then
        assertThat(allUsers).isEqualTo(appUserEntities);
        verify(appUserRepository, times(1)).findAllUsersByName(appUserParams.getName(), pageParams);
        verify(appUserRepository, never()).findAllUsersByEmail(any(), any());
    }

    @Test
    void canFindAllUsersWhenNameNotValidEmailValid() {
        //given
        PaginationParams paginationParams = new PaginationParams(15, 1);
        AppUserParams appUserParams = new AppUserParams(null, "email", paginationParams);

        PaginationParams pageParams = new PaginationParams(15, 0);

        given(validator.isEmailValid(appUserParams.getEmail())).willReturn(true);
        given(paginationProvider.getPaginationParams(appUserParams.getPaginationParams())).willReturn(pageParams);
        given(appUserRepository.findAllUsersByEmail(appUserParams.getEmail(), pageParams)).willReturn(appUserEntities);

        //when
        List<AppUserEntity> allUsers = underTest.findAllUsers(appUserParams);

        //then
        assertThat(allUsers).isEqualTo(appUserEntities);
        verify(appUserRepository, never()).findAllUsersByName(any(), any());
        verify(appUserRepository, times(1)).findAllUsersByEmail(appUserParams.getEmail(), pageParams);
    }

    @Test
    void canFindAllUsersWhenBothNameNotValidEmailNotValid() {
        //given
        PaginationParams paginationParams = new PaginationParams(15, 1);
        AppUserParams appUserParams = new AppUserParams(null, null, paginationParams);

        PaginationParams pageParams = new PaginationParams(15, 0);

        given(paginationProvider.getPaginationParams(appUserParams.getPaginationParams())).willReturn(pageParams);
        given(appUserRepository.findAll(pageParams)).willReturn(appUserEntities);

        //when
        List<AppUserEntity> allUsers = underTest.findAllUsers(appUserParams);

        //then
        assertThat(allUsers).isEqualTo(appUserEntities);
        verify(appUserRepository, never()).findAllUsersByName(any(), any());
        verify(appUserRepository, never()).findAllUsersByEmail(any(), any());
        verify(appUserRepository, times(1)).findAll(pageParams);
    }

    @Test
    void canFindUserById() {
        //given
        long id = 1L;
        given(validator.isNumberValid(id)).willReturn(true);
        given(appUserRepository.findById(id)).willReturn(appUserEntity);

        //when
        AppUserEntity userById = underTest.findUserById(id);

        //then
        assertThat(userById).isEqualTo(appUserEntity);
        verify(appUserRepository, times(1)).findById(id);
    }

    @Test
    void willThrowErrorIfIdNotValidInFinding() {
        //given
        Long id = 1L;
        given(validator.isNumberValid(id)).willReturn(false);

        //when
        //then
        assertThatThrownBy(() -> underTest.findUserById(id))
                .isInstanceOf(ApplicationNotValidDataException.class)
                .hasMessageContaining(ID_NOT_VALID);
        verify(appUserRepository, never()).findById(id);
    }
}