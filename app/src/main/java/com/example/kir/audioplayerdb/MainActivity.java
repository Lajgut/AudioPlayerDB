package com.example.kir.audioplayerdb;

import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    ListView lv;
    String item;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        lv = (ListView) findViewById(R.id.lv);

        ArrayList<File> mTracks = arrayList(Environment.getExternalStorageDirectory());

        for (int i=0; i<mTracks.size(); i++){
            toast(mTracks.get(i).getName());
        }


    }

    public ArrayList<File> arrayList(File root){
        ArrayList<File> a1 = new ArrayList<File>();
        File[] files = root.listFiles();
        for(File file : files){
            if (file.isDirectory() && !file.isHidden()){
                a1.addAll(arrayList(file));
            } else {
                if (file.getName().endsWith(".mp3")){
                    a1.add(file);
                }
            }
        }
        return a1;
    }

    public void toast (String text) {
        Toast.makeText(getApplicationContext(),text,Toast.LENGTH_SHORT).show();
    }
}
