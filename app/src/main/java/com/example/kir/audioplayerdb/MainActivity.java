package com.example.kir.audioplayerdb;

import android.content.Intent;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    ListView lv;
    String[] items;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        lv = (ListView) findViewById(R.id.lv);


        //список трэков с внешнего накопителя
        final ArrayList<File> mTracks = arrayList(Environment.getExternalStorageDirectory());

        items = new String[ mTracks.size() ];

        for (int i=0; i<mTracks.size(); i++){
            items[i] = mTracks.get(i).getName();
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(),R.layout.track_layout,R.id.tv, items);
        lv.setAdapter(adapter);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                startActivity(new Intent(getApplicationContext(),PlayerActivity.class)
                        .putExtra("position", position)
                        .putExtra("tracks",mTracks));
            }
        });

    }

    public ArrayList<File> arrayList(File root){
        ArrayList<File> a1 = new ArrayList<File>();
        File[] files = root.listFiles();
        for(File file : files){                                 //взял с хабра, что то вроде - пока есть файлы, цикл прододжается
            if (file.isDirectory() && !file.isHidden()){        //если файл в искомой директории (root) и не скрыт, то
                a1.addAll(arrayList(file));                     //в переменную добавляются все файлы из списка
            } else {
                if (file.getName().endsWith(".mp3")){           //беруться все файлы которые mp3, если добавить другие форматы OGG например
                    a1.add(file);                               //то будут выводиться всякие системные звуки и прочая фигня
                }
            }
        }
        return a1;
    }

    public void toast (String text) {
        Toast.makeText(getApplicationContext(),text,Toast.LENGTH_SHORT).show();
    }
}
