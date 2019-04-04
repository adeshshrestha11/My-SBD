package com.adesh.my_sbd.sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.adesh.my_sbd.model.DataModel;
import com.adesh.my_sbd.model.MaxLiftModel;

import java.util.ArrayList;

//import static com.infodev.mdabali.sqlite.Database.TAB_TOPUP;


/**
 * Created by kedar on 11/6/2016.
 */

public class DatabaseAccessHelper {

    SQLiteDatabase db;
    public static DatabaseHelper databaseAccessHelper;
    Context mContext;

    public DatabaseAccessHelper(Context context) {
        this.mContext = context;
        if (databaseAccessHelper == null) {
            databaseAccessHelper = DatabaseHelper.getInstance(context);
        }
    }

    //method to add lifts (squat, bench, deadlift)
    public boolean addLiftData(DataModel dataModel) {
        boolean b = false;
        db = databaseAccessHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        try {
            db.beginTransaction();
            if (dataModel != null) {
                values.put(Database.DATE, dataModel.getDate());
                values.put(Database.LIFT_ID, dataModel.getLift_id());
                values.put(Database.WEEK_NO, dataModel.getWeek_no());
                values.put(Database.NOTES, dataModel.getNotes());
                values.put(Database.TAG, dataModel.getTag());
                if (db.insert(Database.EXERCISES, null, values) != -1) b = true;
            }
            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
        }
        db.close();
        return b;
    }

    //method to update lifts
    public boolean updateLiftDetails(DataModel updateDataModel) {
        db = databaseAccessHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        if (updateDataModel != null) {
            values.put(Database.DATE, updateDataModel.getDate());
            values.put(Database.LIFT_ID, updateDataModel.getLift_id());
            values.put(Database.WEEK_NO, updateDataModel.getWeek_no());
            values.put(Database.NOTES, updateDataModel.getNotes());
            values.put(Database.TAG, updateDataModel.getTag());
            db.update(Database.EXERCISES, values, "lift_id = ? ", new String[]{Integer.toString(Integer.parseInt(updateDataModel.getLift_id()))});
            return true;

        } else {
            return false;
        }
    }

    //method to delete lifts
    public ArrayList<DataModel> deleteLiftDetails(DataModel deleteDataModel) {
        ArrayList<DataModel> list = new ArrayList<>();
        db = databaseAccessHelper.getWritableDatabase();

        String query = "DELETE FROM " + Database.EXERCISES + " WHERE " + Database.LIFT_ID + " = " + deleteDataModel.getLift_id();
        Cursor cursor = db.rawQuery(query, null);
        Log.d("deleteQuery", query);

        if (cursor.moveToFirst()) {
            do {
                list.add(new DataModel(
                        cursor.getString(cursor.getColumnIndex(Database.LIFT_ID)),
                        cursor.getString(cursor.getColumnIndex(Database.DATE)),
                        cursor.getString(cursor.getColumnIndex(Database.WEEK_NO)),
                        cursor.getString(cursor.getColumnIndex(Database.NOTES)),
                        cursor.getString(cursor.getColumnIndex(Database.TAG))));
            } while (cursor.moveToNext());
        }

        db.close();
        return list;
    }

    //method for displaying deadlift
    public ArrayList<DataModel> getSquatDetails() {
        ArrayList<DataModel> list = new ArrayList<>();
        db = databaseAccessHelper.getWritableDatabase();

//        String query = "SELECT * FROM " + Database.EXERCISES;
        String query = "SELECT * FROM " + Database.EXERCISES + " WHERE " + Database.TAG + "= \"s\"";
        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            do {
                list.add(new DataModel(
                        cursor.getString(cursor.getColumnIndex(Database.LIFT_ID)),
                        cursor.getString(cursor.getColumnIndex(Database.DATE)),
                        cursor.getString(cursor.getColumnIndex(Database.WEEK_NO)),
                        cursor.getString(cursor.getColumnIndex(Database.NOTES)),
                        cursor.getString(cursor.getColumnIndex(Database.TAG))));
            } while (cursor.moveToNext());
        }

        db.close();
        return list;
    }

    //method for displaying deadlift
    public ArrayList<DataModel> getBenchDetails() {
        ArrayList<DataModel> list = new ArrayList<>();
        db = databaseAccessHelper.getWritableDatabase();

//        String query = "SELECT * FROM " + Database.EXERCISES;
        String query = "SELECT * FROM " + Database.EXERCISES + " WHERE " + Database.TAG + "= \"b\"";
        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            do {
                list.add(new DataModel(
                        cursor.getString(cursor.getColumnIndex(Database.LIFT_ID)),
                        cursor.getString(cursor.getColumnIndex(Database.DATE)),
                        cursor.getString(cursor.getColumnIndex(Database.WEEK_NO)),
                        cursor.getString(cursor.getColumnIndex(Database.NOTES)),
                        cursor.getString(cursor.getColumnIndex(Database.TAG))));
            } while (cursor.moveToNext());
        }

        db.close();
        return list;
    }
    //method for displaying deadlift
    public ArrayList<DataModel> getDeadliftDetails() {
        ArrayList<DataModel> list = new ArrayList<>();
        db = databaseAccessHelper.getWritableDatabase();

//        String query = "SELECT * FROM " + Database.EXERCISES;
        String query = "SELECT * FROM " + Database.EXERCISES + " WHERE " + Database.TAG + "= \"d\"";
        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            do {
                list.add(new DataModel(
                        cursor.getString(cursor.getColumnIndex(Database.LIFT_ID)),
                        cursor.getString(cursor.getColumnIndex(Database.DATE)),
                        cursor.getString(cursor.getColumnIndex(Database.WEEK_NO)),
                        cursor.getString(cursor.getColumnIndex(Database.NOTES)),
                        cursor.getString(cursor.getColumnIndex(Database.TAG))));
            } while (cursor.moveToNext());
        }

        db.close();
        return list;
    }


    //method to add maxes
    public boolean addMaxes(MaxLiftModel maxLiftModel) {
        boolean b = false;
        db = databaseAccessHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        try {
            db.beginTransaction();
            if (maxLiftModel != null) {
                values.put(Database.MAX_LIFT_ID, maxLiftModel.getMax_lift_id());
                values.put(Database.SQUAT, maxLiftModel.getSquat());
                values.put(Database.BENCH, maxLiftModel.getBench());
                values.put(Database.DEADLIFT, maxLiftModel.getDeadlift());
                if (db.insert(Database.MAXES, null, values) != -1) b = true;
            }
            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
        }
        db.close();
        return b;
    }

    //method to get maxes
    public MaxLiftModel getMaxes() {
        MaxLiftModel list = null;
        db = databaseAccessHelper.getWritableDatabase();

        String query = "SELECT * FROM " + Database.MAXES;
        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            do {
                list = new MaxLiftModel(cursor.getString(cursor.getColumnIndex(Database.MAX_LIFT_ID)),
                        cursor.getString(cursor.getColumnIndex(Database.SQUAT)),
                        cursor.getString(cursor.getColumnIndex(Database.BENCH)),
                        cursor.getString(cursor.getColumnIndex(Database.DEADLIFT)));
            } while (cursor.moveToNext());
        }

        db.close();
        return list;
    }
}

