package com.anisbulbul.race.danger.renderer;

import java.util.ArrayList;
import java.util.Random;

import com.anisbulbul.race.danger.assets.GameAssets;
import com.anisbulbul.race.danger.backgrounds.FarBackground;
import com.anisbulbul.race.danger.backgrounds.FireBackground;
import com.anisbulbul.race.danger.backgrounds.NearBackground;
import com.anisbulbul.race.danger.balls.Ball;
import com.anisbulbul.race.danger.boys.Boy;
import com.anisbulbul.race.danger.collision.CollisionCheck;
import com.anisbulbul.race.danger.destruction.DragonDestruction;
import com.anisbulbul.race.danger.dinosaurs.Dinosaur;
import com.anisbulbul.race.danger.earths.Earth;
import com.anisbulbul.race.danger.files.FileHandlers;
import com.anisbulbul.race.danger.fires.Fire;
import com.anisbulbul.race.danger.fires.FireExplusion;
import com.anisbulbul.race.danger.model.GameWorld;
import com.anisbulbul.race.danger.points.PointBall;
import com.anisbulbul.race.danger.points.PointEarn;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class GameWorldRenderer extends GameAssets {

	private SpriteBatch spriteBatch;

	private float width;
	private float height;

	private Random random;

	private Texture farBackgroundTexture;
	private Texture nearBackgroundTexture;
	private Texture fireBackgroundTexture;
	private Texture pauseButtonTexture;
	private Texture fireTexture;
	private Texture[] earthsTexture;
	private TextureRegion[] ballsTexture;
	private Texture[] dinosaursTexture;
	private Texture[] fireExplusionsTexture;
	private Texture[] dragonDestructionsTexture;

	private ArrayList<Earth> earths;
	private ArrayList<Ball> balls;
	public static ArrayList<Fire> fires;
	public static ArrayList<Dinosaur> dinosaurs;
	public static ArrayList<PointBall> pointBalls;
	public static ArrayList<PointEarn> pointEarns;
	public static ArrayList<FireExplusion> fireExplusions;
	public static ArrayList<DragonDestruction> dragonDestructions;

	private Texture pauseScreenTexture;

	private TextureRegion pointTextureRegion;

	private BitmapFont font;

	private Texture gameWinScreenTexture;

	private Texture gameFailedScreenTexture;

	private Texture pointEarnTextureRegion;

	private Texture[][] boysTexture;
	
	public void setSize(int w, int h) {

	}

	public GameWorldRenderer(GameWorld world, boolean debug) {

		super();
		spriteBatch = new SpriteBatch();
		random = new Random();

		width = Gdx.graphics.getWidth();
		height = Gdx.graphics.getHeight();

		new FarBackground();
		new NearBackground();
		new FireBackground();

		dinosaurs = new ArrayList<Dinosaur>();
		dinosaurs.clear();
		fires = new ArrayList<Fire>();
		fires.clear();
		earths = new ArrayList<Earth>();
		earths.clear();
		balls = new ArrayList<Ball>();
		balls.clear();
		pointBalls = new ArrayList<PointBall>();
		pointBalls.clear();
		pointEarns = new ArrayList<PointEarn>();
		pointEarns.clear();
		fireExplusions = new ArrayList<FireExplusion>();
		fireExplusions.clear();
		dragonDestructions = new ArrayList<DragonDestruction>();
		dragonDestructions.clear();

		dragonDestructionsTexture = new Texture[NUMBER_OF_DRAGON_EXPLUSION];
		boysTexture = new Texture[NUMBER_OF_BOYS][NUMBER_OF_BOYS_SPRITE];
		earthsTexture = new Texture[NUMBER_OF_EARTHS];
		ballsTexture = new TextureRegion[NUMBER_OF_BALLS];
		dinosaursTexture = new Texture[NUMBER_OF_DINOSAURS];
		fireExplusionsTexture = new Texture[NUMBER_OF_FIRE_EXPLUSION];

		loadTextures();
		loadFonts();
		loadSounds();
		loadEarths();
		loadBalls();
		loadFires();
		loadPointBalls();
		loadDinosaurs();
		initializeOthers();

	}

	private void loadPointBalls() {

		for (int i = 0; i < 3; i++) {
			pointBalls.add(new PointBall(i * width / 5 + width / 2, height / 2,
					height / 10, height / 10, 2, 0, 0));
		}

	}

	private void loadDinosaurs() {

		dinosaurs.add(new Dinosaur(3 * (0 + 1) * width / 4, height - height / 8
				- height / 20, height / 5, height / 10, DINOSAUR_SPEED_X,
				DINOSAUR_SPEED_Y, Math.abs(random.nextInt(NUMBER_OF_DINOSAURS)) % NUMBER_OF_DINOSAURS));
		dinosaurs.add(new Dinosaur(3 * (1 + 1) * width / 4, height / 2,
				height / 5, height / 10, DINOSAUR_SPEED_X, DINOSAUR_SPEED_Y,
				Math.abs(random.nextInt(NUMBER_OF_DINOSAURS)) % NUMBER_OF_DINOSAURS));

	}

	private void loadFires() {

		for (int i = 0; i < 5; i++) {
			fires.add(new Fire(i * width / 5 + width / 2, Boy.boyY + Boy.boyH
					/ 2, height / 10, height / 20, FIRE_SPEED_X, FIRE_SPEED_Y));
		}

	}

	private void loadBalls() {

		float y, wh, xSpeed;
		int index;

		for (int i = 0; i < 4; i++) {
			y = height / 4;
			wh = Math.abs(random.nextInt((int) (height / 20))) + height / 20;
			xSpeed = BALL_BACKGROUNDSPEED_SPEED_X;
			index = Math.abs(random.nextInt(5)) % NUMBER_OF_BALLS;
			balls.add(new Ball(width
					+ Math.abs(random.nextInt((int) ((i + 1) * width / 4))), y,
					wh, wh, xSpeed, (float) Math.abs(random.nextInt(100)) / 50,
					index, 0, 0));
		}

	}

	private void initializeOthers() {

	}

	private void loadSounds() {

		deathSound = initSound("sounds/death_music.mp3");
		pointSound = initSound("sounds/point.mp3");
		fireSound = initSound("sounds/fire.mp3");
		
		winMusic = initMusic("sounds/win_music.mp3");

	}

	private void loadFonts() {
		font = initFont("fonts/abc.fnt", "fonts/abc.png");
		font.setScale((height / font.getBounds("A").height) / 20);
	}

	private void loadEarths() {
		float y, wh, xSpeed;

		for (int i = 0; i < NUMBER_OF_EARTHS ; i++) {
			y = Math.abs(random.nextInt((int) (height / 2))) + height / 4;
			wh = Math.abs(random.nextInt((int) ((i + 1) * width / 64)))
					+ (i + 1) * width / 64;
			xSpeed = (((float) Math.abs(random.nextInt(5))) / 5 + 1)
					* EARTH_BACKGROUNDSPEED_SPEED_X;
			earths.add(new Earth(width
					+ Math.abs(random.nextInt((int) ((i + 1) * width ))), y,
					wh, wh, xSpeed, 0, i));
		}

	}

	private void loadTextures() {

		farBackgroundTexture = initTexture("backgrounds/set"
				+ GAME_STAGE_NUMBER + "/farbackground.png");
		nearBackgroundTexture = initTexture("backgrounds/set"
				+ GAME_STAGE_NUMBER + "/nearbackground.png");
		fireBackgroundTexture = initTexture("backgrounds/set"
				+ GAME_STAGE_NUMBER + "/upbackground.png");

		pauseScreenTexture = initTexture("backgrounds/pausescreen.png");
		gameWinScreenTexture = initTexture("backgrounds/winscreen.png");
		gameFailedScreenTexture = initTexture("backgrounds/losescreen.png");

		pointTextureRegion = new TextureRegion(initTexture("points/point"
				+ GAME_STAGE_NUMBER + ".png"));
		fireTexture = initTexture("fires/fire.png");

		pointEarnTextureRegion = initTexture("earns/earn.png");
		pauseButtonTexture = initTexture("buttons/pausebutton.png");

		for (int i = 0; i < NUMBER_OF_BOYS; i++) {
			for (int j = 0; j < NUMBER_OF_BOYS_SPRITE; j++) {
				boysTexture[i][j] = initTexture("boys/boy" + (i + 1) + "/boy"
						+ (j + 1) + ".png");
			}
		}
		for (int i = 0; i < NUMBER_OF_FIRE_EXPLUSION; i++) {
			fireExplusionsTexture[i] = initTexture("fires/fire" + (i + 1)
					+ ".png");
		}
		for (int i = 0; i < NUMBER_OF_EARTHS; i++) {
			earthsTexture[i] = initTexture("earths/earth" + (i + 1) + ".png");
		}
		for (int i = 0; i < NUMBER_OF_BALLS; i++) {
			ballsTexture[i] = new TextureRegion(initTexture("balls/ball"
					+ (i + 1) + ".png"));
		}
		for (int i = 0; i < NUMBER_OF_DRAGON_EXPLUSION; i++) {
			dragonDestructionsTexture[i] = initTexture("destruction/destruction"
					+ (i + 1) + ".png");
		}
		for (int i = 0; i < NUMBER_OF_DINOSAURS; i++) {
			dinosaursTexture[i] = initTexture("dinosaurs/dinosaur" + (i + 1)
					+ ".png");
		}

	}

	public void render() {

		spriteBatch.begin();
		spriteBatch.enableBlending();

		drawBackgrounds();
		drawBoy();
		drawBalls();
		drawFires();
		drawFireExplusions();
		drawPointBalls();
		drawPointEarns();
		drawDinosaurs();
		drawDinosaurDestructions();
		drawScoreBoard();
//		drawInside();
		drawPauseScreen();
		drawGameOverScreen();
		drawGameButtons();

		spriteBatch.end();
		spriteBatch.disableBlending();
	}

	private void drawDinosaurDestructions() {

		for (int i = 0; i < dragonDestructions.size(); i++) {
			spriteBatch
					.draw(dragonDestructionsTexture[(int) dragonDestructions
							.get(i).dragonDestructionIndex], dragonDestructions
							.get(i).dragonDestructionX, dragonDestructions
							.get(i).dragonDestructionY, dragonDestructions
							.get(i).dragonDestructionWidth, dragonDestructions
							.get(i).dragonDestructionHeight);

			if (!isGamePause) {
				dragonDestructions.get(i).dragonDestructionIndex += 0.5f;
				dragonDestructions.get(i).dragonDestructionX += dragonDestructions
						.get(i).dragonDestructionSpeedX;
				dragonDestructions.get(i).dragonDestructionY += dragonDestructions
						.get(i).dragonDestructionSpeedX;
				if (dragonDestructions.get(i).dragonDestructionIndex >= NUMBER_OF_DRAGON_EXPLUSION) {
					dragonDestructions.remove(i);
				}
			}
		}

	}

	private void drawFireExplusions() {

		for (int i = 0; i < fireExplusions.size(); i++) {
			spriteBatch
					.draw(fireExplusionsTexture[(int) fireExplusions.get(i).fireExplusionIndex],
							fireExplusions.get(i).fireExplusionX,
							fireExplusions.get(i).fireExplusionY,
							fireExplusions.get(i).fireExplusionWidth,
							fireExplusions.get(i).fireExplusionHeight);
			if (!isGamePause) {
				fireExplusions.get(i).fireExplusionIndex += 0.2f;
				fireExplusions.get(i).fireExplusionX += fireExplusions.get(i).fireExplusionSpeedX;
				fireExplusions.get(i).fireExplusionY += fireExplusions.get(i).fireExplusionSpeedY;
				if (fireExplusions.get(i).fireExplusionIndex >= NUMBER_OF_FIRE_EXPLUSION) {
					fireExplusions.remove(i);
				}
			}
		}
	}

	private void drawGameButtons() {

		if (!isGamePause) {
			spriteBatch.draw(pauseButtonTexture, 0, 0, height / 8, height / 8);
		}
	}

	private void drawPointEarns() {
		for (int i = 0; i < pointEarns.size(); i++) {
			spriteBatch.draw(pointEarnTextureRegion,
					pointEarns.get(i).pointEarnX, pointEarns.get(i).pointEarnY,
					pointEarns.get(i).pointEarnWidth,
					pointEarns.get(i).pointEarnHeight);
			if (!isGamePause) {

				pointEarns.get(i).pointEarnY += pointEarns.get(i).pointEarnSpeddY;

				if (pointEarns.get(i).pointEarnX > height) {
					pointEarns.remove(i);
				}

			}

		}

	}

	private void drawGameOverScreen() {

		if (isGameRaceWin) {
			spriteBatch.draw(gameWinScreenTexture, 0, 0, width, height);
		} else if (isGameRaceFailed) {
			spriteBatch.draw(gameFailedScreenTexture, 0, 0, width, height);
		}

	}
//
//	private void drawInside() {
//
//		spriteBatch.draw(insideTexture, width / 64 + 2 * width / 3,
//				height / 64, width / 3 - 2 * width / 64, height / 4 - 2
//						* height / 64);
//
//	}

	private void drawScoreBoard() {

		String temp = "EARNS";
		font.draw(spriteBatch, temp,
				width / 6 - font.getBounds(temp).width / 2, 3 * height / 16
						+ font.getBounds(temp).height / 2);

		temp = "" + earningPoints + "/" + EARN_POINTS;
		font.draw(spriteBatch, temp,
				width / 6 - font.getBounds(temp).width / 2, 3 * height / 32
						+ font.getBounds(temp).height / 2);

		temp = "KILLS";
		font.draw(spriteBatch, temp,
				width / 2 - font.getBounds(temp).width / 2, 3 * height / 16
						+ font.getBounds(temp).height / 2);

		temp = "" + dragonKiils;
		font.draw(spriteBatch, temp,
				width / 2 - font.getBounds(temp).width / 2, 3 * height / 32
						+ font.getBounds(temp).height / 2);
		
		temp = "TAB TO";
		font.draw(spriteBatch, temp,
				5*width / 6 - font.getBounds(temp).width / 2, 3 * height / 16
						+ font.getBounds(temp).height / 2);

		temp = "JUMP";
		font.draw(spriteBatch, temp,
				5*width / 6 - font.getBounds(temp).width / 2, 3 * height / 32
						+ font.getBounds(temp).height / 2);

	}

	private void drawPointBalls() {

		for (int i = 0; i < pointBalls.size(); i++) {
			// spriteBatch.draw(region, x, y, originX, originY, width, height,
			// scaleX, scaleY, rotation);

			spriteBatch.draw(pointTextureRegion, pointBalls.get(i).pointX,
					pointBalls.get(i).pointY, pointBalls.get(i).pointWidth / 2,
					pointBalls.get(i).pointHeight / 2,
					pointBalls.get(i).pointWidth,
					pointBalls.get(i).pointHeight, 1.0f, 1.0f,
					pointBalls.get(i).pointAngle);
			if (!isGamePause) {
				pointBalls.get(i).pointAngle += 1;
				pointBalls.get(i).pointX -= pointBalls.get(i).pointSpeedX;

				if (pointBalls.get(i).pointX < 0) {
					pointBalls.get(i).pointX = 2 * width;
				}

				if (!isGameRaceWin
						&& !isGameRaceFailed
						&& CollisionCheck.isCollisionOccuredRectaPoint2x2(
								Boy.boyX, Boy.boyY, Boy.boyX + Boy.boyW,
								Boy.boyY + Boy.boyH, pointBalls.get(i).pointX,
								pointBalls.get(i).pointY,
								pointBalls.get(i).pointX
										+ pointBalls.get(i).pointWidth,
								pointBalls.get(i).pointY
										+ pointBalls.get(i).pointHeight)) {

					pointEarns.add(new PointEarn(pointBalls.get(i).pointX,
							pointBalls.get(i).pointY, height / 5, height / 10,
							0, 2));

					earningPoints++;
					if(isGameSound){
						pointSound.play();
					}
					pointBalls.get(i).pointX = 2 * width;
					if (!isGameRaceWin && earningPoints >= EARN_POINTS) {
						isGameRaceWin = true;
						if(isGameSound){
							winMusic.play();
						}
						FileHandlers.setScore(dragonKiils);
					}
				}

			}

		}

	}

	private void drawDinosaurs() {

		for (int i = 0; i < dinosaurs.size(); i++) {
			spriteBatch.draw(dinosaursTexture[dinosaurs.get(i).dinosaurIndex], dinosaurs.get(i).dinosaurX,
					dinosaurs.get(i).dinosaurY, dinosaurs.get(i).dinosaurWidth,
					dinosaurs.get(i).dinosaurHeight);

			// drawCustomRect(dinosaurs.get(i).dinosaurX,
			// dinosaurs.get(i).dinosaurY, dinosaurs.get(i).dinosaurWidth,
			// dinosaurs.get(i).dinosaurHeight, 5);

			if (!isGamePause) {
				dinosaurs.get(i).dinosaurX -= dinosaurs.get(i).dinosaurSpeedX;

				if (dinosaurs.get(i).dinosaurX < 0) {
					dinosaurs.get(i).dinosaurX = width;
					dinosaurs.get(i).dinosaurIndex = Math.abs(random.nextInt(NUMBER_OF_DINOSAURS))%NUMBER_OF_DINOSAURS;
				}
				if (!isGameRaceWin && !isGameRaceFailed
						&& dinosaurs.get(i).dinosaurX < width) {
					for (int k = 0; k < fires.size(); k++) {
						if (CollisionCheck.isCollisionOccuredRectaPoint2x2(
								fires.get(k).fireX, fires.get(k).fireY,
								fires.get(k).fireX + fires.get(k).fireWidth,
								fires.get(k).fireY + fires.get(k).fireHeight,
								dinosaurs.get(i).dinosaurX,
								dinosaurs.get(i).dinosaurY,
								dinosaurs.get(i).dinosaurX
										+ dinosaurs.get(i).dinosaurWidth,
								dinosaurs.get(i).dinosaurY
										+ dinosaurs.get(i).dinosaurHeight)) {
							dragonDestructions.add(new DragonDestruction(
									dinosaurs.get(i).dinosaurX
											- dinosaurs.get(i).dinosaurWidth
											/ 2, dinosaurs.get(i).dinosaurY,
									dinosaurs.get(i).dinosaurWidth, dinosaurs
											.get(i).dinosaurWidth, 0, -3, 0));
							dragonKiils++;
							dinosaurs.get(i).dinosaurX = 2 * width;
							if(isGameSound){
								deathSound.play();
							}
						}
					}
				}
				if (!isGameRaceWin
						&& !isGameRaceFailed
						&& CollisionCheck.isCollisionOccuredRectaPoint2x2(
								dinosaurs.get(i).dinosaurX,
								dinosaurs.get(i).dinosaurY,
								dinosaurs.get(i).dinosaurX
										+ dinosaurs.get(i).dinosaurWidth,
								dinosaurs.get(i).dinosaurY
										+ dinosaurs.get(i).dinosaurHeight,
								Boy.boyX + Boy.boyW / 4, Boy.boyY + Boy.boyH
										/ 4, Boy.boyX + Boy.boyW / 2, Boy.boyY
										+ Boy.boyH / 2)) {
					dragonDestructions.add(new DragonDestruction(Boy.boyX
							- Boy.boyW / 2, Boy.boyY - Boy.boyH / 2,
							2 * Boy.boyW, 2 * Boy.boyW, 0, 0, 0));
					if(isGameSound){
						deathSound.play();
					}
					isGameRaceFailed = true;
					FileHandlers.setScore(dragonKiils);
				}
			}

		}

	}

	private void drawFires() {
		if (!isGameRaceFailed) {
			for (int i = 0; i < fires.size(); i++) {
				spriteBatch.draw(fireTexture, fires.get(i).fireX,
						fires.get(i).fireY, fires.get(i).fireWidth,
						fires.get(i).fireHeight);

				// drawCustomRect(fires.get(i).fireX,
				// fires.get(i).fireY, fires.get(i).fireWidth,
				// fires.get(i).fireHeight, 5);

				if (!isGamePause) {
					fires.get(i).fireX += fires.get(i).fireSpeedX;

					if (fires.get(i).fireX > 2 * width) {
						fires.get(i).fireX = Boy.boyX + Boy.boyW;
						fires.get(i).fireY = Boy.boyY + Boy.boyH / 2;
						if(isGameSound){
							fireSound.play();
						}
						fireExplusions.add(new FireExplusion(
								fires.get(i).fireX, fires.get(i).fireY, fires
										.get(i).fireWidth,
								fires.get(i).fireWidth, 1, 1, 0));

					}
				}

			}
		}
	}

	private void drawBalls() {

		for (int i = 0; i < balls.size(); i++) {
			// spriteBatch.draw(region, x, y, originX, originY, width, height,
			// scaleX, scaleY, rotation);

			spriteBatch.draw(ballsTexture[balls.get(i).ballIndex],
					balls.get(i).ballX, balls.get(i).ballY,
					balls.get(i).ballWidth / 2, balls.get(i).ballHeight / 2,
					balls.get(i).ballWidth, balls.get(i).ballHeight, 1.0f,
					1.0f, balls.get(i).ballAngle);

			// drawCustomRect(balls.get(i).ballX, balls.get(i).ballY,
			// balls.get(i).ballWidth, balls.get(i).ballHeight, 5);

			if (!isGamePause) {
				balls.get(i).ballAngle += 1;
				balls.get(i).ballX -= balls.get(i).ballSpeedX;

				balls.get(i).ballY += (balls.get(i).ballSpeddY - 9.8f * balls
						.get(i).ballJumpCounter);
				balls.get(i).ballJumpCounter += 0.005f;

				if (balls.get(i).ballX + balls.get(i).ballWidth < 0) {
					balls.get(i).ballIndex = Math.abs(random.nextInt(5))
							% NUMBER_OF_BALLS;
					balls.get(i).ballX = width
							+ Math.abs(random
									.nextInt((int) ((i + 1) * width / 4)));
					balls.get(i).ballY = height / 4;
					balls.get(i).ballSpeedX = BALL_BACKGROUNDSPEED_SPEED_X;
					balls.get(i).ballSpeddY = (float) Math.abs(random
							.nextInt(100)) / 50;
				}
				if (balls.get(i).ballY < height / 4) {
					balls.get(i).ballY = height / 4;
					balls.get(i).ballJumpCounter = 0;
				}

				if (!isGameRaceWin
						&& !isGameRaceFailed
						&& CollisionCheck.isCollisionOccuredRectaPoint2x2(
								balls.get(i).ballX, balls.get(i).ballY,
								balls.get(i).ballX + balls.get(i).ballWidth,
								balls.get(i).ballY + balls.get(i).ballHeight,
								Boy.boyX + Boy.boyW / 4, Boy.boyY + Boy.boyH
										/ 4, Boy.boyX + Boy.boyW / 2, Boy.boyY
										+ Boy.boyH / 2)) {
					dragonDestructions.add(new DragonDestruction(Boy.boyX
							- Boy.boyW / 2, Boy.boyY - Boy.boyH / 2,
							2 * Boy.boyW, 2 * Boy.boyW, 0, 0, 0));
					if(isGameSound){
						deathSound.play();
					}
					isGameRaceFailed = true;
					FileHandlers.setScore(dragonKiils);
				}

			}

		}

	}

	private void drawPauseScreen() {

		if (isGamePause) {
			spriteBatch.draw(pauseScreenTexture, 0, 0, width, height);
		}
	}

	private void drawEarths() {

		for (int i = 0; i < earths.size(); i++) {
			spriteBatch.draw(earthsTexture[earths.get(i).earthIndex],
					earths.get(i).earthX - earths.get(i).earthWidth / 2,
					earths.get(i).earthY - earths.get(i).earthHeight / 2,
					earths.get(i).earthWidth, earths.get(i).earthHeight);
			if (!isGamePause) {
				earths.get(i).earthX -= earths.get(i).earthSpeedX;

				if (earths.get(i).earthX + earths.get(i).earthWidth < 0) {
					earths.get(i).earthIndex = Math.abs(random.nextInt(7))
							% NUMBER_OF_EARTHS;
					earths.get(i).earthX = width
							+ Math.abs(random
									.nextInt((int) ((i + 1) * width / 4)));
					earths.get(i).earthY = Math.abs(random
							.nextInt((int) (height / 2))) + height / 4;
					earths.get(i).earthSpeedX = (((float) Math.abs(random
							.nextInt(5))) / 5 + 1)
							* FAR_BACKGROUNDSPEED_SPEED_X;
				}
			}

		}
	}

	private void drawBackgrounds() {
		drawFarBackground();
		drawEarths();
		drawNearBackground();
		drawFireBackground();
	}

	public void drawFarBackground() {
		for (int i = 0; i < 2; i++) {
			spriteBatch.draw(farBackgroundTexture, FarBackground.farBackgroundX
					+ i * width, FarBackground.farBackgroundY, width, height);
		}
		if (!isGamePause) {
			FarBackground.farBackgroundX -= FarBackground.farBackgroundSpeedX;
			if (FarBackground.farBackgroundX <= -width) {
				FarBackground.farBackgroundX += width;
			}
		}

	}

	public void drawNearBackground() {
		for (int i = 0; i < 2; i++) {
			spriteBatch.draw(nearBackgroundTexture,
					NearBackground.nearBackgroundX + i * width,
					NearBackground.nearBackgroundY, width, height / 4);
		}
		if (!isGamePause) {
			NearBackground.nearBackgroundX -= NearBackground.nearBackgroundSpeedX;
			if (NearBackground.nearBackgroundX <= -width) {
				NearBackground.nearBackgroundX += width;
			}
		}

	}

	public void drawFireBackground() {
		for (int i = 0; i < 2; i++) {
			spriteBatch.draw(fireBackgroundTexture,
					FireBackground.fireBackgroundX + i * width,
					FireBackground.fireBackgroundY, width, height / 8);
		}
		if (!isGamePause) {
			FireBackground.fireBackgroundX -= FireBackground.fireBackgroundSpeedX;
			if (FireBackground.fireBackgroundX <= -width) {
				FireBackground.fireBackgroundX += width;
			}
		}

	}

	private void drawBoy() {

		if (!isGameRaceFailed) {
			spriteBatch.draw(boysTexture[boyIndex][(int) Boy.boyIndex],
					Boy.boyX, Boy.boyY, Boy.boyW, Boy.boyH);
		}
		//
		// drawCustomRect(Boy.boyX, Boy.boyY,
		// Boy.boyW, Boy.boyH, 5);
		// drawCustomRect(Boy.boyX+Boy.boyW/4, Boy.boyY+Boy.boyH/4,
		// Boy.boyW/2, Boy.boyH/2, 5);

		if (!isGamePause && Boy.boyY < height / 4) {
			Boy.boyY = height / 4;
			Boy.boyJumpCounter = 0;
			Boy.boyJumpState = false;
			Boy.isBoyJumpPossible = true;
			Boy.boyIndex = (Boy.boyIndex + 0.1f) % NUMBER_OF_BOYS_SPRITE;

		}
	}

}
