package com.anisbulbul.race.danger.screen;

import com.anisbulbul.race.danger.ActionResolver;
import com.anisbulbul.race.danger.DangerRace;
import com.anisbulbul.race.danger.assets.GameAssets;
import com.anisbulbul.race.danger.files.FileHandlers;
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

	private DangerRace game;
	private ActionResolver actionResolver;
	private float width;
	private float height;
	private SpriteBatch spriteBatch;;
	private BitmapFont font;
	private Texture scoreBackgroundTexture;
	private Texture backButtonTexture;
	private Stage stage;
	private Skin skin;
	private Music scoreMusic;
	private Texture updateScoreButtonTexture;
	private int highScore;
	private int achievmentIndex;
	private String[] achievmentIDs;

	public ScoreScreen(DangerRace game, ActionResolver actionResolver) {

		this.game = game;
		this.actionResolver = actionResolver;
		width = Gdx.graphics.getWidth();
		height = Gdx.graphics.getHeight();
		this.spriteBatch = new SpriteBatch();

		loadTextures();
		loadSounds();
		loadFonts();
		loadOtherComponents();

	}

	private void loadFonts() {

		font = initFont("fonts/abc.fnt", "fonts/abc.png");
		font.setScale((height / font.getBounds("A").height) / 10);


	}

	private void loadSounds() {
		scoreMusic = initMusic("sounds/score_music.mp3");

	}

	private void loadTextures() {

		scoreBackgroundTexture = initTexture("screen_backgrounds/scorebackground.png");
		backButtonTexture = initTexture("buttons/backbutton.png");
		updateScoreButtonTexture = initTexture("buttons/updatescorebutton.png");

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

		String tempScore = "Hi : " + highScore + " Dragon kills";

		font.draw(spriteBatch, tempScore,
				width / 2 - font.getBounds(tempScore).width / 2, height
						/ 2 + font.getBounds(tempScore).height / 2);

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
				if (action.equals("BACK")) {
					
					if(scoreMusic.isPlaying()){
						scoreMusic.stop();						
					}

					Gdx.input.setInputProcessor(null);
					game.setScreen(game.menuScreen);
				}
				else if(action.equals("UPSC")){
					actionResolver.submitScoreGPGS(highScore);
					actionResolver.getLeaderboardHighScoreGPGS();
					if(highScore >= 50){
						actionResolver.submitTrophyGPGS(highScore);
						actionResolver.getLeaderboardTrophyGPGS();
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

		addButton(backButtonTexture,  width/4 - width/12, 0, width / 6, width / 12, "BACK");
		addButton(updateScoreButtonTexture,   3*width/4 - width / 6, 0, width / 3, width / 12, "UPSC");

		highScore = FileHandlers.getScore();
		if(highScore >= 10){
			if(highScore >= 10 && highScore >= 16){
				achievmentIndex = 0;
			}
			else if(highScore > 16 && highScore >= 22){
				achievmentIndex = 1;
			}
			else if(highScore > 22 && highScore >= 28){
				achievmentIndex = 2; 
			}
			else if(highScore > 28 && highScore >= 36){
				achievmentIndex = 3;
			}
			else if(highScore > 36 && highScore >= 42){
				achievmentIndex = 4;
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

		spriteBatch.disableBlending();
		font.dispose();
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
