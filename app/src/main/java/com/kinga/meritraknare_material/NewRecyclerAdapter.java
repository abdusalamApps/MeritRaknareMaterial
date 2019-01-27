package com.kinga.meritraknare_material;

import android.content.ClipData;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.kinga.meritraknare_material.data.CourseContract.CourseEntry;

/**
 * Created by kinga on 2018-02-27.
 */

public class NewRecyclerAdapter extends RecyclerView.Adapter<NewRecyclerAdapter.NewViewHolder> {
    private static final String TAG = NewRecyclerAdapter.class.getSimpleName();

    private Context mContext;
    private Cursor mCursor;
    private int mCourseType;
    private int mPoints;
    private String mCourseName;

    public NewRecyclerAdapter(Context context, Cursor cursor) {
        mContext = context;
        mCursor = cursor;
    }

    @Override
    public NewViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.list_item, parent, false);

        return new NewViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final NewViewHolder holder, final int position) {
        if (!mCursor.moveToPosition(position)){
            return;
        }

        final double courseGrade = mCursor.getDouble(mCursor.getColumnIndexOrThrow(CourseEntry.COLUMN_GRADE));

        mCourseName = mCursor.getString(mCursor.getColumnIndexOrThrow(CourseEntry.COLUMN_COURSE));
        mPoints = mCursor.getInt(mCursor.getColumnIndexOrThrow(CourseEntry.COLUMN_POINTS));
        mCourseType = mCursor.getInt(mCursor.getColumnIndexOrThrow(CourseEntry.COLUMN_TYPE));

        holder.nameView.setText(mCourseName);
        holder.pointsView.setText(String.valueOf(mPoints));
        String courseTypeString;
        switch (mCourseType) {
            case CourseEntry.TYPE_GYM_GEM:
                courseTypeString = mContext.getString(R.string.type_gymnasiegemensam);
                break;
            case CourseEntry.TYPE_PROG_GEM:
                courseTypeString = mContext.getString(R.string.type_programgemnsam);
                break;
            case CourseEntry.TYPE_INRIKTNING_GEM:
                courseTypeString = mContext.getString(R.string.type_inriktning_gem);
                break;
            case CourseEntry.TYPE_INDVAL:
                courseTypeString = mContext.getString(R.string.type_indval);
                break;
            case CourseEntry.TYPE_FORDJUPNING:
                courseTypeString = mContext.getString(R.string.type_programfordumpning);
                break;
            case CourseEntry.TYPE_UTOKAD:
                courseTypeString = mContext.getString(R.string.type_utokad);
                break;
            default:
                courseTypeString = mContext.getString(R.string.type_gymnasiegemensam);
                break;
        }

        holder.typeView.setText(courseTypeString);
        holder.itemView.setTag(mCursor.getLong(mCursor.getColumnIndexOrThrow(CourseEntry._ID)));

        if (courseGrade == CourseEntry.GRADE_A){
            holder.gradeView.setText(R.string.grade_a);
            holder.gradeView.setBackground(ContextCompat.getDrawable(mContext,R.drawable.rounded_icon_a));
        } else if (courseGrade == CourseEntry.GRADE_B){
            holder.gradeView.setText(R.string.grade_b);
            holder.gradeView.setBackground(ContextCompat.getDrawable(mContext,R.drawable.rounded_icon_b));
        }  else if (courseGrade == CourseEntry.GRADE_C){
            holder.gradeView.setText(R.string.grade_c);
            holder.gradeView.setBackground(ContextCompat.getDrawable(mContext,R.drawable.rounded_icon_c));
        } else if (courseGrade == CourseEntry.GRADE_D){
            holder.gradeView.setText(R.string.grade_d);
            holder.gradeView.setBackground(ContextCompat.getDrawable(mContext,R.drawable.rounded_icon_d));
        } else if (courseGrade == CourseEntry.GRADE_E){
            holder.gradeView.setText(R.string.grade_e);
            holder.gradeView.setBackground(ContextCompat.getDrawable(mContext,R.drawable.rounded_icon_e));
        } else if (courseGrade == CourseEntry.GRADE_F){
            holder.gradeView.setText(R.string.grade_f);
            holder.gradeView.setBackground(ContextCompat.getDrawable(mContext,R.drawable.rounded_icon_f));
        }

        holder.viewForeground.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext.getApplicationContext(), EditorActivity.class);
                Log.d(TAG, "onClick: item index " + holder.getAdapterPosition() + " name " + holder.nameView.getText());
                Uri uri = ContentUris.withAppendedId(CourseEntry.CONTENT_URI, getItemId(holder.getAdapterPosition()));
                intent.setData(uri);
                intent.putExtra("name", holder.nameView.getText());
                intent.putExtra("grade", courseGrade);
                intent.putExtra("ggPoints", mPoints);
                intent.putExtra("type", mCourseType);
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mCursor.getCount();
    }

    public static class NewViewHolder extends RecyclerView.ViewHolder {
        TextView gradeView, nameView, pointsView, typeView;
        View viewForeground, viewBackground;

        private NewViewHolder.ClickListener mClickListener;
        public NewViewHolder(View itemView) {
            super(itemView);
            gradeView = itemView.findViewById(R.id.item_grade);
            nameView = itemView.findViewById(R.id.item_name);
            pointsView = itemView.findViewById(R.id.item_points);
            typeView = itemView.findViewById(R.id.item_type);

            viewForeground = itemView.findViewById(R.id.item_viewForeground);
            viewBackground = itemView.findViewById(R.id.item_viewBackground);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mClickListener.onItemClick(view, getAdapterPosition());
                }
            });
        }

        //Interface to send callbacks...
        public interface ClickListener{
            public void onItemClick(View view, int position);
            public void onItemLongClick(View view, int position);
        }

        public void setOnClickListener(NewViewHolder.ClickListener clickListener){
            mClickListener = clickListener;
        }

    }

    public void swapCursor(Cursor newCursor){
        if (mCursor != null){
            mCursor.close();
        }
        mCursor = newCursor;
        if (newCursor != null){
            notifyDataSetChanged();
        }

    }


}
