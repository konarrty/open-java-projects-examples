package com.example.astontaskspringrestful.hateoas;

import com.example.astontaskspringrestful.controller.CountryController;
import com.example.astontaskspringrestful.model.Country;
import lombok.NonNull;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class CountryModelAssembler
        implements RepresentationModelAssembler<Country, EntityModel<Country>> {

    @Override
    public @NonNull EntityModel<Country> toModel(@NonNull Country entity) {
        EntityModel<Country> accountModel = EntityModel.of(entity);

        accountModel.add(linkTo(methodOn(CountryController.class).getCountryById(entity.getId())).withSelfRel());
        accountModel.add(linkTo(methodOn(CountryController.class).getAllCountries()).withRel(IanaLinkRelations.COLLECTION));

        return accountModel;
    }
}

