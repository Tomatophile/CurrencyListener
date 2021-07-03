package ru.neoflex.tomatophile.currencylistener.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import ru.neoflex.tomatophile.currencylistener.pojo.Cryptocurrency;

@Service
public class CoinMarketCapService {
    @Value("${coinMarketCap.api.headerKey}")
    private String headerKey;
    @Value("${coinMarketCap.api.key}")
    private String key;
    @Value("${coinMarketCap.api.url}")
    private String quotesUrl;

    private final RestTemplate restTemplate;

    public CoinMarketCapService(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder.build();
    }

    public Cryptocurrency getOneByFigi(String figi){
        var url = quotesUrl.concat(figi);

        var headers = new HttpHeaders();
        headers.add(headerKey, key);
        var entity = new HttpEntity<String>(headers);

        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);

        var split = response.getBody().split("\"");

        var currency = new Cryptocurrency();
        currency.setFigi(figi);

        for(var i =0; i< split.length; i++){
            if(split[i].equals("price")){
                currency.setPrice(Double.parseDouble(split[i+1].replaceAll("[ :,\n\t]", "")));
            }
        }

        return currency;
    }
}
