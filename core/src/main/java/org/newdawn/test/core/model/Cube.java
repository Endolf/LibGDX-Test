package org.newdawn.test.core.model;

import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.model.data.ModelData;
import com.badlogic.gdx.graphics.g3d.model.data.ModelMaterial;
import com.badlogic.gdx.graphics.g3d.model.data.ModelTexture;

public class Cube {
	public static Model getModel(String textureLocation, boolean isTwoSided, float[] colour) {
		ModelData modelData = new ModelData();
		
		modelData.id = "CubeData";
		
		float[] vertices = new float[] { 
				-1f, -1f, -1f,
				-1f, -1f, 1f,
				1f, -1f, 1f,
				1f, -1f, -1f, 
				-1f, 1f, -1f,
				-1f, 1f, 1f, 
				1f, 1f, 1f, 
				1f, 1f, -1f, 
				-1f, -1f, -1f, 
				-1f, 1f, -1f,
				1f, 1f, -1f,
				1f, -1f, -1f, 
				-1f, -1f, 1f, 
				-1f, 1f, 1f, 
				1f, 1f, 1f, 
				1f, -1f, 1f, 
				-1f, -1f, -1f,
				-1f, -1f, 1f,
				-1f, 1f, 1f, 
				-1f, 1f, -1f,
				1f, -1f, -1f, 
				1f, -1f, 1f, 
				1f, 1f, 1f,
				1f, 1f, -1f,
			};
		float[] textureCoords = new float[] { 
				1.0f, 0.0f, 
				1.0f, 1.0f, 
				0.0f, 1.0f, 
				0.0f, 0.0f, 
				0.0f, 0.0f, 
				0.0f, 1.0f, 
				1.0f, 1.0f, 
				1.0f, 0.0f, 
				1.0f, 1.0f, 
				1.0f, 0.0f, 
				0.0f, 0.0f, 
				0.0f, 1.0f, 
				0.0f, 1.0f, 
				0.0f, 0.0f, 
				1.0f, 0.0f, 
				1.0f, 1.0f,
				0.0f, 1.0f, 
				1.0f, 1.0f, 
				1.0f, 0.0f, 
				0.0f, 0.0f, 
				1.0f, 1.0f, 
				0.0f, 1.0f, 
				0.0f, 0.0f, 
				1.0f, 0.0f,				
			};
		short[] indecies = new short[] { 
				0, 2, 1, 
				0, 3, 2, 
				4, 5, 6, 
				4, 6, 7, 
				8, 9, 10, 
				8, 10, 11, 
				12, 15, 14, 
				12, 14, 13, 
				16, 17, 18, 
				16, 18, 19,
				20, 22, 21,
				20, 23, 22, 
			};
		float[] normals = new float[] { 
				0f, -1f, 0f,
				0f, -1f, 0f,
				0f, -1f, 0f,
				0f, -1f, 0f,
				0f, 1f, 0f,
				0f, 1f, 0f, 
				0f, 1f, 0f, 
				0f, 1f, 0f, 
				0f, 0f, -1f, 
				0f, 0f, -1f, 
				0f, 0f, -1f, 
				0f, 0f, -1f, 
				0f, 0f, 1f, 
				0f, 0f, 1f, 
				0f, 0f, 1f, 
				0f, 0f, 1f, 
				-1f, 0f, 0f,
				-1f, 0f, 0f,
				-1f, 0f, 0f,
				-1f, 0f, 0f,
				1f, 0f, 0f,
				1f, 0f, 0f,
				1f, 0f, 0f,
				1f, 0f, 0f,
			};
		
		modelData.addMesh(MeshUtils.createMesh(vertices, textureCoords, normals, indecies));
		
		ModelMaterial material = new ModelMaterial();
		material.emissive.set(colour[0], colour[1], colour[2], colour[3]);

		if(textureCoords!=null && textureLocation!=null) {
//			Texture texture = new Texture(Gdx.files.classpath(textureLocation), Format.RGBA8888, true);
//			texture.setFilter(TextureFilter.MipMapLinearNearest, TextureFilter.Nearest);
			ModelTexture texture = new ModelTexture();
			texture.fileName = textureLocation;
			texture.usage = ModelTexture.USAGE_DIFFUSE;
			material.textures.add(texture);
		}

		modelData.materials.add(material);
		
//		setTwoSided(isTwoSided);
		
		Model model = new Model(modelData);
		
		return model;
	}
}
