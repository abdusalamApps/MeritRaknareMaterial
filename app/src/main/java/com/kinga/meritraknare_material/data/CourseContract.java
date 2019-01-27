package com.kinga.meritraknare_material.data;

import android.content.ContentResolver;
import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by kinga on 2018-02-27.
 */

public class CourseContract {
    public CourseContract() {}

    public static final String CONTENT_AUTHORITY = "com.kinga.meritraknare_material";

    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    public static final String PATH_COURSES = "courses";

    public static final class CourseEntry implements BaseColumns {
        public static final Uri CONTENT_URI = Uri.withAppendedPath(BASE_CONTENT_URI, PATH_COURSES);

        public static final String CONTENT_LIST_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_COURSES;
        public static final String CONTENT_ITEM_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_COURSES;

        public static final String TABLE_NAME = "courses";
        public static final String _ID = BaseColumns._ID;
        public static final String COLUMN_COURSE = "course";
        public static final String COLUMN_GRADE = "grade";
        public static final String COLUMN_POINTS = "points";
        public static final String COLUMN_TYPE = "type";

        // Grades values
        public static final double GRADE_A = 20.0;
        public static final double GRADE_B = 17.5;
        public static final double GRADE_C = 15.0;
        public static final double GRADE_D = 12.5;
        public static final double GRADE_E = 10.0;
        public static final double GRADE_F = 0.0;

        // Courses types
        public static final int TYPE_GYM_GEM = 1;
        public static final int TYPE_PROG_GEM = 2;
        public static final int TYPE_INRIKTNING_GEM = 3;
        public static final int TYPE_INDVAL = 4;
        public static final int TYPE_FORDJUPNING = 5;
        public static final int TYPE_UTOKAD = 6;
    }
}
