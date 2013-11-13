package com.example.sampleone;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.View;

public class CustomDrawableView extends View {

    private Collection<List<RoomPointTo>> roomPoints;

    public Collection<List<RoomPointTo>> getRoomPoints() {
        return roomPoints;
    }

    public void setRoomPoints(Collection<List<RoomPointTo>> roomPoints) {
        this.roomPoints = roomPoints;
    }

    public CustomDrawableView(Context context) {
        super(context);
    }

    @Override
    protected void onDraw(Canvas canvas) {

        Paint p = new Paint(Paint.ANTI_ALIAS_FLAG);
        System.out.println("getHeight() " + getHeight());
        System.out.println("getWidth() " + getWidth());
        float[] arr = { 5, 5, 5, getHeight() - 5, 5, getHeight() - 5, getWidth() - 5, getHeight() - 5, getWidth() - 5,
                getHeight() - 5, getWidth() - 5, 5, getWidth() - 5, 5, 5, 5 };
        Iterator<List<RoomPointTo>> iterator;
        p.setColor(Color.BLACK);
        p.setStyle(Paint.Style.STROKE);

        canvas.drawLines(arr, 0, 16, p);
        p.setStyle(Paint.Style.FILL);
        // canvas.drawRect(0, 0, getWidth(), getHeight(), p);

        if (roomPoints != null) {
            iterator = roomPoints.iterator();
            for (RoomPointTo roomPointTo : iterator.next()) {

                // canvas.drawPoint(roomPointTo.getX().floatValue(),
                // roomPointTo.getY().floatValue(), p);
                canvas.drawCircle(roomPointTo.getX().floatValue() * 7, roomPointTo.getY().floatValue() * 7, 7, p);

            }
        }
    }

}
