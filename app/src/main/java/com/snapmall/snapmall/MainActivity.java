package com.snapmall.snapmall;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener{

    private SwipeRefreshLayout swipeRefreshLayout;
    private RecyclerView mRecView;
    private ImageView mSearch,mAddOffer;
    private AdapterC adapterC ;
    private TextView mCountryPage;
    private DatabaseReference mReferenceAll,mReferenceMale,mReferenceFemale;
    private Query mQueryAll,mQueryMale,mQueryFemale ;
    private AppData mData;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mData = new AppData(this);
        String country = mData.getCountry();
        mCountryPage =findViewById(R.id.country_text);
        String language = mData.getLang();
        if(language.equals("Ar")){
            mCountryPage.setText("الدولة");
        }
        if(country.isEmpty()){
            mCountryPage.setText("Country");

            mReferenceAll = FirebaseDatabase.getInstance().getReference().child("OffersData");
            mQueryAll = mReferenceAll.orderByChild("offerTitle");
            mReferenceAll.keepSynced(true);


            mReferenceMale = FirebaseDatabase.getInstance().getReference().child("OffersMale");
            mQueryMale = mReferenceMale.orderByChild("offerTitle");
            mReferenceMale.keepSynced(true);

            mReferenceFemale = FirebaseDatabase.getInstance().getReference().child("OffersFemale");
            mQueryFemale = mReferenceFemale.orderByChild("offerTitle");
            mReferenceFemale.keepSynced(true);
        }else{
            mCountryPage.setText(country);
            mReferenceAll = FirebaseDatabase.getInstance().getReference().child("OffersData");
            mQueryAll = mReferenceAll.orderByChild("offerLocation").equalTo(country);
            mReferenceAll.keepSynced(true);


            mReferenceMale = FirebaseDatabase.getInstance().getReference().child("OffersMale");
            mQueryMale = mReferenceMale.orderByChild("offerLocation").equalTo(country);
            mReferenceMale.keepSynced(true);


            mReferenceFemale = FirebaseDatabase.getInstance().getReference().child("OffersFemale");
            mQueryFemale = mReferenceFemale.orderByChild("offerLocation").equalTo(country);
            mReferenceFemale.keepSynced(true);
        }





        mSearch =findViewById(R.id.search_icon);
        mAddOffer =findViewById(R.id.add_offer_icon);
        swipeRefreshLayout = findViewById(R.id.main_ref);
        swipeRefreshLayout.setOnRefreshListener(this);

        mCountryPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,Countries.class));
                finish();
            }
        });
        mSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Go to search activity

                startActivity(new Intent(MainActivity.this,Search.class));

            }
        });
        mAddOffer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,AddOffer.class));
            }
        });


        FloatingActionButton myFab = (FloatingActionButton) findViewById(R.id.floating_action_button);
        myFab.setColorFilter(Color.WHITE);
        myFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,Favorite.class));
            }
        });


        mRecView = findViewById(R.id.rec);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setReverseLayout(true);
        linearLayoutManager.setStackFromEnd(true);
        mRecView.setLayoutManager(linearLayoutManager);



        if(mData.getGender().equals("Male")){


            final FirebaseRecyclerOptions<Offer> options =
                    new FirebaseRecyclerOptions.Builder<Offer>()
                            .setQuery(mQueryMale,Offer.class)
                            .build();

            adapterC = new AdapterC(options,this);

            mRecView.setAdapter(adapterC);


        }else if(mData.getGender().equals("Female")){
            final FirebaseRecyclerOptions<Offer> options =
                    new FirebaseRecyclerOptions.Builder<Offer>()
                            .setQuery(mQueryFemale,Offer.class)
                            .build();

            adapterC = new AdapterC(options,this);

            mRecView.setAdapter(adapterC);
        }else{
            final FirebaseRecyclerOptions<Offer> options =
                    new FirebaseRecyclerOptions.Builder<Offer>()
                            .setQuery(mQueryAll,Offer.class)
                            .build();

            adapterC = new AdapterC(options,this);
            mRecView.setAdapter(adapterC);
        }
    }
    //This function to stop get data.
    @Override
    public void onStop() {
        super.onStop();
        adapterC.stopListening();
    }
    //ENd of onStop
    // This function to start get data.
    @Override
    public void onStart() {
        super.onStart();
        adapterC.startListening();

    }


    //END of onStart
    // This function to handle refresh ball.
    @Override
    public void onRefresh() {
        swipeRefreshLayout.setRefreshing(true);
        refreshList();
    }
    //END of onRefresh

    // This function to refresh the adapter.
    void refreshList(){
        adapterC.notifyDataSetChanged();
        swipeRefreshLayout.setRefreshing(false);
    }
    //END of refreshList


}

