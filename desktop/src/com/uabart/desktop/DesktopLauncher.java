package com.uabart.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.uabart.TwitchGdxGame;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.width = 640;
		config.height = 360;
		try {
			new LwjglApplication(new TwitchGdxGame(arg[0]), config);
		} catch (Exception e) {
			new LwjglApplication(new TwitchGdxGame(), config);
		}
	}
}
