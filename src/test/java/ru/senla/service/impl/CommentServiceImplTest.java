package ru.senla.service.impl;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import ru.senla.data.CommentTestData;
import ru.senla.exception.EntityNotFoundException;
import ru.senla.mapper.CommentMapper;
import ru.senla.repository.api.CommentRepository;


import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatNoException;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CommentServiceImplTest {

    @Mock
    private CommentMapper commentMapper;

    @Mock
    private CommentRepository commentRepository;

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
            when(commentRepository.save(comment)).thenReturn(comment);
            when(commentMapper.toCommentResponse(comment)).thenReturn(expectedResponse);

            // when
            var actualResponse = commentService.create(commentRequest);

            // then
            verify(commentRepository).save(comment);
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
            var pageable = Pageable.ofSize(2);
            var comments = List.of(CommentTestData.builder().build().buildComment());
            var expectedResponses = List.of(CommentTestData.builder().build().buildCommentResponse());

            var commentPage = new PageImpl<>(comments,pageable,2);

            doReturn(commentPage)
                    .when(commentRepository).findAll(pageable);

            IntStream.range(0, comments.size())
                    .forEach(i -> doReturn(expectedResponses.get(i))
                            .when(commentMapper).toCommentResponse(comments.get(i)));

            // when
            var actualResponses = commentService.getAll(pageable).getContent();

            // then
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

            when(commentRepository.findById(comment.getId())).thenReturn(Optional.of(comment));
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
            // when
            var exception = assertThrows(EntityNotFoundException.class,
                    () -> commentService.getById(id));

            // then
            assertEquals("Comment with ID -1 was not found", exception.getMessage());
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

            when(commentRepository.findById(comment.getId())).thenReturn(Optional.of(comment));
            when(commentMapper.update(commentRequest, comment)).thenReturn(comment);
            when(commentRepository.save(comment)).thenReturn(comment);
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
            var id = 1L;

            doNothing()
                    .when(commentRepository).deleteById(id);

            assertThatNoException()
                    .isThrownBy(() -> commentService.delete(id));
        }
    }
}
