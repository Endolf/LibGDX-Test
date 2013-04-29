package org.newdawn.test.html;

import org.newdawn.test.core.OpenGLTest;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.backends.gwt.GwtApplication;
import com.badlogic.gdx.backends.gwt.GwtApplicationConfiguration;

public class OpenGLTestHtml extends GwtApplication {
	@Override
	public ApplicationListener getApplicationListener () {
		return new OpenGLTest();
	}
	
	@Override
	public GwtApplicationConfiguration getConfig () {
		return new GwtApplicationConfiguration(480, 320);
	}
}
