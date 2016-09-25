package com.example.kir.audioplayerdb;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.io.File;
import java.util.ArrayList;

public class PlayerActivity extends AppCompatActivity implements View.OnClickListener {

    MediaPlayer mPlayer;
    ArrayList<File> mTracks;
    int position;
    Button btnPlayPause, btnStop, btnNext, btnPrev;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player);

        btnPlayPause = (Button) findViewById(R.id.btnPlayPause);
        btnStop = (Button) findViewById(R.id.btnStop);
        btnNext = (Button) findViewById(R.id.btnNext);
        btnPrev = (Button) findViewById(R.id.btnPrev);

        btnPlayPause.setOnClickListener(this);
        btnStop.setOnClickListener(this);
        btnNext.setOnClickListener(this);
        btnPrev.setOnClickListener(this);

        btnPlayPause.setText(R.string.pause);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        ArrayList<File> mTracks = (ArrayList) bundle.getParcelableArrayList("tracks");
        position = bundle.getInt("position",0);

        Uri uri = Uri.parse(mTracks.get(position).toString());
        mPlayer = MediaPlayer.create(getApplicationContext(),uri);
        mPlayer.start();
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
            switch (id){
                case R.id.btnPlayPause:
                    if (mPlayer.isPlaying()){
                        btnPlayPause.setText(R.string.play);
                        mPlayer.pause();

                    } else
                        mPlayer.start();
                        btnPlayPause.setText(R.string.pause);
                    break;

                case R.id.btnStop:
                    if (mPlayer.isPlaying()) {
                        btnPlayPause.setText(R.string.play);
                    }
                    mPlayer.stop();
                    mPlayer.release();

                    break;

                case R.id.btnNext:
                    mPlayer.stop();
                    mPlayer.release();
                    Uri uri = Uri.parse(mTracks.get(position+1).toString());
                    mPlayer = MediaPlayer.create(getApplicationContext(),uri);
                    mPlayer.start();
                    break;

                case R.id.btnPrev:

                    break;

            }


    }
}
