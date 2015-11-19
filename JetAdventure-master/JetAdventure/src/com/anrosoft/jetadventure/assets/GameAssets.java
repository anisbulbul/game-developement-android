package com.anrosoft.jetadventure.assets;

import java.util.ArrayList;
import java.util.Random;

import com.anrosoft.jetadventure.ActionResolver;
import com.anrosoft.jetadventure.files.FileHandlers;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class GameAssets {

	public static boolean isBackButtonEnable = false;

	public static float originalWidth = Gdx.graphics.getWidth();
	public static float originalHeight = Gdx.graphics.getHeight();

	// constant fields
	public static final String MORE_APPS_URL = "http://www.anrosoft.com";
	// motivation sentance
	public static final String MOTIVATION = "You are the best";

	public static final int NUMBER_OF_STAGE_BACKGROUNDS = 3;
	public static final int NUMBER_OF_COLS = 20;
	public static final int NUMBER_OF_ROWS = 15;
	public static final int NUMBER_OF_OBJECTS = 110;
	public static final int NUMBER_OF_POINTS = 3;
	public static final int NUMBER_OF_BULLET = 2;
	public static final int NUMBER_OF_ANIM = 3;
	public static final int NUMBER_OF_BRICKS = 5;
	public static final int NUMBER_OF_EFFECTS = 5;
	public static final int NUMBER_OF_VOLUME_CINTROLLERS = 4;
	public static final int NUMBER_OF_REVOLD_BULLET = 5;
	public static final float SCOREBOARD_MOVE_SPEED_RATIO = 1.0f + originalHeight / 1500;
	public static final float squarRoot2 = 1.414213f;
	public static float GAME_TIME = Gdx.graphics.getDeltaTime();
	public static int FRAME_PER_SECOND = 45;
	public static int NUMBER_OF_COMPON_PER_SECOND = 10;
	public static final float EFFECT_SPEED = 0.1f * Gdx.graphics.getWidth() / 150;
	public static final float BUTTON_MOVE_INCEMENT = Gdx.graphics.getHeight() / 40;
	public static final float HERO_BULLET_SPEED_X = Gdx.graphics.getWidth() / 150;
	public static final float HERO_BULLET_SPEED_Y = Gdx.graphics.getHeight() / 65;

	public static final float BOMB_SPEED_X = Gdx.graphics.getWidth() / 150;
	public static final float BOMB_SPEED_Y = Gdx.graphics.getHeight() / 65;

	// sprite animations
	public static TextureAtlas[] textureAnimAtlas;
	public static TextureAtlas[] textureHeroAtlas;
	public static TextureAtlas[] textureBulletsAtlas;

	// object atlas
	public static TextureAtlas allTextureAtlas;

	// game object assets animations
	public static ArrayList<AssetObject> assets;
	public static ArrayList<PointObject> points;
	public static ArrayList<AnimationObject> anims;
	public static ArrayList<AnimationFireObject> animFires;
	public static ArrayList<Effect> effects;
	public static ArrayList<HeroBullet> heroBullets;
	public static ArrayList<IceEffect> iceEffect;
	public static ArrayList<SnackEnemy> snackEnemies;
	public static Hero hero;

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

	// alpha
	public static float gameAlpha = 1.0f;

	public static String GAME_STATE = "none";
	public static String GAME_START = "start";
	public static String GAME_MENU = "menu";

	// game fonts
	public static BitmapFont font;
	public static BitmapFont font2;

	// game status
	public static int playBackgroundTextureIndex;
	public static int heroAtlasIndex;
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

	public static float scoreBoardMove = 0.0f;

	// game screen status
	public static boolean isHeroRight;
	public static boolean isRightArrow;
	public static boolean isLeftArrow;
	public static boolean isGameOver;
	public static boolean isGameSound;
	public static boolean isGamePause;
	public static boolean isGamePauseAndroid;
	public static boolean isCompon;
	public static boolean isStageCompon;

	public static boolean isAnimDark;
	public static boolean isAnimWhite;

	public static Music backgroundMusic;
	public static Music gameStageBackgroundMusic;
	public static Music gameOverMusic;
	public static Sound buttonClickSound;
	public static Sound heroBulletSound;
	public static Sound heroDiedSound;
	public static Sound enemyDiedSound;
	public static Sound bombSound;
	public static Sound animBulletSound;

	public static TextureRegion menuBackgroundTextureRegion;
	public static TextureRegion allBackgroundTextureRegion;
	public static TextureRegion[] playBackgroundTextureRegion;
	public static TextureRegion screenOverTextureRegion;
	public static TextureRegion quitScreenBackgroundTextureRegion;

	public static TextureRegion volumeProgressTextureRegion[];
	public static TextureRegion volumeIconTextureRegion[];

	public static Texture playMainButtonTexture;
	public static Texture playButtonTexture;
	public static Texture pauseButtonTexture;
	public static Texture menuButtonTexture;
	public static Texture achievementButtonTexture;
	public static Texture rateButtonTexture;
	public static Texture reloadButtonTexture;
	public static Texture quitButtonTexture;
	public static Texture yesButtonTexture;
	public static Texture noButtonTexture;
	public static Texture shareButtonTexture;
	public static Texture settingButtonTexture;
	public static Texture leaderboardButtonTexture;

	public static TextureRegion tabToPlayTesture;
	public static TextureRegion snakcHeadTextureRegion;
	public static TextureRegion snakcBodyTextureRegion;

	public static TextureRegion[] effetsTextureRegion;
	public static TextureRegion iceEffectTextureRegion;

	public static TextureRegion[] bricksTextureRegion;
	public static TextureRegion heroBulletTextureRegion;

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
		gameStageBackgroundMusic = initMusic("sounds/stage_background.mp3");
		buttonClickSound = initSound("sounds/buttonclick.mp3");
		heroBulletSound = initSound("sounds/hero_bullet.mp3");
		heroDiedSound = initSound("sounds/hero_died.mp3");
		bombSound = initSound("sounds/bombsound.mp3");
		animBulletSound = initSound("sounds/anim_bullet_sound.mp3");
		enemyDiedSound = initSound("sounds/enemy_died.mp3");
		gameOverMusic = initMusic("sounds/level_failed.mp3");

		tempNameFont = "03";
		font = initFont("fonts/" + tempNameFont + ".fnt", "fonts/"
				+ tempNameFont + ".png");
		font.setScale((height / font.getBounds("A").height) / 20);

		tempNameFont = "01";
		font2 = initFont("fonts/" + tempNameFont + ".fnt", "fonts/"
				+ tempNameFont + ".png");
		font2.setScale((height / font2.getBounds("A").height) / 70,
				(height / font2.getBounds("A").height) / 40);

		textureAnimAtlas = new TextureAtlas[NUMBER_OF_ANIM + 2];
		for (int i = 0; i < NUMBER_OF_ANIM; i++) {
			textureAnimAtlas[i] = new TextureAtlas(
					Gdx.files.internal("animations/enemys/enemy" + (i + 1)
							+ ".atlas"));
		}

		textureHeroAtlas = new TextureAtlas[3];
		textureHeroAtlas[0] = new TextureAtlas(
				Gdx.files.internal("animations/heros/hero_fly.atlas"));
		textureHeroAtlas[1] = new TextureAtlas(
				Gdx.files.internal("animations/heros/hero_walk.atlas"));
		textureHeroAtlas[2] = new TextureAtlas(
				Gdx.files.internal("animations/heros/hero_die.atlas"));

		textureAnimAtlas = new TextureAtlas[NUMBER_OF_ANIM + 2];
		for (int i = 0; i < NUMBER_OF_ANIM; i++) {
			textureAnimAtlas[i] = new TextureAtlas(
					Gdx.files.internal("animations/enemys/enemy" + (i + 1)
							+ ".atlas"));
		}
		textureAnimAtlas[NUMBER_OF_ANIM] = new TextureAtlas(
				Gdx.files.internal("animations/bombs/bomb.atlas"));
		textureAnimAtlas[NUMBER_OF_ANIM + 1] = new TextureAtlas(
				Gdx.files.internal("animations/bombs/bomb_1.atlas"));

		textureBulletsAtlas = new TextureAtlas[NUMBER_OF_BULLET];
		for (int i = 0; i < NUMBER_OF_BULLET; i++) {
			textureBulletsAtlas[i] = new TextureAtlas(
					Gdx.files.internal("animations/bullets/bullet" + (i + 1)
							+ ".atlas"));
		}

		// buttons initializations ....
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
		yesButtonTexture = initTexture("buttons/yesbutton.png");
		noButtonTexture = initTexture("buttons/nobutton.png");
		menuButtonTexture = initTexture("buttons/menubutton.png");

		allTextureAtlas = new TextureAtlas(
				Gdx.files.internal("others/game_assets.atlas"));

		menuBackgroundTextureRegion = allTextureAtlas
				.findRegion("background_menu");
		allBackgroundTextureRegion = allTextureAtlas
				.findRegion("background_menu");
		screenOverTextureRegion = allTextureAtlas.findRegion("screenover");
		quitScreenBackgroundTextureRegion = allTextureAtlas
				.findRegion("background_quitscreen");

		playBackgroundTextureRegion = new TextureRegion[NUMBER_OF_STAGE_BACKGROUNDS];
		for (int i = 0; i < NUMBER_OF_STAGE_BACKGROUNDS; i++) {
			playBackgroundTextureRegion[i] = allTextureAtlas
					.findRegion("background_play" + (i + 1) + "");
		}

		bricksTextureRegion = new TextureRegion[NUMBER_OF_BRICKS];
		for (int i = 0; i < NUMBER_OF_BRICKS; i++) {
			bricksTextureRegion[i] = allTextureAtlas.findRegion("brick"
					+ (i + 1));
		}

		tabToPlayTesture = allTextureAtlas.findRegion("tabtoplay");
		snakcHeadTextureRegion = new TextureRegion(
				allTextureAtlas.findRegion("snack_head"));
		snakcBodyTextureRegion = allTextureAtlas.findRegion("snack_body");

		volumeProgressTextureRegion = new TextureRegion[NUMBER_OF_VOLUME_CINTROLLERS];
		for (int i = 0; i < NUMBER_OF_VOLUME_CINTROLLERS; i++) {
			volumeProgressTextureRegion[i] = allTextureAtlas
					.findRegion("volume_progress" + (i + 1) + "");
		}
		volumeIconTextureRegion = new TextureRegion[NUMBER_OF_VOLUME_CINTROLLERS];
		for (int i = 0; i < NUMBER_OF_VOLUME_CINTROLLERS; i++) {
			volumeIconTextureRegion[i] = allTextureAtlas
					.findRegion("volume_icon" + (i + 1) + "");
		}

		effetsTextureRegion = new TextureRegion[NUMBER_OF_EFFECTS];
		for (int i = 0; i < NUMBER_OF_EFFECTS; i++) {
			effetsTextureRegion[i] = allTextureAtlas.findRegion("effect"
					+ (i + 1) + "");
		}

		iceEffectTextureRegion = allTextureAtlas.findRegion("ice");

		assets = new ArrayList<AssetObject>();
		assets.clear();
		anims = new ArrayList<AnimationObject>();
		anims.clear();
		animFires = new ArrayList<AnimationFireObject>();
		animFires.clear();
		effects = new ArrayList<Effect>();
		effects.clear();
		heroBullets = new ArrayList<HeroBullet>();
		heroBullets.clear();
		points = new ArrayList<PointObject>();
		points.clear();
		iceEffect = new ArrayList<IceEffect>();
		iceEffect.clear();
		snackEnemies = new ArrayList<SnackEnemy>();
		snackEnemies.clear();

	}

	public static void newGame() {

		isGameSound = false;
		isCompon = false;
		isGamePause = false;
		isGamePauseAndroid = false;
		isAnimDark = false;
		isAnimWhite = false;
		isStageCompon = false;
		isGameOver = false;
		GAME_STATE = GAME_START;
		GAME_TIME = Gdx.graphics.getDeltaTime();

		heroAtlasIndex = 0;
		gameScore = 0;
		isCompon = false;
		gameAlpha = 1.0f;

		playBackgroundPosX = 0;
		playBackgroundPosY = 0;
		playBackgroundWidth = width;
		playBackgroundHeight = height;

		isGameSound = false;
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
		anims.clear();
		effects.clear();
		heroBullets.clear();
		points.clear();
		animFires.clear();

		playBackgroundTextureIndex = Math.abs(new Random().nextInt(100))
				% NUMBER_OF_STAGE_BACKGROUNDS;

		noOfGamePlays = 0;
		gameHighScore = 0;

		hero = new Hero(0.15f, true);

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

		for (int i = 0; i < NUMBER_OF_REVOLD_BULLET; i++) {
			heroBullets.add(new HeroBullet(i * width / 5, -height,
					hero.heroHeight / 2, hero.heroHeight / 4,
					-HERO_BULLET_SPEED_X, 0, 0, 1.0f, 0.15f));
		}

		for (int i = 0; i < NUMBER_OF_ANIM; i++) {
			snackEnemies.add(new SnackEnemy(width / 2, -height, 0, width / 32,
					0, 0, width / 300, 0, width / 2, 0, 103, 1.0f, true));
		}

		StageLoad.stageLoad(0, 0);
		FileHandlers.initGameData();

	}

	public static void stopAllSound() {

		if (backgroundMusic != null)
			backgroundMusic.stop();
		if (gameStageBackgroundMusic != null)
			gameStageBackgroundMusic.stop();
		if (buttonClickSound != null)
			buttonClickSound.stop();
		if (heroBulletSound != null)
			heroBulletSound.stop();
		if (heroDiedSound != null)
			heroDiedSound.stop();
		if (gameOverMusic != null)
			gameOverMusic.stop();
	}

	public static void pauseAllSound() {

		if (backgroundMusic != null && backgroundMusic.isPlaying()) {
			backgroundMusic.pause();
		}
		if (gameStageBackgroundMusic != null
				&& gameStageBackgroundMusic.isPlaying()) {
			gameStageBackgroundMusic.pause();
		}
		if (gameOverMusic != null && gameOverMusic.isPlaying()) {
			gameOverMusic.pause();
		}
		if (buttonClickSound != null) {
			buttonClickSound.pause();
		}
		if (heroBulletSound != null) {
			heroBulletSound.pause();
		}
		if (heroDiedSound != null) {
			heroDiedSound.pause();
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

	public static void disposeAllAssets() {
		if (backgroundMusic != null)
			backgroundMusic.dispose();
		if (gameStageBackgroundMusic != null)
			gameStageBackgroundMusic.dispose();
		if (gameOverMusic != null)
			gameOverMusic.dispose();
		if (buttonClickSound != null)
			buttonClickSound.dispose();
		if (heroBulletSound != null)
			heroBulletSound.dispose();
		if (heroDiedSound != null)
			heroDiedSound.dispose();
		if (enemyDiedSound != null)
			enemyDiedSound.dispose();
		if (bombSound != null)
			bombSound.dispose();
		if (animBulletSound != null)
			animBulletSound.dispose();

		if (menuBackgroundTextureRegion != null)
			menuBackgroundTextureRegion.getTexture().dispose();
		if (allBackgroundTextureRegion != null)
			allBackgroundTextureRegion.getTexture().dispose();

		if (screenOverTextureRegion != null)
			screenOverTextureRegion.getTexture().dispose();
		if (quitScreenBackgroundTextureRegion != null)
			quitScreenBackgroundTextureRegion.getTexture().dispose();

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
		if (yesButtonTexture != null)
			yesButtonTexture.dispose();
		if (noButtonTexture != null)
			noButtonTexture.dispose();
		if (shareButtonTexture != null)
			shareButtonTexture.dispose();
		if (settingButtonTexture != null)
			settingButtonTexture.dispose();
		if (leaderboardButtonTexture != null)
			leaderboardButtonTexture.dispose();

		if (tabToPlayTesture != null)
			tabToPlayTesture.getTexture().dispose();
		if (snakcHeadTextureRegion != null)
			snakcHeadTextureRegion.getTexture().dispose();
		if (snakcBodyTextureRegion != null)
			snakcBodyTextureRegion.getTexture().dispose();
		if (iceEffectTextureRegion != null)
			iceEffectTextureRegion.getTexture().dispose();
		if (heroBulletTextureRegion != null)
			heroBulletTextureRegion.getTexture().dispose();

		if (playBackgroundTextureRegion != null) {
			for (int i = 0; i < playBackgroundTextureRegion.length; i++) {
				playBackgroundTextureRegion[i].getTexture().dispose();
			}
		}
		if (volumeProgressTextureRegion != null) {
			for (int i = 0; i < volumeProgressTextureRegion.length; i++) {
				volumeProgressTextureRegion[i].getTexture().dispose();
			}
		}
		if (volumeIconTextureRegion != null) {
			for (int i = 0; i < volumeIconTextureRegion.length; i++) {
				volumeIconTextureRegion[i].getTexture().dispose();
			}
		}
		if (bricksTextureRegion != null) {
			for (int i = 0; i < bricksTextureRegion.length; i++) {
				bricksTextureRegion[i].getTexture().dispose();
			}
		}
		if (effetsTextureRegion != null) {
			for (int i = 0; i < effetsTextureRegion.length; i++) {
				effetsTextureRegion[i].getTexture().dispose();
			}
		}

	}
}
