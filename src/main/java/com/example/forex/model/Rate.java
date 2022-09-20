package com.example.forex.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Map;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class Rate implements Serializable {
    private String currency;
    private String date;
    private BigDecimal amount;
    private Map<String, BigDecimal> rates;
}
