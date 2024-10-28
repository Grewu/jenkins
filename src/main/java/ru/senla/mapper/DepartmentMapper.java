package ru.senla.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import ru.senla.model.dto.request.DepartmentRequest;
import ru.senla.model.dto.response.DepartmentResponse;
import ru.senla.model.entity.Department;

/**
 * Mapper interface for converting between {@link Department} entities and their
 * corresponding DTOs, {@link DepartmentRequest} and {@link DepartmentResponse}.
 *
 * <p>
 * This interface uses MapStruct to generate the implementation for mapping
 * properties between the entities and DTOs. It is annotated with
 * {@code @Mapper(componentModel = "spring")} to enable Spring's component
 * scanning.
 * </p>
 */
@Mapper(componentModel = "spring")
public interface DepartmentMapper {

    /**
     * Converts a {@link DepartmentRequest} to a {@link Department} entity.
     *
     * @param departmentRequest the DTO containing the details of the department
     * @return the converted {@link Department} entity
     */
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "name", source = "name")
    Department toDepartment(DepartmentRequest departmentRequest);

    /**
     * Converts a {@link Department} entity to a {@link DepartmentResponse} DTO.
     *
     * @param department the department entity to be converted
     * @return the converted {@link DepartmentResponse} DTO
     */
    DepartmentResponse toDepartmentResponse(Department department);

    /**
     * Updates an existing {@link Department} entity with values from a
     * {@link DepartmentRequest}.
     *
     * @param departmentRequest the DTO containing the updated department details
     * @param current the existing {@link Department} entity to be updated
     * @return the updated {@link Department} entity
     */
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "name", source = "name")
    Department update(DepartmentRequest departmentRequest, @MappingTarget Department current);
}
