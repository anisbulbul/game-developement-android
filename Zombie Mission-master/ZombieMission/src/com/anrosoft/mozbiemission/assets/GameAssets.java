package com.anrosoft.mozbiemission.assets;

import java.util.ArrayList;

import com.anrosoft.mozbiemission.ActionResolver;
import com.anrosoft.mozbiemission.code.GameCode;
import com.anrosoft.mozbiemission.files.FileHandlers;
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

	public static String showStagArch = "not";
	// constant fields
	public static final String MORE_APPS_URL = "http://www.anrosoft.com";
	// public static final String rootDirectory = Gdx.files
	// .getExternalStoragePath();
	public static final String zombieDirectoryName = "zombie_mission_anrosoft";

	public static final float gaps = originalHeight / 15;
	public static final float BUTTON_WIDTH = originalHeight / 6;

	public static final int NUMBER_OF_WORLDS = 3;
	public static final int NUMBER_OF_OBJECTS = 105;
	public static final int NUMBER_OF_STAGES = 10;
	public static final int NUMBER_OF_POINTS = 3;
	public static final int NUMBER_OF_COLS = 20;
	public static final int NUMBER_OF_ROWS = 15;
	public static final int NUMBER_OF_ANIM = 8;
	public static final int NUMBER_OF_EFFECTS = 5;
	public static final int NUMBER_OF_VOLUME_CINTROLLERS = 4;
	public static final int NUMBER_OF_HERO_BULLET = 4;
	public static final int NUMBER_OF_HERO_OPTION = 4;
	public static final int NUMBER_OF_REVOLD_BULLET = 5;
	public static final float squarRoot2 = 1.414213f;
	public static final float COMPON = Gdx.graphics.getHeight() / 80;
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
	public static TextureAtlas[] textureBulletAtlas;
	public static TextureAtlas[] texturePointAtlas;
	public static TextureAtlas textureAnimFireAtlas;
	public static TextureAtlas textureGlowAtlas;

	// object atlas
	public static TextureAtlas textureAtlas;

	// game object assets animations
	public static ArrayList<AssetObject> assets;
	public static ArrayList<PointObject> points;
	public static ArrayList<AnimationObject> anims;
	public static ArrayList<AnimationFireObject> animFires;
	public static ArrayList<Effect> effects;
	public static ArrayList<DoorObject> doors;
	public static ArrayList<HeroBullet> heroBullets;
	public static Hero hero;

	public static String tempNameFont;

	public static String worldCaption[];
	public static String heroCaption[];

	// game screen status
	public static boolean isHeroRight;
	public static boolean isRightArrow;
	public static boolean isLeftArrow;
	public static boolean isGameOver;
	public static boolean isGameWin;
	public static boolean isDoorOpen;
	public static boolean isGamePause;
	public static boolean isGameSound;
	public static boolean isCompon;
	public static boolean isGamePauseAndroid;
	public static boolean isStageCompon;
	public static boolean isAnimDark;
	public static boolean isAnimWhite;

	// volume values
	public static float musicVolume = 1.0f;
	public static float actionsVolume = 1.0f;
	public static float clicksVolume = 1.0f;
	public static float allVolume = 1.0f;

	// stage origin and point
	public static float originX = 0;
	public static float originY = 0;

	public static float lastX = originalWidth;
	public static float lastY = originalHeight + COMPON;

	// screen dimension
	public static float width = lastX - originX;
	public static float height = lastY - originY;

	// alpha
	public static float gameAlpha = 1.0f;

	public static String GAME_STATE = "none";
	public static String GAME_MENU = "game_menu_0934545";
	public static String GAME_PLAY = "game_play_44151";

	// motivation sentance
	public static String motivation;

	// game fonts
	public static BitmapFont font;
	public static BitmapFont font2;

	// game status
	public static int heroAtlasIndex = 0;
	public static int gameScore;
	public static int gameHighScore;
	public static int gameTotalScore;
	public static int noOfGamePlays;
	public static int worldIconTextureIndex;
	public static int stageIconTextureIndex;
	public static String gameWorldStatus;
	public static String heroStoreStatus;
	public static String[] gameStagePerWorldStatus;

	public static int playButtonIndex;

	public static Music backgroundMusic;
	public static Music gameStageBackgroundMusic;
	public static Music levelClearMusic;
	public static Music levelFailedMusic;
	public static Music levelWinMusic;
	public static Sound buttonClickSound;
	public static Sound earnPointSound;
	public static Sound heroBulletSound;
	public static Sound heroDiedSound;
	public static Sound enemyDiedSound;
	public static Sound heroJumpSound;

	public static Texture menuBackgroundTexture;
	public static Texture allBackgroundTexture;
	public static Texture stageBackgroundTexture;
	public static Texture pauseScreenTexture;
	public static Texture gameFailedScreenTexture;
	public static Texture gameWinScreenTexture;
	public static Texture screenOverTextures;
	public static Texture quitScreenBackgroundTextures;

	public static Texture volumeProgressTexture[];
	public static Texture volumeIconTexture[];
	public static Texture handleBackgroundTexture;
	public static Texture joystickTexture;
	public static Texture rightArrowButtonTexture;
	public static Texture leftArrowButtonTexture;
	public static Texture crossButtonTextures;
	public static Texture buyButtonTexture;
	public static Texture cancelButtonTexture;
	public static Texture okButtonTexture;

	public static Texture ratingStarUpButtonTexture;
	public static Texture ratingStarDwonButtonTexture;

	public static Texture[] worldIconTexture;
	public static Texture[] heroIconTexture;
	public static Texture worldLockIconTexture;
	public static Texture[] gameWorldBackgrounds;

	public static Texture stageIconTexture;
	public static Texture stageLockIconTexture;
	public static Texture heroBuyIconTexture;

	public static Texture stageBoardTexture;
	public static Texture storeBoardTexture;

	public static Texture playButtonTexture;
	public static Texture storeButtonTexture;
	public static Texture nextButtonTexture;
	public static Texture menuButtonTexture;
	public static Texture reloadButtonTexture;
	public static Texture achievementButtonTexture;
	public static Texture rateButtonTexture;
	public static Texture shareButtonTexture;
	public static Texture facebookButtonTexture;
	public static Texture twitterButtonTexture;
	public static Texture settingButtonTexture;
	public static Texture leaderboardButtonTexture;
	public static Texture jumpButtonTextures;
	public static Texture fireButtonTextures;
	public static Texture pauseButtonTexture;
	public static Texture yesButtonTexture;
	public static Texture noButtonTexture;

	public static TextureRegion[] objetcsTexture;
	public static Texture[] effetsTexture;

	public static TextureRegion[] heroBulletTexture;

	private static ActionResolver actionResolver;

	public static void setActionResolver(ActionResolver tempActionResolver) {
		actionResolver = tempActionResolver;
	}

	public static void appInit() {
		reloadAssets();
		newGame();
		stopAllSound();
		deleteTempFiles();
	}

	private static void deleteTempFiles() {
		try {
			actionResolver.decrypt("", "", "");
		} catch (Exception e) {
			showStagArch += " ex ";
			System.out.println("problem");
		}
	}

	public static void reloadAssets() {

		heroAtlasIndex = 0;
		isGameSound = false;
		isCompon = false;
		isGamePause = false;
		isAnimDark = false;
		isAnimWhite = false;
		isStageCompon = false;
		isGamePauseAndroid = false;
		isGamePause = false;
		GAME_STATE = GameCode.GAME_MENU;
		GAME_TIME = Gdx.graphics.getDeltaTime();
		motivation = "You are the best";

		backgroundMusic = initMusic("sounds/background.mp3");
		gameStageBackgroundMusic = initMusic("sounds/stage_background.mp3");
		buttonClickSound = initSound("sounds/buttonclick.mp3");
		earnPointSound = initSound("sounds/earn_point.mp3");
		heroBulletSound = initSound("sounds/hero_bullet.mp3");
		heroDiedSound = initSound("sounds/hero_died.mp3");
		enemyDiedSound = initSound("sounds/enemy_died.mp3");
		heroJumpSound = initSound("sounds/hero_jump.wav");
		levelClearMusic = initMusic("sounds/level_clear.wav");
		levelFailedMusic = initMusic("sounds/level_failed.mp3");
		levelWinMusic = initMusic("sounds/level_win.mp3");

		tempNameFont = "03";
		font = initFont("fonts/" + tempNameFont + ".fnt", "fonts/"
				+ tempNameFont + ".png");
		font.setScale((height / font.getBounds("A").height) / 20);

		tempNameFont = "01";
		font2 = initFont("fonts/" + tempNameFont + ".fnt", "fonts/"
				+ tempNameFont + ".png");
		font2.setScale((height / font2.getBounds("A").height) / 30);

		textureAtlas = new TextureAtlas(
				Gdx.files.internal("bricks/object.atlas"));
		initAtlasTextureObjects();

		allBackgroundTexture = initTexture("backgrounds/background.png");
		menuBackgroundTexture = initTexture("backgrounds/background_menu.png");

		stageBackgroundTexture = initTexture("backgrounds/stage_background.png");

		ratingStarDwonButtonTexture = initTexture("stars/ratingstardown.png");
		ratingStarUpButtonTexture = initTexture("stars/ratingstarup.png");

		stageIconTexture = initTexture("stages/stageicon.png");
		stageLockIconTexture = initTexture("stages/stagelockicon.png");
		heroBuyIconTexture = initTexture("hero_icons/lockicon.png");
		stageBoardTexture = initTexture("stages/stageboard.png");
		storeBoardTexture = initTexture("screens/storeboard.png");

		playButtonTexture = initTexture("buttons/playbutton.png");
		storeButtonTexture = initTexture("buttons/storebutton.png");
		nextButtonTexture = initTexture("buttons/nextbutton.png");
		menuButtonTexture = initTexture("buttons/menubutton.png");
		reloadButtonTexture = initTexture("buttons/reloadbutton.png");
		crossButtonTextures = initTexture("buttons/crossbutton.png");
		facebookButtonTexture = initTexture("buttons/facebookbutton.png");
		twitterButtonTexture = initTexture("buttons/twitterbutton.png");
		buyButtonTexture = initTexture("buttons/buybutton.png");
		cancelButtonTexture = initTexture("buttons/notbutton.png");
		okButtonTexture = initTexture("buttons/okbutton.png");

		pauseScreenTexture = initTexture("screens/pausescreen.png");
		gameWinScreenTexture = initTexture("screens/winscreen.png");
		gameFailedScreenTexture = initTexture("screens/failedscreen.png");
		screenOverTextures = initTexture("screens/screenover.png");
		quitScreenBackgroundTextures = initTexture("screens/background_quitscreen.png");

		achievementButtonTexture = initTexture("buttons/achivementbutton.png");
		rateButtonTexture = initTexture("buttons/ratebutton.png");
		shareButtonTexture = initTexture("buttons/sharebutton.png");
		settingButtonTexture = initTexture("buttons/settingbutton.png");
		leaderboardButtonTexture = initTexture("buttons/leaderboardbutton.png");
		handleBackgroundTexture = initTexture("buttons/handlebg.png");
		joystickTexture = initTexture("buttons/joystick.png");
		leftArrowButtonTexture = initTexture("buttons/arrow_left.png");
		rightArrowButtonTexture = initTexture("buttons/arrow_right.png");
		jumpButtonTextures = initTexture("buttons/jumpbutton.png");
		fireButtonTextures = initTexture("buttons/firebutton.png");
		pauseButtonTexture = initTexture("buttons/pausebutton.png");
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

		textureAnimAtlas = new TextureAtlas[NUMBER_OF_ANIM * 2];
		for (int i = 0; i < NUMBER_OF_ANIM - 1; i++) {
			textureAnimAtlas[i] = new TextureAtlas(
					Gdx.files.internal("animations/zombies/zombie" + (i + 1)
							+ ".atlas"));
			textureAnimAtlas[NUMBER_OF_ANIM + i] = new TextureAtlas(
					Gdx.files.internal("animations/zombies/zombie" + (i + 1)
							+ "_1.atlas"));
		}
		textureAnimAtlas[NUMBER_OF_ANIM - 1] = new TextureAtlas(
				Gdx.files.internal("animations/animfires/bomb.atlas"));
		textureAnimAtlas[NUMBER_OF_ANIM + NUMBER_OF_ANIM - 1] = new TextureAtlas(
				Gdx.files.internal("animations/animfires/bomb_1.atlas"));

		textureAnimFireAtlas = new TextureAtlas(
				Gdx.files.internal("animations/animfires/animfire.atlas"));

		texturePointAtlas = new TextureAtlas[NUMBER_OF_POINTS];
		for (int i = 0; i < NUMBER_OF_POINTS; i++) {
			texturePointAtlas[i] = new TextureAtlas(
					Gdx.files.internal("animations/points/point" + (i + 1)
							+ ".atlas"));
		}

		textureBulletAtlas = new TextureAtlas[NUMBER_OF_HERO_BULLET];
		for (int i = 0; i < NUMBER_OF_HERO_BULLET; i++) {
			textureBulletAtlas[i] = new TextureAtlas(
					Gdx.files.internal("bullets/bullet" + (i + 1) + ".atlas"));
		}

		textureGlowAtlas = new TextureAtlas(
				Gdx.files.internal("animations/heros/glow.atlas"));
		textureHeroAtlas = new TextureAtlas[NUMBER_OF_HERO_OPTION * 2];
		for (int i = 0; i < NUMBER_OF_HERO_OPTION; i++) {
			textureHeroAtlas[i] = new TextureAtlas(
					Gdx.files.internal("animations/heros/hero" + (i + 1)
							+ ".atlas"));
			textureHeroAtlas[i + NUMBER_OF_HERO_OPTION] = new TextureAtlas(
					Gdx.files.internal("animations/heros/hero"
							+ (NUMBER_OF_HERO_OPTION + i + 1) + ".atlas"));
		}

		heroBulletTexture = new TextureRegion[NUMBER_OF_HERO_BULLET];
		for (int i = 0; i < NUMBER_OF_HERO_BULLET; i++) {
			heroBulletTexture[i] = new TextureRegion(
					initTexture("bullets/bullet" + (i + 1) + ".png"));
		}

		gameWorldBackgrounds = new Texture[NUMBER_OF_WORLDS];
		for (int i = 0; i < NUMBER_OF_WORLDS; i++) {
			gameWorldBackgrounds[i] = initTexture("backgrounds/worldbg"
					+ (i + 1) + ".png");
		}

		worldIconTexture = new Texture[NUMBER_OF_WORLDS];
		for (int i = 0; i < NUMBER_OF_WORLDS; i++) {
			worldIconTexture[i] = initTexture("worlds/world" + (i + 1) + ".png");
		}
		worldCaption = new String[NUMBER_OF_WORLDS];
		worldCaption[0] = "Zombie Missor";
		worldCaption[1] = "Zombie Tree";
		worldCaption[2] = "Final Zombie";

		heroIconTexture = new Texture[NUMBER_OF_HERO_OPTION];
		for (int i = 0; i < NUMBER_OF_HERO_OPTION; i++) {
			heroIconTexture[i] = initTexture("hero_icons/hero" + (i + 1)
					+ ".png");
		}
		heroCaption = new String[NUMBER_OF_HERO_OPTION];
		heroCaption[0] = "Ninja Knife";
		heroCaption[1] = "Santa Maria";
		heroCaption[2] = "Wood Man";
		heroCaption[3] = "Iron Fighter";

		worldLockIconTexture = initTexture("worlds/lockicon.png");

		effetsTexture = new Texture[NUMBER_OF_EFFECTS];
		for (int i = 0; i < NUMBER_OF_EFFECTS; i++) {
			effetsTexture[i] = initTexture("effects/effect" + (i + 1) + ".png");
		}

		worldIconTextureIndex = 0;

		gameWorldStatus = "00000000"; // 8 zeros
		gameStagePerWorldStatus = new String[NUMBER_OF_STAGES];
		for (int i = 0; i < NUMBER_OF_WORLDS; i++) {
			gameStagePerWorldStatus[i] = "110000000" + i;
		}

		heroAtlasIndex = 0;
		noOfGamePlays = 0;
		gameHighScore = 0;
		gameTotalScore = 0;

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
		doors = new ArrayList<DoorObject>();
		doors.clear();
		hero = new Hero(0.15f, heroAtlasIndex, true);

		FileHandlers.initGameData();

	}

	private static void initAtlasTextureObjects() {

		objetcsTexture = new TextureRegion[NUMBER_OF_OBJECTS];

		objetcsTexture[0] = textureAtlas.findRegion("apple");
		objetcsTexture[1] = textureAtlas.findRegion("banana");
		objetcsTexture[2] = textureAtlas.findRegion("box");
		objetcsTexture[3] = textureAtlas.findRegion("bush 1 a");
		objetcsTexture[4] = textureAtlas.findRegion("bush 1 b");
		objetcsTexture[5] = textureAtlas.findRegion("bush 2 a");
		objetcsTexture[6] = textureAtlas.findRegion("bush 2 b");
		objetcsTexture[7] = textureAtlas.findRegion("grapes");
		objetcsTexture[8] = textureAtlas.findRegion("mushroom 1");
		objetcsTexture[9] = textureAtlas.findRegion("mushroom 2");
		objetcsTexture[10] = textureAtlas.findRegion("mushroom 3");
		objetcsTexture[11] = textureAtlas.findRegion("orange");
		objetcsTexture[12] = textureAtlas.findRegion("pike");
		objetcsTexture[13] = textureAtlas.findRegion("rock");
		objetcsTexture[14] = textureAtlas.findRegion("sign 1");
		objetcsTexture[15] = textureAtlas.findRegion("sign 2");
		objetcsTexture[16] = textureAtlas.findRegion("sign 3");
		objetcsTexture[17] = textureAtlas.findRegion("sign 4");
		objetcsTexture[18] = textureAtlas.findRegion("sign base");
		objetcsTexture[19] = textureAtlas.findRegion("tileset 1 (1)");
		objetcsTexture[20] = textureAtlas.findRegion("tileset 1 (10) - Copy");
		objetcsTexture[21] = textureAtlas.findRegion("tileset 1 (10)");
		objetcsTexture[22] = textureAtlas.findRegion("tileset 1 (11) - Copy");
		objetcsTexture[23] = textureAtlas.findRegion("tileset 1 (11)");
		objetcsTexture[24] = textureAtlas.findRegion("tileset 1 (12) - Copy");
		objetcsTexture[25] = textureAtlas.findRegion("tileset 1 (12)");
		objetcsTexture[26] = textureAtlas.findRegion("tileset 1 (13) - Copy");
		objetcsTexture[27] = textureAtlas.findRegion("tileset 1 (13)");
		objetcsTexture[28] = textureAtlas.findRegion("tileset 1 (14)");
		objetcsTexture[29] = textureAtlas.findRegion("tileset 1 (19)");
		objetcsTexture[30] = textureAtlas.findRegion("tileset 1 (2) - Copy");
		objetcsTexture[31] = textureAtlas.findRegion("tileset 1 (2)");
		objetcsTexture[32] = textureAtlas.findRegion("tileset 1 (20)");
		objetcsTexture[33] = textureAtlas.findRegion("tileset 1 (21)");
		objetcsTexture[34] = textureAtlas.findRegion("tileset 1 (22)");
		objetcsTexture[35] = textureAtlas.findRegion("tileset 1 (23)");
		objetcsTexture[36] = textureAtlas.findRegion("tileset 1 (3) - Copy");
		objetcsTexture[37] = textureAtlas.findRegion("tileset 1 (3)");
		objetcsTexture[38] = textureAtlas.findRegion("tileset 1 (4) - Copy");
		objetcsTexture[39] = textureAtlas.findRegion("tileset 1 (4)");
		objetcsTexture[40] = textureAtlas.findRegion("tileset 1 (5) - Copy");
		objetcsTexture[41] = textureAtlas.findRegion("tileset 1 (5)");
		objetcsTexture[42] = textureAtlas.findRegion("tileset 1 (6) - Copy");
		objetcsTexture[43] = textureAtlas.findRegion("tileset 1 (6)");
		objetcsTexture[44] = textureAtlas.findRegion("tileset 1 (7) - Copy");
		objetcsTexture[45] = textureAtlas.findRegion("tileset 1 (8) - Copy");
		objetcsTexture[46] = textureAtlas.findRegion("tileset 1 (8)");
		objetcsTexture[47] = textureAtlas.findRegion("tileset 1 (9) - Copy");
		objetcsTexture[48] = textureAtlas.findRegion("tileset 2 (1)");
		objetcsTexture[49] = textureAtlas.findRegion("tileset 2 (10) - Copy");
		objetcsTexture[50] = textureAtlas.findRegion("tileset 2 (10)");
		objetcsTexture[51] = textureAtlas.findRegion("tileset 2 (11) - Copy");
		objetcsTexture[52] = textureAtlas.findRegion("tileset 2 (11)");
		objetcsTexture[53] = textureAtlas.findRegion("tileset 2 (12) - Copy");
		objetcsTexture[54] = textureAtlas.findRegion("tileset 2 (12)");
		objetcsTexture[55] = textureAtlas.findRegion("tileset 2 (13) - Copy");
		objetcsTexture[56] = textureAtlas.findRegion("tileset 2 (13)");
		objetcsTexture[57] = textureAtlas.findRegion("tileset 2 (14)");
		objetcsTexture[58] = textureAtlas.findRegion("tileset 2 (19)");
		objetcsTexture[59] = textureAtlas.findRegion("tileset 2 (2) - Copy");
		objetcsTexture[60] = textureAtlas.findRegion("tileset 2 (2)");
		objetcsTexture[61] = textureAtlas.findRegion("tileset 2 (20)");
		objetcsTexture[62] = textureAtlas.findRegion("tileset 2 (21)");
		objetcsTexture[63] = textureAtlas.findRegion("tileset 2 (22)");
		objetcsTexture[64] = textureAtlas.findRegion("tileset 2 (23)");
		objetcsTexture[65] = textureAtlas.findRegion("tileset 2 (3) - Copy");
		objetcsTexture[66] = textureAtlas.findRegion("tileset 2 (3)");
		objetcsTexture[67] = textureAtlas.findRegion("tileset 2 (4) - Copy");
		objetcsTexture[68] = textureAtlas.findRegion("tileset 2 (4)");
		objetcsTexture[69] = textureAtlas.findRegion("tileset 2 (5) - Copy");
		objetcsTexture[70] = textureAtlas.findRegion("tileset 2 (5)");
		objetcsTexture[71] = textureAtlas.findRegion("tileset 2 (6) - Copy");
		objetcsTexture[72] = textureAtlas.findRegion("tileset 2 (6)");
		objetcsTexture[73] = textureAtlas.findRegion("tileset 2 (7) - Copy");
		objetcsTexture[74] = textureAtlas.findRegion("tileset 2 (8) - Copy");
		objetcsTexture[75] = textureAtlas.findRegion("tileset 2 (8)");
		objetcsTexture[76] = textureAtlas.findRegion("tileset 2 (9) - Copy");
		objetcsTexture[77] = textureAtlas.findRegion("tileset-1-(24)");
		objetcsTexture[78] = textureAtlas.findRegion("tileset-1-(25)");
		objetcsTexture[79] = textureAtlas.findRegion("tileset-1-(26)");
		objetcsTexture[80] = textureAtlas.findRegion("tileset-1-(27)");
		objetcsTexture[81] = textureAtlas.findRegion("tileset-1-(28) - Copy");
		objetcsTexture[82] = textureAtlas.findRegion("tileset-1-(28)");
		objetcsTexture[83] = textureAtlas.findRegion("tileset-1-(29) - Copy");
		objetcsTexture[84] = textureAtlas.findRegion("tileset-1-(30) - Copy");
		objetcsTexture[85] = textureAtlas.findRegion("treasure 2");
		objetcsTexture[86] = textureAtlas.findRegion("treasure");
		objetcsTexture[87] = textureAtlas.findRegion("tree 1 a");
		objetcsTexture[88] = textureAtlas.findRegion("tree 1 b");
		objetcsTexture[89] = textureAtlas.findRegion("tree 2 a");
		objetcsTexture[90] = textureAtlas.findRegion("tree 2 b");
		objetcsTexture[91] = textureAtlas.findRegion("tree 3 a");
		objetcsTexture[92] = textureAtlas.findRegion("tree 3 b");
		objetcsTexture[93] = textureAtlas.findRegion("tree trunk");
		objetcsTexture[94] = textureAtlas.findRegion("z1_coin1");
		objetcsTexture[95] = textureAtlas.findRegion("z2_coin2");
		objetcsTexture[96] = textureAtlas.findRegion("z3_zombie1");
		objetcsTexture[97] = textureAtlas.findRegion("z4_zombie2");
		objetcsTexture[98] = textureAtlas.findRegion("z5_zombie3");
		objetcsTexture[99] = textureAtlas.findRegion("z6_zombie4");
		objetcsTexture[100] = textureAtlas.findRegion("z7_zombie5");
		objetcsTexture[101] = textureAtlas.findRegion("z8_zombie6");
		objetcsTexture[102] = textureAtlas.findRegion("z9_zombie7");
		objetcsTexture[103] = textureAtlas.findRegion("z10_door_closed");
		objetcsTexture[104] = textureAtlas.findRegion("z10_door_open");

	}

	public static void newGame() {

		GAME_STATE = GAME_MENU;

		gameScore = 0;
		isCompon = false;
		gameAlpha = 1.0f;

		isGameSound = false;
		isCompon = false;
		isAnimDark = false;
		isAnimWhite = false;
		isStageCompon = false;
		isGamePauseAndroid = false;
		GAME_TIME = Gdx.graphics.getDeltaTime();
		gameWorldStatus = "00000000"; // 8 zeros
		heroStoreStatus = "1000"; // hero store status
		noOfGamePlays = 0;
		gameHighScore = 0;
		gameTotalScore = 0;
		musicVolume = 1;
		actionsVolume = 1;
		clicksVolume = 1;
		allVolume = 1;

		assets.clear();
		anims.clear();
		effects.clear();
		heroBullets.clear();
		points.clear();
		doors.clear();
		animFires.clear();

		hero = new Hero(0.15f, heroAtlasIndex, true);

		FileHandlers.initGameData();

	}

	public static void stopAllSound() {
		if (backgroundMusic != null)
			backgroundMusic.stop();
		if (gameStageBackgroundMusic != null)
			gameStageBackgroundMusic.stop();
		if (buttonClickSound != null)
			buttonClickSound.stop();
		if (earnPointSound != null)
			earnPointSound.stop();
		if (heroBulletSound != null)
			heroBulletSound.stop();
		if (heroDiedSound != null)
			heroDiedSound.stop();
		if (heroJumpSound != null)
			heroJumpSound.stop();
		if (levelClearMusic != null)
			levelClearMusic.stop();
		if (levelFailedMusic != null)
			levelFailedMusic.stop();
		if (levelWinMusic != null)
			levelWinMusic.stop();
	}

	public static void pauseAllSound() {
		if (backgroundMusic != null && backgroundMusic.isPlaying()) {
			backgroundMusic.pause();
		}
		if (gameStageBackgroundMusic != null
				&& gameStageBackgroundMusic.isPlaying()) {
			gameStageBackgroundMusic.pause();
		}
		if (levelFailedMusic != null && levelFailedMusic.isPlaying()) {
			levelFailedMusic.pause();
		}
		if (levelWinMusic != null && levelWinMusic.isPlaying()) {
			levelWinMusic.pause();
		}
		if (levelClearMusic != null && levelClearMusic.isPlaying()) {
			levelClearMusic.pause();
		}
		if (buttonClickSound != null) {
			buttonClickSound.pause();
		}
		if (earnPointSound != null) {
			earnPointSound.pause();
		}
		if (heroBulletSound != null) {
			heroBulletSound.pause();
		}
		if (heroDiedSound != null) {
			heroDiedSound.pause();
		}
		if (heroJumpSound != null) {
			heroJumpSound.pause();
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

		if (textureAnimFireAtlas != null)
			textureAnimFireAtlas.dispose();
		if (textureGlowAtlas != null)
			textureGlowAtlas.dispose();
		if (textureAtlas != null)
			textureAtlas.dispose();

		if (assets != null)
			assets.clear();
		if (anims != null)
			anims.clear();
		if (animFires != null)
			animFires.clear();
		if (effects != null)
			effects.clear();
		if (heroBullets != null)
			heroBullets.clear();
		if (points != null)
			points.clear();
		if (doors != null)
			doors.clear();

		if (font != null)
			font.dispose();
		if (font2 != null)
			font2.dispose();

		if (backgroundMusic != null)
			backgroundMusic.dispose();
		if (gameStageBackgroundMusic != null)
			gameStageBackgroundMusic.dispose();
		if (levelClearMusic != null)
			levelClearMusic.dispose();
		if (levelFailedMusic != null)
			levelFailedMusic.dispose();
		if (levelWinMusic != null)
			levelWinMusic.dispose();
		if (buttonClickSound != null)
			buttonClickSound.dispose();
		if (earnPointSound != null)
			earnPointSound.dispose();
		if (heroBulletSound != null)
			heroBulletSound.dispose();
		if (heroDiedSound != null)
			heroDiedSound.dispose();
		if (enemyDiedSound != null)
			enemyDiedSound.dispose();
		if (heroJumpSound != null)
			heroJumpSound.dispose();

		if (menuBackgroundTexture != null)
			menuBackgroundTexture.dispose();
		if (allBackgroundTexture != null)
			allBackgroundTexture.dispose();
		if (stageBackgroundTexture != null)
			stageBackgroundTexture.dispose();
		if (pauseScreenTexture != null)
			pauseScreenTexture.dispose();
		if (gameFailedScreenTexture != null)
			gameFailedScreenTexture.dispose();
		if (gameWinScreenTexture != null)
			gameWinScreenTexture.dispose();
		if (screenOverTextures != null)
			screenOverTextures.dispose();
		if (quitScreenBackgroundTextures != null)
			quitScreenBackgroundTextures.dispose();

		if (handleBackgroundTexture != null)
			handleBackgroundTexture.dispose();
		if (joystickTexture != null)
			joystickTexture.dispose();
		if (rightArrowButtonTexture != null)
			rightArrowButtonTexture.dispose();
		if (leftArrowButtonTexture != null)
			leftArrowButtonTexture.dispose();
		if (crossButtonTextures != null)
			crossButtonTextures.dispose();
		if (buyButtonTexture != null)
			buyButtonTexture.dispose();
		if (cancelButtonTexture != null)
			cancelButtonTexture.dispose();
		if (okButtonTexture != null)
			okButtonTexture.dispose();
		if (ratingStarUpButtonTexture != null)
			ratingStarUpButtonTexture.dispose();
		if (ratingStarDwonButtonTexture != null)
			ratingStarDwonButtonTexture.dispose();

		if (worldLockIconTexture != null)
			worldLockIconTexture.dispose();
		if (stageIconTexture != null)
			stageIconTexture.dispose();
		if (stageLockIconTexture != null)
			stageLockIconTexture.dispose();
		if (heroBuyIconTexture != null)
			heroBuyIconTexture.dispose();
		if (stageBoardTexture != null)
			stageBoardTexture.dispose();
		if (storeBoardTexture != null)
			storeBoardTexture.dispose();
		if (playButtonTexture != null)
			playButtonTexture.dispose();
		if (storeButtonTexture != null)
			storeButtonTexture.dispose();
		if (nextButtonTexture != null)
			nextButtonTexture.dispose();
		if (menuButtonTexture != null)
			menuButtonTexture.dispose();
		if (reloadButtonTexture != null)
			reloadButtonTexture.dispose();
		if (achievementButtonTexture != null)
			achievementButtonTexture.dispose();
		if (rateButtonTexture != null)
			rateButtonTexture.dispose();
		if (shareButtonTexture != null)
			shareButtonTexture.dispose();
		if (facebookButtonTexture != null)
			facebookButtonTexture.dispose();
		if (twitterButtonTexture != null)
			twitterButtonTexture.dispose();
		if (settingButtonTexture != null)
			settingButtonTexture.dispose();
		if (leaderboardButtonTexture != null)
			leaderboardButtonTexture.dispose();
		if (jumpButtonTextures != null)
			jumpButtonTextures.dispose();
		if (fireButtonTextures != null)
			fireButtonTextures.dispose();
		if (pauseButtonTexture != null)
			pauseButtonTexture.dispose();
		if (yesButtonTexture != null)
			yesButtonTexture.dispose();
		if (noButtonTexture != null)
			noButtonTexture.dispose();

		if (textureAnimAtlas != null) {
			for (int i = 0; i < textureAnimAtlas.length; i++)
				textureAnimAtlas[i].dispose();
		}
		if (textureHeroAtlas != null) {
			for (int i = 0; i < textureHeroAtlas.length; i++)
				textureHeroAtlas[i].dispose();
		}
		if (textureBulletAtlas != null) {
			for (int i = 0; i < textureBulletAtlas.length; i++)
				textureBulletAtlas[i].dispose();
		}
		if (texturePointAtlas != null) {
			for (int i = 0; i < texturePointAtlas.length; i++)
				texturePointAtlas[i].dispose();
		}
		if (worldIconTexture != null) {
			for (int i = 0; i < worldIconTexture.length; i++)
				worldIconTexture[i].dispose();
		}
		if (heroIconTexture != null) {
			for (int i = 0; i < heroIconTexture.length; i++)
				heroIconTexture[i].dispose();
		}
		if (gameWorldBackgrounds != null) {
			for (int i = 0; i < gameWorldBackgrounds.length; i++)
				gameWorldBackgrounds[i].dispose();
		}
		if (volumeProgressTexture != null) {
			for (int i = 0; i < volumeProgressTexture.length; i++)
				volumeProgressTexture[i].dispose();
		}
		if (volumeIconTexture != null) {
			for (int i = 0; i < volumeIconTexture.length; i++)
				volumeIconTexture[i].dispose();
		}
		if (objetcsTexture != null) {
			for (int i = 0; i < objetcsTexture.length; i++)
				objetcsTexture[i].getTexture().dispose();
		}
		if (effetsTexture != null) {
			for (int i = 0; i < effetsTexture.length; i++)
				effetsTexture[i].dispose();
		}
		if (heroBulletTexture != null) {
			for (int i = 0; i < heroBulletTexture.length; i++)
				heroBulletTexture[i].getTexture().dispose();
		}

	}
}
