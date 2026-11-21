package com.resicomplex.people.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.resicomplex.people.dto.CreatePersonRequest;
import com.resicomplex.people.dto.PersonResponse;
import com.resicomplex.people.model.DocumentType;
import com.resicomplex.people.model.Person;
import com.resicomplex.people.model.PersonType;
import com.resicomplex.people.repository.PersonRepository;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

@ExtendWith(MockitoExtension.class)
class PersonServiceTest {

    @Mock
    private PersonRepository personRepository;

    @InjectMocks
    private PersonService personService;

    @Test
    void createPerson_shouldCreateResidentSuccessfully() {
        CreatePersonRequest request = new CreatePersonRequest(
                "Alexandra",
                "Garzón",
                DocumentType.CC,
                "1020304050",
                "Alexandra.Garzon@ResiComplex.com",
                "+57 3001234567",
                PersonType.RESIDENT,
                "unit-uuid-101"
        );

        when(personRepository.existsByDocumentNumber("1020304050")).thenReturn(false);
        when(personRepository.save(any(Person.class))).thenAnswer(invocation -> invocation.getArgument(0));

        PersonResponse response = personService.createPerson(request);

        assertNotNull(response.id());
        assertEquals("Alexandra", response.firstName());
        assertEquals("Garzón", response.lastName());
        assertEquals(DocumentType.CC, response.documentType());
        assertEquals("1020304050", response.documentNumber());
        assertEquals("alexandra.garzon@resicomplex.com", response.email());
        assertEquals("+57 3001234567", response.phone());
        assertEquals(PersonType.RESIDENT, response.personType());
        assertEquals("unit-uuid-101", response.associatedUnitId());
        assertEquals("ACTIVE", response.status());
        assertNotNull(response.createdAt());
        assertNotNull(response.updatedAt());
    }

    @Test
    void createPerson_shouldCreateCommercialOwnerSuccessfully() {
        CreatePersonRequest request = new CreatePersonRequest(
                "Carlos",
                "Mendoza",
                DocumentType.NIT,
                "900123456-7",
                "carlos@cafearoma.co",
                "3159876543",
                PersonType.COMMERCIAL_OWNER_TENANT,
                "unit-uuid-local-01"
        );

        when(personRepository.existsByDocumentNumber("900123456-7")).thenReturn(false);
        when(personRepository.save(any(Person.class))).thenAnswer(invocation -> invocation.getArgument(0));

        PersonResponse response = personService.createPerson(request);

        assertNotNull(response.id());
        assertEquals(DocumentType.NIT, response.documentType());
        assertEquals(PersonType.COMMERCIAL_OWNER_TENANT, response.personType());
        assertEquals("unit-uuid-local-01", response.associatedUnitId());
        assertEquals("ACTIVE", response.status());
    }

    @Test
    void createPerson_shouldThrowConflictWhenDocumentAlreadyExists() {
        CreatePersonRequest request = new CreatePersonRequest(
                "Alexandra",
                "Garzón",
                DocumentType.CC,
                "1020304050",
                "alexandra@resicomplex.com",
                "3001234567",
                PersonType.RESIDENT,
                "unit-uuid-101"
        );

        when(personRepository.existsByDocumentNumber("1020304050")).thenReturn(true);

        ResponseStatusException ex = assertThrows(
                ResponseStatusException.class,
                () -> personService.createPerson(request)
        );

        assertEquals(HttpStatus.CONFLICT, ex.getStatusCode());
        verify(personRepository, never()).save(any(Person.class));
    }

    @Test
    void getPersonsByType_shouldReturnMatchingPersons() {
        Person person = new Person();
        person.setId("person-1");
        person.setFirstName("Laura");
        person.setLastName("Gómez");
        person.setDocumentType(DocumentType.CC);
        person.setDocumentNumber("1098765432");
        person.setEmail("laura@resicomplex.com");
        person.setPhone("3101112233");
        person.setPersonType(PersonType.RESIDENT);
        person.setAssociatedUnitId("unit-101");
        person.setStatus("ACTIVE");
        person.setCreatedAt(LocalDateTime.now());
        person.setUpdatedAt(LocalDateTime.now());

        when(personRepository.findByPersonType(PersonType.RESIDENT)).thenReturn(List.of(person));

        List<PersonResponse> results = personService.getPersonsByType(PersonType.RESIDENT);

        assertEquals(1, results.size());
        assertEquals("1098765432", results.get(0).documentNumber());
        assertEquals(PersonType.RESIDENT, results.get(0).personType());
    }

    @Test
    void getPersonsByUnitId_shouldReturnUnitMembers() {
        Person person = new Person();
        person.setId("person-2");
        person.setFirstName("Andrés");
        person.setLastName("Pérez");
        person.setDocumentType(DocumentType.CE);
        person.setDocumentNumber("CE-889900");
        person.setEmail("andres@resicomplex.com");
        person.setPhone("3204445566");
        person.setPersonType(PersonType.RESIDENT);
        person.setAssociatedUnitId("unit-202");
        person.setStatus("ACTIVE");
        person.setCreatedAt(LocalDateTime.now());
        person.setUpdatedAt(LocalDateTime.now());

        when(personRepository.findByAssociatedUnitId("unit-202")).thenReturn(List.of(person));

        List<PersonResponse> results = personService.getPersonsByUnitId("unit-202");

        assertEquals(1, results.size());
        assertEquals("unit-202", results.get(0).associatedUnitId());
    }

    @Test
    void getPersonById_shouldThrowNotFoundWhenMissing() {
        when(personRepository.findById("non-existent-id")).thenReturn(Optional.empty());

        ResponseStatusException ex = assertThrows(
                ResponseStatusException.class,
                () -> personService.getPersonById("non-existent-id")
        );

        assertEquals(HttpStatus.NOT_FOUND, ex.getStatusCode());
    }
}
