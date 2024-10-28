package ru.senla.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import ru.senla.model.dto.request.CommentRequest;
import ru.senla.model.dto.response.CommentResponse;
import ru.senla.model.entity.Comment;

/**
 * Mapper interface for converting between {@link Comment} entities and their
 * corresponding DTOs, {@link CommentRequest} and {@link CommentResponse}.
 *
 * <p>
 * This interface uses MapStruct to generate the implementation for mapping
 * properties between the entities and DTOs. It is annotated with
 * {@code @Mapper(componentModel = "spring")} to enable Spring's component
 * scanning.
 * </p>
 */
@Mapper(componentModel = "spring")
public interface CommentMapper {

    /**
     * Converts a {@link CommentRequest} to a {@link Comment} entity.
     *
     * @param commentRequest the DTO containing the details of the comment
     * @return the converted {@link Comment} entity
     */
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "usersProfile", expression = "java(new UserProfile(commentRequest.usersProfile()))")
    @Mapping(target = "task", expression = "java(new Task(commentRequest.task()))")
    Comment toComment(CommentRequest commentRequest);

    /**
     * Converts a {@link Comment} entity to a {@link CommentResponse} DTO.
     *
     * @param comment the comment entity to be converted
     * @return the converted {@link CommentResponse} DTO
     */
    @Mapping(target = "task", source = "task.id")
    @Mapping(target = "usersProfile", source = "usersProfile.id")
    CommentResponse toCommentResponse(Comment comment);

    /**
     * Updates an existing {@link Comment} entity with values from a
     * {@link CommentRequest}.
     *
     * @param commentRequest the DTO containing the updated comment details
     * @param current the existing {@link Comment} entity to be updated
     */
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "task.id", source = "task")
    @Mapping(target = "usersProfile.id", source = "usersProfile")
    Comment update(CommentRequest commentRequest, @MappingTarget Comment current);
}
