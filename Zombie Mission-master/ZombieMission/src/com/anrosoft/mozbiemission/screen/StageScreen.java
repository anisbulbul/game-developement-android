package com.anrosoft.mozbiemission.screen;

import com.anrosoft.mozbiemission.ActionResolver;
import com.anrosoft.mozbiemission.ZombieMission;
import com.anrosoft.mozbiemission.assets.GameAssets;
import com.anrosoft.mozbiemission.assets.Hero;
import com.anrosoft.mozbiemission.assets.StageLoad;
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

public class StageScreen extends GameAssets implements Screen, InputProcessor {

	private GameWorld world;

	private ZombieMission game;

	private SpriteBatch spriteBatch;

	private Stage stage;
	private Skin skin;

	private ActionResolver actionResolver;

	private float animDeltaX;
	private float animDeltaY;
	private float animSpeedX;
	private float animSpeedY;
	private boolean isAnim;

	private float animX;
	private float animY;
	private float animW;
	private float animH;

	public StageScreen(ZombieMission game, ActionResolver actionResolver) {

		this.game = game;
		spriteBatch = new SpriteBatch();
		this.actionResolver = actionResolver;

		animH = 16 * height / 20;
		animW = 1.5f * animH;
		if (animW > originalWidth) {
			animW = originalWidth;
		}
		animX = (originalWidth - animW) / 2;
		animY = (originalHeight - animH) / 2;

		animSpeedX = animW / 25;
		animSpeedY = animH / 25;
		isAnim = true;
		animDeltaX = animW;
		animDeltaY = animH;

	}

