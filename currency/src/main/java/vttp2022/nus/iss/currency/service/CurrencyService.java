package vttp2022.nus.iss.currency.service;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import vttp2022.nus.iss.currency.model.Currency;

@Service
public class CurrencyService {

    private static final String URL = "https://free.currconv.com/api/v7/%s";
    
    // @Value("${currency.api.key")
    private String apiKey = "1173d2ccdd06ee455449";


    public List<Currency> getAllCurrencies() {

        String currencyUrl = UriComponentsBuilder
                            .fromUriString(URL.formatted("/countries"))
                            .queryParam("apiKey", apiKey)
                            .toUriString();
        
        System.out.println(">>>>>> Currency URL : " + currencyUrl);
        
        return apiCall(currencyUrl);
    }

    public List<Currency> apiCall(String currencyUrl) {
        RequestEntity<Void> req = RequestEntity.get(currencyUrl)
                                .accept(MediaType.APPLICATION_JSON)
                                .build();

        RestTemplate template = new RestTemplate();
        ResponseEntity<String> resp = template.exchange(req, String.class); 

        List<Currency> currencyList = new LinkedList<>();        
        System.out.println(">>>>>> Resp : " + resp); 
        
        try {
            currencyList =  Currency.create(resp.getBody());
        } catch (IOException e) {
            e.printStackTrace();
        }

        return currencyList;

    } 

    
    
}
