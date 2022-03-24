package vttp2022.nus.iss.currency.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import vttp2022.nus.iss.currency.model.Currency;
import vttp2022.nus.iss.currency.service.CurrencyService;


@Controller
@RequestMapping(path="/")
public class CurrencyController {
    
    @Autowired
    private CurrencyService currSvc;

    @GetMapping
    public String getCurrency(Model model) {

        List<Currency> currencyList = currSvc.getAllCurrencies();

        System.out.println(">>>> currencyList in controller : " + currencyList);
        model.addAttribute("currencies", currencyList);
        return "index";
        
    }
}
