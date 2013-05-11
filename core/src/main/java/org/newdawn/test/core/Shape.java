package org.newdawn.test.core;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Mesh;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.VertexAttribute;
import com.badlogic.gdx.graphics.VertexAttributes.Usage;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector3;

public abstract class Shape implements Renderable {
	private Mesh mesh;

	protected String vertexShaderCode =
		"uniform mat4 uModelMatrix;\n" +
		"uniform mat4 uViewMatrix;\n" +
		"uniform mat4 uProjectionMatrix;\n" +
		"attribute vec4 aPosition;\n" +
		"#ifdef TEXTURED\n" +
		"attribute vec2 aTexCoord0;\n" +
		"varying vec2 vTexCoord0;\n" +
		"#endif\n" +
		"void main() {\n" +
		"#ifdef BILLBOARD\n" +
		"  gl_Position = uProjectionMatrix * (uViewMatrix * uModelMatrix * vec4(0.0, 0.0, 0.0, 1.0) + vec4(aPosition.x, aPosition.y, 0.0, 0.0));\n" +
		"#else\n" + 
		"  gl_Position = uProjectionMatrix * uViewMatrix * uModelMatrix * aPosition;\n" +
		"#endif\n" +
		"#ifdef TEXTURED\n" +
		"  vTexCoord0 = aTexCoord0;\n" +
		"#endif\n" +
		"}\n";

	private final String fragmentShaderCode = 
		"#ifdef GL_ES\n" +
		"precision mediump float;\n" +
		"#endif\n" +
		"uniform vec4 uColor;\n" + 
		"#ifdef TEXTURED\n" +
		"uniform sampler2D uTexture0;\n" +
		"varying vec2 vTexCoord0;\n" +	
		"#endif\n" +
		"void main() {\n" + 
		"#ifdef TEXTURED\n" +
		"  gl_FragColor = texture2D(uTexture0, vTexCoord0) * uColor;\n" +
		"#else\n" + 
		"  gl_FragColor = uColor;\n" +
		"#endif\n" + 
		"}\n";

	private ShaderProgram shader;
	
	private Matrix4 position = new Matrix4();
	private Vector3 translation = new Vector3();
	private float[] rotation = {0,0,1,0};

	private float[] colour;
	private Texture texture;

	private boolean twoSided;
	
	public Shape(float[] vertecies, short[] indecies, boolean twoSided, float[] colour, boolean isBillboard) {
		mesh = new Mesh(true, vertecies.length / 3, indecies.length, new VertexAttribute(Usage.Position, 3, "aPosition"));

		mesh.setVertices(vertecies);

		mesh.setIndices(indecies);
		
		String finalVertexShaderCode = "";
		String finalFragmentShaderCode = "";
		if(isBillboard) {
			finalVertexShaderCode += "#define BILLBOARD\n";
			finalFragmentShaderCode += "#define BILLBOARD\n";
		}
		
		finalVertexShaderCode += vertexShaderCode;
		finalFragmentShaderCode += fragmentShaderCode;
		
		shader = new ShaderProgram(finalVertexShaderCode, finalFragmentShaderCode);		
		
		if(!shader.isCompiled()) {
			Gdx.app.error("shader", "Failed to compile shape shader");
			Gdx.app.log("opengl", shader.getLog());
			Gdx.app.exit();
		}

		mesh.bind(shader);
		
		this.twoSided = twoSided;
		this.colour = colour;
	}
	
	public Shape(float[] vertecies, float[] textureCoords, short[] indecies, boolean twoSided, float[] colour, String textureLocation, boolean isBillboard) {
		int numVertices = vertecies.length / 3;
		float[] allVertexData = new float[numVertices * 5];
		
		for(int i=0;i<numVertices;i++) {
			allVertexData[i*5] = vertecies[i*3];
			allVertexData[i*5+1] = vertecies[i*3+1];
			allVertexData[i*5+2] = vertecies[i*3+2];
			
			allVertexData[i*5+3] = textureCoords[i*2];
			allVertexData[i*5+4] = textureCoords[i*2+1];
		}
		
		mesh = new Mesh(true, numVertices, indecies.length, new VertexAttribute(Usage.Position, 3, "aPosition"), new VertexAttribute(Usage.TextureCoordinates, 2, "aTexCoord0"));

		mesh.setVertices(allVertexData);

		mesh.setIndices(indecies);
		
		texture = new Texture(Gdx.files.internal(textureLocation), Format.RGBA8888, true);
		texture.setFilter(TextureFilter.MipMapLinearNearest, TextureFilter.Nearest);
		
		String finalVertexShaderCode = "#define TEXTURED\n";
		String finalFragmentShaderCode = "#define TEXTURED\n";
		if(isBillboard) {
			finalVertexShaderCode += "#define BILLBOARD\n";
			finalFragmentShaderCode += "#define BILLBOARD\n";
		}
		
		finalVertexShaderCode += vertexShaderCode;
		finalFragmentShaderCode += fragmentShaderCode;

		shader = new ShaderProgram(finalVertexShaderCode, finalFragmentShaderCode);		
		
		if(!shader.isCompiled()) {
			Gdx.app.error("shader", "Failed to compile shape shader with texture");
			Gdx.app.log("opengl", shader.getLog());
			Gdx.app.exit();
		}

		mesh.bind(shader);
		
		this.twoSided = twoSided;
		this.colour = colour;
	}
	
	public void render(Matrix4 viewMatrix, Matrix4 projectionMatrix) {
		if(twoSided) {
			Gdx.gl20.glDisable(GL20.GL_CULL_FACE);
		} else {
			Gdx.gl20.glEnable(GL20.GL_CULL_FACE);
		}
		
		if(texture!=null) {
			Gdx.gl20.glActiveTexture(GL20.GL_TEXTURE0);
			texture.bind();
		}
		shader.begin();
		shader.setUniform4fv("uColor", colour, 0, 4);
		if(texture!=null) {
			shader.setUniformf("uTexture0", 0);
		}
		shader.setUniformMatrix("uProjectionMatrix", projectionMatrix, false);
		shader.setUniformMatrix("uViewMatrix", viewMatrix, false);
		shader.setUniformMatrix("uModelMatrix", position, false);
		mesh.render(shader, GL20.GL_TRIANGLES);
		shader.end();
	}

	private void updatePosition() {
		position.idt();
		position.translate(translation);
		position.rotate(rotation[1], rotation[2], rotation[3], rotation[0]);
	}
	
	public void setPosition(Matrix4 newPosition) {
		position.set(newPosition);
	}
	 
	public void setPosition(float x, float y, float z) {
		translation.set(x, y, z);
		updatePosition();
	}
	
	public Vector3 getPosition(Vector3 vec) {
		if(vec == null) vec = new Vector3();
		vec.set(translation);
		return vec;
	}
	
	public void setRotation(float angle, float x, float y, float z) { 
		rotation[0] = angle;
		rotation[1] = x;
		rotation[2] = y;
		rotation[3] = z;
		updatePosition();
	}

	@Override
	public void dispose() {
		shader.dispose();
		if(texture!=null) texture.dispose();
		mesh.dispose();
	}
}
