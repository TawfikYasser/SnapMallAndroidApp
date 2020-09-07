package com.snapmall.snapmall;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class TermsOfUsage extends AppCompatActivity {
    private TextView mTerms,mAr,mEn;
    private AppData mData;
    private ImageView mBack;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_terms_of_usage);
        mData = new AppData(this);
        mTerms = findViewById(R.id.terms_txt);
        mAr = findViewById(R.id.ar_terms);
        mEn = findViewById(R.id.en_terms);
        mBack = findViewById(R.id.back_terms);
        if(mData.getLang().equals("Ar")){
            mTerms.setText("شروط الإستخدام");
            mAr.setVisibility(View.VISIBLE);
        }else{
            mTerms.setText("Terms Of Usage");
            mEn.setVisibility(View.VISIBLE);
        }

        mBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }
}
