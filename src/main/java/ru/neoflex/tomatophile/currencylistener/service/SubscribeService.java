package ru.neoflex.tomatophile.currencylistener.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.neoflex.tomatophile.currencylistener.pojo.Subscribe;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SubscribeService {
    private final List<Subscribe> subscribesOnUpdate;
    private final List<Subscribe> subscribesOnFall;
    private final List<String> errors;

    private final CoinMarketCapService coinMarketCapService;

    private final EventService eventService;

    public void subscribeOnUpdate(Subscribe subscribe) {
        subscribesOnUpdate.add(subscribe);
    }

    public void subscribeOnFall(Subscribe subscribe) {
        try {
            var lastPrice = coinMarketCapService.getOneByFigi(subscribe.getFigi()).getPrice();
            subscribe.setLastPrice(lastPrice);
            subscribesOnFall.add(subscribe);
        } catch (Exception e) {
            errors.add(subscribe.getChatId());
        }
    }

    public boolean unsubscribeOnUpdate(Subscribe subscribe) {
        if(subscribesOnUpdate.contains(subscribe)){
            subscribesOnUpdate.remove(subscribe);
            return true;
        }
        return false;
    }
}
