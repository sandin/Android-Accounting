package com.lds.accounting.db;

import java.sql.SQLException;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import com.lds.accounting.inputParser.Category;
import com.lds.accounting.inputParser.Contact;
import com.lds.accounting.inputParser.Row;
import com.lds.accounting.inputParser.Tag;
import com.lds.accounting.model.Bill;

public class AccountingDatabase {
	private static final String TAG = "AccountingDatabase";
	private static final String DATABASE_NAME = "accounting.db";
	private static final int DATABASE_VERSION = 2;
	
	private OrmLiteSqliteOpenHelper sqliteOpenHelper;
	private Context mContext;
	
	private static AccountingDatabase sInstance;
	public  static AccountingDatabase getInstance(Context context) {
		if (sInstance == null) {
			sInstance = new AccountingDatabase(context);
		}
		return sInstance;
	}
	
	public AccountingDatabase(Context context) {
		mContext = context;
		
		setSqliteOpenHelper(new DatabaseHelper(mContext));
	}
	
	public ConnectionSource getConnectionSource() {
		if (sqliteOpenHelper != null) {
			return sqliteOpenHelper.getConnectionSource();
		}
		return null;
	}
	
	public OrmLiteSqliteOpenHelper getSqliteOpenHelper() {
		return sqliteOpenHelper;
	}

	public void setSqliteOpenHelper(OrmLiteSqliteOpenHelper sqliteOpenHelper) {
		this.sqliteOpenHelper = sqliteOpenHelper;
	}
	

	/**
	 * SQLiteOpenHelper
	 */
	private static class DatabaseHelper extends OrmLiteSqliteOpenHelper {

		// Construct
		public DatabaseHelper(Context context) {
			super(context, DATABASE_NAME, null, DATABASE_VERSION);
		}

		@Override
		public void onCreate(SQLiteDatabase db, ConnectionSource connectionSource) {
			Log.d(TAG, "Create Database.");
			try {
				Log.i(DatabaseHelper.class.getName(), "onCreate");
				TableUtils.createTable(connectionSource, Bill.class);
				
				TableUtils.createTable(connectionSource, Category.class);
				TableUtils.createTable(connectionSource, Contact.class);
				TableUtils.createTable(connectionSource, Row.class);
				TableUtils.createTable(connectionSource, Tag.class);
			} catch (SQLException e) {
				Log.e(DatabaseHelper.class.getName(), "Can't create database", e);
				throw new RuntimeException(e);
			}
		}

		@Override
		public void onUpgrade(SQLiteDatabase arg0, ConnectionSource arg1,
				int arg2, int arg3) {
			// TODO Auto-generated method stub
		}

	}


}
