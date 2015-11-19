package com.anisbulbul.car.race.screen;

import com.anisbulbul.car.race.ActionResolver;
import com.anisbulbul.car.race.CarRace3D;
import com.anisbulbul.car.race.assets.GameAssets;
import com.anisbulbul.car.race.files.FileHandlers;
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

public class ScoreScreen extends GameAssets implements Screen, InputProcessor {

	private CarRace3D game;
	private ActionResolver actionResolver;
	private float width;
	private float height;
	private SpriteBatch spriteBatch;
	private BitmapFont font;
	private BitmapFont font2;
	private Texture scoreBackgroundTexture;
	private Texture backButtonTexture;
	private Texture scoreUpdateButtonTexture;
	private Stage stage;
	private Skin skin;
	private Music scoreMusic;
	private int achievmentIndex;
	private String[] achievmentIDs;
	private long[] hi_score_name;
	private long highScore;
	public ScoreScreen(CarRace3D game, ActionResolver actionResolver) {

		this.game = game;
		this.actionResolver = actionResolver;
		width = Gdx.graphics.getWidth();
		height = Gdx.graphics.getHeight();
		spriteBatch = new SpriteBatch();

		loadTextures();
		loadSounds();
		loadFonts();
		loadOtherComponents();

	}

	private void loadFonts() {

		font = initFont("fonts/abc.fnt", "fonts/abc.png");
		font2 = initFont("fonts/abc.fnt", "fonts/abc.png");

		font.setScale((height / font.getBounds("A").height) / 15);
		font2.setScale((height / font2.getBounds("A").height) / 15);

		font2.setColor(Color.GREEN);

	}

	private void loadSounds() {
		scoreMusic = initMusic("sounds/score_music.mp3");

	}

	private void loadTextures() {
		backButtonTexture = initTexture("buttons/backbutton.png");
		scoreBackgroundTexture = initTexture("data/background.png");
		scoreUpdateButtonTexture = initTexture("buttons/scoreupdatebutton.png");

	}

	private void loadOtherComponents() {
		highScore = 0;
		achievmentIndex = 0;
		achievmentIDs = actionResolver.getAchievmentIDs();
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0.1f, 0.1f, 0.1f, 1);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);

		spriteBatch.begin();
		spriteBatch.enableBlending();

		drawMenuBackground();
		drawScores();

		spriteBatch.end();
		spriteBatch.disableBlending();

		stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
		stage.draw();
		Table.drawDebug(stage);

	}

	private void drawScores() {

		String tempScore = "";
		for (int i = 0; i < hi_score_name.length && i < 5; i++) {

			if (i == 0) {
				tempScore = (i + 1) + "ST : " + hi_score_name[4 - i];
			} else if (i == 1) {
				tempScore = (i + 1) + "ND : " + hi_score_name[4 - i];
			} else if (i == 2) {
				tempScore = (i + 1) + "RD : " + hi_score_name[4 - i];
			} else {
				tempScore = (i + 1) + "TH : " + hi_score_name[4 - i];
			}
			font.draw(spriteBatch, tempScore,
					width / 2 - font.getBounds(tempScore).width / 2, (8 - i)
							* height / 10 + 4
							* font.getBounds(tempScore).height / 2);
		}
		if (gameScoreIndex >= 1 && gameScoreIndex <= 5) {
			tempScore = "Score :" + hi_score_name[5 - gameScoreIndex];
		} else {
			tempScore = "TRY NOW";
		}

		font2.setColor(Color.GREEN);
		font2.draw(spriteBatch, tempScore,
				width / 2 - font2.getBounds(tempScore).width / 2, 2.5f * height
						/ 10 + 4 * font2.getBounds(tempScore).height / 2);

	}

	private void drawMenuBackground() {

		spriteBatch.draw(scoreBackgroundTexture, 0, 0, width, height);

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
					scoreMusic.stop();
					Gdx.input.setInputProcessor(null);
					game.setScreen(game.menuScreen);
				} else if (action.equals("UPSC")) {
					actionResolver.submitScoreGPGS(highScore);

					if(highScore > 20000){
						actionResolver.submitTrophyGPGS(highScore);
						actionResolver.getLeaderboardTrophyGPGS();
					}
					else{
						actionResolver.getLeaderboardHighScoreGPGS();
					}
				}
			}
		});

	}

	@Override
	public void show() {

		if (isGameSound && !scoreMusic.isPlaying()) {
			scoreMusic.play();
			scoreMusic.setLooping(true);
		} else if (!isGameSound && scoreMusic.isPlaying()) {
			scoreMusic.stop();
		}

		stage = new Stage();

		skin = new Skin();

		addButton(backButtonTexture, width / 2 - width / 8, 0, width / 4,
				width / 8, "OK");
		
		addButton(scoreUpdateButtonTexture, width/8, width/4, width-width/4,
				width / 8, "UPSC");
		hi_score_name = FileHandlers.getHiScore();
		highScore = hi_score_name[hi_score_name.length-1];
		if(highScore <= 100000){
			if(highScore <= 20000){
				achievmentIndex = 4;
			}
			else if(highScore <= 40000){
				achievmentIndex = 3;
			}
			else if(highScore <= 60000){
				achievmentIndex = 2;
			}
			else if(highScore <= 80000){
				achievmentIndex = 1;
			}
			else{
				achievmentIndex = 0;
			}
			actionResolver.unlockAchievementGPGS(achievmentIDs[achievmentIndex]);
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
		font2.dispose();
		scoreBackgroundTexture.dispose();
		backButtonTexture.dispose();
		if (stage != null)
			stage.dispose();
		if (skin != null)
			skin.dispose();
		if (scoreMusic.isPlaying()) {
			scoreMusic.stop();
		}
		scoreMusic.dispose();

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
