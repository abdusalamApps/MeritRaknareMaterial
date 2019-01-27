package com.kinga.meritraknare_material;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatSpinner;
import android.support.v7.widget.ShareActionProvider;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import static com.kinga.meritraknare_material.data.CourseContract.*;

public class ResultActivity extends AppCompatActivity {

    private AppCompatSpinner spinner, languageSpinner;
    private double meriter;
    private TextView meritvardeTV, jamtalTV, resetTV, comparisonTv, pointsSumTV, gradesValueTV;
    private String jamtalString, newJamtalString;
    private double jamtalDouble, pointsSumDouble, gradesValueDouble;
    private TextView courseCountTv, resetTv;
    private ImageView doneImageView, shareImageView;

    private void findViews() {
        spinner = findViewById(R.id.meriter_spinner);
        meritvardeTV = findViewById(R.id.meritvarde_tv);
        courseCountTv = findViewById(R.id.courseCount_tv);
        comparisonTv = findViewById(R.id.comparison_sum_tv);
        pointsSumTV = findViewById(R.id.pointsSum_tv);
        gradesValueTV = findViewById(R.id.grades_value_tv);
        doneImageView = findViewById(R.id.done_imageView);
        shareImageView = findViewById(R.id.share_imageView);
//        languageSpinner = findViewById(R.id.language_spinner);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        Intent intent = getIntent();
        if (intent != null) {
            jamtalString = intent.getStringExtra("jamtalString");
            if (jamtalString.contains(",")) {
                String[] parts = jamtalString.split(",");
                String first = parts[0];
                String last = parts[1];
                newJamtalString = first + "." + last;
                jamtalDouble = Double.parseDouble(newJamtalString);
            } else jamtalDouble = Double.parseDouble(jamtalString);
        }

        findViews();
//        resetTV = findViewById(R.id.reset_tv);
        comparisonTv.setText(jamtalString);

        setupSpinner();
//        setupLangSpinner();

        doneImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        calculate();

        shareImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent shareIntent = new Intent(android.content.Intent.ACTION_SEND);
                shareIntent.setType("text/plain");
                shareIntent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.my_highSchool_Rating));
                shareIntent.putExtra(Intent.EXTRA_TEXT, meritvardeTV.getText().toString());
                startActivity(Intent.createChooser(shareIntent, getString(R.string.share) + "..."));
            }
        });

//        resetTV.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                startActivity(new Intent(ResultActivity.this, ResetActivity.class));
//                /*Intent intent = new Intent();
//                intent.setAction(ACTION_APPLICATION_DETAILS_SETTINGS);
//                Uri uri = Uri.fromParts("package", getPackageName(), null);
//                intent.setData(uri);
//                startActivity(intent);*/
//
//            }
//        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }

    public void setupSpinner() {
        ArrayAdapter spinnerAdapter = ArrayAdapter.createFromResource(
                this,
                R.array.meriter_spinner_array,
                android.R.layout.simple_spinner_item
        );
        spinnerAdapter.setDropDownViewResource(R.layout.dropdown_spinner_item_layout);
        spinner.setAdapter(spinnerAdapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selection = (String) parent.getItemAtPosition(position);
                if (selection.equals(getString(R.string.merit_0))) {
                    meritvardeTV.setText(String.valueOf(jamtalDouble));
                } else if (selection.equals(getString(R.string.merit_05))) {
                    meritvardeTV.setText(String.valueOf(jamtalDouble + 0.5));
                } else if (selection.equals(getString(R.string.merit_1))) {
                    meritvardeTV.setText(String.valueOf(jamtalDouble + 1.0));
                } else if (selection.equals(getString(R.string.merit_15))) {
                    meritvardeTV.setText(String.valueOf(jamtalDouble + 1.5));
                } else if (selection.equals(getString(R.string.merit_2))) {
                    meritvardeTV.setText(String.valueOf(jamtalDouble + 2.0));
                } else if (selection.equals(getString(R.string.merit_25))) {
                    meritvardeTV.setText(String.valueOf(jamtalDouble + 2.5));
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

    }

    private void setupLangSpinner() {
        ArrayAdapter adapter = ArrayAdapter.createFromResource(
                this,
                R.array.language_spinner_array,
                android.R.layout.simple_spinner_item
        );
        adapter.setDropDownViewResource(R.layout.dropdown_spinner_item_layout);
        languageSpinner.setAdapter(adapter);
        languageSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    public void calculate() {
//        Calculating courses count
        Cursor cursor = getContentResolver().query(
                CourseEntry.CONTENT_URI,
                null,
                null,
                null,
                null);
        assert cursor != null;
        cursor.close();
        courseCountTv.setText(String.valueOf(cursor.getCount()));

//        Calculating points' sum
        String[] pointsColumnSum = new String[]{"sum(" + CourseEntry.COLUMN_POINTS + ") " };
        Cursor query = getContentResolver().query(
                CourseEntry.CONTENT_URI,
                pointsColumnSum,
                null,
                null,
                null
        );

        if (query.moveToFirst()) {
            pointsSumDouble = query.getDouble(0);
        }

        pointsSumTV.setText(String.valueOf(pointsSumDouble));

//        Calculating grades' value
        String[] gradesValues = new String[] { "sum(" + CourseEntry.COLUMN_POINTS + "*" + CourseEntry.COLUMN_GRADE + ")"};
        Cursor gradesValuesCursor = getContentResolver().query(
                CourseEntry.CONTENT_URI,
                gradesValues,
                null,
                null,
                null);

        if (gradesValuesCursor.moveToFirst()) {
            gradesValueTV.setText(String.valueOf(gradesValuesCursor.getDouble(0)));
        }

    }

}
