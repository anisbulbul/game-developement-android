package com.anisbulbul.car.race;

import com.anisbulbul.car.race.assets.GameAssets;
import com.anisbulbul.car.race.screen.AboutScreen;
import com.anisbulbul.car.race.screen.HelpScreen;
import com.anisbulbul.car.race.screen.MenuScreen;
import com.anisbulbul.car.race.screen.RaceScreen;
import com.anisbulbul.car.race.screen.ScoreScreen;
import com.anisbulbul.car.race.screen.SettingScreen;
import com.anisbulbul.car.race.splash.Splash;
import com.badlogic.gdx.Game;

public class CarRace3D extends Game {

	ActionResolver actionResolver;

	// game screens
	public Splash splashScreen;
	public MenuScreen menuScreen;
	public ScoreScreen scoreScreen;
	public HelpScreen helpScreen;
	public SettingScreen settingScreen;
	public AboutScreen aboutScreen;
	public RaceScreen raceScreen;

	public CarRace3D(ActionResolver actionResolver) {
		this.actionResolver = actionResolver;
	}

	@Override
	public void resize(int width, int height) {

	}

	@Override
	public void dispose() {

		menuScreen.dispose();
		aboutScreen.dispose();
		settingScreen.dispose();
		splashScreen.dispose();
		scoreScreen.dispose();
		helpScreen.dispose();
		raceScreen.dispose();
	}

	@Override
	public void pause() {

		GameAssets.isGamePause = true;

	}

	@Override
	public void resume() {
		GameAssets.isGamePause = true;
	}

	@Override
	public void create() {

		splashScreen = new Splash(this);
		menuScreen = new MenuScreen(this, actionResolver);
		scoreScreen = new ScoreScreen(this, actionResolver);
		helpScreen = new HelpScreen(this, actionResolver);
		settingScreen = new SettingScreen(this, actionResolver);
		aboutScreen = new AboutScreen(this, actionResolver);
		raceScreen = new RaceScreen(this, actionResolver);

		setScreen(splashScreen);

	}
}