package com.example.justtalk;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import java.util.Calendar;

import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.app.TimePickerDialog;
import android.widget.TimePicker;
import android.widget.Toast;

public class setting_Activity extends AppCompatActivity {
    private ImageView img;
    private TextView selectTime;
    private EditText username;
    private Switch notify;
    private Spinner theme;
    private int hour_p;
    private int minute_p;
    private String user_name;
    private Boolean bool_notify;
    private Calendar c = Calendar.getInstance();
    private String[] theme_array = {"theme1", "theme2", "theme3"};
    private String theme_text="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting_);

        SharedPreferences setting = getSharedPreferences("setting", MODE_PRIVATE);

        img = findViewById(R.id.imageView6);
        img.bringToFront();
        selectTime = findViewById(R.id.selected_time);
        username = findViewById(R.id.username);
        notify = findViewById(R.id.switch1);
        theme = findViewById(R.id.spinner);
        bool_notify = setting.getBoolean("notify",false);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,R.array.theme, R.layout.theme_spinner);
        adapter.setDropDownViewResource(R.layout.theme_spinner);
        theme.setAdapter(adapter);
        theme.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                theme_text = adapterView.getItemAtPosition(i).toString();
                //Toast.makeText(adapterView.getContext(),theme_text,Toast.LENGTH_LONG).show();
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

        if (setting.getString("user_name", "") .equals("")) {
            hour_p = c.get(Calendar.HOUR_OF_DAY);
            minute_p = c.get(Calendar.MINUTE);
            selectTime.setText(String.format("%02d:%02d", hour_p, minute_p));
            notify.setChecked(false);
            theme.setSelection(0);
        } else {
            hour_p = setting.getInt("hour", c.get(Calendar.HOUR_OF_DAY));
            minute_p = setting.getInt("minute", c.get(Calendar.MINUTE));
            selectTime.setText(String.format("%02d:%02d", hour_p,
                    minute_p));
            username.setText(setting.getString("user_name", ""));
            notify.setChecked(setting.getBoolean("notify", false));
            theme.setSelection(((ArrayAdapter)theme.getAdapter()).getPosition(setting.getString("theme","")));
        }
        notify.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(compoundButton.isChecked()){
                    bool_notify=true;
                }else{
                    bool_notify=false;
                }
            }
        });


    }

    public void selectTime(View view){  Calendar c = Calendar.getInstance();

         new TimePickerDialog(setting_Activity.this,2, new TimePickerDialog.OnTimeSetListener(){

            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                selectTime.setText(String.format("%02d:%02d",hourOfDay,minute));
                hour_p = hourOfDay;
                minute_p = minute;
            }
        }, c.get(Calendar.HOUR_OF_DAY), c.get(Calendar.MINUTE), false).show();
    }
    public void back_imageview_onClick(View v){
        SharedPreferences setting = getSharedPreferences("setting",MODE_PRIVATE);

        String name_temp = username.getText().toString();
        String time_temp = selectTime.getText().toString();
        Boolean notify_temp = notify.isChecked();

        if(isEmpty()){
            new AlertDialog.Builder(setting_Activity.this).setTitle("警告").setMessage("未輸入使用者名稱\n確定要離開嗎？")
                    .setPositiveButton("離開", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            Intent intent = new Intent();
                            intent.setClass(setting_Activity.this  , MainActivity.class);
                            startActivity(intent);
                        }
                    })
                    .setNegativeButton("否", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                        }
                    }).show();
        }
        else if(isEdited()){
            new AlertDialog.Builder(setting_Activity.this).setTitle("設定尚未儲存").setMessage("確定要離開嗎？")
                    .setPositiveButton("是", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            Intent intent = new Intent();
                            intent.setClass(setting_Activity.this  , MainActivity.class);
                            startActivity(intent);
                        }
                    }).setNegativeButton("否", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                }
            }).show();
        }
        else{
            Intent intent = new Intent(setting_Activity.this,MainActivity.class);
            startActivity(intent);
        }

    }

    public void click_Confirm(View v){
        if(isEmpty()){
            new AlertDialog.Builder(setting_Activity.this).setTitle("警告").setMessage("使用者名稱不可為空白")
                    .setPositiveButton("好", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                        }
                    }).show();
        }
        else if(!isEdited()){
            Intent intent = new Intent(setting_Activity.this,MainActivity.class);
            startActivity(intent);
        }
        else {
            new AlertDialog.Builder(setting_Activity.this).setTitle("確認修改").setMessage("確定要修改設定？")
                    .setPositiveButton("是", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            user_name = username.getText().toString();
                            refresh();
                            Toast.makeText(setting_Activity.this,"修改完畢",Toast.LENGTH_SHORT).show();
                        }
                    }).setNegativeButton("否", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                }
            }).show();
        }

    }
    public void refresh() {
        SharedPreferences setting = getSharedPreferences("setting", MODE_PRIVATE);

        setting.edit().putInt("hour", hour_p)
                .putInt("minute", minute_p)
                .putString("user_name", user_name)
                .putBoolean("notify", bool_notify)
                .putString("theme", theme_text)
                .apply();
        show();

    }
    public void reset(View v){
        new AlertDialog.Builder(setting_Activity.this).setTitle("重置").setMessage("確定要重置所有設定？")
                .setPositiveButton("是", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        getSharedPreferences("setting",MODE_PRIVATE).edit().clear().apply();
                        show();
                    }
                })
                .setNegativeButton("否", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                    }
                }).show();
    }
    public void show(){
        SharedPreferences setting = getSharedPreferences("setting",MODE_PRIVATE);
        username.setText(setting.getString("user_name",""));
        selectTime.setText(String.format("%02d:%02d",setting.getInt("hour",c.get(Calendar.HOUR_OF_DAY)),setting.getInt("minute",c.get(Calendar.MINUTE))));
        notify.setChecked(setting.getBoolean("notify", false));
        theme.setSelection(0);
        if (setting.getBoolean("notify", false)) {
            Intent intent = new Intent();
            intent.setClass(setting_Activity.this, alarm_Activity.class);
            startActivity(intent);
        }
    }
    public Boolean isEmpty(){
        if(username.getText().toString().equals(""))
            return true;
        return false;
    }
    public Boolean isEdited(){
        SharedPreferences setting = getSharedPreferences("setting",MODE_PRIVATE);

        String name_temp = username.getText().toString();
        String time_temp = selectTime.getText().toString();
        Boolean notify_temp = notify.isChecked();
        String theme_temp = theme.getSelectedItem().toString();

        if(!name_temp.equals(setting.getString("user_name",""))
                || !time_temp.equals(String.format("%02d:%02d",setting.getInt("hour",0),setting.getInt("minute",0)))
                || notify_temp!=setting.getBoolean("notify",false)
                || !theme_temp.equals(setting.getString("theme",""))){
            return true;
        }
        return false;
    }
}
