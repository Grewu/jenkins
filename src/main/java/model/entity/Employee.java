package model.entity;

import model.entity.enums.Position;

public class Employee {
    private final Long id;
    private final String firstName;
    private final String lastName;
    private final Position position;
    private final Long departmentId;
    private final String email;
    private final String password;

    public Employee(Long id, String firstName, String lastName, Position position, Long departmentId, String email, String password) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.position = position;
        this.departmentId = departmentId;
        this.email = email;
        this.password = password;
    }
}
