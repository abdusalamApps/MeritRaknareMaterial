package com.kinga.meritraknare_material;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.kinga.meritraknare_material.data.CourseDbHelper;

import java.text.DecimalFormat;

import static com.kinga.meritraknare_material.data.CourseContract.*;

public class MainActivity extends AppCompatActivity implements RecyclerItemTouchHelper.RecyclerItemTouchHelperListener {

    private static final String TAG = MainActivity.class.getSimpleName();

    private SQLiteDatabase mDatabase;
    private RecyclerView.LayoutManager mLayoutManager;
    NewRecyclerAdapter adapter;
    private CoordinatorLayout coordinatorLayout;
    LinearLayout talToolbar;
    TextView meritvardeMain;
    ImageView sortCoursesIv, sortOrderIv;
    String sortByStatement, sortOrderStatement;
    SwipeRefreshLayout swipeRefreshLayout;

    SharedPreferences sortOrderPref;

    FloatingActionButton fab;
    SearchView searchView;
    private CourseDbHelper mDbHelper;

    private void findViews() {
        coordinatorLayout = findViewById(R.id.coordinator_layout);
        meritvardeMain = findViewById(R.id.meritvarde_tv_main);
        talToolbar = findViewById(R.id.toolbar_tal);
        sortCoursesIv = findViewById(R.id.sortCourses_iv);
        sortOrderIv = findViewById(R.id.sortOrder_iv);
        searchView = findViewById(R.id.search_view);
//        swipeRefreshLayout = findViewById(R.id.swipeRefreshLayout);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SharedPreferences sharedPref = getSharedPreferences("meritMain", Context.MODE_PRIVATE);

        if (!sharedPref.getString("Exists", "").equals("2001")){
            startActivity(new Intent(MainActivity.this, WelcomeActivity.class));
        }

        sortByStatement = CourseEntry.COLUMN_COURSE;
        sortOrderStatement = "ASC";

        sortOrderPref = getSharedPreferences("sortOrder", Context.MODE_PRIVATE);
        if (!TextUtils.isEmpty(sortOrderPref.getString("sort_by", ""))){
            sortByStatement = sortOrderPref.getString("sort_by", "");
            Log.e(TAG + " sort: ", sortByStatement);
        }

        mDbHelper =  new CourseDbHelper(this);

        findViews();

        talToolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, ResultActivity.class);
                intent.putExtra("jamtalString", meritvardeMain.getText().toString());
                startActivity(intent);
            }
        });

        sortCoursesIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, SortOrderChooser.class));
            }
        });

        searchView.setOnSearchClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        CourseDbHelper dbHelper = new CourseDbHelper(this);
        mDatabase = dbHelper.getWritableDatabase();

        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        mLayoutManager = new GridLayoutManager(this, 1);
        recyclerView.setLayoutManager(mLayoutManager);
        adapter = new NewRecyclerAdapter(this, getCoursesCursor());
        recyclerView.setAdapter(adapter);
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (dy < 0) {
                    fab.show();

                } else if (dy > 0) {
                    fab.hide();
                }
            }

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }
        });
        ItemTouchHelper.SimpleCallback itemTouchHelperCallback = new RecyclerItemTouchHelper(0, ItemTouchHelper.LEFT, this);
        new ItemTouchHelper(itemTouchHelperCallback).attachToRecyclerView(recyclerView);

        adapter.swapCursor(getCoursesCursor());

        sortOrderIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences.Editor editor = sortOrderPref.edit();
                if (sortOrderStatement.equals("ASC")){
                    sortOrderStatement = "DESC";
                    editor.putString("sort_order", "DESC");
                    editor.apply();
                    adapter.swapCursor(null);
                    adapter.swapCursor(getCoursesCursor());
                } else if (sortOrderStatement.equals("DESC")){
                    sortOrderStatement = "ASC";
                    editor.putString("sort_order", "ASC");
                    editor.apply();
                    adapter.swapCursor(null);
                    adapter.swapCursor(getCoursesCursor());

                }
            }
        });

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.swapCursor(null);
                adapter.swapCursor(searchCursor(newText));
                return false;
            }
        });

        fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, EditorActivity.class));
            }
        });
        calculateGradesSum();

