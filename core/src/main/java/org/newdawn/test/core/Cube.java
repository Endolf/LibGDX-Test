package org.newdawn.test.core;

public class Cube extends Shape {
	public Cube(String textureLocation, boolean isTwoSided, float[] colour) {
		super();
		
		setVertices(new float[] { 
				-1f, -1f, -1f,
				-1f, -1f, 1f,
				1f, -1f, 1f,
				1f, -1f, -1f, 
				-1f, 1f, -1f,
				-1f, 1f, 1f, 
				1f, 1f, 1f, 
				1f, 1f, -1f, 
				-1f, -1f, -1f, 
				-1f, 1f, -1f,
				1f, 1f, -1f,
				1f, -1f, -1f, 
				-1f, -1f, 1f, 
				-1f, 1f, 1f, 
				1f, 1f, 1f, 
				1f, -1f, 1f, 
				-1f, -1f, -1f,
				-1f, -1f, 1f,
				-1f, 1f, 1f, 
				-1f, 1f, -1f,
				1f, -1f, -1f, 
				1f, -1f, 1f, 
				1f, 1f, 1f,
				1f, 1f, -1f,
			});
		setTextureCoords(new float[] { 
				1.0f, 0.0f, 
				1.0f, 1.0f, 
				0.0f, 1.0f, 
				0.0f, 0.0f, 
				0.0f, 0.0f, 
				0.0f, 1.0f, 
				1.0f, 1.0f, 
				1.0f, 0.0f, 
				1.0f, 1.0f, 
				1.0f, 0.0f, 
				0.0f, 0.0f, 
				0.0f, 1.0f, 
				0.0f, 1.0f, 
				0.0f, 0.0f, 
				1.0f, 0.0f, 
				1.0f, 1.0f,
				0.0f, 1.0f, 
				1.0f, 1.0f, 
				1.0f, 0.0f, 
				0.0f, 0.0f, 
				1.0f, 1.0f, 
				0.0f, 1.0f, 
				0.0f, 0.0f, 
				1.0f, 0.0f,				
			});
		setIndecies(new short[] { 
				0, 2, 1, 
				0, 3, 2, 
				4, 5, 6, 
				4, 6, 7, 
				8, 9, 10, 
				8, 10, 11, 
				12, 15, 14, 
				12, 14, 13, 
				16, 17, 18, 
				16, 18, 19,
				20, 22, 21,
				20, 23, 22, 
			});
		setNormals(new float[] { 
				0f, -1f, 0f,
				0f, -1f, 0f,
				0f, -1f, 0f,
				0f, -1f, 0f,
				0f, 1f, 0f,
				0f, 1f, 0f, 
				0f, 1f, 0f, 
				0f, 1f, 0f, 
				0f, 0f, -1f, 
				0f, 0f, -1f, 
				0f, 0f, -1f, 
				0f, 0f, -1f, 
				0f, 0f, 1f, 
				0f, 0f, 1f, 
				0f, 0f, 1f, 
				0f, 0f, 1f, 
				-1f, 0f, 0f,
				-1f, 0f, 0f,
				-1f, 0f, 0f,
				-1f, 0f, 0f,
				1f, 0f, 0f,
				1f, 0f, 0f,
				1f, 0f, 0f,
				1f, 0f, 0f,
			});
		setTwoSided(isTwoSided);
		setColour(colour);
		setTextureLocation(textureLocation);
		
		initialise();
	}
}
