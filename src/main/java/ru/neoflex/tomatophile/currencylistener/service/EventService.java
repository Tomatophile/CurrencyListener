package ru.neoflex.tomatophile.currencylistener.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import ru.neoflex.tomatophile.currencylistener.pojo.Event;
import ru.neoflex.tomatophile.currencylistener.pojo.Subscribe;

import java.util.List;

@Service
public class EventService {
    @Value("${cryptoKoshBot.baseUrl}")
    private String cryptoKoshBotUrl;
    @Value("${cryptoKoshBot.updateUrl}")
    private String updateUrl;
    @Value("${cryptoKoshBot.fallUrl}")
    private String fallUrl;

    private final List<Subscribe> subscribesOnUpdate;
    private final List<Subscribe> subscribesOnFall;

    private final RestTemplate restTemplate;

    private final CoinMarketCapService coinMarketCapService;


    public EventService(List<Subscribe> subscribesOnUpdate, List<Subscribe> subscribesOnFall, RestTemplateBuilder restTemplateBuilder, CoinMarketCapService coinMarketCapService) {
        this.subscribesOnUpdate = subscribesOnUpdate;
        this.subscribesOnFall = subscribesOnFall;
        this.restTemplate = restTemplateBuilder.build();
        this.coinMarketCapService = coinMarketCapService;
    }

    @Scheduled(fixedRate = 3000)
    public void updateEvent() {
        var url = cryptoKoshBotUrl.concat(updateUrl);

        for (var subscribe : subscribesOnUpdate) {
            var chatId = subscribe.getChatId();
            var figi = subscribe.getFigi();

            var event = Event.builder().chatId(chatId).figi(figi).price(coinMarketCapService.getOneByFigi(figi).getPrice()).build();

            restTemplate.postForObject(url, event, Event.class);
        }
    }

    @Scheduled(fixedRate = 30000)
    public void fallEvent() {
        var url = cryptoKoshBotUrl.concat(fallUrl);

        for (var i = 0; i < subscribesOnFall.size(); i++) {
            var chatId = subscribesOnFall.get(i).getChatId();
            var figi = subscribesOnFall.get(i).getFigi();

            var lastCurrency = subscribesOnFall.get(i);
            var currCurrency = coinMarketCapService.getOneByFigi(lastCurrency.getFigi());

            if(lastCurrency.getLastPrice() - lastCurrency.getLastPrice()*lastCurrency.getFallPercent()/100 >= currCurrency.getPrice()){
                subscribesOnFall.remove(lastCurrency);
                i--;

                var event = Event.builder().chatId(chatId).figi(figi).price(currCurrency.getPrice()).build();

                restTemplate.postForObject(url, event, Event.class);
            }
        }
    }
}
