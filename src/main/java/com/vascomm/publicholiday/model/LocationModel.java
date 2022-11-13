package com.vascomm.publicholiday.model;

public class LocationModel {
	private String country_code;
	private String country_name;
	private String city;
	private String IPv4;
	private String state;

	public String getCountry_code() {
		return country_code;
	}

	public void setCountry_code(String country_code) {
		this.country_code = country_code;
	}

	public String getCountry_name() {
		return country_name;
	}

	public void setCountry_name(String country_name) {
		this.country_name = country_name;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getIPv4() {
		return IPv4;
	}

	public void setIPv4(String iPv4) {
		IPv4 = iPv4;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

}
