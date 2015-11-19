package com.anisbulbul.car.race.files;

import java.util.Arrays;

import com.anisbulbul.car.race.assets.GameAssets;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;

public class FileHandlers extends GameAssets {

	public static int setHiScore(Long score) {

		long[] scoreArray = getHiScore();
		int returnValue = -1;
		if (score > scoreArray[0]) {
			if (!Gdx.files.local("race_status.ini").exists()) {
				initFiles();
			}
//
//			System.out.println("set : ");
//			System.out.println(score);
//			for(int i=0 ; i<scoreArray.length ; i++){
//				System.out.print(scoreArray[i]+" ");
//			}
//			
			scoreArray[0] = score;
			Arrays.sort(scoreArray);
//
//			System.out.println("sort : ");
//			for(int i=0 ; i<scoreArray.length ; i++){
//				System.out.print(scoreArray[i]+" ");
//			}
			
			FileHandle handleTemp = Gdx.files.local("race_status.ini");
			if (handleTemp.exists()) {

				handleTemp.writeString("", false);
				for (int i = 0; i < 5; i++) {
					handleTemp.writeString(scoreArray[i] + "\n", true);
					if (score == scoreArray[i]) {
						returnValue = 5 - i;
					}
//					System.out.println("add : "+scoreArray[i]);
				}

			}
		}
		return returnValue;
	}

	public static long[] getHiScore() {

		long[] score = new long[5];

		if (!Gdx.files.local("race_status.ini").exists()) {
			initFiles();
		}

		FileHandle handle = Gdx.files.local("race_status.ini");
		if (handle.exists()) {

			String[] hiScore = handle.readString().split("\n");
			
			for (int i = 0; i < 5; i++) {
				if (hiScore[i] != null && !hiScore[i].equals("")
						&& hiScore[i].length() > 0) {
					score[i] = Long.parseLong(hiScore[i].trim());
				}
			}

		}
//
//		System.out.println("get : ");
//		for(int i=0 ; i<score.length ; i++){
//			System.out.print(score[i]+" ");
//		}
		
		return score;
	}

	private static void initFiles() {

		Gdx.files.internal("file/race_status.ini").copyTo(
				Gdx.files.local("race_status.ini"));

		FileHandle handleTemp = Gdx.files.local("race_status.ini");

		if (handleTemp.exists()) {

			handleTemp.writeString("", false);
			for (int i = 0; i < 5; i++) {
				handleTemp.writeString("0\n", true);
			}

		}

	}

}
