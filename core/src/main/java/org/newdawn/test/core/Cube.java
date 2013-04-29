package org.newdawn.test.core;

import com.badlogic.gdx.graphics.Mesh;
import com.badlogic.gdx.graphics.VertexAttribute;
import com.badlogic.gdx.graphics.VertexAttributes.Usage;

public class Cube {
	private Mesh mesh;
	
	public Cube() {
		mesh = new Mesh(true, 8, 14, new VertexAttribute(Usage.Position, 3, "a_position"));
		
		mesh.setVertices(new float[] {1f,1f, 1f,
				-1f, 1f, 1f,
				-1f, -1f, 1f,
				1f,-1f,1f,
				1f,1f, -1f,
				-1f, 1f, -1f,
				-1f, -1f, -1f,
				1f,-1f,-1f});
		
		mesh.setIndices(new short[] {0,1,3,2,6,1,5,0,4,3,7,6,4,5});
		
	}
}
