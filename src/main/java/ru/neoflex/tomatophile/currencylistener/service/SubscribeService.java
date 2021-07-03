package ru.neoflex.tomatophile.currencylistener.service;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;
import ru.neoflex.tomatophile.currencylistener.pojo.Subscribe;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SubscribeService {
    private final List<Subscribe> subscribesOnUpdate;
    private final List<Subscribe> subscribesOnFall;

    private final CoinMarketCapService coinMarketCapService;

    private final EventService eventService;

    public void subscribeOnUpdate(Subscribe subscribe){
        subscribesOnUpdate.add(subscribe);
    }

    public void subscribeOnFall(Subscribe subscribe){
        Double lastPrice =null;

        try {
            lastPrice = coinMarketCapService.getOneByFigi(subscribe.getFigi()).getPrice();
        } catch (Exception e){
            eventService.errorEvent(subscribe.getChatId());
        }

        subscribe.setLastPrice(lastPrice);

        subscribesOnFall.add(subscribe);
    }

    public void unsubscribeOnUpdate(Subscribe subscribe) {
        subscribesOnUpdate.remove(subscribe);
    }
}
