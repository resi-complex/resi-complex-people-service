CREATE TABLE persons (
    id VARCHAR(36) PRIMARY KEY,
    first_name VARCHAR(100) NOT NULL,
    last_name VARCHAR(100) NOT NULL,
    document_type VARCHAR(20) NOT NULL, -- CC, CE, NIT, PASSPORT
    document_number VARCHAR(50) NOT NULL UNIQUE,
    email VARCHAR(255) NOT NULL,
    phone VARCHAR(30) NOT NULL,
    person_type VARCHAR(30) NOT NULL, -- 'RESIDENT' o 'COMMERCIAL_OWNER_TENANT'
    associated_unit_id VARCHAR(36) NOT NULL,
    status VARCHAR(20) NOT NULL DEFAULT 'ACTIVE',
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    deleted_at TIMESTAMP NULL
);

CREATE INDEX idx_persons_associated_unit_id ON persons (associated_unit_id);
CREATE INDEX idx_persons_person_type ON persons (person_type);
CREATE INDEX idx_persons_status ON persons (status);
