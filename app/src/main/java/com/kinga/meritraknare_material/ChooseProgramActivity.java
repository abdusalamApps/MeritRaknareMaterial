package com.kinga.meritraknare_material;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class ChooseProgramActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_program);

        ListView listView = findViewById(R.id.program_listView);

        ArrayList<String> programs = new ArrayList<>();

        programs.add(getString(R.string.ekonomiprogrammet));
        programs.add(getString(R.string.estetiska_programmet));
        programs.add(getString(R.string.humanistiska_programmet));
        programs.add(getString(R.string.international_baccalaureate));
        programs.add(getString(R.string.naturvetenskapsprogrammet));
        programs.add(getString(R.string.samhallskunskapsprogrammet));
        programs.add(getString(R.string.teknikprogrammet));

    /*    programs.add("Barn- och fritidsprogrammet");
        programs.add("Bygg- och anläggningsprogrammet");
        programs.add("El- och energiprogrammet");
        programs.add("Fordons- och transportprogrammet");
        programs.add("Handels- och administrationsprogrammet");
        programs.add("Handels- och administrationsprogrammet");
        programs.add("Hotell- och turismprogrammet");
        programs.add("Industritekniska programmet");
        programs.add("Estetiska programmet");
        programs.add("Humanistiska programmet");
        programs.add("International Baccalaureate");
        programs.add("Naturvetenskapsprogrammet");
        programs.add("Samhällsvetenskapsprogrammet");
        programs.add("Teknikprogrammet");*/


        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, programs);
        listView.setAdapter(arrayAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                Intent intent = new Intent(ChooseProgramActivity.this, WelcomeActivity.class);
                intent.setData(Uri.parse("from chooseprogramactivity"));
                int programInt;
                switch (position){
                    case 0:
                        programInt = Constants.ProgramsInts.EKONOMI;
                        break;
                    case 1:
                        programInt = Constants.ProgramsInts.ESTETISK;
                        break;
                    case 2:
                        programInt = Constants.ProgramsInts.HUMANISTISK;
                        break;
                    case 3:
                        programInt = Constants.ProgramsInts.INTERNATIONAL;
                        break;
                    case 4:
                        programInt = Constants.ProgramsInts.NATUR;
                        break;
                    case 5:
                        programInt = Constants.ProgramsInts.SAMHALL;
                        break;
                    case 6:
                        programInt = Constants.ProgramsInts.TEKNIK;
                        break;
                    default:
                        programInt = Constants.ProgramsInts.EKONOMI;
                }
                intent.putExtra("program", programInt);
                startActivity(intent);
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            }
        });


    }
}
