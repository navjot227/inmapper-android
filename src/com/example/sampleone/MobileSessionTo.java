package com.example.sampleone;

import java.util.Collection;

public class MobileSessionTo {

    private String token;
    private String roomId;
    private String userHeight;
    private Collection<MobilePointTo> positions;

    MobileSessionTo() {
    }

    public MobileSessionTo(String token, String roomId, String userHeight,
            Collection<MobilePointTo> positions) {
        this.token = token;
        this.roomId = roomId;
        this.userHeight = userHeight;
        this.positions = positions;
    }

    public String getRoomId() {
        return this.roomId;
    }

    public String getToken() {
        return this.token;
    }

    public String getUserHeight() {
        return this.userHeight;
    }

    public Collection<MobilePointTo> getPositions() {
        return this.positions;
    }

}
