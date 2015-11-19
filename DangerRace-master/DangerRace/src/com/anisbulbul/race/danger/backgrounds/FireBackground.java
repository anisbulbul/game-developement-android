package com.anisbulbul.race.danger.backgrounds;

import com.anisbulbul.race.danger.assets.GameAssets;
import com.badlogic.gdx.Gdx;

public class FireBackground extends GameAssets{
	
	public static float fireBackgroundX;
	public static float fireBackgroundY;
	public static float fireBackgroundSpeedX;
	public static float fireBackgroundSpeddY;
	
	private float width = Gdx.graphics.getWidth();
	private float height = Gdx.graphics.getHeight();
	
	/**
	 * 
	 */
	public FireBackground() {
		fireBackgroundX = 0;
		fireBackgroundY = height-height/8;
		fireBackgroundSpeedX = FIRE_BACKGROUNDSPEED_SPEED_X;
		fireBackgroundSpeddY = 0;
	}

	
}
