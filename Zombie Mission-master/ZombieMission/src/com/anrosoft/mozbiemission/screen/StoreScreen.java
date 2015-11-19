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

public class StoreScreen extends GameAssets implements Screen, InputProcessor {

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
	private boolean isItemSelectEnable;
	private static int heroAtlasIndexLocal;
	private float x, y, w, h;

	public StoreScreen(ZombieMission game, ActionResolver actionResolver) {

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
		x = 0;
		y = 0;
		w = 0;
		h = 0;
		isItemSelectEnable = false;
		heroAtlasIndexLocal = heroAtlasIndex;

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

				if (action.equals(GameCode.BUY_HERO)) {

					heroStoreStatus = heroStoreStatus.substring(0,
							heroAtlasIndexLocal)
							+ "1"
							+ heroStoreStatus
									.substring(heroAtlasIndexLocal + 1);
					gameTotalScore -= (heroAtlasIndexLocal + 1) * 500;

					FileHandlers.setGameData();

					stage.clear();
					isItemSelectEnable = false;
					arrowButtonInit(buttonMove);

				} else if (action.equals(GameCode.BUY_CANCEL)) {
					stage.clear();
					isItemSelectEnable = false;
					arrowButtonInit(buttonMove);
				} else if (isTouchEnable && !isAnimWhite
						&& action.equals(GameCode.RITHG_ARROW)) {
					heroAtlasIndexLocal = (heroAtlasIndexLocal + 1)
							% NUMBER_OF_HERO_OPTION;
					isAnim = true;
					animDeltaX = animW;
					animDeltaY = animH;
				} else if (isTouchEnable && !isAnimWhite
						&& action.equals(GameCode.LEFT_ARROW)) {
					if (heroAtlasIndexLocal > 0) {
						heroAtlasIndexLocal = heroAtlasIndexLocal - 1;
					} else {
						heroAtlasIndexLocal = NUMBER_OF_HERO_OPTION - 1;
					}
					isAnim = true;
					animDeltaX = animW;
					animDeltaY = animH;
				} else if (isTouchEnable && !isAnimWhite
						&& action.equals(GameCode.GAME_MENU)) {
					gameAlpha = 1.0f;
					game.setScreen(game.menuScreen);
				} else if (isTouchEnable && !isAnimWhite
						&& action.equals(GameCode.GAME_PLAY)) {
					gameAlpha = 1.0f;
					game.setScreen(game.worldScreen);
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
			arrowButtonInit(buttonMove);
			buttonMove += BUTTON_MOVE_INCEMENT;
		} else {
			isTouchEnable = true;
		}

		drawStageBackground();
		drawStageIcon();
		drawItemScreen();
		drawHeroCaption();

		spriteBatch.setColor(c.r, c.g, c.b, 1.0f);

		spriteBatch.end();
		spriteBatch.disableBlending();

		stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
		stage.draw();
		Table.drawDebug(stage);

		touchInput();

	}

	private void drawHeroCaption() {
		if (isItemSelectEnable == false) {
			String tempScore = heroCaption[heroAtlasIndexLocal];
			font.draw(
					spriteBatch,
					tempScore,
					width / 2 - font.getBounds(tempScore).width / 2,
					1.7f * (height / width) * buttonMove
							+ font.getBounds(tempScore).height / 2);
		}
	}

	private void drawItemScreen() {

		if (isItemSelectEnable == true) {
			spriteBatch.draw(storeBoardTexture, x, y, w, h);
			spriteBatch.draw(heroIconTexture[heroAtlasIndexLocal], x + w / 8, y
					+ h - 11 * h / 16, w / 4, w / 4);

			String tempScore = "Need : " + (heroAtlasIndexLocal + 1) * 500;
			font.draw(spriteBatch, tempScore,
					x + 5 * w / 8 - font.getBounds(tempScore).width / 2, y + 7
							* h / 12 + font.getBounds(tempScore).height / 2);

			tempScore = "Earn : " + gameTotalScore;
			font.draw(spriteBatch, tempScore,
					x + 5 * w / 8 - font.getBounds(tempScore).width / 2, y + 3
							* h / 6 - font.getBounds(tempScore).height / 2);

		}

	}

