package org.newdawn.test.core;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.VertexAttributes.Usage;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.decals.CameraGroupStrategy;
import com.badlogic.gdx.graphics.g3d.decals.Decal;
import com.badlogic.gdx.graphics.g3d.decals.DecalBatch;
import com.badlogic.gdx.graphics.g3d.lights.DirectionalLight;
import com.badlogic.gdx.graphics.g3d.lights.Lights;
import com.badlogic.gdx.graphics.g3d.lights.PointLight;
import com.badlogic.gdx.graphics.g3d.materials.BlendingAttribute;
import com.badlogic.gdx.graphics.g3d.materials.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.materials.Material;
import com.badlogic.gdx.graphics.g3d.materials.TextureAttribute;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.TimeUtils;
import com.badlogic.gdx.Application;
import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;

public class OpenGLTest implements ApplicationListener, InputProcessor {
	
	private Camera camera;
	private float cameraXRot = -45,cameraYRot = 45;
	private ModelInstance cube;
	private ModelInstance cube2;
	private Decal square;
	private Decal square2;
	private Decal square3;
	private List<ModelInstance> renderables = new ArrayList<ModelInstance>();
	private List<ModelInstance> transparentRenderables = new ArrayList<ModelInstance>();
	private BitmapFont font;
	private SpriteBatch spriteBatch;
	private DecalBatch decalBatch;
	private PointLight pointLight;
	private PointLight pointLight2;
	private PointLight pointLight3;
	private Lights lights;
	
	private Vector3 tempVector01 = new Vector3();
	private Model cubeModel;
	private Model triangleModel;
	private ModelBatch modelBatch;
	private Texture cubeTexture;
	private Texture starTexture;
	private CameraGroupStrategy decalBatchStrategy;
	
	@Override
	public void create () {
		Gdx.app.setLogLevel(Application.LOG_DEBUG);
		Gdx.app.debug("init", "In create");
		
		Gdx.gl20.glClearColor(0, 0, 0, 1);
		Gdx.gl20.glEnable(GL20.GL_DEPTH_TEST);
		Gdx.gl20.glDepthFunc(GL20.GL_LESS);

		Gdx.input.setInputProcessor(this);

		camera = new PerspectiveCamera(67, 1, 1);
		camera.near = 1;
		camera.far = 3000;

		modelBatch = new ModelBatch();
		
		ModelBuilder modelBuilder = new ModelBuilder();
		ColorAttribute diffuseWhite = ColorAttribute.createDiffuse(1, 1, 1, 1);
		ColorAttribute ambientWhite = new ColorAttribute(ColorAttribute.Ambient, 1, 1, 1, 1);
		ColorAttribute diffuseGreen = ColorAttribute.createDiffuse(0, 1, 0, 1);
		ColorAttribute ambientGreen = new ColorAttribute(ColorAttribute.Ambient, 0, 1, 0, 1);
		
		cubeTexture = new Texture(Gdx.files.classpath("textures/nd-logo.png"), Format.RGBA8888, true);
		TextureAttribute cubeTextureAttribute = TextureAttribute.createDiffuse(cubeTexture);
		
		starTexture = new Texture(Gdx.files.classpath("textures/star.png"), Format.RGBA8888, true);
		
		cubeModel = modelBuilder.createBox(1, 1, 1, new Material(diffuseWhite, ambientWhite, cubeTextureAttribute), Usage.Position | Usage.Normal | Usage.TextureCoordinates);
		triangleModel = modelBuilder.createTriangle(0.622008459f, new Material(diffuseGreen, ambientGreen), Usage.Position | Usage.Normal);
		
		cube = new ModelInstance(cubeModel);
		cube.transform.setTranslation(tempVector01.set(0f, 0, -3));
		renderables.add(cube);
		cube2 = new ModelInstance(cubeModel);
		cube2.transform.setTranslation(tempVector01.set(0f, -3, 0));
		renderables.add(cube2);
		renderables.add(new ModelInstance(triangleModel));
		square = Decal.newDecal(1,1,new TextureRegion(starTexture), true);
		square.setPosition(0, 3, 0);
		square2 = Decal.newDecal(1,1,new TextureRegion(starTexture), true);
		square2.setPosition(0, 3, 0);
		square3 = Decal.newDecal(1,1,new TextureRegion(starTexture), true);
		square3.setPosition(0, 3, 0);
		
		decalBatchStrategy = new CameraGroupStrategy(camera);
		decalBatch = new DecalBatch(decalBatchStrategy);
		decalBatch.add(square);
		decalBatch.add(square2);
		decalBatch.add(square3);

		spriteBatch = new SpriteBatch();
		font = new BitmapFont();
		font.setColor(0, 1, 0, 1);
		
		pointLight = new PointLight();
		pointLight.color.set(1,0,0,1);
		pointLight.intensity = 10f;
		pointLight.position.set(square.getPosition());
		
		pointLight2 = new PointLight();
		pointLight2.color.set(0,1,0,1);
		pointLight2.intensity = 10f;
		pointLight2.position.set(square2.getPosition());

		pointLight3 = new PointLight();
		pointLight3.color.set(0,0,1,1);
		pointLight3.intensity = 10f;
		pointLight3.position.set(square3.getPosition());

		DirectionalLight directionalLight = new DirectionalLight();
		directionalLight.direction.set(0,0,-1);
		directionalLight.color.set(0.0f,0.0f,1f,1);
		
		lights = new Lights();
		lights.ambientLight.set(0.125f, 0.125f, 0.125f, 1);
		lights.add(directionalLight);
		lights.add(pointLight);
		lights.add(pointLight2);
		lights.add(pointLight3);
	}

