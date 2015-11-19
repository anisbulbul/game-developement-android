package com.anisbulbul.car.race.splash;

import aurelienribon.tweenengine.BaseTween;
import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenCallback;
import aurelienribon.tweenengine.TweenManager;

import com.anisbulbul.car.race.CarRace3D;
import com.anisbulbul.car.race.assets.GameAssets;
import com.anisbulbul.car.race.tween.SpriteAccessor;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Splash extends GameAssets implements Screen {

	private SpriteBatch batch;
	private Sprite splash;
	private TweenManager tweenManager;
	private Music splashMusic;
	private CarRace3D game;

	public Splash(CarRace3D game) {
		this.game = game;
		splashMusic = initMusic("sounds/splash_music.mp3");
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
		splashMusic.play();
		batch = new SpriteBatch();
		tweenManager = new TweenManager();
		Tween.registerAccessor(Sprite.class, new SpriteAccessor());
		Texture splashTexture = new Texture(
				Gdx.files.internal("data/splash.png"));
		splash = new Sprite(splashTexture);
		splash.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		Tween.set(splash, SpriteAccessor.ALPHA).target(0).start(tweenManager);
		Tween.to(splash, SpriteAccessor.ALPHA, 1).target(1).repeatYoyo(1, 2)
				.setCallback(new TweenCallback() {

					@Override
					public void onEvent(int arg0, BaseTween<?> arg1) {
						splashMusic.stop();
						game.setScreen(game.menuScreen);

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

		if (splashMusic.isPlaying()) {
			splashMusic.stop();
		}
		splashMusic.dispose();
		if (batch != null)
			batch.dispose();
		if (splash != null)
			splash.getTexture().dispose();

	}
}