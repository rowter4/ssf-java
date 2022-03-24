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
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import vttp2022.nus.iss.currency.model.Currency;

@Service
public class CurrencyService {

    private static final String URL = "https://free.currconv.com/api/v7/%s";
    
    // @Value("${currency.api.key")
    private String apiKey = "1173d2ccdd06ee455449";


    // this function is to initialise the function when the page is being loaded
    public List<Currency> getAllCurrencies() { 

        String currencyUrl = UriComponentsBuilder
                            .fromUriString(URL.formatted("/countries"))
                            .queryParam("apiKey", apiKey)
                            .toUriString();
        
        // System.out.println(">>>>>> Currency URL : " + currencyUrl);
        
        return apiCall(currencyUrl);
    }

    public List<Currency> apiCall(String currencyUrl) {
        RequestEntity<Void> req = RequestEntity.get(currencyUrl)
                                .accept(MediaType.APPLICATION_JSON)
                                .build();

        RestTemplate template = new RestTemplate();
        ResponseEntity<String> resp = template.exchange(req, String.class); 

        List<Currency> currencyList = new LinkedList<>();        
        // System.out.println(">>>>>> Resp : " + resp);  // read the list json from here only
        
        try {
            currencyList =  Currency.create(resp.getBody());
            
        } catch (IOException e) {
            e.printStackTrace();
        }

        return currencyList;

    } 


    public Double convertCurrency(String fromCurrency, String toCurrency) {
        
        String conversionUrl = UriComponentsBuilder.fromUriString(URL.formatted("/convert"))
                                .queryParam("q", fromCurrency + "_" + toCurrency)
                                .queryParam("compact", "ultra")
                                .queryParam("apiKey", apiKey)
                                .toUriString();
        
        System.out.println(">>>>>> Conversion URL : " + conversionUrl);
        return apiCall2(conversionUrl);
    }

    public Double apiCall2(String conversionUrl) {
        RequestEntity<Void> req = RequestEntity.get(conversionUrl)
                                .accept(MediaType.APPLICATION_JSON)
                                .build();

        RestTemplate template = new RestTemplate();
        ResponseEntity<String> resp = template.exchange(req, String.class); 

        Double conversionRate = (double) 0 ;        

        try {
            conversionRate =  Currency.convert(resp.getBody());
            System.out.println(">>>> conversionRate  : " + conversionRate);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return conversionRate;

    } 

    
    
}
