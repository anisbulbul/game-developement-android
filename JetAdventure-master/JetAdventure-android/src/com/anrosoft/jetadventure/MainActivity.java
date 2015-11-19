// Copyright:  2015, ANRO SOFT
// Home page:  http://www.anrosoft.com

package com.anrosoft.jetadventure;

import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.Toast;

import com.anrosoft.jetadventure.assets.GameAssets;
import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.games.Games;
import com.google.example.games.basegameutils.GameHelper;

public class MainActivity extends AndroidApplication implements
		GameHelper.GameHelperListener, ActionResolver {

	protected AdView adViewMain;
	private GameHelper gameHelper;
	protected View gameView;
	AdView adView;
	RelativeLayout layout;
	private InterstitialAd interstitialAd;

	private static final String AD_UNIT_ID_BANNER = "ca-app-pub-8606268298440779/9886622846";
	private static final String AD_UNIT_ID_INTERSTITIAL = "ca-app-pub-8606268298440779/2363356044";

	private static final String ANROSOFT_SITE_URL = "http://anrosoft.com";
	private static final String GOOGLE_PLAY_URL = "https://play.google.com/store/apps/details?id=com.anrosoft.jetadventure";
	private static final String ANROSOFT_BLOG_SITE_URL = "http://blogs.anrosoft.com";
	private static final String GOOGLE_PLUS_PAGE_URL = "https://plus.google.com/108784960146736496484/posts";
	private static final String FACEBOOK_PAGE_URL = "https://www.facebook.com/anrosoft";
	private static final String TWITTER_PAGE_URL = "https://twitter.com/anrosoft";
	private static final String TUMBLR_PAGE_URL = "https://www.tumblr.com/blog/anrosoft";
	private static final String REMOVE_ADDS_URL = "http://apps.anrosoft.com";
	private static final String ANROSOFT_MORE_APPS_URL = "http://apps.anrosoft.com";

	private static boolean IS_ADMOB_ACTIVE = true;
	private static boolean DEBUG = false;
	private static boolean IS_PLAY_SERVICE_ACTIVE = true;

	@Override
	public String[] getAchievmentIDs() {
		String achievmentIDs[] = {
				getResources().getString(R.string.achievement_first_adventure),
				getResources().getString(R.string.achievement_try_the_next),
				getResources().getString(R.string.achievement_adventure_night),
				getResources().getString(
						R.string.achievement_fourth_level_grave_yeard),
				getResources().getString(
						R.string.achievement_jet_adventure_horror_nnight) };

		return achievmentIDs;
	}

	private AdView createAdView() {
		this.adViewMain = new AdView(this);
		this.adViewMain.setAdSize(AdSize.SMART_BANNER);
		this.adViewMain.setAdUnitId(AD_UNIT_ID_BANNER);
		this.adViewMain.setId(12345);
		LayoutParams var1 = new LayoutParams(-1, -2);
		var1.addRule(12, -1);
		var1.addRule(14, -1);
		this.adViewMain.setLayoutParams(var1);
		this.adViewMain.setBackgroundColor(-16777216);
		return this.adViewMain;
	}

	private View createGameView(AndroidApplicationConfiguration var1) {
		this.gameView = this.initializeForView(new JetAdventure(this), var1);
		LayoutParams var2 = new LayoutParams(-1, -2);
		var2.addRule(10, -1);
		var2.addRule(14, -1);
		var2.addRule(2, this.adViewMain.getId());
		this.gameView.setLayoutParams(var2);
		return this.gameView;
	}

	private void startAdvertising(AdView var1) {
		var1.loadAd((new AdRequest.Builder()).build());
	}

	@Override
	public void faceBookPost(String fbMessage) {

		Intent sendIntent = new Intent();
		sendIntent.setAction(Intent.ACTION_SEND);
		sendIntent.putExtra(Intent.EXTRA_TEXT,
				"https://facebook.com/share?url=https://play.google.com/store/apps/details?id="
						+ "com.anrosoft.jetadventure" + "&title=" + fbMessage);
		sendIntent.setType("text/plain");
		this.startActivity(sendIntent);

	}

	@Override
	public void googlePlusPost(String gplusMessage) {

		Intent sendIntent = new Intent();
		sendIntent.setAction(Intent.ACTION_SEND);
		sendIntent
				.putExtra(
						Intent.EXTRA_TEXT,
						"https://plus.google.com/share?url=https://play.google.com/store/apps/details?id="
								+ "com.anrosoft.jetadventure"
								+ "&title="
								+ gplusMessage);
		sendIntent.setType("text/plain");
		this.startActivity(sendIntent);

	}

	@Override
	public void twitterPost(String twMessage) {

		Intent sendIntent = new Intent();
		sendIntent.setAction(Intent.ACTION_SEND);
		sendIntent
				.putExtra(
						Intent.EXTRA_TEXT,
						"https://twitter.com/intent/tweet?url=https://play.google.com/store/apps/details?id="
								+ "com.anrosoft.jetadventure"
								+ "&related=anrosoft.com&text=i got high score"
								+ twMessage);
		sendIntent.setType("text/plain");
		this.startActivity(sendIntent);

	}

	@Override
	public void getAchievementsGPGS() {
		if (this.gameHelper.isSignedIn()) {
			this.startActivityForResult(Games.Achievements
					.getAchievementsIntent(this.gameHelper.getApiClient()), 101);
		} else if (!this.gameHelper.isConnecting()) {
			this.loginGPGS();
			return;
		}

	}

	@Override
	public void getLeaderboardHighScoreGPGS() {
		if (this.gameHelper.isSignedIn()) {
			this.startActivityForResult(
					Games.Leaderboards.getLeaderboardIntent(
							this.gameHelper.getApiClient(),
							this.getString(R.string.leaderboard_high_score)),
					100);
		} else if (!this.gameHelper.isConnecting()) {
			this.loginGPGS();
			return;
		}

	}

	@Override
	public void getLeaderboardTrophyGPGS() {
		if (this.gameHelper.isSignedIn()) {
			this.startActivityForResult(
					Games.Leaderboards
							.getLeaderboardIntent(
									this.gameHelper.getApiClient(),
									this.getString(R.string.leaderboard_horror_trophy_winner)),
					100);
		} else if (!this.gameHelper.isConnecting()) {
			this.loginGPGS();
			return;
		}

	}

	@Override
	public void getAllLeaderboardGPGS() {
		if (this.gameHelper.isSignedIn()) {
			this.startActivityForResult(Games.Leaderboards
					.getAllLeaderboardsIntent(this.gameHelper.getApiClient()),
					1);
		} else if (!this.gameHelper.isConnecting()) {
			this.loginGPGS();
			return;
		}

	}

	@Override
	public boolean getSignedInGPGS() {
		return this.gameHelper.isSignedIn();
	}

	@Override
	public void loginGPGS() {
		if (IS_PLAY_SERVICE_ACTIVE) {
			try {
				this.runOnUiThread(new Runnable() {
					public void run() {
						MainActivity.this.gameHelper.beginUserInitiatedSignIn();
					}
				});
			} catch (Exception var2) {
				;
			}
		}
	}

	public void onActivityResult(int var1, int var2, Intent var3) {
		super.onActivityResult(var1, var2, var3);
		this.gameHelper.onActivityResult(var1, var2, var3);
	}

	public void onBackPressed() {

		final Dialog dialog = new Dialog(this);
		dialog.requestWindowFeature(Window.FEATURE_LEFT_ICON);
		dialog.setTitle(R.string.app_name);
		dialog.setContentView(R.layout.back_button_action_layout);

		Button anrosoftSite = (Button) dialog
				.findViewById(R.id.anroSoftSiteButton);
		anrosoftSite.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				startActivity(new Intent(Intent.ACTION_VIEW, Uri
						.parse(ANROSOFT_SITE_URL)));
				if (GameAssets.isBackButtonEnable) {
					backButtonCloseAction();
				}
				dialog.dismiss();
			}
		});

		Button rateButton = (Button) dialog.findViewById(R.id.rateButton);
		rateButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				startActivity(new Intent(Intent.ACTION_VIEW, Uri
						.parse(GOOGLE_PLAY_URL)));
				if (GameAssets.isBackButtonEnable) {
					backButtonCloseAction();
				}
				dialog.dismiss();
			}
		});
		Button shareButton = (Button) dialog.findViewById(R.id.shareButton);
		shareButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				showShare();
				if (GameAssets.isBackButtonEnable) {
					backButtonCloseAction();
				}
				dialog.dismiss();
			}
		});

		Button blogButton = (Button) dialog
				.findViewById(R.id.anroSoftBlogButton);
		blogButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				startActivity(new Intent(Intent.ACTION_VIEW, Uri
						.parse(ANROSOFT_BLOG_SITE_URL)));
				if (GameAssets.isBackButtonEnable) {
					backButtonCloseAction();
				}
				dialog.dismiss();
			}
		});

		Button googlePlusButton = (Button) dialog
				.findViewById(R.id.googlePlusButton);
		googlePlusButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				startActivity(new Intent(Intent.ACTION_VIEW, Uri
						.parse(GOOGLE_PLUS_PAGE_URL)));
				if (GameAssets.isBackButtonEnable) {
					backButtonCloseAction();
				}
				dialog.dismiss();
			}
		});

		Button facebookPageButton = (Button) dialog
				.findViewById(R.id.facebookButton);
		facebookPageButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				startActivity(new Intent(Intent.ACTION_VIEW, Uri
						.parse(FACEBOOK_PAGE_URL)));
				if (GameAssets.isBackButtonEnable) {
					backButtonCloseAction();
				}
				dialog.dismiss();
			}
		});

		Button twitterPageButton = (Button) dialog
				.findViewById(R.id.twitterButton);
		twitterPageButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				startActivity(new Intent(Intent.ACTION_VIEW, Uri
						.parse(TWITTER_PAGE_URL)));
				if (GameAssets.isBackButtonEnable) {
					backButtonCloseAction();
				}
				dialog.dismiss();
			}
		});

		Button flickerPageButton = (Button) dialog
				.findViewById(R.id.tumblrButton);
		flickerPageButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				startActivity(new Intent(Intent.ACTION_VIEW, Uri
						.parse(TUMBLR_PAGE_URL)));
				if (GameAssets.isBackButtonEnable) {
					backButtonCloseAction();
				}
				dialog.dismiss();
			}
		});

		Button removeAddButton = (Button) dialog
				.findViewById(R.id.removeAddsButton);
		removeAddButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				startActivity(new Intent(Intent.ACTION_VIEW, Uri
						.parse(REMOVE_ADDS_URL)));
				if (GameAssets.isBackButtonEnable) {
					backButtonCloseAction();
				}
				dialog.dismiss();
			}
		});

		Button moreAppsGamesButton = (Button) dialog
				.findViewById(R.id.moreAppsGames);
		moreAppsGamesButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				startActivity(new Intent(Intent.ACTION_VIEW, Uri
						.parse(ANROSOFT_MORE_APPS_URL)));
				if (GameAssets.isBackButtonEnable) {
					backButtonCloseAction();
				}
				dialog.dismiss();
			}
		});

		Button backButton = (Button) dialog.findViewById(R.id.backButton);
		backButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (GameAssets.isBackButtonEnable) {
					backButtonCloseAction();
				}
				dialog.dismiss();
			}
		});

		Button exitButton = (Button) dialog.findViewById(R.id.exitButton);
		exitButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				GameAssets.isBackButtonEnable = false;
				finish();
			}
		});

		dialog.show();
		dialog.setFeatureDrawableResource(Window.FEATURE_LEFT_ICON,
				R.drawable.icon);

		if (GameAssets.isBackButtonEnable) {
			backButtonOpenAction();
		}

	}

	private void backButtonOpenAction() {
		if (!GameAssets.isGamePause
				&& !GameAssets.GAME_STATE.equals(GameAssets.GAME_START)
				&& !GameAssets.isGameOver) {
			GameAssets.isGamePauseAndroid = true;
		}
		GameAssets.pauseAllSound();
	}

	private void backButtonCloseAction() {
		if (GameAssets.GAME_STATE.equals(GameAssets.GAME_MENU)) {
			if (GameAssets.backgroundMusic != null) {
				GameAssets.backgroundMusic.play();
				GameAssets.backgroundMusic.setVolume(GameAssets.musicVolume);
			}
		} else if (GameAssets.GAME_STATE.equals(GameAssets.GAME_START)) {
			if (GameAssets.gameStageBackgroundMusic != null) {
				GameAssets.gameStageBackgroundMusic.play();
				GameAssets.gameStageBackgroundMusic
						.setVolume(GameAssets.musicVolume / 2);
			}
		}
	}

	public void onCreate(Bundle var1) {
		super.onCreate(var1);
		this.getWindow().addFlags(128);

		AndroidApplicationConfiguration cfg = new AndroidApplicationConfiguration();
		cfg.useGL20 = false;
		cfg.useAccelerometer = true;
		cfg.useCompass = false;

		this.requestWindowFeature(1);
		this.getWindow().setFlags(1024, 1024);
		this.getWindow().clearFlags(2048);

		layout = new RelativeLayout(this);
		layout.setLayoutParams(new LayoutParams(-1, -1));

		adView = this.createAdView();
		// layout.addView(adView);
		layout.addView(this.createGameView(cfg));

		this.setContentView(layout);
		this.startAdvertising(adView);

		this.interstitialAd = new InterstitialAd(this);
		this.interstitialAd.setAdUnitId(AD_UNIT_ID_INTERSTITIAL);
		interstitialAd.setAdListener(new AdListener() {
			@Override
			public void onAdLoaded() {
				if (DEBUG) {
					Toast.makeText(getApplicationContext(),
							"Finished Loading Interstitial", Toast.LENGTH_SHORT)
							.show();
				}
			}

			@Override
			public void onAdClosed() {
				if (DEBUG) {
					Toast.makeText(getApplicationContext(),
							"Closed Interstitial", Toast.LENGTH_SHORT).show();
				}
			}
		});

		if (this.gameHelper == null) {
			this.gameHelper = new GameHelper(this, 1);
			this.gameHelper.enableDebugLog(true);
		}

		this.gameHelper.setup(this);

	}

	@Override
	public void onSignInFailed() {
	}

	@Override
	public void onSignInSucceeded() {
	}

	@Override
	public void onStart() {
		super.onStart();
		if (IS_PLAY_SERVICE_ACTIVE) {
			this.gameHelper.onStart(this);
		}
	}

	@Override
	public void onStop() {
		super.onStop();
		if (IS_PLAY_SERVICE_ACTIVE) {
			this.gameHelper.onStop();
		}
	}

	@Override
	public void openUri(String uri) {
		this.startActivity(new Intent("android.intent.action.VIEW", Uri
				.parse(uri)));
	}

	@Override
	public void quitApplication() {

	}

	@Override
	public void submitScoreGPGS(int score) {

		if (this.gameHelper.isSignedIn()) {
			Games.Leaderboards.submitScore(this.gameHelper.getApiClient(),
					this.getString(R.string.leaderboard_high_score), score);
		} else if (!this.gameHelper.isConnecting()) {
			this.loginGPGS();
			return;
		}
	}

	@Override
	public void submitTrophyGPGS(int score) {

		if (this.gameHelper.isSignedIn()) {
			Games.Leaderboards.submitScore(this.gameHelper.getApiClient(),
					this.getString(R.string.leaderboard_horror_trophy_winner),
					score);
		} else if (!this.gameHelper.isConnecting()) {
			this.loginGPGS();
			return;
		}

	}

	@Override
	public void unlockAchievementGPGS(String achievementId) {
		if (this.gameHelper.isSignedIn()) {
			Games.Achievements.unlock(this.gameHelper.getApiClient(),
					achievementId);
		} else if (!this.gameHelper.isConnecting()) {
			this.loginGPGS();
			return;
		}
	}

	@Override
	public void showRated() {

		try {
			startActivity(new Intent(
					Intent.ACTION_VIEW,
					Uri.parse("https://play.google.com/store/apps/details?id=com.anrosoft.jetadventure")));
		} catch (ActivityNotFoundException e) {

		}

	}

	@Override
	public void showShare() {

		Intent sendIntent = new Intent();
		sendIntent.setAction(Intent.ACTION_SEND);
		sendIntent
				.putExtra(
						Intent.EXTRA_TEXT,
						" Jet Adventure Horror "
								+ " Visit: https://play.google.com/store/apps/details?id=com.anrosoft.jetadventure");
		sendIntent.setType("text/plain");
		startActivity(sendIntent);

	}

	public void addLayout() {
		try {
			runOnUiThread(new Runnable() {
				public void run() {
					if (interstitialAd.isLoaded()) {
						layout.addView(adView);
					}
				}
			});
		} catch (Exception e) {
		}
	}

	@Override
	public void showOrLoadInterstital() {
		if (IS_ADMOB_ACTIVE) {

			try {
				runOnUiThread(new Runnable() {

					public void run() {

						addLayout();

						if (interstitialAd.isLoaded()) {

							interstitialAd.show();

							if (DEBUG) {
								Toast.makeText(getApplicationContext(),
										"Showing Interstitial",
										Toast.LENGTH_SHORT).show();
							}

						} else {
							AdRequest interstitialRequest = new AdRequest.Builder()
									.build();
							interstitialAd.loadAd(interstitialRequest);

							if (DEBUG) {
								Toast.makeText(getApplicationContext(),
										"Loading Interstitial",
										Toast.LENGTH_SHORT).show();
							}

						}
					}
				});
			} catch (Exception e) {
			}

		}
	}

	@Override
	public void showToast(String msg) {
		Toast.makeText(MainActivity.this, msg, Toast.LENGTH_SHORT).show();
	}

	@Override
	public void decrypt(String algorithm, String tempEnc, String tempDec)
			throws Exception {

	}
}
