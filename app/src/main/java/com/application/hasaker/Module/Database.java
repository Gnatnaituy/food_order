package com.application.hasaker.Module;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import java.util.ArrayList;

public class Database extends SQLiteOpenHelper {

    private static final String CREATE_FOOD = "create table Food ("
            + "id integer primary key autoincrement, "
            + "name text)";

    private static final String TO_DO_LIST = "create table Todo ("
            + "id integer primary key autoincrement, "
            + "name text, "
            + "peppery integer, "
            + "amount integer)";

    private Context mContext;

    public Database(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        mContext = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_FOOD);
        db.execSQL(TO_DO_LIST);
        Toast.makeText(mContext, "添加成功！", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists Food");
        db.execSQL("drop table if exists Todo");
        onCreate(db);
    }

    public ArrayList<String> getAllItems(String tableName, String columnName) {
        ArrayList<String> allItems = new ArrayList<>();
        SQLiteDatabase db = getWritableDatabase();
        Cursor c = db.rawQuery("select * from " + tableName + "." + columnName, null);
        c.moveToFirst();

        while (!c.isAfterLast()) {
            allItems.add(c.getString(c.getColumnIndex(columnName)));
            c.moveToNext();
        }
        c.close();

        return allItems;
    }
}