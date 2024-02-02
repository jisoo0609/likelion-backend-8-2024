# RESTful 연습하기

![Untitled](/Training/image/240119.png)

## fromEntity()

- 각 DTO에 DTO를 반환하는 fromEntity() static method를 추가한다.

```java
public static RestaurantDto fromEntity(Restaurant entity) {
    return new RestaurantDto(
            entity.getId(),
            entity.getName(),
            entity.getCategory(),
            entity.getOpenHours(),
            entity.getCloseHours()
    );
}
```

## Restaurant

### RestaurantService

```java
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
```

```java
public List<RestaurantDto> readAll() {
		// Stream API 활용
		return repository.findAll().stream()
		// .map(entity -> RestaurantDto.fromEntity(entity))
		.map(RestaurantDto::fromEntity)
		.collect(Collectors.toList());
}
```

```java
public RestaurantDto read(Long id) {
    return repository.findById(id)
            // .map(entity -> RestaurantDto.fromEntity(entity))
            .map(RestaurantDto::fromEntity)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
}
```

### RestaurantController

```java
@PostMapping
public RestaurantDto create(
        @RequestBody
        RestaurantDto dto
) {
    return service.create(dto);
}
```

```java
@GetMapping
public List<RestaurantDto> readAll() {
    return service.readAll();
}
```

```java
@GetMapping("/{id}")
public RestaurantDto read(
        @PathVariable("id")
        Long id
) {
    return service.read(id);
}
```

## Menu

### MenuService

```java
public MenuDto create(Long restId, MenuDto dto) {
    // 해당하는 레스토랑 가져옴
    Optional<Restaurant> optionalRestaurant = restaurantRepository.findById(restId);
    // 실존하는 레스토랑인지 확인
    if (optionalRestaurant.isEmpty())
        throw new ResponseStatusException(HttpStatus.NOT_FOUND);

    Menu menu = new Menu(dto.getName(), dto.getPrice(), optionalRestaurant.get());
    return MenuDto.fromEntity(menuRepository.save(menu));
}
```

```java
public List<MenuDto> readAll(Long restId) {
    if (! restaurantRepository.existsById(restId))
        throw new ResponseStatusException(HttpStatus.NOT_FOUND);

    List<MenuDto> menuDtoList = new ArrayList<>();
    for (Menu entity : menuRepository.findAllByRestaurantId(restId)) {
        menuDtoList.add(MenuDto.fromEntity(entity));
    }
    return menuDtoList;
}
```

```java
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
```

### MenuController

- RestaurantController와 유사하다
- import

```java
@Slf4j
@RestController
@RequestMapping("/restaurant/{restId}/menu")
@RequiredArgsConstructor
```

## Reservation

### ReservationService

```java
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

    // 저장된 결과를 바탕으로 응답을 반환한다.
    return ReservationDto.fromEntity(reservationRepository.save(newEntity));
}
```

- 조건 1
    - 예약시간은 오픈시간과 클로즈시간 사이여야 한다.
    - 오픈시간 이전이거나, 클로즈시간을 넘긴 경우 잘못된 요청임을 알려야 한다

```java
Restaurant restaurant = optionalRestaurant.get();
if (restaurant.getOpenHours() > dto.getReserveHour())
    throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
// 닫는시각이 > 요청시각
if (restaurant.getCloseHours() <= dto.getReserveHour())
    throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
```

- 조건 2
    - 예약시간 + 체류시간이 레스토랑의 클로즈시간을 넘기지 않아야 한다.

```java
if (dto.getReserveHour() + dto.getDuration() > restaurant.getCloseHours())
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
```

- 조건 3
    - 예약 가능 여부를 판단할 때, 해당 date의 reservationHour + duration에 해당하는 시간은 겹침 예약이 불가능하다.

```java
List<Reservation> dateReserves =
        reservationRepository.findAllByRestaurantIdAndDate(restId, dto.getDate());
// 24시간 중 예약된 시간을 파악하기 위한 boolean[]
boolean[] reservable = new boolean[24];
Arrays.fill(reservable, true);

// 영업 시간 내에만 예약 가능하다 설정
for (int i = 0; i < restaurant.getOpenHours(); i++) {
    reservable[i] = false;
}
for (int i = restaurant.getCloseHours(); i < 24; i++) {
    reservable[i] = false;
}

// 각각 예약 정보를 바탕으로
for (Reservation entity: dateReserves) {
    // 시간단위 예약 불가능 정보를 갱신
    for (int i = 0; i < entity.getDuration(); i++) {
        reservable[entity.getReserveHour() + i] = false;
    }
}

// 이번 예약 정보를 바탕으로
for (int i = 0; i < dto.getDuration(); i++) {
    // 만약 예약하려는 시간대가 이미 예약 되어있다면
    if (!reservable[dto.getReserveHour() + i])
        // 예약 불가능
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
}
```

### ReservationController

- RestaurantController, MenuController와 유사하다.

---

# Query Parameter

```java
http://example.com/path?query=keyword&limit=20
```

- 요구하는 자원에 대한 동적인 조건을 전달하는 용도로 사용
- 페이지, 검색 등
- ?뒤에 `key=value` 형식으로 활용, 각 인자는 `&` 로 구분

# `@RequestParam`

- Spring의 RequestMapping 메서드에서 `@RequestParam` 을 이용해 Query의 인자를 받아올 수 있다

```java
@GetMapping
public List<ArticleDto> readAllPaged(
        @RequestParam(value = "page", defaultValue = "1")
        Integer page,
        @RequestParam(value = "ilmit", defaultValue = "20")
        Integer limit
) {
    return service.readAllPaged(page, limit);
}
```

- `defalutValue` : 기본값 설정
- `required` : 필수 포함 여부

# Pagination

- 조회할 데이터의 개수가 많을 때, 조회되는 데이터의 개수를 한정시켜 페이지 단위로 나누는 기법
- 조회할 데이터의 개수가 줄어들기 때문에 성능 향상 가능
- 사용자가 데이터를 확인하는 과정에서 확인해야 하는 데이터를 줄여 UX 향상

## JPA와 Pagination

- Repository

```java
// ID가 큰 순서대로 최상위 20개
List<Article> findTop20ByOrderByIdDesc();
```

### Pageable

- `PagingAndSortingRepository` 의 메서드에 전달 가능한 페이지 구분 용도

```java
// 3. JPA PagingAndSortingRepository
public Page<Article> readAllPagination(Integer page, Integer limit) {
    // Pageable 객체 만듦
		Pageable pageable = PageRequest.of(page, limit);
    // findAll 호출 시 Pageable 전달
		Page<Article> articleEntityPage = repository.findAll(pageable);

    return articleEntityPage;
}
```