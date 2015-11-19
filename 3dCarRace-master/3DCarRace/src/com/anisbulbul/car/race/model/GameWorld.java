package com.anisbulbul.car.race.model;

import com.badlogic.gdx.math.Vector2;

public class GameWorld {

	GameBackGround backGround;
	GameWater water;

	public GameBackGround getBackGround() {
		return backGround;
	}

	public GameWater getWater() {
		// TODO Auto-generated method stub
		return water;
	}

	public GameWorld() {
		super();
		createGameWorld();
	}

	private void createGameWorld() {

		backGround = new GameBackGround(new Vector2(1.00f, 1.00f));
		water = new GameWater(new Vector2(1.00f, 1.00f));

	}

}
