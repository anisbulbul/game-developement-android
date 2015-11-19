package com.anrosoft.jumpinghero.assets;

import java.util.ArrayList;
import java.util.Random;

import com.anrosoft.jumpinghero.ActionResolver;
import com.anrosoft.jumpinghero.files.FileHandlers;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class GameAssets {
	
	public static boolean isBackButtonEnable = false;

	public static float originalWidth = Gdx.graphics.getWidth();
	public static float originalHeight = Gdx.graphics.getHeight();

	// constant fields
	public static final String MORE_APPS_URL = "http://www.anrosoft.com";
	// motivation sentance
	public static final String MOTIVATION = "You are the best";
	public static final int NUMBER_OF_SPRINGS_PART = 3;
	public static final int NUMBER_OF_COLS = 20;
	public static final int NUMBER_OF_ROWS = 15;
	public static final int NUMBER_OF_BRICKS = 5;
	public static final int NUMBER_OF_EFFECTS = 1;
	public static final int NUMBER_OF_VOLUME_CINTROLLERS = 4;
	public static final int NUMBER_OF_HERO_OPTION = 2;
	public static final float SCOREBOARD_MOVE_SPEED_RATIO = 1.0f + originalHeight / 1500;
	public static final float squarRoot2 = 1.414213f;
	public static float GAME_TIME = Gdx.graphics.getDeltaTime();
	public static int FRAME_PER_SECOND = 45;
	public static int NUMBER_OF_COMPON_PER_SECOND = 10;
	public static final float EFFECT_SPEED = 0.5f * Gdx.graphics.getWidth() / 150;
	public static final float BUTTON_MOVE_INCEMENT = Gdx.graphics.getHeight() / 40;
	public static final float HERO_BULLET_SPEED_X = Gdx.graphics.getWidth() / 150;
	public static final float HERO_BULLET_SPEED_Y = Gdx.graphics.getHeight() / 65;

	public static final float BOMB_SPEED_X = Gdx.graphics.getWidth() / 150;
	public static final float BOMB_SPEED_Y = Gdx.graphics.getHeight() / 65;

	// game object assets animations
	public static ArrayList<AssetObject> assets;
	public static ArrayList<IceEffect> iceEffect;
	public static ArrayList<Effect> effects;
	public static ArrayList<TextEffect> textEffects;
	public static Hero hero;
	public static TextureRegion[] herosTexture;
	public static Texture arrowsTexture;

	public static String tempNameFont;

	public static final float gaps = originalWidth / 20;
	public static final float BUTTON_WIDTH = originalWidth / 7;

	// volume values
	public static float musicVolume = 1.0f;
	public static float actionsVolume = 1.0f;
	public static float clicksVolume = 1.0f;
	public static float allVolume = 1.0f;

	// stage origin and point
	public static float originX = 0;
	public static float originY = 0;

	public static float lastX = originalWidth;
	public static float lastY = originalHeight;

	// screen dimension
	public static float width = lastX - originX;
	public static float height = lastY - originY;
	public static float MOVE_INCREMENT = width / 50;
	// alpha
	public static float gameAlpha = 1.0f;

	public static String GAME_STATE = "none";
	public static String GAME_MENU = "game_menu_9_89";
	public static String GAME_START = "start_99_55";
	public static String GAME_PLAY = "789_play_8";

	// game fonts
	public static BitmapFont font;
	public static BitmapFont font2;
	public static BitmapFont font3;

	// game status
	public static int heroTextureIndex;
	public static int gameScore;
	public static int gameHighScore;
	public static int noOfGamePlays;
	public static String gameWorldStatus;
	public static String heroStoreStatus;
	public static String[] gameStagePerWorldStatus;

	public static int playButtonIndex;
	public static float playBackgroundPosX;
	public static float playBackgroundPosY;
	public static float playBackgroundWidth;
	public static float playBackgroundHeight;
	public static float arrowMove;

	public static float scoreBoardMove = 0.0f;
	public static int currentSpring;
	public static float currentSpeedY;

	// game screen status
	public static boolean isHeroRight;
	public static boolean isRightArrow;
	public static boolean isLeftArrow;
	public static boolean isGameOver;
	public static boolean isGamePause;
	public static boolean isGamePauseAndroid;
	public static boolean isCompon;
	public static boolean isStageCompon;
	public static boolean isJumpAtleastOne;

	public static boolean isAnimDark;
	public static boolean isAnimWhite;

	public static Music backgroundMusic;
	public static Music gameStageBackgroundMusic;
	public static Music gameOverSound;
	public static Sound jumpSound;
	public static Sound buttonClickSound;
	public static Sound earnPointSound;
	public static Sound lyingHeroSound;
	public static Sound fallHeroSound;

	public static Texture menuBackgroundTexture;
	public static Texture allBackgroundTexture;
	public static Texture playBackgroundTexture;
	public static Texture screenOverTexture;
	public static Texture quitScreenBackgroundTexture;

	public static Texture volumeProgressTexture[];
	public static Texture volumeIconTexture[];

	public static Texture playMainButtonTexture;
	public static Texture playButtonTexture;
	public static Texture pauseButtonTexture;
	public static Texture menuButtonTexture;
	public static Texture achievementButtonTexture;
	public static Texture rateButtonTexture;
	public static Texture reloadButtonTexture;
	public static Texture quitButtonTexture;
	public static Texture shareButtonTexture;
	public static Texture settingButtonTexture;
	public static Texture leaderboardButtonTexture;
	public static Texture yesButtonTexture;
	public static Texture noButtonTexture;

	public static TextureRegion[] springsPart;
	public static Texture[] effetsTexture;
	public static Texture iceEffectTexture;

	public static TextureRegion heroBulletTexture;

	private static ActionResolver actionResolver;

	public static void setActionResolver(ActionResolver tempActionResolver) {
		actionResolver = tempActionResolver;
	}

	public static void appInit() {
		reloadAssets();
		newGame();
		stopAllSound();
		GAME_STATE = GAME_MENU;
	}

	public static void reloadAssets() {

		backgroundMusic = initMusic("sounds/background.mp3");
		gameStageBackgroundMusic = initMusic("sounds/background.mp3");
		buttonClickSound = initSound("sounds/buttonclick.mp3");
		earnPointSound = initSound("sounds/point.wav");
		jumpSound = initSound("sounds/hero_jump.wav");
		lyingHeroSound = initSound("sounds/lying.ogg");
		fallHeroSound = initSound("sounds/fall.mp3");
		gameOverSound = initMusic("sounds/game_over.mp3");

		tempNameFont = "03";
		font = initFont("fonts/" + tempNameFont + ".fnt", "fonts/"
				+ tempNameFont + ".png");
		font.setScale((height / font.getBounds("A").height) / 20);

		tempNameFont = "01";
		font2 = initFont("fonts/" + tempNameFont + ".fnt", "fonts/"
				+ tempNameFont + ".png");
		font2.setScale((height / font2.getBounds("A").height) / 70,
				(height / font2.getBounds("A").height) / 40);

		tempNameFont = "00";
		font3 = initFont("fonts/" + tempNameFont + ".fnt", "fonts/"
				+ tempNameFont + ".png");
		font3.setScale((width / font3.getBounds("A").width) / 15,
				(height / font3.getBounds("A").height) / 15);

		menuBackgroundTexture = initTexture("background_screens/background_menu.png");
		allBackgroundTexture = initTexture("background_screens/background_menu.png");
		screenOverTexture = initTexture("background_screens/screenover.png");
		quitScreenBackgroundTexture = initTexture("background_screens/background_quitscreen.png");

		playBackgroundTexture = initTexture("background_screens/background_play.png");

		playMainButtonTexture = initTexture("buttons/playmainbutton.png");
		playButtonTexture = initTexture("buttons/playbutton.png");
		pauseButtonTexture = initTexture("buttons/pausebutton.png");
		achievementButtonTexture = initTexture("buttons/achivementbutton.png");
		rateButtonTexture = initTexture("buttons/ratebutton.png");
		shareButtonTexture = initTexture("buttons/sharebutton.png");
		settingButtonTexture = initTexture("buttons/settingbutton.png");
		leaderboardButtonTexture = initTexture("buttons/leaderboardbutton.png");
		reloadButtonTexture = initTexture("buttons/reloadbutton.png");
		quitButtonTexture = initTexture("buttons/crossbutton.png");
		menuButtonTexture = initTexture("buttons/menubutton.png");
		yesButtonTexture = initTexture("buttons/yesbutton.png");
		noButtonTexture = initTexture("buttons/nobutton.png");

		volumeProgressTexture = new Texture[NUMBER_OF_VOLUME_CINTROLLERS];
		for (int i = 0; i < NUMBER_OF_VOLUME_CINTROLLERS; i++) {
			volumeProgressTexture[i] = initTexture("volume_controlls/volume_progress"
					+ (i + 1) + ".png");
		}
		volumeIconTexture = new Texture[NUMBER_OF_VOLUME_CINTROLLERS];
		for (int i = 0; i < NUMBER_OF_VOLUME_CINTROLLERS; i++) {
			volumeIconTexture[i] = initTexture("volume_controlls/volume_icon"
					+ (i + 1) + ".png");
		}

		springsPart = new TextureRegion[NUMBER_OF_SPRINGS_PART];
		springsPart[0] = initTextureRegion("bricks/left_part.png");
		springsPart[1] = initTextureRegion("bricks/right_part.png");
		springsPart[2] = initTextureRegion("bricks/up_part.png");

		effetsTexture = new Texture[NUMBER_OF_EFFECTS];
		for (int i = 0; i < NUMBER_OF_EFFECTS; i++) {
			effetsTexture[i] = initTexture("effects/effect" + (i + 1) + ".png");
		}

		arrowsTexture = initTexture("bricks/arrow.png");

		herosTexture = new TextureRegion[NUMBER_OF_HERO_OPTION];
		herosTexture[0] = initTextureRegion("heros/hero_stand.png");
		herosTexture[1] = initTextureRegion("heros/hero_jump.png");

		iceEffectTexture = initTexture("effects/ice.png");

		assets = new ArrayList<AssetObject>();
		assets.clear();
		textEffects = new ArrayList<TextEffect>();
		textEffects.clear();
		effects = new ArrayList<Effect>();
		effects.clear();
		iceEffect = new ArrayList<IceEffect>();
		iceEffect.clear();
	}

	public static void newGame() {

		currentSpring = 0;
		currentSpeedY = 1;
		heroTextureIndex = 0;
		arrowMove = 0.0f;

		isJumpAtleastOne = false;
		isCompon = false;
		isGamePause = false;
		isGamePauseAndroid = false;
		isAnimDark = false;
		isAnimWhite = false;
		isStageCompon = false;
		isGameOver = false;
		GAME_STATE = GAME_START;
		GAME_TIME = Gdx.graphics.getDeltaTime();

		gameScore = 0;
		isCompon = false;
		gameAlpha = 1.0f;

		playBackgroundPosX = 0;
		playBackgroundPosY = 0;
		playBackgroundWidth = width;
		playBackgroundHeight = height;

		isCompon = false;
		isAnimDark = false;
		isAnimWhite = false;
		isStageCompon = false;
		noOfGamePlays = 0;
		gameHighScore = 0;
		musicVolume = 1;
		actionsVolume = 1;
		clicksVolume = 1;
		allVolume = 1;

		assets.clear();
		effects.clear();
		textEffects.clear();

		noOfGamePlays = 0;
		gameHighScore = 0;
		currentSpring = 0;

		hero = new Hero(0.15f, heroTextureIndex);

		iceEffect.clear();
		for (int i = 0; i <= height; i += 15) {
			float tempWidth = Math.abs(new Random().nextInt((int) width / 30))
					+ width / 30;
			float tempSpeed = BOMB_SPEED_Y / 10 < 1 ? 1 : BOMB_SPEED_Y / 10;
			tempSpeed = Math.abs(new Random().nextInt((int) tempSpeed))
					+ tempSpeed;
			iceEffect.add(new IceEffect(Math.abs(new Random()
					.nextInt((int) width)), i, tempWidth, tempWidth, 0,
					tempSpeed));
		}

		assets.clear();
		for (int i = 0; i < 4; i++) {
			float assetPosX = Math.abs(new Random().nextInt((int) (width / 2)))
					+ width / 8;

			float assetPosY = Math
					.abs(new Random().nextInt((int) (height / 4)))
					+ i
					* height
					/ 4;
			if (assetPosY < 3 * width / 8) {
				assetPosY = 3 * width / 8;
			}

			for (int j = i - 1; j >= 0; j--) {
				float tempDifference = Math.abs(assetPosY
						- assets.get(j).assetPosY);
				if (tempDifference < 3 * width / 8) {
					tempDifference = 3 * width / 8 - tempDifference;
					if (assetPosY < assets.get(j).assetPosY) {
						assetPosY -= tempDifference;
					} else {
						assetPosY += tempDifference;
					}
					j = -1;
				}
			}

			for (int j = i - 1; j >= 0; j--) {
				float tempDifference = Math.abs(assetPosX
						- assets.get(j).assetPosX);
				if (tempDifference < width / 4) {
					tempDifference = width / 4 - tempDifference;
					if (assetPosX < assets.get(j).assetPosX) {
						assetPosX -= tempDifference;
					} else {
						assetPosX += tempDifference;
					}
					j = -1;
				}
			}
			assets.add(new AssetObject(assetPosX, assetPosY, width / 4,
					width / 16, 0, 0, 0, false, Category.BLOCK, 1.0f));
		}

		FileHandlers.initGameData();

	}

	public static void stopAllSound() {

		if (backgroundMusic != null)
			backgroundMusic.pause();
		if (gameStageBackgroundMusic != null)
			gameStageBackgroundMusic.pause();
		if (buttonClickSound != null)
			buttonClickSound.pause();
		if (earnPointSound != null)
			earnPointSound.pause();
		if (jumpSound != null)
			jumpSound.pause();
		if (gameOverSound != null)
			gameOverSound.pause();
	}

	public static void pauseAllSound() {

		if (backgroundMusic != null && backgroundMusic.isPlaying()) {
			backgroundMusic.pause();
		}
		if (gameStageBackgroundMusic != null
				&& gameStageBackgroundMusic.isPlaying()) {
			gameStageBackgroundMusic.pause();
		}
		if (gameOverSound != null && gameOverSound.isPlaying()) {
			gameOverSound.pause();
		}
		if (jumpSound != null) {
			jumpSound.pause();
		}

		if (buttonClickSound != null) {
			buttonClickSound.pause();
		}
		if (earnPointSound != null) {
			earnPointSound.pause();
		}
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

	public static TextureRegion initTextureRegion(String path) {
		return new TextureRegion(initTexture(path));
	}

	public static void disposeAllAssets() {

		if (font != null)
			font.dispose();
		if (font2 != null)
			font2.dispose();
		if (font3 != null)
			font3.dispose();

		if (backgroundMusic != null)
			backgroundMusic.dispose();
		if (gameStageBackgroundMusic != null)
			gameStageBackgroundMusic.dispose();
		if (gameOverSound != null)
			gameOverSound.dispose();
		if (jumpSound != null)
			jumpSound.dispose();
		if (buttonClickSound != null)
			buttonClickSound.dispose();
		if (earnPointSound != null)
			earnPointSound.dispose();
		if (lyingHeroSound != null)
			lyingHeroSound.dispose();
		if (fallHeroSound != null)
			fallHeroSound.dispose();

		if (menuBackgroundTexture != null)
			menuBackgroundTexture.dispose();
		if (allBackgroundTexture != null)
			allBackgroundTexture.dispose();
		if (playBackgroundTexture != null)
			playBackgroundTexture.dispose();
		if (screenOverTexture != null)
			screenOverTexture.dispose();
		if (quitScreenBackgroundTexture != null)
			quitScreenBackgroundTexture.dispose();
		if (playMainButtonTexture != null)
			playMainButtonTexture.dispose();
		if (playButtonTexture != null)
			playButtonTexture.dispose();
		if (pauseButtonTexture != null)
			pauseButtonTexture.dispose();
		if (menuButtonTexture != null)
			menuButtonTexture.dispose();
		if (achievementButtonTexture != null)
			achievementButtonTexture.dispose();
		if (rateButtonTexture != null)
			rateButtonTexture.dispose();
		if (reloadButtonTexture != null)
			reloadButtonTexture.dispose();
		if (quitButtonTexture != null)
			quitButtonTexture.dispose();
		if (shareButtonTexture != null)
			shareButtonTexture.dispose();
		if (settingButtonTexture != null)
			settingButtonTexture.dispose();
		if (leaderboardButtonTexture != null)
			leaderboardButtonTexture.dispose();
		if (yesButtonTexture != null)
			yesButtonTexture.dispose();
		if (noButtonTexture != null)
			noButtonTexture.dispose();
		if (iceEffectTexture != null)
			iceEffectTexture.dispose();
		if (heroBulletTexture != null)
			heroBulletTexture.getTexture().dispose();
		if (assets != null)
			assets.clear();
		if (iceEffect != null)
			iceEffect.clear();
		if (effects != null)
			effects.clear();
		if (textEffects != null)
			textEffects.clear();
		if (arrowsTexture != null)
			arrowsTexture.dispose();

		if (volumeProgressTexture != null) {
			for (int i = 0; i < volumeProgressTexture.length; i++) {
				volumeProgressTexture[i].dispose();
			}
		}
		if (volumeIconTexture != null) {
			for (int i = 0; i < volumeIconTexture.length; i++) {
				volumeIconTexture[i].dispose();
			}
		}
		if (springsPart != null) {
			for (int i = 0; i < springsPart.length; i++) {
				springsPart[i].getTexture().dispose();
			}
		}
		if (effetsTexture != null) {
			for (int i = 0; i < effetsTexture.length; i++) {
				effetsTexture[i].dispose();
			}
		}
		if (herosTexture != null) {
			for (int i = 0; i < herosTexture.length; i++) {
				herosTexture[i].getTexture().dispose();
			}
		}

	}
}
