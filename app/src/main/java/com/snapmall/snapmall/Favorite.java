package com.snapmall.snapmall;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.ImageView;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Favorite extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {

    private ImageView mBack,mSettings;
    private SwipeRefreshLayout swipeRefreshLayout;
    private DatabaseReference mReference;
    private RecyclerView mRecView;
    private AdapterC adapterC;
    private TextView mFavT;
    private AppData mData;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite);

        mReference = FirebaseDatabase.getInstance().getReference().child("FavOffers");
        mData = new AppData(this);
        mFavT = findViewById(R.id.fav_headtxt);
        mBack = findViewById(R.id.back_fav_icon);
        mSettings =findViewById(R.id.settings_icon);
        swipeRefreshLayout = findViewById(R.id.fav_refresh);
        swipeRefreshLayout.setOnRefreshListener(this);

        if(mData.getLang().equals("Ar")){
            mFavT.setText("المفضلة");
        }
        mBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        mSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Favorite.this,Settings.class));
            }
        });

        mRecView = findViewById(R.id.rec_fav);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setReverseLayout(true);
        linearLayoutManager.setStackFromEnd(true);
        mRecView.setLayoutManager(linearLayoutManager);


        final FirebaseRecyclerOptions<Offer> options =
                new FirebaseRecyclerOptions.Builder<Offer>()
                        .setQuery(mReference,Offer.class)
                        .build();

        adapterC = new AdapterC(options,this);
        mRecView.setAdapter(adapterC);

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
