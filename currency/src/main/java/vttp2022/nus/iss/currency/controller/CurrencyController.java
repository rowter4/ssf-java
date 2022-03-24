package vttp2022.nus.iss.currency.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import vttp2022.nus.iss.currency.model.Currency;
import vttp2022.nus.iss.currency.service.CurrencyService;


@Controller
@RequestMapping(path="")
public class CurrencyController {
    
    @Autowired
    private CurrencyService currSvc;

    @GetMapping
    public String getCurrency(Model model) {

        List<Currency> currencyList = currSvc.getAllCurrencies();

        // System.out.println(">>>> currencyList in controller : " + currencyList); // will not able to read the actual content of the json,
                                                                                 // only the memory address only
        model.addAttribute("currencies", currencyList);
        return "index"; 
    }


    @GetMapping(path="/convert") 
    public String getConversion (   @RequestParam(name="from_currency") String fromCurrency, 
                                    @RequestParam(name="to_currency") String toCurrency, 
                                    @RequestParam(name="amount") Double amount,
                                    Model model ) {


        // function for the conversion
        Double getConversionRate = currSvc.convertCurrency(fromCurrency, toCurrency);
        
        Double result;
        result = getConversionRate * amount;

        model.addAttribute("fromCurrency", fromCurrency);
        model.addAttribute("toCurrency", toCurrency);
        model.addAttribute("initialAmount", amount);
        model.addAttribute("result", result);

        return "convert";
    }
}
