package com.example.forex.controller;

import com.example.forex.model.ForexRequest;
import com.example.forex.model.Rate;
import com.example.forex.service.ForexService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import javax.validation.Valid;


@RestController
@RequestMapping("/exchange")
@Slf4j
public class ExchangeController {

    private final ForexService forexService;

    public ExchangeController(ForexService forexService) {
        this.forexService = forexService;
    }

    @PostMapping("post")
    private Mono<Rate> post(@Valid @RequestBody ForexRequest forexRequest) {
        return forexService.getRates(forexRequest);
    }


}
