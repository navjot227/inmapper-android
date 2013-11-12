package com.example.sampleone;

public class MobilePointTo {

    private Double x;
    private Double y;
    private Double z;
    private Double heading;

    MobilePointTo() {

    }

    public MobilePointTo(Double x, Double y, Double z, Double heading) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.heading = heading;
    }

    public Double getX() {
        return x;
    }

    public void setX(Double x) {
        this.x = x;
    }

    public Double getY() {
        return y;
    }

    public void setY(Double y) {
        this.y = y;
    }

    public Double getZ() {
        return z;
    }

    public void setZ(Double z) {
        this.z = z;
    }

    public Double getHeading() {
        return heading;
    }

    public void setHeading(Double heading) {
        this.heading = heading;
    }

}
