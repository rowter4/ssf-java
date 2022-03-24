package sg.edu.nus.iss.pokemon.models;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.LinkedList;
import java.util.List;

import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;

public class Pokemon {
    private String name;
    private Integer order; //for testing purposes
    

    private List<String> images = new LinkedList<>();

    private static String[] IMAGES = { "sprites", "versions", "generation-i", "red-blue" };


    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public List<String> getImages() { return images; }
    public void setImages(List<String> images) { this.images = images; }

    public void addImage(String img) { this.images.add(img); }

    public Integer getOrder() {
        return order;
    }
    public void setOrder(Integer order) {
        this.order = order;
    }

    @Override
    public String toString() {
        return "Pokemon [images=" + images + ", name=" + name + "]";
    }

    public static Pokemon create(String json) throws IOException{ // this IOException is being linked to the service
        Pokemon p = new Pokemon();
        try (InputStream is = new ByteArrayInputStream(json.getBytes())) {
            JsonReader r = Json.createReader(is);
            JsonObject o = r.readObject();

            // System.out.println("r = " + r);
            System.out.println("o = " + o);  

            p.setName(o.getString("name"));
            p.setOrder(o.getInt("order")); // testing in case need to add order as another parameter. 

            // this is based on the API response -> improved code is listed as of below
            // o = o.getJsonObject("sprites");
            // o = o.getJsonObject("versions");
            // o = o.getJsonObject("generation-i");
            // o = o.getJsonObject("red-blue");
            
            for (String i: IMAGES) 
                o = o.getJsonObject(i);

            
            List<String> l = o.values().stream()
                            .filter( v -> {
                                return v != null;
                            })
                            .map( v -> {
                                return v.toString().replaceAll("\"", ""); // this is based from what it is returned from the API in order to get the Pokemon Images
                            })
                            .toList();
            
            p.setImages(l);


        } 

        return p;
    }
     
}
