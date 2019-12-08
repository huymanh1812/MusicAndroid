package com.musichak.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.graphics.Color;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.musichak.Adapter.ViewPagerPlayListMusic;
import com.musichak.Fragment.Fragment_Dia_Nhac;
import com.musichak.Fragment.Fragment_Play_Danhsachbaihat;
import com.musichak.Model.BaiHat;
import com.musichak.R;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Random;

public class PlayMusicActivity extends AppCompatActivity {

    Toolbar toolbarPlayMusic;
    TextView txtTimeSong,txtTotalTimeSong;
    SeekBar sktime;
    ImageButton imgPlay,imgRepeat,imgNext,imgPre,imgRandom;
    ViewPager viewPagerPlayMusic;
    public static ArrayList<BaiHat> listBaiHat = new ArrayList<>();
    public static ViewPagerPlayListMusic adapternhac;
    Fragment_Dia_Nhac fragment_dia_nhac;
    Fragment_Play_Danhsachbaihat fragment_play_danhsachbaihat;
    MediaPlayer mediaPlayer;
    int position = 0;
    boolean repeat = false, checkrandom = false, next = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_music);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        GetDataFromIntent();
        init();
        eventClick();
    }

    private void eventClick() {
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (adapternhac.getItem(1) != null){
                    if (listBaiHat.size() > 0){
                        fragment_dia_nhac.PlayNhac(listBaiHat.get(0).getHinhBaiHat());
                        handler.removeCallbacks(this);
                    }else {
                        handler.postDelayed(this,300);
                    }
                }
            }
        },500);
        imgPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mediaPlayer.isPlaying()){
                    mediaPlayer.pause();
                    imgPlay.setImageResource(R.drawable.iconplay);
                }else{
                    mediaPlayer.start();
                    imgPlay.setImageResource(R.drawable.iconpause);
                }
            }

        });
        imgRepeat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (repeat == false){
                    if (checkrandom == true) {
                        checkrandom = false;
                        imgRepeat.setImageResource(R.drawable.iconsyned);
                        imgRandom.setImageResource(R.drawable.iconsuffle);
                    }
                    imgRepeat.setImageResource(R.drawable.iconsyned);
                    repeat = true;
                } else {
                    imgRepeat.setImageResource(R.drawable.iconrepeat);
                    repeat = false;
                }
            }
        });
        imgRandom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkrandom == false){
                    if (repeat == true) {
                        repeat = false;
                        imgRandom.setImageResource(R.drawable.iconsyned);
                        imgRepeat.setImageResource(R.drawable.iconsuffle);
                    }
                    imgRandom.setImageResource(R.drawable.iconsyned);
                    checkrandom = true;
                } else {
                    imgRandom.setImageResource(R.drawable.iconrepeat);
                    checkrandom = false;
                }
            }
        });
        sktime.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                mediaPlayer.seekTo(seekBar.getProgress());
            }
        });
        imgNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listBaiHat.size() > 0){
                    if (mediaPlayer.isPlaying() || mediaPlayer != null){
                        mediaPlayer.stop();
                        mediaPlayer.release();
                        mediaPlayer = null;
                    }
                    if (position < listBaiHat.size()){
                        imgPlay.setImageResource(R.drawable.iconpause);
                        position++;
                        if (repeat == true) {
                            if (position == 0) {
                                position = listBaiHat.size();
                            }
                            position -= 1;
                        }
                        if (checkrandom == true) {
                            Random random = new Random();
                            int index = random.nextInt(listBaiHat.size());
                            if (index == position) {
                                position = index - 1;
                            }
                            position = index;
                        }
                        if (position > listBaiHat.size() - 1){
                            position = 0;
                        }
                        new PlayMp3().execute(listBaiHat.get(position).getLinkBaiHat());
                        fragment_dia_nhac.PlayNhac(listBaiHat.get(position).getHinhBaiHat());
                        getSupportActionBar().setTitle(listBaiHat.get(position).getTenBaiHat());
                        UpdateTime();
                    }
                }
                imgPre.setClickable(false);
                imgNext.setClickable(false);
                Handler handler1 = new Handler();
                handler1.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        imgPre.setClickable(true);
                        imgNext.setClickable(true);
                    }
                },3000);
            }
        });
        imgPre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listBaiHat.size() > 0){
                    if (mediaPlayer.isPlaying() || mediaPlayer != null){
                        mediaPlayer.stop();
                        mediaPlayer.release();
                        mediaPlayer = null;
                    }
                    if (position < listBaiHat.size()){
                        imgPlay.setImageResource(R.drawable.iconpause);
                        position--;
                        if (position < 0) {
                            position = listBaiHat.size()-1;
                        }
                        if (repeat == true) {
                            position += 1;
                        }
                        if (checkrandom == true) {
                            Random random = new Random();
                            int index = random.nextInt(listBaiHat.size());
                            if (index == position) {
                                position = index - 1;
                            }
                            position = index;
                        }
                        new PlayMp3().execute(listBaiHat.get(position).getLinkBaiHat());
                        fragment_dia_nhac.PlayNhac(listBaiHat.get(position).getHinhBaiHat());
                        getSupportActionBar().setTitle(listBaiHat.get(position).getTenBaiHat());
                        UpdateTime();
                    }
                }
                imgPre.setClickable(false);
                imgNext.setClickable(false);
                Handler handler1 = new Handler();
                handler1.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        imgPre.setClickable(true);
                        imgNext.setClickable(true);
                    }
                },3000);
            }
        });
    }

    private void GetDataFromIntent() {
        Intent intent = getIntent();
        listBaiHat.clear();
        if (intent != null) {
            if (intent.hasExtra("cakhuc")){
                BaiHat baiHat = intent.getParcelableExtra("cakhuc");
                Log.d("BBB",baiHat.getTenBaiHat());
                listBaiHat.add(baiHat);
            }
            if (intent.hasExtra("cacbaihat")){
                ArrayList<BaiHat> listphuBaiHat = intent.getParcelableArrayListExtra("cacbaihat");
                Log.d("BBB",listphuBaiHat.get(0).getTenBaiHat());
                listBaiHat = listphuBaiHat;
            }
        }
    }

    private void init() {
        toolbarPlayMusic = findViewById(R.id.toolbarPlayMusic);
        txtTimeSong = findViewById(R.id.textviewTimeSong);
        txtTotalTimeSong = findViewById(R.id.textviewTotalTimeSong);
        sktime = findViewById(R.id.seekbarSong);
        imgPlay = findViewById(R.id.imagebuttonPlay);
        imgRepeat = findViewById(R.id.imagebuttonRepeat);
        imgNext = findViewById(R.id.imagebuttonNext);
        imgPre = findViewById(R.id.imagebuttonPreview);
        imgRandom = findViewById(R.id.imagebuttonSuffle);
        viewPagerPlayMusic = findViewById(R.id.viewpagerPlayMusic);
        setSupportActionBar(toolbarPlayMusic);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbarPlayMusic.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                if (mediaPlayer!=null) {
                    mediaPlayer.stop();
                    listBaiHat.clear();
                }
            }
        });
        toolbarPlayMusic.setTitleTextColor(Color.WHITE);
        fragment_dia_nhac = new Fragment_Dia_Nhac();
        fragment_play_danhsachbaihat = new Fragment_Play_Danhsachbaihat();
        adapternhac = new ViewPagerPlayListMusic(getSupportFragmentManager());
        adapternhac.AddFragment(fragment_play_danhsachbaihat);
        adapternhac.AddFragment(fragment_dia_nhac);
        viewPagerPlayMusic.setAdapter(adapternhac);
        fragment_dia_nhac = (Fragment_Dia_Nhac) adapternhac.getItem(1);
        if (listBaiHat.size() > 0){
            getSupportActionBar().setTitle(listBaiHat.get(0).getTenBaiHat());
            new PlayMp3().execute(listBaiHat.get(0).getLinkBaiHat());
            imgPlay.setImageResource(R.drawable.iconpause);
            Log.d("AAA","chay");
        } else Log.d("AAA","khong chay");
    }

    class PlayMp3 extends AsyncTask<String,Void,String>{

        @Override
        protected String doInBackground(String... strings) {
            return strings[0];
        }

        @Override
        protected void onPostExecute(String baihat) {
            super.onPostExecute(baihat);
            try {
                mediaPlayer = new MediaPlayer();
                mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
                mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mp) {
                        mediaPlayer.stop();
                        mediaPlayer.reset();
                    }
                });
                mediaPlayer.setDataSource(baihat);
                mediaPlayer.prepare();
            } catch (IOException e) {
                e.printStackTrace();
            }
            mediaPlayer.start();
            TimeSong();
            UpdateTime();
        }
    }

    private void TimeSong() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("mm:ss");
        txtTotalTimeSong.setText(simpleDateFormat.format(mediaPlayer.getDuration()));
        sktime.setMax(mediaPlayer.getDuration());
    }
    private void UpdateTime() {
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (mediaPlayer != null) {
                    sktime.setProgress(mediaPlayer.getCurrentPosition());
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("mm:ss");
                    txtTimeSong.setText(simpleDateFormat.format(mediaPlayer.getCurrentPosition()));
                    handler.postDelayed(this,300);
                    mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                        @Override
                        public void onCompletion(MediaPlayer mp) {
                            next = true;
                            try {
                                Thread.sleep(1000);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                    });
                }
            }
        },300);
        final Handler handler1 = new Handler();
        handler1.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (next == true) {
                    if (position < listBaiHat.size()){
                        imgPlay.setImageResource(R.drawable.iconpause);
                        position++;
                        if (repeat == true) {
                            if (position == 0) {
                                position = listBaiHat.size();
                            }
                            position -= 1;
                        }
                        if (checkrandom == true) {
                            Random random = new Random();
                            int index = random.nextInt(listBaiHat.size());
                            if (index == position) {
                                position = index - 1;
                            }
                            position = index;
                        }
                        if (position > listBaiHat.size() - 1){
                            position = 0;
                        }
                        new PlayMp3().execute(listBaiHat.get(position).getLinkBaiHat());
                        fragment_dia_nhac.PlayNhac(listBaiHat.get(position).getHinhBaiHat());
                        getSupportActionBar().setTitle(listBaiHat.get(position).getTenBaiHat());
                    }
                    imgPre.setClickable(false);
                    imgNext.setClickable(false);
                    Handler handler1 = new Handler();
                    handler1.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            imgPre.setClickable(true);
                            imgNext.setClickable(true);
                        }
                    },3000);
                    next = false;
                    handler1.removeCallbacks(this);
                } else {
                    handler1.postDelayed(this,1000);
                }
            }
        },1000);
    }
}
