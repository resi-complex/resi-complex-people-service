package com.resicomplex.people.service;

import com.resicomplex.people.dto.CreatePersonRequest;
import com.resicomplex.people.dto.PersonResponse;
import com.resicomplex.people.model.Person;
import com.resicomplex.people.model.PersonType;
import com.resicomplex.people.repository.PersonRepository;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class PersonService implements IPersonService {

    private static final String DEFAULT_STATUS = "ACTIVE";

    private final PersonRepository personRepository;

    public PersonService(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    @Override
    public PersonResponse createPerson(CreatePersonRequest request) {
        String normalizedDocument = request.documentNumber().trim();

        if (personRepository.existsByDocumentNumber(normalizedDocument)) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "A person with document number '" + normalizedDocument + "' already exists"
            );
        }

        LocalDateTime now = LocalDateTime.now();

        Person person = new Person();
        person.setId(UUID.randomUUID().toString());
        person.setFirstName(request.firstName().trim());
        person.setLastName(request.lastName().trim());
        person.setDocumentType(request.documentType());
        person.setDocumentNumber(normalizedDocument);
        person.setEmail(request.email().trim().toLowerCase());
        person.setPhone(request.phone().trim());
        person.setPersonType(request.personType());
        person.setAssociatedUnitId(request.associatedUnitId().trim());
        person.setStatus(DEFAULT_STATUS);
        person.setCreatedAt(now);
        person.setUpdatedAt(now);

        Person saved = personRepository.save(person);
        return toResponse(saved);
    }

    @Override
    public List<PersonResponse> getAllPersons() {
        return personRepository.findAll().stream()
                .map(this::toResponse)
                .toList();
    }

    @Override
    public List<PersonResponse> getPersonsByType(PersonType personType) {
        return personRepository.findByPersonType(personType).stream()
                .map(this::toResponse)
                .toList();
    }

    @Override
    public List<PersonResponse> getPersonsByUnitId(String associatedUnitId) {
        return personRepository.findByAssociatedUnitId(associatedUnitId).stream()
                .map(this::toResponse)
                .toList();
    }

    @Override
    public PersonResponse getPersonById(String id) {
        Person person = personRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Person not found with id: " + id
                ));
        return toResponse(person);
    }

    private PersonResponse toResponse(Person person) {
        return new PersonResponse(
                person.getId(),
                person.getFirstName(),
                person.getLastName(),
                person.getDocumentType(),
                person.getDocumentNumber(),
                person.getEmail(),
                person.getPhone(),
                person.getPersonType(),
                person.getAssociatedUnitId(),
                person.getStatus(),
                person.getCreatedAt(),
                person.getUpdatedAt()
        );
    }
}
