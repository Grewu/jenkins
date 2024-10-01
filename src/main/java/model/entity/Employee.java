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


    public Employee(Employee.Builder builder) {
        this.id = builder.id;
        this.firstName = builder.firstName;
        this.lastName = builder.lastName;
        this.position = builder.position;
        this.departmentId = builder.departmentId;
        this.email = builder.email;
        this.password = builder.password;
    }


    public static class Builder {
        private Long id;
        private String firstName;
        private String lastName;
        private Position position;
        private Long departmentId;
        private String email;
        private String password;


        public Builder() {
        }

        public Employee.Builder setId(Long id) {
            this.id = id;
            return this;
        }

        public Employee.Builder setFirstName(String firstName) {
            this.firstName = firstName;
            return this;
        }


        public Employee.Builder setLastName(String lastName) {
            this.lastName = lastName;
            return this;
        }

        public Employee.Builder setPosition(Position position) {
            this.position = position;
            return this;
        }

        public Employee.Builder setDepartmentId(Long departmentId) {
            this.departmentId = departmentId;
            return this;
        }

        public Employee.Builder setEmail(String email) {
            this.email = email;
            return this;
        }

        public Employee.Builder setPassword(String password) {
            this.password = password;
            return this;
        }

        public Employee build() {
            return new Employee(this);
        }
    }

    public Long getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public Position getPosition() {
        return position;
    }

    public Long getDepartmentId() {
        return departmentId;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }
}
