package org.newdawn.test.core;

public class Square extends Shape {
	public Square(boolean isBillboard, String texture) {
		super(new float[] { -1f, 1f, 0f,
				-1f, -1f, 0f,
				1f, -1f, 0f,
				1f, 1f, 0f,},
			 new float[] { 0.0f, 0.0f, 
				0.0f, 1.0f, 
				1.0f, 1.0f, 
				1.0f, 0.0f, 
							},
			 new short[] { 
				0, 1, 2, 
				2, 3, 0, 
						}, 
			 true, new float[] {1,1,1,1}, texture, isBillboard);
	}
}
