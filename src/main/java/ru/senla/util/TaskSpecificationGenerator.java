package ru.senla.util;

import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;
import ru.senla.model.entity.Task;
import ru.senla.model.filter.TaskFilter;

import java.util.ArrayList;
import java.util.List;

public class TaskSpecificationGenerator {
    public static Specification<Task> filterToSpecification(TaskFilter filter) {
        return ((root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (filter.name() != null) {
                predicates.add(cb.equal(root.get("name"), filter.name()));
            }

            if (filter.assignedTo() != null) {
                predicates.add(cb.equal(root.get("id"), filter.assignedTo()));
            }

            if (filter.createdBy() != null) {
                predicates.add(cb.equal(root.get("id"), filter.createdBy()));
            }

            if (filter.project() != null) {
                predicates.add(cb.equal(root.get("id"), filter.project()));
            }

            if (filter.dueDate() != null) {
                predicates.add(cb.equal(root.get("dueDate"), filter.dueDate()));
            }

            if (filter.status() != null) {
                predicates.add(cb.equal(root.get("status"), filter.status()));
            }

            if (filter.priority() != null) {
                predicates.add(cb.equal(root.get("priority"), filter.priority()));
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        });
    }
}
