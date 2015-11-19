package com.anisbulbul.car.race.screen;

import com.anisbulbul.car.race.ActionResolver;
import com.anisbulbul.car.race.CarRace3D;
import com.anisbulbul.car.race.assets.GameAssets;
import com.anisbulbul.car.race.controller.GameBackGroundController;
import com.anisbulbul.car.race.controller.GameWaterController;
import com.anisbulbul.car.race.files.FileHandlers;
import com.anisbulbul.car.race.model.GameWorld;
import com.anisbulbul.car.race.renderer.GameEndAnimation;
import com.anisbulbul.car.race.renderer.GameWorldRenderer;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class RaceScreen extends GameAssets implements Screen, InputProcessor {

	private GameWorld world;
	private GameWorldRenderer render;
	private GameBackGroundController backGroundController;
	private GameWaterController waterController;
	private CarRace3D game;
	private ActionResolver actionResolver;
	private float width;
	private float height;
	private SpriteBatch spriteBatch;

	private Texture speedMeterTexture;
	private Texture pasueScreenOverLayTexture;
	private Texture pasueTexture;
	private BitmapFont font2;

	private Texture pauseButtonTexture;

	private GameEndAnimation gameEndAnimation;
	private Texture scoreTexture;

	private Music raceMusic;
	private Music accidentMusic;
	private Music moveMusic;

	public RaceScreen(CarRace3D game, ActionResolver actionResolver) {

		this.game = game;
		this.actionResolver = actionResolver;
		width = Gdx.graphics.getWidth();
		height = Gdx.graphics.getHeight();
		spriteBatch = new SpriteBatch();
		gameEndAnimation = new GameEndAnimation(-width, 0, width, height,
				new Texture(Gdx.files.internal("data/gate.png")));
		loadComponents();
	}

	private void loadComponents() {

		font2 = initFont("fonts/abc.fnt", "fonts/abc.png");

		font2.setScale((width / font2.getBounds("A").width) / 22.0f,
						(height / font2.getBounds("A").height) / 30);

		raceMusic = initMusic("sounds/race_music.mp3");
		accidentMusic = initMusic("sounds/accident_music.mp3");

		pasueScreenOverLayTexture = initTexture("data/pausescreenoverlay.png");
		pasueTexture = initTexture("data/pause.png");

		pauseButtonTexture = initTexture("buttons/pausebutton.png");

		scoreTexture = initTexture("data/score.png");
		speedMeterTexture = initTexture("data/speedmeter.png");
		moveMusic = initMusic("sounds/move.mp3");
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0.1f, 0.1f, 0.1f, 1);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);

		backGroundController.update(delta);
		waterController.update(delta);

		if (GAME_STATE == GAME_FINISHED && gameEndAnimation.gateX == 0
				&& Gdx.input.justTouched()) {
			game.setScreen(game.scoreScreen);
		}
		if (GAME_STATE == GAME_START && !isGamePause) {
			float accelerometerX = Gdx.input.getAccelerometerX();
			if (accelerometerX > 3)
				accelerometerX = 3;
			else if (accelerometerX < -3)
				accelerometerX = -3;
			GameWorldRenderer.myCarX -= accelerometerX;
			myCarRotation = accelerometerX * 3;
			if (isGameSound) {
				moveMusic.setVolume(Math.abs(accelerometerX / 3));
			}
		}

		if (GAME_STATE == GAME_START
				&& (GameWorldRenderer.myCarX < width / 3.7 || GameWorldRenderer.myCarX > width
						- width / 3.7)) {
			GAME_STATE = GAME_ACCIDENT;
		}
		if (GAME_STATE == GAME_START && !isGamePause && Gdx.input.justTouched()) {
			float getX = Gdx.input.getX();
			float getY = height - Gdx.input.getY();
			// pauseButtonTexture, width-width/8, height-width/8, width/8,
			// width/8
			if (getX >= width - width / 8 && getX <= width
					&& getY >= height - width / 8 && getX <= height) {
				isGamePause = true;
				if (isGameSound) {
					raceMusic.pause();
				}
			}
		} else if (GAME_STATE == GAME_START && isGamePause
				&& Gdx.input.justTouched()) {
			isGamePause = false;
			if (isGameSound) {
				raceMusic.play();
			}
		}

		if (GAME_STATE == GAME_START && !isGamePause && Gdx.input.isTouched()) {

			if (backgroundSpeed < backgroundSpeedHighLimit) {
				backgroundSpeed += 0.4f;
			}
			if (carSpeed < carSpeedHighLimit) {
				carSpeed += 0.02f * 2.0;
			}

		} else if (GAME_STATE == GAME_START && !isGamePause) {
			if (backgroundSpeedLowLimit < backgroundSpeedHighLimit) {
				if (backgroundSpeed > backgroundSpeedLowLimit) {
					backgroundSpeed -= 0.5f;
				} else {
					backgroundSpeedLowLimit += 0.005f;
					backgroundSpeed += 0.005f;
				}
			}
			if (carSpeedLowLimit < carSpeedHighLimit) {
				if (carSpeed > carSpeedLowLimit) {
					carSpeed -= 0.5f * 0.125f;
				} else {
					carSpeedLowLimit += 0.003125f;
					carSpeed += 0.003125f;
				}
			}
		}
		if (GAME_STATE == GAME_START && isGameSound) {
			raceMusic.setVolume(backgroundSpeed / backgroundSpeedHighLimit);
		}
		if (GAME_STATE == GAME_ACCIDENT) {
			if (raceMusic.isPlaying()) {
				raceMusic.stop();
				moveMusic.stop();
			}
			if (isGameSound && backgroundSpeed > 0
					&& !accidentMusic.isPlaying()) {
				accidentMusic.play();
			}
			if (backgroundSpeed > 0) {
				backgroundSpeed -= 1.0f;
			} else {
				backgroundSpeed = 0;
			}
		}

		if (GAME_STATE == GAME_ACCIDENT) {
			gameEndDelay -= 0.5f;
		}
		if (gameEndDelay <= 0) {
			gameScoreIndex = FileHandlers.setHiScore((long) gameScore);
			if (raceMusic.isPlaying()) {
				raceMusic.stop();
			}
			if (accidentMusic.isPlaying()) {
				accidentMusic.stop();
			}
			gameEndDelay = 1;
			GAME_STATE = GAME_FINISHED;
		}

		render.render();

		spriteBatch.begin();
		spriteBatch.enableBlending();

		drawScore();
		drawSpeedMeter();
		drawPause();
		drawEndScreen();

		spriteBatch.end();
		spriteBatch.disableBlending();

	}

	private void drawEndScreen() {
		if (GAME_STATE == GAME_FINISHED) {
			spriteBatch.draw(gameEndAnimation.gateTexture,
					gameEndAnimation.gateX, gameEndAnimation.gateY,
					gameEndAnimation.gateW, gameEndAnimation.gateH);
			if (gameEndAnimation.gateX < 0) {
				gameEndAnimation.gateX += 25;
			} else {
				String scoreTemp = "SCORE : " + (int) gameScore;
				gameEndAnimation.gateX = 0;

				font2.draw(spriteBatch, scoreTemp,
						width / 2 - font2.getBounds(scoreTemp).width / 2, 3
								* height / 4
								+ font2.getBounds(scoreTemp).height / 2);

				if (gameScoreIndex < 0) {
					scoreTemp = "You have no position";
				} else {
					scoreTemp = "Your position : " + gameScoreIndex;
				}

				font2.draw(spriteBatch, scoreTemp,
						width / 2 - font2.getBounds(scoreTemp).width / 2,
						height / 2 + font2.getBounds(scoreTemp).height / 2);

				scoreTemp = "TAB TO RETURN";
				font2.draw(spriteBatch, scoreTemp,
						width / 2 - font2.getBounds(scoreTemp).width / 2,
						height / 4 + font2.getBounds(scoreTemp).height / 2);

			}
		}

	}

	private void drawSpeedMeter() {

		spriteBatch.draw(speedMeterTexture, width - width / 3, 0, width / 3,
				width / 3);

		String tempSpeed = ((int) (backgroundSpeed)) * 10 + "";
		font2.draw(spriteBatch, tempSpeed,
				11 * width / 12 - font2.getBounds(tempSpeed).width / 2, width
						/ 12 + font2.getBounds(tempSpeed).height / 2);

	}

	private void drawScore() {

		spriteBatch.draw(scoreTexture, 0, 0, width / 3, width / 3);

		String tempScore = (int) gameScore + "";
		font2.draw(spriteBatch, tempScore,
				width / 8 - font2.getBounds(tempScore).width / 2, width / 12
						+ font2.getBounds(tempScore).height / 2);

		if (GAME_STATE == GAME_START && !isGamePause) {
			gameScore += 0.5f * (backgroundSpeed / 5);
			if (gameScore % 1000 == 0 && gameScore != 0 && gameLevel < 5)
				gameLevel++;
		}

	}

	private void drawPause() {
		if (isGamePause) {
			spriteBatch.draw(pasueScreenOverLayTexture, 0, 0, width, height);
			spriteBatch.draw(pasueTexture, width / 2 - width / 4, height / 2
					- width / 8, width / 2, width / 4);
		} else {
			spriteBatch.draw(pauseButtonTexture, width - width / 8, height
					- width / 8, width / 8, width / 8);
		}
	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
		render.setSize(width, height);

	}

	@Override
	public void show() {

		gameEndAnimation.gateX = -width;

		if (isGameSound && !raceMusic.isPlaying()) {
			raceMusic.play();
			raceMusic.setLooping(true);
			raceMusic.setVolume(1);
		} else if (!isGameSound && raceMusic.isPlaying()) {
			raceMusic.stop();
		}

		if (isGameSound && !moveMusic.isPlaying()) {
			moveMusic.play();
			moveMusic.setLooping(true);
			moveMusic.setVolume(0);
		} else if (!isGameSound && moveMusic.isPlaying()) {
			moveMusic.stop();
		}

		world = new GameWorld();
		render = new GameWorldRenderer(world, true);
		backGroundController = new GameBackGroundController(world);
		waterController = new GameWaterController(world);

		Gdx.input.setInputProcessor(this);
		actionResolver.showOrLoadInterstital();

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
	public void dispose() {

		spriteBatch.dispose();
		speedMeterTexture.dispose();
		pasueScreenOverLayTexture.dispose();
		pasueTexture.dispose();
		font2.dispose();
		pauseButtonTexture.dispose();
		scoreTexture.dispose();

		if (raceMusic.isPlaying()) {
			raceMusic.stop();
		}
		raceMusic.dispose();
		if (accidentMusic.isPlaying()) {
			accidentMusic.stop();
		}
		accidentMusic.dispose();
		if (moveMusic.isPlaying()) {
			moveMusic.stop();
		}
		moveMusic.dispose();

	}

	@Override
	public boolean keyDown(int keycode) {
		// boolean isPressed = Gdx.input.isKeyPressed(Keys.SPACE);

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

}
