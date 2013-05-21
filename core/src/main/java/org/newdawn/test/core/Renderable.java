package org.newdawn.test.core;

import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector3;

public interface Renderable {
	public void render(Matrix4 viewMatrix, Matrix4 projectionMatrix);
	
	/**
	 * Gets the position of the current shape
	 * @param vec The vector to store the position in to.
	 * @return The vector containing this shapes position.
	 */
	public Vector3 getPosition(Vector3 vec);
	
	public void setPosition(Matrix4 newPosition);

	public void dispose();
}
