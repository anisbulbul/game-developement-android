package com.anisbulbul.car.race.tween;

import aurelienribon.tweenengine.TweenAccessor;

import com.badlogic.gdx.graphics.g2d.Sprite;

public class SpriteAccessor implements TweenAccessor<Sprite>{
	public static final int ALPHA=0;

	@Override
	public int getValues(Sprite target, int tweenType, float[] returnValue) {
		switch (tweenType) {
		case ALPHA:
			returnValue[0]=target.getColor().a;
		
			return 1;

		default:
		assert false;
		return -1;
		}
	}

	@Override
	public void setValues(Sprite target, int tweenType, float[] newValue) {
		switch (tweenType) {
		case ALPHA:
		target.setColor(target.getColor().r,target.getColor().g, target.getColor().b,newValue[0]);
			break;

		default:
			assert false;
		}
		
	}

}
