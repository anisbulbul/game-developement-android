package com.anrosoft.jumpinghero;

import com.anrosoft.jumpinghero.assets.GameAssets;
import com.anrosoft.jumpinghero.screen.GameScreen;
import com.anrosoft.jumpinghero.screen.LoadingScreen;
import com.anrosoft.jumpinghero.screen.MenuScreen;
import com.anrosoft.jumpinghero.screen.SoundScreen;
import com.anrosoft.jumpinghero.screen.SplashScreen;
import com.badlogic.gdx.Game;

public class JumpingHero extends Game {

	ActionResolver actionResolver;

	// all game screens
	public SplashScreen splashScreen;
	public MenuScreen menuScreen;
	public GameScreen gameScreen;
	public SoundScreen soundScreen;
	public LoadingScreen loadingScreen;

	@Override
	public void resize(int width, int height) {

	}

	public JumpingHero(ActionResolver actionResolver) {
		this.actionResolver = actionResolver;
	}

	@Override
	public void dispose() {

		splashScreen.dispose();
		menuScreen.dispose();
		gameScreen.dispose();
		soundScreen.dispose();
		loadingScreen.dispose();
		GameAssets.disposeAllAssets();
	}

	@Override
	public void pause() {
		if (GameAssets.isBackButtonEnable) {
			if (GameAssets.GAME_STATE.equals(GameAssets.GAME_MENU)) {
				if (GameAssets.backgroundMusic != null) {
					GameAssets.backgroundMusic.pause();
				}
			}
			if (!GameAssets.isGamePause
					&& !GameAssets.GAME_STATE.equals(GameAssets.GAME_START)
					&& !GameAssets.isGameOver) {
				GameAssets.isGamePauseAndroid = true;
			}
			GameAssets.pauseAllSound();
		}
	}

	@Override
	public void resume() {
		if (GameAssets.isBackButtonEnable) {
			if (GameAssets.GAME_STATE.equals(GameAssets.GAME_MENU)) {
				if (GameAssets.backgroundMusic != null) {
					GameAssets.backgroundMusic.play();
					GameAssets.backgroundMusic.setLooping(true);
					GameAssets.backgroundMusic
							.setVolume(GameAssets.musicVolume);
				}
			}
		}
	}

	@Override
	public void create() {

		// GameAssets.appInit();

		splashScreen = new SplashScreen(this);
		menuScreen = new MenuScreen(this, actionResolver);
		gameScreen = new GameScreen(this, actionResolver);
		soundScreen = new SoundScreen(this, actionResolver);
		loadingScreen = new LoadingScreen(this, actionResolver);

		setScreen(splashScreen);

	}
}