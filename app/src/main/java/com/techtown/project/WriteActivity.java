package com.techtown.project;

import android.app.DatePickerDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Calendar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.OnLifecycleEvent;

public class WriteActivity extends AppCompatActivity {
    DatePickerDialog picker;
    TextView dateView,videoTitleView;
    EditText commentView;
    int Year,MonthOfYear,DayOfMonth;
    String Comment,Video_Title,Video_Id;
    Button searchButton;
    com.google.android.material.floatingactionbutton.FloatingActionButton writeButton;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write);
        Intent intent=getIntent();
        Year=intent.getIntExtra("YEAR",2021);
        MonthOfYear=intent.getIntExtra("MONTH",0);
        DayOfMonth=intent.getIntExtra("DAYOFMONTH",0);
        Comment=intent.getStringExtra("COMMENT");
        Video_Title=intent.getStringExtra("VIDEO_TITLE");
        Video_Id=intent.getStringExtra("VIDEO_ID");
        dateView= (TextView) findViewById(R.id.DateView);
        dateView.setInputType(InputType.TYPE_NULL);
        commentView=(EditText) findViewById(R.id.CommentView);
        dateView.setText(String.valueOf(Year)+"/"+String.valueOf(MonthOfYear)+"/"+String.valueOf(DayOfMonth));
        dateView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar cal = Calendar.getInstance();
                int day = cal.get(Calendar.DAY_OF_MONTH);
                int month = cal.get(Calendar.MONTH);
                int year = cal.get(Calendar.YEAR);
                // date picker dialog
                picker = new DatePickerDialog(WriteActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                dateView.setText( year+ "/" + (monthOfYear + 1) + "/" + dayOfMonth);
                                Year=year;
                                MonthOfYear=monthOfYear+1;
                                DayOfMonth=dayOfMonth;
                            }
                        }, year, month, day);
                picker.show();
            }
        });
        videoTitleView=(TextView)findViewById(R.id.VideoTitleView);
        videoTitleView.setText(Video_Title);
        searchButton=(Button) findViewById(R.id.SearchButton);
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent newintent=new Intent(WriteActivity.this,SearchActivity.class);
                newintent.putExtra("YEAR",Year);
                newintent.putExtra("MONTH",MonthOfYear);
                newintent.putExtra("DAYOFMONTH",DayOfMonth);
                newintent.putExtra("COMMENT",commentView.getText().toString());
                newintent.putExtra("VIDEO_TITLE",Video_Title);
                newintent.putExtra("VIDEO_ID",Video_Id);
                newintent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(newintent);
                finish();
            }
        });
        writeButton=(com.google.android.material.floatingactionbutton.FloatingActionButton) findViewById(R.id.WriteButton);
        writeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String uriString="content://com.techtown.project/record";
                Uri uri=new Uri.Builder().build().parse(uriString);

                Cursor cursor=getContentResolver().query(uri,null,null,null,null);
                String[] columns=cursor.getColumnNames();

                ContentValues values=new ContentValues();
                values.put("date",String.valueOf(Year)+"/"+String.valueOf(MonthOfYear)+"/"+String.valueOf(DayOfMonth));
                values.put("comment",commentView.getText().toString());
                values.put("video_title",Video_Title);
                values.put("video_id",Video_Id);

                uri=getContentResolver().insert(uri,values);
                Intent intent=new Intent(WriteActivity.this,MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    public void refresh() {
        finish();
        overridePendingTransition(0, 0);
        startActivity(getIntent());
        overridePendingTransition(0, 0);
    }
}