package com.anrosoft.jetadventure.screen;

import java.util.Random;

import com.anrosoft.jetadventure.ActionResolver;
import com.anrosoft.jetadventure.JetAdventure;
import com.anrosoft.jetadventure.assets.AnimationFireObject;
import com.anrosoft.jetadventure.assets.AnimationObject;
import com.anrosoft.jetadventure.assets.Category;
import com.anrosoft.jetadventure.assets.Effect;
import com.anrosoft.jetadventure.assets.GameAssets;
import com.anrosoft.jetadventure.assets.Hero;
import com.anrosoft.jetadventure.assets.HeroBullet;
import com.anrosoft.jetadventure.assets.PointObject;
import com.anrosoft.jetadventure.code.GameCode;
import com.anrosoft.jetadventure.collision.CollisionCheck;
import com.anrosoft.jetadventure.files.FileHandlers;
import com.anrosoft.jetadventure.model.GameWorld;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;

public class GameScreen extends GameAssets implements Screen, InputProcessor {

	private GameWorld world;

	private JetAdventure game;

	private SpriteBatch spriteBatch;
	public static Stage stage;
	public static Skin skin;

	private ActionResolver actionResolver;

	private int tempScoreIncrement;
	private float x, y, w, h;
	private int starTemp;
	private float fiveStarScore;

	public GameScreen(JetAdventure game, ActionResolver actionResolver) {

		this.game = game;
		spriteBatch = new SpriteBatch();
		this.actionResolver = actionResolver;
		isRightArrow = isRightArrow = false;
		isHeroRight = true;
		isGameOver = false;
		isGamePause = false;
		tempScoreIncrement = 0;
		starTemp = 0;
		fiveStarScore = 0;
	}

