package ru.neoflex.tomatophile.currencylistener.pojo;

import lombok.Data;

@Data
public class Subscribe {
    private String chatId;
    private String figi;
    private Double fallPercent;
    private Double lastPrice;
}
