package com.kiwihouse.dao.entity;

import java.util.Date;

public class ThreePhseDataAndRealPwr {

	private Float dataPwr;
	
	private Date addTime;
	
	private Float pwrA;
	
	private Float pwrB;
	
	private Float pwrC;
	
	private Float pwrTotle;

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

	public Float getPwrA() {
		return pwrA;
	}

	public void setPwrA(Float pwrA) {
		this.pwrA = pwrA;
	}

	public Float getPwrB() {
		return pwrB;
	}

	public void setPwrB(Float pwrB) {
		this.pwrB = pwrB;
	}

	public Float getPwrC() {
		return pwrC;
	}

	public void setPwrC(Float pwrC) {
		this.pwrC = pwrC;
	}

	public Float getPwrTotle() {
		return pwrTotle;
	}

	public void setPwrTotle(Float pwrTotle) {
		this.pwrTotle = pwrTotle;
	}

	public ThreePhseDataAndRealPwr( Date addTime, Float pwrA, Float pwrB, Float pwrC, Float pwrTotle) {
		super();
		this.addTime = addTime;
		this.pwrA = pwrA;
		this.pwrB = pwrB;
		this.pwrC = pwrC;
		this.pwrTotle = pwrTotle;
	}

	public ThreePhseDataAndRealPwr() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
}
