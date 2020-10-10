package com.kiwihouse.dao.entity;

import java.util.List;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Setter
@Getter
public class DevHistoryDateStatistics {
	private List<Float> cur;
	private List<Integer> csq;
	private List<Float> vol;
	private List<Float> Hz;
	private List<Float> pwr;
	private List<Float> curRidevol;
	private List<String> addTime;
	private List<Float> leakCur;
	private List<Float> pwrFct;
	private List<Float> kwh;
	
	private List<Float> curA;
	private List<Float> curB;
	private List<Float> curC;
	
	private List<Float> volA;
	private List<Float> volB;
	private List<Float> volC;
	
	private List<Float> pwrA;
	private List<Float> pwrB;
	private List<Float> pwrC;
	private List<Float> pwrTotle;
	
	private List<Float> pwrFctA;
	private List<Float> pwrFctB;
	private List<Float> pwrFctC;
}
