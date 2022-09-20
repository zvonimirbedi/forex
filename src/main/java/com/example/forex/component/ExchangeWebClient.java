package com.example.forex.component;

import com.example.forex.model.ExchangerateDto;
import com.example.forex.model.Rate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;

@Component
public class ExchangeWebClient {
    private final WebClient exchangerateWebClient;
    private final ModelMapper modelMapper;
    @Value("${webclient.currency}")
    private String baseCurrency;
    @Value("${webclient.amount}")
    private String baseAmount;

    public ExchangeWebClient(WebClient exchangerateWebClient, ModelMapper modelMapper) {
        this.exchangerateWebClient = exchangerateWebClient;
        this.modelMapper = modelMapper;
    }

    public Mono<Rate> getExchangeData(String currentDateString, BigDecimal amount){
        return exchangerateWebClient.get()
                .uri(currentDateString + "/?base=" + baseCurrency + "&amount=" + baseAmount)
                .retrieve().bodyToMono(ExchangerateDto.class)
                .map(response -> modelMapper.modelMapping(response, amount));
    }
}
