package com.anrosoft.jumpinghero.files;

import com.anrosoft.jumpinghero.assets.GameAssets;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;

public class FileHandlers extends GameAssets{

	public static boolean setGameData() {

		boolean isHigh = false;
		
		if (gameScore > gameHighScore) {
			gameHighScore = gameScore;
			isHigh = true;
		}
		
		if (!Gdx.files.local("jumpinghero_status.ini").exists()) {
			initFiles();
		}

		FileHandle handleTemp = Gdx.files.local("jumpinghero_status.ini");
		
		if (handleTemp.exists()) {
			
			handleTemp.writeString("", false);
			handleTemp.writeString(""+gameHighScore+"d", true);
			handleTemp.writeString(""+noOfGamePlays+"d", true);
			handleTemp.writeString(""+musicVolume+"d", true);
			handleTemp.writeString(""+actionsVolume+"d", true);
			handleTemp.writeString(""+clicksVolume+"d", true);
			handleTemp.writeString(""+allVolume+"d", true);
			
		}		
		return isHigh;
	}

	public static void initGameData() {

		if (!Gdx.files.local("jumpinghero_status.ini").exists()) {
			initFiles();
		}

		FileHandle handle = Gdx.files.local("jumpinghero_status.ini");
		if (handle.exists()) {

			String[] gameDatas = handle.readString().split("d");
			
			gameHighScore = Integer.parseInt(gameDatas[0].trim());
			noOfGamePlays = Integer.parseInt(gameDatas[1].trim());
			musicVolume = Float.parseFloat(gameDatas[2].trim());
			actionsVolume = Float.parseFloat(gameDatas[3].trim());
			clicksVolume = Float.parseFloat(gameDatas[4].trim());
			allVolume = Float.parseFloat(gameDatas[5].trim());
			
			
		}

	}

	private static void initFiles() {

		Gdx.files.internal("file/jumpinghero_status.ini").copyTo(
				Gdx.files.local("jumpinghero_status.ini"));

		FileHandle handleTemp = Gdx.files.local("jumpinghero_status.ini");

		if (handleTemp.exists()) {

			handleTemp.writeString("", false);
			for (int i = 0; i < 2; i++) {
				handleTemp.writeString("000d", true);
			}
			for (int i = 2; i < 6; i++) {
				handleTemp.writeString("1.0d", true);
			}

		}

	}

}
