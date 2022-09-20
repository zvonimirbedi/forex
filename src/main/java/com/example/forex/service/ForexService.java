package com.example.forex.service;

import com.example.forex.component.ExchangeWebClient;
import com.example.forex.component.ModelMapper;
import com.example.forex.model.ForexRequest;
import com.example.forex.model.Rate;
import com.example.forex.repository.RedisRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

@Service
public class ForexService {

    private final RedisRepository redisRepository;
    private final ExchangeWebClient exchangeWebClient;
    private final ModelMapper modelMapper;
    @Value("${webclient.currency}")
    private String baseCurrency;
    @Value("${webclient.amount}")
    private BigDecimal baseAmount;

    public ForexService(RedisRepository redisRepository, ExchangeWebClient exchangeWebClient, ModelMapper modelMapper) {
        this.redisRepository = redisRepository;
        this.exchangeWebClient = exchangeWebClient;
        this.modelMapper = modelMapper;
    }

    public Mono<Rate> getRates(ForexRequest forexRequest){

        // validation
        LocalDate currentDate = LocalDate.now();
        LocalDate date;
        if (forexRequest.getDate() != null && forexRequest.getDate().isBefore(currentDate))
            date = forexRequest.getDate();
        else
            date = currentDate;

        if(forexRequest.getAmount() == null)
            forexRequest.setAmount(BigDecimal.ONE);

        return redisRepository.getRates(modelMapper.forexRequestToRedisCompositeKey(forexRequest))
                .switchIfEmpty(
                        redisRepository.getRates(modelMapper.baseExchangeToRedisCompositeKey(date.toString(), baseCurrency, baseAmount))
                        .switchIfEmpty(exchangeWebClient.getExchangeData(date.toString(), baseAmount))
                                .map(baseRate -> {
                                    redisRepository.saveRates(baseRate, false).subscribe();
                                    return baseRate;}
                                )
                                .map(baseRate -> {
                                    Rate customRate = baseRateToCustomRate(baseRate, forexRequest);
                                    redisRepository.saveRates(customRate, true).subscribe();
                                    return customRate;
                                }))
                .map(rate -> filterRate(rate, forexRequest));
    }

    private Rate filterRate(Rate rate, ForexRequest forexRequest) {
        if(forexRequest.getExchangeCurrencies() != null && forexRequest.getExchangeCurrencies().size()>0){
            Map<String, BigDecimal> customRates = new HashMap<>();
            for (String currency:forexRequest.getExchangeCurrencies()) {
                if(rate.getRates().containsKey(currency))
                    customRates.put(currency, rate.getRates().get(currency));
            }
            rate.setRates(customRates);
        }
        return rate;
    }

    public Rate baseRateToCustomRate(Rate baseRate, ForexRequest forexRequest){
        Rate.RateBuilder customRateBuilder = Rate.builder();
        customRateBuilder.amount(forexRequest.getAmount())
                .currency(forexRequest.getCurrency())
                .date(baseRate.getDate());
        Map<String, BigDecimal> customRates = new HashMap<>();
        baseRate.getRates().forEach((currency, amount) -> {
            if(baseRate.getRates().containsKey(forexRequest.getCurrency()))
                customRates.put(currency, baseRate.getRates().get(currency).divide(baseRate.getRates().get(forexRequest.getCurrency()), 8, RoundingMode.HALF_UP).multiply(forexRequest.getAmount()));
        });
        return customRateBuilder.rates(customRates).build();
    }



}
