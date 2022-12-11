package com.example.wrbmp3;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {

    TextView txtName, txtTimeSong, txtTotal;
    SeekBar skbTime;
    ImageButton btnPrevious, btnPlay, btnNext;
    ImageView imgDisc;

    ArrayList<Song> arrSong;
    int position = 0;
    MediaPlayer mediaplayer;
    Animation animation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Demo();
        AddSong();

        KhoiTaoMediaPlayer();
        SetTimeTotal();
        UpdateTime();
        animation = AnimationUtils.loadAnimation(this, R.anim.disc_rotate);

        btnPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mediaplayer.isPlaying()) {
                    //play -> pause -> đổi icon
                    mediaplayer.pause();
                    btnPlay.setImageResource(R.drawable.play48);
                }
                else{
                    //pause -> play -> đổi icon
                    mediaplayer.start();
                    btnPlay.setImageResource(R.drawable.pause48);
                }
                SetTimeTotal();
                UpdateTime();
                imgDisc.startAnimation(animation);
            }
        });

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                position ++;
                if(position > arrSong.size()-1) {
                    position = 0;
                }
                if(mediaplayer.isPlaying()){
                    mediaplayer.stop();
                    btnPlay.setImageResource(R.drawable.play48);
                }
                KhoiTaoMediaPlayer();
                mediaplayer.start();
                btnPlay.setImageResource(R.drawable.pause48);
                SetTimeTotal();
                UpdateTime();
                imgDisc.startAnimation(animation);
            }

        }) ;

        btnPrevious.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                position --;
                if(position > 0) {
                    position = arrSong.size()-1;
                }
                if(mediaplayer.isPlaying()){
                    mediaplayer.stop();
                    btnPlay.setImageResource(R.drawable.play48);
                }
                KhoiTaoMediaPlayer();
                mediaplayer.start();
                btnPlay.setImageResource(R.drawable.pause48);
                SetTimeTotal();
                UpdateTime();
                imgDisc.startAnimation(animation);
            }
        }) ;

        skbTime.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                mediaplayer.seekTo(skbTime.getProgress());
            }
        });
    }

    private void KhoiTaoMediaPlayer(){
        mediaplayer = MediaPlayer.create(MainActivity.this, arrSong.get(position).getFile());
        txtName.setText(arrSong.get(position).getName());
    }

    private void Demo() {
        txtTimeSong = (TextView) findViewById(R.id.txtTimeSong);
        txtTotal = (TextView) findViewById(R.id.txtTotal);
        txtName = (TextView) findViewById(R.id.txtName);
        skbTime = (SeekBar) findViewById(R.id.skbTime);
        btnPrevious = (ImageButton) findViewById(R.id.btnPrevious);
        btnNext = (ImageButton) findViewById(R.id.btnNext);
        btnPlay = (ImageButton) findViewById(R.id.btnPlay);
        imgDisc = (ImageView) findViewById(R.id.imgDics);
    }

    private void AddSong() {
        arrSong = new ArrayList<>();
        arrSong.add(new Song("Bittersweet", R.raw.bittersweet));
        arrSong.add(new Song("Journey through the Decade", R.raw.journey_through_the_decade));
        arrSong.add(new Song("Rainy rose", R.raw.rainy_rose));
        arrSong.add(new Song("Wish in the dark", R.raw.wish_in_the_dark));
    }

    private void SetTimeTotal(){
        SimpleDateFormat dinhdanggio = new SimpleDateFormat("mm:ss");
        txtTotal.setText(dinhdanggio.format(mediaplayer.getDuration()));
        skbTime.setMax(mediaplayer.getDuration());
    }

    private void UpdateTime() {
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run(){
                SimpleDateFormat dinhdanggio = new SimpleDateFormat("mm:ss");
                txtTimeSong.setText(dinhdanggio.format(mediaplayer.getCurrentPosition()));
                skbTime.setProgress(mediaplayer.getCurrentPosition());
                mediaplayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mp) {
                        position ++;
                        if(position > arrSong.size()-1) {
                            position = 0;
                        }
                        if(mediaplayer.isPlaying()){
                            mediaplayer.stop();
                            btnPlay.setImageResource(R.drawable.play48);
                        }
                        KhoiTaoMediaPlayer();
                        mediaplayer.start();
                        btnPlay.setImageResource(R.drawable.pause48);
                        SetTimeTotal();
                        UpdateTime();
                    }
                });
                handler.postDelayed(this, 500);
            }
        }, 100);
    }
}