package org.newdawn.test.core;

import com.badlogic.gdx.math.Matrix4;

public interface Renderable {
	public void render(Matrix4 viewMatrix, Matrix4 projectionMatrix);

	public void dispose();
}
