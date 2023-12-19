package com.demo.batteryanim.activities;

import android.animation.Animator;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.ParcelFileDescriptor;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import android.widget.ToggleButton;
import android.widget.VideoView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.airbnb.lottie.LottieAnimationView;



import com.demo.batteryanim.utils.Anim_SharedPrefsUtils;
import com.demo.batteryanim.R;


import java.io.IOException;
import java.util.ArrayList;


public class Anim_PreviewActivity extends AppCompatActivity {
    public static TextView txtPer;
    public int anim;
    public int audio;
    int audioId;
    public int closing;
    public int custom;
    public int duration;
    ImageView imgAnim;
    ImageView imgBack;
    ImageView imgDismiss;
    ImageView imgOpenSettings;
    ImageView imgPreview;
    Uri imgUri;
    public boolean isLock;
    public boolean isPer;
    public boolean isSound;
    LinearLayout layoutBattery;
    RelativeLayout layoutVideo;
    LottieAnimationView lottieAnimationView;
    Dialog mainDialog;
    public MediaPlayer mediaPlayer;
    int raw = 0;
    public boolean show;
    Toolbar toolbar;
    TextView txtApply;
    Uri uri;
    String uriCustom;
    MediaPlayer videoMediaPlayer;
    VideoView videoView;

    private int getSpinnerPos(int i) {
        if (i == 5) {
            return 0;
        }
        if (i == 10) {
            return 1;
        }
        if (i == 30) {
            return 2;
        }
        return i == 60 ? 3 : 4;
    }

    public int getDuration(int i) {
        if (i == 0) {
            return 5;
        }
        if (i == 1) {
            return 10;
        }
        if (i == 2) {
            return 30;
        }
        return i == 3 ? 60 : -1;
    }

