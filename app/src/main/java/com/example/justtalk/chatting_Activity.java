package com.example.justtalk;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import static android.view.View.VISIBLE;

public class chatting_Activity extends AppCompatActivity {
    EditText massageboc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chatting_);
    }
    public void back_imageview_onClick(View v){
        Intent intent = new Intent();
        intent.setClass(chatting_Activity.this  , MainActivity.class);
        startActivity(intent);
    }
    public void send_onclik(View view)
    {
        massageboc=findViewById(R.id.massage_view);
        String massage= String.valueOf(massageboc.getText());
        massageboc.setText("");
    }

}
