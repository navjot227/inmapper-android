package com.example.sampleone;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;

public class MapActivity extends Activity {
    private CustomDrawableView mCustomDrawableView;
    private final Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        mCustomDrawableView = new CustomDrawableView(this);

        setContentView(mCustomDrawableView);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.map, menu);
        doTheAutoRefresh();
        return true;
    }

    public void doTheAutoRefresh() {
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                mCustomDrawableView.changeShape();
                mCustomDrawableView.invalidate();
                doTheAutoRefresh();
            }
        }, 1000);
    }
}
