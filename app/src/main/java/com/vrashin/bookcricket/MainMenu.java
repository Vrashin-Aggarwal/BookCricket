package com.vrashin.bookcricket;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

public class MainMenu extends AppCompatActivity {

    MediaPlayer btnsnd;
    private AdView mAdView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().hide();
        btnsnd = MediaPlayer.create(this, R.raw.btn_sound);
        Button b1 = (Button) findViewById(R.id.btnplayer1);

        String android_id = Settings.Secure.getString(getApplicationContext().getContentResolver(),
                Settings.Secure.ANDROID_ID);

        MobileAds.initialize(this, "ca-app-pub-2230158781233514~8619258996");

        mAdView = (AdView) findViewById(R.id.adView);

        AdRequest request = new AdRequest.Builder()
                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)        // All emulators
                .addTestDevice("816E44CAC90B93C81FE4AC91A52AEB68")  // My Galaxy Nexus test phone
                .build();

        mAdView.loadAd(request );
        /*AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);*/

        b1.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub

                btnsnd.start();
                startActivity(new Intent("com.vrashin.bookcricket.ONEPLAYER"));

            }
        });
        Button b2 = (Button) findViewById(R.id.btnplayers2);
        b2.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub

                btnsnd.start();
                startActivity(new Intent("com.vrashin.bookcricket.TWOPLAYER"));

            }
        });
        /*
		 * Button b3 = (Button) findViewById(R.id.btnoptions);
		 * b3.setOnClickListener(new View.OnClickListener() {
		 * 
		 * @Override public void onClick(View v) { // TODO Auto-generated method
		 * stub
		 * 
		 * btnsnd.start(); startActivity(new
		 * Intent("com.vrashin.bookcricket.OPTIONS"));
		 * 
		 * } });
		 */

        Button b6 = (Button) findViewById(R.id.btnexit);
        b6.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub

                btnsnd.start();
                moveTaskToBack(true);
                MainMenu.this.finish();
            }
        });

        Button b4 = (Button) findViewById(R.id.btnhowtoplay);
        b4.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub

                startActivity(new Intent("com.vrashin.bookcricket.HOW"));

                btnsnd.start();
            }
        });

        Button b5 = (Button) findViewById(R.id.btnsettings);
        b5.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                startActivity(new Intent("com.vrashin.bookcricket.SETTINGS"));

                btnsnd.start();
            }
        });

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


}
