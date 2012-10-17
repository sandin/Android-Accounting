package com.lds.accounting.dao;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;
import android.util.Log;

import com.lds.accounting.db.AccountDatabase;

public abstract class BaseDao<T> {
    protected static final String TAG = "Dao";
    protected static final boolean DEBUG = true;

    protected AccountDatabase database;
    protected String tableName;

    public BaseDao(AccountDatabase database, String tableName) {
        this.database = database;
        this.tableName = tableName;
    }

    public long insert(T row) {
        if (DEBUG) Log.v(TAG, "insert: " + row.toString());
        SQLiteDatabase db = database.getDb(true);
        long id = db.insert(tableName, null, objectToValues(row));
        db.close();
        return id;
    }

    /**
     * 
     * @return 永远不会返回null,只会返回空列表
     */
    public List<T> queryForAll() {
        SQLiteDatabase db = database.getDb(false);
        Cursor cursor = db.query(tableName, null, null, null, null, null, null);
        List<T> list = cursorToList(cursor);
        cursor.close();
        db.close();
        return list;
    }
    
    public T findOneByFields(String selection, String[] selectionArgs, String orderBy) {
        SQLiteDatabase db = database.getDb(false);
        String[] columns = null;
        Cursor cursor = db.query(tableName, columns, selection, selectionArgs, null, null, orderBy);
        if (cursor != null && cursor.getCount() > 0) {
            return cursorToObject(cursor);
        }
        return null;
    }
    
    public T findById(long id) {
        String pKey = BaseColumns._ID;
        return findOneByFields(pKey + "=" + id, null, pKey);
    }

    public List<T> cursorToList(Cursor cursor) {
        List<T> list = new ArrayList<T>();
        while (cursor.moveToNext()) {
            list.add(cursorToObject(cursor));
        }
        return list;
    }
    abstract public T cursorToObject(Cursor cursor);
    
    /*
    public T cursorToObject(Cursor cursor) {
        List<T> list = cursorToList(cursor);
        if (list != null && list.size() > 0) {
            return list.get(0);
        }
        return null;
    }
    */
    
    abstract public ContentValues objectToValues(T row);
}
