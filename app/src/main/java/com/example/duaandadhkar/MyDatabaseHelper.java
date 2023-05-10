package com.example.duaandadhkar;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.CursorWindow;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import java.lang.reflect.Field;

public class MyDatabaseHelper extends SQLiteOpenHelper {
    private Context context;
    private static final String DATABASE_NAME = "duaandadhkardb.db";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_NAME = "app_audio";
    private static final String COLUMN_ID = "_id";
    private static final String COLUMN_AUDIO_NAME = "name";
    private static final String COLUMN_AUDIO = "audio";

    public MyDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE_NAME + " (" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + COLUMN_AUDIO_NAME + " TEXT, "+ COLUMN_AUDIO + " BLOB);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // This method is called when the database needs to be upgraded.
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }
    public boolean checkIfRecordExists(String audioName) {
        SQLiteDatabase db = getReadableDatabase();
        String[] columns = {COLUMN_AUDIO_NAME};
        String selection = COLUMN_AUDIO_NAME + " = ?";
        String[] selectionArgs = {audioName};
        String limit = "1";
        Cursor cursor = db.query(TABLE_NAME, columns, selection, selectionArgs, null, null, null, limit);
        boolean exists = (cursor.getCount() > 0);
        cursor.close();
        return exists;
    }
    public void insertBlobData(byte[] data, String audioName) {
        if(!checkIfRecordExists(audioName)) {
            SQLiteDatabase db = getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            contentValues.put(COLUMN_AUDIO, data);
            contentValues.put(COLUMN_AUDIO_NAME, audioName);
            long result = db.insert(TABLE_NAME, null, contentValues);
        }
    }

    @SuppressLint("Range")
    public byte[] getBlobData(String audioName) {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.query(TABLE_NAME, new String[] { COLUMN_AUDIO }, COLUMN_AUDIO_NAME + "=?", new String[] { audioName }, null, null, null);
        cursor.moveToFirst();
//        try {
//            Field field = CursorWindow.class.getDeclaredField("sCursorWindowSize");
//            field.setAccessible(true);
//            field.set(null, 100 * 1024 * 1024); //the 100MB is the new size
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
        @SuppressLint("Range") byte[] blob = cursor.getBlob(cursor.getColumnIndex(COLUMN_AUDIO));
        cursor.close();
        return blob;
    }
}