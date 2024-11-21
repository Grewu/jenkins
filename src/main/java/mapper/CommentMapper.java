package mapper;

import model.dto.request.CommentRequest;
import model.dto.response.CommentResponse;
import model.entity.Comment;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CommentMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "usersProfile", expression = "java(new UserProfile(commentRequest.usersProfile()))")
    @Mapping(target = "task", expression = "java(new Task(commentRequest.task()))")
    Comment toComment(CommentRequest commentRequest);


    @Mapping(target = "task", source = "task.id")
    @Mapping(target = "usersProfile", source = "usersProfile.id")
    CommentResponse toCommentResponse(Comment comment);
}
