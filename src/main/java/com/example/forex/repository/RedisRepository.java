package com.example.forex.repository;

import com.example.forex.component.ModelMapper;
import com.example.forex.model.Rate;
import org.springframework.data.redis.core.ReactiveRedisOperations;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

import java.time.Duration;

@Repository
public class RedisRepository {
    private final ReactiveRedisOperations<String, Rate> rateOps;
    private final ModelMapper modelMapper;

    public RedisRepository(ReactiveRedisOperations<String, Rate> rateOps, ModelMapper modelMapper) {
        this.rateOps = rateOps;
        this.modelMapper = modelMapper;
    }

    public Mono<Boolean> saveRates(Rate rate, boolean isTtl){
        String key = modelMapper.rateToRedisCompositKey(rate);
        return rateOps.opsForValue().set(key, rate)
                .filter(response -> isTtl)
                .flatMap(response -> rateOps.expire(key, Duration.ofSeconds(60))
        );
    }
    public Mono<Rate> getRates(String compositeKey){
        return rateOps.opsForValue().get(compositeKey);
    }


}
