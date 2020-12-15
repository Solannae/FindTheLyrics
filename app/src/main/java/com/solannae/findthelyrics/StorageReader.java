package com.solannae.findthelyrics;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class StorageReader extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION = 2;
    public static final String DATABASE_NAME = "FindTheLyrics.db";

    private static final String SQL_CREATE_ENTRIES_AUTH =
            "CREATE TABLE " + StorageContract.StorageEntry.AUTH_TABLE_NAME + "(" +
                    StorageContract.StorageEntry._ID + " INTEGER PRIMARY KEY, " +
                    StorageContract.StorageEntry.AUTH_COLUMN_KEY + " NVARCHAR(100), " +
                    StorageContract.StorageEntry.AUTH_COLUMN_EXPIRATION_TIME + " DATETIME)";

    private static final String SQL_DROP_ENTRIES_AUTH =
            "DROP TABLE IF EXISTS " + StorageContract.StorageEntry.AUTH_TABLE_NAME;

    public StorageReader(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES_AUTH);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DROP_ENTRIES_AUTH);
        onCreate(db);
    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }
}