	@Override
	public void resize (int width, int height) {
		Gdx.app.debug("init", "In resize, new size: " + width + "x" + height);

		camera.viewportWidth = width;
		camera.viewportHeight = height;
		
		if(spriteBatch!=null) {
			spriteBatch.dispose();
		}
		spriteBatch = new SpriteBatch();
		
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
		
		camera.direction.set(0,0,0);
		camera.direction.sub(camera.position);
		
		camera.update();
	}

	@Override
	public void render () {
		Gdx.gl20.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);
		
		float animationRatio = (TimeUtils.millis() % 2000)/2000.0f;
		
		float offset = MathUtils.sin(animationRatio * (MathUtils.PI2));
		float offset2 = MathUtils.cos(animationRatio * (MathUtils.PI2));
        cube.transform.setToRotation(animationRatio * 360, 1, 0, 0);
        cube.transform.setTranslation(tempVector01.set((offset * 3f),0,-3));
        cube2.transform.setToRotation(animationRatio * 360, 0, 1, 0);
		cube2.transform.setTranslation(tempVector01.set(0f, -3, 0));
        
        square.setPosition(0f, (offset * 5f), (offset2 * 5f));
        pointLight.position.set(square.getPosition());

        square2.setPosition((offset * 5f), 0f, (offset2 * 5f));
        pointLight2.position.set(square2.getPosition());
        
		square3.setPosition((offset * 5f), (offset2 * 5f), 0f);
		pointLight3.position.set(square3.getPosition());

		Gdx.gl20.glEnable(GL20.GL_BLEND);
		Gdx.gl20.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
		
		modelBatch.begin(camera);
		modelBatch.render(renderables, lights);
        
//        Collections.sort(transparentRenderables, depthComparator);
		modelBatch.render(transparentRenderables, lights);
		modelBatch.end();
		
		decalBatch.flush();
        
        spriteBatch.begin();
        font.draw(spriteBatch, "FPS: " + Gdx.graphics.getFramesPerSecond(), 5, font.getCapHeight() + 5);
        spriteBatch.end();
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
		
		if(font!=null) font.dispose();
		
		if(spriteBatch!=null) spriteBatch.dispose();
		
		if(cubeModel!=null) cubeModel.dispose();
		if(triangleModel!=null) triangleModel.dispose();
		
		if(modelBatch!=null) modelBatch.dispose();
		
		if(cubeTexture!=null) cubeTexture.dispose();
		if(starTexture!=null) starTexture.dispose();
		
		if(decalBatch!=null) decalBatch.dispose();
		if(decalBatchStrategy!=null) decalBatchStrategy.dispose();
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
