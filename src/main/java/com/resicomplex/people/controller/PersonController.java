package com.resicomplex.people.controller;

import com.resicomplex.people.dto.CreatePersonRequest;
import com.resicomplex.people.dto.PersonResponse;
import com.resicomplex.people.model.PersonType;
import com.resicomplex.people.service.IPersonService;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping({"/api/v1/people", "/api/v1/persons"})
public class PersonController {

    private final IPersonService personService;

    public PersonController(IPersonService personService) {
        this.personService = personService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PersonResponse createPerson(@Valid @RequestBody CreatePersonRequest request) {
        return personService.createPerson(request);
    }

    @GetMapping
    public List<PersonResponse> getPersons(
            @RequestParam(name = "personType", required = false) PersonType personType,
            @RequestParam(name = "unitId", required = false) String unitId
    ) {
        if (unitId != null && !unitId.isBlank()) {
            return personService.getPersonsByUnitId(unitId.trim());
        }
        if (personType != null) {
            return personService.getPersonsByType(personType);
        }
        return personService.getAllPersons();
    }

    @GetMapping("/{id}")
    public PersonResponse getPersonById(@PathVariable("id") String id) {
        return personService.getPersonById(id);
    }

    @GetMapping("/unit/{unitId}")
    public List<PersonResponse> getPersonsByUnit(@PathVariable("unitId") String unitId) {
        return personService.getPersonsByUnitId(unitId);
    }
}
