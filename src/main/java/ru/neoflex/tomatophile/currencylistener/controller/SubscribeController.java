package ru.neoflex.tomatophile.currencylistener.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.neoflex.tomatophile.currencylistener.pojo.Event;
import ru.neoflex.tomatophile.currencylistener.pojo.Subscribe;
import ru.neoflex.tomatophile.currencylistener.service.EventService;
import ru.neoflex.tomatophile.currencylistener.service.SubscribeService;

@RestController
@RequiredArgsConstructor
public class SubscribeController {

    private final SubscribeService subscribeService;

    private final EventService eventService;

    @PostMapping("/subscribe/update")
    public ResponseEntity<Subscribe> subscribeOnUpdate(@RequestBody Subscribe subscribe) {
        subscribeService.subscribeOnUpdate(subscribe);
        return ResponseEntity.ok(subscribe);
    }

    @PostMapping("/subscribe/fall")
    public ResponseEntity<Subscribe> subscribeOnFall(@RequestBody Subscribe subscribe) {
        subscribeService.subscribeOnFall(subscribe);
        return ResponseEntity.ok(subscribe);
    }

    @PostMapping("/unsubscribe/update")
    public ResponseEntity<Subscribe> unsubscribeOnUpdate(@RequestBody Subscribe subscribe) {
        if(subscribeService.unsubscribeOnUpdate(subscribe)){
            return ResponseEntity.ok(subscribe);
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/get")
    public ResponseEntity<Event> getOne(@RequestParam String figi) {
        var event = eventService.getOne(figi);
        if (event != null) {
            return ResponseEntity.ok(eventService.getOne(figi));
        }
        return ResponseEntity.notFound().build();
    }

}