    public static int dpToPx(int i) {
        return (int) (((float) i) * Resources.getSystem().getDisplayMetrics().density);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public static Bitmap rotateImageIfRequired(Context context, Bitmap bitmap, Uri uri) throws IOException, IOException {
        int attributeInt = new ExifInterface(context.getContentResolver().openInputStream(uri)).getAttributeInt(ExifInterface.TAG_ORIENTATION, 1);
        if (attributeInt == 3) {
            return rotateImageCamera(bitmap, 180);
        }
        if (attributeInt == 6) {
            return rotateImageCamera(bitmap, 90);
        }
        if (attributeInt != 8) {
            return bitmap;
        }
        return rotateImageCamera(bitmap, 270);
    }

    public static Bitmap rotateImageCamera(Bitmap bitmap, int i) {
        Matrix matrix = new Matrix();
        matrix.postRotate((float) i);
        Bitmap createBitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
        bitmap.recycle();
        return createBitmap;
    }

    public static Bitmap uriToBitmap(Context context, Uri uri) {
        try {
            ParcelFileDescriptor openFileDescriptor = context.getContentResolver().openFileDescriptor(uri, "r");
            Bitmap decodeFileDescriptor = BitmapFactory.decodeFileDescriptor(openFileDescriptor.getFileDescriptor());
            openFileDescriptor.close();
            return decodeFileDescriptor;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.anim_activity_anim_preview);
        initView();
        initData();
        TextView textView = txtPer;
        textView.setText(Anim_MainActivity.getBatteryPercentage(this) + "%");
        int intExtra = getIntent().getIntExtra("raw", 0);
        this.raw = intExtra;
        Log.i("rawIs", String.valueOf(intExtra));
        this.audioId = getIntent().getIntExtra("audio", 0);
        if (this.raw == R.raw.anim23) {
            this.lottieAnimationView.setPadding(0, 0, 0, 0);
            this.lottieAnimationView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        }
        if (getIntent().getStringExtra("uri") != null) {
            this.uri = Uri.parse(getIntent().getStringExtra("uri"));
        }
        if (getIntent().getStringExtra("img_uri") != null) {
            this.imgUri = Uri.parse(getIntent().getStringExtra("img_uri"));
        }
        if (this.raw == this.anim) {
            this.txtApply.setText("Applied");
        }
        Uri uri = this.uri;
        if (uri != null && uri.toString().equals(this.uriCustom.toString())) {
            this.txtApply.setText("Applied");
        }
        Uri uri2 = this.imgUri;
        if (uri2 != null && uri2.toString().equals(this.uriCustom.toString())) {
            this.txtApply.setText("Applied");
        }
        if (this.uri != null) {
            this.layoutVideo.setVisibility(View.VISIBLE);
            setVideoPlayer();
        } else if (this.raw != 0) {
            this.layoutVideo.setVisibility(View.GONE);
            this.imgAnim.setVisibility(View.GONE);
            this.lottieAnimationView.setAnimation(this.raw);
            this.lottieAnimationView.addAnimatorListener(new Animator.AnimatorListener() { 
                @Override 
                public void onAnimationCancel(Animator animator) {
                }

                @Override 
                public void onAnimationEnd(Animator animator) {
                }

                @Override 
                public void onAnimationRepeat(Animator animator) {
                }

                @Override 
                public void onAnimationStart(Animator animator) {
                }
            });
            setMediaPlayer();
        } else {
            this.layoutVideo.setVisibility(View.GONE);
            this.imgAnim.setVisibility(View.VISIBLE);
            Bitmap bitmap = null;
            try {
                bitmap = rotateImageIfRequired(this, uriToBitmap(this, this.imgUri), this.imgUri);
            } catch (IOException e) {
                e.printStackTrace();
            }
            this.imgAnim.setImageBitmap(bitmap);
        }
        this.imgPreview.setOnClickListener(new View.OnClickListener() { 
            @Override 
            public void onClick(View view) {
                Anim_PreviewActivity.this.imgPreview.setVisibility(View.GONE);
                Anim_PreviewActivity.this.imgBack.setVisibility(View.GONE);
                Anim_PreviewActivity.this.imgOpenSettings.setVisibility(View.GONE);
                Anim_PreviewActivity.this.txtApply.setVisibility(View.GONE);
                Anim_PreviewActivity.this.layoutBattery.setVisibility(View.VISIBLE);
                Anim_PreviewActivity.this.initData();
                if (Anim_PreviewActivity.this.isPer) {
                    Anim_PreviewActivity.this.layoutBattery.setVisibility(View.VISIBLE);
                } else {
                    Anim_PreviewActivity.this.layoutBattery.setVisibility(View.GONE);
                }
            }
        });
        this.lottieAnimationView.setOnClickListener(new View.OnClickListener() { 
            @Override 
            public void onClick(View view) {
                Anim_PreviewActivity.this.imgPreview.setVisibility(View.VISIBLE);
                Anim_PreviewActivity.this.imgBack.setVisibility(View.VISIBLE);
                Anim_PreviewActivity.this.layoutBattery.setVisibility(View.GONE);
                Anim_PreviewActivity.this.imgOpenSettings.setVisibility(View.VISIBLE);
                Anim_PreviewActivity.this.txtApply.setVisibility(View.VISIBLE);
            }
        });
        this.imgBack.setOnClickListener(new View.OnClickListener() { 
            @Override 
            public void onClick(View view) {
                Anim_PreviewActivity.this.onBackPressed();
            }
        });
        this.imgOpenSettings.setOnClickListener(new View.OnClickListener() { 
            @Override 
            public final void onClick(View view) {
                Anim_PreviewActivity.this.lambda$onCreate$0$Anim_PreviewActivity(view);
            }
        });
        this.txtApply.setOnClickListener(new View.OnClickListener() { 
            @Override 
            public void onClick(View view) {
                Anim_PreviewActivity.this.Applyanimation();
                try {
                    Anim_PreviewActivity anim_PreviewActivity = Anim_PreviewActivity.this;
                    anim_PreviewActivity.loadAMBAd(anim_PreviewActivity);
                } catch (Exception unused) {
                    Anim_PreviewActivity.this.Applyanimation();
                }
            }
        });
    }

    public void loadAMBAd(final Activity activity) {

        Applyanimation();

    }

    public void Applyanimation() {
        Anim_SharedPrefsUtils.setIntegerPreference(this, "custom", -1);
        Anim_SharedPrefsUtils.setIntegerPreference(this, "anim_id", this.raw);
        Anim_SharedPrefsUtils.setIntegerPreference(this, "audio_id", this.audioId);
        if (this.uri != null) {
            Anim_SharedPrefsUtils.setIntegerPreference(this, "custom", 0);
            Anim_SharedPrefsUtils.setStringPreference(this, "custom_uri", this.uri.toString());
            Anim_SharedPrefsUtils.setIntegerPreference(this, "anim_id", -1);
            initData();
            Log.d("uriCustom", this.uriCustom + "abc");
        }
        if (this.imgUri != null) {
            Anim_SharedPrefsUtils.setIntegerPreference(this, "custom", 1);
            Anim_SharedPrefsUtils.setStringPreference(this, "custom_uri", this.imgUri.toString());
            initData();
            Log.d("uriCustom", this.uriCustom + "abc");
        }
        MediaPlayer mediaPlayer = this.mediaPlayer;
        if (mediaPlayer != null) {
            mediaPlayer.reset();
        }
        finish();
    }


    public void lambda$onCreate$0$Anim_PreviewActivity(View view) {
        initData();
        showSettingPopup(true);
    }

    private void initView() {
        this.lottieAnimationView = (LottieAnimationView) findViewById(R.id.animationView);
        this.videoView = (VideoView) findViewById(R.id.videoView);
        this.txtApply = (TextView) findViewById(R.id.txtConfirm);
        this.imgAnim = (ImageView) findViewById(R.id.imgAnim);
        txtPer = (TextView) findViewById(R.id.txtPercentage);
        this.imgBack = (ImageView) findViewById(R.id.imgSettings);
        this.imgOpenSettings = (ImageView) findViewById(R.id.imgOpenSettings);
        this.imgPreview = (ImageView) findViewById(R.id.imgPreview);
        this.layoutBattery = (LinearLayout) findViewById(R.id.layoutBattery);
        this.layoutVideo = (RelativeLayout) findViewById(R.id.layoutVideo);
    }

    private void setVideoPlayer() {
        Uri uri = this.uri;
        if (uri != null) {
            this.videoView.setVideoURI(uri);
            this.videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() { 
                @Override 
                public final void onPrepared(MediaPlayer mediaPlayer) {
                    Anim_PreviewActivity.this.lambda$setVideoPlayer$1$Anim_PreviewActivity(mediaPlayer);
                }
            });
        }
    }