//        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
//            @Override
//            public void onRefresh() {
//                adapter.swapCursor(null);
//                adapter.swapCursor(getCoursesCursor());
//                calculateGradesSum();
//                swipeRefreshLayout.setRefreshing(false);
//            }
//        });

    }

    private Cursor getCoursesCursor() {
        return mDatabase.query(
                CourseEntry.TABLE_NAME,
                null,
                null,
                null,
                null,
                null,
                sortByStatement + " " + sortOrderPref.getString("sort_order", "")
        );
    }

    private Cursor searchCursor(String searchKeyword){
        return mDatabase.query(
                CourseEntry.TABLE_NAME,
                null,
                CourseEntry.COLUMN_COURSE + " LIKE " + "'" + "%" + searchKeyword + "%" + "'",
                null,
                null,
                null,
                sortByStatement + " " + sortOrderPref.getString("sort_order", "")
        );
    }

    private void deleteCourse(long id){
        mDatabase.delete(CourseEntry.TABLE_NAME,
                CourseEntry._ID + "=" + id,
                null);
        adapter.swapCursor(getCoursesCursor());
    }

    public void calculateGradesSum(){
        String[] pointsColumnSum = new String[] { "sum(" + CourseEntry.COLUMN_POINTS + ") "};
        String[] gradesValues = new String[] { "sum(" + CourseEntry.COLUMN_POINTS + "*" + CourseEntry.COLUMN_GRADE + ")"};

        Cursor pointsSumCursor = getContentResolver().query(
                CourseEntry.CONTENT_URI,
                pointsColumnSum,
                null,
                null,
                null);

        Cursor gradesValuesCursor = getContentResolver().query(
                CourseEntry.CONTENT_URI,
                gradesValues,
                null,
                null,
                null);

        if (pointsSumCursor.moveToFirst() && gradesValuesCursor.moveToFirst()){
            double pointSum = pointsSumCursor.getDouble(0);
            double gradesValue = gradesValuesCursor.getDouble(0);
            double jamtal = gradesValue/pointSum;
            String jamtalString = String.valueOf(String.valueOf(jamtal));
            if (jamtalString.contains(",")){
                jamtalString = jamtalString.replaceAll(",", ".");
                Log.e("MainActivity jamtal", jamtalString);
            }

            double jamtalDouble = Double.parseDouble(jamtalString);
            DecimalFormat df = new DecimalFormat("#.##");
            meritvardeMain.setText(df.format(jamtalDouble));

        }

        pointsSumCursor.close();
        assert gradesValuesCursor != null;
        gradesValuesCursor.close();
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction, int position) {
        if (viewHolder instanceof NewRecyclerAdapter.NewViewHolder) {
            // backup of removed item
            final String course = ((NewRecyclerAdapter.NewViewHolder) viewHolder).nameView.getText().toString();
            final double points = Double.parseDouble(((NewRecyclerAdapter.NewViewHolder) viewHolder).pointsView.getText().toString());
            final double grade;
            String gradeString = ((NewRecyclerAdapter.NewViewHolder) viewHolder).gradeView.getText().toString();
            switch (gradeString) {
                case "A":
                    grade = 20.0;
                    break;
                case "B":
                    grade = 17.5;
                    break;
                case "C":
                    grade = 15.0;
                    break;
                case "D":
                    grade = 12.5;
                    break;
                case "E":
                    grade = 10.0;
                    break;
                default:
                    grade = 0.0;
                    break;
            }
            final String courseType = ((NewRecyclerAdapter.NewViewHolder) viewHolder).typeView.getText().toString();
            deleteCourse((long) viewHolder.itemView.getTag());
            calculateGradesSum();

            // showing snack bar with Undo option
            Snackbar snackbar = Snackbar
                    .make(coordinatorLayout, course + " borttagen", Snackbar.LENGTH_LONG);
            snackbar.setAction("ÅNGRA", new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    SQLiteDatabase database = mDbHelper.getWritableDatabase();
                    ContentValues values = new ContentValues();
                    values.put(CourseEntry.COLUMN_COURSE, course);
                    values.put(CourseEntry.COLUMN_TYPE, courseType);
                    values.put(CourseEntry.COLUMN_GRADE, grade);
                    values.put(CourseEntry.COLUMN_POINTS, points);

                    long newRow = database.insertWithOnConflict(CourseEntry.TABLE_NAME, null, values, SQLiteDatabase.CONFLICT_REPLACE);
                    if (newRow == 0){
                        Log.e(TAG, "DID NOT SAVE");
                    } else Log.e(TAG, "SAVED");
                    adapter.swapCursor(null);
                    adapter.swapCursor(getCoursesCursor());
                    calculateGradesSum();

                }
            });
            snackbar.setActionTextColor(Color.YELLOW);
            snackbar.show();

        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        adapter.swapCursor(getCoursesCursor());
        calculateGradesSum();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        adapter.swapCursor(null);
    }

    @Override
    public void onBackPressed() {
        finish();
        super.onBackPressed();
    }

    @Override
    public boolean onNavigateUp() {
        finish();
        return super.onNavigateUp();
    }
}

