package com.eremin.cuberotater;

import android.os.Bundle;
import android.app.Activity;
import android.hardware.SensorManager;
import android.hardware.SensorListener;
import android.opengl.GLSurfaceView;


public class CubeRotaterActivity extends Activity implements SensorListener
{
	private GLSurfaceView glView;
	private CubeRenderer renderer;
	SensorManager sm = null;

	@Override
	public void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);

		renderer = new CubeRenderer(this);
		glView = new GLSurfaceView(this);
		glView.setEGLConfigChooser(false);
		glView.setRenderer(renderer);
		glView.setRenderMode(GLSurfaceView.RENDERMODE_CONTINUOUSLY);
		setContentView(glView);
		sm = (SensorManager) getSystemService(SENSOR_SERVICE);   
	}

	public void onSensorChanged(int sensor, float[] values) 
	{
		synchronized (this) {
			switch (sensor) {
			case SensorManager.SENSOR_ACCELEROMETER:
				renderer.acc[0] = values[0];
				renderer.acc[1] = values[1];
				renderer.acc[2] = values[2];
				break;
			case SensorManager.SENSOR_ORIENTATION:
				renderer.acc[3] = values[0];
				break;
				
			default:
				break;
			}
		}         
	}

	public void onAccuracyChanged(int sensor, int accuracy) 
	{
	}

	@Override
	protected void onResume() 
	{
		super.onResume();
		sm.registerListener(this, SensorManager.SENSOR_ACCELEROMETER | SensorManager.SENSOR_ORIENTATION, SensorManager.SENSOR_DELAY_FASTEST);
	}

	@Override
	protected void onStop() 
	{
		sm.unregisterListener(this);
		super.onStop();
	}   
}