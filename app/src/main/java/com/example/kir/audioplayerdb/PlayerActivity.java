package com.example.kir.audioplayerdb;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.SeekBar;

import java.io.File;
import java.util.ArrayList;

public class PlayerActivity extends AppCompatActivity {

    static  MediaPlayer mPlayer;
    ArrayList<File> mTracks;
    int position;
    Uri uri;
    ImageButton btnPlayPause, btnNext, btnPrev;
    SeekBar sb;
    Thread threadSB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player);

        btnPlayPause = (ImageButton) findViewById(R.id.btnPlayPause);
        btnNext = (ImageButton) findViewById(R.id.btnNext);
        btnPrev = (ImageButton) findViewById(R.id.btnPrev);
        sb = (SeekBar) findViewById(R.id.sb);

        threadSB = new Thread(){
            public void run() {
                int duration = mPlayer.getDuration();
                int currentPosition = 0;
                while (currentPosition < duration) {
                    try {
                        sleep(500);
                        currentPosition = mPlayer.getCurrentPosition();
                        sb.setProgress(currentPosition);
                    } catch (InterruptedException e){
                        e.printStackTrace();
                    }
                }
            }
        };


        if (mPlayer!=null){             //остановить проигрывание трека при переходе к следующему
            mPlayer.stop();
            mPlayer.release();
        }

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        final ArrayList<File> mTracks = (ArrayList) bundle.getParcelableArrayList("tracks"); //видимо это можно переписать получше, но я хз как
        position = bundle.getInt("position",0);

        uri = Uri.parse(mTracks.get(position).toString());
        mPlayer = MediaPlayer.create(getApplicationContext(),uri);
        mPlayer.start();

        sb.setMax(mPlayer.getDuration());
        threadSB.start();

        btnPlayPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mPlayer.isPlaying()){
                    mPlayer.pause();
                    btnPlayPause.setImageResource(R.drawable.play);
                }
                else {
                    mPlayer.start();
                    btnPlayPause.setImageResource(R.drawable.pause);
                }
            }
        });

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPlayer.stop();
                mPlayer.release();
                position=(position+1)%mTracks.size();
                uri=Uri.parse(mTracks.get(position).toString());
                mPlayer=MediaPlayer.create(getApplicationContext(),uri);
                mPlayer.start();
                btnPlayPause.setImageResource(R.drawable.pause);
                sb.setMax(mPlayer.getDuration());
            }
        });

        btnPrev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPlayer.stop();
                mPlayer.release();
                position = (position - 1 < 0)? mTracks.size()-1: position - 1; // если позиция больше нуля, то возвращает 1е значение, иначе 2е (типо отучаюсь говнокодить)
                uri=Uri.parse(mTracks.get(position).toString());
                mPlayer=MediaPlayer.create(getApplicationContext(),uri);
                mPlayer.start();
                btnPlayPause.setImageResource(R.drawable.pause);
                sb.setMax(mPlayer.getDuration());
            }
        });

        sb.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            mPlayer.seekTo(seekBar.getProgress());
            }
        });

    }

}
