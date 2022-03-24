package sg.edu.iss.nus.mock1.services;

import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import sg.edu.iss.nus.mock1.models.Deck;

@Service
public class DoCService {

    private static final String BASE_URL = "https://deckofcardsapi.com/api/deck%s";
    private static final String SHUFFLE = "/new/shuffle";
    private static final String DRAW = "/%s/draw";

    public Deck createDeck() {
        return createDeck(1);
    }
    
    public Deck createDeck(int count) {
        String newDeck = UriComponentsBuilder.fromUriString(BASE_URL.formatted(SHUFFLE))
            .queryParam("deck_count", count)
            .toUriString();
        System.out.println(">>>>>>>>>>>> New Deck Service " + newDeck);
        return invoke(newDeck);
    }

    public Deck drawCards(String deckId, Integer count) {
        String drawFromDeck = UriComponentsBuilder.fromUriString(BASE_URL.formatted(DRAW.formatted(deckId)))
        .queryParam("count", count)
        .toUriString();


        return invoke(drawFromDeck); 
    }
    
    private Deck invoke(String url) { // this is to generate the URL to call the api for the new deck and drawing of cards
        RequestEntity<Void> req = RequestEntity.get(url)
                .accept(MediaType.APPLICATION_JSON)
                .build();
        
        RestTemplate template = new RestTemplate();
        ResponseEntity<String> resp = template.exchange(req, String.class); // response from API call

        System.out.println(">>>>>>>>>>>> Response Service " + resp);
        System.out.println(">>>>>>>>>>>> Response Body Service " + resp.getBody());
        return Deck.create(resp.getBody());

    }
}
