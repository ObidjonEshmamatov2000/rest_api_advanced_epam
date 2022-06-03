package com.epam.esm.service.impl;

import com.epam.esm.entity.AppUserEntity;
import com.epam.esm.exception.ApplicationNotValidDataException;
import com.epam.esm.repository.AppUserRepository;
import com.epam.esm.utils.ApplicationValidator;
import com.epam.esm.utils.PaginationProvider;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.epam.esm.utils.ParamsStringProvider.*;
import static com.epam.esm.utils.ParamsStringProvider.NAME;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;
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
        Map<String, Object> params = new HashMap<>();
        params.put(LIMIT, 25);
        params.put(OFFSET, 1);
        params.put(NAME, "user");
        String name = (String) params.get(NAME);

        Map<String, Integer> paginationParams = new HashMap<>();
        paginationParams.put(LIMIT, 15);
        paginationParams.put(OFFSET, 0);

        given(validator.isNameValid(name)).willReturn(true);
        given(paginationProvider.getPaginationParam(params)).willReturn(paginationParams);
        given(appUserRepository.findAllUsersByName(name, paginationParams)).willReturn(appUserEntities);

        //when
        List<AppUserEntity> allUsers = underTest.findAllUsers(params);

        //then
        assertThat(allUsers).isEqualTo(appUserEntities);
        verify(appUserRepository, times(1)).findAllUsersByName(name, paginationParams);
        verify(appUserRepository, never()).findAllUsersByEmail(any(), any());
    }

    @Test
    void canFindAllUsersWhenNameNotValidEmailValid() {
        //given
        Map<String, Object> params = new HashMap<>();
        params.put(LIMIT, 25);
        params.put(OFFSET, 1);
        params.put(EMAIL, "email");
        String name = (String) params.get(NAME);
        String email = (String) params.get(EMAIL);

        Map<String, Integer> paginationParams = new HashMap<>();
        paginationParams.put(LIMIT, 15);
        paginationParams.put(OFFSET, 0);

        given(validator.isNameValid(name)).willReturn(false);
        given(validator.isEmailValid(email)).willReturn(true);
        given(paginationProvider.getPaginationParam(params)).willReturn(paginationParams);
        given(appUserRepository.findAllUsersByEmail(email, paginationParams)).willReturn(appUserEntities);

        //when
        List<AppUserEntity> allUsers = underTest.findAllUsers(params);

        //then
        assertThat(allUsers).isEqualTo(appUserEntities);
        verify(appUserRepository, never()).findAllUsersByName(any(), any());
        verify(appUserRepository, times(1)).findAllUsersByEmail(email, paginationParams);
    }

    @Test
    void canFindAllUsersWhenBothNameNotValidEmailNotValid() {
        //given
        Map<String, Object> params = new HashMap<>();
        params.put(LIMIT, 25);
        params.put(OFFSET, 1);
        String name = (String) params.get(NAME);
        String email = (String) params.get(EMAIL);

        Map<String, Integer> paginationParams = new HashMap<>();
        paginationParams.put(LIMIT, 15);
        paginationParams.put(OFFSET, 0);

        given(validator.isNameValid(name)).willReturn(false);
        given(validator.isEmailValid(email)).willReturn(false);
        given(paginationProvider.getPaginationParam(params)).willReturn(paginationParams);
        given(appUserRepository.findAll(paginationParams)).willReturn(appUserEntities);

        //when
        List<AppUserEntity> allUsers = underTest.findAllUsers(params);

        //then
        assertThat(allUsers).isEqualTo(appUserEntities);
        verify(appUserRepository, never()).findAllUsersByName(any(), any());
        verify(appUserRepository, never()).findAllUsersByEmail(any(), any());
        verify(appUserRepository, times(1)).findAll(paginationParams);
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