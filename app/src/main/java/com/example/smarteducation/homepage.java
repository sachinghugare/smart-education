package com.example.smarteducation;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

public class homepage extends AppCompatActivity {

    GridView gridView;



    String[] numberword = {"Profile","Books","Teacher","Timetable","Notice","Assignment"};

    int[] numberimage = {R.drawable.profile,R.drawable.books,R.drawable.teacher,R.drawable.timetable,R.drawable.notice,R.drawable.assignment};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage);


        gridView = findViewById(R.id.gridview);

        MainAdapter adapter = new MainAdapter(homepage.this,numberword,numberimage);
        gridView.setAdapter(adapter);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getApplicationContext(),"You clicked"+numberword[+position],Toast.LENGTH_SHORT).show();
            }
        });

    }
}
