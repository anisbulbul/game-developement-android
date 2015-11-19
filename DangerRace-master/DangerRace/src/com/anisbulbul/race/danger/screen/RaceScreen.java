package com.anisbulbul.race.danger.screen;

import com.anisbulbul.race.danger.ActionResolver;
import com.anisbulbul.race.danger.DangerRace;
import com.anisbulbul.race.danger.assets.GameAssets;
import com.anisbulbul.race.danger.boys.Boy;
import com.anisbulbul.race.danger.files.FileHandlers;
import com.anisbulbul.race.danger.model.GameWorld;
import com.anisbulbul.race.danger.renderer.GameWorldRenderer;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class RaceScreen extends GameAssets implements Screen, InputProcessor {

	private GameWorld world;
	private GameWorldRenderer render;
	private DangerRace game;
	private ActionResolver actionResolver;
	private float width;
	private float height;
	private SpriteBatch spriteBatch;

	private BitmapFont font;
	private BitmapFont font2;

	private Music backgroundMusic;

	
	public RaceScreen(DangerRace game, ActionResolver actionResolver) {

		this.game = game;
		this.actionResolver = actionResolver;
		width = Gdx.graphics.getWidth();
		height = Gdx.graphics.getHeight();
		spriteBatch = new SpriteBatch();
		
		loadComponents();
	}

	private void loadComponents() {
		font = initFont("fonts/abc.fnt", "fonts/abc.png");
		font.setScale((height / font.getBounds("A").height) / 15);

		font2 = initFont("fonts/abc.fnt", "fonts/abc.png");
		font2.setScale(0.5f, 0.5f);

		backgroundMusic = initMusic("sounds/background_music.mp3");
		
		
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0.1f, 0.1f, 0.1f, 1);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		
		if(Gdx.input.justTouched()){
			
			if(isGameRaceWin){
				isGameRaceWin = false;
				if(stageStateIndex){
					FileHandlers.setStage();
					FileHandlers.initStage();					
				}
				if(backgroundMusic.isPlaying()){
					backgroundMusic.stop();
				}
				if(winMusic.isPlaying()){
					winMusic.stop();
				}
				Gdx.input.setInputProcessor(null);
                game.setScreen(game.stageScreen);
			}
			else if(isGameRaceFailed){
				isGameRaceFailed = false;
				if(backgroundMusic.isPlaying()){
					backgroundMusic.stop();
				}
				if(winMusic.isPlaying()){
					winMusic.stop();
				}
				Gdx.input.setInputProcessor(null);
                game.setScreen(game.stageScreen);
			}

		}
		else if(!isGamePause && Boy.isBoyJumpPossible && Gdx.input.isTouched()){
			Boy.boyJumpState = true;
			Boy.boyY += (Boy.boySPeedY-9.8f*Boy.boyJumpCounter);
			Boy.boyJumpCounter+=0.005f;
		}
		else if(!isGamePause && Boy.boyJumpState == true){
			Boy.boyJumpState = false;
			Boy.isBoyJumpPossible = false;

			if((Boy.boySPeedY+9.8f*Boy.boyJumpCounter)<=0){
				Boy.boyY += (Boy.boySPeedY-9.8f*Boy.boyJumpCounter);
				Boy.boyJumpCounter+=0.005f;				
			}
			
//			Boy.boySPeedY+9.8f*Boy.boyJumpCounter = ;
			Boy.boyJumpCounter = -Boy.boySPeedY/9.8f+3/9.8f;

		}
		if(Gdx.input.isTouched()){
			if(isGamePause){
				isGamePause = false;
				if(isGameSound){
					backgroundMusic.play();
				}
			}
			if(Gdx.input.getX() < height/8 && height-Gdx.input.getY() < height/8){
				if(!isGamePause){
					isGamePause = true;
					if(isGameSound){
						if(winMusic.isPlaying()){
							winMusic.pause();
						}
						if(backgroundMusic.isPlaying()){
							backgroundMusic.pause();
						}
						fireSound.stop();
						pointSound.stop();
						fireSound.stop();
					}
				}

			}
		}
		if(!isGamePause && !Boy.boyJumpState && (Boy.boySPeedY+9.8f*Boy.boyJumpCounter)>=0){
			Boy.boyY -= (Boy.boySPeedY+9.8f*Boy.boyJumpCounter);
			Boy.boyJumpCounter += 0.005f;				
		}
		
		render.render();
		
		spriteBatch.begin();
		spriteBatch.enableBlending();
		

		spriteBatch.end();
		spriteBatch.disableBlending();

	}



	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
		render.setSize(width, height);

	}

	@Override
	public void show() {


		if (isGameSound && !backgroundMusic.isPlaying()) {
			backgroundMusic.play();
			backgroundMusic.setLooping(true);
			backgroundMusic.setVolume(1);
		} else if (!isGameSound && backgroundMusic.isPlaying()) {
			backgroundMusic.stop();
		}
		
		world = new GameWorld();
		render = new GameWorldRenderer(world, true);

		Gdx.input.setInputProcessor(this);
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
