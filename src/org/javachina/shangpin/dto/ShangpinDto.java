package org.javachina.shangpin.dto;

import java.sql.Date;

public class ShangpinDto {
	private int id;
	private String name;
	private double price;
	private String address;
	private Date inputDate;
	private int leixingId;
	private String description;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public Date getInputDate() {
		return inputDate;
	}
	public void setInputDate(Date inputDate) {
		this.inputDate = inputDate;
	}
	public int getLeixingId() {
		return leixingId;
	}
	public void setLeixingId(int leixingId) {
		this.leixingId = leixingId;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
}
