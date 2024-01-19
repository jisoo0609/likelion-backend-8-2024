package com.example.restaurant.service;

import com.example.restaurant.dto.ReservationDto;
import com.example.restaurant.entity.Reservation;
import com.example.restaurant.entity.Restaurant;
import com.example.restaurant.repository.ReservationRepository;
import com.example.restaurant.repository.RestaurantRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class ReservationService {
    private final RestaurantRepository restaurantRepository;
    private final ReservationRepository reservationRepository;

    public ReservationDto create(Long restId, ReservationDto dto) {
        // 해당하는 레스토랑 가져옴
        Optional<Restaurant> optionalRestaurant = restaurantRepository.findById(restId);

        // 만약 없는 식당이라면 사용자에게 에러를 반환
        if (optionalRestaurant.isEmpty())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);

        // 새로운 예약 정보를 만들 준비를 한다.
        Reservation newEntity = new Reservation(
                dto.getDate(),
                dto.getReserveHour(),
                dto.getPeople(),
                dto.getDuration(),
                optionalRestaurant.get()

        );

        // 1.
        // 예약시간은 오픈시간과 클로즈 시간 사이여야 함
        // 오픈시간 이전이거나, 클로즈 시간을 넘긴 경우 잘못된 요청
        Restaurant restaurant = optionalRestaurant.get();
        if (restaurant.getOpenHours() > dto.getReserveHour())
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        // 닫는시각이 > 요청시각
        if (restaurant.getCloseHours() <= dto.getReserveHour())
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);

        // 2.
        // 예약시간 + 머무는 시간이 레스토랑의 닫는 시간을 넘지 않아야 함
        if (dto.getReserveHour() + dto.getDuration() > restaurant.getCloseHours())
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);

        // 3.
        // 예약 가능 여부를 판단할때
        // 해당 date의 reservationHour + duration에 해당한 시간은 다시 예약에 겹침 불가


        // 저장된 결과를 바탕으로 응답을 반환한다.
        return ReservationDto.fromEntity(reservationRepository.save(newEntity));
    }

    // 예약 전체 조회
    public List<ReservationDto> readAll(Long restId) {
        List<ReservationDto> reservationDtoList = new ArrayList<>();
        for (Reservation entity : reservationRepository.findAllByRestaurantId(restId)) {
            reservationDtoList.add(ReservationDto.fromEntity(entity));
        }
        return reservationDtoList;
    }
}
