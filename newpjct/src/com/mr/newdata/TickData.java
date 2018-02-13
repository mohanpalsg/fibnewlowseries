package com.mr.newdata;

import java.util.Date;

public class TickData {

	public float openprice;
	public float getOpenprice() {
		return openprice;
	}
	public void setOpenprice(float openprice) {
		this.openprice = openprice;
	}
	public float getHighprice() {
		return highprice;
	}
	public void setHighprice(float highprice) {
		this.highprice = highprice;
	}
	public float getLowprice() {
		return lowprice;
	}
	public void setLowprice(float lowprice) {
		this.lowprice = lowprice;
	}
	public float getCloseprice() {
		return closeprice;
	}
	public void setCloseprice(float closeprice) {
		this.closeprice = closeprice;
	}
	public float getVolume() {
		return volume;
	}
	public void setVolume(float volume) {
		this.volume = volume;
	}
	public Date getTickstart() {
		return tickstart;
	}
	public void setTickstart(Date tickstart) {
		this.tickstart = tickstart;
	}
	public Date getTickend() {
		return tickend;
	}
	public void setTickend(Date tickend) {
		this.tickend = tickend;
	}
	public float highprice;
	public float lowprice;
	public float closeprice;
	public float volume;
	public Date tickstart;
	public Date tickend;
	
}
