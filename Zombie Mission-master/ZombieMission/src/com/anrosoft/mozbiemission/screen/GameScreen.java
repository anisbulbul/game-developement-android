package com.anrosoft.mozbiemission.screen;

import java.util.Random;

import com.anrosoft.mozbiemission.ActionResolver;
import com.anrosoft.mozbiemission.ZombieMission;
import com.anrosoft.mozbiemission.assets.AnimationFireObject;
import com.anrosoft.mozbiemission.assets.AnimationObject;
import com.anrosoft.mozbiemission.assets.Category;
import com.anrosoft.mozbiemission.assets.Effect;
import com.anrosoft.mozbiemission.assets.GameAssets;
import com.anrosoft.mozbiemission.assets.Hero;
import com.anrosoft.mozbiemission.assets.HeroBullet;
import com.anrosoft.mozbiemission.assets.PointObject;
import com.anrosoft.mozbiemission.assets.StageLoad;
import com.anrosoft.mozbiemission.code.GameCode;
import com.anrosoft.mozbiemission.collision.CollisionCheck;
import com.anrosoft.mozbiemission.files.FileHandlers;
import com.anrosoft.mozbiemission.model.GameWorld;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.Texture;
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

	private ZombieMission game;

	private SpriteBatch spriteBatch;
	public static Stage stage;
	public static Skin skin;

	private ActionResolver actionResolver;

	private int tempScoreIncrement;
	private float x, y, w, h;
	private int starTemp;
	private float fiveStarScore;
	private int componCounter;
	private int jumpStep;

	private int achievmentIndex;
	private String[] achievmentIDs;

	public GameScreen(ZombieMission game, ActionResolver actionResolver) {

		this.game = game;
		spriteBatch = new SpriteBatch();
		this.actionResolver = actionResolver;
		isRightArrow = isRightArrow = false;
		isHeroRight = true;
		isGameOver = false;
		isGameWin = false;
		isDoorOpen = false;
		isGamePause = false;
		componCounter = 0;
		jumpStep = 0;
		tempScoreIncrement = 0;
		starTemp = 0;
		fiveStarScore = 0;
		componCounter = 0;

		this.achievmentIndex = 0;
		this.achievmentIDs = actionResolver.getAchievmentIDs();

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

				if (jumpStep < 2 && !isGameOver && !isGamePause
						&& action.equals(GameCode.JUMP_UP)) {
					if (!hero.isHeroDied) {
						hero.isJumpState = true;
						jumpStep++;
						hero.heroU = hero.heroUInit;
						hero.heroT = 0;
						hero.heroV = hero.heroU + 9.8f * hero.heroT;
						long id = heroJumpSound.play();
						heroJumpSound.setVolume(id, actionsVolume);
					}
				} else if (!isGameOver && !isGamePause && !hero.isHeroDied
						&& action.equals(GameCode.HERO_BULLET)) {
					int bulletIndex = heroAtlasIndex;
					if (bulletIndex >= NUMBER_OF_HERO_BULLET) {
						bulletIndex = NUMBER_OF_HERO_BULLET - 1;
					}
					if (heroBullets.size() < NUMBER_OF_REVOLD_BULLET) {

						long id = heroBulletSound.play();
						heroBulletSound.setVolume(id, actionsVolume);

						if (isHeroRight) {
							heroBullets.add(new HeroBullet(hero.heroPosX
									+ hero.heroWidth, hero.heroPosY,
									hero.heroHeight / 2 + heroAtlasIndex
											* hero.heroHeight / 10,
									hero.heroHeight / 4 + heroAtlasIndex
											* hero.heroHeight / 10,
									HERO_BULLET_SPEED_X, 0, bulletIndex, 1.0f,
									0.15f));
						} else {
							heroBullets.add(new HeroBullet(hero.heroPosX
									- hero.heroWidth, hero.heroPosY,
									hero.heroHeight / 2 + heroAtlasIndex
											* hero.heroHeight / 10,
									hero.heroHeight / 4 + heroAtlasIndex
											* hero.heroHeight / 10,
									-HERO_BULLET_SPEED_X, 0, bulletIndex, 1.0f,
									0.15f));
						}
					}
				} else if (isGamePause && action.equals(GameCode.GAME_PLAY)) {
					FileHandlers.initGameData();
					stage.clear();
					controllerButtonInit();
					isGamePause = false;
					gameStageBackgroundMusic.play();
					gameStageBackgroundMusic.setVolume(musicVolume);
				} else if (action.equals(GameCode.GAME_NEXT)) {
					newGame();
					if (stageIconTextureIndex + 1 < NUMBER_OF_STAGES) {
						stageIconTextureIndex++;
					} else if (worldIconTextureIndex + 1 < NUMBER_OF_WORLDS) {
						stageIconTextureIndex = 0;
						worldIconTextureIndex++;
					} else {
						// all world mission completed
						worldIconTextureIndex = 0;
						stageIconTextureIndex = 0;
					}
					StageLoad.stageLoad(worldIconTextureIndex,
							stageIconTextureIndex);
					game.setScreen(game.gameScreen);

				} else if (action.equals(GameCode.GAME_RELOAD)) {
					newGame();
					stage.clear();
					StageLoad.stageLoad(worldIconTextureIndex,
							stageIconTextureIndex);
					gameStageBackgroundMusic.play();
					game.setScreen(game.gameScreen);
				} else if (action.equals(GameCode.GAME_MENU)) {
					FileHandlers.initGameData();
					stage.clear();
					game.setScreen(game.menuScreen);
				}
			}
		});

	}

	@Override
	public void render(float delta) {

		Gdx.gl.glClearColor(0.1f, 0.1f, 0.1f, 1);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);

		spriteBatch.begin();
		spriteBatch.enableBlending();

		touchInputs();

		drawStageBackground();
		drawStageComponents();
		drawPointComponents();
		drawAnimations();
		drawAnimationFires();
		drawEffects();
		drawDoor();
		drawHero();
		drawBullets();

		drawPauseScreen();
		drawGameOverScreen();

		drawControllers();

		updateScreens();
		updateComponEffect();
		updateTimer();

		updateOthers();

		// drawDebug();

		if (!isGamePause && !isDoorOpen && points.isEmpty()) {
			isDoorOpen = true;

			levelClearMusic.play();
			levelClearMusic.setVolume(actionsVolume);

			if (doors.size() >= 1) {
				doors.get(0).doorTextureIndex = 104;
			}
		}

		spriteBatch.end();
		spriteBatch.disableBlending();

		stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
		stage.draw();
		Table.drawDebug(stage);
	}

	private void updateOthers() {
		if (isGamePauseAndroid) {
			isGamePauseAndroid = false;
			pauseActionCallback();
		}
	}

	private void drawDebug() {

		System.out.println("assets : " + assets.size());
		System.out.println("points : " + points.size());
		System.out.println("anims : " + anims.size());
		System.out.println("animFires : " + animFires.size());
		System.out.println("effects : " + effects.size());
		System.out.println("doors : " + doors.size());
		System.out.println("bullets : " + heroBullets.size());

	}

	private void drawGameOverScreen() {

		// w = originalHeight - originalHeight / 20;
		// h = originalHeight - originalHeight / 20;
		// x = (originalWidth - w) / 2;
		// y = originalHeight / 40;
		//
		// isGameOver = isGameWin =true;

		if (isGameOver) {
			if (isGameWin) {

				spriteBatch.draw(screenOverTextures, originX, originY, width,
						height + COMPON);
				spriteBatch.draw(gameWinScreenTexture, x + w / 20, y + h / 20,
						18 * w / 20, 18 * h / 20);

				for (int i = 0; i < starTemp && i < 5; i++) {
					spriteBatch.draw(ratingStarUpButtonTexture, x + 1.5f * w
							/ 8 + i * w / 8, 15 * h / 24, w / 8, w / 8);
				}
				for (int i = starTemp; i < 5; i++) {
					spriteBatch.draw(ratingStarDwonButtonTexture, x + 1.5f * w
							/ 8 + i * w / 8, 15 * h / 24, w / 8, w / 8);
				}
				if (tempScoreIncrement < gameScore
						&& tempScoreIncrement < gameScore) {
					tempScoreIncrement += gameScore / 50 >= 50 ? gameScore / 50
							: 50;
				} else {
					tempScoreIncrement = gameScore;
				}
				if ((gameScore) / (fiveStarScore / 5) > starTemp
						&& effects.isEmpty() && tempScoreIncrement == gameScore) {
					effects.add(new Effect(x + 1.5f * w / 8 + starTemp * w / 8,
							15 * h / 24, w / 8, w / 8, EFFECT_SPEED
									/ squarRoot2, EFFECT_SPEED / squarRoot2, 0,
							1.0f));
					effects.add(new Effect(x + 1.5f * w / 8 + starTemp * w / 8,
							15 * h / 24, w / 8, w / 8, -EFFECT_SPEED
									/ squarRoot2, -EFFECT_SPEED / squarRoot2,
							0, 1.0f));
					effects.add(new Effect(x + 1.5f * w / 8 + starTemp * w / 8,
							15 * h / 24, w / 8, w / 8, EFFECT_SPEED
									/ squarRoot2, -EFFECT_SPEED / squarRoot2,
							0, 1.0f));
					effects.add(new Effect(x + 1.5f * w / 8 + starTemp * w / 8,
							15 * h / 24, w / 8, w / 8, -EFFECT_SPEED
									/ squarRoot2, EFFECT_SPEED / squarRoot2, 0,
							1.0f));

					effects.add(new Effect(x + 1.5f * w / 8 + starTemp * w / 8,
							15 * h / 24, w / 8, w / 8, 0, EFFECT_SPEED, 0, 1.0f));
					effects.add(new Effect(x + 1.5f * w / 8 + starTemp * w / 8,
							15 * h / 24, w / 8, w / 8, 0, -EFFECT_SPEED, 0,
							1.0f));
					effects.add(new Effect(x + 1.5f * w / 8 + starTemp * w / 8,
							15 * h / 24, w / 8, w / 8, EFFECT_SPEED, 0, 0, 1.0f));
					effects.add(new Effect(x + 1.5f * w / 8 + starTemp * w / 8,
							15 * h / 24, w / 8, w / 8, -EFFECT_SPEED, 0, 0,
							1.0f));
					starTemp++;

				}
				String tempScore = "Score : " + tempScoreIncrement;
				font2.draw(spriteBatch, tempScore,
						x + w / 2 - font2.getBounds(tempScore).width / 2, 3 * h
								/ 6 + 3 * font2.getBounds(tempScore).height / 2);
				tempScore = "High Score : " + gameHighScore;
				font2.draw(spriteBatch, tempScore,
						x + w / 2 - font2.getBounds(tempScore).width / 2, 3 * h
								/ 6 - font2.getBounds(tempScore).height / 2);
				tempScore = "Game Played : " + noOfGamePlays;
				font2.draw(spriteBatch, tempScore,
						x + w / 2 - font2.getBounds(tempScore).width / 2, 3 * h
								/ 6 - 5 * font2.getBounds(tempScore).height / 2);

			} else if (hero.heroAlpha == 1.5f) {
				spriteBatch.draw(screenOverTextures, originX, originY, width,
						height + COMPON);
				spriteBatch.draw(gameFailedScreenTexture, x, y, w, h);
			}
		}

	}

	private void drawPauseScreen() {

		if (isGamePause) {

			spriteBatch.draw(screenOverTextures, originX, originY,
					originalWidth, originalHeight);
			spriteBatch.draw(pauseScreenTexture, x, y, w, h);

		}
	}

	private void drawDoor() {

		if (doors.size() >= 1) {
			spriteBatch
					.draw(objetcsTexture[doors.get(0).doorTextureIndex],
							originX + doors.get(0).doorPosX
									* (width / originalWidth),
							originY + doors.get(0).doorPosY
									* (height / originalHeight),
							doors.get(0).doorWidth * (width / originalWidth),
							doors.get(0).doorHeight * (height / originalHeight));

		}

		for (int i = 1; i < doors.size(); i++) {

			spriteBatch
					.draw(objetcsTexture[doors.get(i).doorTextureIndex],
							originX + doors.get(i).doorPosX
									* (width / originalWidth),
							originY + doors.get(i).doorPosY
									* (height / originalHeight),
							doors.get(i).doorWidth * (width / originalWidth),
							doors.get(i).doorHeight * (height / originalHeight));

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

				long id = earnPointSound.play();
				earnPointSound.setVolume(id, actionsVolume);

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
							+ heroBullets.get(i).bulletWidth < 0
					|| heroBullets.get(i).bulletPosY
							+ heroBullets.get(i).bulletHeight < 0) {
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
						anims.add(new AnimationObject(
								anims.get(k).animationPosX,
								anims.get(k).animationPosY,
								anims.get(k).animationWidth,
								anims.get(k).animationHeight, 0, 0, anims
										.get(k).animationAtlasIndex
										+ NUMBER_OF_ANIM, false, 0.10f, false,
								1.0f, 0));
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
						anims.remove(k);
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

	}

	private void updateTimer() {
		if (!isGamePause) {
			GAME_TIME += Gdx.graphics.getDeltaTime();
			if (!isStageCompon && (GAME_TIME >= 60 && GAME_TIME <= 61)) {
				isStageCompon = true;
				GAME_TIME = 61.01f;
			}
			// float tempValue = GAME_TIME-(int)GAME_TIME;
			float tempBombCount = GAME_TIME >= 60 ? 0.0125f * (GAME_TIME / 60)
					: 0.006f;
			if ((GAME_TIME - (int) GAME_TIME) <= tempBombCount) {
				anims.add(new AnimationObject(Math.abs(new Random()
						.nextInt((int) width)), height, width / NUMBER_OF_COLS,
						width / NUMBER_OF_COLS, 0, -BOMB_SPEED_Y,
						NUMBER_OF_ANIM - 1, false, 0.10f, true, 1.0f, 0));
			}
		}
		// System.out.println(GAME_TIME);
	}

	private void updateComponEffect() {

		if (!isGamePause && isCompon && componCounter > FRAME_PER_SECOND / 15) {
			componCounter = 0;
			if (originY >= 0) {
				originY -= COMPON;
				lastY -= COMPON;
			} else {
				originY += COMPON;
				lastY += COMPON;
			}
		}
		componCounter++;
	}

	private void drawEffects() {

		for (int i = 0; i < effects.size(); i++) {

			Color c = spriteBatch.getColor();
			spriteBatch.setColor(c.r, c.g, c.b, effects.get(i).effectAplha);

			spriteBatch.draw(effetsTexture[effects.get(i).effectTextureIndex],
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
							* (height / originalHeight), 1.0f, 1.0f, 0);
			spriteBatch.setColor(c.r, c.g, c.b, 1.0f);

			if (!isGamePause) {
				animFires.get(i).animationFirePosX += animFires.get(i).animationFireSpeedX;
				animFires.get(i).animationFirePosY += animFires.get(i).animationFireSpeedY;
			}
			if (!isGamePause
					&& !isGameOver
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
				hero = new Hero(0.15f, heroAtlasIndex + NUMBER_OF_HERO_OPTION,
						false);
				hero.isHeroDied = true;
				hero.heroPosX = tempPosX;
				hero.heroPosY = tempPosY;
				hero.heroAlpha = 1.0f;

				isGameOver = true;
				isGameWin = false;

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
			float scalX = 1.0f;
			if (anims.get(i).animationSpeedX >= 0) {
				scalX = -1.0f;
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
					if (new Random().nextInt(10) % 5 == 0
							&& anims.get(i).animationSpeedX != 0) {
						float posxTemp = anims.get(i).animationPosX;
						if (anims.get(i).animationSpeedX < 0) {
							posxTemp -= anims.get(i).animationWidth / 2;
						} else {
							posxTemp += anims.get(i).animationWidth;
						}
						animFires.add(new AnimationFireObject(posxTemp, anims
								.get(i).animationPosY
								+ anims.get(i).animationHeight / 2, anims
								.get(i).animationWidth / 2,
								anims.get(i).animationWidth / 2,
								anims.get(i).animationSpeedX, 0, 0, true,
								0.10f, true, 1.0f, 0));
					}
					anims.get(i).animationSpeedX = -anims.get(i).animationSpeedX;
					anims.get(i).animationWalkLength = anims.get(i).animationWalkLengthTemp;
				}
			}
			if (!anims.get(i).isAnimationLoop) {
				if (anims.get(i).animation
						.isAnimationFinished(anims.get(i).elapsedTime)) {
					anims.remove(i);
				} else {
					anims.get(i).animAplha -= 0.01f;
					if (anims.get(i).animationSpeedX == 0
							&& anims.get(i).animationSpeedX == 0) {

						if (!isGamePause
								&& !isGameOver
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
							hero = new Hero(0.15f, heroAtlasIndex
									+ NUMBER_OF_HERO_OPTION, false);
							hero.isHeroDied = true;
							hero.heroPosX = tempPosX;
							hero.heroPosY = tempPosY;
							hero.heroAlpha = 1.0f;
							float tempSwap = hero.heroWidth;
							hero.heroWidth = 0.8f * hero.heroHeight;
							hero.heroHeight = tempSwap;
							isGameOver = true;
							isGameWin = false;
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
					hero = new Hero(0.15f, heroAtlasIndex
							+ NUMBER_OF_HERO_OPTION, false);
					hero.isHeroDied = true;
					hero.heroPosX = tempPosX;
					hero.heroPosY = tempPosY;
					hero.heroAlpha = 1.0f;
					float tempSwap = hero.heroWidth;
					hero.heroWidth = 0.8f * hero.heroHeight;
					hero.heroHeight = tempSwap;
					isGameOver = true;
					isGameWin = false;
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
				anims.add(new AnimationObject(anims.get(i).animationPosX
						- anims.get(i).animationWidth / 2,
						anims.get(i).animationPosY
								- anims.get(i).animationWidth / 2, 2 * anims
								.get(i).animationWidth,
						2 * anims.get(i).animationWidth, 0, 0,
						anims.get(i).animationAtlasIndex + NUMBER_OF_ANIM,
						true, 0.05f, false, 1.0f, 0));
				anims.remove(i);
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
				jumpStep = 2;
				hero.heroT = 1.5f * hero.heroU / 9.8f;

			}
		}
	}

	private void drawControllers() {
		if (hero.heroAlpha != 1.5f && !isGameWin && !isGamePause) {
			spriteBatch.draw(handleBackgroundTexture, originalWidth / 4 - 3
					* originalWidth / 20, originalWidth / 20,
					originalWidth / 5, originalWidth / 10);
			if (isLeftArrow) {
				spriteBatch.draw(joystickTexture, 2 * originalWidth / 20,
						originalWidth / 20, originalWidth / 10,
						originalWidth / 10);
				isLeftArrow = false;
			} else if (isRightArrow) {
				spriteBatch.draw(joystickTexture, 2 * originalWidth / 20
						+ originalWidth / 10, originalWidth / 20,
						originalWidth / 10, originalWidth / 10);
				isRightArrow = false;
			} else {
				spriteBatch.draw(joystickTexture, 1.5f * originalWidth / 10,
						originalWidth / 20, originalWidth / 10,
						originalWidth / 10);
			}
			if (!isGamePause) {
				spriteBatch.draw(pauseButtonTexture, originalWidth / 40,
						originalHeight - 3 * originalWidth / 40,
						originalWidth / 20, originalWidth / 20);
			}
		}
	}

	private void touchInputs() {
		if (!isGamePause && !hero.isHeroDied && Gdx.input.isTouched()) {
			float getX = Gdx.input.getX();
			float getY = height - Gdx.input.getY();
			// originalWidth / 20, originalWidth / 20, originalWidth / 10,
			// originalWidth / 10
			if (!isGameOver && getX < originalWidth / 4 - originalWidth / 20
					&& getY < originalHeight / 2) {
				isLeftArrow = true;
				isHeroRight = false;
				hero.heroPosX -= hero.moveHorizontalIncrement;
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
						hero.heroPosX += hero.moveHorizontalIncrement;
					}
				}
			} else if (!isGameOver
					&& getX > originalWidth / 4 - originalWidth / 20
					&& getX < originalWidth / 2 - originalWidth / 20
					&& getY < originalHeight / 2) {
				// originalWidth / 20+originalWidth / 10, originalWidth / 20,
				// originalWidth / 10, originalWidth / 10
				isRightArrow = true;
				isHeroRight = true;
				hero.heroPosX += hero.moveHorizontalIncrement;
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
						hero.heroPosX -= hero.moveHorizontalIncrement;
					}
				}
			} else if ((!isGameOver && !isGamePause
					&& getX > originalWidth / 40
					&& getX < originalWidth / 40 + originalWidth / 20
					&& getY > originalHeight - 3 * originalWidth / 40 && getY < originalHeight
					- 3 * originalWidth / 40 + originalWidth / 20)) {
				pauseActionCallback();
			}
		}
	}

	private void pauseActionCallback() {

		// originalWidth/40, originalHeight - 3*originalWidth/40,
		// originalWidth / 20, originalWidth / 20;
		isGamePause = true;
		x = originalWidth / 5;
		y = originalHeight / 5;
		w = originalWidth - 2 * originalWidth / 5;
		h = originalHeight - 2 * originalHeight / 5;
		stage.clear();
		pauseButtonInit();
		gameStageBackgroundMusic.pause();

	}

	private void pauseButtonInit() {

		float buttonWide = 2.5f * w / 16;
		float buttonHeight = 2.5f * w / 16;

		addButton(playButtonTexture, x + w / 2 - 2 * buttonWide / 2
				- buttonWide, y + h / 4, buttonWide, buttonHeight,
				GameCode.GAME_PLAY);
		addButton(reloadButtonTexture, x + w / 2 - buttonWide / 2, y + h / 4,
				buttonWide, buttonHeight, GameCode.GAME_RELOAD);
		addButton(menuButtonTexture, x + w / 2 + buttonWide, y + h / 4,
				buttonWide, buttonHeight, GameCode.GAME_MENU);

	}

	private void failedButtonInit() {

		addButton(reloadButtonTexture, x + w / 2 - 1.25f * w / 5, y + h / 4,
				w / 5, w / 5, GameCode.GAME_RELOAD);
		addButton(menuButtonTexture, x + w / 2 + 0.25f * w / 5, y + h / 4,
				w / 5, w / 5, GameCode.GAME_MENU);

	}

	private void winButtonInit() {

		addButton(menuButtonTexture, x + w / 4, y, w / 8, w / 8,
				GameCode.GAME_MENU);
		addButton(reloadButtonTexture, x + w / 4 + w / 8 + w / 16, y, w / 8,
				w / 8, GameCode.GAME_RELOAD);
		addButton(nextButtonTexture, x + w / 4 + 2 * w / 8 + 2 * w / 16, y,
				w / 8, w / 8, GameCode.GAME_NEXT);

		addButton(facebookButtonTexture, x + 2 * w / 8, y + h / 6, w / 4,
				w / 8, GameCode.FACEBOOK_SHARE);
		addButton(twitterButtonTexture, x + 2 * w / 8 + w / 4, y + h / 6,
				w / 4, w / 8, GameCode.TWITTER_SHARE);

	}

	private void drawHero() {

		if (points.isEmpty()
				&& !isGamePause
				&& !isGameOver
				&& doors.size() >= 1
				&& CollisionCheck.isCollisionOccuredRectaPoint2x2(
						doors.get(0).doorPosX, doors.get(0).doorPosY,
						doors.get(0).doorPosX + doors.get(0).doorWidth,
						doors.get(0).doorPosY + doors.get(0).doorHeight,
						hero.heroPosX, hero.heroPosY, hero.heroPosX
								+ hero.heroWidth, hero.heroPosY
								+ hero.heroHeight)) {
			isGameOver = true;
			isGameWin = true;
			if (doors.get(0).doorTextureIndex == 104) {
				doors.get(0).doorTextureIndex = 103;
				w = originalHeight - originalHeight / 20;
				h = originalHeight - originalHeight / 20;
				x = (originalWidth - w) / 2;
				y = originalHeight / 40;

				if (stageIconTextureIndex + 1 < NUMBER_OF_STAGES
						&& gameStagePerWorldStatus[worldIconTextureIndex]
								.charAt(stageIconTextureIndex + 1) == '0') {
					String tempString = "";
					for (int i = 0; i < gameStagePerWorldStatus[worldIconTextureIndex]
							.length(); i++) {
						tempString += "1";
						if (gameStagePerWorldStatus[worldIconTextureIndex]
								.charAt(i) == '0') {
							break;
						}
					}
					while (tempString.length() < 10) {
						tempString += "0";
					}
					gameStagePerWorldStatus[worldIconTextureIndex] = tempString;
				}

				System.out
						.println(gameStagePerWorldStatus[worldIconTextureIndex]);
				FileHandlers.setGameData();
				starTemp = 0;
				stage.clear();
				winButtonInit();
				isStageCompon = false;

				if (gameHighScore > 0) {
					if (gameHighScore <= 20000 && gameHighScore > 0) {
						achievmentIndex = 0;
					} else if (gameHighScore <= 40000 && gameHighScore > 20000) {
						achievmentIndex = 1;
					} else if (gameHighScore <= 60000 && gameHighScore > 40000) {
						achievmentIndex = 2;
					} else if (gameHighScore >= 80000 && gameHighScore > 60000) {
						achievmentIndex = 3;
					} else if (gameHighScore > 80000) {
						achievmentIndex = 4;
					}
					actionResolver
							.unlockAchievementGPGS(achievmentIDs[achievmentIndex]);
				}

				if (noOfGamePlays >= 3) {
					actionResolver.submitTrophyGPGS(noOfGamePlays);
				}

				if (gameScore >= gameHighScore) {
					actionResolver.submitScoreGPGS(gameScore);
				}

				levelWinMusic.play();
				levelWinMusic.setVolume(musicVolume);
			}
		}
		float scalX = -1.0f;
		if (isHeroRight) {
			scalX = 1.0f;
		}
		if (!isGamePause) {
			hero.elapsedTime += Gdx.graphics.getDeltaTime();
		}

		if (!isGameWin) {
			spriteBatch.draw(hero.animation.getKeyFrame(hero.elapsedTime,
					hero.isHeroAnimationLoop), originX
					+ (hero.heroPosX - 0.3f * hero.heroWidth)
					* (width / originalWidth), originY
					+ (hero.heroPosY - 0.05f * hero.heroHeight)
					* (height / originalHeight), (1.6f * hero.heroWidth)
					* (width / originalWidth) / 2, (1.1f * hero.heroHeight)
					* (height / originalHeight) / 2, (1.6f * hero.heroWidth)
					* (width / originalWidth), (1.1f * hero.heroHeight)
					* (height / originalHeight), scalX, 1.0f, 0);

			if (GAME_TIME <= 1.5f) {
				spriteBatch
						.draw(hero.animationGlow.getKeyFrame(hero.elapsedTime,
								true),
								originX
										+ (hero.heroPosX - 0.3f
												* hero.heroWidth - 1.5f * 1.6f * hero.heroWidth)
										* (width / originalWidth),
								originY
										+ (hero.heroPosY - 0.05f
												* hero.heroHeight - 1.5f * 1.1f * hero.heroHeight)
										* (height / originalHeight),
								(4 * 1.6f * hero.heroWidth)
										* (width / originalWidth) / 2,
								(4 * 1.1f * hero.heroHeight)
										* (height / originalHeight) / 2,
								(4 * 1.6f * hero.heroWidth)
										* (width / originalWidth),
								(4 * 1.1f * hero.heroHeight)
										* (height / originalHeight), scalX,
								1.0f, 0);
			}
			if (isGameOver) {
				if (hero.heroAlpha >= 0 && hero.heroAlpha <= 1) {
					hero.heroAlpha -= 0.01f;
				} else if (hero.heroAlpha < 0) {
					stage.clear();
					failedButtonInit();
					isStageCompon = false;
					hero.heroAlpha = 1.5f;

					levelFailedMusic.play();
					levelFailedMusic.setVolume(musicVolume);
				}
			}

		}

		if (hero.heroPosX < 0) {
			hero.heroPosX = 0;
		} else if (hero.heroPosX + hero.heroWidth > width) {
			hero.heroPosX = width - hero.heroWidth;
		}
		if (hero.heroPosY < 0) {
			hero.heroPosY = 0;
			hero.isJumpState = false;
			jumpStep = 0;
		} else if (hero.heroPosY + hero.heroHeight > height) {
			hero.heroPosY -= hero.heroV;
			hero.heroT = hero.heroU / 9.8f;
		}
		if (!isGamePause && hero.isJumpState) {
			hero.heroV = hero.heroU - 9.8f * hero.heroT;
			hero.heroPosY += hero.heroV;
			hero.heroT += hero.heroTimeIncrement;

		}
	}

	private void drawStageComponents() {

		for (int i = 0; i < assets.size(); i++) {

			if (!isGamePause
					&& !hero.isHeroDied
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
				float tempPosX = hero.heroPosX;
				float tempPosY = hero.heroPosY;
				hero = new Hero(0.15f, heroAtlasIndex + NUMBER_OF_HERO_OPTION,
						false);
				hero.isHeroDied = true;
				isGameOver = true;
				isGameWin = false;
				hero.heroPosX = tempPosX;
				hero.heroPosY = tempPosY;
				hero.heroAlpha = 1.0f;
				x = originalWidth / 4;
				y = originalHeight / 4;
				w = originalWidth - 2 * originalWidth / 4;
				h = originalHeight - 2 * originalHeight / 4;
				float tempSwap = hero.heroWidth;
				hero.heroWidth = 0.8f * hero.heroHeight;
				hero.heroHeight = tempSwap;
				long id = heroDiedSound.play();
				heroDiedSound.setVolume(id, actionsVolume);

				break;

			}

			if (!isGamePause
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
					jumpStep = 0;
				}
				break;
			}

		}
		for (int i = 0; i < assets.size(); i++) {
			spriteBatch
					.draw(objetcsTexture[assets.get(i).assetTextureIndex],
							originX + assets.get(i).assetPosX
									* (width / originalWidth),
							originY + assets.get(i).assetPosY
									* (height / originalHeight),
							assets.get(i).assetWidth * (width / originalWidth),
							assets.get(i).assetHeight
									* (height / originalHeight));
		}
	}

	private void drawStageBackground() {
		spriteBatch.draw(gameWorldBackgrounds[worldIconTextureIndex], originX,
				originY, width, height);

		if (!isGamePause
				&& isStageCompon
				&& componCounter++ >= FRAME_PER_SECOND
						/ NUMBER_OF_COMPON_PER_SECOND) {
			componCounter = 0;
			if (originY + COMPON <= 0) {
				originY += COMPON;
				lastY += COMPON;
			} else {
				originY -= COMPON;
				lastY -= COMPON;
			}
		}
	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub

	}

	@Override
	public void show() {

		if (!gameStageBackgroundMusic.isPlaying()) {
			gameStageBackgroundMusic.play();
			gameStageBackgroundMusic.setVolume(musicVolume);
			gameStageBackgroundMusic.setLooping(true);
		} else {
			gameStageBackgroundMusic.setVolume(musicVolume);
			gameStageBackgroundMusic.setLooping(true);
		}

		achievmentIndex = 0;
		achievmentIDs = actionResolver.getAchievmentIDs();

		world = new GameWorld();

		stage = new Stage();

		controllerButtonInit();

		FRAME_PER_SECOND = (int) (1.0f / Gdx.graphics.getDeltaTime());

		isGameOver = false;
		isGameWin = false;
		isDoorOpen = false;
		isGamePause = false;
		jumpStep = 0;
		fiveStarScore = anims.size() * 500 + points.size() * 250;
		componCounter = 0;
		int rand = (new Random().nextInt(doors.size())) % doors.size();
		doors.add(0, doors.get(Math.abs(rand)));
		doors.remove(rand + 1);

		Gdx.input.setInputProcessor(stage);

		actionResolver.showOrLoadInterstital();

	}

	private void controllerButtonInit() {

		addButton(fireButtonTextures,
				originalWidth - 3.5f * originalWidth / 10, originalWidth / 160,
				1.5f * originalWidth / 10, 1.5f * originalWidth / 10,
				GameCode.HERO_BULLET);

		addButton(jumpButtonTextures, originalWidth - 2 * originalWidth / 10,
				3 * originalWidth / 40, 1.5f * originalWidth / 10,
				1.5f * originalWidth / 10, GameCode.JUMP_UP);

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
		// boolean isPressed = Gdx.input.isKeyPressed(Keys.SPACE);
		// if(Gdx.input.isKeyPressed(Keys.A)){
		// hero.heroPosX++;
		// }
		// else if(Gdx.input.isKeyPressed(Keys.DPAD_LEFT)){
		// hero.heroPosX--;
		// }
		// else if(Gdx.input.isKeyPressed(Keys.DPAD_DOWN)){
		// hero.heroPosY--;
		// }
		// else if(Gdx.input.isKeyPressed(Keys.DPAD_UP)){
		// hero.heroPosY++;
		// }
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

	}

}
