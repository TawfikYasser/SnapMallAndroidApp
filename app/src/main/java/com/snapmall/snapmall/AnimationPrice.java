package com.snapmall.snapmall;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.rtugeek.android.colorseekbar.ColorSeekBar;

public class AnimationPrice extends AppCompatActivity implements View.OnClickListener {

    ColorSeekBar colorSeekBar;
    TextView textView ;
    RelativeLayout mWithout , mFlash , mPulse , mRubberBand , mShake , mSwing , mTada , mWobble , mJello , mHeartBeta , mZoomIn ;
    ImageView mWithoutCheck , mFlashCheck , mPulseCheck , mRubberBandCheck , mShakeCheck , mSwingCheck , mTadaCheck
            , mWobbleCheck , mJelloCheck , mHeartBetaCheck , mZoomInCheck , mBackIcon,mSaveIcon;
    Animation animationFlash,animationPulse,animationRubberband,animationShake,animationSwing,animationTada,animationWobble,animationJello,animationHeartBeat,animationZoomin ;
    String animationCode;
    String colorCode;
    private AppData mAppD;
    private YoYo.YoYoString rope;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animation_price);
        InitViews();
        mAppD = new AppData(this);
        String PriceFromOffer = getIntent().getStringExtra("PRICE");
        textView.setText("PriceFromOffer");
        LoadColorSeekBar();

    }

    private void InitViews() {

        textView = findViewById(R.id.number);
        colorSeekBar = findViewById(R.id.colorSlider);
        mWithout = findViewById(R.id.without);
        mWithout.setOnClickListener(this);
        mFlash = findViewById(R.id.flash);
        mFlash.setOnClickListener(this);
        mRubberBand = findViewById(R.id.rubberband);
        mRubberBand.setOnClickListener(this);
        mShake = findViewById(R.id.shake);
        mShake.setOnClickListener(this);
        mSwing = findViewById(R.id.swing);
        mSwing.setOnClickListener(this);
        mTada = findViewById(R.id.tada);
        mTada.setOnClickListener(this);
        mWobble = findViewById(R.id.wobble);
        mWobble.setOnClickListener(this);
        mJello = findViewById(R.id.jello);
        mJello.setOnClickListener(this);
        mHeartBeta = findViewById(R.id.heartbeat);
        mHeartBeta.setOnClickListener(this);
        mZoomIn = findViewById(R.id.zoomin);
        mZoomIn.setOnClickListener(this);


        mBackIcon = findViewById(R.id.clear_icon);
        mBackIcon.setOnClickListener(this);
        mSaveIcon =findViewById(R.id.save_anim_color);
        mSaveIcon.setOnClickListener(this);

        mWithoutCheck = findViewById(R.id.checkedWithout);
        mWithoutCheck.setOnClickListener(this);
        mWithoutCheck.setVisibility(View.VISIBLE);
        mFlashCheck = findViewById(R.id.flashcheck);
        mFlashCheck.setOnClickListener(this);
        mRubberBandCheck = findViewById(R.id.rubberbandcheck);
        mRubberBandCheck.setOnClickListener(this);
        mShakeCheck = findViewById(R.id.shakecheck);
        mShakeCheck.setOnClickListener(this);
        mSwingCheck = findViewById(R.id.swingcheck);
        mSwingCheck.setOnClickListener(this);
        mTadaCheck = findViewById(R.id.tadacheck);
        mTadaCheck.setOnClickListener(this);
        mWobbleCheck = findViewById(R.id.wobblecheck);
        mWobbleCheck.setOnClickListener(this);
        mJelloCheck = findViewById(R.id.jellocheck);
        mJelloCheck.setOnClickListener(this);
        mHeartBetaCheck = findViewById(R.id.heartbeatcheck);
        mHeartBetaCheck.setOnClickListener(this);
        mZoomInCheck = findViewById(R.id.zoomincheck);
        mZoomInCheck.setOnClickListener(this);


        animationFlash = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.blink);
        animationRubberband = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.rotate);
        animationShake = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.move);
        animationSwing = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.slide_down);
        animationTada = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.splash_top_anim);
        animationWobble = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.splash_bottom_anim);
        animationJello = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.sequenctial);
        animationHeartBeat = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.fade_out);
        animationZoomin = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.zoom_out);

    }

    private void LoadColorSeekBar() {
        colorSeekBar.setMaxPosition(100);
        colorSeekBar.setColorSeeds(R.array.material_colors); // material_colors is defalut included in res/color,just use it.
        colorSeekBar.setColorBarPosition(10); //0 - maxValue
        colorSeekBar.setAlphaBarPosition(10); //0 - 255
        colorSeekBar.setShowAlphaBar(true);
        colorSeekBar.setBarHeight(5); //5dpi
        colorSeekBar.setThumbHeight(30); //30dpi
        colorSeekBar.setBarMargin(10); //set the margin between colorBar and alphaBar 10dpi
        colorSeekBar.setOnColorChangeListener(new ColorSeekBar.OnColorChangeListener() {
            @Override
            public void onColorChangeListener(int colorBarPosition, int alphaBarPosition, int color) {
                textView.setTextColor(color);

                Log.i("TAG", "===colorPosition:" + colorBarPosition
                        + "-alphaPosition:" + alphaBarPosition
                        + "-ColorIndexPosition:" + colorSeekBar.getColorIndexPosition(color)
                        + "-color:" + color + "===");
                colorCode = String.valueOf(color);
                //colorSeekBar.getAlphaValue();
            }
        });
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.without:
                if (rope != null) {
                    rope.stop(true);
                }
                mWithoutCheck.setVisibility(View.VISIBLE);
                mFlashCheck.setVisibility(View.INVISIBLE);
                mRubberBandCheck.setVisibility(View.GONE);
                mShakeCheck.setVisibility(View.INVISIBLE);
                mSwingCheck.setVisibility(View.INVISIBLE);
                mTadaCheck.setVisibility(View.INVISIBLE);
                mWobbleCheck.setVisibility(View.INVISIBLE);
                mJelloCheck.setVisibility(View.INVISIBLE);
                mHeartBetaCheck.setVisibility(View.INVISIBLE);
                mZoomInCheck.setVisibility(View.INVISIBLE);
                animationCode = "0";
                break;
            case R.id.flash:
                if (rope != null) {
                    rope.stop(true);
                }
                Techniques technique = Techniques.Flash;
                rope = YoYo.with(technique).repeat(YoYo.INFINITE).playOn(findViewById(R.id.number));

                mWithoutCheck.setVisibility(View.INVISIBLE);
                mFlashCheck.setVisibility(View.VISIBLE);
                mRubberBandCheck.setVisibility(View.INVISIBLE);
                mShakeCheck.setVisibility(View.INVISIBLE);
                mSwingCheck.setVisibility(View.INVISIBLE);
                mTadaCheck.setVisibility(View.INVISIBLE);
                mWobbleCheck.setVisibility(View.INVISIBLE);
                mJelloCheck.setVisibility(View.INVISIBLE);
                mHeartBetaCheck.setVisibility(View.INVISIBLE);
                mZoomInCheck.setVisibility(View.INVISIBLE);
                animationCode = "1";
                break;

            case R.id.rubberband:
                if (rope != null) {
                    rope.stop(true);
                }
                Techniques technique2 = Techniques.RubberBand;
                rope = YoYo.with(technique2).repeat(YoYo.INFINITE).playOn(findViewById(R.id.number));

                mWithoutCheck.setVisibility(View.INVISIBLE);
                mFlashCheck.setVisibility(View.INVISIBLE);
                mRubberBandCheck.setVisibility(View.VISIBLE);
                mShakeCheck.setVisibility(View.INVISIBLE);
                mSwingCheck.setVisibility(View.INVISIBLE);
                mTadaCheck.setVisibility(View.INVISIBLE);
                mWobbleCheck.setVisibility(View.INVISIBLE);
                mJelloCheck.setVisibility(View.INVISIBLE);
                mHeartBetaCheck.setVisibility(View.INVISIBLE);
                mZoomInCheck.setVisibility(View.INVISIBLE);
                animationCode = "2";
                break;
            case R.id.shake:
                if (rope != null) {
                    rope.stop(true);
                }
                Techniques technique3 = Techniques.Shake;
                rope = YoYo.with(technique3).repeat(YoYo.INFINITE).playOn(findViewById(R.id.number));

                mWithoutCheck.setVisibility(View.INVISIBLE);
                mFlashCheck.setVisibility(View.INVISIBLE);
                mRubberBandCheck.setVisibility(View.INVISIBLE);
                mShakeCheck.setVisibility(View.VISIBLE);
                mSwingCheck.setVisibility(View.INVISIBLE);
                mTadaCheck.setVisibility(View.INVISIBLE);
                mWobbleCheck.setVisibility(View.INVISIBLE);
                mJelloCheck.setVisibility(View.INVISIBLE);
                mHeartBetaCheck.setVisibility(View.INVISIBLE);
                mZoomInCheck.setVisibility(View.INVISIBLE);
                animationCode = "3";
                break;
            case R.id.swing:
                if (rope != null) {
                    rope.stop(true);
                }
                Techniques technique4 = Techniques.Swing;
                rope = YoYo.with(technique4).repeat(YoYo.INFINITE).playOn(findViewById(R.id.number));

                mWithoutCheck.setVisibility(View.INVISIBLE);
                mFlashCheck.setVisibility(View.INVISIBLE);
                mRubberBandCheck.setVisibility(View.INVISIBLE);
                mShakeCheck.setVisibility(View.INVISIBLE);
                mSwingCheck.setVisibility(View.VISIBLE);
                mTadaCheck.setVisibility(View.INVISIBLE);
                mWobbleCheck.setVisibility(View.INVISIBLE);
                mJelloCheck.setVisibility(View.INVISIBLE);
                mHeartBetaCheck.setVisibility(View.INVISIBLE);
                mZoomInCheck.setVisibility(View.INVISIBLE);
                animationCode = "4";
                break;
            case R.id.tada:
                if (rope != null) {
                    rope.stop(true);
                }
                Techniques technique5 = Techniques.Tada;
                rope = YoYo.with(technique5).repeat(YoYo.INFINITE).playOn(findViewById(R.id.number));

                mWithoutCheck.setVisibility(View.INVISIBLE);
                mFlashCheck.setVisibility(View.INVISIBLE);
                mRubberBandCheck.setVisibility(View.INVISIBLE);
                mShakeCheck.setVisibility(View.INVISIBLE);
                mSwingCheck.setVisibility(View.INVISIBLE);
                mTadaCheck.setVisibility(View.VISIBLE);
                mWobbleCheck.setVisibility(View.INVISIBLE);
                mJelloCheck.setVisibility(View.INVISIBLE);
                mHeartBetaCheck.setVisibility(View.INVISIBLE);
                mZoomInCheck.setVisibility(View.INVISIBLE);
                animationCode = "5";
                break;
            case R.id.wobble:
                if (rope != null) {
                    rope.stop(true);
                }
                Techniques technique6 = Techniques.Wobble;
                rope = YoYo.with(technique6).repeat(YoYo.INFINITE).playOn(findViewById(R.id.number));

                mWithoutCheck.setVisibility(View.INVISIBLE);
                mFlashCheck.setVisibility(View.INVISIBLE);
                mRubberBandCheck.setVisibility(View.INVISIBLE);
                mShakeCheck.setVisibility(View.INVISIBLE);
                mSwingCheck.setVisibility(View.INVISIBLE);
                mTadaCheck.setVisibility(View.INVISIBLE);
                mWobbleCheck.setVisibility(View.VISIBLE);
                mJelloCheck.setVisibility(View.INVISIBLE);
                mHeartBetaCheck.setVisibility(View.INVISIBLE);
                mZoomInCheck.setVisibility(View.INVISIBLE);
                animationCode = "6";
                break;
            case R.id.jello:
                if (rope != null) {
                    rope.stop(true);
                }
                Techniques technique7 = Techniques.Wave;
                rope = YoYo.with(technique7).repeat(YoYo.INFINITE).playOn(findViewById(R.id.number));

                mWithoutCheck.setVisibility(View.INVISIBLE);
                mFlashCheck.setVisibility(View.INVISIBLE);
                mRubberBandCheck.setVisibility(View.INVISIBLE);
                mShakeCheck.setVisibility(View.INVISIBLE);
                mSwingCheck.setVisibility(View.INVISIBLE);
                mTadaCheck.setVisibility(View.INVISIBLE);
                mWobbleCheck.setVisibility(View.INVISIBLE);
                mJelloCheck.setVisibility(View.VISIBLE);
                mHeartBetaCheck.setVisibility(View.INVISIBLE);
                mZoomInCheck.setVisibility(View.INVISIBLE);
                animationCode = "7";
                break;
            case R.id.heartbeat:
                if (rope != null) {
                    rope.stop(true);
                }
                Techniques technique8 = Techniques.StandUp;
                rope = YoYo.with(technique8).repeat(YoYo.INFINITE).playOn(findViewById(R.id.number));

                mWithoutCheck.setVisibility(View.INVISIBLE);
                mFlashCheck.setVisibility(View.INVISIBLE);
                mRubberBandCheck.setVisibility(View.INVISIBLE);
                mShakeCheck.setVisibility(View.INVISIBLE);
                mSwingCheck.setVisibility(View.INVISIBLE);
                mTadaCheck.setVisibility(View.INVISIBLE);
                mWobbleCheck.setVisibility(View.INVISIBLE);
                mJelloCheck.setVisibility(View.INVISIBLE);
                mHeartBetaCheck.setVisibility(View.VISIBLE);
                mZoomInCheck.setVisibility(View.INVISIBLE);
                animationCode = "8";
                break;
            case R.id.zoomin:
                if (rope != null) {
                    rope.stop(true);
                }
                Techniques technique9 = Techniques.ZoomIn;
                rope = YoYo.with(technique9).repeat(YoYo.INFINITE).playOn(findViewById(R.id.number));

                mWithoutCheck.setVisibility(View.INVISIBLE);
                mFlashCheck.setVisibility(View.INVISIBLE);
                mRubberBandCheck.setVisibility(View.INVISIBLE);
                mShakeCheck.setVisibility(View.INVISIBLE);
                mSwingCheck.setVisibility(View.INVISIBLE);
                mTadaCheck.setVisibility(View.INVISIBLE);
                mWobbleCheck.setVisibility(View.INVISIBLE);
                mJelloCheck.setVisibility(View.INVISIBLE);
                mHeartBetaCheck.setVisibility(View.INVISIBLE);
                mZoomInCheck.setVisibility(View.VISIBLE);
                animationCode = "9";

                break;
            case R.id.save_anim_color:
                //go back and save data to shared
                mAppD.saveAnimationId(animationCode);
                mAppD.saveColorId(colorCode);
                Toast.makeText(this, "تم الحفظ!", Toast.LENGTH_SHORT).show();
                onBackPressed();
                break;
            case R.id.clear_icon:
                onBackPressed();
                break;

        }
    }
}

