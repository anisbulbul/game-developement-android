package com.anisbulbul.race.danger.files;

import com.anisbulbul.race.danger.assets.GameAssets;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;

public class FileHandlers extends GameAssets {

	public static void initStage() {

		if (!Gdx.files.local("stage.ini").exists()) {
			initStageFile();
		}

		FileHandle handleTemp = Gdx.files.local("stage.ini");

		if (handleTemp.exists()) {

			String[] hiScore = handleTemp.readString().split(" ");
			if (hiScore.length != 9) {
				for (int i = 0; i < 3; i++) {
					for (int j = 0; j < 3; j++) {
						if (i == 0 && j == 0) {
							stageState[i][j] = 1;
						} else {
							stageState[i][j] = 0;
						}
					}
				}
			}
			for (int i = 0; i < 3; i++) {
				for (int j = 0; j < 3; j++) {
					stageState[i][j] = Integer.parseInt(hiScore[i + j * 3]);
				}
			}
		}
	}

	public static void setStage() {

		if (!Gdx.files.local("stage.ini").exists()) {
			initStageFile();
		}
		int indexX = 0;
		FileHandle handle = Gdx.files.local("stage.ini");

		if (handle.exists()) {
			String[] hiScore = handle.readString().split(" ");
			if (hiScore.length == 9) {
				for (int i = 0; i < 3; i++) {
					for (int j = 0; j < 3; j++) {
						if (Integer.parseInt(hiScore[i + j * 3]) == 0) {
							indexX = i + j * 3;
							i = j = 3;
						}
					}
				}
			}
			FileHandle handleTemp = Gdx.files.local("stage.ini");

			if (handleTemp.exists()) {

				handleTemp.writeString("1", false);
				for (int i = 1; i < 9; i++) {
					if (indexX == i) {
						handleTemp.writeString(" 1", true);
					} else {
						handleTemp.writeString(" " + hiScore[i], true);
					}
				}

			}
		}
	}

	public static int getScore() {

		int score = 0;

		if (!Gdx.files.local("score.ini").exists()) {
			initScoreFile();
		}

		FileHandle handleTemp = Gdx.files.local("score.ini");

		if (handleTemp.exists()) {

			score = Integer.parseInt(handleTemp.readString());

		}

		return score;
	}

	public static void setScore(int tempScore) {

		if (getScore() < tempScore) {

			if (!Gdx.files.local("score.ini").exists()) {
				initScoreFile();
			}

			FileHandle handle = Gdx.files.local("score.ini");

			if (handle.exists()) {

				handle.writeString(tempScore + "", false);

			}
		}

	}

	private static void initStageFile() {

		Gdx.files.internal("file/stage.ini").copyTo(
				Gdx.files.local("stage.ini"));

		FileHandle handleTemp = Gdx.files.local("stage.ini");

		if (handleTemp.exists()) {

			handleTemp.writeString("1", false);
			for (int i = 0; i < 8; i++) {
				handleTemp.writeString(" 0", true);
			}

		}

	}

	private static void initScoreFile() {

		Gdx.files.internal("file/score.ini").copyTo(
				Gdx.files.local("score.ini"));

		FileHandle handleTemp = Gdx.files.local("score.ini");

		if (handleTemp.exists()) {

			handleTemp.writeString("0", false);

		}

	}
}
