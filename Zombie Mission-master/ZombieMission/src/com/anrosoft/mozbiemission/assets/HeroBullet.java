package com.anrosoft.mozbiemission.assets;

import com.badlogic.gdx.graphics.g2d.Animation;


public class HeroBullet {
	
	public float bulletPosX;
	public float bulletPosY;
	
	public float bulletWidth;
	public float bulletHeight;
	
	public float bulletSpeedX;
	public float bulletSpeedY;
	
	public float elapsedTime = 0;
	public float keyFrameDelay = 0.05f;
	
	public int animationAtlasIndex;
	
	public int bulletAtlasIndex;
	
	public float bulletAplha;
	
	public Animation animation;

	public HeroBullet(float bulletPosX, float bulletPosY, float bulletWidth,
			float bulletHeight, float bulletSpeedX, float bulletSpeedY,
			int bulletAtlasIndex, float assetAplha, float keFrameDelay) {
		super();
		this.bulletPosX = bulletPosX;
		this.bulletPosY = bulletPosY;
		this.bulletWidth = bulletWidth;
		this.bulletHeight = bulletHeight;
		this.bulletSpeedX = bulletSpeedX;
		this.bulletSpeedY = bulletSpeedY;
		this.bulletAtlasIndex = bulletAtlasIndex;
		this.bulletAplha = assetAplha;
		this.animation = new Animation(keyFrameDelay, GameAssets.textureBulletAtlas[bulletAtlasIndex].getRegions());

	}
	
}
