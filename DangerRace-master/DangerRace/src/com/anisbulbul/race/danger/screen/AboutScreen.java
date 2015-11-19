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


public class AboutScreen extends GameAssets implements Screen , InputProcessor{

	 private DangerRace game;
	 private ActionResolver actionResolver;
  	 private float width;
	 private float height;
	 private SpriteBatch spriteBatch;
	 private BitmapFont font;
	 private Texture aboutBackgroundTexture;
	 private Stage stage;
	 private Skin skin;


	private Texture backButtonTexture;
	protected Music aboutMusic;
	 public AboutScreen( DangerRace game, ActionResolver actionResolver){
		 
		this.game=game;
		this.actionResolver = actionResolver;
		width = Gdx.graphics.getWidth();
		height = Gdx.graphics.getHeight();
		this.spriteBatch = new SpriteBatch();
		loadComponents();
	 }
		private void loadComponents() {
			font = initFont("fonts/abc.fnt", "fonts/abc.png");
			font.setScale(width/1360, height/768);			
			backButtonTexture = initTexture("buttons/backbutton.png");
			aboutBackgroundTexture = initTexture("screen_backgrounds/aboutbackground.png");
			
			aboutMusic = initMusic("sounds/menu_music.mp3");
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
		
		spriteBatch.draw(aboutBackgroundTexture, 0, 0, width, height);
		
	}
	
	@Override
	public void resize(int width, int height) {
		stage.setViewport(width, height, false);
	}

	public void addButton(Texture buttonTextures,
			float posX, float posY, 
			float bWidth, float bHeight, 
			final String action){
		
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
				if(isGameSound){
					clickSound.play();
				}
				if(action.equals("BACK")){
					aboutMusic.stop();
					Gdx.input.setInputProcessor(null);
					game.setScreen(game.menuScreen);					
				}
			}
        });
		
	}
	
	@Override
	public void show() {
		
		if(isGameSound && !aboutMusic.isPlaying()){
			aboutMusic.play();
			aboutMusic.setLooping(true);
		}
		else if(!isGameSound && aboutMusic.isPlaying()){
			aboutMusic.stop();
		}
		
		stage = new Stage();
		
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
