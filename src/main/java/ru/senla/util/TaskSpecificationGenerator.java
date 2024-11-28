package ru.senla.util;

import jakarta.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;
import org.springframework.data.jpa.domain.Specification;
import ru.senla.model.entity.Task;
import ru.senla.model.filter.TaskFilter;

/** Utility class for generating JPA Specifications for Task filtering. */
public class TaskSpecificationGenerator {

  /**
   * Converts a {@link TaskFilter} into a {@link Specification} for the {@link Task} entity.
   *
   * <p>This method builds a compound predicate based on the filter criteria provided in the {@link
   * TaskFilter} object. It constructs a {@link Specification} that can be used in repository
   * queries to filter tasks according to the specified criteria.
   *
   * @param filter the filter criteria to apply when querying tasks
   * @return a {@link Specification} representing the filtering criteria
   */
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
