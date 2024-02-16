package com.example.kafkaproducer;

import lombok.Data;

@Data
public class PayloadDto {
    private String producer;
    private String message;
}