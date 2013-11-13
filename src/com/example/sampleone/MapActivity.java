package com.example.sampleone;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;

import com.example.sampleone.MappingService.RoomPointList;

public class MapActivity extends Activity {
    private CustomDrawableView mCustomDrawableView;
    private final Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // setContentView(R.layout.activity_map);
        mCustomDrawableView = new CustomDrawableView(this);
        // mCustomDrawableView.changeShape();
        setContentView(mCustomDrawableView);
        new RetreiveMapPoints().execute();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.map, menu);
        // doTheAutoRefresh();
        return true;
    }

    public void doTheAutoRefresh() {
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                mCustomDrawableView.invalidate();
                doTheAutoRefresh();
            }
        }, 1000);
    }

    class RetreiveMapPoints extends AsyncTask<String, Void, RoomPointList> {
        @Override
        protected RoomPointList doInBackground(String... urls) {
            return new MappingService().requestMapping();

        }

        @Override
        protected void onPostExecute(RoomPointList result) {
            mCustomDrawableView.setRoomPoints(result.getUserMappings().values());
            mCustomDrawableView.invalidate();
        }
    }

}
