#ifdef GL_ES
	precision mediump float;
#endif
uniform vec4 uEmissiveColour;
uniform vec4 uDiffuseColour;
#ifdef AMBIENT_LIGHT
uniform vec4 uAmbientLightColour;
#endif
#ifdef TEXTURED
	uniform sampler2D uTexture0;
	varying vec2 vTexCoord0;
#endif
#ifdef NORMALS
	varying vec3 vNormal;
#endif

void main() {
	gl_FragColor = uDiffuseColour;
	#ifdef AMBIENT_LIGHT
		gl_FragColor = gl_FragColor * uAmbientLightColour;
	#endif
	gl_FragColor = gl_FragColor + uEmissiveColour;
	#ifdef TEXTURED
		gl_FragColor = texture2D(uTexture0, vTexCoord0) * gl_FragColor;
	#endif
}