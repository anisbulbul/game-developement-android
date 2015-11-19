package com.anisbulbul.race.danger.assets;

import com.anisbulbul.race.danger.boys.Boy;
import com.anisbulbul.race.danger.files.FileHandlers;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;

public class GameAssets {

	
	public static float width = Gdx.graphics.getWidth();
	public static float height = Gdx.graphics.getHeight();
	
	public static final float FIRE_BACKGROUNDSPEED_SPEED_X = width/300;
	public static final float FAR_BACKGROUNDSPEED_SPEED_X = width/1200;
	public static final float EARTH_BACKGROUNDSPEED_SPEED_X = width/900;
	public static final float NEAR_BACKGROUNDSPEED_SPEED_X = width/300;
	public static final float BALL_BACKGROUNDSPEED_SPEED_X = width/200;
	public static final float DINOSAUR_SPEED_X = width/200;
	public static final float DINOSAUR_SPEED_Y = 0;
	public static final float FIRE_SPEED_X = width/100;
	public static final float FIRE_SPEED_Y = 0;
	
	public static final int NUMBER_OF_BOYS_SPRITE = 6;
	public static final int NUMBER_OF_BOYS = 4;

	public static final int NUMBER_OF_EARTHS = 8;
	public static final int NUMBER_OF_BALLS = 4;
	public static final int NUMBER_OF_DINOSAURS = 5;
	public static final int NUMBER_OF_FIRE_EXPLUSION = 6;
	public static final int NUMBER_OF_DRAGON_EXPLUSION = 14;
	public static int EARN_POINTS = 10;
	public static int GAME_STAGE_NUMBER = 1;
	
	public static int boyIndex;
	public static int gameScoreIndex;
	public static int[][] stageState = {{1,0,0},{0,0,0},{0,0,0}};
	public static boolean stageStateIndex = false;

	public static int GAME_START;
	public static int GAME_MENU;
	public static int GAME_FINISHED;
	public static int GAME_STATE;

	public static boolean isGameSound;
	public static boolean isGamePause;
	public static boolean isGameRaceFailed;
	public static boolean isGameRaceWin;
	public static boolean gameTriality;
		

	public static  Sound clickSound;
	public static  Sound deathSound;
	public static  Sound pointSound;
	public static  Sound fireSound;
	public static  Music winMusic;

	public static int dragonKiils;

	public static int earningPoints;

	static{
		reloadAssetsState();
	}
	
	public static void reloadAssetsState() {

		isGameSound = true;
		boyIndex = 0;
		new Boy();
		clickSound = initSound("sounds/click.mp3");
		FileHandlers.initStage();
		reloadNewGame();
	}

	public static void reloadNewGame() {

		dragonKiils = 0;
		earningPoints = 0;
		gameTriality = false;
		isGamePause = false;
		GAME_STATE = 10;
		GAME_START = 11;
		GAME_MENU = 12;
		GAME_FINISHED = 13;
		gameScoreIndex = -1;
		isGameRaceWin = false;
		isGameRaceFailed = false;
		
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
