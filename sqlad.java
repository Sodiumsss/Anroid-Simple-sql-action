package com.example.mysql;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class sqlad  extends SQLiteOpenHelper {

    private final static  String DATABASE_NAME="mysql1";
    private final static  int DATABASE_VERSION=1;



    public sqlad(Context context) {
        super(context,DATABASE_NAME,null,DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("create table myinfo("
                + "_id integer primary key autoincrement,"
                + "name varchar(64),"
                + "age integer,"
                + "height varchar(64))"
        );

    }


    public Cursor find(String num)
    {
        SQLiteDatabase db=this.getReadableDatabase();
        Cursor cursor=db.query("myinfo",null,null,null,null,null,null);
        return cursor;
        /*
        String[] where={num};
        Cursor c=getWritableDatabase().query("myinfo",null,"age>=?",where,null,null,null);
        return c;

         */
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("drop table if exists myinfo");
        onCreate(sqLiteDatabase);
    }



    public Cursor select()
    {
        SQLiteDatabase db=this.getReadableDatabase();
        Cursor cursor=db.query("myinfo",null,null,null,null,null,null);
        return cursor;
    }



    public long insert(String name,String age,String height)
    {
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues cv=new ContentValues();
        cv.put("name",name);
        cv.put("age",age);
        cv.put("height",height);
        long row=db.insert("myinfo",null,cv);
        return row;
    }

    public void update (int id,String name,String age,String height)
    {
        SQLiteDatabase db =this.getWritableDatabase();
        String where="_id=?";
        String[] strwhere={Integer.toString(id)};
        ContentValues cv=new ContentValues();
        cv.put("name",name);
        cv.put("age",age);
        cv.put("height",height);
        db.update("myinfo",cv,where,strwhere);
    }

    public void delete(int id)
    {
        SQLiteDatabase db =this.getWritableDatabase();
        String where="_id=?";
        String[] strwhere={Integer.toString(id)};
        db.delete("myinfo",where,strwhere);
    }


}
