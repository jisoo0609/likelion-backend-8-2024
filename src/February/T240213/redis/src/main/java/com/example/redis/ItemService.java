package com.example.redis;

import com.example.redis.dto.ItemDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ItemService {
    private final SlowDataQuery repository;

    public List<ItemDto> readAll() {
        return repository.findAll()
                .stream()
                .map(ItemDto::fromEntity)
                .toList();
    }

    public ItemDto readOne(Long id) {
        return repository.findById(id)
                .map(ItemDto::fromEntity)
                .orElseThrow(() ->
                        new ResponseStatusException(HttpStatus.NOT_FOUND));
    }
}