package com.example.forex.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class ForexResponse{
    private String currency;
    private String date;
    private String amount;
    private Map<String, String> rates;
}
