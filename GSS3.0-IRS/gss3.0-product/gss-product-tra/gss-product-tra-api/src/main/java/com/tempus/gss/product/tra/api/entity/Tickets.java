package com.tempus.gss.product.tra.api.entity;

import java.io.Serializable;

public class Tickets  implements Serializable {

	private CySeat hardseat;//硬座

	private CySeat noseat;//无座

	private CySeat hardsleepermid;//硬卧中铺

	private CySeat softsleeperdown;//软卧下铺
	
	private CySeat softseat;//软座
	
	private CySeat firstseat;//一等坐
	
	private CySeat secondseat;//二等坐
	
	private CySeat hardsleeperup;//硬卧上铺
		
	private CySeat hardsleeperdown;//硬卧下铺
	
	private CySeat softsleeperup;//软卧上铺
	
	private CySeat businessseat;//商务坐
	
	public CySeat getSoftseat() {
		return softseat;
	}

	public void setSoftseat(CySeat softseat) {
		this.softseat = softseat;
	}

	public CySeat getFirstseat() {
		return firstseat;
	}

	public void setFirstseat(CySeat firstseat) {
		this.firstseat = firstseat;
	}

	public CySeat getSecondseat() {
		return secondseat;
	}

	public void setSecondseat(CySeat secondseat) {
		this.secondseat = secondseat;
	}

	public CySeat getHardsleeperup() {
		return hardsleeperup;
	}

	public void setHardsleeperup(CySeat hardsleeperup) {
		this.hardsleeperup = hardsleeperup;
	}

	public CySeat getHardsleeperdown() {
		return hardsleeperdown;
	}

	public void setHardsleeperdown(CySeat hardsleeperdown) {
		this.hardsleeperdown = hardsleeperdown;
	}

	public CySeat getSoftsleeperup() {
		return softsleeperup;
	}

	public void setSoftsleeperup(CySeat softsleeperup) {
		this.softsleeperup = softsleeperup;
	}

	public CySeat getBusinessseat() {
		return businessseat;
	}

	public void setBusinessseat(CySeat businessseat) {
		this.businessseat = businessseat;
	}

	public CySeat getSpecialseat() {
		return specialseat;
	}

	public void setSpecialseat(CySeat specialseat) {
		this.specialseat = specialseat;
	}

	public CySeat getAdvancedsoftsleeper() {
		return advancedsoftsleeper;
	}

	public void setAdvancedsoftsleeper(CySeat advancedsoftsleeper) {
		this.advancedsoftsleeper = advancedsoftsleeper;
	}

	public CySeat getOtherseat() {
		return otherseat;
	}

	public void setOtherseat(CySeat otherseat) {
		this.otherseat = otherseat;
	}

	private CySeat specialseat;
	
	private CySeat advancedsoftsleeper;
	
	private CySeat otherseat;
	

	public void setHardseat(CySeat hardseat) {
		this.hardseat = hardseat;
	}

	public CySeat getHardseat() {
		return this.hardseat;
	}

	public void setNoseat(CySeat noseat) {
		this.noseat = noseat;
	}

	public CySeat getNoseat() {
		return this.noseat;
	}

	public void setHardsleepermid(CySeat hardsleepermid) {
		this.hardsleepermid = hardsleepermid;
	}

	public CySeat getHardsleepermid() {
		return this.hardsleepermid;
	}

	public void setSoftsleeperdown(CySeat softsleeperdown) {
		this.softsleeperdown = softsleeperdown;
	}

	public CySeat getSoftsleeperdown() {
		return this.softsleeperdown;
	}

}
