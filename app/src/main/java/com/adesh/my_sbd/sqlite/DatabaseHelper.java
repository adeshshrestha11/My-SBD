package com.adesh.my_sbd.sqlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.getbase.android.schema.Schemas;
import com.google.common.collect.ImmutableList;

public class DatabaseHelper extends SQLiteOpenHelper {

    Context mContext;
    public static DatabaseHelper instance = null;

    final static String DB_NAME = "powerliftnote.db";
    final static int DB_VERSION = 5;

    public static DatabaseHelper getInstance(Context context) {
        if (instance == null) {
            instance = new DatabaseHelper(context);
        }
        return instance;
    }

    public DatabaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
        this.mContext = context;
    }

    @SuppressWarnings("deprecation")
    public final Schemas powerliftnotesSchema = Schemas.Builder
            .currentSchema(200,

                    new Schemas.TableDefinition(Database.EXERCISES, ImmutableList.<Schemas.TableDefinitionOperation>builder().add(
                            new Schemas.AddColumn(Database.LIFT_ID, "INTEGER PRIMARY KEY AUTOINCREMENT"),
                            new Schemas.AddColumn(Database.DATE, "VARCHAR(50)  NULL"),
                            new Schemas.AddColumn(Database.WEEK_NO, "VARCHAR(50)  NULL"),
                            new Schemas.AddColumn(Database.NOTES, "VARCHAR(300)  NULL"),
                            new Schemas.AddColumn(Database.TAG, "VARCHAR(50)  NULL")
                    ).build()),

                    new Schemas.TableDefinition(Database.MAXES, ImmutableList.<Schemas.TableDefinitionOperation>builder().add(
                            new Schemas.AddColumn(Database.MAX_LIFT_ID, "INTEGER PRIMARY KEY AUTOINCREMENT"),
                            new Schemas.AddColumn(Database.SQUAT, "VARCHAR(50)  NULL"),
                            new Schemas.AddColumn(Database.BENCH, "VARCHAR(50)  NULL"),
                            new Schemas.AddColumn(Database.DEADLIFT, "VARCHAR(50)  NULL")
                    ).build())
            ).build();


    @Override
    public void onCreate(SQLiteDatabase db) {
        Schemas.Schema currentSchema = powerliftnotesSchema.getCurrentSchema();
        for (String table : currentSchema.getTables()) {
            db.execSQL(currentSchema.getCreateTableStatement(table));
        }

    }

    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        powerliftnotesSchema.upgrade(oldVersion, mContext, db);
    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        super.onDowngrade(db, oldVersion, newVersion);
        db.beginTransaction();
        try {
            db.execSQL("DROP TABLE IF EXISTS " + Database.EXERCISES);
            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
            onCreate(db);
        }
    }

    @Override
    public void onConfigure(SQLiteDatabase db) {
        super.onConfigure(db);
    }

    @Override
    public synchronized void close() {
        super.close();
    }
}

