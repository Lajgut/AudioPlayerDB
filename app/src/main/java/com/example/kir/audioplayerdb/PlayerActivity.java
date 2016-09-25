package com.example.kir.audioplayerdb;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.io.File;
import java.util.ArrayList;

public class PlayerActivity extends AppCompatActivity {

    MediaPlayer mPlayer;
    ArrayList<File> mTracks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        ArrayList<File> mTracks = (ArrayList) bundle.getParcelableArrayList("tracks");
        int position = bundle.getInt("position",0);

        Uri uri = Uri.parse(mTracks.get(position).toString());
        mPlayer = MediaPlayer.create(getApplicationContext(),uri);
        mPlayer.start();
    }
}
