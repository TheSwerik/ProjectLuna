package de.swerik.luna;

import android.os.Bundle;
import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import de.swerik.ForeignTest.ForeignGame;
import de.swerik.MorpheusTest.Main;

public class AndroidLauncher extends AndroidApplication {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
//		initialize(new Main(), config);
		initialize(new Luna(), config);
//        initialize(new ForeignGame(), config);
    }
}
