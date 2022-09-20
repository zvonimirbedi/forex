package com.example.forex.model;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Map;

@Data
public class ExchangerateDto {
    private String base;
    private String date;
    private Map<String, BigDecimal> rates;
}
