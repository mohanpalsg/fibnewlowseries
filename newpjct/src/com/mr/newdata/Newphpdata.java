package com.mr.newdata;

public class Newphpdata {
public String stocksymbol;
public float lastprice;
public float openprice;
public float highprice;
public float lowprice;
public float prevclose;
public Long tradevolume;
public float getPrevclose() {
	return prevclose;
}
public void setPrevclose(float prevclose) {
	this.prevclose = prevclose;
}
public Long getTradevolume() {
	return tradevolume;
}
public void setTradevolume(Long tradevolume) {
	this.tradevolume = tradevolume;
}
public float getHighprice() {
	return highprice;
}
public void setHighprice(float highprice) {
	this.highprice = highprice;
}
public String getStocksymbol() {
	return stocksymbol;
}
public void setStocksymbol(String stocksymbol) {
	this.stocksymbol = stocksymbol;
}
public float getLastprice() {
	return lastprice;
}
public void setLastprice(float lastprice) {
	this.lastprice = lastprice;
}
public float getOpenprice() {
	return openprice;
}
public void setOpenprice(float openprice) {
	this.openprice = openprice;
}
public float getLowprice() {
	return lowprice;
}
public void setLowprice(float lowprice) {
	this.lowprice = lowprice;
}
}
