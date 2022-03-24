package sg.edu.nus.iss.pokemon.service;

import java.io.IOException;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import sg.edu.nus.iss.pokemon.models.Pokemon;

@Service
public class PokemonService {
    private static final String URL = "https://pokeapi.co/api/v2/pokemon/%s";

    public Optional<Pokemon> findPokemon(String name){
        String toSearch = URL.formatted(name);

        RestTemplate template = new RestTemplate();
        ResponseEntity<String> resp = template.getForEntity(toSearch, String.class);

        if (resp.getStatusCodeValue() >= 400) {
            return Optional.empty();
        }

        try {
            Pokemon pokemon = Pokemon.create(resp.getBody());
            return Optional.of(pokemon);
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        return Optional.empty();
        
    }
}
