package com.anrosoft.mozbiemission.screen;

import com.anrosoft.mozbiemission.ActionResolver;
import com.anrosoft.mozbiemission.ZombieMission;
import com.anrosoft.mozbiemission.assets.GameAssets;
import com.anrosoft.mozbiemission.code.GameCode;
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

public class SoundScreen extends GameAssets implements Screen, InputProcessor {

	private GameWorld world;

	private ZombieMission game;

	private SpriteBatch spriteBatch;

	private Stage stage;
	private Skin skin;

	private ActionResolver actionResolver;

	private final float volumeControllProgressX[] = new float[4];
	private final float volumeControllProgressY[] = new float[4];
	private final float volumeControllProgressW[] = new float[4];
	private final float volumeControllProgressH[] = new float[4];

	private final float volumeControllIconX[] = new float[4];
	private final float volumeControllIconY[] = new float[4];
	private final float volumeControllIconW[] = new float[4];
	private final float volumeControllIconH[] = new float[4];

	private final String tempSoundCaption[] = { "MUSIC", "ACTION", "CLICKS",
			"VOLUME" };

	private final float fullVolume;
	private final float muteVolume;

	private float move;

	private boolean isGameQuitOption;

	private float quitScreenX;
	private float quitScreenY;
	private float quitScreenW;
	private float quitScreenH;

