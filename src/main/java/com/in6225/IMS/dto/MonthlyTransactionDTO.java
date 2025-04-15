package com.in6225.IMS.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MonthlyTransactionDTO {
    private String month;
    private Long inSum;
    private Long outSum;
}
