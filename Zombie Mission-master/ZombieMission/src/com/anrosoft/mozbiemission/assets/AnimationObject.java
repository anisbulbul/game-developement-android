package com.anrosoft.mozbiemission.assets;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;

public class AnimationObject{
	
	public float animationPosX;
	public float animationPosY;
	
	public float animationWidth;
	public float animationHeight;
	
	public float animationSpeedX;
	public float animationSpeedY;
	
	public float elapsedTime = 0;
	public float keyFrameDelay = 0.05f;
	public float animationWalkLength = 0;
	public float animationWalkLengthTemp = 0;
	
	public int animationAtlasIndex;
	
	public boolean isAnimationEnemy;
	public boolean isAnimationLoop;
	
	public float animAplha;
	
	public Animation animation;

	public AnimationObject(float animationPosX, float animationPosY,
			float animationWidth, float animationHeight, float animationSpeedX,
			float animationSpeedY, int animationAtlasIndex,
			boolean isAnimationEnemy, float keyFrameDelay, boolean isAnimationLoop,
			float animAplha, float animationWalkLength) {
		super();
		this.animationPosX = animationPosX;
		this.animationPosY = animationPosY;
		this.animationWidth = animationWidth;
		this.animationHeight = animationHeight;
		this.animationSpeedX = animationSpeedX;
		this.animationSpeedY = animationSpeedY;
		this.animationAtlasIndex = animationAtlasIndex;
		this.isAnimationEnemy = isAnimationEnemy;
		this.keyFrameDelay = keyFrameDelay;
		this.isAnimationLoop = isAnimationLoop;
		this.animAplha = animAplha;
		this.animationWalkLength = animationWalkLength;
		this.animationWalkLengthTemp = animationWalkLength;
//		this.animationWalkLengthTemp = 3*(Gdx.graphics.getWidth()/GameAssets.NUMBER_OF_COLS);
		this.animation = new Animation(keyFrameDelay, GameAssets.textureAnimAtlas[animationAtlasIndex].getRegions());

	}
	

	
	
}
