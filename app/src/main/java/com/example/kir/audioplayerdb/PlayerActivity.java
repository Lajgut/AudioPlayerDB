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

public class PlayerActivity extends AppCompatActivity implements View.OnClickListener {

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

        btnPlayPause.setOnClickListener(this);
        btnNext.setOnClickListener(this);
        btnPrev.setOnClickListener(this);

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
        ArrayList<File> mTracks = (ArrayList) bundle.getParcelableArrayList("tracks");
        position = bundle.getInt("position",0);

        uri = Uri.parse(mTracks.get(position).toString());
        mPlayer = MediaPlayer.create(getApplicationContext(),uri);
        mPlayer.start();
        sb.setMax(mPlayer.getDuration());

        threadSB.start();

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

    @Override
    public void onClick(View view) {
        int id = view.getId();
            switch (id){
                case R.id.btnPlayPause:
                    if (mPlayer.isPlaying()){
                        btnPlayPause.setImageResource(R.drawable.play);
                        mPlayer.pause();
                    } else {
                        btnPlayPause.setImageResource(R.drawable.pause);
                        mPlayer.start();
                    }
                    break;

                case R.id.btnNext:
                    mPlayer.stop();
                    mPlayer.release();
                    position = (position+1)%mTracks.size();
                    uri = Uri.parse(mTracks.get(position).toString());
                    mPlayer = MediaPlayer.create(getApplicationContext(),uri);
                    mPlayer.start();
                    sb.setMax(mPlayer.getDuration());
                    break;

                case R.id.btnPrev:
                    mPlayer.stop();
                    mPlayer.release();
                    position = (position - 1 < 0)? mTracks.size()-1: position - 1; // если позиция больше нуля, то возвращает 1е значение, иначе 2е (типо отучаюсь говнокодить)
                    uri = Uri.parse(mTracks.get(position).toString());
                    mPlayer = MediaPlayer.create(getApplicationContext(),uri);
                    mPlayer.start();
                    sb.setMax(mPlayer.getDuration());
                    break;

            }


    }
}
