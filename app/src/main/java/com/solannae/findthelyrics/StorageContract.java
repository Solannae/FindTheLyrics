package com.solannae.findthelyrics;

import android.provider.BaseColumns;

public class StorageContract {
    private StorageContract() {}

    public static class StorageEntry implements BaseColumns {
        public static final String AUTH_TABLE_NAME = "user_authentication";
        public static final String AUTH_COLUMN_KEY = "key";
        public static final String AUTH_COLUMN_EXPIRATION_TIME = "expiration_time";
    }
}
