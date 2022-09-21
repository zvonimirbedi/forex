package com.example.forex.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Data
public class ForexRequest {
    @NotNull
    @NotEmpty
    @Size(min = 3, max = 3, message = "Please provide valid currency size with 3 letters.")
    @ApiModelProperty(example = "EUR")
    private String currency;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @JsonFormat(pattern = "yyyy-MM-dd")
    @ApiModelProperty(example = "2022-09-19")
    private LocalDate date;
    @DecimalMin(value = "0.01", message = "Smallest allowed amount is 0.01")
    @ApiModelProperty(example = "1000000")
    private BigDecimal amount;
    @ApiModelProperty(example = "[\"USD\",\"GBP\"]")
    private List<String> exchangeCurrencies;
}
