package com.anrosoft.jumpinghero.screen;

import java.util.Random;

import com.anrosoft.jumpinghero.ActionResolver;
import com.anrosoft.jumpinghero.JumpingHero;
import com.anrosoft.jumpinghero.assets.Category;
import com.anrosoft.jumpinghero.assets.Effect;
import com.anrosoft.jumpinghero.assets.GameAssets;
import com.anrosoft.jumpinghero.assets.TextEffect;
import com.anrosoft.jumpinghero.code.GameCode;
import com.anrosoft.jumpinghero.collision.CollisionCheck;
import com.anrosoft.jumpinghero.files.FileHandlers;
import com.anrosoft.jumpinghero.model.GameWorld;
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

	private JumpingHero game;

	private SpriteBatch spriteBatch;
	public static Stage stage;
	public static Skin skin;

	private ActionResolver actionResolver;

	private int tempScoreIncrement;
	private float x, y, w, h;
	private int starTemp;
	private float fiveStarScore;

	private float partWidth;
	private float maxHeight;
	private float minHeight;
	private float minWidth;
	private float op = 0;
	private float speedX;

	private boolean isJumpPosible;
	private boolean isLyingSoundEnable;
	private float scalX;
	private float gameScoreTemp;

	public GameScreen(JumpingHero game, ActionResolver actionResolver) {

		this.game = game;
		spriteBatch = new SpriteBatch();
		this.actionResolver = actionResolver;
		isRightArrow = isRightArrow = false;
		isHeroRight = true;
		isGameOver = false;
		isGamePause = false;
		isJumpPosible = false;
		isJumpAtleastOne = false;
		isLyingSoundEnable = true;
		tempScoreIncrement = 0;
		starTemp = 0;
		fiveStarScore = 0;
		gameScoreTemp = 0;
		currentSpeedY = 1;

		partWidth = 0;
		partWidth = 0;

		maxHeight = 4 * height / 25;
		minHeight = height / 25;
		minWidth = 1.5f * height / 25;

		speedX = 0;
		scalX = 1.0f;

		op = 2;
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

				if (action.equals(GameCode.GAME_RELOAD)) {
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

					hero.heroPosX = assets.get(0).assetPosX
							+ assets.get(0).assetWidth / 2;
					hero.heroPosY = assets.get(0).assetPosY
							+ assets.get(0).assetHeight;
					assets.get(0).isHeroJumpBY = true;
				} else if (action.equals(GameCode.GAME_MENU)) {
					boolean isHigh = FileHandlers.setGameData();
					if (isHigh) {
						actionResolver.submitScoreGPGS(gameHighScore);
					}
					stage.clear();
					controllerButtonInit();
					newGame();
					gameStageBackgroundMusic.pause();
					game.setScreen(game.menuScreen);
				} else if (!isGamePause && !isGameOver
						&& action.equals(GameCode.GAME_PAUSE)) {
					pauseActionCallback();
					scoreBoardMove = height / 2;
				} else if (action.equals(GameCode.GAME_CONITNUE)) {
					stage.clear();
					controllerButtonInit();
					isGamePause = false;
					scoreBoardMove = 1;

					gameStageBackgroundMusic.play();
					gameStageBackgroundMusic.setLooping(true);
					gameStageBackgroundMusic.setVolume(musicVolume / 2);

				}
			}
		});

	}

	private void pauseActionCallback() {
		stage.clear();
		x = originalWidth / 5;
		y = originalHeight / 5;
		w = originalWidth - 2 * originalWidth / 5;
		h = originalHeight - 2 * originalHeight / 5;
		pauseButtonInit();
		isGamePause = true;
		gameStageBackgroundMusic.pause();
	}

	@Override
	public void render(float delta) {

		Gdx.gl.glClearColor(0.1f, 0.1f, 0.1f, 1);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);

		spriteBatch.begin();
		spriteBatch.enableBlending();

		touchInputs();

		drawStageBackground();
		if (GAME_STATE.equals(GAME_START)) {
			spriteBatch.draw(screenOverTexture, 0, 0, width, height);
		}
		drawStageComponents();
		if (!isJumpAtleastOne) {
			drawInstructionJump();
		}
		drawHero();
		drawEffects();
		drawTextEffects();
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

	private void drawTextEffects() {
		for (int i = 0; i < textEffects.size(); i++) {

			font3.draw(
					spriteBatch,
					textEffects.get(i).textEffectString,
					textEffects.get(i).textEffectPosX
							- font3.getBounds(textEffects.get(i).textEffectString).width
							/ 2,
					textEffects.get(i).textEffectPosY
							+ font3.getBounds(textEffects.get(i).textEffectString).height
							/ 2);

			if (!isGamePause) {
				textEffects.get(i).textEffectPosY += textEffects.get(i).textEffectSpeedY;
				textEffects.get(i).textEffectSpeedY *= 1.01f;
			}

		}
	}

	private void drawEffects() {
		for (int i = 0; i < effects.size(); i++) {

			Color c = spriteBatch.getColor();
			spriteBatch.setColor(c.r, c.g, c.b, effects.get(i).effectAplha);

			spriteBatch.draw(effetsTexture[effects.get(i).effectTextureIndex],
					effects.get(i).effectPosX - effects.get(i).effectWidth / 2,
					effects.get(i).effectPosY - effects.get(i).effectWidth / 2,
					effects.get(i).effectWidth, effects.get(i).effectHeight);

			if (!isGamePause) {
				effects.get(i).effectPosX += effects.get(i).effectSpeedX
						- effects.get(i).effectWidth * 0.025f / 2;
				effects.get(i).effectPosY += effects.get(i).effectSpeedY
						- effects.get(i).effectHeight * 0.025f / 2;
				effects.get(i).effectPosY -= height / 3000;
				effects.get(i).effectAplha -= 0.05f;
				effects.get(i).effectWidth += effects.get(i).effectWidth * 0.025f;
				effects.get(i).effectHeight += effects.get(i).effectHeight * 0.025f;
			}

			if (effects.get(i).effectAplha < 0.0f) {
				effects.remove(i);
			}
			spriteBatch.setColor(c.r, c.g, c.b, 1.0f);

		}
	}

	private void drawInstructionJump() {
		spriteBatch.draw(
				arrowsTexture,
				assets.get(currentSpring).assetPosX
						+ assets.get(currentSpring).assetWidth / 2 - 3
						* assets.get(currentSpring).assetWidth / 4 - 1.5f
						* arrowMove / 2, assets.get(currentSpring).assetPosY
						- arrowMove + assets.get(currentSpring).assetHeight
						- height / 5, 3 * assets.get(currentSpring).assetWidth
						/ 2 + 1.5f * arrowMove, 2
						* assets.get(currentSpring).assetWidth / 2 + arrowMove);
		arrowMove += 0.5f;
		if (arrowMove >= 30) {
			arrowMove = 0.0f;
		}

	}

	private void updateOthers() {
		if (isGamePauseAndroid) {
			isGamePauseAndroid = false;
			pauseActionCallback();
		}
		if (partWidth > 0 && partWidth > speedX) {
			speedX = partWidth;
		} else if (partWidth < 0 && partWidth < speedX) {
			speedX = partWidth;
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
			spriteBatch.draw(iceEffectTexture, iceEffect.get(i).effectPosX,
					iceEffect.get(i).effectPosY, iceEffect.get(i).effectWidth,
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

	private void drawDebug() {

		System.out.println("assets : " + assets.size());
		System.out.println("iceEffect : " + iceEffect.size());
	}

	private void drawGameOverScreen() {
		if (isGameOver && hero.heroAlpha == 1.5f) {
			spriteBatch.draw(screenOverTexture, 0, 0, width, height);
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

	private void updateTimer() {
		if (!isGamePause) {
			// GAME_TIME += Gdx.graphics.getDeltaTime();
			// if (!isStageCompon && (GAME_TIME >= 60 && GAME_TIME <= 61)) {
			// isStageCompon = true;
			// GAME_TIME = 61.01f;
			// }
			// float tempValue = GAME_TIME - (int) GAME_TIME;
			// float tempBombCount = GAME_TIME >= 60 ? 0.0125f * (GAME_TIME /
			// 60)
			// : 0.006f;
			//
		}
		// System.out.println(GAME_TIME);
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
				if (!isJumpAtleastOne) {
					isJumpAtleastOne = true;
				}
				hero.isJumpState = true;
				hero.heroTextureIndex = 1;
				hero.heroT = 1.5f * hero.heroU / 9.8f;

			}
		}
	}

	private void touchInputs() {

		if (op == 2 && !isGamePause && !hero.isHeroDied
				&& Gdx.input.isTouched()) {

			GAME_STATE = "runnig";

			float getX = Gdx.input.getX();
			float getY = height - Gdx.input.getY();

			if (!hero.isJumpState
					&& !assets.get(currentSpring).isAssetEnemy
					&& assets.get(currentSpring).assetTextureCategory
							.equals(Category.BLOCK)
					&& CollisionCheck.isCollisionOccuredRectaPoint2x1(
							assets.get(currentSpring).assetPosX,
							assets.get(currentSpring).assetPosY,
							assets.get(currentSpring).assetPosX
									+ assets.get(currentSpring).assetWidth,
							assets.get(currentSpring).assetPosY
									+ assets.get(currentSpring).assetHeight,
							getX, getY)) {

				gameScoreTemp = 0;

				if ((assets.get(currentSpring).assetHeight + (assets
						.get(currentSpring).assetPosY - getY + height / 50)) <= maxHeight
						&& (assets.get(currentSpring).assetHeight + (assets
								.get(currentSpring).assetPosY - getY + height / 50)) >= minHeight) {
					assets.get(currentSpring).assetHeight += (assets
							.get(currentSpring).assetPosY - getY + height / 50);
					assets.get(currentSpring).assetPosY = getY - height / 50;

				}

				if ((getX - assets.get(currentSpring).assetPosX - assets
						.get(currentSpring).assetWidth / 2) <= minWidth
						&& (getX - assets.get(currentSpring).assetPosX - assets
								.get(currentSpring).assetWidth / 2) >= -minWidth) {
					partWidth = getX - assets.get(currentSpring).assetPosX
							- assets.get(currentSpring).assetWidth / 2;
				}

				isJumpPosible = true;
				currentSpeedY = (assets.get(currentSpring).assetHeight - minHeight)
						/ (maxHeight - minHeight);
			}
		} else {
			op = 0;
			if (partWidth >= 1) {
				partWidth -= 1.0f;
			} else if (partWidth <= -1)
				partWidth += 1.0f;
			else {
				partWidth = 0;
				op++;
			}
			if (assets.get(currentSpring).assetHeight > minHeight) {
				float tempHeight = (assets.get(currentSpring).assetHeight - minHeight) / 2;
				assets.get(currentSpring).assetHeight -= tempHeight;
				assets.get(currentSpring).assetPosY += tempHeight;
				if (tempHeight < 1) {
					assets.get(currentSpring).assetHeight = minHeight;
				}
			} else
				op++;
			if (op == 2 && isJumpPosible) {
				hero.isJumpState = true;
				if (!isJumpAtleastOne) {
					isJumpAtleastOne = true;
				}
				hero.heroTextureIndex = 1;
				hero.heroU = hero.heroUInit * (1 + 2.5f * currentSpeedY);
				hero.heroT = 0;
				hero.heroV = hero.heroU + 9.8f * hero.heroT;
				isJumpPosible = false;
				op = 0;
			}
			// System.out.println(op);
		}
	}

	private void gameOverButtonInit() {

		addButton(reloadButtonTexture, x + w / 2 - 1.25f * BUTTON_WIDTH, y + h
				/ 4, BUTTON_WIDTH, BUTTON_WIDTH, GameCode.GAME_RELOAD);
		addButton(menuButtonTexture, x + w / 2 + 0.25f * BUTTON_WIDTH, y + h
				/ 4, BUTTON_WIDTH, BUTTON_WIDTH, GameCode.GAME_MENU);
	}

	private void pauseButtonInit() {

		float buttonWide = BUTTON_WIDTH;
		float buttonHeight = BUTTON_WIDTH;

		addButton(playButtonTexture, x + w / 2 - 2 * buttonWide / 2
				- buttonWide, y + h / 4, buttonWide, buttonHeight,
				GameCode.GAME_CONITNUE);
		addButton(reloadButtonTexture, x + w / 2 - buttonWide / 2, y + h / 4,
				buttonWide, buttonHeight, GameCode.GAME_RELOAD);
		addButton(menuButtonTexture, x + w / 2 + buttonWide, y + h / 4,
				buttonWide, buttonHeight, GameCode.GAME_MENU);

	}

	private void drawHero() {

		if (!isGameOver) {
			if (speedX < 0) {
				scalX = 1.0f;
			} else if (speedX > 0) {
				scalX = -1.0f;
			}

			spriteBatch.draw(herosTexture[hero.heroTextureIndex],
					hero.heroPosX, hero.heroPosY, hero.heroWidth / 2,
					hero.heroHeight / 2, hero.heroWidth, hero.heroHeight,
					scalX, 1.0f, 0);

		} else if (isGameOver) {
			if (hero.heroAlpha >= 0 && hero.heroAlpha <= 1) {
				hero.heroAlpha -= 0.01f;
			} else if (hero.heroAlpha < 0) {
				stage.clear();
				gameOverButtonInit();
				isStageCompon = false;
				hero.heroAlpha = 1.5f;
				gameOverSound.play();
				gameOverSound.setVolume(musicVolume);
			}
		}

		if (GAME_STATE.equals(GAME_START)) {
			//
		} else {

			if (hero.heroPosX < 0) {
				hero.heroPosX = 0;
			} else if (hero.heroPosX + hero.heroWidth > width) {
				hero.heroPosX = width - hero.heroWidth;
			}
			if (hero.heroPosY < 0) {
				hero.heroPosY = 0;
				hero.isJumpState = false;
				if (!hero.isHeroDied) {
					fallHeroSound.play(actionsVolume);
				}
				hero.heroTextureIndex = 0;
				heroDiedActionCallback();
			} else if (hero.heroPosY + hero.heroHeight > height) {
				hero.heroPosY -= hero.heroV;
				hero.heroT = hero.heroU / 9.8f;
			}
			if (!isGamePause && hero.isJumpState) {
				hero.heroV = hero.heroU - 9.8f * hero.heroT;
				if (hero.heroPosY + hero.heroV > height / 2) {
					hero.heroPosX -= speedX / MOVE_INCREMENT;

					int k = 0;
					for (k = 0; k < assets.size(); k++) {
						if (!assets.get(k).isAssetEnemy
								&& CollisionCheck
										.isCollisionOccuredRectaPoint2x2(
												assets.get(k).assetPosX,
												assets.get(k).assetPosY,
												assets.get(k).assetPosX
														+ assets.get(k).assetWidth,
												assets.get(k).assetPosY
														+ assets.get(k).assetHeight,
												hero.heroPosX, hero.heroPosY
														- hero.heroHeight / 10,
												hero.heroPosX + hero.heroWidth,
												hero.heroPosY + hero.heroHeight)) {

							break;

						}

					}
					if (k != assets.size()) {
						hero.heroPosX += speedX / MOVE_INCREMENT;
						// speedX = 0;
					}

					for (int i = 0; i < assets.size(); i++) {
						assets.get(i).assetPosY -= hero.heroV;
					}
					for (int i = 0; i < iceEffect.size(); i++) {
						iceEffect.get(i).effectPosY -= hero.heroV;
					}

					playBackgroundPosY -= hero.heroV / 3;
					if (hero.heroV > 0) {
						if (gameScoreTemp == 0) {
							isLyingSoundEnable = true;
							jumpSound.play(actionsVolume);
						}
						gameScoreTemp += hero.heroV;
					}

				} else {
					hero.heroPosY += hero.heroV;
					hero.heroPosX -= speedX / MOVE_INCREMENT;
					if (hero.heroV > 0) {
						if (hero.heroV > 0) {
							if (gameScoreTemp == 0) {
								isLyingSoundEnable = true;
								jumpSound.play(actionsVolume);
							}
						}
						gameScoreTemp += hero.heroV;
					}
					int i = 0;
					for (i = 0; i < assets.size(); i++) {
						if (!assets.get(i).isAssetEnemy
								&& CollisionCheck
										.isCollisionOccuredRectaPoint2x2(
												assets.get(i).assetPosX,
												assets.get(i).assetPosY,
												assets.get(i).assetPosX
														+ assets.get(i).assetWidth,
												assets.get(i).assetPosY
														+ assets.get(i).assetHeight,
												hero.heroPosX, hero.heroPosY
														- hero.heroHeight / 10,
												hero.heroPosX + hero.heroWidth,
												hero.heroPosY + hero.heroHeight)) {

							break;

						}

					}
					if (i != assets.size()) {
						hero.heroPosX += speedX / MOVE_INCREMENT;
						// speedX = 0;
					}
				}
				hero.heroT += hero.heroTimeIncrement;

			}
		}
	}

	private void drawStageComponents() {

		for (int i = 0; i < assets.size(); i++) {

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
					hero.heroTextureIndex = 0;
					speedX = 0;
					currentSpring = i;
					if (!assets.get(currentSpring).isHeroJumpBY) {
						float tempPoint = 100 * (1 + gameScoreTemp / height);
						gameScore += tempPoint;
						textEffects.add(new TextEffect(hero.heroPosX
								+ hero.heroWidth / 2, hero.heroPosY
								+ hero.heroHeight, 0, EFFECT_SPEED,
								(int) tempPoint + ""));
						earnPointSound.play(actionsVolume);
						assets.get(currentSpring).isHeroJumpBY = true;
						addLandEffects();
					} else {
						if (isLyingSoundEnable) {
							isLyingSoundEnable = false;
							addLandEffects();
							lyingHeroSound.play(actionsVolume);
						}
					}
				}
				break;
			}

		}

		for (int i = 0; i < assets.size(); i++) {

			float tempPartWidth = (i == currentSpring) ? partWidth : 0;
			spriteBatch.draw(springsPart[assets.get(i).assetTextureIndex],
					assets.get(i).assetPosX, assets.get(i).assetPosY,
					assets.get(i).assetWidth / 2 + tempPartWidth,
					assets.get(i).assetHeight);

			spriteBatch.draw(springsPart[assets.get(i).assetTextureIndex + 1],
					assets.get(i).assetPosX + assets.get(i).assetWidth / 2
							+ tempPartWidth, assets.get(i).assetPosY,
					assets.get(i).assetWidth / 2 - tempPartWidth,
					assets.get(i).assetHeight);

			spriteBatch.draw(springsPart[assets.get(i).assetTextureIndex + 2],
					assets.get(i).assetPosX - assets.get(i).assetWidth / 10,
					assets.get(i).assetPosY + assets.get(i).assetHeight
							+ height / 100, 12 * assets.get(i).assetWidth / 10,
					-assets.get(i).assetWidth / 10);

			if (assets.get(i).assetPosY + assets.get(i).assetHeight - 3
					* minHeight < 0) {

				int topValueIndex = 0;
				for (int k = 1; k < assets.size(); k++) {
					if (assets.get(topValueIndex).assetPosY < assets.get(k).assetPosY) {
						topValueIndex = k;
					}
				}

				assets.get(i).isHeroJumpBY = false;
				assets.get(i).assetPosY = height / 8
						+ assets.get(topValueIndex).assetPosY + height / 4;

				float tempPosX = assets.get(topValueIndex).assetPosX
						+ assets.get(topValueIndex).assetWidth / 2;
				tempPosX = Math
						.abs(tempPosX
								- (assets.get(i).assetPosX + assets.get(i).assetWidth / 2));
				if (tempPosX <= assets.get(topValueIndex).assetWidth / 2) {
					if (assets.get(topValueIndex).assetPosX
							+ assets.get(topValueIndex).assetWidth / 2 < assets
							.get(i).assetPosX + assets.get(i).assetWidth / 2) {
						assets.get(i).assetPosX += assets.get(i).assetWidth / 2;
					} else {
						assets.get(i).assetPosX -= assets.get(i).assetWidth / 2;
					}
				}
			}
		}
	}

	private void addLandEffects() {
		int effectsIndex = 0;

		effects.add(new Effect(hero.heroPosX + hero.heroWidth / 2,
				hero.heroPosY, hero.heroWidth / 2, hero.heroWidth / 2,
				-EFFECT_SPEED / squarRoot2, -EFFECT_SPEED / squarRoot2,
				effectsIndex, 1.0f));
		effects.add(new Effect(hero.heroPosX + hero.heroWidth / 2,
				hero.heroPosY, hero.heroWidth / 2, hero.heroWidth / 2,
				EFFECT_SPEED / squarRoot2, -EFFECT_SPEED / squarRoot2,
				effectsIndex, 1.0f));

		effects.add(new Effect(hero.heroPosX + hero.heroWidth / 2,
				hero.heroPosY, hero.heroWidth / 2, hero.heroWidth / 2, 0,
				-EFFECT_SPEED, effectsIndex, 1.0f));
		effects.add(new Effect(hero.heroPosX + hero.heroWidth / 2,
				hero.heroPosY, hero.heroWidth / 2, hero.heroWidth / 2,
				EFFECT_SPEED, 0, effectsIndex, 1.0f));
		effects.add(new Effect(hero.heroPosX + hero.heroWidth / 2,
				hero.heroPosY, hero.heroWidth / 2, hero.heroWidth / 2,
				-EFFECT_SPEED, 0, effectsIndex, 1.0f));
	}

	private void drawStageBackground() {

		if (playBackgroundPosY + 2 * playBackgroundHeight <= 0) {
			playBackgroundPosY += 2 * playBackgroundHeight;
		}

		spriteBatch.draw(playBackgroundTexture, playBackgroundPosX,
				playBackgroundPosY, playBackgroundWidth, playBackgroundHeight);

		spriteBatch.draw(playBackgroundTexture, playBackgroundPosX,
				playBackgroundPosY + 2 * playBackgroundHeight,
				playBackgroundWidth, -playBackgroundHeight);

		spriteBatch.draw(playBackgroundTexture, playBackgroundPosX,
				playBackgroundPosY + 2 * playBackgroundHeight,
				playBackgroundWidth, playBackgroundHeight);
	}

	public void heroDiedActionCallback() {

		float tempPosX = hero.heroPosX;
		float tempPosY = hero.heroPosY;
		hero.isHeroDied = true;
		hero.heroPosX = tempPosX;
		hero.heroPosY = tempPosY;

		isGameOver = true;

		float tempSwap = hero.heroWidth;
		hero.heroWidth = 0.8f * hero.heroHeight;
		hero.heroHeight = tempSwap;

		x = originalWidth / 4;
		y = originalHeight / 4;
		w = originalWidth - 2 * originalWidth / 4;
		h = originalHeight - 2 * originalHeight / 4;
	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub

	}

	@Override
	public void show() {

		if (!gameStageBackgroundMusic.isPlaying()) {
			gameStageBackgroundMusic.play();
			gameStageBackgroundMusic.setLooping(true);
			gameStageBackgroundMusic.setVolume(musicVolume / 2);
		}

		world = new GameWorld();

		stage = new Stage();

		controllerButtonInit();

		FRAME_PER_SECOND = (int) (1.0f / Gdx.graphics.getDeltaTime());

		scalX = 1.0f;

		isGameOver = false;
		isGamePause = false;
		isLyingSoundEnable = true;
		isJumpAtleastOne = false;
		hero.heroPosX = assets.get(0).assetPosX + assets.get(0).assetWidth / 2;
		hero.heroPosY = assets.get(0).assetPosY + assets.get(0).assetHeight;
		currentSpring = 0;

		scoreBoardMove = height / 2;
		currentSpeedY = 1;
		gameScoreTemp = 0;

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
	
	}

}
