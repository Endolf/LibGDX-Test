#ifdef GL_ES
	precision mediump float;
#endif

struct DirLight {
	vec3 direction[MAX_DIR_LIGHTS];
	vec4 colour[MAX_DIR_LIGHTS];
};

struct PointLight {
	vec3 position[MAX_POINT_LIGHTS];
	vec4 colour[MAX_POINT_LIGHTS];
	float intensity[MAX_POINT_LIGHTS];
};

uniform mat4 uModelMatrix;
uniform mat4 uViewMatrix;
uniform mat4 uProjectionMatrix;

uniform DirLight uDirLights;
uniform PointLight uPointLights;

uniform vec4 uEmissiveColour;
uniform vec4 uDiffuseColour;
#ifdef AMBIENT_LIGHT
uniform vec4 uAmbientLightColour;
#endif
uniform int uNumPointLights;
uniform int uNumDirectionalLights;

#ifdef TEXTURED
	uniform sampler2D uTexture0;
	varying vec2 vTexCoord0;
#endif
#ifdef NORMALS
	varying vec3 vModelNormal;
	varying vec3 vWorldNormal;
	varying vec3 vEyeNormal;
#endif

void main() {
	gl_FragColor = uEmissiveColour;
	
	#ifdef AMBIENT_LIGHT
		gl_FragColor = gl_FragColor + (uDiffuseColour * uAmbientLightColour);
	#endif
	
	for(int i=0;i<uNumDirectionalLights;i++) {
		gl_FragColor = gl_FragColor + (uDiffuseColour * uDirLights.colour[i]);
	}

	for(int i=0;i<uNumPointLights;i++) {
		gl_FragColor = gl_FragColor + (uDiffuseColour * uPointLights.colour[i]);
	}
	
	#ifdef TEXTURED
		gl_FragColor = texture2D(uTexture0, vTexCoord0) * gl_FragColor;
	#endif
	
	#ifdef NORMALS
		gl_FragColor = vec4((vEyeNormal + vec3(1,1,1)) * 0.5,1);
	#endif
	
	gl_FragColor = clamp(gl_FragColor, 0, 1);
}