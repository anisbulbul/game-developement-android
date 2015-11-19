package com.anrosoft.mozbiemission.assets;

import com.badlogic.gdx.graphics.g2d.Animation;


public class Hero extends GameAssets{
	
	public static float heroPosX = width/2;
	public static float heroPosY = 0;
	public static float heroWidth = 5f*(width/NUMBER_OF_COLS)/10;
	public static float heroHeight = 11*(height/NUMBER_OF_ROWS)/10;
	
	public static float heroTimeIncrement = height/24000;
	public static float moveHorizontalIncrement = height/200;
	public static float heroUInit = height / 75;
		
	public static float heroV;
	public static float heroU;
	public static float heroT;
	
	public static boolean isHeroDiable = true;
	public static boolean isJumpState = false;
	public static boolean isHeroDied = false;
	
	public static Animation animation;
	public static Animation animationGlow;
	public static float elapsedTime = 0;
	public static float keyFrameDelay = 0.05f;
	public static float heroAlpha = 1.0f;
	public static int animationAtlasIndex = 0;
	public static boolean isHeroAnimationLoop = true;

	public Hero(float keyFrameDelay, int animationAtlasIndex, boolean isHeroAnimationLoop) {
		this.elapsedTime = 0;
		this.keyFrameDelay = keyFrameDelay;
		this.animationAtlasIndex = animationAtlasIndex;
		this.isHeroAnimationLoop = isHeroAnimationLoop;
		this.animation = new Animation(keyFrameDelay, GameAssets.textureHeroAtlas[animationAtlasIndex].getRegions());
		this.animationGlow = new Animation(keyFrameDelay, GameAssets.textureGlowAtlas.getRegions());
		this.isHeroDiable = true;
		this.isJumpState = false;
		this.isHeroDied = false;
		this.heroAlpha = 1.0f;
		this.heroWidth = 5f*(width/NUMBER_OF_COLS)/10;
		this.heroHeight = 11*(height/NUMBER_OF_ROWS)/10;
	}
	
	
		
}
