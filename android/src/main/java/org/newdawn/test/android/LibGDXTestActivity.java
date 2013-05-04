package org.newdawn.test.android;

import org.newdawn.test.core.OpenGLTest;

import android.os.Bundle;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;

public class LibGDXTestActivity extends AndroidApplication {
	
	@Override
   public void onCreate(Bundle savedInstanceState) {
       super.onCreate(savedInstanceState);
       AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
       config.useGL20 = true;
       config.useWakelock = true;
       config.useAccelerometer = false;
       config.useCompass = false;
       config.numSamples = 2;
       initialize(new OpenGLTest(), config);
   }
}
