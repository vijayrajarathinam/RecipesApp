package com.collegecronista.vijay.recipes;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import java.text.SimpleDateFormat;
import android.os.Build;

import java.util.Date;
import java.util.Locale;

/**
 * Created by vijay on 15/07/2017.
 */

public class RecipeDB extends SQLiteOpenHelper {

    private static final String table_name = "recipes";
    private static final String column_1 = "id";
    private static final String column_2 = "title";
    private static final String column_3 = "description";
    private static final String column_4 = "created_at";
    private static final String column_5 = "updated_at";
    private static final String select_query = "select * from ";

    public RecipeDB(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, "recipe.db", factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table "+table_name+"("+column_1+" integer primary key autoincrement,"+column_2+" text ,"+column_3+" text,"+column_4+" datetime,"+column_5+" datetime)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table "+table_name+" if exists");
    }

    /**
     * select all informations in sqlite db table
     */
    public Cursor get_information(){
        Cursor cursor = (Cursor)this.getReadableDatabase().rawQuery(select_query+table_name,null);
        return cursor;
    }

    /**
     * create/insert information in sqlite db table
     */
    public long set_information(String column2,String column3){
        ContentValues contentValues = new ContentValues();
        contentValues.put(column_2,column2);
        contentValues.put(column_3,column3);
        contentValues.put(column_4,getDateTime());
        //contentValues.put(column_5,"");
        return this.getWritableDatabase().insert(table_name,null,contentValues);
    }

    /**
     * edit/updates information in sqlite db table
     */
    public void put_information(int id,String column2,String column3){
        ContentValues contentValues = new ContentValues();
        contentValues.put(column_2,column2);
        contentValues.put(column_3,column3);
        this.getWritableDatabase().update(table_name,contentValues,"id="+String.valueOf(id),null);
    }

    /**
     * returns datetime for creaed_at and updated_at db
     */
    private String getDateTime(){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        return simpleDateFormat.format(new Date());
    }

    /**
     * search information from db table
     */
    public Cursor search_information(String search){
        Cursor cursor = (Cursor)this.getReadableDatabase().rawQuery(select_query+table_name+" where title like %"+search+"%;",null);
        return cursor;
    }

    /**
     * delete from sqlite db table
     */
    public void delete_information(int id){
        this.getWritableDatabase().delete(table_name,column_1+"=?",new String[]{String.valueOf(id)});
    }
}
