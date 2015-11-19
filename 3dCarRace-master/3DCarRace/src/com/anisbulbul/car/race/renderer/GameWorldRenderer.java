package com.anisbulbul.car.race.renderer;

import java.util.Random;

import com.anisbulbul.car.race.assets.GameAssets;
import com.anisbulbul.car.race.collision.CollisionCheck;
import com.anisbulbul.car.race.model.GameBackGround;
import com.anisbulbul.car.race.model.GameWater;
import com.anisbulbul.car.race.model.GameWorld;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class GameWorldRenderer extends GameAssets {

	private PerspectiveCamera cam;

	private GameBackGround backGround;
	private GameWater gameWater;

	private SpriteBatch spriteBatch;

	private float width;
	private float height;

	private Texture roadTexture;

	private EnemyCar[] enemyCars;

	private Texture[] fires;
	private float firesIndex;

	private float camx;
	private float camy;
	private float camz;

	public static float myCarX;
	public static float myCarY;
	public static float myCarH;
	public static float myCarW;

	public static float fireX;
	public static float fireY;
	public static float fireH;
	public static float fireW;

	private Random random;

	private float dimentionRatio;

	private Texture waterAnimationTexture;

	private TextureRegion myCarTexture;

	public void setSize(int w, int h) {

	}

	public GameWorldRenderer(GameWorld world, boolean debug) {

		super();
		backGround = world.getBackGround();
		gameWater = world.getWater();
		spriteBatch = new SpriteBatch();
		random = new Random();

		enemyCars = new EnemyCar[NUMBER_OF_CARS + 2 * NUMBER_OF_LEVELS];
		fires = new Texture[NUMBER_OF_FIRES];
		width = Gdx.graphics.getWidth();
		height = Gdx.graphics.getHeight();

		dimentionRatio = 320 / 4.2f;
		firesIndex = 0;

		camx = (width / 2);
		camy = (height / 2);
		camz = width / dimentionRatio;

		cam = new PerspectiveCamera(177.5f, width / 2, height / 2);
		cam.position.set(camx, camy, camz);
		cam.rotate(0.7f, 0.6f, 0, 0);
		cam.update();

		enemyCarsInit();
		loadTextures();
		loadFonts();
		loadSounds();
		initializeOthers();

	}

	private void enemyCarsInit() {

		for (int i = 0; i < NUMBER_OF_CARS + 2 * NUMBER_OF_LEVELS; i++) {

			enemyCars[i] = new EnemyCar(initTexture("cars/car" + (i + 1)
					+ ".png"), width / 16, 1.5f * width / 16, 
					Math.abs(random.nextInt((int) (width - 2 * width / 3.7)))
					+ (int) (width / 3.7), 
					Math.abs(random.nextInt((int) (height / 4)))
					+ (int) ((i + 1) * height / 4));

		}
	}

	private void loadSounds() {
		// click = initSound("sounds/click.mp3");
	}

	private void loadFonts() {
		// TODO Auto-generated method stub
	}

	private void initializeOthers() {
		myCarW = width / 16;
		myCarH = myCarW * 1.5f;
		myCarX = width / 2;
		myCarY = height / 2.5f;

		fireX = -2 * width;
		fireY = -2 * height;
		fireH = myCarH;
		fireW = myCarH;

	}

	private void loadTextures() {
		roadTexture = initTexture("roads/road" + (roadNumber + 1) + ".png");
		waterAnimationTexture = initTexture("data/wateranimation.png");
		myCarTexture = new TextureRegion(initTexture("mycars/car"
				+ (myCarNumber + 1) + ".png"));
		for (int i = 0; i < NUMBER_OF_FIRES; i++) {
			fires[i] = initTexture("fires/fire" + (i + 1) + ".png");
		}
	}

	public void render() {

		spriteBatch.begin();
		spriteBatch.enableBlending();

		spriteBatch.setProjectionMatrix(cam.combined);

		drawBackGround();
		drawCars();
		drawMyCars();
		drawFires();

		spriteBatch.end();
		spriteBatch.disableBlending();
	}

	private void drawFires() {
		if (GAME_STATE == GAME_ACCIDENT) {
			spriteBatch.draw(fires[(int) firesIndex], fireX - fireW / 2, fireY
					- fireH / 2, fireW, fireH);
			firesIndex += 0.5f;
			if (NUMBER_OF_FIRES <= (int) firesIndex) {
				firesIndex = 0;
				fireX = myCarX + Math.abs(random.nextInt((int) myCarW / 2));
				fireY = myCarY + Math.abs(random.nextInt((int) myCarH / 2));
				fireH = myCarH;
				fireW = myCarH;
			}
		}

	}

	private void drawMyCars() {

		// spriteBatch.draw(myCarTexture, myCarX - myCarW / 2, myCarY - myCarH
		// / 2, myCarW, myCarH);
		spriteBatch.draw(myCarTexture, myCarX - myCarW / 2,
				myCarY - myCarH / 2, myCarW / 2, myCarH / 2, myCarW, myCarH,
				1.0f, 1.0f, myCarRotation);

	}

	private void drawBackGround() {

		for (int i = 5; i >= 0; i--) {
			spriteBatch.draw(waterAnimationTexture,
					(gameWater.getPosition().x - 2 * width),
					(gameWater.getPosition().y + i * height - i * 5),
					5 * width, height);
		}

		for (int i = 5; i >= 0; i--) {
			spriteBatch.draw(roadTexture,
					(backGround.getPosition().x - width / 8),
					(backGround.getPosition().y + i * height - i * 5), width
							/ 3 + width / 4, height);
		}
		int tempScore = ((int) (gameScore / 10));
		if (tempScore != 0 && tempScore % 1000 == 0) {
			roadNumber = (roadNumber + 1) % NUMBER_OF_ROADS;
			roadTexture = initTexture("roads/road" + (roadNumber + 1) + ".png");
			gameScore += 10;
		}
	}

	private void drawCars() {

		for (int i = 0; i < NUMBER_OF_CARS + 2 * gameLevel; i++) {
			spriteBatch.draw(enemyCars[i].carTexture, enemyCars[i].carX
					- enemyCars[i].carW / 2, enemyCars[i].carY
					- enemyCars[i].carH / 2, enemyCars[i].carW,
					enemyCars[i].carH);

			if (!isGamePause) {
				boolean isCollision = CollisionCheck
						.isCollisionOccuredRectaPoint2x2(enemyCars[i].carX
								- enemyCars[i].carW / 2, enemyCars[i].carY
								- enemyCars[i].carH / 2, enemyCars[i].carX
								+ enemyCars[i].carW / 2, enemyCars[i].carY
								+ enemyCars[i].carH / 2, myCarX - myCarW / 2,
								myCarY - myCarH / 2, myCarX + myCarW / 2,
								myCarY + myCarH / 2);
//				isCollision = false;
				if (isCollision) {
					GAME_STATE = GAME_ACCIDENT;
					fireX = myCarX + Math.abs(random.nextInt((int) myCarW / 2));
					fireY = myCarY + Math.abs(random.nextInt((int) myCarH / 2));
					fireH = myCarH;
					fireW = myCarH;
				}

				if (GAME_STATE == GAME_START) {
					enemyCars[i].carY -= carSpeed;
				} else if (GAME_STATE == GAME_ACCIDENT) {
					enemyCars[i].carY += 2 * carSpeed;
				}

				if (enemyCars[i].carY <= 0) {
					enemyCars[i].carX = Math.abs(random.nextInt((int) (width - 2 * width / 3.7)))
							+ (int) (width / 3.7);
					enemyCars[i].carY = 4 * height;
//					for (int j = 0; j < NUMBER_OF_CARS + 2 * gameLevel; j++) {
//						if (i != j
//								&& (enemyCars[j].carY + enemyCars[j].carH) >= enemyCars[i].carY) {
//							enemyCars[i].carY += enemyCars[j].carH;
//						}
//					}
				} else if (enemyCars[i].carY >= 4 * height) {
					enemyCars[i].carX = Math.abs(random
							.nextInt((int) (width - 2 * width / 3.7)))
							+ (int) (width / 3.7);
					enemyCars[i].carY = 0;
//					for (int j = 0; j < NUMBER_OF_CARS + 2 * gameLevel; j++) {
//						if (i != j
//								&& (enemyCars[j].carY - enemyCars[j].carH) <= enemyCars[i].carY) {
//							enemyCars[i].carY -= enemyCars[j].carH;
//						}
//					}
				}
			}
		}
	}

}
