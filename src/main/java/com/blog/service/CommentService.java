package com.blog.service;

import com.blog.payload.CommentDto;

import java.util.List;

public interface CommentService {


    CommentDto createComment(long postId, CommentDto commentDto);

    List<CommentDto> getComments(long postId);

    CommentDto getComment(long postId, long commentId);

    CommentDto updateComment(long postId, long commentId, CommentDto dto);

    void deleteComment(long postId, long commentId);
}


