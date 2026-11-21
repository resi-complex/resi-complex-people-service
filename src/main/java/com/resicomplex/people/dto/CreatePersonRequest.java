package com.resicomplex.people.dto;

import com.resicomplex.people.model.DocumentType;
import com.resicomplex.people.model.PersonType;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record CreatePersonRequest(
        @NotBlank(message = "First name is required")
        @Size(max = 100, message = "First name must not exceed 100 characters")
        String firstName,

        @NotBlank(message = "Last name is required")
        @Size(max = 100, message = "Last name must not exceed 100 characters")
        String lastName,

        @NotNull(message = "Document type is required (CC, CE, NIT or PASSPORT)")
        DocumentType documentType,

        @NotBlank(message = "Document number is required")
        @Size(max = 50, message = "Document number must not exceed 50 characters")
        String documentNumber,

        @NotBlank(message = "Email is required")
        @Email(message = "Email must have a valid format")
        @Size(max = 255, message = "Email must not exceed 255 characters")
        String email,

        @NotBlank(message = "Phone number is required")
        @Size(max = 30, message = "Phone number must not exceed 30 characters")
        String phone,

        @NotNull(message = "Person type is required (RESIDENT or COMMERCIAL_OWNER_TENANT)")
        PersonType personType,

        @NotBlank(message = "Associated unit ID is required")
        @Size(max = 36, message = "Associated unit ID must not exceed 36 characters")
        String associatedUnitId
) {}
