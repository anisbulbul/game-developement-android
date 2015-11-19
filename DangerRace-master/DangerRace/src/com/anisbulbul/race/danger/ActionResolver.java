package com.anisbulbul.race.danger;

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

	void submitScoreGPGS(int score);
	
	void submitTrophyGPGS(int score);
	
	void getLeaderboardHighScoreGPGS();
	
	void unlockAchievementGPGS(String achievment);
}
