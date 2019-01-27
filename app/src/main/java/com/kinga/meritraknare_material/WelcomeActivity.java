package com.kinga.meritraknare_material;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.kinga.meritraknare_material.Constants.GymnasiegemensammaKurser;
import com.kinga.meritraknare_material.data.CourseDbHelper;

import static com.kinga.meritraknare_material.Constants.*;
import static com.kinga.meritraknare_material.data.CourseContract.*;

public class WelcomeActivity extends AppCompatActivity {

    private static final String TAG = WelcomeActivity.class.getSimpleName();
    private CourseDbHelper mDbHelper;

    LinearLayout programLL, loginBtn;
    TextView programTV;

    private int programInt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        mDbHelper = new CourseDbHelper(this);

        programLL = findViewById(R.id.program_linearLayout);
        programTV = findViewById(R.id.program_tv);
        loginBtn = findViewById(R.id.login_btn);

        programTV.setText(getString(R.string.ekonomiprogrammet));
        Intent intent = getIntent();
        if (intent != null) {
            if (intent.getData() != null && intent.getData().toString().equals("from chooseprogramactivity")){
                programInt = intent.getIntExtra("program", 0);
                String programString;
                switch (programInt) {
                    case ProgramsInts.EKONOMI:
                        programString = getString(R.string.ekonomiprogrammet);
                        break;
                    case ProgramsInts.ESTETISK:
                        programString = getString(R.string.estetiska_programmet);
                        break;
                    case ProgramsInts.HUMANISTISK:
                        programString = getString(R.string.humanistiska_programmet);
                        break;
                    case ProgramsInts.INTERNATIONAL:
                        programString = getString(R.string.international_baccalaureate);
                        break;
                    case ProgramsInts.NATUR:
                        programString = getString(R.string.naturvetenskapsprogrammet);
                        break;
                    case ProgramsInts.SAMHALL:
                        programString = getString(R.string.samhallskunskapsprogrammet);
                        break;
                    case ProgramsInts.TEKNIK:
                        programString = getString(R.string.teknikprogrammet);
                        break;
                    default:
                        programString = getString(R.string.ekonomiprogrammet);
                }

                programTV.setText(programString);
            }
        }

