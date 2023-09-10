package com.techtown.project;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper{
    private static final String DATABASE_NAME="record.db";
    private static final int DATABASE_VERSION=1;

    public static final String TABLE_NAME="record";
    public static final String RECORD_ID="_id";
    public static final String RECORD_DATE="date";
    public static final String RECORD_COMMENT="comment";
    public static final String RECORD_VIDEO_TITLE="video_title";
    public static final String RECORD_VIDEO_ID="video_id";

    public static final String[] ALL_COLUMNS={RECORD_ID,RECORD_DATE,RECORD_COMMENT,RECORD_VIDEO_TITLE,RECORD_VIDEO_ID};

    private static final String CREATE_TABLE=
            "CREATE TABLE "+TABLE_NAME+" ("+
                    RECORD_ID+" INTEGER PRIMARY KEY AUTOINCREMENT,"+
                    RECORD_DATE+" TEXT,"+
                    RECORD_COMMENT+" TEXT,"+
                    RECORD_VIDEO_TITLE+" TEXT,"+
                    RECORD_VIDEO_ID+" TEXT"+
                    "); ";

    public DatabaseHelper(Context context) {
        super(context,DATABASE_NAME,null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) { db.execSQL(CREATE_TABLE); }

    @Override
    public void onUpgrade(SQLiteDatabase db,int oldVersion,int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);
        onCreate(db);
    }
}
