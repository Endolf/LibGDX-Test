uniform mat4 uModelMatrix;
uniform mat4 uViewMatrix;
uniform mat4 uProjectionMatrix;
attribute vec4 aPosition;
#ifdef TEXTURED
	attribute vec2 aTexCoord0;
	varying vec2 vTexCoord0;
#endif
#ifdef NORMALS
	attribute vec3 aNormal;
	varying vec3 vNormal;
#endif

void main() {
	#ifdef BILLBOARD
		gl_Position = uProjectionMatrix * (uViewMatrix * uModelMatrix * vec4(0.0, 0.0, 0.0, 1.0) + vec4(aPosition.x, aPosition.y, 0.0, 0.0));
	#else
		gl_Position = uProjectionMatrix * uViewMatrix * uModelMatrix * aPosition;
	#endif
	#ifdef TEXTURED
		vTexCoord0 = aTexCoord0;
	#endif
	#ifdef NORMALS
		vNormal = aNormal;
	#endif
}