	public void addButton(Texture buttonTextures, float posX, float posY,
			float bWidth, float bHeight, final String action) {

		TextButton button;
		TextButtonStyle textButtonStyle;
		skin = new Skin();

		skin.add("white", buttonTextures);
		textButtonStyle = new TextButtonStyle();
		textButtonStyle.up = skin.newDrawable("white", Color.WHITE);
		textButtonStyle.down = skin.newDrawable("white", Color.BLUE);
		textButtonStyle.checked = skin.newDrawable("white", Color.PINK);
		textButtonStyle.over = skin.newDrawable("white", Color.GREEN);
		textButtonStyle.font = font;
		skin.add("default", textButtonStyle);

		button = new TextButton("", textButtonStyle);

		button.setPosition(posX, posY);
		button.setSize(bWidth, bHeight);

		stage.addActor(button);

		button.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {

				if (!isGameOver && !isGamePause
						&& action.equals(GameCode.JUMP_UP)) {
					if (!hero.isHeroDied) {
						hero.isJumpState = true;
						if (heroAtlasIndex != 0) {
							heroAtlasIndex = 0;
							hero.animation = new Animation(hero.keyFrameDelay,
									textureHeroAtlas[heroAtlasIndex]
											.getRegions());
						}
						hero.heroU = hero.heroUInit;
						hero.heroT = 0;
						hero.heroV = hero.heroU + 9.8f * hero.heroT;
						// if(!heroFlySound.isPlaying()){
						// heroFlySound.play();
						// heroFlySound.setLooping(true);
						// }
						// else{
						// if(heroFlySound.getVolume()+0.01f < actionsVolume)
						// heroFlySound.setVolume(heroFlySound.getVolume()+0.01f);
						// }
					}
				} else if (!isGameOver && !isGamePause && !hero.isHeroDied
						&& action.equals(GameCode.HERO_BULLET)) {

					if (heroBullets.size() < NUMBER_OF_REVOLD_BULLET
							&& isBulletReady()) {
						addHeroBullet();
					}
				} else if (action.equals(GameCode.GAME_RELOAD)) {
					boolean isHigh = FileHandlers.setGameData();
					if (isHigh) {
						actionResolver.submitScoreGPGS(gameHighScore);
					}
					stage.clear();
					controllerButtonInit();
					isGameOver = false;
					newGame();
					isGamePause = false;
					gameStageBackgroundMusic.play();
					gameStageBackgroundMusic.setLooping(true);
					gameStageBackgroundMusic.setVolume(musicVolume / 2);
					scoreBoardMove = height / 2;
					for (int i = 0; i < assets.size(); i++) {
						snackEnemies.get(i).snackPosY = -height;
					}
				} else if (action.equals(GameCode.GAME_MENU)) {
					boolean isHigh = FileHandlers.setGameData();
					if (isHigh) {
						actionResolver.submitScoreGPGS(gameHighScore);
					}
					stage.clear();
					controllerButtonInit();
					isGameOver = false;
					newGame();
					isGamePause = false;
					GAME_STATE = GAME_MENU;
					game.setScreen(game.menuScreen);
				} else if (!isGamePause && !isGameOver
						&& action.equals(GameCode.GAME_PAUSE)) {
					gameStageBackgroundMusic.pause();
					pauseActionCallback();
					scoreBoardMove = height / 2;
				} else if (action.equals(GameCode.GAME_PLAY)) {
					stage.clear();
					controllerButtonInit();
					isGamePause = false;
					scoreBoardMove = 1;
					gameStageBackgroundMusic.play();
				} else if (action.equals(GameCode.SETTING)) {
					boolean isHigh = FileHandlers.setGameData();
					if (isHigh) {
						actionResolver.submitScoreGPGS(gameHighScore);
					}
					stage.clear();
					controllerButtonInit();
					isGameOver = false;
					newGame();
					isGamePause = false;
					game.setScreen(game.soundScreen);
				}
			}
		});

	}

	private void addHeroBullet() {
		long id = heroBulletSound.play();
		heroBulletSound.setVolume(id, actionsVolume);

		if (isHeroRight) {
			heroBullets.add(new HeroBullet(hero.heroPosX + 3 * hero.heroWidth
					/ 4, hero.heroPosY + hero.heroHeight / 4,
					hero.heroHeight / 2, hero.heroHeight / 4,
					HERO_BULLET_SPEED_X, 0, 0, 1.0f, 0.15f));
		} else {
			heroBullets.add(new HeroBullet(hero.heroPosX - hero.heroWidth / 2,
					hero.heroPosY + hero.heroHeight / 4, hero.heroHeight / 2,
					hero.heroHeight / 4, -HERO_BULLET_SPEED_X, 0, 0, 1.0f,
					0.15f));
		}
	}

	private void pauseActionCallback() {
		stage.clear();
		x = originalWidth / 5;
		y = originalHeight / 5;
		w = originalWidth - 2 * originalWidth / 5;
		h = originalHeight - 2 * originalHeight / 5;
		pauseButtonInit();
		isGamePause = true;
	}

	@Override
	public void render(float delta) {

		Gdx.gl.glClearColor(0.1f, 0.1f, 0.1f, 1);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);

		spriteBatch.begin();
		spriteBatch.enableBlending();

		touchInputs();
		accelarationInputs();

		drawStageBackground();
		drawSnacks();
		drawStageComponents();
		drawPointComponents();
		drawAnimations();
		drawAnimationFires();
		drawEffects();
		drawHero();
		drawBullets();
		drawIceEffects();
		drawGameOverScreen();

		drawScore();
		drawGameOverDetails();

		updateScreens();
		updateTimer();
		updateOthers();

		// drawDebug();

		spriteBatch.end();
		spriteBatch.disableBlending();

		stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
		stage.draw();
		Table.drawDebug(stage);
	}

	private void drawSnacks() {
		for (int i = 0; i < snackEnemies.size(); i++) {
			spriteBatch.draw(snakcBodyTextureRegion,
					snackEnemies.get(i).snackPosX,
					snackEnemies.get(i).snackPosY,
					snackEnemies.get(i).snackWidth,
					snackEnemies.get(i).snackHeight);

			spriteBatch.draw(
					snakcHeadTextureRegion,
					snackEnemies.get(i).snackPosX
							- snackEnemies.get(i).snackHeight
							+ snackEnemies.get(i).snackWidth,
					snackEnemies.get(i).snackPosY
							- snackEnemies.get(i).snackHeight,
					snackEnemies.get(i).snackHeight,
					snackEnemies.get(i).snackHeight,
					2 * snackEnemies.get(i).snackHeight,
					2 * snackEnemies.get(i).snackHeight, 1.0f, 1.0f,
					snackEnemies.get(i).snackRotation);

			if (!isGameOver
					&& !hero.isHeroDied
					&& CollisionCheck.isCollisionOccuredRectaPoint2x2(
							hero.heroPosX, hero.heroPosY, hero.heroPosX
									+ hero.heroWidth, hero.heroPosY
									+ hero.heroHeight,
							snackEnemies.get(i).snackPosX,
							snackEnemies.get(i).snackPosY,
							snackEnemies.get(i).snackPosX
									+ snackEnemies.get(i).snackWidth,
							snackEnemies.get(i).snackPosY
									+ snackEnemies.get(i).snackHeight)) {
				float tempPosX = hero.heroPosX;
				float tempPosY = hero.heroPosY;
				hero = new Hero(0.15f, true);
				hero.isHeroDied = true;
				hero.heroPosX = tempPosX;
				hero.heroPosY = tempPosY;
				hero.animation = new Animation(hero.keyFrameDelay,
						textureHeroAtlas[2].getRegions());
				hero.heroAlpha = 1.0f;

				isGameOver = true;

				float tempSwap = hero.heroWidth;
				hero.heroWidth = 0.8f * hero.heroHeight;
				hero.heroHeight = tempSwap;

				x = originalWidth / 4;
				y = originalHeight / 4;
				w = originalWidth - 2 * originalWidth / 4;
				h = originalHeight - 2 * originalHeight / 4;

				long id = heroDiedSound.play();
				heroDiedSound.setVolume(id, actionsVolume);

				effects.add(new Effect(hero.heroPosX, hero.heroPosY,
						hero.heroWidth / 2, hero.heroWidth / 2, EFFECT_SPEED
								/ squarRoot2, EFFECT_SPEED / squarRoot2, 3,
						1.0f));
				effects.add(new Effect(hero.heroPosX, hero.heroPosY,
						hero.heroWidth / 2, hero.heroWidth / 2, -EFFECT_SPEED
								/ squarRoot2, -EFFECT_SPEED / squarRoot2, 3,
						1.0f));
				effects.add(new Effect(hero.heroPosX, hero.heroPosY,
						hero.heroWidth / 2, hero.heroWidth / 2, EFFECT_SPEED
								/ squarRoot2, -EFFECT_SPEED / squarRoot2, 3,
						1.0f));
				effects.add(new Effect(hero.heroPosX, hero.heroPosY,
						hero.heroWidth / 2, hero.heroWidth / 2, -EFFECT_SPEED
								/ squarRoot2, EFFECT_SPEED / squarRoot2, 3,
						1.0f));

				effects.add(new Effect(hero.heroPosX, hero.heroPosY,
						hero.heroWidth / 2, hero.heroWidth / 2, 0,
						EFFECT_SPEED, 3, 1.0f));
				effects.add(new Effect(hero.heroPosX, hero.heroPosY,
						hero.heroWidth / 2, hero.heroWidth / 2, 0,
						-EFFECT_SPEED, 3, 1.0f));
				effects.add(new Effect(hero.heroPosX, hero.heroPosY,
						hero.heroWidth / 2, hero.heroWidth / 2, EFFECT_SPEED,
						0, 3, 1.0f));
				effects.add(new Effect(hero.heroPosX, hero.heroPosY,
						hero.heroWidth / 2, hero.heroWidth / 2, -EFFECT_SPEED,
						0, 3, 1.0f));

			}
			if (!isGamePause) {
				if (snackEnemies.get(i).snackLengthTempX >= snackEnemies.get(i).snackLengthX
						|| snackEnemies.get(i).snackLengthTempX <= -snackEnemies
								.get(i).snackLengthX) {
					snackEnemies.get(i).snackIncrementX = -snackEnemies.get(i).snackIncrementX;
				}
				if (snackEnemies.get(i).snackIncrementX > 0) {
					snackEnemies.get(i).snackRotation = (snackEnemies.get(i).snackRotation + 3) % 360;
				} else {
					snackEnemies.get(i).snackRotation = (snackEnemies.get(i).snackRotation - 3) % 360;
				}
				// System.out.println((1+GAME_TIME/60));
				snackEnemies.get(i).snackWidth += (1 + GAME_TIME / 60)
						* snackEnemies.get(i).snackIncrementX;
				snackEnemies.get(i).snackLengthTempX += (1 + GAME_TIME / 60)
						* snackEnemies.get(i).snackIncrementX;
			}

		}
	}

	private void updateOthers() {
		if (isGamePauseAndroid) {
			isGamePauseAndroid = false;
			pauseActionCallback();
		}
	}

	private void drawScore() {

		if (!isGamePause && scoreBoardMove > 0 && GAME_STATE.equals(GAME_START)) {
			scoreBoardMove = scoreBoardMove / SCOREBOARD_MOVE_SPEED_RATIO < 1 ? 1
					: scoreBoardMove / SCOREBOARD_MOVE_SPEED_RATIO;
		} else if (!isGamePause && !isGameOver
				&& !GAME_STATE.equals(GAME_START)) {

			if (scoreBoardMove < height / 2) {
				scoreBoardMove = scoreBoardMove * SCOREBOARD_MOVE_SPEED_RATIO > height / 2 ? height / 2
						: scoreBoardMove * SCOREBOARD_MOVE_SPEED_RATIO;
			}

			String tempString = String.format("%06d", gameScore);
			font.draw(
					spriteBatch,
					tempString,
					width - font.getBounds(tempString).width
							- font.getBounds(tempString).height / 2, height
							- font.getBounds(tempString).height / 2);
		} else if (!isGamePause && isGameOver && hero.heroAlpha == 1.5f) {
			if (scoreBoardMove > 0) {
				scoreBoardMove = scoreBoardMove / SCOREBOARD_MOVE_SPEED_RATIO < 1 ? 1
						: scoreBoardMove / SCOREBOARD_MOVE_SPEED_RATIO;

			}
		} else if (isGamePause && !isGameOver) {
			if (scoreBoardMove > 0) {
				scoreBoardMove = scoreBoardMove / SCOREBOARD_MOVE_SPEED_RATIO < 1 ? 1
						: scoreBoardMove / SCOREBOARD_MOVE_SPEED_RATIO;
			}
		}
	}

	private void drawIceEffects() {
		for (int i = 0; i < iceEffect.size(); i++) {
			spriteBatch
					.draw(iceEffectTextureRegion, iceEffect.get(i).effectPosX,
							iceEffect.get(i).effectPosY,
							iceEffect.get(i).effectWidth,
							iceEffect.get(i).effectHeight);

			if (iceEffect.get(i).effectPosY + iceEffect.get(i).effectHeight < 0) {
				iceEffect.get(i).effectPosX = Math.abs(new Random()
						.nextInt((int) width));
				iceEffect.get(i).effectPosY = Math.abs(new Random()
						.nextInt((int) height / 4)) + height;

				float tempWidth = Math.abs(new Random()
						.nextInt((int) width / 50)) + width / 50;
				float tempSpeed = BOMB_SPEED_Y / 10 < 1 ? 1 : BOMB_SPEED_Y / 10;
				iceEffect.get(i).effectSpeedY = Math.abs(new Random()
						.nextInt((int) tempSpeed)) + tempSpeed;
				iceEffect.get(i).effectWidth = tempWidth;
				iceEffect.get(i).effectHeight = tempWidth;
			}

			if (!isGamePause) {
				iceEffect.get(i).effectPosY -= iceEffect.get(i).effectSpeedY;
			}

		}
	}

	private void accelarationInputs() {

		float accelerometerX = -(Gdx.input.getAccelerometerX() % 4);
		if (!GAME_STATE.equals(GAME_START) && !isGamePause
				&& accelerometerX != 0 && !isGameOver) {

			if (accelerometerX < 0) {
				isLeftArrow = true;
				isHeroRight = false;
			} else if (accelerometerX > 0) {
				isRightArrow = true;
				isHeroRight = true;
			}

			hero.heroPosX += accelerometerX;
			for (int i = 0; i < assets.size(); i++) {
				if (!assets.get(i).isAssetEnemy
						&& assets.get(i).assetTextureCategory
								.equals(Category.BLOCK)
						&& CollisionCheck.isCollisionOccuredRectaPoint2x2(
								assets.get(i).assetPosX,
								assets.get(i).assetPosY,
								assets.get(i).assetPosX
										+ assets.get(i).assetWidth,
								assets.get(i).assetPosY
										+ assets.get(i).assetHeight,
								hero.heroPosX, hero.heroPosY, hero.heroPosX
										+ hero.heroWidth, hero.heroPosY
										+ hero.heroHeight)) {
					hero.heroPosX -= accelerometerX;
				}
			}
		}
	}

	private void drawDebug() {

		System.out.println("assets : " + assets.size());
		System.out.println("points : " + points.size());
		System.out.println("anims : " + anims.size());
		System.out.println("animFires : " + animFires.size());
		System.out.println("effects : " + effects.size());
		System.out.println("snacks : " + snackEnemies.size());
		System.out.println("bullets : " + heroBullets.size());

	}

	private void drawGameOverScreen() {
		if (isGameOver && hero.heroAlpha == 1.5f) {
			spriteBatch.draw(screenOverTextureRegion, 0, 0, width, height);
			drawGameOverDetails();
		}
	}

	private void drawGameOverDetails() {

		String[] tempString = { MOTIVATION,
				"Played : " + noOfGamePlays + " times",
				"High Score : " + gameHighScore, "Your Score : " + gameScore };
		for (int i = 0; i < tempString.length; i++) {
			font.draw(
					spriteBatch,
					tempString[i],
					width / 2 - font.getBounds(tempString[i]).width / 2,
					scoreBoardMove + (i * height / 15) + 2 * height / 3
							+ font.getBounds(tempString[i]).height / 2);

		}
	}

	private void drawPointComponents() {

		for (int i = 0; i < points.size(); i++) {

			if (!isGamePause) {
				points.get(i).elapsedTime += Gdx.graphics.getDeltaTime();
			}

			Color c = spriteBatch.getColor();
			spriteBatch.setColor(c.r, c.g, c.b, points.get(i).pointAplha);

			spriteBatch.draw(points.get(i).animation.getKeyFrame(
					points.get(i).elapsedTime, true), originX
					+ points.get(i).pointPosX * (width / originalWidth),
					originY + points.get(i).pointPosY
							* (height / originalHeight),
					points.get(i).pointWidth * (width / originalWidth),
					points.get(i).pointHeight * (height / originalHeight));
			spriteBatch.setColor(c.r, c.g, c.b, 1.0f);

			points.get(i).pointPosX += points.get(i).pointSpeedX;
			points.get(i).pointPosY += points.get(i).pointSpeedY;

			if (!isGamePause
					&& points.get(i).pointAtlasIndex != (NUMBER_OF_POINTS - 1)
					&& CollisionCheck
							.isCollisionOccuredRectaPoint2x2(
									points.get(i).pointPosX,
									points.get(i).pointPosY,
									points.get(i).pointPosX
											+ points.get(i).pointWidth,
									points.get(i).pointPosY
											+ points.get(i).pointHeight,
									hero.heroPosX, hero.heroPosY, hero.heroPosX
											+ hero.heroWidth, hero.heroPosY
											+ hero.heroHeight)) {

				effects.add(new Effect(points.get(i).pointPosX,
						points.get(i).pointPosY, points.get(i).pointWidth,
						points.get(i).pointHeight, EFFECT_SPEED / squarRoot2,
						EFFECT_SPEED / squarRoot2, 4, 1.0f));
				effects.add(new Effect(points.get(i).pointPosX,
						points.get(i).pointPosY, points.get(i).pointWidth,
						points.get(i).pointHeight, -EFFECT_SPEED / squarRoot2,
						-EFFECT_SPEED / squarRoot2, 4, 1.0f));
				effects.add(new Effect(points.get(i).pointPosX,
						points.get(i).pointPosY, points.get(i).pointWidth,
						points.get(i).pointHeight, EFFECT_SPEED / squarRoot2,
						-EFFECT_SPEED / squarRoot2, 4, 1.0f));
				effects.add(new Effect(points.get(i).pointPosX,
						points.get(i).pointPosY, points.get(i).pointWidth,
						points.get(i).pointHeight, -EFFECT_SPEED / squarRoot2,
						EFFECT_SPEED / squarRoot2, 4, 1.0f));

				effects.add(new Effect(points.get(i).pointPosX,
						points.get(i).pointPosY, points.get(i).pointWidth,
						points.get(i).pointHeight, 0, EFFECT_SPEED, 4, 1.0f));
				effects.add(new Effect(points.get(i).pointPosX,
						points.get(i).pointPosY, points.get(i).pointWidth,
						points.get(i).pointHeight, 0, -EFFECT_SPEED, 4, 1.0f));
				effects.add(new Effect(points.get(i).pointPosX,
						points.get(i).pointPosY, points.get(i).pointWidth,
						points.get(i).pointHeight, EFFECT_SPEED, 0, 4, 1.0f));
				effects.add(new Effect(points.get(i).pointPosX,
						points.get(i).pointPosY, points.get(i).pointWidth,
						points.get(i).pointHeight, -EFFECT_SPEED, 0, 4, 1.0f));

				points.add(new PointObject(points.get(i).pointPosX, points
						.get(i).pointPosY, points.get(i).pointWidth, points
						.get(i).pointHeight, points.get(i).pointSpeedX, 5.0f,
						NUMBER_OF_POINTS - 1, points.get(i).pointAplha, points
								.get(i).keyFrameDelay));

				points.remove(i);
				gameScore += 250;

			}

			if (points.get(i).pointPosX < 0
					|| points.get(i).pointPosX + points.get(i).pointWidth > width
					|| points.get(i).pointPosY < 0
					|| points.get(i).pointPosY + points.get(i).pointHeight > height) {
				points.remove(i);
			}

		}

	}

	private void drawBullets() {

		for (int i = 0; i < heroBullets.size(); i++) {
			Color c = spriteBatch.getColor();
			spriteBatch.setColor(c.r, c.g, c.b, heroBullets.get(i).bulletAplha);

			float scalX = 1.0f;
			if (heroBullets.get(i).bulletSpeedX < 0) {
				scalX = -1.0f;
			}

			if (!isGamePause) {
				heroBullets.get(i).elapsedTime += Gdx.graphics.getDeltaTime();
			}
			spriteBatch
					.draw(heroBullets.get(i).animation.getKeyFrame(
							heroBullets.get(i).elapsedTime, true),
							heroBullets.get(i).bulletPosX
									* (width / originalWidth),
							heroBullets.get(i).bulletPosY
									* (height / originalHeight),
							heroBullets.get(i).bulletWidth
									* (width / originalWidth) / 2,
							heroBullets.get(i).bulletHeight
									* (height / originalHeight) / 2,
							heroBullets.get(i).bulletWidth
									* (width / originalWidth),
							heroBullets.get(i).bulletHeight
									* (height / originalHeight), scalX, 1.0f, 0);
			spriteBatch.setColor(c.r, c.g, c.b, 1.0f);

			if (!isGamePause && heroBullets.get(i).bulletAplha >= 1.0f) {
				heroBullets.get(i).bulletPosX += heroBullets.get(i).bulletSpeedX;
				heroBullets.get(i).bulletPosY += heroBullets.get(i).bulletSpeedY;
			} else if (!isGamePause) {
				heroBullets.get(i).bulletAplha -= 0.01f;
				heroBullets.get(i).bulletPosX += heroBullets.get(i).bulletSpeedX / 10;
				heroBullets.get(i).bulletPosY += heroBullets.get(i).bulletSpeedY / 10;
			}
			if (heroBullets.get(i).bulletPosX > width
					|| heroBullets.get(i).bulletPosY > height
					|| heroBullets.get(i).bulletPosX
							+ heroBullets.get(i).bulletWidth < 0) {
				heroBullets.remove(i);
				break;
			} else if (heroBullets.get(i).bulletAplha < 0) {
				heroBullets.remove(i);
				break;
			}

			if (!isGamePause && heroBullets.get(i).bulletAplha >= 1.0f) {
				for (int k = 0; k < anims.size(); k++) {

					if (anims.get(k).isAnimationLoop
							&& CollisionCheck.isCollisionOccuredRectaPoint2x2(
									anims.get(k).animationPosX,
									anims.get(k).animationPosY,
									anims.get(k).animationPosX
											+ anims.get(k).animationWidth,
									anims.get(k).animationPosY
											+ anims.get(k).animationHeight,
									heroBullets.get(i).bulletPosX,
									heroBullets.get(i).bulletPosY,
									heroBullets.get(i).bulletPosX
											+ heroBullets.get(i).bulletWidth,
									heroBullets.get(i).bulletPosY
											+ heroBullets.get(i).bulletHeight)) {
						heroBullets.get(i).bulletAplha = 0.99f;
						gameScore += 150;
						anims.get(k).animationSpeedX = 0;
						anims.get(k).animationSpeedY = 0;
						anims.get(k).isAnimationEnemy = false;
						anims.get(k).keyFrameDelay = 0.10f;
						anims.get(k).isAnimationLoop = false;
						anims.get(k).animAplha = 1.0f;
						anims.get(k).animationWalkLength = 0;

						effects.add(new Effect(anims.get(k).animationPosX
								+ anims.get(k).animationWidth / 4,
								anims.get(k).animationPosY
										+ anims.get(k).animationHeight / 4,
								anims.get(k).animationWidth / 2,
								anims.get(k).animationHeight / 2, EFFECT_SPEED,
								0, 2, 1.0f));
						effects.add(new Effect(anims.get(k).animationPosX
								+ anims.get(k).animationWidth / 4,
								anims.get(k).animationPosY
										+ anims.get(k).animationHeight / 4,
								anims.get(k).animationWidth / 2,
								anims.get(k).animationHeight / 2,
								-EFFECT_SPEED, 0, 2, 1.0f));
						effects.add(new Effect(anims.get(k).animationPosX
								+ anims.get(k).animationWidth / 4,
								anims.get(k).animationPosY
										+ anims.get(k).animationHeight / 4,
								anims.get(k).animationWidth / 2,
								anims.get(k).animationHeight / 2, 0,
								EFFECT_SPEED, 2, 1.0f));
						effects.add(new Effect(anims.get(k).animationPosX
								+ anims.get(k).animationWidth / 4,
								anims.get(k).animationPosY
										+ anims.get(k).animationHeight / 4,
								anims.get(k).animationWidth / 2,
								anims.get(k).animationHeight / 2, 0,
								-EFFECT_SPEED, 2, 1.0f));
						// anims.remove(k);
						gameScore += 500;

						long id = enemyDiedSound.play();
						enemyDiedSound.setVolume(id, actionsVolume);

						break;
					}
				}
			}

			for (int p = 0; p < assets.size(); p++) {
				if (assets.get(p).assetTextureCategory.equals(Category.BLOCK)
						&& CollisionCheck.isCollisionOccuredRectaPoint2x2(
								assets.get(p).assetPosX,
								assets.get(p).assetPosY,
								assets.get(p).assetPosX
										+ assets.get(p).assetWidth,
								assets.get(p).assetPosY
										+ assets.get(p).assetHeight,
								heroBullets.get(i).bulletPosX,
								heroBullets.get(i).bulletPosY,
								heroBullets.get(i).bulletPosX
										+ heroBullets.get(i).bulletWidth,
								heroBullets.get(i).bulletPosY
										+ heroBullets.get(i).bulletHeight)) {

					effects.add(new Effect(heroBullets.get(i).bulletPosX,
							heroBullets.get(i).bulletPosY,
							heroBullets.get(i).bulletHeight,
							heroBullets.get(i).bulletHeight, EFFECT_SPEED
									/ squarRoot2, EFFECT_SPEED / squarRoot2, 1,
							1.0f));
					effects.add(new Effect(heroBullets.get(i).bulletPosX,
							heroBullets.get(i).bulletPosY,
							heroBullets.get(i).bulletHeight,
							heroBullets.get(i).bulletHeight, -EFFECT_SPEED
									/ squarRoot2, -EFFECT_SPEED / squarRoot2,
							1, 1.0f));
					effects.add(new Effect(heroBullets.get(i).bulletPosX,
							heroBullets.get(i).bulletPosY,
							heroBullets.get(i).bulletHeight,
							heroBullets.get(i).bulletHeight, EFFECT_SPEED
									/ squarRoot2, -EFFECT_SPEED / squarRoot2,
							1, 1.0f));
					effects.add(new Effect(heroBullets.get(i).bulletPosX,
							heroBullets.get(i).bulletPosY,
							heroBullets.get(i).bulletHeight,
							heroBullets.get(i).bulletHeight, -EFFECT_SPEED
									/ squarRoot2, EFFECT_SPEED / squarRoot2, 1,
							1.0f));

					effects.add(new Effect(heroBullets.get(i).bulletPosX,
							heroBullets.get(i).bulletPosY,
							heroBullets.get(i).bulletHeight,
							heroBullets.get(i).bulletHeight, 0, EFFECT_SPEED,
							1, 1.0f));
					effects.add(new Effect(heroBullets.get(i).bulletPosX,
							heroBullets.get(i).bulletPosY,
							heroBullets.get(i).bulletHeight,
							heroBullets.get(i).bulletHeight, 0, -EFFECT_SPEED,
							1, 1.0f));
					effects.add(new Effect(heroBullets.get(i).bulletPosX,
							heroBullets.get(i).bulletPosY,
							heroBullets.get(i).bulletHeight,
							heroBullets.get(i).bulletHeight, EFFECT_SPEED, 0,
							1, 1.0f));
					effects.add(new Effect(heroBullets.get(i).bulletPosX,
							heroBullets.get(i).bulletPosY,
							heroBullets.get(i).bulletHeight,
							heroBullets.get(i).bulletHeight, -EFFECT_SPEED, 0,
							1, 1.0f));
					long id = enemyDiedSound.play();
					enemyDiedSound.setVolume(id, actionsVolume);
					heroBullets.remove(i);
					break;
				}
			}

		}
		if (!isGamePause && !isGameOver && !GAME_STATE.equals(GAME_START)) {

			if (heroBullets.size() < NUMBER_OF_REVOLD_BULLET && isBulletReady()) {
				addHeroBullet();
			}

		}
	}

	private boolean isBulletReady() {

		for (int i = 0; i < heroBullets.size(); i++) {
			float startPosX = 0;
			float startPosY = 0;
			if (isHeroRight) {
				startPosX = hero.heroPosX + 3 * hero.heroWidth / 4;
				startPosY = hero.heroPosY + hero.heroHeight / 4;
			} else {
				startPosX = hero.heroPosX - hero.heroWidth / 2;
				startPosY = hero.heroPosY + hero.heroHeight / 4;
			}

			if (CollisionCheck.isCollisionOccuredRectaPoint2x2(startPosX,
					startPosY, startPosX + heroBullets.get(i).bulletWidth,
					startPosY + heroBullets.get(i).bulletHeight,
					heroBullets.get(i).bulletPosX,
					heroBullets.get(i).bulletPosY,
					heroBullets.get(i).bulletPosX
							+ heroBullets.get(i).bulletWidth,
					heroBullets.get(i).bulletPosY
							+ heroBullets.get(i).bulletHeight)) {
				return false;
			}

		}
		return true;
	}

	private void updateTimer() {
		if (!isGamePause) {
			GAME_TIME += Gdx.graphics.getDeltaTime();
			// if (!isStageCompon && (GAME_TIME >= 60 && GAME_TIME <= 61)) {
			// isStageCompon = true;
			// GAME_TIME = 61.01f;
			// }
			// float tempValue = GAME_TIME-(int)GAME_TIME;
			float tempBombCount = GAME_TIME >= 60 ? 0.0125f * (GAME_TIME / 60)
					: 0.006f;
			if ((GAME_TIME - (int) GAME_TIME) <= tempBombCount) {
				anims.add(new AnimationObject(Math.abs(new Random()
						.nextInt((int) width)), height, 2 * width
						/ NUMBER_OF_COLS, 2 * width / NUMBER_OF_COLS, 0,
						-BOMB_SPEED_Y, NUMBER_OF_ANIM, false, 0.10f, true,
						1.0f, 0));
			}
		}
		// System.out.println(GAME_TIME);
	}

	private void drawEffects() {

		for (int i = 0; i < effects.size(); i++) {

			Color c = spriteBatch.getColor();
			spriteBatch.setColor(c.r, c.g, c.b, effects.get(i).effectAplha);

			spriteBatch.draw(
					effetsTextureRegion[effects.get(i).effectTextureIndex],
					effects.get(i).effectPosX * (width / originalWidth),
					effects.get(i).effectPosY * (height / originalHeight),
					effects.get(i).effectWidth * (width / originalWidth),
					effects.get(i).effectHeight * (height / originalHeight));

			if (!isGamePause) {
				effects.get(i).effectPosX += effects.get(i).effectSpeedX;
				effects.get(i).effectPosY += effects.get(i).effectSpeedY;
				effects.get(i).effectAplha -= 0.03f;
				effects.get(i).effectWidth -= effects.get(i).effectWidth * 0.03f;
				effects.get(i).effectHeight -= effects.get(i).effectHeight * 0.03f;
			}

			if (effects.get(i).effectAplha < 0.0f) {
				effects.remove(i);
			}

			spriteBatch.setColor(c.r, c.g, c.b, 1.0f);
		}

	}

	private void drawAnimationFires() {
		for (int i = 0; i < animFires.size(); i++) {

			Color c = spriteBatch.getColor();
			spriteBatch.setColor(c.r, c.g, c.b, animFires.get(i).animAplha);

			float scalX = -1.0f;
			if (animFires.get(i).animationFireSpeedX > 0) {
				scalX = 1.0f;
			}

			spriteBatch.draw(animFires.get(i).animation.getKeyFrame(
					animFires.get(i).elapsedTime,
					animFires.get(i).isAnimationFireLoop),
					originX + animFires.get(i).animationFirePosX
							* (width / originalWidth),
					originY + animFires.get(i).animationFirePosY
							* (height / originalHeight),
					animFires.get(i).animationFireWidth
							* (width / originalWidth) / 2,
					animFires.get(i).animationFireHeight
							* (height / originalHeight / 2),
					animFires.get(i).animationFireWidth
							* (width / originalWidth),
					animFires.get(i).animationFireHeight
							* (height / originalHeight), scalX, 1.0f, 0);

			spriteBatch.setColor(c.r, c.g, c.b, 1.0f);

			if (!isGamePause) {
				animFires.get(i).animationFirePosX += animFires.get(i).animationFireSpeedX;
				animFires.get(i).animationFirePosY += animFires.get(i).animationFireSpeedY;
			}
			if (!isGameOver
					&& !hero.isHeroDied
					&& CollisionCheck.isCollisionOccuredRectaPoint2x2(
							hero.heroPosX,
							hero.heroPosY,
							hero.heroPosX + hero.heroWidth,
							hero.heroPosY + hero.heroHeight,
							animFires.get(i).animationFirePosX,
							animFires.get(i).animationFirePosY,
							animFires.get(i).animationFirePosX
									+ animFires.get(i).animationFireWidth,
							animFires.get(i).animationFirePosY
									+ animFires.get(i).animationFireHeight)) {
				float tempPosX = hero.heroPosX;
				float tempPosY = hero.heroPosY;
				hero = new Hero(0.15f, true);
				hero.isHeroDied = true;
				hero.heroPosX = tempPosX;
				hero.heroPosY = tempPosY;
				hero.heroAlpha = 1.0f;
				hero.animation = new Animation(hero.keyFrameDelay,
						textureHeroAtlas[2].getRegions());
				isGameOver = true;

				float tempSwap = hero.heroWidth;
				hero.heroWidth = 0.8f * hero.heroHeight;
				hero.heroHeight = tempSwap;

				x = originalWidth / 4;
				y = originalHeight / 4;
				w = originalWidth - 2 * originalWidth / 4;
				h = originalHeight - 2 * originalHeight / 4;

				long id = heroDiedSound.play();
				heroDiedSound.setVolume(id, actionsVolume);

				effects.add(new Effect(hero.heroPosX, hero.heroPosY,
						hero.heroWidth / 2, hero.heroWidth / 2, EFFECT_SPEED
								/ squarRoot2, EFFECT_SPEED / squarRoot2, 3,
						1.0f));
				effects.add(new Effect(hero.heroPosX, hero.heroPosY,
						hero.heroWidth / 2, hero.heroWidth / 2, -EFFECT_SPEED
								/ squarRoot2, -EFFECT_SPEED / squarRoot2, 3,
						1.0f));
				effects.add(new Effect(hero.heroPosX, hero.heroPosY,
						hero.heroWidth / 2, hero.heroWidth / 2, EFFECT_SPEED
								/ squarRoot2, -EFFECT_SPEED / squarRoot2, 3,
						1.0f));
				effects.add(new Effect(hero.heroPosX, hero.heroPosY,
						hero.heroWidth / 2, hero.heroWidth / 2, -EFFECT_SPEED
								/ squarRoot2, EFFECT_SPEED / squarRoot2, 3,
						1.0f));

				effects.add(new Effect(hero.heroPosX, hero.heroPosY,
						hero.heroWidth / 2, hero.heroWidth / 2, 0,
						EFFECT_SPEED, 3, 1.0f));
				effects.add(new Effect(hero.heroPosX, hero.heroPosY,
						hero.heroWidth / 2, hero.heroWidth / 2, 0,
						-EFFECT_SPEED, 3, 1.0f));
				effects.add(new Effect(hero.heroPosX, hero.heroPosY,
						hero.heroWidth / 2, hero.heroWidth / 2, EFFECT_SPEED,
						0, 3, 1.0f));
				effects.add(new Effect(hero.heroPosX, hero.heroPosY,
						hero.heroWidth / 2, hero.heroWidth / 2, -EFFECT_SPEED,
						0, 3, 1.0f));

			}
			if (!isGamePause) {
				animFires.get(i).elapsedTime += Gdx.graphics.getDeltaTime();
			}
			if (!isGamePause) {
				for (int k = 0; k < heroBullets.size(); k++) {
					if (i < animFires.size()
							&& heroBullets.get(k).bulletAplha >= 1.0f
							&& CollisionCheck
									.isCollisionOccuredRectaPoint2x2(
											heroBullets.get(k).bulletPosX,
											heroBullets.get(k).bulletPosY,
											heroBullets.get(k).bulletPosX
													+ heroBullets.get(k).bulletWidth,
											heroBullets.get(k).bulletPosY
													+ heroBullets.get(k).bulletHeight,
											animFires.get(i).animationFirePosX,
											animFires.get(i).animationFirePosY,
											animFires.get(i).animationFirePosX
													+ animFires.get(i).animationFireWidth,
											animFires.get(i).animationFirePosY
													+ animFires.get(i).animationFireHeight)) {

						effects.add(new Effect(
								animFires.get(i).animationFirePosX, animFires
										.get(i).animationFirePosY, animFires
										.get(i).animationFireWidth / 2,
								animFires.get(i).animationFireWidth / 2,
								EFFECT_SPEED / squarRoot2, EFFECT_SPEED
										/ squarRoot2, 3, 1.0f));
						effects.add(new Effect(
								animFires.get(i).animationFirePosX, animFires
										.get(i).animationFirePosY, animFires
										.get(i).animationFireWidth / 2,
								animFires.get(i).animationFireWidth / 2,
								-EFFECT_SPEED / squarRoot2, -EFFECT_SPEED
										/ squarRoot2, 3, 1.0f));
						effects.add(new Effect(
								animFires.get(i).animationFirePosX, animFires
										.get(i).animationFirePosY, animFires
										.get(i).animationFireWidth / 2,
								animFires.get(i).animationFireWidth / 2,
								EFFECT_SPEED / squarRoot2, -EFFECT_SPEED
										/ squarRoot2, 3, 1.0f));
						effects.add(new Effect(
								animFires.get(i).animationFirePosX, animFires
										.get(i).animationFirePosY, animFires
										.get(i).animationFireWidth / 2,
								animFires.get(i).animationFireWidth / 2,
								-EFFECT_SPEED / squarRoot2, EFFECT_SPEED
										/ squarRoot2, 3, 1.0f));

						effects.add(new Effect(
								animFires.get(i).animationFirePosX, animFires
										.get(i).animationFirePosY, animFires
										.get(i).animationFireWidth / 2,
								animFires.get(i).animationFireWidth / 2, 0,
								EFFECT_SPEED, 3, 1.0f));
						effects.add(new Effect(
								animFires.get(i).animationFirePosX, animFires
										.get(i).animationFirePosY, animFires
										.get(i).animationFireWidth / 2,
								animFires.get(i).animationFireWidth / 2, 0,
								-EFFECT_SPEED, 3, 1.0f));
						effects.add(new Effect(
								animFires.get(i).animationFirePosX, animFires
										.get(i).animationFirePosY, animFires
										.get(i).animationFireWidth / 2,
								animFires.get(i).animationFireWidth / 2,
								EFFECT_SPEED, 0, 3, 1.0f));
						effects.add(new Effect(
								animFires.get(i).animationFirePosX, animFires
										.get(i).animationFirePosY, animFires
										.get(i).animationFireWidth / 2,
								animFires.get(i).animationFireWidth / 2,
								-EFFECT_SPEED, 0, 3, 1.0f));

						heroBullets.get(k).bulletAplha = 0.99f;
						long id = enemyDiedSound.play();
						enemyDiedSound.setVolume(id, actionsVolume);
						if (i < animFires.size()) {
							animFires.remove(i);
						}
						break;
					}
				}
			}
			if (!isGamePause) {
				for (int k = 0; k < assets.size(); k++) {
					if (i < animFires.size()
							&& assets.get(k).assetTextureCategory
									.equals(Category.BLOCK)
							&& CollisionCheck
									.isCollisionOccuredRectaPoint2x2(
											assets.get(k).assetPosX,
											assets.get(k).assetPosY,
											assets.get(k).assetPosX
													+ assets.get(k).assetWidth,
											assets.get(k).assetPosY
													+ assets.get(k).assetHeight,
											animFires.get(i).animationFirePosX,
											animFires.get(i).animationFirePosY,
											animFires.get(i).animationFirePosX
													+ animFires.get(i).animationFireWidth,
											animFires.get(i).animationFirePosY
													+ animFires.get(i).animationFireHeight)) {

						animFires.get(i).animationFirePosX = animFires.get(i).animationFirePosX
								+ animFires.get(i).animationFireWidth / 4;
						animFires.get(i).animationFirePosY = animFires.get(i).animationFirePosY
								+ animFires.get(i).animationFireHeight / 4;
						effects.add(new Effect(
								animFires.get(i).animationFirePosX, animFires
										.get(i).animationFirePosY, animFires
										.get(i).animationFireWidth / 2,
								animFires.get(i).animationFireWidth / 2,
								EFFECT_SPEED / squarRoot2, EFFECT_SPEED
										/ squarRoot2, 3, 1.0f));
						effects.add(new Effect(
								animFires.get(i).animationFirePosX, animFires
										.get(i).animationFirePosY, animFires
										.get(i).animationFireWidth / 2,
								animFires.get(i).animationFireWidth / 2,
								-EFFECT_SPEED / squarRoot2, -EFFECT_SPEED
										/ squarRoot2, 3, 1.0f));
						effects.add(new Effect(
								animFires.get(i).animationFirePosX, animFires
										.get(i).animationFirePosY, animFires
										.get(i).animationFireWidth / 2,
								animFires.get(i).animationFireWidth / 2,
								EFFECT_SPEED / squarRoot2, -EFFECT_SPEED
										/ squarRoot2, 3, 1.0f));
						effects.add(new Effect(
								animFires.get(i).animationFirePosX, animFires
										.get(i).animationFirePosY, animFires
										.get(i).animationFireWidth / 2,
								animFires.get(i).animationFireWidth / 2,
								-EFFECT_SPEED / squarRoot2, EFFECT_SPEED
										/ squarRoot2, 3, 1.0f));

						effects.add(new Effect(
								animFires.get(i).animationFirePosX, animFires
										.get(i).animationFirePosY, animFires
										.get(i).animationFireWidth / 2,
								animFires.get(i).animationFireWidth / 2, 0,
								EFFECT_SPEED, 3, 1.0f));
						effects.add(new Effect(
								animFires.get(i).animationFirePosX, animFires
										.get(i).animationFirePosY, animFires
										.get(i).animationFireWidth / 2,
								animFires.get(i).animationFireWidth / 2, 0,
								-EFFECT_SPEED, 3, 1.0f));
						effects.add(new Effect(
								animFires.get(i).animationFirePosX, animFires
										.get(i).animationFirePosY, animFires
										.get(i).animationFireWidth / 2,
								animFires.get(i).animationFireWidth / 2,
								EFFECT_SPEED, 0, 3, 1.0f));
						effects.add(new Effect(
								animFires.get(i).animationFirePosX, animFires
										.get(i).animationFirePosY, animFires
										.get(i).animationFireWidth / 2,
								animFires.get(i).animationFireWidth / 2,
								-EFFECT_SPEED, 0, 3, 1.0f));
						long id = heroDiedSound.play();
						heroDiedSound.setVolume(id, actionsVolume);
						if (i < animFires.size()) {
							animFires.remove(i);
						}
						break;
					}
				}
			}
			if (i < animFires.size()
					&& (animFires.get(i).animationFirePosX > width
							|| animFires.get(i).animationFirePosX
									- animFires.get(i).animationFireWidth < 0
							|| animFires.get(i).animationFirePosY > height || animFires
							.get(i).animationFirePosY
							- animFires.get(i).animationFireHeight < 0)) {
				animFires.remove(i);
			}
		}
	}

	private void drawAnimations() {

		for (int i = 0; i < anims.size(); i++) {
			// System.out.println(anims.size());
			float scalX = -1.0f;
			if (anims.get(i).animationSpeedX >= 0) {
				scalX = 1.0f;
			}
			if (!isGamePause) {
				anims.get(i).elapsedTime += Gdx.graphics.getDeltaTime();
			}

			Color c = spriteBatch.getColor();
			spriteBatch.setColor(c.r, c.g, c.b, anims.get(i).animAplha);

			spriteBatch.draw(
					anims.get(i).animation.getKeyFrame(
							anims.get(i).elapsedTime,
							anims.get(i).isAnimationLoop),
					originX + anims.get(i).animationPosX
							* (width / originalWidth),
					originY
							+ (anims.get(i).animationPosY - 0.05f * anims
									.get(i).animationHeight)
							* (height / originalHeight),
					anims.get(i).animationWidth * (width / originalWidth) / 2,
					(1.05f * anims.get(i).animationHeight)
							* (height / originalHeight / 2),
					anims.get(i).animationWidth * (width / originalWidth),
					anims.get(i).animationHeight * (height / originalHeight),
					scalX, 1.0f, 0);

			spriteBatch.setColor(c.r, c.g, c.b, 1.0f);

			if (!isGamePause && i < anims.size()
					&& anims.get(i).isAnimationLoop
					&& anims.get(i).animationSpeedY != 0) {
				anims.get(i).animationPosY += anims.get(i).animationSpeedY;
				for (int k = 0; k < assets.size(); k++) {
					if (assets.get(k).assetTextureCategory
							.equals(Category.BLOCK)
							&& CollisionCheck.isCollisionOccuredRectaPoint2x2(
									assets.get(k).assetPosX,
									assets.get(k).assetPosY,
									assets.get(k).assetPosX
											+ assets.get(k).assetWidth,
									assets.get(k).assetPosY
											+ assets.get(k).assetHeight,
									anims.get(i).animationPosX
											+ anims.get(i).animationWidth / 4,
									anims.get(i).animationPosY,
									anims.get(i).animationPosX
											+ anims.get(i).animationWidth / 2,
									anims.get(i).animationPosY
											+ anims.get(i).animationHeight)) {
						anims.get(i).animationPosY = assets.get(k).assetPosY
								+ assets.get(k).assetHeight;
						anims.get(i).animationSpeedY = 0;
						anims.get(i).animAplha = 1.9f;
						break;
					}
					if (anims.get(i).animationPosY < 0) {
						anims.get(i).animationPosY = 0;
						anims.get(i).animationSpeedY = 0;
						anims.get(i).animAplha = 1.9f;
					}
				}
			}

			if (!isGamePause) {
				if (anims.get(i).animationWalkLength > 0) {
					anims.get(i).animationPosX += anims.get(i).animationSpeedX;
					anims.get(i).animationWalkLength -= (Math
							.abs(anims.get(i).animationSpeedX));
				} else {
					if (new Random().nextInt(10) % 2 == 0
							&& anims.get(i).animationSpeedX != 0) {
						float posXTemp = anims.get(i).animationPosX;
						if (anims.get(i).animationSpeedX < 0) {
							// posXTemp -= anims.get(i).animationWidth / 2;
						} else {
							posXTemp += anims.get(i).animationWidth / 2;
						}

						long id = animBulletSound.play();
						animBulletSound.setVolume(id, actionsVolume);

						animFires.add(new AnimationFireObject(posXTemp, anims
								.get(i).animationPosY
								+ anims.get(i).animationHeight / 2, anims
								.get(i).animationWidth / 2,
								anims.get(i).animationWidth / 2,
								anims.get(i).animationSpeedX, 0, 1, true,
								0.10f, true, 1.0f, 0));
					}
					anims.get(i).animationSpeedX = -anims.get(i).animationSpeedX;
					anims.get(i).animationWalkLength = anims.get(i).animationWalkLengthTemp;
				}
			}
			if (!isGamePause && !anims.get(i).isAnimationLoop) {
				if (anims.get(i).animation
						.isAnimationFinished(anims.get(i).elapsedTime)) {
					if (assets.size() > i) {
						anims.get(i).animationPosY = -height;
					} else {
						anims.remove(i);
					}
				} else {
					anims.get(i).animAplha -= 0.01f;
					if (anims.get(i).animationSpeedX == 0
							&& anims.get(i).animationSpeedX == 0) {

						if (!isGameOver
								&& !hero.isHeroDied
								&& anims.get(i).isAnimationEnemy
								&& CollisionCheck
										.isCollisionOccuredRectaPoint2x2(
												anims.get(i).animationPosX,
												anims.get(i).animationPosY,
												anims.get(i).animationPosX
														+ anims.get(i).animationWidth,
												anims.get(i).animationPosY
														+ anims.get(i).animationHeight,
												hero.heroPosX, hero.heroPosY,
												hero.heroPosX + hero.heroWidth,
												hero.heroPosY + hero.heroHeight)) {
							float tempPosX = hero.heroPosX;
							float tempPosY = hero.heroPosY;
							hero = new Hero(0.15f, true);
							hero.isHeroDied = true;
							hero.heroPosX = tempPosX;
							hero.heroPosY = tempPosY;
							hero.animation = new Animation(hero.keyFrameDelay,
									textureHeroAtlas[2].getRegions());
							hero.heroAlpha = 1.0f;
							float tempSwap = hero.heroWidth;
							hero.heroWidth = 0.8f * hero.heroHeight;
							hero.heroHeight = tempSwap;
							isGameOver = true;
							x = originalWidth / 4;
							y = originalHeight / 4;
							w = originalWidth - 2 * originalWidth / 4;
							h = originalHeight - 2 * originalHeight / 4;

							long id = heroDiedSound.play();
							heroDiedSound.setVolume(id, actionsVolume);

							effects.add(new Effect(hero.heroPosX,
									hero.heroPosY, hero.heroWidth / 2,
									hero.heroWidth / 2, EFFECT_SPEED
											/ squarRoot2, EFFECT_SPEED
											/ squarRoot2, 0, 1.0f));
							effects.add(new Effect(hero.heroPosX,
									hero.heroPosY, hero.heroWidth / 2,
									hero.heroWidth / 2, -EFFECT_SPEED
											/ squarRoot2, -EFFECT_SPEED
											/ squarRoot2, 0, 1.0f));
							effects.add(new Effect(hero.heroPosX,
									hero.heroPosY, hero.heroWidth / 2,
									hero.heroWidth / 2, EFFECT_SPEED
											/ squarRoot2, -EFFECT_SPEED
											/ squarRoot2, 0, 1.0f));
							effects.add(new Effect(hero.heroPosX,
									hero.heroPosY, hero.heroWidth / 2,
									hero.heroWidth / 2, -EFFECT_SPEED
											/ squarRoot2, EFFECT_SPEED
											/ squarRoot2, 0, 1.0f));

							effects.add(new Effect(hero.heroPosX,
									hero.heroPosY, hero.heroWidth / 2,
									hero.heroWidth / 2, 0, EFFECT_SPEED, 0,
									1.0f));
							effects.add(new Effect(hero.heroPosX,
									hero.heroPosY, hero.heroWidth / 2,
									hero.heroWidth / 2, 0, -EFFECT_SPEED, 0,
									1.0f));
							effects.add(new Effect(hero.heroPosX,
									hero.heroPosY, hero.heroWidth / 2,
									hero.heroWidth / 2, EFFECT_SPEED, 0, 0,
									1.0f));
							effects.add(new Effect(hero.heroPosX,
									hero.heroPosY, hero.heroWidth / 2,
									hero.heroWidth / 2, -EFFECT_SPEED, 0, 0,
									1.0f));

						}

					}
				}
			} else {
				if (!isGamePause
						&& !isGameOver
						&& !hero.isHeroDied
						&& anims.get(i).isAnimationEnemy
						&& CollisionCheck.isCollisionOccuredRectaPoint2x2(
								anims.get(i).animationPosX,
								anims.get(i).animationPosY,
								anims.get(i).animationPosX
										+ anims.get(i).animationWidth,
								anims.get(i).animationPosY
										+ anims.get(i).animationHeight,
								hero.heroPosX, hero.heroPosY, hero.heroPosX
										+ hero.heroWidth, hero.heroPosY
										+ hero.heroHeight)) {
					float tempPosX = hero.heroPosX;
					float tempPosY = hero.heroPosY;
					hero = new Hero(0.15f, true);
					hero.isHeroDied = true;
					hero.heroPosX = tempPosX;
					hero.heroPosY = tempPosY;
					hero.animation = new Animation(hero.keyFrameDelay,
							textureHeroAtlas[2].getRegions());
					hero.heroAlpha = 1.0f;
					float tempSwap = hero.heroWidth;
					hero.heroWidth = 0.8f * hero.heroHeight;
					hero.heroHeight = tempSwap;
					isGameOver = true;
					x = originalWidth / 4;
					y = originalHeight / 4;
					w = originalWidth - 2 * originalWidth / 4;
					h = originalHeight - 2 * originalHeight / 4;

					long id = heroDiedSound.play();
					heroDiedSound.setVolume(id, actionsVolume);

					effects.add(new Effect(hero.heroPosX, hero.heroPosY,
							hero.heroWidth / 2, hero.heroWidth / 2,
							EFFECT_SPEED / squarRoot2, EFFECT_SPEED
									/ squarRoot2, 0, 1.0f));
					effects.add(new Effect(hero.heroPosX, hero.heroPosY,
							hero.heroWidth / 2, hero.heroWidth / 2,
							-EFFECT_SPEED / squarRoot2, -EFFECT_SPEED
									/ squarRoot2, 0, 1.0f));
					effects.add(new Effect(hero.heroPosX, hero.heroPosY,
							hero.heroWidth / 2, hero.heroWidth / 2,
							EFFECT_SPEED / squarRoot2, -EFFECT_SPEED
									/ squarRoot2, 0, 1.0f));
					effects.add(new Effect(hero.heroPosX, hero.heroPosY,
							hero.heroWidth / 2, hero.heroWidth / 2,
							-EFFECT_SPEED / squarRoot2, EFFECT_SPEED
									/ squarRoot2, 0, 1.0f));

					effects.add(new Effect(hero.heroPosX, hero.heroPosY,
							hero.heroWidth / 2, hero.heroWidth / 2, 0,
							EFFECT_SPEED, 0, 1.0f));
					effects.add(new Effect(hero.heroPosX, hero.heroPosY,
							hero.heroWidth / 2, hero.heroWidth / 2, 0,
							-EFFECT_SPEED, 0, 1.0f));
					effects.add(new Effect(hero.heroPosX, hero.heroPosY,
							hero.heroWidth / 2, hero.heroWidth / 2,
							EFFECT_SPEED, 0, 0, 1.0f));
					effects.add(new Effect(hero.heroPosX, hero.heroPosY,
							hero.heroWidth / 2, hero.heroWidth / 2,
							-EFFECT_SPEED, 0, 0, 1.0f));

				}
			}

			if (i < anims.size() && anims.get(i).animAplha < 2.0f
					&& anims.get(i).isAnimationLoop
					&& anims.get(i).animationSpeedX == 0
					&& anims.get(i).animationSpeedY == 0) {
				anims.get(i).animAplha += 0.001f;
			} else if (i < anims.size() && anims.get(i).animAplha >= 2.0f
					&& anims.get(i).animationSpeedX == 0
					&& anims.get(i).animationSpeedY == 0) {

				long id = bombSound.play();
				bombSound.setVolume(id, actionsVolume);

				anims.add(new AnimationObject(anims.get(i).animationPosX
						- anims.get(i).animationWidth / 2,
						anims.get(i).animationPosY
								- anims.get(i).animationWidth / 2, 2 * anims
								.get(i).animationWidth,
						2 * anims.get(i).animationWidth, 0, 0,
						NUMBER_OF_ANIM + 1, true, 0.05f, false, 1.0f, 0));
				if (assets.size() > i) {
					anims.get(i).animationPosY = -height;
				} else {
					anims.remove(i);
				}
			}
		}

	}

	private void updateScreens() {

		if (!isGamePause && !hero.isJumpState && hero.heroPosY > 0) {
			int i;
			for (i = 0; i < assets.size(); i++) {

				if (!assets.get(i).isAssetEnemy
						&& CollisionCheck.isCollisionOccuredRectaPoint2x2(
								assets.get(i).assetPosX,
								assets.get(i).assetPosY,
								assets.get(i).assetPosX
										+ assets.get(i).assetWidth,
								assets.get(i).assetPosY
										+ assets.get(i).assetHeight,
								hero.heroPosX, hero.heroPosY - hero.heroHeight
										/ 10, hero.heroPosX + hero.heroWidth,
								hero.heroPosY + hero.heroHeight)) {

					break;

				}

			}
			if (i == assets.size()) {

				hero.isJumpState = true;
				if (heroAtlasIndex != 0) {
					heroAtlasIndex = 0;
					hero.animation = new Animation(hero.keyFrameDelay,
							textureHeroAtlas[0].getRegions());
				}
				hero.heroT = 1.5f * hero.heroU / 9.8f;

			}
		}
	}

	private void touchInputs() {
		if (!isGamePause && !isGameOver && !hero.isHeroDied
				&& Gdx.input.isTouched()) {

			GAME_STATE = "runnig";

			float getX = Gdx.input.getX();
			float getY = height - Gdx.input.getY();

			hero.isJumpState = true;
			if (heroAtlasIndex != 0) {
				heroAtlasIndex = 0;
				hero.animation = new Animation(hero.keyFrameDelay,
						textureHeroAtlas[heroAtlasIndex].getRegions());
			}
			hero.heroU = hero.heroUInit;
			hero.heroT = 0;
			hero.heroV = hero.heroU + 9.8f * hero.heroT;

		}
	}

	private void gameOverButtonInit() {

		float buttonWide = height / 10;
		float buttonHeight = height / 10;

		addButton(reloadButtonTexture, x + w / 2 - 1.25f * buttonWide, y + h
				/ 4, buttonWide, buttonHeight, GameCode.GAME_RELOAD);
		addButton(menuButtonTexture, x + w / 2 + 0.25f * buttonWide, y + h / 4,
				buttonWide, buttonHeight, GameCode.GAME_MENU);
	}

	private void pauseButtonInit() {

		float buttonWide = height / 10;
		float buttonHeight = height / 10;

		addButton(playButtonTexture, x + w / 2 - 2 * buttonWide / 2
				- buttonWide, y + h / 4, buttonWide, buttonHeight,
				GameCode.GAME_PLAY);
		addButton(reloadButtonTexture, x + w / 2 - buttonWide / 2, y + h / 4,
				buttonWide, buttonHeight, GameCode.GAME_RELOAD);
		addButton(menuButtonTexture, x + w / 2 + buttonWide, y + h / 4,
				buttonWide, buttonHeight, GameCode.GAME_MENU);

	}

	private void drawHero() {

		float scalX = -1.0f;
		if (isHeroRight) {
			scalX = 1.0f;
		}
		if (!isGamePause) {
			hero.elapsedTime += Gdx.graphics.getDeltaTime();
		}

		spriteBatch.draw(hero.animation.getKeyFrame(hero.elapsedTime, true),
				hero.heroPosX, hero.heroPosY, hero.heroWidth / 2,
				hero.heroHeight / 2, hero.heroWidth, hero.heroHeight, scalX,
				1.0f, 0);
		if (isGameOver) {
			if (hero.heroAlpha >= 0 && hero.heroAlpha <= 1) {
				hero.heroAlpha -= 0.01f;
			} else if (hero.heroAlpha < 0) {
				stage.clear();
				gameOverButtonInit();
				isStageCompon = false;
				hero.heroAlpha = 1.5f;
				pauseAllSound();

				if (!gameOverMusic.isPlaying()) {
					gameOverMusic.play();
					gameOverMusic.setVolume(musicVolume);
				} else {
					gameOverMusic.setVolume(musicVolume);
				}
				gameOverMusic.setVolume(musicVolume);
				gameOverMusic.play();

			}
		}

		if (GAME_STATE.equals(GAME_START)) {
			drawInstructions();
		} else {

			if (hero.heroPosX < 0) {
				hero.heroPosX = 0;
			} else if (hero.heroPosX + hero.heroWidth > width) {
				hero.heroPosX = width - hero.heroWidth;
			}
			if (!hero.isHeroDied && hero.heroPosY + hero.heroHeight < 0) {
				float tempPosX = hero.heroPosX;
				float tempPosY = hero.heroPosY;
				hero = new Hero(0.15f, true);
				hero.isHeroDied = true;
				hero.heroPosX = tempPosX;
				hero.heroPosY = tempPosY;
				hero.animation = new Animation(hero.keyFrameDelay,
						textureHeroAtlas[2].getRegions());
				hero.heroAlpha = 1.0f;

				isGameOver = true;

				float tempSwap = hero.heroWidth;
				hero.heroWidth = 0.8f * hero.heroHeight;
				hero.heroHeight = tempSwap;

				x = originalWidth / 4;
				y = originalHeight / 4;
				w = originalWidth - 2 * originalWidth / 4;
				h = originalHeight - 2 * originalHeight / 4;

				long id = heroDiedSound.play();
				heroDiedSound.setVolume(id, actionsVolume);

				effects.add(new Effect(hero.heroPosX, hero.heroPosY,
						hero.heroWidth / 2, hero.heroWidth / 2, EFFECT_SPEED
								/ squarRoot2, EFFECT_SPEED / squarRoot2, 3,
						1.0f));
				effects.add(new Effect(hero.heroPosX, hero.heroPosY,
						hero.heroWidth / 2, hero.heroWidth / 2, -EFFECT_SPEED
								/ squarRoot2, -EFFECT_SPEED / squarRoot2, 3,
						1.0f));
				effects.add(new Effect(hero.heroPosX, hero.heroPosY,
						hero.heroWidth / 2, hero.heroWidth / 2, EFFECT_SPEED
								/ squarRoot2, -EFFECT_SPEED / squarRoot2, 3,
						1.0f));
				effects.add(new Effect(hero.heroPosX, hero.heroPosY,
						hero.heroWidth / 2, hero.heroWidth / 2, -EFFECT_SPEED
								/ squarRoot2, EFFECT_SPEED / squarRoot2, 3,
						1.0f));

				effects.add(new Effect(hero.heroPosX, hero.heroPosY,
						hero.heroWidth / 2, hero.heroWidth / 2, 0,
						EFFECT_SPEED, 3, 1.0f));
				effects.add(new Effect(hero.heroPosX, hero.heroPosY,
						hero.heroWidth / 2, hero.heroWidth / 2, 0,
						-EFFECT_SPEED, 3, 1.0f));
				effects.add(new Effect(hero.heroPosX, hero.heroPosY,
						hero.heroWidth / 2, hero.heroWidth / 2, EFFECT_SPEED,
						0, 3, 1.0f));
				effects.add(new Effect(hero.heroPosX, hero.heroPosY,
						hero.heroWidth / 2, hero.heroWidth / 2, -EFFECT_SPEED,
						0, 3, 1.0f));

			} else if (hero.heroPosY + hero.heroHeight > height) {
				hero.heroPosY -= hero.heroV;
				hero.heroT = hero.heroU / 9.8f;
			}
			if (!isGamePause && hero.isJumpState) {
				hero.heroV = hero.heroU - 9.8f * hero.heroT;
				if (hero.heroPosY + hero.heroV > height / 2) {
					for (int i = 0; i < assets.size(); i++) {
						assets.get(i).assetPosY -= hero.heroV;
					}
					for (int i = 0; i < animFires.size(); i++) {
						animFires.get(i).animationFirePosY -= hero.heroV;
					}
					for (int i = 0; i < anims.size(); i++) {
						anims.get(i).animationPosY -= hero.heroV;
					}
					for (int i = 0; i < heroBullets.size(); i++) {
						heroBullets.get(i).bulletPosY -= hero.heroV;
					}
					for (int i = 0; i < iceEffect.size(); i++) {
						iceEffect.get(i).effectPosY -= hero.heroV;
					}
					for (int i = 0; i < effects.size(); i++) {
						effects.get(i).effectPosY -= hero.heroV;
					}
					for (int i = 0; i < snackEnemies.size(); i++) {
						snackEnemies.get(i).snackPosY -= hero.heroV;
					}
					// playBackgroundPosY -= hero.heroV / 10;
				} else {
					hero.heroPosY += hero.heroV;
				}
				hero.heroT += hero.heroTimeIncrement;

			}
		}

	}

	private void drawInstructions() {
		spriteBatch.draw(tabToPlayTesture, width / 4, height / 4, width / 2,
				width / 2);
	}

	private void drawStageComponents() {

		for (int i = 0; i < assets.size(); i++) {

			if (!hero.isHeroDied
					&& assets.get(i).isAssetEnemy
					&& CollisionCheck
							.isCollisionOccuredRectaPoint2x2(
									assets.get(i).assetPosX,
									assets.get(i).assetPosY,
									assets.get(i).assetPosX
											+ assets.get(i).assetWidth,
									assets.get(i).assetPosY
											+ assets.get(i).assetHeight,
									hero.heroPosX, hero.heroPosY, hero.heroPosX
											+ hero.heroWidth, hero.heroPosY
											+ hero.heroHeight)) {
				// float tempPosX = hero.heroPosX;
				// float tempPosY = hero.heroPosY;
				// hero = new Hero(0.15f, true);
				// hero.isHeroDied = true;
				// isGameOver = true;
				// hero.heroPosX = tempPosX;
				// hero.heroPosY = tempPosY;
				// hero.heroAlpha = 1.0f;
				// x = originalWidth / 4;
				// y = originalHeight / 4;
				// w = originalWidth - 2 * originalWidth / 4;
				// h = originalHeight - 2 * originalHeight / 4;
				// float tempSwap = hero.heroWidth;
				// hero.heroWidth = 0.8f * hero.heroHeight;
				// hero.heroHeight = tempSwap;
				// long id = heroDiedSound.play();
				// heroDiedSound.setVolume(id, actionsVolume);
				//
				// break;

			}

			if (!isGamePause
					&& !isGameOver
					&& assets.get(i).assetTextureCategory
							.equals(Category.BLOCK)
					&& CollisionCheck
							.isCollisionOccuredRectaPoint2x2(
									assets.get(i).assetPosX,
									assets.get(i).assetPosY,
									assets.get(i).assetPosX
											+ assets.get(i).assetWidth,
									assets.get(i).assetPosY
											+ assets.get(i).assetHeight,
									hero.heroPosX, hero.heroPosY, hero.heroPosX
											+ hero.heroWidth, hero.heroPosY
											+ hero.heroHeight)) {

				if (hero.heroPosY + hero.heroHeight / 2 < assets.get(i).assetPosY
						+ assets.get(i).assetHeight / 2) {
					hero.heroPosY -= hero.heroV;
					hero.heroT = hero.heroU / 9.8f;
				} else {
					hero.heroPosY = assets.get(i).assetPosY
							+ assets.get(i).assetHeight;
					hero.isJumpState = false;
					if (heroAtlasIndex != 1) {
						heroAtlasIndex = 1;
						hero.animation = new Animation(hero.keyFrameDelay,
								textureHeroAtlas[heroAtlasIndex].getRegions());
					}
				}
				break;
			}

		}
		for (int i = 0; i < assets.size(); i++) {
			spriteBatch.draw(
					bricksTextureRegion[assets.get(i).assetTextureIndex],
					assets.get(i).assetPosX - assets.get(i).assetWidth / 10,
					assets.get(i).assetPosY - assets.get(i).assetHeight / 2,
					assets.get(i).assetWidth + 2 * assets.get(i).assetWidth
							/ 10, assets.get(i).assetHeight
							+ assets.get(i).assetHeight / 2);

			if (assets.get(i).assetPosY + assets.get(i).assetHeight < 0) {

				int topValueIndex = 0;
				for (int k = 1; k < assets.size(); k++) {
					if (assets.get(topValueIndex).assetPosY < assets.get(k).assetPosY) {
						topValueIndex = k;
					}
				}

				assets.get(i).assetPosY = Math.abs(new Random()
						.nextInt((int) height / 4)) + height;

				if (assets.get(topValueIndex).assetPosY + 6
						* assets.get(topValueIndex).assetHeight > assets.get(i).assetPosY) {
					assets.get(i).assetPosY = assets.get(topValueIndex).assetPosY
							+ 6 * assets.get(topValueIndex).assetHeight;
				}

				assets.get(i).assetPosX = Math.abs(new Random()
						.nextInt((int) width / 2));

				assets.get(i).assetTextureIndex = Math.abs(new Random()
						.nextInt(100)) % NUMBER_OF_BRICKS;

				anims.get(i).animationPosY = assets.get(i).assetPosY
						+ assets.get(i).assetHeight;
				anims.get(i).animationPosX = assets.get(i).assetPosX;
				anims.get(i).animationAtlasIndex = Math.abs(new Random()
						.nextInt(100)) % NUMBER_OF_ANIM;
				anims.get(i).animAplha = 1.0f;
				anims.get(i).isAnimationLoop = true;
				anims.get(i).isAnimationEnemy = true;
				anims.get(i).animationSpeedX = ((float) Math.abs(new Random()
						.nextInt((int) width / 200)))
						/ (width / 200)
						+ width
						/ 400;
				anims.get(i).animation = new Animation(0.05f,
						textureAnimAtlas[anims.get(i).animationAtlasIndex]
								.getRegions());
				anims.get(i).animationWalkLength = Math.abs(new Random()
						.nextInt((int) (assets.get(i).assetWidth / 2)))
						+ (assets.get(i).assetWidth / 2);
				anims.get(i).animationWalkLengthTemp = anims.get(i).animationWalkLength;

				snackEnemies.get(i).snackPosX = assets.get(i).assetPosX
						+ assets.get(i).assetWidth / 2;
				snackEnemies.get(i).snackPosY = assets.get(i).assetPosY
						+ assets.get(i).assetHeight / 2;

				snackEnemies.get(i).snackWidth = (new Random()
						.nextInt((int) (width))) - width / 2;
				snackEnemies.get(i).snackHeight = width / 32;
				snackEnemies.get(i).snackSpeedX = 0;
				snackEnemies.get(i).snackSpeedY = 0;
				snackEnemies.get(i).snackIncrementX = width / 300;
				snackEnemies.get(i).snackIncrementY = 0;
				snackEnemies.get(i).snackLengthX = width / 2;
				snackEnemies.get(i).snackLengthY = 0;
				snackEnemies.get(i).snackLengthTempX = snackEnemies.get(i).snackWidth;
				snackEnemies.get(i).snackTextureIndex = 103;
				snackEnemies.get(i).snackAplha = 1.0f;
				snackEnemies.get(i).isSnackEnemy = true;

			}
		}
	}

	private void drawStageBackground() {
		spriteBatch.draw(
				playBackgroundTextureRegion[playBackgroundTextureIndex],
				playBackgroundPosX, playBackgroundPosY, playBackgroundWidth,
				playBackgroundHeight);
	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub

	}

	@Override
	public void show() {

		if (!gameStageBackgroundMusic.isPlaying()) {
			gameStageBackgroundMusic.play();
			gameStageBackgroundMusic.setVolume(musicVolume / 2);
			gameStageBackgroundMusic.setLooping(true);
		} else {
			gameStageBackgroundMusic.setVolume(musicVolume / 2);
			gameStageBackgroundMusic.setLooping(true);
		}

		if (!gameOverMusic.isPlaying()) {
			gameOverMusic.play();
			gameOverMusic.setVolume(0);
		} else {
			gameOverMusic.setVolume(0);
		}

		world = new GameWorld();

		stage = new Stage();

		controllerButtonInit();

		FRAME_PER_SECOND = (int) (1.0f / Gdx.graphics.getDeltaTime());

		isGameOver = false;
		isGamePause = false;
		fiveStarScore = anims.size() * 500 + points.size() * 250;

		scoreBoardMove = height / 2;

		GAME_STATE = GAME_START;

		Gdx.input.setInputProcessor(stage);

		actionResolver.showOrLoadInterstital();

	}

	private void controllerButtonInit() {
		addButton(pauseButtonTexture, 0, height - height / 10, height / 10,
				height / 10, GameCode.GAME_PAUSE);

	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub

	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub

	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean keyDown(int keycode) {

		return false;
	}

	@Override
	public boolean keyUp(int keycode) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean keyTyped(char character) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub

	}

}
