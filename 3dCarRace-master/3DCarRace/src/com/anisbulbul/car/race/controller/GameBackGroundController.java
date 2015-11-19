package com.anisbulbul.car.race.controller;

import com.anisbulbul.car.race.assets.GameAssets;
import com.anisbulbul.car.race.model.GameBackGround;
import com.anisbulbul.car.race.model.GameWorld;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;

public class GameBackGroundController extends GameAssets {
	private GameWorld wrold;
	GameBackGround backGround;
	float x, y;
	float width;
	float height;

	public GameBackGroundController(GameWorld wrold) {
		super();
		this.wrold = wrold;
		width = Gdx.graphics.getWidth();
		height = Gdx.graphics.getHeight();
		backGround = this.wrold.getBackGround();
		y = 0;
		x = width / 3;
	}

	public void update(float delta) {
		processingInput();
		backGround.update(delta);

		if (!isGamePause) {
			y -= backgroundSpeed;
			if (y + 3 * height < 0) {
				y = y + 2 * height - backgroundSpeed;
			}
		}

	}

	private void processingInput() {

		backGround.setPosition(new Vector2(x, y));

	}

}
