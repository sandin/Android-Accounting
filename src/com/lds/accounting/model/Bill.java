package com.lds.accounting.model;

import java.util.Date;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "bills")
public class Bill {
	
	@DatabaseField(id = true)
	private int id;
	
	/**
	 * 摘要
	 */
	@DatabaseField
	private String summary;
	
	/**
	 * 借方金额
	 */
	@DatabaseField
	private double income;
	
	/**
	 * 贷方金额
	 */
	@DatabaseField
	private double outlay;
	
	/**
	 * 借贷
	 */
	@DatabaseField
	private int direction;
	
	/**
	 * 日期
	 */
	@DatabaseField
	private Date date;
	
	public Bill() {
	}
	
	/* GET & SET */

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	public double getIncome() {
		return income;
	}

	public void setIncome(double income) {
		this.income = income;
	}

	public double getOutlay() {
		return outlay;
	}

	public void setOutlay(double outlay) {
		this.outlay = outlay;
	}

	public int getDirection() {
		return direction;
	}

	public void setDirection(int direction) {
		this.direction = direction;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	@Override
	public String toString() {
		return "Bill [id=" + id + ", summary=" + summary + ", income=" + income
				+ ", outlay=" + outlay + ", direction=" + direction + ", date="
				+ date + "]";
	}
	
}
