package com.kinga.meritraknare_material.data;

/*
Created by @HalabWare
Copyrights reserved
 */

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static com.kinga.meritraknare_material.data.CourseContract.*;

public class CourseDbHelper extends SQLiteOpenHelper {

    protected static final String DATABASE_NAME = "courses.db";
    protected static final int DATABASE_VERSION = 1;

    public CourseDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //Create a String that contains the SQL statement to create the grades table
        String SQL_CREATE_GRADES_TABLE = "CREATE TABLE " + CourseEntry.TABLE_NAME + " ("
                + CourseEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + CourseEntry.COLUMN_COURSE + " TEXT NOT NULL, "
                + CourseEntry.COLUMN_GRADE + " TEXT NOT NULL, "
                + CourseEntry.COLUMN_POINTS + " INTEGER NOT NULL, "
                + CourseEntry.COLUMN_TYPE + " INTEGER NOT NULL);";

        // Execute the SQL statement
        db.execSQL(SQL_CREATE_GRADES_TABLE);

//        insertGymnasieGemensamma(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
    }

    private void insertGymnasieGemensamma(SQLiteDatabase db){

        ContentValues engelska5Values = new ContentValues();
        engelska5Values.put(CourseEntry.COLUMN_COURSE, "Engelska 5");
        engelska5Values.put(CourseEntry.COLUMN_GRADE, 20.0);
        engelska5Values.put(CourseEntry.COLUMN_POINTS, 100);
        engelska5Values.put(CourseEntry.COLUMN_TYPE, CourseEntry.TYPE_GYM_GEM);
        db.insertWithOnConflict(CourseEntry.TABLE_NAME, null, engelska5Values, SQLiteDatabase.CONFLICT_REPLACE);

        ContentValues engelska6Values = new ContentValues();
        engelska6Values.put(CourseEntry.COLUMN_COURSE, "Engelska 6");
        engelska6Values.put(CourseEntry.COLUMN_GRADE, 20.0);
        engelska6Values.put(CourseEntry.COLUMN_POINTS, 100);
        engelska6Values.put(CourseEntry.COLUMN_TYPE, CourseEntry.TYPE_GYM_GEM);
        db.insertWithOnConflict(CourseEntry.TABLE_NAME, null, engelska6Values, SQLiteDatabase.CONFLICT_REPLACE);

        ContentValues historiaValues = new ContentValues();
        historiaValues.put(CourseEntry.COLUMN_COURSE, "Historia 1b");
        historiaValues.put(CourseEntry.COLUMN_GRADE, 20.0);
        historiaValues.put(CourseEntry.COLUMN_POINTS, 100);
        historiaValues.put(CourseEntry.COLUMN_TYPE, CourseEntry.TYPE_GYM_GEM);
        db.insertWithOnConflict(CourseEntry.TABLE_NAME, null, historiaValues, SQLiteDatabase.CONFLICT_REPLACE);

        ContentValues idrottValues = new ContentValues();
        idrottValues.put(CourseEntry.COLUMN_COURSE, "Idrott 1");
        idrottValues.put(CourseEntry.COLUMN_GRADE, 20.0);
        idrottValues.put(CourseEntry.COLUMN_POINTS, 100);
        idrottValues.put(CourseEntry.COLUMN_TYPE, CourseEntry.TYPE_GYM_GEM);
        db.insertWithOnConflict(CourseEntry.TABLE_NAME, null, idrottValues, SQLiteDatabase.CONFLICT_REPLACE);

        ContentValues matte1Values = new ContentValues();
        matte1Values.put(CourseEntry.COLUMN_COURSE, "Matte 1");
        matte1Values.put(CourseEntry.COLUMN_GRADE, 20.0);
        matte1Values.put(CourseEntry.COLUMN_POINTS, 100);
        matte1Values.put(CourseEntry.COLUMN_TYPE, CourseEntry.TYPE_GYM_GEM);
        db.insertWithOnConflict(CourseEntry.TABLE_NAME, null, matte1Values, SQLiteDatabase.CONFLICT_REPLACE);

        ContentValues matte2Values = new ContentValues();
        matte2Values.put(CourseEntry.COLUMN_COURSE, "Matte 2");
        matte2Values.put(CourseEntry.COLUMN_GRADE, 20.0);
        matte2Values.put(CourseEntry.COLUMN_POINTS, 100);
        matte2Values.put(CourseEntry.COLUMN_TYPE, CourseEntry.TYPE_GYM_GEM);
        db.insertWithOnConflict(CourseEntry.TABLE_NAME, null, matte2Values, SQLiteDatabase.CONFLICT_REPLACE);

        ContentValues matte3Values = new ContentValues();
        matte3Values.put(CourseEntry.COLUMN_COURSE, "Matte 3");
        matte3Values.put(CourseEntry.COLUMN_GRADE, 20.0);
        matte3Values.put(CourseEntry.COLUMN_POINTS, 100);
        matte3Values.put(CourseEntry.COLUMN_TYPE, CourseEntry.TYPE_GYM_GEM);
        db.insertWithOnConflict(CourseEntry.TABLE_NAME, null, matte3Values, SQLiteDatabase.CONFLICT_REPLACE);

        ContentValues relValues = new ContentValues();
        relValues.put(CourseEntry.COLUMN_COURSE, "Religion 1b");
        relValues.put(CourseEntry.COLUMN_GRADE, 20.0);
        relValues.put(CourseEntry.COLUMN_POINTS, 50);
        relValues.put(CourseEntry.COLUMN_TYPE, CourseEntry.TYPE_GYM_GEM);
        db.insertWithOnConflict(CourseEntry.TABLE_NAME, null, relValues, SQLiteDatabase.CONFLICT_REPLACE);

        ContentValues samValues = new ContentValues();
        samValues.put(CourseEntry.COLUMN_COURSE, "Samhällskundkap 1b");
        samValues.put(CourseEntry.COLUMN_GRADE, 20.0);
        samValues.put(CourseEntry.COLUMN_POINTS, 100);
        samValues.put(CourseEntry.COLUMN_TYPE, CourseEntry.TYPE_GYM_GEM);
        db.insertWithOnConflict(CourseEntry.TABLE_NAME, null, samValues, SQLiteDatabase.CONFLICT_REPLACE);

        ContentValues sv1Values = new ContentValues();
        sv1Values.put(CourseEntry.COLUMN_COURSE, "SVA/SVE 1");
        sv1Values.put(CourseEntry.COLUMN_GRADE, 20.0);
        sv1Values.put(CourseEntry.COLUMN_POINTS, 100);
        sv1Values.put(CourseEntry.COLUMN_TYPE, CourseEntry.TYPE_GYM_GEM);
        db.insertWithOnConflict(CourseEntry.TABLE_NAME, null, sv1Values, SQLiteDatabase.CONFLICT_REPLACE);

        ContentValues sv2Values = new ContentValues();
        sv2Values.put(CourseEntry.COLUMN_COURSE, "SVA/SVE 2");
        sv2Values.put(CourseEntry.COLUMN_GRADE, 20.0);
        sv2Values.put(CourseEntry.COLUMN_POINTS, 100);
        sv2Values.put(CourseEntry.COLUMN_TYPE, CourseEntry.TYPE_GYM_GEM);
        db.insertWithOnConflict(CourseEntry.TABLE_NAME, null, sv2Values, SQLiteDatabase.CONFLICT_REPLACE);

        ContentValues sv3Values = new ContentValues();
        sv3Values.put(CourseEntry.COLUMN_COURSE, "SVA/SVE 3");
        sv3Values.put(CourseEntry.COLUMN_GRADE, 20.0);
        sv3Values.put(CourseEntry.COLUMN_POINTS, 100);
        sv3Values.put(CourseEntry.COLUMN_TYPE, CourseEntry.TYPE_GYM_GEM);
        db.insertWithOnConflict(CourseEntry.TABLE_NAME, null, sv3Values, SQLiteDatabase.CONFLICT_REPLACE);

    }
}
