package com.example.sampleone;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class SensorsActivity extends Activity implements SensorEventListener {

    private final MappingService service = new MappingService();

    private SensorManager sensorManager;
    private float[] valuesAccelerometer;
    private float[] valuesMagneticField;
    private Context context;

    private float[] matrixR;
    private float[] matrixI;
    private float[] matrixValues;
    EditText roomid, height;
    TextView accx, accy, accz, comp;
    Button start, stop;
    private long lastUpdate;
    private long batchNo = 1;
    private long commitBatchNo;
    boolean startFlag;

    /** Called when the activity is first created. */

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sensors);
        context = this;
        commitBatchNo = batchNo;
        batchNo++;
        roomid = (EditText) findViewById(R.id.editText1);
        height = (EditText) findViewById(R.id.editText2);
        accx = (TextView) findViewById(R.id.textView1);
        accy = (TextView) findViewById(R.id.textView2);
        accz = (TextView) findViewById(R.id.textView3);
        comp = (TextView) findViewById(R.id.textView4);

        start = (Button) findViewById(R.id.start);
        stop = (Button) findViewById(R.id.stop);

        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startFlag = true;
                DbHelper mydb = new DbHelper(context);
                SQLiteDatabase db = mydb.getWritableDatabase();
                db.delete(DbHelper.TABLE_NAME, null, null);

            }

        });
        stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new PostMapPoints().execute();
                startFlag = false;
            }
        });

        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        lastUpdate = System.currentTimeMillis();
        valuesAccelerometer = new float[3];
        valuesMagneticField = new float[3];

        matrixR = new float[9];
        matrixI = new float[9];
        matrixValues = new float[3];
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (startFlag) {
            switch (event.sensor.getType()) {
            case Sensor.TYPE_ACCELEROMETER:
                for (int i = 0; i < 3; i++) {
                    valuesAccelerometer[i] = event.values[i];
                }
                break;
            case Sensor.TYPE_MAGNETIC_FIELD:
                for (int i = 0; i < 3; i++) {
                    valuesMagneticField[i] = event.values[i];
                }
                break;
            }
            long actualTime = System.currentTimeMillis();
            if (actualTime - lastUpdate < 200) {
                return;
            }
            lastUpdate = actualTime;

            boolean success = SensorManager.getRotationMatrix(matrixR, matrixI, valuesAccelerometer,
                    valuesMagneticField);

            if (success) {
                // System.out.println("!!!!Success!!!!");
                SensorManager.getOrientation(matrixR, matrixValues);

                double azimuth = Math.toDegrees(matrixValues[0]);
                if (azimuth < 0) {
                    azimuth += 360;
                }

                accx.setText("X        " + valuesAccelerometer[0]);
                accy.setText("Y        " + valuesAccelerometer[1]);
                accz.setText("Z        " + valuesAccelerometer[2]);
                comp.setText("Heading  " + azimuth);
                System.out.println("Batch_no before insertion = " + batchNo);
                insertDB(valuesAccelerometer[0], valuesAccelerometer[1], valuesAccelerometer[2], azimuth);

            }
        }
    }

    private void insertDB(float x, float y, float z, double azimuth) {

        DbHelper mydb = new DbHelper(this);
        SQLiteDatabase db = mydb.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DbHelper.COLUMN1, x);
        values.put(DbHelper.COLUMN2, y);
        values.put(DbHelper.COLUMN3, z);
        values.put(DbHelper.COLUMN4, azimuth);
        try {
            db.insertOrThrow(DbHelper.TABLE_NAME, null, values);
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
        db.close();

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    @Override
    protected void onResume() {
        super.onResume();
        // register this class as a listener for the orientation and
        // accelerometer sensors
        sensorManager.registerListener(this, sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
                SensorManager.SENSOR_DELAY_NORMAL);
        sensorManager.registerListener(this, sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD),
                SensorManager.SENSOR_DELAY_NORMAL);

    }

    @Override
    protected void onPause() {
        // unregister listener
        super.onPause();
        sensorManager.unregisterListener(this, sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER));
        sensorManager.unregisterListener(this, sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD));
    }

    class PostMapPoints extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... n) {
            service.sendData(SensorsActivity.this, roomid.getText().toString(), height.getText().toString());
            return null;

        }

        @Override
        protected void onPostExecute(Void result) {
            // System.out.println("result.length = " + result.size());
            // TODO: check this.exception
            // TODO: do something with the feed
        }
    }
}
