package com.resicomplex.people.dto;

import com.resicomplex.people.model.DocumentType;
import com.resicomplex.people.model.PersonType;
import java.time.LocalDateTime;

public record PersonResponse(
        String id,
        String firstName,
        String lastName,
        DocumentType documentType,
        String documentNumber,
        String email,
        String phone,
        PersonType personType,
        String associatedUnitId,
        String status,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {}
