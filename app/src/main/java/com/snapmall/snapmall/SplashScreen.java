package com.snapmall.snapmall;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

public class SplashScreen extends AppCompatActivity {


    private ImageView mSplash;
    private TextView mTxt;
    private Animation mTopAnim,mBottomAnim;
    private boolean connected = false;
    private AppData mData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash_screen);
        mData = new AppData(this);
        mSplash =findViewById(R.id.splash_img);
        mTxt =findViewById(R.id.splash_txt);
        if(mData.getLang().equals("Ar")){
            mTxt.setText("سناب مول");
        }
        mTopAnim = AnimationUtils.loadAnimation(SplashScreen.this,R.anim.splash_top_anim);
        mBottomAnim = AnimationUtils.loadAnimation(SplashScreen.this,R.anim.splash_bottom_anim);
        mSplash.setAnimation(mTopAnim);
        mTxt.setAnimation(mBottomAnim);


        if (isConnected()) {
            splashFun();
        } else {
            Intent gotonowifi = new Intent(SplashScreen.this, NoConnection.class);
            gotonowifi.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            gotonowifi.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(gotonowifi);
            finish();
        }


    }
    boolean isConnected() {

        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(this.CONNECTIVITY_SERVICE);
        if (connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {
            //we are connected to a network
            connected = true;
        } else
            connected = false;

        return connected;
    }

    void splashFun(){
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                startActivity(new Intent(SplashScreen.this, MainActivity.class));
                finish();

            }
        }, 3000);
    }
}
