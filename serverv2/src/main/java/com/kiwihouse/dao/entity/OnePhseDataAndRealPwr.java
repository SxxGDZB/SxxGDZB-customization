package com.kiwihouse.dao.entity;

import java.util.Date;

import com.kiwihouse.util.excel.DateUtils;

/**
 * 
 * @author cmx
 *
 */
public class OnePhseDataAndRealPwr {
	/**
	 * 真实功率  ----> 通过电流 * 电压 得出
	 */
	private Float realPwr;
	/**
	 * 设备返回
	 */
	private Float dataPwr;
	
	private Date addTime;

	public Float getRealPwr() {
		return realPwr;
	}

	public void setRealPwr(Float realPwr) {
		this.realPwr = realPwr;
	}

	public Float getDataPwr() {
		return dataPwr;
	}

	public void setDataPwr(Float dataPwr) {
		this.dataPwr = dataPwr;
	}

	public Date getAddTime() {
		return addTime;
	}

	public void setAddTime(Date addTime) {
		this.addTime = addTime;
	}

	public OnePhseDataAndRealPwr(Float realPwr, Float dataPwr, Date addTime) {
		super();
		this.realPwr = realPwr;
		this.dataPwr = dataPwr;
		this.addTime = addTime;
	}

	public OnePhseDataAndRealPwr() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public String toString() {
		return "DataAndRealPwr [realPwr=" + realPwr + ", dataPwr=" + dataPwr + ", addTime=" + DateUtils.parseDateToStr("yyyy-MM-dd HH:mm:ss", addTime) + "]";
	}
	
	
	
	
}
