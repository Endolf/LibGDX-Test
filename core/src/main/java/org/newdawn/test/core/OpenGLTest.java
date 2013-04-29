package org.newdawn.test.core;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;

public class OpenGLTest implements ApplicationListener {
	
	private Camera camera;
	
	@Override
	public void create () {
	}

	@Override
	public void resize (int width, int height) {
		Gdx.gl20.glClearColor(0, 0, 0, 1);
		
		camera = new PerspectiveCamera(60, width, height);
	}

	@Override
	public void render () {
		Gdx.gl20.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);
		camera.update();
	}

	@Override
	public void pause () {
	}

	@Override
	public void resume () {
	}

	@Override
	public void dispose () {
	}
}
