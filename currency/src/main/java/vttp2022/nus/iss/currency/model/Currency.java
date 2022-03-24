package vttp2022.nus.iss.currency.model;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;
import jakarta.json.JsonValue;

public class Currency {

    /* based on the API call, there is a need to make the Currency object

        "alpha3": "AFG",
        "currencyId": "AFN",
        "currencyName": "Afghan afghani",
        "currencySymbol": "؋",
        "id": "AF",
        "name": "Afghanistan"

    */

    public String alpha3;
    public String currencyId;
    public String currencyName;
    public String currencySymbol;
    public String id;
    public String name;

    public String getAlpha3() { return alpha3; }
    public void setAlpha3(String alpha3) { this.alpha3 = alpha3; }

    public String getCurrencyId() { return currencyId; }
    public void setCurrencyId(String currencyId) { this.currencyId = currencyId; }

    public String getCurrencyName() { return currencyName; }
    public void setCurrencyName(String currencyName) { this.currencyName = currencyName; }

    public String getCurrencySymbol() { return currencySymbol; }
    public void setCurrencySymbol(String currencySymbol) { this.currencySymbol = currencySymbol; }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    
    
    public static List<Currency> create(String json) throws IOException {
        
        List<Currency> currencyList = new LinkedList<>();

        try(InputStream is = new ByteArrayInputStream(json.getBytes())) {
            JsonReader r = Json.createReader(is);
            JsonObject o = r.readObject();
            JsonObject countries = o.getJsonObject("results");

            for (String key: countries.keySet()) {
                Currency currency = new Currency();
                JsonObject keyValue = countries.getJsonObject(key);

                // adding each info from the json to the list
                currency.alpha3 = keyValue.getString("alpha3");
                currency.currencyId = keyValue.getString("currencyId");
                currency.currencyName = keyValue.getString("currencyName");
                currency.currencySymbol = keyValue.getString("currencySymbol");
                currency.id = keyValue.getString("id");
                currency.name = keyValue.getString("name");

                currencyList.add(currency);
            }
        }

        return currencyList;
    }


    public static Double convert(String json) throws IOException {

        Double conversionRate2 = (double) 0;

        try(InputStream is = new ByteArrayInputStream(json.getBytes())) {
            JsonReader r = Json.createReader(is);
            JsonObject o = r.readObject();
            Collection<JsonValue> values = o.values();

            for (JsonValue value: values) {
                conversionRate2 = Double.parseDouble(value.toString());
            }

        }
        return conversionRate2;
    }

    
}
