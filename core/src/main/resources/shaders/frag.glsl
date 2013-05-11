#ifdef GL_ES
	precision mediump float;
#endif
uniform vec4 uColor;
#ifdef TEXTURED
	uniform sampler2D uTexture0;
	varying vec2 vTexCoord0;
#endif
#ifdef NORMALS
	varying vec3 vNormal;
#endif

void main() {
	gl_FragColor = uColor;
	#ifdef TEXTURED
		gl_FragColor = texture2D(uTexture0, vTexCoord0) * gl_FragColor;
	#endif
	#ifdef NORMALS
		gl_FragColor = gl_FragColor + vec4(vNormal, gl_FragColor.a);
	#endif
}