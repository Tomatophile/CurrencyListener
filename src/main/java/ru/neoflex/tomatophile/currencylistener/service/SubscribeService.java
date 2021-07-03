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

    private final CoinMarketCapService coinMarketCapService;

    public void subscribeOnUpdate(Subscribe subscribe){
        subscribesOnUpdate.add(subscribe);
    }

    public void subscribeOnFall(Subscribe subscribe){
        subscribe.setLastPrice(coinMarketCapService.getOneByFigi(subscribe.getFigi()).getPrice());

        subscribesOnFall.add(subscribe);
    }

    public void unsubscribeOnUpdate(Subscribe subscribe) {
        subscribesOnUpdate.remove(subscribe);
    }
}
