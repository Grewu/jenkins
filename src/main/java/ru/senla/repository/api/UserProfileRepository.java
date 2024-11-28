package ru.senla.repository.api;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.senla.model.entity.UserProfile;
import ru.senla.repository.AbstractRepository;

@Repository
public interface UserProfileRepository extends AbstractRepository<Long, UserProfile> {

  @Query(
      """
            SELECT new ru.senla.model.entity.UserProfile(
            u.id,
            u.firstName,
            u.lastName,
            u.position,
            u.department,
            u.user)
            FROM UserProfile u
            WHERE u.department.id = :departmentId
            """)
  Page<UserProfile> findByDepartmentId(@Param("departmentId") Long departmentId, Pageable pageable);
}
