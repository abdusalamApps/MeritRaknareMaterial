package com.kinga.meritraknare_material;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;

import android.support.v7.widget.AppCompatSpinner;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.kinga.meritraknare_material.data.CourseContract.CourseEntry;
import com.kinga.meritraknare_material.data.CourseDbHelper;

public class EditorActivity extends AppCompatActivity {

    private CourseDbHelper mDbHelper;

    ImageView confirmIV;
    FrameLayout  closeIV;
    AutoCompleteTextView courseET;
    TextView addTv, mDeleteButtonTV;
    TextInputLayout courseInputLayout;
    TextInputEditText textInputEditText;

    AppCompatSpinner mTypeSpinner, mGradeSpinner, mPointsSpinner;
    int mCourseType;
    double mGrade, mPoints;

    private Uri mCurrentCourseUri;
    private static final int EXISTING_COURSE_LOADER = 0;
    private static final String TAG = "EditorActivity";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(isTablet()) {
            setTheme(R.style.EditorThemeTablet);
        }
        else {
            setTheme(R.style.MainActivityTheme);
        }

        setContentView(R.layout.activity_editor);

        mDbHelper =  new CourseDbHelper(this);

        confirmIV = findViewById(R.id.confirm_tb_iv);
        closeIV = findViewById(R.id.close_tb_iv);
        courseET = findViewById(R.id.course_et);
        mTypeSpinner = findViewById(R.id.course_type_spinner);
        mGradeSpinner = findViewById(R.id.grade_spinner);
        mPointsSpinner = findViewById(R.id.points_spinner);
        addTv = findViewById(R.id.add_tv);
        mDeleteButtonTV = findViewById(R.id.delete_button_tv);
        courseInputLayout = findViewById(R.id.course_textInputLayout);

