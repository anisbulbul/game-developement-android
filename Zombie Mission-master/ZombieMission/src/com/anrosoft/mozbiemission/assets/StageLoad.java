package com.anrosoft.mozbiemission.assets;

import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;

public class StageLoad extends GameAssets{
	
	public static void stageLoad(int world, int stage){
		
		
		stage++;
		world++;
		
		String stageSufix = stage+"";
		String worldSufix = world+"";
		if(stage <= 9){
			stageSufix = "0"+stageSufix;
		}
		if(world <= 9){
			worldSufix = "0"+worldSufix;
		}
		
		String stageArchitecture = "worlds/world"+worldSufix+"/stage"+stageSufix+".stage";
		
		FileHandle handle = Gdx.files.internal(stageArchitecture);
		if (handle.exists()) {

			String[] stageRows = handle.readString().split("\n");
			
			Gdx.app.log("STAGE", stageRows[0]);
			
			for(int i=0 ; i<stageRows.length ; i++){
				
				if(((stageRows[i].trim()).toLowerCase()).equals("end")){
					break;
				}
				
				String tempRow = stageRows[i].trim();
				
				String[] stageRowsComponents = tempRow.split(" ");
				
				if(stageRowsComponents.length == 8){
					
					boolean isEnemy = false;
					if(stageRowsComponents[5].equals("1")){
						isEnemy = true;
					}
					
					if(stageRowsComponents[0].equals("hero")){
						hero.heroPosX = (width/NUMBER_OF_COLS)*Float.parseFloat(stageRowsComponents[1]);
						hero.heroPosY = (height/NUMBER_OF_ROWS)*Float.parseFloat(stageRowsComponents[2]);						
					}
					if(stageRowsComponents[0].equals("asset")){
						assets.add(new AssetObject(
								(width/NUMBER_OF_COLS)*Float.parseFloat(stageRowsComponents[1]),
								(height/NUMBER_OF_ROWS)*Float.parseFloat(stageRowsComponents[2]), 
								width/Float.parseFloat(stageRowsComponents[3]), 
								height/Float.parseFloat(stageRowsComponents[4]), 
								0,0,
								Integer.parseInt(stageRowsComponents[7])-1, 
								isEnemy, Category.category[Integer.parseInt(stageRowsComponents[6])],
								1.0f));
					}
					else if(stageRowsComponents[0].equals("point")){
						points.add(new PointObject(
								(width/NUMBER_OF_COLS)*Float.parseFloat(stageRowsComponents[1]),
								(height/NUMBER_OF_ROWS)*Float.parseFloat(stageRowsComponents[2]), 
								width/Float.parseFloat(stageRowsComponents[3]), 
								height/Float.parseFloat(stageRowsComponents[4]), 
								0,0,Integer.parseInt(stageRowsComponents[7])-1, 1, 0.10f));
					}
					else if(stageRowsComponents[0].equals("door")){
						doors.add(new DoorObject(
								(width/NUMBER_OF_COLS)*Float.parseFloat(stageRowsComponents[1]),
								(height/NUMBER_OF_ROWS)*Float.parseFloat(stageRowsComponents[2]), 
								width/Float.parseFloat(stageRowsComponents[3]), 
								height/Float.parseFloat(stageRowsComponents[4]), 
								0,0,
								Integer.parseInt(stageRowsComponents[7])-1, 
								isEnemy, Category.category[Integer.parseInt(stageRowsComponents[6])],
								1.0f));
					}
				}
				else if(stageRowsComponents.length == 9){
					if(stageRowsComponents[0].equals("anim")){
						
						boolean isEnemy = false;
						if(stageRowsComponents[5].equals("1")){
							isEnemy = true;
						}
						
						boolean isLoop = false;
						if(stageRowsComponents[6].equals("1")){
							isLoop = true;
						}
						float speedX = 0;
						speedX = ((float)Math.abs(new Random().nextInt(100)))/100+0.5f;
						anims.add(new AnimationObject(
								(width/NUMBER_OF_COLS)*Float.parseFloat(stageRowsComponents[1]),
								(height/NUMBER_OF_ROWS)*Float.parseFloat(stageRowsComponents[2]), 
								width/Float.parseFloat(stageRowsComponents[3]), 
								height/Float.parseFloat(stageRowsComponents[4]), 
								speedX,0, 
								Integer.parseInt(stageRowsComponents[7])-1, 
								isEnemy, 0.05f, isLoop,
								1.0f, (width/NUMBER_OF_COLS)*Float.parseFloat(stageRowsComponents[8])));
					}
				}
			}
			
		}
		else {
//			Gdx.app.exit();
		}
	}
	
}
