package org.newdawn.test.core;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Mesh;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.VertexAttribute;
import com.badlogic.gdx.graphics.VertexAttributes.Usage;
import com.badlogic.gdx.graphics.g3d.lights.DirectionalLight;
import com.badlogic.gdx.graphics.g3d.lights.Lights;
import com.badlogic.gdx.graphics.g3d.lights.PointLight;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector3;

public abstract class Shape implements Renderable {
	private Mesh mesh;

	private ShaderProgram shader;
	
	private Matrix4 position = new Matrix4();
	private Vector3 translation = new Vector3();
	private float[] rotation = {0,0,1,0};

	private float[] emissiveColour = new float[] {0,0,0,0};
	private float[] diffuseColour = new float[] {0,0,0,0};
	private float[] vertices;
	private short[] indecies;
	private float[] textureCoords;
	private float[] normals;
	private Texture texture;
	
	private boolean isTwoSided;
	private boolean isBillboard;
	private String textureLocation;

	public Shape(float[] vertices, short[] indecies, boolean twoSided, float[] emissiveColour, float[] diffuseColour, boolean isBillboard) {
		this.vertices = vertices;
		this.indecies = indecies;
		this.isTwoSided = twoSided;
		this.emissiveColour = emissiveColour;
		this.diffuseColour = diffuseColour;
		this.isBillboard = isBillboard;

		initialise();
	}
	
	public Shape(float[] vertices, float[] textureCoords, short[] indecies, boolean twoSided, float[] emissiveColour, float[] diffuseColour, String textureLocation, boolean isBillboard) {
		this.vertices = vertices;
		this.indecies = indecies;
		this.isTwoSided = twoSided;
		this.emissiveColour = emissiveColour;
		this.diffuseColour = diffuseColour;
		this.isBillboard = isBillboard;
		this.textureLocation = textureLocation;
		this.textureCoords = textureCoords;
		
		initialise();
	}
	
	public Shape() {
	}
	
	public void initialise() {
		dispose();
		
		int numVertices = vertices.length / 3;
		
		int numDataTypes = 1;
		if(textureCoords!=null) numDataTypes +=1;
		if(normals!=null) numDataTypes +=1;
		int numDataItems = 3;
		if(textureCoords!=null) numDataItems+=2;
		if(normals!=null) numDataItems+=3;
		
		float[] allVertexData = new float[numVertices * numDataItems];
		
		int dataItem = 0;
		for(int i=0;i<numVertices;i++) {
			allVertexData[dataItem++] = vertices[i*3];
			allVertexData[dataItem++] = vertices[i*3+1];
			allVertexData[dataItem++] = vertices[i*3+2];
			
			if(textureCoords!=null) {
				allVertexData[dataItem++] = textureCoords[i*2];
				allVertexData[dataItem++] = textureCoords[i*2+1];
			}
			
			if(normals!=null) {
				allVertexData[dataItem++] = normals[i*3];
				allVertexData[dataItem++] = normals[i*3+1];
				allVertexData[dataItem++] = normals[i*3+2];
			}
		}
		
		VertexAttribute[] vAttribs = new VertexAttribute[numDataTypes];
		dataItem = 0;
		vAttribs[dataItem++] = new VertexAttribute(Usage.Position, 3, "aPosition");
		if(textureCoords!=null) vAttribs[dataItem++] = new VertexAttribute(Usage.TextureCoordinates, 2, "aTexCoord0");
		if(normals!=null) vAttribs[dataItem++] = new VertexAttribute(Usage.Normal, 3, "aNormal");
		
		mesh = new Mesh(true, numVertices, indecies.length, vAttribs);

		mesh.setVertices(allVertexData);

		mesh.setIndices(indecies);
		
		if(textureCoords!=null && textureLocation!=null) {
			texture = new Texture(Gdx.files.classpath(textureLocation), Format.RGBA8888, true);
			texture.setFilter(TextureFilter.MipMapLinearNearest, TextureFilter.Nearest);
		}
		
		String finalVertexShaderCode = "";
		String finalFragmentShaderCode = "";
		
		if(textureCoords!=null && textureLocation!=null) {
			finalVertexShaderCode += "#define TEXTURED\n";
			finalFragmentShaderCode += "#define TEXTURED\n";
		}
		if(isBillboard) {
			finalVertexShaderCode += "#define BILLBOARD\n";
			finalFragmentShaderCode += "#define BILLBOARD\n";
		}
		if(normals!=null) {
			finalVertexShaderCode += "#define NORMALS\n";
			finalFragmentShaderCode += "#define NORMALS\n";
		}
		
		finalFragmentShaderCode += "#define AMBIENT_LIGHT\n";
		finalFragmentShaderCode += "#define MAX_DIR_LIGHTS 1\n";
		finalFragmentShaderCode += "#define MAX_POINT_LIGHTS 1\n";
		
		String vertexShaderCode = Gdx.files.classpath("shaders/vert.glsl").readString("UTF-8");
		String fragmentShaderCode = Gdx.files.classpath("shaders/frag.glsl").readString("UTF-8");
		
		finalVertexShaderCode += vertexShaderCode;
		finalFragmentShaderCode += fragmentShaderCode;

		shader = new ShaderProgram(finalVertexShaderCode, finalFragmentShaderCode);		
		
		if(!shader.isCompiled()) {
			Gdx.app.error("shader", "Failed to compile shape shader with texture\n" + finalVertexShaderCode + "\n" + finalFragmentShaderCode);
			Gdx.app.log("opengl", shader.getLog());
			Gdx.app.exit();
		}
	}
	
