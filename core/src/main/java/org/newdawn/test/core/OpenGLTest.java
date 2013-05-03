package org.newdawn.test.core;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.Application;
import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;

public class OpenGLTest implements ApplicationListener, InputProcessor {
	
	private Camera camera;
	private float cameraXRot = -45,cameraYRot = 45;
	private Cube cube;
	private float runTime;
	private List<Renderable> renderables = new ArrayList<Renderable>();
	
	@Override
	public void create () {
		Gdx.app.setLogLevel(Application.LOG_DEBUG);
		Gdx.app.debug("init", "In create");
		cube = new Cube();
		
		renderables.add(cube);
		renderables.add(new Cube());
		
		camera = new PerspectiveCamera(67, 1, 1);
		camera.near = 3;
		camera.far = 3000;
		
		Gdx.input.setInputProcessor(this);
	}

	@Override
	public void resize (int width, int height) {
		Gdx.app.debug("init", "In resize");
		Gdx.gl20.glClearColor(0, 0, 0, 1);
		Gdx.gl20.glEnable(GL20.GL_DEPTH_TEST);
		Gdx.gl20.glDepthFunc(GL20.GL_LESS);
		
		camera.viewportWidth = width;
		camera.viewportHeight = height;
		
		rotateCamera(0,0);
	}

	private void rotateCamera(float xRot, float yRot) {
		cameraXRot += xRot;
		cameraYRot += yRot;
		
		if(cameraYRot>180) cameraYRot = cameraYRot - 360;
		if(cameraYRot < -180) cameraYRot = cameraYRot + 360;
		
		if(cameraXRot > 90) cameraXRot = 90;
		if(cameraXRot < -90) cameraXRot = -90;
		
		camera.position.set(0,0,5);
		camera.position.rotate(cameraXRot, 1, 0, 0);
		camera.position.rotate(cameraYRot, 0, 1, 0);
		
		camera.lookAt(0, 0, 0);

		camera.update();
	}

	@Override
	public void render () {
		Gdx.gl20.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);
		
		runTime += Gdx.graphics.getDeltaTime();
		
        double deltaTime =  (runTime % 2) / 2 ;
        float offset = (float) Math.sin(deltaTime * (2 * Math.PI));
        cube.setPosition(offset * 3,0,-3);
        
        for(Renderable renderable : renderables) {
        	renderable.render(camera.view, camera.projection);
        }
	}

	@Override
	public void pause () {
	}

	@Override
	public void resume () {
	}

	@Override
	public void dispose () {
        for(Renderable renderable : renderables) {
        	renderable.dispose();
        }
	}

	@Override
	public boolean keyDown(int keycode) {
		Gdx.app.debug("touch", "keyDown: " + keycode);
		return false;
	}

	@Override
	public boolean keyUp(int keycode) {
		Gdx.app.debug("touch", "keyUp: " + keycode);
		return false;
	}

	@Override
	public boolean keyTyped(char character) {
		Gdx.app.debug("touch", "keyTyped: \"" + character + "\"");
		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		Gdx.app.debug("touch", "touchDown: " + pointer + ", " + button);
		return false;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		Gdx.app.debug("touch", "touchUp: " + pointer + ", " + button);
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		Gdx.app.debug("touch", "touchDragged: " + pointer + ", x: " + Gdx.app.getInput().getDeltaX(pointer) + ",y: " + Gdx.app.getInput().getDeltaY(pointer));
		rotateCamera(-Gdx.app.getInput().getDeltaY(pointer),-Gdx.app.getInput().getDeltaX(pointer));
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
//		Gdx.app.debug("touch", "mouseMoved: ");
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		Gdx.app.debug("touch", "scrolled: ");
		return false;
	}
}
