package com.anrosoft.jetadventure.assets;

import com.badlogic.gdx.graphics.g2d.Animation;


public class Hero extends GameAssets{
	
	public static float heroPosX = width/2;
	public static float heroPosY = height/2;
	public static float heroWidth = width/10;
	public static float heroHeight = 1.5f*heroWidth;
	
	public static float heroTimeIncrement = height/30000;
	public static float moveHorizontalIncrement = height/200;
	public static float heroUInit = height / 125;
		
	public static float heroV;
	public static float heroU;
	public static float heroT;
	
	public static boolean isHeroDiable = true;
	public static boolean isJumpState = false;
	public static boolean isHeroDied = false;
	
	public static Animation animation;
	public static float elapsedTime = 0;
	public static float keyFrameDelay = 0.05f;
	public static float heroAlpha = 1.0f;
	public static boolean isHeroAnimationLoop = true;

	public Hero(float keyFrameDelay, boolean isHeroAnimationLoop) {
		this.elapsedTime = 0;
		this.keyFrameDelay = keyFrameDelay;
		this.isHeroAnimationLoop = isHeroAnimationLoop;
		this.animation = new Animation(keyFrameDelay, GameAssets.textureHeroAtlas[0].getRegions());
		this.isHeroDiable = true;
		this.isJumpState = false;
		this.isHeroDied = false;
		this.heroAlpha = 1.0f;
		this.heroWidth = width/10;
		this.heroHeight = 1.5f*heroWidth;
		this.heroPosX = width/2 - this.heroWidth/2;
		this.heroPosY = height/2 - this.heroHeight/2;
	}
	
	
		
}
