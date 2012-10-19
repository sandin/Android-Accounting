package com.lds.accounting.dao;

import java.text.ParseException;
import java.util.List;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.lds.accounting.db.AccountDatabase;
import com.lds.accounting.db.AccountDatabase.AccountCols;
import com.lds.accounting.db.AccountDatabase.CategoryCols;
import com.lds.accounting.inputParser.Category;
import com.lds.accounting.inputParser.Row;

public class RowDao extends BaseDao<Row> {
   
    public RowDao(AccountDatabase database) {
        super(database, AccountDatabase.ACCOUNT_TABLE_NAME);
    }
    
    private static final String QUERY_FOR_ALL_SQL = "SELECT a.*, c.name as category_name FROM "
            + AccountDatabase.ACCOUNT_TABLE_NAME + " AS a " 
            + " LEFT JOIN " + AccountDatabase.CATEGORY_TABLE_NAME + " AS c "
            + " ON a." + AccountCols.CATEGORY_ID + "=c." + CategoryCols._ID
            + "";
    
    @Override
    public List<Row> queryForAll() {
        SQLiteDatabase db = database.getDb(false);
        Cursor cursor = db.rawQuery(QUERY_FOR_ALL_SQL, null);
        List<Row> list = cursorToList(cursor);
        cursor.close();
        db.close();
        return list;
    }

    @Override
    public Row cursorToObject(Cursor cursor) {
        Row item = new Row();
        item.setId(cursor.getLong(cursor.getColumnIndexOrThrow(AccountCols._ID)));
        item.setContent(cursor.getString(cursor.getColumnIndexOrThrow(AccountCols.CONTENT)));
        try {
            item.setDate(AccountDatabase.datetimeFormat.parse(cursor.getString(cursor.getColumnIndexOrThrow(AccountCols.DATE))));
        } catch (ParseException e) { e.printStackTrace(); }
        //item.setCategory(category);
        item.setDirection(cursor.getInt(cursor.getColumnIndexOrThrow(AccountCols.DIRECTION)) == 1);
        item.setNote(cursor.getString(cursor.getColumnIndexOrThrow(AccountCols.NOTE)));
        item.setPrice(cursor.getDouble(cursor.getColumnIndexOrThrow(AccountCols.PRICE)));
        item.setSummary(cursor.getString(cursor.getColumnIndexOrThrow(AccountCols.SUMMARY)));
        int i = cursor.getColumnIndex("category_name");
        if (i != -1) {
            item.setCategory(new Category(cursor.getString(i)));
        } else {
            Log.e(TAG, "no category");
        }
        return item;
    }
    
    @Override
    public ContentValues objectToValues(Row obj) {
        ContentValues values = new ContentValues();
        //if (obj.getCategory() != null) {
        values.put(AccountCols.CATEGORY_ID, obj.getCategoryId());
        //}
        values.put(AccountCols.CONTENT, obj.getContent());
        values.put(AccountCols.DATE, AccountDatabase.datetimeFormat.format(obj.getDate()));
        values.put(AccountCols.DIRECTION, obj.isDirection());
        values.put(AccountCols.NOTE, obj.getNote());
        values.put(AccountCols.PRICE, obj.getPrice());
        values.put(AccountCols.SUMMARY, obj.getSummary());
        return values;
    }

}
