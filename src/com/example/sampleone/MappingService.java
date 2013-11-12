package com.example.sampleone;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJacksonHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class MappingService {
    // private static final String SERVICE_URL = "http://10.0.2.2:8080";
    private static final String SERVICE_URL = "http://172.31.34.89:8080";
    private static final String V_TOKEN = SERVICE_URL + "/api/v/token";

    private static final String V_SAMPLE_ROOM_MAPPINGS = SERVICE_URL + "/api/v/sample_room/mappings";

    private static final String V_POSITION_POSITIONS = SERVICE_URL + "/api/v/positions";

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
        String url = V_SAMPLE_ROOM_MAPPINGS;
        RoomPointList room_points;
        // Create a new RestTemplate instance
        RestTemplate restTemplate = createClient();

        // Make the HTTP GET request, marshaling the response to a String
        room_points = restTemplate.getForObject(url, RoomPointList.class, "sample_room");
        return room_points;

    }

    public void sendData(Context context, String roomId, String height) {
        RestTemplate restTemplate = createClient();
        String token = restTemplate.getForObject(V_TOKEN, String.class);

        Collection<MobilePointTo> positions = selectMobilePoints(context);
        MobileSessionTo mobSession = new MobileSessionTo(token, roomId, height, positions);
        restTemplate.postForObject(V_POSITION_POSITIONS, mobSession, String.class);
    }

    private Collection<MobilePointTo> selectMobilePoints(Context context) {
        Collection<MobilePointTo> positions = new ArrayList<MobilePointTo>();
        DbHelper mydb = new DbHelper(context);
        SQLiteDatabase db = mydb.getReadableDatabase();
        String[] cols = { DbHelper.COLUMN1, DbHelper.COLUMN2, DbHelper.COLUMN3, DbHelper.COLUMN4 };

        Cursor cursor = db.query(false, DbHelper.TABLE_NAME, cols, null, null, null, null, null, null);

        System.out.println("cursor.getCount()  = " + cursor.getCount());

        if (cursor.getCount() != 0) {
            while (cursor.moveToNext()) {
                positions.add(new MobilePointTo(cursor.getDouble(0), cursor.getDouble(1), cursor.getDouble(2), cursor
                        .getDouble(3)));

            }
            cursor.close();
            db.delete(DbHelper.TABLE_NAME, null, null);
            db.close();
        }
        return positions;
    }
}
