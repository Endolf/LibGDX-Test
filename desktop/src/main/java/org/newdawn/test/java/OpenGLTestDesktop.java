package org.newdawn.test.java;

import org.newdawn.test.core.OpenGLTest;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

public class OpenGLTestDesktop {
	public static void main(String[] args) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.useGL20 = true;
		config.title = "LibGDX Test";
		config.samples = 2;
		config.vSyncEnabled = true;
		new LwjglApplication(new OpenGLTest(), config);
	}
}
