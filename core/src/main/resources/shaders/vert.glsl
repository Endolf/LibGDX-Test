uniform mat4 uModelMatrix;
uniform mat4 uViewMatrix;
uniform mat4 uProjectionMatrix;
attribute vec4 aPosition;
varying vec4 vWorldPosition;
#ifdef TEXTURED
	attribute vec2 aTexCoord0;
	varying vec2 vTexCoord0;
#endif
#ifdef NORMALS
	attribute vec3 aNormal;
	varying vec3 vModelNormal;
	varying vec3 vWorldNormal;
	varying vec3 vEyeNormal;
#endif

void main() {
	#ifdef BILLBOARD
		gl_Position = uProjectionMatrix * (uViewMatrix * uModelMatrix * vec4(0.0, 0.0, 0.0, 1.0) + vec4(aPosition.x, aPosition.y, 0.0, 0.0));
	#else
		gl_Position = uProjectionMatrix * uViewMatrix * uModelMatrix * aPosition;
	#endif
	
	vWorldPosition = uModelMatrix * aPosition;
	
	#ifdef TEXTURED
		vTexCoord0 = aTexCoord0;
	#endif
	#ifdef NORMALS
		vModelNormal = aNormal;
		#ifdef BILLBOARD
			vWorldNormal = (uModelMatrix * vec4(0.0, 0.0, 0.0, 1.0) + vec4(aNormal.x, aNormal.y, 0.0, 0.0)).xyz;
			vEyeNormal = (uProjectionMatrix * (uViewMatrix * uModelMatrix * vec4(0.0, 0.0, 0.0, 1.0) + vec4(aNormal.x, aNormal.y, 0.0, 0.0))).xyz;
		#else
			vWorldNormal = (uModelMatrix * vec4(aNormal,0)).xyz;
			vEyeNormal = (uProjectionMatrix * uViewMatrix * uModelMatrix * vec4(aNormal,0)).xyz;
		#endif
	#endif
}

