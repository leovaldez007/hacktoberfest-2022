package com.example.myjournal;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.HashSet;

public class MainActivity extends AppCompatActivity {
    static ArrayList<String> names = new ArrayList<>();
    static ArrayAdapter<String> adapter;
    FloatingActionButton button;
    SharedPreferences sharedPreferences;
    int image = R.drawable.ic_baseline_delete_24;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        ListView list = findViewById(R.id.listView);
        button = findViewById(R.id.additionButton);
        sharedPreferences = getApplicationContext().getSharedPreferences("com.example.myjournal", Context.MODE_PRIVATE);
        HashSet<String> hashSet = (HashSet<String>) sharedPreferences.getStringSet("notes",null);
        if (hashSet == null){
            names.add("Add a Note !!");
        }else{
            names = new ArrayList<>(hashSet);
        }
        adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,names);
        list.setAdapter(adapter);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getApplicationContext(),noteEditor.class);
                intent.putExtra("namesIndex",i);
                startActivity(intent);
            }
        });

        list.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {

                final int itemToDelete = i;

                new AlertDialog.Builder(MainActivity.this)
                        .setIcon(R.drawable.ic_baseline_content_cut_24)
                        .setTitle("Are you sure?")
                        .setMessage("You sure You want to Delete this note?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                names.remove(itemToDelete);
                                adapter.notifyDataSetChanged();

                                HashSet<String> set = new HashSet<>(MainActivity.names);
                                sharedPreferences.edit().putStringSet("notes",set).apply();
                            }
                        })
                        .setNegativeButton("No",null)
                        .show();
                return true;
            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),noteEditor.class);
                startActivity(intent);
            }
        });

    }
}