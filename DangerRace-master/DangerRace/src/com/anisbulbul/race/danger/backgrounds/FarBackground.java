package com.anisbulbul.race.danger.backgrounds;

import com.anisbulbul.race.danger.assets.GameAssets;
import com.badlogic.gdx.Gdx;

public class FarBackground extends GameAssets{
	
	public static float farBackgroundX;
	public static float farBackgroundY;
	public static float farBackgroundSpeedX;
	public static float farBackgroundSpeddY;
	
	private float width = Gdx.graphics.getWidth();
	private float height = Gdx.graphics.getHeight();
	
	/**
	 * 
	 */
	public FarBackground() {
		farBackgroundX = 0;
		farBackgroundY = 0;
		farBackgroundSpeedX = FAR_BACKGROUNDSPEED_SPEED_X;
		farBackgroundSpeddY = 0;
	}

	
}
