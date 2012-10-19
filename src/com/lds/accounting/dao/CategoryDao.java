package com.lds.accounting.dao;

import android.content.ContentValues;
import android.database.Cursor;

import com.lds.accounting.db.AccountDatabase;
import com.lds.accounting.db.AccountDatabase.CategoryCols;
import com.lds.accounting.inputParser.Category;

public class CategoryDao extends BaseDao<Category> {
   
    public CategoryDao(AccountDatabase database) {
        super(database, AccountDatabase.CATEGORY_TABLE_NAME);
    }

    @Override
    public Category cursorToObject(Cursor cursor) {
        Category item = new Category();
        item.setName(cursor.getString(cursor.getColumnIndexOrThrow(CategoryCols.NAME)));
        item.setId(cursor.getLong(cursor.getColumnIndexOrThrow(CategoryCols._ID)));
        return item;
    }
    
    @Override
    public ContentValues objectToValues(Category obj) {
        ContentValues values = new ContentValues();
        values.put(CategoryCols.NAME, obj.getName());
        return values;
    }
    
    public Category findByName(String name) {
        return findOneByFields(CategoryCols.NAME + "=?", new String[] {name}, null);
    }

    /*
     SELECT  c._id as id, name,  SUM(a.price) as total 
FROM accounts AS a LEFT JOIN categories AS c ON a.category_id=c._id
group by a.category_id having a.category_id>0
---------------------
id  name    total
1   123 6.0
3   321 35.0
     */
}
