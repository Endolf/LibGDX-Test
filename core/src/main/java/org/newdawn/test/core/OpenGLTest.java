package org.newdawn.test.core;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.FPSLogger;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.TimeUtils;
import com.badlogic.gdx.Application;
import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;

public class OpenGLTest implements ApplicationListener, InputProcessor {
	
	private Camera camera;
	private float cameraXRot = -45,cameraYRot = 45;
	private Cube cube;
	private List<Renderable> renderables = new ArrayList<Renderable>();
	private FPSLogger fpsLogger;
	
	@Override
	public void create () {
		Gdx.app.setLogLevel(Application.LOG_DEBUG);
		Gdx.app.debug("init", "In create");
		cube = new Cube();
		
		renderables.add(cube);
		renderables.add(new Triangle());
		Shape square = new Square();
		square.setPosition(0, 3, 0);
		renderables.add(square);
		
		camera = new PerspectiveCamera(67, 1, 1);
		camera.near = 1;
		camera.far = 3000;
		
		Gdx.input.setInputProcessor(this);
		
		Gdx.gl20.glClearColor(0, 0, 0, 1);
		Gdx.gl20.glEnable(GL20.GL_DEPTH_TEST);
		Gdx.gl20.glDepthFunc(GL20.GL_LESS);
		
		fpsLogger = new FPSLogger();
	}

	@Override
	public void resize (int width, int height) {
		Gdx.app.debug("init", "In resize");

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
		
		camera.position.set(0,0,7.1f);
		camera.position.rotate(cameraXRot, 1, 0, 0);
		camera.position.rotate(cameraYRot, 0, 1, 0);
		
		camera.lookAt(0, 0, 0);

		camera.update();
	}

	@Override
	public void render () {
		Gdx.gl20.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);
		
		float animationRatio = (TimeUtils.millis() % 2000)/2000.0f;
		
		float offset = MathUtils.sin(animationRatio * (MathUtils.PI2));
        cube.setPosition(offset * 3f,0,-3);
        cube.setRotation(animationRatio * 360, 1, 0, 0);
        
        for(Renderable renderable : renderables) {
        	renderable.render(camera.view, camera.projection);
        }
        
        fpsLogger.log();
	}

	@Override
	public void pause () {
		Gdx.app.debug("init", "In pause");
	}

	@Override
	public void resume () {
		Gdx.app.debug("init", "In resume");
	}

	@Override
	public void dispose () {
		Gdx.app.debug("init", "In dispose");
        for(Renderable renderable : renderables) {
        	renderable.dispose();
        }
	}

	@Override
	public boolean keyDown(int keycode) {
//		Gdx.app.debug("touch", "keyDown: " + keycode);
		return false;
	}

	@Override
	public boolean keyUp(int keycode) {
//		Gdx.app.debug("touch", "keyUp: " + keycode);
		return false;
	}

	@Override
	public boolean keyTyped(char character) {
//		Gdx.app.debug("touch", "keyTyped: \"" + character + "\"");
		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
//		Gdx.app.debug("touch", "touchDown: " + pointer + ", " + button);
		return false;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
//		Gdx.app.debug("touch", "touchUp: " + pointer + ", " + button);
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
//		Gdx.app.debug("touch", "touchDragged: " + pointer + ", x: " + Gdx.app.getInput().getDeltaX(pointer) + ",y: " + Gdx.app.getInput().getDeltaY(pointer));
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
//		Gdx.app.debug("touch", "scrolled: ");
		return false;
	}
}
