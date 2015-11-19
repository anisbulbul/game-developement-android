package com.anrosoft.mozbiemission;

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
	
	void getLeaderboardHighScoreGPGS();
	
	void quitApplication();

	void submitScoreGPGS(int score);
	
	void submitTrophyGPGS(int score);
	
	void unlockAchievementGPGS(String achievment);

	void showToast(String msg);
	
	void decrypt(String algorithm, String tempEnc, String tempDec) throws Exception;

}
