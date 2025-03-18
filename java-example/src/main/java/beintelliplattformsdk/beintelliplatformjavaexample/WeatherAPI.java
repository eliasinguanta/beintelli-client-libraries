package beintelliplattformsdk.beintelliplatformjavaexample;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.LocalDateTime;
import java.util.ArrayList;

import org.springframework.boot.json.JsonParser;
import org.springframework.boot.json.JsonParserFactory;

import beintelliplattformsdk.beintelliplatformjavaexample.utils.Weather;


public class WeatherAPI {
    private final String url = "https://api.dev.be-intelli.com/rest/weather_data";
    private final String accessToken;
    
    public WeatherAPI(String accessToken){
       this.accessToken=accessToken;
    }

    /**
     * Requests all weather-data for given time-frame
     * @param startTime
     * @param endTime
     * @return weather-data
     * @throws IOException
     * @throws InterruptedException
     * @throws ClassCastException
     */
    public ArrayList<Weather> getWeather(LocalDateTime startTime, LocalDateTime endTime) throws IOException, InterruptedException, ClassCastException{
        
        //add query-params    
        String queryUrl = String.valueOf(url);
        queryUrl+= '?';
        queryUrl+= "start_tmpstmp" + "=" + startTime.toString();
        queryUrl+= "&";
        queryUrl+= "end_tmpstmp" + "=" + endTime.toString();

        
        //build HttpRequest
        HttpRequest httpRequest = HttpRequest.newBuilder()
        .uri(URI.create(queryUrl))
        .header("Authorization", "Bearer " + this.accessToken)
        .header("maxContentLength", Integer.toString(Integer.MAX_VALUE))
        .header("maxBodyLength", Integer.toString(Integer.MAX_VALUE))
        .GET()
        .build();
                    
        //send request
        HttpResponse<String> response =  HttpClient.newHttpClient().send(httpRequest, HttpResponse.BodyHandlers.ofString());
            
        //unpack Response
        JsonParser jsonParser = JsonParserFactory.getJsonParser();
        ArrayList<Weather> weatherList = castToWeatherList(jsonParser.parseMap(response.body()).get("message"));
            
        return weatherList;
    }

    /**
     * Casts any Object to weatherlist.
     * @param object
     * @return weatherlist
     * @throws ClassCastException
     */
    private ArrayList<Weather> castToWeatherList(Object object) throws ClassCastException{
        ArrayList<ArrayList<Object>> lists = (ArrayList<ArrayList<Object>>) object;
        ArrayList<Weather> result = new ArrayList<>();
        for(ArrayList<Object> objects: lists){
            result.add(
                new Weather(
                    (Integer) objects.get(0),
                    (Double) objects.get(1),
                    (Double) objects.get(2),
                    (Double) objects.get(3),
                    (Double) objects.get(4), 
                    (Double) objects.get(5), 
                    (Double) objects.get(6), 
                    (Double) objects.get(7), 
                    (Double) objects.get(8), 
                    (Double) objects.get(9),
                    (Double) objects.get(10),
                    (String) objects.get(11),
                    (String) objects.get(12), 
                    (String) objects.get(13)
                )
            );
        }
        return result;
    }
}
