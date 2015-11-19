package com.anisbulbul.race.danger;

import com.anisbulbul.race.danger.assets.GameAssets;
import com.anisbulbul.race.danger.screen.AboutScreen;
import com.anisbulbul.race.danger.screen.HelpScreen;
import com.anisbulbul.race.danger.screen.MenuScreen;
import com.anisbulbul.race.danger.screen.RaceScreen;
import com.anisbulbul.race.danger.screen.ScoreScreen;
import com.anisbulbul.race.danger.screen.SettingScreen;
import com.anisbulbul.race.danger.screen.StageScreen;
import com.anisbulbul.race.danger.splash.Splash;
import com.badlogic.gdx.Game;

public class DangerRace extends Game {
	
	ActionResolver actionResolver;
	
	//all game screens 
	public Splash splashScreen;
	public MenuScreen menuScreen;
	public HelpScreen helpScreen;
	public ScoreScreen scoreScreen;
	public SettingScreen settingScreen;
	public AboutScreen aboutScreen;
	public StageScreen stageScreen;
	public RaceScreen raceScreen;
	
	public DangerRace(ActionResolver actionResolver) {
		this.actionResolver = actionResolver;
	}
	
	@Override
	public void resize(int width, int height) {
		
	}

	@Override
	public void dispose() {

		menuScreen.dispose();
		aboutScreen.dispose();
		scoreScreen.dispose();
		settingScreen.dispose();
		splashScreen.dispose();
		helpScreen.dispose();
		raceScreen.dispose();
		stageScreen.dispose();
	}


	@Override
	public void pause() {

		GameAssets.isGamePause  = true;
		
	}

	@Override
	public void resume() {
		GameAssets.isGamePause  = true;
//		setScreen(loadingScreen);
	}
	
	@Override
	public void create() {

		splashScreen = new Splash(this);
		menuScreen = new MenuScreen(this, actionResolver);
		helpScreen = new HelpScreen(this, actionResolver);
		scoreScreen = new ScoreScreen(this, actionResolver);
		settingScreen = new SettingScreen(this, actionResolver);
		aboutScreen = new AboutScreen(this, actionResolver);
		raceScreen= new RaceScreen(this, actionResolver);
		stageScreen = new StageScreen(this, actionResolver);
		setScreen(menuScreen);

	}
}