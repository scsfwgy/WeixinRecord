package com.gaoyuan.weixinrecord.history;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.gaoyuan.weixinrecord.example.Record;

public class DBManager {
    private DBHelper helper;
    private SQLiteDatabase db;

    public DBManager(Context context) {
        helper = new DBHelper(context);
        //因为getWritableDatabase内部调用了mContext.openOrCreateDatabase(mName, 0, mFactory);
        //所以要确保context已初始化,我们可以把实例化DBManager的步骤放在Activity的onCreate里
    }


    public void add(Record record) {
        record.setId(UUID.randomUUID().toString());
        db = helper.getWritableDatabase();
        db.beginTransaction();  //开始事务
        try {
            db.execSQL("INSERT INTO Record VALUES(?, ?, ?, ?)", new Object[]{record.getId(), record.getPath(), record.getSecond(), record.isPlayed() ? 0 : 1});
            db.setTransactionSuccessful();  //设置事务成功完成
        } finally {
            db.endTransaction();    //结束事务
            db.close();
        }
        Log.e("wgy", "添加数据库成功："+record.toString());
    }


    public void updateRecord(Record record) {
        db = helper.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("isPlayed", record.isPlayed() ? 0 : 1);
        db.update("record", cv, "id = ?", new String[]{record.getId()});
        db.close();

    }


    public void deleteRecord(Record record) {
        db = helper.getWritableDatabase();
        db.delete("Record", "id = ?", new String[]{record.getId()});
        db.close();

    }

    /**
     * query all persons, return list
     *
     * @return List<Person>
     */
    public List<Record> query() {
        db = helper.getWritableDatabase();
        ArrayList<Record> records = new ArrayList<Record>();
        Cursor c = queryTheCursor();
        while (c.moveToNext()) {
            Record record = new Record();
            record.setId(c.getString(c.getColumnIndex("id")));
            record.setPath(c.getString(c.getColumnIndex("path")));
            record.setSecond(c.getInt(c.getColumnIndex("second")));
            record.setPlayed(c.getInt(c.getColumnIndex("isPlayed"))==0?true:false);
            records.add(record);
        }
        c.close();
        return records;
    }

    /**
     * query all persons, return cursor
     *
     * @return Cursor
     */
    public Cursor queryTheCursor() {
        Cursor c = db.rawQuery("SELECT * FROM record", null);
        return c;
    }

    /**
     * close database
     */
    public void closeDB() {
        db.close();
    }
}
