package com.example.restaurant;

import com.example.restaurant.dto.MenuDto;
import com.example.restaurant.entity.Menu;
import com.example.restaurant.entity.Restaurant;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.swing.text.html.Option;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class MenuService {
    private final RestaurantRepository restaurantRepository;
    private final MenuRepository menuRepository;

    public MenuDto create(Long restId, MenuDto dto) {
        // 해당하는 레스토랑 가져옴
        Optional<Restaurant> optionalRestaurant = restaurantRepository.findById(restId);
        // 실존하는 레스토랑인지 확인
        if (optionalRestaurant.isEmpty())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);

        Menu menu = new Menu(dto.getName(), dto.getPrice(), optionalRestaurant.get());
        return MenuDto.fromEntity(menuRepository.save(menu));
    }

    public List<MenuDto> readAll(Long restId) {
        if (! restaurantRepository.existsById(restId))
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);

        List<MenuDto> menuDtoList = new ArrayList<>();
        for (Menu entity : menuRepository.findAllByRestaurantId(restId)) {
            menuDtoList.add(MenuDto.fromEntity(entity));
        }
        return menuDtoList;
    }

    public MenuDto read(Long restId, Long menuId) {
        Optional<Menu> optionalMenu = menuRepository.findById(menuId);
        if (optionalMenu.isEmpty())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);

        // 해당 메뉴가 식당의 메뉴가 맞는지
        Menu menu = optionalMenu.get();
        if (!menu.getRestaurant().getId().equals(restId))
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);

        return MenuDto.fromEntity(menu);
    }

    public MenuDto update(Long restId, Long menuId, MenuDto dto) {
        Optional<Menu> optionalMenu = menuRepository.findById(menuId);
        if (optionalMenu.isEmpty())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);

        // 해당 메뉴가 식당의 메뉴가 맞는지
        Menu menu = optionalMenu.get();
        if (!menu.getRestaurant().getId().equals(restId))
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);

        menu.setName(dto.getName());
        menu.setPrice(dto.getPrice());
        return MenuDto.fromEntity(menuRepository.save(menu));
    }

    public void delete(Long restId, Long menuId) {
        Optional<Menu> optionalMenu
                = menuRepository.findById(menuId);
        if (optionalMenu.isEmpty())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);

        Menu menu = optionalMenu.get();
        if (!menu.getRestaurant().getId().equals(restId))
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        menuRepository.deleteById(menuId);
    }
}