	@Override
	public void render(Matrix4 viewMatrix, Matrix4 projectionMatrix, Lights lights) {
		if(isTwoSided) {
			Gdx.gl20.glDisable(GL20.GL_CULL_FACE);
		} else {
			Gdx.gl20.glEnable(GL20.GL_CULL_FACE);
		}
		
		if(texture!=null) {
			texture.bind(0);
		}
		shader.begin();
		shader.setUniformf("uAmbientLightColour", lights.ambientLight);
		shader.setUniform4fv("uEmissiveColour", emissiveColour, 0, 4);
		shader.setUniform4fv("uDiffuseColour", diffuseColour, 0, 4);
		if(texture!=null) {
			shader.setUniformf("uTexture0", 0);
		}
		shader.setUniformMatrix("uProjectionMatrix", projectionMatrix, false);
		shader.setUniformMatrix("uViewMatrix", viewMatrix, false);
		shader.setUniformMatrix("uModelMatrix", position, false);
		
		shader.setUniformi("uNumDirectionalLights", lights.directionalLights.size);
		shader.setUniformi("uNumPointLights", lights.pointLights.size);
		
		for(int i=0;i<lights.directionalLights.size;i++) {
			DirectionalLight light = lights.directionalLights.get(i);
			shader.setUniformf("uDirLights.colour[" + i + "]", light.color);
			shader.setUniformf("uDirLights.direction[" + i + "]", light.direction);
		}
		
		for(int i=0;i<lights.pointLights.size;i++) {
			PointLight light = lights.pointLights.get(i);
			shader.setUniformf("uPointLights.colour[" + i + "]", light.color);
			shader.setUniformf("uPointLights.intensity[" + i + "]", light.intensity);
			shader.setUniformf("uPointLights.position[" + i + "]", light.position);
		}
		
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
		if(vec == null) throw new IllegalArgumentException("vec must not be null");
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
		if(shader!=null) shader.dispose();
		if(texture!=null) texture.dispose();
		if(mesh!=null) mesh.dispose();
	}

	public float[] getEmissiveColour() {
		return emissiveColour;
	}

	public void setEmissiveColour(float[] colour) {
		this.emissiveColour = colour;
	}

	public float[] getDiffuseColour() {
		return diffuseColour;
	}

	public void setDiffuseColour(float[] colour) {
		this.diffuseColour = colour;
	}

	public float[] getVertices() {
		return vertices;
	}

	public void setVertices(float[] vertices) {
		this.vertices = vertices;
	}

	public short[] getIndecies() {
		return indecies;
	}

	public void setIndecies(short[] indecies) {
		this.indecies = indecies;
	}

	public float[] getTextureCoords() {
		return textureCoords;
	}

	public void setTextureCoords(float[] textureCoords) {
		this.textureCoords = textureCoords;
	}

	public float[] getNormals() {
		return normals;
	}

	public void setNormals(float[] normals) {
		this.normals = normals;
	}

	public boolean isTwoSided() {
		return isTwoSided;
	}

	public void setTwoSided(boolean isTwoSided) {
		this.isTwoSided = isTwoSided;
	}

	public boolean isBillboard() {
		return isBillboard;
	}

	public void setBillboard(boolean isBillboard) {
		this.isBillboard = isBillboard;
	}

	public String getTextureLocation() {
		return textureLocation;
	}

	public void setTextureLocation(String textureLocation) {
		this.textureLocation = textureLocation;
	}
}
