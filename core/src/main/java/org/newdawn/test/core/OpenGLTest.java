package org.newdawn.test.core;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.Application;
import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;

public class OpenGLTest implements ApplicationListener {
	
	private Camera camera;
	private Cube cube;
	private float runTime;
	
	@Override
	public void create () {
		Gdx.app.setLogLevel(Application.LOG_DEBUG);
		Gdx.app.debug("init", "In create");
		cube = new Cube();
	}

	@Override
	public void resize (int width, int height) {
		Gdx.app.debug("init", "In resize");
		Gdx.gl20.glClearColor(0, 0, 0, 1);
		Gdx.gl20.glEnable(GL20.GL_DEPTH_TEST);
		Gdx.gl20.glDepthFunc(GL20.GL_LESS);
		
		camera = new PerspectiveCamera(67, width, height);
		camera.position.set(0,0,10);
		camera.lookAt(0,0,0);
		camera.update();
	}

	@Override
	public void render () {
		Gdx.gl20.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);
		
		runTime += Gdx.graphics.getDeltaTime();
		
        float deltaTime =  (runTime % 2) / 2;
        float offset = (float) Math.sin(deltaTime * 2 * Math.PI);
        cube.setPosition(offset,0,0);
        
        cube.render(camera.view, camera.projection);
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
