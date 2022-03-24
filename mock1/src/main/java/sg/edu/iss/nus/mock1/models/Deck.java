package sg.edu.iss.nus.mock1.models;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.LinkedList;
import java.util.List;

import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;

public class Deck {
    // based from deckofcardsapi.com

    private String deckId;
    private Integer remaining;
    private List<Card> cards;


    public String getDeckId() { return deckId;}
    public void setDeckId(String deckId) { this.deckId = deckId; }

    public Integer getRemaining() { return remaining; }
    public void setRemaining(Integer remaining) { this.remaining = remaining; }

    public List<Card> getCards() { return cards; }
    public void setCards(List<Card> cards) { this.cards = cards; }

    public static Deck create(String json) {
        Deck deck = new Deck();

        System.out.println(">>>>>>>>>>>> Json in Deck Class " + json);
        
        try (InputStream is = new ByteArrayInputStream(json.getBytes())) {
            JsonReader reader = Json.createReader(is);
            JsonObject o = reader.readObject();

            deck.deckId = o.getString("deck_id");
            deck.remaining = o.getInt("remaining");

            if (o.containsKey("cards")) {
                List<Card> cards = new LinkedList<>();
                JsonArray cardsArr = o.getJsonArray("cards");

                for (int i=0; i<cardsArr.size(); i++){
                    cards.add(Card.create(cardsArr.getJsonObject(i)));
                }

                deck.cards = cards;
                System.out.println(">>>>>>>>>>>> Deck with Cards Key for drawing " + deck);
            }

        } catch (IOException e ) {
            e.printStackTrace();
        }

        System.out.println(">>>>>>>>>>>> Deck from DeckObject " + deck);
        return deck;
    }
    
}
