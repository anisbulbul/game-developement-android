package com.anrosoft.mozbiemission;

import com.anrosoft.mozbiemission.assets.GameAssets;
import com.anrosoft.mozbiemission.screen.GameScreen;
import com.anrosoft.mozbiemission.screen.LoadingScreen;
import com.anrosoft.mozbiemission.screen.MenuScreen;
import com.anrosoft.mozbiemission.screen.SoundScreen;
import com.anrosoft.mozbiemission.screen.SplashScreen;
import com.anrosoft.mozbiemission.screen.StageScreen;
import com.anrosoft.mozbiemission.screen.StoreScreen;
import com.anrosoft.mozbiemission.screen.WorldScreen;
import com.badlogic.gdx.Game;

public class ZombieMission extends Game {

	public ActionResolver actionResolver;

	// all game screens
	public SplashScreen splashScreen;
	public LoadingScreen loadingScreen;
	public MenuScreen menuScreen;
	public WorldScreen worldScreen;
	public StageScreen stageScreen;
	public GameScreen gameScreen;
	public StoreScreen storeScreen;
	public SoundScreen soundScreen;

	@Override
	public void resize(int width, int height) {
	}

	public ZombieMission(ActionResolver actionResolver) {
		this.actionResolver = actionResolver;
	}

	@Override
	public void dispose() {

		splashScreen.dispose();
		loadingScreen.dispose();
		menuScreen.dispose();
		worldScreen.dispose();
		gameScreen.dispose();
		stageScreen.dispose();
		storeScreen.dispose();
		soundScreen.dispose();
		GameAssets.disposeAllAssets();
	}

	@Override
	public void pause() {
		if (GameAssets.isBackButtonEnable) {
			if (GameAssets.GAME_STATE.equals(GameAssets.GAME_PLAY)
					&& !GameAssets.isGamePause && !GameAssets.isGameOver) {
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
			} else if (GameAssets.GAME_STATE.equals(GameAssets.GAME_PLAY)) {
				if (GameAssets.gameStageBackgroundMusic != null) {
					GameAssets.gameStageBackgroundMusic.play();
					GameAssets.gameStageBackgroundMusic
							.setVolume(GameAssets.musicVolume);
				}
			}
		}
	}

	@Override
	public void create() {

		// GameAssets.appInit();

		GameAssets.setActionResolver(actionResolver);

		splashScreen = new SplashScreen(this);
		loadingScreen = new LoadingScreen(this, actionResolver);
		menuScreen = new MenuScreen(this, actionResolver);
		worldScreen = new WorldScreen(this, actionResolver);
		stageScreen = new StageScreen(this, actionResolver);
		gameScreen = new GameScreen(this, actionResolver);
		storeScreen = new StoreScreen(this, actionResolver);
		soundScreen = new SoundScreen(this, actionResolver);

		setScreen(splashScreen);

	}
}