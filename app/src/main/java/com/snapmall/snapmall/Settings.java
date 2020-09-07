package com.snapmall.snapmall;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.button.MaterialButtonToggleGroup;

public class Settings extends AppCompatActivity {
    private MaterialButtonToggleGroup mGender, mLang;
    private String mGenderType, mLangApp;
    private Button mSave, mSaveL,mB1,mB2,mB3,mBL1,mBL2,mContact,mTerms;
    private ImageView mBack;
    private AppData data;
    private String lang;
    private TextView mGen,mLan,mSM,mSettingsTxt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        data = new AppData(this);
        lang = data.getLang();
        mGender = findViewById(R.id.toggle_btn_gender);
        mLang = findViewById(R.id.toggle_btn_lang);
        mSave = findViewById(R.id.save_settings);
        mSaveL = findViewById(R.id.save_settings_lang);
        mBack = findViewById(R.id.back_settings);
        mGen = findViewById(R.id.genTxt);
        mLan = findViewById(R.id.mLangTxt);
        mSettingsTxt =findViewById(R.id.settings_txt);
        mSM = findViewById(R.id.snT);
        mB1 = findViewById(R.id.button1s);
        mB2 =findViewById(R.id.button2s);
        mB3 =findViewById(R.id.button3s);
        mBL1 = findViewById(R.id.button1l);
        mBL2 = findViewById(R.id.button2l);
        mContact =findViewById(R.id.contact_us_btn);
        mContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mailTo = "support@snapmall.app";
                String[] mailTos = mailTo.split(",");
                String mailSubject = "رسالة اقتراح أو رأي";
                Intent mailIntent = new Intent(Intent.ACTION_SEND);
                mailIntent.putExtra(Intent.EXTRA_EMAIL,mailTos);
                mailIntent.putExtra(Intent.EXTRA_SUBJECT,mailSubject);
                mailIntent.putExtra(Intent.EXTRA_TEXT,"");
                mailIntent.setType("message/rfc822");
                startActivity(Intent.createChooser(mailIntent,"Choose an email app:"));
            }
        });
        mTerms =findViewById(R.id.terms_btn);
        mTerms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Settings.this,TermsOfUsage.class));
            }
        });
        if(lang.equals("Ar")){
            mSettingsTxt.setText("الإعدادات");
            mB1.setText("انثى");
            mB2.setText("ذكر");
            mB3.setText("الجميع");
            mGen.setText("النوع");
            mGen.setGravity(View.FOCUS_RIGHT);
            mLan.setText("اللغة");
            mLan.setGravity(View.FOCUS_RIGHT);
            mSM.setText("سناب مول");
            mContact.setText("تواصل معنا");
            mTerms.setText("شروط الإستخدام");
            mSave.setText("حفظ");
            mSaveL.setText("حفظ");
        }

        mBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        mSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                data.saveGender(mGenderType);
                if (lang.equals("Ar")) {
                    Toast.makeText(Settings.this, "من فضلك قم بغلق وفتح التطبيق مرة أخرى!", Toast.LENGTH_SHORT).show();

                } else {
                    Toast.makeText(Settings.this, "Please restart the application!", Toast.LENGTH_SHORT).show();

                }
            }
        });
        mSaveL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                data.saveLang(mLangApp);
                if (lang.equals("Ar")) {
                    Toast.makeText(Settings.this, "من فضلك قم بغلق وفتح التطبيق مرة أخرى!", Toast.LENGTH_SHORT).show();

                } else {
                    Toast.makeText(Settings.this, "Please restart the application!", Toast.LENGTH_SHORT).show();

                }
            }
        });
        mGender.addOnButtonCheckedListener(new MaterialButtonToggleGroup.OnButtonCheckedListener() {
            @Override
            public void onButtonChecked(MaterialButtonToggleGroup group, int checkedId, boolean isChecked) {
                if (isChecked) {
                    switch (checkedId) {
                        case R.id.button1s:
                            mGenderType = "Male";

                            break;

                        case R.id.button2s:
                            mGenderType = "Female";

                            break;
                        case R.id.button3s:
                            mGenderType = "All";

                            break;
                    }
                } else {
                    if (-1 == group.getCheckedButtonId()) {
                        group.check(checkedId);
                    }
                }

            }
        });

        mLang.addOnButtonCheckedListener(new MaterialButtonToggleGroup.OnButtonCheckedListener() {
            @Override
            public void onButtonChecked(MaterialButtonToggleGroup group, int checkedId, boolean isChecked) {
                if (isChecked) {
                    switch (checkedId) {
                        case R.id.button1l:
                            mLangApp = "En";
                            break;

                        case R.id.button2l:
                            mLangApp = "Ar";

                            break;

                    }
                } else {
                    if (-1 == group.getCheckedButtonId()) {
                        group.check(checkedId);
                    }
                }

            }
        });


    }
}