    public void lambda$setVideoPlayer$1$Anim_PreviewActivity(MediaPlayer mediaPlayer) {
        this.videoView.start();
        initData();
        if (!this.isSound) {
            mediaPlayer.setVolume(0.0f, 0.0f);
        }
        mediaPlayer.setLooping(true);
        this.videoMediaPlayer = mediaPlayer;
    }

    public void setMediaPlayer() {
        initData();
        MediaPlayer mediaPlayer = this.mediaPlayer;
        if (mediaPlayer != null) {
            mediaPlayer.reset();
        }
        boolean z = this.isSound;
        if (z) {
            int i = this.audioId;
            if (i != 0) {
                MediaPlayer create = MediaPlayer.create(this, i);
                this.mediaPlayer = create;
                create.setLooping(true);
                this.mediaPlayer.start();
                return;
            }
            MediaPlayer mediaPlayer2 = this.videoMediaPlayer;
            if (mediaPlayer2 != null && z) {
                mediaPlayer2.setVolume(1.0f, 1.0f);
                return;
            }
            return;
        }
        MediaPlayer mediaPlayer3 = this.videoMediaPlayer;
        if (mediaPlayer3 != null) {
            mediaPlayer3.setVolume(0.0f, 0.0f);
        }
    }

    @Override 
    public void onBackPressed() {
        if (this.imgPreview.getVisibility() == View.GONE) {
            this.imgPreview.setVisibility(View.VISIBLE);
            this.imgBack.setVisibility(View.VISIBLE);
            this.layoutBattery.setVisibility(View.GONE);
            this.txtApply.setVisibility(View.VISIBLE);
            this.imgOpenSettings.setVisibility(View.VISIBLE);
            return;
        }
        MediaPlayer mediaPlayer = this.mediaPlayer;
        if (mediaPlayer != null) {
            mediaPlayer.reset();
        }
        super.onBackPressed();
    }

