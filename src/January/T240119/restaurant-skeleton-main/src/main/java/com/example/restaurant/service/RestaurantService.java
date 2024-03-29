package com.example.restaurant.service;

import com.example.restaurant.dto.RestaurantDto;
import com.example.restaurant.entity.Restaurant;
import com.example.restaurant.repository.RestaurantRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class RestaurantService {
    private final RestaurantRepository repository;

    // 사용자의 데이터를 가지고 새로운 식당을 만듦
    public RestaurantDto create(RestaurantDto dto) {
        // 만약 닫는 시간이 오픈시간을 초과하면 잘못된 입력
        if (dto.getCloseHour() > dto.getOpenHour())
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);

        Restaurant newRestaurant = new Restaurant(
                dto.getName(),
                dto.getCategory(),
                dto.getOpenHour(),
                dto.getCloseHour()
        );
        return RestaurantDto.fromEntity(repository.save(newRestaurant));
    }

    public List<RestaurantDto> readAll() {
        /*
        List<RestaurantDto> restaurantDtoList = new ArrayList<>();
        List<Restaurant> restaurantList = repository.findAll();

        for (Restaurant entity : restaurantList) {
            // restaurantDto로 변환해서 저장
            // fromEntity 사용
            restaurantDtoList.add(RestaurantDto.fromEntity(entity));
        }
        return restaurantDtoList;
         */

        // Stream API 활용
        return repository.findAll().stream()
//              .map(entity -> RestaurantDto.fromEntity(entity))
                .map(RestaurantDto::fromEntity)
                .collect(Collectors.toList());

    }

    public RestaurantDto read(Long id) {
        /*
        // Optional: mapper class. 자바의 null safety를 위함
        Optional<Restaurant> optionalRestaurant = repository.findById(id);
        if (optionalRestaurant.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        return RestaurantDto.fromEntity(optionalRestaurant.get());
         */

        return repository.findById(id)
                // .map(entity -> RestaurantDto.fromEntity(entity))
                .map(RestaurantDto::fromEntity)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    public RestaurantDto update(Long id, RestaurantDto dto) {
        Optional<Restaurant> optionalRestaurant = repository.findById(id);

        if (optionalRestaurant.isEmpty())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);

        Restaurant target = optionalRestaurant.get();
        target.setName(dto.getName());
        target.setCategory(dto.getCategory());
        target.setOpenHours(dto.getOpenHour());
        target.setCloseHours(dto.getCloseHour());

        return RestaurantDto.fromEntity(repository.save(target));
    }

    public void delete(Long id) {
        if (repository.existsById(id))
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        repository.deleteById(id);
    }
}
