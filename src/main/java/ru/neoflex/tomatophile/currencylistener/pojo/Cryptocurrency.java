package ru.neoflex.tomatophile.currencylistener.pojo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Cryptocurrency {
    @JsonProperty("symbol")
    private String figi;
    @JsonProperty("price")
    private Double price;
}
