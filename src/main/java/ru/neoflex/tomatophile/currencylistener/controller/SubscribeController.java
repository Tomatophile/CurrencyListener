package ru.neoflex.tomatophile.currencylistener.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.neoflex.tomatophile.currencylistener.pojo.Subscribe;
import ru.neoflex.tomatophile.currencylistener.service.SubscribeService;

@RestController
@RequiredArgsConstructor
public class SubscribeController {

    private final SubscribeService subscribeService;

    @PostMapping("/subscribe/update")
    public ResponseEntity<Subscribe> subscribeOnUpdate(@RequestBody Subscribe subscribe){
        subscribeService.subscribeOnUpdate(subscribe);
        return ResponseEntity.ok(subscribe);
    }

    @PostMapping("/subscribe/fall")
    public ResponseEntity<Subscribe> subscribeOnFall(@RequestBody Subscribe subscribe){
        subscribeService.subscribeOnFall(subscribe);
        return ResponseEntity.ok(subscribe);
    }

    @PostMapping("/unsubscribe/update")
    public void unsubscribeOnUpdate(@RequestBody Subscribe subscribe){
        subscribeService.unsubscribeOnUpdate(subscribe);
    }
}
