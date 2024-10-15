CREATE TYPE user_role AS ENUM (
    'ADMIN',
    'USER',
    'GUEST'
    );

CREATE TYPE departments_type AS ENUM (
    'DEVELOPERS',
    'MANAGERS',
    'MARKETING',
    'HR',
    'SALES',
    'SUPPORT',
    'FINANCE',
    'LEGAL',
    'ADMINISTRATION',
    'OPERATIONS',
    'RESEARCH',
    'DESIGN'
    );

CREATE TYPE positions_type AS ENUM (
    'MANAGER',
    'DEVELOPER',
    'MARKETING_SPECIALIST',
    'HR'
    );
CREATE TYPE status_type AS ENUM (
    'IN_PROGRESS',
    'NOT_STARTED',
    'COMPLETED'
    );

CREATE TYPE priority_type AS ENUM (
    'HIGH',
    'MEDIUM',
    'LOW'
    );
CREATE TYPE privileges_type AS ENUM (
    'READ',
    'WRITE',
    'DELETE'
    );
