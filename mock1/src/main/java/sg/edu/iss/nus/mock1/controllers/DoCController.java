package sg.edu.iss.nus.mock1.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import sg.edu.iss.nus.mock1.services.DoCService;
import org.springframework.http.MediaType;

import sg.edu.iss.nus.mock1.models.Deck;



@Controller
@RequestMapping(path="/deck")
public class DoCController {
    
    @Autowired
    private DoCService docSvc;

    @PostMapping(consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public String postDeck (Model model) {
        
        Deck deck = docSvc.createDeck();
        model.addAttribute("deck", deck);
        model.addAttribute("cards", List.of());
        model.addAttribute("action", "/deck/%s".formatted(deck.getDeckId()));

        System.out.println(">>>>>>>>>>>> Deck " + deck);
        System.out.println(">>>>>>>>>>>> Cards " + List.of());
        System.out.println(">>>>>>>>>>>> Model " + model);

        return "card_game";
    }

    @PostMapping(path = "/{deckId}" , consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public String postDeckId(@PathVariable String deckId, @RequestBody MultiValueMap<String,String> form, Model model ) {

        Integer count = Integer.parseInt(form.getFirst("draw_count")); // this is from the html selected

        Deck deck = docSvc.drawCards(deckId, count);

        model.addAttribute("deck", deck);
        model.addAttribute("cards", deck.getCards());  
        
        System.out.println(">>>>>>>>>>>> Deck when drawing cards " + deck);
                                
        return "card_game";

    }

}
