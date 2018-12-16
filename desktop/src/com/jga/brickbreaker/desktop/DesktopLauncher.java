package com.jga.brickbreaker.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.jga.brickbreaker.BrickbreakerGame;
import com.jga.brickbreaker.config.GameConfig;
import com.jga.util.ads.NullAdController;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.width = GameConfig.WIDTH;
		config.height = GameConfig.HEIGHT;

		new LwjglApplication(new BrickbreakerGame(NullAdController.INSTANCE), config);
	}
}