	private void touchInput() {

		if (!isItemSelectEnable && isTouchEnable && Gdx.input.justTouched()) {
			// animX, animY, animW, animH
			float getX = Gdx.input.getX();
			float getY = height - Gdx.input.getY();
			if ((getX > animX && getX < animX + animW)
					&& (getY > animY && getY < animY + animH)) {
				if (!isAnimWhite && animDeltaX == 0
						&& heroStoreStatus.charAt(heroAtlasIndexLocal) == '0') {

					stage.clear();
					x = originalWidth / 8;
					y = originalHeight / 8;
					w = originalWidth - 2 * originalWidth / 8;
					h = originalHeight - 2 * originalHeight / 8;
					isItemSelectEnable = true;
					isTouchEnable = false;
					if ((heroAtlasIndexLocal + 1) * 500 > gameTotalScore) {
						buyNotPossibleButtonInit();
					} else {
						buyButtonInit();
					}
				} else if (!isAnimWhite && animDeltaX == 0) {
					if (heroStoreStatus.charAt(heroAtlasIndexLocal) == '1') {
						heroAtlasIndex = heroAtlasIndexLocal;
						FileHandlers.setGameData();
					}
					game.setScreen(game.worldScreen);
				}
			}
		}

	}

	private void drawStageIcon() {

		if (isAnim) {
			spriteBatch.draw(heroIconTexture[heroAtlasIndexLocal], animX
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
			spriteBatch.draw(heroIconTexture[heroAtlasIndexLocal], animX,
					animY, animW, animH);
			if (heroStoreStatus.charAt(heroAtlasIndexLocal) == '0') {
				spriteBatch
						.draw(heroBuyIconTexture, animX, animY, animW, animH);
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

		if (!backgroundMusic.isPlaying()) {
			backgroundMusic.play();
			backgroundMusic.setVolume(musicVolume);
			backgroundMusic.setLooping(true);
		} else {
			backgroundMusic.setVolume(musicVolume);
		}

		world = new GameWorld();

		stage = new Stage();

		worldIconTextureIndex = 0;
		gameAlpha = 1.0f;

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
		x = 0;
		y = 0;
		w = 0;
		h = 0;
		isItemSelectEnable = false;
		heroAtlasIndexLocal = heroAtlasIndex;
		Gdx.input.setInputProcessor(stage);
		actionResolver.showOrLoadInterstital();

	}

	private void arrowButtonInit(float move) {

		addButton(leftArrowButtonTexture, -(width / 2 - move) + width / 6
				- height / 8, height / 2 - height / 8, height / 4, height / 4,
				GameCode.LEFT_ARROW);
		addButton(rightArrowButtonTexture, (width / 2 - move) + 5 * width / 6
				- height / 8, height / 2 - height / 8, height / 4, height / 4,
				GameCode.RITHG_ARROW);

		addButton(menuButtonTexture, width / 2 - move, 0, height / 6,
				height / 6, GameCode.GAME_MENU);
		addButton(playButtonTexture, -(width / 2 - move) + width - height / 6,
				0, height / 6, height / 6, GameCode.GAME_PLAY);

	}

	private void buyButtonInit() {

		addButton(buyButtonTexture, x + w / 2 - w / 32 - w / 4, y + w / 16,
				w / 4, w / 8, GameCode.BUY_HERO);
		addButton(cancelButtonTexture, x + w / 2 + w / 32, y + w / 16, w / 4,
				w / 8, GameCode.BUY_CANCEL);

	}

	private void buyNotPossibleButtonInit() {

		addButton(okButtonTexture, x + w / 2 - w / 8, y + w / 16, w / 4, w / 8,
				GameCode.BUY_CANCEL);

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