    @Override
    
    public void onDestroy() {
        MediaPlayer mediaPlayer = this.mediaPlayer;
        if (mediaPlayer != null) {
            mediaPlayer.reset();
        }
        super.onDestroy();
    }

    public void initData() {
        this.anim = Anim_SharedPrefsUtils.getIntegerPreference(this, "anim_id", R.raw.anim3);
        this.audio = Anim_SharedPrefsUtils.getIntegerPreference(this, "audio_id", R.raw.music3_new);
        this.isSound = Anim_SharedPrefsUtils.getBooleanPreference(this, "sound", true);
        this.isLock = Anim_SharedPrefsUtils.getBooleanPreference(this, "lock", false);
        this.isPer = Anim_SharedPrefsUtils.getBooleanPreference(this, "per", true);
        this.show = Anim_SharedPrefsUtils.getBooleanPreference(this, "show", true);
        this.duration = Anim_SharedPrefsUtils.getIntegerPreference(this, "duration", 5);
        this.closing = Anim_SharedPrefsUtils.getIntegerPreference(this, "closing", 0);
        this.custom = Anim_SharedPrefsUtils.getIntegerPreference(this, "custom", -1);
        this.uriCustom = Anim_SharedPrefsUtils.getStringPreference(this, "custom_uri");
    }

