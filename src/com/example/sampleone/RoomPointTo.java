package com.example.sampleone;

public class RoomPointTo {

    private String roomId;
    private String mobileId;
    private Double x;
    private Double y;

    public RoomPointTo() {
    }

    public RoomPointTo(String roomId, String mobileId, Double x, Double y) {
        this.roomId = roomId;
        this.mobileId = mobileId;
        this.x = x;
        this.y = y;
    }

    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }

    public void setMobileId(String mobileId) {
        this.mobileId = mobileId;
    }

    public void setX(Double x) {
        this.x = x;
    }

    public void setY(Double y) {
        this.y = y;
    }

    public String getRoomId() {
        return this.roomId;
    }

    public String getMobileId() {
        return this.mobileId;
    }

    public Double getX() {
        return this.x;
    }

    public Double getY() {
        return this.y;
    }

}