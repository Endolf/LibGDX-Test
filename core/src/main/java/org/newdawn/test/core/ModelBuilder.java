package org.newdawn.test.core;

import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.materials.Material;
import com.badlogic.gdx.math.Vector3;

public class ModelBuilder extends com.badlogic.gdx.graphics.g3d.utils.ModelBuilder {

	public Model createTriangle(float size, Material material, int attributes) {
		begin();
		part("triangle", GL10.GL_TRIANGLES, attributes, material).triangle(new Vector3(0,size,0), new Vector3(-0.5f, size/2, 0), new Vector3(0.5f, size/2, 0));
		return end();
	}
	
	public Model createSquare(float size, Material material, int attributes) {
		begin();
		part("square", GL10.GL_TRIANGLES, attributes, material).rect(-size/2f, size/2f, 0f,
				-size/2f, -size/2f, 0f,
				size/2f, -size/2f, 0f,
				size/2f, size/2f, 0f,
				0,0,1
				);
		return end();
	}

}
