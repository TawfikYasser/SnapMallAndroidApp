package com.snapmall.snapmall;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.MenuItemCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import androidx.appcompat.widget.SearchView;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

public class Search extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {
    private DatabaseReference mDatabaseReference;
    private RecyclerView mSearchRec;
    private EditText mEditSearch;
    private SwipeRefreshLayout mSwipeRef;
    private String searchQuery;
    private AdapterC adapterC ;
    private Button mS;
    private Toolbar mToolbar;
    private AppData mData;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        mDatabaseReference = FirebaseDatabase.getInstance().getReference().child("OffersData");
        //mEditSearch = findViewById(R.id.search_et);
        mToolbar = findViewById(R.id.toolbar_search);
        setSupportActionBar(mToolbar);
        mData = new AppData(this);
        mSearchRec = findViewById(R.id.search_recycler);
        mSwipeRef = findViewById(R.id.swipe_search);
        mSwipeRef.setOnRefreshListener(this);
        if (getSupportActionBar() != null) {
            if(mData.getLang().equals("Ar")){
                getSupportActionBar().setTitle("بحث");
            }else{
                getSupportActionBar().setTitle("Search");
            }

                getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }




    }

        @Override
        public boolean onSupportNavigateUp() {
            onBackPressed();
            return true;
        }

        //This function to stop get data.
        @Override
        public void onStop() {
            super.onStop();
            if(adapterC != null){
                adapterC.stopListening();

            }
        }
        //ENd of onStop

        // This function to start get data.
        @Override
        public void onStart() {
            super.onStart();
            if(adapterC !=null){
                adapterC.startListening();

            }


        }
    // This function to handle refresh ball.
    @Override
    public void onRefresh() {
        mSwipeRef.setRefreshing(true);
        refreshList();
    }
    //END of onRefresh

    // This function to refresh the adapter.
    void refreshList(){
        adapterC.notifyDataSetChanged();
        mSwipeRef.setRefreshing(false);
    }
    //END of refreshList


    private void firebaseSearch(String searchText){
        String q = searchText.toLowerCase();
        Query FirebaseQ = mDatabaseReference.orderByChild("offerTitle").startAt(searchText).endAt(searchText +"\uf8ff");
        final FirebaseRecyclerOptions<Offer> options =
                new FirebaseRecyclerOptions.Builder<Offer>()
                        .setQuery(FirebaseQ,Offer.class)
                        .build();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setReverseLayout(true);
        linearLayoutManager.setStackFromEnd(true);
        mSearchRec.setLayoutManager(linearLayoutManager);
        adapterC = new AdapterC(options,this);
        adapterC.startListening();
        mSearchRec.setAdapter(adapterC);

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search_menu, menu);
        MenuItem sItem = menu.findItem(R.id.search_menu);
        SearchView searchView = (SearchView) sItem.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                firebaseSearch(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                firebaseSearch(newText);
                return false;
            }
        });
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.search_menu:
                return true;

            default:
                return false;
        }
    }
}
