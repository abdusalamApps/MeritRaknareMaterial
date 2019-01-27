package com.kinga.meritraknare_material.data;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import static com.kinga.meritraknare_material.data.CourseContract.CONTENT_AUTHORITY;
import static com.kinga.meritraknare_material.data.CourseContract.CourseEntry.COLUMN_COURSE;
import static com.kinga.meritraknare_material.data.CourseContract.CourseEntry.CONTENT_ITEM_TYPE;
import static com.kinga.meritraknare_material.data.CourseContract.CourseEntry.CONTENT_LIST_TYPE;
import static com.kinga.meritraknare_material.data.CourseContract.CourseEntry.TABLE_NAME;
import static com.kinga.meritraknare_material.data.CourseContract.CourseEntry._ID;
import static com.kinga.meritraknare_material.data.CourseContract.PATH_COURSES;

/**
 * Created by kinga on 2018-02-27.
 */

public class CourseProvider extends ContentProvider {
    private static final String TAG = "CourseProvider";

    private static final int COURSE = 100;
    private static final int COURSE_ID = 101;

    private static final UriMatcher sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    static {
        sUriMatcher.addURI(CONTENT_AUTHORITY, PATH_COURSES, COURSE);
        sUriMatcher.addURI(CONTENT_AUTHORITY, PATH_COURSES + "/#", COURSE_ID);
    }

    private CourseDbHelper mDbHelper;
    @Override
    public boolean onCreate() {
        mDbHelper = new CourseDbHelper(getContext());
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        // Get readable database
        SQLiteDatabase database = mDbHelper.getReadableDatabase();
        // This cursor will hold the result of the query
        Cursor cursor;

        // Figure out if the URI matcher can mathc the URI to a specific code
        int match = sUriMatcher.match(uri);
        switch (match){
            case COURSE:
                cursor = database.query(
                        TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder
                );
                break;
            case COURSE_ID:
                selection = _ID + "=?";
                selectionArgs = new String[] {String.valueOf(ContentUris.parseId(uri))};

                cursor = database.query(
                        TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder
                );
                break;
            default:
                throw new IllegalArgumentException("Cannot query Unknown URI " + uri);
        }
        cursor.setNotificationUri(getContext().getContentResolver(), uri);
        return cursor;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues contentValues) {
        final int match = sUriMatcher.match(uri);
        switch (match){
            case COURSE:
                return insert(uri, contentValues);
            default:
                throw new IllegalArgumentException("Insertion is not supported for " + uri);
        }
    }

    private Uri insertCourse(Uri uri, ContentValues contentValues){
        String course = contentValues.getAsString(COLUMN_COURSE);
        if (course == null){
            throw new IllegalArgumentException("Course requires a name");
        }
        // Get writable database
        SQLiteDatabase database = mDbHelper.getWritableDatabase();
        long id = database.insert(TABLE_NAME, null, contentValues);
        if (id == -1){
            Log.e(TAG, "Failes to insert row for " + uri);
            return null;
        }

        getContext().getContentResolver().notifyChange(uri, null);

        // Return the new URI with the ID (of the newly inserted row) appended at the end
        return ContentUris.withAppendedId(uri, id);
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues contentValues, @Nullable String selection, @Nullable String[] selectionArgs) {
        final int match = sUriMatcher.match(uri);
        switch (match){
            case COURSE:
                return updateCourse(uri, contentValues, selection, selectionArgs);
            case COURSE_ID:
                selection = _ID + "=?";
                selectionArgs = new String[]{ String.valueOf(ContentUris.parseId(uri)) };
                return updateCourse(uri, contentValues, selection, selectionArgs);
            default:
                throw new IllegalArgumentException("Update is not supported for " + uri);
        }
    }

    private int updateCourse(Uri uri, ContentValues values, String selection,
                             String[] selectionArgs){

        if (values.size() == 0){
            return 0;
        }
        SQLiteDatabase database = mDbHelper.getWritableDatabase();
        int rowsUpdated = database.update(TABLE_NAME, values, selection, selectionArgs);
        // If 1 or more rows were updated, then notify all listeners that the data at the
        // given URI has changed
        if (rowsUpdated != 0){
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return rowsUpdated;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        // Get writable database
        SQLiteDatabase database = mDbHelper.getWritableDatabase();

        // Track the number of rows that were deleted
        int rowsDeleted;

        final int match = sUriMatcher.match(uri);
        switch (match){
            case COURSE:
                // Deleting all rows that match the selection and selection args
                // for case Grades
                rowsDeleted = database.delete(TABLE_NAME, selection, selectionArgs);
                // if 1 or more rows were deleted , then notify all listeners that the data at the
                // given URI has changed
                if (rowsDeleted!= 0){
                    getContext().getContentResolver().notifyChange(uri, null);
                }
                return rowsDeleted;
            case COURSE_ID:
                // Delete a single row given by the Id in the URI
                selection = _ID + "=?";
                selectionArgs = new String[] { String.valueOf(ContentUris.parseId(uri))};
                // For case COURSE_ID
                // Delete a single row given by the ID in the URI
                rowsDeleted = database.delete(TABLE_NAME, selection, selectionArgs);
                if (rowsDeleted != 0){
                    getContext().getContentResolver().notifyChange(uri, null);
                }
                return rowsDeleted;
            default:
                throw new IllegalArgumentException("Deleting is not supported for " + uri);
        }
    }


    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        final int match = sUriMatcher.match(uri);
        switch (match){
            case COURSE:
                return CONTENT_LIST_TYPE;
            case COURSE_ID:
                return CONTENT_ITEM_TYPE;

            default:
                throw new IllegalStateException("Unknown URI " + uri + " with match " + match);
        }
    }


}
