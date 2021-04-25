package com.redlotus.galacticwarfare.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.redlotus.galacticwarfare.GalacticWarfareGame;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = "Galactic Warfare";
		config.width = 800;
		config.height = 480;
		//new LwjglApplication(new GameScreen(), config);
		new LwjglApplication(new GalacticWarfareGame(), config);
	}
}
