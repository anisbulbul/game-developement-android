package com.anisbulbul.car.race.screen;

import com.anisbulbul.car.race.ActionResolver;
import com.anisbulbul.car.race.CarRace3D;
import com.anisbulbul.car.race.assets.GameAssets;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;

public class SettingScreen extends GameAssets implements Screen, InputProcessor {

	private CarRace3D game;
	private ActionResolver actionResolver;
	private float width;
	private float height;
	private SpriteBatch spriteBatch;
	private BitmapFont font;
	private Texture[] roadTexture;
	private Texture[] levelTexture;
	private TextureRegion[] myCarTexture;
	private Stage stage;
	private Skin skin;
	private Texture rightArrowButtonTexture;
	private Texture leftArrowButtonTexture;
	private Texture settingBackgroundTexture;
	private Texture backButtonTexture;
	private Music settingMusic;

	public SettingScreen(CarRace3D game, ActionResolver actionResolver) {

		this.game = game;
		this.actionResolver = actionResolver;
		width = Gdx.graphics.getWidth();
		height = Gdx.graphics.getHeight();
		spriteBatch = new SpriteBatch();

		loadComponents();
	}

	private void loadComponents() {

		font = initFont("fonts/abc.fnt", "fonts/abc.png");
		font.setScale(width / 1360, height / 768);

		roadTexture = new Texture[NUMBER_OF_ROADS];
		levelTexture = new Texture[NUMBER_OF_LEVELS];
		myCarTexture = new TextureRegion[NUMBER_OF_MY_CARS];

		for (int i = 0; i < NUMBER_OF_ROADS; i++) {
			roadTexture[i] = initTexture("roads/road" + (i + 1) + ".png");
		}
		for (int i = 0; i < NUMBER_OF_LEVELS; i++) {
			levelTexture[i] = initTexture("levels/level" + (i + 1) + ".png");
		}

		for (int i = 0; i < NUMBER_OF_MY_CARS; i++) {
			myCarTexture[i] = new TextureRegion(initTexture("mycars/car"
					+ (i + 1) + ".png"));
		}

		backButtonTexture = initTexture("buttons/backbutton.png");
		leftArrowButtonTexture = initTexture("buttons/leftarrowbutton.png");
		rightArrowButtonTexture = initTexture("buttons/rightarrowbutton.png");
		settingBackgroundTexture = initTexture("data/settingbackground.png");

		settingMusic = initMusic("sounds/setting_music.mp3");
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0.1f, 0.1f, 0.1f, 1);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);

		spriteBatch.begin();
		spriteBatch.enableBlending();

		drawbackground();
		drawMyCar();
		drawRoads();
		drawLevels();

		spriteBatch.end();
		spriteBatch.disableBlending();

		stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
		stage.draw();
		Table.drawDebug(stage);

	}

	private void drawbackground() {
		spriteBatch.draw(settingBackgroundTexture, 0, 0, width, height);

	}

	private void drawLevels() {
		spriteBatch.draw(levelTexture[gameLevel], width / 4, 3 * height / 5,
				2 * width / 4, height / 5);

	}

	private void drawMyCar() {

		spriteBatch.draw(myCarTexture[myCarNumber], width / 4, 0, width / 8,
				width / 8, width / 4, width / 2, 1.0f, 1.0f, -90);

		// spriteBatch.draw(region, x, y, originX, originY,
		// width, height, scaleX, scaleY, rotation)
	}

	private void drawRoads() {

		spriteBatch.draw(roadTexture[roadNumber], width / 4, height / 5,
				2 * width / 4, 2 * height / 5);

	}

	@Override
	public void resize(int width, int height) {
		stage.setViewport(width, height, false);
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
				if (isGameSound) {
					clickSound.play();
				}
				if (action.equals("OK")) {
					settingMusic.stop();
					Gdx.input.setInputProcessor(null);
					game.setScreen(game.menuScreen);
				} else if (action.equals("BN")) {
					myCarNumber = (myCarNumber + 1) % NUMBER_OF_MY_CARS;
				} else if (action.equals("BP")) {
					if (myCarNumber > 0) {
						myCarNumber = myCarNumber - 1;
					} else {
						myCarNumber = NUMBER_OF_CARS - 1;
					}
				} else if (action.equals("RN")) {
					roadNumber = (roadNumber + 1) % NUMBER_OF_ROADS;
				} else if (action.equals("RP")) {
					if (roadNumber > 0) {
						roadNumber = roadNumber - 1;
					} else {
						roadNumber = NUMBER_OF_ROADS - 1;
					}
				} else if (action.equals("LN")) {
					gameLevel = (gameLevel + 1) % NUMBER_OF_LEVELS;
				} else if (action.equals("LP")) {
					if (gameLevel > 0) {
						gameLevel = gameLevel - 1;
					} else {
						gameLevel = NUMBER_OF_LEVELS - 1;
					}
				}
			}
		});

	}

	@Override
	public void show() {

		if (isGameSound && !settingMusic.isPlaying()) {
			settingMusic.play();
			settingMusic.setLooping(true);
		} else if (!isGameSound && settingMusic.isPlaying()) {
			settingMusic.stop();
		}

		stage = new Stage();

		addButton(backButtonTexture, width / 4, 4 * height / 5, width / 2,
				height / 5, "OK");
		addButton(rightArrowButtonTexture, width / 16 + 3 * width / 4,
				width / 16, width / 8, width / 8, "BN");
		addButton(leftArrowButtonTexture, width / 16, width / 16, width / 8,
				width / 8, "BP");
		addButton(rightArrowButtonTexture, width / 16 + 3 * width / 4, width
				/ 16 + 3 * height / 10, width / 8, width / 8, "RN");
		addButton(leftArrowButtonTexture, width / 16, width / 16 + 3 * height
				/ 10, width / 8, width / 8, "RP");
		addButton(rightArrowButtonTexture, width / 16 + 3 * width / 4, width
				/ 16 + 3 * height / 5, width / 8, width / 8, "LN");
		addButton(leftArrowButtonTexture, width / 16, width / 16 + 3 * height
				/ 5, width / 8, width / 8, "LP");

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
	public void dispose() {

		spriteBatch.dispose();
		font.dispose();
		for (int i = 0; i < roadTexture.length; i++) {
			roadTexture[i].dispose();
		}
		for (int i = 0; i < levelTexture.length; i++) {
			levelTexture[i].dispose();
		}
		for (int i = 0; i < myCarTexture.length; i++) {
			myCarTexture[i].getTexture().dispose();
		}

		backButtonTexture.dispose();

		if (stage != null)
			stage.dispose();
		if (skin != null)
			skin.dispose();
		rightArrowButtonTexture.dispose();
		leftArrowButtonTexture.dispose();
		settingBackgroundTexture.dispose();
		if (settingMusic.isPlaying()) {
			settingMusic.stop();
		}
		settingMusic.dispose();

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