    private void showSettingPopup(boolean z) {
        initData();
        MediaPlayer mediaPlayer = this.mediaPlayer;
        if (mediaPlayer != null && this.isSound) {
            mediaPlayer.setVolume(0.0f, 0.0f);
        }
        MediaPlayer mediaPlayer2 = this.videoMediaPlayer;
        if (mediaPlayer2 != null && this.isSound) {
            mediaPlayer2.setVolume(0.0f, 0.0f);
        }
        View inflate = getLayoutInflater().inflate(R.layout.anim_settings_layout, (ViewGroup) null);
        this.imgDismiss = (ImageView) inflate.findViewById(R.id.imgBack);
        ToggleButton toggleButton = (ToggleButton) inflate.findViewById(R.id.switchLock);
        ToggleButton toggleButton2 = (ToggleButton) inflate.findViewById(R.id.switchSound);
        ToggleButton toggleButton3 = (ToggleButton) inflate.findViewById(R.id.switchPer);
        Spinner spinner = (Spinner) inflate.findViewById(R.id.spinnerDuration);
        RelativeLayout relativeLayout = (RelativeLayout) inflate.findViewById(R.id.layoutSound);
        Spinner spinner2 = (Spinner) inflate.findViewById(R.id.spinnerClosingMethod);
        ToggleButton toggleButton4 = (ToggleButton) inflate.findViewById(R.id.switchShow);
        this.mainDialog = new Dialog(this);
        ArrayList arrayList = new ArrayList();
        arrayList.add("5 secs");
        arrayList.add("10 secs");
        arrayList.add("30 secs");
        arrayList.add("1 min");
        arrayList.add("loop");
        ArrayAdapter arrayAdapter = new ArrayAdapter(this, (int) R.layout.anim_spinner_item, arrayList);
        ArrayList arrayList2 = new ArrayList();
        arrayList2.add("Double click");
        arrayList2.add("Single click");
        ArrayAdapter arrayAdapter2 = new ArrayAdapter(this, (int) R.layout.anim_spinner_item, arrayList2);
        spinner.setAdapter((SpinnerAdapter) arrayAdapter);
        spinner2.setAdapter((SpinnerAdapter) arrayAdapter2);
        if (this.imgUri != null) {
            relativeLayout.setVisibility(View.GONE);
        }
        if (z) {
            toggleButton2.setChecked(this.isSound);
            toggleButton.setChecked(this.isLock);
            toggleButton3.setChecked(this.isPer);
            toggleButton4.setChecked(this.show);
            spinner2.setSelection(this.closing);
            spinner.setSelection(getSpinnerPos(this.duration));
        }
        toggleButton2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() { 
            @Override 
            public void onCheckedChanged(CompoundButton compoundButton, boolean z2) {
                Anim_SharedPrefsUtils.setBooleanPreference(Anim_PreviewActivity.this, "sound", z2);
            }
        });
        toggleButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() { 
            @Override 
            public void onCheckedChanged(CompoundButton compoundButton, boolean z2) {
                Anim_SharedPrefsUtils.setBooleanPreference(Anim_PreviewActivity.this, "lock", z2);
            }
        });
        toggleButton3.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() { 
            @Override 
            public void onCheckedChanged(CompoundButton compoundButton, boolean z2) {
                Anim_SharedPrefsUtils.setBooleanPreference(Anim_PreviewActivity.this, "per", z2);
            }
        });
        toggleButton4.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() { 
            @Override 
            public void onCheckedChanged(CompoundButton compoundButton, boolean z2) {
                Anim_SharedPrefsUtils.setBooleanPreference(Anim_PreviewActivity.this, "show", z2);
            }
        });
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() { 
            @Override 
            public void onNothingSelected(AdapterView<?> adapterView) {
            }

            @Override 
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long j) {
                Anim_PreviewActivity anim_PreviewActivity = Anim_PreviewActivity.this;
                Anim_SharedPrefsUtils.setIntegerPreference(anim_PreviewActivity, "duration", anim_PreviewActivity.getDuration(i));
            }
        });
        spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() { 
            @Override 
            public void onNothingSelected(AdapterView<?> adapterView) {
            }

            @Override 
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long j) {
                Anim_SharedPrefsUtils.setIntegerPreference(Anim_PreviewActivity.this, "closing", i);
            }
        });
        this.mainDialog.requestWindowFeature(1);
        this.imgDismiss.setOnClickListener(new View.OnClickListener() { 
            @Override 
            public void onClick(View view) {
                Anim_PreviewActivity.this.mainDialog.dismiss();
            }
        });
        this.mainDialog.getWindow().setBackgroundDrawableResource(17170445);
        this.mainDialog.setContentView(inflate, new LinearLayout.LayoutParams(getResources().getDisplayMetrics().widthPixels, getResources().getDisplayMetrics().heightPixels));
        this.mainDialog.setOnDismissListener(new DialogInterface.OnDismissListener() { 
            @Override 
            public void onDismiss(DialogInterface dialogInterface) {
                Anim_PreviewActivity.this.setMediaPlayer();
            }
        });
        this.mainDialog.show();
    }

    @Override 
    public void onResume() {
        super.onResume();
        MediaPlayer mediaPlayer = this.mediaPlayer;
        if (mediaPlayer != null) {
            mediaPlayer.start();
        }
    }

    @Override 
    public void onPause() {
        MediaPlayer mediaPlayer = this.mediaPlayer;
        if (mediaPlayer != null) {
            mediaPlayer.pause();
        }
        super.onPause();
    }
}
