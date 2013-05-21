package org.newdawn.test.core;

public class Square extends Shape {
	public Square(boolean isBillboard, String texture) {
		super();
		
		setVertices(new float[] { -1f, 1f, 0f,
				-1f, -1f, 0f,
				1f, -1f, 0f,
				1f, 1f, 0f,});
		
		setTextureCoords(new float[] { 0.0f, 0.0f, 
				0.0f, 1.0f, 
				1.0f, 1.0f, 
				1.0f, 0.0f, 
							});
		setIndecies(new short[] { 
				0, 1, 2, 
				2, 3, 0, 
						});
		setTwoSided(true);
		setEmissiveColour(new float[] {1,1,1,1});
		setTextureLocation(texture);
		setBillboard(isBillboard);
		setNormals(new float[] {0,0,1,
				0,0,1,
				0,0,1,
				0,0,1,
				});
		
		initialise();
	}
}
