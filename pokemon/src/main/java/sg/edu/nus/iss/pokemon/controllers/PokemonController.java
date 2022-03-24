package sg.edu.nus.iss.pokemon.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import sg.edu.nus.iss.pokemon.models.Pokemon;
import sg.edu.nus.iss.pokemon.service.PokemonService;

import java.util.Optional;

// Please place this in the browser to look at the type of pokemons
//  http://localhost:8080/pokemon/search?pokemon_name=bulbasaur

@Controller
@RequestMapping(path="/pokemon")
public class PokemonController {

    @Autowired
    private PokemonService pokemonSvc;
    
    @GetMapping(path="/search")
    public String search(
        @RequestParam(name="pokemon_name") String pokemonName, Model model) {
        
        Optional<Pokemon> opt = pokemonSvc.findPokemon(pokemonName);

        if (opt.isEmpty()) {
            return "404";
        }

        Pokemon pokemon = opt.get();
        System.out.println("p = " + pokemon); 
        
        model.addAttribute("pokemonName", pokemonName.toUpperCase());
        model.addAttribute("pokemon", pokemon);

        return "search_result";
    }

}
