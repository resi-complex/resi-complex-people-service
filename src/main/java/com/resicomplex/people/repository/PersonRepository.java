package com.resicomplex.people.repository;

import com.resicomplex.people.model.Person;
import com.resicomplex.people.model.PersonType;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonRepository extends JpaRepository<Person, String> {

    boolean existsByDocumentNumber(String documentNumber);

    List<Person> findByPersonType(PersonType personType);

    List<Person> findByAssociatedUnitId(String associatedUnitId);
}
