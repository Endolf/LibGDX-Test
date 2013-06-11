package org.newdawn.test.core.model;

import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.VertexAttribute;
import com.badlogic.gdx.graphics.VertexAttributes.Usage;
import com.badlogic.gdx.graphics.g3d.model.data.ModelMesh;
import com.badlogic.gdx.graphics.g3d.model.data.ModelMeshPart;

public class MeshUtils {
	public static ModelMesh createMesh(float[] vertices, float[] textureCoords, float[] normals, short[] indecies) {
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

		ModelMeshPart part = new ModelMeshPart();
		part.primitiveType = GL20.GL_TRIANGLES;
		part.indices = indecies;
		
		ModelMesh modelMesh = new ModelMesh();
		modelMesh.attributes = vAttribs;
		modelMesh.vertices = allVertexData;
		modelMesh.parts = new ModelMeshPart[] {part};
		
		return modelMesh;
	}
}
