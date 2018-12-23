package com.jga.brickbreaker.common;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.jga.brickbreaker.BrickbreakerGame;

public class ScoreController {

    // == constants ==
    private static final String HIGH_SCORE_KEY = "highscore";

    // == attributes ==
    private final Preferences prefs;

    private int score;
    private int highScore;

    // == constructor ==
    public ScoreController() {
        prefs = Gdx.app.getPreferences(BrickbreakerGame.class.getSimpleName());
        highScore = prefs.getInteger(HIGH_SCORE_KEY, 0);
    }

    // == public methods ==
    public void reset(){
        score =0;
    }

    public void addScore(int toAdd){
        score += toAdd;
    }

    public void updateHighScore(){
        if (score < highScore){
            return;
        }

        highScore = score;
        prefs.putInteger(HIGH_SCORE_KEY, highScore);
        prefs.flush();
    }

    public String getScoreScring(){
        return Integer.toString(score);
    }

    private String getHighScoreString(){
        return Integer.toString(highScore);
    }

    // == private methods ==
}
