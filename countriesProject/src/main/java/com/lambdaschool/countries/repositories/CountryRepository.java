package com.lambdaschool.countries.repositories;

import com.lambdaschool.countries.models.Country;
import org.springframework.data.repository.CrudRepository;

// connects our data in country repository to the rest of our files
public interface CountryRepository extends CrudRepository<Country, Long> {

}
