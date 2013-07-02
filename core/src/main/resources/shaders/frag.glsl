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
	float attenuation[MAX_POINT_LIGHTS];
};

uniform mat4 uModelMatrix;
uniform mat4 uViewMatrix;
uniform mat4 uProjectionMatrix;

varying vec4 vWorldPosition;

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
		#ifdef NORMALS
			float cosine = dot(vWorldNormal, uDirLights.direction[i]);
			float lambert = max(cosine, 0);
			gl_FragColor = gl_FragColor + (uDiffuseColour * uDirLights.colour[i] * lambert);
		#else
			gl_FragColor = gl_FragColor + (uDiffuseColour * uDirLights.colour[i]);
		#endif
	}

	for(int i=0;i<uNumPointLights;i++) {
		#ifdef NORMALS			
			vec3 lightVector = normalize(uPointLights.position[i] - vWorldPosition.xyz);
			float cosine = dot(vWorldNormal, lightVector);
			float lambert = max(cosine, 0);
			float distance = length(uPointLights.position[i] - vWorldPosition.xyz);
//			float attenuation = (1.0 / (uPointLights.attenuation[i] + (uPointLights.attenuation[i] * distance) + (uPointLights.attenuation[i] * distance * distance)));
			float attenuation = (1.0 / (uPointLights.attenuation[i] * distance));
			float lightFactor = clamp(lambert * attenuation,0,1);
			gl_FragColor = gl_FragColor + (uDiffuseColour * uPointLights.colour[i] * lightFactor);
		#else
			gl_FragColor = gl_FragColor + (uDiffuseColour * uPointLights.colour[i]);
		#endif
	}
	
	#ifdef TEXTURED
		gl_FragColor = texture2D(uTexture0, vTexCoord0) * gl_FragColor;
	#endif
	
	gl_FragColor = clamp(gl_FragColor, 0, 1);
}