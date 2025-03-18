package beintelliplattformsdk.beintelliplatformjavaexample;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import beintelliplattformsdk.beintelliplatformjavaexample.utils.TimeFrame;
import beintelliplattformsdk.beintelliplatformjavaexample.utils.Weather;


@Controller
public class WeatherController {
    
    private WeatherAPI weatherAPI;
    private final int rows = 8; //its a random number of rows i liked 


    @GetMapping("/")
    public String login(){
        return "redirect:/login";
    }

    /**
     * Initiates the weather website by setting the weather API and loading the default Website.
     * Is only accessed after login.
     * @param accessToken needed by weather API for authentication
     * @param model
     * @return initial weather table website
     */
    @GetMapping("/weatherInitial")
    public String weatherInitial(@RequestParam String accessToken,Model model){
        this.weatherAPI = new WeatherAPI(accessToken);
        return "redirect:/weather";
    }

    /**
     * Initiates the default weather website 
     * @param model
     * @return default weather-table-website
     */
    @GetMapping("/weather")
    public String weatherDefault(Model model){
        return weather(model, new TimeFrame("2020-01-01", "2024-01-01"));
    }

    /**
     * Update weather-data for given timeframe
     * @param model
     * @param timeFrame 
     * @return weather-table-website
     */
    @PostMapping("/weather")
    public String weather(Model model, TimeFrame timeFrame){   
        
        //request weather-data
        ArrayList<Weather> weathers;
        try {
            LocalDateTime startTime = LocalDate.parse(timeFrame.startTime()).atStartOfDay();
            LocalDateTime endTime = LocalDate.parse(timeFrame.endTime()).atStartOfDay();
            weathers = weatherAPI.getWeather(startTime, endTime);
        } catch (Exception e) {
            return "redirect:/weather";
        }
        
        //change website-attributes
        List<Weather> subList = weathers.subList(0,Math.min(weathers.size(), rows));
        model.addAttribute("timeFrame", timeFrame);
        model.addAttribute("weathers", subList);
        return "weather";
        
    }





}
