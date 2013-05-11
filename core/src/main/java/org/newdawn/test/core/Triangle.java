package org.newdawn.test.core;

public class Triangle extends Shape {
	public Triangle(boolean isBillboard) {
		super();
		
		setVertices(new float[] {0.0f,  0.622008459f, 0.0f,
				-0.5f, -0.311004243f, 0.0f,
				0.5f, -0.311004243f, 0.0f });
		
		setIndecies(new short[] { 0, 1, 2 });
		setTwoSided(true);
		setColour(new float[]{0,1,0,1});
		setBillboard(isBillboard);
		setNormals(new float[] {0,0,1,
				0,0,1,
				0,0,1});
		
		initialise();
	}
}
