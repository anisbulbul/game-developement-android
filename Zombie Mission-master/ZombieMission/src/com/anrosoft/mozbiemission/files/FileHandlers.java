package com.anrosoft.mozbiemission.files;

import com.anrosoft.mozbiemission.assets.GameAssets;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;

public class FileHandlers extends GameAssets{

	public static boolean setGameData() {

		boolean isHigh = false;
		
		if (gameScore > gameHighScore) {
			gameHighScore = gameScore;
			isHigh = true;
		}
		
		if (!Gdx.files.local("tilesmission_status.ini").exists()) {
			initFiles();
		}

		FileHandle handleTemp = Gdx.files.local("tilesmission_status.ini");
		
		if (handleTemp.exists()) {
			
			handleTemp.writeString("", false);
			handleTemp.writeString(""+gameHighScore+"d", true);
			handleTemp.writeString(""+(gameTotalScore+gameScore)+"d", true);
			handleTemp.writeString(""+noOfGamePlays+"d", true);
			handleTemp.writeString(""+gameWorldStatus+"d", true);
			handleTemp.writeString(gameStagePerWorldStatus[0]+"d", true);
			handleTemp.writeString(gameStagePerWorldStatus[1]+"d", true);
			handleTemp.writeString(gameStagePerWorldStatus[2]+"d", true);
			handleTemp.writeString(heroStoreStatus+"d", true);
			handleTemp.writeString(heroAtlasIndex+"d", true);
			handleTemp.writeString(musicVolume+"d", true);
			handleTemp.writeString(actionsVolume+"d", true);
			handleTemp.writeString(clicksVolume+"d", true);
			handleTemp.writeString(allVolume+"d", true);
			
		}		
		return isHigh;
	}

	public static void initGameData() {

		if (!Gdx.files.local("tilesmission_status.ini").exists()) {
			initFiles();
		}

		FileHandle handle = Gdx.files.local("tilesmission_status.ini");
		if (handle.exists()) {

			String[] gameDatas = handle.readString().split("d");
			
			gameHighScore = Integer.parseInt(gameDatas[0].trim());
			gameTotalScore = Integer.parseInt(gameDatas[1].trim());
			noOfGamePlays = Integer.parseInt(gameDatas[2].trim());
			gameWorldStatus = gameDatas[3].trim();
			if(gameWorldStatus.length()!=NUMBER_OF_WORLDS){
				gameWorldStatus = "11000000";
			}
			
			gameStagePerWorldStatus[0] = gameDatas[4].trim();
			if(gameStagePerWorldStatus[0].length() != NUMBER_OF_STAGES){
				gameStagePerWorldStatus[0] = "1100000000";
			}
			
			gameStagePerWorldStatus[1] = gameDatas[5].trim();
			if(gameStagePerWorldStatus[1].length() != NUMBER_OF_STAGES){
				gameStagePerWorldStatus[1] = "1100000000";
			}
			
			gameStagePerWorldStatus[2] = gameDatas[6].trim();
			if(gameStagePerWorldStatus[2].length() != NUMBER_OF_STAGES){
				gameStagePerWorldStatus[2] = "1100000000";
			}
			heroStoreStatus = gameDatas[7].trim();
			heroAtlasIndex = Integer.parseInt(gameDatas[8].trim());
			musicVolume = Float.parseFloat(gameDatas[9].trim());
			actionsVolume = Float.parseFloat(gameDatas[10].trim());
			clicksVolume = Float.parseFloat(gameDatas[11].trim());
			allVolume = Float.parseFloat(gameDatas[12].trim());
			
		}

	}

	private static void initFiles() {

		Gdx.files.internal("file/tilesmission_status.ini").copyTo(
				Gdx.files.local("tilesmission_status.ini"));

		FileHandle handleTemp = Gdx.files.local("tilesmission_status.ini");

		if (handleTemp.exists()) {

			handleTemp.writeString("", false);
			handleTemp.writeString("000d", true);
			handleTemp.writeString("000d", true);
			handleTemp.writeString("000d", true);
			handleTemp.writeString("100d", true);
			handleTemp.writeString(""+1000000000+"d", true);
			handleTemp.writeString(""+1000000000+"d", true);
			handleTemp.writeString(""+1000000000+"d", true);
			handleTemp.writeString(""+1000+"d", true);
			handleTemp.writeString(""+0+"d", true);
			handleTemp.writeString(""+1+"d", true);
			handleTemp.writeString(""+1+"d", true);
			handleTemp.writeString(""+1+"d", true);
			handleTemp.writeString(""+1+"d", true);

		}

	}

}
