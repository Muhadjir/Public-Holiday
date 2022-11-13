package com.vascomm.publicholiday.service;

import java.text.DateFormatSymbols;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Year;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.vascomm.publicholiday.model.CountryList;
import com.vascomm.publicholiday.model.CountryModel;
import com.vascomm.publicholiday.model.LocationModel;
import com.vascomm.publicholiday.model.LogHoliday;
import com.vascomm.publicholiday.model.RequestCountry;
import com.vascomm.publicholiday.repository.LogHolidayRepo;

@Service
public class CountryService {

	@Autowired
	RestTemplate restTemplate;
	
	@Autowired
	LogHolidayRepo logHolidayRepo;

	private static String url = "https://date.nager.at/api/v3/";

	public CountryModel[] getIndex() {
		LocationModel ccty = getLocation();
		int year = Year.now().getValue();
		CountryModel[] countryModel = restTemplate
				.getForObject(url + "PublicHolidays/" + year + "/" + ccty.getCountry_code(), CountryModel[].class);
		for (CountryModel cm : countryModel) {
			cm.setDate(setDateFormat(cm.getDate()));
		}
		return countryModel;
	}

	public CountryList[] getCountryList() {
		CountryList[] countryList = restTemplate.getForObject(url + "AvailableCountries/", CountryList[].class);
		return countryList;
	}
	public LocationModel getCountryList(RequestCountry req) {
		CountryList[] countryList = restTemplate.getForObject(url + "AvailableCountries/", CountryList[].class);
		LocationModel country = new LocationModel();
		for (CountryList cl : countryList) {
			if(cl.getCountryCode().equalsIgnoreCase(req.getIdCountry())){
				country.setCountry_code(cl.getCountryCode());
				country.setCountry_name(cl.getName());
			}
		}
		return country;
	}

	public CountryModel[] getCountry(RequestCountry req) {
		System.out.println(req.getIdCountry() + " : " + req.getYear() + " : " + req.getMonth());
		CountryModel[] countryModel = restTemplate
				.getForObject(url + "PublicHolidays/" + req.getYear() + "/" + req.getIdCountry(), CountryModel[].class);

		for (CountryModel cm : countryModel) {
			cm.setDate(setDateFormat(cm.getDate()));
		}
		String save = saveLog(req, countryModel);
		System.out.print(save);
		
		return countryModel;
	}
	
	public String saveLog(RequestCountry req, CountryModel[] resp) {
		LogHoliday log = new LogHoliday();
		log.setRequest("{\"idCountry\":\""+req.getIdCountry()+"\",\"year\":\""+req.getYear()+"\","
				+ "\"month\":\""+req.getMonth()+"\"}");
		log.setResponse(setResp(resp));
		log.setCreatedAt(LocalDateTime.now().toString());
		LogHoliday saveLog = logHolidayRepo.saveAndFlush(log);
		System.out.print(saveLog);
		return "Save Log Done";
	}
	
	public String setResp(CountryModel[] resp) {
		String value = "";
		for(CountryModel cm : resp) {
			String add = "{\"countryCode\":\""+cm.getCountryCode()+"\",\"date\":\""+cm.getDate()+"\","
					+ "\"desc\":\""+cm.getLocalName()+"-"+cm.getName()+"\"}";
			value.concat(add);
		}
		return value;
	}

	public String setDateFormat(String dateFormat) {
		DateTimeFormatter inputFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		LocalDate dateTime = LocalDate.parse(dateFormat, inputFormat);
		DateTimeFormatter outputFormat = DateTimeFormatter.ofPattern("EEEE, dd MMMM yyyy");
		return dateTime.format(outputFormat);
	}

	public List<String> getMonth() {
//		String[] months = {"January","February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};
		List<String> months = new ArrayList<String>();
		// Create a DateFormatSymbols instance
		DateFormatSymbols dfs = new DateFormatSymbols();

		// DateFormatSymbols instance has a method by name
		// getMonths() which returns back an array of
		// months name
		String[] arrayOfMonthsNames = dfs.getMonths();

		// loop over each month name and printing on the
		// console
		months.add("Select Month");
		for (String monthName : arrayOfMonthsNames) {
			months.add(monthName);
		}
		System.out.println(months);
		return months;
	}

	public LocationModel getLocation() {

		List<HttpMessageConverter<?>> messageConverters = new ArrayList<>();
		MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
		converter.setSupportedMediaTypes(Collections.singletonList(MediaType.ALL));
		messageConverters.add(converter);
		restTemplate.setMessageConverters(messageConverters);
		LocationModel loc = restTemplate.getForObject("https://geolocation-db.com/json/", LocationModel.class);
		System.out.println(loc.getCountry_code());
		return loc;
	}
}
