package com.snapmall.snapmall;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class Countries extends AppCompatActivity {
    private ListView listView;
    private TextView textView,mCTxt;
    private String[] listItem;
    private String selectedCountry = "";
    private ImageView mBack,mSave;
    private AppData mData;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_countries);
        mData = new AppData(this);
        mCTxt = findViewById(R.id.countries_txt_act);
        if(mData.getLang().equals("Ar")){
            mCTxt.setText("الدول والمدن");
        }
        listView=(ListView)findViewById(R.id.listView);
        textView=(TextView)findViewById(R.id.textView);
        mBack =findViewById(R.id.back_country);
        mSave =findViewById(R.id.save_country);
        listItem = getResources().getStringArray(R.array.country_data);
        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, android.R.id.text1, listItem);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                // TODO Auto-generated method stub
                String value=adapter.getItem(position);
                Toast.makeText(getApplicationContext(),value,Toast.LENGTH_SHORT).show();
                selectedCountry = value;
            }
        });
        mSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mData.saveCountry(selectedCountry);
                startActivity(new Intent(Countries.this,MainActivity.class));
                finish();
            }
        });
    mBack.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            onBackPressed();
        }
    });
    }
}
