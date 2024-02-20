package com.example.toss.dto;

import lombok.Data;

// 결제 승인을 위한 DTO
@Data
public class PaymentConfirmDto {
    private String paymentKey;
    private String orderId;
    private Integer amount;
}
