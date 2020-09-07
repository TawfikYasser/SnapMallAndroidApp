package com.snapmall.snapmall;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButtonToggleGroup;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class AddOffer extends AppCompatActivity  {
    private TextInputEditText mTitleEt, mPriceEt, mWebsiteEt, mCityEt;
    private TextInputLayout mPriceLayout, mSiteLayout, mLocationLayout;
    private MaterialButtonToggleGroup mCategory;
    private Button mOfferImgBtn, mApplyOffer,mB1,mB2,mB3;
    private SeekBar mSeekBar;
    private TextView mHoursTxt,AddOfferTxt,mCatText,mHTxt;
    private DatabaseReference mDatabaseReference, mDataMale, mDataFemale;
    private StorageReference mStorageReference;
    private Uri mainImageUri = null;
    private int mSeekProg = 1;
    private String mOfferType = "All";
    private AppData data;
    private ProgressDialog progressDialog;
    private ImageView mBack;
    private Spinner mSpinC;
    private String lang;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_offer);
        /// System Data START ************************************************************///
        mDatabaseReference = FirebaseDatabase.getInstance().getReference().child("OffersData");
        mDataMale = FirebaseDatabase.getInstance().getReference().child("OffersMale");
        mDataFemale = FirebaseDatabase.getInstance().getReference().child("OffersFemale");
        mStorageReference = FirebaseStorage.getInstance().getReference();
        /// System Data START ************************************************************///

        data = new AppData(this);
        progressDialog = new ProgressDialog(this);
        lang = data.getLang();
        Views();
        if(lang.equals("Ar")){
            AddOfferTxt.setText("إضافة عرض");
            mTitleEt.setHint("عنوان العرض");
            mOfferImgBtn.setText("اختار صورة العرض");
            mPriceEt.setHint("سعر العرض");
            mWebsiteEt.setHint("رابط العرض");
            mCityEt.setHint("الدولة");
            mCatText.setText("الفئة");
            mHTxt.setText("الزمن بالساعات");
            mApplyOffer.setText("تأكيد");
            mB1.setText("أنثى");
            mB2.setText("ذكر");
            mB3.setText("الجميع");
        }else{
            mPriceEt.setHint("Price");
            mTitleEt.setHint("Title");
            mWebsiteEt.setHint("Website");
            mCityEt.setHint("Country");
        }

        mCategory.addOnButtonCheckedListener(new MaterialButtonToggleGroup.OnButtonCheckedListener() {
            @Override
            public void onButtonChecked(MaterialButtonToggleGroup group, int checkedId, boolean isChecked) {


                if (isChecked) {
                    switch (checkedId) {
                        case R.id.button1:
                            mOfferType = "Female";
                            break;

                        case R.id.button2:
                            mOfferType = "Male";
                            break;
                        case R.id.button3:
                            mOfferType = "All";
                            break;
                    }
                } else {
                    if (-1 == group.getCheckedButtonId()) {
                        group.check(checkedId);
                    }
                }


            }
        });

        mBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        mOfferImgBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Toast.makeText(AddOffer.this, "Choose Image", Toast.LENGTH_SHORT).show();
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    //check the permission
                    if (ContextCompat.checkSelfPermission(AddOffer.this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                        //Toast.makeText(this, "Permission denied!", Toast.LENGTH_SHORT).show();
                        ActivityCompat.requestPermissions(AddOffer.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
                    } else {
                        //Toast.makeText(this, "Permission ok!", Toast.LENGTH_SHORT).show();
                        CropImage.activity()
                                .setGuidelines(CropImageView.Guidelines.ON)
                                .setAspectRatio(1, 1)
                                .start(AddOffer.this);
                    }
                }

            }
        });

        mSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                mHoursTxt.setText(String.valueOf(progress));
                mSeekProg = progress;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        mPriceLayout.setEndIconOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(mPriceEt.getText().toString())) {
                    if(lang.equals("Ar")){
                        Toast.makeText(AddOffer.this, "يجب كتابة السعر اولاً!", Toast.LENGTH_SHORT).show();

                    }else{
                        Toast.makeText(AddOffer.this, "Type the price before continue!", Toast.LENGTH_SHORT).show();

                    }
                } else {
                    Intent AnimPrice = new Intent(AddOffer.this, AnimationPrice.class);
                    AnimPrice.putExtra("PRICE", mPriceEt.getText().toString());
                    startActivity(AnimPrice);
                }
            }
        });
       /* mSiteLayout.setEndIconOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(AddOffer.this, "Website", Toast.LENGTH_SHORT).show();

            }
        });*/



        mLocationLayout.setEndIconOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //getCountry();
                mCityEt.setText(mSpinC.getSelectedItem().toString());
            }
        });

        mApplyOffer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Save all data to fire base
                // Image and all data

                if(!TextUtils.isEmpty(mTitleEt.getText().toString()) && !TextUtils.isEmpty(mPriceEt.getText().toString()) && !TextUtils.isEmpty(mWebsiteEt.getText().toString()) && !TextUtils.isEmpty(mCityEt.getText().toString())
                        && !TextUtils.isEmpty(mOfferType) && mSeekProg >=1 && mainImageUri !=null && !TextUtils.isEmpty(data.getAnimId()) && !TextUtils.isEmpty(data.getColorId())){
                    if(lang.equals("Ar")){
                        progressDialog.setMessage("جاري حفظ العرض، من فضلك انتظر قليلاً...");

                    }else{
                        progressDialog.setMessage("Uploading the offer, Please wait...");

                    }
                    progressDialog.show();
                    final StorageReference filepath = mStorageReference.child("OfferImages").child(mainImageUri.getLastPathSegment());
                    filepath.putFile(mainImageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            filepath.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    // Dave the data to database
                                    final String offerId = mDatabaseReference.push().getKey();
                                    if (mOfferType.equals("Male")) {

                                        final int time;
                                        time = mSeekProg *60;
                                        final long timeCountInMilliSec = time * 60 * 1000 ;
                                        DatabaseReference databaseReference = mDataMale.child(offerId);
                                        Offer offer = new Offer(offerId, mTitleEt.getText().toString(), uri.toString(), mPriceEt.getText().toString(), mWebsiteEt.getText().toString(), mCityEt.getText().toString(), mOfferType, mSeekProg + "", data.getAnimId(), data.getColorId());
                                        databaseReference.setValue(offer).addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                    final DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("OffersMale");
                                                    ref.addListenerForSingleValueEvent(new ValueEventListener() {
                                                        @Override
                                                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                                            new android.os.Handler().postDelayed(
                                                                    new Runnable() {
                                                                        @Override
                                                                        public void run() {
                                                                            //After delete the node
                                                                            ref.child(offerId).removeValue();
                                                                        }
                                                                    },timeCountInMilliSec);
                                                        }

                                                        @Override
                                                        public void onCancelled(@NonNull DatabaseError databaseError) {

                                                        }
                                                    });
                                            }
                                        });
                                    } else if (mOfferType.equals("Female")) {
                                        final int time;
                                        time = mSeekProg *60 ;
                                        final long timeCountInMilliSec = time * 60 * 1000 ;
                                        DatabaseReference databaseReference = mDataFemale.child(offerId);
                                        Offer offer = new Offer(offerId, mTitleEt.getText().toString(), uri.toString(), mPriceEt.getText().toString(), mWebsiteEt.getText().toString(), mCityEt.getText().toString(), mOfferType, mSeekProg + "", data.getAnimId(), data.getColorId());
                                        databaseReference.setValue(offer).addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                final DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("OffersFemale");
                                                ref.addListenerForSingleValueEvent(new ValueEventListener() {
                                                    @Override
                                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                                        new android.os.Handler().postDelayed(
                                                                new Runnable() {
                                                                    @Override
                                                                    public void run() {
                                                                        //After delete the node
                                                                        ref.child(offerId).removeValue();
                                                                    }
                                                                },timeCountInMilliSec);
                                                    }

                                                    @Override
                                                    public void onCancelled(@NonNull DatabaseError databaseError) {

                                                    }
                                                });
                                            }
                                        });
                                    }
                                    final int time;
                                    time = mSeekProg * 60 ;
                                    final long timeCountInMilliSec = time * 60 * 1000 ;
                                    DatabaseReference databaseReference = mDatabaseReference.child(offerId);
                                    Offer offer = new Offer(offerId, mTitleEt.getText().toString(), uri.toString(), mPriceEt.getText().toString(), mWebsiteEt.getText().toString(), mCityEt.getText().toString(), mOfferType, mSeekProg + "", data.getAnimId(), data.getColorId());
                                    databaseReference.setValue(offer).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            final DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("OffersData");
                                            ref.addListenerForSingleValueEvent(new ValueEventListener() {
                                                @Override
                                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                                    new android.os.Handler().postDelayed(
                                                            new Runnable() {
                                                                @Override
                                                                public void run() {
                                                                    //After delete the node

                                                                    ref.child(offerId).removeValue();
                                                                }
                                                            },timeCountInMilliSec);
                                                }

                                                @Override
                                                public void onCancelled(@NonNull DatabaseError databaseError) {

                                                }
                                            });
                                        }
                                    });
                                    progressDialog.dismiss();
                                }
                            });
                        }
                    });
                }else{
                    Toast.makeText(AddOffer.this, "Please Fill All Offer Data or Check Internet!", Toast.LENGTH_SHORT).show();
                }





            }
        });


    }

