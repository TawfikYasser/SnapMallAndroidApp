package com.snapmall.snapmall;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

public class AdapterC extends FirebaseRecyclerAdapter<Offer, AdapterC.MyViewHolder> {

    TimerOffe timerOffe;

    private AppData appData;
    private YoYo.YoYoString rope;
    private Context context;
    private DatabaseReference mFavData;
    private DatabaseReference mDataOffer;
    boolean mProcessFav = false;
    private long timeCountInMilliSeconds = 1 * 60000;
    private CountDownTimer countDownTimer;
    String hourTime;
    String hours;

    public AdapterC(@NonNull FirebaseRecyclerOptions<Offer> options, Context context) {
        super(options);
        this.context = context;
        mFavData = FirebaseDatabase.getInstance().getReference().child("FavOffers");
        mDataOffer = FirebaseDatabase.getInstance().getReference().child("OffersData");
        mFavData.keepSynced(true);
        mDataOffer.keepSynced(true);
    }

    @Override
    protected void onBindViewHolder(@NonNull final MyViewHolder holder, final int position, @NonNull final Offer model) {
        appData = new AppData(context);
        final String offerId = model.getOfferId();
        final String key = getRef(position).getKey();
        holder.setFavIcon(key);
        String aId = model.getPriceId();
        holder.mTitle.setText(model.getOfferTitle());
        final String uri = model.getOfferImage();
        Glide.with(holder.itemView.getContext()).load(uri).into(holder.mImage);
        if (aId.equals("0")) {
            holder.mPrice.setText(model.getOfferPrice());
            String d = model.getColorId();
            holder.mPrice.setTextColor(Integer.parseInt(d));
        } else if (aId.equals("1")) {
            holder.mPrice.setText(model.getOfferPrice());
            String d = model.getColorId();
            holder.mPrice.setTextColor(Integer.parseInt(d));
            Techniques technique6 = Techniques.Flash;
            rope = YoYo.with(technique6).repeat(YoYo.INFINITE).playOn(holder.mPrice);

        } else if (aId.equals("2")) {
            holder.mPrice.setText(model.getOfferPrice());
            String d = model.getColorId();
            holder.mPrice.setTextColor(Integer.parseInt(d));
            Techniques technique6 = Techniques.RubberBand;
            rope = YoYo.with(technique6).repeat(YoYo.INFINITE).playOn(holder.mPrice);

        } else if (aId.equals("3")) {
            holder.mPrice.setText(model.getOfferPrice());
            String d = model.getColorId();
            holder.mPrice.setTextColor(Integer.parseInt(d));
            Techniques technique6 = Techniques.Shake;
            rope = YoYo.with(technique6).repeat(YoYo.INFINITE).playOn(holder.mPrice);

        } else if (aId.equals("4")) {
            holder.mPrice.setText(model.getOfferPrice());
            String d = model.getColorId();
            holder.mPrice.setTextColor(Integer.parseInt(d));
            Techniques technique6 = Techniques.Swing;
            rope = YoYo.with(technique6).repeat(YoYo.INFINITE).playOn(holder.mPrice);

        } else if (aId.equals("5")) {
            holder.mPrice.setText(model.getOfferPrice());
            String d = model.getColorId();
            holder.mPrice.setTextColor(Integer.parseInt(d));
            Techniques technique6 = Techniques.Tada;
            rope = YoYo.with(technique6).repeat(YoYo.INFINITE).playOn(holder.mPrice);

        } else if (aId.equals("6")) {
            holder.mPrice.setText(model.getOfferPrice());
            String d = model.getColorId();
            holder.mPrice.setTextColor(Integer.parseInt(d));
            Techniques technique6 = Techniques.Wobble;
            rope = YoYo.with(technique6).repeat(YoYo.INFINITE).playOn(holder.mPrice);

        } else if (aId.equals("7")) {
            holder.mPrice.setText(model.getOfferPrice());
            String d = model.getColorId();
            holder.mPrice.setTextColor(Integer.parseInt(d));
            Techniques technique6 = Techniques.Wave;
            rope = YoYo.with(technique6).repeat(YoYo.INFINITE).playOn(holder.mPrice);

        } else if (aId.equals("8")) {
            holder.mPrice.setText(model.getOfferPrice());
            String d = model.getColorId();
            holder.mPrice.setTextColor(Integer.parseInt(d));
            Techniques technique6 = Techniques.StandUp;
            rope = YoYo.with(technique6).repeat(YoYo.INFINITE).playOn(holder.mPrice);

        } else if (aId.equals("9")) {
            holder.mPrice.setText(model.getOfferPrice());
            String d = model.getColorId();
            holder.mPrice.setTextColor(Integer.parseInt(d));
            Techniques technique6 = Techniques.ZoomIn;
            rope = YoYo.with(technique6).repeat(YoYo.INFINITE).playOn(holder.mPrice);

        }


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = model.getOfferWebsite();
                Intent offerUrl = new Intent(Intent.ACTION_VIEW);
                offerUrl.setData(Uri.parse(url));
                context.startActivity(offerUrl);
            }
        });

        holder.mFavIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mProcessFav = true;
                mFavData.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        if (mProcessFav) {


                            if (dataSnapshot.hasChild(key)) {
                                mFavData.child(key).removeValue();
                                if (appData.getLang().equals("Ar")) {
                                    Toast.makeText(context, "مُسح من المفضلة!", Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(context, "Removed From Favorite!", Toast.LENGTH_SHORT).show();
                                }

                                mProcessFav = false;

                            } else {

                                mFavData.child(key).setValue(model);
                                if (appData.getLang().equals("Ar")) {
                                    Toast.makeText(context, "أُضيف للمفضلة!", Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(context, "Added To Favorite!", Toast.LENGTH_SHORT).show();
                                }

                                mProcessFav = false;

                            }
                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }

        });

    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_offer, parent, false);

        MyViewHolder MVH = new MyViewHolder(view);

        return MVH;
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        TextView mTitle, mPrice, mLeftTime;
        ImageView mImage, mFavIcon;
        DatabaseReference mFData;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            mTitle = itemView.findViewById(R.id.offer_title);
            mPrice = itemView.findViewById(R.id.offer_price);
            mImage = itemView.findViewById(R.id.offer_image);
            mFavIcon = itemView.findViewById(R.id.fav_icon);
            mFData = FirebaseDatabase.getInstance().getReference().child("FavOffers");
            mFData.keepSynced(true);
        }

        public void setFavIcon(final String offer_Key) {

            mFData.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (dataSnapshot.hasChild(offer_Key)) {
                        mFavIcon.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_favorite_red_24dp));
                    } else {
                        mFavIcon.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_favorite_border_black_24dp));
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });

        }

    }

}