	public SoundScreen(ZombieMission game, ActionResolver actionResolver) {

		this.game = game;
		spriteBatch = new SpriteBatch();
		this.actionResolver = actionResolver;

		fullVolume = height - height / 8 - width / 12;
		muteVolume = 2 * height / 8;

		for (int i = 0; i < 4; i++) {
			volumeControllProgressX[i] = volumeControllIconX[i] = 5 * width
					/ 24 + (i * 2) * width / 12;
			volumeControllProgressY[i] = 2 * height / 8;
			volumeControllProgressW[i] = volumeControllIconW[i] = volumeControllIconH[i] = width / 12;
			volumeControllProgressH[i] = 5 * height / 8;
			// volumeControllIconY[i] = (i + 2.6f) * height / 8;
		}
		volumeControllIconY[0] = musicVolume * (fullVolume - muteVolume)
				+ muteVolume;
		volumeControllIconY[1] = actionsVolume * (fullVolume - muteVolume)
				+ muteVolume;
		volumeControllIconY[2] = clicksVolume * (fullVolume - muteVolume)
				+ muteVolume;
		volumeControllIconY[3] = allVolume * (fullVolume - muteVolume)
				+ muteVolume;

		quitScreenX = 0;
		quitScreenY = 0;
		quitScreenW = 0;
		quitScreenH = 0;

		isGameQuitOption = false;

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
				System.out.println("quit");
				if (isGameSound) {
					buttonClickSound.play();
				}
				if (action.equals(GameCode.QUIT)) {
					isGameQuitOption = true;
					stage.clear();
					quitScreenH = height - 4 * gaps;
					quitScreenW = width - 6 * gaps;
					quitScreenX = (width - quitScreenW) / 2;
					quitScreenY = (height - quitScreenH) / 2;
					quitButtonInit();
					backgroundMusic.pause();
				} else if (action.equals(GameCode.GAME_MENU)) {
					gameAlpha = 1.0f;
					FileHandlers.setGameData();
					game.setScreen(game.menuScreen);
				} else if (action.equals(GameCode.YES)) {
					isBackButtonEnable = false;
					Gdx.app.exit();
				} else if (action.equals(GameCode.NO)) {
					isGameQuitOption = false;
					stage.clear();
					buttonInit();
					backgroundMusic.play();
					backgroundMusic.setVolume(musicVolume);
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

		Color c = spriteBatch.getColor();

		if (isAnimWhite) {
			if (gameAlpha >= 1.0f) {
				gameAlpha = 1.0f;
				isAnimWhite = false;
			}
			spriteBatch.setColor(c.r, c.g, c.b, gameAlpha);
			gameAlpha += 0.01f;
		}

		drawSoundScreenBackground();
		drawSoundOptions();
		drawQuitScreen();
		touchInput();

		spriteBatch.setColor(c.r, c.g, c.b, 1.0f);

		spriteBatch.end();
		spriteBatch.disableBlending();

		stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
		stage.draw();
		Table.drawDebug(stage);

	}

	private void drawQuitScreen() {
		if (isGameQuitOption) {
			spriteBatch.draw(screenOverTextures, 0, 0, width, height);
			spriteBatch.draw(quitScreenBackgroundTextures, quitScreenX,
					quitScreenY, quitScreenW, quitScreenH);
		}
	}

	private void drawSoundOptions() {

		for (int i = 0; i < 4; i++) {
			spriteBatch.draw(volumeProgressTexture[i],
					volumeControllProgressX[i], move
							+ volumeControllProgressY[i],
					volumeControllProgressW[i], volumeControllProgressH[i]);

			spriteBatch.draw(volumeIconTexture[i], volumeControllIconX[i], move
					+ volumeControllIconY[i], volumeControllIconW[i],
					volumeControllIconH[i]);

		}

		musicVolume = (volumeControllIconY[0] - muteVolume)
				/ (fullVolume - muteVolume);
		actionsVolume = (volumeControllIconY[1] - muteVolume)
				/ (fullVolume - muteVolume);
		clicksVolume = (volumeControllIconY[2] - muteVolume)
				/ (fullVolume - muteVolume);
		allVolume = (volumeControllIconY[3] - muteVolume)
				/ (fullVolume - muteVolume);

		String tempVolumeValue[] = { "" + (int) (100 * musicVolume),
				"" + (int) (100 * actionsVolume),
				"" + (int) (100 * clicksVolume), "" + (int) (100 * allVolume) };
		for (int i = 0; i < 4; i++) {
			font.draw(
					spriteBatch,
					tempSoundCaption[i],
					3 * width / 12 + i * width / 6
							- font.getBounds(tempSoundCaption[i]).width / 2,
					-move + height / 8
							- font.getBounds(tempSoundCaption[i]).height / 2);
			font.draw(spriteBatch, tempVolumeValue[i], 3 * width / 12 + i
					* width / 6 - font.getBounds(tempVolumeValue[i]).width / 2,
					-move + height / 8 + 3
							* font.getBounds(tempVolumeValue[i]).height / 2);
		}

		if (move > 0) {
			move -= move / 20 > 1 ? move / 20 : width / 300;
			stage.clear();
			buttonInit();
		} else if (move < 0) {
			move = 0;
			stage.clear();
			buttonInit();
		}

	}

	private void touchInput() {

		if (!isGameQuitOption && move == 0 && Gdx.input.isTouched()) {
			float getX = Gdx.input.getX();
			float getY = height - Gdx.input.getY();
			for (int i = 0; i < 4; i++) {
				if (getX >= volumeControllIconX[i]
						&& getY >= volumeControllIconY[i]
						&& getX <= volumeControllIconX[i]
								+ volumeControllIconW[i]
						&& getY <= volumeControllIconY[i]
								+ volumeControllIconH[i]) {
					if (i == 3) {
						for (int k = 0; k < 4; k++) {
							volumeControllIconY[3] = getY
									- volumeControllIconH[k] / 2;
							if (volumeControllIconY[3] > fullVolume) {
								volumeControllIconY[3] = fullVolume;
							} else if (volumeControllIconY[3] < muteVolume) {
								volumeControllIconY[3] = muteVolume;
							}
							if (volumeControllIconY[k] > volumeControllIconY[3]) {
								volumeControllIconY[k] = volumeControllIconY[3];
							}
						}
					} else {
						if (volumeControllIconY[i] > volumeControllIconY[3]) {
							volumeControllIconY[3] = volumeControllIconY[i];
						}
						volumeControllIconY[i] = getY - volumeControllIconH[i]
								/ 2;
					}
				}

				if (volumeControllIconY[i] > fullVolume) {
					volumeControllIconY[i] = fullVolume;
				} else if (volumeControllIconY[i] < muteVolume) {
					volumeControllIconY[i] = muteVolume;
				}
			}
			backgroundMusic.setVolume(musicVolume);
			gameStageBackgroundMusic.setVolume(musicVolume);
		}
	}

	private void drawSoundScreenBackground() {
		spriteBatch.draw(allBackgroundTexture, 0, 0, width, height);
	}

	private void buttonInit() {

		addButton(menuButtonTexture, move, 0, height / 6, height / 6,
				GameCode.GAME_MENU);
		addButton(crossButtonTextures, -move + width - height / 6, 0,
				height / 6, height / 6, GameCode.QUIT);

	}

	private void quitButtonInit() {
		addButton(yesButtonTexture, quitScreenX + quitScreenW / 2 - gaps / 2
				- 1.8f * BUTTON_WIDTH, quitScreenY + quitScreenH / 4
				- BUTTON_WIDTH / 2, 1.8f * BUTTON_WIDTH, BUTTON_WIDTH,
				GameCode.YES);
		addButton(noButtonTexture, quitScreenX + quitScreenW / 2 + gaps / 2,
				quitScreenY + quitScreenH / 4 - BUTTON_WIDTH / 2,
				1.8f * BUTTON_WIDTH, BUTTON_WIDTH, GameCode.NO);
	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub

	}

	@Override
	public void show() {

		world = new GameWorld();
		stage = new Stage();

		gameAlpha = 1.0f;
		move = width;

		volumeControllIconY[0] = musicVolume * (fullVolume - muteVolume)
				+ muteVolume;
		volumeControllIconY[1] = actionsVolume * (fullVolume - muteVolume)
				+ muteVolume;
		volumeControllIconY[2] = clicksVolume * (fullVolume - muteVolume)
				+ muteVolume;
		volumeControllIconY[3] = allVolume * (fullVolume - muteVolume)
				+ muteVolume;

		quitScreenX = 0;
		quitScreenY = 0;
		quitScreenW = 0;
		quitScreenH = 0;

		isGameQuitOption = false;

		Gdx.input.setInputProcessor(stage);
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

	@Override
	public void dispose() {

	}

}
