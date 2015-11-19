package com.anrosoft.jetadventure.screen;

import com.anrosoft.jetadventure.ActionResolver;
import com.anrosoft.jetadventure.JetAdventure;
import com.anrosoft.jetadventure.assets.GameAssets;
import com.anrosoft.jetadventure.code.GameCode;
import com.anrosoft.jetadventure.model.GameWorld;
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

	private JetAdventure game;

	private SpriteBatch spriteBatch;
	public static Stage stage;
	public static Skin skin;

	public String[] achievmentIDs;
	public int achievmentIndex;

	private ActionResolver actionResolver;

	private float buttonMove;
	private boolean isTouchEnable;
	private boolean isPlay;
	private boolean isSoundOption;
	private boolean isGameQuitOption;

	private float quitScreenX;
	private float quitScreenY;
	private float quitScreenW;
	private float quitScreenH;

	public MenuScreen(JetAdventure game, ActionResolver actionResolver) {

		this.game = game;
		spriteBatch = new SpriteBatch();
		this.actionResolver = actionResolver;
		buttonMove = 0;
		isTouchEnable = true;
		isPlay = false;
		isSoundOption = false;
		isGameQuitOption = false;
		quitScreenX = 0;
		quitScreenY = 0;
		quitScreenW = 0;
		quitScreenH = 0;
	}

	public void addButton(Texture ButtonTexture, float posX, float posY,
			float bWidth, float bHeight, final String action) {

		TextButton button;
		TextButtonStyle textButtonStyle;
		skin = new Skin();

		skin.add("white", ButtonTexture);
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

				if (isTouchEnable && action.equals(GameCode.NEW_GAME)) {
					isAnimDark = true;
					isTouchEnable = false;
					isPlay = true;
				} else if (isTouchEnable && action.equals(GameCode.SETTING)) {
					isAnimDark = true;
					isTouchEnable = false;
					isSoundOption = true;
				} else if (isTouchEnable && action.equals(GameCode.SHARE)) {
					actionResolver.showShare();

				} else if (isTouchEnable && action.equals(GameCode.RATE)) {
					actionResolver.showRated();

				} else if (isTouchEnable && action.equals(GameCode.LEADERBOARD)) {
					actionResolver.submitScoreGPGS(gameHighScore);
					if (gameHighScore > 100) {
						actionResolver.submitTrophyGPGS(gameHighScore);
					}
					actionResolver.getAllLeaderboardGPGS();

				} else if (isTouchEnable && action.equals(GameCode.ACHIEVEMENT)) {
					actionResolver.getAchievementsGPGS();

				} else if (isTouchEnable && action.equals(GameCode.QUIT)) {
					isGameQuitOption = true;
					stage.clear();
					quitScreenX = 3 * gaps;
					quitScreenY = (height - (width - 2 * gaps)) / 2;
					quitScreenW = width - 6 * gaps;
					quitScreenH = quitScreenW + 2 * gaps;
					quitButtonInit();
					backgroundMusic.pause();
				} else if (isTouchEnable && action.equals(GameCode.YES)) {
					isBackButtonEnable = false;
					Gdx.app.exit();

				} else if (isTouchEnable && action.equals(GameCode.NO)) {
					isGameQuitOption = false;
					stage.clear();
					menuButtonInit(0);
					backgroundMusic.play();
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
					noOfGamePlays++;
					backgroundMusic.stop();
					newGame();
					game.setScreen(game.gameScreen);
				} else if (isSoundOption) {
					game.setScreen(game.soundScreen);
				}
			}
			spriteBatch.setColor(c.r, c.g, c.b, gameAlpha);
			gameAlpha -= 0.01f;
			stage.clear();
			menuButtonInit(buttonMove);
			buttonMove += BUTTON_MOVE_INCEMENT;
		}

		drawMenuComponents();

		spriteBatch.setColor(c.r, c.g, c.b, 1.0f); // set alpha to 1

		spriteBatch.end();
		spriteBatch.disableBlending();

		stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
		stage.draw();

		Table.drawDebug(stage);

	}

	private void drawMenuComponents() {
		spriteBatch.draw(menuBackgroundTextureRegion, 0, 0, width, height);
		if (isGameQuitOption) {
			spriteBatch.draw(screenOverTextureRegion, 0, 0, width, height);
			spriteBatch.draw(quitScreenBackgroundTextureRegion, quitScreenX,
					quitScreenY, quitScreenW, quitScreenH);
		}
	}

	private void menuButtonInit(float move) {

		addButton(quitButtonTexture, 0, height - 0.7f * BUTTON_WIDTH + move,
				0.7f * BUTTON_WIDTH, 0.7f * BUTTON_WIDTH, GameCode.QUIT);

		addButton(shareButtonTexture, gaps, gaps / 2 - move, BUTTON_WIDTH,
				BUTTON_WIDTH, GameCode.SHARE);
		addButton(achievementButtonTexture, 2 * gaps + BUTTON_WIDTH, gaps / 2
				- move, BUTTON_WIDTH, BUTTON_WIDTH, GameCode.ACHIEVEMENT);
		addButton(leaderboardButtonTexture, 3 * gaps + 2 * BUTTON_WIDTH, gaps
				/ 2 - move, BUTTON_WIDTH, BUTTON_WIDTH, GameCode.LEADERBOARD);
		addButton(rateButtonTexture, 4 * gaps + 3 * BUTTON_WIDTH, gaps / 2
				- move, BUTTON_WIDTH, BUTTON_WIDTH, GameCode.RATE);
		addButton(settingButtonTexture, 5 * gaps + 4 * BUTTON_WIDTH, gaps / 2
				- move, BUTTON_WIDTH, BUTTON_WIDTH, GameCode.SETTING);

		addButton(playMainButtonTexture, move + 4 * gaps + 3 * BUTTON_WIDTH,
				gaps + BUTTON_WIDTH, 2.5f * BUTTON_WIDTH, BUTTON_WIDTH,
				GameCode.NEW_GAME);

	}

	private void quitButtonInit() {
		addButton(yesButtonTexture, quitScreenX + quitScreenW / 2 - gaps / 2
				- BUTTON_WIDTH, quitScreenY + quitScreenH / 4 - BUTTON_WIDTH
				/ 2, BUTTON_WIDTH, BUTTON_WIDTH, GameCode.YES);
		addButton(noButtonTexture, quitScreenX + quitScreenW / 2 + gaps / 2,
				quitScreenY + quitScreenH / 4 - BUTTON_WIDTH / 2, BUTTON_WIDTH,
				BUTTON_WIDTH, GameCode.NO);
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
		isSoundOption = false;
		isGameQuitOption = false;

		quitScreenX = 0;
		quitScreenY = 0;
		quitScreenW = 0;
		quitScreenH = 0;

		achievmentIndex = 0;
		achievmentIDs = actionResolver.getAchievmentIDs();

		if (gameHighScore <= 20000) {
			if (gameHighScore >= 20000) {
				actionResolver.submitTrophyGPGS(gameHighScore);
			} else if (gameHighScore >= 16000) {
				achievmentIndex = 4;
			} else if (gameHighScore >= 12000) {
				achievmentIndex = 3;
			} else if (gameHighScore >= 8000) {
				gameHighScore = 2;
			} else if (gameHighScore >= 4000) {
				gameHighScore = 1;
			} else {
				gameHighScore = 0;
			}
			actionResolver
					.unlockAchievementGPGS(achievmentIDs[achievmentIndex]);
		}

		menuButtonInit(0);

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
		// TODO Auto-generated method stub

	}

}