        // Setting auto-complete suggestions to the text field
        final String[] courses = getResources().getStringArray(R.array.courses_autoComplete);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, courses);
        courseET.setAdapter(adapter);

        Intent intent = getIntent();
        mCurrentCourseUri = intent.getData();

        mDeleteButtonTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog alertDialog = new AlertDialog.Builder(EditorActivity.this).create();
                alertDialog.setTitle("Ta bort kursen?");
                alertDialog.setButton(DialogInterface.BUTTON_POSITIVE, "TA BORT", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String name = getIntent().getStringExtra("name");
                        SQLiteDatabase database = mDbHelper.getWritableDatabase();
                        database.delete(CourseEntry.TABLE_NAME,
                                CourseEntry.COLUMN_COURSE + " = " + "'" + name + "'",
                                null);
                        startActivity(new Intent(EditorActivity.this, MainActivity.class));
                    }
                });
                alertDialog.setButton(DialogInterface.BUTTON_NEGATIVE, "NEJ", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
                alertDialog.show();
            }
        });



        addTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveCourse();
                courseET.setText("");
                courseInputLayout.setErrorEnabled(false);
            }
        });
        confirmIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveCourse();
                finish();
            }
        });

        closeIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        setupSpinner(R.id.course_type_spinner, mTypeSpinner);
        setupSpinner(R.id.grade_spinner, mGradeSpinner);
        setupSpinner(R.id.points_spinner, mPointsSpinner);

        if (mCurrentCourseUri == null){
            mDeleteButtonTV.setVisibility(View.INVISIBLE);
        } else {
            addTv.setVisibility(View.GONE);
            courseET.setText(getIntent().getStringExtra("name"));
            Double grade = getIntent().getDoubleExtra("grade", 0);
            if (grade == 20.0){
                mGradeSpinner.setSelection(0);
            } else if (grade == 17.5){
                mGradeSpinner.setSelection(1);
            } else if (grade == 15.0){
                mGradeSpinner.setSelection(2);
            } else if (grade == 12.5){
                mGradeSpinner.setSelection(3);
            } else if (grade == 10.0){
                mGradeSpinner.setSelection(4);
            } else if (grade == 0.0){
                mGradeSpinner.setSelection(5);
            }
            int ggPoints = getIntent().getIntExtra("ggPoints", 0);
            if (ggPoints == 100){
                mPointsSpinner.setSelection(0);
            } else if (ggPoints == 50){
                mPointsSpinner.setSelection(1);
            } else if (ggPoints == 150){
                mPointsSpinner.setSelection(2);
            }
            int type = getIntent().getIntExtra("type", 1);
            switch (type) {
                case CourseEntry.TYPE_GYM_GEM:
                    mTypeSpinner.setSelection(0);
                    break;
                case CourseEntry.TYPE_PROG_GEM:
                    mTypeSpinner.setSelection(1);
                    break;
                case CourseEntry.TYPE_INDVAL:
                    mTypeSpinner.setSelection(2);
                    break;
                case CourseEntry.TYPE_FORDJUPNING:
                    mTypeSpinner.setSelection(3);
                    break;
                case CourseEntry.TYPE_UTOKAD:
                    mTypeSpinner.setSelection(4);
                    break;
                case CourseEntry.TYPE_INRIKTNING_GEM:
                    mTypeSpinner.setSelection(5);
                    break;
            }
        }

    }

    private void setupSpinner(final int spinnerId, AppCompatSpinner spinner){
        int arrayRecourse;
        switch (spinnerId){
            case R.id.course_type_spinner:
                arrayRecourse = R.array.course_type_spinner_array;
                break;
            case R.id.grade_spinner:
                arrayRecourse = R.array.grades_spinner_array;
                break;
            case R.id.points_spinner:
                arrayRecourse = R.array.points_spinner_array;
                break;
            default:
                throw new IllegalArgumentException("spinner require array recourse");
        }
        ArrayAdapter spinnerAdapter = ArrayAdapter.createFromResource(
                this,
                arrayRecourse,
                android.R.layout.simple_spinner_item
        );
        spinnerAdapter.setDropDownViewResource(R.layout.dropdown_spinner_item_layout);

        spinner.setAdapter(spinnerAdapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selection = (String) parent.getItemAtPosition(position);
                switch (spinnerId){
                    case R.id.course_type_spinner:
                        if (selection.equals(getString(R.string.type_gymnasiegemensam))) {
                            mCourseType = CourseEntry.TYPE_GYM_GEM;
                        } else if (selection.equals(getString(R.string.type_programgemnsam))) {
                            mCourseType = CourseEntry.TYPE_PROG_GEM;
                        } else if (selection.equals(getString(R.string.type_indval))) {
                            mCourseType = CourseEntry.TYPE_INDVAL;
                        } else if (selection.equals(getString(R.string.type_programfordumpning))) {
                            mCourseType = CourseEntry.TYPE_FORDJUPNING;
                        } else if (selection.equals(getString(R.string.type_utokad))) {
                            mCourseType = CourseEntry.TYPE_UTOKAD;
                        } else if (selection.equals(getString(R.string.type_inriktning_gem))) {
                            mCourseType = CourseEntry.TYPE_INRIKTNING_GEM;
                        } else {
                            mCourseType = CourseEntry.TYPE_GYM_GEM;
                        }
                    case R.id.grade_spinner:
                        if (selection.equals(getString(R.string.grade_a))){
                            mGrade = 20.0;
                        } else if (selection.equals(getString(R.string.grade_b))){
                            mGrade = 17.5;
                        } else if (selection.equals(getString(R.string.grade_c))){
                            mGrade = 15.0;
                        } else if (selection.equals(getString(R.string.grade_d))){
                            mGrade = 12.5;
                        } else if (selection.equals(getString(R.string.grade_e))){
                            mGrade = 10.0;
                        } else if (selection.equals(getString(R.string.grade_f))){
                            mGrade = 0.0;
                        }
                        break;
                    case R.id.points_spinner:
                        if (selection.equals(getString(R.string.points_100))){
                            mPoints = 100.0;
                        } else if (selection.equals(getString(R.string.points_50))){
                            mPoints = 50.0;
                        } else if (selection.equals(getString(R.string.points_150))){
                            mPoints = 150.0;
                        }
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

    }

    private void saveCourse(){
        String courseString = courseET.getText().toString().trim();
        if (TextUtils.isEmpty(courseString)){
            Log.e(TAG, "course empty");
        } else {
            SQLiteDatabase database = mDbHelper.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(CourseEntry.COLUMN_COURSE, courseET.getText().toString().trim());
            values.put(CourseEntry.COLUMN_TYPE, mCourseType);
            values.put(CourseEntry.COLUMN_GRADE, mGrade);
            values.put(CourseEntry.COLUMN_POINTS, mPoints);

            if (mCurrentCourseUri == null){
                long insert = database.insert(CourseEntry.TABLE_NAME, null, values);
                if (insert <= 0){
                    Toast.makeText(this, "Insertion failed", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, String.valueOf(insert), Toast.LENGTH_SHORT).show();
                }
                Log.e("EditorKursnamn", courseET.getText().toString());

            } else {
                String name = getIntent().getStringExtra("name");
                String update = "UPDATE " + CourseEntry.TABLE_NAME + " SET " +
                        CourseEntry.COLUMN_COURSE + " = " + "'" + courseET.getText().toString().trim() + "'" + ", " +
                        CourseEntry.COLUMN_GRADE + " = " + mGrade + ", " +
                        CourseEntry.COLUMN_POINTS + " = " + mPoints
                        + ", " + CourseEntry.COLUMN_TYPE + " = " + "'" + mCourseType +
                        "'" + " WHERE " + CourseEntry.COLUMN_COURSE + " = " + "'" + name + "'" + ";";
                database.execSQL(update);
            }
        }
    }

    public boolean isTablet() {
        try {
            // Compute screen size
            DisplayMetrics dm = getApplicationContext().getResources().getDisplayMetrics();
            float screenWidth  = dm.widthPixels / dm.xdpi;
            float screenHeight = dm.heightPixels / dm.ydpi;
            double size = Math.sqrt(Math.pow(screenWidth, 2) +
                    Math.pow(screenHeight, 2));
            // Tablet devices should have a screen size greater than 6 inches
            return size >= 6;
        } catch(Throwable t) {
            Log.e("EditorA ", "Failed to compute screen size", t);
            return false;
        }

    }
}
