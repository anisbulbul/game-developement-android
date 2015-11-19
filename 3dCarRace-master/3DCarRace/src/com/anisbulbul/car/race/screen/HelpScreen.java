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
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;

public class HelpScreen extends GameAssets implements Screen, InputProcessor {

	private CarRace3D game;
	private ActionResolver actionResolver;
	private float width;
	private float height;
	private SpriteBatch spriteBatch;
	private BitmapFont font;
	private Texture helpBackgroundTexture;
	private Stage stage;
	private Skin skin;
	private Texture backButtonTexture;
	private Music helpMusic;

	public HelpScreen(CarRace3D game, ActionResolver actionResolver) {

		this.game = game;
		this.actionResolver = actionResolver;
		width = Gdx.graphics.getWidth();
		height = Gdx.graphics.getHeight();
		this.spriteBatch = new SpriteBatch();
		loadComponents();
	}

	private void loadComponents() {
		font = initFont("fonts/abc.fnt", "fonts/abc.png");
		font.setScale(width / 1360, height / 768);

		backButtonTexture = initTexture("buttons/backbutton.png");
		helpBackgroundTexture = initTexture("data/helpbackground.png");

		helpMusic = initMusic("sounds/help_music.mp3");
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0.1f, 0.1f, 0.1f, 1);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);

		spriteBatch.begin();
		spriteBatch.enableBlending();

		drawMenuBackground();

		spriteBatch.end();
		spriteBatch.disableBlending();

		stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
		stage.draw();
		Table.drawDebug(stage);

	}

	private void drawMenuBackground() {

		spriteBatch.draw(helpBackgroundTexture, 0, 0, width, height);

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
					helpMusic.stop();
					Gdx.input.setInputProcessor(null);
					game.setScreen(game.menuScreen);
				}
			}
		});

	}

	@Override
	public void show() {

		if (isGameSound && !helpMusic.isPlaying()) {
			helpMusic.play();
			helpMusic.setLooping(true);
		} else if (!isGameSound && helpMusic.isPlaying()) {
			helpMusic.stop();
		}
		stage = new Stage();

		skin = new Skin();

		addButton(backButtonTexture, width / 2 - width / 4, 0, width / 2,
				width / 4, "OK");

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
		helpBackgroundTexture.dispose();
		backButtonTexture.dispose();
		if (stage != null)
			stage.dispose();
		if (skin != null)
			skin.dispose();
		if (helpMusic.isPlaying()) {
			helpMusic.stop();
		}
		helpMusic.dispose();

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
