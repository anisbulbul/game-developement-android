package com.anisbulbul.race.danger.screen;

import com.anisbulbul.race.danger.ActionResolver;
import com.anisbulbul.race.danger.DangerRace;
import com.anisbulbul.race.danger.assets.GameAssets;
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

public class StageScreen extends GameAssets implements Screen, InputProcessor {

	private DangerRace game;
	private ActionResolver actionResolver;
	private float width;
	private float height;
	private SpriteBatch spriteBatch;
	private BitmapFont font;
	private Texture stageBackgroundTexture;
	private Stage stage;
	private Skin skin;

	private Texture backButtonTexture;
	private Texture lockButtonTexture;
	private Texture unlockButtonTexture;
	protected Music stageMusic;

	public StageScreen(DangerRace game, ActionResolver actionResolver) {

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
		unlockButtonTexture = initTexture("buttons/unlockbutton.png");
		lockButtonTexture = initTexture("buttons/lockbutton.png");
		stageBackgroundTexture = initTexture("screen_backgrounds/stagebackground.png");

		stageMusic = initMusic("sounds/stage_music.mp3");
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

		spriteBatch.draw(stageBackgroundTexture, 0, 0, width, height);

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
					if(stageMusic.isPlaying()){
						stageMusic.stop();
					}
					Gdx.input.setInputProcessor(null);
					game.setScreen(game.menuScreen);
				}
				else if(action.equals("LOCK")){
					if(isGameSound){
						//
					}
					
				}
				else if(action.length() == 8 && (action.substring(0, 6)).equals("UNLOCK")){
					
					int row = Integer.parseInt(action.substring(6, 7));
					int col = Integer.parseInt(action.substring(7, 8));
					
					if(col < 2 || row < 2 ){
						if(col < 2){
							if(stageState[row][col+1] == 0){
								stageStateIndex = true;							
							}
							else{
								stageStateIndex = false;
							}
						}
						else{
							if(stageState[row+1][col] == 0){
								stageStateIndex = true;							
							}
							else{
								stageStateIndex = false;
							}
						}

					}
					
//					System.out.println(action+" "+row+" "+col+" "+stageState[row][col]);
//					System.out.println(row*9+col*3);
//					System.out.println(col+1);
					
					EARN_POINTS = 10+ row*9+col*3;
					GAME_STAGE_NUMBER = col+1;

					if(stageMusic.isPlaying()){
						stageMusic.stop();
					}
					
					Gdx.input.setInputProcessor(null);
					GameAssets.reloadNewGame();
	                game.setScreen(game.raceScreen);
				}
			}
		});

	}

	@Override
	public void show() {

		if (isGameSound && !stageMusic.isPlaying()) {
			stageMusic.play();
			stageMusic.setLooping(true);
		} else if (!isGameSound && stageMusic.isPlaying()) {
			stageMusic.stop();
		}

		stage = new Stage();

		for (int i = 0 ; i < 9; i++) {
			int row = i / 3;
			int col = i % 3;
			
			//winkButtonTexture
			
			if(stageState[row][col] == 1){
				addButton(unlockButtonTexture, (width-3*height/4)/2+col*height/4, 
						(height-3*height/4)/2+(2-row)*height/4, height/4, height/4, "UNLOCK"+row+""+col);				
			}
			else{
				addButton(lockButtonTexture, (width-3*height/4)/2+col*height/4, 
						(height-3*height/4)/2+(2-row)*height/4, height/4, height/4, "LOCK");				
			}

		}

		addButton(backButtonTexture, 0, 0, width/6, width/12, "BACK");

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
		// TODO Auto-generated method stub
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