/*
    public void getCountry() {

        if (location != null) {
            try {
                Geocoder geocoder = new Geocoder(this);
                List<Address> addresses = null;
                addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
                String Country = addresses.get(0).getCountryName();
                mCityEt.setText(Country);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else{
            Toast.makeText(this , "please Open Maps" , Toast.LENGTH_LONG).show();
        }
    }

*/


    public void Views() {


        mSpinC = findViewById(R.id.country_spin);
        ArrayAdapter<CharSequence> arrayAdapter = ArrayAdapter.createFromResource(this,R.array.country_data,R.layout.support_simple_spinner_dropdown_item);
        mSpinC.setAdapter(arrayAdapter);
        mTitleEt = findViewById(R.id.et_offer_title);
        mPriceEt = findViewById(R.id.et_offer_price);
        mWebsiteEt = findViewById(R.id.et_offer_website);
        mCityEt = findViewById(R.id.et_offer_city);

        mPriceLayout = findViewById(R.id.et_price);
        mSiteLayout = findViewById(R.id.et_website);
        mLocationLayout = findViewById(R.id.et_city);

        mOfferImgBtn = findViewById(R.id.choose_offer_img);
        mCategory = findViewById(R.id.toggle_btn_category);
        mBack = findViewById(R.id.back_addoffer);
        mSeekBar = findViewById(R.id.seek_bar);
        mHoursTxt = findViewById(R.id.number_of_hours);
        mApplyOffer = findViewById(R.id.apply_offer_btn);
        AddOfferTxt =findViewById(R.id.addoffer_txt);
        mCatText =findViewById(R.id.cat_txt);
        mHTxt =findViewById(R.id.tih);
        mB1 =findViewById(R.id.button1);
        mB2 =findViewById(R.id.button2);
        mB3 =findViewById(R.id.button3);
    }


    // This function to choose and crop the image.
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                //image chosed ok and croped then get it as uri
                mainImageUri = result.getUri();
                if(lang.equals("Ar")){
                    Toast.makeText(this, "تم إختيار الصورة بنجاح!", Toast.LENGTH_SHORT).show();

                }else{
                    Toast.makeText(this, "Photo Uploaded!", Toast.LENGTH_SHORT).show();

                }
                //mProfileSetupImg.setImageURI(mainImageUri);
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
            }
        }
    }


    //END of onActivityResult




}
