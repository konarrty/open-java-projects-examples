package com.example.aston.controller;

import com.example.aston.dao.CountryDAO;
import com.example.aston.dao.impl.CountryDAOImpl;
import com.example.aston.model.Country;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;

import java.util.Collection;

@Produces(value = MediaType.APPLICATION_JSON)
@Consumes(value = MediaType.APPLICATION_JSON)
@Path("/countries")
public class CountryResource {

    private final CountryDAO countryDAO = new CountryDAOImpl();

    @GET
    @Path("{id}")
    public Country getCountryById(@PathParam(value = "id") Long id,
                                  @HeaderParam("cacheable") boolean cacheable) {
        return countryDAO.getCountryById(id, cacheable);
    }

    @GET
    public Collection<Country> getAllCountries() {

        return countryDAO.getAllCountries();
    }

    @POST
    public void createCountry(Country country) {
        countryDAO.createCountry(country);
    }

    @PUT
    @Path("/{id}")
    public void putCountry(@PathParam(value = "id") Long id, Country country) {
        countryDAO.putCountry(country, id);
    }

    @DELETE
    @Path("/{id}")
    public void deleteCountry(@PathParam(value = "id") Long id) {
        countryDAO.deleteCountry(id);
    }

}
