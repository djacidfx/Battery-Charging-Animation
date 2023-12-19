package com.demo.batteryanim.adapters;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.recyclerview.widget.RecyclerView;

import com.airbnb.lottie.LottieAnimationView;
import com.bumptech.glide.Glide;
import com.demo.batteryanim.R;
import com.demo.batteryanim.activities.Anim_PreviewActivity;
import com.demo.batteryanim.model.Anim;
import com.demo.batteryanim.utils.Anim_SharedPrefsUtils;


import java.util.ArrayList;


public class Anim_ListAdapter extends RecyclerView.Adapter<Anim_ListAdapter.ViewHolder> {
    ArrayList<Anim> anims;
    Activity context;
    int raw;
    int checked = -1;
    public boolean isLoad = false;

    public Anim_ListAdapter(Activity activity, ArrayList<Anim> arrayList) {
        this.context = activity;
        this.anims = arrayList;
    }

    @Override 
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        return new ViewHolder(LayoutInflater.from(this.context).inflate(R.layout.anim_animation_row, viewGroup, false));
    }

    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        final Anim anim = this.anims.get(i);
        this.raw = Anim_SharedPrefsUtils.getIntegerPreference(this.context, "anim_id", R.raw.anim3);
        if (this.anims.get(i).getJsonRes() == this.raw) {
            this.checked = i;
        }
        if (anim.getCustom() != -1) {
            this.checked = 0;
        }
        if (this.checked == i) {
            viewHolder.imgChecked.setVisibility(View.VISIBLE);
        } else {
            viewHolder.imgChecked.setVisibility(View.GONE);
        }
        Log.d("custom", anim.getCustom() + " " + i);
        if (anim.getCustom() == -1 || anim.getUri() == null) {
            if (i == this.anims.size() - 1) {
                viewHolder.gifImageView.setPadding(0, 0, 0, 0);
                viewHolder.gifImageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            }
            viewHolder.gifImageView.setVisibility(View.GONE);
            viewHolder.imgAnimation.setVisibility(View.GONE);



            viewHolder.animationView.setAnimation(anim.getJsonRes());
            viewHolder.animationView.setOnClickListener(new View.OnClickListener() { 
                @Override 
                public void onClick(View view) {
                    if (anim.getCustom() == -1) {
                        Intent intent = new Intent(Anim_ListAdapter.this.context, Anim_PreviewActivity.class);
                        intent.putExtra("raw", anim.getJsonRes());
                        intent.putExtra("audio", anim.getAudioRes());

                        Anim_ListAdapter.this.context.startActivity(intent);

                    } else if (anim.getCustom() == 0) {
                        Intent intent2 = new Intent(Anim_ListAdapter.this.context, Anim_PreviewActivity.class);
                        intent2.putExtra("uri", anim.getUri().toString());

                        Anim_ListAdapter.this.context.startActivity(intent2);

                    } else {
                        Intent intent3 = new Intent(Anim_ListAdapter.this.context, Anim_PreviewActivity.class);
                        intent3.putExtra("img_uri", anim.getUri().toString());

                        Anim_ListAdapter.this.context.startActivity(intent3);

                    }
                }
            });
            return;
        }
        viewHolder.imgAnimation.setVisibility(View.VISIBLE);
        viewHolder.animationView.setVisibility(View.GONE);
        viewHolder.gifImageView.setVisibility(View.GONE);
        Glide.with(this.context).load(anim.getUri()).into(viewHolder.imgAnimation);


        viewHolder.imgAnimation.setOnClickListener(new View.OnClickListener() { 
            @Override 
            public void onClick(View view) {
                if (anim.getCustom() == -1) {
                    Anim_ListAdapter.this.isLoad = false;
                    Intent intent = new Intent(Anim_ListAdapter.this.context, Anim_PreviewActivity.class);
                    intent.putExtra("raw", anim.getJsonRes());
                    intent.putExtra("audio", anim.getAudioRes());

                    Anim_ListAdapter.this.context.startActivity(intent);

                } else if (anim.getCustom() == 0) {
                    Intent intent2 = new Intent(Anim_ListAdapter.this.context, Anim_PreviewActivity.class);
                    intent2.putExtra("uri", anim.getUri().toString());

                    Anim_ListAdapter.this.context.startActivity(intent2);

                } else {
                    Intent intent3 = new Intent(Anim_ListAdapter.this.context, Anim_PreviewActivity.class);
                    intent3.putExtra("img_uri", anim.getUri().toString());

                    Anim_ListAdapter.this.context.startActivity(intent3);

                }
            }
        });
    }


    @Override 
    public int getItemCount() {
        return this.anims.size();
    }

    
    public class ViewHolder extends RecyclerView.ViewHolder {
        LottieAnimationView animationView;
        ImageView gifImageView;
        ImageView imgAnimation;
        ImageView imgChecked;

        public ViewHolder(View view) {
            super(view);
            this.imgAnimation = (ImageView) view.findViewById(R.id.imgAnimation);
            this.imgChecked = (ImageView) view.findViewById(R.id.imgDone);
            this.animationView = (LottieAnimationView) view.findViewById(R.id.animationView);
            this.gifImageView = (ImageView) view.findViewById(R.id.gifView);
        }
    }
}
