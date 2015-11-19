package com.anrosoft.mozbiemission.screen;

import com.anrosoft.mozbiemission.ActionResolver;
import com.anrosoft.mozbiemission.ZombieMission;
import com.anrosoft.mozbiemission.assets.GameAssets;
import com.anrosoft.mozbiemission.code.GameCode;
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

public class WorldScreen extends GameAssets implements Screen, InputProcessor {

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

	private float buttonMove;
	private boolean isTouchEnable;

	public WorldScreen(ZombieMission game, ActionResolver actionResolver) {

		this.game = game;
		spriteBatch = new SpriteBatch();
		this.actionResolver = actionResolver;

		animX = width / 2 - height / 4;
		animY = height / 2 - height / 4;
		animW = height / 2;
		animH = height / 2;
		animSpeedX = animW / 25;
		animSpeedY = animH / 25;
		isAnim = true;
		animDeltaX = animW;
		animDeltaY = animH;
		buttonMove = 0;
		isTouchEnable = false;

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

				long id = buttonClickSound.play();
				buttonClickSound.setVolume(id, clicksVolume);

				if (isTouchEnable && !isAnimWhite
						&& action.equals(GameCode.RITHG_ARROW)) {
					worldIconTextureIndex = (worldIconTextureIndex + 1)
							% NUMBER_OF_WORLDS;
					isAnim = true;
					animDeltaX = animW;
					animDeltaY = animH;
				} else if (isTouchEnable && !isAnimWhite
						&& action.equals(GameCode.LEFT_ARROW)) {
					if (worldIconTextureIndex > 0) {
						worldIconTextureIndex = worldIconTextureIndex - 1;
					} else {
						worldIconTextureIndex = NUMBER_OF_WORLDS - 1;
					}
					isAnim = true;
					animDeltaX = animW;
					animDeltaY = animH;
				} else if (isTouchEnable && !isAnimWhite
						&& action.equals(GameCode.BACK)) {
					gameAlpha = 1.0f;
					game.setScreen(game.menuScreen);
				} else if (isTouchEnable && !isAnimWhite
						&& action.equals(GameCode.GAME_STORE)) {
					gameAlpha = 1.0f;
					game.setScreen(game.storeScreen);
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
		} else if (buttonMove <= width / 2) {
			stage.clear();
			buttonInit(buttonMove);
			buttonMove += BUTTON_MOVE_INCEMENT;
		} else {
			isTouchEnable = true;
		}

		drawStageBackground();
		drawWorldIcon();
		drawWorldCaption();

		spriteBatch.setColor(c.r, c.g, c.b, 1.0f);

		spriteBatch.end();
		spriteBatch.disableBlending();

		stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
		stage.draw();
		Table.drawDebug(stage);

		touchInput();

	}

	private void drawWorldCaption() {

		String tempScore = worldCaption[worldIconTextureIndex];
		font.draw(spriteBatch, tempScore, width / 2
				- font.getBounds(tempScore).width / 2, 1.7f * (height / width)
				* buttonMove + font.getBounds(tempScore).height / 2);

	}

	private void touchInput() {

		if (Gdx.input.justTouched()) {
			// animX, animY, animW, animH
			float getX = Gdx.input.getX();
			float getY = Gdx.input.getY();
			if ((getX > animX && getX < animX + animW)
					&& (getY > animY && getY < animY + animH)) {
				if (isTouchEnable && !isAnimWhite && animDeltaX == 0
						&& gameWorldStatus.charAt(worldIconTextureIndex) == '1') {

					// reset state : StageScreen.class
					animX = width / 2 - height / 4;
					animY = height / 2 - height / 4;
					animW = height / 2;
					animH = height / 2;
					animSpeedX = animW / 25;
					animSpeedY = animH / 25;
					isAnim = true;
					animDeltaX = animW;
					animDeltaY = animH;
					buttonMove = 0;
					isTouchEnable = false;

					// newGame();
					// StageLoad.stageLoad(worldIconTextureIndex);

					game.setScreen(game.stageScreen);
				} else {
					// lock stage sound play
				}
			}
		}

	}

	private void drawWorldIcon() {

		if (isAnim) {
			spriteBatch.draw(worldIconTexture[worldIconTextureIndex], animX
					+ animDeltaX / 2, animY + animDeltaY / 2, animW
					- animDeltaX, animH - animDeltaY);
			animDeltaX -= animSpeedX;
			animDeltaY -= animSpeedY;
			if (animDeltaX <= 0 || animDeltaY <= 0) {
				isAnim = false;
				animDeltaX = 0;
				animDeltaY = 0;
			}
		} else {
			spriteBatch.draw(worldIconTexture[worldIconTextureIndex], animX,
					animY, animW, animH);
			if (gameWorldStatus.charAt(worldIconTextureIndex) == '0') {
				spriteBatch.draw(worldLockIconTexture, animX, animY, animW,
						animH);
			}
		}
	}

	private void drawStageBackground() {
		spriteBatch.draw(allBackgroundTexture, 0, 0, width, height);

	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub

	}

	@Override
	public void show() {

		world = new GameWorld();
		stage = new Stage();

		buttonMove = 0;
		isTouchEnable = false;
		worldIconTextureIndex = 0;
		animX = width / 2 - height / 4;
		animY = height / 2 - height / 4;
		animW = height / 2;
		animH = height / 2;
		animSpeedX = animW / 25;
		animSpeedY = animH / 25;
		isAnim = true;
		animDeltaX = animW;
		animDeltaY = animH;
		buttonMove = 0;
		isTouchEnable = false;

		Gdx.input.setInputProcessor(stage);
		actionResolver.showOrLoadInterstital();

	}

	private void buttonInit(float move) {

		addButton(leftArrowButtonTexture, -(width / 2 - move) + width / 6
				- height / 8, height / 2 - height / 8, height / 4, height / 4,
				GameCode.LEFT_ARROW);
		addButton(rightArrowButtonTexture, (width / 2 - move) + 5 * width / 6
				- height / 8, height / 2 - height / 8, height / 4, height / 4,
				GameCode.RITHG_ARROW);

		addButton(menuButtonTexture, width / 2 - move, 0, height / 6,
				height / 6, GameCode.BACK);
		addButton(storeButtonTexture, -(width / 2 - move) + width - height / 6,
				0, height / 6, height / 6, GameCode.GAME_STORE);

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
