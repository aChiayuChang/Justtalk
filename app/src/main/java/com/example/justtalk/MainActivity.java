package com.example.justtalk;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    Button bt1,bt2,bt3;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bt1=findViewById(R.id.button);

    }
    public void setting_btn_onClick(View v){
        Intent intent = new Intent();
        intent.setClass(MainActivity.this  , setting_Activity.class);
        startActivity(intent);
    }
    public void chatting_btn_onClick(View v){

        if(getSharedPreferences("setting",MODE_PRIVATE).getString("user_name","").equals("")){
            new AlertDialog.Builder(MainActivity.this).setTitle("未設定使用者名稱").setMessage("要前往設定使用者名稱嗎？")
                    .setPositiveButton("好", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            Intent intent = new Intent();
                            intent.setClass(MainActivity.this,setting_Activity.class);
                            startActivity(intent);
                        }
                    }).setNegativeButton("稍後設定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                        }
                    }).show();

        }
        else {
            Intent intent = new Intent();
            intent.setClass(MainActivity.this, chatting_Activity.class);
            startActivity(intent);
        }
    }
    public void imfo_btn_onClick(View v){
        Intent intent = new Intent();
        intent.setClass(MainActivity.this  , imfo_Activity.class);
        startActivity(intent);
    }
}
