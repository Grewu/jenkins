package ru.senla.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import ru.senla.model.dto.request.CommentRequest;
import ru.senla.model.dto.response.CommentResponse;
import ru.senla.model.entity.Comment;

@Mapper(componentModel = "spring")
public interface CommentMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "usersProfile", expression = "java(new UserProfile(commentRequest.usersProfile()))")
    @Mapping(target = "task", expression = "java(new Task(commentRequest.task()))")
    Comment toComment(CommentRequest commentRequest);


    @Mapping(target = "task", source = "task.id")
    @Mapping(target = "usersProfile", source = "usersProfile.id")
    CommentResponse toCommentResponse(Comment comment);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "task.id", source = "task")
    @Mapping(target = "usersProfile.id", source = "usersProfile")
    Comment update(CommentRequest commentRequest, @MappingTarget Comment current);
}
