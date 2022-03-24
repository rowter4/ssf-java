package sg.edu.nus.iss.weather.controllers;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import sg.edu.nus.iss.weather.models.Weather;
import sg.edu.nus.iss.weather.services.WeatherService;

@Controller
@RequestMapping(path="/weather")
public class WeatherController {

    @Autowired
    private WeatherService weatherSvc;

    @GetMapping
    public String getWeather(@RequestParam(required = true) String city, Model model) {

        Optional<Weather> opt = weatherSvc.getWeather(city);

        if (opt.isEmpty())
            return "weather";

        System.out.println(">>>>> opt " + opt);
        System.out.println(">>>>> opt get " + opt.get());

        model.addAttribute("weather", opt.get());
        return "weather";
    }
    
}
