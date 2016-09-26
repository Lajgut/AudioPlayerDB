package com.example.kir.audioplayerdb;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;

import java.io.File;
import java.util.ArrayList;

public class PlayerActivity extends AppCompatActivity implements View.OnClickListener {

    static  MediaPlayer mPlayer;
    ArrayList<File> mTracks;
    int position;
    Uri uri;
    Button btnPlayPause, btnStop, btnNext, btnPrev;
    SeekBar sb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player);

        btnPlayPause = (Button) findViewById(R.id.btnPlayPause);
        btnStop = (Button) findViewById(R.id.btnStop);
        btnNext = (Button) findViewById(R.id.btnNext);
        btnPrev = (Button) findViewById(R.id.btnPrev);
        sb = (SeekBar) findViewById(R.id.sb);

        btnPlayPause.setOnClickListener(this);
        btnStop.setOnClickListener(this);
        btnNext.setOnClickListener(this);
        btnPrev.setOnClickListener(this);

        btnPlayPause.setText("||");
        btnNext.setText(">|");
        btnPrev.setText("|<");

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
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
            switch (id){
                case R.id.btnPlayPause:
                    if (mPlayer.isPlaying()){
                        btnPlayPause.setText(">");
                        mPlayer.pause();
                    } else {
                        btnPlayPause.setText("||");
                        mPlayer.start();
                    }
                    break;

                case R.id.btnStop:
                    if (mPlayer.isPlaying()) {
                    }
                    mPlayer.stop();
                    mPlayer.release();

                    break;

                case R.id.btnNext:
                    mPlayer.stop();
                    mPlayer.release();
                    position = (position+1)%mTracks.size();
                    uri = Uri.parse(mTracks.get(position).toString());
                    mPlayer = MediaPlayer.create(getApplicationContext(),uri);
                    mPlayer.start();
                    break;

                case R.id.btnPrev:
                    mPlayer.stop();
                    mPlayer.release();
                    position = (position - 1 < 0)? mTracks.size()-1: position - 1; // если позиция больше нуля, то возвращает 1е значение, иначе 2е (типо отучаюсь говнокодить)
                    uri = Uri.parse(mTracks.get(position).toString());
                    mPlayer = MediaPlayer.create(getApplicationContext(),uri);
                    mPlayer.start();
                    break;

            }


    }
}
