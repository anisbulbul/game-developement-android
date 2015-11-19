package com.anrosoft.mozbiemission.screen;

import com.anrosoft.mozbiemission.ActionResolver;
import com.anrosoft.mozbiemission.ZombieMission;
import com.anrosoft.mozbiemission.assets.GameAssets;
import com.anrosoft.mozbiemission.model.GameWorld;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class LoadingScreen extends GameAssets implements Screen, InputProcessor {

	private GameWorld world;
	private ZombieMission game;

	private SpriteBatch spriteBatch;

	private ActionResolver actionResolver;

	private float initStatus;

	private Texture loadingScreenTexture;

	public LoadingScreen(ZombieMission game, ActionResolver actionResolver) {
		this.game = game;
		this.spriteBatch = new SpriteBatch();
		this.actionResolver = actionResolver;
		this.initStatus = 0.0f;
		loadingScreenTexture = initTexture("backgrounds/loadingscreenbackground.png");
	}

	@Override
	public void render(float delta) {

		Gdx.gl.glClearColor(0.1f, 0.1f, 0.1f, 1);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);

		spriteBatch.begin();
		spriteBatch.enableBlending();

		drawBackground();

		if (initStatus < 1) {
			initStatus += 0.05f;
		} else if (initStatus >= 1 && initStatus < 4) {
			initStatus = 4;
			appInit();
			game.setScreen(game.menuScreen);
		}

		spriteBatch.end();
		spriteBatch.disableBlending();

	}

	private void drawBackground() {

		spriteBatch.draw(loadingScreenTexture, 0, 0, width, height);

	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
	}

	@Override
	public void show() {

		world = new GameWorld();
		actionResolver.showOrLoadInterstital();
		initStatus = 0.0f;
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
