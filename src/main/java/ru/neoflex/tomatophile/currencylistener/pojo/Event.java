package ru.neoflex.tomatophile.currencylistener.pojo;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Event {
    private String chatId;
    private String figi;
    private Double price;
}
