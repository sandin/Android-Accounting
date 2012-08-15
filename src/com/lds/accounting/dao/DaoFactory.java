package com.lds.accounting.dao;

import java.sql.SQLException;

import android.content.Context;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.support.ConnectionSource;
import com.lds.accounting.db.AccountingDatabase;
import com.lds.accounting.model.Bill;

public class DaoFactory {
	
	public static <T, K> Dao<T, K> createDao(Context context, Class<T> clz) {
		ConnectionSource connectionSource = AccountingDatabase.getInstance(context).getConnectionSource();
		
		try {
			return DaoManager.createDao(connectionSource, clz);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
}
