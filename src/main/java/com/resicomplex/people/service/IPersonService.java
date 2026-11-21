package com.resicomplex.people.service;

import com.resicomplex.people.dto.CreatePersonRequest;
import com.resicomplex.people.dto.PersonResponse;
import com.resicomplex.people.model.PersonType;
import java.util.List;

public interface IPersonService {

    PersonResponse createPerson(CreatePersonRequest request);

    List<PersonResponse> getAllPersons();

    List<PersonResponse> getPersonsByType(PersonType personType);

    List<PersonResponse> getPersonsByUnitId(String associatedUnitId);

    PersonResponse getPersonById(String id);
}
