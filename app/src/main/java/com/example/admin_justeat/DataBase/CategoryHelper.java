package com.example.admin_justeat.DataBase;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class CategoryHelper extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "categories.db";

    //Category db query creation;
    private static final String SQL_CREATE_ENTRY = "CREATE TABLE " + CategoryContracts.TABLE_NAME +
            " (" + CategoryContracts.ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                   CategoryContracts.CATEGORY_NAME + " VARCHAR(10), " +
                   CategoryContracts.CATEGORY_DESCRIPTION + " TEXT" + ");";

    public CategoryHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    //
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(SQL_CREATE_ENTRY);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) { }
}
