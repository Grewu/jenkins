package model.dto.request;

import model.entity.enums.DepartmentType;

public record DepartmentRequest(
        DepartmentType name
) {
}
