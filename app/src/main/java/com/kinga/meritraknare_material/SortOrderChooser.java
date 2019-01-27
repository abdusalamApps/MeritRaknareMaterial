package com.kinga.meritraknare_material;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.kinga.meritraknare_material.data.CourseContract;

import java.util.ArrayList;

import static com.kinga.meritraknare_material.data.CourseContract.*;

public class SortOrderChooser extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sort_order_chooser);

        ListView listView = findViewById(R.id.sortOrder_listView);
        ArrayList<String> sorts = new ArrayList<>();
        sorts.add(getString(R.string.kurs));
        sorts.add(getString(R.string.betyg));
        sorts.add(getString(R.string.poang));
        sorts.add(getString(R.string.typ));

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, sorts);
        listView.setAdapter(arrayAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                Intent intent = new Intent(SortOrderChooser.this, MainActivity.class);
                String sortStatement;
                switch (position){
                    case 0:
                        sortStatement = CourseEntry.COLUMN_COURSE;
                        break;
                    case 1:
                        sortStatement = CourseEntry.COLUMN_GRADE;
                        break;
                    case 2:
                        sortStatement = CourseEntry.COLUMN_POINTS;
                        break;
                    case 3:
                        sortStatement = CourseEntry.COLUMN_TYPE;
                        break;
                    default:
                        sortStatement = CourseEntry.COLUMN_COURSE;
                        break;
                }
                SharedPreferences sharedPref = getSharedPreferences("sortOrder", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPref.edit();
                editor.putString("sort_by", sortStatement);
                editor.apply();
                startActivity(intent);
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            }
        });
    }
}
