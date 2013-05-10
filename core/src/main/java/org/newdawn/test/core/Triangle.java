package org.newdawn.test.core;

public class Triangle extends Shape {
	public Triangle(boolean isBillboard) {
		super(new float[] {0.0f,  0.622008459f, 0.0f,
			-0.5f, -0.311004243f, 0.0f,
			0.5f, -0.311004243f, 0.0f }, new short[] { 0, 1, 2 }, true, new float[]{0,1,0,1}, isBillboard);
	}
}
