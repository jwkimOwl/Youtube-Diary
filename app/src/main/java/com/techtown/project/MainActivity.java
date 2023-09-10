package com.techtown.project;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.PopupWindow;

import java.util.Calendar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.OnLifecycleEvent;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements LifecycleObserver {
    ListView recordView;
    com.google.android.material.floatingactionbutton.FloatingActionButton writeButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ArrayList<RecordItem> recordData=new ArrayList<>();
        ArrayList<String> recordprintData=new ArrayList<>();
        try {
            String uriString="content://com.techtown.project/record";
            Uri uri=new Uri.Builder().build().parse(uriString);

            String[] columns=new String[] {"_id","date","comment","video_title","video_id"};
            Cursor cursor=getContentResolver().query(uri,columns,null,null,"date ASC");

            int index=0;
            while(cursor.moveToNext()) {
                String _id=cursor.getString(cursor.getColumnIndex(columns[0]));
                String date=cursor.getString(cursor.getColumnIndex(columns[1]));
                String comment=cursor.getString(cursor.getColumnIndex(columns[2]));
                String video_title=cursor.getString(cursor.getColumnIndex(columns[3]));
                String video_id=cursor.getString(cursor.getColumnIndex(columns[4]));

                RecordItem record=new RecordItem(Integer.valueOf(_id),date,comment,video_title,video_id);
                recordData.add(record);
                recordprintData.add(String.valueOf(date)+" , "+String.valueOf(video_title));
                index+=1;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        ArrayAdapter<String> adapter=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,recordprintData);
        recordView=(ListView) findViewById(R.id.RecordView);
        recordView.setAdapter(adapter);
        recordView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> a_parent, View a_view, int a_position, long a_id) {
                final RecordItem item = (RecordItem) recordData.get(a_position);
                Intent intent = new Intent(MainActivity.this, ReadActivity.class);
                intent.putExtra("DATE", item.getDate());
                intent.putExtra("COMMENT", item.getComment());
                intent.putExtra("VIDEO_TITLE", item.getVideo_title());
                intent.putExtra("VIDEO_ID", item.getVideo_id());
                startActivity(intent);
            }
        });
        recordView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> a_parent, View a_view, int a_position, long a_id) {
                final PopupWindow popupWindow = new PopupWindow(a_view); // onClick(View v)로 받아온 View 위에 PopupWindow 생성
                // PopupWindow에 반영할 layout을 다음과 같이 생성 및 연결
                LayoutInflater layoutInflater = (LayoutInflater) getBaseContext().getSystemService(LAYOUT_INFLATER_SERVICE);
                View popup = layoutInflater.inflate(R.layout.layout_popup, null);
                popupWindow.setContentView(popup);
                popupWindow.setWindowLayoutMode(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                popupWindow.setTouchable(true); // PopupWindow 위에서 Button의 Click이 가능하도록 setTouchable(true);
                Button yesBtn = (Button) popup.findViewById(R.id.ybtn); // PopupWindow 상의 View의 Button 연결
                Button noBtn = (Button) popup.findViewById(R.id.nbtn);
                yesBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String uriString="content://com.techtown.project/record";
                        Uri uri=new Uri.Builder().build().parse(uriString);
                        String selection="_id = ?";
                        String[] selectionArgs=new String[] {String.valueOf(recordData.get(a_position).get_id())};
                        int count=getContentResolver().delete(uri,selection,selectionArgs);
                        finish();
                        startActivity(getIntent());
                        popupWindow.dismiss();
                    }
                });
                noBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        popupWindow.dismiss(); // PopupWindow의 해제
                    }
                });
                popupWindow.showAsDropDown(a_view); // PopupWindow를 View 위에 뿌려 줌. 선언하지 않을 경우, PopupWindow가 보이지 않음
                return true;
            }
        });
        writeButton=(com.google.android.material.floatingactionbutton.FloatingActionButton) findViewById(R.id.WriteButton);
        writeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this,WriteActivity.class);
                final Calendar cal = Calendar.getInstance();
                intent.putExtra("YEAR",cal.YEAR);
                intent.putExtra("MONTH",cal.MONTH);
                intent.putExtra("DAYOFMONTH",cal.DAY_OF_MONTH);
                intent.putExtra("COMMENT","");
                intent.putExtra("VIDEO_TITLE","");
                intent.putExtra("VIDEO_ID","");
                startActivity(intent);
                finish();
            }
        });
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    public void refresh() {
        finish();
        //overridePendingTransition(0, 0);
        startActivity(getIntent());
        //overridePendingTransition(0, 0);
    }
}