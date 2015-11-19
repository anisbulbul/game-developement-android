package com.anisbulbul.car.race;

public interface ActionResolver {
	
	String[] getAchievmentIDs();
	
	void openUri(String uri);

	void showRated();

	void showShare();

	void showOrLoadInterstital();

	void faceBookPost(String fbMessage);
	
	void googlePlusPost(String gplusMessage);
	
	void twitterPost(String twMessage);

	void getAchievementsGPGS();

	void getAllLeaderboardGPGS();

	boolean getSignedInGPGS();

	void loginGPGS();

	void getLeaderboardTrophyGPGS();
	
	void quitApplication();

	void submitScoreGPGS(long score);
	
	void submitTrophyGPGS(long score);
	
	void getLeaderboardHighScoreGPGS();
	
	void unlockAchievementGPGS(String achievment);
}
