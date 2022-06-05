package com.epam.esm.service.impl;

import com.epam.esm.dto.OrderRequestDto;
import com.epam.esm.dto.OrderResponseDto;
import com.epam.esm.entity.AppUserEntity;
import com.epam.esm.entity.GiftCertificateEntity;
import com.epam.esm.entity.OrderEntity;
import com.epam.esm.exception.ApplicationNotValidDataException;
import com.epam.esm.repository.OrderRepository;
import com.epam.esm.service.AppUserService;
import com.epam.esm.service.GiftCertificateService;
import com.epam.esm.utils.ApplicationValidator;
import com.epam.esm.utils.PaginationProvider;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.epam.esm.utils.ParamsStringProvider.*;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

/**
 * @author Obidjon Eshmamatov
 * @project rest_api_advanced_2
 * @created 02/06/2022 - 9:08 PM
 */

@ExtendWith(MockitoExtension.class)
class OrderServiceImplTest {

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private ApplicationValidator validator;

    @Mock
    private PaginationProvider paginationProvider;

    @Mock
    private GiftCertificateService giftCertificateService;

    @Mock
    private AppUserService appUserService;

    private OrderServiceImpl underTest;
    private OrderEntity orderEntity;
    private OrderRequestDto orderRequestDto;
    private List<OrderEntity> orderEntities;
    private AppUserEntity appUserEntity;

    @BeforeEach
    void setUp() {
        underTest = new OrderServiceImpl(
                orderRepository,
                validator,
                appUserService,
                giftCertificateService,
                paginationProvider
        );

        appUserEntity = new AppUserEntity(1L, "john", "backer", "john@gmail.com", null);

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
        List<GiftCertificateEntity> giftCertificateEntities = new ArrayList<>();
        giftCertificateEntities.add(giftCertificateEntity1);
        giftCertificateEntities.add(giftCertificateEntity2);

        orderEntity = new OrderEntity(1L, BigDecimal.valueOf(3), LocalDateTime.now(), giftCertificateEntities, appUserEntity);

        List<Long> giftCertificateIds = new ArrayList<>();
        giftCertificateIds.add(1L);
        giftCertificateIds.add(2L);
        orderRequestDto = new OrderRequestDto(1L, giftCertificateIds);

        orderEntities = new ArrayList<>();
        OrderEntity orderEntity1 = new OrderEntity(2L, BigDecimal.valueOf(12), LocalDateTime.now(), giftCertificateEntities, appUserEntity);
        OrderEntity orderEntity2 = new OrderEntity(3L, BigDecimal.valueOf(12), LocalDateTime.now(), giftCertificateEntities, appUserEntity);
        orderEntities.add(orderEntity1);
        orderEntities.add(orderEntity2);
    }

    @Test
    @Disabled
    void canCreateOrder() {
        //given
        given(appUserService.findUserById(orderRequestDto.getUserId())).willReturn(appUserEntity);
        given(orderRepository.merge(orderEntity)).willReturn(orderEntity);



        //when
        OrderEntity createdOrder = underTest.create(orderRequestDto);

        //then
        assertThat(createdOrder).isEqualTo(orderEntity);
    }

    @Test
    void canFindOrderById() {
        //given
        long id = 1L;
        given(validator.isNumberValid(id)).willReturn(true);
        given(orderRepository.findById(id)).willReturn(orderEntity);

        //when
        OrderEntity byId = underTest.findById(id);

        //then
        assertThat(byId).isEqualTo(orderEntity);
        verify(orderRepository, times(1)).findById(id);
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
        verify(orderRepository, never()).findById(id);
    }


    @Test
    void canDeleteById() {
        //given
        long id = 3L;
        given(validator.isNumberValid(id)).willReturn(true);

        //when
        underTest.deleteById(id);

        //then
        verify(orderRepository, times(1)).deleteById(id);
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
        verify(orderRepository, never()).deleteById(id);
    }

    @Test
    void canFindAllWhenUserIdValid() {
        //given
        Map<String, Object> params = new HashMap<>();
        params.put(LIMIT, 15);
        params.put(OFFSET, 1);
        params.put(USER_ID, 1);
        Integer userId = (Integer) params.get(USER_ID);

        Map<String, Integer> paginationParams = new HashMap<>();
        paginationParams.put(LIMIT, 15);
        paginationParams.put(OFFSET, 0);

        given(validator.isNumberValid(userId)).willReturn(true);
        given(paginationProvider.getPaginationParam(params)).willReturn(paginationParams);
        given(orderRepository.findAllOrdersByUserId(userId, paginationParams)).willReturn(orderEntities);

        //when
        List<OrderEntity> all = underTest.findAll(params);

        //then
        assertThat(all).isEqualTo(orderEntities);
        verify(orderRepository, times(1)).findAllOrdersByUserId(userId, paginationParams);
    }

    @Test
    void willThrowErrorIfUserIdNotValidDuringFindingAll() {
        //given
        Map<String, Object> params = new HashMap<>();
        params.put(LIMIT, 15);
        params.put(OFFSET, 1);
        params.put(USER_ID, -12);
        int userId = (int) params.get(USER_ID);

        given(validator.isNumberValid(userId)).willReturn(false);

        //when
        //then
        assertThatThrownBy(() -> underTest.findAll(params))
                .isInstanceOf(ApplicationNotValidDataException.class)
                .hasMessageContaining(ID_NOT_VALID);
        verify(orderRepository, never()).findAllOrdersByUserId(any(), any());
    }

    @Test
    void canFindSingleUserOrder() {
        //given
        int userId = 1;
        int orderId = 2;
        given(validator.isNumberValid(userId)).willReturn(true);
        given(validator.isNumberValid(orderId)).willReturn(true);
        given(orderRepository.findSingleUserOrder(userId, orderId)).willReturn(orderEntities);
        OrderResponseDto orderResponseDto =
                new OrderResponseDto(orderEntities.get(0).getCost(), orderEntities.get(0).getCreateDate());

        //when
        OrderResponseDto singleUserOrder = underTest.findSingleUserOrder(userId, orderId);

        //then
        assertThat(singleUserOrder.getCost()).isEqualTo(orderResponseDto.getCost());
        assertThat(singleUserOrder.getCreateDate()).isEqualTo(orderResponseDto.getCreateDate());
        verify(orderRepository, times(1)).findSingleUserOrder(userId, orderId);
    }
}