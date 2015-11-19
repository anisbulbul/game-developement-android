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

public class MenuScreen extends GameAssets implements Screen, InputProcessor {

	private CarRace3D game;
	private ActionResolver actionResolver;
	private float width;
	private float height;
	private SpriteBatch spriteBatch;
	private BitmapFont font;
	private Texture soundOnTexture;
	private Texture[] menuBttonTexture;
	private Stage stage;
	private Skin skin;
	private Texture soundOffTexture;
	private Texture rateButtonTexture;
	private Texture shareButtonTexture;
	private Texture menuBackgroundTexture;
	private Music menuMusic;
	private String[] buttonOptions;
	private Texture playstoreButtonTexture;
	private Texture leaderboardButtonTexture;
	private Texture achievementButtonTexture;

	public MenuScreen(CarRace3D game, ActionResolver actionResolver) {

		this.game = game;
		this.actionResolver = actionResolver;
		width = Gdx.graphics.getWidth();
		height = Gdx.graphics.getHeight();
		spriteBatch = new SpriteBatch();
		buttonOptions = new String[6];
		menuBttonTexture = new Texture[6];
		loadOthersComponents();
		loatFonts();
		loadTextures();
		loadSounds();

		

	}

	private void loadSounds() {
		menuMusic = initMusic("sounds/menu_music.mp3");

	}

	private void loadTextures() {

		soundOnTexture = initTexture("buttons/soundon.png");
		soundOffTexture = initTexture("buttons/soundoff.png");
		shareButtonTexture = initTexture("buttons/sharebutton.png");
		rateButtonTexture = initTexture("buttons/ratebutton.png");
		menuBttonTexture[0] = initTexture("buttons/quitbutton.png");
		menuBttonTexture[1] = initTexture("buttons/aboutbutton.png");
		menuBttonTexture[2] = initTexture("buttons/helpbutton.png");
		menuBttonTexture[3] = initTexture("buttons/settingbutton.png");
		menuBttonTexture[4] = initTexture("buttons/scorebutton.png");
		menuBttonTexture[5] = initTexture("buttons/newgamebutton.png");

		playstoreButtonTexture = initTexture("buttons/playstorebutton.png");
		leaderboardButtonTexture = initTexture("buttons/leaderboardbutton.png");
		achievementButtonTexture = initTexture("buttons/achievementbutton.png");
		
		menuBackgroundTexture = initTexture("data/menubackground.png");

	}

	private void loatFonts() {

		font = initFont("fonts/abc.fnt", "fonts/abc.png");
		font.setScale(width / 1360, height / 768);

	}

	private void loadOthersComponents() {

		buttonOptions[0] = "EX";
		buttonOptions[1] = "AB";
		buttonOptions[2] = "HP";
		buttonOptions[3] = "ST";
		buttonOptions[4] = "SC";
		buttonOptions[5] = "NG";

	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0.1f, 0.1f, 0.1f, 1);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);

		spriteBatch.begin();
		spriteBatch.enableBlending();

		if (Gdx.input.justTouched()) {
			// 4*width/5, 8*height/10, width/10, width/10
			float getX = Gdx.input.getX();
			float getY = height - Gdx.input.getY();

			if (getX >= width / 10 && getX <= width / 10 + width / 10
					&& getY >= 8 * height / 10
					&& getY <= 8 * height / 10 + width / 10) {
				if (isGameSound) {
					if (menuMusic.isPlaying()) {
						menuMusic.stop();
					}
					isGameSound = false;
				} else {
					if (!menuMusic.isPlaying()) {
						menuMusic.play();
					}
					isGameSound = true;
				}
			}
		}
		drawMenuBackground();

		spriteBatch.end();
		spriteBatch.disableBlending();

		stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
		stage.draw();
		Table.drawDebug(stage);

	}

	private void drawMenuBackground() {

		spriteBatch.draw(menuBackgroundTexture, 0, 0, width, height);
		if (isGameSound) {
			spriteBatch.draw(soundOnTexture, width / 10, 8 * height / 10,
					width / 10, width / 10);
		} else {
			spriteBatch.draw(soundOffTexture, width / 10, 8 * height / 10,
					width / 10, width / 10);
		}

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
				if (action.equals("NG")) {

					if (menuMusic.isPlaying()) {
						menuMusic.stop();
					}

					Gdx.input.setInputProcessor(null);
					GameAssets.reloadNewGame();
					game.setScreen(game.raceScreen);

				} else if (action.equals("SC")) {
					if (menuMusic.isPlaying()) {
						menuMusic.stop();
					}
					Gdx.input.setInputProcessor(null);
					game.setScreen(game.scoreScreen);

				} else if (action.equals("ST")) {
					if (menuMusic.isPlaying()) {
						menuMusic.stop();
					}
					Gdx.input.setInputProcessor(null);
					game.setScreen(game.settingScreen);

				} else if (action.equals("HP")) {
					if (menuMusic.isPlaying()) {
						menuMusic.stop();
					}
					Gdx.input.setInputProcessor(null);
					game.setScreen(game.helpScreen);

				} else if (action.equals("AB")) {
					if (menuMusic.isPlaying()) {
						menuMusic.stop();
					}
					Gdx.input.setInputProcessor(null);
					game.setScreen(game.aboutScreen);

				} else if (action.equals("EX")) {
					if (menuMusic.isPlaying()) {
						menuMusic.stop();
					}
					Gdx.app.exit();

				} else if (action.equals("SR")) {
					actionResolver.showShare();
				} else if (action.equals("RA")) {
					actionResolver.showRated();
				} else if (action.equals("LD")) {
					actionResolver.getAllLeaderboardGPGS();
				} else if (action.equals("AC")) {
					actionResolver.getAchievementsGPGS();
				} else if (action.equals("PS")) {
					actionResolver.showRated();
				}
			}
		});

	}

	@Override
	public void show() {

		if (isGameSound && !menuMusic.isPlaying()) {
			menuMusic.play();
		} else if (!isGameSound && menuMusic.isPlaying()) {
			menuMusic.stop();
		}

		stage = new Stage();

		addButton(shareButtonTexture, 0, height / 10 - height / 20,
				width / 5, width / 5, "SR");
		
		addButton(achievementButtonTexture, width / 5, height / 10 - height / 20,
				width / 5, width / 5, "AC");
		
		addButton(playstoreButtonTexture, 2 * width / 5, height / 10 - height / 20,
				width / 5, width / 5, "PS");
		
		addButton(rateButtonTexture, 3 * width / 5, height / 10 - height / 20,
				width / 5, width / 5, "RA");
		
		addButton(leaderboardButtonTexture, 4 * width / 5, height / 10 - height / 20,
				width / 5, width / 5, "LD");


		for (int i = 0; i < 6; i++) {
			addButton(menuBttonTexture[i], width / 2 - width / 4, (i + 3)
					* height / 10 - height / 20, width / 2, height / 10,
					buttonOptions[i]);
		}

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
		soundOnTexture.dispose();
		for (int i = 0; i < menuBttonTexture.length; i++) {
			menuBttonTexture[i].dispose();
		}

		if (stage != null)
			stage.dispose();
		if (skin != null)
			skin.dispose();
		soundOffTexture.dispose();
		rateButtonTexture.dispose();
		shareButtonTexture.dispose();
		menuBackgroundTexture.dispose();
		if (menuMusic.isPlaying()) {
			menuMusic.stop();
		}
		menuMusic.dispose();

	}

	@Override
	public boolean keyDown(int keycode) {
		// TODO Auto-generated method stub
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
