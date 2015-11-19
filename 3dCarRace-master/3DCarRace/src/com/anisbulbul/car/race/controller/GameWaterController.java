package com.anisbulbul.car.race.controller;

import com.anisbulbul.car.race.assets.GameAssets;
import com.anisbulbul.car.race.model.GameWater;
import com.anisbulbul.car.race.model.GameWorld;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;

public class GameWaterController extends GameAssets {
	private GameWorld wrold;
	GameWater gameWater;
	float x, y;
	float width;
	float height;

	public GameWaterController(GameWorld wrold) {
		super();
		this.wrold = wrold;
		width = Gdx.graphics.getWidth();
		height = Gdx.graphics.getHeight();
		gameWater = this.wrold.getWater();
		y = 0;
		x = 0;
	}

	public void update(float delta) {
		processingInput();
		gameWater.update(delta);
		if (GAME_STATE == GAME_START && !isGamePause) {
			y -= backgroundSpeed / 10;
			if (y + 3 * height < 0) {
				y = y + 2 * height - backgroundSpeed;
			}

		}

	}

	private void processingInput() {

		gameWater.setPosition(new Vector2(x, y));

	}

}
