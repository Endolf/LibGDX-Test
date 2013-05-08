package org.newdawn.test.core;

public class Cube extends Shape {
	public Cube() {
		super(new float[] { 1f, 1f, 1f, -1f, 1f, 1f, -1f, -1f, 1f,
				1f, -1f, 1f, 1f, 1f, -1f, -1f, 1f, -1f, -1f, -1f, -1f, 1f, -1f,
				-1f }, new short[] { 0, 1, 3, 
									 2, 3, 1, 
									 3, 2, 6, 
									 1, 6, 2, 
									 6, 1, 5,
									 0, 5, 1, 
									 5, 0, 4, 
									 3, 4, 0, 
									 4, 3, 7, 
									 6, 7, 3, 
									 7, 6, 4, 
									 5, 4, 6
									 }, false, new float[] {1,0,0,1});
	}
}
