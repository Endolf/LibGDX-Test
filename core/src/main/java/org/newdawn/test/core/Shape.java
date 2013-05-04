package org.newdawn.test.core;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Mesh;
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
		"uniform vec4 vColor;" + 
		"void main() {" + 
		"  gl_FragColor = vColor;" + 
		"}";
	
	private ShaderProgram shader;
	
	private Matrix4 mvpMatrix = new Matrix4();
	private Matrix4 position = new Matrix4();
	private Vector3 translation = new Vector3();
	private float[] rotation = {0,0,1,0};

	private float[] colour;

	private boolean twoSided;
	
	public Shape(float[] vertecies, short[] indecies, boolean twoSided, float[] colour) {
		mesh = new Mesh(true, vertecies.length, indecies.length, new VertexAttribute(Usage.Position, 3, "vPosition"));

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
		
		shader.begin();
		shader.setUniform4fv("vColor", colour, 0, 4);
		shader.setUniformMatrix("uMVPMatrix", mvpMatrix, false);
		mesh.render(shader, GL20.GL_TRIANGLE_STRIP);
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
		mesh.dispose();
	}
}
