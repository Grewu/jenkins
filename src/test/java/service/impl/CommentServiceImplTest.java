package service.impl;

import dao.api.CommentDao;
import data.CommentTestData;
import exception.EntityNotFoundException;
import mapper.CommentMapper;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CommentServiceImplTest {

    @Mock
    private CommentMapper commentMapper;

    @Mock
    private CommentDao commentDao;

    @InjectMocks
    private CommentServiceImpl commentService;

    @Nested
    class Create {
        @Test
        void createShouldReturnCommentResponse() {
            // given
            var commentRequest = CommentTestData.builder().build().buildCommentRequest();
            var comment = CommentTestData.builder().build().buildComment();
            var expectedResponse = CommentTestData.builder().build().buildCommentResponse();

            when(commentMapper.toComment(commentRequest)).thenReturn(comment);
            when(commentDao.create(comment)).thenReturn(comment);
            when(commentMapper.toCommentResponse(comment)).thenReturn(expectedResponse);

            // when
            var actualResponse = commentService.create(commentRequest);

            // then
            verify(commentDao).create(comment);
            verify(commentMapper).toComment(commentRequest);
            verify(commentMapper).toCommentResponse(comment);
            assertEquals(expectedResponse, actualResponse);
        }
    }

    @Nested
    class GetAll {
        @Test
        void getAllShouldReturnListOfCommentResponses() {
            // given
            var comments = List.of(CommentTestData.builder().build().buildComment());
            var expectedResponses = List.of(CommentTestData.builder().build().buildCommentResponse());

            when(commentDao.findAll()).thenReturn(comments);
            when(commentMapper.toCommentResponse(comments.get(0))).thenReturn(expectedResponses.get(0));

            // when
            var actualResponses = commentService.getAll();

            // then
            verify(commentDao).findAll();
            verify(commentMapper).toCommentResponse(comments.get(0));
            assertEquals(expectedResponses, actualResponses);
        }
    }

    @Nested
    class GetById {
        @Test
        void getByIdShouldReturnExpectedCommentResponse() {
            // given
            var comment = CommentTestData.builder().build().buildComment();
            var expectedResponse = CommentTestData.builder().build().buildCommentResponse();

            when(commentDao.findById(comment.getId())).thenReturn(Optional.of(comment));
            when(commentMapper.toCommentResponse(comment)).thenReturn(expectedResponse);

            // when
            var actualResponse = commentService.getById(comment.getId());

            // then
            assertEquals(expectedResponse, actualResponse);
        }

        @Test
        void getByIdShouldThrowNotFoundException() {
            // given
            var id = -1L;
            var expectedMessage = "Comment with ID -1 was not found";

            // when
            var exception = assertThrows(EntityNotFoundException.class,
                    () -> commentService.getById(id));

            // then
            assertEquals(expectedMessage, exception.getMessage());
        }
    }

    @Nested
    class Update {
        @Test
        void updateShouldReturnCommentResponse() {
            // given
            var commentRequest = CommentTestData.builder().build().buildCommentRequest();
            var expectedResponse = CommentTestData.builder().build().buildCommentResponse();
            var comment = CommentTestData.builder().build().buildComment();

            when(commentMapper.toComment(commentRequest)).thenReturn(comment);
            when(commentDao.update(comment)).thenReturn(comment);
            when(commentMapper.toCommentResponse(comment)).thenReturn(expectedResponse);

            // when
            var actualResponse = commentService.update(1L, commentRequest);

            // then
            assertEquals(expectedResponse, actualResponse);
        }
    }

    @Nested
    class Delete {
        @Test
        void deleteShouldCallDaoDeleteMethod() {
            // given
            var commentRequest = CommentTestData.builder().build().buildCommentRequest();
            var comment = CommentTestData.builder().build().buildComment();

            when(commentMapper.toComment(commentRequest)).thenReturn(comment);

            // when
            commentService.delete(commentRequest);

            // then
            verify(commentDao).delete(comment);
        }
    }
}
