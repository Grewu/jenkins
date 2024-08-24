package model.entity;

public class Department {
    private final Long id;
    private final String name;

    private Department(Builder builder) {
        this.id = builder.id;
        this.name = builder.name;
    }

    public static class Builder {
        private Long id;
        private String name;

        public Builder() {
        }

        public Builder setId(Long id) {
            this.id = id;
            return this;
        }

        public Builder setName(String name) {
            this.name = name;
            return this;
        }


        public Department build() {
            return new Department(this);
        }
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

}
