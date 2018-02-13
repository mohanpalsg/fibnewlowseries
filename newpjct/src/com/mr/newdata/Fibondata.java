package com.mr.newdata;

public class Fibondata {
public String stocksymbol;
public float lowbasic;
public float highbasic;
public float mid1;
public float mid2;
public float mid3;
public float mid4;
public float lowbasic1;
public float wpr;
public float stochk;
public float stochd;
public float rsi;
public double sma200;
public double getSma200() {
	return sma200;
}
public void setSma200(double sma200) {
	this.sma200 = sma200;
}
public float getMid1() {
	return mid1;
}
public void setMid1(float mid1) {
	this.mid1 = mid1;
}
public float getMid2() {
	return mid2;
}
public void setMid2(float mid2) {
	this.mid2 = mid2;
}
public float getMid3() {
	return mid3;
}
public void setMid3(float mid3) {
	this.mid3 = mid3;
}
public float getMid4() {
	return mid4;
}
public void setMid4(float mid4) {
	this.mid4 = mid4;
}
public float getLowbasic1() {
	return lowbasic1;
}
public void setLowbasic1(float lowbasic1) {
	this.lowbasic1 = lowbasic1;
}
public float getLowbasic2() {
	return lowbasic2;
}
public void setLowbasic2(float lowbasic2) {
	this.lowbasic2 = lowbasic2;
}
public float getHighbasic1() {
	return highbasic1;
}
public void setHighbasic1(float highbasic1) {
	this.highbasic1 = highbasic1;
}
public float getHighbasic2() {
	return highbasic2;
}
public void setHighbasic2(float highbasic2) {
	this.highbasic2 = highbasic2;
}
public float getHighbasic3() {
	return highbasic3;
}
public void setHighbasic3(float highbasic3) {
	this.highbasic3 = highbasic3;
}
public float getHighbasic4() {
	return highbasic4;
}
public void setHighbasic4(float highbasic4) {
	this.highbasic4 = highbasic4;
}
public float lowbasic2;
public float highbasic1;
public float highbasic2;
public float highbasic3;
public float highbasic4;

public float getWpr() {
	return wpr;
}
public void setWpr(float wpr) {
	this.wpr = wpr;
}
public float getRsi() {
	return rsi;
}
public void setRsi(float rsi) {
	this.rsi = rsi;
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
public String getStocksymbol() {
	return stocksymbol;
}
public void setStocksymbol(String stocksymbol) {
	this.stocksymbol = stocksymbol;
}
public float getLowbasic() {
	return lowbasic;
}
public void setLowbasic(float lowbasic) {
	this.lowbasic = lowbasic;
}
public float getHighbasic() {
	return highbasic;
}
public void setHighbasic(float highbasic) {
	this.highbasic = highbasic;
}
public String toString() {
	
	return this.lowbasic +":"+
			this.highbasic+":"+
			this.mid1+":"+
			this.mid2+":"+
			this.mid3+":"+
			this.mid4+":"+
			this.lowbasic1+":"+
			this.lowbasic2+":"+
			this.highbasic1+":"+
			this.highbasic2+":"+
			this.highbasic3+":"+
			this.highbasic4;
	
}
}
