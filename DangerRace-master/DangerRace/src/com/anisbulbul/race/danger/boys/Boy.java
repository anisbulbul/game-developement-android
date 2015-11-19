package com.anisbulbul.race.danger.boys;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Boy {
	
	public static float boyX;
	public static float boyY;
	public static float boyW;
	public static float boyH;
	public static float boySpeedX;
	public static float boySPeedY;
	public static float boyJumpCounter;
	public static boolean boyJumpState;
	public static boolean isBoyJumpPossible;
	public static float boyIndex;
	

	public static float width = Gdx.graphics.getWidth();
	public static float height = Gdx.graphics.getHeight();
    
    /**
	 * 
	 */
	public Boy() {
		
		boyX = width/4;
		boyY = height/4;
		boyW = height/5;
		boyH = height/5;
		boySpeedX = 0;
		boySPeedY = 5;
		boyJumpCounter = 0;
		boyJumpState = false;
		isBoyJumpPossible = true;
		boyIndex = 0;

		
	}
}
