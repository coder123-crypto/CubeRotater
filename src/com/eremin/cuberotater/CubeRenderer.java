package com.eremin.cuberotater;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.ShortBuffer;

import javax.microedition.khronos.opengles.GL10;

import android.content.Context;

public class CubeRenderer extends AbstractRenderer
{
	public float[] acc = {0.0f, 0.0f, 0.0f, 0.0f};

	private FloatBuffer vertexBuffer;
	private int numFaces = 6;

	private float[][] colors = { 
			{1.0f, 0.5f, 0.0f, 1.0f}, 
			{1.0f, 0.0f, 1.0f, 1.0f},  
			{0.0f, 1.0f, 0.0f, 1.0f}, 
			{0.0f, 0.0f, 1.0f, 1.0f}, 
			{1.0f, 0.0f, 0.0f, 1.0f},  
			{1.0f, 1.0f, 0.0f, 1.0f}  
	};

	private float[] vertices = { 
			-1.0f, -1.0f,  1.0f, 
			1.0f, -1.0f,  1.0f,  
			-1.0f,  1.0f,  1.0f, 
			1.0f,  1.0f,  1.0f,  
			1.0f, -1.0f, -1.0f,  
			-1.0f, -1.0f, -1.0f, 
			1.0f,  1.0f, -1.0f,  
			-1.0f,  1.0f, -1.0f, 
			-1.0f, -1.0f, -1.0f, 
			-1.0f, -1.0f,  1.0f, 
			-1.0f,  1.0f, -1.0f, 
			-1.0f,  1.0f,  1.0f, 
			1.0f, -1.0f,  1.0f,  
			1.0f, -1.0f, -1.0f,  
			1.0f,  1.0f,  1.0f,  
			1.0f,  1.0f, -1.0f,  
			-1.0f,  1.0f,  1.0f, 
			1.0f,  1.0f,  1.0f,  
			-1.0f,  1.0f, -1.0f, 
			1.0f,  1.0f, -1.0f,  
			-1.0f, -1.0f, -1.0f, 
			1.0f, -1.0f, -1.0f,  
			-1.0f, -1.0f,  1.0f, 
			1.0f, -1.0f,  1.0f  
	};

	public CubeRenderer(Context context)
	{	
		ByteBuffer vbb = ByteBuffer.allocateDirect(vertices.length * 4);
		vbb.order(ByteOrder.nativeOrder()); 
		vertexBuffer = vbb.asFloatBuffer(); 
		vertexBuffer.put(vertices);        
		vertexBuffer.position(0);           
	}

	private float toAngle(float x, float y)
	{
		return (float)Math.toDegrees(Math.atan2(x, y));
	}

	protected void draw(GL10 gl)
	{
		gl.glRotatef(toAngle(-acc[0], -acc[1]), 0.0f, 0.0f, 1.0f);
		gl.glRotatef(toAngle(acc[2], -acc[1]), 1.0f, 0.0f, 0.0f);
		gl.glRotatef(acc[3], 0.0f, 1.0f, 0.0f);
		
		gl.glFrontFace(GL10.GL_CCW);    
		gl.glEnable(GL10.GL_CULL_FACE);
		gl.glCullFace(GL10.GL_BACK);   

		gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
		gl.glVertexPointer(3, GL10.GL_FLOAT, 0, vertexBuffer);

		for (int face = 0; face < numFaces; face++) 
		{
			gl.glColor4f(colors[face][0], colors[face][1], colors[face][2], colors[face][3]);
			gl.glDrawArrays(GL10.GL_TRIANGLE_STRIP, face*4, 4);
		}
		gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);
		gl.glDisable(GL10.GL_CULL_FACE);
	}
}
