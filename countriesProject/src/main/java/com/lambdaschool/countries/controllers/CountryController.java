package com.lambdaschool.countries.controllers;

import com.lambdaschool.countries.models.Country;
import com.lambdaschool.countries.repositories.CountryRepository;
import org.h2.util.CacheHead;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

//tells the spring framework to connect all of our get requests in a way that users can get to em
@RestController
public class CountryController {

    //function that will help filter
    private List<Country> findCountries(List<Country> countryList, CheckCountry tester){
        //creates new list
        List<Country> filteredList = new ArrayList<>();
        //filters through old list adds items that pass test to new list
        for (Country c : countryList){
            if(tester.test(c)){
                filteredList.add(c);
            }
        }
        //return new list
        return filteredList;
    }

    //connects controller to repo
    @Autowired
    CountryRepository countrepos;

    //get all countries
    //address that the calls will use
    @GetMapping(value = "countries/all", produces = {"application/json"})
    //creates a method to grab all the countries in the list, sort them, and return them
    public ResponseEntity<?> listAllCountries(){
        //dimension list
        List<Country> countryList = new ArrayList<>();
        //add data in countRepo to countryList
        countrepos.findAll().iterator().forEachRemaining(countryList::add);

        //sort through countryList and alphabetize it
        countryList.sort((e1, e2) -> e1.getName().compareToIgnoreCase(e2.getName()));
        //return sorted list
        return new ResponseEntity<>(countryList, HttpStatus.OK);
    }

    //listens at endpoint with the variable "letter", returns a json object
    @GetMapping(value = "names/start/{letter}", produces = {"application/json"})
    //function listAllByName takes a parameter from url
    public ResponseEntity<?> listAllByName(@PathVariable char letter){
        //make new list
        List<Country> countryList = new ArrayList<>();
        //adds all countries in countRepo to countryList
        countrepos.findAll().iterator().forEachRemaining(countryList::add);
        //creates a new list from countryList that has been filtered
        List<Country> filteredCountryList = findCountries(countryList, c -> c.getName().charAt(0) == letter);

        //gives filtered list to client
        return new ResponseEntity<>(filteredCountryList, HttpStatus.OK);
    }

    @GetMapping(value = "population/total", produces = {"application/json"})
    //function listAllByName takes a parameter from url
    public ResponseEntity<?> totalPop() {
        //dimensioning list and adding countries
        List<Country> countryList = new ArrayList<>();
        countrepos.findAll().iterator().forEachRemaining(countryList::add);

        //create a accumulator to add to
        long accu = 0;
        //Looping through country
        for (Country c : countryList){
            accu += c.getPopulation();
        }

        //prints the population total
        System.out.println("The Total Population is " + accu);

        //gives OK
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping(value = "population/min", produces = {"application/json"})
    //function listAllByName takes a parameter from url
    public ResponseEntity<?> minPop() {
        //dimensioning list and adding countries
        List<Country> countryList = new ArrayList<>();
        countrepos.findAll().iterator().forEachRemaining(countryList::add);

        countryList.sort((c1, c2) -> Long.compare(c1.getPopulation(), c2.getPopulation()));

        return new ResponseEntity<>(countryList.get(0), HttpStatus.OK);

    }

    @GetMapping(value = "population/max", produces = {"application/json"})
    //function listAllByName takes a parameter from url
    public ResponseEntity<?> maxPop() {
        //dimensioning list and adding countries
        List<Country> countryList = new ArrayList<>();
        countrepos.findAll().iterator().forEachRemaining(countryList::add);

        countryList.sort((c1, c2) -> Long.compare(c2.getPopulation(), c1.getPopulation()));

        return new ResponseEntity<>(countryList.get(0), HttpStatus.OK);

    }

}
