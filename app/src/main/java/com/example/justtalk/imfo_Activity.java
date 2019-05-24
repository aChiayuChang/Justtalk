package com.example.justtalk;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class imfo_Activity extends AppCompatActivity {
    ImageView img1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_imfo_);
        img1=findViewById(R.id.imageView3);
        img1.bringToFront();
    }
    public void back_imageview_onClick(View v){
        Intent intent = new Intent();
        intent.setClass(imfo_Activity.this  , MainActivity.class);
        startActivity(intent);
    }
}
