package com.anisbulbul.car.race.collision;

public class CollisionCheck {

	public static boolean isCollisionOccuredRectaPoint(float x0, float y0,
			float xw, float yh, float px, float py) {
		if (x0 < px && px < x0 + xw && y0 < py && py < y0 + yh) {
			return true;
		}
		return false;
	}

	public static boolean isCollisionOccuredRectaPoint2x1(float x1, float y1,
			float x2, float y2, float x, float y) {
		if (x1 < x && x < x2 && y1 < y && y < y2) {
			return true;
		}
		return false;
	}

	public static boolean isCollisionOccuredRectaPoint2x2(float x1, float y1,
			float x2, float y2, float x3, float y3, float x4, float y4) {

		float x5 = x4;
		float y5 = y3;
		float x6 = x3;
		float y6 = y4;

		if ((x1 < x3 && x3 < x2 && y1 < y3 && y3 < y2)
				|| (x1 < x4 && x4 < x2 && y1 < y4 && y4 < y2)
				|| (x1 < x5 && x5 < x2 && y1 < y5 && y5 < y2)
				|| (x1 < x6 && x6 < x2 && y1 < y6 && y6 < y2)) {
			return true;
		}
		return false;
	}
}
