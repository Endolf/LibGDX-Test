package org.newdawn.test.core;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Mesh;
import com.badlogic.gdx.graphics.VertexAttribute;
import com.badlogic.gdx.graphics.VertexAttributes.Usage;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector3;

public class Cube {
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
	
	private Matrix4 position = new Matrix4();
	private Vector3 translation = new Vector3();
	private float[] rotation = {0,0,1,0};

	private float[] colour = {1,0,0,1};
	
	public Cube() {
		mesh = new Mesh(true, 8, 14, new VertexAttribute(Usage.Position, 3, "vPosition"));

		mesh.setVertices(new float[] { 1f, 1f, 1f, -1f, 1f, 1f, -1f, -1f, 1f,
				1f, -1f, 1f, 1f, 1f, -1f, -1f, 1f, -1f, -1f, -1f, -1f, 1f, -1f,
				-1f });

		mesh.setIndices(new short[] { 0, 1, 3, 2, 6, 1, 5, 0, 4, 3, 7, 6, 4, 5 });
		
		shader = new ShaderProgram(vertexShaderCode, fragmentShaderCode);		
		
		if(!shader.isCompiled()) {
			Gdx.app.error("shader", "Failed to compile shader");
			Gdx.app.exit();
		}

		mesh.bind(shader);
	}
	
	public void render(Matrix4 viewMatrix, Matrix4 projectionMatrix) {
		Gdx.gl20.glEnable(GL20.GL_CULL_FACE);
		
		position.idt();
		position.translate(translation);
		position.rotate(rotation[1], rotation[2], rotation[3], rotation[0]);
		
		position.mul(projectionMatrix);
		position.mul(viewMatrix);
		
		shader.begin();
		shader.setUniform4fv("vColor", colour, 0, 4);
		shader.setUniformMatrix("uMVPMatrix", position, false);
		mesh.render(shader, GL20.GL_TRIANGLE_STRIP);
		shader.end();
	}
	
	public void setPosition(float x, float y, float z) {
		translation.set(x, y, z);
	}
}
