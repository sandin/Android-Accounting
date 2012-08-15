package com.lds.accounting.model;

import java.util.List;

import android.content.Context;
import android.test.InstrumentationTestCase;

import com.j256.ormlite.dao.Dao;
import com.lds.accounting.dao.DaoFactory;

public class BillTest extends InstrumentationTestCase {
	private Context context;
	Dao<Bill, Integer> billDao;
	
	protected void setUp() throws Exception {
		context = getInstrumentation().getTargetContext();
		billDao = DaoFactory.createDao(context, Bill.class);
	};

	protected void tearDown() throws Exception {};
	
	public void testInsert() throws Exception {
		Bill bill = new Bill();
		bill.setOutlay(10D);
		
		billDao.create(bill);
	}
	
	public void testQuery() throws Exception {
		List<Bill> list = billDao.queryForAll();
		assertNotNull(list);
		assertTrue(list.size() > 0);
		
		for (Bill b: list) {
			System.out.println("b: " + b);
		}
	}
}
