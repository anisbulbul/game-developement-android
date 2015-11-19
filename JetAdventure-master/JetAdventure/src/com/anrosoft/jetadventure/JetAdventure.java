package com.anrosoft.jetadventure;

import com.anrosoft.jetadventure.assets.GameAssets;
import com.anrosoft.jetadventure.screen.GameScreen;
import com.anrosoft.jetadventure.screen.LoadingScreen;
import com.anrosoft.jetadventure.screen.MenuScreen;
import com.anrosoft.jetadventure.screen.SoundScreen;
import com.anrosoft.jetadventure.screen.SplashScreen;
import com.badlogic.gdx.Game;

public class JetAdventure extends Game {

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

	public JetAdventure(ActionResolver actionResolver) {
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
					GameAssets.backgroundMusic
							.setVolume(GameAssets.musicVolume);
				}
			} else if (GameAssets.GAME_STATE.equals(GameAssets.GAME_START)) {
				if (GameAssets.gameStageBackgroundMusic != null) {
					GameAssets.gameStageBackgroundMusic.play();
					GameAssets.gameStageBackgroundMusic
							.setVolume(GameAssets.musicVolume / 2);
				}
			}
		}
	}

	@Override
	public void create() {

		splashScreen = new SplashScreen(this);
		menuScreen = new MenuScreen(this, actionResolver);
		gameScreen = new GameScreen(this, actionResolver);
		soundScreen = new SoundScreen(this, actionResolver);
		loadingScreen = new LoadingScreen(this, actionResolver);

		setScreen(splashScreen);

	}
}