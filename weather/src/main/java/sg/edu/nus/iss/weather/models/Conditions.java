package sg.edu.nus.iss.weather.models;

import jakarta.json.JsonObject;

public class Conditions {
    private String description;
    private String icon;

    public String getDescription() {  return description; }
    public void setDescription(String description) { this.description = description; }

    public String getIcon() { return icon; }
    public void setIcon(String icon) { this.icon = icon; } 

    public static Conditions create(JsonObject o) {
        Conditions c = new Conditions();
        c.description = "%s - %s".formatted(o.getString("main"),
                                            o.getString("description"));   
        return c;
    }
    
    

    
}
