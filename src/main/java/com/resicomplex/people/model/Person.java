package com.resicomplex.people.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "persons")
@Getter
@Setter
@NoArgsConstructor
public class Person {

    @Id
    @Column(name = "id", length = 36, nullable = false)
    private String id;

    @Column(name = "first_name", length = 100, nullable = false)
    private String firstName;

    @Column(name = "last_name", length = 100, nullable = false)
    private String lastName;

    @Enumerated(EnumType.STRING)
    @Column(name = "document_type", length = 20, nullable = false)
    private DocumentType documentType;

    @Column(name = "document_number", length = 50, nullable = false, unique = true)
    private String documentNumber;

    @Column(name = "email", length = 255, nullable = false)
    private String email;

    @Column(name = "phone", length = 30, nullable = false)
    private String phone;

    @Enumerated(EnumType.STRING)
    @Column(name = "person_type", length = 30, nullable = false)
    private PersonType personType;

    @Column(name = "associated_unit_id", length = 36, nullable = false)
    private String associatedUnitId;

    @Column(name = "status", length = 20, nullable = false)
    private String status;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;
}