	public void addButton(Texture buttonTextures, float posX, float posY,
			float bWidth, float bHeight, final int stageIndex,
			final String action) {

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

				long id = buttonClickSound.play();
				buttonClickSound.setVolume(id, clicksVolume);

				if (action.equals(GameCode.CROSS)) {
					game.setScreen(game.worldScreen);
				} else if (action.equals(GameCode.GAME_PLAY)) {
					newGame();
					stageIconTextureIndex = stageIndex;
					StageLoad.stageLoad(worldIconTextureIndex,
							stageIconTextureIndex);
					noOfGamePlays++;
					System.out.println(heroAtlasIndex);
					hero = new Hero(0.15f, heroAtlasIndex, true);
					FileHandlers.setGameData();
					backgroundMusic.stop();
					gameStageBackgroundMusic.play();
					gameStageBackgroundMusic.setVolume(musicVolume);
					gameStageBackgroundMusic.setLooping(true);
					GAME_STATE = GAME_PLAY;
					game.setScreen(game.gameScreen);
				} else if (action.equals(GameCode.GAME_LOCK)) {
					//
				} else if (action.equals(GameCode.GAME_STORE)) {
					backgroundMusic.stop();
					gameStageBackgroundMusic.play();
					gameStageBackgroundMusic.setVolume(musicVolume);
					gameStageBackgroundMusic.setLooping(true);
					game.setScreen(game.storeScreen);
				} else if (action.equals(GameCode.GAME_MENU)) {
					backgroundMusic.stop();
					gameStageBackgroundMusic.play();
					gameStageBackgroundMusic.setVolume(musicVolume);
					gameStageBackgroundMusic.setLooping(true);
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

		Color c = spriteBatch.getColor();

		if (isAnimWhite) {
			if (gameAlpha >= 1.0f) {
				gameAlpha = 1.0f;
				isAnimWhite = false;
			}
			spriteBatch.setColor(c.r, c.g, c.b, gameAlpha);
			gameAlpha += 0.01f;
		}

		drawStageBackground();
		drawStagePlate();

		spriteBatch.setColor(c.r, c.g, c.b, 1.0f);

		spriteBatch.end();
		spriteBatch.disableBlending();

		stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
		stage.draw();
		Table.drawDebug(stage);

		touchInput();

	}

	private void touchInput() {

		if (Gdx.input.justTouched()) {

			float getX = Gdx.input.getX();
			float getY = Gdx.input.getY();

		}

	}

	private void drawStagePlate() {

		if (isAnim) {
			spriteBatch.draw(stageBoardTexture, animX + animDeltaX / 2, animY
					+ animDeltaY / 2, animW - animDeltaX, animH - animDeltaY);
			stage.clear();
			buttonInit();
			animDeltaX -= animSpeedX;
			animDeltaY -= animSpeedY;
			if (animDeltaX <= 0 || animDeltaY <= 0) {
				isAnim = false;
				// animDeltaX = 0;
				// animDeltaY = 0;
				stage.clear();
				buttonInit();
			}
		} else {
			spriteBatch.draw(stageBoardTexture, animX, animY, animW, animH);
			if (gameWorldStatus.charAt(worldIconTextureIndex) == '0') {
				spriteBatch.draw(worldLockIconTexture, animX, animY, animW,
						animH);
			}
		}
	}

	private void buttonInit() {
		stage.clear();
		addButton(crossButtonTextures, (animX + animDeltaX / 2)
				+ (animW - animDeltaX) - 3 * (animH - animDeltaY) / 20,
				(animY + animDeltaY / 2) + (animH - animDeltaY) - 5
						* (animH - animDeltaY) / 20, (animH - animDeltaY) / 5,
				(animH - animDeltaY) / 5, -1, GameCode.CROSS);

		addButton(storeButtonTexture, (animX + animDeltaX / 2) + 7
				* (animW - animDeltaX) / 12 + (animW - animDeltaX) / 10,
				(animY + animDeltaY / 2) - (animH - animDeltaY) / 16,
				(animH - animDeltaY) / 6, (animH - animDeltaY) / 6, -1,
				GameCode.GAME_STORE);

		addButton(menuButtonTexture, (animX + animDeltaX / 2) + 1.5f
				* (animW - animDeltaX) / 12 + (animW - animDeltaX) / 10,
				(animY + animDeltaY / 2) - (animH - animDeltaY) / 16,
				(animH - animDeltaY) / 6, (animH - animDeltaY) / 6, -1,
				GameCode.GAME_MENU);

		int j = 0;
		for (int k = 1; k >= 0; k--) {
			for (int i = 0; i < 9; i++) {
				if (i % 2 == 0) {
					// animX+animDeltaX/2, animY+animDeltaY/2, animW-animDeltaX,
					// animH-animDeltaY
					if (gameStagePerWorldStatus[worldIconTextureIndex]
							.charAt(j) == '1') {
						addButton(stageIconTexture, (animX + animDeltaX / 2)
								+ i * (animW - animDeltaX) / 12
								+ (animW - animDeltaX) / 10,
								(animY + animDeltaY / 2) + (k + 1)
										* (animH - animDeltaY) / 4
										- (animH - animDeltaY) / 16,
								(animH - animDeltaY) / 6,
								1.5f * (animH - animDeltaY) / 6, j,
								GameCode.GAME_PLAY);
					} else {
						addButton(stageLockIconTexture,
								(animX + animDeltaX / 2) + i
										* (animW - animDeltaX) / 12
										+ (animW - animDeltaX) / 10,
								(animY + animDeltaY / 2) + (k + 1)
										* (animH - animDeltaY) / 4
										- (animH - animDeltaY) / 16,
								(animH - animDeltaY) / 6,
								1.5f * (animH - animDeltaY) / 6, j,
								GameCode.GAME_LOCK);
					}
					j++;
				}
			}
		}
	}

	private void drawStageBackground() {
		spriteBatch.draw(stageBackgroundTexture, 0, 0, width, height);

	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub

	}

	@Override
	public void show() {

		if (!backgroundMusic.isPlaying()) {
			backgroundMusic.play();
			backgroundMusic.setVolume(musicVolume);
			backgroundMusic.setLooping(true);
		} else {
			backgroundMusic.setVolume(musicVolume);
		}

		world = new GameWorld();
		stage = new Stage();

		animH = 16 * height / 20;
		animW = 1.5f * animH;
		if (animW > originalWidth) {
			animW = originalWidth;
		}
		animX = (originalWidth - animW) / 2;
		animY = (originalHeight - animH) / 2;

		animSpeedX = animW / 25;
		animSpeedY = animH / 25;
		isAnim = true;
		animDeltaX = animW;
		animDeltaY = animH;

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
