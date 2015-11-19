package com.anisbulbul.race.danger.backgrounds;

import com.anisbulbul.race.danger.assets.GameAssets;
import com.badlogic.gdx.Gdx;

public class NearBackground extends GameAssets{
	
	public static float nearBackgroundX;
	public static float nearBackgroundY;
	public static float nearBackgroundSpeedX;
	public static float nearBackgroundSpeddY;
	
	private float width = Gdx.graphics.getWidth();
	private float height = Gdx.graphics.getHeight();
	
	/**
	 * 
	 */
	public NearBackground() {
		nearBackgroundX = 0;
		nearBackgroundY = 0;
		nearBackgroundSpeedX = NEAR_BACKGROUNDSPEED_SPEED_X;
		nearBackgroundSpeddY = 0;
	}

	
}
