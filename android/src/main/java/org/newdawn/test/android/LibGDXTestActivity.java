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
       //Bug in the nexus 4 causes hard locks when changing screen orientation with AA.
       if(android.os.Build.MODEL.contains("Nexus 4")) {
    	   config.numSamples = 0;
       } else {
    	   config.numSamples = 2;
       }
       initialize(new OpenGLTest(), config);
   }
}
