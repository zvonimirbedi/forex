package com.example.forex.component;

import com.example.forex.model.ExchangerateDto;
import com.example.forex.model.ForexRequest;
import com.example.forex.model.Rate;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class ModelMapper {
    public String forexRequestToRedisCompositeKey(ForexRequest forexRequest) {
        return forexRequest.getDate() + "_" + forexRequest.getCurrency() + "_" + forexRequest.getAmount().toPlainString();
    }
    public String baseExchangeToRedisCompositeKey(String currentDate, String baseCurrency, BigDecimal baseAmount) {
        return currentDate + "_" + baseCurrency + "_" + baseAmount.toPlainString();
    }


    public Rate modelMapping (ExchangerateDto exchangerateDto, BigDecimal amount) {
        return Rate.builder()
                .currency(exchangerateDto.getBase())
                .date(exchangerateDto.getDate())
                .rates(exchangerateDto.getRates())
                .amount(amount)
                .build();
    }

    public String rateToRedisCompositKey(Rate rate) {
        return rate.getDate() + "_" + rate.getCurrency() + "_" + rate.getAmount().toPlainString();
    }
}
