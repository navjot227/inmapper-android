package com.example.sampleone;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJacksonHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

public class MappingService {
    private static final List<HttpMessageConverter<?>> CONVERTERS;

    static {
        StringHttpMessageConverter stringConverter = new StringHttpMessageConverter();
        MappingJacksonHttpMessageConverter jsonConverter = new MappingJacksonHttpMessageConverter();
        CONVERTERS = new ArrayList<HttpMessageConverter<?>>();
        CONVERTERS.add(stringConverter);
        CONVERTERS.add(jsonConverter);
    }

    private RestTemplate createClient() {
        RestTemplate client = new RestTemplate();
        client.setMessageConverters(CONVERTERS);
        return client;
    }

    public static class RoomPointList {

        private Map<String, List<RoomPointTo>> userMappings;

        public RoomPointList() {
        }

        public Map<String, List<RoomPointTo>> getUserMappings() {
            return userMappings;
        }
    };

    public RoomPointList requestMapping() {
        String url = "http://10.0.2.2:8080/api/v/sample_room/mappings";
        RoomPointList room_points;
        // Create a new RestTemplate instance
        RestTemplate restTemplate = createClient();

        // Add the String message converter
        // restTemplate.getMessageConverters().add(
        // new StringHttpMessageConverter());

        // Make the HTTP GET request, marshaling the response to a String
        room_points = restTemplate.getForObject(url, RoomPointList.class,
                "sample_room");
        return room_points;

    }
}
