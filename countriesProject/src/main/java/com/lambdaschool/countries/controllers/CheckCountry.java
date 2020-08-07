package com.lambdaschool.countries.controllers;

import com.lambdaschool.countries.models.Country;

//creates type so we can filter a list
public interface CheckCountry {
    //returns a boolean if the test is true
    boolean test(Country c);
}
