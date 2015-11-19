package com.anisbulbul.car.race.assets;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;

public class GameAssets {

	public final int NUMBER_OF_CARS = 10;
	public final int NUMBER_OF_MY_CARS = 10;
	public final int NUMBER_OF_ROADS = 8;
	public final int NUMBER_OF_LEVELS = 5;
	public final int NUMBER_OF_FIRES = 16;

	
	public static int myCarNumber;
	public static int roadNumber;
	public static int gameLevel;
	public static int gameScoreIndex;

	public static int GAME_START;
	public static int GAME_MENU;
	public static int GAME_ACCIDENT;
	public static int GAME_FINISHED;
	public static int GAME_STATE;

	public static boolean isGameSound;
	public static boolean isGamePause;
	public static boolean gameTriality;

	public static float backgroundSpeed;
	public static float carSpeed;
	public static float gameScore;
	public static float gameEndDelay;
	public static float myCarRotation;

	public static float backgroundSpeedLowLimit;
	public static float backgroundSpeedHighLimit;

	public static float carSpeedLowLimit;
	public static float carSpeedHighLimit;

	public static Sound clickSound;

	static {
		reloadAssetsState();
	}

	public static void reloadAssetsState() {

		myCarNumber = 0;
		gameLevel = 0;
		roadNumber = 0;
		isGameSound = true;
		clickSound = initSound("sounds/click.mp3");
		reloadNewGame();
	}

	public static void reloadNewGame() {

		gameTriality = false;
		isGamePause = false;
		GAME_STATE = 10;
		GAME_START = 11;
		GAME_MENU = 12;
		GAME_ACCIDENT = 13;
		GAME_FINISHED = 14;
		gameEndDelay = 100;
		gameScoreIndex = -1;
		myCarRotation = 0;
		backgroundSpeed = 5;
		carSpeed = 2;
		backgroundSpeedLowLimit = 5;
		backgroundSpeedHighLimit = 60;
		carSpeedLowLimit = 2;
		carSpeedHighLimit = 10;
		gameScore = 0;
		GAME_STATE = GAME_START;

	}

	public static BitmapFont initFont(String fnt, String png) {
		return new BitmapFont(Gdx.files.internal(fnt), Gdx.files.internal(png),
				false);
	}

	public static Music initMusic(String path) {
		return Gdx.audio.newMusic(Gdx.files.internal(path));
	}

	public static Sound initSound(String path) {
		return Gdx.audio.newSound(Gdx.files.internal(path));
	}

	public static Texture initTexture(String path) {
		return new Texture(Gdx.files.internal(path));
	}
}
