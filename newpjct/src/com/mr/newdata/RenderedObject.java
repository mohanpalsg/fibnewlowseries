package com.mr.newdata;

public class RenderedObject {

	public String stocksymbol;
	public float Lasttradedprice;
	public String Level;
	public float  Levelvalue;
	public float  LTPdiff;
	public float  highdiff;
	public float  Lowdiff;
	
	public float stochk;
	public float stochd;
	public float Williamsr;
	public float rsi;
	public String Trend;
	public String getTrend() {
		return Trend;
	}
	public void setTrend(String trend) {
		Trend = trend;
	}
	public String getStocksymbol() {
		return stocksymbol;
	}
	public void setStocksymbol(String stocksymbol) {
		this.stocksymbol = stocksymbol;
	}
	public float getLasttradedprice() {
		return Lasttradedprice;
	}
	public void setLasttradedprice(float lasttradedprice) {
		Lasttradedprice = lasttradedprice;
	}
	public String getLevel() {
		return Level;
	}
	public void setLevel(String level) {
		Level = level;
	}
	public float getLevelvalue() {
		return Levelvalue;
	}
	public void setLevelvalue(float levelvalue) {
		Levelvalue = levelvalue;
	}
	public float getLTPdiff() {
		return LTPdiff;
	}
	public void setLTPdiff(float lTPdiff) {
		LTPdiff = lTPdiff;
	}
	public float getHighdiff() {
		return highdiff;
	}
	public void setHighdiff(float highdiff) {
		this.highdiff = highdiff;
	}
	public float getLowdiff() {
		return Lowdiff;
	}
	public void setLowdiff(float lowdiff) {
		Lowdiff = lowdiff;
	}
	
	public float getStochk() {
		return stochk;
	}
	public void setStochk(float stochk) {
		this.stochk = stochk;
	}
	public float getStochd() {
		return stochd;
	}
	public void setStochd(float stochd) {
		this.stochd = stochd;
	}
	public float getWilliamsr() {
		return Williamsr;
	}
	public void setWilliamsr(float williamsr) {
		Williamsr = williamsr;
	}
	public float getRsi() {
		return rsi;
	}
	public void setRsi(float rsi) {
		this.rsi = rsi;
	}
}
