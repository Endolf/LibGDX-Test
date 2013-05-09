package org.newdawn.test.core;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Mesh;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.VertexAttribute;
import com.badlogic.gdx.graphics.VertexAttributes.Usage;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector3;

public abstract class Shape implements Renderable {
	private Mesh mesh;

	private final String vertexShaderCode =
		"uniform mat4 uMVPMatrix;" +
		"attribute vec4 vPosition;" +
		"void main() {" +
		"  gl_Position = uMVPMatrix * vPosition;" + 
		"}";

	private final String fragmentShaderCode =
		"#ifdef GL_ES\n" +
		"precision mediump float;\n" +
		"#endif\n" +
		"uniform vec4 vColor;" + 
		"void main() {" + 
		"  gl_FragColor = vColor;" + 
		"}";
	
	private final String vertexShaderCodeWithTexture =
		"uniform mat4 uMVPMatrix;" +
		"attribute vec4 vPosition;" +
		"attribute vec2 texCoord0;" +
		"varying vec2 v_texCoord0;" +
		"void main() {" +
		"  gl_Position = uMVPMatrix * vPosition;" +
		"  v_texCoord0 = texCoord0;" +
		"}";

	private final String fragmentShaderCodeWithTexture = 
		"#ifdef GL_ES\n" +
		"precision mediump float;\n" +
		"#endif\n" +
		"uniform sampler2D texture0;" +
		"varying vec2 v_texCoord0;" +	
		"void main() {" + 
		"  gl_FragColor = texture2D(texture0, v_texCoord0);" + 
		"}";

	private ShaderProgram shader;
	
	private Matrix4 mvpMatrix = new Matrix4();
	private Matrix4 position = new Matrix4();
	private Vector3 translation = new Vector3();
	private float[] rotation = {0,0,1,0};

	private float[] colour;
	private Texture texture;

	private boolean twoSided;
	
	public Shape(float[] vertecies, short[] indecies, boolean twoSided, float[] colour) {
		mesh = new Mesh(true, vertecies.length / 3, indecies.length, new VertexAttribute(Usage.Position, 3, "vPosition"));

		mesh.setVertices(vertecies);

		mesh.setIndices(indecies);
		
		shader = new ShaderProgram(vertexShaderCode, fragmentShaderCode);		
		
		if(!shader.isCompiled()) {
			Gdx.app.error("shader", "Failed to compile shader");
			Gdx.app.exit();
		}

		mesh.bind(shader);
		
		this.twoSided = twoSided;
		this.colour = colour;
	}
	
	public Shape(float[] vertecies, float[] textureCoords, short[] indecies, boolean twoSided, float[] colour, String textureLocation) {
		int numVertices = vertecies.length / 3;
		float[] allVertexData = new float[numVertices * 5];
		
		for(int i=0;i<numVertices;i++) {
			allVertexData[i*5] = vertecies[i*3];
			allVertexData[i*5+1] = vertecies[i*3+1];
			allVertexData[i*5+2] = vertecies[i*3+2];
			
			allVertexData[i*5+3] = textureCoords[i*2];
			allVertexData[i*5+4] = textureCoords[i*2+1];
		}
		
		mesh = new Mesh(true, numVertices, indecies.length, new VertexAttribute(Usage.Position, 3, "vPosition"), new VertexAttribute(Usage.TextureCoordinates, 2, "texCoord0"));

		mesh.setVertices(allVertexData);

		mesh.setIndices(indecies);
		
		texture = new Texture(Gdx.files.internal(textureLocation));
		texture.setFilter(TextureFilter.Nearest, TextureFilter.Nearest);
		
		shader = new ShaderProgram(vertexShaderCodeWithTexture, fragmentShaderCodeWithTexture);		
		
		if(!shader.isCompiled()) {
			Gdx.app.error("shader", "Failed to compile shader");
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
		
		position.idt();
		position.translate(translation);
		position.rotate(rotation[1], rotation[2], rotation[3], rotation[0]);

		mvpMatrix.set(projectionMatrix).mul(viewMatrix).mul(position);
		
		if(texture!=null) {
			Gdx.gl20.glActiveTexture(GL20.GL_TEXTURE0);
			texture.bind();
		}
		shader.begin();
		if(texture==null) {
			shader.setUniform4fv("vColor", colour, 0, 4);
		} else {
			shader.setUniformf("texture0", 0);
		}
		shader.setUniformMatrix("uMVPMatrix", mvpMatrix, false);
		mesh.render(shader, GL20.GL_TRIANGLES);
		shader.end();
	}
	
	public void setPosition(float x, float y, float z) {
		translation.set(x, y, z);
	}
	
	public void setRotation(float angle, float x, float y, float z) { 
		rotation[0] = angle;
		rotation[1] = x;
		rotation[2] = y;
		rotation[3] = z;
	}

	@Override
	public void dispose() {
		shader.dispose();
		if(texture!=null) texture.dispose();
		mesh.dispose();
	}
}
