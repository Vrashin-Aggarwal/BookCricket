package com.vrashin.bookcricket;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.GestureDetector;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import java.util.Random;

import static com.vrashin.bookcricket.R.raw.flippage;

public class OnePlayer extends AppCompatActivity {

	private static final int RESULT_SETTINGS1 = 1;
	private static final int DIALOG_GAMEOVER_ID = 0;
	private static final int DIALOG_GAMEOVER_ID2 = 1;
	private static final int DIALOG_GAMEOVER_ID3 = 2;

    MediaPlayer mp;
	private GestureDetectorCompat gDetect;

	int sum = 0, wk = 0, wkcpu = 0, cpusum = 0, wicktot = 5, pagetot = 100;

	static final String score = "score";
	static final String wickets = "wickets";
	static final String cscore = "cscore";
	static final String cwickets = "cwickets";
	private AdView mAdView;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.oneplayer);

		mAdView = (AdView) findViewById(R.id.adView);

		AdRequest request = new AdRequest.Builder()
				.addTestDevice(AdRequest.DEVICE_ID_EMULATOR)        // All emulators
				.addTestDevice("816E44CAC90B93C81FE4AC91A52AEB68")  // My Galaxy Nexus test phone
				.build();

		mAdView.loadAd(request );

		gDetect = new GestureDetectorCompat(this, new GestureListener());

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("1 PLAYER");

        showUserSettings();

        mp = MediaPlayer.create(this, flippage);

		//this.setVolumeControlStream(AudioManager.STREAM_MUSIC);
		//spool = new SoundPool(10, AudioManager.STREAM_MUSIC, 0);
		//soundID = spool.load(this, R.raw.flippage, 1);

        while (wkcpu < wicktot) {
			Random r1 = new Random();
			int a = r1.nextInt(pagetot) + 1;

			TextView cpusc = (TextView) findViewById(R.id.p1score);
			TextView cpuwk = (TextView) findViewById(R.id.p1wick);

			if (a % 2 == 0) {

				if((a) % 10 != 8 )
				cpusum = cpusum + ((a) % 10);
				if ((a) % 10 == 0)
					wkcpu++;
			} else {
                if((a + 1) % 10 != 8 )
				cpusum = cpusum + ((a + 1) % 10);
				if ((a + 1) % 10 == 0)
					wkcpu++;
			}

			cpusc.setText(Integer.toString(cpusum));
			cpuwk.setText(Integer.toString(wkcpu));

		}
		Context context = getApplicationContext();
		CharSequence text = "Beat the CPU score to WIN!";
		int duration = Toast.LENGTH_LONG;

		Toast toast = Toast.makeText(context, text, duration);
		toast.setGravity(Gravity.CENTER, 0, 0);
		toast.show();

		Button b7 = (Button) findViewById(R.id.btnflip);

		b7.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

                if(mp.isPlaying()){
                    mp.stop();
                }
                mp.start();

                Random r1 = new Random();
				int a = r1.nextInt(pagetot) + 1;

				TextView lp = (TextView) findViewById(R.id.leftpage);
				TextView rp = (TextView) findViewById(R.id.rightpage);
				TextView plsc = (TextView) findViewById(R.id.player2score);
				TextView plwk = (TextView) findViewById(R.id.player2wick);

                //Sound();
				if (a % 2 == 0) {
					// flipsnd.start();

					lp.setText(Integer.toString(a - 1));
					rp.setText(Integer.toString(a));
                    if((a) % 10 != 8 )
					sum = sum + ((a) % 10);
					if ((a) % 10 == 0)
						wk++;
				} else {
					// flipsnd.start();
					lp.setText(Integer.toString(a));
					rp.setText(Integer.toString(a + 1));
                    if((a + 1) % 10 != 8 )
					sum = sum + ((a + 1) % 10);
					if ((a + 1) % 10 == 0)
						wk++;
				}

				plsc.setText(Integer.toString(sum));
				plwk.setText(Integer.toString(wk));

				if (sum > cpusum && wk < wicktot) {

					runOnUiThread(new Runnable() {
						@Override
						public void run() {
							OnePlayer.this.showDialog(DIALOG_GAMEOVER_ID2);
						}
					});

				}

				else if (sum < cpusum && wk == wicktot) {

					runOnUiThread(new Runnable() {
						@Override
						public void run() {
							OnePlayer.this.showDialog(DIALOG_GAMEOVER_ID);
						}
					});

				} else if (sum == cpusum && wk == wicktot) {
					/*
					 * Context context = getApplicationContext(); CharSequence
					 * text = "NO RESULT!!"; int duration = Toast.LENGTH_LONG;
					 *
					 * Toast toast = Toast.makeText(context, text, duration);
					 * toast.setGravity(Gravity.CENTER, 0, 0); toast.show();
					 * Button b7 = (Button) findViewById(R.id.btnflip);
					 * b7.setEnabled(false);
					 */
					runOnUiThread(new Runnable() {
						@Override
						public void run() {

							OnePlayer.this.showDialog(DIALOG_GAMEOVER_ID3);
						}
					});

				}

			}

		});

	}

	protected Dialog onCreateDialog(int id) {

		AlertDialog.Builder builder = new AlertDialog.Builder(this);

		switch (id) {
			case DIALOG_GAMEOVER_ID:

				builder.setMessage("CPU Wins.Better luck next time ")
						.setCancelable(false)
						.setPositiveButton("Play Again",
								new DialogInterface.OnClickListener() {

									public void onClick(DialogInterface arg0,
														int arg1) {
										Intent intent = getIntent();
										finish();
										startActivity(intent);

									}
								})
						.setNegativeButton("Exit",
								new DialogInterface.OnClickListener() {

									public void onClick(DialogInterface dialog,
														int which) {
										OnePlayer.this.finish();

									}
								});
				builder.setTitle("GAME OVER");
				AlertDialog gameOverDialog = builder.create();
				return gameOverDialog;
			case DIALOG_GAMEOVER_ID2:

				builder.setMessage("Congratulations. You Win")
						.setCancelable(false)
						.setPositiveButton("Play Again",
								new DialogInterface.OnClickListener() {

									public void onClick(DialogInterface arg0,
														int arg1) {
										Intent intent = getIntent();
										finish();
										startActivity(intent);

									}
								})
						.setNegativeButton("Exit",
								new DialogInterface.OnClickListener() {

									public void onClick(DialogInterface dialog,
														int which) {
										OnePlayer.this.finish();

									}
								});
				builder.setTitle("GAME OVER");
				AlertDialog gameOverDialog1 = builder.create();
				return gameOverDialog1;
			case DIALOG_GAMEOVER_ID3:

				builder.setMessage("Match Draw")
						.setCancelable(false)
						.setPositiveButton("Play Again",
								new DialogInterface.OnClickListener() {

									public void onClick(DialogInterface arg0,
														int arg1) {
										Intent intent = getIntent();
										finish();
										startActivity(intent);

									}
								})
						.setNegativeButton("Exit",
								new DialogInterface.OnClickListener() {

									public void onClick(DialogInterface dialog,
														int which) {
										OnePlayer.this.finish();

									}
								});
				builder.setTitle("GAME OVER");
				AlertDialog gameOverDialog2 = builder.create();
				return gameOverDialog2;
			default:
				return null;
		}

	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		this.gDetect.onTouchEvent(event);
		return super.onTouchEvent(event);
	}

	public class GestureListener extends
			GestureDetector.SimpleOnGestureListener {
		// content
		private float flingMin = 100;
		private float velocityMin = 100;

		@Override
		public boolean onDown(MotionEvent event) {
			return true;
		}

		@Override
		public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
							   float velocityY) {
			// TODO Auto-generated method stub
			// user will move forward through messages on fling up or left
			boolean forward = false;
			// user will move backward through messages on fling down or right
			boolean backward = false;
			// calculate the change in X position within the fling gesture
			float horizontalDiff = e2.getX() - e1.getX();
			// calculate the change in Y position within the fling gesture
			float verticalDiff = e2.getY() - e1.getY();
			float absHDiff = Math.abs(horizontalDiff);
			float absVDiff = Math.abs(verticalDiff);
			float absVelocityX = Math.abs(velocityX);
			float absVelocityY = Math.abs(velocityY);

			if (absHDiff > absVDiff && absHDiff > flingMin
					&& absVelocityX > velocityMin) {
				// move forward or backward
				if (horizontalDiff > 0)
					backward = true;
				else
					forward = true;
			} else if (absVDiff > flingMin && absVelocityY > velocityMin) {
				if (verticalDiff > 0)
					backward = true;
				else
					forward = true;
			}

			if (forward) {
				// flipsnd.start();
				//Sound();
                if(mp.isPlaying()){
                    mp.stop();
                }
                mp.start();
				Random r1 = new Random();
				int a = r1.nextInt(pagetot) + 1;

				TextView lp = (TextView) findViewById(R.id.leftpage);
				TextView rp = (TextView) findViewById(R.id.rightpage);
				TextView plsc = (TextView) findViewById(R.id.player2score);
				TextView plwk = (TextView) findViewById(R.id.player2wick);

				if (a % 2 == 0) {
					lp.setText(Integer.toString(a - 1));
					rp.setText(Integer.toString(a));
					sum = sum + ((a) % 10);
					if ((a) % 10 == 0)
						wk++;
				} else {
					lp.setText(Integer.toString(a));
					rp.setText(Integer.toString(a + 1));
					sum = sum + ((a + 1) % 10);
					if ((a + 1) % 10 == 0)
						wk++;
				}

				plsc.setText(Integer.toString(sum));
				plwk.setText(Integer.toString(wk));

				if (sum > cpusum && wk < wicktot) {
					runOnUiThread(new Runnable() {
						@Override
						public void run() {

							OnePlayer.this.showDialog(DIALOG_GAMEOVER_ID2);
						}
					});

				}

				else if (sum < cpusum && wk == wicktot) {
					runOnUiThread(new Runnable() {
						@Override
						public void run() {

							OnePlayer.this.showDialog(DIALOG_GAMEOVER_ID);
						}
					});

				} else if (sum == cpusum && wk == wicktot) {
					runOnUiThread(new Runnable() {
						@Override
						public void run() {

							OnePlayer.this.showDialog(DIALOG_GAMEOVER_ID3);
						}
					});

				}

			}

			return true;

		}

	}

	@Override
	protected void onSaveInstanceState(Bundle savedInstanceState) {
		// TODO Auto-generated method stub

		savedInstanceState.putInt(score, sum);
		savedInstanceState.putInt(wickets, wk);
		savedInstanceState.putInt(cscore, cpusum);
		savedInstanceState.putInt(cwickets, wkcpu);
		super.onSaveInstanceState(savedInstanceState);

	}

	@Override
	protected void onRestoreInstanceState(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onRestoreInstanceState(savedInstanceState);

		// Restore value of members from saved state
		sum = savedInstanceState.getInt(score);
		wk = savedInstanceState.getInt(wickets);
		cpusum = savedInstanceState.getInt(cscore);
		wkcpu = savedInstanceState.getInt(cwickets);

	}

	protected Context getActivity() {
		// TODO Auto-generated method stub

		return null;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.exit) {
			finish();
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		switch (requestCode) {
			case RESULT_SETTINGS1:
				showUserSettings();
				break;

		}

	}

	private void showUserSettings() {
		SharedPreferences sharedPrefs = PreferenceManager
				.getDefaultSharedPreferences(this);

		String pg = new String();
		pg = sharedPrefs.getString("pages", "100");

		String wkt = new String();
		wkt = sharedPrefs.getString("wickets", "5");
		switch (pg) {
			case "100":
				pagetot = 100;
				break;
			case "200":
				pagetot = 200;
				break;
			case "500":
				pagetot = 500;
				break;
		}

		switch (wkt) {
			case "5":
				wicktot = 5;
				break;
			case "10":
				wicktot = 10;
				break;

		}

	}



}