        programLL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(WelcomeActivity.this, ChooseProgramActivity.class));
            }
        });

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                insertCourses();
                SharedPreferences sharedPref = getSharedPreferences("meritMain", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPref.edit();
                editor.putString("Exists", "2001");
                editor.apply();
                startActivity(new Intent(WelcomeActivity.this, MainActivity.class));
            }
        });
    }

    private void insertCourses() {
        SQLiteDatabase database = mDbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        for (int i = 0; i < GymnasiegemensammaKurser.ggCourses.length; i++) {
            values.put(CourseEntry.COLUMN_COURSE, GymnasiegemensammaKurser.ggCourses[i]);
            values.put(CourseEntry.COLUMN_TYPE, GymnasiegemensammaKurser.ggCoursesTypes[i]);
            values.put(CourseEntry.COLUMN_GRADE, GymnasiegemensammaKurser.ggGrades[i]);
            values.put(CourseEntry.COLUMN_POINTS, GymnasiegemensammaKurser.ggPoints[i]);

            long newRow = database.insertWithOnConflict(CourseEntry.TABLE_NAME, null, values, SQLiteDatabase.CONFLICT_REPLACE);
            if (newRow == 0) {
                Log.e(TAG, "DID NOT SAVE");
            } else Log.d(TAG, "SAVED");
        }

        String[] courses = {"Mdernaspråk"};
        double[] grades;
        double[] points;
        int[] types;
        if (programInt == ProgramsInts.EKONOMI) {

            for (int i = 0; i < EkonomiKurser.ekonomiCourses.length; i++) {

                values.put(CourseEntry.COLUMN_COURSE, EkonomiKurser.ekonomiCourses[i]);
                values.put(CourseEntry.COLUMN_GRADE, EkonomiKurser.ekonomiGrades[i]);
                values.put(CourseEntry.COLUMN_POINTS, EkonomiKurser.ekonomiCoursesPoints[i]);
                values.put(CourseEntry.COLUMN_TYPE, EkonomiKurser.ekonomiCoursesTypes[i]);

                long newRow = database.insertWithOnConflict(CourseEntry.TABLE_NAME, null, values, SQLiteDatabase.CONFLICT_REPLACE);
                if (newRow == 0) {
                    Log.e(TAG, "DID NOT SAVE");
                } else Log.e(TAG, "SAVED");
            }

        } else if (programInt == ProgramsInts.ESTETISK) {

            for (int i = 0; i < EstetiskaKurser.estetiskaCourses.length; i++) {

                values.put(CourseEntry.COLUMN_COURSE, EstetiskaKurser.estetiskaCourses[i]);
                values.put(CourseEntry.COLUMN_GRADE, EstetiskaKurser.estetiskaGrades[i]);
                values.put(CourseEntry.COLUMN_POINTS, EstetiskaKurser.estetiskaCoursesPoints[i]);
                values.put(CourseEntry.COLUMN_TYPE, EstetiskaKurser.estetiskaCoursesTypes[i]);

                long newRow = database.insertWithOnConflict(CourseEntry.TABLE_NAME, null, values, SQLiteDatabase.CONFLICT_REPLACE);
                if (newRow == 0) {
                    Log.e(TAG, "DID NOT SAVE");
                } else Log.e(TAG, "SAVED");
            }
        } else if (programInt == ProgramsInts.HUMANISTISK) {

            for (int i = 0; i < HumanistiskaKurser.humanistiskaCourses.length; i++) {

                values.put(CourseEntry.COLUMN_COURSE, HumanistiskaKurser.humanistiskaCourses[i]);
                values.put(CourseEntry.COLUMN_GRADE, HumanistiskaKurser.humanistiskaCoursesGrades[i]);
                values.put(CourseEntry.COLUMN_POINTS, HumanistiskaKurser.humanistiskaCoursesCoursesPoints[i]);
                values.put(CourseEntry.COLUMN_TYPE, HumanistiskaKurser.humanistiskaCoursesCoursesTypes[i]);

                long newRow = database.insertWithOnConflict(CourseEntry.TABLE_NAME, null, values, SQLiteDatabase.CONFLICT_REPLACE);
                if (newRow == 0) {
                    Log.e(TAG, "DID NOT SAVE");
                } else Log.e(TAG, "SAVED");
            }

        } else if (programInt == ProgramsInts.NATUR) {
            for (int i = 0; i < NaturvetenskapsKurser.naturCourses.length; i++) {

                values.put(CourseEntry.COLUMN_COURSE, NaturvetenskapsKurser.naturCourses[i]);
                values.put(CourseEntry.COLUMN_GRADE, NaturvetenskapsKurser.naturGrades[i]);
                values.put(CourseEntry.COLUMN_POINTS, NaturvetenskapsKurser.naturCoursesPoints[i]);
                values.put(CourseEntry.COLUMN_TYPE, NaturvetenskapsKurser.naturCoursesTypes[i]);

                long newRow = database.insertWithOnConflict(CourseEntry.TABLE_NAME, null, values, SQLiteDatabase.CONFLICT_REPLACE);
                if (newRow == 0) {
                    Log.e(TAG, "DID NOT SAVE");
                } else Log.e(TAG, "SAVED");
            }

        } else if (programInt == ProgramsInts.SAMHALL) {

            for (int i = 0; i < SamhallsvetenskapsKurser.samhallsCourses.length; i++) {

                values.put(CourseEntry.COLUMN_COURSE, SamhallsvetenskapsKurser.samhallsCourses[i]);
                values.put(CourseEntry.COLUMN_GRADE, SamhallsvetenskapsKurser.samhallsGrades[i]);
                values.put(CourseEntry.COLUMN_POINTS, SamhallsvetenskapsKurser.samhallsCoursesPoints[i]);
                values.put(CourseEntry.COLUMN_TYPE, SamhallsvetenskapsKurser.samhallsCoursesTypes[i]);

                long newRow = database.insertWithOnConflict(CourseEntry.TABLE_NAME, null, values, SQLiteDatabase.CONFLICT_REPLACE);
                if (newRow == 0) {
                    Log.e(TAG, "DID NOT SAVE");
                } else Log.e(TAG, "SAVED");
            }

            courses = SamhallsvetenskapsKurser.samhallsCourses;
            grades = SamhallsvetenskapsKurser.samhallsGrades;
            points = SamhallsvetenskapsKurser.samhallsCoursesPoints;
            types = SamhallsvetenskapsKurser.samhallsCoursesTypes;
        } else if (programInt == ProgramsInts.TEKNIK) {
            for (int i = 0; i < TekniksKurser.tekniksCourses.length; i++) {

                values.put(CourseEntry.COLUMN_COURSE, TekniksKurser.tekniksCourses[i]);
                values.put(CourseEntry.COLUMN_GRADE, TekniksKurser.tekniksGrades[i]);
                values.put(CourseEntry.COLUMN_POINTS, TekniksKurser.tekniksCoursesPoints[i]);
                values.put(CourseEntry.COLUMN_TYPE, TekniksKurser.tekniksCoursesTypes[i]);

                long newRow = database.insertWithOnConflict(CourseEntry.TABLE_NAME, null, values, SQLiteDatabase.CONFLICT_REPLACE);
                if (newRow == 0) {
                    Log.e(TAG, "DID NOT SAVE");
                } else Log.e(TAG, "SAVED");
            }

           /* courses = TekniksKurser.tekniksCourses;
            grades = TekniksKurser.tekniksGrades;
            points = TekniksKurser.tekniksCoursesPoints;
            types = TekniksKurser.tekniksCoursesTypes;*/
        } else {
            String[] cc = {""};
            double[] gg = {};
            courses = cc;
            grades = gg;
            points = gg;
            types = new int[]{0};
        }

//        for (int i = 0; i < courses.length - 1; i++){
//            values.put(CourseEntry.COLUMN_COURSE, courses[i]);
//            values.put(CourseEntry.COLUMN_GRADE, grades[i]);
//            values.put(CourseEntry.COLUMN_POINTS, points[i]);
//            values.put(CourseEntry.COLUMN_TYPE, types[i]);
//
//            long newRow = database.insertWithOnConflict(CourseEntry.TABLE_NAME, null, values, SQLiteDatabase.CONFLICT_REPLACE);
//            if (newRow == 0){
//                Log.e(TAG, "DID NOT SAVE");
//            } else Log.e(TAG, "SAVED");
//        }

    }

}
