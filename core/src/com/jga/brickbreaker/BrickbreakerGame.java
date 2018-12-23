package com.jga.brickbreaker;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.jga.brickbreaker.common.ScoreController;
import com.jga.brickbreaker.screen.game.GameScreen;
import com.jga.util.ads.AdController;
import com.jga.util.game.GameBase;

public class BrickbreakerGame extends GameBase {

	// == attributes ==
	private ScoreController scoreController;

	// == constructor ==
	public BrickbreakerGame(AdController adController) {
		super(adController);
	}

	// == public methods ==
	@Override
	public void postCreate() {
		scoreController = new ScoreController();
		setScreen(new GameScreen(this));
	}

	public ScoreController getScoreController() {
		return scoreController;
	}
}
