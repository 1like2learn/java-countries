package com.lambdaschool.countries.controllers;

import com.lambdaschool.countries.models.Country;
import com.lambdaschool.countries.repositories.CountryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

//tells the spring framework to connect all of our get requests in a way that users can get to em
@RestController
public class CountryController {
    //connects controller to repo
    @Autowired
    CountryRepository countrepos;

    // address that the calls will use
    @GetMapping(value = "countries/all",
    produces = {"application/json"})
    //^gives us a json file
    // creates a method to grab all the countries in the list, sort them, and return them
    public ResponseEntity<?> listAllCountries(){
        List<Country> countryList = new ArrayList<>();
        countrepos.findAll().iterator().forEachRemaining(countryList::add);

        countryList.sort((e1, e2) -> e1.getName().compareToIgnoreCase(e2.getName()));
        return new ResponseEntity<>(countryList, HttpStatus.OK);
    }
}
