package org.newdawn.test.core;

public class Cube extends Shape {
	public Cube() {
		super(new float[] { -1f, -1f, -1f, -1f, -1f, 1f, 1f, -1f, 1f, 1f, -1f, -1f, -1f, 1f, -1f,
				-1f, 1f, 1f, 1f, 1f, 1f, 1f, 1f, -1f, -1f, -1f, -1f, -1f, 1f, -1f, 1f, 1f, -1f,
				1f, -1f, -1f, -1f, -1f, 1f, -1f, 1f, 1f, 1f, 1f, 1f, 1f, -1f, 1f, -1f, -1f, -1f,
				-1f, -1f, 1f, -1f, 1f, 1f, -1f, 1f, -1f, 1f, -1f, -1f, 1f, -1f, 1f, 1f, 1f, 1f,
				1f, 1f, -1f,},
			 new float[] { 0.0f, 0.0f, 0.0f, 1.0f, 1.0f, 1.0f, 1.0f, 0.0f, 1.0f, 0.0f, 1.0f, 1.0f, 0.0f, 1.0f, 0.0f, 0.0f, 0.0f,
				0.0f, 0.0f, 1.0f, 1.0f, 1.0f, 1.0f, 0.0f, 0.0f, 0.0f, 0.0f, 1.0f, 1.0f, 1.0f, 1.0f, 0.0f, 0.0f, 0.0f, 0.0f, 1.0f, 1.0f,
				1.0f, 1.0f, 0.0f, 0.0f, 0.0f, 0.0f, 1.0f, 1.0f, 1.0f, 1.0f, 0.0f,				
							},
			 new short[] { 0, 2, 1, 0, 3, 2, 4, 5, 6, 4, 6, 7, 8, 9, 10, 8, 10, 11, 12, 15, 14, 12, 14, 13, 16, 17, 18, 16, 18, 19,
				20, 23, 22, 20, 22, 21
						}, 
			 false, new float[] {1,0,0,1}, "nd-logo.png");
	}
}
