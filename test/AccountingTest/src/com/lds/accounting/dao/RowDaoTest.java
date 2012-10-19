package com.lds.accounting.dao;

import java.util.Date;
import java.util.List;

import android.content.Context;
import android.test.InstrumentationTestCase;

import com.lds.accounting.db.AccountDatabase;
import com.lds.accounting.inputParser.Row;

public class RowDaoTest extends InstrumentationTestCase {
    
    private Context mContext;
    private RowDao dao;
    private AccountDatabase database;
    
    public void setUp() throws Exception {
        mContext = getInstrumentation().getTargetContext();
        assertNotNull(mContext);
        
        database = AccountDatabase.getInstance(mContext);
        assertNotNull(database);
        
        dao = new RowDao(database);
        assertNotNull(dao);
    }
    
    public void testInsert() { 
        Row row = insert();
        assertTrue(row.getId() > 0);
    }
    
    private Row insert() {
        Row row = new Row();
        //row.setCategory(new Category("cate"));
        row.setCategoryId(0);
        row.setContent("content");
        row.setDate(new Date());
        row.setDirection(true);
        row.setNote("note");
        row.setPrice(1.0);
        row.setSummary("summary");
        long id = dao.insert(row);
        //System.out.println("id: " + id);
        assertTrue(id > 0);
        row.setId(id);
        return row;
    }
    
    public void testQuery() {
        insert();
        
        List<Row> list = dao.queryForAll();
        System.out.println("list: " + list);
        assertNotNull(list);
        assertTrue(list.size() > 0);
        
        for (Row row : list) {
            Row r = dao.findById(row.getId());
            System.out.println("Find Item row: " + r);
            assertNotNull(r);
            assertTrue(r.getId() > 0);
        }
        
        // Test BaseDao
        Row r = dao.findOneByFields(null, null, null);
        assertNotNull(r);
    }
    
    public void testUpdate() {
        Row row = insert();
        long id = row.getId();
        row = dao.findById(id);
        assertNotNull(row);
        assertTrue(row.getId() > 0);
        
        // TODO;
    }

}
