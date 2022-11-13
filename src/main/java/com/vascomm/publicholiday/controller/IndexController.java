package com.vascomm.publicholiday.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.vascomm.publicholiday.model.CountryModel;
import com.vascomm.publicholiday.model.RequestCountry;
import com.vascomm.publicholiday.service.CountryService;

@Controller
public class IndexController {

	@Autowired
	CountryService countryService;
	
	@RequestMapping("/index")
	public String showPublicHoliday(Model model) {
		model.addAttribute("holidays", countryService.getIndex());
		model.addAttribute("location", countryService.getLocation());
		return "index";
	}
	
	@GetMapping("/country")
	public String selectCountry(Model model) {
		model.addAttribute("countryList", countryService.getCountryList());
		model.addAttribute("months", countryService.getMonth());
		return "country";
	}
	
	@PostMapping("/country")
	public String showCountry(@ModelAttribute("selectCountry") RequestCountry reqCountry, Model model) {
		model.addAttribute("holidays", countryService.getCountry(reqCountry));
		model.addAttribute("location", countryService.getCountryList(reqCountry));
		return "index";
	}
}
