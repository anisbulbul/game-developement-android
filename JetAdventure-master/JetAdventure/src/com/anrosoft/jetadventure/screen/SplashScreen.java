package com.anrosoft.jetadventure.screen;

import aurelienribon.tweenengine.BaseTween;
import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenCallback;
import aurelienribon.tweenengine.TweenManager;

import com.anrosoft.jetadventure.JetAdventure;
import com.anrosoft.jetadventure.assets.GameAssets;
import com.anrosoft.jetadventure.tween.SpriteAccessor;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class SplashScreen extends GameAssets implements Screen {

	private SpriteBatch batch;
	private Sprite splash;
	private TweenManager tweenManager;
	private JetAdventure game;
	private Texture splashScreenTexture;

	public SplashScreen(JetAdventure game) {
		this.game = game;
		this.splashScreenTexture = initTexture("others/splash.png");
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		tweenManager.update(delta);
		batch.begin();
		splash.draw(batch);
		batch.end();

	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub

	}

	@Override
	public void show() {

		batch = new SpriteBatch();
		tweenManager = new TweenManager();
		Tween.registerAccessor(Sprite.class, new SpriteAccessor());
		splash = new Sprite(splashScreenTexture);
		splash.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		Tween.set(splash, SpriteAccessor.ALPHA).target(0).start(tweenManager);
		Tween.to(splash, SpriteAccessor.ALPHA, 1).target(1).repeatYoyo(1, 2)
				.setCallback(new TweenCallback() {

					@Override
					public void onEvent(int arg0, BaseTween<?> arg1) {
						game.setScreen(game.loadingScreen);

					}
				}).start(tweenManager);
		// Tween.to(splash, SpriteAccessor.ALPHA,
		// 2).target(0).delay(2).start(tweenManager);
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

	}
}