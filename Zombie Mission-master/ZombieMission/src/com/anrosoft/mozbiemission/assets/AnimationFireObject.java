package com.anrosoft.mozbiemission.assets;

import com.badlogic.gdx.graphics.g2d.Animation;

public class AnimationFireObject{
	
	public float animationFirePosX;
	public float animationFirePosY;
	
	public float animationFireWidth;
	public float animationFireHeight;
	
	public float animationFireSpeedX;
	public float animationFireSpeedY;
	
	public float elapsedTime = 0;
	public float keyFrameDelay = 0.05f;
	public float animationFireWalkLength = 0;
	public float animationFireWalkLengthTemp = 0;
	
	public int animationFireAtlasIndex;
	
	public boolean isAnimationFireEnemy;
	public boolean isAnimationFireLoop;
	
	public float animAplha;
	
	public Animation animation;

	public AnimationFireObject(float animationFirePosX, float animationFirePosY,
			float animationFireWidth, float animationFireHeight, float animationFireSpeedX,
			float animationFireSpeedY, int animationFireAtlasIndex,
			boolean isanimationFireEnemy, float keyFrameDelay, boolean isanimationFireLoop,
			float animAplha, float animationFireWalkLength) {
		super();
		this.animationFirePosX = animationFirePosX;
		this.animationFirePosY = animationFirePosY;
		this.animationFireWidth = animationFireWidth;
		this.animationFireHeight = animationFireHeight;
		this.animationFireSpeedX = animationFireSpeedX;
		this.animationFireSpeedY = animationFireSpeedY;
		this.animationFireAtlasIndex = animationFireAtlasIndex;
		this.isAnimationFireEnemy = isanimationFireEnemy;
		this.keyFrameDelay = keyFrameDelay;
		this.isAnimationFireLoop = isanimationFireLoop;
		this.animAplha = animAplha;
		this.animationFireWalkLength = animationFireWalkLength;
		this.animationFireWalkLengthTemp = animationFireWalkLength;
		this.animation = new Animation(keyFrameDelay, GameAssets.textureAnimFireAtlas.getRegions());

	}
	

	
	
}
