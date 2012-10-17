package com.lds.accounting.db;

import java.text.SimpleDateFormat;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

public class AccountDatabase {
    
    public static final String DB_NAME = "account.db";
    public static final int DB_VERSION = 1;
    
    
    public static SimpleDateFormat datetimeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
    
    private MySQLiteOpenHelper mOpenHelper;
    
    private static AccountDatabase mInstance;
    
    public static AccountDatabase getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new AccountDatabase(context);
        }
        return mInstance;
    }
    
    private AccountDatabase(Context context) {
        mOpenHelper = new MySQLiteOpenHelper(context);
    }
    
    public SQLiteDatabase getDb(boolean writeable) {
        if (writeable) {
            return mOpenHelper.getWritableDatabase();
        } else {
            return mOpenHelper.getReadableDatabase();
        }
    }
    
    // Tables 
    
    public static final String ACCOUNT_TABLE_NAME = "accounts";
    public static final String CATEGORY_TABLE_NAME = "categories";
    
    public interface AccountCols extends BaseColumns {
        public static final String SUMMARY = "summary";
        public static final String CONTENT = "content";
        public static final String NOTE = "note";
        public static final String PRICE = "price";
        public static final String DATE = "date";
        public static final String DIRECTION = "direction";
        public static final String CATEGORY_ID = "category_id";
        // tags
        // contacts
    }
    
    public interface CategoryCols extends BaseColumns {
        public static final String NAME = "name";
    }
    
    private static final String ACCOUNT_TABLE_CREATE = "CREATE TABLE "
            + ACCOUNT_TABLE_NAME + " ( " 
            + AccountCols._ID + " INTEGER PRIMARY KEY , "
            + AccountCols.SUMMARY + " TEXT, "
            + AccountCols.CONTENT + " TEXT, "
            + AccountCols.DIRECTION + " INT, "
            + AccountCols.NOTE + " TEXT, "
            + AccountCols.PRICE + " REAL, "
            + AccountCols.DATE + " DATETIME, "
            + AccountCols.CATEGORY_ID + " INT "
            + " ); ";
    
    private static final String CATEGORY_TABLE_CREATE = "CREATE TABLE "
            + CATEGORY_TABLE_NAME + "(" 
            + CategoryCols._ID + " INTEGER PRIMARY KEY ,"
            + CategoryCols.NAME + " TEXT "
            + " ); ";
    
    private class MySQLiteOpenHelper extends SQLiteOpenHelper {

        public MySQLiteOpenHelper(Context context) {
            super(context, DB_NAME, null, DB_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(ACCOUNT_TABLE_CREATE);
            db.execSQL(CATEGORY_TABLE_CREATE);
        }

        @Override
        public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) {
            // TODO Auto-generated method stub
        }
    }

}
