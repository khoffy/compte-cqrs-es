package com.khoffylabs.comptecqrses.commonApi.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @NoArgsConstructor @AllArgsConstructor
public class CreditAccountRequestDto {
    private String accountId;
    private double amount;
    private String currency;
}
