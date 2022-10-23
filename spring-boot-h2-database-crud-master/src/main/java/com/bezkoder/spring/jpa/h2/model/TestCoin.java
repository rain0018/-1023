package com.bezkoder.spring.jpa.h2.model;

import java.util.Date;

import javax.persistence.*;

@Entity
@Table(name = "testCoin")
public class TestCoin{

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id; 

	@Column(name = "coinName")
	private String coinName;

	@Column(name = "coinChName")
	private String coinChName;

	@Column(name = "exchangeRate")
	private String exchangeRate;
	
	@Column(name = "updateDate")
	private Date updateDate;

	public TestCoin() {

	}

	public TestCoin(String coinName, String coinChName, String exchangeRate, Date updateDate) {
		this.coinName = coinName;
		this.coinChName = coinChName;
		this.exchangeRate = exchangeRate;
		this.updateDate = updateDate;
	}

	public long getId() {
		return id;
	}



	public String getCoinName() {
		return coinName;
	}

	public void setCoinName(String coinName) {
		this.coinName = coinName;
	}

	public String getCoinChName() {
		return coinChName;
	}

	public void setCoinChName(String coinChName) {
		this.coinChName = coinChName;
	}

	public String getExchangeRate() {
		return exchangeRate;
	}

	public void setExchangeRate(String exchangeRate) {
		this.exchangeRate = exchangeRate;
	}
	
	

	public Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	@Override
	public String toString() {
		return "Tutorial [id=" + id + ", coinName=" + coinName + ", coinChName=" + coinChName + ", exchangeRate=" + exchangeRate + "]";
	}

}
