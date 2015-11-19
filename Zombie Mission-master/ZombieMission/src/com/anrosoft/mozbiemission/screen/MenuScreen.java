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

public class MenuScreen extends GameAssets implements Screen, InputProcessor {

	private GameWorld world;

	private ZombieMission game;

	private SpriteBatch spriteBatch;
	public static Stage stage;
	public static Skin skin;

	public String[] achievmentIDs;
	public int achievmentIndex;

	private ActionResolver actionResolver;

	private float buttonMove;
	private boolean isTouchEnable;
	private boolean isPlay;
	private boolean isStore;
	private boolean isSoundOption;

	private float quitScreenX;
	private float quitScreenY;
	private float quitScreenW;
	private float quitScreenH;

	private boolean isGameQuitOption;

	public MenuScreen(ZombieMission game, ActionResolver actionResolver) {

		this.game = game;
		spriteBatch = new SpriteBatch();
		this.actionResolver = actionResolver;
		buttonMove = 0;
		isTouchEnable = true;
		isPlay = false;
		isSoundOption = false;
		isStore = false;

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

				long id = buttonClickSound.play();
				buttonClickSound.setVolume(id, clicksVolume);

				if (isTouchEnable && action.equals(GameCode.GAME_WORLD)) {
					isAnimDark = true;
					isTouchEnable = false;
					isPlay = true;
				} else if (isTouchEnable && action.equals(GameCode.GAME_STORE)) {
					isAnimDark = true;
					isTouchEnable = false;
					isStore = true;
				} else if (isTouchEnable
						&& action.equals(GameCode.GAME_SOUND_SETTING)) {
					isAnimDark = true;
					isTouchEnable = false;
					isSoundOption = true;
				} else if (isTouchEnable && action.equals(GameCode.SHARE)) {
					actionResolver.showShare();

				} else if (isTouchEnable && action.equals(GameCode.RATE)) {
					actionResolver.showRated();

				} else if (isTouchEnable && action.equals(GameCode.LEADERBOARD)) {
					actionResolver.getAllLeaderboardGPGS();

				} else if (isTouchEnable && action.equals(GameCode.ACHIEVEMENT)) {
					actionResolver.getAchievementsGPGS();

				} else if (isTouchEnable && action.equals(GameCode.QUIT)) {
					isGameQuitOption = true;
					stage.clear();
					quitScreenH = height - 4 * gaps;
					quitScreenW = width - 6 * gaps;
					quitScreenX = (width - quitScreenW) / 2;
					quitScreenY = (height - quitScreenH) / 2;
					backgroundMusic.pause();
					quitButtonInit();
				} else if (action.equals(GameCode.YES)) {
					isBackButtonEnable = false;
					Gdx.app.exit();
				} else if (action.equals(GameCode.NO)) {
					isGameQuitOption = false;
					stage.clear();
					buttonInit(0);
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

		if (isAnimDark) {
			if (gameAlpha < 0.5f) {
				gameAlpha = 0.5f;
				isAnimDark = false;
				isAnimWhite = true;
				if (isPlay) {
					game.setScreen(game.worldScreen);
				} else if (isStore) {
					game.setScreen(game.storeScreen);
				} else if (isSoundOption) {
					game.setScreen(game.soundScreen);
				}
			}
			spriteBatch.setColor(c.r, c.g, c.b, gameAlpha);
			gameAlpha -= 0.01f;
			stage.clear();
			buttonInit(buttonMove);
			buttonMove += BUTTON_MOVE_INCEMENT;
		}

		drawMenuComponents();
		drawQuitScreen();

		spriteBatch.setColor(c.r, c.g, c.b, 1.0f); // set alpha to 1

		spriteBatch.end();
		spriteBatch.disableBlending();

		stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
		stage.draw();

		Table.drawDebug(stage);

		// if (Gdx.input.justTouched()) {
		//
		// tempNameFontIndex = (tempNameFontIndex+1)%4;
		// if (tempNameFontIndex < 10) {
		// tempNameFont = "0" + tempNameFontIndex;
		// } else {
		// tempNameFont = "" + tempNameFontIndex;
		// }
		// font = initFont("fonts/" + tempNameFont + ".fnt", "fonts/"
		// + tempNameFont + ".png");
		// font.setScale((height / font.getBounds("A").height) / 30);
		// }

	}

	private void drawQuitScreen() {
		if (isGameQuitOption) {
			spriteBatch.draw(screenOverTextures, 0, 0, width, height);
			spriteBatch.draw(quitScreenBackgroundTextures, quitScreenX,
					quitScreenY, quitScreenW, quitScreenH);
		}
	}

	private void drawMenuComponents() {
		spriteBatch.draw(menuBackgroundTexture, 0, 0, width, height);

		Color c = spriteBatch.getColor();
		font.setColor(c.r, c.g, c.b, gameAlpha);

		String[] tempString = { "Total Credit : " + gameTotalScore,
				"Game Played : " + noOfGamePlays + " times",
				"High Score : " + gameHighScore, "Your Score : " + gameScore };
		for (int i = 0; i < tempString.length; i++) {
			font.draw(spriteBatch, tempString[i],
					width / 2 - font.getBounds(tempString[i]).width / 2, (i
							* height / 15)
							+ 2
							* height
							/ 3
							+ font.getBounds(tempString[i]).height / 2);

		}
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

	private void buttonInit(float move) {

		float xCodinate = (width - (4 * 1.5f * height / 10 + 3 * height / 100)) / 2;
		float yCodinate = height / 4;

		addButton(crossButtonTextures, 0, -move, 0.8f * BUTTON_WIDTH,
				0.8f * BUTTON_WIDTH, GameCode.QUIT);

		addButton(playButtonTexture, width / 2 - height / 10, -move + yCodinate
				+ height / 20, height / 5, height / 5, GameCode.GAME_WORLD);

		addButton(storeButtonTexture, -move + width / 2 - height / 10 - height
				/ 8, yCodinate + height / 20, height / 8, height / 8,
				GameCode.GAME_STORE);
		addButton(settingButtonTexture, move + width / 2 - height / 10 + height
				/ 5, yCodinate + height / 20, height / 8, height / 8,
				GameCode.GAME_SOUND_SETTING);

		addButton(shareButtonTexture, -move + xCodinate, -move + yCodinate
				- 1.0f * height / 10, 1.5f * height / 10, 1.5f * height / 10,
				GameCode.SHARE);
		addButton(achievementButtonTexture, xCodinate + 1.1f * 1.5f * height
				/ 10, -move + yCodinate - 1.0f * height / 10,
				1.5f * height / 10, 1.5f * height / 10, GameCode.ACHIEVEMENT);
		addButton(leaderboardButtonTexture, xCodinate + 2.2f * 1.5f * height
				/ 10, -move + yCodinate - 1.0f * height / 10,
				1.5f * height / 10, 1.5f * height / 10, GameCode.LEADERBOARD);
		addButton(rateButtonTexture, move + xCodinate + 3.3f * 1.5f * height
				/ 10, -move + yCodinate - 1.0f * height / 10,
				1.5f * height / 10, 1.5f * height / 10, GameCode.RATE);

	}

	@Override
	public void resize(int width, int height) {

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

		isBackButtonEnable = true;
		buttonMove = 0;
		isTouchEnable = true;
		isPlay = false;
		isStore = false;
		isSoundOption = false;

		quitScreenX = 0;
		quitScreenY = 0;
		quitScreenW = 0;
		quitScreenH = 0;

		isGameQuitOption = false;

		buttonInit(0);

		Gdx.input.setInputProcessor(stage);
		actionResolver.showOrLoadInterstital();

		achievmentIndex = 0;
		achievmentIDs = actionResolver.getAchievmentIDs();
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
