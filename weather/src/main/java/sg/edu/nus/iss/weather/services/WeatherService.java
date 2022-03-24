package sg.edu.nus.iss.weather.services;

import java.io.IOException;
import java.util.Optional;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import sg.edu.nus.iss.weather.models.Weather;

@Service
public class WeatherService {

    // @Value("${open.weather.map}")
    private String apiKey;

    private boolean hasKey;
    private static final String WEATHER_URL = "https://api.openweathermap.org/data/2.5/weather";

    // @PostConstruct  // this is to initialise the weather key. This will be called after the object is being created
    // public void init() {
    //     hasKey = null != apiKey;
    //     System.out.println(">>>> API key set: " + hasKey);
    // }

    @PostConstruct
    public void init() {
        // apiKey = System.getenv("OPEN_WEATHER_MAP");
        apiKey = "8588b7f8ed5483c602987bff9e5ea1e8" ;
        hasKey = null != apiKey;
    }

    public Optional<Weather> getWeather(String city) {
        String url = UriComponentsBuilder.fromUriString(WEATHER_URL)
                                            .queryParam("q", city)
                                            .queryParam("appid", apiKey)
                                            .toUriString();

        RequestEntity<Void> req = RequestEntity.get(url)
                                    .accept(MediaType.APPLICATION_JSON)
                                    .build();
        
        RestTemplate template = new RestTemplate();
        

        System.out.println(">>>>> apiKey " + apiKey);
        System.out.println(">>>>> city " + city);
        System.out.println(">>>>> url " + url);
        
        try {
            ResponseEntity<String> resp = template.exchange(req, String.class); // response from API call
            Weather w = Weather.create(resp.getBody());

            System.out.println(">>>>> resp " + resp);
            System.out.println(">>>>> resp Body " + resp.getBody());

            return Optional.of(w);

        } catch (IOException e) {
            e.printStackTrace();
        }
        

        return Optional.empty();
    }
   
}
