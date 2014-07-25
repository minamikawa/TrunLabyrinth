package com.example.turnlabyrinth;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;


public class MainActivity extends Activity implements SensorEventListener {


	private SensorManager sensorManager;
	private Sensor accelerometer;
	private MainView view;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

		view = new MainView(this);
		setContentView(view);
        
		sensorManager=(SensorManager)getSystemService(Context.SENSOR_SERVICE);

		List<Sensor> list;
		list=sensorManager.getSensorList(Sensor.TYPE_ACCELEROMETER);
		if (list.size()>0) accelerometer=list.get(0);
	}
	
	protected void onResume() 
	{
		super.onResume();
		if (accelerometer!=null) sensorManager.registerListener(this,accelerometer,SensorManager.SENSOR_DELAY_FASTEST);
	}

	protected void onStop()
	{
		sensorManager.unregisterListener(this);
		super.onStop();
	}    

	public void onSensorChanged(SensorEvent event)
	{
		
		if (event.sensor==accelerometer)  view.setAcce(-event.values[0]*0.9f, event.values[1]*0.9f);
	}

	public void onAccuracyChanged(Sensor sensor,int accuracy){}

	public void onDestroy()
	{
		super.onDestroy();
	}

}
